package br.ufop.ruapplicationmvvm.model.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Dish implements Serializable {

    @Json(name = "id")
    private  int id;
    @Json(name = "type")
    private   int type;
    @Json(name = "name")
    private String name;
    @Json(name = "photo")
    private String photo;
    @Json(name = "description")
    private String description;
    @Json(name = "average")
    private Float average;

    public Dish(int id, int type, String name, String photo, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.photo = photo;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }


    public Float getAverage() {
        return average;
    }

}
