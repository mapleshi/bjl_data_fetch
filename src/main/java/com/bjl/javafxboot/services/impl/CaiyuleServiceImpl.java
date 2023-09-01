package com.bjl.javafxboot.services.impl;

import com.bjl.javafxboot.dao.BjlDao;
import com.bjl.javafxboot.dao.FeifanDao;
import com.bjl.javafxboot.entity.Bjl;
import com.bjl.javafxboot.entity.FeifanEntity;
import com.bjl.javafxboot.services.CaiyuleService;
import com.bjl.javafxboot.util.CommonUtil;
import javafx.scene.control.TextArea;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
@EnableAsync
public class CaiyuleServiceImpl implements CaiyuleService {

    @Resource
    private BjlDao bjlDao;

    @Resource
    private FeifanDao feifanDao;


    /**
     * @param betModel    表示是否是翻倍压，还是平压
     * @param model1
     * @param model2
     * @param model3
     * @param model4
     * @param mcnt1
     * @param mcnt2
     * @param mcnt3
     * @param mcnt4
     * @param suijiStart1
     * @param suijiEnd1
     * @param suijiStart2
     * @param suijiEnd2
     * @param suijiStart3
     * @param suijiEnd3
     * @param suijiStart4
     * @param suijiEnd4
     * @param betModel1
     * @param betModel2
     * @param betModel3
     * @param betModel4
     * @param baocnt  爆仓的局数
     * @param time1
     * @param time2
     * @param tab
     * @param console
     * @param tabStart
     * @param tabEnd
     * @param leiCheck
     * @param leiLostCnt
     * @param leiWinCnt
     */
    @Override
    @Async
    public void smartModel(boolean model1, boolean model2, boolean model3, boolean model4, int mcnt1, int mcnt2, int mcnt3, int mcnt4,
                           int suijiStart1, int suijiEnd1, int suijiStart2, int suijiEnd2, int suijiStart3, int suijiEnd3, int suijiStart4, int suijiEnd4,
                           int betModel1, int betModel2, int betModel3, int betModel4, int baocnt, Timestamp time1, Timestamp time2, String tab, TextArea console,
                           int tabStart, int tabEnd, boolean leiCheck, int leiLostCnt, int leiWinCnt) {
        float[] totalMoney1 = {0, 0}, totalMoney2 = {0, 0}, totalMoney3 = {0, 0}, totalMoney4 = {0, 0};
        for (int i = tabStart-1; i < tabEnd; i++) {
            if (model1)
                m1deal(console, mcnt1, time1, time2, tab+(i+1), baocnt, betModel1, suijiStart1, suijiEnd1, totalMoney1, leiCheck, leiLostCnt, leiWinCnt);
            if (model2)
                m2deal(console, mcnt2, time1, time2, tab+(i+1), baocnt, betModel2, suijiStart2, suijiEnd2, totalMoney2, leiCheck, leiLostCnt, leiWinCnt);
            if (model3)
                m3deal(console, mcnt3, time1, time2, tab+(i+1), baocnt, betModel3, suijiStart3, suijiEnd3, totalMoney3, leiCheck, leiLostCnt, leiWinCnt);
            if (model4)
                m4deal(console, mcnt4, time1, time2, tab+(i+1), baocnt, betModel4, suijiStart4, suijiEnd4, totalMoney4, leiCheck, leiLostCnt, leiWinCnt);
        }
        if (model1)
            CommonUtil.updateTextArea(console, "模式一所有桌赢利："+totalMoney1[0]+"，总下注金额为："+totalMoney1[1]+"，预计返水金额为："+totalMoney1[1]*0.01f+"\n");
        if (model2)
            CommonUtil.updateTextArea(console, "模式二所有桌赢利："+totalMoney2[0]+"，总下注金额为："+totalMoney2[1]+"，预计返水金额为："+totalMoney2[1]*0.01f+"\n");
        if (model3)
            CommonUtil.updateTextArea(console, "模式三所有桌赢利："+totalMoney3[0]+"，总下注金额为："+totalMoney3[1]+"，预计返水金额为："+totalMoney3[1]*0.01f+"\n");
        if (model4)
            CommonUtil.updateTextArea(console, "模式四所有桌赢利："+totalMoney4[0]+"，总下注金额为："+totalMoney4[1]+"，预计返水金额为："+totalMoney4[1]*0.01f+"\n");
        CommonUtil.updateTextArea(console, "所有模式所有桌总赢利："+(totalMoney1[0]+totalMoney2[0]+totalMoney3[0]+totalMoney4[0])
                +"，总下注金额为："+(totalMoney1[1]+totalMoney2[1]+totalMoney3[1]+totalMoney4[1])
                +"，预计返水金额为："+((totalMoney1[1]+totalMoney2[1]+totalMoney3[1]+totalMoney4[1])*0.01f)+"\n");
    }

