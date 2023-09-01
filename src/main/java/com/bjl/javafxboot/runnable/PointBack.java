package com.bjl.javafxboot.runnable;

public class PointBack {
    //取点颜色
//    if (newColor1T != preColor1[no]) { //已经变了
//        //判断是否是洗牌后第一局
//        if (x1[no] == 0 && y1[no] == 0) {
//            y1[no] = 1;
//            x2[no] = 1;
//            String newResult = getNewResultStr(newColor1T);
//            // 表示没有跳
//            if (newResult.equals(result[no])) {
//                cnt[no]++;
//                //follow(cnt[no]);
//            } else {    // 跳了，记录数据
//                Bjl obj = new Bjl(table + (no + 1) ,result[no], cnt[no]);
//                list.add(obj);
//                //jump(cnt[no])
//                // 记录连排,连排结束
//                cnt[no] = 1;
//                result[no] = getNewResultStr(newColor2T);
//            }
//            //确定好监控点后，获取最新的监控点颜色
//            preColor1[no] = KCOLORRGB;
//            preColor2[no] = KCOLORRGB;
//        } else {
//            // 正常桌图
//            if (preColor1[no] == KCOLORRGB) {
//                cnt[no]++;
//                // 确定下一个监控点,纵向往下一格找空白点，如果不是空白点，则横向找空白点
//                colorT = pickColor(initX[no] + x1[no] * baseX, initY[no] + y1[no] * baseX + baseX);
//                if (colorT == KCOLORRGB) {    //纵向空
//                    y1[no]++;
//                    //确定好监控点后，获取最新的监控点颜色, x1,x2颜色不变
//                    //color1[no] = KCOLORRGB;
//                    //color2[no] = KCOLORRGB;
//                } else {    //找横向
//                    colorT = pickColor(initX[no] + x1[no] * baseX + baseX, initY[no] + y1[no] * baseX);
//                    if (colorT == KCOLORRGB) {
//                        x1[no]++;
//                        //确定好监控点后，获取最新的监控点颜色, x1,x2颜色不变
//                        //color1[no] = KCOLORRGB;
//                        //color2[no] = KCOLORRGB;
//                    } else {    //横向非空，则表示满格了, 16 6个
//                        x1[no] --;
//                        y1[no] = 0;
//                        //确定好监控点后，获取最新的监控点颜色, x2颜色未变
//                        preColor1[no] = result[no].equals("z")?XCOLORRGB:ZCOLORRGB;
//                    }
//                }
//            } else {    // 满格局
//                //转弯x1[no]满格,且x2[no]未满格
//                if (preColor2[no] == KCOLORRGB) {
//                    x1[no]--;
//                    y1[no] = 0;
//                    x2[no]--;
//                    cnt[no]++;
//                    //确定好监控点后，获取最新的监控点颜色，此处颜色不变
//                    //color1[no] = color1[no];
//                    //color2[no] = KCOLORRGB;
//                    //follow(cnt[no]);
//                } else {    // 转弯x1[no]满格，且x2[no]满格
//                    //转弯
//                    if (newColor2T == KCOLORRGB) {
//                        // 满格了，x1和x2不变
//                        //x1[no] --; x2[no] --;
//                        cnt[no]++;
//                        //确定好监控点后，获取最新的监控点颜色
//                        preColor1[no] = result[no].equals("z")?ZCOLORRGB:XCOLORRGB;
//                        preColor2[no] = KCOLORRGB;
//                        //follow(cnt[no]);
//                    } else {    //跳了
//                        //记录
//                        Bjl obj = new Bjl(table + (no + 1) ,result[no], cnt[no]);
//                        list.add(obj);
//                        result[no] = getNewResultStr(newColor2T);
//                        x1[no] ++;
//                        y1[no] = 1;
//                        preColor1[no] = KCOLORRGB;
//                        colorT = pickColor(initX[no] + x1[no] * baseX, initY[no] + baseX);
//                        if (colorT != KCOLORRGB) {
//                            x1[no] --;
//                            y1[no] = 0;
//                            preColor1[no] = result[no].equals("z")?ZCOLORRGB:XCOLORRGB;
//                        }
//                        preColor2[no] = result[no].equals("z")?XCOLORRGB:ZCOLORRGB;
//                        // 记录连排,连排结束
//                        cnt[no] = 1;
//                        //jump(cnt[no]);
//                    }
//                }
//            }
//        }
//        CommonUtil.updateTextArea(console, "第" + table + (no + 1) + "桌：" + result[no] + cnt[no] + ",第1点为" + x1[no] + "," + y1[no] + ",第2点为" + x2[no] + "," + y2[no] + "\n");
//    } else if (newColor2T != preColor2[no]) {
//        //跳了,先记录数据
//        //记录
//        Bjl obj = new Bjl(table + (no + 1) ,result[no], cnt[no]);
//        list.add(obj);
//        result[no] = getNewResultStr(newColor2T);
//        x1[no] = x2[no];
//        y1[no] = 1;
//        x2[no]++;
//        //确定好监控点后，获取最新的监控点颜色
//        preColor1[no] = KCOLORRGB;
//        preColor2[no] = KCOLORRGB;
//        // 满格，将颜色设正
//        if (x2[no] > 16) {
//            x2[no] = 16;
//            preColor2[no] = result[no].equals("z")?ZCOLORRGB:XCOLORRGB;
//        }
//        // 跳了就满格的情况非常少。几乎没有，可以不考虑
//        colorT = pickColor(initX[no] + x1[no] * baseX, initY[no] + baseX);
//        if (colorT != KCOLORRGB) {
//            // 满格，x1颜色跟结果一致，x2将颜色设反
//            if (x1[no] == 16) {
//                x1[no] --;
//                y1[no] = 0;
//                preColor1[no] = result[no].equals("z")?XCOLORRGB:ZCOLORRGB;
//                preColor2[no] = result[no].equals("z")?ZCOLORRGB:XCOLORRGB;
//            } else {    // 未满格而第2格就非空了，这情况非常少。几乎没有
//                x1[no] = x2[no];
//                y1[no] = 0;
//            }
//        }
//        // 记录连排,连排结束
//        cnt[no] = 1;
//        //jump(cnt[no]);
//        CommonUtil.updateTextArea(console, "第" + table + (no + 1) + "桌：" + result[no] + cnt[no] + ",第1点为" + x1[no] + "," + y1[no] + ",第2点为" + x2[no] + "," + y2[no] + "\n");
//    }

