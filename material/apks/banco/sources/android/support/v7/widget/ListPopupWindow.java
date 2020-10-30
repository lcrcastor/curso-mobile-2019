package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ShowableListMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.lang.reflect.Method;

public class ListPopupWindow implements ShowableListMenu {
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    public static final int WRAP_CONTENT = -2;
    private static Method a;
    private static Method b;
    private static Method h;
    private Drawable A;
    private OnItemClickListener B;
    private OnItemSelectedListener C;
    private final PopupTouchInterceptor D;
    private final PopupScrollListener E;
    private final ListSelectorHider F;
    private Runnable G;
    private final Rect H;
    private Rect I;
    private boolean J;
    DropDownListView c;
    int d;
    final ResizePopupRunnable e;
    final Handler f;
    PopupWindow g;
    private Context i;
    private ListAdapter j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private boolean p;
    private boolean q;
    private boolean r;
    private boolean s;
    private int t;
    private boolean u;
    private boolean v;
    private View w;
    private int x;
    private DataSetObserver y;
    private View z;

    class ListSelectorHider implements Runnable {
        ListSelectorHider() {
        }

        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    class PopupDataSetObserver extends DataSetObserver {
        PopupDataSetObserver() {
        }

        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    class PopupScrollListener implements OnScrollListener {
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        PopupScrollListener() {
        }

        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.g.getContentView() != null) {
                ListPopupWindow.this.f.removeCallbacks(ListPopupWindow.this.e);
                ListPopupWindow.this.e.run();
            }
        }
    }

