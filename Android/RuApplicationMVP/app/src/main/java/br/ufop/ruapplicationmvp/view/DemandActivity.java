package br.ufop.ruapplicationmvp.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.util.NameDate;
import br.ufop.ruapplicationmvp.util.SectionsPageAdapter;
import br.ufop.ruapplicationmvp.view.fragment.DemandFragment;

public class DemandActivity extends AppCompatActivity {
    Day day;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null && mBundle.get("day") != null) {
            day = (Day) mBundle.get("day");
            setTitle("Demanda - " + NameDate.dayName(day.getNumDay()));
            mBundle.clear();
        }


        mViewPager = findViewById(R.id.demand_viewpager);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.demand_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.sun);
        tabLayout.getTabAt(1).setIcon(R.drawable.night);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        Bundle bundleLunch = new Bundle();
        bundleLunch.putSerializable("day", day);
        bundleLunch.putInt("type", 0);

        Bundle bundleDinner = new Bundle();
        bundleDinner.putSerializable("day", day);
        bundleDinner.putInt("type", 1);

        Fragment lunch = new DemandFragment();
        lunch.setArguments(bundleLunch);


        Fragment dinner = new DemandFragment();
        dinner.setArguments(bundleDinner);

        adapter.addFragment(lunch, "");
        adapter.addFragment(dinner, "");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
