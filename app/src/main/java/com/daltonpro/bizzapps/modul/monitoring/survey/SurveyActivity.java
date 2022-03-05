package com.daltonpro.bizzapps.modul.monitoring.survey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.BaseActivity;
import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.daltonpro.bizzapps.model.database.MonitorDetail;
import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.modul.monitoring.photo.PhotoActivity;
import com.daltonpro.bizzapps.modul.monitoring.survey.adapter.SurveyAdapter;
import com.daltonpro.bizzapps.modul.monitoring.survey.presenter.SurveyPresenter;
import com.daltonpro.bizzapps.modul.monitoring.survey.presenter.SurveyPresenterContract;
import com.daltonpro.bizzapps.modul.monitoring.survey.view.SurveyViewContract;
import com.daltonpro.bizzapps.util.Constanta;
import com.daltonpro.bizzapps.util.NullEmptyChecker;
import com.daltonpro.bizzapps.util.localstorage.Session;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends BaseActivity implements SurveyViewContract {
    private Activity activity;
    private DaltonDatabase daltonDatabase;
    private Customer customer;
    private int customerID,idCallplan;
    private boolean isCheckout = false;
    private SurveyPresenterContract surveyPresenterContract;

    private TextView tvCustomer, tvAddress;
    private AppCompatButton btnNext;
    private ProgressBar progressBar;

    private MonitorHeader monitorHeader;
    private MonitorDetail monitorDetail;
    private RecyclerView recyclerView;
    private SurveyAdapter surveyAdapter;
    private List<InfoHeader> infoHeaderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        activity = SurveyActivity.this;
        Session.beginInitialization(activity);

        surveyPresenterContract = new SurveyPresenter(SurveyActivity.this, SurveyActivity.this);
        tvCustomer = findViewById(R.id.tv_customer);
        tvAddress = findViewById(R.id.tv_address);
        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.rv_survey);
        recyclerView.setHasFixedSize(true);


        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setEnabled(false);
                btnNext.setEnabled(false);

                List<MonitorDetail> monitorDetailList = new ArrayList<>();
                if (NullEmptyChecker.isNullOrEmpty(infoHeaderList)) {
                    surveyPresenterContract.doGetDataInfo(monitorHeader, customer, daltonDatabase.callplanDAO().getIdProject(customer.getIdcallplan()));
                }
                for (int i = 0; i < infoHeaderList.size(); i++) {

                    for (int j = 0; j < infoHeaderList.get(i).getDetailList().size(); j++) {
                        MonitorDetail monitorDetail = new MonitorDetail();
                        monitorDetail.setMonitor_header_id(monitorHeader.getId());
                        monitorDetail.setInfoid(infoHeaderList.get(i).getId());
                        if (infoHeaderList.get(i).getType().equals(Constanta.STR_TYPE_DDL)) {
                            daltonDatabase.monitorDetailDao().deleteAllByID(monitorHeader.getId(), infoHeaderList.get(i).getId());

                            monitorDetail.setIdinfodetail(infoHeaderList.get(i).getDetailList().get(j).getId());
                            monitorDetail.setInfoanswer("");
                            monitorDetailList.add(monitorDetail);
                            infoHeaderList.get(i).setInputed(true);
                        } else if (infoHeaderList.get(i).getType().equals(Constanta.STR_TYPE_CL)) {
                            if (infoHeaderList.get(i).getDetailList().get(j).isCheckedCl()) {
                                daltonDatabase.monitorDetailDao().deleteAllByID(monitorHeader.getId(), infoHeaderList.get(i).getId());

                                monitorDetail.setIdinfodetail(infoHeaderList.get(i).getDetailList().get(j).getId());
                                monitorDetail.setInfoanswer("");
                                monitorDetailList.add(monitorDetail);
                                infoHeaderList.get(i).setInputed(true);
                            }
                        } else if (infoHeaderList.get(i).getType().equals(Constanta.STR_TYPE_RB)) {
                            if ((NullEmptyChecker.isNotNullOrNotEmpty(infoHeaderList.get(i).getDetailList().get(j).getRadioButtonAnswer()) && !infoHeaderList.get(i).getDetailList().get(j).getRadioButtonAnswer().equals("")) &&
                                    infoHeaderList.get(i).getDetailList().get(j).getRadioButtonAnswer().equals(infoHeaderList.get(i).getDetailList().get(j).getAnswer())) {

                                daltonDatabase.monitorDetailDao().deleteAllByID(monitorHeader.getId(), infoHeaderList.get(i).getId());

                                monitorDetail.setIdinfodetail(infoHeaderList.get(i).getDetailList().get(j).getId());
                                monitorDetail.setInfoanswer("");
                                monitorDetailList.add(monitorDetail);
                                infoHeaderList.get(i).setInputed(true);
                            }
                        } else if (infoHeaderList.get(i).getType().equals(Constanta.STR_TYPE_TA) && NullEmptyChecker.isNotNullOrNotEmpty(infoHeaderList.get(i).getDetailList().get(j).getTextAreaAnswer())) {
                            daltonDatabase.monitorDetailDao().deleteAllByID(monitorHeader.getId(), infoHeaderList.get(i).getId());

                            monitorDetail.setIdinfodetail(infoHeaderList.get(i).getDetailList().get(j).getId());
                            monitorDetail.setInfoanswer(infoHeaderList.get(i).getDetailList().get(j).getTextAreaAnswer());
                            monitorDetailList.add(monitorDetail);
                            infoHeaderList.get(i).setInputed(true);
                        }
                    }
                }

                int countInputed = 0;
                for (int i = 0; i < infoHeaderList.size(); i++) {
                    if (infoHeaderList.get(i).isInputed()) {
                        countInputed = countInputed + 1;
                    }
                }
                if (countInputed == infoHeaderList.size()) {
                    surveyPresenterContract.doSaveAndNext(monitorDetailList);
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setEnabled(true);
                    btnNext.setEnabled(true);
                    Toast.makeText(activity, "Survey Belum terisi semuanya", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        daltonDatabase = DaltonDatabase.getInstance(activity);
        try {
            customerID = (int) getIntent().getExtras().get("customerID");
            idCallplan = (int) getIntent().getExtras().get("idcallplan");
            isCheckout = getIntent().getExtras().getBoolean("isCheckout", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        customer = daltonDatabase.customerDao().getCustomerByCode(customerID,idCallplan);
        updateUI();

    }


    private void updateUI() {
        tvCustomer.setText(customer.getNama());
        tvAddress.setText(customer.getAddress());

        surveyPresenterContract.doGetData(customer);
        surveyPresenterContract.doGetDataInfo(monitorHeader, customer, daltonDatabase.callplanDAO().getIdProject(customer.getIdcallplan()));

        surveyAdapter = new SurveyAdapter(infoHeaderList, activity, surveyPresenterContract, isCheckout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(surveyAdapter);

        if (isCheckout) {
            recyclerView.setEnabled(false);
            btnNext.setVisibility(View.GONE);
        }
    }

    @Override
    public void getData(MonitorHeader monitorHeader) {
        this.monitorHeader = monitorHeader;

    }

    @Override
    public void getDataInfo(List<InfoHeader> infoHeaderList) {
        this.infoHeaderList = infoHeaderList;
    }

    @Override
    public void onInfoDetailChanged(List<InfoHeader> infoHeaderList) {
        this.infoHeaderList = infoHeaderList;
    }

    @Override
    public void onSaveAndNext(List<MonitorDetail> monitorDetailList) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setEnabled(true);
        btnNext.setEnabled(true);
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra("customerID", customerID);
        intent.putExtra("idcallplan", customer.getIdcallplan());
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

}