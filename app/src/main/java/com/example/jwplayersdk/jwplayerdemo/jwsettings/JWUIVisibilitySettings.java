package com.example.jwplayersdk.jwplayerdemo.jwsettings;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

class JWUIVisibilitySettings {

    private static List<Integer> checkedItems = new ArrayList<>(); // Double check if this is okay to do, to instantiate it here

    private static List<CheckBox> mCheckBoxSettingsList;

    static void setChecked(List<CheckBox> checkBoxSettingsList, int checked) {
        mCheckBoxSettingsList = checkBoxSettingsList;
        checkedItems.add(checked);
        showOptions(checked);
        hideOptions();
    }

    static List<Integer> getChecked() {
        return checkedItems;
    }

    /*
     * Show the optional section
     * TODO: pass each index and then set the view -OR- get an array and set them all at once
     * */
    private static void showOptions(int index) {
        if(index >= 0){
            mCheckBoxSettingsList.get(index).setVisibility(View.VISIBLE);
        }
    }

    /*
    * Show the options for headers and
    * */
    static void showOptions(LinearLayout additionalOptionsUI) {
        additionalOptionsUI.setVisibility(View.VISIBLE);
    }

    /*
     * Hide the optional section
     * */
    static void hideOptions() {
        for(CheckBox eachCheckBox : mCheckBoxSettingsList){
            eachCheckBox.setVisibility(View.GONE);
        }
    }
}
