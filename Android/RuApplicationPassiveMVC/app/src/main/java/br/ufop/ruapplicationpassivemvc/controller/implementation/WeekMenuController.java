package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import br.ufop.ruapplicationpassivemvc.activity.listener.WeekMenuControllerListener;
import br.ufop.ruapplicationpassivemvc.adapter.WeekMenuAdapter;
import br.ufop.ruapplicationpassivemvc.controller.listener.WeekServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Week;
import br.ufop.ruapplicationpassivemvc.service.WeekService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;
import br.ufop.ruapplicationpassivemvc.view.WeekMenuView;

import java.util.List;

import retrofit2.Response;

public class WeekMenuController implements WeekServiceListener, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {

    private TokenManager tokenManager;
    private WeekMenuAdapter adapter;
    private WeekMenuView view;
    private WeekMenuControllerListener listener;

    public WeekMenuController(WeekMenuView view, WeekMenuControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

    }


    @Override
    public void onRefresh() {
        WeekService service = new WeekService(this, tokenManager);
        service.index();
        view.showLoading();
    }

    @Override
    public void onItemClick(View view, int position) {

        listener.days(adapter.getItem(position));
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onIndexSuccess(Response<List<Week>> response) {
        List<Week> data = response.body();
        if (adapter == null) {
            adapter = new WeekMenuAdapter(view.getContext(), data);
            view.setAdapter(adapter);
        } else {
            adapter.refresh(data);
        }

        view.showForm();
    }

    @Override
    public void onIndexError(Response<List<Week>> response) {
        view.showForm();
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
    }
}
