package com.bjl.javafxboot.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjl.javafxboot.entity.Bjl;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {

    public static final int MAXCOLORRGB = 16777216;
    public static int[] moniMoney = {30,40,50,75,100,130,170,240,320,420,560,630,830};
    public static int[] tuierjinyiMoney = {10,10,20,30,50,80,130,210,340,550,890,1440, 2330, 3770, 6100, 9870, 15970, 25840, 41810, 67650, 109460};

    public static void addHeader(HttpRequestBase request){
        // 设置请求头
        request.addHeader("Accept", "application/json, text/plain, */*");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        request.addHeader("Connection", "keep-alive");
        //request.addHeader("Content-type", "application/json");
        request.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
    }

    public static void addHeaderP(HttpRequestBase request){
        // 设置请求头
        request.addHeader("Accept", "application/json, text/plain, */*");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Content-type", "application/json");
        request.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
    }

    public static void addCookies(CookieStore cookieStore, String tokenId){
        BasicClientCookie cookie1 = new BasicClientCookie( "affid" , null );
        cookie1.setDomain( ".71ab.vip" );
        cookieStore.addCookie(cookie1);

        BasicClientCookie cookie2 = new BasicClientCookie( "_skin_" , "blue" );
        cookie2.setDomain( ".71ab.vip" );
        cookieStore.addCookie(cookie2);

        BasicClientCookie cookie3 = new BasicClientCookie( "a7e44ec6c233" , tokenId );
        cookie3.setDomain( ".71ab.vip" );
        cookieStore.addCookie(cookie3);

        BasicClientCookie cookie4 = new BasicClientCookie( "defaultLT" , "PK10JSC" );
        cookie4.setDomain( ".71ab.vip" );
        cookieStore.addCookie(cookie4);

        BasicClientCookie cookie5 = new BasicClientCookie( "token" , tokenId );
        cookie5.setDomain( ".71ab.vip" );
        cookieStore.addCookie(cookie5);
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
    
    //获取单双
    public static String getDS(String ball){
        if (Integer.parseInt(ball)%2!=0){
            //单
            return "D";
        }else {
            //双
            return "S";
        }
    }

    //获取大小
    public static String getDX(String ball){
        if (Integer.parseInt(ball)>5){
            //大
            return "D";
        }else {
            //小
            return "X";
        }
    }


    public static String getDX1(int ball) {
        if (ball>11) return "D";
        else return "X";
    }

    //获取龙虎
    public static String getLH(String ball1,String ball2){
        if (Integer.parseInt(ball1)>Integer.parseInt(ball2))
            return "L";
        else
            return "H";
    }

    public static void buildXiaJsonObj(JSONObject jsonObject,String drawNumber,String game,String contents,int amount,String lotteryType){
        if (jsonObject.isEmpty()){
            JSONArray bets = new JSONArray();
            jsonObject.put("lottery","PK10JSC");
            jsonObject.put("ignore",false);
            jsonObject.put("drawNumber",Integer.parseInt(drawNumber));
            JSONObject betObj = new JSONObject();
            betObj.put("game",game);
            betObj.put("contents",contents);
            betObj.put("amount",amount);
            betObj.put("odds",1.98);
            bets.add(betObj);
            jsonObject.put("bets",bets);
        }else{
            JSONArray bets = jsonObject.getJSONArray("bets");
            for (int i=0;i<bets.size();i++){
                JSONObject betObj = (JSONObject) bets.get(i);
                //相同的球把下注金额相加
                if (betObj.getString("game").equals(game) && betObj.getString("contents").equals(contents)){
                    betObj.put("amount",betObj.getIntValue("amount")+amount);
                    return;
                }
            }
            JSONObject betObj = new JSONObject();
            betObj.put("game",game);
            betObj.put("contents",contents);
            betObj.put("amount",amount);
            betObj.put("odds",1.98);
            bets.add(betObj);
        }
    }

    public static Timestamp formartTime(String st,String yyyy){
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Timestamp ts = null;
        try {
//            String[] strArr = st.split(" ");
//            //针对跨年的进行特殊处理，本方法不可通用其它业务
//            if (strArr[0].equals("01-01") && Integer.parseInt(strArr[2].split(":")[0])<6){
//                yyyy = String.valueOf(Integer.parseInt(yyyy)+1);
//            }
            ts = new Timestamp(format1.parse(st).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ts;
    }

    //获取指定月份的天数
    public static int getMaxByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static List<String> getDaysByDateStr(String date){
        List<String> dates = new ArrayList<>();
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        int max = getMaxByYearMonth(year,month);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始
        for (int i = 0; i < max; i++, cal.add(Calendar.DATE, 1)) {
            Date d = cal.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String df = simpleDateFormat.format(d);
            dates.add(df);
        }
        return dates;
    }

    /**
       * 获取两个日期之间的所有日期(字符串格式, 按天计算)
       *
       * @param startTime String 开始时间 yyyy-MM-dd
       * @param endTime String 结束时间 yyyy-MM-dd
       * @return
       */
    public static List<String> getBetweenDays(String startTime, String endTime) throws ParseException {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            return null;
        }
        //1、定义转换格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date start = df.parse(startTime);
        Date end = df.parse(endTime);
        List<String> result = new ArrayList<String>();

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(Calendar.DAY_OF_YEAR,1);
        result.add(sdf.format(start));
        while (tempStart.before(tempEnd)) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    public static Timestamp getStartTime(String startStrs) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = df.parse(startStrs);
        return new Timestamp(start.getTime());
    }

    public static Timestamp getEndTime(String endStrs) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date end = df.parse(endStrs);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(end);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        return new Timestamp(tempStart.getTime().getTime());
    }

    public static long getBetweenBaoTime(String oldTime,String newTime){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldT = null;
        Date newT = null;
        try {
            oldT = df.parse(oldTime);
            newT = df.parse(newTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (newT.getTime()-oldT.getTime())/1000/3600;
    }

    public static int[] getXiaBalls(int ball){
        Random random = new Random();
        int[] arr = new int[9];
        arr[0] = ball;
        int i = 1;
        //外循环定义四个数
        while(i<=8) {
            int x = random.nextInt(10);
            /*内循环：新生成随机数和已生成的比较，
             *相同则跳出内循环，再生成一个随机数进行比较
             *和前几个生成的都不同则这个就是新的随机数
             */
            if (x!=0 && x!=ball){
                for (int j = 0; j <= i - 1; j++) {
                    //相同则跳出内循环，再生成一个随机数进行比较
                    if (arr[j] == x) {
                        break;
                    }
                    //执行完循环和前几个生成的都不同则这个就是新的随机数
                    if(j+1==i){
                        arr[i] = x;
                        i++;
                    }
                }
            }
        }
        //打印出来生成的随机数
//        for (int aaa : arr) {
//            System.out.print(aaa);
//        }
        return arr;
    }

    public static int[] getXiaBalls1(String[] preballs,String[] drawBalls,int b){
        int index = 5;
        boolean flag = true;
        int[] arr = new int[9];
        while (flag){
            if (preballs[index]!=null){
                String[] ball = preballs[index].split(",");
                if (ball[b].equals(drawBalls[b])){
                    index --;
                }else {
                    int j = 0;
                    for (int i = 0; i < 9; i++) {
                        if ((i+1)==Integer.parseInt(ball[b])){
                            j++;
                        }
                        arr[i] = i+1+j;
                    }
                    flag = false;
                }
            }else {
                Random random = new Random();
                arr[0] = Integer.parseInt(drawBalls[b]);
                int i = 1;
                //外循环定义四个数
                while(i<=8) {
                    int x = random.nextInt(10);
                    /*内循环：新生成随机数和已生成的比较，
                     *相同则跳出内循环，再生成一个随机数进行比较
                     *和前几个生成的都不同则这个就是新的随机数
                     */
                    if (x!=0 && x!=arr[0]){
                        for (int j = 0; j <= i - 1; j++) {
                            //相同则跳出内循环，再生成一个随机数进行比较
                            if (arr[j] == x) {
                                break;
                            }
                            //执行完循环和前几个生成的都不同则这个就是新的随机数
                            if(j+1==i){
                                arr[i] = x;
                                i++;
                            }
                        }
                    }
                }
                flag = false;
            }
        }
        //打印出来生成的随机数
        System.out.print("第"+(b+1)+"球下注号码：");
        for (int aaa : arr) {
            System.out.print(aaa+",");
        }
        System.out.println("");
        return arr;
    }

    /**
     * 开某投某
     * @param b 开奖号码
     * @param cnt 下几个球
     * @return
     */
    public static int[] getXiaBalls2(int b,int cnt){
        int[] arr = new int[cnt];
        arr[0] = b;
        for (int i = 1; i < 7; i++) {
            if ((b+i)<=10) arr[i] = b+i;
            else arr[i] = b+i-10;
        }
        return arr;
    }

    public static boolean contains(int[] balls,int ball){
        for (int i = 0; i < balls.length; i++) {
            if (balls[i]==ball)
                return true;
        }
        return false;
    }

    public static void updateTextArea(TextArea console, String message){
        Platform.runLater(()->{
            //任何更新界面组件信息的代码
            int lineCount = console.getText().split("\n").length;
            if (lineCount >= 34) {
                console.setText(message);
            } else {
                console.appendText(message);
            }
        });
    }

    // 获取10～100间，间隔为10的随机数
    public static int getRandomNum(int m1, int m2) {
        Random random = new Random();
        int min = m1, max = m2, interval = 10;
        return min + interval * random.nextInt((max - min) / interval + 1);
    }

    public static void main(String[] args) throws ParseException {
//        Timestamp timestamp = formartTime("03-04 四 15:55:30");
//        System.out.println(timestamp.getTime());
//        String strs = "03-04 四 15:55:30";
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date start = df.parse("2021-03-10 ");

        String sDate = "2022-05-01";
        char[] t = sDate.toCharArray();
        System.out.println(t[0]);
        System.out.println(t[1]);
    }
}
