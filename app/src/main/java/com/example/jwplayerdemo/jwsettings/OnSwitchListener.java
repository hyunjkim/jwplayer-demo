package com.example.jwplayerdemo.jwsettings;

import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;

import com.example.jwplayerdemo.R;
import com.example.jwplayerdemo.jwutilities.JWLogger;

public class OnSwitchListener implements CompoundButton.OnCheckedChangeListener {

    private JWViewModel model;
    private SwitchCompat switches;
    private static OnSwitchListener switchListener;
    private boolean autostart = false, repeat = false, mute = false;

    private OnSwitchListener(SwitchCompat switches, JWViewModel model){
        this.switches = switches;
        this.model = model;
    }

    static OnSwitchListener newInstance(SwitchCompat switches, JWViewModel model) {
        if(switchListener == null) switchListener = new OnSwitchListener(switches, model);
        return switchListener;
    }

    public static void removeInstance(){
        switchListener = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton checkbox, boolean isChecked) {
        int id = checkbox.getId();

        switch (id) {
            case R.id.switch_autostart:
                JWLogger.log("Autostart");
                model.setAutoStart(isChecked);
                break;
            case R.id.switch_repeat:
                JWLogger.log("Repeat mode");
                model.setRepeat(isChecked);
                break;
            case R.id.switch_mute:
                JWLogger.log("Mute");
                model.setMute(isChecked);
                break;
        }
    }

    boolean show(SwitchCompat each) {
        switch (each.getId()) {
            case R.id.switch_autostart:
                return model.getAutoStart();
            case R.id.switch_repeat:
                return model.getRepeat();
            case R.id.switch_mute:
                return model.getMute();
        }
        return false;
    }
}
