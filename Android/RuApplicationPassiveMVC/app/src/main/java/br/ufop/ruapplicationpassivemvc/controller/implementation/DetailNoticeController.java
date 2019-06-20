package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;

import br.ufop.ruapplicationpassivemvc.activity.listener.DetailNoticeControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.listener.NoticeServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Notice;
import br.ufop.ruapplicationpassivemvc.service.NoticeService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.DetailNoticeView;

import java.util.List;

import retrofit2.Response;

public class DetailNoticeController implements NoticeServiceListener {
    private DetailNoticeView view;
    private Notice notice;
    private DetailNoticeControllerListener listener;
    private TokenManager tokenManager;

    public DetailNoticeController(DetailNoticeView view, Notice notice, DetailNoticeControllerListener listener) {
        this.view = view;
        this.notice = notice;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
    }

    public void delete() {
        view.showLoading();
        NoticeService noticeService = new NoticeService(this, tokenManager);
        noticeService.delete(notice.getId());
    }

    public void setView() {
        NoticeService noticeService = new NoticeService(this, tokenManager);
        noticeService.setView(notice.getId());
    }

    @Override
    public void onActionSuccess(Response<Notice> response, int option) {
        view.showLoading();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, notice.getSubject()), Constants.SUCCESS);

    }

    @Override
    public void onActionError(Response<Notice> response, int option) {
        listener.onActionExecuted(ReplyAction.replyActionError(option, notice.getSubject()), Constants.ERROR);
        view.showLoading();
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showLoading();
    }

    @Override
    public void onIndexSuccess(Response<List<Notice>> response) {

    }

    @Override
    public void onIndexError(Response<List<Notice>> response) {

    }
}