    @Override
    public void saveBjlAll(List<Bjl> list) {
        bjlDao.saveAll(list);
    }

    /**
     * 本方法为排跳模式
     * 当两方连续胜大于1个时开始下注；如闲2庄2连胜时开始压闲。
     */
    private void m2deal(TextArea console, int cnt, Timestamp time1, Timestamp time2, String tab, int baocnt, int betModel, int suijiM1, int suijiM2,
                        float[] totalMoney, boolean leiCheck, int leiLostCnt, int leiWinCnt) {
        float winMoney = 0, maxLostMoney = 0, totalBetMoney = 0;
        int lotCnt = 0, winCnt = 0, baseMoney;
        int lianCnt = 0;
        List<Bjl> list = bjlDao.findByTabAndCreateTimeBetweenOrderByCreateTime(tab, time1, time2);
        for (int j = 0; j < list.size(); j++) {
            Bjl e = list.get(j);
            if (e.getCnt()>1){
                lianCnt ++;
            }else {
                if (betModel == 1) {
                    // 平投
                    if (lianCnt==cnt){
                        baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                        winMoney += e.getResult().equals("z")?baseMoney:baseMoney*0.95f;
                        totalBetMoney += baseMoney;
                    }else if (lianCnt > cnt){
                        baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                        winMoney -= baseMoney;
                        if (winMoney < maxLostMoney) {
                            maxLostMoney = winMoney;
                        }
                        totalBetMoney += baseMoney;
                    }
                } else if (betModel == 2) {
                    // 胜进
                    if (lianCnt == cnt) {
                        int xiaMoney = (int) (10 * Math.pow(2,winCnt));
                        winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                        winCnt ++;
                        if (winCnt == baocnt) {
                            winCnt = 0;
                        }
                    } else if (lianCnt > cnt) {
                        winMoney -= 10 * Math.pow(2,winCnt);
                        winCnt = 0;
                    }
                } else {
                    // 倍投
                    if (lianCnt==cnt){
                        int xiaMoney = (int) (10 * Math.pow(2,lotCnt));
                        winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                        lotCnt = 0;
                    }else if (lianCnt > cnt){
                        lotCnt ++;
                        if (lotCnt > baocnt){
                            winMoney -= 5110;
                            lotCnt = 0;
                            System.out.println("第"+tab+"爆了！时间："+e.getCreateTime());
                        }
                    }
                }
                lianCnt = 0;
            }
        }
        CommonUtil.updateTextArea(console, "第"+tab+"总投注金额："+totalBetMoney+"，赢利："+winMoney+"，最小负金额为："+maxLostMoney+"\n");
        totalMoney[0] += winMoney;
        totalMoney[1] += totalBetMoney;
    }

