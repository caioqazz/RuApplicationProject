package br.ufop.ruapplicationmvvm.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityDishBinding;
import br.ufop.ruapplicationmvvm.model.entity.Dish;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Networking;
import br.ufop.ruapplicationmvvm.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.view.adapter.DishAdapter;
import br.ufop.ruapplicationmvvm.viewmodel.DishViewModel;
import es.dmoral.toasty.Toasty;


public class DishActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private int type = 0;
    private DishAdapter mAdapter;
    private DishViewModel mViewModel;
    private AlertDialog mDialog;
    private ActivityDishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dish);
        setTitle("Pratos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initiate();

        mDialog = DialogManager.loadingDialog(this);
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(DishViewModel.class);


        mViewModel.getResult().observe(this, result -> {
            onDeleteLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onFinishedDelete();
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });

        mViewModel.getListResult().observe(this, result -> {
            onListLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onRefreshFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        binding.dishSwipeRefresh.setOnRefreshListener(this);

        mViewModel.getDishes(type);
    }

    private void onListLoading(boolean b) {
        if (b)
            binding.dishSwipeRefresh.setRefreshing(true);
        else
            binding.dishSwipeRefresh.setRefreshing(false);
    }

    private void onDeleteLoading(boolean b) {
        if (b)
            mDialog.show();
        else
            mDialog.hide();
    }


    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            type = mBundle.getInt("category_dish_type");
            mBundle.clear();
        }
    }


    public void editDish(Dish dish) {
//        Intent intent = new Intent(this, FormDishActivity.class);
//        intent.putExtra("dish", dish);
//        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void onFinishedDelete() {
        Toasty.success(this, "Deletado!", Toasty.LENGTH_LONG).show();
        mViewModel.getDishes(type);
    }

    public void onRefreshFinished(List<Dish> dishes) {

        if (mAdapter == null) {
            mAdapter = new DishAdapter(this, dishes);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(dishes);
        }

    }

    private void setAdapter(DishAdapter mAdapter) {
        binding.dishRecyclerview.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        binding.dishRecyclerview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        binding.dishRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(this, binding.dishRecyclerview, this));
        binding.dishRecyclerview.setLayoutManager(mLinearLayoutManager);
        binding.dishRecyclerview.setAdapter(mAdapter);
    }

    private void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    private void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, DetailDishActivity.class);
        intent.putExtra("dish", mAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getUser().getType() != Constants.CLIENT) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_detail);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            editDish(mAdapter.getItem(position));
                            break;
                        case R.id.menu_delete:
                            DialogManager.confimationDelete(view.getContext(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Networking.isNetworkConnected(DishActivity.this)) {
                                        mViewModel.delete(mAdapter.getItem(position).getId());
                                    }
                                }
                            }, mAdapter.getItem(position).getName());


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
    public void onRefresh() {

        mViewModel.getDishes(type);
    }


}
