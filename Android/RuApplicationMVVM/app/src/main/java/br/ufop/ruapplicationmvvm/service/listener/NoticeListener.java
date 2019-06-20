package br.ufop.ruapplicationmvvm.service.listener;

import br.ufop.ruapplicationmvvm.model.result.NoticeResult;
import br.ufop.ruapplicationmvvm.model.result.NoticesResult;

public interface NoticeListener {
    interface OnFinished {
        void onRequestFinished(NoticeResult result);
    }

    interface OnListFinished {
        void onRequestFinished(NoticesResult result);
    }
}