    /**
     * 本方法为个跳模式
     * 当某方连续胜N个时开始下注；如闲2连胜时开始压庄。
     */
    private void m1deal(TextArea console, int cnt, Timestamp time1, Timestamp time2, String tab, int baocnt, int betModel, int suijiM1, int suijiM2,
                        float[] totalMoney, boolean leiCheck, int leiLostCnt, int leiWinCnt) {
        float winMoney = 0, maxLostMoney = 0, totalBetMoney = 0;
        int lotCnt = 0, winCnt = 0, baseMoney, leijiCnt = 0;
        boolean fanBeiFlag = false;
        List<Bjl> list = bjlDao.findByTabAndCntGreaterThanAndCreateTimeBetweenOrderByCreateTime(tab, cnt-1, time1, time2);
        for (int j = 0; j < list.size(); j++) {
            Bjl e = list.get(j);
            int bcnt = e.getCnt();
            if (betModel == 1) {
                // 平投
                if (bcnt==cnt){
                    baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                    // 累计输N翻倍
                    if (leiCheck) {
                        if (fanBeiFlag)
                            baseMoney = CommonUtil.getRandomNum(suijiM1*2, suijiM2*2);
                    }
                    winMoney += e.getResult().equals("z")?baseMoney:baseMoney*0.95f;
                    totalBetMoney += baseMoney;
                    leijiCnt ++;
                    // 累计赢N回归
                    if (leiCheck) {
                        if (leijiCnt == leiWinCnt) {
                            leijiCnt = 0;
                            fanBeiFlag = false;
                        }
                    }
                }else if (bcnt>cnt){
                    baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                    // 累计输N翻倍
                    if (leiCheck) {
                        if (fanBeiFlag)
                            baseMoney = CommonUtil.getRandomNum(suijiM1*2, suijiM2*2);
                    }
                    winMoney -= baseMoney;
                    if (winMoney < maxLostMoney) {
                        maxLostMoney = winMoney;
                    }
                    totalBetMoney += baseMoney;
                    leijiCnt --;
                    if (leiCheck) {
                        if (!fanBeiFlag && leijiCnt == -leiLostCnt) {
                            System.out.println("leijiCnt=="+leijiCnt);
                            leijiCnt = 0;
                            fanBeiFlag = true;
                        }
                    }
                }
            } else if (betModel == 2) {
                // 胜进
                if (bcnt == cnt) {
                    baseMoney = (int) (10 * Math.pow(2,winCnt));
                    winMoney += e.getResult().equals("z")?10:(10-baseMoney*0.05);
                    winCnt ++;
                    if (winCnt == baocnt) {
                        winCnt = 0;
                    }
                    totalBetMoney += baseMoney;
                } else if (bcnt > cnt) {
                    baseMoney = (int) (10 * Math.pow(2,winCnt));
                    winMoney -= baseMoney;
                    winCnt = 0;
                    totalBetMoney += baseMoney;
                }
            }
            else if (betModel == 3) {
                // 退二进一
                if (bcnt == cnt) {
                    winMoney += e.getResult().equals("z")?10:(10-CommonUtil.tuierjinyiMoney[lotCnt]*0.05);
                    lotCnt -= 2;
                    if (lotCnt < 0) lotCnt = 0;
                } else if (bcnt > cnt) {
                    winMoney -= CommonUtil.tuierjinyiMoney[lotCnt];
                    lotCnt ++;
                    if (lotCnt == baocnt) lotCnt = 0;
                }
            } else {
                // 倍投
                if (bcnt==cnt){
                    int xiaMoney = (int) (10 * Math.pow(2,lotCnt));
                    winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                    lotCnt = 0;
                }else if (bcnt>cnt){
                    lotCnt ++;
                    if (lotCnt==baocnt){
                        winMoney -= 5110;
                        lotCnt = 0;
                        System.out.println("第"+tab+"爆了！时间："+e.getCreateTime());
                    }
                }
            }
        }
        CommonUtil.updateTextArea(console, "第"+tab+"总投注金额："+totalBetMoney+"，赢利："+winMoney+"，最小负金额为："+maxLostMoney+"\n");
        totalMoney[0] += winMoney;
        totalMoney[1] += totalBetMoney;
    }

