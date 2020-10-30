package android.support.constraint.solver.widgets;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ResolutionDimension extends ResolutionNode {
    float a = BitmapDescriptorFactory.HUE_RED;

    public void reset() {
        super.reset();
        this.a = BitmapDescriptorFactory.HUE_RED;
    }

    public void resolve(int i) {
        if (this.i == 0 || this.a != ((float) i)) {
            this.a = (float) i;
            if (this.i == 1) {
                invalidate();
            }
            didResolve();
        }
    }

    public void remove() {
        this.i = 2;
    }
}
