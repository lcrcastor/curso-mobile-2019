package android.support.constraint;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Build.VERSION;
import android.support.constraint.Constraints.LayoutParams;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintSet {
    public static final int BASELINE = 5;
    public static final int BOTTOM = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static final int END = 7;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int HORIZONTAL_GUIDELINE = 0;
    public static final int INVISIBLE = 4;
    public static final int LEFT = 1;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    public static final int START = 6;
    public static final int TOP = 3;
    public static final int UNSET = -1;
    public static final int VERTICAL = 1;
    public static final int VERTICAL_GUIDELINE = 1;
    public static final int VISIBLE = 0;
    public static final int WRAP_CONTENT = -2;
    private static final int[] a = {0, 4, 8};
    private static SparseIntArray c = new SparseIntArray();
    private HashMap<Integer, Constraint> b = new HashMap<>();

    static class Constraint {
        public int A;
        public int B;
        public int C;
        public int D;
        public int E;
        public int F;
        public int G;
        public int H;
        public int I;
        public int J;
        public int K;
        public int L;
        public int M;
        public int N;
        public int O;
        public int P;
        public float Q;
        public float R;
        public int S;
        public int T;
        public float U;
        public boolean V;
        public float W;
        public float X;
        public float Y;
        public float Z;
        boolean a;
        public float aa;
        public float ab;
        public float ac;
        public float ad;
        public float ae;
        public float af;
        public float ag;
        public boolean ah;
        public boolean ai;
        public int aj;
        public int ak;
        public int al;
        public int am;
        public int an;
        public int ao;
        public float ap;
        public float aq;

        /* renamed from: ar reason: collision with root package name */
        public int f233ar;
        public int as;
        public int[] at;
        public int b;
        public int c;
        int d;
        public int e;
        public int f;
        public float g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        public int r;
        public int s;
        public int t;
        public float u;
        public float v;
        public String w;
        public int x;
        public int y;
        public float z;

        private Constraint() {
            this.a = false;
            this.e = -1;
            this.f = -1;
            this.g = -1.0f;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = -1;
            this.n = -1;
            this.o = -1;
            this.p = -1;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = -1;
            this.u = 0.5f;
            this.v = 0.5f;
            this.w = null;
            this.x = -1;
            this.y = 0;
            this.z = BitmapDescriptorFactory.HUE_RED;
            this.A = -1;
            this.B = -1;
            this.C = -1;
            this.D = -1;
            this.E = -1;
            this.F = -1;
            this.G = -1;
            this.H = -1;
            this.I = -1;
            this.J = 0;
            this.K = -1;
            this.L = -1;
            this.M = -1;
            this.N = -1;
            this.O = -1;
            this.P = -1;
            this.Q = BitmapDescriptorFactory.HUE_RED;
            this.R = BitmapDescriptorFactory.HUE_RED;
            this.S = 0;
            this.T = 0;
            this.U = 1.0f;
            this.V = false;
            this.W = BitmapDescriptorFactory.HUE_RED;
            this.X = BitmapDescriptorFactory.HUE_RED;
            this.Y = BitmapDescriptorFactory.HUE_RED;
            this.Z = BitmapDescriptorFactory.HUE_RED;
            this.aa = 1.0f;
            this.ab = 1.0f;
            this.ac = Float.NaN;
            this.ad = Float.NaN;
            this.ae = BitmapDescriptorFactory.HUE_RED;
            this.af = BitmapDescriptorFactory.HUE_RED;
            this.ag = BitmapDescriptorFactory.HUE_RED;
            this.ah = false;
            this.ai = false;
            this.aj = 0;
            this.ak = 0;
            this.al = -1;
            this.am = -1;
            this.an = -1;
            this.ao = -1;
            this.ap = 1.0f;
            this.aq = 1.0f;
            this.f233ar = -1;
            this.as = -1;
        }

        /* renamed from: a */
        public Constraint clone() {
            Constraint constraint = new Constraint();
            constraint.a = this.a;
            constraint.b = this.b;
            constraint.c = this.c;
            constraint.e = this.e;
            constraint.f = this.f;
            constraint.g = this.g;
            constraint.h = this.h;
            constraint.i = this.i;
            constraint.j = this.j;
            constraint.k = this.k;
            constraint.l = this.l;
            constraint.m = this.m;
            constraint.n = this.n;
            constraint.o = this.o;
            constraint.p = this.p;
            constraint.q = this.q;
            constraint.r = this.r;
            constraint.s = this.s;
            constraint.t = this.t;
            constraint.u = this.u;
            constraint.v = this.v;
            constraint.w = this.w;
            constraint.A = this.A;
            constraint.B = this.B;
            constraint.u = this.u;
            constraint.u = this.u;
            constraint.u = this.u;
            constraint.u = this.u;
            constraint.u = this.u;
            constraint.C = this.C;
            constraint.D = this.D;
            constraint.E = this.E;
            constraint.F = this.F;
            constraint.G = this.G;
            constraint.H = this.H;
            constraint.I = this.I;
            constraint.J = this.J;
            constraint.K = this.K;
            constraint.L = this.L;
            constraint.M = this.M;
            constraint.N = this.N;
            constraint.O = this.O;
            constraint.P = this.P;
            constraint.Q = this.Q;
            constraint.R = this.R;
            constraint.S = this.S;
            constraint.T = this.T;
            constraint.U = this.U;
            constraint.V = this.V;
            constraint.W = this.W;
            constraint.X = this.X;
            constraint.Y = this.Y;
            constraint.Z = this.Z;
            constraint.aa = this.aa;
            constraint.ab = this.ab;
            constraint.ac = this.ac;
            constraint.ad = this.ad;
            constraint.ae = this.ae;
            constraint.af = this.af;
            constraint.ag = this.ag;
            constraint.ah = this.ah;
            constraint.ai = this.ai;
            constraint.aj = this.aj;
            constraint.ak = this.ak;
            constraint.al = this.al;
            constraint.am = this.am;
            constraint.an = this.an;
            constraint.ao = this.ao;
            constraint.ap = this.ap;
            constraint.aq = this.aq;
            constraint.f233ar = this.f233ar;
            constraint.as = this.as;
            if (this.at != null) {
                constraint.at = Arrays.copyOf(this.at, this.at.length);
            }
            constraint.x = this.x;
            constraint.y = this.y;
            constraint.z = this.z;
            return constraint;
        }

        /* access modifiers changed from: private */
        public void a(ConstraintHelper constraintHelper, int i2, LayoutParams layoutParams) {
            a(i2, layoutParams);
            if (constraintHelper instanceof Barrier) {
                this.as = 1;
                Barrier barrier = (Barrier) constraintHelper;
                this.f233ar = barrier.getType();
                this.at = barrier.getReferencedIds();
            }
        }

        /* access modifiers changed from: private */
        public void a(int i2, LayoutParams layoutParams) {
            a(i2, (ConstraintLayout.LayoutParams) layoutParams);
            this.U = layoutParams.alpha;
            this.X = layoutParams.rotation;
            this.Y = layoutParams.rotationX;
            this.Z = layoutParams.rotationY;
            this.aa = layoutParams.scaleX;
            this.ab = layoutParams.scaleY;
            this.ac = layoutParams.transformPivotX;
            this.ad = layoutParams.transformPivotY;
            this.ae = layoutParams.translationX;
            this.af = layoutParams.translationY;
            this.ag = layoutParams.translationZ;
            this.W = layoutParams.elevation;
            this.V = layoutParams.applyElevation;
        }

        /* access modifiers changed from: private */
        public void a(int i2, ConstraintLayout.LayoutParams layoutParams) {
            this.d = i2;
            this.h = layoutParams.leftToLeft;
            this.i = layoutParams.leftToRight;
            this.j = layoutParams.rightToLeft;
            this.k = layoutParams.rightToRight;
            this.l = layoutParams.topToTop;
            this.m = layoutParams.topToBottom;
            this.n = layoutParams.bottomToTop;
            this.o = layoutParams.bottomToBottom;
            this.p = layoutParams.baselineToBaseline;
            this.q = layoutParams.startToEnd;
            this.r = layoutParams.startToStart;
            this.s = layoutParams.endToStart;
            this.t = layoutParams.endToEnd;
            this.u = layoutParams.horizontalBias;
            this.v = layoutParams.verticalBias;
            this.w = layoutParams.dimensionRatio;
            this.x = layoutParams.circleConstraint;
            this.y = layoutParams.circleRadius;
            this.z = layoutParams.circleAngle;
            this.A = layoutParams.editorAbsoluteX;
            this.B = layoutParams.editorAbsoluteY;
            this.C = layoutParams.orientation;
            this.g = layoutParams.guidePercent;
            this.e = layoutParams.guideBegin;
            this.f = layoutParams.guideEnd;
            this.b = layoutParams.width;
            this.c = layoutParams.height;
            this.D = layoutParams.leftMargin;
            this.E = layoutParams.rightMargin;
            this.F = layoutParams.topMargin;
            this.G = layoutParams.bottomMargin;
            this.Q = layoutParams.verticalWeight;
            this.R = layoutParams.horizontalWeight;
            this.T = layoutParams.verticalChainStyle;
            this.S = layoutParams.horizontalChainStyle;
            this.ah = layoutParams.constrainedWidth;
            this.ai = layoutParams.constrainedHeight;
            this.aj = layoutParams.matchConstraintDefaultWidth;
            this.ak = layoutParams.matchConstraintDefaultHeight;
            this.ah = layoutParams.constrainedWidth;
            this.al = layoutParams.matchConstraintMaxWidth;
            this.am = layoutParams.matchConstraintMaxHeight;
            this.an = layoutParams.matchConstraintMinWidth;
            this.ao = layoutParams.matchConstraintMinHeight;
            this.ap = layoutParams.matchConstraintPercentWidth;
            this.aq = layoutParams.matchConstraintPercentHeight;
            if (VERSION.SDK_INT >= 17) {
                this.H = layoutParams.getMarginEnd();
                this.I = layoutParams.getMarginStart();
            }
        }

        public void a(ConstraintLayout.LayoutParams layoutParams) {
            layoutParams.leftToLeft = this.h;
            layoutParams.leftToRight = this.i;
            layoutParams.rightToLeft = this.j;
            layoutParams.rightToRight = this.k;
            layoutParams.topToTop = this.l;
            layoutParams.topToBottom = this.m;
            layoutParams.bottomToTop = this.n;
            layoutParams.bottomToBottom = this.o;
            layoutParams.baselineToBaseline = this.p;
            layoutParams.startToEnd = this.q;
            layoutParams.startToStart = this.r;
            layoutParams.endToStart = this.s;
            layoutParams.endToEnd = this.t;
            layoutParams.leftMargin = this.D;
            layoutParams.rightMargin = this.E;
            layoutParams.topMargin = this.F;
            layoutParams.bottomMargin = this.G;
            layoutParams.goneStartMargin = this.P;
            layoutParams.goneEndMargin = this.O;
            layoutParams.horizontalBias = this.u;
            layoutParams.verticalBias = this.v;
            layoutParams.circleConstraint = this.x;
            layoutParams.circleRadius = this.y;
            layoutParams.circleAngle = this.z;
            layoutParams.dimensionRatio = this.w;
            layoutParams.editorAbsoluteX = this.A;
            layoutParams.editorAbsoluteY = this.B;
            layoutParams.verticalWeight = this.Q;
            layoutParams.horizontalWeight = this.R;
            layoutParams.verticalChainStyle = this.T;
            layoutParams.horizontalChainStyle = this.S;
            layoutParams.constrainedWidth = this.ah;
            layoutParams.constrainedHeight = this.ai;
            layoutParams.matchConstraintDefaultWidth = this.aj;
            layoutParams.matchConstraintDefaultHeight = this.ak;
            layoutParams.matchConstraintMaxWidth = this.al;
            layoutParams.matchConstraintMaxHeight = this.am;
            layoutParams.matchConstraintMinWidth = this.an;
            layoutParams.matchConstraintMinHeight = this.ao;
            layoutParams.matchConstraintPercentWidth = this.ap;
            layoutParams.matchConstraintPercentHeight = this.aq;
            layoutParams.orientation = this.C;
            layoutParams.guidePercent = this.g;
            layoutParams.guideBegin = this.e;
            layoutParams.guideEnd = this.f;
            layoutParams.width = this.b;
            layoutParams.height = this.c;
            if (VERSION.SDK_INT >= 17) {
                layoutParams.setMarginStart(this.I);
                layoutParams.setMarginEnd(this.H);
            }
            layoutParams.validate();
        }
    }

    private String b(int i) {
        switch (i) {
            case 1:
                return "left";
            case 2:
                return "right";
            case 3:
                return "top";
            case 4:
                return "bottom";
            case 5:
                return "baseline";
            case 6:
                return "start";
            case 7:
                return "end";
            default:
                return "undefined";
        }
    }

    public void setBarrierType(int i, int i2) {
    }

    static {
        c.append(R.styleable.ConstraintSet_layout_constraintLeft_toLeftOf, 25);
        c.append(R.styleable.ConstraintSet_layout_constraintLeft_toRightOf, 26);
        c.append(R.styleable.ConstraintSet_layout_constraintRight_toLeftOf, 29);
        c.append(R.styleable.ConstraintSet_layout_constraintRight_toRightOf, 30);
        c.append(R.styleable.ConstraintSet_layout_constraintTop_toTopOf, 36);
        c.append(R.styleable.ConstraintSet_layout_constraintTop_toBottomOf, 35);
        c.append(R.styleable.ConstraintSet_layout_constraintBottom_toTopOf, 4);
        c.append(R.styleable.ConstraintSet_layout_constraintBottom_toBottomOf, 3);
        c.append(R.styleable.ConstraintSet_layout_constraintBaseline_toBaselineOf, 1);
        c.append(R.styleable.ConstraintSet_layout_editor_absoluteX, 6);
        c.append(R.styleable.ConstraintSet_layout_editor_absoluteY, 7);
        c.append(R.styleable.ConstraintSet_layout_constraintGuide_begin, 17);
        c.append(R.styleable.ConstraintSet_layout_constraintGuide_end, 18);
        c.append(R.styleable.ConstraintSet_layout_constraintGuide_percent, 19);
        c.append(R.styleable.ConstraintSet_android_orientation, 27);
        c.append(R.styleable.ConstraintSet_layout_constraintStart_toEndOf, 32);
        c.append(R.styleable.ConstraintSet_layout_constraintStart_toStartOf, 33);
        c.append(R.styleable.ConstraintSet_layout_constraintEnd_toStartOf, 10);
        c.append(R.styleable.ConstraintSet_layout_constraintEnd_toEndOf, 9);
        c.append(R.styleable.ConstraintSet_layout_goneMarginLeft, 13);
        c.append(R.styleable.ConstraintSet_layout_goneMarginTop, 16);
        c.append(R.styleable.ConstraintSet_layout_goneMarginRight, 14);
        c.append(R.styleable.ConstraintSet_layout_goneMarginBottom, 11);
        c.append(R.styleable.ConstraintSet_layout_goneMarginStart, 15);
        c.append(R.styleable.ConstraintSet_layout_goneMarginEnd, 12);
        c.append(R.styleable.ConstraintSet_layout_constraintVertical_weight, 40);
        c.append(R.styleable.ConstraintSet_layout_constraintHorizontal_weight, 39);
        c.append(R.styleable.ConstraintSet_layout_constraintHorizontal_chainStyle, 41);
        c.append(R.styleable.ConstraintSet_layout_constraintVertical_chainStyle, 42);
        c.append(R.styleable.ConstraintSet_layout_constraintHorizontal_bias, 20);
        c.append(R.styleable.ConstraintSet_layout_constraintVertical_bias, 37);
        c.append(R.styleable.ConstraintSet_layout_constraintDimensionRatio, 5);
        c.append(R.styleable.ConstraintSet_layout_constraintLeft_creator, 64);
        c.append(R.styleable.ConstraintSet_layout_constraintTop_creator, 64);
        c.append(R.styleable.ConstraintSet_layout_constraintRight_creator, 64);
        c.append(R.styleable.ConstraintSet_layout_constraintBottom_creator, 64);
        c.append(R.styleable.ConstraintSet_layout_constraintBaseline_creator, 64);
        c.append(R.styleable.ConstraintSet_android_layout_marginLeft, 24);
        c.append(R.styleable.ConstraintSet_android_layout_marginRight, 28);
        c.append(R.styleable.ConstraintSet_android_layout_marginStart, 31);
        c.append(R.styleable.ConstraintSet_android_layout_marginEnd, 8);
        c.append(R.styleable.ConstraintSet_android_layout_marginTop, 34);
        c.append(R.styleable.ConstraintSet_android_layout_marginBottom, 2);
        c.append(R.styleable.ConstraintSet_android_layout_width, 23);
        c.append(R.styleable.ConstraintSet_android_layout_height, 21);
        c.append(R.styleable.ConstraintSet_android_visibility, 22);
        c.append(R.styleable.ConstraintSet_android_alpha, 43);
        c.append(R.styleable.ConstraintSet_android_elevation, 44);
        c.append(R.styleable.ConstraintSet_android_rotationX, 45);
        c.append(R.styleable.ConstraintSet_android_rotationY, 46);
        c.append(R.styleable.ConstraintSet_android_rotation, 60);
        c.append(R.styleable.ConstraintSet_android_scaleX, 47);
        c.append(R.styleable.ConstraintSet_android_scaleY, 48);
        c.append(R.styleable.ConstraintSet_android_transformPivotX, 49);
        c.append(R.styleable.ConstraintSet_android_transformPivotY, 50);
        c.append(R.styleable.ConstraintSet_android_translationX, 51);
        c.append(R.styleable.ConstraintSet_android_translationY, 52);
        c.append(R.styleable.ConstraintSet_android_translationZ, 53);
        c.append(R.styleable.ConstraintSet_layout_constraintWidth_default, 54);
        c.append(R.styleable.ConstraintSet_layout_constraintHeight_default, 55);
        c.append(R.styleable.ConstraintSet_layout_constraintWidth_max, 56);
        c.append(R.styleable.ConstraintSet_layout_constraintHeight_max, 57);
        c.append(R.styleable.ConstraintSet_layout_constraintWidth_min, 58);
        c.append(R.styleable.ConstraintSet_layout_constraintHeight_min, 59);
        c.append(R.styleable.ConstraintSet_layout_constraintCircle, 61);
        c.append(R.styleable.ConstraintSet_layout_constraintCircleRadius, 62);
        c.append(R.styleable.ConstraintSet_layout_constraintCircleAngle, 63);
        c.append(R.styleable.ConstraintSet_android_id, 38);
    }

    public void clone(Context context, int i) {
        clone((ConstraintLayout) LayoutInflater.from(context).inflate(i, null));
    }

    public void clone(ConstraintSet constraintSet) {
        this.b.clear();
        for (Integer num : constraintSet.b.keySet()) {
            this.b.put(num, ((Constraint) constraintSet.b.get(num)).clone());
        }
    }

    public void clone(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        this.b.clear();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
            int id2 = childAt.getId();
            if (id2 == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.b.containsKey(Integer.valueOf(id2))) {
                this.b.put(Integer.valueOf(id2), new Constraint());
            }
            Constraint constraint = (Constraint) this.b.get(Integer.valueOf(id2));
            constraint.a(id2, layoutParams);
            constraint.J = childAt.getVisibility();
            if (VERSION.SDK_INT >= 17) {
                constraint.U = childAt.getAlpha();
                constraint.X = childAt.getRotation();
                constraint.Y = childAt.getRotationX();
                constraint.Z = childAt.getRotationY();
                constraint.aa = childAt.getScaleX();
                constraint.ab = childAt.getScaleY();
                float pivotX = childAt.getPivotX();
                float pivotY = childAt.getPivotY();
                if (!(((double) pivotX) == 0.0d && ((double) pivotY) == 0.0d)) {
                    constraint.ac = pivotX;
                    constraint.ad = pivotY;
                }
                constraint.ae = childAt.getTranslationX();
                constraint.af = childAt.getTranslationY();
                if (VERSION.SDK_INT >= 21) {
                    constraint.ag = childAt.getTranslationZ();
                    if (constraint.V) {
                        constraint.W = childAt.getElevation();
                    }
                }
            }
        }
    }

    public void clone(Constraints constraints) {
        int childCount = constraints.getChildCount();
        this.b.clear();
        for (int i = 0; i < childCount; i++) {
            View childAt = constraints.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int id2 = childAt.getId();
            if (id2 == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.b.containsKey(Integer.valueOf(id2))) {
                this.b.put(Integer.valueOf(id2), new Constraint());
            }
            Constraint constraint = (Constraint) this.b.get(Integer.valueOf(id2));
            if (childAt instanceof ConstraintHelper) {
                constraint.a((ConstraintHelper) childAt, id2, layoutParams);
            }
            constraint.a(id2, layoutParams);
        }
    }

    public void applyTo(ConstraintLayout constraintLayout) {
        a(constraintLayout);
        constraintLayout.setConstraintSet(null);
    }

    /* access modifiers changed from: 0000 */
    public void a(ConstraintLayout constraintLayout) {
        int childCount = constraintLayout.getChildCount();
        HashSet hashSet = new HashSet(this.b.keySet());
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id2 = childAt.getId();
            if (id2 == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (this.b.containsKey(Integer.valueOf(id2))) {
                hashSet.remove(Integer.valueOf(id2));
                Constraint constraint = (Constraint) this.b.get(Integer.valueOf(id2));
                if (constraint.as != -1 && constraint.as == 1) {
                    Barrier barrier = (Barrier) childAt;
                    barrier.setId(id2);
                    barrier.setReferencedIds(constraint.at);
                    barrier.setType(constraint.f233ar);
                    constraint.a(constraintLayout.generateDefaultLayoutParams());
                }
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) childAt.getLayoutParams();
                constraint.a(layoutParams);
                childAt.setLayoutParams(layoutParams);
                childAt.setVisibility(constraint.J);
                if (VERSION.SDK_INT >= 17) {
                    childAt.setAlpha(constraint.U);
                    childAt.setRotation(constraint.X);
                    childAt.setRotationX(constraint.Y);
                    childAt.setRotationY(constraint.Z);
                    childAt.setScaleX(constraint.aa);
                    childAt.setScaleY(constraint.ab);
                    if (!Float.isNaN(constraint.ac)) {
                        childAt.setPivotX(constraint.ac);
                    }
                    if (!Float.isNaN(constraint.ad)) {
                        childAt.setPivotY(constraint.ad);
                    }
                    childAt.setTranslationX(constraint.ae);
                    childAt.setTranslationY(constraint.af);
                    if (VERSION.SDK_INT >= 21) {
                        childAt.setTranslationZ(constraint.ag);
                        if (constraint.V) {
                            childAt.setElevation(constraint.W);
                        }
                    }
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            Constraint constraint2 = (Constraint) this.b.get(num);
            if (constraint2.as != -1 && constraint2.as == 1) {
                Barrier barrier2 = new Barrier(constraintLayout.getContext());
                barrier2.setId(num.intValue());
                barrier2.setReferencedIds(constraint2.at);
                barrier2.setType(constraint2.f233ar);
                ConstraintLayout.LayoutParams generateDefaultLayoutParams = constraintLayout.generateDefaultLayoutParams();
                constraint2.a(generateDefaultLayoutParams);
                constraintLayout.addView(barrier2, generateDefaultLayoutParams);
            }
            if (constraint2.a) {
                Guideline guideline = new Guideline(constraintLayout.getContext());
                guideline.setId(num.intValue());
                ConstraintLayout.LayoutParams generateDefaultLayoutParams2 = constraintLayout.generateDefaultLayoutParams();
                constraint2.a(generateDefaultLayoutParams2);
                constraintLayout.addView(guideline, generateDefaultLayoutParams2);
            }
        }
    }

    public void center(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        int i8 = i3;
        float f2 = f;
        if (i4 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (i7 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (f2 <= BitmapDescriptorFactory.HUE_RED || f2 > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        } else if (i8 == 1 || i8 == 2) {
            int i9 = i;
            connect(i9, 1, i2, i8, i4);
            connect(i9, 2, i5, i6, i7);
            ((Constraint) this.b.get(Integer.valueOf(i))).u = f2;
        } else if (i8 == 6 || i8 == 7) {
            int i10 = i;
            connect(i10, 6, i2, i8, i4);
            connect(i10, 7, i5, i6, i7);
            ((Constraint) this.b.get(Integer.valueOf(i))).u = f2;
        } else {
            int i11 = i;
            connect(i11, 3, i2, i8, i4);
            connect(i11, 4, i5, i6, i7);
            ((Constraint) this.b.get(Integer.valueOf(i))).v = f2;
        }
    }

    public void centerHorizontally(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        connect(i, 1, i2, i3, i4);
        connect(i, 2, i5, i6, i7);
        ((Constraint) this.b.get(Integer.valueOf(i))).u = f;
    }

    public void centerHorizontallyRtl(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        connect(i, 6, i2, i3, i4);
        connect(i, 7, i5, i6, i7);
        ((Constraint) this.b.get(Integer.valueOf(i))).u = f;
    }

    public void centerVertically(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        connect(i, 3, i2, i3, i4);
        connect(i, 4, i5, i6, i7);
        ((Constraint) this.b.get(Integer.valueOf(i))).v = f;
    }

    public void createVerticalChain(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        int[] iArr2 = iArr;
        float[] fArr2 = fArr;
        if (iArr2.length < 2) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        } else if (fArr2 == null || fArr2.length == iArr2.length) {
            if (fArr2 != null) {
                a(iArr2[0]).Q = fArr2[0];
            }
            a(iArr2[0]).T = i5;
            connect(iArr2[0], 3, i, i2, 0);
            for (int i6 = 1; i6 < iArr2.length; i6++) {
                int i7 = iArr2[i6];
                int i8 = i6 - 1;
                connect(iArr2[i6], 3, iArr2[i8], 4, 0);
                connect(iArr2[i8], 4, iArr2[i6], 3, 0);
                if (fArr2 != null) {
                    a(iArr2[i6]).Q = fArr2[i6];
                }
            }
            connect(iArr2[iArr2.length - 1], 4, i3, i4, 0);
        } else {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
    }

    public void createHorizontalChain(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        a(i, i2, i3, i4, iArr, fArr, i5, 1, 2);
    }

    public void createHorizontalChainRtl(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5) {
        a(i, i2, i3, i4, iArr, fArr, i5, 6, 7);
    }

    private void a(int i, int i2, int i3, int i4, int[] iArr, float[] fArr, int i5, int i6, int i7) {
        int[] iArr2 = iArr;
        float[] fArr2 = fArr;
        if (iArr2.length < 2) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        } else if (fArr2 == null || fArr2.length == iArr2.length) {
            if (fArr2 != null) {
                a(iArr2[0]).R = fArr2[0];
            }
            a(iArr2[0]).S = i5;
            connect(iArr2[0], i6, i, i2, -1);
            for (int i8 = 1; i8 < iArr2.length; i8++) {
                int i9 = iArr2[i8];
                int i10 = i8 - 1;
                connect(iArr2[i8], i6, iArr2[i10], i7, -1);
                connect(iArr2[i10], i7, iArr2[i8], i6, -1);
                if (fArr2 != null) {
                    a(iArr2[i8]).R = fArr2[i8];
                }
            }
            connect(iArr2[iArr2.length - 1], i7, i3, i4, -1);
        } else {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
    }

    public void connect(int i, int i2, int i3, int i4, int i5) {
        if (!this.b.containsKey(Integer.valueOf(i))) {
            this.b.put(Integer.valueOf(i), new Constraint());
        }
        Constraint constraint = (Constraint) this.b.get(Integer.valueOf(i));
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    constraint.h = i3;
                    constraint.i = -1;
                } else if (i4 == 2) {
                    constraint.i = i3;
                    constraint.h = -1;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Left to ");
                    sb.append(b(i4));
                    sb.append(" undefined");
                    throw new IllegalArgumentException(sb.toString());
                }
                constraint.D = i5;
                return;
            case 2:
                if (i4 == 1) {
                    constraint.j = i3;
                    constraint.k = -1;
                } else if (i4 == 2) {
                    constraint.k = i3;
                    constraint.j = -1;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("right to ");
                    sb2.append(b(i4));
                    sb2.append(" undefined");
                    throw new IllegalArgumentException(sb2.toString());
                }
                constraint.E = i5;
                return;
            case 3:
                if (i4 == 3) {
                    constraint.l = i3;
                    constraint.m = -1;
                    constraint.p = -1;
                } else if (i4 == 4) {
                    constraint.m = i3;
                    constraint.l = -1;
                    constraint.p = -1;
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("right to ");
                    sb3.append(b(i4));
                    sb3.append(" undefined");
                    throw new IllegalArgumentException(sb3.toString());
                }
                constraint.F = i5;
                return;
            case 4:
                if (i4 == 4) {
                    constraint.o = i3;
                    constraint.n = -1;
                    constraint.p = -1;
                } else if (i4 == 3) {
                    constraint.n = i3;
                    constraint.o = -1;
                    constraint.p = -1;
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("right to ");
                    sb4.append(b(i4));
                    sb4.append(" undefined");
                    throw new IllegalArgumentException(sb4.toString());
                }
                constraint.G = i5;
                return;
            case 5:
                if (i4 == 5) {
                    constraint.p = i3;
                    constraint.o = -1;
                    constraint.n = -1;
                    constraint.l = -1;
                    constraint.m = -1;
                    return;
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append("right to ");
                sb5.append(b(i4));
                sb5.append(" undefined");
                throw new IllegalArgumentException(sb5.toString());
            case 6:
                if (i4 == 6) {
                    constraint.r = i3;
                    constraint.q = -1;
                } else if (i4 == 7) {
                    constraint.q = i3;
                    constraint.r = -1;
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("right to ");
                    sb6.append(b(i4));
                    sb6.append(" undefined");
                    throw new IllegalArgumentException(sb6.toString());
                }
                constraint.I = i5;
                return;
            case 7:
                if (i4 == 7) {
                    constraint.t = i3;
                    constraint.s = -1;
                } else if (i4 == 6) {
                    constraint.s = i3;
                    constraint.t = -1;
                } else {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append("right to ");
                    sb7.append(b(i4));
                    sb7.append(" undefined");
                    throw new IllegalArgumentException(sb7.toString());
                }
                constraint.H = i5;
                return;
            default:
                StringBuilder sb8 = new StringBuilder();
                sb8.append(b(i2));
                sb8.append(" to ");
                sb8.append(b(i4));
                sb8.append(" unknown");
                throw new IllegalArgumentException(sb8.toString());
        }
    }

    public void connect(int i, int i2, int i3, int i4) {
        if (!this.b.containsKey(Integer.valueOf(i))) {
            this.b.put(Integer.valueOf(i), new Constraint());
        }
        Constraint constraint = (Constraint) this.b.get(Integer.valueOf(i));
        switch (i2) {
            case 1:
                if (i4 == 1) {
                    constraint.h = i3;
                    constraint.i = -1;
                    return;
                } else if (i4 == 2) {
                    constraint.i = i3;
                    constraint.h = -1;
                    return;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("left to ");
                    sb.append(b(i4));
                    sb.append(" undefined");
                    throw new IllegalArgumentException(sb.toString());
                }
            case 2:
                if (i4 == 1) {
                    constraint.j = i3;
                    constraint.k = -1;
                    return;
                } else if (i4 == 2) {
                    constraint.k = i3;
                    constraint.j = -1;
                    return;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("right to ");
                    sb2.append(b(i4));
                    sb2.append(" undefined");
                    throw new IllegalArgumentException(sb2.toString());
                }
            case 3:
                if (i4 == 3) {
                    constraint.l = i3;
                    constraint.m = -1;
                    constraint.p = -1;
                    return;
                } else if (i4 == 4) {
                    constraint.m = i3;
                    constraint.l = -1;
                    constraint.p = -1;
                    return;
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("right to ");
                    sb3.append(b(i4));
                    sb3.append(" undefined");
                    throw new IllegalArgumentException(sb3.toString());
                }
            case 4:
                if (i4 == 4) {
                    constraint.o = i3;
                    constraint.n = -1;
                    constraint.p = -1;
                    return;
                } else if (i4 == 3) {
                    constraint.n = i3;
                    constraint.o = -1;
                    constraint.p = -1;
                    return;
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("right to ");
                    sb4.append(b(i4));
                    sb4.append(" undefined");
                    throw new IllegalArgumentException(sb4.toString());
                }
            case 5:
                if (i4 == 5) {
                    constraint.p = i3;
                    constraint.o = -1;
                    constraint.n = -1;
                    constraint.l = -1;
                    constraint.m = -1;
                    return;
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append("right to ");
                sb5.append(b(i4));
                sb5.append(" undefined");
                throw new IllegalArgumentException(sb5.toString());
            case 6:
                if (i4 == 6) {
                    constraint.r = i3;
                    constraint.q = -1;
                    return;
                } else if (i4 == 7) {
                    constraint.q = i3;
                    constraint.r = -1;
                    return;
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("right to ");
                    sb6.append(b(i4));
                    sb6.append(" undefined");
                    throw new IllegalArgumentException(sb6.toString());
                }
            case 7:
                if (i4 == 7) {
                    constraint.t = i3;
                    constraint.s = -1;
                    return;
                } else if (i4 == 6) {
                    constraint.s = i3;
                    constraint.t = -1;
                    return;
                } else {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append("right to ");
                    sb7.append(b(i4));
                    sb7.append(" undefined");
                    throw new IllegalArgumentException(sb7.toString());
                }
            default:
                StringBuilder sb8 = new StringBuilder();
                sb8.append(b(i2));
                sb8.append(" to ");
                sb8.append(b(i4));
                sb8.append(" unknown");
                throw new IllegalArgumentException(sb8.toString());
        }
    }

    public void centerHorizontally(int i, int i2) {
        if (i2 == 0) {
            center(i, 0, 1, 0, 0, 2, 0, 0.5f);
        } else {
            center(i, i2, 2, 0, i2, 1, 0, 0.5f);
        }
    }

    public void centerHorizontallyRtl(int i, int i2) {
        if (i2 == 0) {
            center(i, 0, 6, 0, 0, 7, 0, 0.5f);
        } else {
            center(i, i2, 7, 0, i2, 6, 0, 0.5f);
        }
    }

    public void centerVertically(int i, int i2) {
        if (i2 == 0) {
            center(i, 0, 3, 0, 0, 4, 0, 0.5f);
        } else {
            center(i, i2, 4, 0, i2, 3, 0, 0.5f);
        }
    }

    public void clear(int i) {
        this.b.remove(Integer.valueOf(i));
    }

    public void clear(int i, int i2) {
        if (this.b.containsKey(Integer.valueOf(i))) {
            Constraint constraint = (Constraint) this.b.get(Integer.valueOf(i));
            switch (i2) {
                case 1:
                    constraint.i = -1;
                    constraint.h = -1;
                    constraint.D = -1;
                    constraint.K = -1;
                    return;
                case 2:
                    constraint.k = -1;
                    constraint.j = -1;
                    constraint.E = -1;
                    constraint.M = -1;
                    return;
                case 3:
                    constraint.m = -1;
                    constraint.l = -1;
                    constraint.F = -1;
                    constraint.L = -1;
                    return;
                case 4:
                    constraint.n = -1;
                    constraint.o = -1;
                    constraint.G = -1;
                    constraint.N = -1;
                    return;
                case 5:
                    constraint.p = -1;
                    return;
                case 6:
                    constraint.q = -1;
                    constraint.r = -1;
                    constraint.I = -1;
                    constraint.P = -1;
                    return;
                case 7:
                    constraint.s = -1;
                    constraint.t = -1;
                    constraint.H = -1;
                    constraint.O = -1;
                    return;
                default:
                    throw new IllegalArgumentException("unknown constraint");
            }
        }
    }

    public void setMargin(int i, int i2, int i3) {
        Constraint a2 = a(i);
        switch (i2) {
            case 1:
                a2.D = i3;
                return;
            case 2:
                a2.E = i3;
                return;
            case 3:
                a2.F = i3;
                return;
            case 4:
                a2.G = i3;
                return;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                a2.I = i3;
                return;
            case 7:
                a2.H = i3;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setGoneMargin(int i, int i2, int i3) {
        Constraint a2 = a(i);
        switch (i2) {
            case 1:
                a2.K = i3;
                return;
            case 2:
                a2.M = i3;
                return;
            case 3:
                a2.L = i3;
                return;
            case 4:
                a2.N = i3;
                return;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                a2.P = i3;
                return;
            case 7:
                a2.O = i3;
                return;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setHorizontalBias(int i, float f) {
        a(i).u = f;
    }

    public void setVerticalBias(int i, float f) {
        a(i).v = f;
    }

    public void setDimensionRatio(int i, String str) {
        a(i).w = str;
    }

    public void setVisibility(int i, int i2) {
        a(i).J = i2;
    }

    public void setAlpha(int i, float f) {
        a(i).U = f;
    }

    public boolean getApplyElevation(int i) {
        return a(i).V;
    }

    public void setApplyElevation(int i, boolean z) {
        a(i).V = z;
    }

    public void setElevation(int i, float f) {
        a(i).W = f;
        a(i).V = true;
    }

    public void setRotation(int i, float f) {
        a(i).X = f;
    }

    public void setRotationX(int i, float f) {
        a(i).Y = f;
    }

    public void setRotationY(int i, float f) {
        a(i).Z = f;
    }

    public void setScaleX(int i, float f) {
        a(i).aa = f;
    }

    public void setScaleY(int i, float f) {
        a(i).ab = f;
    }

    public void setTransformPivotX(int i, float f) {
        a(i).ac = f;
    }

    public void setTransformPivotY(int i, float f) {
        a(i).ad = f;
    }

    public void setTransformPivot(int i, float f, float f2) {
        Constraint a2 = a(i);
        a2.ad = f2;
        a2.ac = f;
    }

    public void setTranslationX(int i, float f) {
        a(i).ae = f;
    }

    public void setTranslationY(int i, float f) {
        a(i).af = f;
    }

    public void setTranslation(int i, float f, float f2) {
        Constraint a2 = a(i);
        a2.ae = f;
        a2.af = f2;
    }

    public void setTranslationZ(int i, float f) {
        a(i).ag = f;
    }

    public void constrainHeight(int i, int i2) {
        a(i).c = i2;
    }

    public void constrainWidth(int i, int i2) {
        a(i).b = i2;
    }

    public void constrainCircle(int i, int i2, int i3, float f) {
        Constraint a2 = a(i);
        a2.x = i2;
        a2.y = i3;
        a2.z = f;
    }

    public void constrainMaxHeight(int i, int i2) {
        a(i).am = i2;
    }

    public void constrainMaxWidth(int i, int i2) {
        a(i).al = i2;
    }

    public void constrainMinHeight(int i, int i2) {
        a(i).ao = i2;
    }

    public void constrainMinWidth(int i, int i2) {
        a(i).an = i2;
    }

    public void constrainPercentWidth(int i, float f) {
        a(i).ap = f;
    }

    public void constrainPercentHeight(int i, float f) {
        a(i).aq = f;
    }

    public void constrainDefaultHeight(int i, int i2) {
        a(i).ak = i2;
    }

    public void constrainDefaultWidth(int i, int i2) {
        a(i).aj = i2;
    }

    public void setHorizontalWeight(int i, float f) {
        a(i).R = f;
    }

    public void setVerticalWeight(int i, float f) {
        a(i).Q = f;
    }

    public void setHorizontalChainStyle(int i, int i2) {
        a(i).S = i2;
    }

    public void setVerticalChainStyle(int i, int i2) {
        a(i).T = i2;
    }

    public void addToHorizontalChain(int i, int i2, int i3) {
        connect(i, 1, i2, i2 == 0 ? 1 : 2, 0);
        connect(i, 2, i3, i3 == 0 ? 2 : 1, 0);
        if (i2 != 0) {
            connect(i2, 2, i, 1, 0);
        }
        if (i3 != 0) {
            connect(i3, 1, i, 2, 0);
        }
    }

    public void addToHorizontalChainRTL(int i, int i2, int i3) {
        connect(i, 6, i2, i2 == 0 ? 6 : 7, 0);
        connect(i, 7, i3, i3 == 0 ? 7 : 6, 0);
        if (i2 != 0) {
            connect(i2, 7, i, 6, 0);
        }
        if (i3 != 0) {
            connect(i3, 6, i, 7, 0);
        }
    }

    public void addToVerticalChain(int i, int i2, int i3) {
        connect(i, 3, i2, i2 == 0 ? 3 : 4, 0);
        connect(i, 4, i3, i3 == 0 ? 4 : 3, 0);
        if (i2 != 0) {
            connect(i2, 4, i, 3, 0);
        }
        if (i2 != 0) {
            connect(i3, 3, i, 4, 0);
        }
    }

    public void removeFromVerticalChain(int i) {
        if (this.b.containsKey(Integer.valueOf(i))) {
            Constraint constraint = (Constraint) this.b.get(Integer.valueOf(i));
            int i2 = constraint.m;
            int i3 = constraint.n;
            if (!(i2 == -1 && i3 == -1)) {
                if (i2 != -1 && i3 != -1) {
                    connect(i2, 4, i3, 3, 0);
                    connect(i3, 3, i2, 4, 0);
                } else if (!(i2 == -1 && i3 == -1)) {
                    if (constraint.o != -1) {
                        connect(i2, 4, constraint.o, 4, 0);
                    } else if (constraint.l != -1) {
                        connect(i3, 3, constraint.l, 3, 0);
                    }
                }
            }
        }
        clear(i, 3);
        clear(i, 4);
    }

    public void removeFromHorizontalChain(int i) {
        if (this.b.containsKey(Integer.valueOf(i))) {
            Constraint constraint = (Constraint) this.b.get(Integer.valueOf(i));
            int i2 = constraint.i;
            int i3 = constraint.j;
            if (i2 == -1 && i3 == -1) {
                int i4 = constraint.q;
                int i5 = constraint.s;
                if (!(i4 == -1 && i5 == -1)) {
                    if (i4 != -1 && i5 != -1) {
                        connect(i4, 7, i5, 6, 0);
                        connect(i5, 6, i2, 7, 0);
                    } else if (!(i2 == -1 && i5 == -1)) {
                        if (constraint.k != -1) {
                            connect(i2, 7, constraint.k, 7, 0);
                        } else if (constraint.h != -1) {
                            connect(i5, 6, constraint.h, 6, 0);
                        }
                    }
                }
                clear(i, 6);
                clear(i, 7);
                return;
            }
            if (i2 != -1 && i3 != -1) {
                connect(i2, 2, i3, 1, 0);
                connect(i3, 1, i2, 2, 0);
            } else if (!(i2 == -1 && i3 == -1)) {
                if (constraint.k != -1) {
                    connect(i2, 2, constraint.k, 2, 0);
                } else if (constraint.h != -1) {
                    connect(i3, 1, constraint.h, 1, 0);
                }
            }
            clear(i, 1);
            clear(i, 2);
        }
    }

    public void create(int i, int i2) {
        Constraint a2 = a(i);
        a2.a = true;
        a2.C = i2;
    }

    public void createBarrier(int i, int i2, int... iArr) {
        Constraint a2 = a(i);
        a2.as = 1;
        a2.f233ar = i2;
        a2.a = false;
        a2.at = iArr;
    }

    public void setGuidelineBegin(int i, int i2) {
        a(i).e = i2;
        a(i).f = -1;
        a(i).g = -1.0f;
    }

    public void setGuidelineEnd(int i, int i2) {
        a(i).f = i2;
        a(i).e = -1;
        a(i).g = -1.0f;
    }

    public void setGuidelinePercent(int i, float f) {
        a(i).g = f;
        a(i).f = -1;
        a(i).e = -1;
    }

    private Constraint a(int i) {
        if (!this.b.containsKey(Integer.valueOf(i))) {
            this.b.put(Integer.valueOf(i), new Constraint());
        }
        return (Constraint) this.b.get(Integer.valueOf(i));
    }

    public void load(Context context, int i) {
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType != 0) {
                    switch (eventType) {
                        case 2:
                            String name = xml.getName();
                            Constraint a2 = a(context, Xml.asAttributeSet(xml));
                            if (name.equalsIgnoreCase("Guideline")) {
                                a2.a = true;
                            }
                            this.b.put(Integer.valueOf(a2.d), a2);
                            break;
                        case 3:
                            break;
                    }
                } else {
                    xml.getName();
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private static int a(TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        return resourceId == -1 ? typedArray.getInt(i, -1) : resourceId;
    }

    private Constraint a(Context context, AttributeSet attributeSet) {
        Constraint constraint = new Constraint();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ConstraintSet);
        a(constraint, obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        return constraint;
    }

    private void a(Constraint constraint, TypedArray typedArray) {
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            int i2 = c.get(index);
            switch (i2) {
                case 1:
                    constraint.p = a(typedArray, index, constraint.p);
                    break;
                case 2:
                    constraint.G = typedArray.getDimensionPixelSize(index, constraint.G);
                    break;
                case 3:
                    constraint.o = a(typedArray, index, constraint.o);
                    break;
                case 4:
                    constraint.n = a(typedArray, index, constraint.n);
                    break;
                case 5:
                    constraint.w = typedArray.getString(index);
                    break;
                case 6:
                    constraint.A = typedArray.getDimensionPixelOffset(index, constraint.A);
                    break;
                case 7:
                    constraint.B = typedArray.getDimensionPixelOffset(index, constraint.B);
                    break;
                case 8:
                    constraint.H = typedArray.getDimensionPixelSize(index, constraint.H);
                    break;
                case 9:
                    constraint.t = a(typedArray, index, constraint.t);
                    break;
                case 10:
                    constraint.s = a(typedArray, index, constraint.s);
                    break;
                case 11:
                    constraint.N = typedArray.getDimensionPixelSize(index, constraint.N);
                    break;
                case 12:
                    constraint.O = typedArray.getDimensionPixelSize(index, constraint.O);
                    break;
                case 13:
                    constraint.K = typedArray.getDimensionPixelSize(index, constraint.K);
                    break;
                case 14:
                    constraint.M = typedArray.getDimensionPixelSize(index, constraint.M);
                    break;
                case 15:
                    constraint.P = typedArray.getDimensionPixelSize(index, constraint.P);
                    break;
                case 16:
                    constraint.L = typedArray.getDimensionPixelSize(index, constraint.L);
                    break;
                case 17:
                    constraint.e = typedArray.getDimensionPixelOffset(index, constraint.e);
                    break;
                case 18:
                    constraint.f = typedArray.getDimensionPixelOffset(index, constraint.f);
                    break;
                case 19:
                    constraint.g = typedArray.getFloat(index, constraint.g);
                    break;
                case 20:
                    constraint.u = typedArray.getFloat(index, constraint.u);
                    break;
                case 21:
                    constraint.c = typedArray.getLayoutDimension(index, constraint.c);
                    break;
                case 22:
                    constraint.J = typedArray.getInt(index, constraint.J);
                    constraint.J = a[constraint.J];
                    break;
                case 23:
                    constraint.b = typedArray.getLayoutDimension(index, constraint.b);
                    break;
                case 24:
                    constraint.D = typedArray.getDimensionPixelSize(index, constraint.D);
                    break;
                case 25:
                    constraint.h = a(typedArray, index, constraint.h);
                    break;
                case 26:
                    constraint.i = a(typedArray, index, constraint.i);
                    break;
                case 27:
                    constraint.C = typedArray.getInt(index, constraint.C);
                    break;
                case 28:
                    constraint.E = typedArray.getDimensionPixelSize(index, constraint.E);
                    break;
                case 29:
                    constraint.j = a(typedArray, index, constraint.j);
                    break;
                case 30:
                    constraint.k = a(typedArray, index, constraint.k);
                    break;
                case 31:
                    constraint.I = typedArray.getDimensionPixelSize(index, constraint.I);
                    break;
                case 32:
                    constraint.q = a(typedArray, index, constraint.q);
                    break;
                case 33:
                    constraint.r = a(typedArray, index, constraint.r);
                    break;
                case 34:
                    constraint.F = typedArray.getDimensionPixelSize(index, constraint.F);
                    break;
                case 35:
                    constraint.m = a(typedArray, index, constraint.m);
                    break;
                case 36:
                    constraint.l = a(typedArray, index, constraint.l);
                    break;
                case 37:
                    constraint.v = typedArray.getFloat(index, constraint.v);
                    break;
                case 38:
                    constraint.d = typedArray.getResourceId(index, constraint.d);
                    break;
                case 39:
                    constraint.R = typedArray.getFloat(index, constraint.R);
                    break;
                case 40:
                    constraint.Q = typedArray.getFloat(index, constraint.Q);
                    break;
                case 41:
                    constraint.S = typedArray.getInt(index, constraint.S);
                    break;
                case 42:
                    constraint.T = typedArray.getInt(index, constraint.T);
                    break;
                case 43:
                    constraint.U = typedArray.getFloat(index, constraint.U);
                    break;
                case 44:
                    constraint.V = true;
                    constraint.W = typedArray.getDimension(index, constraint.W);
                    break;
                case 45:
                    constraint.Y = typedArray.getFloat(index, constraint.Y);
                    break;
                case 46:
                    constraint.Z = typedArray.getFloat(index, constraint.Z);
                    break;
                case 47:
                    constraint.aa = typedArray.getFloat(index, constraint.aa);
                    break;
                case 48:
                    constraint.ab = typedArray.getFloat(index, constraint.ab);
                    break;
                case 49:
                    constraint.ac = typedArray.getFloat(index, constraint.ac);
                    break;
                case 50:
                    constraint.ad = typedArray.getFloat(index, constraint.ad);
                    break;
                case 51:
                    constraint.ae = typedArray.getDimension(index, constraint.ae);
                    break;
                case 52:
                    constraint.af = typedArray.getDimension(index, constraint.af);
                    break;
                case 53:
                    constraint.ag = typedArray.getDimension(index, constraint.ag);
                    break;
                default:
                    switch (i2) {
                        case 60:
                            constraint.X = typedArray.getFloat(index, constraint.X);
                            break;
                        case 61:
                            constraint.x = a(typedArray, index, constraint.x);
                            break;
                        case 62:
                            constraint.y = typedArray.getDimensionPixelSize(index, constraint.y);
                            break;
                        case 63:
                            constraint.z = typedArray.getFloat(index, constraint.z);
                            break;
                        case 64:
                            StringBuilder sb = new StringBuilder();
                            sb.append("unused attribute 0x");
                            sb.append(Integer.toHexString(index));
                            sb.append("   ");
                            sb.append(c.get(index));
                            Log.w("ConstraintSet", sb.toString());
                            break;
                        default:
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Unknown attribute 0x");
                            sb2.append(Integer.toHexString(index));
                            sb2.append("   ");
                            sb2.append(c.get(index));
                            Log.w("ConstraintSet", sb2.toString());
                            break;
                    }
            }
        }
    }
}
