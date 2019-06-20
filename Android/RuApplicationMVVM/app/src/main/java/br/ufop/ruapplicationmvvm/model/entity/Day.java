package br.ufop.ruapplicationmvvm.model.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Day implements Serializable {

    @Json(name = "date")
    private String date;
    @Json(name = "day")
    private String day;
    @Json(name = "day_of_week")
    private   int numDay;
    @Json(name = "month")
    private   int month;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getNumDay() {
        return numDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
