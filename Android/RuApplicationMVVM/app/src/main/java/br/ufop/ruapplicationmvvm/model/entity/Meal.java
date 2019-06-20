package br.ufop.ruapplicationmvvm.model.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Meal implements Serializable {

    @Json(name = "id")
    private int id;
    @Json(name = "day")
    private String day;
    @Json(name = "day_of_week")
    private int numDay;
    @Json(name = "month")
    private int month;
    @Json(name = "type")
    private int type;
    @Json(name = "status")
    private int status;
    @Json(name = "reason")
    private String reason;
    @Json(name = "date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

}
