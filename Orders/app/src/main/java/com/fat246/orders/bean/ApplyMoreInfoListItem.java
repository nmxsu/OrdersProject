package com.fat246.orders.bean;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ApplyMoreInfoListItem {

    //申请单基本信息
    private String MATE_Code;
    private String MATE_Name;
    private String MATE_Size;
    private String PRHSD_AMNT;
    private String MATE_PRICEP;

    public ApplyMoreInfoListItem(String MATE_Code,String MATE_Name,String MATE_Size,
                                 String PRHSD_AMNT,String MATE_PRICEP){

        this.MATE_Code=MATE_Code;
        this.MATE_Name=MATE_Name;
        this.MATE_Size=MATE_Size;
        this.PRHSD_AMNT=PRHSD_AMNT;
        this.MATE_PRICEP=MATE_PRICEP;
    }

    public String getMATE_Code(){return this.MATE_Code;}
    public String getMATE_Name(){return this.MATE_Name;}
    public String getMATE_Size(){return this.MATE_Size;}
    public String getPRHSD_AMNT(){return this.PRHSD_AMNT;}
    public String getMATE_PRICEP(){return this.MATE_PRICEP;}
}
