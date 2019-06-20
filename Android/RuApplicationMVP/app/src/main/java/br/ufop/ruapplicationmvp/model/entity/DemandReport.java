package br.ufop.ruapplicationmvp.model.entity;

import com.squareup.moshi.Json;

public class DemandReport {
    @Json(name = "total")
    private  int total;
    @Json(name = "day")
    private  int day;
    @Json(name = "date")
    private String date;

    public int getTotal() {
        return total;
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