    class PopupTouchInterceptor implements OnTouchListener {
        PopupTouchInterceptor() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && ListPopupWindow.this.g != null && ListPopupWindow.this.g.isShowing() && x >= 0 && x < ListPopupWindow.this.g.getWidth() && y >= 0 && y < ListPopupWindow.this.g.getHeight()) {
                ListPopupWindow.this.f.postDelayed(ListPopupWindow.this.e, 250);
            } else if (action == 1) {
                ListPopupWindow.this.f.removeCallbacks(ListPopupWindow.this.e);
            }
            return false;
        }
    }

    class ResizePopupRunnable implements Runnable {
        ResizePopupRunnable() {
        }

        public void run() {
            if (ListPopupWindow.this.c != null && ViewCompat.isAttachedToWindow(ListPopupWindow.this.c) && ListPopupWindow.this.c.getCount() > ListPopupWindow.this.c.getChildCount() && ListPopupWindow.this.c.getChildCount() <= ListPopupWindow.this.d) {
                ListPopupWindow.this.g.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    private static boolean a(int i2) {
        return i2 == 66 || i2 == 23;
    }

    static {
        try {
            a = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", new Class[]{Boolean.TYPE});
        } catch (NoSuchMethodException unused) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
            b = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", new Class[]{View.class, Integer.TYPE, Boolean.TYPE});
        } catch (NoSuchMethodException unused2) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
            h = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", new Class[]{Rect.class});
        } catch (NoSuchMethodException unused3) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public ListPopupWindow(@NonNull Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2) {
        this(context, attributeSet, i2, 0);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2, @StyleRes int i3) {
        this.k = -2;
        this.l = -2;
        this.o = 1002;
        this.q = true;
        this.t = 0;
        this.u = false;
        this.v = false;
        this.d = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        this.x = 0;
        this.e = new ResizePopupRunnable();
        this.D = new PopupTouchInterceptor();
        this.E = new PopupScrollListener();
        this.F = new ListSelectorHider();
        this.H = new Rect();
        this.i = context;
        this.f = new Handler(context.getMainLooper());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, i2, i3);
        this.m = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.n = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.n != 0) {
            this.p = true;
        }
        obtainStyledAttributes.recycle();
        this.g = new AppCompatPopupWindow(context, attributeSet, i2, i3);
        this.g.setInputMethodMode(1);
    }

    public void setAdapter(@Nullable ListAdapter listAdapter) {
        if (this.y == null) {
            this.y = new PopupDataSetObserver();
        } else if (this.j != null) {
            this.j.unregisterDataSetObserver(this.y);
        }
        this.j = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.y);
        }
        if (this.c != null) {
            this.c.setAdapter(this.j);
        }
    }

    public void setPromptPosition(int i2) {
        this.x = i2;
    }

    public int getPromptPosition() {
        return this.x;
    }

    public void setModal(boolean z2) {
        this.J = z2;
        this.g.setFocusable(z2);
    }

    public boolean isModal() {
        return this.J;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setForceIgnoreOutsideTouch(boolean z2) {
        this.v = z2;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setDropDownAlwaysVisible(boolean z2) {
        this.u = z2;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isDropDownAlwaysVisible() {
        return this.u;
    }

    public void setSoftInputMode(int i2) {
        this.g.setSoftInputMode(i2);
    }

    public int getSoftInputMode() {
        return this.g.getSoftInputMode();
    }

    public void setListSelector(Drawable drawable) {
        this.A = drawable;
    }

    @Nullable
    public Drawable getBackground() {
        return this.g.getBackground();
    }

    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        this.g.setBackgroundDrawable(drawable);
    }

    public void setAnimationStyle(@StyleRes int i2) {
        this.g.setAnimationStyle(i2);
    }

    @StyleRes
    public int getAnimationStyle() {
        return this.g.getAnimationStyle();
    }

    @Nullable
    public View getAnchorView() {
        return this.z;
    }

    public void setAnchorView(@Nullable View view) {
        this.z = view;
    }

    public int getHorizontalOffset() {
        return this.m;
    }

    public void setHorizontalOffset(int i2) {
        this.m = i2;
    }

    public int getVerticalOffset() {
        if (!this.p) {
            return 0;
        }
        return this.n;
    }

    public void setVerticalOffset(int i2) {
        this.n = i2;
        this.p = true;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setEpicenterBounds(Rect rect) {
        this.I = rect;
    }

    public void setDropDownGravity(int i2) {
        this.t = i2;
    }

    public int getWidth() {
        return this.l;
    }

    public void setWidth(int i2) {
        this.l = i2;
    }

    public void setContentWidth(int i2) {
        Drawable background = this.g.getBackground();
        if (background != null) {
            background.getPadding(this.H);
            this.l = this.H.left + this.H.right + i2;
            return;
        }
        setWidth(i2);
    }

    public int getHeight() {
        return this.k;
    }

    public void setHeight(int i2) {
        if (i2 >= 0 || -2 == i2 || -1 == i2) {
            this.k = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
    }

    public void setWindowLayoutType(int i2) {
        this.o = i2;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        this.B = onItemClickListener;
    }

    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener onItemSelectedListener) {
        this.C = onItemSelectedListener;
    }

    public void setPromptView(@Nullable View view) {
        boolean isShowing = isShowing();
        if (isShowing) {
            a();
        }
        this.w = view;
        if (isShowing) {
            show();
        }
    }

    public void postShow() {
        this.f.post(this.G);
    }

    public void show() {
        int i2;
        int i3;
        int b2 = b();
        boolean isInputMethodNotNeeded = isInputMethodNotNeeded();
        PopupWindowCompat.setWindowLayoutType(this.g, this.o);
        boolean z2 = true;
        if (!this.g.isShowing()) {
            if (this.l == -1) {
                i2 = -1;
            } else if (this.l == -2) {
                i2 = getAnchorView().getWidth();
            } else {
                i2 = this.l;
            }
            if (this.k == -1) {
                b2 = -1;
            } else if (this.k != -2) {
                b2 = this.k;
            }
            this.g.setWidth(i2);
            this.g.setHeight(b2);
            a(true);
            this.g.setOutsideTouchable(!this.v && !this.u);
            this.g.setTouchInterceptor(this.D);
            if (this.s) {
                PopupWindowCompat.setOverlapAnchor(this.g, this.r);
            }
            if (h != null) {
                try {
                    h.invoke(this.g, new Object[]{this.I});
                } catch (Exception e2) {
                    Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e2);
                }
            }
            PopupWindowCompat.showAsDropDown(this.g, getAnchorView(), this.m, this.n, this.t);
            this.c.setSelection(-1);
            if (!this.J || this.c.isInTouchMode()) {
                clearListSelection();
            }
            if (!this.J) {
                this.f.post(this.F);
            }
        } else if (ViewCompat.isAttachedToWindow(getAnchorView())) {
            if (this.l == -1) {
                i3 = -1;
            } else if (this.l == -2) {
                i3 = getAnchorView().getWidth();
            } else {
                i3 = this.l;
            }
            if (this.k == -1) {
                if (!isInputMethodNotNeeded) {
                    b2 = -1;
                }
                if (isInputMethodNotNeeded) {
                    this.g.setWidth(this.l == -1 ? -1 : 0);
                    this.g.setHeight(0);
                } else {
                    this.g.setWidth(this.l == -1 ? -1 : 0);
                    this.g.setHeight(-1);
                }
            } else if (this.k != -2) {
                b2 = this.k;
            }
            PopupWindow popupWindow = this.g;
            if (this.v || this.u) {
                z2 = false;
            }
            popupWindow.setOutsideTouchable(z2);
            this.g.update(getAnchorView(), this.m, this.n, i3 < 0 ? -1 : i3, b2 < 0 ? -1 : b2);
        }
    }

    public void dismiss() {
        this.g.dismiss();
        a();
        this.g.setContentView(null);
        this.c = null;
        this.f.removeCallbacks(this.e);
    }

    public void setOnDismissListener(@Nullable OnDismissListener onDismissListener) {
        this.g.setOnDismissListener(onDismissListener);
    }

    private void a() {
        if (this.w != null) {
            ViewParent parent = this.w.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.w);
            }
        }
    }

    public void setInputMethodMode(int i2) {
        this.g.setInputMethodMode(i2);
    }

    public int getInputMethodMode() {
        return this.g.getInputMethodMode();
    }

    public void setSelection(int i2) {
        DropDownListView dropDownListView = this.c;
        if (isShowing() && dropDownListView != null) {
            dropDownListView.setListSelectionHidden(false);
            dropDownListView.setSelection(i2);
            if (dropDownListView.getChoiceMode() != 0) {
                dropDownListView.setItemChecked(i2, true);
            }
        }
    }

    public void clearListSelection() {
        DropDownListView dropDownListView = this.c;
        if (dropDownListView != null) {
            dropDownListView.setListSelectionHidden(true);
            dropDownListView.requestLayout();
        }
    }

    public boolean isShowing() {
        return this.g.isShowing();
    }

    public boolean isInputMethodNotNeeded() {
        return this.g.getInputMethodMode() == 2;
    }

    public boolean performItemClick(int i2) {
        if (!isShowing()) {
            return false;
        }
        if (this.B != null) {
            DropDownListView dropDownListView = this.c;
            int i3 = i2;
            this.B.onItemClick(dropDownListView, dropDownListView.getChildAt(i2 - dropDownListView.getFirstVisiblePosition()), i3, dropDownListView.getAdapter().getItemId(i2));
        }
        return true;
    }

    @Nullable
    public Object getSelectedItem() {
        if (!isShowing()) {
            return null;
        }
        return this.c.getSelectedItem();
    }

    public int getSelectedItemPosition() {
        if (!isShowing()) {
            return -1;
        }
        return this.c.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        if (!isShowing()) {
            return Long.MIN_VALUE;
        }
        return this.c.getSelectedItemId();
    }

    @Nullable
    public View getSelectedView() {
        if (!isShowing()) {
            return null;
        }
        return this.c.getSelectedView();
    }

    @Nullable
    public ListView getListView() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public DropDownListView a(Context context, boolean z2) {
        return new DropDownListView(context, z2);
    }

    public boolean onKeyDown(int i2, @NonNull KeyEvent keyEvent) {
        int i3;
        int i4;
        if (isShowing() && i2 != 62 && (this.c.getSelectedItemPosition() >= 0 || !a(i2))) {
            int selectedItemPosition = this.c.getSelectedItemPosition();
            boolean z2 = !this.g.isAboveAnchor();
            ListAdapter listAdapter = this.j;
            int i5 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
            if (listAdapter != null) {
                boolean areAllItemsEnabled = listAdapter.areAllItemsEnabled();
                if (areAllItemsEnabled) {
                    i4 = 0;
                } else {
                    i4 = this.c.lookForSelectablePosition(0, true);
                }
                if (areAllItemsEnabled) {
                    i3 = listAdapter.getCount() - 1;
                } else {
                    i3 = this.c.lookForSelectablePosition(listAdapter.getCount() - 1, false);
                }
                i5 = i4;
            } else {
                i3 = Integer.MIN_VALUE;
            }
            if ((!z2 || i2 != 19 || selectedItemPosition > i5) && (z2 || i2 != 20 || selectedItemPosition < i3)) {
                this.c.setListSelectionHidden(false);
                if (this.c.onKeyDown(i2, keyEvent)) {
                    this.g.setInputMethodMode(2);
                    this.c.requestFocusFromTouch();
                    show();
                    if (!(i2 == 23 || i2 == 66)) {
                        switch (i2) {
                            case 19:
                            case 20:
                                break;
                        }
                    }
                    return true;
                } else if (!z2 || i2 != 20) {
                    if (!z2 && i2 == 19 && selectedItemPosition == i5) {
                        return true;
                    }
                    return false;
                } else if (selectedItemPosition == i3) {
                    return true;
                }
            } else {
                clearListSelection();
                this.g.setInputMethodMode(1);
                show();
                return true;
            }
        }
        return false;
    }

    public boolean onKeyUp(int i2, @NonNull KeyEvent keyEvent) {
        if (!isShowing() || this.c.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean onKeyUp = this.c.onKeyUp(i2, keyEvent);
        if (onKeyUp && a(i2)) {
            dismiss();
        }
        return onKeyUp;
    }

    public boolean onKeyPreIme(int i2, @NonNull KeyEvent keyEvent) {
        if (i2 == 4 && isShowing()) {
            View view = this.z;
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                DispatcherState keyDispatcherState = view.getKeyDispatcherState();
                if (keyDispatcherState != null) {
                    keyDispatcherState.startTracking(keyEvent, this);
                }
                return true;
            } else if (keyEvent.getAction() == 1) {
                DispatcherState keyDispatcherState2 = view.getKeyDispatcherState();
                if (keyDispatcherState2 != null) {
                    keyDispatcherState2.handleUpEvent(keyEvent);
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    dismiss();
                    return true;
                }
            }
        }
        return false;
    }

    public OnTouchListener createDragToOpenListener(View view) {
        return new ForwardingListener(view) {
            /* renamed from: b */
            public ListPopupWindow getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    private int b() {
        int i2;
        int i3;
        int makeMeasureSpec;
        View view;
        int i4;
        int i5;
        boolean z2 = true;
        if (this.c == null) {
            Context context = this.i;
            this.G = new Runnable() {
                public void run() {
                    View anchorView = ListPopupWindow.this.getAnchorView();
                    if (anchorView != null && anchorView.getWindowToken() != null) {
                        ListPopupWindow.this.show();
                    }
                }
            };
            this.c = a(context, !this.J);
            if (this.A != null) {
                this.c.setSelector(this.A);
            }
            this.c.setAdapter(this.j);
            this.c.setOnItemClickListener(this.B);
            this.c.setFocusable(true);
            this.c.setFocusableInTouchMode(true);
            this.c.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (i != -1) {
                        DropDownListView dropDownListView = ListPopupWindow.this.c;
                        if (dropDownListView != null) {
                            dropDownListView.setListSelectionHidden(false);
                        }
                    }
                }
            });
            this.c.setOnScrollListener(this.E);
            if (this.C != null) {
                this.c.setOnItemSelectedListener(this.C);
            }
            View view2 = this.c;
            View view3 = this.w;
            if (view3 != null) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                LayoutParams layoutParams = new LayoutParams(-1, 0, 1.0f);
                switch (this.x) {
                    case 0:
                        linearLayout.addView(view3);
                        linearLayout.addView(view2, layoutParams);
                        break;
                    case 1:
                        linearLayout.addView(view2, layoutParams);
                        linearLayout.addView(view3);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid hint position ");
                        sb.append(this.x);
                        Log.e("ListPopupWindow", sb.toString());
                        break;
                }
                if (this.l >= 0) {
                    i5 = this.l;
                    i4 = Integer.MIN_VALUE;
                } else {
                    i5 = 0;
                    i4 = 0;
                }
                view3.measure(MeasureSpec.makeMeasureSpec(i5, i4), 0);
                LayoutParams layoutParams2 = (LayoutParams) view3.getLayoutParams();
                i2 = view3.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                view = linearLayout;
            } else {
                i2 = 0;
                view = view2;
            }
            this.g.setContentView(view);
        } else {
            ViewGroup viewGroup = (ViewGroup) this.g.getContentView();
            View view4 = this.w;
            if (view4 != null) {
                LayoutParams layoutParams3 = (LayoutParams) view4.getLayoutParams();
                i2 = view4.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
            } else {
                i2 = 0;
            }
        }
        Drawable background = this.g.getBackground();
        if (background != null) {
            background.getPadding(this.H);
            i3 = this.H.top + this.H.bottom;
            if (!this.p) {
                this.n = -this.H.top;
            }
        } else {
            this.H.setEmpty();
            i3 = 0;
        }
        if (this.g.getInputMethodMode() != 2) {
            z2 = false;
        }
        int a2 = a(getAnchorView(), this.n, z2);
        if (this.u || this.k == -1) {
            return a2 + i3;
        }
        switch (this.l) {
            case -2:
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.i.getResources().getDisplayMetrics().widthPixels - (this.H.left + this.H.right), Integer.MIN_VALUE);
                break;
            case -1:
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.i.getResources().getDisplayMetrics().widthPixels - (this.H.left + this.H.right), 1073741824);
                break;
            default:
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(this.l, 1073741824);
                break;
        }
        int measureHeightOfChildrenCompat = this.c.measureHeightOfChildrenCompat(makeMeasureSpec, 0, -1, a2 - i2, -1);
        if (measureHeightOfChildrenCompat > 0) {
            i2 += i3 + this.c.getPaddingTop() + this.c.getPaddingBottom();
        }
        return measureHeightOfChildrenCompat + i2;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setOverlapAnchor(boolean z2) {
        this.s = true;
        this.r = z2;
    }

    private void a(boolean z2) {
        if (a != null) {
            try {
                a.invoke(this.g, new Object[]{Boolean.valueOf(z2)});
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
    }

    private int a(View view, int i2, boolean z2) {
        if (b != null) {
            try {
                return ((Integer) b.invoke(this.g, new Object[]{view, Integer.valueOf(i2), Boolean.valueOf(z2)})).intValue();
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.g.getMaxAvailableHeight(view, i2);
    }
}
