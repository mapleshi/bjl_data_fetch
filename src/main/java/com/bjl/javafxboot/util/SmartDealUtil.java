package com.bjl.javafxboot.util;

import com.bjl.javafxboot.dto.AllParams;

import java.util.Date;

public class SmartDealUtil {

    public static void smartDealBall(String oldNum, String newNum, String no, AllParams params, String nowTime,
                                     String drawTime, String lotteryType){
        if (oldNum.contains(",")&& newNum.contains(",")){
            String[] oldBalls = oldNum.split(",");
            String[] drawBalls = newNum.split(",");

            for (int ball=0;ball<drawBalls.length;ball++){
                //前一期的单双大小
                String oDS = CommonUtil.getDS(oldBalls[ball]);
                String oDX = CommonUtil.getDX(oldBalls[ball]);
                //这一期的单双大小
                String DS = CommonUtil.getDS(drawBalls[ball]);
                String DX = CommonUtil.getDX(drawBalls[ball]);

                //处理单双
                if (oDS.equals(DS)){    //顺
                    params.dscnt[ball] ++;
                    //方案零一二三
                    for (int f=0;f<params.fCnt;f++){
                        if (params.fanan[f]) smartDealByFanan(ball,f,DS,0,true, no, params, nowTime, lotteryType);
                    }
                    if (params.dscnt[ball] == 2) {
                        params.dsliancnt[ball] ++;
                        params.dstiaocnt[ball] = 0;
                    }
                }else {     //跳
                    //方案零一二三
                    for (int f=0;f<params.fCnt;f++){
                        if (params.fanan[f]) smartDealByFanan(ball,f,DS,0,false, no, params, nowTime, lotteryType);
                    }
                    if (params.dscnt[ball] == 1) params.dsliancnt[ball] = 0;
                    params.dscnt[ball] = 1;
                    params.dstiaocnt[ball] ++;
                }

                //处理大小
                if (oDX.equals(DX)){    //顺
                    params.dxcnt[ball] ++;
                    //方案零一二三
                    for (int f=0;f<params.fCnt;f++){
                        if (params.fanan[f]) smartDealByFanan(ball,f,DX,1,true, no, params, nowTime, lotteryType);
                    }
                    if (params.dxcnt[ball] == 2) {
                        params.dxliancnt[ball] ++;
                        params.dxtiaocnt[ball] = 0;
                    }
                }else {     //跳
                    //方案零一二三
                    for (int f=0;f<params.fCnt;f++){
                        if (params.fanan[f]) smartDealByFanan(ball,f,DX,1,false, no, params, nowTime, lotteryType);
                    }
                    if (params.dxcnt[ball] == 1) params.dxliancnt[ball] = 0;
                    params.dxcnt[ball] = 1;
                    params.dxtiaocnt[ball] ++;
                }

                //处理_0 _1 _2 _3模式
                fanKaiDeal(ball,oDS,oDX,DS,DX,no,params, nowTime, lotteryType);
            }

            //判断是否到达重置点，并重置方案数据
            initReplayData(params,drawTime);
        }
    }

    private static void smartDealByFanan(int ball, int f, String res, int flag, boolean add, String no, AllParams params,
                                         String drawTime, String lotteryType){
        switch (f){
            case 0:
            case 1:
            case 2:
                geTiao(ball,f,res,flag,add, no, params, drawTime, lotteryType);
                break;
            case 3:
            case 4:
            case 5:
                paiTiao(ball,f,res,flag,add, no, params, drawTime, lotteryType);
                break;
            case 6:
            case 7:
                paiZhui(ball,f,res,flag,add, no, params, drawTime, lotteryType);
                break;
            case 8:
                geZhui(ball,f,res,flag,add, no, params, drawTime, lotteryType);
                break;
            case 9:
                break;
            case 10:
                lianTiao(ball,f,res,flag,add, no, params, drawTime, lotteryType);
                break;
        }
    }

