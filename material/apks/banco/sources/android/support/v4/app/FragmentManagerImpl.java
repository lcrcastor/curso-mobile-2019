package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl extends FragmentManager implements Factory2 {
    static final Interpolator E = new DecelerateInterpolator(2.5f);
    static final Interpolator F = new DecelerateInterpolator(1.5f);
    static final Interpolator G = new AccelerateInterpolator(2.5f);
    static final Interpolator H = new AccelerateInterpolator(1.5f);
    static boolean a = false;
    static Field q;
    SparseArray<Parcelable> A = null;
    ArrayList<StartEnterTransitionListener> B;
    FragmentManagerNonConfig C;
    Runnable D = new Runnable() {
        public void run() {
            FragmentManagerImpl.this.d();
        }
    };
    private final CopyOnWriteArrayList<Pair<FragmentLifecycleCallbacks, Boolean>> I = new CopyOnWriteArrayList<>();
    ArrayList<OpGenerator> b;
    boolean c;
    int d = 0;
    final ArrayList<Fragment> e = new ArrayList<>();
    SparseArray<Fragment> f;
    ArrayList<BackStackRecord> g;
    ArrayList<Fragment> h;
    ArrayList<BackStackRecord> i;
    ArrayList<Integer> j;
    ArrayList<OnBackStackChangedListener> k;
    int l = 0;
    FragmentHostCallback m;
    FragmentContainer n;
    Fragment o;
    Fragment p;
    boolean r;
    boolean s;
    boolean t;
    String u;
    boolean v;
    ArrayList<BackStackRecord> w;
    ArrayList<Boolean> x;
    ArrayList<Fragment> y;
    Bundle z = null;

    static class AnimateOnHWLayerIfNeededListener extends AnimationListenerWrapper {
        View a;

        AnimateOnHWLayerIfNeededListener(View view, AnimationListener animationListener) {
            super(animationListener);
            this.a = view;
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (ViewCompat.isAttachedToWindow(this.a) || VERSION.SDK_INT >= 24) {
                this.a.post(new Runnable() {
                    public void run() {
                        AnimateOnHWLayerIfNeededListener.this.a.setLayerType(0, null);
                    }
                });
            } else {
                this.a.setLayerType(0, null);
            }
            super.onAnimationEnd(animation);
        }
    }

    static class AnimationListenerWrapper implements AnimationListener {
        private final AnimationListener a;

        private AnimationListenerWrapper(AnimationListener animationListener) {
            this.a = animationListener;
        }

        @CallSuper
        public void onAnimationStart(Animation animation) {
            if (this.a != null) {
                this.a.onAnimationStart(animation);
            }
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (this.a != null) {
                this.a.onAnimationEnd(animation);
            }
        }

        @CallSuper
        public void onAnimationRepeat(Animation animation) {
            if (this.a != null) {
                this.a.onAnimationRepeat(animation);
            }
        }
    }

    static class AnimationOrAnimator {
        public final Animation a;
        public final Animator b;

        private AnimationOrAnimator(Animation animation) {
            this.a = animation;
            this.b = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }

        private AnimationOrAnimator(Animator animator) {
            this.a = null;
            this.b = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }
    }

    static class AnimatorOnHWLayerIfNeededListener extends AnimatorListenerAdapter {
        View a;

        AnimatorOnHWLayerIfNeededListener(View view) {
            this.a = view;
        }

        public void onAnimationStart(Animator animator) {
            this.a.setLayerType(2, null);
        }

        public void onAnimationEnd(Animator animator) {
            this.a.setLayerType(0, null);
            animator.removeListener(this);
        }
    }

    static class EndViewTransitionAnimator extends AnimationSet implements Runnable {
        private final ViewGroup a;
        private final View b;
        private boolean c;
        private boolean d;

        EndViewTransitionAnimator(@NonNull Animation animation, @NonNull ViewGroup viewGroup, @NonNull View view) {
            super(false);
            this.a = viewGroup;
            this.b = view;
            addAnimation(animation);
        }

        public boolean getTransformation(long j, Transformation transformation) {
            if (this.c) {
                return !this.d;
            }
            if (!super.getTransformation(j, transformation)) {
                this.c = true;
                this.a.post(this);
            }
            return true;
        }

        public boolean getTransformation(long j, Transformation transformation, float f) {
            if (this.c) {
                return !this.d;
            }
            if (!super.getTransformation(j, transformation, f)) {
                this.c = true;
                this.a.post(this);
            }
            return true;
        }

        public void run() {
            this.a.endViewTransition(this.b);
            this.d = true;
        }
    }

    static class FragmentTag {
        public static final int[] a = {16842755, 16842960, 16842961};

        FragmentTag() {
        }
    }

    interface OpGenerator {
        boolean a(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2);
    }

    class PopBackStackState implements OpGenerator {
        final String a;
        final int b;
        final int c;

        PopBackStackState(String str, int i, int i2) {
            this.a = str;
            this.b = i;
            this.c = i2;
        }

        public boolean a(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            if (FragmentManagerImpl.this.p != null && this.b < 0 && this.a == null) {
                FragmentManager b2 = FragmentManagerImpl.this.p.b();
                if (b2 != null && b2.popBackStackImmediate()) {
                    return false;
                }
            }
            return FragmentManagerImpl.this.a(arrayList, arrayList2, this.a, this.b, this.c);
        }
    }

    static class StartEnterTransitionListener implements OnStartEnterTransitionListener {
        /* access modifiers changed from: private */
        public final boolean a;
        /* access modifiers changed from: private */
        public final BackStackRecord b;
        private int c;

        StartEnterTransitionListener(BackStackRecord backStackRecord, boolean z) {
            this.a = z;
            this.b = backStackRecord;
        }

        public void a() {
            this.c--;
            if (this.c == 0) {
                this.b.a.w();
            }
        }

        public void b() {
            this.c++;
        }

        public boolean c() {
            return this.c == 0;
        }

        public void d() {
            boolean z = this.c > 0;
            FragmentManagerImpl fragmentManagerImpl = this.b.a;
            int size = fragmentManagerImpl.e.size();
            for (int i = 0; i < size; i++) {
                Fragment fragment = (Fragment) fragmentManagerImpl.e.get(i);
                fragment.a((OnStartEnterTransitionListener) null);
                if (z && fragment.w()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            this.b.a.a(this.b, this.a, !z, true);
        }

        public void e() {
            this.b.a.a(this.b, this.a, false, false);
        }
    }

    public static int b(int i2, boolean z2) {
        if (i2 == 4097) {
            return z2 ? 1 : 2;
        }
        if (i2 == 4099) {
            return z2 ? 5 : 6;
        }
        if (i2 != 8194) {
            return -1;
        }
        return z2 ? 3 : 4;
    }

    public static int c(int i2) {
        if (i2 == 4097) {
            return 8194;
        }
        if (i2 == 4099) {
            return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
        }
        if (i2 != 8194) {
            return 0;
        }
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    /* access modifiers changed from: 0000 */
    public Factory2 u() {
        return this;
    }

    FragmentManagerImpl() {
    }

    static boolean a(AnimationOrAnimator animationOrAnimator) {
        if (animationOrAnimator.a instanceof AlphaAnimation) {
            return true;
        }
        if (!(animationOrAnimator.a instanceof AnimationSet)) {
            return a(animationOrAnimator.b);
        }
        List animations = ((AnimationSet) animationOrAnimator.a).getAnimations();
        for (int i2 = 0; i2 < animations.size(); i2++) {
            if (animations.get(i2) instanceof AlphaAnimation) {
                return true;
            }
        }
        return false;
    }

    static boolean a(Animator animator) {
        if (animator == null) {
            return false;
        }
        if (animator instanceof ValueAnimator) {
            PropertyValuesHolder[] values = ((ValueAnimator) animator).getValues();
            for (PropertyValuesHolder propertyName : values) {
                if ("alpha".equals(propertyName.getPropertyName())) {
                    return true;
                }
            }
        } else if (animator instanceof AnimatorSet) {
            ArrayList childAnimations = ((AnimatorSet) animator).getChildAnimations();
            for (int i2 = 0; i2 < childAnimations.size(); i2++) {
                if (a((Animator) childAnimations.get(i2))) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean a(View view, AnimationOrAnimator animationOrAnimator) {
        boolean z2 = false;
        if (view == null || animationOrAnimator == null) {
            return false;
        }
        if (VERSION.SDK_INT >= 19 && view.getLayerType() == 0 && ViewCompat.hasOverlappingRendering(view) && a(animationOrAnimator)) {
            z2 = true;
        }
        return z2;
    }

    private void a(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
        if (this.m != null) {
            try {
                this.m.onDump("  ", null, printWriter, new String[0]);
            } catch (Exception e2) {
                Log.e("FragmentManager", "Failed dumping state", e2);
            }
        } else {
            try {
                dump("  ", null, printWriter, new String[0]);
            } catch (Exception e3) {
                Log.e("FragmentManager", "Failed dumping state", e3);
            }
        }
        throw runtimeException;
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        boolean d2 = d();
        y();
        return d2;
    }

    public void popBackStack() {
        a((OpGenerator) new PopBackStackState(null, -1, 0), false);
    }

    public boolean popBackStackImmediate() {
        v();
        return a((String) null, -1, 0);
    }

    public void popBackStack(String str, int i2) {
        a((OpGenerator) new PopBackStackState(str, -1, i2), false);
    }

    public boolean popBackStackImmediate(String str, int i2) {
        v();
        return a(str, -1, i2);
    }

    public void popBackStack(int i2, int i3) {
        if (i2 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad id: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
        a((OpGenerator) new PopBackStackState(null, i2, i3), false);
    }

    public boolean popBackStackImmediate(int i2, int i3) {
        v();
        d();
        if (i2 >= 0) {
            return a((String) null, i2, i3);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Bad id: ");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }

    private boolean a(String str, int i2, int i3) {
        d();
        c(true);
        if (this.p != null && i2 < 0 && str == null) {
            FragmentManager b2 = this.p.b();
            if (b2 != null && b2.popBackStackImmediate()) {
                return true;
            }
        }
        boolean a2 = a(this.w, this.x, str, i2, i3);
        if (a2) {
            this.c = true;
            try {
                b(this.w, this.x);
            } finally {
                x();
            }
        }
        e();
        A();
        return a2;
    }

    public int getBackStackEntryCount() {
        if (this.g != null) {
            return this.g.size();
        }
        return 0;
    }

    public BackStackEntry getBackStackEntryAt(int i2) {
        return (BackStackEntry) this.g.get(i2);
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {
        if (this.k == null) {
            this.k = new ArrayList<>();
        }
        this.k.add(onBackStackChangedListener);
    }

    public void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {
        if (this.k != null) {
            this.k.remove(onBackStackChangedListener);
        }
    }

    public void putFragment(Bundle bundle, String str, Fragment fragment) {
        if (fragment.n < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(fragment);
            sb.append(" is not currently in the FragmentManager");
            a((RuntimeException) new IllegalStateException(sb.toString()));
        }
        bundle.putInt(str, fragment.n);
    }

    public Fragment getFragment(Bundle bundle, String str) {
        int i2 = bundle.getInt(str, -1);
        if (i2 == -1) {
            return null;
        }
        Fragment fragment = (Fragment) this.f.get(i2);
        if (fragment == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment no longer exists for key ");
            sb.append(str);
            sb.append(": index ");
            sb.append(i2);
            a((RuntimeException) new IllegalStateException(sb.toString()));
        }
        return fragment;
    }

    public List<Fragment> getFragments() {
        List<Fragment> list;
        if (this.e.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        synchronized (this.e) {
            list = (List) this.e.clone();
        }
        return list;
    }

    /* access modifiers changed from: 0000 */
    public List<Fragment> a() {
        if (this.f == null) {
            return null;
        }
        int size = this.f.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(this.f.valueAt(i2));
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        if (this.f == null) {
            return 0;
        }
        return this.f.size();
    }

    public SavedState saveFragmentInstanceState(Fragment fragment) {
        if (fragment.n < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment ");
            sb.append(fragment);
            sb.append(" is not currently in the FragmentManager");
            a((RuntimeException) new IllegalStateException(sb.toString()));
        }
        SavedState savedState = null;
        if (fragment.k <= 0) {
            return null;
        }
        Bundle n2 = n(fragment);
        if (n2 != null) {
            savedState = new SavedState(n2);
        }
        return savedState;
    }

    public boolean isDestroyed() {
        return this.t;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.o != null) {
            DebugUtils.buildShortClassTag(this.o, sb);
        } else {
            DebugUtils.buildShortClassTag(this.m, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("    ");
        String sb2 = sb.toString();
        if (this.f != null) {
            int size = this.f.size();
            if (size > 0) {
                printWriter.print(str);
                printWriter.print("Active Fragments in ");
                printWriter.print(Integer.toHexString(System.identityHashCode(this)));
                printWriter.println(":");
                for (int i2 = 0; i2 < size; i2++) {
                    Fragment fragment = (Fragment) this.f.valueAt(i2);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i2);
                    printWriter.print(": ");
                    printWriter.println(fragment);
                    if (fragment != null) {
                        fragment.dump(sb2, fileDescriptor, printWriter, strArr);
                    }
                }
            }
        }
        int size2 = this.e.size();
        if (size2 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i3 = 0; i3 < size2; i3++) {
                Fragment fragment2 = (Fragment) this.e.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(fragment2.toString());
            }
        }
        if (this.h != null) {
            int size3 = this.h.size();
            if (size3 > 0) {
                printWriter.print(str);
                printWriter.println("Fragments Created Menus:");
                for (int i4 = 0; i4 < size3; i4++) {
                    Fragment fragment3 = (Fragment) this.h.get(i4);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i4);
                    printWriter.print(": ");
                    printWriter.println(fragment3.toString());
                }
            }
        }
        if (this.g != null) {
            int size4 = this.g.size();
            if (size4 > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack:");
                for (int i5 = 0; i5 < size4; i5++) {
                    BackStackRecord backStackRecord = (BackStackRecord) this.g.get(i5);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i5);
                    printWriter.print(": ");
                    printWriter.println(backStackRecord.toString());
                    backStackRecord.a(sb2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        synchronized (this) {
            if (this.i != null) {
                int size5 = this.i.size();
                if (size5 > 0) {
                    printWriter.print(str);
                    printWriter.println("Back Stack Indices:");
                    for (int i6 = 0; i6 < size5; i6++) {
                        BackStackRecord backStackRecord2 = (BackStackRecord) this.i.get(i6);
                        printWriter.print(str);
                        printWriter.print("  #");
                        printWriter.print(i6);
                        printWriter.print(": ");
                        printWriter.println(backStackRecord2);
                    }
                }
            }
            if (this.j != null && this.j.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.j.toArray()));
            }
        }
        if (this.b != null) {
            int size6 = this.b.size();
            if (size6 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i7 = 0; i7 < size6; i7++) {
                    OpGenerator opGenerator = (OpGenerator) this.b.get(i7);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i7);
                    printWriter.print(": ");
                    printWriter.println(opGenerator);
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.m);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.n);
        if (this.o != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.o);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.l);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.s);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.t);
        if (this.r) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.r);
        }
        if (this.u != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.u);
        }
    }

    static AnimationOrAnimator a(Context context, float f2, float f3, float f4, float f5) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f3, f2, f3, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(E);
        scaleAnimation.setDuration(220);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f4, f5);
        alphaAnimation.setInterpolator(F);
        alphaAnimation.setDuration(220);
        animationSet.addAnimation(alphaAnimation);
        return new AnimationOrAnimator((Animation) animationSet);
    }

    static AnimationOrAnimator a(Context context, float f2, float f3) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f3);
        alphaAnimation.setInterpolator(F);
        alphaAnimation.setDuration(220);
        return new AnimationOrAnimator((Animation) alphaAnimation);
    }

    /* access modifiers changed from: 0000 */
    public AnimationOrAnimator a(Fragment fragment, int i2, boolean z2, int i3) {
        int o2 = fragment.o();
        Animation onCreateAnimation = fragment.onCreateAnimation(i2, z2, o2);
        if (onCreateAnimation != null) {
            return new AnimationOrAnimator(onCreateAnimation);
        }
        Animator onCreateAnimator = fragment.onCreateAnimator(i2, z2, o2);
        if (onCreateAnimator != null) {
            return new AnimationOrAnimator(onCreateAnimator);
        }
        if (o2 != 0) {
            boolean equals = "anim".equals(this.m.c().getResources().getResourceTypeName(o2));
            boolean z3 = false;
            if (equals) {
                try {
                    Animation loadAnimation = AnimationUtils.loadAnimation(this.m.c(), o2);
                    if (loadAnimation != null) {
                        return new AnimationOrAnimator(loadAnimation);
                    }
                    z3 = true;
                } catch (NotFoundException e2) {
                    throw e2;
                } catch (RuntimeException unused) {
                }
            }
            if (!z3) {
                try {
                    Animator loadAnimator = AnimatorInflater.loadAnimator(this.m.c(), o2);
                    if (loadAnimator != null) {
                        return new AnimationOrAnimator(loadAnimator);
                    }
                } catch (RuntimeException e3) {
                    if (equals) {
                        throw e3;
                    }
                    Animation loadAnimation2 = AnimationUtils.loadAnimation(this.m.c(), o2);
                    if (loadAnimation2 != null) {
                        return new AnimationOrAnimator(loadAnimation2);
                    }
                }
            }
        }
        if (i2 == 0) {
            return null;
        }
        int b2 = b(i2, z2);
        if (b2 < 0) {
            return null;
        }
        switch (b2) {
            case 1:
                return a(this.m.c(), 1.125f, 1.0f, (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
            case 2:
                return a(this.m.c(), 1.0f, 0.975f, 1.0f, (float) BitmapDescriptorFactory.HUE_RED);
            case 3:
                return a(this.m.c(), 0.975f, 1.0f, (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
            case 4:
                return a(this.m.c(), 1.0f, 1.075f, 1.0f, (float) BitmapDescriptorFactory.HUE_RED);
            case 5:
                return a(this.m.c(), (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
            case 6:
                return a(this.m.c(), 1.0f, (float) BitmapDescriptorFactory.HUE_RED);
            default:
                if (i3 == 0 && this.m.onHasWindowAnimations()) {
                    i3 = this.m.onGetWindowAnimations();
                }
                return i3 == 0 ? null : null;
        }
    }

    public void a(Fragment fragment) {
        if (fragment.T) {
            if (this.c) {
                this.v = true;
                return;
            }
            fragment.T = false;
            a(fragment, this.l, 0, 0, false);
        }
    }

    private static void b(View view, AnimationOrAnimator animationOrAnimator) {
        if (!(view == null || animationOrAnimator == null || !a(view, animationOrAnimator))) {
            if (animationOrAnimator.b != null) {
                animationOrAnimator.b.addListener(new AnimatorOnHWLayerIfNeededListener(view));
            } else {
                AnimationListener a2 = a(animationOrAnimator.a);
                view.setLayerType(2, null);
                animationOrAnimator.a.setAnimationListener(new AnimateOnHWLayerIfNeededListener(view, a2));
            }
        }
    }

    private static AnimationListener a(Animation animation) {
        try {
            if (q == null) {
                q = Animation.class.getDeclaredField("mListener");
                q.setAccessible(true);
            }
            return (AnimationListener) q.get(animation);
        } catch (NoSuchFieldException e2) {
            Log.e("FragmentManager", "No field with the name mListener is found in Animation class", e2);
            return null;
        } catch (IllegalAccessException e3) {
            Log.e("FragmentManager", "Cannot access Animation's mListener field", e3);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i2) {
        return this.l >= i2;
    }

    /* JADX WARNING: type inference failed for: r8v0 */
    /* JADX WARNING: type inference failed for: r8v1, types: [int] */
    /* JADX WARNING: type inference failed for: r11v1 */
    /* JADX WARNING: type inference failed for: r8v2 */
    /* JADX WARNING: type inference failed for: r11v2 */
    /* JADX WARNING: type inference failed for: r11v3 */
    /* JADX WARNING: type inference failed for: r11v4 */
    /* JADX WARNING: type inference failed for: r11v5 */
    /* JADX WARNING: type inference failed for: r8v3, types: [boolean] */
    /* JADX WARNING: type inference failed for: r8v4 */
    /* JADX WARNING: type inference failed for: r11v6 */
    /* JADX WARNING: type inference failed for: r11v7 */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r11v10 */
    /* JADX WARNING: type inference failed for: r11v11 */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0234, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0316, code lost:
        if (r11 >= 4) goto L_0x0338;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x031a, code lost:
        if (a == false) goto L_0x0332;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x031c, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append("movefrom STARTED: ");
        r1.append(r7);
        android.util.Log.v("FragmentManager", r1.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0332, code lost:
        r16.j();
        e(r7, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0338, code lost:
        if (r11 >= 3) goto L_0x0357;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x033c, code lost:
        if (a == false) goto L_0x0354;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x033e, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append("movefrom STOPPED: ");
        r1.append(r7);
        android.util.Log.v("FragmentManager", r1.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0354, code lost:
        r16.k();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0357, code lost:
        if (r11 >= 2) goto L_0x03d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x035b, code lost:
        if (a == false) goto L_0x0373;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x035d, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append("movefrom ACTIVITY_CREATED: ");
        r1.append(r7);
        android.util.Log.v("FragmentManager", r1.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x0375, code lost:
        if (r7.R == null) goto L_0x0386;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x037d, code lost:
        if (r6.m.onShouldSaveFragmentState(r7) == false) goto L_0x0386;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x0381, code lost:
        if (r7.m != null) goto L_0x0386;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0383, code lost:
        m(r16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x0386, code lost:
        r16.l();
        f(r7, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x038e, code lost:
        if (r7.R == null) goto L_0x03cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0392, code lost:
        if (r7.Q == null) goto L_0x03cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x0394, code lost:
        r7.Q.endViewTransition(r7.R);
        r7.R.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x03a3, code lost:
        if (r6.l <= 0) goto L_0x03c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x03a7, code lost:
        if (r6.t != false) goto L_0x03c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x03af, code lost:
        if (r7.R.getVisibility() != 0) goto L_0x03c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x03b5, code lost:
        if (r7.Z < com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED) goto L_0x03c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x03b7, code lost:
        r0 = a(r7, r18, false, r19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x03c0, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x03c1, code lost:
        r7.Z = com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x03c3, code lost:
        if (r0 == null) goto L_0x03c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x03c5, code lost:
        a(r7, r0, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x03c8, code lost:
        r7.Q.removeView(r7.R);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x03cf, code lost:
        r7.Q = null;
        r7.R = null;
        r7.S = null;
        r7.w = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x03d7, code lost:
        if (r11 >= 1) goto L_0x044d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x03db, code lost:
        if (r6.t == false) goto L_0x03fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x03e1, code lost:
        if (r16.t() == null) goto L_0x03ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x03e3, code lost:
        r0 = r16.t();
        r7.a((android.view.View) null);
        r0.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x03f2, code lost:
        if (r16.u() == null) goto L_0x03fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x03f4, code lost:
        r0 = r16.u();
        r7.a((android.animation.Animator) null);
        r0.cancel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x0402, code lost:
        if (r16.t() != null) goto L_0x0449;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x0408, code lost:
        if (r16.u() == null) goto L_0x040b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x040d, code lost:
        if (a == false) goto L_0x0425;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x040f, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append("movefrom CREATED: ");
        r1.append(r7);
        android.util.Log.v("FragmentManager", r1.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x0427, code lost:
        if (r7.M != false) goto L_0x0430;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x0429, code lost:
        r16.m();
        g(r7, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x0430, code lost:
        r7.k = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x0432, code lost:
        r16.n();
        h(r7, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x0438, code lost:
        if (r20 != false) goto L_0x044d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x043c, code lost:
        if (r7.M != false) goto L_0x0442;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x043e, code lost:
        g(r16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:207:0x0442, code lost:
        r7.B = null;
        r7.F = null;
        r7.A = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x0449, code lost:
        r7.b(r11);
        r8 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x019d, code lost:
        c(r16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01a0, code lost:
        if (r11 <= 1) goto L_0x029a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01a4, code lost:
        if (a == false) goto L_0x01bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01a6, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append("moveto ACTIVITY_CREATED: ");
        r1.append(r7);
        android.util.Log.v("FragmentManager", r1.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01be, code lost:
        if (r7.v != false) goto L_0x0285;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01c2, code lost:
        if (r7.H == 0) goto L_0x0234;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01c7, code lost:
        if (r7.H != -1) goto L_0x01e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01c9, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append("Cannot create fragment ");
        r1.append(r7);
        r1.append(" for a container view with no id");
        a((java.lang.RuntimeException) new java.lang.IllegalArgumentException(r1.toString()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01e7, code lost:
        r0 = (android.view.ViewGroup) r6.n.onFindViewById(r7.H);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01f1, code lost:
        if (r0 != null) goto L_0x0235;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01f5, code lost:
        if (r7.x != false) goto L_0x0235;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        r1 = r16.getResources().getResourceName(r7.H);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0202, code lost:
        r1 = android.support.v4.os.EnvironmentCompat.MEDIA_UNKNOWN;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r11v6
      assigns: []
      uses: []
      mth insns count: 446
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x0452  */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.support.v4.app.Fragment r16, int r17, int r18, int r19, boolean r20) {
        /*
            r15 = this;
            r6 = r15
            r7 = r16
            boolean r0 = r7.t
            r8 = 1
            if (r0 == 0) goto L_0x0010
            boolean r0 = r7.K
            if (r0 == 0) goto L_0x000d
            goto L_0x0010
        L_0x000d:
            r0 = r17
            goto L_0x0015
        L_0x0010:
            r0 = r17
            if (r0 <= r8) goto L_0x0015
            r0 = 1
        L_0x0015:
            boolean r1 = r7.u
            if (r1 == 0) goto L_0x002b
            int r1 = r7.k
            if (r0 <= r1) goto L_0x002b
            int r0 = r7.k
            if (r0 != 0) goto L_0x0029
            boolean r0 = r16.a()
            if (r0 == 0) goto L_0x0029
            r0 = 1
            goto L_0x002b
        L_0x0029:
            int r0 = r7.k
        L_0x002b:
            boolean r1 = r7.T
            r9 = 4
            r10 = 3
            if (r1 == 0) goto L_0x0039
            int r1 = r7.k
            if (r1 >= r9) goto L_0x0039
            if (r0 <= r10) goto L_0x0039
            r11 = 3
            goto L_0x003a
        L_0x0039:
            r11 = r0
        L_0x003a:
            int r0 = r7.k
            r12 = 2
            r13 = 0
            r14 = 0
            if (r0 > r11) goto L_0x02e8
            boolean r0 = r7.v
            if (r0 == 0) goto L_0x004a
            boolean r0 = r7.w
            if (r0 != 0) goto L_0x004a
            return
        L_0x004a:
            android.view.View r0 = r16.t()
            if (r0 != 0) goto L_0x0056
            android.animation.Animator r0 = r16.u()
            if (r0 == 0) goto L_0x0068
        L_0x0056:
            r7.a(r13)
            r7.a(r13)
            int r2 = r16.v()
            r3 = 0
            r4 = 0
            r5 = 1
            r0 = r6
            r1 = r7
            r0.a(r1, r2, r3, r4, r5)
        L_0x0068:
            int r0 = r7.k
            switch(r0) {
                case 0: goto L_0x006f;
                case 1: goto L_0x019d;
                case 2: goto L_0x029a;
                case 3: goto L_0x029e;
                case 4: goto L_0x02c0;
                default: goto L_0x006d;
            }
        L_0x006d:
            goto L_0x044d
        L_0x006f:
            if (r11 <= 0) goto L_0x019d
            boolean r0 = a
            if (r0 == 0) goto L_0x008b
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto CREATED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x008b:
            android.os.Bundle r0 = r7.l
            if (r0 == 0) goto L_0x00d3
            android.os.Bundle r0 = r7.l
            android.support.v4.app.FragmentHostCallback r1 = r6.m
            android.content.Context r1 = r1.c()
            java.lang.ClassLoader r1 = r1.getClassLoader()
            r0.setClassLoader(r1)
            android.os.Bundle r0 = r7.l
            java.lang.String r1 = "android:view_state"
            android.util.SparseArray r0 = r0.getSparseParcelableArray(r1)
            r7.m = r0
            android.os.Bundle r0 = r7.l
            java.lang.String r1 = "android:target_state"
            android.support.v4.app.Fragment r0 = r6.getFragment(r0, r1)
            r7.q = r0
            android.support.v4.app.Fragment r0 = r7.q
            if (r0 == 0) goto L_0x00c0
            android.os.Bundle r0 = r7.l
            java.lang.String r1 = "android:target_req_state"
            int r0 = r0.getInt(r1, r14)
            r7.s = r0
        L_0x00c0:
            android.os.Bundle r0 = r7.l
            java.lang.String r1 = "android:user_visible_hint"
            boolean r0 = r0.getBoolean(r1, r8)
            r7.U = r0
            boolean r0 = r7.U
            if (r0 != 0) goto L_0x00d3
            r7.T = r8
            if (r11 <= r10) goto L_0x00d3
            r11 = 3
        L_0x00d3:
            android.support.v4.app.FragmentHostCallback r0 = r6.m
            r7.B = r0
            android.support.v4.app.Fragment r0 = r6.o
            r7.F = r0
            android.support.v4.app.Fragment r0 = r6.o
            if (r0 == 0) goto L_0x00e4
            android.support.v4.app.Fragment r0 = r6.o
            android.support.v4.app.FragmentManagerImpl r0 = r0.C
            goto L_0x00ea
        L_0x00e4:
            android.support.v4.app.FragmentHostCallback r0 = r6.m
            android.support.v4.app.FragmentManagerImpl r0 = r0.e()
        L_0x00ea:
            r7.A = r0
            android.support.v4.app.Fragment r0 = r7.q
            if (r0 == 0) goto L_0x0134
            android.util.SparseArray<android.support.v4.app.Fragment> r0 = r6.f
            android.support.v4.app.Fragment r1 = r7.q
            int r1 = r1.n
            java.lang.Object r0 = r0.get(r1)
            android.support.v4.app.Fragment r1 = r7.q
            if (r0 == r1) goto L_0x0124
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Fragment "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r2 = " declared target fragment "
            r1.append(r2)
            android.support.v4.app.Fragment r2 = r7.q
            r1.append(r2)
            java.lang.String r2 = " that does not belong to this FragmentManager!"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0124:
            android.support.v4.app.Fragment r0 = r7.q
            int r0 = r0.k
            if (r0 >= r8) goto L_0x0134
            android.support.v4.app.Fragment r1 = r7.q
            r2 = 1
            r3 = 0
            r4 = 0
            r5 = 1
            r0 = r6
            r0.a(r1, r2, r3, r4, r5)
        L_0x0134:
            android.support.v4.app.FragmentHostCallback r0 = r6.m
            android.content.Context r0 = r0.c()
            r6.a(r7, r0, r14)
            r7.P = r14
            android.support.v4.app.FragmentHostCallback r0 = r6.m
            android.content.Context r0 = r0.c()
            r7.onAttach(r0)
            boolean r0 = r7.P
            if (r0 != 0) goto L_0x0168
            android.support.v4.app.SuperNotCalledException r0 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Fragment "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r2 = " did not call through to super.onAttach()"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0168:
            android.support.v4.app.Fragment r0 = r7.F
            if (r0 != 0) goto L_0x0172
            android.support.v4.app.FragmentHostCallback r0 = r6.m
            r0.a(r7)
            goto L_0x0177
        L_0x0172:
            android.support.v4.app.Fragment r0 = r7.F
            r0.onAttachFragment(r7)
        L_0x0177:
            android.support.v4.app.FragmentHostCallback r0 = r6.m
            android.content.Context r0 = r0.c()
            r6.b(r7, r0, r14)
            boolean r0 = r7.ab
            if (r0 != 0) goto L_0x0194
            android.os.Bundle r0 = r7.l
            r6.a(r7, r0, r14)
            android.os.Bundle r0 = r7.l
            r7.d(r0)
            android.os.Bundle r0 = r7.l
            r6.b(r7, r0, r14)
            goto L_0x019b
        L_0x0194:
            android.os.Bundle r0 = r7.l
            r7.c(r0)
            r7.k = r8
        L_0x019b:
            r7.M = r14
        L_0x019d:
            r15.c(r16)
            if (r11 <= r8) goto L_0x029a
            boolean r0 = a
            if (r0 == 0) goto L_0x01bc
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto ACTIVITY_CREATED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x01bc:
            boolean r0 = r7.v
            if (r0 != 0) goto L_0x0285
            int r0 = r7.H
            if (r0 == 0) goto L_0x0234
            int r0 = r7.H
            r1 = -1
            if (r0 != r1) goto L_0x01e7
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Cannot create fragment "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r2 = " for a container view with no id"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            r6.a(r0)
        L_0x01e7:
            android.support.v4.app.FragmentContainer r0 = r6.n
            int r1 = r7.H
            android.view.View r0 = r0.onFindViewById(r1)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            if (r0 != 0) goto L_0x0235
            boolean r1 = r7.x
            if (r1 != 0) goto L_0x0235
            android.content.res.Resources r1 = r16.getResources()     // Catch:{ NotFoundException -> 0x0202 }
            int r2 = r7.H     // Catch:{ NotFoundException -> 0x0202 }
            java.lang.String r1 = r1.getResourceName(r2)     // Catch:{ NotFoundException -> 0x0202 }
            goto L_0x0204
        L_0x0202:
            java.lang.String r1 = "unknown"
        L_0x0204:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "No view found for id 0x"
            r3.append(r4)
            int r4 = r7.H
            java.lang.String r4 = java.lang.Integer.toHexString(r4)
            r3.append(r4)
            java.lang.String r4 = " ("
            r3.append(r4)
            r3.append(r1)
            java.lang.String r1 = ") for fragment "
            r3.append(r1)
            r3.append(r7)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            r6.a(r2)
            goto L_0x0235
        L_0x0234:
            r0 = r13
        L_0x0235:
            r7.Q = r0
            android.os.Bundle r1 = r7.l
            android.view.LayoutInflater r1 = r7.b(r1)
            android.os.Bundle r2 = r7.l
            android.view.View r1 = r7.a(r1, r0, r2)
            r7.R = r1
            android.view.View r1 = r7.R
            if (r1 == 0) goto L_0x0283
            android.view.View r1 = r7.R
            r7.S = r1
            android.view.View r1 = r7.R
            r1.setSaveFromParentEnabled(r14)
            if (r0 == 0) goto L_0x0259
            android.view.View r1 = r7.R
            r0.addView(r1)
        L_0x0259:
            boolean r0 = r7.J
            if (r0 == 0) goto L_0x0264
            android.view.View r0 = r7.R
            r1 = 8
            r0.setVisibility(r1)
        L_0x0264:
            android.view.View r0 = r7.R
            android.os.Bundle r1 = r7.l
            r7.onViewCreated(r0, r1)
            android.view.View r0 = r7.R
            android.os.Bundle r1 = r7.l
            r6.a(r7, r0, r1, r14)
            android.view.View r0 = r7.R
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x027f
            android.view.ViewGroup r0 = r7.Q
            if (r0 == 0) goto L_0x027f
            goto L_0x0280
        L_0x027f:
            r8 = 0
        L_0x0280:
            r7.X = r8
            goto L_0x0285
        L_0x0283:
            r7.S = r13
        L_0x0285:
            android.os.Bundle r0 = r7.l
            r7.e(r0)
            android.os.Bundle r0 = r7.l
            r6.c(r7, r0, r14)
            android.view.View r0 = r7.R
            if (r0 == 0) goto L_0x0298
            android.os.Bundle r0 = r7.l
            r7.a(r0)
        L_0x0298:
            r7.l = r13
        L_0x029a:
            if (r11 <= r12) goto L_0x029e
            r7.k = r10
        L_0x029e:
            if (r11 <= r10) goto L_0x02c0
            boolean r0 = a
            if (r0 == 0) goto L_0x02ba
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto STARTED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x02ba:
            r16.e()
            r6.b(r7, r14)
        L_0x02c0:
            if (r11 <= r9) goto L_0x044d
            boolean r0 = a
            if (r0 == 0) goto L_0x02dc
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto RESUMED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x02dc:
            r16.f()
            r6.c(r7, r14)
            r7.l = r13
            r7.m = r13
            goto L_0x044d
        L_0x02e8:
            int r0 = r7.k
            if (r0 <= r11) goto L_0x044d
            int r0 = r7.k
            switch(r0) {
                case 1: goto L_0x03d7;
                case 2: goto L_0x0357;
                case 3: goto L_0x0338;
                case 4: goto L_0x0316;
                case 5: goto L_0x02f3;
                default: goto L_0x02f1;
            }
        L_0x02f1:
            goto L_0x044d
        L_0x02f3:
            r0 = 5
            if (r11 >= r0) goto L_0x0316
            boolean r0 = a
            if (r0 == 0) goto L_0x0310
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom RESUMED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0310:
            r16.i()
            r6.d(r7, r14)
        L_0x0316:
            if (r11 >= r9) goto L_0x0338
            boolean r0 = a
            if (r0 == 0) goto L_0x0332
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom STARTED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0332:
            r16.j()
            r6.e(r7, r14)
        L_0x0338:
            if (r11 >= r10) goto L_0x0357
            boolean r0 = a
            if (r0 == 0) goto L_0x0354
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom STOPPED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0354:
            r16.k()
        L_0x0357:
            if (r11 >= r12) goto L_0x03d7
            boolean r0 = a
            if (r0 == 0) goto L_0x0373
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom ACTIVITY_CREATED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0373:
            android.view.View r0 = r7.R
            if (r0 == 0) goto L_0x0386
            android.support.v4.app.FragmentHostCallback r0 = r6.m
            boolean r0 = r0.onShouldSaveFragmentState(r7)
            if (r0 == 0) goto L_0x0386
            android.util.SparseArray<android.os.Parcelable> r0 = r7.m
            if (r0 != 0) goto L_0x0386
            r15.m(r16)
        L_0x0386:
            r16.l()
            r6.f(r7, r14)
            android.view.View r0 = r7.R
            if (r0 == 0) goto L_0x03cf
            android.view.ViewGroup r0 = r7.Q
            if (r0 == 0) goto L_0x03cf
            android.view.ViewGroup r0 = r7.Q
            android.view.View r1 = r7.R
            r0.endViewTransition(r1)
            android.view.View r0 = r7.R
            r0.clearAnimation()
            int r0 = r6.l
            r1 = 0
            if (r0 <= 0) goto L_0x03c0
            boolean r0 = r6.t
            if (r0 != 0) goto L_0x03c0
            android.view.View r0 = r7.R
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x03c0
            float r0 = r7.Z
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 < 0) goto L_0x03c0
            r0 = r18
            r2 = r19
            android.support.v4.app.FragmentManagerImpl$AnimationOrAnimator r0 = r6.a(r7, r0, r14, r2)
            goto L_0x03c1
        L_0x03c0:
            r0 = r13
        L_0x03c1:
            r7.Z = r1
            if (r0 == 0) goto L_0x03c8
            r6.a(r7, r0, r11)
        L_0x03c8:
            android.view.ViewGroup r0 = r7.Q
            android.view.View r1 = r7.R
            r0.removeView(r1)
        L_0x03cf:
            r7.Q = r13
            r7.R = r13
            r7.S = r13
            r7.w = r14
        L_0x03d7:
            if (r11 >= r8) goto L_0x044d
            boolean r0 = r6.t
            if (r0 == 0) goto L_0x03fe
            android.view.View r0 = r16.t()
            if (r0 == 0) goto L_0x03ee
            android.view.View r0 = r16.t()
            r7.a(r13)
            r0.clearAnimation()
            goto L_0x03fe
        L_0x03ee:
            android.animation.Animator r0 = r16.u()
            if (r0 == 0) goto L_0x03fe
            android.animation.Animator r0 = r16.u()
            r7.a(r13)
            r0.cancel()
        L_0x03fe:
            android.view.View r0 = r16.t()
            if (r0 != 0) goto L_0x0449
            android.animation.Animator r0 = r16.u()
            if (r0 == 0) goto L_0x040b
            goto L_0x0449
        L_0x040b:
            boolean r0 = a
            if (r0 == 0) goto L_0x0425
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom CREATED: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0425:
            boolean r0 = r7.M
            if (r0 != 0) goto L_0x0430
            r16.m()
            r6.g(r7, r14)
            goto L_0x0432
        L_0x0430:
            r7.k = r14
        L_0x0432:
            r16.n()
            r6.h(r7, r14)
            if (r20 != 0) goto L_0x044d
            boolean r0 = r7.M
            if (r0 != 0) goto L_0x0442
            r15.g(r16)
            goto L_0x044d
        L_0x0442:
            r7.B = r13
            r7.F = r13
            r7.A = r13
            goto L_0x044d
        L_0x0449:
            r7.b(r11)
            goto L_0x044e
        L_0x044d:
            r8 = r11
        L_0x044e:
            int r0 = r7.k
            if (r0 == r8) goto L_0x0481
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveToState: Fragment state for "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r2 = " not updated inline; "
            r1.append(r2)
            java.lang.String r2 = "expected state "
            r1.append(r2)
            r1.append(r8)
            java.lang.String r2 = " found "
            r1.append(r2)
            int r2 = r7.k
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.w(r0, r1)
            r7.k = r8
        L_0x0481:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.a(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    private void a(@NonNull final Fragment fragment, @NonNull AnimationOrAnimator animationOrAnimator, int i2) {
        final View view = fragment.R;
        final ViewGroup viewGroup = fragment.Q;
        viewGroup.startViewTransition(view);
        fragment.b(i2);
        if (animationOrAnimator.a != null) {
            EndViewTransitionAnimator endViewTransitionAnimator = new EndViewTransitionAnimator(animationOrAnimator.a, viewGroup, view);
            fragment.a(fragment.R);
            endViewTransitionAnimator.setAnimationListener(new AnimationListenerWrapper(a((Animation) endViewTransitionAnimator)) {
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    viewGroup.post(new Runnable() {
                        public void run() {
                            if (fragment.t() != null) {
                                fragment.a((View) null);
                                FragmentManagerImpl.this.a(fragment, fragment.v(), 0, 0, false);
                            }
                        }
                    });
                }
            });
            b(view, animationOrAnimator);
            fragment.R.startAnimation(endViewTransitionAnimator);
            return;
        }
        Animator animator = animationOrAnimator.b;
        fragment.a(animationOrAnimator.b);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                viewGroup.endViewTransition(view);
                Animator u = fragment.u();
                fragment.a((Animator) null);
                if (u != null && viewGroup.indexOfChild(view) < 0) {
                    FragmentManagerImpl.this.a(fragment, fragment.v(), 0, 0, false);
                }
            }
        });
        animator.setTarget(fragment.R);
        b(fragment.R, animationOrAnimator);
        animator.start();
    }

    /* access modifiers changed from: 0000 */
    public void b(Fragment fragment) {
        a(fragment, this.l, 0, 0, false);
    }

    /* access modifiers changed from: 0000 */
    public void c(Fragment fragment) {
        if (fragment.v && !fragment.y) {
            fragment.R = fragment.a(fragment.b(fragment.l), null, fragment.l);
            if (fragment.R != null) {
                fragment.S = fragment.R;
                fragment.R.setSaveFromParentEnabled(false);
                if (fragment.J) {
                    fragment.R.setVisibility(8);
                }
                fragment.onViewCreated(fragment.R, fragment.l);
                a(fragment, fragment.R, fragment.l, false);
                return;
            }
            fragment.S = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void d(final Fragment fragment) {
        if (fragment.R != null) {
            AnimationOrAnimator a2 = a(fragment, fragment.p(), !fragment.J, fragment.q());
            if (a2 == null || a2.b == null) {
                if (a2 != null) {
                    b(fragment.R, a2);
                    fragment.R.startAnimation(a2.a);
                    a2.a.start();
                }
                fragment.R.setVisibility((!fragment.J || fragment.x()) ? 0 : 8);
                if (fragment.x()) {
                    fragment.d(false);
                }
            } else {
                a2.b.setTarget(fragment.R);
                if (!fragment.J) {
                    fragment.R.setVisibility(0);
                } else if (fragment.x()) {
                    fragment.d(false);
                } else {
                    final ViewGroup viewGroup = fragment.Q;
                    final View view = fragment.R;
                    viewGroup.startViewTransition(view);
                    a2.b.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            viewGroup.endViewTransition(view);
                            animator.removeListener(this);
                            if (fragment.R != null) {
                                fragment.R.setVisibility(8);
                            }
                        }
                    });
                }
                b(fragment.R, a2);
                a2.b.start();
            }
        }
        if (fragment.t && fragment.N && fragment.O) {
            this.r = true;
        }
        fragment.Y = false;
        fragment.onHiddenChanged(fragment.J);
    }

    /* access modifiers changed from: 0000 */
    public void e(Fragment fragment) {
        if (fragment != null) {
            int i2 = this.l;
            if (fragment.u) {
                if (fragment.a()) {
                    i2 = Math.min(i2, 1);
                } else {
                    i2 = Math.min(i2, 0);
                }
            }
            a(fragment, i2, fragment.p(), fragment.q(), false);
            if (fragment.R != null) {
                Fragment p2 = p(fragment);
                if (p2 != null) {
                    View view = p2.R;
                    ViewGroup viewGroup = fragment.Q;
                    int indexOfChild = viewGroup.indexOfChild(view);
                    int indexOfChild2 = viewGroup.indexOfChild(fragment.R);
                    if (indexOfChild2 < indexOfChild) {
                        viewGroup.removeViewAt(indexOfChild2);
                        viewGroup.addView(fragment.R, indexOfChild);
                    }
                }
                if (fragment.X && fragment.Q != null) {
                    if (fragment.Z > BitmapDescriptorFactory.HUE_RED) {
                        fragment.R.setAlpha(fragment.Z);
                    }
                    fragment.Z = BitmapDescriptorFactory.HUE_RED;
                    fragment.X = false;
                    AnimationOrAnimator a2 = a(fragment, fragment.p(), true, fragment.q());
                    if (a2 != null) {
                        b(fragment.R, a2);
                        if (a2.a != null) {
                            fragment.R.startAnimation(a2.a);
                        } else {
                            a2.b.setTarget(fragment.R);
                            a2.b.start();
                        }
                    }
                }
            }
            if (fragment.Y) {
                d(fragment);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, boolean z2) {
        if (this.m == null && i2 != 0) {
            throw new IllegalStateException("No activity");
        } else if (z2 || i2 != this.l) {
            this.l = i2;
            if (this.f != null) {
                int size = this.e.size();
                boolean z3 = false;
                for (int i3 = 0; i3 < size; i3++) {
                    Fragment fragment = (Fragment) this.e.get(i3);
                    e(fragment);
                    if (fragment.V != null) {
                        z3 |= fragment.V.hasRunningLoaders();
                    }
                }
                int size2 = this.f.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    Fragment fragment2 = (Fragment) this.f.valueAt(i4);
                    if (fragment2 != null && ((fragment2.u || fragment2.K) && !fragment2.X)) {
                        e(fragment2);
                        if (fragment2.V != null) {
                            z3 |= fragment2.V.hasRunningLoaders();
                        }
                    }
                }
                if (!z3) {
                    c();
                }
                if (this.r && this.m != null && this.l == 5) {
                    this.m.onSupportInvalidateOptionsMenu();
                    this.r = false;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (this.f != null) {
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                Fragment fragment = (Fragment) this.f.valueAt(i2);
                if (fragment != null) {
                    a(fragment);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void f(Fragment fragment) {
        if (fragment.n < 0) {
            int i2 = this.d;
            this.d = i2 + 1;
            fragment.a(i2, this.o);
            if (this.f == null) {
                this.f = new SparseArray<>();
            }
            this.f.put(fragment.n, fragment);
            if (a) {
                StringBuilder sb = new StringBuilder();
                sb.append("Allocated fragment index ");
                sb.append(fragment);
                Log.v("FragmentManager", sb.toString());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void g(Fragment fragment) {
        if (fragment.n >= 0) {
            if (a) {
                StringBuilder sb = new StringBuilder();
                sb.append("Freeing fragment index ");
                sb.append(fragment);
                Log.v("FragmentManager", sb.toString());
            }
            this.f.put(fragment.n, null);
            fragment.c();
        }
    }

    public void a(Fragment fragment, boolean z2) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append("add: ");
            sb.append(fragment);
            Log.v("FragmentManager", sb.toString());
        }
        f(fragment);
        if (fragment.K) {
            return;
        }
        if (this.e.contains(fragment)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Fragment already added: ");
            sb2.append(fragment);
            throw new IllegalStateException(sb2.toString());
        }
        synchronized (this.e) {
            this.e.add(fragment);
        }
        fragment.t = true;
        fragment.u = false;
        if (fragment.R == null) {
            fragment.Y = false;
        }
        if (fragment.N && fragment.O) {
            this.r = true;
        }
        if (z2) {
            b(fragment);
        }
    }

    public void h(Fragment fragment) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append("remove: ");
            sb.append(fragment);
            sb.append(" nesting=");
            sb.append(fragment.z);
            Log.v("FragmentManager", sb.toString());
        }
        boolean z2 = !fragment.a();
        if (!fragment.K || z2) {
            synchronized (this.e) {
                this.e.remove(fragment);
            }
            if (fragment.N && fragment.O) {
                this.r = true;
            }
            fragment.t = false;
            fragment.u = true;
        }
    }

    public void i(Fragment fragment) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append("hide: ");
            sb.append(fragment);
            Log.v("FragmentManager", sb.toString());
        }
        if (!fragment.J) {
            fragment.J = true;
            fragment.Y = true ^ fragment.Y;
        }
    }

    public void j(Fragment fragment) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append("show: ");
            sb.append(fragment);
            Log.v("FragmentManager", sb.toString());
        }
        if (fragment.J) {
            fragment.J = false;
            fragment.Y = !fragment.Y;
        }
    }

    public void k(Fragment fragment) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append("detach: ");
            sb.append(fragment);
            Log.v("FragmentManager", sb.toString());
        }
        if (!fragment.K) {
            fragment.K = true;
            if (fragment.t) {
                if (a) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("remove from detach: ");
                    sb2.append(fragment);
                    Log.v("FragmentManager", sb2.toString());
                }
                synchronized (this.e) {
                    this.e.remove(fragment);
                }
                if (fragment.N && fragment.O) {
                    this.r = true;
                }
                fragment.t = false;
            }
        }
    }

    public void l(Fragment fragment) {
        if (a) {
            StringBuilder sb = new StringBuilder();
            sb.append("attach: ");
            sb.append(fragment);
            Log.v("FragmentManager", sb.toString());
        }
        if (fragment.K) {
            fragment.K = false;
            if (fragment.t) {
                return;
            }
            if (this.e.contains(fragment)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Fragment already added: ");
                sb2.append(fragment);
                throw new IllegalStateException(sb2.toString());
            }
            if (a) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("add from attach: ");
                sb3.append(fragment);
                Log.v("FragmentManager", sb3.toString());
            }
            synchronized (this.e) {
                this.e.add(fragment);
            }
            fragment.t = true;
            if (fragment.N && fragment.O) {
                this.r = true;
            }
        }
    }

    public Fragment findFragmentById(int i2) {
        for (int size = this.e.size() - 1; size >= 0; size--) {
            Fragment fragment = (Fragment) this.e.get(size);
            if (fragment != null && fragment.G == i2) {
                return fragment;
            }
        }
        if (this.f != null) {
            for (int size2 = this.f.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = (Fragment) this.f.valueAt(size2);
                if (fragment2 != null && fragment2.G == i2) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String str) {
        if (str != null) {
            for (int size = this.e.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.e.get(size);
                if (fragment != null && str.equals(fragment.I)) {
                    return fragment;
                }
            }
        }
        if (!(this.f == null || str == null)) {
            for (int size2 = this.f.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = (Fragment) this.f.valueAt(size2);
                if (fragment2 != null && str.equals(fragment2.I)) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public Fragment a(String str) {
        if (!(this.f == null || str == null)) {
            for (int size = this.f.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.f.valueAt(size);
                if (fragment != null) {
                    Fragment a2 = fragment.a(str);
                    if (a2 != null) {
                        return a2;
                    }
                }
            }
        }
        return null;
    }

    private void v() {
        if (this.s) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.u != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Can not perform this action inside of ");
            sb.append(this.u);
            throw new IllegalStateException(sb.toString());
        }
    }

    public boolean isStateSaved() {
        return this.s;
    }

    public void a(OpGenerator opGenerator, boolean z2) {
        if (!z2) {
            v();
        }
        synchronized (this) {
            if (!this.t) {
                if (this.m != null) {
                    if (this.b == null) {
                        this.b = new ArrayList<>();
                    }
                    this.b.add(opGenerator);
                    w();
                    return;
                }
            }
            if (!z2) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    /* access modifiers changed from: private */
    public void w() {
        synchronized (this) {
            boolean z2 = false;
            boolean z3 = this.B != null && !this.B.isEmpty();
            if (this.b != null && this.b.size() == 1) {
                z2 = true;
            }
            if (z3 || z2) {
                this.m.d().removeCallbacks(this.D);
                this.m.d().post(this.D);
            }
        }
    }

    public int a(BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.j != null) {
                if (this.j.size() > 0) {
                    int intValue = ((Integer) this.j.remove(this.j.size() - 1)).intValue();
                    if (a) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Adding back stack index ");
                        sb.append(intValue);
                        sb.append(" with ");
                        sb.append(backStackRecord);
                        Log.v("FragmentManager", sb.toString());
                    }
                    this.i.set(intValue, backStackRecord);
                    return intValue;
                }
            }
            if (this.i == null) {
                this.i = new ArrayList<>();
            }
            int size = this.i.size();
            if (a) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Setting back stack index ");
                sb2.append(size);
                sb2.append(" to ");
                sb2.append(backStackRecord);
                Log.v("FragmentManager", sb2.toString());
            }
            this.i.add(backStackRecord);
            return size;
        }
    }

    public void a(int i2, BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.i == null) {
                this.i = new ArrayList<>();
            }
            int size = this.i.size();
            if (i2 < size) {
                if (a) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Setting back stack index ");
                    sb.append(i2);
                    sb.append(" to ");
                    sb.append(backStackRecord);
                    Log.v("FragmentManager", sb.toString());
                }
                this.i.set(i2, backStackRecord);
            } else {
                while (size < i2) {
                    this.i.add(null);
                    if (this.j == null) {
                        this.j = new ArrayList<>();
                    }
                    if (a) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Adding available back stack index ");
                        sb2.append(size);
                        Log.v("FragmentManager", sb2.toString());
                    }
                    this.j.add(Integer.valueOf(size));
                    size++;
                }
                if (a) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Adding back stack index ");
                    sb3.append(i2);
                    sb3.append(" with ");
                    sb3.append(backStackRecord);
                    Log.v("FragmentManager", sb3.toString());
                }
                this.i.add(backStackRecord);
            }
        }
    }

    public void b(int i2) {
        synchronized (this) {
            this.i.set(i2, null);
            if (this.j == null) {
                this.j = new ArrayList<>();
            }
            if (a) {
                StringBuilder sb = new StringBuilder();
                sb.append("Freeing back stack index ");
                sb.append(i2);
                Log.v("FragmentManager", sb.toString());
            }
            this.j.add(Integer.valueOf(i2));
        }
    }

    private void c(boolean z2) {
        if (this.c) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (this.m == null) {
            throw new IllegalStateException("Fragment host has been destroyed");
        } else if (Looper.myLooper() != this.m.d().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        } else {
            if (!z2) {
                v();
            }
            if (this.w == null) {
                this.w = new ArrayList<>();
                this.x = new ArrayList<>();
            }
            this.c = true;
            try {
                a(null, null);
            } finally {
                this.c = false;
            }
        }
    }

    public void b(OpGenerator opGenerator, boolean z2) {
        if (!z2 || (this.m != null && !this.t)) {
            c(z2);
            if (opGenerator.a(this.w, this.x)) {
                this.c = true;
                try {
                    b(this.w, this.x);
                } finally {
                    x();
                }
            }
            e();
            A();
        }
    }

    private void x() {
        this.c = false;
        this.x.clear();
        this.w.clear();
    }

    /* JADX INFO: finally extract failed */
    public boolean d() {
        c(true);
        boolean z2 = false;
        while (c(this.w, this.x)) {
            this.c = true;
            try {
                b(this.w, this.x);
                x();
                z2 = true;
            } catch (Throwable th) {
                x();
                throw th;
            }
        }
        e();
        A();
        return z2;
    }

    private void a(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        int size = this.B == null ? 0 : this.B.size();
        int i2 = 0;
        while (i2 < size) {
            StartEnterTransitionListener startEnterTransitionListener = (StartEnterTransitionListener) this.B.get(i2);
            if (arrayList != null && !startEnterTransitionListener.a) {
                int indexOf = arrayList.indexOf(startEnterTransitionListener.b);
                if (indexOf != -1 && ((Boolean) arrayList2.get(indexOf)).booleanValue()) {
                    startEnterTransitionListener.e();
                    i2++;
                }
            }
            if (startEnterTransitionListener.c() || (arrayList != null && startEnterTransitionListener.b.a(arrayList, 0, arrayList.size()))) {
                this.B.remove(i2);
                i2--;
                size--;
                if (arrayList != null && !startEnterTransitionListener.a) {
                    int indexOf2 = arrayList.indexOf(startEnterTransitionListener.b);
                    if (indexOf2 != -1 && ((Boolean) arrayList2.get(indexOf2)).booleanValue()) {
                        startEnterTransitionListener.e();
                    }
                }
                startEnterTransitionListener.d();
            }
            i2++;
        }
    }

    private void b(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (arrayList != null && !arrayList.isEmpty()) {
            if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
                throw new IllegalStateException("Internal error with the back stack records");
            }
            a(arrayList, arrayList2);
            int size = arrayList.size();
            int i2 = 0;
            int i3 = 0;
            while (i2 < size) {
                if (!((BackStackRecord) arrayList.get(i2)).t) {
                    if (i3 != i2) {
                        a(arrayList, arrayList2, i3, i2);
                    }
                    i3 = i2 + 1;
                    if (((Boolean) arrayList2.get(i2)).booleanValue()) {
                        while (i3 < size && ((Boolean) arrayList2.get(i3)).booleanValue() && !((BackStackRecord) arrayList.get(i3)).t) {
                            i3++;
                        }
                    }
                    a(arrayList, arrayList2, i2, i3);
                    i2 = i3 - 1;
                }
                i2++;
            }
            if (i3 != size) {
                a(arrayList, arrayList2, i3, size);
            }
        }
    }

    private void a(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        int i4;
        ArrayList<BackStackRecord> arrayList3 = arrayList;
        ArrayList<Boolean> arrayList4 = arrayList2;
        int i5 = i2;
        int i6 = i3;
        boolean z2 = ((BackStackRecord) arrayList3.get(i5)).t;
        if (this.y == null) {
            this.y = new ArrayList<>();
        } else {
            this.y.clear();
        }
        this.y.addAll(this.e);
        Fragment primaryNavigationFragment = getPrimaryNavigationFragment();
        boolean z3 = false;
        for (int i7 = i5; i7 < i6; i7++) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList3.get(i7);
            if (!((Boolean) arrayList4.get(i7)).booleanValue()) {
                primaryNavigationFragment = backStackRecord.a(this.y, primaryNavigationFragment);
            } else {
                primaryNavigationFragment = backStackRecord.b(this.y, primaryNavigationFragment);
            }
            z3 = z3 || backStackRecord.i;
        }
        this.y.clear();
        if (!z2) {
            FragmentTransition.a(this, arrayList3, arrayList4, i5, i6, false);
        }
        b(arrayList, arrayList2, i2, i3);
        if (z2) {
            ArraySet arraySet = new ArraySet();
            b(arraySet);
            int a2 = a(arrayList3, arrayList4, i5, i6, arraySet);
            a(arraySet);
            i4 = a2;
        } else {
            i4 = i6;
        }
        if (i4 != i5 && z2) {
            FragmentTransition.a(this, arrayList3, arrayList4, i5, i4, true);
            a(this.l, true);
        }
        while (i5 < i6) {
            BackStackRecord backStackRecord2 = (BackStackRecord) arrayList3.get(i5);
            if (((Boolean) arrayList4.get(i5)).booleanValue() && backStackRecord2.m >= 0) {
                b(backStackRecord2.m);
                backStackRecord2.m = -1;
            }
            backStackRecord2.a();
            i5++;
        }
        if (z3) {
            f();
        }
    }

    private void a(ArraySet<Fragment> arraySet) {
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            Fragment fragment = (Fragment) arraySet.valueAt(i2);
            if (!fragment.t) {
                View view = fragment.getView();
                fragment.Z = view.getAlpha();
                view.setAlpha(BitmapDescriptorFactory.HUE_RED);
            }
        }
    }

    private int a(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3, ArraySet<Fragment> arraySet) {
        int i4 = i3;
        for (int i5 = i3 - 1; i5 >= i2; i5--) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i5);
            boolean booleanValue = ((Boolean) arrayList2.get(i5)).booleanValue();
            if (backStackRecord.c() && !backStackRecord.a(arrayList, i5 + 1, i3)) {
                if (this.B == null) {
                    this.B = new ArrayList<>();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, booleanValue);
                this.B.add(startEnterTransitionListener);
                backStackRecord.a((OnStartEnterTransitionListener) startEnterTransitionListener);
                if (booleanValue) {
                    backStackRecord.b();
                } else {
                    backStackRecord.b(false);
                }
                i4--;
                if (i5 != i4) {
                    arrayList.remove(i5);
                    arrayList.add(i4, backStackRecord);
                }
                b(arraySet);
            }
        }
        return i4;
    }

    /* access modifiers changed from: private */
    public void a(BackStackRecord backStackRecord, boolean z2, boolean z3, boolean z4) {
        if (z2) {
            backStackRecord.b(z4);
        } else {
            backStackRecord.b();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(backStackRecord);
        arrayList2.add(Boolean.valueOf(z2));
        if (z3) {
            FragmentTransition.a(this, arrayList, arrayList2, 0, 1, true);
        }
        if (z4) {
            a(this.l, true);
        }
        if (this.f != null) {
            int size = this.f.size();
            for (int i2 = 0; i2 < size; i2++) {
                Fragment fragment = (Fragment) this.f.valueAt(i2);
                if (fragment != null && fragment.R != null && fragment.X && backStackRecord.b(fragment.H)) {
                    if (fragment.Z > BitmapDescriptorFactory.HUE_RED) {
                        fragment.R.setAlpha(fragment.Z);
                    }
                    if (z4) {
                        fragment.Z = BitmapDescriptorFactory.HUE_RED;
                    } else {
                        fragment.Z = -1.0f;
                        fragment.X = false;
                    }
                }
            }
        }
    }

    private Fragment p(Fragment fragment) {
        ViewGroup viewGroup = fragment.Q;
        View view = fragment.R;
        if (viewGroup == null || view == null) {
            return null;
        }
        for (int indexOf = this.e.indexOf(fragment) - 1; indexOf >= 0; indexOf--) {
            Fragment fragment2 = (Fragment) this.e.get(indexOf);
            if (fragment2.Q == viewGroup && fragment2.R != null) {
                return fragment2;
            }
        }
        return null;
    }

    private static void b(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        while (i2 < i3) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i2);
            boolean z2 = true;
            if (((Boolean) arrayList2.get(i2)).booleanValue()) {
                backStackRecord.a(-1);
                if (i2 != i3 - 1) {
                    z2 = false;
                }
                backStackRecord.b(z2);
            } else {
                backStackRecord.a(1);
                backStackRecord.b();
            }
            i2++;
        }
    }

    private void b(ArraySet<Fragment> arraySet) {
        if (this.l >= 1) {
            int min = Math.min(this.l, 4);
            int size = this.e.size();
            for (int i2 = 0; i2 < size; i2++) {
                Fragment fragment = (Fragment) this.e.get(i2);
                if (fragment.k < min) {
                    a(fragment, min, fragment.o(), fragment.p(), false);
                    if (fragment.R != null && !fragment.J && fragment.X) {
                        arraySet.add(fragment);
                    }
                }
            }
        }
    }

    private void y() {
        if (this.B != null) {
            while (!this.B.isEmpty()) {
                ((StartEnterTransitionListener) this.B.remove(0)).d();
            }
        }
    }

    private void z() {
        int size = this.f == null ? 0 : this.f.size();
        for (int i2 = 0; i2 < size; i2++) {
            Fragment fragment = (Fragment) this.f.valueAt(i2);
            if (fragment != null) {
                if (fragment.t() != null) {
                    int v2 = fragment.v();
                    View t2 = fragment.t();
                    Animation animation = t2.getAnimation();
                    if (animation != null) {
                        animation.cancel();
                        t2.clearAnimation();
                    }
                    fragment.a((View) null);
                    a(fragment, v2, 0, 0, false);
                } else if (fragment.u() != null) {
                    fragment.u().end();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean c(java.util.ArrayList<android.support.v4.app.BackStackRecord> r5, java.util.ArrayList<java.lang.Boolean> r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r0 = r4.b     // Catch:{ all -> 0x003c }
            r1 = 0
            if (r0 == 0) goto L_0x003a
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r0 = r4.b     // Catch:{ all -> 0x003c }
            int r0 = r0.size()     // Catch:{ all -> 0x003c }
            if (r0 != 0) goto L_0x000f
            goto L_0x003a
        L_0x000f:
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r0 = r4.b     // Catch:{ all -> 0x003c }
            int r0 = r0.size()     // Catch:{ all -> 0x003c }
            r2 = 0
        L_0x0016:
            if (r1 >= r0) goto L_0x0028
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r3 = r4.b     // Catch:{ all -> 0x003c }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ all -> 0x003c }
            android.support.v4.app.FragmentManagerImpl$OpGenerator r3 = (android.support.v4.app.FragmentManagerImpl.OpGenerator) r3     // Catch:{ all -> 0x003c }
            boolean r3 = r3.a(r5, r6)     // Catch:{ all -> 0x003c }
            r2 = r2 | r3
            int r1 = r1 + 1
            goto L_0x0016
        L_0x0028:
            java.util.ArrayList<android.support.v4.app.FragmentManagerImpl$OpGenerator> r5 = r4.b     // Catch:{ all -> 0x003c }
            r5.clear()     // Catch:{ all -> 0x003c }
            android.support.v4.app.FragmentHostCallback r5 = r4.m     // Catch:{ all -> 0x003c }
            android.os.Handler r5 = r5.d()     // Catch:{ all -> 0x003c }
            java.lang.Runnable r6 = r4.D     // Catch:{ all -> 0x003c }
            r5.removeCallbacks(r6)     // Catch:{ all -> 0x003c }
            monitor-exit(r4)     // Catch:{ all -> 0x003c }
            return r2
        L_0x003a:
            monitor-exit(r4)     // Catch:{ all -> 0x003c }
            return r1
        L_0x003c:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003c }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.c(java.util.ArrayList, java.util.ArrayList):boolean");
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        if (this.v) {
            boolean z2 = false;
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                Fragment fragment = (Fragment) this.f.valueAt(i2);
                if (!(fragment == null || fragment.V == null)) {
                    z2 |= fragment.V.hasRunningLoaders();
                }
            }
            if (!z2) {
                this.v = false;
                c();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void f() {
        if (this.k != null) {
            for (int i2 = 0; i2 < this.k.size(); i2++) {
                ((OnBackStackChangedListener) this.k.get(i2)).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(BackStackRecord backStackRecord) {
        if (this.g == null) {
            this.g = new ArrayList<>();
        }
        this.g.add(backStackRecord);
    }

    /* access modifiers changed from: 0000 */
    public boolean a(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, String str, int i2, int i3) {
        int i4;
        if (this.g == null) {
            return false;
        }
        if (str == null && i2 < 0 && (i3 & 1) == 0) {
            int size = this.g.size() - 1;
            if (size < 0) {
                return false;
            }
            arrayList.add(this.g.remove(size));
            arrayList2.add(Boolean.valueOf(true));
        } else {
            if (str != null || i2 >= 0) {
                int size2 = this.g.size() - 1;
                while (i4 >= 0) {
                    BackStackRecord backStackRecord = (BackStackRecord) this.g.get(i4);
                    if ((str != null && str.equals(backStackRecord.getName())) || (i2 >= 0 && i2 == backStackRecord.m)) {
                        break;
                    }
                    size2 = i4 - 1;
                }
                if (i4 < 0) {
                    return false;
                }
                if ((i3 & 1) != 0) {
                    i4--;
                    while (i4 >= 0) {
                        BackStackRecord backStackRecord2 = (BackStackRecord) this.g.get(i4);
                        if ((str == null || !str.equals(backStackRecord2.getName())) && (i2 < 0 || i2 != backStackRecord2.m)) {
                            break;
                        }
                        i4--;
                    }
                }
            } else {
                i4 = -1;
            }
            if (i4 == this.g.size() - 1) {
                return false;
            }
            for (int size3 = this.g.size() - 1; size3 > i4; size3--) {
                arrayList.add(this.g.remove(size3));
                arrayList2.add(Boolean.valueOf(true));
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public FragmentManagerNonConfig g() {
        a(this.C);
        return this.C;
    }

    private static void a(FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (fragmentManagerNonConfig != null) {
            List<Fragment> a2 = fragmentManagerNonConfig.a();
            if (a2 != null) {
                for (Fragment fragment : a2) {
                    fragment.M = true;
                }
            }
            List<FragmentManagerNonConfig> b2 = fragmentManagerNonConfig.b();
            if (b2 != null) {
                for (FragmentManagerNonConfig a3 : b2) {
                    a(a3);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void h() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        FragmentManagerNonConfig fragmentManagerNonConfig;
        if (this.f != null) {
            arrayList3 = null;
            arrayList2 = null;
            arrayList = null;
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                Fragment fragment = (Fragment) this.f.valueAt(i2);
                if (fragment != null) {
                    if (fragment.L) {
                        if (arrayList3 == null) {
                            arrayList3 = new ArrayList();
                        }
                        arrayList3.add(fragment);
                        fragment.r = fragment.q != null ? fragment.q.n : -1;
                        if (a) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("retainNonConfig: keeping retained ");
                            sb.append(fragment);
                            Log.v("FragmentManager", sb.toString());
                        }
                    }
                    if (fragment.C != null) {
                        fragment.C.h();
                        fragmentManagerNonConfig = fragment.C.C;
                    } else {
                        fragmentManagerNonConfig = fragment.D;
                    }
                    if (arrayList2 == null && fragmentManagerNonConfig != null) {
                        arrayList2 = new ArrayList(this.f.size());
                        for (int i3 = 0; i3 < i2; i3++) {
                            arrayList2.add(null);
                        }
                    }
                    if (arrayList2 != null) {
                        arrayList2.add(fragmentManagerNonConfig);
                    }
                    if (arrayList == null && fragment.E != null) {
                        arrayList = new ArrayList(this.f.size());
                        for (int i4 = 0; i4 < i2; i4++) {
                            arrayList.add(null);
                        }
                    }
                    if (arrayList != null) {
                        arrayList.add(fragment.E);
                    }
                }
            }
        } else {
            arrayList3 = null;
            arrayList2 = null;
            arrayList = null;
        }
        if (arrayList3 == null && arrayList2 == null && arrayList == null) {
            this.C = null;
        } else {
            this.C = new FragmentManagerNonConfig(arrayList3, arrayList2, arrayList);
        }
    }

    /* access modifiers changed from: 0000 */
    public void m(Fragment fragment) {
        if (fragment.S != null) {
            if (this.A == null) {
                this.A = new SparseArray<>();
            } else {
                this.A.clear();
            }
            fragment.S.saveHierarchyState(this.A);
            if (this.A.size() > 0) {
                fragment.m = this.A;
                this.A = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Bundle n(Fragment fragment) {
        Bundle bundle;
        if (this.z == null) {
            this.z = new Bundle();
        }
        fragment.f(this.z);
        d(fragment, this.z, false);
        if (!this.z.isEmpty()) {
            bundle = this.z;
            this.z = null;
        } else {
            bundle = null;
        }
        if (fragment.R != null) {
            m(fragment);
        }
        if (fragment.m != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", fragment.m);
        }
        if (!fragment.U) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", fragment.U);
        }
        return bundle;
    }

    /* access modifiers changed from: 0000 */
    public Parcelable i() {
        int[] iArr;
        y();
        z();
        d();
        this.s = true;
        BackStackState[] backStackStateArr = null;
        this.C = null;
        if (this.f == null || this.f.size() <= 0) {
            return null;
        }
        int size = this.f.size();
        FragmentState[] fragmentStateArr = new FragmentState[size];
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            Fragment fragment = (Fragment) this.f.valueAt(i2);
            if (fragment != null) {
                if (fragment.n < 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failure saving state: active ");
                    sb.append(fragment);
                    sb.append(" has cleared index: ");
                    sb.append(fragment.n);
                    a((RuntimeException) new IllegalStateException(sb.toString()));
                }
                FragmentState fragmentState = new FragmentState(fragment);
                fragmentStateArr[i2] = fragmentState;
                if (fragment.k <= 0 || fragmentState.k != null) {
                    fragmentState.k = fragment.l;
                } else {
                    fragmentState.k = n(fragment);
                    if (fragment.q != null) {
                        if (fragment.q.n < 0) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Failure saving state: ");
                            sb2.append(fragment);
                            sb2.append(" has target not in fragment manager: ");
                            sb2.append(fragment.q);
                            a((RuntimeException) new IllegalStateException(sb2.toString()));
                        }
                        if (fragmentState.k == null) {
                            fragmentState.k = new Bundle();
                        }
                        putFragment(fragmentState.k, "android:target_state", fragment.q);
                        if (fragment.s != 0) {
                            fragmentState.k.putInt("android:target_req_state", fragment.s);
                        }
                    }
                }
                if (a) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Saved state of ");
                    sb3.append(fragment);
                    sb3.append(": ");
                    sb3.append(fragmentState.k);
                    Log.v("FragmentManager", sb3.toString());
                }
                z2 = true;
            }
        }
        if (!z2) {
            if (a) {
                Log.v("FragmentManager", "saveAllState: no fragments!");
            }
            return null;
        }
        int size2 = this.e.size();
        if (size2 > 0) {
            iArr = new int[size2];
            for (int i3 = 0; i3 < size2; i3++) {
                iArr[i3] = ((Fragment) this.e.get(i3)).n;
                if (iArr[i3] < 0) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Failure saving state: active ");
                    sb4.append(this.e.get(i3));
                    sb4.append(" has cleared index: ");
                    sb4.append(iArr[i3]);
                    a((RuntimeException) new IllegalStateException(sb4.toString()));
                }
                if (a) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("saveAllState: adding fragment #");
                    sb5.append(i3);
                    sb5.append(": ");
                    sb5.append(this.e.get(i3));
                    Log.v("FragmentManager", sb5.toString());
                }
            }
        } else {
            iArr = null;
        }
        if (this.g != null) {
            int size3 = this.g.size();
            if (size3 > 0) {
                backStackStateArr = new BackStackState[size3];
                for (int i4 = 0; i4 < size3; i4++) {
                    backStackStateArr[i4] = new BackStackState((BackStackRecord) this.g.get(i4));
                    if (a) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("saveAllState: adding back stack #");
                        sb6.append(i4);
                        sb6.append(": ");
                        sb6.append(this.g.get(i4));
                        Log.v("FragmentManager", sb6.toString());
                    }
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.a = fragmentStateArr;
        fragmentManagerState.b = iArr;
        fragmentManagerState.c = backStackStateArr;
        if (this.p != null) {
            fragmentManagerState.d = this.p.n;
        }
        fragmentManagerState.e = this.d;
        h();
        return fragmentManagerState;
    }

    /* access modifiers changed from: 0000 */
    public void a(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        List list;
        List list2;
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.a != null) {
                if (fragmentManagerNonConfig != null) {
                    List a2 = fragmentManagerNonConfig.a();
                    list2 = fragmentManagerNonConfig.b();
                    list = fragmentManagerNonConfig.c();
                    int size = a2 != null ? a2.size() : 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        Fragment fragment = (Fragment) a2.get(i2);
                        if (a) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("restoreAllState: re-attaching retained ");
                            sb.append(fragment);
                            Log.v("FragmentManager", sb.toString());
                        }
                        int i3 = 0;
                        while (i3 < fragmentManagerState.a.length && fragmentManagerState.a[i3].b != fragment.n) {
                            i3++;
                        }
                        if (i3 == fragmentManagerState.a.length) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Could not find active fragment with index ");
                            sb2.append(fragment.n);
                            a((RuntimeException) new IllegalStateException(sb2.toString()));
                        }
                        FragmentState fragmentState = fragmentManagerState.a[i3];
                        fragmentState.l = fragment;
                        fragment.m = null;
                        fragment.z = 0;
                        fragment.w = false;
                        fragment.t = false;
                        fragment.q = null;
                        if (fragmentState.k != null) {
                            fragmentState.k.setClassLoader(this.m.c().getClassLoader());
                            fragment.m = fragmentState.k.getSparseParcelableArray("android:view_state");
                            fragment.l = fragmentState.k;
                        }
                    }
                } else {
                    list2 = null;
                    list = null;
                }
                this.f = new SparseArray<>(fragmentManagerState.a.length);
                int i4 = 0;
                while (i4 < fragmentManagerState.a.length) {
                    FragmentState fragmentState2 = fragmentManagerState.a[i4];
                    if (fragmentState2 != null) {
                        Fragment a3 = fragmentState2.a(this.m, this.n, this.o, (list2 == null || i4 >= list2.size()) ? null : (FragmentManagerNonConfig) list2.get(i4), (list == null || i4 >= list.size()) ? null : (ViewModelStore) list.get(i4));
                        if (a) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("restoreAllState: active #");
                            sb3.append(i4);
                            sb3.append(": ");
                            sb3.append(a3);
                            Log.v("FragmentManager", sb3.toString());
                        }
                        this.f.put(a3.n, a3);
                        fragmentState2.l = null;
                    }
                    i4++;
                }
                if (fragmentManagerNonConfig != null) {
                    List a4 = fragmentManagerNonConfig.a();
                    int size2 = a4 != null ? a4.size() : 0;
                    for (int i5 = 0; i5 < size2; i5++) {
                        Fragment fragment2 = (Fragment) a4.get(i5);
                        if (fragment2.r >= 0) {
                            fragment2.q = (Fragment) this.f.get(fragment2.r);
                            if (fragment2.q == null) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("Re-attaching retained fragment ");
                                sb4.append(fragment2);
                                sb4.append(" target no longer exists: ");
                                sb4.append(fragment2.r);
                                Log.w("FragmentManager", sb4.toString());
                            }
                        }
                    }
                }
                this.e.clear();
                if (fragmentManagerState.b != null) {
                    for (int i6 = 0; i6 < fragmentManagerState.b.length; i6++) {
                        Fragment fragment3 = (Fragment) this.f.get(fragmentManagerState.b[i6]);
                        if (fragment3 == null) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("No instantiated fragment for index #");
                            sb5.append(fragmentManagerState.b[i6]);
                            a((RuntimeException) new IllegalStateException(sb5.toString()));
                        }
                        fragment3.t = true;
                        if (a) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append("restoreAllState: added #");
                            sb6.append(i6);
                            sb6.append(": ");
                            sb6.append(fragment3);
                            Log.v("FragmentManager", sb6.toString());
                        }
                        if (this.e.contains(fragment3)) {
                            throw new IllegalStateException("Already added!");
                        }
                        synchronized (this.e) {
                            this.e.add(fragment3);
                        }
                    }
                }
                if (fragmentManagerState.c != null) {
                    this.g = new ArrayList<>(fragmentManagerState.c.length);
                    for (int i7 = 0; i7 < fragmentManagerState.c.length; i7++) {
                        BackStackRecord a5 = fragmentManagerState.c[i7].a(this);
                        if (a) {
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append("restoreAllState: back stack #");
                            sb7.append(i7);
                            sb7.append(" (index ");
                            sb7.append(a5.m);
                            sb7.append("): ");
                            sb7.append(a5);
                            Log.v("FragmentManager", sb7.toString());
                            PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
                            a5.a("  ", printWriter, false);
                            printWriter.close();
                        }
                        this.g.add(a5);
                        if (a5.m >= 0) {
                            a(a5.m, a5);
                        }
                    }
                } else {
                    this.g = null;
                }
                if (fragmentManagerState.d >= 0) {
                    this.p = (Fragment) this.f.get(fragmentManagerState.d);
                }
                this.d = fragmentManagerState.e;
            }
        }
    }

    private void A() {
        if (this.f != null) {
            for (int size = this.f.size() - 1; size >= 0; size--) {
                if (this.f.valueAt(size) == null) {
                    this.f.delete(this.f.keyAt(size));
                }
            }
        }
    }

    public void a(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.m != null) {
            throw new IllegalStateException("Already attached");
        }
        this.m = fragmentHostCallback;
        this.n = fragmentContainer;
        this.o = fragment;
    }

    public void j() {
        this.C = null;
        this.s = false;
        int size = this.e.size();
        for (int i2 = 0; i2 < size; i2++) {
            Fragment fragment = (Fragment) this.e.get(i2);
            if (fragment != null) {
                fragment.g();
            }
        }
    }

    public void k() {
        this.s = false;
        d(1);
    }

    public void l() {
        this.s = false;
        d(2);
    }

    public void m() {
        this.s = false;
        d(4);
    }

    public void n() {
        this.s = false;
        d(5);
    }

    public void o() {
        d(4);
    }

    public void p() {
        this.s = true;
        d(3);
    }

    public void q() {
        d(2);
    }

    public void r() {
        d(1);
    }

    public void s() {
        this.t = true;
        d();
        d(0);
        this.m = null;
        this.n = null;
        this.o = null;
    }

    /* JADX INFO: finally extract failed */
    private void d(int i2) {
        try {
            this.c = true;
            a(i2, false);
            this.c = false;
            d();
        } catch (Throwable th) {
            this.c = false;
            throw th;
        }
    }

    public void a(boolean z2) {
        for (int size = this.e.size() - 1; size >= 0; size--) {
            Fragment fragment = (Fragment) this.e.get(size);
            if (fragment != null) {
                fragment.b(z2);
            }
        }
    }

    public void b(boolean z2) {
        for (int size = this.e.size() - 1; size >= 0; size--) {
            Fragment fragment = (Fragment) this.e.get(size);
            if (fragment != null) {
                fragment.c(z2);
            }
        }
    }

    public void a(Configuration configuration) {
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            Fragment fragment = (Fragment) this.e.get(i2);
            if (fragment != null) {
                fragment.a(configuration);
            }
        }
    }

    public void t() {
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            Fragment fragment = (Fragment) this.e.get(i2);
            if (fragment != null) {
                fragment.h();
            }
        }
    }

    public boolean a(Menu menu, MenuInflater menuInflater) {
        if (this.l < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z2 = false;
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            Fragment fragment = (Fragment) this.e.get(i2);
            if (fragment != null && fragment.a(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(fragment);
                z2 = true;
            }
        }
        if (this.h != null) {
            for (int i3 = 0; i3 < this.h.size(); i3++) {
                Fragment fragment2 = (Fragment) this.h.get(i3);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.h = arrayList;
        return z2;
    }

    public boolean a(Menu menu) {
        if (this.l < 1) {
            return false;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            Fragment fragment = (Fragment) this.e.get(i2);
            if (fragment != null && fragment.a(menu)) {
                z2 = true;
            }
        }
        return z2;
    }

    public boolean a(MenuItem menuItem) {
        if (this.l < 1) {
            return false;
        }
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            Fragment fragment = (Fragment) this.e.get(i2);
            if (fragment != null && fragment.a(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public boolean b(MenuItem menuItem) {
        if (this.l < 1) {
            return false;
        }
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            Fragment fragment = (Fragment) this.e.get(i2);
            if (fragment != null && fragment.b(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public void b(Menu menu) {
        if (this.l >= 1) {
            for (int i2 = 0; i2 < this.e.size(); i2++) {
                Fragment fragment = (Fragment) this.e.get(i2);
                if (fragment != null) {
                    fragment.b(menu);
                }
            }
        }
    }

    public void o(Fragment fragment) {
        if (fragment == null || (this.f.get(fragment.n) == fragment && (fragment.B == null || fragment.getFragmentManager() == this))) {
            this.p = fragment;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Fragment ");
        sb.append(fragment);
        sb.append(" is not an active fragment of FragmentManager ");
        sb.append(this);
        throw new IllegalArgumentException(sb.toString());
    }

    public Fragment getPrimaryNavigationFragment() {
        return this.p;
    }

    public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z2) {
        this.I.add(new Pair(fragmentLifecycleCallbacks, Boolean.valueOf(z2)));
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        synchronized (this.I) {
            int i2 = 0;
            int size = this.I.size();
            while (true) {
                if (i2 >= size) {
                    break;
                } else if (((Pair) this.I.get(i2)).first == fragmentLifecycleCallbacks) {
                    this.I.remove(i2);
                    break;
                } else {
                    i2++;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Fragment fragment, Context context, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).a(fragment, context, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentPreAttached(this, fragment, context);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Fragment fragment, Context context, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).b(fragment, context, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentAttached(this, fragment, context);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Fragment fragment, Bundle bundle, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).a(fragment, bundle, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentPreCreated(this, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Fragment fragment, Bundle bundle, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).b(fragment, bundle, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentCreated(this, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(Fragment fragment, Bundle bundle, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).c(fragment, bundle, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentActivityCreated(this, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Fragment fragment, View view, Bundle bundle, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).a(fragment, view, bundle, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentViewCreated(this, fragment, view, bundle);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Fragment fragment, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).b(fragment, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentStarted(this, fragment);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(Fragment fragment, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).c(fragment, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentResumed(this, fragment);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void d(Fragment fragment, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).d(fragment, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentPaused(this, fragment);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void e(Fragment fragment, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).e(fragment, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentStopped(this, fragment);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void d(Fragment fragment, Bundle bundle, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).d(fragment, bundle, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentSaveInstanceState(this, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void f(Fragment fragment, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).f(fragment, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentViewDestroyed(this, fragment);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void g(Fragment fragment, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).g(fragment, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentDestroyed(this, fragment);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void h(Fragment fragment, boolean z2) {
        if (this.o != null) {
            FragmentManager fragmentManager = this.o.getFragmentManager();
            if (fragmentManager instanceof FragmentManagerImpl) {
                ((FragmentManagerImpl) fragmentManager).h(fragment, true);
            }
        }
        Iterator it = this.I.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (!z2 || ((Boolean) pair.second).booleanValue()) {
                ((FragmentLifecycleCallbacks) pair.first).onFragmentDetached(this, fragment);
            }
        }
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        Fragment fragment;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet2.getAttributeValue(null, "class");
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet2, FragmentTag.a);
        int i2 = 0;
        if (attributeValue == null) {
            attributeValue = obtainStyledAttributes.getString(0);
        }
        String str2 = attributeValue;
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        String string = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        if (!Fragment.a(this.m.c(), str2)) {
            return null;
        }
        if (view != null) {
            i2 = view.getId();
        }
        if (i2 == -1 && resourceId == -1 && string == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(attributeSet.getPositionDescription());
            sb.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
            sb.append(str2);
            throw new IllegalArgumentException(sb.toString());
        }
        Fragment findFragmentById = resourceId != -1 ? findFragmentById(resourceId) : null;
        if (findFragmentById == null && string != null) {
            findFragmentById = findFragmentByTag(string);
        }
        if (findFragmentById == null && i2 != -1) {
            findFragmentById = findFragmentById(i2);
        }
        if (a) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onCreateView: id=0x");
            sb2.append(Integer.toHexString(resourceId));
            sb2.append(" fname=");
            sb2.append(str2);
            sb2.append(" existing=");
            sb2.append(findFragmentById);
            Log.v("FragmentManager", sb2.toString());
        }
        if (findFragmentById == null) {
            Fragment instantiate = this.n.instantiate(context2, str2, null);
            instantiate.v = true;
            instantiate.G = resourceId != 0 ? resourceId : i2;
            instantiate.H = i2;
            instantiate.I = string;
            instantiate.w = true;
            instantiate.A = this;
            instantiate.B = this.m;
            instantiate.onInflate(this.m.c(), attributeSet2, instantiate.l);
            a(instantiate, true);
            fragment = instantiate;
        } else if (findFragmentById.w) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(attributeSet.getPositionDescription());
            sb3.append(": Duplicate id 0x");
            sb3.append(Integer.toHexString(resourceId));
            sb3.append(", tag ");
            sb3.append(string);
            sb3.append(", or parent id 0x");
            sb3.append(Integer.toHexString(i2));
            sb3.append(" with another fragment for ");
            sb3.append(str2);
            throw new IllegalArgumentException(sb3.toString());
        } else {
            findFragmentById.w = true;
            findFragmentById.B = this.m;
            if (!findFragmentById.M) {
                findFragmentById.onInflate(this.m.c(), attributeSet2, findFragmentById.l);
            }
            fragment = findFragmentById;
        }
        if (this.l >= 1 || !fragment.v) {
            b(fragment);
        } else {
            a(fragment, 1, 0, 0, false);
        }
        if (fragment.R == null) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Fragment ");
            sb4.append(str2);
            sb4.append(" did not create a view.");
            throw new IllegalStateException(sb4.toString());
        }
        if (resourceId != 0) {
            fragment.R.setId(resourceId);
        }
        if (fragment.R.getTag() == null) {
            fragment.R.setTag(string);
        }
        return fragment.R;
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }
}
