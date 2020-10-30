package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import java.util.ArrayList;

public abstract class Animator implements Cloneable {
    ArrayList<AnimatorListener> a = null;

    public interface AnimatorListener {
        void onAnimationCancel(Animator animator);

        void onAnimationEnd(Animator animator);

        void onAnimationRepeat(Animator animator);

        void onAnimationStart(Animator animator);
    }

    public void cancel() {
    }

    public void end() {
    }

    public abstract long getDuration();

    public abstract long getStartDelay();

    public abstract boolean isRunning();

    public abstract Animator setDuration(long j);

    public abstract void setInterpolator(Interpolator interpolator);

    public abstract void setStartDelay(long j);

    public void setTarget(Object obj) {
    }

    public void setupEndValues() {
    }

    public void setupStartValues() {
    }

    public void start() {
    }

    public boolean isStarted() {
        return isRunning();
    }

    public void addListener(AnimatorListener animatorListener) {
        if (this.a == null) {
            this.a = new ArrayList<>();
        }
        this.a.add(animatorListener);
    }

    public void removeListener(AnimatorListener animatorListener) {
        if (this.a != null) {
            this.a.remove(animatorListener);
            if (this.a.size() == 0) {
                this.a = null;
            }
        }
    }

    public ArrayList<AnimatorListener> getListeners() {
        return this.a;
    }

    public void removeAllListeners() {
        if (this.a != null) {
            this.a.clear();
            this.a = null;
        }
    }

    public Animator clone() {
        try {
            Animator animator = (Animator) super.clone();
            if (this.a != null) {
                ArrayList<AnimatorListener> arrayList = this.a;
                animator.a = new ArrayList<>();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    animator.a.add(arrayList.get(i));
                }
            }
            return animator;
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }
}
