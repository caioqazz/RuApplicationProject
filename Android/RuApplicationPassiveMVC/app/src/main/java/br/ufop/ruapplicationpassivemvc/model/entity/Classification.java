package br.ufop.ruapplicationpassivemvc.model.entity;

import com.squareup.moshi.Json;

public class Classification {

    @Json(name = "option")
    private int option;
    @Json(name = "comment")
    private String comment;
    @Json(name = "rating")
    private  Float rating;

    @Json(name = "dish_id")
    private int id;

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Classification{" +
                "option=" + option +
                ", comment=" + comment +
                ", rating=" + rating +
                ", id=" + id +
                '}';
    }
}
