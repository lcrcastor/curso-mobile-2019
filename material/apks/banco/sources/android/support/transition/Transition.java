package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public abstract class Transition implements Cloneable {
    public static final int MATCH_ID = 3;
    public static final int MATCH_INSTANCE = 1;
    public static final int MATCH_ITEM_ID = 4;
    public static final int MATCH_NAME = 2;
    private static final int[] g = {2, 1, 3, 4};
    private static final PathMotion h = new PathMotion() {
        public Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> z = new ThreadLocal<>();
    private ViewGroup A = null;
    /* access modifiers changed from: private */
    public ArrayList<Animator> B = new ArrayList<>();
    private int C = 0;
    private boolean D = false;
    private boolean E = false;
    private ArrayList<TransitionListener> F = null;
    private ArrayList<Animator> G = new ArrayList<>();
    private EpicenterCallback H;
    private ArrayMap<String, String> I;
    private PathMotion J = h;
    long a = -1;
    ArrayList<Integer> b = new ArrayList<>();
    ArrayList<View> c = new ArrayList<>();
    TransitionSet d = null;
    boolean e = false;
    TransitionPropagation f;
    private String i = getClass().getName();
    private long j = -1;
    private TimeInterpolator k = null;
    private ArrayList<String> l = null;
    private ArrayList<Class> m = null;
    private ArrayList<Integer> n = null;
    private ArrayList<View> o = null;
    private ArrayList<Class> p = null;
    private ArrayList<String> q = null;
    private ArrayList<Integer> r = null;
    private ArrayList<View> s = null;
    private ArrayList<Class> t = null;
    private TransitionValuesMaps u = new TransitionValuesMaps();
    private TransitionValuesMaps v = new TransitionValuesMaps();
    private int[] w = g;
    private ArrayList<TransitionValues> x;
    private ArrayList<TransitionValues> y;

    static class AnimationInfo {
        View a;
        String b;
        TransitionValues c;
        WindowIdImpl d;
        Transition e;

        AnimationInfo(View view, String str, Transition transition, WindowIdImpl windowIdImpl, TransitionValues transitionValues) {
            this.a = view;
            this.b = str;
            this.c = transitionValues;
            this.d = windowIdImpl;
            this.e = transition;
        }
    }

    static class ArrayListManager {
        private ArrayListManager() {
        }

        static <T> ArrayList<T> a(ArrayList<T> arrayList, T t) {
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            if (!arrayList.contains(t)) {
                arrayList.add(t);
            }
            return arrayList;
        }

        static <T> ArrayList<T> b(ArrayList<T> arrayList, T t) {
            if (arrayList == null) {
                return arrayList;
            }
            arrayList.remove(t);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList;
        }
    }

    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(@NonNull Transition transition);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MatchOrder {
    }

    public interface TransitionListener {
        void onTransitionCancel(@NonNull Transition transition);

        void onTransitionEnd(@NonNull Transition transition);

        void onTransitionPause(@NonNull Transition transition);

        void onTransitionResume(@NonNull Transition transition);

        void onTransitionStart(@NonNull Transition transition);
    }

    private static boolean a(int i2) {
        return i2 >= 1 && i2 <= 4;
    }

    public abstract void captureEndValues(@NonNull TransitionValues transitionValues);

    public abstract void captureStartValues(@NonNull TransitionValues transitionValues);

    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        return null;
    }

    @Nullable
    public String[] getTransitionProperties() {
        return null;
    }

    public Transition() {
    }

    public Transition(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.c);
        XmlResourceParser xmlResourceParser = (XmlResourceParser) attributeSet;
        long namedInt = (long) TypedArrayUtils.getNamedInt(obtainStyledAttributes, xmlResourceParser, "duration", 1, -1);
        if (namedInt >= 0) {
            setDuration(namedInt);
        }
        long namedInt2 = (long) TypedArrayUtils.getNamedInt(obtainStyledAttributes, xmlResourceParser, "startDelay", 2, -1);
        if (namedInt2 > 0) {
            setStartDelay(namedInt2);
        }
        int namedResourceId = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlResourceParser, "interpolator", 0, 0);
        if (namedResourceId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, namedResourceId));
        }
        String namedString = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlResourceParser, "matchOrder", 3);
        if (namedString != null) {
            setMatchOrder(b(namedString));
        }
        obtainStyledAttributes.recycle();
    }

    private static int[] b(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
        int[] iArr = new int[stringTokenizer.countTokens()];
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String trim = stringTokenizer.nextToken().trim();
            if ("id".equalsIgnoreCase(trim)) {
                iArr[i2] = 3;
            } else if ("instance".equalsIgnoreCase(trim)) {
                iArr[i2] = 1;
            } else if ("name".equalsIgnoreCase(trim)) {
                iArr[i2] = 2;
            } else if ("itemId".equalsIgnoreCase(trim)) {
                iArr[i2] = 4;
            } else if (trim.isEmpty()) {
                int[] iArr2 = new int[(iArr.length - 1)];
                System.arraycopy(iArr, 0, iArr2, 0, i2);
                i2--;
                iArr = iArr2;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown match type in matchOrder: '");
                sb.append(trim);
                sb.append("'");
                throw new InflateException(sb.toString());
            }
            i2++;
        }
        return iArr;
    }

    @NonNull
    public Transition setDuration(long j2) {
        this.a = j2;
        return this;
    }

    public long getDuration() {
        return this.a;
    }

    @NonNull
    public Transition setStartDelay(long j2) {
        this.j = j2;
        return this;
    }

    public long getStartDelay() {
        return this.j;
    }

    @NonNull
    public Transition setInterpolator(@Nullable TimeInterpolator timeInterpolator) {
        this.k = timeInterpolator;
        return this;
    }

    @Nullable
    public TimeInterpolator getInterpolator() {
        return this.k;
    }

    public void setMatchOrder(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            this.w = g;
            return;
        }
        int i2 = 0;
        while (i2 < iArr.length) {
            if (!a(iArr[i2])) {
                throw new IllegalArgumentException("matches contains invalid value");
            } else if (a(iArr, i2)) {
                throw new IllegalArgumentException("matches contains a duplicate value");
            } else {
                i2++;
            }
        }
        this.w = (int[]) iArr.clone();
    }

    private static boolean a(int[] iArr, int i2) {
        int i3 = iArr[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            if (iArr[i4] == i3) {
                return true;
            }
        }
        return false;
    }

    private void a(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View view = (View) arrayMap.keyAt(size);
            if (view != null && b(view)) {
                TransitionValues transitionValues = (TransitionValues) arrayMap2.remove(view);
                if (!(transitionValues == null || transitionValues.view == null || !b(transitionValues.view))) {
                    this.x.add((TransitionValues) arrayMap.removeAt(size));
                    this.y.add(transitionValues);
                }
            }
        }
    }

    private void a(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, LongSparseArray<View> longSparseArray, LongSparseArray<View> longSparseArray2) {
        int size = longSparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = (View) longSparseArray.valueAt(i2);
            if (view != null && b(view)) {
                View view2 = (View) longSparseArray2.get(longSparseArray.keyAt(i2));
                if (view2 != null && b(view2)) {
                    TransitionValues transitionValues = (TransitionValues) arrayMap.get(view);
                    TransitionValues transitionValues2 = (TransitionValues) arrayMap2.get(view2);
                    if (!(transitionValues == null || transitionValues2 == null)) {
                        this.x.add(transitionValues);
                        this.y.add(transitionValues2);
                        arrayMap.remove(view);
                        arrayMap2.remove(view2);
                    }
                }
            }
        }
    }

    private void a(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = (View) sparseArray.valueAt(i2);
            if (view != null && b(view)) {
                View view2 = (View) sparseArray2.get(sparseArray.keyAt(i2));
                if (view2 != null && b(view2)) {
                    TransitionValues transitionValues = (TransitionValues) arrayMap.get(view);
                    TransitionValues transitionValues2 = (TransitionValues) arrayMap2.get(view2);
                    if (!(transitionValues == null || transitionValues2 == null)) {
                        this.x.add(transitionValues);
                        this.y.add(transitionValues2);
                        arrayMap.remove(view);
                        arrayMap2.remove(view2);
                    }
                }
            }
        }
    }

    private void a(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, ArrayMap<String, View> arrayMap3, ArrayMap<String, View> arrayMap4) {
        int size = arrayMap3.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = (View) arrayMap3.valueAt(i2);
            if (view != null && b(view)) {
                View view2 = (View) arrayMap4.get(arrayMap3.keyAt(i2));
                if (view2 != null && b(view2)) {
                    TransitionValues transitionValues = (TransitionValues) arrayMap.get(view);
                    TransitionValues transitionValues2 = (TransitionValues) arrayMap2.get(view2);
                    if (!(transitionValues == null || transitionValues2 == null)) {
                        this.x.add(transitionValues);
                        this.y.add(transitionValues2);
                        arrayMap.remove(view);
                        arrayMap2.remove(view2);
                    }
                }
            }
        }
    }

    private void b(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            TransitionValues transitionValues = (TransitionValues) arrayMap.valueAt(i2);
            if (b(transitionValues.view)) {
                this.x.add(transitionValues);
                this.y.add(null);
            }
        }
        for (int i3 = 0; i3 < arrayMap2.size(); i3++) {
            TransitionValues transitionValues2 = (TransitionValues) arrayMap2.valueAt(i3);
            if (b(transitionValues2.view)) {
                this.y.add(transitionValues2);
                this.x.add(null);
            }
        }
    }

    private void a(TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        ArrayMap arrayMap = new ArrayMap((SimpleArrayMap) transitionValuesMaps.a);
        ArrayMap arrayMap2 = new ArrayMap((SimpleArrayMap) transitionValuesMaps2.a);
        for (int i2 : this.w) {
            switch (i2) {
                case 1:
                    a(arrayMap, arrayMap2);
                    break;
                case 2:
                    a(arrayMap, arrayMap2, transitionValuesMaps.d, transitionValuesMaps2.d);
                    break;
                case 3:
                    a(arrayMap, arrayMap2, transitionValuesMaps.b, transitionValuesMaps2.b);
                    break;
                case 4:
                    a(arrayMap, arrayMap2, transitionValuesMaps.c, transitionValuesMaps2.c);
                    break;
            }
        }
        b(arrayMap, arrayMap2);
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        int i2;
        int i3;
        View view;
        Animator animator;
        TransitionValues transitionValues;
        TransitionValues transitionValues2;
        Animator animator2;
        ViewGroup viewGroup2 = viewGroup;
        ArrayMap a2 = a();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        long j2 = Long.MAX_VALUE;
        int i4 = 0;
        while (i4 < size) {
            TransitionValues transitionValues3 = (TransitionValues) arrayList.get(i4);
            TransitionValues transitionValues4 = (TransitionValues) arrayList2.get(i4);
            if (transitionValues3 != null && !transitionValues3.a.contains(this)) {
                transitionValues3 = null;
            }
            if (transitionValues4 != null && !transitionValues4.a.contains(this)) {
                transitionValues4 = null;
            }
            if (!(transitionValues3 == null && transitionValues4 == null)) {
                if (transitionValues3 == null || transitionValues4 == null || isTransitionRequired(transitionValues3, transitionValues4)) {
                    Animator createAnimator = createAnimator(viewGroup2, transitionValues3, transitionValues4);
                    if (createAnimator != null) {
                        if (transitionValues4 != null) {
                            view = transitionValues4.view;
                            String[] transitionProperties = getTransitionProperties();
                            if (view != null && transitionProperties != null && transitionProperties.length > 0) {
                                transitionValues2 = new TransitionValues();
                                transitionValues2.view = view;
                                Animator animator3 = createAnimator;
                                i3 = size;
                                TransitionValues transitionValues5 = (TransitionValues) transitionValuesMaps2.a.get(view);
                                if (transitionValues5 != null) {
                                    int i5 = 0;
                                    while (i5 < transitionProperties.length) {
                                        int i6 = i4;
                                        TransitionValues transitionValues6 = transitionValues5;
                                        transitionValues2.values.put(transitionProperties[i5], transitionValues5.values.get(transitionProperties[i5]));
                                        i5++;
                                        i4 = i6;
                                        transitionValues5 = transitionValues6;
                                        ArrayList<TransitionValues> arrayList3 = arrayList2;
                                    }
                                }
                                i2 = i4;
                                int size2 = a2.size();
                                int i7 = 0;
                                while (true) {
                                    if (i7 >= size2) {
                                        animator2 = animator3;
                                        break;
                                    }
                                    AnimationInfo animationInfo = (AnimationInfo) a2.get((Animator) a2.keyAt(i7));
                                    if (animationInfo.c != null && animationInfo.a == view && animationInfo.b.equals(getName()) && animationInfo.c.equals(transitionValues2)) {
                                        animator2 = null;
                                        break;
                                    }
                                    i7++;
                                }
                            } else {
                                i3 = size;
                                i2 = i4;
                                animator2 = createAnimator;
                                transitionValues2 = null;
                            }
                            animator = animator2;
                            transitionValues = transitionValues2;
                        } else {
                            Animator animator4 = createAnimator;
                            i3 = size;
                            i2 = i4;
                            view = transitionValues3.view;
                            animator = animator4;
                            transitionValues = null;
                        }
                        if (animator != null) {
                            if (this.f != null) {
                                long startDelay = this.f.getStartDelay(viewGroup2, this, transitionValues3, transitionValues4);
                                sparseIntArray.put(this.G.size(), (int) startDelay);
                                j2 = Math.min(startDelay, j2);
                            }
                            long j3 = j2;
                            AnimationInfo animationInfo2 = new AnimationInfo(view, getName(), this, ViewUtils.b(viewGroup), transitionValues);
                            a2.put(animator, animationInfo2);
                            this.G.add(animator);
                            j2 = j3;
                        }
                        i4 = i2 + 1;
                        size = i3;
                    }
                }
            }
            i3 = size;
            i2 = i4;
            i4 = i2 + 1;
            size = i3;
        }
        if (j2 != 0) {
            for (int i8 = 0; i8 < sparseIntArray.size(); i8++) {
                Animator animator5 = (Animator) this.G.get(sparseIntArray.keyAt(i8));
                animator5.setStartDelay((((long) sparseIntArray.valueAt(i8)) - j2) + animator5.getStartDelay());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean b(View view) {
        int id2 = view.getId();
        if (this.n != null && this.n.contains(Integer.valueOf(id2))) {
            return false;
        }
        if (this.o != null && this.o.contains(view)) {
            return false;
        }
        if (this.p != null) {
            int size = this.p.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (((Class) this.p.get(i2)).isInstance(view)) {
                    return false;
                }
            }
        }
        if (this.q != null && ViewCompat.getTransitionName(view) != null && this.q.contains(ViewCompat.getTransitionName(view))) {
            return false;
        }
        if ((this.b.size() == 0 && this.c.size() == 0 && ((this.m == null || this.m.isEmpty()) && (this.l == null || this.l.isEmpty()))) || this.b.contains(Integer.valueOf(id2)) || this.c.contains(view)) {
            return true;
        }
        if (this.l != null && this.l.contains(ViewCompat.getTransitionName(view))) {
            return true;
        }
        if (this.m != null) {
            for (int i3 = 0; i3 < this.m.size(); i3++) {
                if (((Class) this.m.get(i3)).isInstance(view)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ArrayMap<Animator, AnimationInfo> a() {
        ArrayMap<Animator, AnimationInfo> arrayMap = (ArrayMap) z.get();
        if (arrayMap != null) {
            return arrayMap;
        }
        ArrayMap<Animator, AnimationInfo> arrayMap2 = new ArrayMap<>();
        z.set(arrayMap2);
        return arrayMap2;
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void runAnimators() {
        start();
        ArrayMap a2 = a();
        Iterator it = this.G.iterator();
        while (it.hasNext()) {
            Animator animator = (Animator) it.next();
            if (a2.containsKey(animator)) {
                start();
                a(animator, a2);
            }
        }
        this.G.clear();
        end();
    }

    private void a(Animator animator, final ArrayMap<Animator, AnimationInfo> arrayMap) {
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    Transition.this.B.add(animator);
                }

                public void onAnimationEnd(Animator animator) {
                    arrayMap.remove(animator);
                    Transition.this.B.remove(animator);
                }
            });
            animate(animator);
        }
    }

    @NonNull
    public Transition addTarget(@NonNull View view) {
        this.c.add(view);
        return this;
    }

    @NonNull
    public Transition addTarget(@IdRes int i2) {
        if (i2 != 0) {
            this.b.add(Integer.valueOf(i2));
        }
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull String str) {
        if (this.l == null) {
            this.l = new ArrayList<>();
        }
        this.l.add(str);
        return this;
    }

    @NonNull
    public Transition addTarget(@NonNull Class cls) {
        if (this.m == null) {
            this.m = new ArrayList<>();
        }
        this.m.add(cls);
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull View view) {
        this.c.remove(view);
        return this;
    }

    @NonNull
    public Transition removeTarget(@IdRes int i2) {
        if (i2 != 0) {
            this.b.remove(Integer.valueOf(i2));
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull String str) {
        if (this.l != null) {
            this.l.remove(str);
        }
        return this;
    }

    @NonNull
    public Transition removeTarget(@NonNull Class cls) {
        if (this.m != null) {
            this.m.remove(cls);
        }
        return this;
    }

    private static <T> ArrayList<T> a(ArrayList<T> arrayList, T t2, boolean z2) {
        if (t2 == null) {
            return arrayList;
        }
        if (z2) {
            return ArrayListManager.a(arrayList, t2);
        }
        return ArrayListManager.b(arrayList, t2);
    }

    @NonNull
    public Transition excludeTarget(@NonNull View view, boolean z2) {
        this.o = a(this.o, view, z2);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@IdRes int i2, boolean z2) {
        this.n = a(this.n, i2, z2);
        return this;
    }

    @NonNull
    public Transition excludeTarget(@NonNull String str, boolean z2) {
        this.q = a(this.q, (T) str, z2);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull View view, boolean z2) {
        this.s = a(this.s, view, z2);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@IdRes int i2, boolean z2) {
        this.r = a(this.r, i2, z2);
        return this;
    }

    private ArrayList<Integer> a(ArrayList<Integer> arrayList, int i2, boolean z2) {
        if (i2 <= 0) {
            return arrayList;
        }
        if (z2) {
            return ArrayListManager.a(arrayList, Integer.valueOf(i2));
        }
        return ArrayListManager.b(arrayList, Integer.valueOf(i2));
    }

    private ArrayList<View> a(ArrayList<View> arrayList, View view, boolean z2) {
        if (view == null) {
            return arrayList;
        }
        if (z2) {
            return ArrayListManager.a(arrayList, view);
        }
        return ArrayListManager.b(arrayList, view);
    }

    @NonNull
    public Transition excludeTarget(@NonNull Class cls, boolean z2) {
        this.p = a(this.p, cls, z2);
        return this;
    }

    @NonNull
    public Transition excludeChildren(@NonNull Class cls, boolean z2) {
        this.t = a(this.t, cls, z2);
        return this;
    }

    private ArrayList<Class> a(ArrayList<Class> arrayList, Class cls, boolean z2) {
        if (cls == null) {
            return arrayList;
        }
        if (z2) {
            return ArrayListManager.a(arrayList, cls);
        }
        return ArrayListManager.b(arrayList, cls);
    }

    @NonNull
    public List<Integer> getTargetIds() {
        return this.b;
    }

    @NonNull
    public List<View> getTargets() {
        return this.c;
    }

    @Nullable
    public List<String> getTargetNames() {
        return this.l;
    }

    @Nullable
    public List<Class> getTargetTypes() {
        return this.m;
    }

    /* access modifiers changed from: 0000 */
    public void a(ViewGroup viewGroup, boolean z2) {
        a(z2);
        if ((this.b.size() > 0 || this.c.size() > 0) && ((this.l == null || this.l.isEmpty()) && (this.m == null || this.m.isEmpty()))) {
            for (int i2 = 0; i2 < this.b.size(); i2++) {
                View findViewById = viewGroup.findViewById(((Integer) this.b.get(i2)).intValue());
                if (findViewById != null) {
                    TransitionValues transitionValues = new TransitionValues();
                    transitionValues.view = findViewById;
                    if (z2) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.a.add(this);
                    a(transitionValues);
                    if (z2) {
                        a(this.u, findViewById, transitionValues);
                    } else {
                        a(this.v, findViewById, transitionValues);
                    }
                }
            }
            for (int i3 = 0; i3 < this.c.size(); i3++) {
                View view = (View) this.c.get(i3);
                TransitionValues transitionValues2 = new TransitionValues();
                transitionValues2.view = view;
                if (z2) {
                    captureStartValues(transitionValues2);
                } else {
                    captureEndValues(transitionValues2);
                }
                transitionValues2.a.add(this);
                a(transitionValues2);
                if (z2) {
                    a(this.u, view, transitionValues2);
                } else {
                    a(this.v, view, transitionValues2);
                }
            }
        } else {
            b((View) viewGroup, z2);
        }
        if (!z2 && this.I != null) {
            int size = this.I.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i4 = 0; i4 < size; i4++) {
                arrayList.add(this.u.d.remove((String) this.I.keyAt(i4)));
            }
            for (int i5 = 0; i5 < size; i5++) {
                View view2 = (View) arrayList.get(i5);
                if (view2 != null) {
                    this.u.d.put((String) this.I.valueAt(i5), view2);
                }
            }
        }
    }

    private static void a(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues transitionValues) {
        transitionValuesMaps.a.put(view, transitionValues);
        int id2 = view.getId();
        if (id2 >= 0) {
            if (transitionValuesMaps.b.indexOfKey(id2) >= 0) {
                transitionValuesMaps.b.put(id2, null);
            } else {
                transitionValuesMaps.b.put(id2, view);
            }
        }
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            if (transitionValuesMaps.d.containsKey(transitionName)) {
                transitionValuesMaps.d.put(transitionName, null);
            } else {
                transitionValuesMaps.d.put(transitionName, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                if (transitionValuesMaps.c.indexOfKey(itemIdAtPosition) >= 0) {
                    View view2 = (View) transitionValuesMaps.c.get(itemIdAtPosition);
                    if (view2 != null) {
                        ViewCompat.setHasTransientState(view2, false);
                        transitionValuesMaps.c.put(itemIdAtPosition, null);
                        return;
                    }
                    return;
                }
                ViewCompat.setHasTransientState(view, true);
                transitionValuesMaps.c.put(itemIdAtPosition, view);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z2) {
        if (z2) {
            this.u.a.clear();
            this.u.b.clear();
            this.u.c.clear();
            return;
        }
        this.v.a.clear();
        this.v.b.clear();
        this.v.c.clear();
    }

    private void b(View view, boolean z2) {
        if (view != null) {
            int id2 = view.getId();
            if (this.n != null && this.n.contains(Integer.valueOf(id2))) {
                return;
            }
            if (this.o == null || !this.o.contains(view)) {
                if (this.p != null) {
                    int size = this.p.size();
                    int i2 = 0;
                    while (i2 < size) {
                        if (!((Class) this.p.get(i2)).isInstance(view)) {
                            i2++;
                        } else {
                            return;
                        }
                    }
                }
                if (view.getParent() instanceof ViewGroup) {
                    TransitionValues transitionValues = new TransitionValues();
                    transitionValues.view = view;
                    if (z2) {
                        captureStartValues(transitionValues);
                    } else {
                        captureEndValues(transitionValues);
                    }
                    transitionValues.a.add(this);
                    a(transitionValues);
                    if (z2) {
                        a(this.u, view, transitionValues);
                    } else {
                        a(this.v, view, transitionValues);
                    }
                }
                if ((view instanceof ViewGroup) && (this.r == null || !this.r.contains(Integer.valueOf(id2)))) {
                    if (this.s == null || !this.s.contains(view)) {
                        if (this.t != null) {
                            int size2 = this.t.size();
                            int i3 = 0;
                            while (i3 < size2) {
                                if (!((Class) this.t.get(i3)).isInstance(view)) {
                                    i3++;
                                } else {
                                    return;
                                }
                            }
                        }
                        ViewGroup viewGroup = (ViewGroup) view;
                        for (int i4 = 0; i4 < viewGroup.getChildCount(); i4++) {
                            b(viewGroup.getChildAt(i4), z2);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public TransitionValues getTransitionValues(@NonNull View view, boolean z2) {
        if (this.d != null) {
            return this.d.getTransitionValues(view, z2);
        }
        return (TransitionValues) (z2 ? this.u : this.v).a.get(view);
    }

    /* access modifiers changed from: 0000 */
    public TransitionValues a(View view, boolean z2) {
        if (this.d != null) {
            return this.d.a(view, z2);
        }
        ArrayList<TransitionValues> arrayList = z2 ? this.x : this.y;
        TransitionValues transitionValues = null;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i2 = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            TransitionValues transitionValues2 = (TransitionValues) arrayList.get(i3);
            if (transitionValues2 == null) {
                return null;
            }
            if (transitionValues2.view == view) {
                i2 = i3;
                break;
            }
            i3++;
        }
        if (i2 >= 0) {
            transitionValues = (TransitionValues) (z2 ? this.y : this.x).get(i2);
        }
        return transitionValues;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void pause(View view) {
        if (!this.E) {
            ArrayMap a2 = a();
            int size = a2.size();
            WindowIdImpl b2 = ViewUtils.b(view);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                AnimationInfo animationInfo = (AnimationInfo) a2.valueAt(i2);
                if (animationInfo.a != null && b2.equals(animationInfo.d)) {
                    AnimatorUtils.a((Animator) a2.keyAt(i2));
                }
            }
            if (this.F != null && this.F.size() > 0) {
                ArrayList arrayList = (ArrayList) this.F.clone();
                int size2 = arrayList.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ((TransitionListener) arrayList.get(i3)).onTransitionPause(this);
                }
            }
            this.D = true;
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public void resume(View view) {
        if (this.D) {
            if (!this.E) {
                ArrayMap a2 = a();
                int size = a2.size();
                WindowIdImpl b2 = ViewUtils.b(view);
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    AnimationInfo animationInfo = (AnimationInfo) a2.valueAt(i2);
                    if (animationInfo.a != null && b2.equals(animationInfo.d)) {
                        AnimatorUtils.b((Animator) a2.keyAt(i2));
                    }
                }
                if (this.F != null && this.F.size() > 0) {
                    ArrayList arrayList = (ArrayList) this.F.clone();
                    int size2 = arrayList.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        ((TransitionListener) arrayList.get(i3)).onTransitionResume(this);
                    }
                }
            }
            this.D = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ViewGroup viewGroup) {
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        a(this.u, this.v);
        ArrayMap a2 = a();
        int size = a2.size();
        WindowIdImpl b2 = ViewUtils.b(viewGroup);
        for (int i2 = size - 1; i2 >= 0; i2--) {
            Animator animator = (Animator) a2.keyAt(i2);
            if (animator != null) {
                AnimationInfo animationInfo = (AnimationInfo) a2.get(animator);
                if (!(animationInfo == null || animationInfo.a == null || !b2.equals(animationInfo.d))) {
                    TransitionValues transitionValues = animationInfo.c;
                    View view = animationInfo.a;
                    TransitionValues transitionValues2 = getTransitionValues(view, true);
                    TransitionValues a3 = a(view, true);
                    if (!(transitionValues2 == null && a3 == null) && animationInfo.e.isTransitionRequired(transitionValues, a3)) {
                        if (animator.isRunning() || animator.isStarted()) {
                            animator.cancel();
                        } else {
                            a2.remove(animator);
                        }
                    }
                }
            }
        }
        createAnimators(viewGroup, this.u, this.v, this.x, this.y);
        runAnimators();
    }

    public boolean isTransitionRequired(@Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return false;
        }
        String[] transitionProperties = getTransitionProperties();
        if (transitionProperties != null) {
            int length = transitionProperties.length;
            int i2 = 0;
            while (i2 < length) {
                if (!a(transitionValues, transitionValues2, transitionProperties[i2])) {
                    i2++;
                }
            }
            return false;
        }
        for (String a2 : transitionValues.values.keySet()) {
            if (a(transitionValues, transitionValues2, a2)) {
            }
        }
        return false;
        return true;
    }

    private static boolean a(TransitionValues transitionValues, TransitionValues transitionValues2, String str) {
        Object obj = transitionValues.values.get(str);
        Object obj2 = transitionValues2.values.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return true ^ obj.equals(obj2);
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void animate(Animator animator) {
        if (animator == null) {
            end();
            return;
        }
        if (getDuration() >= 0) {
            animator.setDuration(getDuration());
        }
        if (getStartDelay() >= 0) {
            animator.setStartDelay(getStartDelay());
        }
        if (getInterpolator() != null) {
            animator.setInterpolator(getInterpolator());
        }
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                Transition.this.end();
                animator.removeListener(this);
            }
        });
        animator.start();
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void start() {
        if (this.C == 0) {
            if (this.F != null && this.F.size() > 0) {
                ArrayList arrayList = (ArrayList) this.F.clone();
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((TransitionListener) arrayList.get(i2)).onTransitionStart(this);
                }
            }
            this.E = false;
        }
        this.C++;
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void end() {
        this.C--;
        if (this.C == 0) {
            if (this.F != null && this.F.size() > 0) {
                ArrayList arrayList = (ArrayList) this.F.clone();
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((TransitionListener) arrayList.get(i2)).onTransitionEnd(this);
                }
            }
            for (int i3 = 0; i3 < this.u.c.size(); i3++) {
                View view = (View) this.u.c.valueAt(i3);
                if (view != null) {
                    ViewCompat.setHasTransientState(view, false);
                }
            }
            for (int i4 = 0; i4 < this.v.c.size(); i4++) {
                View view2 = (View) this.v.c.valueAt(i4);
                if (view2 != null) {
                    ViewCompat.setHasTransientState(view2, false);
                }
            }
            this.E = true;
        }
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void b(ViewGroup viewGroup) {
        ArrayMap a2 = a();
        int size = a2.size();
        if (viewGroup != null) {
            WindowIdImpl b2 = ViewUtils.b(viewGroup);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                AnimationInfo animationInfo = (AnimationInfo) a2.valueAt(i2);
                if (!(animationInfo.a == null || b2 == null || !b2.equals(animationInfo.d))) {
                    ((Animator) a2.keyAt(i2)).end();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void cancel() {
        for (int size = this.B.size() - 1; size >= 0; size--) {
            ((Animator) this.B.get(size)).cancel();
        }
        if (this.F != null && this.F.size() > 0) {
            ArrayList arrayList = (ArrayList) this.F.clone();
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((TransitionListener) arrayList.get(i2)).onTransitionCancel(this);
            }
        }
    }

    @NonNull
    public Transition addListener(@NonNull TransitionListener transitionListener) {
        if (this.F == null) {
            this.F = new ArrayList<>();
        }
        this.F.add(transitionListener);
        return this;
    }

    @NonNull
    public Transition removeListener(@NonNull TransitionListener transitionListener) {
        if (this.F == null) {
            return this;
        }
        this.F.remove(transitionListener);
        if (this.F.size() == 0) {
            this.F = null;
        }
        return this;
    }

    public void setPathMotion(@Nullable PathMotion pathMotion) {
        if (pathMotion == null) {
            this.J = h;
        } else {
            this.J = pathMotion;
        }
    }

    @NonNull
    public PathMotion getPathMotion() {
        return this.J;
    }

    public void setEpicenterCallback(@Nullable EpicenterCallback epicenterCallback) {
        this.H = epicenterCallback;
    }

    @Nullable
    public EpicenterCallback getEpicenterCallback() {
        return this.H;
    }

    @Nullable
    public Rect getEpicenter() {
        if (this.H == null) {
            return null;
        }
        return this.H.onGetEpicenter(this);
    }

    public void setPropagation(@Nullable TransitionPropagation transitionPropagation) {
        this.f = transitionPropagation;
    }

    @Nullable
    public TransitionPropagation getPropagation() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public void a(TransitionValues transitionValues) {
        if (this.f != null && !transitionValues.values.isEmpty()) {
            String[] propagationProperties = this.f.getPropagationProperties();
            if (propagationProperties != null) {
                boolean z2 = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= propagationProperties.length) {
                        z2 = true;
                        break;
                    } else if (!transitionValues.values.containsKey(propagationProperties[i2])) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (!z2) {
                    this.f.captureValues(transitionValues);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Transition c(ViewGroup viewGroup) {
        this.A = viewGroup;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void b(boolean z2) {
        this.e = z2;
    }

    public String toString() {
        return a("");
    }

    public Transition clone() {
        try {
            Transition transition = (Transition) super.clone();
            transition.G = new ArrayList<>();
            transition.u = new TransitionValuesMaps();
            transition.v = new TransitionValuesMaps();
            transition.x = null;
            transition.y = null;
            return transition;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @NonNull
    public String getName() {
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public String a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(getClass().getSimpleName());
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        sb.append(": ");
        String sb2 = sb.toString();
        if (this.a != -1) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("dur(");
            sb3.append(this.a);
            sb3.append(") ");
            sb2 = sb3.toString();
        }
        if (this.j != -1) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb2);
            sb4.append("dly(");
            sb4.append(this.j);
            sb4.append(") ");
            sb2 = sb4.toString();
        }
        if (this.k != null) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb2);
            sb5.append("interp(");
            sb5.append(this.k);
            sb5.append(") ");
            sb2 = sb5.toString();
        }
        if (this.b.size() <= 0 && this.c.size() <= 0) {
            return sb2;
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(sb2);
        sb6.append("tgts(");
        String sb7 = sb6.toString();
        if (this.b.size() > 0) {
            String str2 = sb7;
            for (int i2 = 0; i2 < this.b.size(); i2++) {
                if (i2 > 0) {
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(str2);
                    sb8.append(", ");
                    str2 = sb8.toString();
                }
                StringBuilder sb9 = new StringBuilder();
                sb9.append(str2);
                sb9.append(this.b.get(i2));
                str2 = sb9.toString();
            }
            sb7 = str2;
        }
        if (this.c.size() > 0) {
            for (int i3 = 0; i3 < this.c.size(); i3++) {
                if (i3 > 0) {
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(sb7);
                    sb10.append(", ");
                    sb7 = sb10.toString();
                }
                StringBuilder sb11 = new StringBuilder();
                sb11.append(sb7);
                sb11.append(this.c.get(i3));
                sb7 = sb11.toString();
            }
        }
        StringBuilder sb12 = new StringBuilder();
        sb12.append(sb7);
        sb12.append(")");
        return sb12.toString();
    }
}
