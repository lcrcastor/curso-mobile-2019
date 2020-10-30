package com.facebook.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.android.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.LikeActionController;
import com.facebook.internal.LikeActionController.CreationCallback;
import com.facebook.internal.LikeBoxCountView;
import com.facebook.internal.LikeBoxCountView.LikeBoxCountViewCaretPosition;
import com.facebook.internal.LikeButton;
import com.facebook.internal.Utility;
import cz.msebera.android.httpclient.client.config.CookieSpecs;

public class LikeView extends FrameLayout {
    /* access modifiers changed from: private */
    public String a;
    private LinearLayout b;
    private LikeButton c;
    private LikeBoxCountView d;
    private TextView e;
    private LikeActionController f;
    /* access modifiers changed from: private */
    public OnErrorListener g;
    private BroadcastReceiver h;
    /* access modifiers changed from: private */
    public LikeActionControllerCreationCallback i;
    private Style j = Style.a;
    private HorizontalAlignment k = HorizontalAlignment.a;
    private AuxiliaryViewPosition l = AuxiliaryViewPosition.a;
    private int m = -1;
    private int n;
    private int o;

    public enum AuxiliaryViewPosition {
        BOTTOM("bottom", 0),
        INLINE("inline", 1),
        TOP("top", 2);
        
        static AuxiliaryViewPosition a;
        private String b;
        private int c;

        static {
            a = BOTTOM;
        }

        static AuxiliaryViewPosition a(int i) {
            AuxiliaryViewPosition[] values;
            for (AuxiliaryViewPosition auxiliaryViewPosition : values()) {
                if (auxiliaryViewPosition.a() == i) {
                    return auxiliaryViewPosition;
                }
            }
            return null;
        }

        private AuxiliaryViewPosition(String str, int i) {
            this.b = str;
            this.c = i;
        }

        public String toString() {
            return this.b;
        }

        /* access modifiers changed from: private */
        public int a() {
            return this.c;
        }
    }

    public enum HorizontalAlignment {
        CENTER("center", 0),
        LEFT("left", 1),
        RIGHT("right", 2);
        
        static HorizontalAlignment a;
        private String b;
        private int c;

        static {
            a = CENTER;
        }

        static HorizontalAlignment a(int i) {
            HorizontalAlignment[] values;
            for (HorizontalAlignment horizontalAlignment : values()) {
                if (horizontalAlignment.a() == i) {
                    return horizontalAlignment;
                }
            }
            return null;
        }

        private HorizontalAlignment(String str, int i) {
            this.b = str;
            this.c = i;
        }

        public String toString() {
            return this.b;
        }

        /* access modifiers changed from: private */
        public int a() {
            return this.c;
        }
    }

    class LikeActionControllerCreationCallback implements CreationCallback {
        private boolean b;

        private LikeActionControllerCreationCallback() {
        }

        public void a() {
            this.b = true;
        }

        public void onComplete(LikeActionController likeActionController) {
            if (!this.b) {
                LikeView.this.a(likeActionController);
                LikeView.this.c();
                LikeView.this.i = null;
            }
        }
    }

