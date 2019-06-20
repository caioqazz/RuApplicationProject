package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.DayControllerListener;
import br.ufop.ruapplicationpassivemvc.adapter.DayAdapter;
import br.ufop.ruapplicationpassivemvc.controller.listener.DayServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.model.entity.Week;
import br.ufop.ruapplicationpassivemvc.service.DayService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;
import br.ufop.ruapplicationpassivemvc.view.DayView;

import java.util.List;

import retrofit2.Response;

public class MealDayController implements DayServiceListener, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {


    private TokenManager tokenManager;
    private DayAdapter adapter;
    private DayView view;
    private Week week;
    private DayControllerListener listener;

    public MealDayController(DayView view, Week week, DayControllerListener listener) {
        this.week = week;
        this.view = view;
        this.listener = listener;
        tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));

    }

    @Override
    public void onRefresh() {
        DayService service = new DayService(this, tokenManager);
        service.mealDayIndex(week.getWeekOfMonth(), week.getMonth(), week.getYear());
        view.showLoading();
    }

    @Override
    public void onItemClick(View view, int position) {
        listener.menu(adapter.getItem(position));
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        if (tokenManager.getUser().getType() != Constants.CLIENT) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_delete);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.action_delete:
                            listener.delete(adapter.getItem(position));
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

    @Override
    public void onActionFailure(Throwable t, int option) {
        view.showForm();
    }
}
