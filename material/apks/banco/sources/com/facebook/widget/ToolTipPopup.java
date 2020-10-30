package com.facebook.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.facebook.android.R;
import java.lang.ref.WeakReference;

public class ToolTipPopup {
    public static final long DEFAULT_POPUP_DISPLAY_TIME = 6000;
    private final String a;
    /* access modifiers changed from: private */
    public final WeakReference<View> b;
    private final Context c;
    /* access modifiers changed from: private */
    public PopupContentView d;
    /* access modifiers changed from: private */
    public PopupWindow e;
    private Style f = Style.BLUE;
    private long g = DEFAULT_POPUP_DISPLAY_TIME;
    private final OnScrollChangedListener h = new OnScrollChangedListener() {
        public void onScrollChanged() {
            if (ToolTipPopup.this.b.get() != null && ToolTipPopup.this.e != null && ToolTipPopup.this.e.isShowing()) {
                if (ToolTipPopup.this.e.isAboveAnchor()) {
                    ToolTipPopup.this.d.b();
                } else {
                    ToolTipPopup.this.d.a();
                }
            }
        }
    };

    class PopupContentView extends FrameLayout {
        /* access modifiers changed from: private */
        public ImageView b;
        /* access modifiers changed from: private */
        public ImageView c;
        /* access modifiers changed from: private */
        public View d;
        /* access modifiers changed from: private */
        public ImageView e;

        public PopupContentView(Context context) {
            super(context);
            c();
        }

        private void c() {
            LayoutInflater.from(getContext()).inflate(R.layout.com_facebook_tooltip_bubble, this);
            this.b = (ImageView) findViewById(R.id.com_facebook_tooltip_bubble_view_top_pointer);
            this.c = (ImageView) findViewById(R.id.com_facebook_tooltip_bubble_view_bottom_pointer);
            this.d = findViewById(R.id.com_facebook_body_frame);
            this.e = (ImageView) findViewById(R.id.com_facebook_button_xout);
        }

        public void a() {
            this.b.setVisibility(0);
            this.c.setVisibility(4);
        }

        public void b() {
            this.b.setVisibility(4);
            this.c.setVisibility(0);
        }

        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
        }
    }

    public enum Style {
        BLUE,
        BLACK
    }

    public ToolTipPopup(String str, View view) {
        this.a = str;
        this.b = new WeakReference<>(view);
        this.c = view.getContext();
    }

    public void setStyle(Style style) {
        this.f = style;
    }

    public void show() {
        if (this.b.get() != null) {
            this.d = new PopupContentView(this.c);
            ((TextView) this.d.findViewById(R.id.com_facebook_tooltip_bubble_view_text_body)).setText(this.a);
            if (this.f == Style.BLUE) {
                this.d.d.setBackgroundResource(R.drawable.com_facebook_tooltip_blue_background);
                this.d.c.setImageResource(R.drawable.com_facebook_tooltip_blue_bottomnub);
                this.d.b.setImageResource(R.drawable.com_facebook_tooltip_blue_topnub);
                this.d.e.setImageResource(R.drawable.com_facebook_tooltip_blue_xout);
            } else {
                this.d.d.setBackgroundResource(R.drawable.com_facebook_tooltip_black_background);
                this.d.c.setImageResource(R.drawable.com_facebook_tooltip_black_bottomnub);
                this.d.b.setImageResource(R.drawable.com_facebook_tooltip_black_topnub);
                this.d.e.setImageResource(R.drawable.com_facebook_tooltip_black_xout);
            }
            View decorView = ((Activity) this.c).getWindow().getDecorView();
            int width = decorView.getWidth();
            int height = decorView.getHeight();
            b();
            this.d.onMeasure(MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE));
            this.e = new PopupWindow(this.d, this.d.getMeasuredWidth(), this.d.getMeasuredHeight());
            this.e.showAsDropDown((View) this.b.get());
            a();
            if (this.g > 0) {
                this.d.postDelayed(new Runnable() {
                    public void run() {
                        ToolTipPopup.this.dismiss();
                    }
                }, this.g);
            }
            this.e.setTouchable(true);
            this.d.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ToolTipPopup.this.dismiss();
                }
            });
        }
    }

    public void setNuxDisplayTime(long j) {
        this.g = j;
    }

    private void a() {
        if (this.e != null && this.e.isShowing()) {
            if (this.e.isAboveAnchor()) {
                this.d.b();
            } else {
                this.d.a();
            }
        }
    }

    public void dismiss() {
        c();
        if (this.e != null) {
            this.e.dismiss();
        }
    }

    private void b() {
        c();
        if (this.b.get() != null) {
            ((View) this.b.get()).getViewTreeObserver().addOnScrollChangedListener(this.h);
        }
    }

    private void c() {
        if (this.b.get() != null) {
            ((View) this.b.get()).getViewTreeObserver().removeOnScrollChangedListener(this.h);
        }
    }
}
