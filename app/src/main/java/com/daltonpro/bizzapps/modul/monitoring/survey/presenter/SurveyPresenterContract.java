package com.daltonpro.bizzapps.modul.monitoring.survey.presenter;

import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.daltonpro.bizzapps.model.database.MonitorDetail;
import com.daltonpro.bizzapps.model.database.MonitorHeader;

import java.util.List;

public interface SurveyPresenterContract {
    void doGetData(Customer custome);

    void doGetDataInfo(MonitorHeader monitorHeader,Customer customer,int idProject);

    void doChangedList(List<InfoHeader> infoHeaderList);

    void doSaveAndNext(List<MonitorDetail> monitorDetailList);


}
