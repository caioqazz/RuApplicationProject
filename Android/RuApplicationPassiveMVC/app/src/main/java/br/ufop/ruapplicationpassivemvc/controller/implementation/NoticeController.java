package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.NoticeControllerListener;
import br.ufop.ruapplicationpassivemvc.adapter.NoticeAdapter;
import br.ufop.ruapplicationpassivemvc.controller.listener.NoticeServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Notice;
import br.ufop.ruapplicationpassivemvc.service.NoticeService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.Dialog;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.NoticeView;

import java.util.List;

import retrofit2.Response;

public class NoticeController implements View.OnClickListener, NoticeServiceListener, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {

    private TokenManager tokenManager;
    private NoticeAdapter adapter;
    private NoticeView noticeView;
    private NoticeControllerListener listener;
    private Notice removedNotice;


    public NoticeController(NoticeView noticeView, NoticeControllerListener listener) {
        this.noticeView = noticeView;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(noticeView.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

    }

    public void delete(int id) {
        noticeView.showDialog();
        NoticeService service = new NoticeService(this, tokenManager);
        service.delete(id);

    }


    @Override
    public void onActionSuccess(Response<Notice> response, int option) {
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, removedNotice.getSubject()), Constants.SUCCESS);
        if (option == Constants.DELETE) {
            onRefresh();
        }
        noticeView.disposeDialog();
    }

    @Override
    public void onActionError(Response<Notice> response, int option) {
        listener.onActionExecuted(ReplyAction.replyActionError(option, removedNotice.getSubject()), Constants.ERROR);
        noticeView.disposeDialog();
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        listener.onActionExecuted(t.getMessage(), Constants.FAILURE);
        noticeView.disposeDialog();
        noticeView.showForm();
    }

    @Override
    public void onIndexSuccess(Response<List<Notice>> response) {
        List<Notice> data = response.body();
        if (adapter == null) {
            adapter = new NoticeAdapter(data, noticeView.getContext());
            noticeView.setAdapter(adapter);
        } else {
            adapter.refresh(data);
        }
        noticeView.showForm();

    }

    @Override
    public void onIndexError(Response<List<Notice>> response) {
        listener.onActionExecuted(response.message(), Constants.ERROR);
        noticeView.showForm();
    }


    @Override
    public void onClick(View v) {
        listener.newNotice();
    }

    @Override
    public void onRefresh() {
        if (Networking.isNetworkConnected(noticeView)) {
            NoticeService noticeService = new NoticeService(this, tokenManager);
            noticeService.index();
            noticeView.showLoading();

        }
    }


    @Override
    public void onItemClick(View view, int position) {
        Notice notice = adapter.getItem(position);
        listener.noticeDetail(notice);

    }

    @Override
    public void onItemLongClick(final View view, final int position) {
        if (tokenManager.getUser().getType() != Constants.CLIENT) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_delete);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.action_delete:
                            Dialog.confimationDelete(view.getContext(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Networking.isNetworkConnected(view)) {
                                        removedNotice = adapter.getItem(position);
                                        delete(adapter.getItem(position).getId());
                                    }
                                }
                            }, adapter.getItem(position).getSubject());


                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    }


}