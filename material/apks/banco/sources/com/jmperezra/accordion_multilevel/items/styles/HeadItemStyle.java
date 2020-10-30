package com.jmperezra.accordion_multilevel.items.styles;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.jmperezra.accordion_multilevel.R;

public class HeadItemStyle implements StyleItem {
    public int getResourceArrowId() {
        return R.drawable.ic_arrow_item_black;
    }

    public int getResourceColor(Context context) {
        return context.getResources().getColor(R.color.black);
    }

    public Typeface getTypeface(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/OpenSans-Bold.otf");
    }
}
