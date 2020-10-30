package android.support.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.transition.Transition.EpicenterCallback;
import android.support.transition.Transition.TransitionListener;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

public class TransitionSet extends Transition {
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    private ArrayList<Transition> g = new ArrayList<>();
    private boolean h = true;
    /* access modifiers changed from: private */
    public int i;
    /* access modifiers changed from: private */
    public boolean j = false;

    static class TransitionSetListener extends TransitionListenerAdapter {
        TransitionSet a;

        TransitionSetListener(TransitionSet transitionSet) {
            this.a = transitionSet;
        }

        public void onTransitionStart(@NonNull Transition transition) {
            if (!this.a.j) {
                this.a.start();
                this.a.j = true;
            }
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            TransitionSet.b(this.a);
            if (this.a.i == 0) {
                this.a.j = false;
                this.a.end();
            }
            transition.removeListener(this);
        }
    }

    static /* synthetic */ int b(TransitionSet transitionSet) {
        int i2 = transitionSet.i - 1;
        transitionSet.i = i2;
        return i2;
    }

    public TransitionSet() {
    }

    public TransitionSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.i);
        setOrdering(TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionOrdering", 0, 0));
        obtainStyledAttributes.recycle();
    }

    @NonNull
    public TransitionSet setOrdering(int i2) {
        switch (i2) {
            case 0:
                this.h = true;
                break;
            case 1:
                this.h = false;
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid parameter for TransitionSet ordering: ");
                sb.append(i2);
                throw new AndroidRuntimeException(sb.toString());
        }
        return this;
    }

    public int getOrdering() {
        return this.h ^ true ? 1 : 0;
    }

    @NonNull
    public TransitionSet addTransition(@NonNull Transition transition) {
        this.g.add(transition);
        transition.d = this;
        if (this.a >= 0) {
            transition.setDuration(this.a);
        }
        return this;
    }

    public int getTransitionCount() {
        return this.g.size();
    }

    public Transition getTransitionAt(int i2) {
        if (i2 < 0 || i2 >= this.g.size()) {
            return null;
        }
        return (Transition) this.g.get(i2);
    }

    @NonNull
    public TransitionSet setDuration(long j2) {
        super.setDuration(j2);
        if (this.a >= 0) {
            int size = this.g.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((Transition) this.g.get(i2)).setDuration(j2);
            }
        }
        return this;
    }

    @NonNull
    public TransitionSet setStartDelay(long j2) {
        return (TransitionSet) super.setStartDelay(j2);
    }

    @NonNull
    public TransitionSet setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        return (TransitionSet) super.setInterpolator(timeInterpolator);
    }

    @NonNull
    public TransitionSet addTarget(@NonNull View view) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).addTarget(view);
        }
        return (TransitionSet) super.addTarget(view);
    }

    @NonNull
    public TransitionSet addTarget(@IdRes int i2) {
        for (int i3 = 0; i3 < this.g.size(); i3++) {
            ((Transition) this.g.get(i3)).addTarget(i2);
        }
        return (TransitionSet) super.addTarget(i2);
    }

    @NonNull
    public TransitionSet addTarget(@NonNull String str) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).addTarget(str);
        }
        return (TransitionSet) super.addTarget(str);
    }

    @NonNull
    public TransitionSet addTarget(@NonNull Class cls) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).addTarget(cls);
        }
        return (TransitionSet) super.addTarget(cls);
    }

    @NonNull
    public TransitionSet addListener(@NonNull TransitionListener transitionListener) {
        return (TransitionSet) super.addListener(transitionListener);
    }

    @NonNull
    public TransitionSet removeTarget(@IdRes int i2) {
        for (int i3 = 0; i3 < this.g.size(); i3++) {
            ((Transition) this.g.get(i3)).removeTarget(i2);
        }
        return (TransitionSet) super.removeTarget(i2);
    }

    @NonNull
    public TransitionSet removeTarget(@NonNull View view) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).removeTarget(view);
        }
        return (TransitionSet) super.removeTarget(view);
    }

    @NonNull
    public TransitionSet removeTarget(@NonNull Class cls) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).removeTarget(cls);
        }
        return (TransitionSet) super.removeTarget(cls);
    }

    @NonNull
    public TransitionSet removeTarget(@NonNull String str) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).removeTarget(str);
        }
        return (TransitionSet) super.removeTarget(str);
    }

    @NonNull
    public Transition excludeTarget(@NonNull View view, boolean z) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).excludeTarget(view, z);
        }
        return super.excludeTarget(view, z);
    }

    @NonNull
    public Transition excludeTarget(@NonNull String str, boolean z) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).excludeTarget(str, z);
        }
        return super.excludeTarget(str, z);
    }

    @NonNull
    public Transition excludeTarget(int i2, boolean z) {
        for (int i3 = 0; i3 < this.g.size(); i3++) {
            ((Transition) this.g.get(i3)).excludeTarget(i2, z);
        }
        return super.excludeTarget(i2, z);
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class cls, boolean z) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).excludeTarget(cls, z);
        }
        return super.excludeTarget(cls, z);
    }

    @NonNull
    public TransitionSet removeListener(@NonNull TransitionListener transitionListener) {
        return (TransitionSet) super.removeListener(transitionListener);
    }

    public void setPathMotion(PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            ((Transition) this.g.get(i2)).setPathMotion(pathMotion);
        }
    }

    @NonNull
    public TransitionSet removeTransition(@NonNull Transition transition) {
        this.g.remove(transition);
        transition.d = null;
        return this;
    }

    private void a() {
        TransitionSetListener transitionSetListener = new TransitionSetListener(this);
        Iterator it = this.g.iterator();
        while (it.hasNext()) {
            ((Transition) it.next()).addListener(transitionSetListener);
        }
        this.i = this.g.size();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        long startDelay = getStartDelay();
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            Transition transition = (Transition) this.g.get(i2);
            if (startDelay > 0 && (this.h || i2 == 0)) {
                long startDelay2 = transition.getStartDelay();
                if (startDelay2 > 0) {
                    transition.setStartDelay(startDelay + startDelay2);
                } else {
                    transition.setStartDelay(startDelay);
                }
            }
            transition.createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2, arrayList, arrayList2);
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void runAnimators() {
        if (this.g.isEmpty()) {
            start();
            end();
            return;
        }
        a();
        if (!this.h) {
            for (int i2 = 1; i2 < this.g.size(); i2++) {
                final Transition transition = (Transition) this.g.get(i2);
                ((Transition) this.g.get(i2 - 1)).addListener(new TransitionListenerAdapter() {
                    public void onTransitionEnd(@NonNull Transition transition) {
                        transition.runAnimators();
                        transition.removeListener(this);
                    }
                });
            }
            Transition transition2 = (Transition) this.g.get(0);
            if (transition2 != null) {
                transition2.runAnimators();
            }
        } else {
            Iterator it = this.g.iterator();
            while (it.hasNext()) {
                ((Transition) it.next()).runAnimators();
            }
        }
    }

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        if (b(transitionValues.view)) {
            Iterator it = this.g.iterator();
            while (it.hasNext()) {
                Transition transition = (Transition) it.next();
                if (transition.b(transitionValues.view)) {
                    transition.captureStartValues(transitionValues);
                    transitionValues.a.add(transition);
                }
            }
        }
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        if (b(transitionValues.view)) {
            Iterator it = this.g.iterator();
            while (it.hasNext()) {
                Transition transition = (Transition) it.next();
                if (transition.b(transitionValues.view)) {
                    transition.captureEndValues(transitionValues);
                    transitionValues.a.add(transition);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(TransitionValues transitionValues) {
        super.a(transitionValues);
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).a(transitionValues);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void pause(View view) {
        super.pause(view);
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).pause(view);
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void resume(View view) {
        super.resume(view);
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).resume(view);
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void cancel() {
        super.cancel();
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).cancel();
        }
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void b(ViewGroup viewGroup) {
        super.b(viewGroup);
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).b(viewGroup);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: d */
    public TransitionSet c(ViewGroup viewGroup) {
        super.c(viewGroup);
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).c(viewGroup);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void b(boolean z) {
        super.b(z);
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).b(z);
        }
    }

    public void setPropagation(TransitionPropagation transitionPropagation) {
        super.setPropagation(transitionPropagation);
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).setPropagation(transitionPropagation);
        }
    }

    public void setEpicenterCallback(EpicenterCallback epicenterCallback) {
        super.setEpicenterCallback(epicenterCallback);
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Transition) this.g.get(i2)).setEpicenterCallback(epicenterCallback);
        }
    }

    /* access modifiers changed from: 0000 */
    public String a(String str) {
        String a = super.a(str);
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("\n");
            Transition transition = (Transition) this.g.get(i2);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("  ");
            sb.append(transition.a(sb2.toString()));
            a = sb.toString();
        }
        return a;
    }

    public Transition clone() {
        TransitionSet transitionSet = (TransitionSet) super.clone();
        transitionSet.g = new ArrayList<>();
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            transitionSet.addTransition(((Transition) this.g.get(i2)).clone());
        }
        return transitionSet;
    }
}
