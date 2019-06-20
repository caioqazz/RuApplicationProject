package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.CalendarControllerListener;
import br.ufop.ruapplicationpassivemvc.adapter.CalendarAdapter;
import br.ufop.ruapplicationpassivemvc.controller.listener.MealServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Meal;
import br.ufop.ruapplicationpassivemvc.service.MealService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;
import br.ufop.ruapplicationpassivemvc.view.CalendarView;

import java.util.List;

import retrofit2.Response;

public class CalendarController implements View.OnClickListener, MealServiceListener, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {

    private TokenManager tokenManager;
    private CalendarAdapter adapter;
    private CalendarView view;
    private CalendarControllerListener listener;

    public CalendarController(CalendarView view, CalendarControllerListener listener) {
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
    }


    @Override
    public void onRefresh() {
        MealService service = new MealService(this, tokenManager);
        service.indexDaysClosed();
        view.showLoading();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(final View view, final int position) {
        if (tokenManager.getUser().getType() != 1) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_detail);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            if (Networking.isNetworkConnected(view))
                                listener.form(adapter.getItem(position));
                            break;
                        case R.id.menu_delete:
                            if (Networking.isNetworkConnected(view))
                                listener.form(adapter.getItem(position));
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


    @Override
    public void onIndexSuccess(Response<List<Meal>> response) {
        List<Meal> data = response.body();
        if (adapter == null) {
            adapter = new CalendarAdapter(view.getContext(), data);
            view.setAdapter(adapter);
        } else {
            adapter.refresh(data);
        }
        view.showForm();
    }

    @Override
    public void onIndexError(Response<List<Meal>> response) {

    }


    @Override
    public void onActionFailure(Throwable t, int option) {

    }

    @Override
    public void onActionSuccess(Response<Meal> response, int option) {

    }

    @Override
    public void onActionError(Response<Meal> response, int option) {

    }
}
