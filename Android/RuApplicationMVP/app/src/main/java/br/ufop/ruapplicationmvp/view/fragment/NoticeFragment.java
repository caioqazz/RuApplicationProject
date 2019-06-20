package br.ufop.ruapplicationmvp.view.fragment;


import android.content.DialogInterface;
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
import br.ufop.ruapplicationmvp.contract.NoticeContract;
import br.ufop.ruapplicationmvp.model.entity.Notice;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.presenter.NoticePresenter;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.Networking;
import br.ufop.ruapplicationmvp.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvp.view.DetailNoticeActivity;
import br.ufop.ruapplicationmvp.view.FormNoticeActivity;
import br.ufop.ruapplicationmvp.view.adapter.NoticeAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment
        implements NoticeContract.View, RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.notice_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.notice_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.notice_btn_add)
    FloatingActionButton mFloatingActionButton;

    private NoticeAdapter mAdapter;
    private NoticePresenter mPresenter;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Avisos");
        setHasOptionsMenu(true);

        ButterKnife.bind(this, view);
        mPresenter = new NoticePresenter(this, getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        mRefreshLayout.setOnRefreshListener(this);

        UserManager userManager = UserManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        user = userManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            clientView();
        }

    }

    private void clientView() {
        mFloatingActionButton.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onRefresh();
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
    public void onRefreshFinished(List<Notice> notices) {
        if (mAdapter == null) {
            mAdapter = new NoticeAdapter(getContext(), notices);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(notices);
        }

    }

    @Override
    public void onFinishedDelete() {
        Toasty.success(getContext(), "Deletado!", Toasty.LENGTH_LONG).show();
        mPresenter.onRefresh();
    }

    private void setAdapter(NoticeAdapter mAdapter) {
        mRecyclerView.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, this));
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onFailure(String message, int operation) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message, int operation) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), DetailNoticeActivity.class);
        intent.putExtra("notice", mAdapter.getItem(position));
        getContext().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (user.getType() != Constants.CLIENT) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.menu_delete);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.action_delete:
                            DialogManager.confimationDelete(view.getContext(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Networking.isNetworkConnected(getContext())) {
                                        mPresenter.onDeleteButtonClick(mAdapter.getItem(position).getId());
                                    }
                                }
                            }, mAdapter.getItem(position).getSubject());


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

    @OnClick(R.id.notice_btn_add)
    public void onViewClicked() {
        getContext().startActivity(new Intent(getContext(), FormNoticeActivity.class));
    }
}
