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
import br.ufop.ruapplicationmvvm.databinding.ActivityDetailNoticeBinding;
import br.ufop.ruapplicationmvvm.model.entity.Notice;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.util.Constants;
import br.ufop.ruapplicationmvvm.util.DialogManager;
import br.ufop.ruapplicationmvvm.util.Status;
import br.ufop.ruapplicationmvvm.viewmodel.DetailNoticeViewModel;
import es.dmoral.toasty.Toasty;

public class DetailNoticeActivity extends AppCompatActivity {

    private Notice notice;
    private User user;
    private AlertDialog mDialog;
    private ActivityDetailNoticeBinding binding;
    private DetailNoticeViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_notice);


        setTitle("");
        mViewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE)))
                .get(DetailNoticeViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initiate();

        UserManager userManager = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = userManager.getUser();
        mDialog = DialogManager.loadingDialog(this);
        mViewModel.getResult().observe(this, result -> {
            onLoading(result.getStatus() == Status.LOADING);

            if (result.getStatus() == Status.SUCCESS)
                onCommitFinished();
            else if (result.getStatus() == Status.ERROR)
                onError(result.getError());
            else if (result.getStatus() == Status.FAILURE)
                onFailure(result.getFailure());
        });


        if (user.getType() == Constants.CLIENT && notice.getVisualized() == Constants.NOT_VISUALIZED) {
            mViewModel.setViewed(notice.getId());
        }

    }

    private void onLoading(boolean b) {
        if (b)
            mDialog.show();
        else
            mDialog.hide();
    }


    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            notice = (Notice) mBundle.get("notice");
            binding.setNotice(notice);
            mBundle.clear();
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getType() != Constants.CLIENT) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_delete, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete:
                DialogManager.confimationDelete(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.delete(notice.getId());

                    }
                }, notice.getSubject());


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onCommitFinished() {
        Toasty.success(this, notice.getSubject(), Toasty.LENGTH_LONG).show();
        finish();
    }

    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }


}
