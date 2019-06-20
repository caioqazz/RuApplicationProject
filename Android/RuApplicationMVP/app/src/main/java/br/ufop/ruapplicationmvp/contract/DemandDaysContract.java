package br.ufop.ruapplicationmvp.contract;

import java.util.List;

import br.ufop.ruapplicationmvp.model.entity.Day;

public interface DemandDaysContract {
    interface Model {
        void index(final OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(List<Day> days);

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

        void onRefreshFinished(List<Day> days);

        void onFailure(String message);

        void onError(String message);

    }
}
