package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.Meal;

public interface FormCalendarContract {
    interface Model {
        void update(final OnFinishedListener onFinishedListener, String date, int type, int status, String reason);

        interface OnFinishedListener {
            void onFinished(Meal meal);

            void onError(String message);

            void onFailure(String message);
        }
    }

    interface Presenter {
        void onDestroy();

        boolean validate(String date, int type, int status, String reason);

        void onCommitButton(String date, int type, int status, String reason);
    }

    interface View {
        void showProgress();

        void hideProgress();

        void onCommitFinished(Meal meal);

        void onFailure(String message);

        void onError(String message);

    }
}
