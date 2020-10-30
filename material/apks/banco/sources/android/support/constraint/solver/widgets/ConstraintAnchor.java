package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;
import java.util.ArrayList;
import java.util.HashSet;

public class ConstraintAnchor {
    public static final int AUTO_CONSTRAINT_CREATOR = 2;
    public static final int SCOUT_CREATOR = 1;
    public static final int USER_CREATOR = 0;
    final ConstraintWidget a;
    final Type b;
    ConstraintAnchor c;
    int d = -1;
    SolverVariable e;
    private ResolutionAnchor f = new ResolutionAnchor(this);
    private Strength g = Strength.NONE;
    private ConnectionType h = ConnectionType.RELAXED;
    private int i = 0;
    public int mMargin = 0;

    public enum ConnectionType {
        RELAXED,
        STRICT
    }

    public enum Strength {
        NONE,
        STRONG,
        WEAK
    }

    public enum Type {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y
    }

    public ResolutionAnchor getResolutionNode() {
        return this.f;
    }

    public ConstraintAnchor(ConstraintWidget constraintWidget, Type type) {
        this.a = constraintWidget;
        this.b = type;
    }

    public SolverVariable getSolverVariable() {
        return this.e;
    }

    public void resetSolverVariable(Cache cache) {
        if (this.e == null) {
            this.e = new SolverVariable(android.support.constraint.solver.SolverVariable.Type.UNRESTRICTED, (String) null);
        } else {
            this.e.reset();
        }
    }

    public ConstraintWidget getOwner() {
        return this.a;
    }

    public Type getType() {
        return this.b;
    }

    public int getMargin() {
        if (this.a.getVisibility() == 8) {
            return 0;
        }
        if (this.d <= -1 || this.c == null || this.c.a.getVisibility() != 8) {
            return this.mMargin;
        }
        return this.d;
    }

    public Strength getStrength() {
        return this.g;
    }

    public ConstraintAnchor getTarget() {
        return this.c;
    }

    public ConnectionType getConnectionType() {
        return this.h;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.h = connectionType;
    }

    public int getConnectionCreator() {
        return this.i;
    }

    public void setConnectionCreator(int i2) {
        this.i = i2;
    }

    public void reset() {
        this.c = null;
        this.mMargin = 0;
        this.d = -1;
        this.g = Strength.STRONG;
        this.i = 0;
        this.h = ConnectionType.RELAXED;
        this.f.reset();
    }

    public boolean connect(ConstraintAnchor constraintAnchor, int i2, Strength strength, int i3) {
        return connect(constraintAnchor, i2, -1, strength, i3, false);
    }

    public boolean connect(ConstraintAnchor constraintAnchor, int i2, int i3, Strength strength, int i4, boolean z) {
        if (constraintAnchor == null) {
            this.c = null;
            this.mMargin = 0;
            this.d = -1;
            this.g = Strength.NONE;
            this.i = 2;
            return true;
        } else if (!z && !isValidConnection(constraintAnchor)) {
            return false;
        } else {
            this.c = constraintAnchor;
            if (i2 > 0) {
                this.mMargin = i2;
            } else {
                this.mMargin = 0;
            }
            this.d = i3;
            this.g = strength;
            this.i = i4;
            return true;
        }
    }

    public boolean connect(ConstraintAnchor constraintAnchor, int i2, int i3) {
        return connect(constraintAnchor, i2, -1, Strength.STRONG, i3, false);
    }

    public boolean connect(ConstraintAnchor constraintAnchor, int i2) {
        return connect(constraintAnchor, i2, -1, Strength.STRONG, 0, false);
    }

    public boolean isConnected() {
        return this.c != null;
    }

