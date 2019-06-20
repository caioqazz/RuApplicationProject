package br.ufop.ruapplicationmvp.view;

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
import br.ufop.ruapplicationmvp.contract.DayMealContract;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.model.entity.Week;
import br.ufop.ruapplicationmvp.presenter.DayMealPresenter;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.NameDate;
import br.ufop.ruapplicationmvp.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvp.view.adapter.DayAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class DayMealActivity extends AppCompatActivity implements DayMealContract.View, RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.day_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.day_recyclerview)
    RecyclerView mRecyclerView;
    private Week week;
    private DayAdapter mAdapter;
    private DayMealPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        initiate();
        ButterKnife.bind(this);
        mPresenter = new DayMealPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE), week);

        mPresenter.onRefresh();

        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("week") != null) {
            week = (Week) mBundle.get("week");
            String title = week.getWeekOfMonth() + "ª Semana de " + NameDate.monthName(week.getMonth());
            setTitle(title);
            mBundle.clear();
        }
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
    public void onRefreshFinished(List<Day> days) {
        if (mAdapter == null) {
            mAdapter = new DayAdapter(this, days);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(days);
        }
    }


    private void setAdapter(DayAdapter mAdapter) {
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
    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("day", mAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getUser().getType() != Constants.CLIENT) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_delete);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.action_delete:
                            delete(mAdapter.getItem(position));
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
    public void delete(Day day) {
        Intent intent = new Intent(this, FormCalendarActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
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