    /**
     * 本方法为排跳模式 模式三（1排跳）：10～20
     * 当两方连续胜大于1个时开始下注；如闲2庄2连胜时开始压闲。
     */
    private void m3deal(TextArea console, int cnt, Timestamp time1, Timestamp time2, String tab, int baocnt, int betModel, int suijiM1, int suijiM2,
                        float[] totalMoney, boolean leiCheck, int leiLostCnt, int leiWinCnt) {
        float winMoney = 0, maxLostMoney = 0, totalBetMoney = 0;
        int lotCnt = 0, winCnt = 0, baseMoney;
        int lianCnt = 0;
        List<Bjl> list = bjlDao.findByTabAndCreateTimeBetweenOrderByCreateTime(tab, time1, time2);
        for (int j = 0; j < list.size(); j++) {
            Bjl e = list.get(j);
            if (e.getCnt()>1){
                lianCnt ++;
            }else {
                if (betModel == 1) {
                    // 平投
                    if (lianCnt==cnt){
                        baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                        winMoney += e.getResult().equals("z")?baseMoney:baseMoney*0.95f;
                        totalBetMoney += baseMoney;
                    }else if (lianCnt > cnt){
                        baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                        winMoney -= baseMoney;
                        if (winMoney < maxLostMoney) {
                            maxLostMoney = winMoney;
                        }
                        totalBetMoney += baseMoney;
                    }
                } else if (betModel == 2) {
                    // 胜进
                    if (lianCnt == cnt) {
                        int xiaMoney = (int) (10 * Math.pow(2,winCnt));
                        winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                        winCnt ++;
                        if (winCnt == baocnt) {
                            winCnt = 0;
                        }
                    } else if (lianCnt > cnt) {
                        winMoney -= 10 * Math.pow(2,winCnt);
                        winCnt = 0;
                    }
                } else {
                    // 倍投
                    if (lianCnt==cnt){
                        int xiaMoney = (int) (10 * Math.pow(2,lotCnt));
                        winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                        lotCnt = 0;
                    }else if (lianCnt > cnt){
                        lotCnt ++;
                        if (lotCnt > baocnt){
                            winMoney -= 5110;
                            lotCnt = 0;
                            System.out.println("第"+tab+"爆了！时间："+e.getCreateTime());
                        }
                    }
                }
                lianCnt = 0;
            }
        }
        CommonUtil.updateTextArea(console, "第"+tab+"总投注金额："+totalBetMoney+"，赢利："+winMoney+"，最小负金额为："+maxLostMoney+"\n");
        totalMoney[0] += winMoney;
        totalMoney[1] += totalBetMoney;
    }

