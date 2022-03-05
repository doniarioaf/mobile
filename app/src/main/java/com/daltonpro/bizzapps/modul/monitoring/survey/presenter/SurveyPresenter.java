package com.daltonpro.bizzapps.modul.monitoring.survey.presenter;

import android.content.Context;

import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.model.database.InfoDetail;
import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.daltonpro.bizzapps.model.database.MonitorDetail;
import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.modul.monitoring.survey.view.SurveyViewContract;
import com.daltonpro.bizzapps.util.Constanta;

import java.util.ArrayList;
import java.util.List;

public class SurveyPresenter implements SurveyPresenterContract {
    private final SurveyViewContract surveyViewContract;
    private Context context;
    private DaltonDatabase daltonDatabase;

    public SurveyPresenter(SurveyViewContract surveyViewContract, Context context) {
        this.surveyViewContract = surveyViewContract;
        daltonDatabase = DaltonDatabase.getInstance(context);
    }

    @Override
    public void doGetData(Customer customer) {
        MonitorHeader monitorHeader = daltonDatabase.monitorHeaderDao().getAllListByCustomerID(customer.getId(),customer.getIdcallplan());
        surveyViewContract.getData(monitorHeader);

    }

    @Override
    public void doGetDataInfo(MonitorHeader monitorHeader, Customer customer,int idProject) {
        List<InfoHeader> infoHeaderList = daltonDatabase.infoHeaderDao().getAllList(idProject);
        List<InfoDetail> infoDetailList = new ArrayList<>();
        for (int i = 0; i < infoHeaderList.size(); i++) {
            if (infoHeaderList.get(i).getType().equals(Constanta.STR_TYPE_DDL)) {

                //DDL selalu diisi 1
                MonitorDetail monitorDetailList = daltonDatabase.monitorDetailDao().getMonitorDetailByMonitorHeaderIDAndInfoHeader(monitorHeader.getId(), infoHeaderList.get(i).getId());
                InfoDetail infoDetail = new InfoDetail();
                if (monitorDetailList != null) {
                    infoDetail.setId(monitorDetailList.getIdinfodetail());
                    infoDetail.setIdInfoHeader(infoHeaderList.get(i).getId());
                    infoDetail.setAnswer(daltonDatabase.infoDetailDao().getInfoDetailByCode(monitorDetailList.getIdinfodetail()).getAnswer());
                } else {
                    infoDetail.setId(-1);
                    infoDetail.setIdInfoHeader(infoHeaderList.get(i).getId());
                    infoDetail.setAnswer("");
                }

                infoDetailList.add(infoDetail);
                infoHeaderList.get(i).setDetailList(infoDetailList);
            } else if (infoHeaderList.get(i).getType().equals(Constanta.STR_TYPE_RB)) {


                infoDetailList = daltonDatabase.infoDetailDao().getAllListByHeaderID(infoHeaderList.get(i).getId());
                for (int j = 0; j < infoDetailList.size(); j++) {
                    MonitorDetail monitorDetailList = daltonDatabase.monitorDetailDao().getMonitorDetailByMonitorHeaderIDAndInfoHeaderDetail(monitorHeader.getId(), infoHeaderList.get(i).getId(), infoDetailList.get(j).getId());

                    if (monitorDetailList != null) {
                        infoDetailList.get(j).setId(monitorDetailList.getIdinfodetail());
                        infoDetailList.get(j).setIdInfoHeader(infoHeaderList.get(i).getId());
                        infoDetailList.get(j).setAnswer(daltonDatabase.infoDetailDao().getInfoDetailByCode(monitorDetailList.getIdinfodetail()).getAnswer());
                        infoDetailList.get(j).setRadioButtonAnswer(daltonDatabase.infoDetailDao().getInfoDetailByCode(monitorDetailList.getIdinfodetail()).getAnswer());
                    }
                }

                infoHeaderList.get(i).setDetailList(infoDetailList);
            } else if (infoHeaderList.get(i).getType().equals(Constanta.STR_TYPE_TA)) {


                infoDetailList = daltonDatabase.infoDetailDao().getAllListByHeaderID(infoHeaderList.get(i).getId());
                for (int j = 0; j < infoDetailList.size(); j++) {
                    MonitorDetail monitorDetailList = daltonDatabase.monitorDetailDao().getMonitorDetailByMonitorHeaderIDAndInfoHeaderDetail(monitorHeader.getId(), infoHeaderList.get(i).getId(), infoDetailList.get(j).getId());

                    if (monitorDetailList != null) {
                        infoDetailList.get(j).setId(monitorDetailList.getIdinfodetail());
                        infoDetailList.get(j).setIdInfoHeader(infoHeaderList.get(i).getId());
                        infoDetailList.get(j).setAnswer(daltonDatabase.infoDetailDao().getInfoDetailByCode(monitorDetailList.getIdinfodetail()).getAnswer());
                        infoDetailList.get(j).setTextAreaAnswer(monitorDetailList.getInfoanswer());
                    }
                }

                infoHeaderList.get(i).setDetailList(infoDetailList);
            } else if (infoHeaderList.get(i).getType().equals(Constanta.STR_TYPE_CL)) {


                infoDetailList = daltonDatabase.infoDetailDao().getAllListByHeaderID(infoHeaderList.get(i).getId());
                for (int j = 0; j < infoDetailList.size(); j++) {
                    MonitorDetail monitorDetailList = daltonDatabase.monitorDetailDao().getMonitorDetailByMonitorHeaderIDAndInfoHeaderDetail(monitorHeader.getId(), infoHeaderList.get(i).getId(), infoDetailList.get(j).getId());

                    if (monitorDetailList != null) {
                        infoDetailList.get(j).setId(monitorDetailList.getIdinfodetail());
                        infoDetailList.get(j).setIdInfoHeader(infoHeaderList.get(i).getId());
                        infoDetailList.get(j).setAnswer(daltonDatabase.infoDetailDao().getInfoDetailByCode(monitorDetailList.getIdinfodetail()).getAnswer());
                        infoDetailList.get(j).setCheckedCl(true);
                    }
                }

                infoHeaderList.get(i).setDetailList(infoDetailList);
            }

        }

        surveyViewContract.getDataInfo(infoHeaderList);
    }

    @Override
    public void doChangedList(List<InfoHeader> infoHeaderList) {
        surveyViewContract.onInfoDetailChanged(infoHeaderList);
    }

    @Override
    public void doSaveAndNext(List<MonitorDetail> monitorDetailList) {
        daltonDatabase.monitorDetailDao().insertMonitordetailList(monitorDetailList);
        surveyViewContract.onSaveAndNext(monitorDetailList);
    }


}
