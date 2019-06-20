package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Dish;
import okhttp3.MultipartBody;

public interface FormDishContract {
    interface Model {
        void update(final OnFinishedListener onFinishedListener, MultipartBody.Part photo, int id, String name, int type, String description);

        void insert(final OnFinishedListener onFinishedListener, MultipartBody.Part photo, String name, int type, String description);

        interface OnFinishedListener {
            void onFinished(Dish dish, int option);

            void onError(String message, int option);

            void onFailure(String message, int option);
        }
    }

    interface Presenter {
        void onDestroy();

        boolean validate(String name, int type, String description);

        void insert(MultipartBody.Part photo, String name, int type, String description);

        void update(MultipartBody.Part photo, int id, String name, int type, String description);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onCommitFinished(Dish dish, int option);

        void onFailure(String message, int option);

        void onError(String message, int option);

    }
}
