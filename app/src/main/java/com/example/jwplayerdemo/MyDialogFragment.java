package com.example.jwplayerdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.longtailvideo.jwplayer.JWPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDialogFragment extends DialogFragment {

    @BindView(R.id.check_licensekey)
    Button licenseKeyBtn;

    @BindView(R.id.enter_licensekey)
    EditText retrievelicenseKey;

    private String licensekey = "";

    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout to use as dialog or embedded fragment
        View view = inflater.inflate(R.layout.dialog_licensekeycheck, container, false);

        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.check_licensekey)
    public void onSetLicenseKey(View view){
        Log.i("HYUNJOO", "GET LICENSE KEY!");
        JWPlayerView.setLicenseKey(getContext(),retrievelicenseKey.getText().toString());
        licensekey = retrievelicenseKey.getText().toString();
        dismiss();
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public String getLicensekey() {
        return licensekey;
    }
}
