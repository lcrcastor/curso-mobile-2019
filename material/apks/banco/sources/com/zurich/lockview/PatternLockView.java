package com.zurich.lockview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.zurich.lcd_test.R;
import com.zurich.lcd_test.TouchTestActivity;
import com.zurich.lockview.listener.PatternLockViewListener;
import com.zurich.lockview.utils.PatternLockUtils;
import com.zurich.lockview.utils.ResourceUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.crypto.tls.CipherSuite;

public class PatternLockView extends View {
    /* access modifiers changed from: private */
    public static int h;
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    private float E;
    private float F;
    private final Path G;
    private final Rect H;
    private final Rect I;
    /* access modifiers changed from: private */
    public Interpolator J;
    private Interpolator K;
    private int a;
    private int b;
    private DotState[][] c;
    private int d;
    private boolean e;
    private long f;
    private float g;
    private boolean i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    /* access modifiers changed from: private */
    public int o;
    /* access modifiers changed from: private */
    public int p;
    /* access modifiers changed from: private */
    public int q;
    private int r;
    private Paint s;
    private Paint t;
    private List<PatternLockViewListener> u;
    private ArrayList<Dot> v;
    private boolean[][] w;
    private float x;
    private float y;
    private int z;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AspectRatio {
        public static final int ASPECT_RATIO_HEIGHT_BIAS = 2;
        public static final int ASPECT_RATIO_SQUARE = 0;
        public static final int ASPECT_RATIO_WIDTH_BIAS = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PatternViewMode {
        public static final int AUTO_DRAW = 1;
        public static final int CORRECT = 0;
        public static final int WRONG = 2;
    }

    public static class Dot implements Parcelable {
        public static final Creator<Dot> CREATOR = new Creator<Dot>() {
            /* renamed from: a */
            public Dot createFromParcel(Parcel parcel) {
                return new Dot(parcel);
            }

            /* renamed from: a */
            public Dot[] newArray(int i) {
                return new Dot[i];
            }
        };
        private static Dot[][] c = ((Dot[][]) Array.newInstance(Dot.class, new int[]{PatternLockView.h, PatternLockView.h}));
        /* access modifiers changed from: private */
        public int a;
        /* access modifiers changed from: private */
        public int b;

        public int describeContents() {
            return 0;
        }

        static {
            for (int i = 0; i < PatternLockView.h; i++) {
                for (int i2 = 0; i2 < PatternLockView.h; i2++) {
                    c[i][i2] = new Dot(i, i2);
                }
            }
        }

        private Dot(int i, int i2) {
            a(i, i2);
            this.a = i;
            this.b = i2;
        }

        public int getId() {
            return (this.a * PatternLockView.h) + this.b;
        }

        public int getRow() {
            return this.a;
        }

        public int getColumn() {
            return this.b;
        }

        public static synchronized Dot of(int i, int i2) {
            Dot dot;
            synchronized (Dot.class) {
                a(i, i2);
                dot = c[i][i2];
            }
            return dot;
        }

        public static synchronized Dot of(int i) {
            Dot of;
            synchronized (Dot.class) {
                of = of(i / PatternLockView.h, i % PatternLockView.h);
            }
            return of;
        }

        private static void a(int i, int i2) {
            if (i < 0 || i > PatternLockView.h - 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("mRow must be in range 0-");
                sb.append(PatternLockView.h - 1);
                throw new IllegalArgumentException(sb.toString());
            } else if (i2 < 0 || i2 > PatternLockView.h - 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("mColumn must be in range 0-");
                sb2.append(PatternLockView.h - 1);
                throw new IllegalArgumentException(sb2.toString());
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(Row = ");
            sb.append(this.a);
            sb.append(", Col = ");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Dot)) {
                return super.equals(obj);
            }
            Dot dot = (Dot) obj;
            return this.b == dot.b && this.a == dot.a;
        }

        public int hashCode() {
            return (this.a * 31) + this.b;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.b);
            parcel.writeInt(this.a);
        }

        private Dot(Parcel parcel) {
            this.b = parcel.readInt();
            this.a = parcel.readInt();
        }
    }

