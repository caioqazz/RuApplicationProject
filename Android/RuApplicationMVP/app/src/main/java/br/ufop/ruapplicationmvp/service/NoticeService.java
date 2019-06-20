package br.ufop.ruapplicationmvp.service;

import java.util.List;

import br.ufop.ruapplicationmvp.contract.NoticeContract;
import br.ufop.ruapplicationmvp.model.entity.Notice;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeService implements NoticeContract.Model {

    private ApiService service;

    public NoticeService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void index(OnFinishedListener onFinishedListener) {
        Call<List<Notice>> callIndex = service.notices();
        callIndex.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message(), Constants.GET);
                }
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.GET);
            }
        });

    }

    @Override
    public void delete(OnFinishedListener onFinishedListener, int id) {
        Call<Notice> call = service.deleteAviso(id);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinishedDelete();
                } else {
                    onFinishedListener.onError(response.message(), Constants.DELETE);
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage(), Constants.DELETE);
            }
        });
    }
}
