package com.kubatov.client.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kubatov.client.R;
import com.kubatov.client.ui.auth.RegistrationActivity;
import com.kubatov.client.ui.profile.profileFragment;
import com.kubatov.client.ui.trip.tripFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;

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

        db = FirebaseFirestore.getInstance();
        db.collection("clients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() > 0) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    if (document.exists()){
                                        Log.d("ololo", "Room already exists, start the chat");

                                    } else {
                                        RegistrationActivity.start(MainActivity.this);
                                    }
                                }
                            } else {
                                Log.d("olol", "room doesn't exist create a new room");

                            }
                        } else {
                            Log.d("ololo", "Error getting documents: ", task.getException());
                        }
                    }
                });
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
