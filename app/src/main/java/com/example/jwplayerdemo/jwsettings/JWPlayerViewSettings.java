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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.jwplayerdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JWPlayerViewSettings extends Fragment {

    //    @BindViews({R.id.check_mediaID, R.id.check_playlistID, R.id.check_title, R.id.check_image, R.id.check_description, R.id.check_headers, R.id.check_widevinedrm})
//    List<CheckBox> checkBoxSettingsList;


//    @BindViews({R.id.add_mediaID, R.id.add_playlistID, R.id.add_title, R.id.add_image, R.id.add_description, R.id.add_stream_tag, R.id.add_header_key, R.id.add_header_value})

    @BindViews({R.id.check_headers, R.id.check_widevinedrm})
    List<CheckBox> checkBoxSettingsList;

    @BindView(R.id.add_more_headers)
    Button add_headers_button;

    @BindView(R.id.custom_headers_box)
    LinearLayout custom_header;

    @BindView(R.id.add_widevine)
    LinearLayout widevine;

    @BindView(R.id.add_http_headers)
    LinearLayout custom_http_header;

    //    final ButterKnife.Action<LinearLayout> SHOW_HEADERBOX = ((linearlayout, index) -> {
//
//        LinearLayout moreHeaders = new LinearLayout(linearlayout.getContext()); // create new headers
//
//        if (httpHeadersChecked) {
//            custom_http_header.addView(moreHeaders);
//            moreHeaders.setVisibility(View.VISIBLE); // add new headers
//        } else {
//            widevine.addView(moreHeaders);
//            moreHeaders.setVisibility(View.VISIBLE); // add new headers
//        }
//    });
//
//    final ButterKnife.Action<LinearLayout> HIDE_HEADERBOX = (linearlayout, index) -> {
//        return linearlayout.setVisibility(View.GONE);
//    };
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

        // Initialize View Listeners
//        checkBoxListeners();
        editorActionListeners(view);

        // Bind all my views
        ButterKnife.bind(this, view);

        // TODO: I might now user butterknife because it does not seem to get updated
        // Hide the Header box
//        ButterKnife.bind(custom_header,HIDE_HEADERBOX);
//        ButterKnife.apply(custom_header, HIDE_HEADERBOX);

        // Show the Header box
//        ButterKnife.apply(custom_header, SHOW_HEADERBOX);

        return view;
    }

//    @OnEditorAction({R.id.add_mediaID, R.id.add_playlistID, R.id.add_title, R.id.add_image, R.id.add_description, R.id.add_stream_tag, R.id.add_header_key, R.id.add_header_value})
//    boolean setupStream(TextView editText, int actionId, KeyEvent event) {
//
//        String text = editText.getText().toString();
//        int id = editText.getId();
//
//        switch (id) {
//            case R.id.add_stream_tag:
//                toast("Add Stream Tag");
//                Log.i("HYUNJOO", "Add stream tag: " + text);
//                jwViewModel.setFile(text);
//                return true;
////            case R.id.add_mediaID:
////                toast("Add Media ID");
////                jwViewModel.setMediaId(text);
////                return true;
////            case R.id.add_playlistID:
////                toast("Add Playlist ID");
////                jwViewModel.setPlaylistId(text);
////                return true;
////            case R.id.add_title:
////                toast("Add Title");
////                jwViewModel.setTitle(text);
////                return true;
////            case R.id.add_image:
////                toast("Add image");
////                jwViewModel.setImage(text);
////                return true;
////            case R.id.add_description:
////                toast("Add Decription");
////                jwViewModel.setDescription(text);
////                return true;
////            case R.id.add_header_key:
////                toast(" Add Custom Header Key");
////                jwViewModel.setHeaderKey(text);
//////                JWUIVisibilitySettings.showLinearLayoutOptions(customHeadersBox);
////                break;
////            case R.id.add_header_value:
////                toast(" Add Custom HeaderValue");
////                jwViewModel.setHeaderValue(text);
//////                JWUIVisibilitySettings.showLinearLayoutOptions(customHeadersBox);
////                break;
////            case R.id.add_header_key:
////                toast(" Add Widevine key");
//////                JWUIVisibilitySettings.showLinearLayoutOptions(customHeadersBox);
////                break;
////            case R.id.add_widevine_value:
////                toast(" Add Widevine value");
//////                JWUIVisibilitySettings.showLinearLayoutOptions(customHeadersBox);
////                break;
////            }
//        }
//
//        return false;
//    }

    /*
     * Initialize EditText listener
     * */
    private void editorActionListeners(View view) {
//        EditText tag = view.findViewById(R.id.add_stream_tag);
//        String text = tag.getText().toString();
//        Log.i("HYUNJOO", "afterTextChanged() " + text);
//        jwViewModel.setFile(text);

        int[] editTextList = {
                R.id.add_mediaID,
                R.id.add_playlistID,
                R.id.add_title,
                R.id.add_image,
                R.id.add_description,
                R.id.add_stream_tag,
                R.id.add_header_key,
                R.id.add_header_value
        };
        for (int each : editTextList) {
            EditText editText = view.findViewById(each);
            OnTextChangeListener textChangeListener = new OnTextChangeListener(editText, jwViewModel);
            editText.addTextChangedListener(textChangeListener);
        }

    }

    /**
     * TODO: I might have to change this like the EditorActionListeners
     */
    @OnClick({R.id.add_more_headers, R.id.check_mediaID, R.id.check_playlistID, R.id.check_title, R.id.check_image, R.id.check_description, R.id.check_widevinedrm, R.id.check_headers,})
    void onCheckboxClicked(View checkBoxes) {

        int id = checkBoxes.getId();

        switch (id) {
            case R.id.check_mediaID:
                toast("Add Media ID");
                break;
            case R.id.check_playlistID:
                toast("Add Playlist ID");
                break;
            case R.id.check_title:
                toast("Title Checked");
                break;
            case R.id.check_image:
                toast("Image Checked");
                break;
            case R.id.check_description:
                toast("Description Checked");
                break;
            case R.id.check_widevinedrm:
                toast("Checked Widevine DRM");
                break;
            case R.id.check_headers:
                toast("Checked Headers");
                break;
            case R.id.add_more_headers:
                toast("Add More Headers Below");
                break;
        }

        if (id != R.id.check_widevinedrm && id != R.id.check_headers) {
            //do something
        }
    }

    /*
     * Display toast for user
     * */
    private void toast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
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
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_save:
                Log.i("HYUNJOO", "JW Settings - SAVED ");
                jwViewModel.setCurrentConfig(); // Setting up the PlayerConfig in JWViewModel
                callback.onSettingsChanged(); // Notify JWMainActivity that the settings were updated
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Communicate with other fragments
     * The Fragment captures the interface implementation during its onAttach() lifecycle method
     * and can then call the Interface methods in order to communicate with the Activity.
     * <p>
     * {@link - https://developer.android.com/training/basics/fragments/communicating#java}
     */
    public void addOnSettingsListener(OnSettingsListener callback) {
        this.callback = callback;
    }

    /**
     * This interface can be implemented by the Activity, parent Fragment, or a separate test implementation.
     */
    public interface OnSettingsListener {
        void onSettingsChanged();
    }
}
