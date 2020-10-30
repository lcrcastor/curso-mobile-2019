package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityWindowInfo;

public class AccessibilityWindowInfoCompat {
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    private Object a;

    private static String a(int i) {
        switch (i) {
            case 1:
                return "TYPE_APPLICATION";
            case 2:
                return "TYPE_INPUT_METHOD";
            case 3:
                return "TYPE_SYSTEM";
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            default:
                return "<UNKNOWN>";
        }
    }

    static AccessibilityWindowInfoCompat a(Object obj) {
        if (obj != null) {
            return new AccessibilityWindowInfoCompat(obj);
        }
        return null;
    }

    private AccessibilityWindowInfoCompat(Object obj) {
        this.a = obj;
    }

    public int getType() {
        if (VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).getType();
        }
        return -1;
    }

    public int getLayer() {
        if (VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).getLayer();
        }
        return -1;
    }

    public AccessibilityNodeInfoCompat getRoot() {
        if (VERSION.SDK_INT >= 21) {
            return AccessibilityNodeInfoCompat.a((Object) ((AccessibilityWindowInfo) this.a).getRoot());
        }
        return null;
    }

    public AccessibilityWindowInfoCompat getParent() {
        if (VERSION.SDK_INT >= 21) {
            return a((Object) ((AccessibilityWindowInfo) this.a).getParent());
        }
        return null;
    }

    public int getId() {
        if (VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).getId();
        }
        return -1;
    }

    public void getBoundsInScreen(Rect rect) {
        if (VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.a).getBoundsInScreen(rect);
        }
    }

    public boolean isActive() {
        if (VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).isActive();
        }
        return true;
    }

    public boolean isFocused() {
        if (VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).isFocused();
        }
        return true;
    }

    public boolean isAccessibilityFocused() {
        if (VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).isAccessibilityFocused();
        }
        return true;
    }

    public int getChildCount() {
        if (VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).getChildCount();
        }
        return 0;
    }

    public AccessibilityWindowInfoCompat getChild(int i) {
        if (VERSION.SDK_INT >= 21) {
            return a((Object) ((AccessibilityWindowInfo) this.a).getChild(i));
        }
        return null;
    }

    public CharSequence getTitle() {
        if (VERSION.SDK_INT >= 24) {
            return ((AccessibilityWindowInfo) this.a).getTitle();
        }
        return null;
    }

    public AccessibilityNodeInfoCompat getAnchor() {
        if (VERSION.SDK_INT >= 24) {
            return AccessibilityNodeInfoCompat.a((Object) ((AccessibilityWindowInfo) this.a).getAnchor());
        }
        return null;
    }

    public static AccessibilityWindowInfoCompat obtain() {
        if (VERSION.SDK_INT >= 21) {
            return a((Object) AccessibilityWindowInfo.obtain());
        }
        return null;
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat2 = null;
        if (VERSION.SDK_INT < 21) {
            return null;
        }
        if (accessibilityWindowInfoCompat != null) {
            accessibilityWindowInfoCompat2 = a((Object) AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) accessibilityWindowInfoCompat.a));
        }
        return accessibilityWindowInfoCompat2;
    }

    public void recycle() {
        if (VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.a).recycle();
        }
    }

    public int hashCode() {
        if (this.a == null) {
            return 0;
        }
        return this.a.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat) obj;
        if (this.a == null) {
            if (accessibilityWindowInfoCompat.a != null) {
                return false;
            }
        } else if (!this.a.equals(accessibilityWindowInfoCompat.a)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Rect rect = new Rect();
        getBoundsInScreen(rect);
        sb.append("AccessibilityWindowInfo[");
        sb.append("id=");
        sb.append(getId());
        sb.append(", type=");
        sb.append(a(getType()));
        sb.append(", layer=");
        sb.append(getLayer());
        sb.append(", bounds=");
        sb.append(rect);
        sb.append(", focused=");
        sb.append(isFocused());
        sb.append(", active=");
        sb.append(isActive());
        sb.append(", hasParent=");
        boolean z = false;
        sb.append(getParent() != null);
        sb.append(", hasChildren=");
        if (getChildCount() > 0) {
            z = true;
        }
        sb.append(z);
        sb.append(']');
        return sb.toString();
    }
}
