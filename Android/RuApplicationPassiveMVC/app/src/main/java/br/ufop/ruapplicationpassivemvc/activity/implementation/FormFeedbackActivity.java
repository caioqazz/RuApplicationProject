package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.FormFeedbackControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.FormFeedbackController;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.FormFeedbackView;

public class FormFeedbackActivity extends AppCompatActivity implements FormFeedbackControllerListener {
    private FormFeedbackController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);
        FormFeedbackView view = findViewById(R.id.form_feedback_container);
        controller = new FormFeedbackController(this, view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Novo Feedback");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.send:
                controller.insert();
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
    public void onActionExecuted(String message, int result) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if (result == Constants.SUCCESS)
            finish();
    }
}
