package br.ufop.ruapplicationmvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.model.entity.User;
import br.ufop.ruapplicationmvp.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvp.service.api.TokenManager;
import br.ufop.ruapplicationmvp.service.api.UserManager;
import br.ufop.ruapplicationmvp.view.fragment.CalendarFragment;
import br.ufop.ruapplicationmvp.view.fragment.CategoryDishFragment;
import br.ufop.ruapplicationmvp.view.fragment.FeedbackFragment;
import br.ufop.ruapplicationmvp.view.fragment.MainClientFragment;
import br.ufop.ruapplicationmvp.view.fragment.NoticeFragment;
import br.ufop.ruapplicationmvp.view.fragment.WeekMenuFragment;

public class MainClientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private TokenManager tokenManager;
    private View headerView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        Toolbar toolbar = findViewById(R.id.main_client_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_client_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getUser();
        initiateNav();


        loadData();


    }

    private void initiateNav() {
        NavigationView navigationView = findViewById(R.id.nav_client_view);
        headerView = navigationView.getHeaderView(0);
        headerView.findViewById(R.id.nav_header_container).setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    private void loadData() {


        if (user != null) {
            TextView email = headerView.findViewById(R.id.nav_header_email);
            email.setText(user.getEmail());
            TextView name = headerView.findViewById(R.id.nav_header_name);
            name.setText(user.getName());
            if (user.getPhoto() != null) {
                ImageView imageView = headerView.findViewById(R.id.nav_header_imageView);
                if (!user.getPhoto().isEmpty())
                    Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + user.getPhoto()).error(R.mipmap.user).into(imageView);
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tokenManager.getToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (user != UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getUser()) {
            user = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getUser();
            loadData();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_manager_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_client_frame, fragment);
        ft.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_client_principal:
                setFragment(new MainClientFragment());
                break;
            case R.id.nav_client_feedbacks:
                setFragment(new FeedbackFragment());
                break;
            case R.id.nav_client_cardapio:
                setFragment(new WeekMenuFragment());
                break;

            case R.id.nav_client_aviso:
                setFragment(new NoticeFragment());
                break;

            case R.id.nav_client_pratos:
                setFragment(new CategoryDishFragment());
                break;

            case R.id.nav_client_calendario:
                setFragment(new CalendarFragment());
                break;

            case R.id.nav_client_sair:
                tokenManager.deleteToken();
                startActivity(new Intent(MainClientActivity.this, LoginActivity.class));
                finish();


                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_client_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainClientActivity.this, ProfileActivity.class));
    }
}
