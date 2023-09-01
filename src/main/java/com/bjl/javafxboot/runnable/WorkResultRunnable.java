package com.bjl.javafxboot.runnable;


import com.bjl.javafxboot.entity.Bjl;
import com.bjl.javafxboot.services.CaiyuleService;
import com.bjl.javafxboot.util.CommonUtil;
import javafx.scene.control.TextArea;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Slf4j
public class WorkResultRunnable extends Thread {

    //volatile修饰符用来保证其它线程读取的总是该变量的最新的值
    public volatile Boolean exitFlag = false;

    CaiyuleService service;
    Robot robot = new Robot();
    TextArea console;
    String table;
    //初始点的坐标
    int CX_X,CX_Y,CX_X1,CX_Y1,baseX = 21,baseY = 150;
    private final int tabCnt = 10;
    private int ZCOLORRGB, XCOLORRGB, KCOLORRGB = 0, CX_COLOR_RGB = 9008487, TIP_COLOR_RGB = 3287348, MAXCOLORRGB = 16777216;
    //每桌两个监控点坐标
    private int[] x1  = new int[tabCnt],y1 = new int[tabCnt],x2 = new int[tabCnt],y2 = new int[tabCnt];
    private int[] initX = new int[tabCnt], initY = new int[tabCnt], cnt = new int[tabCnt];
    private String[] result = new String[tabCnt];
    private int[] preColor1 = new int[tabCnt], preColor2 = new int[tabCnt];
    //**************************************
//    Boolean m1,m2,m3,m4;
//    int m1cnt,m2cnt,m3cnt,m4cnt,m1BaoCnt,m2BaoCnt,m3BaoCnt,m4BaoCnt;
//    int lianCnt = 0;
//    int m1LostCnt = 0,m2LostCnt = 0,m3LostCnt = 0,m4LostCnt = 0;
//    int m1_1LostCnt = 0,m2_1LostCnt = 0,m3_1LostCnt = 0,m4_1LostCnt = 0;
//    int m1_2LostCnt = 0,m2_2LostCnt = 0,m3_2LostCnt = 0,m4_2LostCnt = 0;
//    int m1_3LostCnt = 0,m2_3LostCnt = 0,m3_3LostCnt = 0,m4_3LostCnt = 0;
//    BigDecimal totalMoney = new BigDecimal(0);
//    BigDecimal noMoney = new BigDecimal(0),m1Money = new BigDecimal(0);
//    BigDecimal m2Money = new BigDecimal(0),m3Money = new BigDecimal(0),m4Money = new BigDecimal(0);

