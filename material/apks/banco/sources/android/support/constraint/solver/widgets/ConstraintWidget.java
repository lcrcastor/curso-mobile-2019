package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

public class ConstraintWidget {
    protected static final int ANCHOR_BASELINE = 4;
    protected static final int ANCHOR_BOTTOM = 3;
    protected static final int ANCHOR_LEFT = 0;
    protected static final int ANCHOR_RIGHT = 1;
    protected static final int ANCHOR_TOP = 2;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.5f;
    protected static final int DIRECT = 2;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_RATIO = 3;
    public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    protected static final int SOLVER = 1;
    public static final int UNKNOWN = -1;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    int A;
    float B;
    float C;
    boolean D;
    boolean E;
    int F;
    int G;
    boolean H;
    boolean I;
    float[] J;
    ConstraintWidget K;
    ConstraintWidget L;
    private int[] M;
    private float N;
    private int O;
    private int P;
    private int Q;
    private int R;
    private int S;
    private int T;
    private Object U;
    private int V;
    private int W;
    private String X;
    private String Y;
    ResolutionDimension a;
    ResolutionDimension b;
    int c;
    int d;
    int[] e;
    int f;
    int g;
    float h;
    int i;
    int j;
    float k;
    boolean l;
    boolean m;
    protected ArrayList<ConstraintAnchor> mAnchors;
    protected float mDimensionRatio;
    protected int mDimensionRatioSide;
    public int mHorizontalResolution;
    protected ConstraintAnchor[] mListAnchors;
    protected DimensionBehaviour[] mListDimensionBehaviors;
    protected ConstraintWidget[] mListNextMatchConstraintsWidget;
    protected ConstraintWidget[] mListNextVisibleWidget;
    protected int mMinHeight;
    protected int mMinWidth;
    protected int mOffsetX;
    protected int mOffsetY;
    public int mVerticalResolution;
    protected int mX;
    protected int mY;
    int n;
    float o;
    ConstraintAnchor p;
    ConstraintAnchor q;
    ConstraintAnchor r;
    ConstraintAnchor s;
    ConstraintAnchor t;
    ConstraintAnchor u;
    ConstraintAnchor v;
    ConstraintAnchor w;
    ConstraintWidget x;
    int y;
    int z;

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public void connectedTo(ConstraintWidget constraintWidget) {
    }

    public void resolve() {
    }

    public int getMaxHeight() {
        return this.M[1];
    }

    public int getMaxWidth() {
        return this.M[0];
    }

    public void setMaxWidth(int i2) {
        this.M[0] = i2;
    }

    public void setMaxHeight(int i2) {
        this.M[1] = i2;
    }

