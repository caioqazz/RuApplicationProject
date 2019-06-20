package br.ufop.ruapplicationpassivemvc.view;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.adapter.NoticeAdapter;
import br.ufop.ruapplicationpassivemvc.util.RecyclerItemClickListener;

public class NoticeView extends FrameLayout {
    AlertDialog dialog;

    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListeners(OnClickListener onClickListener, SwipeRefreshLayout.OnRefreshListener onRefreshListener,
                             RecyclerItemClickListener.OnItemClickListener onItemClickListener) {
        findViewById(R.id.notice_btn_add).setOnClickListener(onClickListener);
        ((SwipeRefreshLayout) findViewById(R.id.notice_swipe_refresh)).setOnRefreshListener(onRefreshListener);
        RecyclerView mRecyclerView = findViewById(R.id.notice_recyclerview);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, onItemClickListener));
    }

    public void showLoading() {
        TransitionManager.beginDelayedTransition(this);
        ((SwipeRefreshLayout) findViewById(R.id.notice_swipe_refresh)).setRefreshing(true);
    }

    public void showForm() {
        TransitionManager.beginDelayedTransition(this);
        ((SwipeRefreshLayout) findViewById(R.id.notice_swipe_refresh)).setRefreshing(false);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout_message, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }


    public void disposeDialog() {
        if (dialog != null)
            dialog.dismiss();
    }


    public void setAdapter(NoticeAdapter adapter) {
        RecyclerView mRecyclerView = findViewById(R.id.notice_recyclerview);
        mRecyclerView.removeAllViewsInLayout();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);


        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }


    public void clientView() {
        (findViewById(R.id.notice_btn_add)).setVisibility(View.GONE);
    }
}
