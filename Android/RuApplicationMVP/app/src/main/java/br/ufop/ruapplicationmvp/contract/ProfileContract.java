package br.ufop.ruapplicationmvp.contract;

import android.graphics.Bitmap;

import br.ufop.ruapplicationmvp.model.entity.User;
import okhttp3.MultipartBody;

public interface ProfileContract {
    interface Model {
        void update(final OnFinishedListener onFinishedListener, String name, MultipartBody.Part photo);

        interface OnFinishedListener {
            void onFinished(User user);

            void onError(String errorMsg);

            void onFailure(Throwable t);
        }
    }

    interface View {

        void showProgress();

        void hideProgress();

        void onError(String message);

        void onFailure(Throwable t);

        void loadUser(User user);

        void loadPhoto(Bitmap bitmap);

        void onSuccess(User user);
    }

    interface Presenter {
        void onDestroy();

        void onSendButtonClick(String name, MultipartBody.Part photo);
    }
}
