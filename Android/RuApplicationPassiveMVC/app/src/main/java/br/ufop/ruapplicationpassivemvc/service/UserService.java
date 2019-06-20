package br.ufop.ruapplicationpassivemvc.service;

import br.ufop.ruapplicationpassivemvc.controller.listener.UserServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    ApiService service;
    Call<User> call;
    UserServiceListener listener;

    public UserService(UserServiceListener listener, TokenManager tokenManager) {
        this.service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
        this.listener = listener;
    }

    public void getUser() {
        call = service.user();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.SHOW);
                } else {
                    listener.onActionError(response, Constants.SHOW);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onActionFailure(t, Constants.SHOW);
            }
        });

    }

    public void updateUser(String name, MultipartBody.Part photo) {
        Call<User> call = service.updateUser(photo, name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    listener.onActionSuccess(response, Constants.UPDATE);
                } else {
                    listener.onActionError(response, Constants.UPDATE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onActionFailure(t, Constants.UPDATE);
            }
        });
    }
}
