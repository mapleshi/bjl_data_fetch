package com.bjl.javafxboot.util;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class OcrUtil {
    //设置APPID/AK/SK
    public static final String APP_ID = "23773706";
    public static final String API_KEY = "4KX9oeWDvSpuFWOYlWrRH2V2";
    public static final String SECRET_KEY = "ociSKK1iqwL5G45kyno8wmeERr1ivbMW";
    // 初始化一个AipOcr
    AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    public String getVerifyCode(byte[] file){
        //String url = "https://71ab.vip/web/rest/code?_="+new Date().getTime();
        if (client==null)
            client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "false");
        options.put("detect_language", "false");
        options.put("probability", "false");
        JSONObject res = client.basicGeneral(file, options);
        System.out.println(res.toString(2));
        JSONArray words_result = res.getJSONArray("words_result");
        String words = words_result.getJSONObject(0).get("words").toString();
        System.out.println("验证码："+words);
        return words;
    }



    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        // 通用文字识别, 图片参数为远程url图片
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "false");
        options.put("detect_language", "false");
        options.put("probability", "false");
        String url = "https://71ab.vip/web/rest/code?_="+new Date().getTime();
        JSONObject res = client.basicGeneralUrl(url, options);
        System.out.println(res.toString(2));
        JSONArray words_result = res.getJSONArray("words_result");
        String words = words_result.getJSONObject(0).get("words").toString();
        System.out.println("验证码："+words);
    }
}
