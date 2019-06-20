package br.ufop.ruapplicationmvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.WeekMenuContract;
import br.ufop.ruapplicationmvp.model.entity.Week;
import br.ufop.ruapplicationmvp.presenter.WeekMenuPresenter;
import br.ufop.ruapplicationmvp.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvp.view.DayMealActivity;
import br.ufop.ruapplicationmvp.view.adapter.WeekMenuAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class WeekMenuFragment extends Fragment
        implements WeekMenuContract.View, RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.week_menu_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.week_menu_recyclerview)
    RecyclerView mRecyclerView;
    private WeekMenuAdapter mAdapter;
    private WeekMenuPresenter mPresenter;

    public WeekMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Cardápios");
        ButterKnife.bind(this, view);
        mPresenter = new WeekMenuPresenter(this, getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));

        mPresenter.onRefresh();
        mRefreshLayout.setOnRefreshListener(this);
    }

//
//    @Override
//    public void days(Week week) {
//        Intent intent = new Intent(getContext(), DayMealActivity.class);
//        intent.putExtra("week", week);
//        startActivity(intent);
//    }

    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshFinished(List<Week> weeks) {
        if (mAdapter == null) {
            mAdapter = new WeekMenuAdapter(getContext(), weeks);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(weeks);
        }

    }

    private void setAdapter(WeekMenuAdapter mAdapter) {
        mRecyclerView.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, this));
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), DayMealActivity.class);
        intent.putExtra("week", mAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

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



