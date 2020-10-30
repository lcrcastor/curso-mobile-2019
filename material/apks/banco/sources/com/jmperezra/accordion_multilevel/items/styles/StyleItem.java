package com.jmperezra.accordion_multilevel.items.styles;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public interface StyleItem {
    int getResourceArrowId();

    int getResourceColor(Context context);

    Typeface getTypeface(AssetManager assetManager);
}
