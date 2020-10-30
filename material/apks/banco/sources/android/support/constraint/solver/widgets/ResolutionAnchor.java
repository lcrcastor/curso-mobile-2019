package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ResolutionAnchor extends ResolutionNode {
    public static final int BARRIER_CONNECTION = 5;
    public static final int CENTER_CONNECTION = 2;
    public static final int CHAIN_CONNECTION = 4;
    public static final int DIRECT_CONNECTION = 1;
    public static final int MATCH_CONNECTION = 3;
    public static final int UNCONNECTED = 0;
    ConstraintAnchor a;
    float b;
    ResolutionAnchor c;
    float d;
    ResolutionAnchor e;
    float f;
    int g = 0;
    private ResolutionAnchor j;
    private float k;
    private ResolutionDimension l = null;
    private int m = 1;
    private ResolutionDimension n = null;
    private int o = 1;

    /* access modifiers changed from: 0000 */
    public String a(int i) {
        return i == 1 ? "DIRECT" : i == 2 ? "CENTER" : i == 3 ? "MATCH" : i == 4 ? "CHAIN" : i == 5 ? "BARRIER" : "UNCONNECTED";
    }

    public ResolutionAnchor(ConstraintAnchor constraintAnchor) {
        this.a = constraintAnchor;
    }

    public void remove(ResolutionDimension resolutionDimension) {
        if (this.l == resolutionDimension) {
            this.l = null;
            this.d = (float) this.m;
        } else if (this.l == this.n) {
            this.n = null;
            this.k = (float) this.o;
        }
        resolve();
    }

    public String toString() {
        if (this.i != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            sb.append(this.a);
            sb.append(" UNRESOLVED} type: ");
            sb.append(a(this.g));
            return sb.toString();
        } else if (this.e == this) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[");
            sb2.append(this.a);
            sb2.append(", RESOLVED: ");
            sb2.append(this.f);
            sb2.append("]  type: ");
            sb2.append(a(this.g));
            return sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("[");
            sb3.append(this.a);
            sb3.append(", RESOLVED: ");
            sb3.append(this.e);
            sb3.append(":");
            sb3.append(this.f);
            sb3.append("] type: ");
            sb3.append(a(this.g));
            return sb3.toString();
        }
    }

    public void resolve(ResolutionAnchor resolutionAnchor, float f2) {
        if (this.i == 0 || !(this.e == resolutionAnchor || this.f == f2)) {
            this.e = resolutionAnchor;
            this.f = f2;
            if (this.i == 1) {
                invalidate();
            }
            didResolve();
        }
    }

    public void resolve() {
        float f2;
        float f3;
        float f4;
        boolean z = true;
        if (this.i != 1 && this.g != 4) {
            if (this.l != null) {
                if (this.l.i == 1) {
                    this.d = ((float) this.m) * this.l.a;
                } else {
                    return;
                }
            }
            if (this.n != null) {
                if (this.n.i == 1) {
                    this.k = ((float) this.o) * this.n.a;
                } else {
                    return;
                }
            }
            if (this.g == 1 && (this.c == null || this.c.i == 1)) {
                if (this.c == null) {
                    this.e = this;
                    this.f = this.d;
                } else {
                    this.e = this.c.e;
                    this.f = this.c.f + this.d;
                }
                didResolve();
            } else if (this.g == 2 && this.c != null && this.c.i == 1 && this.j != null && this.j.c != null && this.j.c.i == 1) {
                if (LinearSystem.getMetrics() != null) {
                    Metrics metrics = LinearSystem.getMetrics();
                    metrics.centerConnectionResolved++;
                }
                this.e = this.c.e;
                this.j.e = this.j.c.e;
                int i = 0;
                if (!(this.a.b == Type.RIGHT || this.a.b == Type.BOTTOM)) {
                    z = false;
                }
                if (z) {
                    f2 = this.c.f - this.j.c.f;
                } else {
                    f2 = this.j.c.f - this.c.f;
                }
                if (this.a.b == Type.LEFT || this.a.b == Type.RIGHT) {
                    f4 = f2 - ((float) this.a.a.getWidth());
                    f3 = this.a.a.B;
                } else {
                    f4 = f2 - ((float) this.a.a.getHeight());
                    f3 = this.a.a.C;
                }
                int margin = this.a.getMargin();
                int margin2 = this.j.a.getMargin();
                if (this.a.getTarget() == this.j.a.getTarget()) {
                    f3 = 0.5f;
                    margin2 = 0;
                } else {
                    i = margin;
                }
                float f5 = (float) i;
                float f6 = (float) margin2;
                float f7 = (f4 - f5) - f6;
                if (z) {
                    this.j.f = this.j.c.f + f6 + (f7 * f3);
                    this.f = (this.c.f - f5) - (f7 * (1.0f - f3));
                } else {
                    this.f = this.c.f + f5 + (f7 * f3);
                    this.j.f = (this.j.c.f - f6) - (f7 * (1.0f - f3));
                }
                didResolve();
                this.j.didResolve();
            } else if (this.g == 3 && this.c != null && this.c.i == 1 && this.j != null && this.j.c != null && this.j.c.i == 1) {
                if (LinearSystem.getMetrics() != null) {
                    Metrics metrics2 = LinearSystem.getMetrics();
                    metrics2.matchConnectionResolved++;
                }
                this.e = this.c.e;
                this.j.e = this.j.c.e;
                this.f = this.c.f + this.d;
                this.j.f = this.j.c.f + this.j.d;
                didResolve();
                this.j.didResolve();
            } else if (this.g == 5) {
                this.a.a.resolve();
            }
        }
    }

    public void setType(int i) {
        this.g = i;
    }

    public void reset() {
        super.reset();
        this.c = null;
        this.d = BitmapDescriptorFactory.HUE_RED;
        this.l = null;
        this.m = 1;
        this.n = null;
        this.o = 1;
        this.e = null;
        this.f = BitmapDescriptorFactory.HUE_RED;
        this.b = BitmapDescriptorFactory.HUE_RED;
        this.j = null;
        this.k = BitmapDescriptorFactory.HUE_RED;
        this.g = 0;
    }

    public void update() {
        ConstraintAnchor target = this.a.getTarget();
        if (target != null) {
            if (target.getTarget() == this.a) {
                this.g = 4;
                target.getResolutionNode().g = 4;
            }
            int margin = this.a.getMargin();
            if (this.a.b == Type.RIGHT || this.a.b == Type.BOTTOM) {
                margin = -margin;
            }
            dependsOn(target.getResolutionNode(), margin);
        }
    }

    public void dependsOn(int i, ResolutionAnchor resolutionAnchor, int i2) {
        this.g = i;
        this.c = resolutionAnchor;
        this.d = (float) i2;
        this.c.addDependent(this);
    }

    public void dependsOn(ResolutionAnchor resolutionAnchor, int i) {
        this.c = resolutionAnchor;
        this.d = (float) i;
        this.c.addDependent(this);
    }

    public void dependsOn(ResolutionAnchor resolutionAnchor, int i, ResolutionDimension resolutionDimension) {
        this.c = resolutionAnchor;
        this.c.addDependent(this);
        this.l = resolutionDimension;
        this.m = i;
        this.l.addDependent(this);
    }

    public void setOpposite(ResolutionAnchor resolutionAnchor, float f2) {
        this.j = resolutionAnchor;
        this.k = f2;
    }

    public void setOpposite(ResolutionAnchor resolutionAnchor, int i, ResolutionDimension resolutionDimension) {
        this.j = resolutionAnchor;
        this.n = resolutionDimension;
        this.o = i;
    }

    /* access modifiers changed from: 0000 */
    public void a(LinearSystem linearSystem) {
        SolverVariable solverVariable = this.a.getSolverVariable();
        if (this.e == null) {
            linearSystem.addEquality(solverVariable, (int) this.f);
        } else {
            linearSystem.addEquality(solverVariable, linearSystem.createObjectVariable(this.e.a), (int) this.f, 6);
        }
    }

    public float getResolvedValue() {
        return this.f;
    }
}
