package br.ufop.ruapplicationpassivemvc.activity.implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.ruapplicationpassivemvc.R;
import br.ufop.ruapplicationpassivemvc.activity.fragment.CalendarFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.CategoryDishFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.DemandDaysFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.FeedbackFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.MainManagerFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.NoticeFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.ReportFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.WeekMenuFragment;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import com.squareup.picasso.Picasso;

public class MainManagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TokenManager tokenManager;
    private View headerView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);
        Toolbar toolbar = findViewById(R.id.main_manager_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_manager_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_manager_view);
        headerView = navigationView.getHeaderView(0);
        initiate();
        navigationView.setNavigationItemSelectedListener(this);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    private void initiate() {

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("user") != null) {
            user = (User) mBundle.get("user");
            TextView email = headerView.findViewById(R.id.nav_header_email);
            email.setText(user.getEmail());
            TextView name = headerView.findViewById(R.id.nav_header_name);
            name.setText(user.getName());
            if (user.getPhoto() != null) {
                ImageView imageView = headerView.findViewById(R.id.nav_header_imageView);
                if (!user.getPhoto().isEmpty())
                    Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + user.getPhoto()).error(R.drawable.user).into(imageView);
            }


        }
        headerView.findViewById(R.id.nav_header_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainManagerActivity.this, ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        ImageButton editProfile = headerView.findViewById(R.id.nav_header_edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainManagerActivity.this, ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tokenManager.getToken() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        user = tokenManager.getUser();
        if (user != null) {
            TextView email = headerView.findViewById(R.id.nav_header_email);
            email.setText(user.getEmail());
            TextView name = headerView.findViewById(R.id.nav_header_name);
            name.setText(user.getName());
            if (user.getPhoto() != null) {
                ImageView imageView = headerView.findViewById(R.id.nav_header_imageView);
                if (!user.getPhoto().isEmpty())
                    Picasso.get().load(RetrofitBuilder.BASE_PARSE_URL + user.getPhoto()).error(R.drawable.user).into(imageView);
            }
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
            case R.id.nav_cardapio:
                setFragment(new WeekMenuFragment());
                break;

            case R.id.nav_aviso:
                setFragment(new NoticeFragment());
                break;

            case R.id.nav_pratos:
                setFragment(new CategoryDishFragment());
                break;

            case R.id.nav_calendario:
                setFragment(new CalendarFragment());
                break;

            case R.id.nav_demanda:
                setFragment(new DemandDaysFragment());
                break;

            case R.id.nav_relatorios:
                setFragment(new ReportFragment());
                break;

            case R.id.nav_feedbacks:
                setFragment(new FeedbackFragment());
                break;
            case R.id.nav_sair:
                tokenManager.deleteToken();
                startActivity(new Intent(MainManagerActivity.this, LoginActivity.class));
                finish();
                break;


        }

        DrawerLayout drawer = findViewById(R.id.drawer_manager_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
