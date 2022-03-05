package com.daltonpro.bizzapps.modul.monitoring.survey.view;

import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.daltonpro.bizzapps.model.database.MonitorDetail;
import com.daltonpro.bizzapps.model.database.MonitorHeader;

import java.util.List;

public interface SurveyViewContract {
    void getData(MonitorHeader monitorHeader);

    void getDataInfo(List<InfoHeader> infoHeaderList);

    void onSaveAndNext(List<MonitorDetail> monitorDetailList);

    void onInfoDetailChanged(List<InfoHeader> infoHeaderList);

}
