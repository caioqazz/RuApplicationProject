package br.ufop.ruapplicationmvvm.view;

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

import br.ufop.ruapplicationmvvm.R;
import br.ufop.ruapplicationmvvm.model.entity.User;
import br.ufop.ruapplicationmvvm.service.api.RetrofitBuilder;
import br.ufop.ruapplicationmvvm.service.api.TokenManager;
import br.ufop.ruapplicationmvvm.service.api.UserManager;
import br.ufop.ruapplicationmvvm.view.fragment.CalendarFragment;
import br.ufop.ruapplicationmvvm.view.fragment.CategoryDishFragment;
import br.ufop.ruapplicationmvvm.view.fragment.DemandDaysFragment;
import br.ufop.ruapplicationmvvm.view.fragment.FeedbackFragment;
import br.ufop.ruapplicationmvvm.view.fragment.MainManagerFragment;
import br.ufop.ruapplicationmvvm.view.fragment.NoticeFragment;
import br.ufop.ruapplicationmvvm.view.fragment.ReportFragment;
import br.ufop.ruapplicationmvvm.view.fragment.WeekMenuFragment;

public class MainManagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private TokenManager tokenManager;
    private View headerView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //  NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        user = UserManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE)).getUser();
        initiateNav();


        loadData();
        // navigationView.setNavigationItemSelectedListener(this);
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

    private void initiateNav() {
        NavigationView navigationView = findViewById(R.id.nav_view);
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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_principal:
                setFragment(new MainManagerFragment());
                break;

            case R.id.nav_pratos:
                setFragment(new CategoryDishFragment());
                break;
            case R.id.nav_cardapio:
                setFragment(new WeekMenuFragment());
                break;


            case R.id.nav_demanda:
                setFragment(new DemandDaysFragment());
                break;

            case R.id.nav_calendario:
                setFragment(new CalendarFragment());
                break;


            case R.id.nav_relatorios:
                setFragment(new ReportFragment());
                break;

            case R.id.nav_feedbacks:
                setFragment(new FeedbackFragment());
                break;

            case R.id.nav_aviso:
                setFragment(new NoticeFragment());
                break;

            case R.id.nav_sair:
                tokenManager.deleteToken();
                startActivity(new Intent(MainManagerActivity.this, LoginActivity.class));
                finish();
                break;


        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainManagerActivity.this, ProfileActivity.class));
    }
}