    /**
     * 个跳
     * @param ball
     * @param f
     * @param res
     * @param flag
     * @param add
     * @param no
     * @param params
     * @param drawTime
     */
    private static void geTiao(int ball, int f, String res, int flag, boolean add, String no, AllParams params,
                               String drawTime, String lotteryType){
        if (flag==0){   //单双
            if (add){  //连
                if (params.dscnt[ball]==params.mVal[f]){
                    dsbet(ball, f, res, params, drawTime, lotteryType, no, 1);
                }else if (params.dscnt[ball]==params.mVal[f]+1){
                    if (params.tiao[f]){
                        dslost(ball, f, params, drawTime);
                    }else if (params.geng[f]){
                        dswin(ball, f, params);
                    }
                }
            }else { //跳
                if (params.dscnt[ball]==params.mVal[f]){
                    if (params.tiao[f]){
                        dswin(ball, f, params);
                    }else if (params.geng[f]){
                        dslost(ball, f, params, drawTime);
                    }
                }
            }
        }else { //大小
            if (add){  //连
                if (params.dxcnt[ball]==params.mVal[f]){
                    dxbet(ball, f, res, params, drawTime, lotteryType, no, 1);
                }else if (params.dxcnt[ball]==params.mVal[f]+1){
                    if (params.tiao[f]){
                        dxlost(ball, f, params, drawTime);
                    }else if (params.geng[f]){
                        dxwin(ball, f, params);
                    }
                }
            }else { //跳
                if (params.dxcnt[ball]==params.mVal[f]){
                    if (params.tiao[f]){
                        dxwin(ball, f, params);
                    }else if (params.geng[f]){
                        dxlost(ball, f, params, drawTime);
                    }
                }
            }
        }
    }

    /**
     * 排跳
     * @param ball
     * @param f
     * @param res
     * @param flag
     * @param add
     * @param no
     * @param params
     * @param drawTime
     */
    private static void paiTiao(int ball, int f, String res, int flag, boolean add, String no, AllParams params,
                                String drawTime, String lotteryType){
        if (flag==0){   //单双
            if (add){   //连
                if (params.dscnt[ball]==2 && params.dsliancnt[ball]==params.mVal[f]){
                    if (params.tiao[f]){
                        dslost(ball, f, params, drawTime);
                    }
                    else if (params.geng[f]){
                        dswin(ball, f, params);
                    }
                }
            }else { //跳
                if (params.dsliancnt[ball]==params.mVal[f]){
                    if (params.dscnt[ball]==1){
                        if (params.tiao[f]){
                            dswin(ball, f, params);
                        }else if (params.geng[f]){
                            dslost(ball, f, params, drawTime);
                        }
                    }else if (params.dscnt[ball]>1){
                        dsbet(ball, f, res, params, drawTime, lotteryType, no, 1);
                    }
                }
            }
        }else { //大小
            if (add){   //连
                if (params.dxcnt[ball]==2 && params.dxliancnt[ball]==params.mVal[f]){
                    if (params.tiao[f]){
                        dxlost(ball, f, params, drawTime);
                    }
                    else if (params.geng[f]){
                        dxwin(ball, f, params);
                    }
                }
            }else { //跳
                if (params.dxliancnt[ball]==params.mVal[f]){
                    if (params.dxcnt[ball]==1){
                        if (params.tiao[f]){
                            dxwin(ball, f, params);
                        }else if (params.geng[f]){
                            dxlost(ball, f, params, drawTime);
                        }
                    }else if (params.dxcnt[ball]>1){
                        dxbet(ball, f, res, params, drawTime, lotteryType, no, 1);
                    }
                }
            }
        }
    }

    /**
     * 排追
     * @param ball
     * @param f
     * @param res
     * @param flag
     * @param add
     * @param no
     * @param params
     * @param drawTime
     */
    private static void paiZhui(int ball, int f, String res, int flag, boolean add, String no, AllParams params,
                                String drawTime, String lotteryType){
        if (flag==0) {   //单双
            if (add) {   //连
                if (params.dscnt[ball]==2 && params.dsliancnt[ball]>=params.mVal[f]){
                    dslost(ball, f, params, drawTime);
                }
            }else {     //跳
                if (params.dsliancnt[ball]>=params.mVal[f]){
                    if (params.dscnt[ball]==1){
                        dswin(ball, f, params);
                    }else if (params.dscnt[ball]>1){
                        dsbet(ball, f, res, params, drawTime, lotteryType, no, 2);
                    }
                }
            }
        }else {     //大小
            if (add) {   //连
                if (params.dxcnt[ball]==2 && params.dxliancnt[ball]>=params.mVal[f]){
                    dxlost(ball, f, params, drawTime);
                }
            }else {     //跳
                if (params.dxliancnt[ball]>=params.mVal[f]){
                    if (params.dxcnt[ball]==1){
                        dxwin(ball, f, params);
                    }else if (params.dxcnt[ball]>1){
                        dxbet(ball, f, res, params, drawTime, lotteryType, no, 2);
                    }
                }
            }
        }
    }

