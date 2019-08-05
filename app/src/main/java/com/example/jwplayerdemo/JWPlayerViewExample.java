package com.example.jwplayerdemo;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.jwplayerdemo.eventhandlers.JWAdEventHandler;
import com.example.jwplayerdemo.eventhandlers.JWEventHandler;
import com.example.jwplayerdemo.eventhandlers.KeepScreenOnHandler;
import com.example.jwplayerdemo.jwsettings.JWViewModel;
import com.example.jwplayerdemo.jwutilities.JWLogger;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;


public class JWPlayerViewExample extends Fragment implements
        VideoPlayerEvents.OnFullscreenListener {

    private final int TAG = 0;
    /**
     * Reference to the {@link com.longtailvideo.jwplayer.JWPlayerView}
     */
    private JWPlayerView mPlayerView;
    private String licenseKey = "";
    private PlayerConfig config;

    private JWViewModel jwViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a new View Model instance
        jwViewModel = ViewModelProviders
                .of(getMainActivity())
                .get(JWViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Show the Cast and License Key button on the action bar
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_jwplayerview, container, false);

        mPlayerView = view.findViewById(R.id.jwplayer);
        TextView outputTextView = view.findViewById(R.id.output);
        NestedScrollView scrollView = view.findViewById(R.id.nestedscrollview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // Display JWPlayerVersion
        JWLogger.generateOutput("Build version: " + mPlayerView.getVersionCode());

        // Handle hiding/showing of ActionBar
        mPlayerView.addOnFullscreenListener(this);

        // Keep the screen on during playback
        new KeepScreenOnHandler(mPlayerView, getActivity().getWindow());

        // Instantiate the JW Player event handler class
        new JWEventHandler(mPlayerView, outputTextView, scrollView);

        // Instantiate the JW Player Ad event handler class
        new JWAdEventHandler(mPlayerView, outputTextView, scrollView);

        // Setup JWPlayer
        setupJWPlayer();

        return view;
    }

    /*
     * Get the associated Activity which is JWMainActivity
     * */
    private FragmentActivity getMainActivity() {
        return getActivity();
    }

    /*
     * Setup JW Player
     * More info for our Player Configurations and other Configurations:
     * {@link - https://developer.jwplayer.com/sdk/android/reference/com/longtailvideo/jwplayer/configuration/package-summary.html}
     * 1 - PlayerConfig
     * 2 - LogoConfig
     * 3 - PlaybackRateConfig
     * 4 - CaptionsConfig
     * 5 - RelatedConfig
     * 6 - SharingConfig
     * 7 - SkinConfig
     * */
    private void setupJWPlayer() {

        // if user clicked save
        if (jwViewModel.didUserClickSave()) {
            config = jwViewModel.getPlayerConfig().getValue();
        }

        // if the config is null from jwViewModel and in this class
        if (config == null) {
            config = jwViewModel.getDefaultConfig();
        }

        mPlayerView.setup(config);
    }

    /*
     * In landscape mode, set to fullscreen or if the user clicks the fullscreen button
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // Set fullscreen when the device is rotated to landscape
        mPlayerView.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE, false);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPlayerView.onStart();
    }

    @Override
    public void onResume() {
        // Let JW Player know that the app has returned from the background
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    public void onPause() {
        // Let JW Player know that the app is going to the background
        mPlayerView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPlayerView.onStop();
        JWLogger.reset();
    }

    @Override
    public void onDestroy() {
        // Let JW Player know that the app is being destroyed
        mPlayerView.onDestroy();
        super.onDestroy();
    }

    boolean onMyKeyDown(int keyCode, KeyEvent event) {
        // Exit fullscreen when the user pressed the Back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPlayerView.getFullscreen()) {
                mPlayerView.setFullscreen(false, true);
                return false;
            }
        }
        return true;
    }


    /**
     * Handles JW Player going to and returning from fullscreen by hiding the ActionBar
     *
     * @param fullscreenEvent true if the player is fullscreen
     */
    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            if (fullscreenEvent.getFullscreen()) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
        }
    }

    /*
     * Shows only the license key button
     * */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_jwplayerview, menu);

        // inflate chromecast button here
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*
     * Alert Dialog will popup for license key
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.licensekey_dropdown:
                Toast.makeText(getContext(), "SHOW DIALOG!", Toast.LENGTH_LONG).show();
                MyDialogFragment dialogFragment = new MyDialogFragment();
                if (getFragmentManager() != null) {
                    dialogFragment.show(getFragmentManager(), "");
                    JWPlayerView.setLicenseKey(getContext(), dialogFragment.getLicensekey());
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
