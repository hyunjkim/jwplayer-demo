package com.example.jwplayerdemo;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.crashlytics.android.Crashlytics;
import com.example.jwplayerdemo.jwsettings.JWPlayerViewSettings;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

    /*
     * Instantiate CoordinatorLayout
     * in order to use the "Hiding Bottom Navigation View Behavior"
     * */
    @BindView(R.id.container)
    CoordinatorLayout container;

    /*
     * Instantiate FrameLayout
     * a reusable container for Fragments
     * */
    @BindView(R.id.framelayout)
    FrameLayout mFrameLayout;

    /*
     * Instantiate Bottom Navigation View
     * */
    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initializeFabric();

        // Bind all of my views to this activity
        ButterKnife.bind(this);

        // Call Support Fragment's Manager to manage my fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // Set OnNavigationItemListener
        navigation.setOnNavigationItemSelectedListener(menuItem -> {

            mFragment = new JWPlayerViewExample();

            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    mFragment = new JWPlayerViewExample();
                    break;
                case R.id.navigation_settings:
                    mFragment = new JWPlayerViewSettings();
                    break;
            }

            // Replace my current Fragment view (container.getId) with mFragment
            fragmentManager.beginTransaction()
                    .replace(mFrameLayout.getId(), mFragment)
                    .commit();

            return true;

        });

        // My default nagivation page is JWPlayerView
        navigation.setSelectedItemId(R.id.navigation_home);

    }

    private void initializeFabric() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Fabric.with(this, new Crashlytics());

        Crashlytics.setUserIdentifier("JWPlayer Android SDK Demo Version 3");
        Crashlytics.setUserEmail("hkim@jwplayer.com");
        Crashlytics.setUserName("Test User: Hyunjoo");
    }

    /*
     * Credits to: https://stackoverflow.com/questions/12210906/fragment-activity-catch-onkeydown-and-use-in-fragment#answer-12211422
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Exit fullscreen when the user pressed the Back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mFragment != null && mFragment.getClass().getName().contains("JWPlayerViewExample")) {
                return ((JWPlayerViewExample) mFragment).onMyKeyDown(keyCode, event);
            }
        }
        return true;
    }

}
