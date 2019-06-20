package br.ufop.ruapplicationmvvm.service;

import br.ufop.ruapplicationmvvm.model.response.MenuResponse;
import br.ufop.ruapplicationmvvm.model.result.MenuResult;
import br.ufop.ruapplicationmvvm.service.api.ApiService;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.listener.MenuListener;
import br.ufop.ruapplicationmvvm.util.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuService {

    private ApiService service;

    public MenuService(TokenManager tokenManager) {
        this.service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    public void getMenu(MenuListener.onMenuFinished listener, int type, String date) {
        Call<MenuResponse> call = service.menus(type, date);
        call.enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                if (response.isSuccessful()) {
                    listener.onRequestFinished(new MenuResult(Status.SUCCESS, response.body(), null, null));
                } else {
                    listener.onRequestFinished(new MenuResult(Status.ERROR, null, response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                listener.onRequestFinished(new MenuResult(Status.FAILURE, null, null, t.getMessage()));
            }
        });
    }

}
