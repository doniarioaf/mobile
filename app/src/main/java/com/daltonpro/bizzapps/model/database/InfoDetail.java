package com.daltonpro.bizzapps.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "info_detail")
public class InfoDetail {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;

    @NonNull
    @ColumnInfo(name = "info_header_id")
    @Expose
    private int idInfoHeader;

    @ColumnInfo(name = "answer")
    @SerializedName("answer")
    @Expose
    private String answer;

    //additonal Variable answer feedback
    @Ignore
    private boolean isCheckedCl;
    @Ignore
    private String radioButtonAnswer;
    @Ignore
    private String textAreaAnswer;
    @Ignore
    private String dropdownAnser;

    public InfoDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInfoHeader() {
        return idInfoHeader;
    }

    public void setIdInfoHeader(int idInfoHeader) {
        this.idInfoHeader = idInfoHeader;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCheckedCl() {
        return isCheckedCl;
    }

    public void setCheckedCl(boolean checkedCl) {
        isCheckedCl = checkedCl;
    }

    public String getRadioButtonAnswer() {
        return radioButtonAnswer;
    }

    public void setRadioButtonAnswer(String radioButtonAnswer) {
        this.radioButtonAnswer = radioButtonAnswer;
    }

    public String getTextAreaAnswer() {
        return textAreaAnswer;
    }

    public void setTextAreaAnswer(String textAreaAnswer) {
        this.textAreaAnswer = textAreaAnswer;
    }

    public String getDropdownAnser() {
        return dropdownAnser;
    }

    public void setDropdownAnser(String dropdownAnser) {
        this.dropdownAnser = dropdownAnser;
    }
}
