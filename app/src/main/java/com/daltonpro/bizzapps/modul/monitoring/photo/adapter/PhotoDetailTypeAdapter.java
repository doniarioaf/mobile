package com.daltonpro.bizzapps.modul.monitoring.photo.adapter;

import static com.daltonpro.bizzapps.util.Constanta.TYPE_PHOTO_ADD;
import static com.daltonpro.bizzapps.util.Constanta.TYPE_PHOTO_SHOW;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhotoShow;
import com.daltonpro.bizzapps.modul.monitoring.photo.presenter.PhotoPresenterContract;
import com.daltonpro.bizzapps.util.NullEmptyChecker;

import java.util.ArrayList;
import java.util.List;

public class PhotoDetailTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private DaltonDatabase daltonDatabase;
    private PhotoPresenterContract photoPresenterContract;
    private List<MonitorDetailPhotoShow> monitorDetailPhotoShowList;
    private int monitorHeaderID;
    private boolean isCheckout;

    public PhotoDetailTypeAdapter(Context context, PhotoPresenterContract photoPresenterContract, List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int monitorHeaderID, boolean isCheckout) {
        this.context = context;
        this.photoPresenterContract = photoPresenterContract;
        this.monitorDetailPhotoShowList = monitorDetailPhotoShowList;
        this.monitorHeaderID = monitorHeaderID;
        this.isCheckout = isCheckout;

        daltonDatabase = DaltonDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_PHOTO_ADD) {
            view = LayoutInflater.from(context).inflate(R.layout.item_photo_add, viewGroup, false);
            return new AddViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_photo_show, viewGroup, false);
            return new ShowViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        MonitorDetailPhotoShow monitorDetailPhotoShow = monitorDetailPhotoShowList.get(position);

        if (getItemViewType(position) == TYPE_PHOTO_ADD) {
            AddViewHolder addViewHolder = (AddViewHolder) viewHolder;

            if (isCheckout) {
                addViewHolder.ivAdd.setVisibility(View.GONE);
                addViewHolder.ivAdd.setEnabled(false);
            }

            addViewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<MonitorDetailPhotoShow> monitorDetailPhotoList = new ArrayList<>();
                    MonitorDetailPhotoShow monitorDetailPhoto = new MonitorDetailPhotoShow();
                    monitorDetailPhoto.setMonitor_header_id(monitorHeaderID);
                    if (monitorDetailPhotoList.size() > 0) {
                        monitorDetailPhoto.setIdinfophoto(-1);
                    }
                    monitorDetailPhoto.setTypePhotoShow("");
                    monitorDetailPhotoShowList.add(monitorDetailPhoto);


                    photoPresenterContract.doAddPhoto(monitorDetailPhotoShowList, position);
                }
            });

        } else if (getItemViewType(position) == TYPE_PHOTO_SHOW) {
            ShowViewHolder showViewHolder = (ShowViewHolder) viewHolder;

            if (isCheckout) {
                showViewHolder.ivShow.setEnabled(false);
            }

            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhotoShow.getDataPhotoBitmap())) {
                showViewHolder.ivShow.setImageBitmap(monitorDetailPhotoShow.getDataPhotoBitmap());
            }
            showViewHolder.ivShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoPresenterContract.doUpdatePhoto(monitorDetailPhotoShowList, position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(monitorDetailPhotoShowList.get(position).getTypePhotoShow())) {
            return TYPE_PHOTO_ADD;
        } else {
            return TYPE_PHOTO_SHOW;
        }
    }


    @Override
    public int getItemCount() {
        return monitorDetailPhotoShowList.size();

    }

    class AddViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAdd;

        AddViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAdd = itemView.findViewById(R.id.iv_image_add);
        }

    }

    class ShowViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivShow;

        ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            ivShow = itemView.findViewById(R.id.iv_image_show);
        }

    }

    public void updateItems(List<MonitorDetailPhotoShow> newList) {
        this.monitorDetailPhotoShowList = new ArrayList<MonitorDetailPhotoShow>();
        this.monitorDetailPhotoShowList.addAll(newList);
        this.notifyDataSetChanged();
    }

}
