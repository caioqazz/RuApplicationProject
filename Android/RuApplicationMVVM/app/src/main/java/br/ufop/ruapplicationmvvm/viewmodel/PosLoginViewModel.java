package br.ufop.ruapplicationmvvm.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.UserAuthService;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosLoginViewModel extends ViewModel {
    private MediatorLiveData<User> result;
    private UserAuthService service;

    public PosLoginViewModel(SharedPreferences prefs) {
        result = new MediatorLiveData<>();
        service = new UserAuthService(TokenManager.getInstance(prefs));
    }

    public MediatorLiveData<User> getResult() {
        return result;
    }

    public void getUser() {
        service.getUser(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful())
                    result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
