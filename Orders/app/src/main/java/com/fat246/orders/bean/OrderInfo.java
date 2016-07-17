package com.fat246.orders.bean;

public class OrderInfo {

    //订单ID
    private String PRHSORD_ID;

    //NAMEE
    private String NAMEE;

    //状态
    private String PRAC_NAME;

    //未开放信息
    private String ID;
    private String MATE_CODE;
    private String PRHS_SOUR;
    private String DEP_CODE;
    private boolean IS_PASSED;

    public OrderInfo(String PRHSORD_ID, String NAMEE, String PRAC_NAME, boolean IS_PASSED) {

        this.IS_PASSED = IS_PASSED;
        this.PRHSORD_ID = PRHSORD_ID;
        this.NAMEE = NAMEE;
        this.PRAC_NAME = PRAC_NAME;
    }

    //只包含ID的OrderInfo
    public OrderInfo(String PRHSORD_ID) {

        this.PRHSORD_ID = PRHSORD_ID;
        this.MATE_CODE = "";
        this.PRHS_SOUR = "";
        this.DEP_CODE = "";
        this.IS_PASSED = false;
    }

    //只读
    public String getPRHSORD_ID() {
        return this.PRHSORD_ID;
    }

    public String getNAMEE() {
        return this.NAMEE;
    }

    public String getPRAC_NAME() {
        return this.PRAC_NAME;
    }

    public boolean getIS_PASSED() {

        return this.IS_PASSED;
    }
}