    public WorkResultRunnable(CaiyuleService service, TextArea console, int intX, int intY, String table, int colorX, int colorZ) throws AWTException {
        this.service = service; this.table = table; this.console = console;
        this.CX_X = intX + 441; this.CX_Y = intY + 475;
        this.CX_X1 = intX + 706; this.CX_Y1 = intY + 283;
        this.ZCOLORRGB = colorZ; this.XCOLORRGB = colorX;
        for (int no = 0; no < tabCnt; no++) {
            this.initX[no] = no > 4 ? intX + 629 : intX;
            this.initY[no] = no > 4 ? intY + (no - 5) * baseY : intY + no * baseY;
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        // 获取初始的两个监控点
        getTwoPoint(console);
        int colorT, newColor1T, newColor2T, nextColor1, nextColor2;
        List<Bjl> list = new ArrayList<>();
        while (!exitFlag){
            for (int no = 0; no < tabCnt; no++) {
                //监控大厅是否要重新连接
                checkRoomStatus();
                try {
                    //监控第1点是否是空白
                    colorT = pickColor(initX[no], initY[no]);
                    if (colorT != KCOLORRGB) {
                        int baseX1 = initX[no] + x1[no] * baseX, baseY1 = initY[no] + y1[no] * baseX;
                        //为了防止在找点的过程中出现弹窗，每次都提前找下一个点颜色
                        nextColor1 = pickColor(baseX1, baseY1 + baseX);
                        nextColor2 = pickColor(baseX1 + baseX, baseY1);
                        //监控两点的颜色是否变化
                        newColor1T = pickColor(baseX1, baseY1);
                        newColor2T = pickColor(initX[no] + x2[no] * baseX, initY[no] + y2[no] * baseX);
                        if ((newColor1T != KCOLORRGB && newColor1T != XCOLORRGB && newColor1T != ZCOLORRGB)
                                || (newColor2T != KCOLORRGB && newColor2T != XCOLORRGB && newColor2T != ZCOLORRGB)) {
                            continue;
                        }
                        // 是否是洗牌后的第一点
                        if (x1[no] == 0 && y1[no] == 0 && x2[no] == 0) {
                            dealFirstPoint(newColor1T, newColor2T, list, no);
                            continue;
                        }
                        if (newColor2T != preColor2[no]) {  //第2点颜色变了
                            if (newColor2T == KCOLORRGB) {  //第2点颜色变为空了，表示没有跳; 只有一种情况：满格转弯
                                cnt[no]++;
                                //确定两个新的监控点和颜色
                                x1[no] = 15; x2[no] = 16; y1[no] = 0;
                                preColor1[no] = result[no].equals("z")?ZCOLORRGB:XCOLORRGB;
                                preColor2[no] = KCOLORRGB;
                            } else {
                                // 跳了，记录数据
                                Bjl obj = new Bjl(table + (no + 1) ,result[no], cnt[no], x1[no]+","+y1[no], x2[no]+","+y2[no]);
                                list.add(obj);
                                result[no] = getNewResultStr(newColor2T);
                                cnt[no] = 1;
                                //确定两个新的监控点和颜色
                                x1[no] = x2[no]; y1[no] = 1; x2[no]++;
                                //确定好监控点后，获取最新的监控点颜色
                                preColor1[no] = KCOLORRGB;
                                preColor2[no] = KCOLORRGB;
                                // 满格，将颜色设正
                                if (x2[no] > 16) {
                                    x2[no] = 16;
                                    preColor2[no] = result[no].equals("z")?ZCOLORRGB:XCOLORRGB;
                                }
                                // 跳了就满格的情况非常少。几乎没有，可以不考虑，暂时注释，否则会出现不对的情况
//                                colorT = pickColor(baseX1, initY[no] + baseX);
//                                if (colorT != KCOLORRGB) {
//                                    // 满格，x1颜色跟结果一致，x2将颜色设反
//                                    if (x1[no] == 16) {
//                                        x1[no] --; y1[no] = 0;
//                                        preColor1[no] = result[no].equals("z")?XCOLORRGB:ZCOLORRGB;
//                                        preColor2[no] = result[no].equals("z")?ZCOLORRGB:XCOLORRGB;
//                                    } else {    // 未满格而第2格就非空了，这情况非常少。几乎没有
//                                        x1[no] = x2[no]; y1[no] = 0;
//                                    }
//                                }
                            }
                            CommonUtil.updateTextArea(console, "第" + table + (no + 1) + "桌：" + result[no] + cnt[no] + ",第1点为" + x1[no] + "," + y1[no] + ",第2点为" + x2[no] + "," + y2[no] + "\n");
                        } else if (newColor1T != preColor1[no]){    // 第2点未变，第1点变了; 非常肯定：没有跳；有两种情况：一、正常图；二、转弯满格图;
                            //确定两个新的监控点和颜色
                            if (preColor1[no] == KCOLORRGB) {
                                // 确定下一个监控点,纵向往下一格找空白点，如果不是空白点，则横向找空白点,如果横向也非空白点，则满格了 提到程序开始找了
                                //colorT = pickColor(baseX1, baseY1 + baseX);
                                if (nextColor1 == KCOLORRGB) {    //纵向空
                                    y1[no]++;
                                    //确定好监控点后，获取最新的监控点颜色, x1,x2颜色不变
                                    //color1[no] = KCOLORRGB;
                                    //color2[no] = KCOLORRGB;
                                } else {    //找横向  提到程序开始找了
                                    //colorT = pickColor(baseX1 + baseX, baseY1);
                                    if (nextColor2 == KCOLORRGB) {
                                        x1[no]++;
                                        //确定好监控点后，获取最新的监控点颜色, x1,x2颜色不变
                                        //color1[no] = KCOLORRGB;
                                        //color2[no] = KCOLORRGB;
                                    } else {    //横向非空，则表示满格了, 16 6个
                                        x1[no] --; y1[no] = 0;
                                        //确定好监控点后，获取最新的监控点颜色, x2颜色未变
                                        preColor1[no] = result[no].equals("z")?XCOLORRGB:ZCOLORRGB;
                                    }
                                }
                            } else {    //转弯满格，x1满格且x2未满格
                                //确定两个新的监控点和颜色
                                x1[no] --; x2[no] --;
                            }
                            cnt[no]++;
                            CommonUtil.updateTextArea(console, "第" + table + (no + 1) + "桌：" + result[no] + cnt[no] + ",第1点为" + x1[no] + "," + y1[no] + ",第2点为" + x2[no] + "," + y2[no] + "\n");
                        }
                    } else {
                        x1[no] = 0; y1[no] = 0; x2[no] = 0; y2[no] = 0;
                        preColor1[no] = KCOLORRGB; preColor2[no] = KCOLORRGB;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (list.size() > 0) {
                service.saveBjlAll(list);
                list.clear();
            }
            sleep(1000);
        }
    }

    private void dealFirstPoint(int newColor1T, int newColor2T, List<Bjl> list, int no) {
        y1[no] = 1;
        x2[no] = 1;
        String newResult = getNewResultStr(newColor1T);
        if (result[no].equals("")){ // 开局第一局
            result[no] = newResult;
        }
        // 表示没有跳 或 开局第一局
        if (newResult.equals(result[no]) || cnt[no] == 0) {
            cnt[no]++;
        } else {
            // 跳了，记录数据
            Bjl obj = new Bjl(table + (no + 1) ,result[no], cnt[no], x1[no]+","+y1[no], x2[no]+","+y2[no]);
            list.add(obj);
            // 记录连排,连排结束
            cnt[no] = 1;
            result[no] = getNewResultStr(newColor2T);
        }
        //确定好监控点后，获取最新的监控点颜色
        preColor1[no] = KCOLORRGB;
        preColor2[no] = KCOLORRGB;
        CommonUtil.updateTextArea(console, "第" + table + (no + 1) + "桌：" + result[no] + cnt[no] + ",第1点为" + x1[no] + "," + y1[no] + ",第2点为" + x2[no] + "," + y2[no] + "\n");
    }

    private void checkRoomStatus() throws InterruptedException {
        int colorT;
        while (true) {
            colorT = pickColor(CX_X, CX_Y);
            if (colorT == CX_COLOR_RGB){
                robot.mouseMove(CX_X,CX_Y);
                sleep(500);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                robot.mouseMove(100,100);
                sleep(3000);
            } else if (colorT == TIP_COLOR_RGB) {
                robot.mouseMove(CX_X1,CX_Y1);
                sleep(500);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                robot.mouseMove(100,100);
                sleep(2000);
            } else {
                break;
            }
        }
    }

    // 获取初始的两个监控点
    private void getTwoPoint(TextArea console) {
        //A B C D E F G H I J桌
        int color1Tmp, color2Tmp;
        for (int no = 0; no < tabCnt; no++) {
            //第1列到第17列
            for (int x = 0; x <= 16; x++) {
                cnt[no] = 0;
                //优先判断每列第一个是否是空白
                color1Tmp = pickColor(initX[no]+x*baseX, initY[no]);
                if (color1Tmp == KCOLORRGB) {
                    x2[no] = x; y2[no] = 0;
                    //第一点就是空白，表示局刚刚开始
                    if (x==0){
                        x1[no] = 0; y1[no] = 0; result[no] = "";
                    } else {
                        //找到空白的点,取前一个点和当前点作为监控点
                        x1[no] = x - 1;
                        //取前一个点颜色,判断庄闲
                        color1Tmp = pickColor(initX[no]+x1[no]*baseX, initY[no]);
                        result[no] = getNewResultStr(color1Tmp);
                        //前一个肯定有色，从第2个开始
                        for (int y = 1; y < 7; y++) {
                            int intX = initX[no] + x1[no]*baseX;
                            int intY = initY[no] + y*baseX;
                            color2Tmp = pickColor(intX,intY);
                            if (color2Tmp==KCOLORRGB){
                                y1[no] = y; cnt[no] = y;
                                break;
                            } else if (color2Tmp != color1Tmp){
                                //转弯
                                intY = initY[no] + (y-1) * baseX;
                                for (int xx = 0; xx <= 16; xx++) {
                                    intX = initX[no] + (x + xx) * baseX;
                                    color2Tmp = pickColor(intX,intY);
                                    if (color2Tmp==KCOLORRGB){
                                        x1[no] = x + xx; y1[no] = y - 1; cnt[no] = y + xx;
                                        break;
                                    }else if (color2Tmp != color1Tmp){
                                        //满格
                                        x1[no] = x -1; y1[no] = 0; cnt[no] = y + xx;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }

                //满格
                if (x == 16){
                    x1[no] = 16;
                    //取点颜色,判断庄闲
                    color1Tmp = pickColor(initX[no]+x1[no]*baseX, initY[no]);
                    result[no] = getNewResultStr(color1Tmp);
                    for (int y = 1; y <= 5; y++) {
                        color2Tmp = pickColor(initX[no] + 16 * baseX,initY[no] + y * baseX);
                        if (color2Tmp == KCOLORRGB){
                            y1[no] = y; x2[no] = 16; y2[no] = 0; cnt[no] = y;
                            break;
                        } else if (color2Tmp != color1Tmp){
                            x1[no] = 15; y1[no] = 0; x2[no] = 16; y2[no] = 0; cnt[no] = y;
                            break;
                        }
                        if (y == 5) {
                            x1[no] = 15; y1[no] = 0; x2[no] = 16; y2[no] = 0; cnt[no] = y+1;
                        }
                    }
                }
            }
            preColor1[no] = pickColor(initX[no]+x1[no]*baseX,initY[no]+y1[no]*baseX);
            preColor2[no] = pickColor(initX[no]+x2[no]*baseX,initY[no]+y2[no]*baseX);
            CommonUtil.updateTextArea(console, "第"+(no+1)+"桌:第1点为"+x1[no]+","+y1[no]+",第2点为"+x2[no]+","+y2[no]+",个数为"+result[no]+""+cnt[no]+"\n");
        }
    }

    //获取新的庄还是闲
    private String getNewResultStr(int color1T) {
        String str = "x";
        if (color1T == ZCOLORRGB) {
            str = "z";
        }
        return str;
    }

    // 取得指定坐标的颜色
    private int pickColor(int x, int y) {
        Color pixel = robot.getPixelColor(x, y);
        return pixel.getRGB()+MAXCOLORRGB;
    }

    public void shutdown() {
        this.exitFlag = true;
    }
}