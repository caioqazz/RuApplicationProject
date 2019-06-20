package br.ufop.ruapplicationmvp.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.DetailFeedbackContract;
import br.ufop.ruapplicationmvp.model.entity.Feedback;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.presenter.DetailFeedbackPresenter;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.FormatDate;
import br.ufop.ruapplicationmvp.util.Networking;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class DetailFeedbackActivity extends AppCompatActivity
        implements DetailFeedbackContract.View {
    @BindView(R.id.detail_feedback_subject)
    TextView mSubject;
    @BindView(R.id.detail_feedback_imageview)
    ImageView mImageview;
    @BindView(R.id.detail_feedback_date)
    TextView mDate;
    @BindView(R.id.detail_feedback_name)
    TextView mName;
    @BindView(R.id.detail_feedback_content)
    TextView mContent;
    @BindView(R.id.detail_feedback_reply)
    EditText mReply;
    private Feedback feedback;
    private User user;
    private DetailFeedbackPresenter mPresenter;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feedback);
        ButterKnife.bind(this);
        setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        UserManager userManager = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = userManager.getUser();

        initiate();
        mDialog = DialogManager.loadingDialog(this);
        mPresenter = new DetailFeedbackPresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));
        onFeedbackVisualized();


        setViewByUser();


    }

    private void onFeedbackVisualized() {
        if (user.getType() == Constants.MANAGER && feedback.getManagerViewed() == Constants.NOT_VISUALIZED)
            mPresenter.onFeedbackVisualized(feedback.getId(), Constants.VISUALIZED, feedback.getUserView());
        else if (user.getType() == Constants.CLIENT && feedback.getUserView() == Constants.NOT_VISUALIZED)
            mPresenter.onFeedbackVisualized(feedback.getId(), feedback.getManagerView(), Constants.VEGETARIANO);
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
                mPresenter.reply(feedback.getId(), feedback.getSubject(), feedback.getContent(), mReply.getText().toString());
                return true;
            case R.id.action_delete:
                DialogManager.confimationDelete(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Networking.isNetworkConnected(DetailFeedbackActivity.this))
                            mPresenter.delete(feedback.getId());
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

    private void setViewByUser() {
        if (user.getType() == Constants.CLIENT) {
            mReply.setEnabled(false);
            mReply.setHint("");
        }
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("feedback") != null) {
            feedback = (Feedback) mBundle.get("feedback");
            mContent.setText(feedback.getContent());
            mSubject.setText(feedback.getSubject());
            mDate.setText(FormatDate.simpleFormat(feedback.getDate()));
            mName.setText(feedback.getName());

            if (!feedback.getReply().isEmpty())
                mReply.setText(feedback.getReply());

            if (feedback.getPhoto() != null) {
                if (!feedback.getPhoto().isEmpty())
                    Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL +
                            feedback.getPhoto()).error(R.drawable.meat).into(mImageview);
            }

            mBundle.clear();
        }
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
    public void onFinished(Feedback feedback, int option) {
        Toasty.success(this, feedback.getSubject(), Toasty.LENGTH_LONG).show();
        if (option == Constants.DELETE)
            finish();

    }

    @Override
    public void onFailure(String message, int option) {
        Toasty.warning(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message, int option) {

        Toasty.error(this, message, Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
