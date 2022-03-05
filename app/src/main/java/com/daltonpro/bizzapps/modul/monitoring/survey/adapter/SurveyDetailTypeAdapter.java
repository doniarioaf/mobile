package com.daltonpro.bizzapps.modul.monitoring.survey.adapter;

import static com.daltonpro.bizzapps.util.Constanta.TYPE_CL;
import static com.daltonpro.bizzapps.util.Constanta.TYPE_DDL;
import static com.daltonpro.bizzapps.util.Constanta.TYPE_RB;
import static com.daltonpro.bizzapps.util.Constanta.TYPE_TA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.model.database.InfoDetail;
import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.daltonpro.bizzapps.modul.monitoring.survey.presenter.SurveyPresenterContract;
import com.daltonpro.bizzapps.util.NullEmptyChecker;

import java.util.List;

public class SurveyDetailTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int typeSurvey;
    private Context context;
    private List<InfoDetail> infoDetailList;
    private List<InfoHeader> infoHeaderList;
    private int positionHeader;
    private boolean isCheckout;
    private DaltonDatabase daltonDatabase;
    private SurveyPresenterContract surveyPresenterContract;

    public SurveyDetailTypeAdapter(Context context, List<InfoDetail> infoDetailList, int typeSurvey,
                                   SurveyPresenterContract surveyPresenterContract, List<InfoHeader> infoHeaderList, int positionHeader,boolean isCheckout) {
        this.context = context;
        this.infoDetailList = infoDetailList;
        this.infoHeaderList = infoHeaderList;
        this.positionHeader = positionHeader;
        this.typeSurvey = typeSurvey;
        this.surveyPresenterContract = surveyPresenterContract;
        this.isCheckout = isCheckout;
        daltonDatabase = DaltonDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (typeSurvey == TYPE_RB) {
            view = LayoutInflater.from(context).inflate(R.layout.item_survey_radio_button, viewGroup, false);
            return new RadioButtonViewHolder(view);
        } else if (typeSurvey == TYPE_TA) {
            view = LayoutInflater.from(context).inflate(R.layout.item_survey_text_area, viewGroup, false);
            return new TextAreaViewHolder(view);
        } else if (typeSurvey == TYPE_DDL) {
            view = LayoutInflater.from(context).inflate(R.layout.item_survey_dropdown_list, viewGroup, false);
            return new DropDownListViewHolder(view);
        } else if (typeSurvey == TYPE_CL) {
            view = LayoutInflater.from(context).inflate(R.layout.item_survey_checklist, viewGroup, false);
            return new CheckListViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_survey_radio_button, viewGroup, false);
            return new RadioButtonViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        InfoDetail infoDetail = infoDetailList.get(position);

        if (typeSurvey == TYPE_RB) {
            RadioButtonViewHolder radioButtonViewHolder = (RadioButtonViewHolder) viewHolder;

            if (isCheckout){
                radioButtonViewHolder.rbOption.setEnabled(false);
            }

            if (infoDetail.getAnswer().equals(infoDetail.getRadioButtonAnswer())) {
                radioButtonViewHolder.rbOption.setChecked(true);
            } else {
                radioButtonViewHolder.rbOption.setChecked(false);
            }

            radioButtonViewHolder.rbOption.setText(infoDetail.getAnswer());
            radioButtonViewHolder.rbOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < infoDetailList.size(); i++) {
                        infoDetailList.get(i).setRadioButtonAnswer("");
                    }
                    infoDetailList.get(position).setId(infoDetail.getId());
                    infoDetailList.get(position).setRadioButtonAnswer(infoDetail.getAnswer());
                    infoHeaderList.get(positionHeader).setDetailList(infoDetailList);
                    surveyPresenterContract.doChangedList(infoHeaderList);
                    notifyDataSetChanged();
                }
            });

        } else if (typeSurvey == TYPE_TA) {
            TextAreaViewHolder textAreaViewHolder = (TextAreaViewHolder) viewHolder;

            if (isCheckout){
                textAreaViewHolder.editText.setEnabled(false);
            }

            if (NullEmptyChecker.isNotNullOrNotEmpty(infoDetail.getTextAreaAnswer()) && infoDetail.getTextAreaAnswer().length() > 0 && !infoDetail.getTextAreaAnswer().equalsIgnoreCase("text")) {
                textAreaViewHolder.editText.setText(infoDetail.getTextAreaAnswer());
            }

            textAreaViewHolder.editText.setTextColor(context.getResources().getColor(R.color.black));
            textAreaViewHolder.editText.setTypeface(null, Typeface.BOLD);
            textAreaViewHolder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    infoHeaderList.get(positionHeader).getDetailList().get(position).setTextAreaAnswer(s.toString());
                    surveyPresenterContract.doChangedList(infoHeaderList);
                }
            });
        } else if (typeSurvey == TYPE_DDL) {
            DropDownListViewHolder dropDownListViewHolder = (DropDownListViewHolder) viewHolder;

            if (isCheckout){
                dropDownListViewHolder.spinner.setEnabled(false);
            }

            List<Integer> listID = daltonDatabase.infoDetailDao().getListID(infoDetail.getIdInfoHeader());
            List<InfoDetail> infoDetailListSpinner = daltonDatabase.infoDetailDao().getAllListByHeaderID(infoDetail.getIdInfoHeader());
            List<String> arrayListSpinner = daltonDatabase.infoDetailDao().getListNameByHeaderID(infoDetail.getIdInfoHeader());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simplerow, arrayListSpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropDownListViewHolder.spinner.setAdapter(adapter);


            if (listID.indexOf(infoDetail.getId()) != -1) {
                dropDownListViewHolder.spinner.setSelection(arrayListSpinner.indexOf(infoDetail.getAnswer()));
            }

            dropDownListViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    //Set Default style
                    try {
                        TextView tmpView = (TextView) parent.getChildAt(0);
                        tmpView.setTextColor(context.getResources().getColor(R.color.black));
                        tmpView.setTypeface(null, Typeface.BOLD);
                        tmpView.setTextSize(18f);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    infoHeaderList.get(positionHeader).getDetailList().get(position).setId(infoDetailListSpinner.get(pos).getId());
                    infoHeaderList.get(positionHeader).getDetailList().get(position).setDropdownAnser(infoDetailListSpinner.get(pos).getAnswer());
                    surveyPresenterContract.doChangedList(infoHeaderList);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    infoHeaderList.get(positionHeader).getDetailList().get(position).setId(infoDetailListSpinner.get(0).getId());
                    infoHeaderList.get(positionHeader).getDetailList().get(position).setDropdownAnser(infoDetailListSpinner.get(0).getAnswer());
                    surveyPresenterContract.doChangedList(infoHeaderList);
                }
            });


        } else if (typeSurvey == TYPE_CL) {
            CheckListViewHolder checkListViewHolder = (CheckListViewHolder) viewHolder;

            if (isCheckout){
                checkListViewHolder.checkBox.setEnabled(false);
            }

            if (infoDetail.isCheckedCl()) {
                checkListViewHolder.checkBox.setChecked(true);
            } else {
                checkListViewHolder.checkBox.setChecked(false);
            }
            checkListViewHolder.checkBox.setText(infoDetail.getAnswer());
            checkListViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    infoHeaderList.get(positionHeader).getDetailList().get(position).setCheckedCl(isChecked);
                    surveyPresenterContract.doChangedList(infoHeaderList);
                    notifyDataSetChanged();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return infoDetailList.size();

    }

    class RadioButtonViewHolder extends RecyclerView.ViewHolder {

        private RadioButton rbOption;

        RadioButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            rbOption = itemView.findViewById(R.id.radioButton);
        }

    }

    class DropDownListViewHolder extends RecyclerView.ViewHolder {

        private Spinner spinner;

        DropDownListViewHolder(@NonNull View itemView) {
            super(itemView);
            spinner = itemView.findViewById(R.id.spinner);
        }

    }

    class CheckListViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;

        CheckListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

    }

    class TextAreaViewHolder extends RecyclerView.ViewHolder {

        private EditText editText;

        TextAreaViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.et_answer);
        }

    }


}
