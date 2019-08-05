package com.example.jwplayerdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.jwplayerdemo.jwsettings.JWPlayerViewSettings;
import com.example.jwplayerdemo.jwsettings.JWViewModel;
import com.example.jwplayerdemo.jwsettings.OnSwitchListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JWMainActivity extends AppCompatActivity implements
        JWPlayerViewSettings.OnSettingsListener {

    /*
     * Instantiate CoordinatorLayout - use the "Hiding Bottom Navigation View Behavior"
     * */
    @BindView(R.id.container)
    CoordinatorLayout container;

    /*
     * Instantiate FrameLayout a reusable container for Fragments
     * */
    @BindView(R.id.framelayout)
    FrameLayout mFrameLayout;

    /*
     * Instantiate Bottom Navigation View
     * */
    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigation;

    private Fragment mCurrFragmentView;

    /**
     * {@link ViewModelProviders} - Preserve Data
     * The first time the ViewModelProviders.of method is called by JWMainActivity, it creates a new ViewModel instance.
     * When this method is called again, which happens whenever onCreate is called,
     * it will return the pre-existing ViewModel associated with the specific Court-Counter JWMainActivity.
     * This is what preserves the data.
     * <p>
     * {@link - https://developer.android.com/training/basics/fragments/communicating}
     * {@link - https://medium.com/androiddevelopers/viewmodels-a-simple-example-ed5ac416317e}
     *
     * @see androidx.lifecycle.ViewModel
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        ViewModelProviders
                .of(this)
                .get(JWViewModel.class);

        // Bind all of my views to this activity
        // TODO: I might get rid of bind because I'm not sure if Butterknife will stay up to date
        ButterKnife.bind(this);

        // Call Support Fragment's Manager to manage my fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Set OnNavigationItemListener
        navigation.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.navigation_jwplayer:
                    Log.i("HYUNJOO", "JWMainActivity - navigation bar - jwplayerview");
                    mCurrFragmentView = new JWPlayerViewExample();
                    break;
                case R.id.navigation_settings:
                    Log.i("HYUNJOO", "JWMainActivity - navigation bar - settings");
                    mCurrFragmentView = new JWPlayerViewSettings();
                    break;
            }

            // Replace my current Fragment view (container.getId) with mCurrFragmentView
            fragmentManager
                    .beginTransaction()
                    .replace(mFrameLayout.getId(), mCurrFragmentView)
                    .commit();

            return true;

        });

        // My default nagivation page is JWPlayerView
        navigation.setSelectedItemId(R.id.navigation_jwplayer);
    }

    /**
     * Exit fullscreen when the user pressed the Back button
     * Credits to: {@link - https://stackoverflow.com/questions/12210906/fragment-activity-catch-onkeydown-and-use-in-fragment#answer-12211422
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCurrFragmentView != null && mCurrFragmentView.getClass().getName().equalsIgnoreCase("com.example.jwplayerdemo.JWPlayerViewExample")) {
                return ((JWPlayerViewExample) mCurrFragmentView).onMyKeyDown(keyCode, event);
            }
        }
        return true;
    }

    /**
     * Define an Interface
     * Now the Setting fragment can notify the activity to show JWPlayerView by calling the onSettingsChanged()
     * {@link - https://developer.android.com/training/basics/fragments/communicating#DefineInterface}
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof JWPlayerViewSettings) {
            JWPlayerViewSettings jwPlayerViewSettings = (JWPlayerViewSettings) fragment;
            jwPlayerViewSettings.addOnSettingsListener(this);
        }
    }

    /**
     * When user clicks "SAVE"
     * Then, notify JWPlayerView that playerConfig settings was updated
     * JWPlayerView will grab the updated settings from the JWViewModel
     *
     * @see JWPlayerViewSettings#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public void onSettingsChanged() {
        Log.i("HYUNJOO", "JWMainActivity - onSettingsChanged() ");
        navigation.setSelectedItemId(R.id.navigation_jwplayer);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        // Let JW Player know that the app has returned from the background
        super.onResume();
    }

    @Override
    protected void onPause() {
        // Let JW Player know that the app is going to the background
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        OnSwitchListener.removeInstance();
    }

    @Override
    protected void onDestroy() {
        // Let JW Player know that the app is being destroyed
        super.onDestroy();
    }

}
