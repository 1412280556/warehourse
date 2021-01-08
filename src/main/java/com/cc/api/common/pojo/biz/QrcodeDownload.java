package com.cc.api.common.pojo.biz;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

import lombok.Data;

@Excel(value = "二维码下载")
@Data
public class QrcodeDownload {
	
	@ExcelField(value = "编号", width = 50)
    private Long id;
	
	@ExcelField(value = "图片", width = 50)
    private String imageUrl;

}