    public boolean isSpreadWidth() {
        return this.c == 0 && this.mDimensionRatio == BitmapDescriptorFactory.HUE_RED && this.f == 0 && this.g == 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isSpreadHeight() {
        return this.d == 0 && this.mDimensionRatio == BitmapDescriptorFactory.HUE_RED && this.i == 0 && this.j == 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public void reset() {
        this.p.reset();
        this.q.reset();
        this.r.reset();
        this.s.reset();
        this.t.reset();
        this.u.reset();
        this.v.reset();
        this.w.reset();
        this.x = null;
        this.N = BitmapDescriptorFactory.HUE_RED;
        this.y = 0;
        this.z = 0;
        this.mDimensionRatio = BitmapDescriptorFactory.HUE_RED;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        this.R = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.A = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.S = 0;
        this.T = 0;
        this.B = DEFAULT_BIAS;
        this.C = DEFAULT_BIAS;
        this.mListDimensionBehaviors[0] = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors[1] = DimensionBehaviour.FIXED;
        this.U = null;
        this.V = 0;
        this.W = 0;
        this.Y = null;
        this.D = false;
        this.E = false;
        this.F = 0;
        this.G = 0;
        this.H = false;
        this.I = false;
        this.J[0] = -1.0f;
        this.J[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.M[0] = Integer.MAX_VALUE;
        this.M[1] = Integer.MAX_VALUE;
        this.c = 0;
        this.d = 0;
        this.h = 1.0f;
        this.k = 1.0f;
        this.g = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        this.j = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        this.f = 0;
        this.i = 0;
        this.n = -1;
        this.o = 1.0f;
        if (this.a != null) {
            this.a.reset();
        }
        if (this.b != null) {
            this.b.reset();
        }
    }

    public void resetResolutionNodes() {
        for (int i2 = 0; i2 < 6; i2++) {
            this.mListAnchors[i2].getResolutionNode().reset();
        }
    }

    public void updateResolutionNodes() {
        for (int i2 = 0; i2 < 6; i2++) {
            this.mListAnchors[i2].getResolutionNode().update();
        }
    }

    public void analyze(int i2) {
        Optimizer.a(i2, this);
    }

    public boolean isFullyResolved() {
        if (this.p.getResolutionNode().i == 1 && this.r.getResolutionNode().i == 1 && this.q.getResolutionNode().i == 1 && this.s.getResolutionNode().i == 1) {
            return true;
        }
        return false;
    }

    public ResolutionDimension getResolutionWidth() {
        if (this.a == null) {
            this.a = new ResolutionDimension();
        }
        return this.a;
    }

    public ResolutionDimension getResolutionHeight() {
        if (this.b == null) {
            this.b = new ResolutionDimension();
        }
        return this.b;
    }

    public ConstraintWidget() {
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.c = 0;
        this.d = 0;
        this.e = new int[2];
        this.f = 0;
        this.g = 0;
        this.h = 1.0f;
        this.i = 0;
        this.j = 0;
        this.k = 1.0f;
        this.n = -1;
        this.o = 1.0f;
        this.M = new int[]{SubsamplingScaleImageView.TILE_SIZE_AUTO, SubsamplingScaleImageView.TILE_SIZE_AUTO};
        this.N = BitmapDescriptorFactory.HUE_RED;
        this.p = new ConstraintAnchor(this, Type.LEFT);
        this.q = new ConstraintAnchor(this, Type.TOP);
        this.r = new ConstraintAnchor(this, Type.RIGHT);
        this.s = new ConstraintAnchor(this, Type.BOTTOM);
        this.t = new ConstraintAnchor(this, Type.BASELINE);
        this.u = new ConstraintAnchor(this, Type.CENTER_X);
        this.v = new ConstraintAnchor(this, Type.CENTER_Y);
        this.w = new ConstraintAnchor(this, Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.p, this.r, this.q, this.s, this.t, this.w};
        this.mAnchors = new ArrayList<>();
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.x = null;
        this.y = 0;
        this.z = 0;
        this.mDimensionRatio = BitmapDescriptorFactory.HUE_RED;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        this.R = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.A = 0;
        this.B = DEFAULT_BIAS;
        this.C = DEFAULT_BIAS;
        this.V = 0;
        this.W = 0;
        this.X = null;
        this.Y = null;
        this.F = 0;
        this.G = 0;
        this.J = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mListNextVisibleWidget = new ConstraintWidget[]{null, null};
        this.K = null;
        this.L = null;
        a();
    }

    public ConstraintWidget(int i2, int i3, int i4, int i5) {
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.c = 0;
        this.d = 0;
        this.e = new int[2];
        this.f = 0;
        this.g = 0;
        this.h = 1.0f;
        this.i = 0;
        this.j = 0;
        this.k = 1.0f;
        this.n = -1;
        this.o = 1.0f;
        this.M = new int[]{SubsamplingScaleImageView.TILE_SIZE_AUTO, SubsamplingScaleImageView.TILE_SIZE_AUTO};
        this.N = BitmapDescriptorFactory.HUE_RED;
        this.p = new ConstraintAnchor(this, Type.LEFT);
        this.q = new ConstraintAnchor(this, Type.TOP);
        this.r = new ConstraintAnchor(this, Type.RIGHT);
        this.s = new ConstraintAnchor(this, Type.BOTTOM);
        this.t = new ConstraintAnchor(this, Type.BASELINE);
        this.u = new ConstraintAnchor(this, Type.CENTER_X);
        this.v = new ConstraintAnchor(this, Type.CENTER_Y);
        this.w = new ConstraintAnchor(this, Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.p, this.r, this.q, this.s, this.t, this.w};
        this.mAnchors = new ArrayList<>();
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.x = null;
        this.y = 0;
        this.z = 0;
        this.mDimensionRatio = BitmapDescriptorFactory.HUE_RED;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        this.R = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.A = 0;
        this.B = DEFAULT_BIAS;
        this.C = DEFAULT_BIAS;
        this.V = 0;
        this.W = 0;
        this.X = null;
        this.Y = null;
        this.F = 0;
        this.G = 0;
        this.J = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mListNextVisibleWidget = new ConstraintWidget[]{null, null};
        this.K = null;
        this.L = null;
        this.mX = i2;
        this.mY = i3;
        this.y = i4;
        this.z = i5;
        a();
        forceUpdateDrawPosition();
    }

    public ConstraintWidget(int i2, int i3) {
        this(0, 0, i2, i3);
    }

    public void resetSolverVariables(Cache cache) {
        this.p.resetSolverVariable(cache);
        this.q.resetSolverVariable(cache);
        this.r.resetSolverVariable(cache);
        this.s.resetSolverVariable(cache);
        this.t.resetSolverVariable(cache);
        this.w.resetSolverVariable(cache);
        this.u.resetSolverVariable(cache);
        this.v.resetSolverVariable(cache);
    }

    private void a() {
        this.mAnchors.add(this.p);
        this.mAnchors.add(this.q);
        this.mAnchors.add(this.r);
        this.mAnchors.add(this.s);
        this.mAnchors.add(this.u);
        this.mAnchors.add(this.v);
        this.mAnchors.add(this.w);
        this.mAnchors.add(this.t);
    }

    public boolean isRoot() {
        return this.x == null;
    }

    public boolean isRootContainer() {
        return (this instanceof ConstraintWidgetContainer) && (this.x == null || !(this.x instanceof ConstraintWidgetContainer));
    }

    public boolean isInsideConstraintLayout() {
        ConstraintWidget parent = getParent();
        if (parent == null) {
            return false;
        }
        while (parent != null) {
            if (parent instanceof ConstraintWidgetContainer) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    public boolean hasAncestor(ConstraintWidget constraintWidget) {
        ConstraintWidget parent = getParent();
        if (parent == constraintWidget) {
            return true;
        }
        if (parent == constraintWidget.getParent()) {
            return false;
        }
        while (parent != null) {
            if (parent == constraintWidget || parent == constraintWidget.getParent()) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    public WidgetContainer getRootWidgetContainer() {
        ConstraintWidget constraintWidget = this;
        while (constraintWidget.getParent() != null) {
            constraintWidget = constraintWidget.getParent();
        }
        if (constraintWidget instanceof WidgetContainer) {
            return (WidgetContainer) constraintWidget;
        }
        return null;
    }

    public ConstraintWidget getParent() {
        return this.x;
    }

    public void setParent(ConstraintWidget constraintWidget) {
        this.x = constraintWidget;
    }

    public void setWidthWrapContent(boolean z2) {
        this.l = z2;
    }

    public boolean isWidthWrapContent() {
        return this.l;
    }

    public void setHeightWrapContent(boolean z2) {
        this.m = z2;
    }

    public boolean isHeightWrapContent() {
        return this.m;
    }

    public void connectCircularConstraint(ConstraintWidget constraintWidget, float f2, int i2) {
        immediateConnect(Type.CENTER, constraintWidget, Type.CENTER, i2, 0);
        this.N = f2;
    }

    public String getType() {
        return this.Y;
    }

    public void setType(String str) {
        this.Y = str;
    }

    public void setVisibility(int i2) {
        this.W = i2;
    }

    public int getVisibility() {
        return this.W;
    }

    public String getDebugName() {
        return this.X;
    }

    public void setDebugName(String str) {
        this.X = str;
    }

    public void setDebugSolverName(LinearSystem linearSystem, String str) {
        this.X = str;
        SolverVariable createObjectVariable = linearSystem.createObjectVariable(this.p);
        SolverVariable createObjectVariable2 = linearSystem.createObjectVariable(this.q);
        SolverVariable createObjectVariable3 = linearSystem.createObjectVariable(this.r);
        SolverVariable createObjectVariable4 = linearSystem.createObjectVariable(this.s);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".left");
        createObjectVariable.setName(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(".top");
        createObjectVariable2.setName(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(".right");
        createObjectVariable3.setName(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append(".bottom");
        createObjectVariable4.setName(sb4.toString());
        if (this.A > 0) {
            SolverVariable createObjectVariable5 = linearSystem.createObjectVariable(this.t);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append(".baseline");
            createObjectVariable5.setName(sb5.toString());
        }
    }

    public void createObjectVariables(LinearSystem linearSystem) {
        linearSystem.createObjectVariable(this.p);
        linearSystem.createObjectVariable(this.q);
        linearSystem.createObjectVariable(this.r);
        linearSystem.createObjectVariable(this.s);
        if (this.A > 0) {
            linearSystem.createObjectVariable(this.t);
        }
    }

    public String toString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        if (this.Y != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("type: ");
            sb2.append(this.Y);
            sb2.append(UtilsCuentas.SEPARAOR2);
            str = sb2.toString();
        } else {
            str = "";
        }
        sb.append(str);
        if (this.X != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("id: ");
            sb3.append(this.X);
            sb3.append(UtilsCuentas.SEPARAOR2);
            str2 = sb3.toString();
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.mX);
        sb.append(", ");
        sb.append(this.mY);
        sb.append(") - (");
        sb.append(this.y);
        sb.append(" x ");
        sb.append(this.z);
        sb.append(") wrap: (");
        sb.append(this.S);
        sb.append(" x ");
        sb.append(this.T);
        sb.append(")");
        return sb.toString();
    }

    public int getInternalDrawRight() {
        return this.O + this.Q;
    }

    public int getInternalDrawBottom() {
        return this.P + this.R;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getWidth() {
        if (this.W == 8) {
            return 0;
        }
        return this.y;
    }

    public int getOptimizerWrapWidth() {
        int i2;
        int i3 = this.y;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return i3;
        }
        if (this.c == 1) {
            i2 = Math.max(this.f, i3);
        } else if (this.f > 0) {
            i2 = this.f;
            this.y = i2;
        } else {
            i2 = 0;
        }
        return (this.g <= 0 || this.g >= i2) ? i2 : this.g;
    }

    public int getOptimizerWrapHeight() {
        int i2;
        int i3 = this.z;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return i3;
        }
        if (this.d == 1) {
            i2 = Math.max(this.i, i3);
        } else if (this.i > 0) {
            i2 = this.i;
            this.z = i2;
        } else {
            i2 = 0;
        }
        return (this.j <= 0 || this.j >= i2) ? i2 : this.j;
    }

    public int getWrapWidth() {
        return this.S;
    }

    public int getHeight() {
        if (this.W == 8) {
            return 0;
        }
        return this.z;
    }

    public int getWrapHeight() {
        return this.T;
    }

    public int getDrawX() {
        return this.O + this.mOffsetX;
    }

    public int getDrawY() {
        return this.P + this.mOffsetY;
    }

    public int getDrawWidth() {
        return this.Q;
    }

    public int getDrawHeight() {
        return this.R;
    }

    public int getDrawBottom() {
        return getDrawY() + this.R;
    }

    public int getDrawRight() {
        return getDrawX() + this.Q;
    }

    /* access modifiers changed from: protected */
    public int getRootX() {
        return this.mX + this.mOffsetX;
    }

    /* access modifiers changed from: protected */
    public int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getLeft() {
        return getX();
    }

    public int getTop() {
        return getY();
    }

    public int getRight() {
        return getX() + this.y;
    }

    public int getBottom() {
        return getY() + this.z;
    }

    public float getHorizontalBiasPercent() {
        return this.B;
    }

    public float getVerticalBiasPercent() {
        return this.C;
    }

    public boolean hasBaseline() {
        return this.A > 0;
    }

    public int getBaselineDistance() {
        return this.A;
    }

    public Object getCompanionWidget() {
        return this.U;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int i2) {
        this.mX = i2;
    }

    public void setY(int i2) {
        this.mY = i2;
    }

    public void setOrigin(int i2, int i3) {
        this.mX = i2;
        this.mY = i3;
    }

    public void setOffset(int i2, int i3) {
        this.mOffsetX = i2;
        this.mOffsetY = i3;
    }

    public void setGoneMargin(Type type, int i2) {
        switch (type) {
            case LEFT:
                this.p.d = i2;
                return;
            case TOP:
                this.q.d = i2;
                return;
            case RIGHT:
                this.r.d = i2;
                return;
            case BOTTOM:
                this.s.d = i2;
                return;
            default:
                return;
        }
    }

    public void updateDrawPosition() {
        int i2 = this.mX;
        int i3 = this.mY;
        int i4 = this.mX + this.y;
        int i5 = this.mY + this.z;
        this.O = i2;
        this.P = i3;
        this.Q = i4 - i2;
        this.R = i5 - i3;
    }

    public void forceUpdateDrawPosition() {
        int i2 = this.mX;
        int i3 = this.mY;
        int i4 = this.mX + this.y;
        int i5 = this.mY + this.z;
        this.O = i2;
        this.P = i3;
        this.Q = i4 - i2;
        this.R = i5 - i3;
    }

    public void setDrawOrigin(int i2, int i3) {
        this.O = i2 - this.mOffsetX;
        this.P = i3 - this.mOffsetY;
        this.mX = this.O;
        this.mY = this.P;
    }

    public void setDrawX(int i2) {
        this.O = i2 - this.mOffsetX;
        this.mX = this.O;
    }

    public void setDrawY(int i2) {
        this.P = i2 - this.mOffsetY;
        this.mY = this.P;
    }

    public void setDrawWidth(int i2) {
        this.Q = i2;
    }

    public void setDrawHeight(int i2) {
        this.R = i2;
    }

    public void setWidth(int i2) {
        this.y = i2;
        if (this.y < this.mMinWidth) {
            this.y = this.mMinWidth;
        }
    }

    public void setHeight(int i2) {
        this.z = i2;
        if (this.z < this.mMinHeight) {
            this.z = this.mMinHeight;
        }
    }

    public void setHorizontalMatchStyle(int i2, int i3, int i4, float f2) {
        this.c = i2;
        this.f = i3;
        this.g = i4;
        this.h = f2;
        if (f2 < 1.0f && this.c == 0) {
            this.c = 2;
        }
    }

    public void setVerticalMatchStyle(int i2, int i3, int i4, float f2) {
        this.d = i2;
        this.i = i3;
        this.j = i4;
        this.k = f2;
        if (f2 < 1.0f && this.d == 0) {
            this.d = 2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0089  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setDimensionRatio(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto L_0x008e
            int r1 = r9.length()
            if (r1 != 0) goto L_0x000b
            goto L_0x008e
        L_0x000b:
            r1 = -1
            int r2 = r9.length()
            r3 = 44
            int r3 = r9.indexOf(r3)
            r4 = 0
            r5 = 1
            if (r3 <= 0) goto L_0x0037
            int r6 = r2 + -1
            if (r3 >= r6) goto L_0x0037
            java.lang.String r6 = r9.substring(r4, r3)
            java.lang.String r7 = "W"
            boolean r7 = r6.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x002c
            r1 = 0
            goto L_0x0035
        L_0x002c:
            java.lang.String r4 = "H"
            boolean r4 = r6.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0035
            r1 = 1
        L_0x0035:
            int r4 = r3 + 1
        L_0x0037:
            r3 = 58
            int r3 = r9.indexOf(r3)
            if (r3 < 0) goto L_0x0075
            int r2 = r2 - r5
            if (r3 >= r2) goto L_0x0075
            java.lang.String r2 = r9.substring(r4, r3)
            int r3 = r3 + r5
            java.lang.String r9 = r9.substring(r3)
            int r3 = r2.length()
            if (r3 <= 0) goto L_0x0084
            int r3 = r9.length()
            if (r3 <= 0) goto L_0x0084
            float r2 = java.lang.Float.parseFloat(r2)     // Catch:{ NumberFormatException -> 0x0084 }
            float r9 = java.lang.Float.parseFloat(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            int r3 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r3 <= 0) goto L_0x0084
            int r3 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r3 <= 0) goto L_0x0084
            if (r1 != r5) goto L_0x006f
            float r9 = r9 / r2
            float r9 = java.lang.Math.abs(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x006f:
            float r2 = r2 / r9
            float r9 = java.lang.Math.abs(r2)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x0075:
            java.lang.String r9 = r9.substring(r4)
            int r2 = r9.length()
            if (r2 <= 0) goto L_0x0084
            float r9 = java.lang.Float.parseFloat(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x0084:
            r9 = 0
        L_0x0085:
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x008d
            r8.mDimensionRatio = r9
            r8.mDimensionRatioSide = r1
        L_0x008d:
            return
        L_0x008e:
            r8.mDimensionRatio = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidget.setDimensionRatio(java.lang.String):void");
    }

    public void setDimensionRatio(float f2, int i2) {
        this.mDimensionRatio = f2;
        this.mDimensionRatioSide = i2;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public void setHorizontalBiasPercent(float f2) {
        this.B = f2;
    }

    public void setVerticalBiasPercent(float f2) {
        this.C = f2;
    }

    public void setMinWidth(int i2) {
        if (i2 < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = i2;
        }
    }

    public void setMinHeight(int i2) {
        if (i2 < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = i2;
        }
    }

    public void setWrapWidth(int i2) {
        this.S = i2;
    }

    public void setWrapHeight(int i2) {
        this.T = i2;
    }

    public void setDimension(int i2, int i3) {
        this.y = i2;
        if (this.y < this.mMinWidth) {
            this.y = this.mMinWidth;
        }
        this.z = i3;
        if (this.z < this.mMinHeight) {
            this.z = this.mMinHeight;
        }
    }

    public void setFrame(int i2, int i3, int i4, int i5) {
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        this.mX = i2;
        this.mY = i3;
        if (this.W == 8) {
            this.y = 0;
            this.z = 0;
            return;
        }
        if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && i6 < this.y) {
            i6 = this.y;
        }
        if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && i7 < this.z) {
            i7 = this.z;
        }
        this.y = i6;
        this.z = i7;
        if (this.z < this.mMinHeight) {
            this.z = this.mMinHeight;
        }
        if (this.y < this.mMinWidth) {
            this.y = this.mMinWidth;
        }
    }

    public void setHorizontalDimension(int i2, int i3) {
        this.mX = i2;
        this.y = i3 - i2;
        if (this.y < this.mMinWidth) {
            this.y = this.mMinWidth;
        }
    }

    public void setVerticalDimension(int i2, int i3) {
        this.mY = i2;
        this.z = i3 - i2;
        if (this.z < this.mMinHeight) {
            this.z = this.mMinHeight;
        }
    }

    public void setBaselineDistance(int i2) {
        this.A = i2;
    }

    public void setCompanionWidget(Object obj) {
        this.U = obj;
    }

    public void setContainerItemSkip(int i2) {
        if (i2 >= 0) {
            this.V = i2;
        } else {
            this.V = 0;
        }
    }

    public int getContainerItemSkip() {
        return this.V;
    }

    public void setHorizontalWeight(float f2) {
        this.J[0] = f2;
    }

    public void setVerticalWeight(float f2) {
        this.J[1] = f2;
    }

    public void setHorizontalChainStyle(int i2) {
        this.F = i2;
    }

    public int getHorizontalChainStyle() {
        return this.F;
    }

    public void setVerticalChainStyle(int i2) {
        this.G = i2;
    }

    public int getVerticalChainStyle() {
        return this.G;
    }

    public boolean allowedInBarrier() {
        return this.W != 8;
    }

    public void immediateConnect(Type type, ConstraintWidget constraintWidget, Type type2, int i2, int i3) {
        getAnchor(type).connect(constraintWidget.getAnchor(type2), i2, i3, Strength.STRONG, 0, true);
    }

    public void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i2, int i3) {
        connect(constraintAnchor, constraintAnchor2, i2, Strength.STRONG, i3);
    }

    public void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i2) {
        connect(constraintAnchor, constraintAnchor2, i2, Strength.STRONG, 0);
    }

    public void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i2, Strength strength, int i3) {
        if (constraintAnchor.getOwner() == this) {
            connect(constraintAnchor.getType(), constraintAnchor2.getOwner(), constraintAnchor2.getType(), i2, strength, i3);
        }
    }

    public void connect(Type type, ConstraintWidget constraintWidget, Type type2, int i2) {
        connect(type, constraintWidget, type2, i2, Strength.STRONG);
    }

    public void connect(Type type, ConstraintWidget constraintWidget, Type type2) {
        connect(type, constraintWidget, type2, 0, Strength.STRONG);
    }

    public void connect(Type type, ConstraintWidget constraintWidget, Type type2, int i2, Strength strength) {
        connect(type, constraintWidget, type2, i2, strength, 0);
    }

    public void connect(Type type, ConstraintWidget constraintWidget, Type type2, int i2, Strength strength, int i3) {
        boolean z2;
        Type type3 = type;
        ConstraintWidget constraintWidget2 = constraintWidget;
        Type type4 = type2;
        int i4 = i3;
        int i5 = 0;
        if (type3 == Type.CENTER) {
            if (type4 == Type.CENTER) {
                ConstraintAnchor anchor = getAnchor(Type.LEFT);
                ConstraintAnchor anchor2 = getAnchor(Type.RIGHT);
                ConstraintAnchor anchor3 = getAnchor(Type.TOP);
                ConstraintAnchor anchor4 = getAnchor(Type.BOTTOM);
                boolean z3 = true;
                if ((anchor == null || !anchor.isConnected()) && (anchor2 == null || !anchor2.isConnected())) {
                    ConstraintWidget constraintWidget3 = constraintWidget2;
                    Strength strength2 = strength;
                    int i6 = i4;
                    connect(Type.LEFT, constraintWidget3, Type.LEFT, 0, strength2, i6);
                    connect(Type.RIGHT, constraintWidget3, Type.RIGHT, 0, strength2, i6);
                    z2 = true;
                } else {
                    z2 = false;
                }
                if ((anchor3 == null || !anchor3.isConnected()) && (anchor4 == null || !anchor4.isConnected())) {
                    ConstraintWidget constraintWidget4 = constraintWidget2;
                    Strength strength3 = strength;
                    int i7 = i4;
                    connect(Type.TOP, constraintWidget4, Type.TOP, 0, strength3, i7);
                    connect(Type.BOTTOM, constraintWidget4, Type.BOTTOM, 0, strength3, i7);
                } else {
                    z3 = false;
                }
                if (z2 && z3) {
                    getAnchor(Type.CENTER).connect(constraintWidget2.getAnchor(Type.CENTER), 0, i4);
                } else if (z2) {
                    getAnchor(Type.CENTER_X).connect(constraintWidget2.getAnchor(Type.CENTER_X), 0, i4);
                } else if (z3) {
                    getAnchor(Type.CENTER_Y).connect(constraintWidget2.getAnchor(Type.CENTER_Y), 0, i4);
                }
            } else if (type4 == Type.LEFT || type4 == Type.RIGHT) {
                ConstraintWidget constraintWidget5 = constraintWidget2;
                Type type5 = type4;
                Strength strength4 = strength;
                int i8 = i4;
                connect(Type.LEFT, constraintWidget5, type5, 0, strength4, i8);
                connect(Type.RIGHT, constraintWidget5, type5, 0, strength4, i8);
                getAnchor(Type.CENTER).connect(constraintWidget.getAnchor(type2), 0, i4);
            } else if (type4 == Type.TOP || type4 == Type.BOTTOM) {
                ConstraintWidget constraintWidget6 = constraintWidget2;
                Type type6 = type4;
                Strength strength5 = strength;
                int i9 = i4;
                connect(Type.TOP, constraintWidget6, type6, 0, strength5, i9);
                connect(Type.BOTTOM, constraintWidget6, type6, 0, strength5, i9);
                getAnchor(Type.CENTER).connect(constraintWidget.getAnchor(type2), 0, i4);
            }
        } else if (type3 == Type.CENTER_X && (type4 == Type.LEFT || type4 == Type.RIGHT)) {
            ConstraintAnchor anchor5 = getAnchor(Type.LEFT);
            ConstraintAnchor anchor6 = constraintWidget.getAnchor(type2);
            ConstraintAnchor anchor7 = getAnchor(Type.RIGHT);
            anchor5.connect(anchor6, 0, i4);
            anchor7.connect(anchor6, 0, i4);
            getAnchor(Type.CENTER_X).connect(anchor6, 0, i4);
        } else if (type3 == Type.CENTER_Y && (type4 == Type.TOP || type4 == Type.BOTTOM)) {
            ConstraintAnchor anchor8 = constraintWidget.getAnchor(type2);
            getAnchor(Type.TOP).connect(anchor8, 0, i4);
            getAnchor(Type.BOTTOM).connect(anchor8, 0, i4);
            getAnchor(Type.CENTER_Y).connect(anchor8, 0, i4);
        } else if (type3 == Type.CENTER_X && type4 == Type.CENTER_X) {
            getAnchor(Type.LEFT).connect(constraintWidget2.getAnchor(Type.LEFT), 0, i4);
            getAnchor(Type.RIGHT).connect(constraintWidget2.getAnchor(Type.RIGHT), 0, i4);
            getAnchor(Type.CENTER_X).connect(constraintWidget.getAnchor(type2), 0, i4);
        } else if (type3 == Type.CENTER_Y && type4 == Type.CENTER_Y) {
            getAnchor(Type.TOP).connect(constraintWidget2.getAnchor(Type.TOP), 0, i4);
            getAnchor(Type.BOTTOM).connect(constraintWidget2.getAnchor(Type.BOTTOM), 0, i4);
            getAnchor(Type.CENTER_Y).connect(constraintWidget.getAnchor(type2), 0, i4);
        } else {
            ConstraintAnchor anchor9 = getAnchor(type);
            ConstraintAnchor anchor10 = constraintWidget.getAnchor(type2);
            if (anchor9.isValidConnection(anchor10)) {
                if (type3 == Type.BASELINE) {
                    ConstraintAnchor anchor11 = getAnchor(Type.TOP);
                    ConstraintAnchor anchor12 = getAnchor(Type.BOTTOM);
                    if (anchor11 != null) {
                        anchor11.reset();
                    }
                    if (anchor12 != null) {
                        anchor12.reset();
                    }
                } else {
                    if (type3 == Type.TOP || type3 == Type.BOTTOM) {
                        ConstraintAnchor anchor13 = getAnchor(Type.BASELINE);
                        if (anchor13 != null) {
                            anchor13.reset();
                        }
                        ConstraintAnchor anchor14 = getAnchor(Type.CENTER);
                        if (anchor14.getTarget() != anchor10) {
                            anchor14.reset();
                        }
                        ConstraintAnchor opposite = getAnchor(type).getOpposite();
                        ConstraintAnchor anchor15 = getAnchor(Type.CENTER_Y);
                        if (anchor15.isConnected()) {
                            opposite.reset();
                            anchor15.reset();
                        }
                    } else if (type3 == Type.LEFT || type3 == Type.RIGHT) {
                        ConstraintAnchor anchor16 = getAnchor(Type.CENTER);
                        if (anchor16.getTarget() != anchor10) {
                            anchor16.reset();
                        }
                        ConstraintAnchor opposite2 = getAnchor(type).getOpposite();
                        ConstraintAnchor anchor17 = getAnchor(Type.CENTER_X);
                        if (anchor17.isConnected()) {
                            opposite2.reset();
                            anchor17.reset();
                        }
                    }
                    i5 = i2;
                }
                anchor9.connect(anchor10, i5, strength, i4);
                anchor10.getOwner().connectedTo(anchor9.getOwner());
            }
        }
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
        if (!(this instanceof ConstraintWidgetContainer)) {
            if (getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) {
                if (getWidth() == getWrapWidth()) {
                    setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
                } else if (getWidth() > getMinWidth()) {
                    setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
            }
            if (getVerticalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) {
                if (getHeight() == getWrapHeight()) {
                    setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
                } else if (getHeight() > getMinHeight()) {
                    setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
            }
        }
    }

    public void resetAnchor(ConstraintAnchor constraintAnchor) {
        if (getParent() == null || !(getParent() instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            ConstraintAnchor anchor = getAnchor(Type.LEFT);
            ConstraintAnchor anchor2 = getAnchor(Type.RIGHT);
            ConstraintAnchor anchor3 = getAnchor(Type.TOP);
            ConstraintAnchor anchor4 = getAnchor(Type.BOTTOM);
            ConstraintAnchor anchor5 = getAnchor(Type.CENTER);
            ConstraintAnchor anchor6 = getAnchor(Type.CENTER_X);
            ConstraintAnchor anchor7 = getAnchor(Type.CENTER_Y);
            if (constraintAnchor == anchor5) {
                if (anchor.isConnected() && anchor2.isConnected() && anchor.getTarget() == anchor2.getTarget()) {
                    anchor.reset();
                    anchor2.reset();
                }
                if (anchor3.isConnected() && anchor4.isConnected() && anchor3.getTarget() == anchor4.getTarget()) {
                    anchor3.reset();
                    anchor4.reset();
                }
                this.B = 0.5f;
                this.C = 0.5f;
            } else if (constraintAnchor == anchor6) {
                if (anchor.isConnected() && anchor2.isConnected() && anchor.getTarget().getOwner() == anchor2.getTarget().getOwner()) {
                    anchor.reset();
                    anchor2.reset();
                }
                this.B = 0.5f;
            } else if (constraintAnchor == anchor7) {
                if (anchor3.isConnected() && anchor4.isConnected() && anchor3.getTarget().getOwner() == anchor4.getTarget().getOwner()) {
                    anchor3.reset();
                    anchor4.reset();
                }
                this.C = 0.5f;
            } else if (constraintAnchor == anchor || constraintAnchor == anchor2) {
                if (anchor.isConnected() && anchor.getTarget() == anchor2.getTarget()) {
                    anchor5.reset();
                }
            } else if ((constraintAnchor == anchor3 || constraintAnchor == anchor4) && anchor3.isConnected() && anchor3.getTarget() == anchor4.getTarget()) {
                anchor5.reset();
            }
            constraintAnchor.reset();
        }
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int size = this.mAnchors.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((ConstraintAnchor) this.mAnchors.get(i2)).reset();
            }
        }
    }

    public void resetAnchors(int i2) {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int size = this.mAnchors.size();
            for (int i3 = 0; i3 < size; i3++) {
                ConstraintAnchor constraintAnchor = (ConstraintAnchor) this.mAnchors.get(i3);
                if (i2 == constraintAnchor.getConnectionCreator()) {
                    if (constraintAnchor.isVerticalAnchor()) {
                        setVerticalBiasPercent(DEFAULT_BIAS);
                    } else {
                        setHorizontalBiasPercent(DEFAULT_BIAS);
                    }
                    constraintAnchor.reset();
                }
            }
        }
    }

    public void disconnectWidget(ConstraintWidget constraintWidget) {
        ArrayList anchors = getAnchors();
        int size = anchors.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) anchors.get(i2);
            if (constraintAnchor.isConnected() && constraintAnchor.getTarget().getOwner() == constraintWidget) {
                constraintAnchor.reset();
            }
        }
    }

    public void disconnectUnlockedWidget(ConstraintWidget constraintWidget) {
        ArrayList anchors = getAnchors();
        int size = anchors.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) anchors.get(i2);
            if (constraintAnchor.isConnected() && constraintAnchor.getTarget().getOwner() == constraintWidget && constraintAnchor.getConnectionCreator() == 2) {
                constraintAnchor.reset();
            }
        }
    }

    public ConstraintAnchor getAnchor(Type type) {
        switch (type) {
            case LEFT:
                return this.p;
            case TOP:
                return this.q;
            case RIGHT:
                return this.r;
            case BOTTOM:
                return this.s;
            case BASELINE:
                return this.t;
            case CENTER:
                return this.w;
            case CENTER_X:
                return this.u;
            case CENTER_Y:
                return this.v;
            case NONE:
                return null;
            default:
                throw new AssertionError(type.name());
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
        if (dimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            setWidth(this.S);
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
        if (dimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            setHeight(this.T);
        }
    }

    public boolean isInHorizontalChain() {
        return (this.p.c != null && this.p.c.c == this.p) || (this.r.c != null && this.r.c.c == this.r);
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        ConstraintAnchor constraintAnchor;
        ConstraintWidget constraintWidget;
        ConstraintAnchor constraintAnchor2;
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget constraintWidget2 = this;
        ConstraintWidget constraintWidget3 = null;
        while (constraintWidget3 == null && constraintWidget2 != null) {
            ConstraintAnchor anchor = constraintWidget2.getAnchor(Type.LEFT);
            if (anchor == null) {
                constraintAnchor = null;
            } else {
                constraintAnchor = anchor.getTarget();
            }
            if (constraintAnchor == null) {
                constraintWidget = null;
            } else {
                constraintWidget = constraintAnchor.getOwner();
            }
            if (constraintWidget == getParent()) {
                return constraintWidget2;
            }
            if (constraintWidget == null) {
                constraintAnchor2 = null;
            } else {
                constraintAnchor2 = constraintWidget.getAnchor(Type.RIGHT).getTarget();
            }
            if (constraintAnchor2 == null || constraintAnchor2.getOwner() == constraintWidget2) {
                constraintWidget2 = constraintWidget;
            } else {
                constraintWidget3 = constraintWidget2;
            }
        }
        return constraintWidget3;
    }

    public boolean isInVerticalChain() {
        return (this.q.c != null && this.q.c.c == this.q) || (this.s.c != null && this.s.c.c == this.s);
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        ConstraintAnchor constraintAnchor;
        ConstraintWidget constraintWidget;
        ConstraintAnchor constraintAnchor2;
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget constraintWidget2 = this;
        ConstraintWidget constraintWidget3 = null;
        while (constraintWidget3 == null && constraintWidget2 != null) {
            ConstraintAnchor anchor = constraintWidget2.getAnchor(Type.TOP);
            if (anchor == null) {
                constraintAnchor = null;
            } else {
                constraintAnchor = anchor.getTarget();
            }
            if (constraintAnchor == null) {
                constraintWidget = null;
            } else {
                constraintWidget = constraintAnchor.getOwner();
            }
            if (constraintWidget == getParent()) {
                return constraintWidget2;
            }
            if (constraintWidget == null) {
                constraintAnchor2 = null;
            } else {
                constraintAnchor2 = constraintWidget.getAnchor(Type.BOTTOM).getTarget();
            }
            if (constraintAnchor2 == null || constraintAnchor2.getOwner() == constraintWidget2) {
                constraintWidget2 = constraintWidget;
            } else {
                constraintWidget3 = constraintWidget2;
            }
        }
        return constraintWidget3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0231, code lost:
        if (r15.n == -1) goto L_0x0235;
     */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x022a  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x0238  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0244  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x025b  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x02cf  */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x02de A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x02df  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x0348  */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x0352  */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x0358  */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x0362  */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x039d  */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x03c6  */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x03d0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(android.support.constraint.solver.LinearSystem r42) {
        /*
            r41 = this;
            r15 = r41
            r14 = r42
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.p
            android.support.constraint.solver.SolverVariable r21 = r14.createObjectVariable(r0)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.r
            android.support.constraint.solver.SolverVariable r13 = r14.createObjectVariable(r0)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.q
            android.support.constraint.solver.SolverVariable r12 = r14.createObjectVariable(r0)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.s
            android.support.constraint.solver.SolverVariable r11 = r14.createObjectVariable(r0)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.t
            android.support.constraint.solver.SolverVariable r10 = r14.createObjectVariable(r0)
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            r1 = 8
            r9 = 1
            r8 = 0
            if (r0 == 0) goto L_0x0125
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            if (r0 == 0) goto L_0x003a
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r0.mListDimensionBehaviors
            r0 = r0[r8]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r2) goto L_0x003a
            r0 = 1
            goto L_0x003b
        L_0x003a:
            r0 = 0
        L_0x003b:
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r15.x
            if (r2 == 0) goto L_0x004b
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r15.x
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r2.mListDimensionBehaviors
            r2 = r2[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r2 != r3) goto L_0x004b
            r2 = 1
            goto L_0x004c
        L_0x004b:
            r2 = 0
        L_0x004c:
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.p
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            if (r3 == 0) goto L_0x0073
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.p
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.p
            if (r3 == r4) goto L_0x0073
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.r
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            if (r3 == 0) goto L_0x0073
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.r
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.r
            if (r3 != r4) goto L_0x0073
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r15.x
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = (android.support.constraint.solver.widgets.ConstraintWidgetContainer) r3
            r3.a(r15, r8)
        L_0x0073:
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.p
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            if (r3 == 0) goto L_0x0083
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.p
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.p
            if (r3 == r4) goto L_0x0093
        L_0x0083:
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.r
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            if (r3 == 0) goto L_0x0095
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.r
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.r
            if (r3 != r4) goto L_0x0095
        L_0x0093:
            r3 = 1
            goto L_0x0096
        L_0x0095:
            r3 = 0
        L_0x0096:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.q
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            if (r4 == 0) goto L_0x00bd
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.q
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.q
            if (r4 == r5) goto L_0x00bd
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.s
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            if (r4 == 0) goto L_0x00bd
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.s
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.s
            if (r4 != r5) goto L_0x00bd
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.x
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r4 = (android.support.constraint.solver.widgets.ConstraintWidgetContainer) r4
            r4.a(r15, r9)
        L_0x00bd:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.q
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            if (r4 == 0) goto L_0x00cd
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.q
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.q
            if (r4 == r5) goto L_0x00dd
        L_0x00cd:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.s
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            if (r4 == 0) goto L_0x00df
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.s
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.s
            if (r4 != r5) goto L_0x00df
        L_0x00dd:
            r4 = 1
            goto L_0x00e0
        L_0x00df:
            r4 = 0
        L_0x00e0:
            if (r0 == 0) goto L_0x00fd
            int r5 = r15.W
            if (r5 == r1) goto L_0x00fd
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.p
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.c
            if (r5 != 0) goto L_0x00fd
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.r
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.c
            if (r5 != 0) goto L_0x00fd
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.r
            android.support.constraint.solver.SolverVariable r5 = r14.createObjectVariable(r5)
            r14.addGreaterThan(r5, r13, r8, r9)
        L_0x00fd:
            if (r2 == 0) goto L_0x011e
            int r5 = r15.W
            if (r5 == r1) goto L_0x011e
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.q
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.c
            if (r5 != 0) goto L_0x011e
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.s
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.c
            if (r5 != 0) goto L_0x011e
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.t
            if (r5 != 0) goto L_0x011e
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.s
            android.support.constraint.solver.SolverVariable r5 = r14.createObjectVariable(r5)
            r14.addGreaterThan(r5, r11, r8, r9)
        L_0x011e:
            r7 = r2
            r16 = r3
            r22 = r4
            r2 = r0
            goto L_0x012b
        L_0x0125:
            r2 = 0
            r7 = 0
            r16 = 0
            r22 = 0
        L_0x012b:
            int r0 = r15.y
            int r3 = r15.mMinWidth
            if (r0 >= r3) goto L_0x0133
            int r0 = r15.mMinWidth
        L_0x0133:
            int r3 = r15.z
            int r4 = r15.mMinHeight
            if (r3 >= r4) goto L_0x013b
            int r3 = r15.mMinHeight
        L_0x013b:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r15.mListDimensionBehaviors
            r4 = r4[r8]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 == r5) goto L_0x0145
            r4 = 1
            goto L_0x0146
        L_0x0145:
            r4 = 0
        L_0x0146:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r15.mListDimensionBehaviors
            r5 = r5[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 == r6) goto L_0x0150
            r5 = 1
            goto L_0x0151
        L_0x0150:
            r5 = 0
        L_0x0151:
            int r6 = r15.mDimensionRatioSide
            r15.n = r6
            float r6 = r15.mDimensionRatio
            r15.o = r6
            int r6 = r15.c
            int r9 = r15.d
            float r8 = r15.mDimensionRatio
            r17 = 0
            int r8 = (r8 > r17 ? 1 : (r8 == r17 ? 0 : -1))
            r17 = 4
            if (r8 <= 0) goto L_0x0212
            int r8 = r15.W
            r1 = 8
            if (r8 == r1) goto L_0x0212
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r8 = 0
            r1 = r1[r8]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r27 = r0
            r0 = 3
            if (r1 != r8) goto L_0x017c
            if (r6 != 0) goto L_0x017c
            r6 = 3
        L_0x017c:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r8 = 1
            r1 = r1[r8]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r8) goto L_0x0188
            if (r9 != 0) goto L_0x0188
            r9 = 3
        L_0x0188:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r8 = 0
            r1 = r1[r8]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r8) goto L_0x01a3
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r8 = 1
            r1 = r1[r8]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r8) goto L_0x01a3
            if (r6 != r0) goto L_0x01a3
            if (r9 != r0) goto L_0x01a3
            r15.setupDimensionRatio(r2, r7, r4, r5)
            goto L_0x0207
        L_0x01a3:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r4 = 0
            r1 = r1[r4]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r5) goto L_0x01d1
            if (r6 != r0) goto L_0x01d1
            r15.n = r4
            float r0 = r15.o
            int r1 = r15.z
            float r1 = (float) r1
            float r0 = r0 * r1
            int r0 = (int) r0
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r4 = 1
            r1 = r1[r4]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 == r5) goto L_0x01c8
            r18 = r0
            r28 = r3
            r25 = r9
            goto L_0x021c
        L_0x01c8:
            r18 = r0
            r28 = r3
            r17 = r6
            r25 = r9
            goto L_0x020f
        L_0x01d1:
            r4 = 1
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r1 = r1[r4]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r5) goto L_0x0207
            if (r9 != r0) goto L_0x0207
            r15.n = r4
            int r0 = r15.mDimensionRatioSide
            r1 = -1
            if (r0 != r1) goto L_0x01ea
            r0 = 1065353216(0x3f800000, float:1.0)
            float r1 = r15.o
            float r0 = r0 / r1
            r15.o = r0
        L_0x01ea:
            float r0 = r15.o
            int r1 = r15.y
            float r1 = (float) r1
            float r0 = r0 * r1
            int r0 = (int) r0
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r3 = 0
            r1 = r1[r3]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 == r3) goto L_0x0204
            r28 = r0
            r17 = r6
            r18 = r27
            r25 = 4
            goto L_0x021c
        L_0x0204:
            r28 = r0
            goto L_0x0209
        L_0x0207:
            r28 = r3
        L_0x0209:
            r17 = r6
            r25 = r9
            r18 = r27
        L_0x020f:
            r27 = 1
            goto L_0x021e
        L_0x0212:
            r27 = r0
            r28 = r3
            r17 = r6
            r25 = r9
            r18 = r27
        L_0x021c:
            r27 = 0
        L_0x021e:
            int[] r0 = r15.e
            r1 = 0
            r0[r1] = r17
            int[] r0 = r15.e
            r1 = 1
            r0[r1] = r25
            if (r27 == 0) goto L_0x0238
            int r0 = r15.n
            if (r0 == 0) goto L_0x0234
            int r0 = r15.n
            r1 = -1
            if (r0 != r1) goto L_0x0239
            goto L_0x0235
        L_0x0234:
            r1 = -1
        L_0x0235:
            r19 = 1
            goto L_0x023b
        L_0x0238:
            r1 = -1
        L_0x0239:
            r19 = 0
        L_0x023b:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r3 = 0
            r0 = r0[r3]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r3) goto L_0x024a
            boolean r0 = r15 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x024a
            r6 = 1
            goto L_0x024b
        L_0x024a:
            r6 = 0
        L_0x024b:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.w
            boolean r0 = r0.isConnected()
            r9 = 1
            r23 = r0 ^ 1
            int r0 = r15.mHorizontalResolution
            r8 = 2
            r26 = 0
            if (r0 == r8) goto L_0x02cf
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            if (r0 == 0) goto L_0x0269
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.r
            android.support.constraint.solver.SolverVariable r0 = r14.createObjectVariable(r0)
            r4 = r0
            goto L_0x026b
        L_0x0269:
            r4 = r26
        L_0x026b:
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            if (r0 == 0) goto L_0x0279
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.p
            android.support.constraint.solver.SolverVariable r0 = r14.createObjectVariable(r0)
            r3 = r0
            goto L_0x027b
        L_0x0279:
            r3 = r26
        L_0x027b:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r20 = 0
            r5 = r0[r20]
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.p
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r15.r
            int r9 = r15.mX
            r31 = r11
            int r11 = r15.mMinWidth
            int[] r1 = r15.M
            r24 = r1[r20]
            float r1 = r15.B
            r33 = r13
            int r13 = r15.f
            r34 = r13
            int r13 = r15.g
            r35 = r13
            float r13 = r15.h
            r36 = r0
            r0 = r15
            r32 = r1
            r1 = r14
            r37 = r7
            r7 = r36
            r38 = r10
            r10 = r18
            r29 = r31
            r39 = r12
            r12 = r24
            r30 = r13
            r24 = r33
            r18 = r34
            r20 = r35
            r13 = r32
            r14 = r19
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r20
            r19 = r30
            r20 = r23
            r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r15 = r41
            goto L_0x02d9
        L_0x02cf:
            r37 = r7
            r38 = r10
            r29 = r11
            r39 = r12
            r24 = r13
        L_0x02d9:
            int r0 = r15.mVerticalResolution
            r1 = 2
            if (r0 != r1) goto L_0x02df
            return
        L_0x02df:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r14 = 1
            r0 = r0[r14]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r1) goto L_0x02ee
            boolean r0 = r15 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x02ee
            r6 = 1
            goto L_0x02ef
        L_0x02ee:
            r6 = 0
        L_0x02ef:
            if (r27 == 0) goto L_0x02fd
            int r0 = r15.n
            if (r0 == r14) goto L_0x02fa
            int r0 = r15.n
            r1 = -1
            if (r0 != r1) goto L_0x02fd
        L_0x02fa:
            r16 = 1
            goto L_0x02ff
        L_0x02fd:
            r16 = 0
        L_0x02ff:
            int r0 = r15.A
            if (r0 <= 0) goto L_0x033e
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.t
            android.support.constraint.solver.widgets.ResolutionAnchor r0 = r0.getResolutionNode()
            int r0 = r0.i
            if (r0 != r14) goto L_0x031b
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.t
            android.support.constraint.solver.widgets.ResolutionAnchor r0 = r0.getResolutionNode()
            r13 = r42
            r0.a(r13)
            r12 = r39
            goto L_0x0342
        L_0x031b:
            r13 = r42
            int r0 = r41.getBaselineDistance()
            r1 = 6
            r2 = r38
            r12 = r39
            r13.addEquality(r2, r12, r0, r1)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.t
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.c
            if (r0 == 0) goto L_0x0342
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.t
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.c
            android.support.constraint.solver.SolverVariable r0 = r13.createObjectVariable(r0)
            r3 = 0
            r13.addEquality(r2, r0, r3, r1)
            r20 = 0
            goto L_0x0344
        L_0x033e:
            r12 = r39
            r13 = r42
        L_0x0342:
            r20 = r23
        L_0x0344:
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            if (r0 == 0) goto L_0x0352
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.s
            android.support.constraint.solver.SolverVariable r0 = r13.createObjectVariable(r0)
            r4 = r0
            goto L_0x0354
        L_0x0352:
            r4 = r26
        L_0x0354:
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            if (r0 == 0) goto L_0x0362
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.q
            android.support.constraint.solver.SolverVariable r0 = r13.createObjectVariable(r0)
            r3 = r0
            goto L_0x0364
        L_0x0362:
            r3 = r26
        L_0x0364:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r5 = r0[r14]
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r15.q
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r15.s
            int r9 = r15.mY
            int r11 = r15.mMinHeight
            int[] r0 = r15.M
            r17 = r0[r14]
            float r10 = r15.C
            int r2 = r15.i
            int r1 = r15.j
            float r0 = r15.k
            r19 = r0
            r0 = r15
            r18 = r1
            r1 = r13
            r23 = r2
            r2 = r37
            r26 = r10
            r10 = r28
            r28 = r12
            r12 = r17
            r13 = r26
            r14 = r16
            r15 = r22
            r16 = r25
            r17 = r23
            r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            if (r27 == 0) goto L_0x03c6
            r6 = 6
            r7 = r41
            int r0 = r7.n
            r1 = 1
            if (r0 != r1) goto L_0x03b6
            float r5 = r7.o
            r6 = 6
            r0 = r42
            r1 = r29
            r2 = r28
            r3 = r24
            r4 = r21
            r0.addRatio(r1, r2, r3, r4, r5, r6)
            goto L_0x03c8
        L_0x03b6:
            float r5 = r7.o
            r0 = r42
            r1 = r24
            r2 = r21
            r3 = r29
            r4 = r28
            r0.addRatio(r1, r2, r3, r4, r5, r6)
            goto L_0x03c8
        L_0x03c6:
            r7 = r41
        L_0x03c8:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.w
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x03f0
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.w
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.getTarget()
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.getOwner()
            float r1 = r7.N
            r2 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 + r2
            double r1 = (double) r1
            double r1 = java.lang.Math.toRadians(r1)
            float r1 = (float) r1
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r7.w
            int r2 = r2.getMargin()
            r3 = r42
            r3.addCenterPoint(r7, r0, r1, r2)
        L_0x03f0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidget.addToSolver(android.support.constraint.solver.LinearSystem):void");
    }

    public void setupDimensionRatio(boolean z2, boolean z3, boolean z4, boolean z5) {
        if (this.n == -1) {
            if (z4 && !z5) {
                this.n = 0;
            } else if (!z4 && z5) {
                this.n = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.o = 1.0f / this.o;
                }
            }
        }
        if (this.n == 0 && (!this.q.isConnected() || !this.s.isConnected())) {
            this.n = 1;
        } else if (this.n == 1 && (!this.p.isConnected() || !this.r.isConnected())) {
            this.n = 0;
        }
        if (this.n == -1 && (!this.q.isConnected() || !this.s.isConnected() || !this.p.isConnected() || !this.r.isConnected())) {
            if (this.q.isConnected() && this.s.isConnected()) {
                this.n = 0;
            } else if (this.p.isConnected() && this.r.isConnected()) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1) {
            if (z2 && !z3) {
                this.n = 0;
            } else if (!z2 && z3) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1) {
            if (this.f > 0 && this.i == 0) {
                this.n = 0;
            } else if (this.f == 0 && this.i > 0) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1 && z2 && z3) {
            this.o = 1.0f / this.o;
            this.n = 1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:159:0x02bd  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02d8  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02de  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x02f1  */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x02f4  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x02fd  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01f0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.support.constraint.solver.LinearSystem r33, boolean r34, android.support.constraint.solver.SolverVariable r35, android.support.constraint.solver.SolverVariable r36, android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour r37, boolean r38, android.support.constraint.solver.widgets.ConstraintAnchor r39, android.support.constraint.solver.widgets.ConstraintAnchor r40, int r41, int r42, int r43, int r44, float r45, boolean r46, boolean r47, int r48, int r49, int r50, float r51, boolean r52) {
        /*
            r32 = this;
            r0 = r32
            r10 = r33
            r11 = r35
            r12 = r36
            r1 = r43
            r2 = r44
            r13 = r39
            android.support.constraint.solver.SolverVariable r9 = r10.createObjectVariable(r13)
            r8 = r40
            android.support.constraint.solver.SolverVariable r7 = r10.createObjectVariable(r8)
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r39.getTarget()
            android.support.constraint.solver.SolverVariable r6 = r10.createObjectVariable(r6)
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r40.getTarget()
            android.support.constraint.solver.SolverVariable r14 = r10.createObjectVariable(r14)
            r20 = r14
            boolean r14 = r10.graphOptimizer
            r15 = 1
            if (r14 == 0) goto L_0x0069
            android.support.constraint.solver.widgets.ResolutionAnchor r14 = r39.getResolutionNode()
            int r14 = r14.i
            r11 = 1
            if (r14 != r11) goto L_0x0069
            android.support.constraint.solver.widgets.ResolutionAnchor r14 = r40.getResolutionNode()
            int r14 = r14.i
            if (r14 != r11) goto L_0x0069
            android.support.constraint.solver.Metrics r1 = android.support.constraint.solver.LinearSystem.getMetrics()
            if (r1 == 0) goto L_0x0051
            android.support.constraint.solver.Metrics r1 = android.support.constraint.solver.LinearSystem.getMetrics()
            long r2 = r1.resolvedWidgets
            long r5 = r2 + r15
            r1.resolvedWidgets = r5
        L_0x0051:
            android.support.constraint.solver.widgets.ResolutionAnchor r1 = r39.getResolutionNode()
            r1.a(r10)
            android.support.constraint.solver.widgets.ResolutionAnchor r1 = r40.getResolutionNode()
            r1.a(r10)
            if (r47 != 0) goto L_0x0068
            if (r34 == 0) goto L_0x0068
            r1 = 0
            r2 = 6
            r10.addGreaterThan(r12, r7, r1, r2)
        L_0x0068:
            return
        L_0x0069:
            android.support.constraint.solver.Metrics r11 = android.support.constraint.solver.LinearSystem.getMetrics()
            if (r11 == 0) goto L_0x007e
            android.support.constraint.solver.Metrics r11 = android.support.constraint.solver.LinearSystem.getMetrics()
            long r1 = r11.nonresolvedWidgets
            r22 = r6
            r21 = r7
            long r6 = r1 + r15
            r11.nonresolvedWidgets = r6
            goto L_0x0082
        L_0x007e:
            r22 = r6
            r21 = r7
        L_0x0082:
            boolean r1 = r39.isConnected()
            boolean r2 = r40.isConnected()
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r0.w
            boolean r6 = r6.isConnected()
            if (r1 == 0) goto L_0x0094
            r7 = 1
            goto L_0x0095
        L_0x0094:
            r7 = 0
        L_0x0095:
            if (r2 == 0) goto L_0x0099
            int r7 = r7 + 1
        L_0x0099:
            if (r6 == 0) goto L_0x009d
            int r7 = r7 + 1
        L_0x009d:
            if (r46 == 0) goto L_0x00a1
            r14 = 3
            goto L_0x00a3
        L_0x00a1:
            r14 = r48
        L_0x00a3:
            int[] r15 = android.support.constraint.solver.widgets.ConstraintWidget.AnonymousClass1.b
            int r16 = r37.ordinal()
            r15 = r15[r16]
            r11 = 4
            switch(r15) {
                case 1: goto L_0x00af;
                case 2: goto L_0x00af;
                case 3: goto L_0x00af;
                case 4: goto L_0x00b1;
                default: goto L_0x00af;
            }
        L_0x00af:
            r15 = 0
            goto L_0x00b5
        L_0x00b1:
            if (r14 != r11) goto L_0x00b4
            goto L_0x00af
        L_0x00b4:
            r15 = 1
        L_0x00b5:
            int r11 = r0.W
            r8 = 8
            if (r11 != r8) goto L_0x00be
            r8 = 0
            r11 = 0
            goto L_0x00c1
        L_0x00be:
            r8 = r42
            r11 = r15
        L_0x00c1:
            if (r52 == 0) goto L_0x00e2
            if (r1 != 0) goto L_0x00cf
            if (r2 != 0) goto L_0x00cf
            if (r6 != 0) goto L_0x00cf
            r12 = r41
            r10.addEquality(r9, r12)
            goto L_0x00e2
        L_0x00cf:
            if (r1 == 0) goto L_0x00e2
            if (r2 != 0) goto L_0x00e2
            int r12 = r39.getMargin()
            r24 = r2
            r23 = r6
            r6 = r22
            r2 = 6
            r10.addEquality(r9, r6, r12, r2)
            goto L_0x00e9
        L_0x00e2:
            r24 = r2
            r23 = r6
            r6 = r22
            r2 = 6
        L_0x00e9:
            if (r11 != 0) goto L_0x0122
            if (r38 == 0) goto L_0x010d
            r25 = r11
            r12 = r21
            r2 = 0
            r11 = 3
            r10.addEquality(r12, r9, r2, r11)
            r2 = r43
            if (r2 <= 0) goto L_0x00ff
            r11 = 6
            r10.addGreaterThan(r12, r9, r2, r11)
            goto L_0x0100
        L_0x00ff:
            r11 = 6
        L_0x0100:
            r8 = 2147483647(0x7fffffff, float:NaN)
            r26 = r6
            r6 = r44
            if (r6 >= r8) goto L_0x0119
            r10.addLowerThan(r12, r9, r6, r11)
            goto L_0x0119
        L_0x010d:
            r26 = r6
            r25 = r11
            r12 = r21
            r2 = r43
            r11 = 6
            r10.addEquality(r12, r9, r8, r11)
        L_0x0119:
            r2 = r49
            r11 = r50
            r0 = r14
            r6 = r20
            goto L_0x01fa
        L_0x0122:
            r26 = r6
            r25 = r11
            r12 = r21
            r2 = r43
            r6 = -2
            r11 = r49
            if (r11 != r6) goto L_0x0133
            r11 = r50
            r2 = r8
            goto L_0x0136
        L_0x0133:
            r2 = r11
            r11 = r50
        L_0x0136:
            if (r11 != r6) goto L_0x0139
            r11 = r8
        L_0x0139:
            if (r2 <= 0) goto L_0x014b
            if (r34 == 0) goto L_0x0142
            r6 = 6
            r10.addGreaterThan(r12, r9, r2, r6)
            goto L_0x0146
        L_0x0142:
            r6 = 6
            r10.addGreaterThan(r12, r9, r2, r6)
        L_0x0146:
            int r8 = java.lang.Math.max(r8, r2)
            goto L_0x014c
        L_0x014b:
            r6 = 6
        L_0x014c:
            if (r11 <= 0) goto L_0x015d
            if (r34 == 0) goto L_0x0156
            r6 = 1
            r10.addLowerThan(r12, r9, r11, r6)
            r6 = 6
            goto L_0x0159
        L_0x0156:
            r10.addLowerThan(r12, r9, r11, r6)
        L_0x0159:
            int r8 = java.lang.Math.min(r8, r11)
        L_0x015d:
            r6 = 1
            if (r14 != r6) goto L_0x0176
            if (r34 == 0) goto L_0x0168
            r6 = 6
            r10.addEquality(r12, r9, r8, r6)
            goto L_0x01e0
        L_0x0168:
            if (r47 == 0) goto L_0x0170
            r6 = 4
            r10.addEquality(r12, r9, r8, r6)
            goto L_0x01e0
        L_0x0170:
            r6 = 1
            r10.addEquality(r12, r9, r8, r6)
            goto L_0x01e0
        L_0x0176:
            r6 = 2
            if (r14 != r6) goto L_0x01e0
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r6 = r39.getType()
            r27 = r14
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            if (r6 == r14) goto L_0x01ab
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r6 = r39.getType()
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            if (r6 != r14) goto L_0x018c
            goto L_0x01ab
        L_0x018c:
            android.support.constraint.solver.widgets.ConstraintWidget r6 = r0.x
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.getAnchor(r14)
            android.support.constraint.solver.SolverVariable r6 = r10.createObjectVariable(r6)
            android.support.constraint.solver.widgets.ConstraintWidget r14 = r0.x
            r28 = r6
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r6 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r14.getAnchor(r6)
            android.support.constraint.solver.SolverVariable r6 = r10.createObjectVariable(r6)
            r17 = r6
            r18 = r28
            goto L_0x01c9
        L_0x01ab:
            android.support.constraint.solver.widgets.ConstraintWidget r6 = r0.x
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.getAnchor(r14)
            android.support.constraint.solver.SolverVariable r6 = r10.createObjectVariable(r6)
            android.support.constraint.solver.widgets.ConstraintWidget r14 = r0.x
            r29 = r6
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r6 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r14.getAnchor(r6)
            android.support.constraint.solver.SolverVariable r6 = r10.createObjectVariable(r6)
            r17 = r6
            r18 = r29
        L_0x01c9:
            android.support.constraint.solver.ArrayRow r14 = r33.createRow()
            r6 = r20
            r0 = r27
            r15 = r12
            r16 = r9
            r19 = r51
            android.support.constraint.solver.ArrayRow r14 = r14.createRowDimensionRatio(r15, r16, r17, r18, r19)
            r10.addConstraint(r14)
            r25 = 0
            goto L_0x01e3
        L_0x01e0:
            r0 = r14
            r6 = r20
        L_0x01e3:
            if (r25 == 0) goto L_0x01fa
            r14 = 2
            if (r7 == r14) goto L_0x01fa
            if (r46 != 0) goto L_0x01fa
            int r8 = java.lang.Math.max(r2, r8)
            if (r11 <= 0) goto L_0x01f4
            int r8 = java.lang.Math.min(r11, r8)
        L_0x01f4:
            r14 = 6
            r10.addEquality(r12, r9, r8, r14)
            r25 = 0
        L_0x01fa:
            if (r52 == 0) goto L_0x0303
            if (r47 == 0) goto L_0x0200
            goto L_0x0303
        L_0x0200:
            r4 = 5
            if (r1 != 0) goto L_0x0213
            if (r24 != 0) goto L_0x0213
            if (r23 != 0) goto L_0x0213
            if (r34 == 0) goto L_0x020f
            r5 = 0
            r14 = r36
            r10.addGreaterThan(r14, r12, r5, r4)
        L_0x020f:
            r2 = 6
        L_0x0210:
            r3 = 0
            goto L_0x02fb
        L_0x0213:
            r5 = 0
            r14 = r36
            if (r1 == 0) goto L_0x0220
            if (r24 != 0) goto L_0x0220
            if (r34 == 0) goto L_0x020f
            r10.addGreaterThan(r14, r12, r5, r4)
            goto L_0x020f
        L_0x0220:
            if (r1 != 0) goto L_0x0235
            if (r24 == 0) goto L_0x0235
            int r0 = r40.getMargin()
            int r0 = -r0
            r1 = 6
            r10.addEquality(r12, r6, r0, r1)
            if (r34 == 0) goto L_0x020f
            r8 = r35
            r10.addGreaterThan(r9, r8, r5, r4)
            goto L_0x020f
        L_0x0235:
            r7 = 1
            r8 = r35
            if (r1 == 0) goto L_0x020f
            if (r24 == 0) goto L_0x020f
            if (r25 == 0) goto L_0x02a4
            if (r34 == 0) goto L_0x0248
            r1 = r43
            if (r1 != 0) goto L_0x0248
            r1 = 6
            r10.addGreaterThan(r12, r9, r5, r1)
        L_0x0248:
            if (r0 != 0) goto L_0x0273
            if (r11 > 0) goto L_0x0252
            if (r2 <= 0) goto L_0x024f
            goto L_0x0252
        L_0x024f:
            r0 = 6
            r1 = 0
            goto L_0x0254
        L_0x0252:
            r0 = 4
            r1 = 1
        L_0x0254:
            int r3 = r39.getMargin()
            r5 = r26
            r10.addEquality(r9, r5, r3, r0)
            int r3 = r40.getMargin()
            int r3 = -r3
            r10.addEquality(r12, r6, r3, r0)
            if (r11 > 0) goto L_0x026c
            if (r2 <= 0) goto L_0x026a
            goto L_0x026c
        L_0x026a:
            r11 = 0
            goto L_0x026d
        L_0x026c:
            r11 = 1
        L_0x026d:
            r15 = r1
            r7 = r11
            r0 = r32
            r11 = 5
            goto L_0x02bb
        L_0x0273:
            r5 = r26
            if (r0 != r7) goto L_0x027c
            r0 = r32
            r11 = 6
        L_0x027a:
            r15 = 1
            goto L_0x02bb
        L_0x027c:
            r1 = 3
            if (r0 != r1) goto L_0x02a0
            if (r46 != 0) goto L_0x028c
            r0 = r32
            int r1 = r0.n
            r2 = -1
            if (r1 == r2) goto L_0x028e
            if (r11 > 0) goto L_0x028e
            r1 = 6
            goto L_0x028f
        L_0x028c:
            r0 = r32
        L_0x028e:
            r1 = 4
        L_0x028f:
            int r2 = r39.getMargin()
            r10.addEquality(r9, r5, r2, r1)
            int r2 = r40.getMargin()
            int r2 = -r2
            r10.addEquality(r12, r6, r2, r1)
            r11 = 5
            goto L_0x027a
        L_0x02a0:
            r0 = r32
            r7 = 0
            goto L_0x02b9
        L_0x02a4:
            r5 = r26
            r0 = r32
            if (r34 == 0) goto L_0x02b9
            int r1 = r39.getMargin()
            r10.addGreaterThan(r9, r5, r1, r4)
            int r1 = r40.getMargin()
            int r1 = -r1
            r10.addLowerThan(r12, r6, r1, r4)
        L_0x02b9:
            r11 = 5
            r15 = 0
        L_0x02bb:
            if (r7 == 0) goto L_0x02d8
            int r4 = r39.getMargin()
            int r16 = r40.getMargin()
            r1 = r10
            r2 = r9
            r3 = r5
            r7 = r5
            r5 = r45
            r30 = r6
            r0 = r7
            r7 = r12
            r14 = r8
            r8 = r16
            r14 = r9
            r9 = r11
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x02dc
        L_0x02d8:
            r0 = r5
            r30 = r6
            r14 = r9
        L_0x02dc:
            if (r15 == 0) goto L_0x02f1
            int r1 = r39.getMargin()
            r2 = 6
            r10.addGreaterThan(r14, r0, r1, r2)
            int r0 = r40.getMargin()
            int r0 = -r0
            r1 = r30
            r10.addLowerThan(r12, r1, r0, r2)
            goto L_0x02f2
        L_0x02f1:
            r2 = 6
        L_0x02f2:
            if (r34 == 0) goto L_0x0210
            r1 = r14
            r0 = r35
            r3 = 0
            r10.addGreaterThan(r1, r0, r3, r2)
        L_0x02fb:
            if (r34 == 0) goto L_0x0302
            r0 = r36
            r10.addGreaterThan(r0, r12, r3, r2)
        L_0x0302:
            return
        L_0x0303:
            r1 = r9
            r0 = r36
            r2 = 6
            r3 = 0
            r4 = r35
            r5 = 2
            if (r7 >= r5) goto L_0x0315
            if (r34 == 0) goto L_0x0315
            r10.addGreaterThan(r1, r4, r3, r2)
            r10.addGreaterThan(r0, r12, r3, r2)
        L_0x0315:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidget.a(android.support.constraint.solver.LinearSystem, boolean, android.support.constraint.solver.SolverVariable, android.support.constraint.solver.SolverVariable, android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour, boolean, android.support.constraint.solver.widgets.ConstraintAnchor, android.support.constraint.solver.widgets.ConstraintAnchor, int, int, int, int, float, boolean, boolean, int, int, int, float, boolean):void");
    }

    public void updateFromSolver(LinearSystem linearSystem) {
        int objectVariableValue = linearSystem.getObjectVariableValue(this.p);
        int objectVariableValue2 = linearSystem.getObjectVariableValue(this.q);
        int objectVariableValue3 = linearSystem.getObjectVariableValue(this.r);
        int objectVariableValue4 = linearSystem.getObjectVariableValue(this.s);
        int i2 = objectVariableValue4 - objectVariableValue2;
        if (objectVariableValue3 - objectVariableValue < 0 || i2 < 0 || objectVariableValue == Integer.MIN_VALUE || objectVariableValue == Integer.MAX_VALUE || objectVariableValue2 == Integer.MIN_VALUE || objectVariableValue2 == Integer.MAX_VALUE || objectVariableValue3 == Integer.MIN_VALUE || objectVariableValue3 == Integer.MAX_VALUE || objectVariableValue4 == Integer.MIN_VALUE || objectVariableValue4 == Integer.MAX_VALUE) {
            objectVariableValue4 = 0;
            objectVariableValue = 0;
            objectVariableValue2 = 0;
            objectVariableValue3 = 0;
        }
        setFrame(objectVariableValue, objectVariableValue2, objectVariableValue3, objectVariableValue4);
    }
}
