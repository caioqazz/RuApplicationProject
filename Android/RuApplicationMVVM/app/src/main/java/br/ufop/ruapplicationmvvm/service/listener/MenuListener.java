package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.MenuResult;

public interface MenuListener {
    interface onMenuFinished{
        void onRequestFinished(MenuResult result);
    }
}
