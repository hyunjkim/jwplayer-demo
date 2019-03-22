package com.example.jwplayerdemo;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.jwplayerdemo.behavior.BottomNavigationBehavior;
import com.example.jwplayerdemo.jwsettings.JWPlayerViewSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    CoordinatorLayout container;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;

    @BindView(R.id.framelayout)
    FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        final FragmentManager fragmentManager = getSupportFragmentManager();
//
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

        navigation.setOnNavigationItemSelectedListener(menuItem -> {

            Fragment fragment = new JWPlayerViewExample();

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    fragment = new JWPlayerViewExample();
                    break;
                case R.id.navigation_settings:
                    fragment = new JWPlayerViewSettings();
                    break;
            }

            fragmentManager.beginTransaction()
                    .replace(container.getId(), fragment)
                    .commit();

            return true;

        });

        navigation.setSelectedItemId(R.id.navigation_home);

    }
}