    /**
     * 个追
     * @param ball
     * @param f
     * @param res
     * @param flag
     * @param add
     * @param drawTime
     */
    private static void geZhui(int ball, int f, String res, int flag, boolean add, String no, AllParams params,
                               String drawTime, String lotteryType){
        if (flag==0) {   //单双
            if (add) {   //连
                if (params.dscnt[ball]==params.mVal[f]){
                    dsbet(ball, f, res, params, drawTime, lotteryType, no, 2);
                }else if (params.dscnt[ball]>params.mVal[f]){
                    dslost(ball, f, params, drawTime);

                    dsbet(ball, f, res, params, drawTime, lotteryType, no, 2);
                }
            }else {     //跳
                if (params.dscnt[ball]>=params.mVal[f]){
                    dswin(ball, f, params);
                }
            }
        }else {     //大小
            if (add) {   //连
                if (params.dxcnt[ball]==params.mVal[f]){
                    dxbet(ball, f, res, params, drawTime, lotteryType, no, 2);
                }else if (params.dxcnt[ball]>params.mVal[f]){
                    dxlost(ball, f, params, drawTime);

                    dxbet(ball, f, res, params, drawTime, lotteryType, no, 2);
                }
            }else {     //跳
                if (params.dxcnt[ball]>=params.mVal[f]){
                    dxwin(ball, f, params);
                }
            }
        }
    }

    /**
     * 连跳
     * @param ball
     * @param f
     * @param res
     * @param flag
     * @param add
     * @param no
     * @param params
     * @param drawTime
     * @param lotteryType
     */
    private static void lianTiao(int ball, int f, String res, int flag, boolean add, String no, AllParams params,
                                 String drawTime, String lotteryType){
        if (flag==0) {   //单双
            if (add) {   //连
                if (params.dstiaocnt[ball]==params.mVal[f] && params.dscnt[ball]==2){
                    if (params.tiao[f]){
                        dslost(ball, f, params, drawTime);
                    }else if (params.geng[f]){
                        dswin(ball,f,params);
                    }
                }
            } else {     //跳
                if (params.dstiaocnt[ball]==params.mVal[f]-1){
                    dsbet(ball, f, res, params, drawTime, lotteryType, no, 1);
                }else if (params.dstiaocnt[ball]==params.mVal[f]){
                    if (params.tiao[f]){
                        dswin(ball,f,params);
                    }else if (params.geng[f]){
                        dslost(ball, f, params, drawTime);
                    }
                }
            }
        } else {     //大小
            if (add) {   //连
                if (params.dxtiaocnt[ball]==params.mVal[f] && params.dxcnt[ball]==2){
                    if (params.tiao[f]){
                        dxlost(ball, f, params, drawTime);
                    }else if (params.geng[f]){
                        dxwin(ball,f,params);
                    }
                }
            }else {     //跳
                if (params.dxtiaocnt[ball]==params.mVal[f]-1){
                    dxbet(ball, f, res, params, drawTime, lotteryType, no, 1);
                }else if (params.dxtiaocnt[ball]==params.mVal[f]){
                    if (params.tiao[f]){
                        dxwin(ball,f,params);
                    }else if (params.geng[f]){
                        dxlost(ball, f, params, drawTime);
                    }
                }
            }
        }
    }



