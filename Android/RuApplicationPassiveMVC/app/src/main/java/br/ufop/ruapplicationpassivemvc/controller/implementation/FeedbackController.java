package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.FeedbackControllerListener;
import br.ufop.ruapplicationpassivemvc.adapter.FeedbackAdapter;
import br.ufop.ruapplicationpassivemvc.controller.listener.FeedbackServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;
import br.ufop.ruapplicationpassivemvc.service.FeedbackService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.Dialog;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.FeedbackView;

import java.util.List;

import retrofit2.Response;

public class FeedbackController implements FeedbackServiceListener, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {

    private final String TAG = "Feedback";
    TokenManager tokenManager;
    FeedbackView view;
    FeedbackControllerListener listener;
    FeedbackAdapter adapter;
    private Feedback removed;

    public FeedbackController(FeedbackView view, FeedbackControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));


    }

    public void delete(int id) {
        //showDialog
        FeedbackService service = new FeedbackService(this, tokenManager);
        service.delete(id);
    }

    @Override
    public void onActionSuccess(Response<Feedback> response, int option) {
        onRefresh();
        listener.onActionExecuted(ReplyAction.replyActionSucess(option, removed.getSubject()), Constants.SUCCESS);
    }

    @Override
    public void onActionError(Response<Feedback> response, int option) {
        listener.onActionExecuted(ReplyAction.replyActionError(option, removed.getSubject()), Constants.ERROR);
    }

    @Override
    public void onActionFailure(Throwable t, int option) {

    }

    @Override
    public void onIndexSuccess(Response<List<Feedback>> response) {
        List<Feedback> feedbacks = response.body();
        if (adapter == null) {
            if (!feedbacks.isEmpty()) {
                adapter = new FeedbackAdapter(view.getContext(), feedbacks);
                view.setAdapter(adapter);
            }
        } else {
            adapter.refresh(feedbacks);
        }
        view.showForm();
    }

    @Override
    public void onIndexError(Response<List<Feedback>> response) {

    }

    @Override
    public void onRefresh() {
        view.showLoading();
        FeedbackService feedbackService = new FeedbackService(this, tokenManager);
        if (tokenManager.getUser().getType() == Constants.MANAGER) {
            feedbackService.index();
        } else {
            feedbackService.indexByUser();
        }

    }


    @Override
    public void onItemClick(View view, int position) {
        Feedback feedback = adapter.getItem(position);
        listener.feedbackDetail(feedback);


    }

    @Override
    public void onItemLongClick(final View view, final int position) {
        if (tokenManager.getUser().getType() == Constants.CLIENT) {
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
                                        removed = adapter.getItem(position);
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
