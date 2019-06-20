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
import br.ufop.ruapplicationpassivemvc.activity.fragment.FeedbackFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.MainClientFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.NoticeFragment;
import br.ufop.ruapplicationpassivemvc.activity.fragment.WeekMenuFragment;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.service.api.ApiService;
import br.ufop.ruapplicationpassivemvc.service.api.RetrofitBuilder;
import br.ufop.ruapplicationpassivemvc.service.api.TokenManager;
import com.squareup.picasso.Picasso;

public class MainClientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ApiService service;
    private TokenManager tokenManager;
    private User user;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        Toolbar toolbar = findViewById(R.id.main_client_toolbar);
        setSupportActionBar(toolbar);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        DrawerLayout drawer = findViewById(R.id.drawer_client_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_client_view);
        headerView = navigationView.getHeaderView(0);
        initiate();

        navigationView.setNavigationItemSelectedListener(this);

        //  navigationView.getMenu().getItem(1).setActionView(R.layout.menu_count_item);

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
                Intent intent = new Intent(MainClientActivity.this, ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        ImageButton editProfile = headerView.findViewById(R.id.nav_header_edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainClientActivity.this, ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_client_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    void setFragment(Fragment frag) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_client_frame, frag);
        ft.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_client_principal:
                getSupportFragmentManager().popBackStack();
                Fragment mainFragment = new MainClientFragment();
                setFragment(mainFragment);
                break;
            case R.id.nav_client_feedbacks:
                Fragment feedbackFragmentFragment = new FeedbackFragment();
                setFragment(feedbackFragmentFragment);
                break;
            case R.id.nav_client_cardapio:
                Fragment menuFragment = new WeekMenuFragment();
                setFragment(menuFragment);
                break;

            case R.id.nav_client_aviso:
                Fragment noticeFragment = new NoticeFragment();
                setFragment(noticeFragment);
                break;

            case R.id.nav_client_pratos:
                Fragment categoryDishFragment = new CategoryDishFragment();
                setFragment(categoryDishFragment);
                break;

            case R.id.nav_client_calendario:
                Fragment calendarFragment = new CalendarFragment();
                setFragment(calendarFragment);
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


}
