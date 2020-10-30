package ar.com.santander.rio.mbanking.app.ui.forms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter.ButtonMarginViewHolder;
import ar.com.santander.rio.mbanking.app.ui.utils.VerticalDividerItemDecoration;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class FormRecyclerView extends RecyclerView implements IFormViewGroup {
    private int J = 0;

    public FormRecyclerView(Context context) {
        super(context);
    }

    public FormRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FormRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public Canvas getComprobante() {
        Adapter adapter = getAdapter();
        if (adapter == null) {
            return null;
        }
        Paint paint = new Paint();
        Bitmap dividerBitmap = ((VerticalDividerItemDecoration) getItemDecorationAt(0)).getDividerBitmap(getMeasuredWidth(), paint);
        Boolean valueOf = Boolean.valueOf(dividerBitmap != null);
        int i = valueOf.booleanValue() ? 2 : 0;
        int itemCount = adapter.getItemCount();
        LruCache lruCache = new LruCache(((int) (Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) / 2);
        int i2 = 0;
        for (int i3 = 0; i3 < itemCount; i3++) {
            ViewHolder createViewHolder = adapter.createViewHolder(this, adapter.getItemViewType(i3));
            if (!(createViewHolder instanceof ButtonMarginViewHolder)) {
                adapter.onBindViewHolder(createViewHolder, i3);
                createViewHolder.itemView.measure(MeasureSpec.makeMeasureSpec(getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
                createViewHolder.itemView.layout(0, 0, createViewHolder.itemView.getMeasuredWidth(), createViewHolder.itemView.getMeasuredHeight());
                createViewHolder.itemView.setDrawingCacheEnabled(true);
                createViewHolder.itemView.buildDrawingCache();
                Bitmap drawingCache = createViewHolder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    lruCache.put(String.valueOf(i3), drawingCache);
                }
                i2 += createViewHolder.itemView.getMeasuredHeight();
            }
        }
        Canvas canvas = new Canvas(Bitmap.createBitmap(getMeasuredWidth(), i2 + (getAdapter().getItemCount() * i), Config.ARGB_8888));
        canvas.drawColor(-1);
        for (int i4 = 0; i4 < itemCount; i4++) {
            Bitmap bitmap = (Bitmap) lruCache.get(String.valueOf(i4));
            canvas.drawBitmap(bitmap, BitmapDescriptorFactory.HUE_RED, (float) this.J, paint);
            this.J += bitmap.getHeight();
            if (valueOf.booleanValue()) {
                canvas.drawBitmap(dividerBitmap, BitmapDescriptorFactory.HUE_RED, (float) this.J, paint);
                this.J += i;
            }
            bitmap.recycle();
        }
        return canvas;
    }

    public int getTotalHeight() {
        Adapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }
        Paint paint = new Paint();
        Bitmap dividerBitmap = ((VerticalDividerItemDecoration) getItemDecorationAt(0)).getDividerBitmap(getMeasuredWidth(), paint);
        Boolean valueOf = Boolean.valueOf(dividerBitmap != null);
        int i = valueOf.booleanValue() ? 2 : 0;
        int itemCount = adapter.getItemCount();
        LruCache lruCache = new LruCache(((int) (Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) / 2);
        int i2 = 0;
        for (int i3 = 0; i3 < itemCount; i3++) {
            ViewHolder createViewHolder = adapter.createViewHolder(this, adapter.getItemViewType(i3));
            if (!(createViewHolder instanceof ButtonMarginViewHolder)) {
                adapter.onBindViewHolder(createViewHolder, i3);
                createViewHolder.itemView.measure(MeasureSpec.makeMeasureSpec(getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
                createViewHolder.itemView.layout(0, 0, createViewHolder.itemView.getMeasuredWidth(), createViewHolder.itemView.getMeasuredHeight());
                createViewHolder.itemView.setDrawingCacheEnabled(true);
                createViewHolder.itemView.buildDrawingCache();
                Bitmap drawingCache = createViewHolder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    lruCache.put(String.valueOf(i3), drawingCache);
                }
                i2 += createViewHolder.itemView.getMeasuredHeight();
            }
        }
        Canvas canvas = new Canvas(Bitmap.createBitmap(getMeasuredWidth(), i2 + (getAdapter().getItemCount() * i), Config.ARGB_8888));
        canvas.drawColor(-1);
        int i4 = 0;
        for (int i5 = 0; i5 < itemCount; i5++) {
            Bitmap bitmap = (Bitmap) lruCache.get(String.valueOf(i5));
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, BitmapDescriptorFactory.HUE_RED, (float) i4, paint);
                i4 += bitmap.getHeight();
                if (valueOf.booleanValue()) {
                    canvas.drawBitmap(dividerBitmap, BitmapDescriptorFactory.HUE_RED, (float) i4, paint);
                    i4 += i;
                }
                bitmap.recycle();
            }
        }
        return i4;
    }

    public void drawCanvas(Canvas canvas) {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            Paint paint = new Paint();
            Bitmap dividerBitmap = ((VerticalDividerItemDecoration) getItemDecorationAt(0)).getDividerBitmap(getMeasuredWidth(), paint);
            Boolean valueOf = Boolean.valueOf(dividerBitmap != null);
            int i = valueOf.booleanValue() ? 2 : 0;
            int itemCount = adapter.getItemCount();
            LruCache lruCache = new LruCache(((int) (Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) / 2);
            int i2 = 0;
            for (int i3 = 0; i3 < itemCount; i3++) {
                ViewHolder createViewHolder = adapter.createViewHolder(this, adapter.getItemViewType(i3));
                if (!(createViewHolder instanceof ButtonMarginViewHolder)) {
                    adapter.onBindViewHolder(createViewHolder, i3);
                    createViewHolder.itemView.measure(MeasureSpec.makeMeasureSpec(getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
                    createViewHolder.itemView.layout(0, 0, createViewHolder.itemView.getMeasuredWidth(), createViewHolder.itemView.getMeasuredHeight());
                    createViewHolder.itemView.setDrawingCacheEnabled(true);
                    createViewHolder.itemView.buildDrawingCache();
                    Bitmap drawingCache = createViewHolder.itemView.getDrawingCache();
                    if (drawingCache != null) {
                        lruCache.put(String.valueOf(i3), drawingCache);
                    }
                    i2 += createViewHolder.itemView.getMeasuredHeight();
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(getMeasuredWidth(), i2 + (getAdapter().getItemCount() * i), Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap);
            canvas2.drawColor(-1);
            int i4 = 0;
            for (int i5 = 0; i5 < itemCount; i5++) {
                Bitmap bitmap = (Bitmap) lruCache.get(String.valueOf(i5));
                if (bitmap != null) {
                    canvas2.drawBitmap(bitmap, BitmapDescriptorFactory.HUE_RED, (float) i4, paint);
                    i4 += bitmap.getHeight();
                    if (valueOf.booleanValue()) {
                        canvas2.drawBitmap(dividerBitmap, BitmapDescriptorFactory.HUE_RED, (float) i4, paint);
                        i4 += i;
                    }
                    bitmap.recycle();
                }
            }
            canvas.drawBitmap(createBitmap, new Matrix(), paint);
        }
    }
}
