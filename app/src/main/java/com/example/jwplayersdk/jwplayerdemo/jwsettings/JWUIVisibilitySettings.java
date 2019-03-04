package com.example.jwplayersdk.jwplayerdemo.jwsettings;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

class JWUIVisibilitySettings {

    private static List<Integer> checkedItems = new ArrayList<>(); // Double check if this is okay to do, to instantiate it here

    private static List<CheckBox> mCheckBoxSettingsList;

    private static List<EditText> mEditTextList;

    static void setChecked(List<EditText> editTextList, int id, int checked) {
        mEditTextList = editTextList;
        checkedItems.add(checked);
        getValues(id);
        hideOptions();
    }

    static List<Integer> getChecked() {
        return checkedItems;
    }

    static void setRadioBtn(List<CheckBox> checkBoxSettingsList, int radioBtn) {
        mCheckBoxSettingsList = checkBoxSettingsList;
    }

    /*
     * Show the optional section
     * TODO: pass each index and then set the view -OR- get an array and set them all at once
     * */
    static void showOptions(int index) {
        if(index >= 0){
            print("Check Box setting list :" + mCheckBoxSettingsList.get(index));
            mCheckBoxSettingsList.get(index).setVisibility(View.VISIBLE);
        }
    }

    private static void getValues(int index) {
        if(index >= 0){
            mEditTextList.get(index).getText();
        }
    }

    private static void print(String s) {
        Log.i("JWEVENT", "JWUIVisibilitySettings: "+ s);
    }

    /*
    * Show the options for headers and
    * */
    static void showLinearLayoutOptions(LinearLayout additionalOptionsUI) {
        additionalOptionsUI.setVisibility(View.VISIBLE);
    }

    /*
     * Hide the optional section
     * */
    static void hideOptions() {
        for(CheckBox eachCheckBox : mCheckBoxSettingsList){
            if(!checkedItems.contains(eachCheckBox)) eachCheckBox.setVisibility(View.GONE);
        }
    }

}