    public boolean isValidConnection(ConstraintAnchor constraintAnchor) {
        boolean z = false;
        if (constraintAnchor == null) {
            return false;
        }
        Type type = constraintAnchor.getType();
        if (type != this.b) {
            switch (this.b) {
                case CENTER:
                    if (!(type == Type.BASELINE || type == Type.CENTER_X || type == Type.CENTER_Y)) {
                        z = true;
                    }
                    return z;
                case LEFT:
                case RIGHT:
                    boolean z2 = type == Type.LEFT || type == Type.RIGHT;
                    if (constraintAnchor.getOwner() instanceof Guideline) {
                        z2 = z2 || type == Type.CENTER_X;
                    }
                    return z2;
                case TOP:
                case BOTTOM:
                    boolean z3 = type == Type.TOP || type == Type.BOTTOM;
                    if (constraintAnchor.getOwner() instanceof Guideline) {
                        z3 = z3 || type == Type.CENTER_Y;
                    }
                    return z3;
                case BASELINE:
                case CENTER_X:
                case CENTER_Y:
                case NONE:
                    return false;
                default:
                    throw new AssertionError(this.b.name());
            }
        } else if (this.b != Type.BASELINE || (constraintAnchor.getOwner().hasBaseline() && getOwner().hasBaseline())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSideAnchor() {
        switch (this.b) {
            case CENTER:
            case BASELINE:
            case CENTER_X:
            case CENTER_Y:
            case NONE:
                return false;
            case LEFT:
            case RIGHT:
            case TOP:
            case BOTTOM:
                return true;
            default:
                throw new AssertionError(this.b.name());
        }
    }

    public boolean isSimilarDimensionConnection(ConstraintAnchor constraintAnchor) {
        Type type = constraintAnchor.getType();
        boolean z = true;
        if (type == this.b) {
            return true;
        }
        switch (this.b) {
            case CENTER:
                if (type == Type.BASELINE) {
                    z = false;
                }
                return z;
            case LEFT:
            case RIGHT:
            case CENTER_X:
                if (!(type == Type.LEFT || type == Type.RIGHT || type == Type.CENTER_X)) {
                    z = false;
                }
                return z;
            case TOP:
            case BOTTOM:
            case BASELINE:
            case CENTER_Y:
                if (!(type == Type.TOP || type == Type.BOTTOM || type == Type.CENTER_Y || type == Type.BASELINE)) {
                    z = false;
                }
                return z;
            case NONE:
                return false;
            default:
                throw new AssertionError(this.b.name());
        }
    }

    public void setStrength(Strength strength) {
        if (isConnected()) {
            this.g = strength;
        }
    }

    public void setMargin(int i2) {
        if (isConnected()) {
            this.mMargin = i2;
        }
    }

    public void setGoneMargin(int i2) {
        if (isConnected()) {
            this.d = i2;
        }
    }

    public boolean isVerticalAnchor() {
        switch (this.b) {
            case CENTER:
            case LEFT:
            case RIGHT:
            case CENTER_X:
                return false;
            case TOP:
            case BOTTOM:
            case BASELINE:
            case CENTER_Y:
            case NONE:
                return true;
            default:
                throw new AssertionError(this.b.name());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getDebugName());
        sb.append(":");
        sb.append(this.b.toString());
        return sb.toString();
    }

    public int getSnapPriorityLevel() {
        switch (this.b) {
            case CENTER:
                return 3;
            case LEFT:
                return 1;
            case RIGHT:
                return 1;
            case TOP:
                return 0;
            case BOTTOM:
                return 0;
            case BASELINE:
                return 2;
            case CENTER_X:
                return 0;
            case CENTER_Y:
                return 1;
            case NONE:
                return 0;
            default:
                throw new AssertionError(this.b.name());
        }
    }

    public int getPriorityLevel() {
        switch (this.b) {
            case CENTER:
                return 2;
            case LEFT:
                return 2;
            case RIGHT:
                return 2;
            case TOP:
                return 2;
            case BOTTOM:
                return 2;
            case BASELINE:
                return 1;
            case CENTER_X:
                return 0;
            case CENTER_Y:
                return 0;
            case NONE:
                return 0;
            default:
                throw new AssertionError(this.b.name());
        }
    }

    public boolean isSnapCompatibleWith(ConstraintAnchor constraintAnchor) {
        if (this.b == Type.CENTER) {
            return false;
        }
        if (this.b == constraintAnchor.getType()) {
            return true;
        }
        switch (this.b) {
            case CENTER:
            case BASELINE:
            case NONE:
                return false;
            case LEFT:
                int i2 = AnonymousClass1.a[constraintAnchor.getType().ordinal()];
                if (i2 == 3 || i2 == 7) {
                    return true;
                }
                return false;
            case RIGHT:
                int i3 = AnonymousClass1.a[constraintAnchor.getType().ordinal()];
                if (i3 == 2 || i3 == 7) {
                    return true;
                }
                return false;
            case TOP:
                int i4 = AnonymousClass1.a[constraintAnchor.getType().ordinal()];
                if (i4 == 5 || i4 == 8) {
                    return true;
                }
                return false;
            case BOTTOM:
                int i5 = AnonymousClass1.a[constraintAnchor.getType().ordinal()];
                if (i5 == 4 || i5 == 8) {
                    return true;
                }
                return false;
            case CENTER_X:
                switch (constraintAnchor.getType()) {
                    case LEFT:
                        return true;
                    case RIGHT:
                        return true;
                    default:
                        return false;
                }
            case CENTER_Y:
                switch (constraintAnchor.getType()) {
                    case TOP:
                        return true;
                    case BOTTOM:
                        return true;
                    default:
                        return false;
                }
            default:
                throw new AssertionError(this.b.name());
        }
    }

    public boolean isConnectionAllowed(ConstraintWidget constraintWidget, ConstraintAnchor constraintAnchor) {
        return isConnectionAllowed(constraintWidget);
    }

    public boolean isConnectionAllowed(ConstraintWidget constraintWidget) {
        if (a(constraintWidget, new HashSet())) {
            return false;
        }
        ConstraintWidget parent = getOwner().getParent();
        if (parent == constraintWidget || constraintWidget.getParent() == parent) {
            return true;
        }
        return false;
    }

    private boolean a(ConstraintWidget constraintWidget, HashSet<ConstraintWidget> hashSet) {
        if (hashSet.contains(constraintWidget)) {
            return false;
        }
        hashSet.add(constraintWidget);
        if (constraintWidget == getOwner()) {
            return true;
        }
        ArrayList anchors = constraintWidget.getAnchors();
        int size = anchors.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) anchors.get(i2);
            if (constraintAnchor.isSimilarDimensionConnection(this) && constraintAnchor.isConnected() && a(constraintAnchor.getTarget().getOwner(), hashSet)) {
                return true;
            }
        }
        return false;
    }

    public final ConstraintAnchor getOpposite() {
        switch (this.b) {
            case CENTER:
            case BASELINE:
            case CENTER_X:
            case CENTER_Y:
            case NONE:
                return null;
            case LEFT:
                return this.a.r;
            case RIGHT:
                return this.a.p;
            case TOP:
                return this.a.s;
            case BOTTOM:
                return this.a.q;
            default:
                throw new AssertionError(this.b.name());
        }
    }
}