    /**
     * 本方法为个跳模式 模式四（6个跳）：30～100
     * 当某方连续胜N个时开始下注；如闲2连胜时开始压庄。
     */
    private void m4deal(TextArea console, int cnt, Timestamp time1, Timestamp time2, String tab, int baocnt, int betModel, int suijiM1, int suijiM2,
                        float[] totalMoney, boolean leiCheck, int leiLostCnt, int leiWinCnt) {
        float winMoney = 0, maxLostMoney = 0, totalBetMoney = 0;
        int lotCnt = 0, winCnt = 0, baseMoney;
        List<Bjl> list = bjlDao.findByTabAndCntGreaterThanAndCreateTimeBetweenOrderByCreateTime(tab, cnt-1, time1, time2);
        for (int j = 0; j < list.size(); j++) {
            Bjl e = list.get(j);
            int bcnt = e.getCnt();
            if (betModel == 1) {
                // 平投
                if (bcnt==cnt){
                    baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                    winMoney += e.getResult().equals("z")?baseMoney:baseMoney*0.95f;
                    totalBetMoney += baseMoney;
                }else if (bcnt>cnt){
                    baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                    winMoney -= baseMoney;
                    if (winMoney < maxLostMoney) {
                        maxLostMoney = winMoney;
                    }
                    totalBetMoney += baseMoney;
                }
            } else if (betModel == 2) {
                // 胜进
                if (bcnt == cnt) {
                    int xiaMoney = (int) (10 * Math.pow(2,winCnt));
                    winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                    winCnt ++;
                    if (winCnt == baocnt) {
                        winCnt = 0;
                    }
                } else if (bcnt > cnt) {
                    winMoney -= 10 * Math.pow(2,winCnt);
                    winCnt = 0;
                }
            }
            else if (betModel == 3) {
                // 退二进一
                if (bcnt == cnt) {
                    winMoney += e.getResult().equals("z")?10:(10-CommonUtil.tuierjinyiMoney[lotCnt]*0.05);
                    lotCnt -= 2;
                    if (lotCnt < 0) lotCnt = 0;
                } else if (bcnt > cnt) {
                    winMoney -= CommonUtil.tuierjinyiMoney[lotCnt];
                    lotCnt ++;
                    if (lotCnt == baocnt) lotCnt = 0;
                }
            } else {
                // 倍投
                if (bcnt==cnt){
                    int xiaMoney = (int) (10 * Math.pow(2,lotCnt));
                    winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                    lotCnt = 0;
                }else if (bcnt>cnt){
                    lotCnt ++;
                    if (lotCnt==baocnt){
                        winMoney -= 5110;
                        lotCnt = 0;
                        System.out.println("第"+tab+"爆了！时间："+e.getCreateTime());
                    }
                }
            }
        }
        CommonUtil.updateTextArea(console, "第"+tab+"总投注金额："+totalBetMoney+"，赢利："+winMoney+"，最小负金额为："+maxLostMoney+"\n");
        totalMoney[0] += winMoney;
        totalMoney[1] += totalBetMoney;
    }


    private void m6deal(TextArea console, int cnt, Timestamp time1, Timestamp time2, String tab, int baocnt, int betModel, int suijiM1, int suijiM2, float[] totalMoney) {
        float winMoney = 0, maxLostMoney = 0, totalBetMoney = 0;
        int lotCnt = 0, lianCnt = 0, baseMoney;
        List<Bjl> list = bjlDao.findByTabAndCreateTimeBetweenOrderByCreateTime(tab, time1, time2);
        for (int j = 0; j < list.size(); j++) {
            Bjl e = list.get(j);
            if (e.getCnt()>1){
                lianCnt ++;
            }else {
                if (betModel == 1) {
                    // 平投
                    if (lianCnt > cnt) {
                        baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                        winMoney += e.getResult().equals("z")?baseMoney:baseMoney*0.95f;
                        totalBetMoney += baseMoney;
                    } else if (lianCnt == cnt) {
                        baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                        winMoney -= baseMoney;
                        if (winMoney < maxLostMoney) {
                            maxLostMoney = winMoney;
                        }
                        totalBetMoney += baseMoney;
                    }
                } else {
                    if (lianCnt==cnt){
                        int xiaMoney = (int) (10 * Math.pow(2,lotCnt));
                        winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                        lotCnt = 0;
                    }else if (lianCnt > cnt){
                        lotCnt ++;
                        if (lotCnt > baocnt){
                            winMoney -= 5110;
                            lotCnt = 0;
                            System.out.println("第"+tab+"爆了！时间："+e.getCreateTime());
                        }
                    }
                }
                lianCnt = 0;
            }
        }
        CommonUtil.updateTextArea(console, "第"+tab+"总投注金额："+totalBetMoney+"，赢利："+winMoney+"，最小负金额为："+maxLostMoney+"\n");
        totalMoney[0] += winMoney;
        totalMoney[1] += totalBetMoney;
    }

