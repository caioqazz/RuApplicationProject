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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.FeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.presenter.FeedbackPresenter;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.Networking;
import br.ufop.ruapplicationmvp.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvp.view.DetailFeedbackActivity;
import br.ufop.ruapplicationmvp.view.FormFeedbackActivity;
import br.ufop.ruapplicationmvp.view.adapter.FeedbackAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class FeedbackFragment extends Fragment implements FeedbackContract.View, RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.feedback_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.feedback_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.feedback_btn_add)
    FloatingActionButton feedbackBtnAdd;
    private FeedbackAdapter mAdapter;
    private FeedbackPresenter mPresenter;
    private User user;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Feedbacks");

        ButterKnife.bind(this, view);
        mPresenter = new FeedbackPresenter(this, getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        mRefreshLayout.setOnRefreshListener(this);

        UserManager userManager = UserManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        user = userManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            clientView();
        }

    }

    private void clientView() {
        feedbackBtnAdd.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onRefresh(user.getType());
    }

//    @Override
//    public void feedbackDetail(Feedback feedback) {
//        Intent intent = new Intent(getContext(), DetailFeedbackActivity.class);
//        intent.putExtra("feedback", feedback);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onActionExecuted(String message, int result) {
//        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(getContext(), FormFeedbackActivity.class);
//        startActivity(intent);
//    }

    @OnClick(R.id.feedback_btn_add)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), FormFeedbackActivity.class));
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
        Toasty.success(getContext(), "Deletado!", Toasty.LENGTH_LONG).show();
        mPresenter.onRefresh(user.getType());
    }

    @Override
    public void onRefreshFinished(List<Feedback> feedbacks) {
        if (mAdapter == null) {
            mAdapter = new FeedbackAdapter(getContext(), feedbacks);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(feedbacks);
        }

    }

    private void setAdapter(FeedbackAdapter mAdapter) {
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
        Intent intent = new Intent(getContext(), DetailFeedbackActivity.class);
        intent.putExtra("feedback", mAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (user.getType() == Constants.CLIENT) {
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

        mPresenter.onRefresh(user.getType());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
