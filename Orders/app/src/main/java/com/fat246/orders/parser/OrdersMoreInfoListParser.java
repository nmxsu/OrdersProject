package com.fat246.orders.parser;

import android.util.Xml;

import com.fat246.orders.bean.OrderInfo;
import com.fat246.orders.bean.OrderMoreInfoListItem;

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
 * Created by Administrator on 2016/3/28.
 */
public class OrdersMoreInfoListParser {

    //订单详细信息地址
    private String URL_Str;

    //订单信息
    private OrderInfo mOrderInfo;

    public OrdersMoreInfoListParser(String URL_Str, OrderInfo mOrderInfo) {

        this.URL_Str = URL_Str;
        this.mOrderInfo = mOrderInfo;
    }

    //得到订单详细信息List
    public List<OrderMoreInfoListItem> getOrdersMoreInfoList() {

        List<OrderMoreInfoListItem> mOrdersListInfo = sendGetOrdersMoreInfoList("OrderId=" + mOrderInfo.getPRHSORD_ID());

        return mOrdersListInfo;
    }

    //发送post 请求
    private List<OrderMoreInfoListItem> sendGetOrdersMoreInfoList(String param) {

        PrintWriter out = null;
        List<OrderMoreInfoListItem> mOrdersMoreInfoList;

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

            mOrdersMoreInfoList = parse(is);

        } catch (Exception e) {

            e.printStackTrace();
            mOrdersMoreInfoList = new ArrayList<>();
        }


        return mOrdersMoreInfoList;
    }

    //解析xml数据
    private List<OrderMoreInfoListItem> parse(InputStream is) throws XmlPullParserException, IOException {

        List<OrderMoreInfoListItem> mOrdersMoreInfoList = new ArrayList<>();

        try {

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "utf-8");

            //首先跳出 ArrayOfString

            int eventType = parser.getEventType();
            int i = 0;

            //引用
            String MATE_Code=null;
            String MATE_Name=null;
            String MUNITU_NAME=null;
            String MATE_Size=null;
            String MATE_Model=null;
            String PRHSOD_AMNT=null;
            String PRHSOD_ACCEIN=null;
            String PRHSOD_BILLIN=null;
            String PRHSOD_AMNT_RTN=null;
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
                                    MUNITU_NAME = str;

                                    if (MUNITU_NAME == null) MUNITU_NAME = "";
                                    break;
                                case 3:
                                    MATE_Size = str;

                                    if (MATE_Size == null) MATE_Size = "";
                                    break;
                                case 4:
                                    MATE_Model = str;

                                    if (MATE_Model == null) MATE_Model = "";
                                    break;
                                case 5:
                                    PRHSOD_AMNT = str;

                                    if (PRHSOD_AMNT == null) PRHSOD_AMNT = "";
                                    break;
                                case 6:
                                    PRHSOD_ACCEIN = str;

                                    if (PRHSOD_ACCEIN == null) PRHSOD_ACCEIN = "";
                                    break;
                                case 7:
                                    PRHSOD_BILLIN = str;

                                    if (PRHSOD_BILLIN == null) PRHSOD_BILLIN = "";
                                    break;
                                case 8:
                                    PRHSOD_AMNT_RTN = str;

                                    if (PRHSOD_AMNT_RTN == null) PRHSOD_AMNT_RTN = "";
                                    break;
                                case 9:
                                    MATE_PRICEP = str;

                                    if (MATE_PRICEP == null) MATE_PRICEP = "";

                                    mOrdersMoreInfoList.add(new OrderMoreInfoListItem(MATE_Code
                                    , MATE_Name
                                    , MUNITU_NAME
                                    , MATE_Size
                                    , MATE_Model
                                    , PRHSOD_AMNT
                                    , PRHSOD_ACCEIN
                                    , PRHSOD_BILLIN
                                    , PRHSOD_AMNT_RTN
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

        return mOrdersMoreInfoList;
    }
}
