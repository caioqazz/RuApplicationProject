package br.ufop.ruapplicationmvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.CalendarContract;
import br.ufop.ruapplicationmvp.model.entity.Meal;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.presenter.CalendarPresenter;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.Networking;
import br.ufop.ruapplicationmvp.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvp.view.FormCalendarActivity;
import br.ufop.ruapplicationmvp.view.adapter.CalendarAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class CalendarFragment extends Fragment
        implements CalendarContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener.OnItemClickListener {
    @BindView(R.id.calendar_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.calendar_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.calendar_btn_add)
    FloatingActionButton mFloatingActionButton;


    private CalendarAdapter mAdapter;
    private CalendarPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Calendário de Funcionamento");
        ButterKnife.bind(this, view);
        mPresenter = new CalendarPresenter(this, getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));

        mPresenter.onRefresh();
        mRefreshLayout.setOnRefreshListener(this);


        UserManager userManager = UserManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        User user = userManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            clientView();
        }

    }

    private void clientView() {
        mFloatingActionButton.setVisibility(View.GONE);
    }


    @OnClick(R.id.calendar_btn_add)
    public void onFloatingButtonClick() {
        startActivity(new Intent(getContext(), FormCalendarActivity.class));
    }


    private void form(Meal meal) {
        Intent intent = new Intent(getContext(), FormCalendarActivity.class);
        intent.putExtra("meal", meal);
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
    public void onRefreshFinished(List<Meal> meals) {
        if (mAdapter == null) {
            mAdapter = new CalendarAdapter(getContext(), meals);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(meals);
        }

    }

    private void setAdapter(CalendarAdapter mAdapter) {
        mRecyclerView.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
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
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (UserManager.getInstance(getActivity().getSharedPreferences("prefs", getContext().MODE_PRIVATE)).getUser().getType() != 1) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_detail);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_edit:
                            if (Networking.isNetworkConnected(getContext()))
                                form(mAdapter.getItem(position));
                            break;
                        case R.id.menu_delete:
                            if (Networking.isNetworkConnected(getContext()))
                                form(mAdapter.getItem(position));
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
}
