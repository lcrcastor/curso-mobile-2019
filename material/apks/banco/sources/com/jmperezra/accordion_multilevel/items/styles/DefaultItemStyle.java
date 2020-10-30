package com.jmperezra.accordion_multilevel.items.styles;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.jmperezra.accordion_multilevel.R;

public class DefaultItemStyle implements StyleItem {
    public int getResourceArrowId() {
        return R.drawable.ic_arrow_item_grey;
    }

    public int getResourceColor(Context context) {
        return context.getResources().getColor(R.color.grey_medium_light);
    }

    public Typeface getTypeface(AssetManager assetManager) {
        return Typeface.createFromAsset(assetManager, "fonts/OpenSans-Regular.otf");
    }
}
