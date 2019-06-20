package br.ufop.ruapplicationmvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import br.ufop.ruapplicationmvvm.databinding.ActivityDayBinding;
import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.model.entity.Week;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.NameDate;
import br.ufop.ruapplicationmvvm.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.view.adapter.DayAdapter;
import br.ufop.ruapplicationmvvm.viewmodel.DayMealViewModel;
import es.dmoral.toasty.Toasty;


public class DayMealActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private Week week;
    private DayAdapter mAdapter;
    private DayMealViewModel mViewModel;
    private ActivityDayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_day);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initiate();

        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(DayMealViewModel.class);
        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onRefreshFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });


        binding.daySwipeRefresh.setOnRefreshListener(this);
        mViewModel.getDays(week.getWeekOfMonth(), week.getMonth(), week.getYear());
    }


    private void onLoading(boolean b) {
        if (b)
            binding.daySwipeRefresh.setRefreshing(true);
        else
            binding.daySwipeRefresh.setRefreshing(false);
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


    private void onRefreshFinished(List<Day> days) {

        if (mAdapter == null) {
            mAdapter = new DayAdapter(this, days);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(days);
        }
    }


    private void setAdapter(DayAdapter mAdapter) {
        binding.dayRecyclerview.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        binding.dayRecyclerview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        binding.dayRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(this, binding.dayRecyclerview, this));
        binding.dayRecyclerview.setLayoutManager(mLinearLayoutManager);
        binding.dayRecyclerview.setAdapter(mAdapter);
    }

    private void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }


    private void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
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


    private void delete(Day day) {
        Intent intent = new Intent(this, FormCalendarActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
    }


    @Override
    public void onRefresh() {
        mViewModel.getDays(week.getWeekOfMonth(), week.getMonth(), week.getYear());
    }


}
