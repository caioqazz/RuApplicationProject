package br.ufop.ruapplicationpassivemvc.model.entity;

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

    public void setVisualized(int visualized) {
        this.visualized = visualized;
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

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
