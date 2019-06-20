package br.ufop.ruapplicationmvvm.model.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Notice implements Serializable {
    @Json(name = "id")
    private int id;
    @Json(name = "user_id")
    private int userId;
    @Json(name = "subject")
    private String subject;
    @Json(name = "content")
    private String content;
    @Json(name = "views")
    private int views;
    @Json(name = "type")
    private int type;
    @Json(name = "updated_at")
    private String updated_at;
    @Json(name = "first_name")
    private String first_name;
    @Json(name = "visualized")
    private int visualized;

    public int getVisualized() {
        return visualized;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getViews() {
        return views;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
