package com.cc.api.common.pojo.biz;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Excel(value = "出库表单")
@Data
@Table(name = "biz_in_stock")
public class InStock {

    @Id
    @ExcelField(value = "编号", width = 50)
    private Long id;

    @ExcelField(value = "入库单编号", width = 250)
    private String inNum;

    @ExcelField(value = "类型", width = 100)
    private Integer type;

    @ExcelField(value = "操作人员", width = 100)
    private String operator;

    @ExcelField(value = "来源", width = 100)
    private Long supplierId;

    @ExcelField(value = "创建时间", width = 220)
    private Date createTime;

    @ExcelField(value = "修改时间", width = 220)
    private Date modified;

    @ExcelField(value = "物资总数", width = 100)
    private Integer productNumber;

    @ExcelField(value = "描述信息", width = 100)
    private String remark;

    @ExcelField(value = "状态", width = 100)
    private Integer status;
}
