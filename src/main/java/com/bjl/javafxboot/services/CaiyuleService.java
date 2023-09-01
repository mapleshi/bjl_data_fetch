package com.bjl.javafxboot.services;

import com.bjl.javafxboot.entity.Bjl;
import javafx.scene.control.TextArea;

import java.sql.Timestamp;
import java.util.List;

public interface CaiyuleService {

    void smartModel(boolean model1, boolean model2, boolean model3, boolean model4, int mcnt1, int mcnt2, int mcnt3, int mcnt4, int suijiStart1, int suijiEnd1, int suijiStart2, int suijiEnd2, int suijiStart3, int suijiEnd3, int suijiStart4, int suijiEnd4, int betModel1, int betModel2, int betModel3, int betModel4, int baocnt, Timestamp time1, Timestamp time2, String tab, TextArea console, int tabStart, int tabEnd, boolean leiCheck, int leiLostCnt, int leiWinCnt);

    void saveBjlAll(List<Bjl> list);

    void smartModelForHeiLei(boolean model1, boolean model2, boolean model3, boolean model4, int mcnt1, int mcnt2, int mcnt3, int mcnt4, int suijiStart1, int suijiEnd1, int suijiStart2, int suijiEnd2, int suijiStart3, int suijiEnd3, int suijiStart4, int suijiEnd4, int betModel1, int betModel2, int betModel3, int betModel4, int baocnt, String sDate, String eDate, TextArea console, boolean leiCheck, int leiLostCnt, int leiWinCnt);
}
