package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.DetailNoticeControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.DetailNoticeController;
import br.ufop.ruapplicationpassivemvc.model.entity.Notice;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.Dialog;
import br.ufop.ruapplicationpassivemvc.util.FormatDate;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.view.DetailNoticeView;

public class DetailNoticeActivity extends AppCompatActivity implements DetailNoticeControllerListener {
    private DetailNoticeView view;
    private Notice notice;
    private DetailNoticeController controller;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notice);
        setTitle("");

        view = findViewById(R.id.detail_notice_container);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initiate();

        controller = new DetailNoticeController(view, notice, this);
        TokenManager tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = tokenManager.getUser();
        if (user.getType() == Constants.CLIENT && notice.getVisualized() == Constants.NOT_VISUALIZED) {
            controller.setView();
        }

    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            notice = (Notice) mBundle.get("notice");
            view.setSubject(notice.getSubject());
            view.setContent(notice.getContent());
            view.setDate(FormatDate.simpleFormat(notice.getUpdated_at()));
            view.setColor(notice.getType());
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
                Dialog.confimationDelete(view.getContext(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Networking.isNetworkConnected(view))
                            controller.delete();

                    }
                }, notice.getSubject());


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActionExecuted(String s, int result) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

        if (result == Constants.SUCCESS)
            finish();
    }


}
