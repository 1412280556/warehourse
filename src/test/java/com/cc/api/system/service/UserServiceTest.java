package com.cc.api.system.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.iherus.codegen.qrcode.SimpleQrcodeGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cc.api.biz.service.InStockService;
import com.cc.api.biz.service.ScannerService;
import com.cc.api.common.pojo.biz.Scanner;
import com.cc.api.common.pojo.system.User;
import com.cc.api.common.utils.QrCodeUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private InStockService inStockService;
    
    @Autowired
    private ScannerService scannerService;

    @Test
    void findUserByName() {
        User user = userService.findUserByName("admin");
        log.info("user={}",user);
    }

    @Test
    void code() throws IOException {


        System.out.println(QrCodeUtil.createQRCode("this is a code",100,100,"111"));

    }

    @Test
    void CodeUtils(){

        String content = "www.baidu.com";
        CodeUtils.productQrCodeWithLog(content,100,100,true, String.valueOf(63));
    }

    @Test
    void freemarker() throws Exception {

//
//        Map<String, Object> dataMap = new HashMap<String, Object>();
//        //查出来列表中的所有的id,和ImageURL。（base64的图片，需要除去头）;
//        InStockDetailVO detailVO = inStockService.detail(127L,1,3);
//        System.out.println(detailVO);
//        for (InStockItemVO detail : detailVO.getItemVOS()){
//            System.out.println(detail.getImageUrl());
//            String ImageUrl = detail.getImageUrl().substring(22);
//            //日期
//            dataMap.put("date", new Date());
//
//            //图片
//            dataMap.put("image", ImageUrl);
//
//            WordUtil wordUtil = new WordUtil();
//
//            wordUtil.createWord(dataMap, "test.ftl", "D:/", "a.doc");
//        }
    }
    
    @Test
    void testQrext4j() throws IOException {
    	String content = "123";

    	new SimpleQrcodeGenerator().generate(content).toFile("C:\\Users\\Administrator\\Desktop\\testQrcode\\AodaCat_default.png");
    }
    
