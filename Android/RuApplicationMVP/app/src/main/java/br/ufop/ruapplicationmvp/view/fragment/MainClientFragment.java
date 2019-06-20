package br.ufop.ruapplicationmvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.ufop.ruapplicationmvp.R;
import br.ufop.ruapplicationmvp.model.entity.Day;
import br.ufop.ruapplicationmvp.util.SectionsPageAdapter;


public class MainClientFragment extends Fragment {
    private ViewPager mViewPager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Principal");
        mViewPager = view.findViewById(R.id.main_client_container);

        setupViewPager(mViewPager);

        TabLayout tabLayout = getActivity().findViewById(R.id.main_client_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.sun);
        tabLayout.getTabAt(1).setIcon(R.drawable.night);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_client, container, false);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        Day day = new Day();


        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        day.setDate(df.format(date));

        Bundle bundleLunch = new Bundle();
        bundleLunch.putSerializable("day", day);
        bundleLunch.putInt("type", 0);

        Bundle bundleDinner = new Bundle();
        bundleDinner.putSerializable("day", day);
        bundleDinner.putInt("type", 1);

        Fragment lunch = new MenuFragment();
        lunch.setArguments(bundleLunch);

        Fragment dinner = new MenuFragment();
        dinner.setArguments(bundleDinner);

        adapter.addFragment(lunch, "");
        adapter.addFragment(dinner, "");
        viewPager.setAdapter(adapter);
    }
}
