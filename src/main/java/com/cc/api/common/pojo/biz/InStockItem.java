package com.cc.api.common.pojo.biz;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "biz_in_stock_item")
public class InStockItem {


    @Id
    private Long id;

    private String pNum;

    private String name;

    private String model;

    private String unit;

    private String imageUrl;

    private Integer count;

    private  Integer status;


}
