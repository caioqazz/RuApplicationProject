package br.ufop.ruapplicationpassivemvc.model.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Feedback implements Serializable {
    @Json(name = "id")
    private int id;
    @Json(name = "name")
    private String name;
    @Json(name = "updated_at")
    private  String date;
    @Json(name = "photo")
    private String photo;
    @Json(name = "subject")
    private String subject;
    @Json(name = "content")
    private String content;
    @Json(name = "reply")
    private String reply;
    @Json(name = "manager_view")
    private int managerView;
    @Json(name = "user_view")
    private int userView;


    public int getManagerViewed() {
        return managerView;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getManagerView() {
        return managerView;
    }

    public void setManagerView(int managerView) {
        this.managerView = managerView;
    }

    public void setUserView(int userView) {
        this.userView = userView;
    }

    public int getUserView() {
        return userView;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getReply() {
        return reply;
    }

}
