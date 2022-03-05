package com.daltonpro.bizzapps.modul.monitoring.callplan.presenter;

import android.content.Context;

import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.modul.monitoring.callplan.view.MonitoringViewContract;
import com.daltonpro.bizzapps.util.NullEmptyChecker;

import java.util.List;

public class MonitoringPresenter implements MonitoringPresenterContract {
    private final MonitoringViewContract monitoringViewContract;
    private DaltonDatabase daltonDatabase;
    private Context context;

    public MonitoringPresenter(MonitoringViewContract monitoringViewContract, Context context) {
        this.monitoringViewContract = monitoringViewContract;
        this.daltonDatabase = DaltonDatabase.getInstance(context);
        this.context = context;
    }


    @Override
    public void getListSpinner() {
        monitoringViewContract.onGetListSpinner(daltonDatabase.callplanDAO().getAllList(), daltonDatabase.callplanDAO().getAllListArrayNama());
    }

    @Override
    public void getListCustomerByCallplan(int idCallplan) {
        List<Customer> customerList = daltonDatabase.customerDao().getAllListByIdCallplan(idCallplan);
        for (int i = 0; i < customerList.size(); i++) {
            if (NullEmptyChecker.isNotNullOrNotEmpty(daltonDatabase.monitorHeaderDao().getStatusUploaded(customerList.get(i).getId(), customerList.get(i).getIdcallplan())) &&
                    daltonDatabase.monitorHeaderDao().getStatusUploaded(customerList.get(i).getId(), customerList.get(i).getIdcallplan()).equalsIgnoreCase("Y")) {
                customerList.get(i).setStatusUploaded("Y");
            } else if (NullEmptyChecker.isNotNullOrNotEmpty(daltonDatabase.monitorHeaderDao().getStatusUploaded(customerList.get(i).getId(), customerList.get(i).getIdcallplan())) &&
                    daltonDatabase.monitorHeaderDao().getStatusUploaded(customerList.get(i).getId(), customerList.get(i).getIdcallplan()).equalsIgnoreCase("N")){
                customerList.get(i).setStatusUploaded("N");
            }else{
                customerList.get(i).setStatusUploaded("");
            }
        }

        monitoringViewContract.onGetListCustomerByCallplan(customerList);
    }

    @Override
    public void adapterDataClicked(Customer customer) {
        monitoringViewContract.onClickedAdapter(customer);
    }

    @Override
    public void adapterDataClickedPhone(String phoneNumber) {
        monitoringViewContract.onClickedPhoneAdapter(phoneNumber);
    }
}
