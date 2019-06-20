package br.ufop.ruapplicationmvp.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.FormFeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.presenter.FormFeedbackPresenter;
import br.ufop.ruapplicationmvp.util.DialogManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class FormFeedbackActivity extends AppCompatActivity
        implements FormFeedbackContract.View {
    @BindView(R.id.form_feedback_subject)
    EditText mSubject;
    @BindView(R.id.form_feedback_content)
    EditText mContent;
    private FormFeedbackPresenter mPresenter;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Novo Feedback");
        mDialog = DialogManager.loadingDialog(this);
        mPresenter = new FormFeedbackPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.send:
                mPresenter.onCommitButton(mSubject.getText().toString(), mContent.getText().toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.send_menu, menu);

        return true;
    }


    @Override
    public void showProgress() {
        mDialog.show();
    }


    @Override
    public void hideProgress() {
        mDialog.hide();
    }

    @Override
    public void onCommitFinished(Feedback feedback) {
        Toasty.success(this, feedback.getSubject(), Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String message) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message) {
        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
