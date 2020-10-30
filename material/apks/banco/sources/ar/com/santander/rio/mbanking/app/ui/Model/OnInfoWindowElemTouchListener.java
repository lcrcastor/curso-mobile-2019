package ar.com.santander.rio.mbanking.app.ui.Model;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

public abstract class OnInfoWindowElemTouchListener implements OnTouchListener {
    /* access modifiers changed from: private */
    public final View a;
    private final Drawable b;
    private final Drawable c;
    private final Handler d = new Handler();
    /* access modifiers changed from: private */
    public Marker e;
    private boolean f = false;
    private final Runnable g = new Runnable() {
        public void run() {
            if (OnInfoWindowElemTouchListener.this.b()) {
                OnInfoWindowElemTouchListener.this.onClickConfirmed(OnInfoWindowElemTouchListener.this.a, OnInfoWindowElemTouchListener.this.e);
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract void onClickConfirmed(View view, Marker marker);

    public OnInfoWindowElemTouchListener(View view, Drawable drawable, Drawable drawable2) {
        this.a = view;
        this.b = drawable;
        this.c = drawable2;
    }

    public OnInfoWindowElemTouchListener(View view) {
        this.a = view;
        this.b = null;
        this.c = null;
    }

    public void setMarker(Marker marker) {
        this.e = marker;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (BitmapDescriptorFactory.HUE_RED > motionEvent.getX() || motionEvent.getX() > ((float) this.a.getWidth()) || BitmapDescriptorFactory.HUE_RED > motionEvent.getY() || motionEvent.getY() > ((float) this.a.getHeight())) {
            b();
        } else {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 3) {
                switch (actionMasked) {
                    case 0:
                        a();
                        break;
                    case 1:
                        this.d.postDelayed(this.g, 150);
                        break;
                }
            } else {
                b();
            }
        }
        return false;
    }

    private void a() {
        if (!this.f) {
            this.f = true;
            this.d.removeCallbacks(this.g);
            this.a.setBackground(this.c);
            if (this.e != null) {
                this.e.showInfoWindow();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean b() {
        if (!this.f) {
            return false;
        }
        this.f = false;
        this.d.removeCallbacks(this.g);
        this.a.setBackground(this.b);
        if (this.e != null) {
            this.e.showInfoWindow();
        }
        return true;
    }
}
