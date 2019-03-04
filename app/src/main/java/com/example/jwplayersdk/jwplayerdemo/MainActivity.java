package com.example.jwplayersdk.jwplayerdemo;

import android.os.Bundle;

import com.example.jwplayersdk.jwplayerdemo.jwsettings.JWPlayerViewSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    ConstraintLayout CONTAINER;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        final FragmentManager fragmentManager = getSupportFragmentManager();

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
                    .replace(CONTAINER.getId(), fragment)
                    .commit();
            return true;
        });
        navigation.setSelectedItemId(R.id.navigation_home);
    }
}
