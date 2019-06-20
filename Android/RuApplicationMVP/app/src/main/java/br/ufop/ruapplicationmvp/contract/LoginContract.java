package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.response.AccessToken;
import retrofit2.Response;

public interface LoginContract {

    interface Model {
        void authentication(final OnFinishedListener onFinishedListener, String email, String password);

        interface OnFinishedListener {
            void onFinished(AccessToken accessToken);

            void onError(Response<AccessToken> response);

            void onFailure(Throwable t);
        }
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setErrorOnEmail(String errorMsg);

        void setErrorOnPassword(String errorMsg);

        void onFailure(Throwable t);

        void onSuccessLogin();


        void onLoginError(String message);
    }

    interface Presenter {
        void onDestroy();

        void onLoginButtonClick(String username, String password);
    }
}
