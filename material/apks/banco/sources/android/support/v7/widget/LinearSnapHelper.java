package android.support.v7.widget;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider;
import android.view.View;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class LinearSnapHelper extends SnapHelper {
    @Nullable
    private OrientationHelper b;
    @Nullable
    private OrientationHelper c;

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

    public int findTargetSnapPosition(LayoutManager layoutManager, int i, int i2) {
        int i3;
        int i4;
        if (!(layoutManager instanceof ScrollVectorProvider)) {
            return -1;
        }
        int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return -1;
        }
        View findSnapView = findSnapView(layoutManager);
        if (findSnapView == null) {
            return -1;
        }
        int position = layoutManager.getPosition(findSnapView);
        if (position == -1) {
            return -1;
        }
        int i5 = itemCount - 1;
        PointF computeScrollVectorForPosition = ((ScrollVectorProvider) layoutManager).computeScrollVectorForPosition(i5);
        if (computeScrollVectorForPosition == null) {
            return -1;
        }
        if (layoutManager.canScrollHorizontally()) {
            i3 = a(layoutManager, b(layoutManager), i, 0);
            if (computeScrollVectorForPosition.x < BitmapDescriptorFactory.HUE_RED) {
                i3 = -i3;
            }
        } else {
            i3 = 0;
        }
        if (layoutManager.canScrollVertically()) {
            i4 = a(layoutManager, a(layoutManager), 0, i2);
            if (computeScrollVectorForPosition.y < BitmapDescriptorFactory.HUE_RED) {
                i4 = -i4;
            }
        } else {
            i4 = 0;
        }
        if (layoutManager.canScrollVertically()) {
            i3 = i4;
        }
        if (i3 == 0) {
            return -1;
        }
        int i6 = position + i3;
        if (i6 < 0) {
            i6 = 0;
        }
        if (i6 >= itemCount) {
            i6 = i5;
        }
        return i6;
    }

    public View findSnapView(LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return a(layoutManager, a(layoutManager));
        }
        if (layoutManager.canScrollHorizontally()) {
            return a(layoutManager, b(layoutManager));
        }
        return null;
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

    private int a(LayoutManager layoutManager, OrientationHelper orientationHelper, int i, int i2) {
        int[] calculateScrollDistance = calculateScrollDistance(i, i2);
        float b2 = b(layoutManager, orientationHelper);
        if (b2 <= BitmapDescriptorFactory.HUE_RED) {
            return 0;
        }
        return Math.round(((float) (Math.abs(calculateScrollDistance[0]) > Math.abs(calculateScrollDistance[1]) ? calculateScrollDistance[0] : calculateScrollDistance[1])) / b2);
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

    private float b(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return 1.0f;
        }
        View view = null;
        int i = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        View view2 = null;
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = layoutManager.getChildAt(i3);
            int position = layoutManager.getPosition(childAt);
            if (position != -1) {
                if (position < i) {
                    view = childAt;
                    i = position;
                }
                if (position > i2) {
                    view2 = childAt;
                    i2 = position;
                }
            }
        }
        if (view == null || view2 == null) {
            return 1.0f;
        }
        int max = Math.max(orientationHelper.getDecoratedEnd(view), orientationHelper.getDecoratedEnd(view2)) - Math.min(orientationHelper.getDecoratedStart(view), orientationHelper.getDecoratedStart(view2));
        if (max == 0) {
            return 1.0f;
        }
        return (((float) max) * 1.0f) / ((float) ((i2 - i) + 1));
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
