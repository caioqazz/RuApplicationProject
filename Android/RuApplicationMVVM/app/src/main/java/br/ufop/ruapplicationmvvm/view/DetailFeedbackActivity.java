package br.ufop.ruapplicationmvvm.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.ViewModelFactory;
import br.ufop.ruapplicationmvvm.databinding.ActivityDetailFeedbackBinding;
import br.ufop.ruapplicationmvvm.model.entity.Feedback;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Networking;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.DetailFeedbackViewModel;
import es.dmoral.toasty.Toasty;

public class DetailFeedbackActivity extends AppCompatActivity {
    private Feedback feedback;
    private User user;
    private ActivityDetailFeedbackBinding binding;
    private AlertDialog mDialog;
    private DetailFeedbackViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_feedback);

        setTitle("");
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(DetailFeedbackViewModel.class);

        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onFinished(result.getData());
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        UserManager userManager = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = userManager.getUser();

        initiate();
        mDialog = DialogManager.loadingDialog(this);
        onFeedbackVisualized();


    }

    private void onLoading(boolean b) {
        if (b)
            mDialog.show();
        else
            mDialog.hide();
    }

    private void onFeedbackVisualized() {
        if (user.getType() == Constants.MANAGER && feedback.getManagerViewed() == Constants.NOT_VISUALIZED)
            mViewModel.onFeedbackVisualized(feedback.getId(), Constants.VISUALIZED, feedback.getUserView());
        else if (user.getType() == Constants.CLIENT && feedback.getUserView() == Constants.NOT_VISUALIZED)
            mViewModel.onFeedbackVisualized(feedback.getId(), feedback.getManagerView(), Constants.VEGETARIANO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getType() != Constants.CLIENT) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.send_menu, menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_delete, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.send:
                mViewModel.reply(feedback.getId(), feedback.getSubject(), feedback.getContent(), binding.detailFeedbackReply.getText().toString());
                return true;
            case R.id.action_delete:
                DialogManager.confimationDelete(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Networking.isNetworkConnected(DetailFeedbackActivity.this))
                            mViewModel.delete(feedback.getId());
                    }
                }, feedback.getSubject());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("feedback") != null) {
            feedback = (Feedback) mBundle.get("feedback");
            binding.setFeedback(feedback);
            mBundle.clear();
        }
    }


    public void onFinished(Feedback feedback) {
        if (feedback == null)
            finish();
        Toasty.success(this, feedback.getSubject(), Toasty.LENGTH_LONG).show();
    }

    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    public void onError(String message) {

        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

}
