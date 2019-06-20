package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.DetailFeedbackControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.DetailFeedbackController;
import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.util.Dialog;
import br.ufop.ruapplicationpassivemvc.util.FormatDate;
import br.ufop.ruapplicationpassivemvc.util.Networking;
import br.ufop.ruapplicationpassivemvc.view.DetailFeedbackView;
import com.squareup.picasso.Picasso;

public class DetailFeedbackActivity extends AppCompatActivity implements DetailFeedbackControllerListener {
    Feedback feedback;
    DetailFeedbackView view;
    DetailFeedbackController controller;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feedback);
        setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TokenManager tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = tokenManager.getUser();

        view = findViewById(R.id.detail_feedback_container);
        initiate();
        controller = new DetailFeedbackController(view, feedback, this);
        controller.setFeedback();

        setViewByUser();


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
                controller.reply();
                return true;
            case R.id.action_delete:
                Dialog.confimationDelete(view.getContext(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Networking.isNetworkConnected(view))
                            controller.delete();
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
        if (user.getType() == Constants.CLIENT)
            view.clientView();
    }

    private void initiate() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("feedback") != null) {
            feedback = (Feedback) mBundle.get("feedback");
            view.setContent(feedback.getContent());
            view.setSubject(feedback.getSubject());
            view.setDate(FormatDate.simpleFormat(feedback.getDate()));
            view.setName(feedback.getName());

            if (!feedback.getReply().isEmpty())
                view.setReply(feedback.getReply());

            if (feedback.getPhoto() != null) {
                if (!feedback.getPhoto().isEmpty())
                    Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + feedback.getPhoto()).error(R.drawable.meat).into(view.getPhoto());
            }

            mBundle.clear();
        }
    }

    @Override
    public void onActionExecuted(String s, int result) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        if (result == Constants.SUCCESS)
            finish();
    }
}
