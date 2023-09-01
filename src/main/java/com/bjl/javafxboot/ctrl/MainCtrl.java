package com.bjl.javafxboot.ctrl;

import com.bjl.javafxboot.runnable.WorkResultRunnable;
import com.bjl.javafxboot.services.CaiyuleService;
import com.bjl.javafxboot.util.CommonUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 主界面控制器
 *
 * @author yestin shi
 * @date 2021/3/23 2:01
 */
@Slf4j
@FXMLController
public class MainCtrl {

    @Resource
    CaiyuleService service;
    WorkResultRunnable runnable;

    public CheckBox m1,m2,m3,m4,leijiCheckBox;
    public TextField m1cnt,m2cnt,m3cnt,m4cnt,m1baocnt,m2baocnt,m3baocnt,m4baocnt,leiLostCnt,leiWinCnt;
    public TextField suijiStart1,suijiStart2,suijiStart3,suijiStart4,suijiEnd1,suijiEnd2,suijiEnd3,suijiEnd4;
    public TextField intX,intY,colorX,colorY,colorInt, zColor,xColor,tabIndex, tabStart, tabEnd, hourStart, hourEnd;
    public TextArea console;
    public DatePicker beginDate, endDate, beginDate1, endDate1;
    public ToggleGroup betModel1, betModel2, betModel3, betModel4;

