package com.cc.api.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.factory.ExcelMappingFactory;
import com.wuwenze.poi.pojo.ExcelMapping;
import com.wuwenze.poi.util.Const;

public class ExcelBase64Util extends ExcelKit{
	
	
	  private Class<?> mClass = null;
	  private HttpServletResponse mResponse = null;
	
	
	
	
	public void downXlsx(List<?> data, boolean isTemplate,String base64) throws Exception {
		
		/**
		  ExcelMapping excelMapping = ExcelMappingFactory.get(mClass);
	      ExcelXlsxWriter excelXlsxWriter = new ExcelXlsxWriter(excelMapping,
	          mMaxSheetRecords);
	      SXSSFWorkbook workbook = excelXlsxWriter.generateXlsxWorkbook(data, isTemplate);
	      String fileName = isTemplate ? (excelMapping.getName() + "-导入模板.xlsx")
	          : (excelMapping.getName() + "-导出结果.xlsx");
	      POIUtil.download(workbook, mResponse, URLEncoder.encode(fileName, Const.ENCODING));
		 */
		
		
		ExcelMapping excelMapping = ExcelMappingFactory.get(mClass);
		
		XSSFWorkbook workbook = ExcelBase64Util.generateXlsxWorkbook();
		
		
		String fileName = isTemplate ? (excelMapping.getName() + "-导入模板.xlsx") : (excelMapping.getName() + "-导出结果.xlsx");
		
		ExcelBase64Util.download(workbook, mResponse, URLEncoder.encode(fileName, Const.ENCODING), base64);
	}
	
	
	 public static void download(
			 XSSFWorkbook workbook, HttpServletResponse response, String filename,String base64) throws IOException {
		   
//	      OutputStream out = response.getOutputStream();
//	      response.setContentType(Const.XLSX_CONTENT_TYPE);
//	      response.setHeader(Const.XLSX_HEADER_KEY,
//	          String.format(Const.XLSX_HEADER_VALUE_TEMPLATE, filename));
//	      ExcelBase64Util.write(wb, out);
		 	OutputStream out = response.getOutputStream();
		 
		 	XSSFSheet sheet1 = workbook.createSheet("测试sheet");
		
		 	XSSFDrawing createDrawingPatriarch = sheet1.createDrawingPatriarch();
		
		 	XSSFClientAnchor xssfClientAnchor = new XSSFClientAnchor(0,0,255,255,1,1,3,9);

		 	byte[] bs = new byte[1024];
			bs = Base64.getMimeDecoder().decode(base64);
		
			createDrawingPatriarch.createPicture(xssfClientAnchor, workbook.addPicture(bs,XSSFWorkbook .PICTURE_TYPE_PNG));
	 
			workbook.write(out);
	 
	 
	 }
	 
	 public static XSSFWorkbook generateXlsxWorkbook() {
		    XSSFWorkbook workbook = new XSSFWorkbook();
		    return workbook;
		  }
	
	 
	 public static void write(XSSFWorkbook wb, OutputStream out) {
		    try {
		      if (null != out) {
		        wb.write(out);
		        out.flush();
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    } finally {
		      if (null != out) {
		        try {
		          out.close();
		        } catch (IOException e) {
		          e.printStackTrace();
		        }
		      }
		    }
		  }
	
	

}
