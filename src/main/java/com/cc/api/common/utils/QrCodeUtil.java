package com.cc.api.common.utils;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;


@Slf4j
public class QrCodeUtil {
    //编码格式,采用utf-8
    private static final String UNICODE = "utf-8";
    //图片格式
    private static final String FORMAT = "JPG";
    //二维码宽度,单位：像素pixels
    private static final int QRCODE_WIDTH = 300;
    //二维码高度,单位：像素pixels
    private static final int QRCODE_HEIGHT = 300;
    //LOGO宽度,单位：像素pixels
    private static final int LOGO_WIDTH = 100;
    //LOGO高度,单位：像素pixels
    private static final int LOGO_HEIGHT = 100;

    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param logoPath 图片地址
     * @param needCompress 是否压缩
     * @return
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String logoPath, boolean needCompress,String text,Integer width, Integer height) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 3);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//        int width = bitMatrix.getWidth();
//        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        QrCodeUtil.insertImage(image, logoPath, needCompress,text);
        return image;
    }

    /**
     * 插入LOGO
     * @param source 二维码图片
     * @param logoPath LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress,String text) throws Exception {
        File file = new File(logoPath);
        if (!file.exists()) {
            throw new Exception("logo file not found.");
        }
        Image src = ImageIO.read(new File(logoPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_WIDTH - width) / 2;
        int y = (QRCODE_HEIGHT - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        if (!StringUtils.isEmpty(text)) {
            int fontStyle = 1;
            int fontSize = 12; //
            // 计算文字开始的位置(居中显示)
            // x开始的位置：（图片宽度-字体大小*字的个数）/2
            int startX = (width - (fontSize * text.length())) / 2;
            System.out.println(startX);
            // y开始的位置：图片高度-（图片高度-图片宽度）/2
            int startY = height - (height - width) / 2;
            System.out.println(startY);
            graph.setColor(Color.BLACK);
            graph.setFont(new Font(null, fontStyle, fontSize)); // 字体风格与字体大小 graph.drawString(text, startX, startY);
            graph.drawString(text, startX, startY);
        }
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     * 调用者指定二维码文件名
     * @param content 二维码的内容
     * @param logoPath 中间图片地址
     * @param destPath 存储路径
     * @param fileName 文件名称
     * @param needCompress 是否压缩
     * @return
     * @throws Exception
     */
    public static String encode(String content, String logoPath, String destPath, String fileName, boolean needCompress,Integer width, Integer height) throws Exception {
        BufferedImage image = QrCodeUtil.createImage(content, logoPath, needCompress,null,width,height);
        mkdirs(destPath);
        //文件名称通过传递
        fileName = fileName.substring(0, fileName.indexOf(".")>0?fileName.indexOf("."):fileName.length())
                + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 创建文件夹， mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @param destPath
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 解析二维码
     * @param path 二维码图片路径
     * @return String 二维码内容
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, UNICODE);
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    private static void insertText(BufferedImage source, Integer width, Integer height, String text) throws Exception {
        Graphics2D graph = source.createGraphics();
        if (!StringUtils.isEmpty(text)) {
            int fontStyle = 1;
            int fontSize = 12; //
            // 计算文字开始的位置(居中显示)
            // x开始的位置：（图片宽度-字体大小*字的个数）/2
            int startX = (width - (fontSize * text.length())) / 2 + (fontSize*text.length()) /4;
            // y开始的位置：图片高度-（图片高度-图片宽度）/2
            int startY = (height - (height - width) / 2 )*19/20;
            graph.setColor(Color.BLUE);
            graph.setFont(new Font(null, fontStyle, fontSize)); // 字体风格与字体大小 graph.drawString(text, startX, startY);
            graph.drawString(text, startX, startY);
        }
        graph.dispose();
    }

    public static String createQRCode(String content, int width, int height,String text) throws IOException {

        String resultImage = "";
        if (!StringUtils.isEmpty(content)) {
            ServletOutputStream stream = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            @SuppressWarnings("rawtypes")
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 指定字符编码为“utf-8”
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 指定二维码的纠错等级为中级
            hints.put(EncodeHintType.MARGIN, 2); // 设置图片的边距
            try {
                QRCodeWriter writer = new QRCodeWriter();
//                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//                ImageIO.write(bufferedImage, "png", os);
                BufferedImage b1 = QrCodeUtil.createImage(content,null,false,text,width,height);
                insertText(b1,width,height,text);

                ImageIO.write(b1, "png", os);
                /**
                 * 原生转码前面没有 data:image/png;base64 这些字段，返回给前端是无法被解析，可以让前端加，也可以在下面加上
                 */
                resultImage = new String("data:image/png;base64," + Base64.encode(os.toByteArray()));

                return resultImage;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
        return null;
    }
}
