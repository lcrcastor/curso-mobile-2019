package com.zurich.lockview.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

public class ResourceUtils {
    private ResourceUtils() {
        throw new AssertionError("You can not instantiate this class. Use its static utility methods instead");
    }

    public static int getColor(@NonNull Context context, @ColorRes int i) {
        return ContextCompat.getColor(context, i);
    }

    public static String getString(@NonNull Context context, @StringRes int i) {
        return context.getString(i);
    }

    public static float getDimensionInPx(@NonNull Context context, @DimenRes int i) {
        return context.getResources().getDimension(i);
    }
}
