package com.kubatov.client.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kubatov.client.R;
import com.kubatov.client.ui.auth.PhoneAuthActivity;
import com.kubatov.client.ui.main.viewpager.SimpleFragmentFour;
import com.kubatov.client.ui.fragmentOne.SimpleFragmentOne;
import com.kubatov.client.ui.main.viewpager.SimpleFragmentThree;
import com.kubatov.client.ui.main.viewpager.SimpleFragmentTwo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    public static void start(Context context) {
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
        bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.bottom_nav_1:
                    viewPager.setCurrentItem(0);
                    new SimpleFragmentOne();
                    break;
                case R.id.bottom_nav_2:
                    viewPager.setCurrentItem(1);
                    new SimpleFragmentTwo();
                    break;
                case R.id.bottom_nav_3:
                    viewPager.setCurrentItem(2);
                    new SimpleFragmentThree();
                    break;
                case R.id.bottom_nav_4:
                    viewPager.setCurrentItem(3);
                    new SimpleFragmentFour();
                    break;
            }
            return false;
        });
    }
    private List<Fragment> sendFragment(){
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new SimpleFragmentOne());
        fragmentList.add(new SimpleFragmentTwo());
        fragmentList.add(new SimpleFragmentThree());
        fragmentList.add(new SimpleFragmentFour());
        return fragmentList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            FirebaseAuth.getInstance().signOut();
            PhoneAuthActivity.start(this);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
