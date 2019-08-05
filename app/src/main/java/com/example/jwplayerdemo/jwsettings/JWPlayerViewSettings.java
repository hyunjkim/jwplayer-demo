package com.example.jwplayerdemo.jwsettings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.jwplayerdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class JWPlayerViewSettings extends Fragment {

    //    @BindViews({R.id.check_mediaID, R.id.check_playlistID, R.id.check_title, R.id.check_image, R.id.check_description, R.id.check_headers, R.id.check_widevinedrm})
//    List<CheckBox> checkBoxSettingsList;

    @BindViews({
            R.id.add_mediaID,
            R.id.add_playlistID,
            R.id.add_title,
            R.id.add_image,
            R.id.add_description,
            R.id.add_stream_tag,
            R.id.add_header_key,
            R.id.add_header_value
    })
    List<EditText> editTexts;

    @BindViews({
            R.id.check_mediaID,
            R.id.check_playlistID,
            R.id.check_title,
            R.id.check_image,
            R.id.check_description,
            R.id.check_widevinedrm,
            R.id.check_headers
    })
    List<CheckBox> checkBoxes;

    @BindViews({
            R.id.switch_autostart,
            R.id.switch_repeat,
            R.id.switch_mute,
    })
    List<SwitchCompat> switches;

    @BindView(R.id.add_more_headers)
    Button add_headers_button;

    @BindView(R.id.custom_headers_box)
    LinearLayout custom_header;

    @BindView(R.id.add_widevine)
    LinearLayout widevine;

    @BindView(R.id.add_http_headers)
    LinearLayout custom_http_header;

    private OnSettingsListener callback;
    private JWViewModel jwViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a new View Model instance
        jwViewModel = ViewModelProviders
                .of(getMainActivity())
                .get(JWViewModel.class);

    }

    /*
     * Get the associated Activity which is JWMainActivity
     * */
    private FragmentActivity getMainActivity() {
        return getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Show the save button on the action bar
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_jwplayerviewsettings, container, false);

        // Bind all my views
        ButterKnife.bind(this, view);

        // Initialize CheckBox Listeners
        checkBoxChangedListener();

        // Initialize EditText Listeners
        editTextListeners();

        // Initialize Switch Listeners
        switchListeners();

        return view;
    }

    /**
     * Initialize Switch listener
     * // TODO: I'm accessing the data from the JWViewModel, but I have to see if that is best pracitce Or I have to use sharedPreferences
     * {@link - https://stackoverflow.com/questions/25945742/change-android-switch-state}
     */
    private void switchListeners() {
        for (SwitchCompat each : switches) {
            OnSwitchListener switchListener = OnSwitchListener.newInstance(each, jwViewModel);
            each.setOnCheckedChangeListener(switchListener);
            each.setChecked(switchListener.show(each));
        }
    }

    /*
     * Initialize EditText listener
     * */
    private void editTextListeners() {
        for (EditText each : editTexts) {
            OnTextChangeListener textChangeListener = new OnTextChangeListener(each, jwViewModel);
            each.addTextChangedListener(textChangeListener);
        }
    }

    /*
     * Initialize CheckBox listener
     * */
    private void checkBoxChangedListener() {
        for (CheckBox each : checkBoxes) {
            OnCheckBoxChangedListener checkBoxChangedListener = new OnCheckBoxChangedListener(each, jwViewModel);
            each.setOnCheckedChangeListener(checkBoxChangedListener);
            each.setChecked(checkBoxChangedListener.show(each));
        }
    }

    /*
     * Inflate Actionbar options
     * */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Store settings to JWViewModel
     *
     * @see JWViewModel#setCurrentConfig()
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            Log.i("HYUNJOO", "JW Settings - SAVED ");
            jwViewModel.setCurrentConfig(); // Setting up the PlayerConfig in JWViewModel and lettings JWViewModel know the config is up-to-date
            callback.onSettingsChanged(); // Notify JWMainActivity that the settings were updated
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Communicate with other fragments
     * The Fragment captures the interface implementation during its onAttach() lifecycle method
     * and can then call the Interface methods in order to communicate with the Activity.
     * {@link - https://developer.android.com/training/basics/fragments/communicating#java}
     */
    public void addOnSettingsListener(OnSettingsListener callback) {
        this.callback = callback;
    }

    /**
     * This interface can be implemented by
     * the Activity,
     * parent Fragment,
     * or a separate test implementation.
     */
    public interface OnSettingsListener {
        void onSettingsChanged();
    }
}
