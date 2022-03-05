package com.daltonpro.bizzapps.modul.monitoring.callplan.presenter;

import com.daltonpro.bizzapps.model.database.Customer;

public interface MonitoringPresenterContract {
    void getListSpinner();

    void getListCustomerByCallplan(int idCallplan);

    void adapterDataClicked(Customer customer);

    void adapterDataClickedPhone(String phoneNumber);


}
