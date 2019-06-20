package br.ufop.ruapplicationpassivemvc.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.implementation.DetailNoticeActivity;
import br.ufop.ruapplicationpassivemvc.activity.implementation.FormNoticeActivity;
import br.ufop.ruapplicationpassivemvc.activity.listener.NoticeControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.NoticeController;
import br.ufop.ruapplicationpassivemvc.model.entity.Notice;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.NoticeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment implements NoticeControllerListener {

    private NoticeController noticeController;
    private NoticeView noticeView;

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

        noticeView = (NoticeView) view;
        noticeController = new NoticeController(noticeView, this);
        noticeView.setListeners(noticeController, noticeController, noticeController);

        TokenManager tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        User user = tokenManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            noticeView.clientView();
        }
        noticeController.onRefresh();
    }

    @Override
    public void newNotice() {
        startActivity(new Intent(getContext(), FormNoticeActivity.class));
    }


    @Override
    public void onResume() {
        super.onResume();
      //  noticeController.onRefresh();
    }


    @Override
    public void noticeDetail(Notice notice) {
        Intent intent = new Intent(getContext(), DetailNoticeActivity.class);
        intent.putExtra("notice", notice);
        getContext().startActivity(intent);
    }

    @Override
    public void onActionExecuted(String s, int result) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void noticeEdit(Notice notice) {
        Intent intent = new Intent(getContext(), FormNoticeActivity.class);
        intent.putExtra("notice", notice);
        getContext().startActivity(intent);

    }

}
