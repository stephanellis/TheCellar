package com.ronbreier.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ron Breier on 4/18/2017.
 */
public class DataTablesResponse<T> {

    @JsonProperty("data")
    private List<T> data = new ArrayList<T>();

    public DataTablesResponse(List<T> data) {
        this.data = data;
    }

    public DataTablesResponse(T data) {
        this.data.add(data);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return data.toString();
    }
}
