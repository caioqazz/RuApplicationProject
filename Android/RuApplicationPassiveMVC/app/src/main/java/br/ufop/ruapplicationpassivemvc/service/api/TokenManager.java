package br.ufop.ruapplicationpassivemvc.service.api;

import android.content.SharedPreferences;

import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.model.response.AccessToken;


public class TokenManager {

    private static TokenManager INSTANCE = null;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private TokenManager(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized TokenManager getInstance(SharedPreferences prefs) {
        if (INSTANCE == null) {
            INSTANCE = new TokenManager(prefs);
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

    public void saveToken(AccessToken token) {
        editor.putString("ACCESS_TOKEN", token.getAccessToken()).commit();
        editor.putString("REFRESH_TOKEN", token.getRefreshToken()).commit();
    }

    public void deleteToken() {
        editor.remove("ACCESS_TOKEN").commit();
        editor.remove("REFRESH_TOKEN").commit();
        deleteUser();
    }

    public AccessToken getToken() {
        AccessToken token = new AccessToken();
        token.setAccessToken(prefs.getString("ACCESS_TOKEN", null));
        token.setRefreshToken(prefs.getString("REFRESH_TOKEN", null));
        return token;
    }


}
