package com.daltonpro.bizzapps.modul.monitoring.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.modul.monitoring.callplan.presenter.MonitoringPresenterContract;
import com.daltonpro.bizzapps.util.NullEmptyChecker;

import java.util.ArrayList;
import java.util.List;


public class MonitoringAdapter extends RecyclerView.Adapter<MonitoringAdapter.ViewHolder> {

    private List<Customer> listData;
    private Context context;
    private MonitoringPresenterContract monitoringPresenterContract;

    public MonitoringAdapter(List<Customer> listData, Context context, MonitoringPresenterContract monitoringPresenterContract) {
        this.listData = listData;
        this.context = context;
        this.monitoringPresenterContract = monitoringPresenterContract;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_monitoring_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Customer customer = listData.get(position);
        if (NullEmptyChecker.isNotNullOrNotEmpty(customer.getStatusUploaded()) && customer.getStatusUploaded().equalsIgnoreCase("Y")) {
            holder.ivCheck.setVisibility(View.VISIBLE);
            holder.ivCheck.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_check_circle_24));
        } else if (NullEmptyChecker.isNotNullOrNotEmpty(customer.getStatusUploaded()) && customer.getStatusUploaded().equalsIgnoreCase("N")){
            holder.ivCheck.setVisibility(View.VISIBLE);
            holder.ivCheck.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_red));
        }else{
            holder.ivCheck.setVisibility(View.GONE);
        }

        holder.tv_name.setText(customer.getNama());
        holder.tv_address.setText(customer.getAddress());
        holder.tv_id.setText("[" + String.valueOf(customer.getId()) + "]");

        holder.rlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monitoringPresenterContract.adapterDataClicked(customer);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_address, tv_id;
        RelativeLayout rlData;
        ImageView ivCheck;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_nama);
            tv_address = itemView.findViewById(R.id.tv_address);
            ivCheck = itemView.findViewById(R.id.iv_check);


            rlData = itemView.findViewById(R.id.rl_data);
        }
    }

    public void updateItems(List<Customer> newList) {
        this.listData = new ArrayList<Customer>();
        this.listData.addAll(newList);
        this.notifyDataSetChanged();
    }
}
