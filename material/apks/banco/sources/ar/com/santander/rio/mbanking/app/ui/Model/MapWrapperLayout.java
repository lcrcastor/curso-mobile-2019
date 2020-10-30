package ar.com.santander.rio.mbanking.app.ui.Model;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapWrapperLayout extends RelativeLayout {
    private GoogleMap a;
    private int b;
    private Marker c;
    private View d;

    public MapWrapperLayout(Context context) {
        super(context);
    }

    public MapWrapperLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MapWrapperLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void init(GoogleMap googleMap, int i) {
        this.a = googleMap;
        this.b = i;
    }

    public void setMarkerWithInfoWindow(Marker marker, View view) {
        this.c = marker;
        this.d = view;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (this.c == null || !this.c.isInfoWindowShown() || this.a == null || this.d == null) {
            z = false;
        } else {
            Point screenLocation = this.a.getProjection().toScreenLocation(this.c.getPosition());
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.offsetLocation((float) ((-screenLocation.x) + (this.d.getWidth() / 2)), (float) ((-screenLocation.y) + this.d.getHeight() + this.b));
            z = this.d.dispatchTouchEvent(obtain);
        }
        if (z || super.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        return false;
    }
}
