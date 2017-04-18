package com.ronbreier.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Ron Breier on 4/18/2017.
 */
public class DataTablesResponse<T> {

    @JsonProperty("data")
    private List<T> data;

    public DataTablesResponse(List<T> data) {
        this.data = data;

    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
