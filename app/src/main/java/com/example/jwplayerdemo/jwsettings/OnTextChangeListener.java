package com.example.jwplayerdemo.jwsettings;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.jwplayerdemo.R;

public class OnTextChangeListener implements TextWatcher {

    private int viewId;
    private JWViewModel configStore;

    OnTextChangeListener(EditText editText, JWViewModel configStore) {
        this.viewId = editText.getId();
        this.configStore = configStore;
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
                configStore.setFile(text);
                break;
            case R.id.add_mediaID:
                configStore.setMediaId(text);
            case R.id.add_playlistID:
                configStore.setPlaylistId(text);
                break;
            case R.id.add_title:
                configStore.setTitle(text);
                break;
            case R.id.add_image:
                configStore.setImage(text);
                break;
            case R.id.add_description:
                configStore.setDescription(text);
                break;
            case R.id.add_header_key:
                break;
            case R.id.add_header_value:
                break;
        }
    }
}
