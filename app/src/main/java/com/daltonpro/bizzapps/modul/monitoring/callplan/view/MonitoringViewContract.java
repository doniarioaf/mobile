package com.daltonpro.bizzapps.modul.monitoring.callplan.view;

import com.daltonpro.bizzapps.model.database.Callplan;
import com.daltonpro.bizzapps.model.database.Customer;

import java.util.List;

public interface MonitoringViewContract {
    void onGetListSpinner(List<Callplan> callplanList, List<String> arrayListSpinner);

    void onGetListCustomerByCallplan(List<Customer> customerList);

    void onClickedAdapter(Customer customer);

    void onClickedPhoneAdapter(String phoneNumber);

}
