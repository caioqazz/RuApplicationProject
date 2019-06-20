package br.ufop.ruapplicationmvp.service.api;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvp.model.entity.User;

public class UserManager {

    private static UserManager INSTANCE = null;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private UserManager(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized UserManager getInstance(SharedPreferences prefs) {
        if (INSTANCE == null) {
            INSTANCE = new UserManager(prefs);
        }
        return INSTANCE;


    }

    public void saveUser(User user) {
        editor.putString("name", user.getName()).commit();
        editor.putString("email", user.getEmail()).commit();
        editor.putInt("id", user.getId()).commit();
        editor.putString("CPF", user.getCpf()).commit();
        editor.putInt("type", user.getType()).commit();
        editor.putString("photo", user.getPhoto()).commit();

    }

    public void deleteUser() {
        editor.remove("name").commit();
        editor.remove("email").commit();
        editor.remove("id").commit();
        editor.remove("CPF").commit();
        editor.remove("type").commit();
        editor.remove("photo").commit();
    }

    public User getUser() {
        User user = new User();
        user.setCpf(prefs.getString("CPF", null));
        user.setEmail(prefs.getString("email", null));
        user.setId(prefs.getInt("id", 0));
        user.setName(prefs.getString("name", null));
        user.setType(prefs.getInt("type", 0));
        user.setPhoto(prefs.getString("photo", null));
        return user;

    }
}
