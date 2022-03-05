package com.daltonpro.bizzapps.modul.monitoring.callplan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.BaseActivity;
import com.daltonpro.bizzapps.model.database.Callplan;
import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.modul.monitoring.adapter.MonitoringAdapter;
import com.daltonpro.bizzapps.modul.monitoring.callplan.presenter.MonitoringPresenter;
import com.daltonpro.bizzapps.modul.monitoring.callplan.presenter.MonitoringPresenterContract;
import com.daltonpro.bizzapps.modul.monitoring.callplan.view.MonitoringViewContract;
import com.daltonpro.bizzapps.modul.monitoring.checkin.CheckInOutSurveyActivity;
import com.daltonpro.bizzapps.util.NullEmptyChecker;
import com.daltonpro.bizzapps.util.localstorage.Session;

import java.util.List;

public class MonitoringActivity extends BaseActivity implements MonitoringViewContract {
    private MonitoringPresenterContract monitoringPresenterContract;
    private ProgressBar progressBar;
    private Activity activity;
    private Spinner routeSpinner;
    private int idCallplan;
    private String strNameCallplan = "";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        activity = MonitoringActivity.this;
        Session.beginInitialization(activity);

        progressBar = findViewById(R.id.progressbar);
        routeSpinner = findViewById(R.id.spinner_route);
        recyclerView = findViewById(R.id.rv_customer);
        recyclerView.setHasFixedSize(true);

        monitoringPresenterContract = new MonitoringPresenter(MonitoringActivity.this, activity);


    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        monitoringPresenterContract.getListSpinner();

    }


    @Override
    public void onGetListSpinner(List<Callplan> callplanList, List<String> arrayListSpinner) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(activity, R.layout.simplerow, arrayListSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        routeSpinner.setAdapter(adapter);

        if (NullEmptyChecker.isNotNullOrNotEmpty(Session.getSessionGlobal(Session.SESSION_CALLPLAN_LAST_SELECTED)) &&
            arrayListSpinner.indexOf(Session.getSessionGlobal(Session.SESSION_CALLPLAN_LAST_SELECTED)) != -1) {
            routeSpinner.setSelection(arrayListSpinner.indexOf(Session.getSessionGlobal(Session.SESSION_CALLPLAN_LAST_SELECTED)));
        }

        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Set Default style
                try {
                    TextView tmpView = (TextView) parent.getChildAt(0);
                    tmpView.setTextColor(activity.getResources().getColor(R.color.black));
                    tmpView.setTypeface(null, Typeface.BOLD);
                    tmpView.setTextSize(18f);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (callplanList.size() > 0) {
                    idCallplan = callplanList.get(position).getIdcallplan();
                    strNameCallplan = callplanList.get(position).getNama();
                    monitoringPresenterContract.getListCustomerByCallplan(idCallplan);
                    Session.setSessionGlobal(Session.SESSION_CALLPLAN_LAST_SELECTED, strNameCallplan);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (callplanList.size() > 0) {
                    idCallplan = callplanList.get(0).getIdcallplan();
                    strNameCallplan = callplanList.get(0).getNama();
                    monitoringPresenterContract.getListCustomerByCallplan(idCallplan);
                }
            }
        });


        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onGetListCustomerByCallplan(List<Customer> customerList) {
        MonitoringAdapter monitoringAdapter = new MonitoringAdapter(customerList, activity, monitoringPresenterContract);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(monitoringAdapter);
    }

    @Override
    public void onClickedAdapter(Customer customer) {
        Intent intent = new Intent(activity, CheckInOutSurveyActivity.class);
        intent.putExtra("customerID", customer.getId());
        intent.putExtra("idcallplan", customer.getIdcallplan());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClickedPhoneAdapter(String phoneNumber) {
        if (phoneNumber.length() > 0) {
            String encodedHash = Uri.encode("#");
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber + encodedHash));
            startActivity(intent);
        } else {
            Toast.makeText(activity, "Tidak bisa menghubungi, nomor handphone kosong", Toast.LENGTH_LONG).show();
        }

    }
}