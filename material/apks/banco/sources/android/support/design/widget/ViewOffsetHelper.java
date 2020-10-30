package android.support.design.widget;

import android.support.v4.view.ViewCompat;
import android.view.View;

class ViewOffsetHelper {
    private final View a;
    private int b;
    private int c;
    private int d;
    private int e;

    public ViewOffsetHelper(View view) {
        this.a = view;
    }

    public void a() {
        this.b = this.a.getTop();
        this.c = this.a.getLeft();
        e();
    }

    private void e() {
        ViewCompat.offsetTopAndBottom(this.a, this.d - (this.a.getTop() - this.b));
        ViewCompat.offsetLeftAndRight(this.a, this.e - (this.a.getLeft() - this.c));
    }

    public boolean a(int i) {
        if (this.d == i) {
            return false;
        }
        this.d = i;
        e();
        return true;
    }

    public boolean b(int i) {
        if (this.e == i) {
            return false;
        }
        this.e = i;
        e();
        return true;
    }

    public int b() {
        return this.d;
    }

    public int c() {
        return this.e;
    }

    public int d() {
        return this.b;
    }
}
