package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

class LayoutState {
    boolean a = true;
    int b;
    int c;
    int d;
    int e;
    int f = 0;
    int g = 0;
    boolean h;
    boolean i;

    LayoutState() {
    }

    /* access modifiers changed from: 0000 */
    public boolean a(State state) {
        return this.c >= 0 && this.c < state.getItemCount();
    }

    /* access modifiers changed from: 0000 */
    public View a(Recycler recycler) {
        View viewForPosition = recycler.getViewForPosition(this.c);
        this.c += this.d;
        return viewForPosition;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LayoutState{mAvailable=");
        sb.append(this.b);
        sb.append(", mCurrentPosition=");
        sb.append(this.c);
        sb.append(", mItemDirection=");
        sb.append(this.d);
        sb.append(", mLayoutDirection=");
        sb.append(this.e);
        sb.append(", mStartLine=");
        sb.append(this.f);
        sb.append(", mEndLine=");
        sb.append(this.g);
        sb.append('}');
        return sb.toString();
    }
}
