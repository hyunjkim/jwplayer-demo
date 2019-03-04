package com.example.jwplayersdk.jwplayerdemo.jwsettings;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jwplayersdk.jwplayerdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JWPlayerViewSettings extends Fragment{

    @BindViews({R.id.check_mediaID, R.id.check_playlistID,R.id.check_title,R.id.check_image,R.id.check_description,R.id.check_headers})
    List<CheckBox> checkBoxSettingsList;

    @BindViews({R.id.add_mediaID,R.id.add_playlistID,R.id.add_title,R.id.add_image,R.id.add_description,R.id.add_stream_tag,R.id.add_header_key,R.id.add_header_value,R.id.add_widevine_key,R.id.add_widevine_value})
    List<EditText> editTextList;

    @BindView(R.id.check_widevinedrm)
    RadioButton checkWideVineDrm;

    @BindView(R.id.add_drm_values)
    LinearLayout addDrmValues;

    @BindView(R.id.custom_headers_box)
    LinearLayout customHeadersBox;

    @BindView(R.id.add_custom_headers)
    LinearLayout addCustomHeaders;


//    private CheckBox checkBoxPlaylistID,checkBoxMediaID,checkBoxDRM,addHeaders,checkBoxTitle, checkBoxImage, checkBoxDescription, addCustomHeaders;
//    private EditText mPlaylistID,mMediaID, setTitle,setImage,setDescription,addDrmValues,addStreamTag, addWideVineKey, addWideVineValue, addHeaderKey, addHeaderValue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Show the save button on the action bar
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_jwplayerviewsettings, container, false);
        ButterKnife.bind(this, view);

        // Checkboxes
//        checkBoxMediaID = view.findViewById(R.id.check_mediaID);
//        checkBoxPlaylistID = view.findViewById(R.id.check_playlistID);
//        checkBoxTitle = view.findViewById(R.id.check_title);
//        checkBoxImage = view.findViewById(R.id.check_image);
//        checkBoxDescription = view.findViewById(R.id.check_description);
//        checkBoxDRM = view.findViewById(R.id.check_widevinedrm); // if checked, show addDrmValues
//        addHeaders = view.findViewById(R.id.check_headers); // if checked, show addCustomHeaders
//
//        // Edit Text(s)
//        mMediaID = view.findViewById(R.id.add_mediaID); // if checkBoxMediaID is checked, disable everyone else
//        mPlaylistID = view.findViewById(R.id.add_playlistID); // if checkBoxPlaylistID is checked, disable everyone else
//        setTitle = view.findViewById(R.id.add_title);
//        setImage = view.findViewById(R.id.add_image);
//        setDescription = view.findViewById(R.id.add_description);
//        addStreamTag = view.findViewById(R.id.add_stream_tag);
//
//        // Show the Edit Text(s)
//        addDrmValues = view.findViewById(R.id.add_drm_values);
//        addCustomHeaders = view.findViewById(R.id.add_custom_headers);
//
//        // When the DRM or Headers were checked, then get the values that are passed here
//        addWideVineKey = view.findViewById(R.id.add_header_key);
//        addHeaderKey = view.findViewById(R.id.add_header_value);
//        addWideVineKey = view.findViewById(R.id.add_widevine_key);
//        addHeaderValue = view.findViewById(R.id.add_widevine_value);
//        Button doneBtn = view.findViewById(R.id.done_btn);

        return view;
    }

    @OnClick({R.id.check_widevinedrm,})
    void onRadioButtonSelected(View radioBtn) {

        switch(radioBtn.getId()){
            case R.id.check_widevinedrm:
                toast("Use Widevine DRM");
                this.getView().findViewById(R.id.check_headers).setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick({R.id.check_mediaID,R.id.check_playlistID,R.id.check_title,R.id.check_image,R.id.check_description,R.id.check_headers})
    void onCheckboxClicked(View checkBoxes) {

        int id = checkBoxes.getId();

        switch(checkBoxes.getId()){
            case R.id.check_mediaID:
                toast("Add Media ID");
                break;
            case R.id.check_playlistID:
                toast("Add Playlist ID");
                break;
            case R.id.check_title:
                toast("Add Title");
                break;
            case R.id.check_image:
                toast("Add Image");
                break;
            case R.id.check_description:
                toast("Add Description");
                break;
            case R.id.check_headers:
                toast(" Add Custom Headers");
                JWUIVisibilitySettings.showLinearLayoutOptions(customHeadersBox);
                break;
        }

        if(id != R.id.check_widevinedrm && id != R.id.check_headers) {
            JWUIVisibilitySettings.setChecked(editTextList,id,checkBoxes.getId());
        }

    }


    private void toast(String str) {
        Toast.makeText(getContext(),str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_save:
                Toast.makeText(getContext(),"Saved!", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
