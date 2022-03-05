package com.daltonpro.bizzapps.modul.monitoring.survey.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.daltonpro.bizzapps.modul.monitoring.survey.presenter.SurveyPresenterContract;
import com.daltonpro.bizzapps.util.Constanta;

import java.util.ArrayList;
import java.util.List;


public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {

    private List<InfoHeader> listData;
    private Context context;
    private SurveyPresenterContract surveyPresenterContract;
    private boolean isCheckout = false;

    public SurveyAdapter(List<InfoHeader> listData, Context context, SurveyPresenterContract surveyPresenterContract,boolean isCheckout) {
        this.listData = listData;
        this.context = context;
        this.surveyPresenterContract = surveyPresenterContract;
        this.isCheckout = isCheckout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_survey_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        InfoHeader infoHeader = listData.get(position);
        holder.tv_name.setText(infoHeader.getQuestion());

        SurveyDetailTypeAdapter surveyAdapter = new SurveyDetailTypeAdapter(context, infoHeader.getDetailList(),
                getIndexSurveyType(infoHeader.getType()), surveyPresenterContract, listData, position,isCheckout);

        if (infoHeader.getType().equals(Constanta.STR_TYPE_TA) || infoHeader.getType().equals(Constanta.STR_TYPE_DDL)) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.setReverseLayout(true);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        }

        holder.recyclerView.setAdapter(surveyAdapter);

        if (isCheckout){
            holder.recyclerView.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        RelativeLayout rlData;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_nama);
            rlData = itemView.findViewById(R.id.rl_data);
            recyclerView = itemView.findViewById(R.id.rv_survey_detail);
            recyclerView.setHasFixedSize(true);
        }
    }

    public void updateItems(List<InfoHeader> newList) {
        this.listData = new ArrayList<InfoHeader>();
        this.listData.addAll(newList);
        this.notifyDataSetChanged();
    }

    private int getIndexSurveyType(String type) {
        if (type.equals(Constanta.STR_TYPE_RB)) {
            return Constanta.TYPE_RB;
        } else if (type.equals(Constanta.STR_TYPE_TA)) {
            return Constanta.TYPE_TA;
        } else if (type.equals(Constanta.STR_TYPE_DDL)) {
            return Constanta.TYPE_DDL;
        } else if (type.equals(Constanta.STR_TYPE_CL)) {
            return Constanta.TYPE_CL;
        }
        return Constanta.TYPE_RB;
    }
}
