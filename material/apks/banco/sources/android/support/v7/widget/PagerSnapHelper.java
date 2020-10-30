package android.support.v7.widget;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.SmoothScroller.Action;
import android.support.v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.support.v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.view.View;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class PagerSnapHelper extends SnapHelper {
    @Nullable
    private OrientationHelper b;
    @Nullable
    private OrientationHelper c;

    @Nullable
    public int[] calculateDistanceToFinalSnap(@NonNull LayoutManager layoutManager, @NonNull View view) {
        int[] iArr = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            iArr[0] = a(layoutManager, view, b(layoutManager));
        } else {
            iArr[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            iArr[1] = a(layoutManager, view, a(layoutManager));
        } else {
            iArr[1] = 0;
        }
        return iArr;
    }

    @Nullable
    public View findSnapView(LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return a(layoutManager, a(layoutManager));
        }
        if (layoutManager.canScrollHorizontally()) {
            return a(layoutManager, b(layoutManager));
        }
        return null;
    }

    public int findTargetSnapPosition(LayoutManager layoutManager, int i, int i2) {
        int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return -1;
        }
        View view = null;
        if (layoutManager.canScrollVertically()) {
            view = b(layoutManager, a(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            view = b(layoutManager, b(layoutManager));
        }
        if (view == null) {
            return -1;
        }
        int position = layoutManager.getPosition(view);
        if (position == -1) {
            return -1;
        }
        boolean z = false;
        boolean z2 = !layoutManager.canScrollHorizontally() ? i2 > 0 : i > 0;
        if (layoutManager instanceof ScrollVectorProvider) {
            PointF computeScrollVectorForPosition = ((ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(itemCount - 1);
            if (computeScrollVectorForPosition != null && (computeScrollVectorForPosition.x < BitmapDescriptorFactory.HUE_RED || computeScrollVectorForPosition.y < BitmapDescriptorFactory.HUE_RED)) {
                z = true;
            }
        }
        if (z) {
            if (z2) {
                position--;
            }
        } else if (z2) {
            position++;
        }
        return position;
    }

    /* access modifiers changed from: protected */
    public LinearSmoothScroller createSnapScroller(LayoutManager layoutManager) {
        if (!(layoutManager instanceof ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(this.a.getContext()) {
            /* access modifiers changed from: protected */
            public void onTargetFound(View view, State state, Action action) {
                int[] calculateDistanceToFinalSnap = PagerSnapHelper.this.calculateDistanceToFinalSnap(PagerSnapHelper.this.a.getLayoutManager(), view);
                int i = calculateDistanceToFinalSnap[0];
                int i2 = calculateDistanceToFinalSnap[1];
                int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(i2)));
                if (calculateTimeForDeceleration > 0) {
                    action.update(i, i2, calculateTimeForDeceleration, this.mDecelerateInterpolator);
                }
            }

            /* access modifiers changed from: protected */
            public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / ((float) displayMetrics.densityDpi);
            }

            /* access modifiers changed from: protected */
            public int calculateTimeForScrolling(int i) {
                return Math.min(100, super.calculateTimeForScrolling(i));
            }
        };
    }

    private int a(@NonNull LayoutManager layoutManager, @NonNull View view, OrientationHelper orientationHelper) {
        int i;
        int decoratedStart = orientationHelper.getDecoratedStart(view) + (orientationHelper.getDecoratedMeasurement(view) / 2);
        if (layoutManager.getClipToPadding()) {
            i = orientationHelper.getStartAfterPadding() + (orientationHelper.getTotalSpace() / 2);
        } else {
            i = orientationHelper.getEnd() / 2;
        }
        return decoratedStart - i;
    }

    @Nullable
    private View a(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int i;
        int childCount = layoutManager.getChildCount();
        View view = null;
        if (childCount == 0) {
            return null;
        }
        if (layoutManager.getClipToPadding()) {
            i = orientationHelper.getStartAfterPadding() + (orientationHelper.getTotalSpace() / 2);
        } else {
            i = orientationHelper.getEnd() / 2;
        }
        int i2 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = layoutManager.getChildAt(i3);
            int abs = Math.abs((orientationHelper.getDecoratedStart(childAt) + (orientationHelper.getDecoratedMeasurement(childAt) / 2)) - i);
            if (abs < i2) {
                view = childAt;
                i2 = abs;
            }
        }
        return view;
    }

    @Nullable
    private View b(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int childCount = layoutManager.getChildCount();
        View view = null;
        if (childCount == 0) {
            return null;
        }
        int i = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = layoutManager.getChildAt(i2);
            int decoratedStart = orientationHelper.getDecoratedStart(childAt);
            if (decoratedStart < i) {
                view = childAt;
                i = decoratedStart;
            }
        }
        return view;
    }

    @NonNull
    private OrientationHelper a(@NonNull LayoutManager layoutManager) {
        if (this.b == null || this.b.mLayoutManager != layoutManager) {
            this.b = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.b;
    }

    @NonNull
    private OrientationHelper b(@NonNull LayoutManager layoutManager) {
        if (this.c == null || this.c.mLayoutManager != layoutManager) {
            this.c = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.c;
    }
}
