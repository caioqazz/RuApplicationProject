package br.ufop.ruapplicationmvp.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.contract.FormNoticeContract;
import br.ufop.ruapplicationmvp.model.entity.Notice;
import br.ufop.ruapplicationmvp.presenter.FormNoticePresenter;
import br.ufop.ruapplicationmvp.util.DialogManager;
import br.ufop.ruapplicationmvp.util.Networking;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class FormNoticeActivity extends AppCompatActivity
        implements FormNoticeContract.View {


    @BindView(R.id.form_notice_subject)
    EditText formNoticeSubject;
    @BindView(R.id.form_notice_spinner)
    Spinner formNoticeSpinner;
    @BindView(R.id.form_notice_content)
    EditText formNoticeContent;
    private FormNoticePresenter mPresenter;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_notice);
        ButterKnife.bind(this);
        setTitle("Escrever");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDialog = DialogManager.loadingDialog(this);
        mPresenter = new FormNoticePresenter(this, getSharedPreferences("prefs", MODE_PRIVATE));
        initiateSpinner();

    }

    private void initiateSpinner() {
        String[] colors = {"Selecione o tipo de aviso", "Informativo", "Alerta", "Urgência"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formNoticeSpinner.setAdapter(mAdapter);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.send:
                if (Networking.isNetworkConnected(this))
                    mPresenter.onCommitButton(formNoticeSubject.getText().toString()
                            , formNoticeContent.getText().toString()
                            , formNoticeSpinner.getSelectedItemPosition() - 1);

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
    public void onCommitFinished(Notice notice) {
        Toasty.success(this, notice.getSubject(), Toasty.LENGTH_LONG).show();
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
