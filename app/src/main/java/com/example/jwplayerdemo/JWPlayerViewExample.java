package com.example.jwplayerdemo;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.jwplayerdemo.eventhandlers.JWAdEventHandler;
import com.example.jwplayerdemo.eventhandlers.JWEventHandler;
import com.example.jwplayerdemo.eventhandlers.KeepScreenOnHandler;
import com.example.jwplayerdemo.jwutil.Logger;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.configuration.SkinConfig;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.ads.AdBreak;
import com.longtailvideo.jwplayer.media.ads.AdSource;
import com.longtailvideo.jwplayer.media.ads.Advertising;
import com.longtailvideo.jwplayer.media.ads.ImaAdvertising;
import com.longtailvideo.jwplayer.media.playlists.MediaSource;
import com.longtailvideo.jwplayer.media.playlists.MediaType;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

import java.util.ArrayList;
import java.util.List;

public class JWPlayerViewExample extends Fragment implements
        VideoPlayerEvents.OnFullscreenListener {

    /**
     * Reference to the {@link com.longtailvideo.jwplayer.JWPlayerView}
     */
    private JWPlayerView mPlayerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_jwplayerview, container, false);

        Logger.newInstance();

        mPlayerView = view.findViewById(R.id.jwplayer);
        TextView outputTextView = view.findViewById(R.id.output);
//        ScrollView scrollView = view.findViewById(R.id.scroll);
//        NestedScrollView scrollView = view.findViewById(R.id.scroll);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

//        initializeFabric();

        // Setup JWPlayer
        setupJWPlayer();

        // Handle hiding/showing of ActionBar
        mPlayerView.addOnFullscreenListener(this);

        // Keep the screen on during playback
        new KeepScreenOnHandler(mPlayerView, getActivity().getWindow());

        // Instantiate the JW Player event handler class
//        new JWEventHandler(mPlayerView, outputTextView, scrollView);

        // Instantiate the JW Player Ad event handler class
//        new JWAdEventHandler(mPlayerView, outputTextView, scrollView);

        return view;
    }


    private void initializeFabric() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods

        Crashlytics.setUserIdentifier("JWPlayer Android SDK Demo Version 3");
        Crashlytics.setUserEmail("hkim@jwplayer.com");
        Crashlytics.setUserName("Test User: Hyunjoo");
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

        List<PlaylistItem> playlistItemList = createPlaylist();

        // Ima Tag Example
        ImaAdvertising imaAdvertising = getImaAd();

        // VAST Tag Example
        Advertising vastAdvertising = getVastAd();

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
                .allowCrossProtocolRedirects(true)
                .advertising(vastAdvertising)
//				.advertising(imaAdvertising)
                .skinConfig(skinConfig)
                .build();

        mPlayerView.setup(config);
    }

    /*
     * Vast Setup Example
     * */

    private Advertising getVastAd() {
        List<AdBreak> adbreaklist = new ArrayList<>();

        String ad = "https://s3.amazonaws.com/abpdemooutput.transcoded-videos/3_adcampaign.xml";
        String vpaid = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dlinearvpaid2js&correlator=";

        AdBreak adbreak = new AdBreak("pre", AdSource.VAST, ad);

        adbreaklist.add(adbreak);

//		AdRules adRules = new AdRules.Builder()
//				.frequency(1)
//				.startOn(0)
//				.startOnSeek(AdRules.RULES_START_ON_SEEK_PRE)
//				.timeBetweenAds(2)
//				.build();

        Advertising vastad = new Advertising(AdSource.VAST, adbreaklist);
        vastad.setVpaidControls(true);
//		vastad.setAdRules(adRules);
//		vastad.setClient(AdSource.VAST);
//		vastad.setRequestTimeout(2);
//		vastad.setSkipOffset(1);
//		vastad.setAdMessage("");
//		vastad.setCueText("");
//		vastad.setSkipMessage("");
//		vastad.setSkipText("");

        return vastad;
    }

    /*
     * IMA Ad Example
     * */
    private ImaAdvertising getImaAd() {
        List<AdBreak> adbreaklist = new ArrayList<>();

        String ad = "";

        AdBreak adBreak = new AdBreak("pre", AdSource.IMA, ad);

        adbreaklist.add(adBreak);

        ImaSdkSettings imaSettings = ImaSdkFactory.getInstance().createImaSdkSettings();
//		imaSettings.setRestrictToCustomPlayer(true);
//		imaSettings.setPpid("");
//		imaSettings.setPlayerVersion("");
//		imaSettings.setPlayerType("");
//		imaSettings.setMaxRedirects(1);
//		imaSettings.setLanguage("");
//		imaSettings.setEnableOmidExperimentally(true);
//		imaSettings.setDebugMode(true);
//		imaSettings.setAutoPlayAdBreaks(true);

        return new ImaAdvertising(adbreaklist, imaSettings);
    }

    /*
     * Create a Playlist Example
     * */
    private List<PlaylistItem> createPlaylist() {
        List<PlaylistItem> playlistItemList = new ArrayList<>();

        String[] playlist = {
                "https://cdn.jwplayer.com/manifests/jumBvHdL.m3u8",
                "http://content.jwplatform.com/videos/tkM1zvBq-cIp6U8lV.mp4",
                "http://content.jwplatform.com/videos/RDn7eg0o-cIp6U8lV.mp4",
                "http://content.jwplatform.com/videos/i3q4gcBi-cIp6U8lV.mp4",
                "http://content.jwplatform.com/videos/iLwfYW2S-cIp6U8lV.mp4",
                "http://content.jwplatform.com/videos/8TbJTFy5-cIp6U8lV.mp4",
                "http://playertest.longtailvideo.com/adaptive/bipbop/gear4/prog_index.m3u8",
        };

        for (String each : playlist) {
            PlaylistItem item = new PlaylistItem(each);
            playlistItemList.add(item);
        }

        return playlistItemList;
    }

    /**
     * MediaSource Playlist Example
     */
    private List<PlaylistItem> createMediaSourcePlaylist() {
        List<MediaSource> mediaSourceList = new ArrayList<>();
        List<PlaylistItem> playlistItemList = new ArrayList<>();

        String hls = "https://cdn.jwplayer.com/manifests/jumBvHdL.m3u8";

        MediaSource ms = new MediaSource.Builder()
                .file(hls)
                .type(MediaType.HLS)
                .build();
        mediaSourceList.add(ms);

        PlaylistItem item = new PlaylistItem.Builder()
                .sources(mediaSourceList)
                .build();

        playlistItemList.add(item);

        return playlistItemList;
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
    public void onDestroy() {
        // Let JW Player know that the app is being destroyed
        mPlayerView.onDestroy();
        super.onDestroy();
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // Exit fullscreen when the user pressed the Back button
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mPlayerView.getFullscreen()) {
//                mPlayerView.setFullscreen(false, true);
//                return false;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_jwplayerview, menu);
//        // Register the MediaRouterButton on the JW Player SDK
//        mCastManager.addMediaRouterButton(menu, R.id.media_route_menu_item);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.switch_to_fragment:
//                Intent i = new Intent(this, JWPlayerFragmentExample.class);
//                startActivity(i);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}