    //    private void follow(int num){
//        String msgLevel0 = "",msgLevel1 = "",msgLevel2 = "";
//        if (num==2){
//            lianCnt++;
//        }
//        if (m1){
//            if (num==m1cnt+1){  //输
//                m1LostCnt++;
//                msgLevel2 = "m1 "+table+(no+1)+" Tab lost="+m1LostCnt;
//                if (m1LostCnt==m1BaoCnt){
//                    int baoMoney = 10 * 2^m1BaoCnt - 10;
//                    m1LostCnt = 0;
//                    msgLevel0 = "m1 "+table+(no+1)+" Tab Bao!";
//                    m1Money = m1Money.subtract(new BigDecimal(baoMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    noMoney = noMoney.subtract(new BigDecimal(baoMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                }
//            }
//        }
//        if (m2){
//            if (num==2 && lianCnt==(m2cnt+1)){
//                m2LostCnt++;
//                if (msgLevel2.equals(""))
//                    msgLevel2 = "m2 "+table+(no+1)+" Tab lost="+m2LostCnt;
//                else
//                    msgLevel2 += ";m2 "+table+(no+1)+" Tab lost="+m2LostCnt;
//                if (m2LostCnt==m2BaoCnt){
//                    int baoMoney = 10 * 2^m2BaoCnt - 10;
//                    m2LostCnt = 0;
//                    if (msgLevel0.equals(""))
//                        msgLevel0 = "m2 "+table+(no+1)+" Tab Bao!";
//                    else
//                        msgLevel0 += ";m2 "+table+(no+1)+" Tab Bao!";
//                    m2Money = m2Money.subtract(new BigDecimal(baoMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    noMoney = noMoney.subtract(new BigDecimal(baoMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                }
//            }
//        }
//        if (m3){
//            if (num>m3cnt){  //输
//                m3LostCnt++;
//                if (msgLevel2.equals(""))
//                    msgLevel2 = "m3 "+table+(no+1)+" Tab lost="+m3LostCnt;
//                else
//                    msgLevel2 += ";m3 "+table+(no+1)+" Tab lost="+m3LostCnt;
//                if (m3LostCnt==m3BaoCnt){
//                    int baoMoney = 10 * 2^m3BaoCnt - 10;
//                    m3LostCnt = 0;
//                    if (msgLevel0.equals(""))
//                        msgLevel0 = "m3 "+table+(no+1)+" Tab Bao!";
//                    else
//                        msgLevel0 += ";m3 "+table+(no+1)+" Tab Bao!";
//                    m3Money = m3Money.subtract(new BigDecimal(baoMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    noMoney = noMoney.subtract(new BigDecimal(baoMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                }
//            }
//        }
//        if (m4){
//            if (num==2 && lianCnt>m4cnt){  //输
//                m4LostCnt++;
//                if (msgLevel2.equals(""))
//                    msgLevel2 = "m4 "+table+(no+1)+" Tab lost="+m4LostCnt;
//                else
//                    msgLevel2 += ";m4 "+table+(no+1)+" Tab lost="+m4LostCnt;
//                if (m4LostCnt==m4BaoCnt){
//                    int baoMoney = 10 * 2^m4BaoCnt - 10;
//                    m4LostCnt = 0;
//                    if (msgLevel0.equals(""))
//                        msgLevel0 = "m4 "+table+(no+1)+" Tab Bao!";
//                    else
//                        msgLevel0 += ";m4 "+table+(no+1)+" Tab Bao!";
//                    m4Money = m4Money.subtract(new BigDecimal(baoMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    noMoney = noMoney.subtract(new BigDecimal(baoMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                }
//            }
//        }
//        service.saveCaiyuleXiaMsg("test",msgLevel0,msgLevel1,msgLevel2);
//    }

