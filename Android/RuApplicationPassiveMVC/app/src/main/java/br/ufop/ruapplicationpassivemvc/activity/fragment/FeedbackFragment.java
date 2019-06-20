package br.ufop.ruapplicationpassivemvc.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.implementation.DetailFeedbackActivity;
import br.ufop.ruapplicationpassivemvc.activity.implementation.FormFeedbackActivity;
import br.ufop.ruapplicationpassivemvc.activity.listener.FeedbackControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.FeedbackController;
import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.FeedbackView;

public class FeedbackFragment extends Fragment implements FeedbackControllerListener, View.OnClickListener {

    private FeedbackController controller;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Feedbacks");
        FeedbackView feedbackView = getActivity().findViewById(R.id.feedback_container);
        controller = new FeedbackController(feedbackView, this);
        feedbackView.setListeners(controller, controller, this);

        TokenManager tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE));
        User user = tokenManager.getUser();
        if (user.getType() == Constants.CLIENT) {
            feedbackView.clientView();
        }

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
        controller.onRefresh();
    }

    @Override
    public void feedbackDetail(Feedback feedback) {
        Intent intent = new Intent(getContext(), DetailFeedbackActivity.class);
        intent.putExtra("feedback", feedback);
        startActivity(intent);
    }

    @Override
    public void onActionExecuted(String message, int result) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), FormFeedbackActivity.class);
        startActivity(intent);
    }
}
