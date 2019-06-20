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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.FragmentNoticeBinding;
import br.ufop.ruapplicationmvvm.model.entity.Notice;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Networking;
import br.ufop.ruapplicationmvvm.util.RecyclerItemClickListener;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.view.DetailNoticeActivity;
import br.ufop.ruapplicationmvvm.view.FormNoticeActivity;
import br.ufop.ruapplicationmvvm.view.adapter.NoticeAdapter;
import br.ufop.ruapplicationmvvm.viewmodel.NoticeViewModel;
import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment
        implements RecyclerItemClickListener.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private NoticeAdapter mAdapter;
    private User user;
    private AlertDialog mDialog;
    private NoticeViewModel mViewModel;
    private FragmentNoticeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_notice, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Avisos");
        setHasOptionsMenu(true);
        mDialog = DialogManager.loadingDialog(getContext());
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(NoticeViewModel.class);
        binding.setViewmodel(mViewModel);

        user = UserManager.getInstance(getActivity().
                getSharedPreferences("prefs", getActivity().MODE_PRIVATE)).getUser();
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
        binding.noticeSwipeRefresh.setOnRefreshListener(this);
        mViewModel.getNotices();
        binding.noticeBtnAdd.setOnClickListener(this);
    }

    private void onListLoading(boolean b) {
        if (b)
            binding.noticeSwipeRefresh.setRefreshing(true);
        else
            binding.noticeSwipeRefresh.setRefreshing(false);
    }

    private void onDeleteLoading(boolean b) {
        if (b)
            mDialog.show();
        else
            mDialog.hide();
    }


    private void onRefreshFinished(List<Notice> notices) {
        if (mAdapter == null) {
            mAdapter = new NoticeAdapter(getContext(), notices);
            setAdapter(mAdapter);
        } else {
            mAdapter.refresh(notices);
        }

    }

    private void onFinishedDelete() {
        Toasty.success(getContext(), "Deletado!", Toasty.LENGTH_LONG).show();
        mViewModel.getNotices();
    }

    private void setAdapter(NoticeAdapter mAdapter) {
        binding.noticeRecyclerview.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.noticeRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.noticeRecyclerview, this));
        binding.noticeRecyclerview.setLayoutManager(mLinearLayoutManager);
        binding.noticeRecyclerview.setAdapter(mAdapter);
    }

    private void onError(String message) {
        Toasty.error(getContext(), message, Toasty.LENGTH_LONG).show();
    }


    private void onFailure(String message) {
        Toasty.warning(getContext(), message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), DetailNoticeActivity.class);
        intent.putExtra("notice", mAdapter.getItem(position));
        getContext().startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

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
                }
                return false;
            }
        });
        popupMenu.show();
    }


    @Override
    public void onRefresh() {
        mViewModel.getNotices();
    }

    @Override
    public void onClick(View view) {
        getContext().startActivity(new Intent(getContext(), FormNoticeActivity.class));

    }
}
