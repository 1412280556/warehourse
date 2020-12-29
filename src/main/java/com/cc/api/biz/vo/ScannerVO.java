package com.cc.api.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ScannerVO {

    private Long id;

    private Long pid;

    private String number;

    private String details;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date instocktime;

    private Integer instockstatus;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outstocktime;

    private Integer outstockstatus;

    private Long userId;

    private String imageUrl;

}
