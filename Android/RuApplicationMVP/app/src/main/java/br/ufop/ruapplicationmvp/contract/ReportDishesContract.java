package br.ufop.ruapplicationmvp.contract;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.model.entity.ReportDish;

public interface ReportDishesContract {
    interface Model {
        void reportDish(final OnFinishedListener onFinishedListener, int id);

        void index(final OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(ReportDish response);

            void onFinishedIndex(List<Dish> dishes);

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        void getDishes();

        void onCommitButtonClick(int id);

    }

    interface View {
        void showProgress();

        void hideProgress();

        void onFinished(ReportDish response);

        void onFinishedIndex(List<Dish> dishes);

        void onFailure(String message);

        void onError(String message);

    }
}
