package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.response.AccessToken;
import br.ufop.ruapplicationmvp.model.response.ApiError;
import retrofit2.Response;

public interface RegisterContract {
    interface Model {
        void register(final OnFinishedListener listener, String name, String email, String cpf, String password);

        interface OnFinishedListener {
            void onFinished(AccessToken accessToken);

            void onError(Response<AccessToken> response);

            void onFailure(Throwable t);
        }
    }

    interface Presenter {
        void onCommitButtonClick(String name, String email, String cpf, String password);

        void onDestroy();
    }

    interface View {
        void onRegisterSuccess();

        void handleFieldError(ApiError apiError);

        void showProgress();

        void hideProgress();

        void onFailure(Throwable t);

        void onRegisterError(String erroMsg);
    }
}
