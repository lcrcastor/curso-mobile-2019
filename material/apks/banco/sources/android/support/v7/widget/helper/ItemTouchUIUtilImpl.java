package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class ItemTouchUIUtilImpl {

    static class Api21Impl extends BaseImpl {
        Api21Impl() {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (z && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
                Float valueOf = Float.valueOf(ViewCompat.getElevation(view));
                ViewCompat.setElevation(view, a(recyclerView, view) + 1.0f);
                view.setTag(R.id.item_touch_helper_previous_elevation, valueOf);
            }
            super.onDraw(canvas, recyclerView, view, f, f2, i, z);
        }

        private float a(RecyclerView recyclerView, View view) {
            int childCount = recyclerView.getChildCount();
            float f = BitmapDescriptorFactory.HUE_RED;
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                if (childAt != view) {
                    float elevation = ViewCompat.getElevation(childAt);
                    if (elevation > f) {
                        f = elevation;
                    }
                }
            }
            return f;
        }

        public void clearView(View view) {
            Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
            if (tag != null && (tag instanceof Float)) {
                ViewCompat.setElevation(view, ((Float) tag).floatValue());
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
            super.clearView(view);
        }
    }

    static class BaseImpl implements ItemTouchUIUtil {
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
        }

        public void onSelected(View view) {
        }

        BaseImpl() {
        }

        public void clearView(View view) {
            view.setTranslationX(BitmapDescriptorFactory.HUE_RED);
            view.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            view.setTranslationX(f);
            view.setTranslationY(f2);
        }
    }

    ItemTouchUIUtilImpl() {
    }
}
