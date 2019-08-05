package com.example.jwplayerdemo.jwsettings;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jwplayerdemo.JWMainActivity;
import com.example.jwplayerdemo.JWPlayerViewExample;
import com.example.jwplayerdemo.jwutilities.JWLogger;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

import java.util.ArrayList;
import java.util.List;

/**
 * JWPlayer Config Settings Storage
 *
 * <p>
 * Sometimes you might need an Application context (as opposed to an Activity context) for use with things like system services.
 * Storing an Application context in a ViewModel is okay because an Application context is tied to the Application lifecycle.
 * This is different from an Activity context, which is tied to the Activity lifecycle.
 * In fact, if you need an Application context, you should extend AndroidViewModel which is simply
 * a ViewModel that includes an Application reference. - I did not do this step
 * <p>
 * The first time the ViewModelProviders.of method is called by JWMainActivity, it creates a new ViewModel instance.
 * When this method is called again, which happens whenever onCreate is called,
 * it will return the pre-existing ViewModel associated with the specific Court-Counter JWMainActivity.
 * This is what preserves the data.
 * <p>
 *
 * @see #ViewModel
 * Credits to: {@link - https://medium.com/androiddevelopers/viewmodels-a-simple-example-ed5ac416317e}
 */

public class JWViewModel extends ViewModel {

    // static will only allocate memory for it once across all instances so it's a little more resource efficient
    private final static String DEFAULT_URL = "https://content.jwplatform.com/videos/8TbJTFy5-cIp6U8lV.mp4";

    private MutableLiveData<PlayerConfig> selected = new MutableLiveData<>();
    private PlaylistItem playlistItem = new PlaylistItem(DEFAULT_URL);
    private PlayerConfig config = new PlayerConfig.Builder().file(DEFAULT_URL).build();
    private boolean checkedTitle = false,
            checkedDescription = false,
            checkedImage = false,
            checkedMediaId = false,
            checkedPlaylistId = false,
            autostart = false,
            repeat = false,
            mute = false;
    private boolean isSettingsUpdated = false;

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    /**
     * Made public so other packages can access this, this is how all the Fragments have access to this data store
     *
     * @see JWMainActivity
     * @see JWPlayerViewExample
     * @see JWPlayerViewSettings
     */
    public LiveData<PlayerConfig> getPlayerConfig() {
        Log.i("HYUNJOO", "JWViewModel - getPlayerConfig() ");
        return selected;
    }

    /**
     * When user clicks "SAVE"
     *
     * @see JWPlayerViewSettings#onOptionsItemSelected(android.view.MenuItem)
     */
    void setCurrentConfig() {
        Log.i("HYUNJOO", "JWViewModel - setupConfig() ");
        isSettingsUpdated = true;
        selected.setValue(setupConfig());
    }

    /*
     * Setup JW Config
     * */
    private PlayerConfig setupConfig() {
        Log.i("HYUNJOO", "JWViewModel - getConfig() ");
        if(config == null) {
          config = getDefaultConfig();
        }
        return config;
    }

    void setFile(String file) {
        // If the file starts with "{" then this is a json
        if (file.startsWith("{")) {
            List<PlaylistItem> playlist = PlaylistItem.listFromJson(file);
            config.setPlaylist(playlist);
        } else {
            config.setFile(file); // otherwise, just pass this as a file
        }
    }

    void setMediaId(String mediaid) {
    }

    void setPlaylistId(String playlistid) {
    }

    void setTitle(String title) {
        JWLogger.log("JWViewModel - Add Title: " + title);
        playlistItem.setTitle(title);
        config.setDisplayTitle(checkedTitle);
    }

    void setDescription(String description) {
        JWLogger.log("JWViewModel - Add Description: " + description);
        playlistItem.setDescription(description);
        config.setDisplayDescription(checkedDescription);
    }

    void setImage(String image) {
        JWLogger.log("JWViewModel - Add Image: " + image);
        if (checkedImage) {
            playlistItem.setImage(image);
        }
    }

    void showTitle(boolean isChecked) {
        checkedTitle = isChecked;
        if (!checkedTitle) playlistItem.setTitle("");
    }{

    }

    void showDescription(boolean isChecked) {
        checkedDescription = isChecked;
        if (!checkedDescription) {
            playlistItem.setDescription("");
        }
    }

    void showImage(boolean isChecked) {
        checkedImage = isChecked;
        if (!checkedImage) {
            config.setImage("");
        }
    }

    boolean getAutoStart() {
        JWLogger.log("Autostart: " + autostart);
        return autostart;
    }

    void setAutoStart(boolean isChecked) {
        autostart = isChecked;
        config.setAutostart(isChecked);
    }

    boolean getRepeat() {
        JWLogger.log("Repeat mode " + repeat);
        return repeat;
    }

    void setRepeat(boolean isChecked) {
        repeat = isChecked;
        config.setRepeat(isChecked);
    }

    boolean getMute() {
        JWLogger.log("Mute " + mute);
        return mute;
    }

    void setMute(boolean isChecked) {
        mute = isChecked;
        config.setMute(isChecked);
    }

    boolean isMediaIdChecked() {
        return checkedMediaId;
    }

    boolean isPlaylistIdChecked() {
        return checkedPlaylistId;
    }

    boolean isTitleChecked() {
        return checkedTitle;
    }

    boolean isDescriptionChecked() {
        return checkedDescription;
    }

    boolean isImageChecked() {
        return checkedImage;
    }

    public boolean didUserClickSave() {
        return isSettingsUpdated;
    }

    public PlayerConfig getDefaultConfig() {
        return new PlayerConfig.Builder()
                .file("https://content.jwplatform.com/videos/8TbJTFy5-cIp6U8lV.mp4")
                .autostart(true)
                .preload(true)
                .allowCrossProtocolRedirects(true)
                .build();
    }
}
