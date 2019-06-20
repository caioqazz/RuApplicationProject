package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import br.ufop.ruapplicationpassivemvc.activity.listener.DemandDaysControllerListener;
import br.ufop.ruapplicationpassivemvc.adapter.DayAdapter;
import br.ufop.ruapplicationpassivemvc.controller.listener.DayServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.service.DayService;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;
import br.ufop.ruapplicationpassivemvc.view.DemandDaysView;

import java.util.List;

import retrofit2.Response;

public class DemandDaysController implements DayServiceListener, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {

    private ApiService service;
    private TokenManager tokenManager;
    private DayAdapter adapter;
    private DemandDaysView view;
    private DemandDaysControllerListener listener;

    public DemandDaysController(DemandDaysView view, DemandDaysControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

        if (tokenManager.getToken() == null) {
            //unauthenticate
        }

        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }


    @Override
    public void onRefresh() {
        DayService dayService = new DayService(this, tokenManager);
        dayService.demandDayIndex();
        view.showLoading();
    }

    @Override
    public void onItemClick(View view, int position) {
        listener.detailDemand(adapter.getItem(position));
    }

    @Override
    public void onItemLongClick(View view, int position) {
        listener.detailDemand(adapter.getItem(position));
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
    }

    @Override
    public void onIndexSuccess(Response<List<Day>> response) {
        List<Day> data = response.body();
        if (adapter == null) {
            adapter = new DayAdapter(view.getContext(), data);
            view.setAdapter(adapter);
        } else {
            adapter.refresh(data);
        }

        view.showForm();
    }

    @Override
    public void onIndexError(Response<List<Day>> response) {
        view.showForm();
    }
}
