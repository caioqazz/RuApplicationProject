package br.ufop.ruapplicationmvp.contract;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.Dish;

public interface FormMenuContract {
    interface Model {
        void insert(final OnFinishedListener onFinishedListener, String date, int type, int dishId, int dishType);

        void update(final OnFinishedListener onFinishedListener, String date, int type, int dishId, int dishType);

        void indexByType(final OnFinishedListener onFinishedListener, int type);

        interface OnFinishedListener {
            void onFinished(List<Dish> dishes);

            void onFinished(Dish dish);

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        void insert(String date, int type, int dishId, int dishType);

        void update(String date, int type, int dishId, int dishType);

        void getDishes(int dishType);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onFinished(List<Dish> dishes);

        void onFinished(Dish dish);

        void onFailure(String message);

        void onError(String message);

    }
}
