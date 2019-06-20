package br.ufop.ruapplicationmvp.model.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Week implements Serializable {


    @Json(name = "month")
    private int month;
    @Json(name = "week_of_month")
    private int weekOfMonth;
    @Json(name = "year")
    private int year;
    @Json(name = "days")
    private String days;

    public int getWeekOfMonth() {
        return weekOfMonth;
    }


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
