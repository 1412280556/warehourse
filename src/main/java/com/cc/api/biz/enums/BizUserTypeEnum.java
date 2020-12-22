package com.cc.api.biz.enums;


public enum  BizUserTypeEnum {

    DEAN("部门主任");
    private String val;

    BizUserTypeEnum(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
