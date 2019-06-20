package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.result.UserResult;
import br.ufop.ruapplicationmvvm.service.UserAuthService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.UserListener;
import br.ufop.ruapplicationmvvm.util.Status;
import okhttp3.MultipartBody;

public class ProfileViewModel extends ViewModel implements UserListener.OnFinished {
    private UserAuthService service;
    private MediatorLiveData<UserResult> result;

    public ProfileViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new UserAuthService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<UserResult> getResult() {
        return result;
    }

    public void store(String name, MultipartBody.Part photo) {
        result.setValue(new UserResult(Status.LOADING, null, null, null));
        service.update(this, name, photo);
    }

    @Override
    public void onRequestFinished(UserResult result) {
        this.result.setValue(result);
    }
}
