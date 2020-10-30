package android.support.design.widget;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;

public class BottomSheetDialog extends AppCompatDialog {
    boolean a;
    private BottomSheetBehavior<FrameLayout> b;
    private boolean c;
    private boolean d;
    private BottomSheetCallback e;

    public BottomSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BottomSheetDialog(@NonNull Context context, @StyleRes int i) {
        super(context, a(context, i));
        this.a = true;
        this.c = true;
        this.e = new BottomSheetCallback() {
            public void onSlide(@NonNull View view, float f) {
            }

            public void onStateChanged(@NonNull View view, int i) {
                if (i == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }
        };
        supportRequestWindowFeature(1);
    }

    protected BottomSheetDialog(@NonNull Context context, boolean z, OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.a = true;
        this.c = true;
        this.e = new BottomSheetCallback() {
            public void onSlide(@NonNull View view, float f) {
            }

            public void onStateChanged(@NonNull View view, int i) {
                if (i == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }
        };
        supportRequestWindowFeature(1);
        this.a = z;
    }

    public void setContentView(@LayoutRes int i) {
        super.setContentView(a(i, null, null));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            if (VERSION.SDK_INT >= 21) {
                window.clearFlags(67108864);
                window.addFlags(Integer.MIN_VALUE);
            }
            window.setLayout(-1, -1);
        }
    }

    public void setContentView(View view) {
        super.setContentView(a(0, view, null));
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        super.setContentView(a(0, view, layoutParams));
    }

    public void setCancelable(boolean z) {
        super.setCancelable(z);
        if (this.a != z) {
            this.a = z;
            if (this.b != null) {
                this.b.setHideable(z);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (this.b != null) {
            this.b.setState(4);
        }
    }

    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.a) {
            this.a = true;
        }
        this.c = z;
        this.d = true;
    }

    private View a(int i, View view, LayoutParams layoutParams) {
        FrameLayout frameLayout = (FrameLayout) View.inflate(getContext(), R.layout.design_bottom_sheet_dialog, null);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) frameLayout.findViewById(R.id.coordinator);
        if (i != 0 && view == null) {
            view = getLayoutInflater().inflate(i, coordinatorLayout, false);
        }
        FrameLayout frameLayout2 = (FrameLayout) coordinatorLayout.findViewById(R.id.design_bottom_sheet);
        this.b = BottomSheetBehavior.from(frameLayout2);
        this.b.setBottomSheetCallback(this.e);
        this.b.setHideable(this.a);
        if (layoutParams == null) {
            frameLayout2.addView(view);
        } else {
            frameLayout2.addView(view, layoutParams);
        }
        coordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (BottomSheetDialog.this.a && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.a()) {
                    BottomSheetDialog.this.cancel();
                }
            }
        });
        ViewCompat.setAccessibilityDelegate(frameLayout2, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                if (BottomSheetDialog.this.a) {
                    accessibilityNodeInfoCompat.addAction(1048576);
                    accessibilityNodeInfoCompat.setDismissable(true);
                    return;
                }
                accessibilityNodeInfoCompat.setDismissable(false);
            }

            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 1048576 || !BottomSheetDialog.this.a) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                BottomSheetDialog.this.cancel();
                return true;
            }
        });
        frameLayout2.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return frameLayout;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        if (!this.d) {
            if (VERSION.SDK_INT < 11) {
                this.c = true;
            } else {
                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{16843611});
                this.c = obtainStyledAttributes.getBoolean(0, true);
                obtainStyledAttributes.recycle();
            }
            this.d = true;
        }
        return this.c;
    }

    private static int a(Context context, int i) {
        if (i != 0) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, typedValue, true)) {
            return typedValue.resourceId;
        }
        return R.style.Theme_Design_Light_BottomSheetDialog;
    }
}
