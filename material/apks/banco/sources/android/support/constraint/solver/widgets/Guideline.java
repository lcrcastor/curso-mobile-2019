package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;

public class Guideline extends ConstraintWidget {
    public static final int HORIZONTAL = 0;
    public static final int RELATIVE_BEGIN = 1;
    public static final int RELATIVE_END = 2;
    public static final int RELATIVE_PERCENT = 0;
    public static final int RELATIVE_UNKNWON = -1;
    public static final int VERTICAL = 1;
    private ConstraintAnchor M = this.q;
    private int N;
    private boolean O;
    private int P;
    private Rectangle Q;
    private int R;
    protected int mRelativeBegin = -1;
    protected int mRelativeEnd = -1;
    protected float mRelativePercent = -1.0f;

    public boolean allowedInBarrier() {
        return true;
    }

    public String getType() {
        return "Guideline";
    }

    public Guideline() {
        this.N = 0;
        this.O = false;
        this.P = 0;
        this.Q = new Rectangle();
        this.R = 8;
        this.mAnchors.clear();
        this.mAnchors.add(this.M);
        int length = this.mListAnchors.length;
        for (int i = 0; i < length; i++) {
            this.mListAnchors[i] = this.M;
        }
    }

    public int getRelativeBehaviour() {
        if (this.mRelativePercent != -1.0f) {
            return 0;
        }
        if (this.mRelativeBegin != -1) {
            return 1;
        }
        if (this.mRelativeEnd != -1) {
            return 2;
        }
        return -1;
    }

    public Rectangle getHead() {
        this.Q.setBounds(getDrawX() - this.R, getDrawY() - (this.R * 2), this.R * 2, this.R * 2);
        if (getOrientation() == 0) {
            this.Q.setBounds(getDrawX() - (this.R * 2), getDrawY() - this.R, this.R * 2, this.R * 2);
        }
        return this.Q;
    }

    public void setOrientation(int i) {
        if (this.N != i) {
            this.N = i;
            this.mAnchors.clear();
            if (this.N == 1) {
                this.M = this.p;
            } else {
                this.M = this.q;
            }
            this.mAnchors.add(this.M);
            int length = this.mListAnchors.length;
            for (int i2 = 0; i2 < length; i2++) {
                this.mListAnchors[i2] = this.M;
            }
        }
    }

    public ConstraintAnchor getAnchor() {
        return this.M;
    }

    public int getOrientation() {
        return this.N;
    }

    public void setMinimumPosition(int i) {
        this.P = i;
    }

    public void setPositionRelaxed(boolean z) {
        if (this.O != z) {
            this.O = z;
        }
    }

