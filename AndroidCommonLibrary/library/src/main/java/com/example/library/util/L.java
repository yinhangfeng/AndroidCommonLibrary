package com.example.library.util;

import android.util.Log;

/**
 * Created by yhf on 2015/5/25.
 */
public class L {

    private static boolean allowV = true;
    private static boolean allowD = true;
    private static boolean allowI = true;
    private static boolean allowW = true;
    private static boolean allowE = true;

    private static int sMinimumLoggingLevel = Log.VERBOSE;

    private L() {
    }

    /**
     * 设置最低的Log等级,大于此等级的可以Log
     * @param level {@link Log#VERBOSE}, {@link Log#DEBUG}, {@link Log#INFO}, {@link Log#WARN}, {@link Log#ERROR}
     */
    public static void setMinimumLoggingLevel(int level) {
        if(level < Log.VERBOSE) {
            level = Log.VERBOSE;
        }
        allowV = allowD = allowI = allowW = allowE = false;
        switch(level) {
        case Log.VERBOSE:
            allowV = true;
        case Log.DEBUG:
            allowD = true;
        case Log.INFO:
            allowI = true;
        case Log.WARN:
            allowW = true;
        case Log.ERROR:
            allowE = true;
        }
        sMinimumLoggingLevel = level;
    }

    public static void setAllLevelLoggable(boolean loggable) {
        allowV = allowD = allowI = allowW = allowE = loggable;
        sMinimumLoggingLevel = loggable ? Log.VERBOSE : Integer.MAX_VALUE;
    }

    public static boolean isLoggable(int level) {
        return level >= sMinimumLoggingLevel;
    }

    public static void d(String TAG, String msg) {
        Log.d(TAG, msg);
    }

    public static void d(String TAG, Throwable tr, String msg) {

    }
}