    private void m5deal(TextArea console, int cnt, Timestamp time1, Timestamp time2, String tab, int baocnt, int betModel, int suijiM1, int suijiM2, float[] totalMoney) {
        float winMoney = 0, maxLostMoney = 0, totalBetMoney = 0;
        int baseMoney;
        List<Bjl> list = bjlDao.findByTabAndCntGreaterThanAndCreateTimeBetweenOrderByCreateTime(tab, cnt-1, time1, time2);
        for (int j = 0; j < list.size(); j++) {
            Bjl e = list.get(j);
            int bcnt = e.getCnt();
            if (betModel == 1) {
                // 平投
                if (bcnt > cnt) {
                    baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                    winMoney += e.getResult().equals("z")?baseMoney:baseMoney*0.95f;
                    totalBetMoney += baseMoney;
                } else if (bcnt == cnt) {
                    baseMoney = CommonUtil.getRandomNum(suijiM1, suijiM2);
                    winMoney -= baseMoney;
                    if (winMoney < maxLostMoney) {
                        maxLostMoney = winMoney;
                    }
                    totalBetMoney += baseMoney;
                }
            } else {
                if (bcnt>=cnt){
                    int gcnt = cnt+baocnt;
                    if (bcnt<gcnt){
                        int xiaMoney = (int) (10 * Math.pow(2,bcnt-cnt));
                        winMoney += e.getResult().equals("z")?10:(10-xiaMoney*0.05);
                    } else {
                        winMoney -= ((int) (10 * Math.pow(2,baocnt)) * 2) - 10;
                        System.out.println("第"+tab+"爆了！时间："+e.getCreateTime());
                    }
                }
            }
        }
        CommonUtil.updateTextArea(console, "第"+tab+"总投注金额："+totalBetMoney+"，赢利："+winMoney+"，最小负金额为："+maxLostMoney+"\n");
        totalMoney[0] += winMoney;
        totalMoney[1] += totalBetMoney;
    }

    @Override
    public void smartModelForHeiLei(boolean model1, boolean model2, boolean model3, boolean model4, int mcnt1, int mcnt2, int mcnt3, int mcnt4,
                                    int suijiStart1, int suijiEnd1, int suijiStart2, int suijiEnd2, int suijiStart3, int suijiEnd3, int suijiStart4, int suijiEnd4,
                                    int betModel1, int betModel2, int betModel3, int betModel4, int baocnt, String sDate, String eDate,
                                    TextArea console, boolean leiCheck, int leiLostCnt, int leiWinCnt) {
        float[] totalMoney1 = {0, 0}, totalMoney2 = {0, 0}, totalMoney3 = {0, 0}, totalMoney4 = {0, 0};
        String[] dsResult = {"","","","",""}, dxResult = {"","","","",""};
        int[] dsCnt = {1,1,1,1,1}, dxCnt = {1,1,1,1,1};
        // 获取数据
        List<FeifanEntity> list = feifanDao.findByIssueBetweenOrderByIssue(sDate, eDate);
        for (int i = 0; i < list.size(); i++) {
            FeifanEntity entity = list.get(i);
            char[] chars = entity.getCode().toCharArray();
            for (int k = 0; k < chars.length; k++) {
                String code = String.valueOf(chars[k]);
                String ds = CommonUtil.getDS(code);
                String dx = CommonUtil.getDX(code);
                dealModelMoney(mcnt1, totalMoney1, dsResult, dsCnt, k, ds);
                if (leiCheck)
                    dealModelMoney(mcnt1, totalMoney1, dxResult, dxCnt, k, dx);
            }
        }
        CommonUtil.updateTextArea(console, "所有球总赢利："+(totalMoney1[0])
                +"，总下注金额为："+(totalMoney1[1])
                +"，预计返水金额为："+((totalMoney1[1])*0.01f)+"\n");
    }

    private void dealModelMoney(int mcnt1, float[] totalMoney1, String[] result, int[] cnt, int i, String ds) {
        if (result[i].equals(ds)) {
            cnt[i]++;
            if (cnt[i] == mcnt1+1) {  //  lost
                totalMoney1[0] -= 10;
                totalMoney1[1] += 10;
            }
        } else {
            if (cnt[i] == mcnt1) {  //  win
                totalMoney1[0] += 9.7f;
                totalMoney1[1] += 10;
            }
            result[i] = ds;
            cnt[i] = 1;
        }
    }
}
