package com.example.jwplayersdk.jwplayerdemo;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

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
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        });
        navigation.setSelectedItemId(R.id.navigation_home);
    }
}
