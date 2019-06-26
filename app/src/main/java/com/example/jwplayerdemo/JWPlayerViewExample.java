package com.example.jwplayerdemo;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jwplayerdemo.eventhandlers.JWAdEventHandler;
import com.example.jwplayerdemo.eventhandlers.JWEventHandler;
import com.example.jwplayerdemo.eventhandlers.KeepScreenOnHandler;
import com.example.jwplayerdemo.jwutil.JWLogger;
import com.example.jwplayerdemo.samples.SampleAds;
import com.example.jwplayerdemo.samples.SamplePlaylist;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.configuration.SkinConfig;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.ads.Advertising;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

import java.util.List;


public class JWPlayerViewExample extends Fragment implements
        VideoPlayerEvents.OnFullscreenListener {

    private final int TAG = 0;
    /**
     * Reference to the {@link com.longtailvideo.jwplayer.JWPlayerView}
     */
    private JWPlayerView mPlayerView;
    private String licenseKey = "";

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

        // Handle hiding/showing of ActionBar
        mPlayerView.addOnFullscreenListener(this);

        // Keep the screen on during playback
        new KeepScreenOnHandler(mPlayerView, getActivity().getWindow());

        // Display JWPlayerVersion
        JWLogger.generateOutput("Build version: " + mPlayerView.getVersionCode());

        // Instantiate the JW Player event handler class
        new JWEventHandler(mPlayerView, outputTextView, scrollView);

        // Instantiate the JW Player Ad event handler class
        new JWAdEventHandler(mPlayerView, outputTextView, scrollView);

        // Setup JWPlayer
        setupJWPlayer();

        return view;
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

        List<PlaylistItem> playlistItemList = SamplePlaylist.createPlaylist();

        // TODO: I want to get this string response from the USER
        String ad = "vast";

        // Get IMA or VAST Tag
        Advertising advertising = ad.equals("vast") ? SampleAds.getVastAd() : SampleAds.getImaAd();

        // SkinConifg - more info: https://developer.jwplayer.com/sdk/android/reference/com/longtailvideo/jwplayer/configuration/SkinConfig.Builder.html
        SkinConfig skinConfig = new SkinConfig.Builder()
                .url("https://myserver.com/css/mycustomcss.css")
                .name("mycustomcss")
                .build();

        // More info: https://developer.jwplayer.com/sdk/android/reference/com/longtailvideo/jwplayer/configuration/PlayerConfig.Builder.html
        PlayerConfig config = new PlayerConfig.Builder()
                .playlist(playlistItemList)
                .autostart(true)
                .preload(true)
                .mute(true)
                .allowCrossProtocolRedirects(true)
                .advertising(advertising)
                .skinConfig(skinConfig)
                .build();

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
//        JWPlayerView.setLicenseKey(getContext(),dialogFragment.getLicensekey());
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
