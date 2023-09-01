package com.bjl.javafxboot.util;

import com.bjl.javafxboot.runnable.WorkResultRunnable;
import com.bjl.javafxboot.services.CaiyuleService;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.util.List;

public class InitTwoPoint {
    CaiyuleService service;
    List<WorkResultRunnable> runnables;
    private Robot robot = null;/**/
    private final int tabCnt = 10;
    //每桌两个监控点坐标
    private int[] x1  = new int[tabCnt],y1 = new int[tabCnt],x2 = new int[tabCnt],y2 = new int[tabCnt];
    private int[] cnt = new int[tabCnt],zCnt = new int[tabCnt],xCnt = new int[tabCnt];
    private String[] result = new String[tabCnt];
    private int[] color1 = new int[tabCnt],color2 = new int[tabCnt];
    //初始点的坐标
    private int initX,initY,baseX = 21,baseY = 150;


    public void smartDeal(TextArea console, String tab) throws AWTException {
        for (int i = 0; i < 10; i++) {
            WorkResultRunnable runnable = new WorkResultRunnable(service,console,initX, initY, tab, 0, 0);
            runnable.start();
            runnables.add(runnable);
        }
    }

    public InitTwoPoint(CaiyuleService service, int intX, int intY, List runnables) {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.service = service;
        this.initX = intX;
        this.initY = intY;
        this.runnables = runnables;
    }



    // 取得指定坐标的颜色
    private int pickColor(int x, int y) {
        Color pixel = this.robot.getPixelColor(x, y);
        return pixel.getRGB()+CommonUtil.MAXCOLORRGB;
    }

    public static void main(String[] args) throws AWTException {
        Robot robot  = new Robot();
        Color pixel = robot.getPixelColor(1397, 131);
        System.out.println(pixel.getRGB()+CommonUtil.MAXCOLORRGB);
    }
}
