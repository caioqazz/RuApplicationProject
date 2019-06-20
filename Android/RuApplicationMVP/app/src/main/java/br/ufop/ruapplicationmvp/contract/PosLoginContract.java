package br.ufop.ruapplicationmvp.contract;

import br.ufop.ruapplicationmvp.model.entity.User;

public interface PosLoginContract {
    interface Model {
        void getUser(final OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(User user);

            void onError();

            void onFailure();
        }
    }

    interface View {

        void onSuccess(User user);


    }

    interface Presenter {
        void getUser();

        void onDestroy();
    }
}