    private static void dswin(int ball, int f, AllParams params){
        String outputStrs = "m"+f+" DS["+(ball+1)+"]*** win!";
        if (params.dslost[f][ball]>params.fancnt[f]-2) {
            params.msgLevel[0] += outputStrs+"; ";
        }
        params.dslost[f][ball] = 0;
        params.totalMoney[0] += params.dsmoney[f][ball]+params.dsmoney[f][ball]*0.985;
        params.fananMoney[f] += params.dsmoney[f][ball]+params.dsmoney[f][ball]*0.985;
        params.msgLevel[2] += outputStrs+"; ";
    }

    private static void dslost(int ball, int f, AllParams params, String drawTime){
        params.dslost[f][ball] ++;
        String outputStrs = "m"+f+" DS["+(ball+1)+"] lost="+params.dslost[f][ball];
        if (params.dslost[f][ball]>params.fancnt[f]-2){
            params.msgLevel[0] += outputStrs+"; ";
            if (params.dslost[f][ball]==params.fancnt[f]) {
                System.out.println(outputStrs+" 时间："+params.dsStartTime[f][ball]+"--"+drawTime);
                params.dslost[f][ball] = 0;
            }
        }
        params.msgLevel[2] += outputStrs+"; ";
    }

    private static void dsbet(int ball, int f, String res, AllParams params, String drawTime, String lotteryType, String no, int flag){
        if (flag==1)
            params.dsmoney[f][ball] = params.money[f] * (new Double(Math.pow(2,params.dslost[f][ball])).intValue());
        else if (flag==2)
            params.dsmoney[f][ball] = params.money[f] * (new Double(Math.pow(2,(params.dslost[f][ball]+1))).intValue()-1);
        if (params.dslost[f][ball]>2){
            int ran1 = params.r.nextInt(2);
            if (params.dslost[f][ball]==3)
                params.r_dsmoney[f][ball] = ran1;
            else
                params.r_dsmoney[f][ball] = 2*params.r_dsmoney[f][ball]+ran1;
            params.dsmoney[f][ball] += params.r_dsmoney[f][ball];
        }
        params.totalMoney[0] -= params.dsmoney[f][ball];
        params.fananMoney[f] -= params.dsmoney[f][ball];
        if (params.dslost[f][ball]==0) params.dsStartTime[f][ball] = drawTime;
        String content = "D";
        if (!params.isTest){
            if (params.tiao[f])
                content = res.equals("D")?"S":"D";
            else if (params.geng[f])
                content = res.equals("D")?"D":"S";
            CommonUtil.buildXiaJsonObj(params.xiaJson,no,"DS"+(ball+1),content,params.dsmoney[f][ball],lotteryType);
        }
        String outputStrs = "m"+f+" DS["+(ball+1)+"] lost="+params.dslost[f][ball]+","+content+" xia="+params.dsmoney[f][ball];
        if (params.dslost[f][ball]>params.fancnt[f]-2){
            params.msgLevel[0] += outputStrs+"; ";
            if (params.dslost[f][ball]==params.fancnt[f]) {
                System.out.println(outputStrs+" 时间："+params.dsStartTime[f][ball]+"--"+drawTime);
                params.dslost[f][ball] = 0;
            }
        }
        params.msgLevel[2] += outputStrs+"; ";
    }

    private static void dxwin(int ball, int f, AllParams params){
        String outputStrs = "m"+f+" DX["+(ball+1)+"]*** win!";
        if (params.dxlost[f][ball]>params.fancnt[f]-2) params.msgLevel[0] += outputStrs+"; ";
        params.msgLevel[2] += outputStrs+"; ";
        params.dxlost[f][ball] = 0;
        params.totalMoney[0] += params.dxmoney[f][ball]+params.dxmoney[f][ball]*0.985;
        params.fananMoney[f] += params.dxmoney[f][ball]+params.dxmoney[f][ball]*0.985;
    }

    private static void dxlost(int ball, int f, AllParams params, String drawTime){
        params.dxlost[f][ball] ++;
        String outputStrs = "m"+f+" DX["+(ball+1)+"] lost="+params.dxlost[f][ball];
        if (params.dxlost[f][ball]>params.fancnt[f]-2){
            params.msgLevel[0] += outputStrs+"; ";
            if (params.dxlost[f][ball]==params.fancnt[f]) {
                System.out.println(outputStrs+" 时间："+params.dxStartTime[f][ball]+"--"+drawTime);
                params.dxlost[f][ball] = 0;
            }
        }
        params.msgLevel[2] += outputStrs+"; ";
    }

