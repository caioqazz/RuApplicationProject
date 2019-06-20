package br.ufop.ruapplicationmvvm.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.FragmentDemandDaysBinding;
import br.ufop.ruapplicationmvvm.model.entity.Day;
import br.ufop.ruapplicationmvvm.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.view.DemandActivity;
import br.ufop.ruapplicationmvvm.view.adapter.DayAdapter;
import br.ufop.ruapplicationmvvm.viewmodel.DayDemandViewModel;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemandDaysFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {
    private DayAdapter mAdapter;
    private DayDemandViewModel mViewModel;
    private FragmentDemandDaysBinding binding;

    public DemandDaysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_demand_days, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Demanda");
        ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(DayDemandViewModel.class);

        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onRefreshFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        binding.demandDaysSwipeRefresh.setOnRefreshListener(this);
        mViewModel.getDays();

    }

    private void onLoading(boolean b) {
        if (b)
            binding.demandDaysSwipeRefresh.setRefreshing(true);
        else
            binding.demandDaysSwipeRefresh.setRefreshing(false);
    }


    private void detailDemand(Day day) {
        Intent intent = new Intent(getContext(), DemandActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
    }


    private void onRefreshFinished(List<Day> days) {
        if (mAdapter == null) {
            mAdapter = new DayAdapter(getContext(), days);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(days);
        }


    }

    private void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }


    private void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    private void setAdapter(DayAdapter mAdapter) {
        binding.demandDaysRecyclerview.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.demandDaysRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        binding.demandDaysRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.demandDaysRecyclerview, this));
        binding.demandDaysRecyclerview.setLayoutManager(mLinearLayoutManager);
        binding.demandDaysRecyclerview.setAdapter(mAdapter);
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
        mViewModel.getDays();
    }

}
