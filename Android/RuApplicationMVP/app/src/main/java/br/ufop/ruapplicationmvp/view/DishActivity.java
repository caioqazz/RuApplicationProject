package br.ufop.ruapplicationmvp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.DishContract;
import br.ufop.ruapplicationmvp.model.entity.Dish;
import br.ufop.ruapplicationmvp.presenter.DishPresenter;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.Networking;
import br.ufop.ruapplicationmvp.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvp.view.adapter.DishAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class DishActivity extends AppCompatActivity implements DishContract.View, RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.dish_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.dish_recyclerview)
    RecyclerView mRecyclerView;
    private int type = 0;
    private DishAdapter mAdapter;
    private DishPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        setTitle("Pratos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        initiate();
        mPresenter = new DishPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE), type);
        mPresenter.onRefresh();
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            type = mBundle.getInt("category_dish_type");
            mBundle.clear();
        }
    }


    public void editDish(Dish dish) {
        Intent intent = new Intent(this, FormDishActivity.class);
        intent.putExtra("dish", dish);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFinishedDelete() {
        Toasty.success(this, "Deletado!", Toasty.LENGTH_LONG).show();
        mPresenter.onRefresh();
    }

    @Override
    public void onRefreshFinished(List<Dish> dishes) {
        if (mAdapter == null) {
            mAdapter = new DishAdapter(this, dishes);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(dishes);
        }

    }

    private void setAdapter(DishAdapter mAdapter) {
        mRecyclerView.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, this));
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onFailure(String message, int option) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message, int option) {
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
                                        mPresenter.onDeleteButtonClick(mAdapter.getItem(position).getId());
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

        mPresenter.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
