package com.example.jwplayerdemo.jwsettings;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.jwplayerdemo.R;

public class OnTextChangeListener implements TextWatcher {

    private int viewId;
    private JWViewModel model;

    OnTextChangeListener(EditText editText, JWViewModel model) {
        this.viewId = editText.getId();
        this.model = model;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        switch (viewId) {
            case R.id.add_stream_tag:
                model.setFile(text);
                break;
            case R.id.add_mediaID:
                model.setMediaId(text);
            case R.id.add_playlistID:
                model.setPlaylistId(text);
                break;
            case R.id.add_title:
                model.setTitle(text);
                break;
            case R.id.add_image:
                model.setImage(text);
                break;
            case R.id.add_description:
                model.setDescription(text);
                break;
            case R.id.add_header_key:
                break;
            case R.id.add_header_value:
                break;
        }
    }
}