    Robot robot;
    {
        try {
            System.setProperty("java.awt.headless", "false");
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    //获取颜色
    public void onBtnGetColor(){
        int x = Integer.parseInt(colorX.getText());
        int y = Integer.parseInt(colorY.getText());
        Color pixel = robot.getPixelColor(x, y);
        colorInt.setText(String.valueOf(pixel.getRGB()+ CommonUtil.MAXCOLORRGB));
    }

    //开始
    public void onBtnBegin() throws AWTException {
        String tab = tabIndex.getText();
        int intX1 = Integer.parseInt(intX.getText());
        int intY1 = Integer.parseInt(intY.getText());
        int colorXT = Integer.parseInt(xColor.getText()), colorZT = Integer.parseInt(zColor.getText());
        runnable = new WorkResultRunnable(service, console, intX1, intY1, tab, colorXT, colorZT);
        runnable.start();
    }

    //结束
    public void onBtnStop() {
        runnable.shutdown();
    }

    //复盘
    public void onBtnReplay() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = df.parse(beginDate.getValue().toString()+" "+hourStart.getText()+":00:00");
        Date end = df.parse(endDate.getValue().toString()+" "+hourEnd.getText()+":59:59");
        String tab = tabIndex.getText();
        // 下注模式
        int betM1 = Integer.parseInt(betModel1.getSelectedToggle().getUserData().toString());
        int betM2 = Integer.parseInt(betModel2.getSelectedToggle().getUserData().toString());
        int betM3 = Integer.parseInt(betModel3.getSelectedToggle().getUserData().toString());
        int betM4 = Integer.parseInt(betModel4.getSelectedToggle().getUserData().toString());
        String mbaocnt = m1.isSelected()?m1baocnt.getText():m2.isSelected()?m2baocnt.getText():m3.isSelected()?m3baocnt.getText():m4.isSelected()?m4baocnt.getText():m1baocnt.getText();
        // 随机金额
        int suijiS1 = Integer.parseInt(suijiStart1.getText()), suijiE1 = Integer.parseInt(suijiEnd1.getText());
        int suijiS2 = Integer.parseInt(suijiStart2.getText()), suijiE2 = Integer.parseInt(suijiEnd2.getText());
        int suijiS3 = Integer.parseInt(suijiStart3.getText()), suijiE3 = Integer.parseInt(suijiEnd3.getText());
        int suijiS4 = Integer.parseInt(suijiStart4.getText()), suijiE4 = Integer.parseInt(suijiEnd4.getText());
        // 可查看特定的连续桌
        int tabS = Integer.parseInt(tabStart.getText());
        int tabE = Integer.parseInt(tabEnd.getText());
        // 累计输赢
        int leiLCnt = Integer.parseInt(leiLostCnt.getText());
        int leiWCnt = Integer.parseInt(leiWinCnt.getText());
        service.smartModel(m1.isSelected(), m2.isSelected(), m3.isSelected(), m4.isSelected(),
                Integer.parseInt(m1cnt.getText()), Integer.parseInt(m2cnt.getText()), Integer.parseInt(m3cnt.getText()), Integer.parseInt(m4cnt.getText()),
                suijiS1, suijiE1, suijiS2, suijiE2, suijiS3, suijiE3, suijiS4, suijiE4, betM1, betM2, betM3, betM4,
                Integer.parseInt(mbaocnt), new Timestamp(start.getTime()), new Timestamp(end.getTime()), tab, console, tabS, tabE,
                leijiCheckBox.isSelected(), leiLCnt, leiWCnt);
    }

    //河内彩票 复盘
    public void onBtnReplay1() throws ParseException {
        String sDate = beginDate1.getValue().toString();
        String eDate = endDate1.getValue().toString();
        sDate = sDate.replace("-", "").substring(2)+"-0000";
        eDate = eDate.replace("-", "").substring(2)+"-1440";
        // 下注模式
        int betM1 = Integer.parseInt(betModel1.getSelectedToggle().getUserData().toString());
        int betM2 = Integer.parseInt(betModel2.getSelectedToggle().getUserData().toString());
        int betM3 = Integer.parseInt(betModel3.getSelectedToggle().getUserData().toString());
        int betM4 = Integer.parseInt(betModel4.getSelectedToggle().getUserData().toString());
        String mbaocnt = m1.isSelected()?m1baocnt.getText():m2.isSelected()?m2baocnt.getText():m3.isSelected()?m3baocnt.getText():m4.isSelected()?m4baocnt.getText():m1baocnt.getText();
        // 随机金额
        int suijiS1 = Integer.parseInt(suijiStart1.getText()), suijiE1 = Integer.parseInt(suijiEnd1.getText());
        int suijiS2 = Integer.parseInt(suijiStart2.getText()), suijiE2 = Integer.parseInt(suijiEnd2.getText());
        int suijiS3 = Integer.parseInt(suijiStart3.getText()), suijiE3 = Integer.parseInt(suijiEnd3.getText());
        int suijiS4 = Integer.parseInt(suijiStart4.getText()), suijiE4 = Integer.parseInt(suijiEnd4.getText());
        // 累计输赢
        int leiLCnt = Integer.parseInt(leiLostCnt.getText());
        int leiWCnt = Integer.parseInt(leiWinCnt.getText());
        service.smartModelForHeiLei(m1.isSelected(), m2.isSelected(), m3.isSelected(), m4.isSelected(),
                Integer.parseInt(m1cnt.getText()), Integer.parseInt(m2cnt.getText()), Integer.parseInt(m3cnt.getText()), Integer.parseInt(m4cnt.getText()),
                suijiS1, suijiE1, suijiS2, suijiE2, suijiS3, suijiE3, suijiS4, suijiE4, betM1, betM2, betM3, betM4,
                Integer.parseInt(mbaocnt), sDate, eDate, console,
                leijiCheckBox.isSelected(), leiLCnt, leiWCnt);
    }

    // 清空
    public void onClearBtn(){
        this.console.setText("");
    }

    public void onBtnSaveConfig(ActionEvent actionEvent){
        //地址
        File file = new File( "config.properties" );
        Properties properties = new Properties();
        //properties.setProperty ("account", username.getText());

        try {
            //存储
            properties.store ( new PrintStream( file ), "UTF-8" );
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void readConfigs(){
        //地址
        File file = new File( "config.properties" );
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
            //username.setText(properties.getProperty("account"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}