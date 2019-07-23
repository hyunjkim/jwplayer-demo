package com.example.jwplayerdemo.navigationbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import static java.lang.Math.max;
import static java.lang.Math.min;


/*
 * Credits to : https://android.jlelse.eu/scroll-your-bottom-navigation-view-away-with-10-lines-of-code-346f1ed40e9e
 * More info: https://developer.android.com/reference/com/google/android/material/behavior/HideBottomViewOnScrollBehavior.html
 * */
public class BottomNavigationBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    /*
    * This is for JW Player Settings layout
    * The parent view is using NestedScroll to utilize the Bottom Navigation hiding behavior
    * */
    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        child.setTranslationY(max(0f, min(child.getHeight(), child.getTranslationY() + dy)));
    }

}

