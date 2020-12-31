package com.cc.api.common.pojo.biz;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "biz_scanner")
public class Scanner {

    @Id
    private Long id;

    private Long pid;

    private String number;

    private String details;

    private Date instocktime;

    private Integer instockstatus;

    private Date outstocktime;

    private Integer outstockstatus;

    private String userName;

    private String imageUrl;
}
