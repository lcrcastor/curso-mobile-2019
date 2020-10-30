package ar.com.santander.rio.mbanking.app.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class VerticalDividerItemDecoration extends ItemDecoration {
    private Drawable a;

    public VerticalDividerItemDecoration(Context context) {
        if (context != null) {
            this.a = context.getResources().getDrawable(R.drawable.item_divider_vertical);
        }
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int bottom = childAt.getBottom() + ((LayoutParams) childAt.getLayoutParams()).bottomMargin;
            this.a.setBounds(paddingLeft, bottom, width, this.a.getIntrinsicHeight() + bottom);
            this.a.draw(canvas);
        }
    }

    public Bitmap getDividerBitmap(int i, Paint paint) {
        Bitmap createBitmap = Bitmap.createBitmap(i, 2, Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) i, 2.0f), paint);
        return createBitmap;
    }
}
