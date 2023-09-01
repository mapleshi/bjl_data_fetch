package com.bjl.javafxboot.listener;

import com.alibaba.fastjson.JSONObject;
import com.bjl.javafxboot.dto.AllParams;
import com.bjl.javafxboot.util.HttpUtils;
import com.bjl.javafxboot.util.SmartDealUtil;
import com.bjl.javafxboot.services.CaiyuleService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.http.client.CookieStore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkListener implements ChangeListener {

    CookieStore cookieStore;String lotteryType,userName;CaiyuleService caiyuleService;
    Label nextNumber,accountshow,lostTip,winTip,drawTime;
    TextField dayWinMoney;CheckBox isTest;
    TextField fancnt0, fancnt1, fancnt2, fancnt3, fancnt4, fancnt5, fancnt6, fancnt7, fancnt8, fancnt9, fancnt10, fancnt_0, fancnt_1, fancnt_2, fancnt_3;
    TextField money0, money1, money2, money3, money4, money5, money6, money7, money8, money9, money10, money_0, money_1, money_2, money_3;
    TextField mVal0, mVal1, mVal2, mVal3, mVal4, mVal5, mVal6, mVal7, mVal8, mVal9, mVal10, mVal_0, mVal_1, mVal_2, mVal_3;
    CheckBox fanan0, fanan1, fanan2, fanan3, fanan4, fanan5, fanan6, fanan7, fanan8, fanan9, fanan10, fanan_0, fanan_1, fanan_2, fanan_3;
    CheckBox tiao0, tiao1, tiao2, tiao3, tiao4, tiao5, tiao10, geng0, geng1, geng2, geng3, geng4, geng5, geng10;
    CheckBox pin0, pin1, pin2, pin3, pin4, pin5, pin10, fan0, fan1, fan2, fan3, fan4, fan5, fan10;
    TextField hour01,hour02,hour03,hour04,hour05,hour06,hour07,hour08,replayCnt0,hour11,hour12,hour13,hour14,hour15,hour16,replayCnt1;
    TextField hour21,hour22,hour23,hour24,hour25,hour26,replayCnt2,hour31,hour32,hour33,hour34,hour35,hour36,replayCnt3;
    TextField hour41,hour42,hour43,hour44,hour45,hour46,replayCnt4,hour51,hour52,hour53,hour54,hour55,hour56,replayCnt5;
    TextField hour101,hour102,hour103,hour104,hour105,hour106,replayCnt10;
    CheckBox isReplay0,isReplay1,isReplay2,isReplay3,isReplay4,isReplay5,isReplay10;

    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //方案变量
    AllParams params = new AllParams();

    public WorkListener(CaiyuleService caiyuleService, CookieStore cookieStore, CheckBox isTest,
                        String userName, Label nextNumber, Label accountshow, TextField dayWinMoney, Label lostTip, Label winTip, Label drawTime,String lotteryType,
                        CheckBox fanan0,CheckBox fanan1,CheckBox fanan2,CheckBox fanan3,CheckBox fanan4,CheckBox fanan5,CheckBox fanan6,
                        CheckBox fanan7,CheckBox fanan8,CheckBox fanan9,CheckBox fanan10,CheckBox fanan_0,CheckBox fanan_1,CheckBox fanan_2,CheckBox fanan_3,
                        TextField fancnt0,TextField fancnt1,TextField fancnt2,TextField fancnt3,TextField fancnt4,TextField fancnt5,
                        TextField fancnt6,TextField fancnt7,TextField fancnt8,TextField fancnt9,TextField fancnt10,
                        TextField fancnt_0,TextField fancnt_1,TextField fancnt_2,TextField fancnt_3,
                        TextField mVal0,TextField mVal1,TextField mVal2,TextField mVal3,TextField mVal4,TextField mVal5,TextField mVal6,
                        TextField mVal7,TextField mVal8, TextField mVal9, TextField mVal10,
                        TextField mVal_0,TextField mVal_1,TextField mVal_2,TextField mVal_3,
                        TextField money0,TextField money1,TextField money2,TextField money3,TextField money4,TextField money5,TextField money6,
                        TextField money7,TextField money8, TextField money9, TextField money10,
                        TextField money_0,TextField money_1,TextField money_2,TextField money_3,
                        CheckBox tiao0,CheckBox tiao1,CheckBox tiao2,CheckBox tiao3,CheckBox tiao4,CheckBox tiao5,CheckBox tiao10,
                        CheckBox geng0,CheckBox geng1,CheckBox geng2,CheckBox geng3,CheckBox geng4,CheckBox geng5,CheckBox geng10,
                        CheckBox pin0,CheckBox pin1,CheckBox pin2,CheckBox pin3,CheckBox pin4,CheckBox pin5,CheckBox pin10,
                        CheckBox fan0,CheckBox fan1,CheckBox fan2,CheckBox fan3,CheckBox fan4,CheckBox fan5,CheckBox fan10,
                        TextField hour01,TextField hour02,TextField hour03,TextField hour04,TextField hour05,TextField hour06,
                        TextField hour07,TextField hour08,TextField replayCnt0,
                        TextField hour11,TextField hour12,TextField hour13,TextField hour14,TextField hour15,TextField hour16,TextField replayCnt1,
                        TextField hour21,TextField hour22,TextField hour23,TextField hour24,TextField hour25,TextField hour26,TextField replayCnt2,
                        TextField hour31,TextField hour32,TextField hour33,TextField hour34,TextField hour35,TextField hour36,TextField replayCnt3,
                        TextField hour41,TextField hour42,TextField hour43,TextField hour44,TextField hour45,TextField hour46,TextField replayCnt4,
                        TextField hour51,TextField hour52,TextField hour53,TextField hour54,TextField hour55,TextField hour56,TextField replayCnt5,
                        TextField hour101,TextField hour102,TextField hour103,TextField hour104,TextField hour105,TextField hour106,TextField replayCnt10,
                        CheckBox isReplay0,CheckBox isReplay1,CheckBox isReplay2,CheckBox isReplay3,CheckBox isReplay4,CheckBox isReplay5,CheckBox isReplay10) {
        this.caiyuleService = caiyuleService;this.cookieStore = cookieStore;this.isTest = isTest;this.userName = userName;
        this.nextNumber = nextNumber;this.accountshow = accountshow;this.dayWinMoney = dayWinMoney;
        this.lostTip = lostTip;this.winTip = winTip;this.drawTime = drawTime;this.lotteryType = lotteryType;
        this.fanan0 = fanan0;this.fanan1 = fanan1;this.fanan2 = fanan2;this.fanan3 = fanan3;this.fanan4 = fanan4;this.fanan5 = fanan5;
        this.fanan6 = fanan6;this.fanan7 = fanan7;this.fanan8 = fanan8;this.fanan9 = fanan9;this.fanan10 = fanan10;
        this.fanan_0 = fanan_0;this.fanan_1 = fanan_1;this.fanan_2 = fanan_2;this.fanan_3 = fanan_3;
        this.money0 = money0;this.money1 = money1;this.money2 = money2;this.money3 = money3;this.money4 = money4;this.money5 = money5;
        this.money6 = money6;this.money7 = money7;this.money8 = money8;this.money9 = money9;this.money10 = money10;
        this.money_0 = money_0;this.money_1 = money_1;this.money_2 = money_2;this.money_3 = money_3;
        this.fancnt0 = fancnt0;this.fancnt1 = fancnt1;this.fancnt2 = fancnt2;this.fancnt3 = fancnt3;this.fancnt4 = fancnt4;this.fancnt5 = fancnt5;
        this.fancnt6 = fancnt6;this.fancnt7 = fancnt7;this.fancnt8 = fancnt8;this.fancnt9 = fancnt9;this.fancnt10 = fancnt10;
        this.fancnt_0 = fancnt_0;this.fancnt_1 = fancnt_1;this.fancnt_2 = fancnt_2;this.fancnt_3 = fancnt_3;
        this.mVal0 = mVal0;this.mVal1 = mVal1;this.mVal2 = mVal2;this.mVal3 = mVal3;this.mVal4 = mVal4;this.mVal5 = mVal5;
        this.mVal6 = mVal6;this.mVal7 = mVal7;this.mVal8 = mVal8;this.mVal9 = mVal9;this.mVal10 = mVal10;
        this.mVal_0 = mVal_0;this.mVal_1 = mVal_1;this.mVal_2 = mVal_2;this.mVal_3 = mVal_3;
        this.tiao0 = tiao0;this.tiao1 = tiao1;this.tiao2 = tiao2;this.tiao3 = tiao3;this.tiao4 = tiao4;this.tiao5 = tiao5;this.tiao10 = tiao10;
        this.geng0 = geng0;this.geng1 = geng1;this.geng2 = geng2;this.geng3 = geng3;this.geng4 = geng4;this.geng5 = geng5;this.geng10 = geng10;
        this.pin0 = pin0;this.pin1 = pin1;this.pin2 = pin2;this.pin3 = pin3;this.pin4 = pin4;this.pin5 = pin5;this.pin10 = pin10;
        this.fan0 = fan0;this.fan1 = fan1;this.fan2 = fan2;this.fan3 = fan3;this.fan4 = fan4;this.fan5 = fan5;this.fan10 = fan10;
        this.hour01 = hour01;this.hour02 = hour02;this.hour03 = hour03;this.hour04 = hour04;this.hour05 = hour05;this.hour06 = hour06;this.isReplay0 = isReplay0;this.replayCnt0 = replayCnt0;
        this.hour11 = hour11;this.hour12 = hour12;this.hour13 = hour13;this.hour14 = hour14;this.hour15 = hour15;this.hour16 = hour16;this.isReplay1 = isReplay1;this.replayCnt1 = replayCnt1;
        this.hour21 = hour21;this.hour22 = hour22;this.hour23 = hour23;this.hour24 = hour24;this.hour25 = hour25;this.hour26 = hour26;this.isReplay2 = isReplay2;this.replayCnt2 = replayCnt2;
        this.hour31 = hour31;this.hour32 = hour32;this.hour33 = hour33;this.hour34 = hour34;this.hour35 = hour35;this.hour36 = hour36;this.isReplay3 = isReplay3;this.replayCnt3 = replayCnt3;
        this.hour41 = hour41;this.hour42 = hour42;this.hour43 = hour43;this.hour44 = hour44;this.hour45 = hour45;this.hour46 = hour46;this.isReplay4 = isReplay4;this.replayCnt4 = replayCnt4;
        this.hour51 = hour51;this.hour52 = hour52;this.hour53 = hour53;this.hour54 = hour54;this.hour55 = hour55;this.hour56 = hour56;this.isReplay5 = isReplay5;this.replayCnt5 = replayCnt5;
        this.hour101 = hour101;this.hour102 = hour102;this.hour103 = hour103;this.hour104 = hour104;this.hour105 = hour105;this.hour106 = hour106;this.isReplay10 = isReplay10;this.replayCnt10 = replayCnt10;
        this.hour07 = hour07;this.hour08 = hour08;
        //初始化基础数据
        putcurrentBaseParams();
        initBaseData();
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        System.out.println("最新开奖结果开出，触发模式计算！-----开始，时间："+sdf.format(new Date()));
        //每次计算前取最新的基础数据
        putcurrentBaseParams();
        if (params.isFirst){
            firstDraw();
            params.isFirst = false;
        }else {
            params.xiaJson = new JSONObject();
            params.msgLevel[0] = "";params.msgLevel[1] = "";params.msgLevel[2] = "";

            SmartDealUtil.smartDealBall(oldValue.toString(),newValue.toString(),nextNumber.getText(),params,
                    sdf.format(new Date()), drawTime.getText(), lotteryType);

            //下注
            if (!isTest.isSelected()){
                //下单_2次，确保下单成功
                if (!params.xiaJson.isEmpty()){
                    for (int i=0;i<params.bCnt;i++){
                        try {
                            System.out.println("params.xiaJson="+JSONObject.toJSONString(params.xiaJson)+"，时间："+sdf.format(new Date()));
                            String results = HttpUtils.doPost_bet(cookieStore,params.xiaJson);
                            System.out.println("下单接口返回为："+results+"，时间："+sdf.format(new Date()));
                            JSONObject resObj = JSONObject.parseObject(results);
                            if (resObj.containsKey("status")){
                                if (resObj.getIntValue("status")==0){
                                    JSONObject accountObj = resObj.getJSONObject("account");
                                    updateFxWeb(accountObj.getString("balance"),accountObj.getString("result"));
                                    System.out.println("下单成功！");
                                }else {
                                    System.out.println("下单失败！");
                                }
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("下单异常！重新下单！");
                        }
                    }
                }
            }
            //显示输赢记录
            showTipMsg();
            //插入提醒消息
            //if (!params.msgLevel[0].isEmpty() || !params.msgLevel[1].isEmpty() || !params.msgLevel[2].isEmpty())
                //caiyuleService.saveCaiyuleXiaMsg(userName,params.msgLevel[0],params.msgLevel[1],params.msgLevel[2]);
        }
        System.out.println("最新开奖结果开出，触发模式计算！-----结束，时间："+sdf.format(new Date()));
    }

    private void firstDraw(){
        for (int ball=0;ball<params.bCnt;ball++){
            params.dscnt[ball] = 1;params.dxcnt[ball] = 1;
        }
    }

    private void initBaseData(){
        for (int ball=0;ball<params.bCnt;ball++){
            params.dscnt[ball] = 0;params.dxcnt[ball] = 0;params.dsliancnt[ball] = 0;params.dxliancnt[ball] = 0;
            params.dacnt[ball] = 0;params.xcnt[ball] = 0;params.dcnt[ball] = 0;params.scnt[ball] = 0;
            params.dstiaocnt[ball] = 0;params.dxtiaocnt[ball] = 0;
            for (int f=0;f<params.fCnt;f++){
                params.dsmoney[f][ball] = params.money[f];params.dxmoney[f][ball] = params.money[f];
                params.dslost[f][ball] = 0;params.dxlost[f][ball] = 0;
                params.r_dsmoney[f][ball] = 0;params.r_dxmoney[f][ball] = 0;
            }
            for (int o_f=0;o_f<params.o_fCnt;o_f++){
                params.r_o_money[o_f][ball] = 0;
            }
            params.m_0dmoney[ball] = params.o_money[0];params.m_1xmoney[ball] = params.o_money[1];params.m_0dlost[ball] = 0;params.m_1xlost[ball] = 0;
            params.m_2dmoney[ball] = params.o_money[2];params.m_3smoney[ball] = params.o_money[3];params.m_2dlost[ball] = 0;params.m_3slost[ball] = 0;
        }
    }

    private void showTipMsg(){
        StringBuilder lostShow = new StringBuilder();
        for (int f=0;f<params.fCnt;f++){
            for (int ball=0;ball<params.bCnt;ball++){
                if (params.dslost[f][ball]>4){
                    lostShow.append("m").append(f).append(" DS[").append(ball + 1).append("] lost=").append(params.dslost[f][ball]).append("; ");
                }
                if (params.dxlost[f][ball]>4){
                    lostShow.append("m").append(f).append(" DX[").append(ball + 1).append("] lost=").append(params.dxlost[f][ball]).append("; ");
                }
            }
        }

        StringBuilder winMoneyTip = new StringBuilder("总计:" + String.format("%.2f", params.totalMoney[0]));
        for (int f=0;f<params.fCnt;f++){
            winMoneyTip.append(",方案").append(f).append(":").append(String.format("%.2f", params.fananMoney[f]));
        }
        for (int o_f = 0; o_f < params.o_fCnt; o_f++) {
            winMoneyTip.append(",方_").append(o_f).append(":").append(String.format("%.2f", params.o_fananMoney[o_f]));
        }
        Platform.runLater(()->{
            //任何更新界面组件信息的代码
            lostTip.setText(lostShow.toString());
            winTip.setText(winMoneyTip.toString());
        });
    }

    private void updateFxWeb(String balance,String dayWinM){
        Platform.runLater(()->{
            //任何更新界面组件信息的代码
            accountshow.setText(balance);
            dayWinMoney.setText(dayWinM);
        });
    }

    public void fananCheck(int f) {
        for (int ball=0;ball<params.bCnt;ball++){
            params.dslost[f][ball] = 0;params.dxlost[f][ball] = 0;
        }
        params.fananMoney[f] = 0;
    }

    public void _fananCheck(int f) {
        for (int ball=0;ball<params.bCnt;ball++){
            switch (f){
                case 20:
                    params.m_0dlost[ball] = 0;
                    break;
                case 21:
                    params.m_1xlost[ball] = 0;
                    break;
                case 22:
                    params.m_2dlost[ball] = 0;
                    break;
                case 23:
                    params.m_3slost[ball] = 0;
                    break;
            }
        }
        params.o_fananMoney[f-20] = 0;
    }

    private void putcurrentBaseParams(){
        params.isTest = this.isTest.isSelected();
        params.fancnt[0] = Integer.parseInt(fancnt0.getText());
        params.fancnt[1] = Integer.parseInt(fancnt1.getText());
        params.fancnt[2] = Integer.parseInt(fancnt2.getText());
        params.fancnt[3] = Integer.parseInt(fancnt3.getText());
        params.fancnt[4] = Integer.parseInt(fancnt4.getText());
        params.fancnt[5] = Integer.parseInt(fancnt5.getText());
        params.fancnt[6] = Integer.parseInt(fancnt6.getText());
        params.fancnt[7] = Integer.parseInt(fancnt7.getText());
        params.fancnt[8] = Integer.parseInt(fancnt8.getText());
        params.fancnt[9] = Integer.parseInt(fancnt9.getText());
        params.fancnt[10] = Integer.parseInt(fancnt10.getText());
        params.o_fancnt[0] = Integer.parseInt(fancnt_0.getText());
        params.o_fancnt[1] = Integer.parseInt(fancnt_1.getText());
        params.o_fancnt[2] = Integer.parseInt(fancnt_2.getText());
        params.o_fancnt[3] = Integer.parseInt(fancnt_3.getText());
        params.mVal[0] = Integer.parseInt(mVal0.getText());
        params.mVal[1] = Integer.parseInt(mVal1.getText());
        params.mVal[2] = Integer.parseInt(mVal2.getText());
        params.mVal[3] = Integer.parseInt(mVal3.getText());
        params.mVal[4] = Integer.parseInt(mVal4.getText());
        params.mVal[5] = Integer.parseInt(mVal5.getText());
        params.mVal[6] = Integer.parseInt(mVal6.getText());
        params.mVal[7] = Integer.parseInt(mVal7.getText());
        params.mVal[8] = Integer.parseInt(mVal8.getText());
        params.mVal[9] = Integer.parseInt(mVal9.getText());
        params.mVal[10] = Integer.parseInt(mVal10.getText());
        params.o_mVal[0] = Integer.parseInt(mVal_0.getText());
        params.o_mVal[1] = Integer.parseInt(mVal_1.getText());
        params.o_mVal[2] = Integer.parseInt(mVal_2.getText());
        params.o_mVal[3] = Integer.parseInt(mVal_3.getText());
        params.money[0] = Integer.parseInt(money0.getText());
        params.money[1] = Integer.parseInt(money1.getText());
        params.money[2] = Integer.parseInt(money2.getText());
        params.money[3] = Integer.parseInt(money3.getText());
        params.money[4] = Integer.parseInt(money4.getText());
        params.money[5] = Integer.parseInt(money5.getText());
        params.money[6] = Integer.parseInt(money6.getText());
        params.money[7] = Integer.parseInt(money7.getText());
        params.money[8] = Integer.parseInt(money8.getText());
        params.money[9] = Integer.parseInt(money9.getText());
        params.money[10] = Integer.parseInt(money10.getText());
        params.o_money[0] = Integer.parseInt(money_0.getText());
        params.o_money[1] = Integer.parseInt(money_1.getText());
        params.o_money[2] = Integer.parseInt(money_2.getText());
        params.o_money[3] = Integer.parseInt(money_3.getText());
        params.fanan[0] = fanan0.isSelected();
        params.fanan[1] = fanan1.isSelected();
        params.fanan[2] = fanan2.isSelected();
        params.fanan[3] = fanan3.isSelected();
        params.fanan[4] = fanan4.isSelected();
        params.fanan[5] = fanan5.isSelected();
        params.fanan[6] = fanan6.isSelected();
        params.fanan[7] = fanan7.isSelected();
        params.fanan[8] = fanan8.isSelected();
        params.fanan[9] = fanan9.isSelected();
        params.fanan[10] = fanan10.isSelected();
        params.o_fanan[0] = fanan_0.isSelected();
        params.o_fanan[1] = fanan_1.isSelected();
        params.o_fanan[2] = fanan_2.isSelected();
        params.o_fanan[3] = fanan_3.isSelected();
        params.tiao[0] = tiao0.isSelected();
        params.tiao[1] = tiao1.isSelected();
        params.tiao[2] = tiao2.isSelected();
        params.tiao[3] = tiao3.isSelected();
        params.tiao[4] = tiao4.isSelected();
        params.tiao[5] = tiao5.isSelected();
        params.tiao[10] = tiao10.isSelected();
        params.geng[0] = geng0.isSelected();
        params.geng[1] = geng1.isSelected();
        params.geng[2] = geng2.isSelected();
        params.geng[3] = geng3.isSelected();
        params.geng[4] = geng4.isSelected();
        params.geng[5] = geng5.isSelected();
        params.geng[10] = geng10.isSelected();
        params.pin[0] = pin0.isSelected();
        params.pin[1] = pin1.isSelected();
        params.pin[2] = pin2.isSelected();
        params.pin[3] = pin3.isSelected();
        params.pin[4] = pin4.isSelected();
        params.pin[5] = pin5.isSelected();
        params.pin[9] = true;
        params.pin[10] = pin10.isSelected();
        params.fan[0] = fan0.isSelected();
        params.fan[1] = fan1.isSelected();
        params.fan[2] = fan2.isSelected();
        params.fan[3] = fan3.isSelected();
        params.fan[4] = fan4.isSelected();
        params.fan[5] = fan5.isSelected();
        params.fan[10] = fan10.isSelected();
        params.isReplay[0] = isReplay0.isSelected();
        params.isReplay[1] = isReplay1.isSelected();
        params.isReplay[2] = isReplay2.isSelected();
        params.isReplay[3] = isReplay3.isSelected();
        params.isReplay[4] = isReplay4.isSelected();
        params.isReplay[5] = isReplay5.isSelected();
        params.hours[0][0] = Integer.parseInt(hour01.getText().equals("")?"-1":hour01.getText());
        params.hours[0][1] = Integer.parseInt(hour02.getText().equals("")?"-1":hour02.getText());
        params.hours[0][2] = Integer.parseInt(hour03.getText().equals("")?"-1":hour03.getText());
        params.hours[0][3] = Integer.parseInt(hour04.getText().equals("")?"-1":hour04.getText());
        params.hours[0][4] = Integer.parseInt(hour05.getText().equals("")?"-1":hour05.getText());
        params.hours[0][5] = Integer.parseInt(hour06.getText().equals("")?"-1":hour06.getText());
        params.hours[0][6] = Integer.parseInt(hour07.getText().equals("")?"-1":hour07.getText());
        params.hours[0][7] = Integer.parseInt(hour08.getText().equals("")?"-1":hour08.getText());
        params.hours[1][0] = Integer.parseInt(hour11.getText().equals("")?"-1":hour11.getText());
        params.hours[1][1] = Integer.parseInt(hour12.getText().equals("")?"-1":hour12.getText());
        params.hours[1][2] = Integer.parseInt(hour13.getText().equals("")?"-1":hour13.getText());
        params.hours[1][3] = Integer.parseInt(hour14.getText().equals("")?"-1":hour14.getText());
        params.hours[1][4] = Integer.parseInt(hour15.getText().equals("")?"-1":hour15.getText());
        params.hours[1][5] = Integer.parseInt(hour16.getText().equals("")?"-1":hour16.getText());
        params.hours[2][0] = Integer.parseInt(hour21.getText().equals("")?"-1":hour21.getText());
        params.hours[2][1] = Integer.parseInt(hour22.getText().equals("")?"-1":hour22.getText());
        params.hours[2][2] = Integer.parseInt(hour23.getText().equals("")?"-1":hour23.getText());
        params.hours[2][3] = Integer.parseInt(hour24.getText().equals("")?"-1":hour24.getText());
        params.hours[2][4] = Integer.parseInt(hour25.getText().equals("")?"-1":hour25.getText());
        params.hours[2][5] = Integer.parseInt(hour26.getText().equals("")?"-1":hour26.getText());
        params.hours[3][0] = Integer.parseInt(hour31.getText().equals("")?"-1":hour31.getText());
        params.hours[3][1] = Integer.parseInt(hour32.getText().equals("")?"-1":hour32.getText());
        params.hours[3][2] = Integer.parseInt(hour33.getText().equals("")?"-1":hour33.getText());
        params.hours[3][3] = Integer.parseInt(hour34.getText().equals("")?"-1":hour34.getText());
        params.hours[3][4] = Integer.parseInt(hour35.getText().equals("")?"-1":hour35.getText());
        params.hours[3][5] = Integer.parseInt(hour36.getText().equals("")?"-1":hour36.getText());
        params.hours[4][0] = Integer.parseInt(hour41.getText().equals("")?"-1":hour41.getText());
        params.hours[4][1] = Integer.parseInt(hour42.getText().equals("")?"-1":hour42.getText());
        params.hours[4][2] = Integer.parseInt(hour43.getText().equals("")?"-1":hour43.getText());
        params.hours[4][3] = Integer.parseInt(hour44.getText().equals("")?"-1":hour44.getText());
        params.hours[4][4] = Integer.parseInt(hour45.getText().equals("")?"-1":hour45.getText());
        params.hours[4][5] = Integer.parseInt(hour46.getText().equals("")?"-1":hour46.getText());
        params.hours[5][0] = Integer.parseInt(hour51.getText().equals("")?"-1":hour51.getText());
        params.hours[5][1] = Integer.parseInt(hour52.getText().equals("")?"-1":hour52.getText());
        params.hours[5][2] = Integer.parseInt(hour53.getText().equals("")?"-1":hour53.getText());
        params.hours[5][3] = Integer.parseInt(hour54.getText().equals("")?"-1":hour54.getText());
        params.hours[5][4] = Integer.parseInt(hour55.getText().equals("")?"-1":hour55.getText());
        params.hours[5][5] = Integer.parseInt(hour56.getText().equals("")?"-1":hour56.getText());
        params.hours[6][0] = Integer.parseInt(hour101.getText().equals("")?"-1":hour101.getText());
        params.hours[6][1] = Integer.parseInt(hour102.getText().equals("")?"-1":hour102.getText());
        params.hours[6][2] = Integer.parseInt(hour103.getText().equals("")?"-1":hour103.getText());
        params.hours[6][3] = Integer.parseInt(hour104.getText().equals("")?"-1":hour104.getText());
        params.hours[6][4] = Integer.parseInt(hour105.getText().equals("")?"-1":hour105.getText());
        params.hours[6][5] = Integer.parseInt(hour106.getText().equals("")?"-1":hour106.getText());
        params.replayCnt[0] = Integer.parseInt(replayCnt0.getText().equals("")?"-1":replayCnt0.getText());
        params.replayCnt[1] = Integer.parseInt(replayCnt1.getText().equals("")?"-1":replayCnt1.getText());
        params.replayCnt[2] = Integer.parseInt(replayCnt2.getText().equals("")?"-1":replayCnt2.getText());
        params.replayCnt[3] = Integer.parseInt(replayCnt3.getText().equals("")?"-1":replayCnt3.getText());
        params.replayCnt[4] = Integer.parseInt(replayCnt4.getText().equals("")?"-1":replayCnt4.getText());
        params.replayCnt[5] = Integer.parseInt(replayCnt5.getText().equals("")?"-1":replayCnt5.getText());
        params.replayCnt[6] = Integer.parseInt(replayCnt10.getText().equals("")?"-1":replayCnt10.getText());
    }
}