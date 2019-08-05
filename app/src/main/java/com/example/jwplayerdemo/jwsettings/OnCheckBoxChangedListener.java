package com.example.jwplayerdemo.jwsettings;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.jwplayerdemo.R;
import com.example.jwplayerdemo.jwutilities.JWLogger;

public class OnCheckBoxChangedListener implements CheckBox.OnCheckedChangeListener {

    private CheckBox checkbox;
    private JWViewModel model;

    OnCheckBoxChangedListener(CheckBox checkBox, JWViewModel model) {
        this.checkbox = checkBox;
        this.model = model;
    }

    @Override
    public void onCheckedChanged(CompoundButton checkbox, boolean isChecked) {

        int id = checkbox.getId();

        switch (id) {
            case R.id.check_mediaID:
                JWLogger.log("Add Media ID: "+ isChecked);
                break;
            case R.id.check_playlistID:
                JWLogger.log("Add Playlist ID: "+ isChecked);
                break;
            case R.id.check_title:
                JWLogger.log("Title Checked: "+ isChecked);
                model.showTitle(isChecked);
                break;
            case R.id.check_image:
                model.showImage(isChecked);
                JWLogger.log("Image Checked: "+ isChecked);
                break;
            case R.id.check_description:
                model.showDescription(isChecked);
                JWLogger.log("Description Checked: "+ isChecked);
                break;
            case R.id.check_widevinedrm:
                JWLogger.log("Checked Widevine DRM");
                break;
            case R.id.check_headers:
                JWLogger.log("Checked Headers");
                break;
            case R.id.add_more_headers:
                JWLogger.log("Add More Headers Below");
                break;
        }
    }

    boolean show(CheckBox each) {
        switch (each.getId()) {
            case R.id.check_mediaID:
                return model.isMediaIdChecked();
            case R.id.check_playlistID:
                return model.isPlaylistIdChecked();
            case R.id.check_title:
                return model.isTitleChecked();
            case R.id.check_image:
                return model.isImageChecked();
            case R.id.check_description:
                return model.isDescriptionChecked();
            case R.id.check_widevinedrm:
                return false;
            case R.id.check_headers:
                return false;
            case R.id.add_more_headers:
                return false;
        }
        return false;
    }
}
