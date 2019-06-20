package br.ufop.ruapplicationmvvm.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
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
import br.ufop.ruapplicationmvvm.databinding.FragmentFeedbackBinding;
import br.ufop.ruapplicationmvvm.model.entity.Feedback;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Networking;
import br.ufop.ruapplicationmvvm.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.view.DetailFeedbackActivity;
import br.ufop.ruapplicationmvvm.view.FormFeedbackActivity;
import br.ufop.ruapplicationmvvm.view.adapter.FeedbackAdapter;
import br.ufop.ruapplicationmvvm.viewmodel.FeedbackViewModel;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

public class FeedbackFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private FeedbackAdapter mAdapter;
    private User user;
    private AlertDialog mDialog;
    private FeedbackViewModel mViewModel;
    private FragmentFeedbackBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Feedbacks");
        mDialog = DialogManager.loadingDialog(getContext());
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(FeedbackViewModel.class);

        binding.feedbackSwipeRefresh.setOnRefreshListener(this);

        UserManager userManager = UserManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        user = userManager.getUser();
        binding.setUser(user);
        mViewModel.getResult().observe(this, result -> {
            onDeleteLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onFinishedDelete();
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });

        mViewModel.getListResult().observe(this, result -> {
            onListLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onRefreshFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        mViewModel.getFeedbacks(user.getType());
        binding.feedbackBtnAdd.setOnClickListener(this);

    }

    private void onListLoading(boolean b) {
        if (b)
            binding.feedbackSwipeRefresh.setRefreshing(true);
        else
            binding.feedbackSwipeRefresh.setRefreshing(false);
    }

    private void onDeleteLoading(boolean b) {
        if (b)
            mDialog.show();
        else
            mDialog.hide();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_feedback, container, false);
        View view = binding.getRoot();
        return view;
    }


    private void onRefreshFinished(List<Feedback> feedbacks) {
        if (mAdapter == null) {
            mAdapter = new FeedbackAdapter(getContext(), feedbacks);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(feedbacks);
        }

    }

    private void onFinishedDelete() {
        Toasty.success(getContext(), "Deletado!", Toasty.LENGTH_LONG).show();
        mViewModel.getFeedbacks(user.getType());
    }

    private void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }


    private void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    private void setAdapter(FeedbackAdapter mAdapter) {
        binding.feedbackRecyclerview.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.feedbackRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        binding.feedbackRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.feedbackRecyclerview, this));
        binding.feedbackRecyclerview.setLayoutManager(mLinearLayoutManager);
        binding.feedbackRecyclerview.setAdapter(mAdapter);
    }


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
                                        mViewModel.delete(mAdapter.getItem(position).getId());
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
        mViewModel.getFeedbacks(user.getType());
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), FormFeedbackActivity.class));
    }
}
