package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

public class Barrier extends Helper {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    private int M = 0;
    private ArrayList<ResolutionAnchor> N = new ArrayList<>(4);
    private boolean O = true;

    public boolean allowedInBarrier() {
        return true;
    }

    public void setBarrierType(int i) {
        this.M = i;
    }

    public void setAllowsGoneWidget(boolean z) {
        this.O = z;
    }

    public void resetResolutionNodes() {
        super.resetResolutionNodes();
        this.N.clear();
    }

    public void analyze(int i) {
        ResolutionAnchor resolutionAnchor;
        ResolutionAnchor resolutionAnchor2;
        if (this.x != null && ((ConstraintWidgetContainer) this.x).optimizeFor(2)) {
            switch (this.M) {
                case 0:
                    resolutionAnchor = this.p.getResolutionNode();
                    break;
                case 1:
                    resolutionAnchor = this.r.getResolutionNode();
                    break;
                case 2:
                    resolutionAnchor = this.q.getResolutionNode();
                    break;
                case 3:
                    resolutionAnchor = this.s.getResolutionNode();
                    break;
                default:
                    return;
            }
            resolutionAnchor.setType(5);
            if (this.M == 0 || this.M == 1) {
                this.q.getResolutionNode().resolve(null, BitmapDescriptorFactory.HUE_RED);
                this.s.getResolutionNode().resolve(null, BitmapDescriptorFactory.HUE_RED);
            } else {
                this.p.getResolutionNode().resolve(null, BitmapDescriptorFactory.HUE_RED);
                this.r.getResolutionNode().resolve(null, BitmapDescriptorFactory.HUE_RED);
            }
            this.N.clear();
            for (int i2 = 0; i2 < this.mWidgetsCount; i2++) {
                ConstraintWidget constraintWidget = this.mWidgets[i2];
                if (this.O || constraintWidget.allowedInBarrier()) {
                    switch (this.M) {
                        case 0:
                            resolutionAnchor2 = constraintWidget.p.getResolutionNode();
                            break;
                        case 1:
                            resolutionAnchor2 = constraintWidget.r.getResolutionNode();
                            break;
                        case 2:
                            resolutionAnchor2 = constraintWidget.q.getResolutionNode();
                            break;
                        case 3:
                            resolutionAnchor2 = constraintWidget.s.getResolutionNode();
                            break;
                        default:
                            resolutionAnchor2 = null;
                            break;
                    }
                    if (resolutionAnchor2 != null) {
                        this.N.add(resolutionAnchor2);
                        resolutionAnchor2.addDependent(resolutionAnchor);
                    }
                }
            }
        }
    }

    public void resolve() {
        ResolutionAnchor resolutionAnchor;
        float f = Float.MAX_VALUE;
        switch (this.M) {
            case 0:
                resolutionAnchor = this.p.getResolutionNode();
                break;
            case 1:
                resolutionAnchor = this.r.getResolutionNode();
                break;
            case 2:
                resolutionAnchor = this.q.getResolutionNode();
                break;
            case 3:
                resolutionAnchor = this.s.getResolutionNode();
                break;
            default:
                return;
        }
        f = BitmapDescriptorFactory.HUE_RED;
        int size = this.N.size();
        ResolutionAnchor resolutionAnchor2 = null;
        int i = 0;
        while (i < size) {
            ResolutionAnchor resolutionAnchor3 = (ResolutionAnchor) this.N.get(i);
            if (resolutionAnchor3.i == 1) {
                if (this.M == 0 || this.M == 2) {
                    if (resolutionAnchor3.f < f) {
                        f = resolutionAnchor3.f;
                        resolutionAnchor2 = resolutionAnchor3.e;
                    }
                } else if (resolutionAnchor3.f > f) {
                    f = resolutionAnchor3.f;
                    resolutionAnchor2 = resolutionAnchor3.e;
                }
                i++;
            } else {
                return;
            }
        }
        if (LinearSystem.getMetrics() != null) {
            Metrics metrics = LinearSystem.getMetrics();
            metrics.barrierConnectionResolved++;
        }
        resolutionAnchor.e = resolutionAnchor2;
        resolutionAnchor.f = f;
        resolutionAnchor.didResolve();
        switch (this.M) {
            case 0:
                this.r.getResolutionNode().resolve(resolutionAnchor2, f);
                break;
            case 1:
                this.p.getResolutionNode().resolve(resolutionAnchor2, f);
                break;
            case 2:
                this.s.getResolutionNode().resolve(resolutionAnchor2, f);
                break;
            case 3:
                this.q.getResolutionNode().resolve(resolutionAnchor2, f);
                break;
            default:
                return;
        }
    }

    public void addToSolver(LinearSystem linearSystem) {
        boolean z;
        this.mListAnchors[0] = this.p;
        this.mListAnchors[2] = this.q;
        this.mListAnchors[1] = this.r;
        this.mListAnchors[3] = this.s;
        for (int i = 0; i < this.mListAnchors.length; i++) {
            this.mListAnchors[i].e = linearSystem.createObjectVariable(this.mListAnchors[i]);
        }
        if (this.M >= 0 && this.M < 4) {
            ConstraintAnchor constraintAnchor = this.mListAnchors[this.M];
            int i2 = 0;
            while (true) {
                if (i2 >= this.mWidgetsCount) {
                    z = false;
                    break;
                }
                ConstraintWidget constraintWidget = this.mWidgets[i2];
                if ((this.O || constraintWidget.allowedInBarrier()) && (((this.M == 0 || this.M == 1) && constraintWidget.getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) || ((this.M == 2 || this.M == 3) && constraintWidget.getVerticalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT))) {
                    z = true;
                } else {
                    i2++;
                }
            }
            z = true;
            if (this.M == 0 || this.M == 1 ? getParent().getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT : getParent().getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT) {
                z = false;
            }
            for (int i3 = 0; i3 < this.mWidgetsCount; i3++) {
                ConstraintWidget constraintWidget2 = this.mWidgets[i3];
                if (this.O || constraintWidget2.allowedInBarrier()) {
                    SolverVariable createObjectVariable = linearSystem.createObjectVariable(constraintWidget2.mListAnchors[this.M]);
                    constraintWidget2.mListAnchors[this.M].e = createObjectVariable;
                    if (this.M == 0 || this.M == 2) {
                        linearSystem.addLowerBarrier(constraintAnchor.e, createObjectVariable, z);
                    } else {
                        linearSystem.addGreaterBarrier(constraintAnchor.e, createObjectVariable, z);
                    }
                }
            }
            if (this.M == 0) {
                linearSystem.addEquality(this.r.e, this.p.e, 0, 6);
                if (!z) {
                    linearSystem.addEquality(this.p.e, this.x.r.e, 0, 5);
                }
            } else if (this.M == 1) {
                linearSystem.addEquality(this.p.e, this.r.e, 0, 6);
                if (!z) {
                    linearSystem.addEquality(this.p.e, this.x.p.e, 0, 5);
                }
            } else if (this.M == 2) {
                linearSystem.addEquality(this.s.e, this.q.e, 0, 6);
                if (!z) {
                    linearSystem.addEquality(this.q.e, this.x.s.e, 0, 5);
                }
            } else if (this.M == 3) {
                linearSystem.addEquality(this.q.e, this.s.e, 0, 6);
                if (!z) {
                    linearSystem.addEquality(this.q.e, this.x.q.e, 0, 5);
                }
            }
        }
    }
}