    private static void dxbet(int ball, int f, String res, AllParams params, String drawTime, String lotteryType, String no, int flag){
        if (flag==1)
            params.dxmoney[f][ball] = params.money[f] * (new Double(Math.pow(2,params.dxlost[f][ball])).intValue());
        else if (flag==2)
            params.dxmoney[f][ball] = params.money[f] * (new Double(Math.pow(2,(params.dxlost[f][ball]+1))).intValue()-1);
        if (params.dxlost[f][ball]>2){
            int ran1 = params.r.nextInt(2);
            if (params.dxlost[f][ball]==3)
                params.r_dxmoney[f][ball] = ran1;
            else
                params.r_dxmoney[f][ball] = 2*params.r_dxmoney[f][ball]+ran1;
            params.dxmoney[f][ball] += params.r_dxmoney[f][ball];
        }

        params.totalMoney[0] -= params.dxmoney[f][ball];
        params.fananMoney[f] -= params.dxmoney[f][ball];
        if (params.dxlost[f][ball]==0) params.dxStartTime[f][ball] = drawTime;
        String content = "D";
        if (!params.isTest){
            if (params.tiao[f])
                content = res.equals("D")?"X":"D";
            else if (params.geng[f])
                content = res.equals("D")?"D":"X";
            CommonUtil.buildXiaJsonObj(params.xiaJson,no,"DX"+(ball+1),content,params.dxmoney[f][ball],lotteryType);
        }
        String outputStrs = "m"+f+" DX["+(ball+1)+"] lost="+params.dxlost[f][ball]+","+content+" xia="+params.dxmoney[f][ball];
        if (params.dxlost[f][ball]>params.fancnt[f]-2){
            params.msgLevel[0] += outputStrs+"; ";
            if (params.dxlost[f][ball]==params.fancnt[f]) {
                System.out.println(outputStrs+" 时间："+params.dxStartTime[f][ball]+"--"+drawTime);
                params.dxlost[f][ball] = 0;
            }
        }
        params.msgLevel[2] += outputStrs+"; ";
    }

    private static void fanKaiDeal(int ball, String oDS, String oDX, String DS, String DX, String no, AllParams params,
                                   String drawTime, String lotteryType) {
        if (oDX.equals("D")){
            if (oDX.equals(DX)){
                params.dacnt[ball] ++;
                if (params.o_fanan[0]) fananDaDeal(0,ball,no,params, drawTime, lotteryType);
            }else {
                if (params.o_fanan[0]) fananDaDeal(1,ball,no,params, drawTime, lotteryType);
                params.dacnt[ball] = 0;
                params.xcnt[ball] = 1;
            }
        }else {
            if (oDX.equals(DX)){
                params.xcnt[ball] ++;
                if (params.o_fanan[1]) fananXDeal(0,ball,no,params, drawTime, lotteryType);
            }else {
                if (params.o_fanan[1]) fananXDeal(1,ball,no,params, drawTime, lotteryType);
                params.xcnt[ball] = 0;
                params.dacnt[ball] = 1;
            }
        }
        if (oDS.equals("D")){
            if (oDS.equals(DS)){
                params.dcnt[ball] ++;
                if (params.o_fanan[2]) fananDDeal(0,ball,no,params, drawTime, lotteryType);
            }else {
                if (params.o_fanan[2]) fananDDeal(1,ball,no,params, drawTime, lotteryType);
                params.dcnt[ball] = 0;
                params.scnt[ball] = 1;
            }
        }else {
            if (oDS.equals(DS)){
                params.scnt[ball] ++;
                if (params.o_fanan[3]) fananSDeal(0,ball,no,params, drawTime, lotteryType);
            }else {
                if (params.o_fanan[3]) fananSDeal(1,ball,no,params, drawTime, lotteryType);
                params.scnt[ball] = 0;
                params.dcnt[ball] = 1;
            }
        }
    }