//    @Test
//    void testExcel(HttpServletResponse response) throws Exception {
//    	List<Scanner> scannerList = scannerService.findAll();
//        String fileName = "耳标二维码导出" + new Date();
//        ExcelUtil.writeExcel(response, fileName, ExcelUtil.exportPicture(scannerList, "123"));
//
//    }
    
    @Test
    void test() throws IOException {
    	String base64 = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAIAAAD/gAIDAAAEVElEQVR4Xu2VQYojUQxD+/6Xzgw0CKNX49iqQArmP7KSJX2XN/l5Hcb8uHD4N+dYC86xFpxjLTjHWnCOteAca8E51oJzrAXnWAvOsRacYy04x1pwjrXgHGvBOdaCc6wF4bF+7sGe0r0YZXjdmDDp7y9hT+lejDK8bkyY9PeXsKd0L0YZXjcmTGYPNymNGmguBe/JUpUwmT3cpDRqoLkUvCdLVcJk9nCT0qiB5lLwnixVCZN8WAqhR0oGe6QQeqRsCZN8WAqhR0oGe6QQeqRsCZN8WAqhR0oGe6QQeqRsCZN8WAqhh8oEpqgQeqRsCZN8WAqhh8oEpqgQeqRsCZN8WAqhh8oEpqgQeqRsCZN8WAqhhwpH9EwUQo+ULWGSD0sh9FDhiJ6JQuiRsiVM8mEphB4qHNEzUQg9UraEyexhpqQ0lAJP+aAlS1XCZPYwU1IaSoGnfNCSpSphMnuYKSkNpcBTPmjJUpUwqYcz2JMpGerZEib9/SXsyZQM9WwJk/7+EvZkSoZ6tuTJj8APKB/llNx3+PIGPEQ5jlNy3+HLG/AQ5ThOyX2HcIPJB9AjhZScm6lMKE0e98GYMDl5mB4ppOTcTGVCafK4D8aEycnD9EghJedmKhNKk8d9MCZP/lLW81WokMaj0cQzUTjakid/8UWutix2p/FoNPFMFI625MlffJGrLYvdaTwaTTwThaMtYbJ5uGzlHh9cxUXjKQXv8fANwq5mlbKne3xwFReNpxS8x8M3CLuaVcqe7vHBVVw0nlLwHg/fIOxqVmlGJDMzNVHuE3Y1qzQjkpmZmij3CbuaVZoRycxMTZT73O1qduKICqFHCik5x62tecjdimYVjqgQeqSQknPc2pqH3K1oVuGICqFHCik5x62tecgHKu7AL2kUIg9xa8GtY/LkR+AHNAqRh7i14NYxefIj8AMahchD3Fpw65gw6e8vaXrKIwtPQ5a6JKwomyc0PeWRhachS10SVpTNE5qe8sjC05ClLgkrsg2aFEdSOKKnUT5IWJrt1KQ4ksIRPY3yQcLSbKcmxZEUjuhplA8SlnInKYQeKg0yr1INXjcmTPLhsoxDD5UGmVepBq8bEyb5cFnGoYdKg8yrVIPXjQmTfLgs49AjhZSc49arwkbhaEuY5MNlGYceKaTkHLdeFTYKR1vCJB8uyzj0SCEl57j1qrBRONoSJvlwWcahR0ozosLRBA/fIOziKmU9hx4pzYgKRxM8fIOwi6uU9Rx6pDQjKhxN8PANwq5slSwlFGdPo3AUE1ZkG2QpoTh7GoWjmLAi2yBLCcXZ0ygcxYQVvsiSSc/KQ9xacOuYMOnvL5n0rDzErQW3jgmT/v6SSc/KQ9xacOuYPPkfco614BxrwTnWgnOsBY871t8/K/2exrM2qjd64L0etk7hHGvBOdaUB17q9cxjPfNSrwce67GXej3tWE++1OuZx6q/R/GwdZ7NOdaCc6wF51gLzrEW/AGhCGKrrtMfJgAAAABJRU5ErkJggg==\n"
    			+ "";
        byte[] bs = new byte[1024];
        bs = Base64.getMimeDecoder().decode(base64);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        file = new File("D:\\"+UUID.randomUUID()+".jpg");
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    void testExcelUtil(String base64) {
    	FileOutputStream fileOut = null;

    	/*

    	* 目的：操作图片 实现：第一步-需要将图片从磁盘加载到内存中，第二步-java中有Image和BufferedImage这两种处理图片的类，

    	* 第一种类似copy，不能对 图片进行操作，而BufferedImage则是将图片放入内存图片缓冲区中，可以对图片进行修改。

    	*/

    	BufferedImage bufferImg = null;
    	
    	
    		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray

    	try {
			//bufferImg = ImageIO.read(new File("C:/Users/Administrator/Desktop/testQrcode/temp.jpg"));
	    	XSSFWorkbook xsb = new XSSFWorkbook();
	    	XSSFSheet sheet1 = xsb.createSheet("测试sheet");
	    	
	    	XSSFDrawing createDrawingPatriarch = sheet1.createDrawingPatriarch();
	    	
	    	XSSFClientAnchor xssfClientAnchor = new XSSFClientAnchor(0,0,255,255,1,1,3,9);
//	    	
//	    	String base64 = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAIAAAD/gAIDAAAEVElEQVR4Xu2VQYojUQxD+/6Xzgw0CKNX49iqQArmP7KSJX2XN/l5Hcb8uHD4N+dYC86xFpxjLTjHWnCOteAca8E51oJzrAXnWAvOsRacYy04x1pwjrXgHGvBOdaCc6wF4bF+7sGe0r0YZXjdmDDp7y9hT+lejDK8bkyY9PeXsKd0L0YZXjcmTGYPNymNGmguBe/JUpUwmT3cpDRqoLkUvCdLVcJk9nCT0qiB5lLwnixVCZN8WAqhR0oGe6QQeqRsCZN8WAqhR0oGe6QQeqRsCZN8WAqhR0oGe6QQeqRsCZN8WAqhh8oEpqgQeqRsCZN8WAqhh8oEpqgQeqRsCZN8WAqhh8oEpqgQeqRsCZN8WAqhhwpH9EwUQo+ULWGSD0sh9FDhiJ6JQuiRsiVM8mEphB4qHNEzUQg9UraEyexhpqQ0lAJP+aAlS1XCZPYwU1IaSoGnfNCSpSphMnuYKSkNpcBTPmjJUpUwqYcz2JMpGerZEib9/SXsyZQM9WwJk/7+EvZkSoZ6tuTJj8APKB/llNx3+PIGPEQ5jlNy3+HLG/AQ5ThOyX2HcIPJB9AjhZScm6lMKE0e98GYMDl5mB4ppOTcTGVCafK4D8aEycnD9EghJedmKhNKk8d9MCZP/lLW81WokMaj0cQzUTjakid/8UWutix2p/FoNPFMFI625MlffJGrLYvdaTwaTTwThaMtYbJ5uGzlHh9cxUXjKQXv8fANwq5mlbKne3xwFReNpxS8x8M3CLuaVcqe7vHBVVw0nlLwHg/fIOxqVmlGJDMzNVHuE3Y1qzQjkpmZmij3CbuaVZoRycxMTZT73O1qduKICqFHCik5x62tecjdimYVjqgQeqSQknPc2pqH3K1oVuGICqFHCik5x62tecgHKu7AL2kUIg9xa8GtY/LkR+AHNAqRh7i14NYxefIj8AMahchD3Fpw65gw6e8vaXrKIwtPQ5a6JKwomyc0PeWRhachS10SVpTNE5qe8sjC05ClLgkrsg2aFEdSOKKnUT5IWJrt1KQ4ksIRPY3yQcLSbKcmxZEUjuhplA8SlnInKYQeKg0yr1INXjcmTPLhsoxDD5UGmVepBq8bEyb5cFnGoYdKg8yrVIPXjQmTfLgs49AjhZSc49arwkbhaEuY5MNlGYceKaTkHLdeFTYKR1vCJB8uyzj0SCEl57j1qrBRONoSJvlwWcahR0ozosLRBA/fIOziKmU9hx4pzYgKRxM8fIOwi6uU9Rx6pDQjKhxN8PANwq5slSwlFGdPo3AUE1ZkG2QpoTh7GoWjmLAi2yBLCcXZ0ygcxYQVvsiSSc/KQ9xacOuYMOnvL5n0rDzErQW3jgmT/v6SSc/KQ9xacOuYPPkfco614BxrwTnWgnOsBY871t8/K/2exrM2qjd64L0etk7hHGvBOdaUB17q9cxjPfNSrwce67GXej3tWE++1OuZx6q/R/GwdZ7NOdaCc6wF51gLzrEW/AGhCGKrrtMfJgAAAABJRU5ErkJggg==\n"
//	    			+ "";
	        byte[] bs = new byte[1024];
	        bs = Base64.getMimeDecoder().decode(base64);
	    	
	    	createDrawingPatriarch.createPicture(xssfClientAnchor, xsb.addPicture(bs, XSSFWorkbook.PICTURE_TYPE_PNG));

	
	    	File file = new File("C:/Users/Administrator/Desktop/testQrcode/1234.xlsx");
	
	    	file.createNewFile();
	
	    	fileOut = new FileOutputStream(file);

	    	// 写入excel文件
	
	    	// wb.write(fileOut);
	    	xsb.write(fileOut);
	
	    	System.out.println("----Excel文件已生成------");
	    	
//	    	HSSFWorkbook wb = new HSSFWorkbook();
//	
//	    	HSSFSheet sheet1 = wb.createSheet("test picture");
//	
//	    	// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
//	
//	    	HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
//	    	
//	    	//XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, 1, 1, 1, 1);
//	    	
//	    	// anchor主要用于设置图片的属性
//	
//	    	HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short) 1,1 , (short) 3,9);

	    	// 插入图片

	    //	patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

    	} catch (Exception e) {
    		e.printStackTrace();

    	} finally {
	    	if (fileOut != null) {
		    	try {
		    	fileOut.close();
		
		    	} catch (IOException e) {
		    		e.printStackTrace();

		    	}

	    	}

    	}
    }
    
    @Test
    void testIset() throws IOException {
    	
    	
    	
////    	 InputStream i = new FileInputStream("E:\\x.xlsx");
//         //导入我的图片
//         BufferedImage image = ImageIO.read(new File("C:/Users/Administrator/Desktop/testQrcode/temp.jpg"));
//         XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
//         XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
//         XSSFDrawing xssfDrawing = sheet.createDrawingPatriarch();
//         //字节流
//         ByteArrayOutputStream bao = new ByteArrayOutputStream();
//         ImageIO.write(image, "jpg", bao);
//                 //设置起始坐标,前四个是xy函数, 后四个是设置起始行列和图形行列 我选择后者
//         XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, 1,1,1,1);
//      //   anchor.setAnchorType(0);
//         //创建图片
//         xssfDrawing.createPicture(anchor, xssfWorkbook.addPicture(
//                 bao.toByteArray(), xssfWorkbook.PICTURE_TYPE_JPEG));
//                 //关闭流
////         i.close();
//         FileOutputStream fos = new FileOutputStream(new File(
//                 "C:/Users/Administrator/Desktop/testQrcode/1234.xlsx"));
//         xssfWorkbook.write(fos);
//         fos.close();
//        // new File("E:\\" + 123 + ".jpg").delete();
    	
    }
    
    void testBase64Util() {
    	
    	
    	
    	
    	
    }

}
