package com.cc.api.common.pojo.biz;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

import java.util.Date;

@Excel(value = "扫描二维码")
@Data
@Table(name = "biz_scanner")
public class Scanner {

    @Id
    @ExcelField(value = "编号", width = 50)
    private Long id;

    @ExcelField(value = "订单编号", width = 50)
    private Long pid;

    @ExcelField(value = "数量", width = 50)
    private String number;

    @ExcelField(value = "描述", width = 50)
    private String details;

    @ExcelField(value = "入库时间", width = 50)
    private Date instocktime;

    @ExcelField(value = "入库状态", width = 50)
    private Integer instockstatus;

    @ExcelField(value = "出库时间", width = 50)
    private Date outstocktime;

    @ExcelField(value = "出库状态", width = 50)
    private Integer outstockstatus;

    @ExcelField(value = "操作员", width = 50)
    private String userName;

    @ExcelField(value = "图片", width = 50)
    private String imageUrl;
    
    @ExcelField(value = "扫描状态", width = 50)
    private Integer scanStatus;
}