    private static void fananDaDeal(int flag, int ball, String no, AllParams params, String drawTime, String lotteryType) {
        if (flag==0){   //跟
            if (params.dacnt[ball]==params.o_mVal[0]){
                params.m_0dmoney[ball] = params.o_money[0] * (new Double(Math.pow(2,params.m_0dlost[ball])).intValue());
                if (params.m_0dlost[ball]>2){
                    int ran1 = params.r.nextInt(2);
                    if (params.m_0dlost[ball]==3)
                        params.r_o_money[0][ball] = ran1;
                    else
                        params.r_o_money[0][ball] = 2*params.r_o_money[0][ball]+ran1;
                    params.m_0dmoney[ball] += params.r_o_money[0][ball];
                }
                params.totalMoney[0] -= params.m_0dmoney[ball];
                params.o_fananMoney[0] -= params.m_0dmoney[ball];
                if (params.m_0dlost[ball]==0) params.o_dsStartTime[0][ball] = drawTime;
                if (!params.isTest){
                    CommonUtil.buildXiaJsonObj(params.xiaJson,no,"DX"+(ball+1),"X",params.m_0dmoney[ball],lotteryType);
                }
                String outputStrs = "m_0 D["+(ball+1)+"] lost="+params.m_0dlost[ball]+",X xia="+params.m_0dmoney[ball];
                if (params.m_0dlost[ball]>params.o_fancnt[0]-2){
                    params.msgLevel[0] += outputStrs+"; ";
                }
                params.msgLevel[2] += outputStrs+"; ";
            }else if (params.dacnt[ball]==params.o_mVal[0]+1){
                params.m_0dlost[ball] ++;
                String outputStrs = "m_0 D["+(ball+1)+"] lost="+params.m_0dlost[ball];
                if (params.m_0dlost[ball]>params.o_fancnt[0]-2){
                    params.msgLevel[0] += outputStrs+"; ";
                    if (params.m_0dlost[ball]==params.o_fancnt[0]) {
                        System.out.println(outputStrs+" 时间："+params.o_dsStartTime[0][ball]+"--"+drawTime);
                        params.m_0dlost[ball] = 0;
                    }
                }
                params.msgLevel[2] += outputStrs+"; ";
            }
        }else { //跳
            if (params.dacnt[ball]==params.o_mVal[0]){
                String outputStrs = "m_0 D["+(ball+1)+"]*** win!";
                if (params.m_0dlost[ball]>params.o_fancnt[0]-2) params.msgLevel[0] += outputStrs+"; ";
                params.msgLevel[2] += outputStrs+"; ";
                params.m_0dlost[ball] = 0;
                params.totalMoney[0] += params.m_0dmoney[ball]+params.m_0dmoney[ball]*0.985;
                params.o_fananMoney[0] += params.m_0dmoney[ball]+params.m_0dmoney[ball]*0.985;
            }
        }
    }

