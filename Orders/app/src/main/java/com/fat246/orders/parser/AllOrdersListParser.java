package com.fat246.orders.parser;

import android.util.Log;
import android.util.Xml;

import com.fat246.orders.bean.OrderInfo;
import com.fat246.orders.bean.UserInfo;

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
 * Created by Administrator on 2016/3/7.
 */
public class AllOrdersListParser {

    //URL str
    private String URL_Str;

    //UserInfo
    private UserInfo mUserInfo;

    public AllOrdersListParser(UserInfo mUserInfo, String URL_Str) {

        this.mUserInfo = mUserInfo;

        this.URL_Str = URL_Str;
    }

    public List<OrderInfo> getAllOrdersList() {

        //保存中网页服务上面加载下来的  xml数据
        List<OrderInfo> mOrdersList = sendGetAllOrdersListPost("autId=" + mUserInfo.getmUser());

        return mOrdersList;
    }

    //发送  post 请求
    private List<OrderInfo> sendGetAllOrdersListPost(String param) {

        Log.e("List",param);

        PrintWriter out = null;
        List<OrderInfo> mOrdersList;

        try {

            URL url = new URL(URL_Str);

            Log.e("url====>>>>",URL_Str);

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

            mOrdersList = parse(is);

        } catch (Exception e) {

            e.printStackTrace();
            Log.e("misstake",""+e.getMessage());
            mOrdersList = new ArrayList<>();
        }

        //添加一点 车市数据
        mOrdersList.add(new OrderInfo("id","dd","na"));

        return mOrdersList;
    }

    //解析  xml数据
    private List<OrderInfo> parse(InputStream is) throws XmlPullParserException, IOException {

        List<OrderInfo> mOrdersList = new ArrayList<>();

        try {

            XmlPullParser parser = Xml.newPullParser();

            parser.setInput(is, "utf-8");

            //首先跳出 ArrayOfString

            int eventType = parser.getEventType();
            int i = 0;

            //引用
            String PRHSORD_ID = null, NAMEE = null, PRAC_NAME = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {

                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:

                        if (parser.getName().equals("string")) {

                            eventType = parser.next();
                            String str = parser.getText();
                            switch (i % 3) {

                                case 0:
                                    PRHSORD_ID = str;

                                    if (PRHSORD_ID==null) PRHSORD_ID="";
                                    break;
                                case 1:
                                    NAMEE = str;

                                    if (NAMEE==null) NAMEE="";
                                    break;
                                case 2:
                                    PRAC_NAME = str;

                                    if (PRAC_NAME==null) PRAC_NAME="";
                                    //添加到  mOrdersList
                                    mOrdersList.add(new OrderInfo(PRHSORD_ID, NAMEE, PRAC_NAME));
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
        return mOrdersList;
    }
}
