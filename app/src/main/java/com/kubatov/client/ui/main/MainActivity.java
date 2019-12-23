package com.kubatov.client.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kubatov.client.R;
import com.kubatov.client.ui.profile.profileFragment;
import com.kubatov.client.ui.trip.tripFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    public static void start(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Автобекет");
        ButterKnife.bind(this);
        setUpViewPager();
    }
    private void setUpViewPager() {
        SimpleViewPagerAdapter pagerAdapter = new SimpleViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setFragment(sendFragment());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        setUpBottomNavigation();
    }

    @SuppressLint("ResourceType")
    private void setUpBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.bottom_nav_1:
                    viewPager.setCurrentItem(0);
                    new tripFragment();
                    break;
                case R.id.bottom_nav_4:
                    viewPager.setCurrentItem(1);
                    new profileFragment();
                    break;
            }
            return false;
        });
    }

    private List<Fragment> sendFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new tripFragment());
        fragmentList.add(new profileFragment());
        return fragmentList;
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        } else {
            finish();
        }
    }
}
