package br.ufop.ruapplicationpassivemvc.model.entity;

import com.squareup.moshi.Json;

public class Demand {
    @Json(name = "total")
    private  int total;
    @Json(name = "principal")
    private  int sumPrincipal;
    @Json(name = "vegetariano")
    private  int sumVegetariano;
    @Json(name = "day")
    private  int day;
    @Json(name = "parcipation")
    private  int parcipation;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getParcipation() {
        return parcipation;
    }

    public void setParcipation(int parcipation) {
        this.parcipation = parcipation;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSumPrincipal() {
        return sumPrincipal;
    }

    public void setSumPrincipal(int sumPrincipal) {
        this.sumPrincipal = sumPrincipal;
    }

    public int getSumVegetariano() {
        return sumVegetariano;
    }

    public void setSumVegetariano(int sumVegetariano) {
        this.sumVegetariano = sumVegetariano;
    }
}