    class LikeControllerBroadcastReceiver extends BroadcastReceiver {
        private LikeControllerBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            boolean z = true;
            if (extras != null) {
                String string = extras.getString(LikeActionController.ACTION_OBJECT_ID_KEY);
                if (!Utility.isNullOrEmpty(string) && !Utility.areObjectsEqual(LikeView.this.a, string)) {
                    z = false;
                }
            }
            if (z) {
                if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED.equals(action)) {
                    LikeView.this.c();
                } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR.equals(action)) {
                    if (LikeView.this.g != null) {
                        LikeView.this.g.onError(extras);
                    }
                } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET.equals(action)) {
                    LikeView.this.setObjectIdForced(LikeView.this.a);
                    LikeView.this.c();
                }
            }
        }
    }

    public interface OnErrorListener {
        void onError(Bundle bundle);
    }

    public enum Style {
        STANDARD(CookieSpecs.STANDARD, 0),
        BUTTON("button", 1),
        BOX_COUNT("box_count", 2);
        
        static Style a;
        private String b;
        private int c;

        static {
            a = STANDARD;
        }

        static Style a(int i) {
            Style[] values;
            for (Style style : values()) {
                if (style.a() == i) {
                    return style;
                }
            }
            return null;
        }

        private Style(String str, int i) {
            this.b = str;
            this.c = i;
        }

        public String toString() {
            return this.b;
        }

        /* access modifiers changed from: private */
        public int a() {
            return this.c;
        }
    }

    public static boolean handleOnActivityResult(Context context, int i2, int i3, Intent intent) {
        return LikeActionController.handleOnActivityResult(context, i2, i3, intent);
    }

    public LikeView(Context context) {
        super(context);
        a(context);
    }

    public LikeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
        a(context);
    }

    public void setObjectId(String str) {
        String coerceValueIfNullOrEmpty = Utility.coerceValueIfNullOrEmpty(str, null);
        if (!Utility.areObjectsEqual(coerceValueIfNullOrEmpty, this.a)) {
            setObjectIdForced(coerceValueIfNullOrEmpty);
            c();
        }
    }

    public void setLikeViewStyle(Style style) {
        if (style == null) {
            style = Style.a;
        }
        if (this.j != style) {
            this.j = style;
            d();
        }
    }

    public void setAuxiliaryViewPosition(AuxiliaryViewPosition auxiliaryViewPosition) {
        if (auxiliaryViewPosition == null) {
            auxiliaryViewPosition = AuxiliaryViewPosition.a;
        }
        if (this.l != auxiliaryViewPosition) {
            this.l = auxiliaryViewPosition;
            d();
        }
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        if (horizontalAlignment == null) {
            horizontalAlignment = HorizontalAlignment.a;
        }
        if (this.k != horizontalAlignment) {
            this.k = horizontalAlignment;
            d();
        }
    }

    public void setForegroundColor(int i2) {
        if (this.m != i2) {
            this.e.setTextColor(i2);
        }
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.g = onErrorListener;
    }

    public OnErrorListener getOnErrorListener() {
        return this.g;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        setObjectId(null);
        super.onDetachedFromWindow();
    }

    private void a(AttributeSet attributeSet) {
        if (attributeSet != null && getContext() != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.com_facebook_like_view);
            if (obtainStyledAttributes != null) {
                this.a = Utility.coerceValueIfNullOrEmpty(obtainStyledAttributes.getString(1), null);
                this.j = Style.a(obtainStyledAttributes.getInt(2, Style.a.a()));
                if (this.j == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'style'");
                }
                this.l = AuxiliaryViewPosition.a(obtainStyledAttributes.getInt(3, AuxiliaryViewPosition.a.a()));
                if (this.l == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'auxiliary_view_position'");
                }
                this.k = HorizontalAlignment.a(obtainStyledAttributes.getInt(4, HorizontalAlignment.a.a()));
                if (this.k == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'horizontal_alignment'");
                }
                this.m = obtainStyledAttributes.getColor(0, -1);
                obtainStyledAttributes.recycle();
            }
        }
    }

    private void a(Context context) {
        this.n = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeview_edge_padding);
        this.o = getResources().getDimensionPixelSize(R.dimen.com_facebook_likeview_internal_padding);
        if (this.m == -1) {
            this.m = getResources().getColor(R.color.com_facebook_likeview_text_color);
        }
        setBackgroundColor(0);
        this.b = new LinearLayout(context);
        this.b.setLayoutParams(new LayoutParams(-2, -2));
        b(context);
        c(context);
        d(context);
        this.b.addView(this.c);
        this.b.addView(this.e);
        this.b.addView(this.d);
        addView(this.b);
        setObjectIdForced(this.a);
        c();
    }

    private void b(Context context) {
        this.c = new LikeButton(context, this.f != null ? this.f.isObjectLiked() : false);
        this.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LikeView.this.a();
            }
        });
        this.c.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    }

    private void c(Context context) {
        this.e = new TextView(context);
        this.e.setTextSize(0, getResources().getDimension(R.dimen.com_facebook_likeview_text_size));
        this.e.setMaxLines(2);
        this.e.setTextColor(this.m);
        this.e.setGravity(17);
        this.e.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
    }

    private void d(Context context) {
        this.d = new LikeBoxCountView(context);
        this.d.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.f != null) {
            this.f.toggleLike((Activity) getContext(), getAnalyticsParameters());
        }
    }

    private Bundle getAnalyticsParameters() {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, this.j.toString());
        bundle.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_AUXILIARY_POSITION, this.l.toString());
        bundle.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_HORIZONTAL_ALIGNMENT, this.k.toString());
        bundle.putString("object_id", Utility.coerceValueIfNullOrEmpty(this.a, ""));
        return bundle;
    }

    /* access modifiers changed from: private */
    public void setObjectIdForced(String str) {
        b();
        this.a = str;
        if (!Utility.isNullOrEmpty(str)) {
            this.i = new LikeActionControllerCreationCallback();
            LikeActionController.getControllerForObjectId(getContext(), str, this.i);
        }
    }

    /* access modifiers changed from: private */
    public void a(LikeActionController likeActionController) {
        this.f = likeActionController;
        this.h = new LikeControllerBroadcastReceiver();
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR);
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
        instance.registerReceiver(this.h, intentFilter);
    }

    private void b() {
        if (this.h != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.h);
            this.h = null;
        }
        if (this.i != null) {
            this.i.a();
            this.i = null;
        }
        this.f = null;
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.f == null) {
            this.c.setLikeState(false);
            this.e.setText(null);
            this.d.setText(null);
        } else {
            this.c.setLikeState(this.f.isObjectLiked());
            this.e.setText(this.f.getSocialSentence());
            this.d.setText(this.f.getLikeCountString());
        }
        d();
    }

    private void d() {
        View view;
        LayoutParams layoutParams = (LayoutParams) this.b.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.c.getLayoutParams();
        int i2 = this.k == HorizontalAlignment.LEFT ? 3 : this.k == HorizontalAlignment.CENTER ? 1 : 5;
        layoutParams.gravity = i2 | 48;
        layoutParams2.gravity = i2;
        this.e.setVisibility(8);
        this.d.setVisibility(8);
        if (this.j == Style.STANDARD && this.f != null && !Utility.isNullOrEmpty(this.f.getSocialSentence())) {
            view = this.e;
        } else if (this.j == Style.BOX_COUNT && this.f != null && !Utility.isNullOrEmpty(this.f.getLikeCountString())) {
            e();
            view = this.d;
        } else {
            return;
        }
        int i3 = 0;
        view.setVisibility(0);
        ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity = i2;
        LinearLayout linearLayout = this.b;
        if (this.l != AuxiliaryViewPosition.INLINE) {
            i3 = 1;
        }
        linearLayout.setOrientation(i3);
        if (this.l == AuxiliaryViewPosition.TOP || (this.l == AuxiliaryViewPosition.INLINE && this.k == HorizontalAlignment.RIGHT)) {
            this.b.removeView(this.c);
            this.b.addView(this.c);
        } else {
            this.b.removeView(view);
            this.b.addView(view);
        }
        switch (this.l) {
            case TOP:
                view.setPadding(this.n, this.n, this.n, this.o);
                break;
            case BOTTOM:
                view.setPadding(this.n, this.o, this.n, this.n);
                break;
            case INLINE:
                if (this.k != HorizontalAlignment.RIGHT) {
                    view.setPadding(this.o, this.n, this.n, this.n);
                    break;
                } else {
                    view.setPadding(this.n, this.n, this.o, this.n);
                    break;
                }
        }
    }

    private void e() {
        switch (this.l) {
            case TOP:
                this.d.setCaretPosition(LikeBoxCountViewCaretPosition.BOTTOM);
                return;
            case BOTTOM:
                this.d.setCaretPosition(LikeBoxCountViewCaretPosition.TOP);
                return;
            case INLINE:
                this.d.setCaretPosition(this.k == HorizontalAlignment.RIGHT ? LikeBoxCountViewCaretPosition.RIGHT : LikeBoxCountViewCaretPosition.LEFT);
                return;
            default:
                return;
        }
    }
}
