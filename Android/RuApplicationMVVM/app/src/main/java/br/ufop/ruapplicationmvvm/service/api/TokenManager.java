package br.ufop.ruapplicationmvvm.service.api;

import android.content.SharedPreferences;

import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.model.response.AccessToken;


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


    public void saveToken(AccessToken token) {
        editor.putString("ACCESS_TOKEN", token.getAccessToken()).commit();
        editor.putString("REFRESH_TOKEN", token.getRefreshToken()).commit();
    }

    public void deleteToken() {
        editor.remove("ACCESS_TOKEN").commit();
        editor.remove("REFRESH_TOKEN").commit();
        UserManager.getInstance(prefs).deleteUser();
    }

    public void saveUser(User user) {
        UserManager.getInstance(prefs).saveUser(user);
    }

    public AccessToken getToken() {
        AccessToken token = new AccessToken();
        token.setAccessToken(prefs.getString("ACCESS_TOKEN", null));
        token.setRefreshToken(prefs.getString("REFRESH_TOKEN", null));
        return token;
    }


}
