package com.fat246.orders.bean;

/**
 * Created by sun on 2016/2/19.
 */
public class ApplyMoreInfo extends ApplyInfo {

    //更多信息
    private String CREATE_NAME;
    private String MODIFY_NAME;
    private String dry_auth_name;

    public ApplyMoreInfo(String PRHS_ID, String DEP_NAME, String PSD_NAME, String PSR_NAME,
                         String CREATE_NAME,String MODIFY_NAME,String dry_auth_name) {
        super(PRHS_ID, DEP_NAME, PSD_NAME, PSR_NAME);

        this.CREATE_NAME=CREATE_NAME;
        this.MODIFY_NAME=MODIFY_NAME;
        this.dry_auth_name=dry_auth_name;
    }

    //只读
    public String getCREATE_NAME(){return this.CREATE_NAME;}
    public String getMODIFY_NAME(){return this.MODIFY_NAME;}
    public String getDry_auth_name(){return this.dry_auth_name;}
}
