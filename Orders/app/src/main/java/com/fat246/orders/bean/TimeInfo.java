package com.fat246.orders.bean;

import java.sql.Time;

/**
 * Created by hx on 2016/7/23.
 */
public class TimeInfo {

    //时间参数
    public static final String Time="Time";

    private String Date;



    //时间状态
    private String Create;
    private String Service;
    private String Submit;
    private String Approve;
    private String Exit;

    public TimeInfo(String Create,String Service,String Submit,
                    String Approve,String Exit){
        this.Create=Create;
        this.Service=Service;
        this.Submit=Submit;
        this.Approve=Approve;
        this.Exit=Exit;
    }



    public TimeInfo(String Date){
        this.Date=Date;
        this.Create="";
        this.Service="";
        this.Submit="";
        this.Approve="";
        this.Exit="";
    }

    public String getDate() {
        return this.Date;
    }

    public static String getTime() {
        return Time;
    }
}
