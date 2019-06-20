package br.ufop.ruapplicationmvp.contract;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.Meal;

public interface CalendarContract {
    interface Model {
        void index(final OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(List<Meal> meals);

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        void onRefresh();

    }

    interface View {
        void showProgress();

        void hideProgress();

        void onRefreshFinished(List<Meal> meals);

        void onFailure(String message);

        void onError(String message);

    }
}
