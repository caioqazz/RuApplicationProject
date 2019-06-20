package br.ufop.ruapplicationmvp.service;

import br.ufop.ruapplicationmvp.contract.FormNoticeContract;
import br.ufop.ruapplicationmvp.model.entity.Notice;
import br.ufop.ruapplicationmvp.service.api.ApiService;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormNoticeService implements FormNoticeContract.Model {
    private ApiService service;

    public FormNoticeService(TokenManager tokenManager) {
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @Override
    public void insert(OnFinishedListener onFinishedListener, String subject, String content, int type) {
        Call<Notice> call = service.newAviso(subject, content, type);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onFinished(response.body());
                } else {
                    onFinishedListener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
                onFinishedListener.onFailure(t.getMessage());
            }
        });
    }
}
