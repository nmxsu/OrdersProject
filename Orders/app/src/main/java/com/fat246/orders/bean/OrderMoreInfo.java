package com.fat246.orders.bean;

/**
 * Created by sun on 2016/2/19.
 */
public class OrderMoreInfo extends OrderInfo {

    //订单详细信息
    private String CREATE_NAME;
    private String MODIFY_NAME;
    private String dry_auth_name;

    public OrderMoreInfo(String PRHSORD_ID, String NAMEE, String PRAC_NAME,
                         String CREATE_NAME, String MODIFY_NAME, String dry_auth_name) {
        super(PRHSORD_ID, NAMEE, PRAC_NAME, true);

        this.CREATE_NAME = CREATE_NAME;
        this.MODIFY_NAME = MODIFY_NAME;
        this.dry_auth_name = dry_auth_name;
    }

    //只读
    public String getCREATE_NAME() {
        return this.CREATE_NAME;
    }

    public String getMODIFY_NAME() {
        return this.MODIFY_NAME;
    }

    public String getDry_auth_name() {
        return this.dry_auth_name;
    }
}
