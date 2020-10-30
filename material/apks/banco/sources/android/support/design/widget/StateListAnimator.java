package android.support.design.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import java.util.ArrayList;

final class StateListAnimator {
    ValueAnimator a = null;
    private final ArrayList<Tuple> b = new ArrayList<>();
    private Tuple c = null;
    private final AnimatorListener d = new AnimatorListenerAdapter() {
        public void onAnimationEnd(Animator animator) {
            if (StateListAnimator.this.a == animator) {
                StateListAnimator.this.a = null;
            }
        }
    };

    static class Tuple {
        final int[] a;
        final ValueAnimator b;

        Tuple(int[] iArr, ValueAnimator valueAnimator) {
            this.a = iArr;
            this.b = valueAnimator;
        }
    }

    StateListAnimator() {
    }

    public void a(int[] iArr, ValueAnimator valueAnimator) {
        Tuple tuple = new Tuple(iArr, valueAnimator);
        valueAnimator.addListener(this.d);
        this.b.add(tuple);
    }

    /* access modifiers changed from: 0000 */
    public void a(int[] iArr) {
        Tuple tuple;
        int size = this.b.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                tuple = null;
                break;
            }
            tuple = (Tuple) this.b.get(i);
            if (StateSet.stateSetMatches(tuple.a, iArr)) {
                break;
            }
            i++;
        }
        if (tuple != this.c) {
            if (this.c != null) {
                b();
            }
            this.c = tuple;
            if (tuple != null) {
                a(tuple);
            }
        }
    }

    private void a(Tuple tuple) {
        this.a = tuple.b;
        this.a.start();
    }

    private void b() {
        if (this.a != null) {
            this.a.cancel();
            this.a = null;
        }
    }

    public void a() {
        if (this.a != null) {
            this.a.end();
            this.a = null;
        }
    }
}
