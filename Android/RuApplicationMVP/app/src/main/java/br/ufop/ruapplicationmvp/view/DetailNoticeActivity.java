package br.ufop.ruapplicationmvp.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.DetailNoticeContract;
import br.ufop.ruapplicationmvp.model.entity.Notice;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.presenter.DetailNoticePresenter;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.util.Constants;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.FormatDate;
import br.ufop.ruapplicationmvp.util.Networking;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class DetailNoticeActivity extends AppCompatActivity
        implements DetailNoticeContract.View {
    @BindView(R.id.detail_notice_subject)
    TextView mSubject;
    @BindView(R.id.detail_notice_date)
    TextView mDate;
    @BindView(R.id.detail_notice_content)
    TextView mContent;
    @BindView(R.id.detail_notice_header)
    RelativeLayout mHeader;
    private Notice notice;
    private User user;
    private DetailNoticePresenter mPresenter;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notice);
        ButterKnife.bind(this);
        setTitle("");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initiate();

        UserManager userManager = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = userManager.getUser();
        mDialog = DialogManager.loadingDialog(this);
        mPresenter = new DetailNoticePresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));

        if (user.getType() == Constants.CLIENT && notice.getVisualized() == Constants.NOT_VISUALIZED) {
            mPresenter.setViewed(notice.getId());
        }

    }


    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            notice = (Notice) mBundle.get("notice");
            mSubject.setText(notice.getSubject());
            mContent.setText(notice.getContent());
            mDate.setText(FormatDate.simpleFormat(notice.getUpdated_at()));
            setColor(notice.getType());
            mBundle.clear();
        }

    }

    private void setColor(int type) {
        switch (type) {
            case 0:
                mHeader.setBackgroundResource(R.color.lightBlue);
                break;
            case 1:
                mHeader.setBackgroundResource(R.color.darkYellow);
                break;
            case 2:
                mHeader.setBackgroundResource(R.color.lightRed);
                break;
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
                        if (Networking.isNetworkConnected(DetailNoticeActivity.this))
                            mPresenter.onCommitButton(notice.getId());

                    }
                }, notice.getSubject());


                return true;
            default:
                return super.onOptionsItemSelected(item);
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
    public void onCommitFinished() {
        Toasty.success(this, notice.getSubject(), Toasty.LENGTH_LONG).show();
        finish();
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
