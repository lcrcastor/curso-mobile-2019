package android.support.v4.view;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.compat.R;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public final class ViewGroupCompat {
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;
    static final ViewGroupCompatBaseImpl a;

    @RequiresApi(18)
    static class ViewGroupCompatApi18Impl extends ViewGroupCompatBaseImpl {
        ViewGroupCompatApi18Impl() {
        }

        public int a(ViewGroup viewGroup) {
            return viewGroup.getLayoutMode();
        }

        public void a(ViewGroup viewGroup, int i) {
            viewGroup.setLayoutMode(i);
        }
    }

    @RequiresApi(21)
    static class ViewGroupCompatApi21Impl extends ViewGroupCompatApi18Impl {
        ViewGroupCompatApi21Impl() {
        }

        public void a(ViewGroup viewGroup, boolean z) {
            viewGroup.setTransitionGroup(z);
        }

        public boolean b(ViewGroup viewGroup) {
            return viewGroup.isTransitionGroup();
        }

        public int c(ViewGroup viewGroup) {
            return viewGroup.getNestedScrollAxes();
        }
    }

    static class ViewGroupCompatBaseImpl {
        public int a(ViewGroup viewGroup) {
            return 0;
        }

        public void a(ViewGroup viewGroup, int i) {
        }

        ViewGroupCompatBaseImpl() {
        }

        public void a(ViewGroup viewGroup, boolean z) {
            viewGroup.setTag(R.id.tag_transition_group, Boolean.valueOf(z));
        }

        public boolean b(ViewGroup viewGroup) {
            Boolean bool = (Boolean) viewGroup.getTag(R.id.tag_transition_group);
            return ((bool == null || !bool.booleanValue()) && viewGroup.getBackground() == null && ViewCompat.getTransitionName(viewGroup) == null) ? false : true;
        }

        public int c(ViewGroup viewGroup) {
            if (viewGroup instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) viewGroup).getNestedScrollAxes();
            }
            return 0;
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            a = new ViewGroupCompatApi21Impl();
        } else if (VERSION.SDK_INT >= 18) {
            a = new ViewGroupCompatApi18Impl();
        } else {
            a = new ViewGroupCompatBaseImpl();
        }
    }

    private ViewGroupCompat() {
    }

    @Deprecated
    public static boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return viewGroup.onRequestSendAccessibilityEvent(view, accessibilityEvent);
    }

    @Deprecated
    public static void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z) {
        viewGroup.setMotionEventSplittingEnabled(z);
    }

    public static int getLayoutMode(ViewGroup viewGroup) {
        return a.a(viewGroup);
    }

    public static void setLayoutMode(ViewGroup viewGroup, int i) {
        a.a(viewGroup, i);
    }

    public static void setTransitionGroup(ViewGroup viewGroup, boolean z) {
        a.a(viewGroup, z);
    }

    public static boolean isTransitionGroup(ViewGroup viewGroup) {
        return a.b(viewGroup);
    }

    public static int getNestedScrollAxes(@NonNull ViewGroup viewGroup) {
        return a.c(viewGroup);
    }
}
