package com.fat246.orders.bean;

/**
 * Created by ken on 16-7-21.
 */
public class OrderStandInfo {

    //不知道怎么给这些字段命名了，老师只给了中文名，我就根据意思自己翻译了。

    //单号
    private String ORDER_ID;

    //供应商
    private String ORDER_PROVIDER;

    //完成情况
    private String COMPLETE_STATUS;

    //所有者
    private String ORDER_OWNER;

    //维护人
    private String ORDER_MAINTAIN;

    //审批人
    private String ORDER_JUDGE;

    public OrderStandInfo(String ORDER_ID, String ORDER_PROVIDER, String COMPLETE_STATUS,
                          String ORDER_OWNER, String ORDER_MAINTAIN, String ORDER_JUDGE) {

        this.ORDER_ID = ORDER_ID;
        this.ORDER_PROVIDER = ORDER_PROVIDER;
        this.COMPLETE_STATUS = COMPLETE_STATUS;
        this.ORDER_OWNER = ORDER_OWNER;
        this.ORDER_MAINTAIN = ORDER_MAINTAIN;
        this.ORDER_JUDGE = ORDER_JUDGE;
    }

    //get
    public String getORDER_ID() {
        return this.ORDER_ID;
    }

    public String getORDER_PROVIDER() {
        return this.ORDER_PROVIDER;
    }

    public String getCOMPLETE_STATUS() {
        return this.COMPLETE_STATUS;
    }

    public String getORDER_OWNER() {
        return this.ORDER_OWNER;
    }

    public String getORDER_MAINTAIN() {
        return this.ORDER_MAINTAIN;
    }

    public String getORDER_JUDGE() {
        return this.ORDER_JUDGE;
    }
}
