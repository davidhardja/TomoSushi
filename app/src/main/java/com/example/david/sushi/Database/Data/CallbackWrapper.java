package com.example.david.sushi.Database.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by David on 25/04/2017.
 */

public class CallbackWrapper {
    private String code;
    private String status;
    private List<Data> data;
    private DataBon databon;
    private String status_meja;

    public String getStatus_meja() {
        return status_meja;
    }

    public void setStatus_meja(String status_meja) {
        this.status_meja = status_meja;
    }

    public DataBon getDataBon() {
        return databon;
    }

    public void setDataBon(DataBon dataBon) {
        this.databon = dataBon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
