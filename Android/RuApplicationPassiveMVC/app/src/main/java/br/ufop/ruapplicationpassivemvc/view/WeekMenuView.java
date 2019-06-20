package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.adapter.WeekMenuAdapter;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;

public class WeekMenuView extends FrameLayout {

    public WeekMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(SwipeRefreshLayout.OnRefreshListener onRefreshListener, RecyclerItemClickListener.OnItemClickListener onItemClickListener) {
        ((SwipeRefreshLayout) findViewById(R.id.week_menu_swipe_refresh)).setOnRefreshListener(onRefreshListener);
        RecyclerView mRecyclerView = findViewById(R.id.week_menu_recyclerview);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, onItemClickListener));
    }

    public void showLoading() {
        TransitionManager.beginDelayedTransition(this);
        ((SwipeRefreshLayout) findViewById(R.id.week_menu_swipe_refresh)).setRefreshing(true);
    }

    public void showForm() {
        TransitionManager.beginDelayedTransition(this);
        ((SwipeRefreshLayout) findViewById(R.id.week_menu_swipe_refresh)).setRefreshing(false);
    }

    public void setAdapter(WeekMenuAdapter adapter) {
        RecyclerView mRecyclerView = findViewById(R.id.week_menu_recyclerview);
        mRecyclerView.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

}