    //    private void jump(int num){
//        String msgLevel0 = "",msgLevel1 = "",msgLevel2 = "";
//        if (num==1){
//            if (m2){
//                if (lianCnt==m2cnt){
//                    double winMoney = 10;
//                    if (result.equals("z"))
//                        winMoney = 10 - (10 * 2^m2LostCnt)*0.05;
//                    else
//                        winMoney = 10;
//                    m2Money = m2Money.add(new BigDecimal(winMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    noMoney = noMoney.add(new BigDecimal(winMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    m2LostCnt = 0;
//                    if (msgLevel2.equals(""))
//                        msgLevel2 = "m2 win! "+table+(no+1)+" Tab total="+noMoney;
//                    else
//                        msgLevel2 += ";m2 win! "+table+(no+1)+" Tab total="+noMoney;
//                }
//            }
//            if (m4){
//                if (lianCnt>=m4cnt){
//                    double winMoney = 10;
//                    if (result.equals("z"))
//                        winMoney = 10 - (10 * 2^m4LostCnt)*0.05;
//                    else
//                        winMoney = 10;
//                    m4Money = m4Money.add(new BigDecimal(winMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    noMoney = noMoney.add(new BigDecimal(winMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    m4LostCnt = 0;
//                    if (msgLevel2.equals(""))
//                        msgLevel2 = "m4 win! "+table+(no+1)+" Tab total="+noMoney;
//                    else
//                        msgLevel2 += ";m4 win! "+table+(no+1)+" Tab total="+noMoney;
//                }
//            }
//            lianCnt = 0;
//        } else {
//            if (m1){
//                if (num==m1cnt){    //赢
//                    double winMoney = 10;
//                    if (result.equals("z"))
//                        winMoney = 10 - (10 * 2^m1LostCnt)*0.05;
//                    else
//                        winMoney = 10;
//                    m1Money = m1Money.add(new BigDecimal(winMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    noMoney = noMoney.add(new BigDecimal(winMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    m1LostCnt = 0;
//                    if (msgLevel2.equals(""))
//                        msgLevel2 = "m1 win! "+table+(no+1)+" Tab total="+noMoney;
//                    else
//                        msgLevel2 += ";m1 win! "+table+(no+1)+" Tab total="+noMoney;
//                }
//            }
//            if (m3){
//                if (num>=m3cnt){
//                    double winMoney = 10;
//                    if (result.equals("z"))
//                        winMoney = 10 - (10 * 2^m3LostCnt)*0.05;
//                    else
//                        winMoney = 10;
//                    m3Money = m3Money.add(new BigDecimal(winMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    noMoney = noMoney.add(new BigDecimal(winMoney)).setScale(2,BigDecimal.ROUND_HALF_UP);
//                    m3LostCnt = 0;
//                    if (msgLevel2.equals(""))
//                        msgLevel2 = "m3 win! "+table+(no+1)+" Tab total="+noMoney;
//                    else
//                        msgLevel2 += ";m3 win! "+table+(no+1)+" Tab total="+noMoney;
//                }
//            }
//        }
//        service.saveCaiyuleXiaMsg("test",msgLevel0,msgLevel1,msgLevel2);
//    }
}