    private static void fananXDeal(int flag, int ball, String no, AllParams params, String drawTime, String lotteryType) {
        if (flag==0) {   //跟
            if (params.xcnt[ball]==params.o_mVal[1]){
                params.m_1xmoney[ball] = params.o_money[1] * (new Double(Math.pow(2,params.m_1xlost[ball])).intValue());
                if (params.m_1xlost[ball]>2){
                    int ran1 = params.r.nextInt(2);
                    if (params.m_1xlost[ball]==3)
                        params.r_o_money[1][ball] = ran1;
                    else
                        params.r_o_money[1][ball] = 2*params.r_o_money[1][ball]+ran1;
                    params.m_1xmoney[ball] += params.r_o_money[1][ball];
                }
                params.totalMoney[0] -= params.m_1xmoney[ball];
                params.o_fananMoney[1] -= params.m_1xmoney[ball];
                if (params.m_1xlost[ball]==0) params.o_dsStartTime[1][ball] = drawTime;
                if (!params.isTest){
                    CommonUtil.buildXiaJsonObj(params.xiaJson,no,"DX"+(ball+1),"D",params.m_1xmoney[ball],lotteryType);
                }
                String outputStrs = "m_1 X["+(ball+1)+"] lost="+params.m_1xlost[ball]+",D xia="+params.m_1xmoney[ball];
                if (params.m_1xlost[ball]>params.o_fancnt[1]-2){
                    params.msgLevel[0] += outputStrs+"; ";
                }
                params.msgLevel[2] += outputStrs+"; ";
            }else if (params.xcnt[ball]==params.o_mVal[1]+1){
                params.m_1xlost[ball] ++;
                String outputStrs = "m_1 X["+(ball+1)+"] lost="+params.m_1xlost[ball];
                if (params.m_1xlost[ball]>params.o_fancnt[1]-2){
                    params.msgLevel[0] += outputStrs+"; ";
                    if (params.m_1xlost[ball]==params.o_fancnt[1]) {
                        System.out.println(outputStrs+" 时间："+params.o_dsStartTime[1][ball]+"--"+drawTime);
                        params.m_1xlost[ball] = 0;
                    }
                }
                params.msgLevel[2] += outputStrs+"; ";
            }
        }else { //跳
            if (params.xcnt[ball]==params.o_mVal[1]){
                String outputStrs = "m_1 X["+(ball+1)+"]*** win!";
                if (params.m_1xlost[ball]>params.o_fancnt[1]-2) params.msgLevel[0] += outputStrs+"; ";
                params.msgLevel[2] += outputStrs+"; ";
                params.m_1xlost[ball] = 0;
                params.totalMoney[0] += params.m_1xmoney[ball]+params.m_1xmoney[ball]*0.985;
                params.o_fananMoney[1] += params.m_1xmoney[ball]+params.m_1xmoney[ball]*0.985;
            }
        }
    }

    private static void fananDDeal(int flag, int ball, String no, AllParams params, String drawTime, String lotteryType) {
        if (flag==0){   //跟
            if (params.dcnt[ball]==params.o_mVal[2]){
                params.m_2dmoney[ball] = params.o_money[2] * (new Double(Math.pow(2,params.m_2dlost[ball])).intValue());
                if (params.m_2dlost[ball]>2){
                    int ran1 = params.r.nextInt(2);
                    if (params.m_2dlost[ball]==3)
                        params.r_o_money[2][ball] = ran1;
                    else
                        params.r_o_money[2][ball] = 2*params.r_o_money[2][ball]+ran1;
                    params.m_2dmoney[ball] += params.r_o_money[2][ball];
                }
                params.totalMoney[0] -= params.m_2dmoney[ball];
                params.o_fananMoney[2] -= params.m_2dmoney[ball];
                if (params.m_2dlost[ball]==0) params.o_dsStartTime[2][ball] = drawTime;
                if (!params.isTest){
                    CommonUtil.buildXiaJsonObj(params.xiaJson,no,"DS"+(ball+1),"S",params.m_2dmoney[ball],lotteryType);
                }
                String outputStrs = "m_2 D["+(ball+1)+"] lost="+params.m_1xlost[ball]+",S xia="+params.m_2dmoney[ball];
                if (params.m_2dlost[ball]>params.o_fancnt[2]-2){
                    params.msgLevel[0] += outputStrs+"; ";
                }
                params.msgLevel[2] += outputStrs+"; ";
            }else if (params.dcnt[ball]==params.o_mVal[2]+1){
                params.m_2dlost[ball] ++;
                String outputStrs = "m_2 D["+(ball+1)+"] lost="+params.m_2dlost[ball];
                if (params.m_2dlost[ball]>params.o_fancnt[2]-2){
                    params.msgLevel[0] += outputStrs+"; ";
                    if (params.m_2dlost[ball]==params.o_fancnt[2]) {
                        System.out.println(outputStrs+" 时间："+params.o_dsStartTime[2][ball]+"--"+drawTime);
                        params.m_2dlost[ball] = 0;
                    }
                }
                params.msgLevel[2] += outputStrs+"; ";
            }
        }else { //跳
            if (params.dcnt[ball]==params.o_mVal[2]){
                String outputStrs = "m_2 D["+(ball+1)+"]*** win!";
                if (params.m_2dlost[ball]>params.o_fancnt[2]-2) params.msgLevel[0] += outputStrs+"; ";
                params.msgLevel[2] += outputStrs+"; ";
                params.m_2dlost[ball] = 0;
                params.totalMoney[0] += params.m_2dmoney[ball]+params.m_2dmoney[ball]*0.985;
                params.o_fananMoney[2] += params.m_2dmoney[ball]+params.m_2dmoney[ball]*0.985;
            }
        }
    }

