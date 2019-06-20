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
import br.ufop.ruapplicationmvp.contract.DemandDaysContract;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.presenter.DemandDaysPresenter;
import br.ufop.ruapplicationmvp.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvp.view.DemandActivity;
import br.ufop.ruapplicationmvp.view.adapter.DayAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemandDaysFragment extends Fragment
        implements DemandDaysContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {
    @BindView(R.id.demand_days_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.demand_days_recyclerview)
    RecyclerView mRecyclerView;
    private DayAdapter mAdapter;
    private DemandDaysPresenter mPresenter;

    public DemandDaysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demand_days, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Demanda");
        ButterKnife.bind(this, view);
        mPresenter = new DemandDaysPresenter(this, getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));

        mPresenter.onRefresh();
        mRefreshLayout.setOnRefreshListener(this);

    }


    private void detailDemand(Day day) {
        Intent intent = new Intent(getContext(), DemandActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
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
            mAdapter = new DayAdapter(getContext(), days);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(days);
        }

    }

    private void setAdapter(DayAdapter mAdapter) {
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
        detailDemand(mAdapter.getItem(position));
    }

    @Override
    public void onItemLongClick(View view, int position) {
        detailDemand(mAdapter.getItem(position));
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
