package br.ufop.ruapplicationpassivemvc.controller.implementation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.DishControllerListener;
import br.ufop.ruapplicationpassivemvc.adapter.DishAdapter;
import br.ufop.ruapplicationpassivemvc.controller.listener.DishServiceListener;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.service.DishService;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.Dialog;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;
import br.ufop.ruapplicationpassivemvc.util.ReplyAction;
import br.ufop.ruapplicationpassivemvc.view.DishView;

import java.util.List;

import retrofit2.Response;

public class DishController implements DishServiceListener, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {
    private TokenManager tokenManager;
    private int type;
    private DishAdapter adapter;
    private Dish removedDish;
    private DishView dishView;
    private DishControllerListener listener;


    public DishController(DishView dishView, DishControllerListener listener, int type) {
        this.dishView = dishView;
        this.listener = listener;
        this.type = type;
        tokenManager = TokenManager.getInstance(dishView.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
    }

    public void delete(int id) {
        DishService service = new DishService(this, tokenManager);
        service.delete(id);
        dishView.showDialog();
    }

    @Override
    public void onIndexSuccess(Response<List<Dish>> response) {
        List<Dish> data = response.body();
        if (adapter == null) {
            adapter = new DishAdapter(dishView.getContext(), data);
            dishView.setAdapter(adapter);
        } else {
            adapter.refresh(data);
        }
        dishView.showForm();
    }

    @Override
    public void onIndexError(Response<List<Dish>> response) {
        dishView.showForm();
    }

    @Override
    public void onActionSuccess(Response<Dish> response, int option) {
        if (option == Constants.DELETE) {
            Toast.makeText(dishView.getContext(), ReplyAction.replyActionSucess(option, removedDish.getName()), Toast.LENGTH_SHORT).show();
            onRefresh();
        }
        dishView.disposeDialog();
    }

    @Override
    public void onActionError(Response<Dish> response, int option) {
        if (option == Constants.DELETE) {
            Toast.makeText(dishView.getContext(), ReplyAction.replyActionError(option, response.message()), Toast.LENGTH_SHORT).show();
        }
        dishView.disposeDialog();
    }

    @Override
    public void onActionFailure(Throwable t, int option) {
        dishView.disposeDialog();
    }


    @Override
    public void onRefresh() {
        DishService service = new DishService(this, tokenManager);
        service.indexByType(type);
        dishView.showLoading();
    }

    @Override
    public void onItemClick(View view, int position) {
        Dish dish = adapter.getItem(position);
        listener.detailDish(dish);
    }

    @Override
    public void onItemLongClick(final View view, final int position) {

        if (tokenManager.getUser().getType() != Constants.CLIENT) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_detail);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            listener.editDish(adapter.getItem(position));
                            break;
                        case R.id.menu_delete:
                            Dialog.confimationDelete(view.getContext(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Networking.isNetworkConnected(view)) {
                                        removedDish = adapter.getItem(position);
                                        delete(adapter.getItem(position).getId());
                                    }
                                }
                            }, adapter.getItem(position).getName());


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

    public void teste() {

    }

}
