package com.bjl.javafxboot.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.*;


public class HttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    //https://71ab.vip/web/rest/code?_=1615609908616    验证码
    //https://71ab.vip/web/rest/cashlogin   登录
    //https://71ab.vip/member/account?_=1615609908616   账户信息
    //https://71ab.vip/member/period?lottery=PK10JSC&_=1615708191238    开奖周期
    //{"closeTime":1616989215000,"currentTime":1616989178000,"drawDate":1616947200000,"drawNumber":"31855745","drawTime":1616989230000,"openTime":1616989155000,"pnumber":"31855744","status":1}
    //https://71ab.vip/member/lastResult?lottery=PK10JSC&_=1615609908616    最近开奖结果
        //{"lottery":"PK10JSC","drawNumber":"31841124","result":"6,3,1,4,9,5,10,2,7,8","detail":"LH1=H,2,冠军-虎;LH3=H,2,第三名-虎;LH4=H,2,第四名-虎;GDX=X,2,冠亚小;GDS=D,3,冠亚单;DX3=X,2,第三名-小;DX7=D,6,第七名-大;DX8=X,3,第八名-小;DX10=D,2,第十名-大;DS1=S,3,冠军-双;DS2=D,4,亚军-单;DS3=D,3,第三名-单;DS4=S,3,第四名-双;DS5=D,2,第五名-单;DS8=S,2,第八名-双;DS9=D,2,第九名-单;DS10=S,2,第十名-双;B3=1,2,第三名-1;","drawTime":1615892655000,"resultOther":"9,小,单,虎,虎,虎,虎,龙,"}
    //https://71ab.vip/member/dayResult?lottery=SGFT&_=1615892567214    当天所有开奖结果
    //https://71ab.vip/member/dresult?lottery=PK10JSC&date=2021-03-04&table=1   历史开奖
    //https://71ab.vip/member/bet   下注
        //{"lottery":"PK10JSC","drawNumber":"31837965","bets":[{"game":"DS3","contents":"D","amount":2,"odds":1.98}],"ignore":false}

    public static JSONObject doget_login(CookieStore cookieStore,Map<String,String> user,String lotteryType) throws Exception {
        String codeurl = "https://71ab.vip/web/rest/code?_="+new Date().getTime();
        HttpGet httpGet = new HttpGet(codeurl);
        BasicClientCookie cookie = new BasicClientCookie( "affid" , null );
        cookie.setDomain( ".71ab.vip" );
        cookieStore.addCookie(cookie);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        //添加请求头
        CommonUtil.addHeader(httpGet);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (200 == response.getStatusLine().getStatusCode()) {
            // 获得Cookies
            List<Cookie> cookies = cookieStore.getCookies();
            for (Cookie c : cookies) {
                System.out.println("验证码回带cookie为："+c.getName()+"="+c.getValue()+";");
            }
            return doLogin(CommonUtil.toByteArray(response.getEntity().getContent()),cookieStore,user,lotteryType);
        } else {
            System.out.println(EntityUtils.toString(response.getEntity(), Charset.forName("utf-8")));
        }
        return null;
    }

    private static JSONObject doLogin(byte[] file, CookieStore cookieStore, Map<String,String> user,String lotteryType) throws Exception {
        OcrUtil ocrUtil = new OcrUtil();
        //调用百度API识别验证码
        String words = ocrUtil.getVerifyCode(file);
        String loginurl = "https://71ab.vip/web/rest/cashlogin";
        user.put("code",words);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpPost httpPost = new HttpPost(loginurl);
        //添加请求头
        CommonUtil.addHeader(httpPost);
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        Set<String> keySet = user.keySet();
        for (String key : keySet) {
            arrayList.add(new BasicNameValuePair(key, user.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(arrayList));
        HttpResponse response = httpClient.execute(httpPost);
        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                JSONObject json = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                if (json.getBoolean("success")){
                    String tokenId = json.getString("message").split("=")[1];
                    BasicClientCookie cookie1 = new BasicClientCookie( "_skin_" , "blue" );
                    cookie1.setDomain( ".71ab.vip" );
                    cookieStore.addCookie(cookie1);
                    BasicClientCookie cookie2 = new BasicClientCookie( "defaultLT" , lotteryType );
                    cookie2.setDomain( ".71ab.vip" );
                    cookieStore.addCookie(cookie2);
                    BasicClientCookie cookie3 = new BasicClientCookie( "a7e44ec6c233" , tokenId );
                    cookie3.setDomain( ".71ab.vip" );
                    cookieStore.addCookie(cookie3);
                    BasicClientCookie cookie4 = new BasicClientCookie( "token" , tokenId );
                    cookie4.setDomain( ".71ab.vip" );
                    cookieStore.addCookie(cookie4);
                }
                // 获得Cookies
                List<Cookie> cookies = cookieStore.getCookies();
                for (Cookie c : cookies) {
                    System.out.println("登录回带cookie为："+c.getName()+"="+c.getValue()+";");
                }
                //用httpcore.jar提供的工具类将"实体"转化为字符串打印到控制台
                return json;
            }
        }
        return null;
    }

    public static JSONObject doGet(CookieStore cookieStore, String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        //添加请求头
        CommonUtil.addHeader(httpGet);
        return JSONObject.parseObject(execute(httpGet,cookieStore));
    }

    public static String execute(HttpRequestBase request, CookieStore cookieStore) throws Exception {
        // 设置httpclient请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                // 默认连接超时300ms
                .setConnectionRequestTimeout(500)
                // 请求超时600ms
                .setSocketTimeout(800)
                .build();
        request.setConfig(requestConfig);
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = httpClient.execute(request);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        } else {
            System.out.println(EntityUtils.toString(response.getEntity(), Charset.forName("utf-8")));
        }
        return "";
    }

    public static JSONArray doGet_dayResult(CookieStore cookieStore,String lotteryType) throws Exception {
        String url = "https://71ab.vip/member/dayResult?lottery="+lotteryType+"&_="+new Date().getTime();
        HttpGet httpGet = new HttpGet(url);
        //添加请求头
        CommonUtil.addHeader(httpGet);
        return JSONArray.parseArray(execute(httpGet,cookieStore));
    }

    public static String doPost_bet(CookieStore cookieStore,JSONObject xiaJson) throws Exception {
        String url = "https://71ab.vip/member/bet";
        //{"lottery":"PK10JSC","drawNumber":"31837965","bets":[{"game":"DS3","contents":"D","amount":2,"odds":1.98}],"ignore":false}
        //StringEntity stringEntity = new StringEntity((strPayloadDataJson), "application/json", "utf-8");
        HttpPost httpPost = new HttpPost(url);
        //添加请求头
        CommonUtil.addHeaderP(httpPost);
        //String strPayloadDataJson = "{\"lottery\":\"PK10JSC\",\"drawNumber\":\"31837995\",\"bets\":[{\"game\":\"DS3\",\"contents\":\"D\",\"amount\":2,\"odds\":1.98}],\"ignore\":false}";
        StringEntity stringEntity = new StringEntity(xiaJson.toJSONString(), "application/json", "utf-8");
        httpPost.setEntity(stringEntity);
        return execute(httpPost,cookieStore);
    }

    public static JSONObject getAccountInfo(CookieStore cookieStore) throws Exception {
        String url = "https://71ab.vip/member/account?_="+new Date().getTime();
        return doGet(cookieStore,url);
    }

    public static JSONObject getLastResult(CookieStore cookieStore,String lotteryType) throws Exception {
        String url = "https://71ab.vip/member/lastResult?lottery="+lotteryType+"&_="+new Date().getTime();
        return doGet(cookieStore,url);
    }

    public static JSONObject getPeriod(CookieStore cookieStore,String lotteryType) throws Exception {
        String url = "https://71ab.vip/member/period?lottery="+lotteryType+"&_="+new Date().getTime();
        return doGet(cookieStore,url);
    }

    public static String getDrawDataByDate(CookieStore cookieStore,String date,String lotteryType) throws Exception {
        String url = "https://71ab.vip/member/dresult?lottery="+lotteryType+"&date="+date+"&table=1";
        HttpGet httpGet = new HttpGet(url);
        //添加请求头
        CommonUtil.addHeader(httpGet);
        return execute(httpGet,cookieStore);
    }

    public static void main(String[] args) {
        try {
            System.out.println(2*0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
