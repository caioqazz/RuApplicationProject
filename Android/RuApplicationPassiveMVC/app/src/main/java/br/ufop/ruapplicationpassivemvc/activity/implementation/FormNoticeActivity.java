package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.listener.FormNoticeControllerListener;
import br.ufop.ruapplicationpassivemvc.controller.implementation.FormNoticeController;
import br.ufop.ruapplicationpassivemvc.util.Constants;
import br.ufop.ruapplicationpassivemvc.view.FormNoticeView;

public class FormNoticeActivity extends AppCompatActivity implements FormNoticeControllerListener {

    private FormNoticeController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_notice);
        setTitle("Escrever");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FormNoticeView view = findViewById(R.id.form_notice_container);
        controller = new FormNoticeController(view, this);


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
                controller.insert();

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
