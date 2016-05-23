package com.fat246.orders.parser;

import android.util.Xml;

import com.fat246.orders.bean.ApplyInfo;
import com.fat246.orders.bean.ApplyMoreInfoListItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class ApplysMoreInfoListParser {

    //申请单详细信息地址
    private String URL_Str;

    //申请单信息
    private ApplyInfo mApplyInfo;

    public ApplysMoreInfoListParser(String URL_Str,ApplyInfo mApplyInfo){

        this.URL_Str=URL_Str;
        this.mApplyInfo=mApplyInfo;
    }

    public List<ApplyMoreInfoListItem> getApplysMoreInfoList(){

        List<ApplyMoreInfoListItem> applyMoreInfoList=sendGetApplysMoreInfoList("ApplyId="+mApplyInfo.getPRHS_ID());

        return applyMoreInfoList;
    }

    private List<ApplyMoreInfoListItem> sendGetApplysMoreInfoList(String param){

        PrintWriter out = null;
        List<ApplyMoreInfoListItem> mApplysMoreInfoList;

        try {

            URL url = new URL(URL_Str);

            //打开和URL之间的链接
            URLConnection conn = url.openConnection();

            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 非常重要的两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());

            //发送请求参数
            out.print(param);

            //flush
            out.flush();

            //定义InputStream 输入流来读取URL的响应
            InputStream is = conn.getInputStream();

            mApplysMoreInfoList = parse(is);

        } catch (Exception e) {

            e.printStackTrace();
            mApplysMoreInfoList = new ArrayList<>();
        }

        return mApplysMoreInfoList;
    }

    private List<ApplyMoreInfoListItem> parse(InputStream is)throws XmlPullParserException, IOException {

        List<ApplyMoreInfoListItem> mApplysMoreInfoList = new ArrayList<>();

        try {

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "utf-8");

            //首先跳出 ArrayOfString

            int eventType = parser.getEventType();
            int i = 0;

            //引用
            String MATE_Code=null;
            String MATE_Name=null;
            String MATE_Size=null;
            String PRHSD_AMNT=null;
            String MATE_PRICEP=null;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {

                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:

                        if (parser.getName().equals("string")) {

                            eventType = parser.next();
                            String str = parser.getText();
                            switch (i % 10) {

                                case 0:
                                    MATE_Code = str;

                                    if (MATE_Code == null) MATE_Code = "";
                                    break;
                                case 1:
                                    MATE_Name = str;

                                    if (MATE_Name == null) MATE_Name = "";
                                    break;
                                case 2:
                                    MATE_Size = str;

                                    if (MATE_Size == null) MATE_Size = "";
                                    break;
                                case 3:
                                    PRHSD_AMNT = str;

                                    if (PRHSD_AMNT == null) PRHSD_AMNT = "";
                                    break;
                                case 4:
                                    MATE_PRICEP = str;

                                    if (MATE_PRICEP == null) MATE_PRICEP = "";

                                    //将这个OrdersMoreInfoListItem 添加到List
                                    mApplysMoreInfoList.add(new ApplyMoreInfoListItem(MATE_Code
                                            , MATE_Name
                                            , MATE_Size
                                            , PRHSD_AMNT
                                            , MATE_PRICEP));
                                    break;

                            }

                            //别忘了  ++
                            i++;
                        }
                        break;
                }
                eventType = parser.next();
            }

        } finally {

            is.close();
        }

        return mApplysMoreInfoList;
    }
}
