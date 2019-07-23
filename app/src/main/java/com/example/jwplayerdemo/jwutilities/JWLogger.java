package com.example.jwplayerdemo.jwutilities;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JWLogger {

    private static final String TAG = "HYUNJOO";
    private static StringBuilder outputStringBuilder;
    private static DateFormat dateFormat = new SimpleDateFormat("KK:mm:ss.SSS", Locale.US);

    /*
     * Return to String format
     * */
    public static String generateOutput(String output) {

        if (outputStringBuilder == null) outputStringBuilder = new StringBuilder();

        outputStringBuilder.append(dateFormat.format(new Date())).append(" ").append(output).append("\r\n");

        return outputStringBuilder.toString();
    }

    /*
     * Reset the StringBuilder
     * */
    public static void reset() {
        outputStringBuilder = null;
    }

    /*
     * Display this log
     * */
    public static void log(String msg) {
        Log.i(TAG, msg);

    }
}
