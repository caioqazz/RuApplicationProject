package br.ufop.ruapplicationpassivemvc.model.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class User implements Serializable {
    @Json(name = "id")
    private int id;
    @Json(name = "name")
    private String name;
    @Json(name = "email")
    private String email;
    @Json(name = "cpf")
    private String cpf;
    @Json(name = "photo")
    private String photo;
    @Json(name = "type")
    private int type;


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
