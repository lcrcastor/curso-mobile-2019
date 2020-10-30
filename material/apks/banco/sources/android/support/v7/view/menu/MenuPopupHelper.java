package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuPresenter.Callback;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;

@RestrictTo({Scope.LIBRARY_GROUP})
public class MenuPopupHelper {
    private final Context a;
    private final MenuBuilder b;
    private final boolean c;
    private final int d;
    private final int e;
    private View f;
    private int g;
    private boolean h;
    private Callback i;
    private MenuPopup j;
    private OnDismissListener k;
    private final OnDismissListener l;

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view) {
        this(context, menuBuilder, view, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view, boolean z, @AttrRes int i2) {
        this(context, menuBuilder, view, z, i2, 0);
    }

    public MenuPopupHelper(@NonNull Context context, @NonNull MenuBuilder menuBuilder, @NonNull View view, boolean z, @AttrRes int i2, @StyleRes int i3) {
        this.g = GravityCompat.START;
        this.l = new OnDismissListener() {
            public void onDismiss() {
                MenuPopupHelper.this.onDismiss();
            }
        };
        this.a = context;
        this.b = menuBuilder;
        this.f = view;
        this.c = z;
        this.d = i2;
        this.e = i3;
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.k = onDismissListener;
    }

    public void setAnchorView(@NonNull View view) {
        this.f = view;
    }

    public void setForceShowIcon(boolean z) {
        this.h = z;
        if (this.j != null) {
            this.j.a(z);
        }
    }

    public void setGravity(int i2) {
        this.g = i2;
    }

    public int getGravity() {
        return this.g;
    }

    public void show() {
        if (!tryShow()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public void show(int i2, int i3) {
        if (!tryShow(i2, i3)) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    @NonNull
    public MenuPopup getPopup() {
        if (this.j == null) {
            this.j = a();
        }
        return this.j;
    }

    public boolean tryShow() {
        if (isShowing()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int i2, int i3) {
        if (isShowing()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(i2, i3, true, true);
        return true;
    }

    /* JADX WARNING: type inference failed for: r0v7, types: [android.support.v7.view.menu.MenuPopup] */
    /* JADX WARNING: type inference failed for: r7v0, types: [android.support.v7.view.menu.StandardMenuPopup] */
    /* JADX WARNING: type inference failed for: r1v12, types: [android.support.v7.view.menu.CascadingMenuPopup] */
    /* JADX WARNING: type inference failed for: r7v1, types: [android.support.v7.view.menu.StandardMenuPopup] */
    /* JADX WARNING: type inference failed for: r1v13, types: [android.support.v7.view.menu.CascadingMenuPopup] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r7v1, types: [android.support.v7.view.menu.StandardMenuPopup]
      assigns: [android.support.v7.view.menu.StandardMenuPopup, android.support.v7.view.menu.CascadingMenuPopup]
      uses: [android.support.v7.view.menu.StandardMenuPopup, android.support.v7.view.menu.MenuPopup, android.support.v7.view.menu.CascadingMenuPopup]
      mth insns count: 49
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    @android.support.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.support.v7.view.menu.MenuPopup a() {
        /*
            r14 = this;
            android.content.Context r0 = r14.a
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.view.Display r0 = r0.getDefaultDisplay()
            android.graphics.Point r1 = new android.graphics.Point
            r1.<init>()
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 17
            if (r2 < r3) goto L_0x001d
            r0.getRealSize(r1)
            goto L_0x0020
        L_0x001d:
            r0.getSize(r1)
        L_0x0020:
            int r0 = r1.x
            int r1 = r1.y
            int r0 = java.lang.Math.min(r0, r1)
            android.content.Context r1 = r14.a
            android.content.res.Resources r1 = r1.getResources()
            int r2 = android.support.v7.appcompat.R.dimen.abc_cascading_menus_min_smallest_width
            int r1 = r1.getDimensionPixelSize(r2)
            if (r0 < r1) goto L_0x0038
            r0 = 1
            goto L_0x0039
        L_0x0038:
            r0 = 0
        L_0x0039:
            if (r0 == 0) goto L_0x004c
            android.support.v7.view.menu.CascadingMenuPopup r0 = new android.support.v7.view.menu.CascadingMenuPopup
            android.content.Context r2 = r14.a
            android.view.View r3 = r14.f
            int r4 = r14.d
            int r5 = r14.e
            boolean r6 = r14.c
            r1 = r0
            r1.<init>(r2, r3, r4, r5, r6)
            goto L_0x005e
        L_0x004c:
            android.support.v7.view.menu.StandardMenuPopup r0 = new android.support.v7.view.menu.StandardMenuPopup
            android.content.Context r8 = r14.a
            android.support.v7.view.menu.MenuBuilder r9 = r14.b
            android.view.View r10 = r14.f
            int r11 = r14.d
            int r12 = r14.e
            boolean r13 = r14.c
            r7 = r0
            r7.<init>(r8, r9, r10, r11, r12, r13)
        L_0x005e:
            android.support.v7.view.menu.MenuBuilder r1 = r14.b
            r0.a(r1)
            android.widget.PopupWindow$OnDismissListener r1 = r14.l
            r0.a(r1)
            android.view.View r1 = r14.f
            r0.a(r1)
            android.support.v7.view.menu.MenuPresenter$Callback r1 = r14.i
            r0.setCallback(r1)
            boolean r1 = r14.h
            r0.a(r1)
            int r1 = r14.g
            r0.a(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.view.menu.MenuPopupHelper.a():android.support.v7.view.menu.MenuPopup");
    }

    private void a(int i2, int i3, boolean z, boolean z2) {
        MenuPopup popup = getPopup();
        popup.b(z2);
        if (z) {
            if ((GravityCompat.getAbsoluteGravity(this.g, ViewCompat.getLayoutDirection(this.f)) & 7) == 5) {
                i2 += this.f.getWidth();
            }
            popup.b(i2);
            popup.c(i3);
            int i4 = (int) ((this.a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            popup.a(new Rect(i2 - i4, i3 - i4, i2 + i4, i3 + i4));
        }
        popup.show();
    }

    public void dismiss() {
        if (isShowing()) {
            this.j.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDismiss() {
        this.j = null;
        if (this.k != null) {
            this.k.onDismiss();
        }
    }

    public boolean isShowing() {
        return this.j != null && this.j.isShowing();
    }

    public void setPresenterCallback(@Nullable Callback callback) {
        this.i = callback;
        if (this.j != null) {
            this.j.setCallback(callback);
        }
    }

    public ListView getListView() {
        return getPopup().getListView();
    }
}
