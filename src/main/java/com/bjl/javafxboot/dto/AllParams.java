package com.bjl.javafxboot.dto;

import com.alibaba.fastjson.JSONObject;

import java.util.Random;

public class AllParams {
    //方案变量
    public int bCnt = 10,fCnt = 11,hCnt = 8;
    public boolean isFirst = true,isTest = true;
    public int[] money = new int[fCnt];            //所有方案的基础金额
    public float[] fananMoney = new float[fCnt],totalMoney = new float[]{0};   //所有方案的盈利金额
    public int[] fancnt = new int[fCnt];          //所有方案止损局数
    public boolean[] fanan = new boolean[fCnt];   //所有方案是否有开启
    public int[] mVal = new int[fCnt];            //所有方案触发局数
    public boolean[] geng = new boolean[fCnt],tiao = new boolean[fCnt],pin = new boolean[fCnt],fan = new boolean[fCnt];
    //重置配置
    public int[][] hours =new int[7][hCnt];public boolean[] isReplay = new boolean[7];public int[] replayCnt = new int[7];
    //爆点时间
    public String[][] dsStartTime = new String[fCnt][bCnt],dxStartTime = new String[fCnt][bCnt];

    public int o_fCnt = 4;
    public int[] o_money = new int[o_fCnt];            //所有方案的基础金额
    public float[] o_fananMoney = new float[o_fCnt];   //所有方案的盈利金额
    public int[] o_fancnt = new int[o_fCnt];          //所有方案止损局数
    public boolean[] o_fanan = new boolean[o_fCnt];   //所有方案是否有开启
    public int[] o_mVal = new int[o_fCnt];            //所有方案触发局数
    public String[][] o_dsStartTime = new String[o_fCnt][bCnt];

    //球变量
    public int[] dscnt = new int[bCnt],dxcnt = new int[bCnt],dsliancnt = new int[bCnt],dxliancnt = new int[bCnt];
    public int[] dacnt = new int[bCnt],xcnt = new int[bCnt],dcnt = new int[bCnt],scnt = new int[bCnt];
    public int[] dstiaocnt = new int[bCnt],dxtiaocnt = new int[bCnt];
    public int[][] dsmoney = new int[fCnt][bCnt],dxmoney = new int[fCnt][bCnt],dslost = new int[fCnt][bCnt],dxlost = new int[fCnt][bCnt];
    public int[][] dsbaocnt = new int[fCnt][bCnt],dxbaocnt = new int[fCnt][bCnt];
    //随机数干扰金额
    public int[][] r_dsmoney = new int[fCnt][bCnt],r_dxmoney = new int[fCnt][bCnt];
    public int[][] r_o_money = new int[o_fCnt][bCnt];

    public int[] m_0dmoney = new int[bCnt],m_0dlost = new int[bCnt],m_1xmoney = new int[bCnt],m_1xlost = new int[bCnt];
    public int[] m_2dmoney = new int[bCnt],m_2dlost = new int[bCnt],m_3smoney = new int[bCnt],m_3slost = new int[bCnt];

    //下单
    public JSONObject xiaJson = new JSONObject();
    //消息
    public String[] msgLevel = new String[3];  //告警//重要//记录

    //加入干扰随机数，金额大于等于8
    public Random r = new Random();
}