    public static class DotState {
        float a = 1.0f;
        float b = BitmapDescriptorFactory.HUE_RED;
        float c = 1.0f;
        float d;
        float e = Float.MIN_VALUE;
        float f = Float.MIN_VALUE;
        ValueAnimator g;
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final String a;
        private final int b;
        private final boolean c;
        private final boolean d;
        private final boolean e;

        private SavedState(Parcelable parcelable, String str, int i, boolean z, boolean z2, boolean z3) {
            super(parcelable);
            this.a = str;
            this.b = i;
            this.c = z;
            this.d = z2;
            this.e = z3;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.a = parcel.readString();
            this.b = parcel.readInt();
            this.c = ((Boolean) parcel.readValue(null)).booleanValue();
            this.d = ((Boolean) parcel.readValue(null)).booleanValue();
            this.e = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        public String a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }

        public boolean c() {
            return this.c;
        }

        public boolean d() {
            return this.d;
        }

        public boolean e() {
            return this.e;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.a);
            parcel.writeInt(this.b);
            parcel.writeValue(Boolean.valueOf(this.c));
            parcel.writeValue(Boolean.valueOf(this.d));
            parcel.writeValue(Boolean.valueOf(this.e));
        }
    }

    public PatternLockView(Context context) {
        this(context, null);
    }

    /* JADX INFO: finally extract failed */
    public PatternLockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = 0;
        this.b = 2;
        this.e = false;
        this.g = 0.6f;
        this.x = -1.0f;
        this.y = -1.0f;
        this.z = 0;
        this.A = true;
        this.B = false;
        this.C = true;
        this.D = false;
        this.G = new Path();
        this.H = new Rect();
        this.I = new Rect();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PatternLockView);
        try {
            h = obtainStyledAttributes.getInt(R.styleable.PatternLockView_dotCount, 3);
            this.i = obtainStyledAttributes.getBoolean(R.styleable.PatternLockView_aspectRatioEnabled, false);
            this.j = obtainStyledAttributes.getInt(R.styleable.PatternLockView_aspectRatio, 0);
            this.n = (int) obtainStyledAttributes.getDimension(R.styleable.PatternLockView_pathWidth, ResourceUtils.getDimensionInPx(getContext(), R.dimen.pattern_lock_path_width));
            this.k = obtainStyledAttributes.getColor(R.styleable.PatternLockView_normalStateColor, ResourceUtils.getColor(getContext(), R.color.balck));
            this.m = obtainStyledAttributes.getColor(R.styleable.PatternLockView_correctStateColor, ResourceUtils.getColor(getContext(), R.color.green));
            this.l = obtainStyledAttributes.getColor(R.styleable.PatternLockView_wrongStateColor, ResourceUtils.getColor(getContext(), R.color.pomegranate));
            this.o = (int) obtainStyledAttributes.getDimension(R.styleable.PatternLockView_dotNormalSize, ResourceUtils.getDimensionInPx(getContext(), R.dimen.pattern_lock_dot_size));
            this.p = (int) obtainStyledAttributes.getDimension(R.styleable.PatternLockView_dotSelectedSize, ResourceUtils.getDimensionInPx(getContext(), R.dimen.pattern_lock_dot_selected_size));
            this.q = obtainStyledAttributes.getInt(R.styleable.PatternLockView_dotAnimationDuration, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256);
            this.r = obtainStyledAttributes.getInt(R.styleable.PatternLockView_pathEndAnimationDuration, 100);
            obtainStyledAttributes.recycle();
            this.d = h * h;
            this.v = new ArrayList<>(this.d);
            this.w = (boolean[][]) Array.newInstance(boolean.class, new int[]{h, h});
            this.c = (DotState[][]) Array.newInstance(DotState.class, new int[]{h, h});
            for (int i2 = 0; i2 < h; i2++) {
                for (int i3 = 0; i3 < h; i3++) {
                    this.c[i2][i3] = new DotState();
                    this.c[i2][i3].d = (float) this.o;
                }
            }
            this.u = new ArrayList();
            b();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    private void b() {
        setClickable(true);
        this.t = new Paint();
        this.t.setAntiAlias(true);
        this.t.setDither(true);
        this.t.setColor(this.k);
        this.t.setStyle(Style.STROKE);
        this.t.setStrokeJoin(Join.ROUND);
        this.t.setStrokeCap(Cap.ROUND);
        this.t.setStrokeWidth((float) this.n);
        this.s = new Paint();
        this.s.setAntiAlias(true);
        this.s.setDither(true);
        if (VERSION.SDK_INT >= 21 && !isInEditMode()) {
            this.J = AnimationUtils.loadInterpolator(getContext(), 17563661);
            this.K = AnimationUtils.loadInterpolator(getContext(), 17563662);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.i) {
            int a2 = a(i2, getSuggestedMinimumWidth());
            int a3 = a(i3, getSuggestedMinimumHeight());
            switch (this.j) {
                case 0:
                    a2 = Math.min(a2, a3);
                    a3 = a2;
                    break;
                case 1:
                    a3 = Math.min(a2, a3);
                    break;
                case 2:
                    a2 = Math.min(a2, a3);
                    break;
                default:
                    throw new IllegalStateException("Unknown aspect ratio");
            }
            setMeasuredDimension(a2, a3);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        ArrayList<Dot> arrayList = this.v;
        int size = arrayList.size();
        boolean[][] zArr = this.w;
        int i2 = 0;
        if (this.z == 1) {
            int elapsedRealtime = ((int) (SystemClock.elapsedRealtime() - this.f)) % ((size + 1) * 700);
            int i3 = elapsedRealtime / 700;
            k();
            for (int i4 = 0; i4 < i3; i4++) {
                Dot dot = (Dot) arrayList.get(i4);
                zArr[dot.a][dot.b] = true;
            }
            if (i3 > 0 && i3 < size) {
                float f2 = ((float) (elapsedRealtime % 700)) / 700.0f;
                Dot dot2 = (Dot) arrayList.get(i3 - 1);
                float b2 = b(dot2.b);
                float c2 = c(dot2.a);
                Dot dot3 = (Dot) arrayList.get(i3);
                float b3 = (b(dot3.b) - b2) * f2;
                float c3 = f2 * (c(dot3.a) - c2);
                this.x = b2 + b3;
                this.y = c2 + c3;
            }
            invalidate();
        }
        Path path = this.G;
        path.rewind();
        for (int i5 = 0; i5 < h; i5++) {
            float c4 = c(i5);
            int i6 = 0;
            while (i6 < h) {
                DotState dotState = this.c[i5][i6];
                float f3 = dotState.d * dotState.a;
                float b4 = (float) ((int) b(i6));
                float f4 = ((float) ((int) c4)) + dotState.b;
                boolean z2 = zArr[i5][i6];
                float f5 = b4;
                float f6 = f4;
                int i7 = i6;
                boolean z3 = z2;
                float f7 = c4;
                a(canvas2, f5, f6, f3, z3, dotState.c);
                i6 = i7 + 1;
                c4 = f7;
            }
        }
        if (!this.B) {
            this.t.setColor(a(true));
            boolean z4 = false;
            float f8 = BitmapDescriptorFactory.HUE_RED;
            float f9 = BitmapDescriptorFactory.HUE_RED;
            while (i2 < size) {
                Dot dot4 = (Dot) arrayList.get(i2);
                if (!zArr[dot4.a][dot4.b]) {
                    break;
                }
                float b5 = b(dot4.b);
                float c5 = c(dot4.a);
                if (i2 != 0) {
                    DotState dotState2 = this.c[dot4.a][dot4.b];
                    path.rewind();
                    path.moveTo(f8, f9);
                    if (dotState2.e == Float.MIN_VALUE || dotState2.f == Float.MIN_VALUE) {
                        path.lineTo(b5, c5);
                    } else {
                        path.lineTo(dotState2.e, dotState2.f);
                    }
                    canvas2.drawPath(path, this.t);
                }
                i2++;
                f8 = b5;
                f9 = c5;
                z4 = true;
            }
            if ((this.D || this.z == 1) && z4) {
                path.rewind();
                path.moveTo(f8, f9);
                path.lineTo(this.x, this.y);
                this.t.setAlpha((int) (a(this.x, this.y, f8, f9) * 255.0f));
                canvas2.drawPath(path, this.t);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        this.E = ((float) ((i2 - getPaddingLeft()) - getPaddingRight())) / ((float) h);
        this.F = ((float) ((i3 - getPaddingTop()) - getPaddingBottom())) / ((float) h);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState(), PatternLockUtils.patternToString(this, this.v), this.z, this.A, this.B, this.C);
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setPattern(0, PatternLockUtils.stringToPattern(this, savedState.a()));
        this.z = savedState.b();
        this.A = savedState.c();
        this.B = savedState.d();
        this.C = savedState.e();
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
            int action = motionEvent.getAction();
            if (action != 7) {
                switch (action) {
                    case 9:
                        motionEvent.setAction(0);
                        break;
                    case 10:
                        motionEvent.setAction(1);
                        break;
                }
            } else {
                motionEvent.setAction(2);
            }
            onTouchEvent(motionEvent);
            motionEvent.setAction(action);
        }
        return super.onHoverEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.A || !isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                c(motionEvent);
                return true;
            case 1:
                b(motionEvent);
                h();
                return true;
            case 2:
                a(motionEvent);
                return true;
            case 3:
                this.D = false;
                h();
                g();
                return true;
            default:
                return false;
        }
    }

    public List<Dot> getPattern() {
        return (List) this.v.clone();
    }

    public int getPatternViewMode() {
        return this.z;
    }

    public boolean isInStealthMode() {
        return this.B;
    }

    public boolean isTactileFeedbackEnabled() {
        return this.C;
    }

    public boolean isInputEnabled() {
        return this.A;
    }

    public int getDotCount() {
        return h;
    }

    public boolean isAspectRatioEnabled() {
        return this.i;
    }

    public int getAspectRatio() {
        return this.j;
    }

    public int getNormalStateColor() {
        return this.k;
    }

    public int getWrongStateColor() {
        return this.l;
    }

    public int getCorrectStateColor() {
        return this.m;
    }

    public int getPathWidth() {
        return this.n;
    }

    public int getDotNormalSize() {
        return this.o;
    }

    public int getDotSelectedSize() {
        return this.p;
    }

    public int getPatternSize() {
        return this.d;
    }

    public int getDotAnimationDuration() {
        return this.q;
    }

    public int getPathEndAnimationDuration() {
        return this.r;
    }

    public void setPattern(int i2, List<Dot> list) {
        this.v.clear();
        this.v.addAll(list);
        k();
        for (Dot dot : list) {
            this.w[dot.a][dot.b] = true;
        }
        setViewMode(i2);
    }

    public void setViewMode(int i2) {
        this.z = i2;
        if (i2 == 2) {
            if (this.v.size() == 0) {
                throw new IllegalStateException("you must have a pattern to animate if you want to set the display mode to animate");
            }
            this.f = SystemClock.elapsedRealtime();
            Dot dot = (Dot) this.v.get(0);
            this.x = b(dot.b);
            this.y = c(dot.a);
            k();
        }
        invalidate();
    }

    public void setDotCount(int i2) {
        h = i2;
        this.d = h * h;
        this.v = new ArrayList<>(this.d);
        this.w = (boolean[][]) Array.newInstance(boolean.class, new int[]{h, h});
        this.c = (DotState[][]) Array.newInstance(DotState.class, new int[]{h, h});
        for (int i3 = 0; i3 < h; i3++) {
            for (int i4 = 0; i4 < h; i4++) {
                this.c[i3][i4] = new DotState();
                this.c[i3][i4].d = (float) this.o;
            }
        }
        requestLayout();
        invalidate();
    }

    public void setAspectRatioEnabled(boolean z2) {
        this.i = z2;
        requestLayout();
    }

    public void setAspectRatio(int i2) {
        this.j = i2;
        requestLayout();
    }

    public void setNormalStateColor(@ColorInt int i2) {
        this.k = i2;
    }

    public void setWrongStateColor(@ColorInt int i2) {
        this.l = i2;
    }

    public void setCorrectStateColor(@ColorInt int i2) {
        this.m = i2;
    }

    public void setPathWidth(@Dimension int i2) {
        this.n = i2;
        b();
        invalidate();
    }

    public void setDotNormalSize(@Dimension int i2) {
        this.o = i2;
        for (int i3 = 0; i3 < h; i3++) {
            for (int i4 = 0; i4 < h; i4++) {
                this.c[i3][i4] = new DotState();
                this.c[i3][i4].d = (float) this.o;
            }
        }
        invalidate();
    }

    public void setDotSelectedSize(@Dimension int i2) {
        this.p = i2;
    }

    public void setDotAnimationDuration(int i2) {
        this.q = i2;
        invalidate();
    }

    public void setPathEndAnimationDuration(int i2) {
        this.r = i2;
    }

    public void setInStealthMode(boolean z2) {
        this.B = z2;
    }

    public void setTactileFeedbackEnabled(boolean z2) {
        this.C = z2;
    }

    public void setInputEnabled(boolean z2) {
        this.A = z2;
    }

    public void setEnableHapticFeedback(boolean z2) {
        this.C = z2;
    }

    public void addPatternLockListener(PatternLockViewListener patternLockViewListener) {
        this.u.add(patternLockViewListener);
    }

    public void removePatternLockListener(PatternLockViewListener patternLockViewListener) {
        this.u.remove(patternLockViewListener);
    }

    public void clearPattern() {
        h();
    }

    private int a(int i2, int i3) {
        int size = MeasureSpec.getSize(i2);
        int mode = MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            return Math.max(size, i3);
        }
        if (mode != 0) {
            return size;
        }
        return i3;
    }

    private void c() {
        a(R.string.message_pattern_dot_added);
        a((List<Dot>) this.v);
    }

    private void d() {
        a(R.string.message_pattern_started);
        i();
    }

    private void e() {
        a(R.string.message_pattern_detected);
        b((List<Dot>) this.v);
    }

    private void f() {
        a(R.string.message_pattern_cleared);
        c((List<Dot>) this.v);
    }

    private void g() {
        a(R.string.message_pattern_cleared);
        j();
    }

    private void h() {
        this.v.clear();
        k();
        this.z = 0;
        invalidate();
    }

    private void i() {
        for (PatternLockViewListener patternLockViewListener : this.u) {
            if (patternLockViewListener != null) {
                patternLockViewListener.onStarted();
            }
        }
    }

    private void a(List<Dot> list) {
        for (PatternLockViewListener patternLockViewListener : this.u) {
            if (patternLockViewListener != null) {
                patternLockViewListener.onProgress(list);
            }
        }
    }

    private void b(List<Dot> list) {
        for (PatternLockViewListener patternLockViewListener : this.u) {
            if (patternLockViewListener != null) {
                patternLockViewListener.onComplete(list);
            }
        }
    }

    private void c(List<Dot> list) {
        for (PatternLockViewListener patternLockViewListener : this.u) {
            if (patternLockViewListener != null) {
                patternLockViewListener.testFails();
            }
        }
    }

    private void j() {
        for (PatternLockViewListener patternLockViewListener : this.u) {
            if (patternLockViewListener != null) {
                patternLockViewListener.onCleared();
            }
        }
    }

    private void k() {
        for (int i2 = 0; i2 < h; i2++) {
            for (int i3 = 0; i3 < h; i3++) {
                this.w[i2][i3] = false;
            }
        }
    }

    private Dot a(float f2, float f3) {
        Dot b2 = b(f2, f3);
        Dot dot = null;
        if (b2 == null) {
            return null;
        }
        ArrayList<Dot> arrayList = this.v;
        if (!arrayList.isEmpty()) {
            Dot dot2 = (Dot) arrayList.get(arrayList.size() - 1);
            int a2 = b2.a - dot2.a;
            int b3 = b2.b - dot2.b;
            int a3 = dot2.a;
            int b4 = dot2.b;
            int i2 = -1;
            if (Math.abs(a2) == 2 && Math.abs(b3) != 1) {
                a3 = dot2.a + (a2 > 0 ? 1 : -1);
            }
            if (Math.abs(b3) == 2 && Math.abs(a2) != 1) {
                int b5 = dot2.b;
                if (b3 > 0) {
                    i2 = 1;
                }
                b4 = b5 + i2;
            }
            dot = Dot.of(a3, b4);
        }
        if (dot != null && !this.w[dot.a][dot.b]) {
            a(dot);
        }
        a(b2);
        if (this.C) {
            performHapticFeedback(1, 3);
        }
        return b2;
    }

    private void a(Dot dot) {
        this.w[dot.a][dot.b] = true;
        this.v.add(dot);
        if (!this.B) {
            b(dot);
        }
        c();
    }

    private void b(Dot dot) {
        final DotState dotState = this.c[dot.a][dot.b];
        a((float) this.o, (float) this.p, (long) this.q, this.K, dotState, (Runnable) new Runnable() {
            public void run() {
                PatternLockView.this.a((float) PatternLockView.this.p, (float) PatternLockView.this.o, (long) PatternLockView.this.q, PatternLockView.this.J, dotState, (Runnable) null);
            }
        });
        a(dotState, this.x, this.y, b(dot.b), c(dot.a));
    }

    private void a(final DotState dotState, float f2, float f3, float f4, float f5) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        final DotState dotState2 = dotState;
        final float f6 = f2;
        final float f7 = f4;
        final float f8 = f3;
        final float f9 = f5;
        AnonymousClass2 r1 = new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float f2 = 1.0f - floatValue;
                dotState2.e = (f6 * f2) + (f7 * floatValue);
                dotState2.f = (f2 * f8) + (floatValue * f9);
                PatternLockView.this.invalidate();
            }
        };
        ofFloat.addUpdateListener(r1);
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                dotState.g = null;
            }
        });
        ofFloat.setInterpolator(this.J);
        ofFloat.setDuration((long) this.r);
        ofFloat.start();
        dotState.g = ofFloat;
    }

    /* access modifiers changed from: private */
    public void a(float f2, float f3, long j2, Interpolator interpolator, final DotState dotState, final Runnable runnable) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f2, f3});
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dotState.d = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                PatternLockView.this.invalidate();
            }
        });
        if (runnable != null) {
            ofFloat.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
        }
        ofFloat.setInterpolator(interpolator);
        ofFloat.setDuration(j2);
        ofFloat.start();
    }

    private Dot b(float f2, float f3) {
        int a2 = a(f3);
        if (a2 < 0) {
            return null;
        }
        int b2 = b(f2);
        if (b2 >= 0 && !this.w[a2][b2]) {
            return Dot.of(a2, b2);
        }
        return null;
    }

    private int a(float f2) {
        float f3 = this.F;
        float f4 = this.g * f3;
        float paddingTop = ((float) getPaddingTop()) + ((f3 - f4) / 2.0f);
        for (int i2 = 0; i2 < h; i2++) {
            float f5 = (((float) i2) * f3) + paddingTop;
            if (f2 >= f5 && f2 <= f5 + f4) {
                return i2;
            }
        }
        return -1;
    }

    private int b(float f2) {
        float f3 = this.E;
        float f4 = this.g * f3;
        float paddingLeft = ((float) getPaddingLeft()) + ((f3 - f4) / 2.0f);
        for (int i2 = 0; i2 < h; i2++) {
            float f5 = (((float) i2) * f3) + paddingLeft;
            if (f2 >= f5 && f2 <= f5 + f4) {
                return i2;
            }
        }
        return -1;
    }

    private void a(MotionEvent motionEvent) {
        float f2;
        float f3;
        float f4 = (float) this.n;
        int historySize = motionEvent.getHistorySize();
        this.I.setEmpty();
        boolean z2 = false;
        for (int i2 = 0; i2 < historySize + 1; i2++) {
            if (i2 < historySize) {
                f2 = motionEvent.getHistoricalX(i2);
            } else {
                f2 = motionEvent.getX();
            }
            if (i2 < historySize) {
                f3 = motionEvent.getHistoricalY(i2);
            } else {
                f3 = motionEvent.getY();
            }
            Dot a2 = a(f2, f3);
            int size = this.v.size();
            if (a2 != null && size == 1) {
                this.D = true;
                d();
            }
            float abs = Math.abs(f2 - this.x);
            float abs2 = Math.abs(f3 - this.y);
            if (abs > BitmapDescriptorFactory.HUE_RED || abs2 > BitmapDescriptorFactory.HUE_RED) {
                z2 = true;
            }
            if (this.D && size > 0) {
                Dot dot = (Dot) this.v.get(size - 1);
                float b2 = b(dot.b);
                float c2 = c(dot.a);
                float min = Math.min(b2, f2) - f4;
                float max = Math.max(b2, f2) + f4;
                float min2 = Math.min(c2, f3) - f4;
                float max2 = Math.max(c2, f3) + f4;
                if (a2 != null) {
                    float f5 = this.E * 0.5f;
                    float f6 = this.F * 0.5f;
                    float b3 = b(a2.b);
                    float c3 = c(a2.a);
                    min = Math.min(b3 - f5, min);
                    max = Math.max(b3 + f5, max);
                    min2 = Math.min(c3 - f6, min2);
                    max2 = Math.max(c3 + f6, max2);
                }
                this.I.union(Math.round(min), Math.round(min2), Math.round(max), Math.round(max2));
            }
        }
        this.x = motionEvent.getX();
        this.y = motionEvent.getY();
        if (z2) {
            this.H.union(this.I);
            invalidate(this.H);
            this.H.set(this.I);
        }
    }

    private void a(int i2) {
        if (VERSION.SDK_INT < 16) {
            setContentDescription(getContext().getString(i2));
            sendAccessibilityEvent(4);
            setContentDescription(null);
            return;
        }
        announceForAccessibility(getContext().getString(i2));
    }

    private void b(MotionEvent motionEvent) {
        if (this.a == this.b) {
            Toast.makeText(getContext(), TouchTestActivity.messageKO, 0).show();
            f();
        }
        if (!this.v.isEmpty()) {
            if (this.v.size() == 25) {
                Toast.makeText(getContext(), TouchTestActivity.messageOK, 0).show();
                e();
            } else {
                this.a++;
            }
            this.D = false;
            l();
            invalidate();
        }
    }

    private void l() {
        for (int i2 = 0; i2 < h; i2++) {
            for (int i3 = 0; i3 < h; i3++) {
                DotState dotState = this.c[i2][i3];
                if (dotState.g != null) {
                    dotState.g.cancel();
                    dotState.e = Float.MIN_VALUE;
                    dotState.f = Float.MIN_VALUE;
                }
            }
        }
    }

    private void c(MotionEvent motionEvent) {
        h();
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        Dot a2 = a(x2, y2);
        if (a2 != null) {
            this.D = true;
            this.z = 0;
            d();
        } else {
            this.D = false;
            g();
        }
        if (a2 != null) {
            float b2 = b(a2.b);
            float c2 = c(a2.a);
            float f2 = this.E / 2.0f;
            float f3 = this.F / 2.0f;
            invalidate((int) (b2 - f2), (int) (c2 - f3), (int) (b2 + f2), (int) (c2 + f3));
        }
        this.x = x2;
        this.y = y2;
    }

    private float b(int i2) {
        return ((float) getPaddingLeft()) + (((float) i2) * this.E) + (this.E / 2.0f);
    }

    private float c(int i2) {
        return ((float) getPaddingTop()) + (((float) i2) * this.F) + (this.F / 2.0f);
    }

    private float a(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f4;
        float f7 = f3 - f5;
        return Math.min(1.0f, Math.max(BitmapDescriptorFactory.HUE_RED, ((((float) Math.sqrt((double) ((f6 * f6) + (f7 * f7)))) / this.E) - 0.3f) * 4.0f));
    }

    private int a(boolean z2) {
        if (!z2 || this.B || this.D) {
            return this.k;
        }
        if (this.z == 2) {
            return this.l;
        }
        if (this.z == 0 || this.z == 1) {
            return this.m;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown view mode ");
        sb.append(this.z);
        throw new IllegalStateException(sb.toString());
    }

    private void a(Canvas canvas, float f2, float f3, float f4, boolean z2, float f5) {
        this.s.setColor(a(z2));
        this.s.setAlpha((int) (f5 * 255.0f));
        canvas.drawCircle(f2, f3, f4 / 2.0f, this.s);
    }
}
