package com.cc.api.system.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CodeUtils {

        /**
         * 默认编码方式
         */
        public static final String DEFAULT_CHARSET = "UTF-8";
        /**
         * 默认二维码图片格式
         */
        public static final String DEFAULT_SUBFIX = "PNG";

        /**
         * 生成二维码默认宽度
         */
        public static final int DEFAULT_WIDTH = 250;
        /**
         * 生成二维码默认高度
         */
        public static final int DEFAULT_HEIGHT = 300;
        /**
         * 默认二维码中间log宽度
         */
        public static final int DEFAULT_LOG_WIDTH = 50;
        /**
         * 默认二维码中间log高度
         */
        public static final int DEFAULT_LOG_HEIGHT = 50;
        /**
         * 生成二维码默认保存位置
         */
        public static final String DEFAULT_QRCODE_PATH = System.getProperty("user.dir") + "\\" + "qrCode." + DEFAULT_SUBFIX;
        /**
         * log默认路径
         */
//        public static final String DEFAULT_LOG_PATH = CodeUtils.class.getClassLoader().getResource("log/log.jpg")
//                .getPath();

        /**
         * 由字符串生成二维码BufferedImage对象
         *
         * @param content 字符串内容
         * @param width   二维码宽度,如果为空或小于等于0采用默认宽度
         * @param height  二维码高度,如果为空或小于等于0采用默认高度
         * @return
         */
        private static BufferedImage createQrCodeBufferedImage(String content, Integer width, Integer height)
                throws Exception {
            BufferedImage resultImage = null;
            if (!StringUtils.isEmpty(content)) {
                Map<EncodeHintType, Object> hints = new HashMap<>();
                hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_CHARSET);// 指定字符编码为UTF-8
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 指定二维码的纠错等级为中级
                hints.put(EncodeHintType.MARGIN, 2);// 设置图片的边距

                QRCodeWriter writer = new QRCodeWriter();
                width = width != null && width > 0 ? width : DEFAULT_WIDTH;
                height = height != null && height > 0 ? height : DEFAULT_HEIGHT;

                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
                // 写到字节数据中
                // MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_SUBFIX, os);
                // resultImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                // ImageIO.write(resultImage, DEFAULT_SUBFIX, os);

                // 写到文件中
                // MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_SUBFIX,
                // Paths.get(DEFAULT_PATH));

                resultImage = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        resultImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF008000 : 0xFFFFFFFF);// 0xFF000000 黑色 0xFF008000 绿色
                    }
                }
            }
            return resultImage;
        }

        /**
         * 由字符串生成base64格式的简单二维码
         *
         * @param content      字符串内容
         * @param width        二维码宽度,如果为空或小于等于0采用默认宽度
         * @param height       二维码高度,如果为空或小于等于0采用默认高度
         * @param isSaveToPath 是否保存到文件中
         * @return
         */
        public static String productQrCodeString(String content, Integer width, Integer height, boolean isSaveToPath) {
            String resultImage = "";
            if (!StringUtils.isEmpty(content)) {
                try {
                    BufferedImage image = createQrCodeBufferedImage(content, width, height);
                    if (image != null) {
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        ImageIO.write(image, DEFAULT_SUBFIX, os);
                        resultImage = "data:image/" + DEFAULT_SUBFIX + ";base64,"
                                + new String(Base64.getEncoder().encode(os.toByteArray()));
                        if (isSaveToPath) {
                            ImageIO.write(image, DEFAULT_SUBFIX, new FileOutputStream(DEFAULT_QRCODE_PATH));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return resultImage;
        }

        /**
         * 由字符串生成base64格式的复杂二维码
         *
         * @param content        字符串内容
         * @param width          二维码宽度,如果为空或小于等于0采用默认宽度
         * @param height         二维码高度,如果为空或小于等于0采用默认高度
         * @param isFixedLogSize 是否固定log图片大小
         * @param text           二维码底部文本内容
         * @return
         */
        public static String productQrCodeWithLog(String content, Integer width, Integer height, boolean isFixedLogSize, String text) {
            String resultImage = "";
            if (!StringUtils.isEmpty(content)) {
                try {
                    width = width != null && width > 0 ? width : DEFAULT_WIDTH;
                    height = height != null && height > 0 ? height : DEFAULT_HEIGHT;
                    BufferedImage image = createQrCodeBufferedImage(content, width, height);
                    if (image != null) {
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                       // insertImageAndText(image, width, height,isFixedLogSize, text);
                        insertText(image,width,height,text);
                        ImageIO.write(image, DEFAULT_SUBFIX, os);
                        resultImage = "data:image/" + DEFAULT_SUBFIX + ";base64,"
                                + new String(Base64.getEncoder().encode(os.toByteArray()));
//                        if (isSaveToPath) {
//                            ImageIO.write(image, DEFAULT_SUBFIX, new FileOutputStream(DEFAULT_QRCODE_PATH));
//                        }
                        System.out.println("ResultImage:"+resultImage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return resultImage;
        }

    private static void insertText(BufferedImage source, Integer width, Integer height, String text) throws Exception {
        Graphics2D graph = source.createGraphics();

        if (!StringUtils.isEmpty(text)) {
            int fontStyle = 1;
            int fontSize = 12; //
            // 计算文字开始的位置(居中显示)
            // x开始的位置：（图片宽度-字体大小*字的个数）/2
            int startX = (width - (fontSize * text.length())) / 2;
            // y开始的位置：图片高度-（图片高度-图片宽度）/2
            int startY = height - (height - width) / 2;
            graph.setColor(Color.BLUE);
            graph.setFont(new Font(null, fontStyle, fontSize)); // 字体风格与字体大小 graph.drawString(text, startX, startY);
            graph.drawString(text, startX, startY);
        }

        graph.dispose();

    }


        /**
         * 插入LOGO
         *
         * @param source         二维码图片
         * @param width          二维码宽度,如果为空或小于等于0采用默认宽度
         * @param height         二维码高度,如果为空或小于等于0采用默认高度
         * @param imgPath        LOGO图片地址
         * @param isFixedLogSize 是否固定二维码中间log图标大小
         * @param text           二维码底部文本内容
         * @throws Exception
         */
        private static void insertImageAndText(BufferedImage source, Integer width, Integer height, String imgPath,
                                               boolean isFixedLogSize, String text) throws Exception {
            File file = new File(imgPath);
            if (!file.exists()) {
                System.err.println("" + imgPath + "   该文件不存在！");
                return;
            }
            Image src = ImageIO.read(new File(imgPath));
            int lwidth = src.getWidth(null);
            int lheight = src.getHeight(null);
            if (isFixedLogSize || lwidth >= width || lheight >= height) { // 固定LOGO大小
                if (lwidth > width) {
                    lwidth = DEFAULT_LOG_WIDTH;
                }
                if (lheight > height) {
                    lheight = DEFAULT_LOG_HEIGHT;
                }
                Image image = src.getScaledInstance(lwidth, lheight, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(lwidth, lheight, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                src = image;
            }
            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            int x = (width - lwidth) / 2;
            int y = (height - lheight) / 2;
            graph.drawImage(src, x, y, lwidth, lheight, null);
            Shape shape = new RoundRectangle2D.Float(x, y, lwidth, lheight, 6, 6);
            graph.setStroke(new BasicStroke(3f));
            graph.draw(shape);

            if (!StringUtils.isEmpty(text)) {
                int fontStyle = 1;
                int fontSize = 12; //
                // 计算文字开始的位置(居中显示)
                // x开始的位置：（图片宽度-字体大小*字的个数）/2
                int startX = (width - (fontSize * text.length())) / 2;
                // y开始的位置：图片高度-（图片高度-图片宽度）/2
                int startY = height - (height - width) / 2;
                graph.setColor(Color.BLUE);
                graph.setFont(new Font(null, fontStyle, fontSize)); // 字体风格与字体大小 graph.drawString(text, startX, startY);
                graph.drawString(text, startX, startY);
            }

            graph.dispose();
        }

        /**
         * 解码二维码内容
         *
         * @param file
         * @return
         * @throws Exception
         */
        public static String decode(File file) throws Exception {
            BufferedImage image;
            image = ImageIO.read(file);
            if (image == null) {
                return null;
            }
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result;
            Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, DEFAULT_CHARSET);
            result = new MultiFormatReader().decode(bitmap, hints);
            String resultStr = result.getText();
            return resultStr;
        }

        public static void main(String[] args) throws Exception {
            System.out.println(decode(new File(DEFAULT_QRCODE_PATH)));
        }
    }