    private static void fananSDeal(int flag, int ball, String no, AllParams params, String drawTime, String lotteryType) {
        if (flag==0) {   //跟
            if (params.scnt[ball]==params.o_mVal[3]){
                params.m_3smoney[ball] = params.o_money[3] * (new Double(Math.pow(2,params.m_3slost[ball])).intValue());
                if (params.m_3slost[ball]>2){
                    int ran1 = params.r.nextInt(2);
                    if (params.m_3slost[ball]==3)
                        params.r_o_money[3][ball] = ran1;
                    else
                        params.r_o_money[3][ball] = 2*params.r_o_money[3][ball]+ran1;
                    params.m_3smoney[ball] += params.r_o_money[3][ball];
                }
                params.totalMoney[0] -= params.m_3smoney[ball];
                params.o_fananMoney[3] -= params.m_3smoney[ball];
                if (params.m_3slost[ball]==0) params.o_dsStartTime[3][ball] = drawTime;
                if (!params.isTest){
                    CommonUtil.buildXiaJsonObj(params.xiaJson,no,"DS"+(ball+1),"D",params.m_3smoney[ball],lotteryType);
                }
                String outputStrs = "m_3 S["+(ball+1)+"] lost="+params.m_3slost[ball]+",D xia="+params.m_3smoney[ball];
                if (params.m_3slost[ball]>params.o_fancnt[3]-2){
                    params.msgLevel[0] += outputStrs+"; ";
                }
                params.msgLevel[2] += outputStrs+"; ";
            }else if (params.scnt[ball]==params.o_mVal[3]+1){
                params.m_3slost[ball] ++;
                String outputStrs = "m_3 S["+(ball+1)+"] lost="+params.m_3slost[ball];
                if (params.m_3slost[ball]>params.o_fancnt[3]-2){
                    params.msgLevel[0] += outputStrs+"; ";
                    if (params.m_3slost[ball]==params.o_fancnt[2]) {
                        System.out.println(outputStrs+" 时间："+params.o_dsStartTime[3][ball]+"--"+drawTime);
                        params.m_3slost[ball] = 0;
                    }
                }
                params.msgLevel[2] += outputStrs+"; ";
            }
        }else { //跳
            if (params.scnt[ball]==params.o_mVal[3]){
                String outputStrs = "m_3 S["+(ball+1)+"]*** win!";
                if (params.m_3slost[ball]>params.o_fancnt[3]-2) params.msgLevel[0] += outputStrs+"; ";
                params.msgLevel[2] += outputStrs+"; ";
                params.m_3slost[ball] = 0;
                params.totalMoney[0] += params.m_3smoney[ball]+params.m_3smoney[ball]*0.985;
                params.o_fananMoney[3] += params.m_3smoney[ball]+params.m_3smoney[ball]*0.985;
            }
        }
    }

    private static void initReplayData(AllParams params, String drawTime){
        for (int f=0;f<7;f++){
            int f1 = f==6?10:f;
            if (params.fanan[f1] && params.isReplay[f]){
                for (int i = 0; i < params.hCnt; i++) {
                    if (params.hours[f][i]!=-1){
                        long time = Long.parseLong(drawTime);
                        Date dtime = new Date(time);
                        if ((dtime.getHours()==params.hours[f][i] || dtime.getHours()==7) && dtime.getMinutes()==0){
                            System.out.println("定点："+params.hours[f][i]+"点重置方案"+f);
                            for (int b = 0; b < params.bCnt; b++) {
                                if (params.dslost[f1][b]>0 && params.dslost[f1][b]<params.replayCnt[f]) {
                                    System.out.println("包含单双["+b+"] lost="+params.dslost[f1][b]);
                                    params.dslost[f1][b] = 0;
                                }
                                if (params.dxlost[f1][b]>0 && params.dxlost[f1][b]<params.replayCnt[f]) {
                                    System.out.println("包含大小["+b+"] lost="+params.dxlost[f1][b]);
                                    params.dxlost[f1][b] = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