    public ConstraintAnchor getAnchor(Type type) {
        switch (type) {
            case LEFT:
            case RIGHT:
                if (this.N == 1) {
                    return this.M;
                }
                break;
            case TOP:
            case BOTTOM:
                if (this.N == 0) {
                    return this.M;
                }
                break;
            case BASELINE:
            case CENTER:
            case CENTER_X:
            case CENTER_Y:
            case NONE:
                return null;
        }
        throw new AssertionError(type.name());
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setGuidePercent(int i) {
        setGuidePercent(((float) i) / 100.0f);
    }

    public void setGuidePercent(float f) {
        if (f > -1.0f) {
            this.mRelativePercent = f;
            this.mRelativeBegin = -1;
            this.mRelativeEnd = -1;
        }
    }

    public void setGuideBegin(int i) {
        if (i > -1) {
            this.mRelativePercent = -1.0f;
            this.mRelativeBegin = i;
            this.mRelativeEnd = -1;
        }
    }

    public void setGuideEnd(int i) {
        if (i > -1) {
            this.mRelativePercent = -1.0f;
            this.mRelativeBegin = -1;
            this.mRelativeEnd = i;
        }
    }

    public float getRelativePercent() {
        return this.mRelativePercent;
    }

    public int getRelativeBegin() {
        return this.mRelativeBegin;
    }

    public int getRelativeEnd() {
        return this.mRelativeEnd;
    }

    public void analyze(int i) {
        ConstraintWidget parent = getParent();
        if (parent != null) {
            if (getOrientation() == 1) {
                this.q.getResolutionNode().dependsOn(1, parent.q.getResolutionNode(), 0);
                this.s.getResolutionNode().dependsOn(1, parent.q.getResolutionNode(), 0);
                if (this.mRelativeBegin != -1) {
                    this.p.getResolutionNode().dependsOn(1, parent.p.getResolutionNode(), this.mRelativeBegin);
                    this.r.getResolutionNode().dependsOn(1, parent.p.getResolutionNode(), this.mRelativeBegin);
                } else if (this.mRelativeEnd != -1) {
                    this.p.getResolutionNode().dependsOn(1, parent.r.getResolutionNode(), -this.mRelativeEnd);
                    this.r.getResolutionNode().dependsOn(1, parent.r.getResolutionNode(), -this.mRelativeEnd);
                } else if (this.mRelativePercent != -1.0f && parent.getHorizontalDimensionBehaviour() == DimensionBehaviour.FIXED) {
                    int i2 = (int) (((float) parent.y) * this.mRelativePercent);
                    this.p.getResolutionNode().dependsOn(1, parent.p.getResolutionNode(), i2);
                    this.r.getResolutionNode().dependsOn(1, parent.p.getResolutionNode(), i2);
                }
            } else {
                this.p.getResolutionNode().dependsOn(1, parent.p.getResolutionNode(), 0);
                this.r.getResolutionNode().dependsOn(1, parent.p.getResolutionNode(), 0);
                if (this.mRelativeBegin != -1) {
                    this.q.getResolutionNode().dependsOn(1, parent.q.getResolutionNode(), this.mRelativeBegin);
                    this.s.getResolutionNode().dependsOn(1, parent.q.getResolutionNode(), this.mRelativeBegin);
                } else if (this.mRelativeEnd != -1) {
                    this.q.getResolutionNode().dependsOn(1, parent.s.getResolutionNode(), -this.mRelativeEnd);
                    this.s.getResolutionNode().dependsOn(1, parent.s.getResolutionNode(), -this.mRelativeEnd);
                } else if (this.mRelativePercent != -1.0f && parent.getVerticalDimensionBehaviour() == DimensionBehaviour.FIXED) {
                    int i3 = (int) (((float) parent.z) * this.mRelativePercent);
                    this.q.getResolutionNode().dependsOn(1, parent.q.getResolutionNode(), i3);
                    this.s.getResolutionNode().dependsOn(1, parent.q.getResolutionNode(), i3);
                }
            }
        }
    }

    public void addToSolver(LinearSystem linearSystem) {
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) getParent();
        if (constraintWidgetContainer != null) {
            ConstraintAnchor anchor = constraintWidgetContainer.getAnchor(Type.LEFT);
            ConstraintAnchor anchor2 = constraintWidgetContainer.getAnchor(Type.RIGHT);
            boolean z = this.x != null && this.x.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT;
            if (this.N == 0) {
                anchor = constraintWidgetContainer.getAnchor(Type.TOP);
                anchor2 = constraintWidgetContainer.getAnchor(Type.BOTTOM);
                z = this.x != null && this.x.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT;
            }
            if (this.mRelativeBegin != -1) {
                SolverVariable createObjectVariable = linearSystem.createObjectVariable(this.M);
                linearSystem.addEquality(createObjectVariable, linearSystem.createObjectVariable(anchor), this.mRelativeBegin, 6);
                if (z) {
                    linearSystem.addGreaterThan(linearSystem.createObjectVariable(anchor2), createObjectVariable, 0, 5);
                }
            } else if (this.mRelativeEnd != -1) {
                SolverVariable createObjectVariable2 = linearSystem.createObjectVariable(this.M);
                SolverVariable createObjectVariable3 = linearSystem.createObjectVariable(anchor2);
                linearSystem.addEquality(createObjectVariable2, createObjectVariable3, -this.mRelativeEnd, 6);
                if (z) {
                    linearSystem.addGreaterThan(createObjectVariable2, linearSystem.createObjectVariable(anchor), 0, 5);
                    linearSystem.addGreaterThan(createObjectVariable3, createObjectVariable2, 0, 5);
                }
            } else if (this.mRelativePercent != -1.0f) {
                linearSystem.addConstraint(LinearSystem.createRowDimensionPercent(linearSystem, linearSystem.createObjectVariable(this.M), linearSystem.createObjectVariable(anchor), linearSystem.createObjectVariable(anchor2), this.mRelativePercent, this.O));
            }
        }
    }

    public void updateFromSolver(LinearSystem linearSystem) {
        if (getParent() != null) {
            int objectVariableValue = linearSystem.getObjectVariableValue(this.M);
            if (this.N == 1) {
                setX(objectVariableValue);
                setY(0);
                setHeight(getParent().getHeight());
                setWidth(0);
            } else {
                setX(0);
                setY(objectVariableValue);
                setWidth(getParent().getWidth());
                setHeight(0);
            }
        }
    }

    public void setDrawOrigin(int i, int i2) {
        if (this.N == 1) {
            int i3 = i - this.mOffsetX;
            if (this.mRelativeBegin != -1) {
                setGuideBegin(i3);
            } else if (this.mRelativeEnd != -1) {
                setGuideEnd(getParent().getWidth() - i3);
            } else if (this.mRelativePercent != -1.0f) {
                setGuidePercent(((float) i3) / ((float) getParent().getWidth()));
            }
        } else {
            int i4 = i2 - this.mOffsetY;
            if (this.mRelativeBegin != -1) {
                setGuideBegin(i4);
            } else if (this.mRelativeEnd != -1) {
                setGuideEnd(getParent().getHeight() - i4);
            } else if (this.mRelativePercent != -1.0f) {
                setGuidePercent(((float) i4) / ((float) getParent().getHeight()));
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        float x = ((float) getX()) / ((float) getParent().getWidth());
        if (this.N == 0) {
            x = ((float) getY()) / ((float) getParent().getHeight());
        }
        setGuidePercent(x);
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        int x = getX();
        if (this.N == 0) {
            x = getY();
        }
        setGuideBegin(x);
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        int width = getParent().getWidth() - getX();
        if (this.N == 0) {
            width = getParent().getHeight() - getY();
        }
        setGuideEnd(width);
    }

    public void cyclePosition() {
        if (this.mRelativeBegin != -1) {
            a();
        } else if (this.mRelativePercent != -1.0f) {
            c();
        } else if (this.mRelativeEnd != -1) {
            b();
        }
    }
}
