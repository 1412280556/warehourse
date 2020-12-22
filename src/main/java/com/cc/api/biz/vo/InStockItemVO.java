package com.cc.api.biz.vo;

import lombok.Data;

import javax.persistence.Id;


@Data
public class InStockItemVO {

    @Id
    private Long id;

    private String pNum;

    private String name;

    private String model;

    private String unit;

    private String imageUrl;

    private int count;
}
