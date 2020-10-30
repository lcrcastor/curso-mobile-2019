package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable.Type;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import io.fabric.sdk.android.services.common.IdManager;

public class ArrayRow implements Row {
    SolverVariable a = null;
    float b = BitmapDescriptorFactory.HUE_RED;
    boolean c = false;
    boolean d = false;
    public final ArrayLinkedVariables variables;

    public ArrayRow(Cache cache) {
        this.variables = new ArrayLinkedVariables(this, cache);
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.a != null && (this.a.c == Type.UNRESTRICTED || this.b >= BitmapDescriptorFactory.HUE_RED);
    }

    public String toString() {
        return b();
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        String str;
        boolean z;
        String str2;
        String str3 = "";
        if (this.a == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append("0");
            str = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(this.a);
            str = sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(" = ");
        String sb4 = sb3.toString();
        if (this.b != BitmapDescriptorFactory.HUE_RED) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(this.b);
            sb4 = sb5.toString();
            z = true;
        } else {
            z = false;
        }
        int i = this.variables.a;
        for (int i2 = 0; i2 < i; i2++) {
            SolverVariable a2 = this.variables.a(i2);
            if (a2 != null) {
                float b2 = this.variables.b(i2);
                if (b2 != BitmapDescriptorFactory.HUE_RED) {
                    String solverVariable = a2.toString();
                    if (!z) {
                        if (b2 < BitmapDescriptorFactory.HUE_RED) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str2);
                            sb6.append("- ");
                            str2 = sb6.toString();
                            b2 *= -1.0f;
                        }
                    } else if (b2 > BitmapDescriptorFactory.HUE_RED) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(str2);
                        sb7.append(" + ");
                        str2 = sb7.toString();
                    } else {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(str2);
                        sb8.append(" - ");
                        str2 = sb8.toString();
                        b2 *= -1.0f;
                    }
                    if (b2 == 1.0f) {
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append(str2);
                        sb9.append(solverVariable);
                        str2 = sb9.toString();
                    } else {
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append(str2);
                        sb10.append(b2);
                        sb10.append(UtilsCuentas.SEPARAOR2);
                        sb10.append(solverVariable);
                        str2 = sb10.toString();
                    }
                    z = true;
                }
            }
        }
        if (z) {
            return str2;
        }
        StringBuilder sb11 = new StringBuilder();
        sb11.append(str2);
        sb11.append(IdManager.DEFAULT_VERSION_NAME);
        return sb11.toString();
    }

    public void reset() {
        this.a = null;
        this.variables.clear();
        this.b = BitmapDescriptorFactory.HUE_RED;
        this.d = false;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(SolverVariable solverVariable) {
        return this.variables.a(solverVariable);
    }

    /* access modifiers changed from: 0000 */
    public ArrayRow a(SolverVariable solverVariable, int i) {
        this.a = solverVariable;
        float f = (float) i;
        solverVariable.computedValue = f;
        this.b = f;
        this.d = true;
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, int i) {
        if (i < 0) {
            this.b = (float) (i * -1);
            this.variables.put(solverVariable, 1.0f);
        } else {
            this.b = (float) i;
            this.variables.put(solverVariable, -1.0f);
        }
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, SolverVariable solverVariable2, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = (float) i;
        }
        if (!z) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
        } else {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public ArrayRow b(SolverVariable solverVariable, int i) {
        this.variables.put(solverVariable, (float) i);
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = (float) i;
        }
        if (!z) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, 1.0f);
        } else {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
        }
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, int i, SolverVariable solverVariable2) {
        this.b = (float) i;
        this.variables.put(solverVariable, -1.0f);
        return this;
    }

    public ArrayRow createRowLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = (float) i;
        }
        if (!z) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, 1.0f);
        }
        return this;
    }

    public ArrayRow createRowEqualMatchDimensions(float f, float f2, float f3, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4) {
        this.b = BitmapDescriptorFactory.HUE_RED;
        if (f2 == BitmapDescriptorFactory.HUE_RED || f == f3) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else if (f == BitmapDescriptorFactory.HUE_RED) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
        } else if (f3 == BitmapDescriptorFactory.HUE_RED) {
            this.variables.put(solverVariable3, 1.0f);
            this.variables.put(solverVariable4, -1.0f);
        } else {
            float f4 = (f / f2) / (f3 / f2);
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, f4);
            this.variables.put(solverVariable3, -f4);
        }
        return this;
    }

    public ArrayRow createRowEqualDimension(float f, float f2, float f3, SolverVariable solverVariable, int i, SolverVariable solverVariable2, int i2, SolverVariable solverVariable3, int i3, SolverVariable solverVariable4, int i4) {
        if (f2 == BitmapDescriptorFactory.HUE_RED || f == f3) {
            this.b = (float) (((-i) - i2) + i3 + i4);
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
        } else {
            float f4 = (f / f2) / (f3 / f2);
            this.b = ((float) ((-i) - i2)) + (((float) i3) * f4) + (((float) i4) * f4);
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, f4);
            this.variables.put(solverVariable3, -f4);
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public ArrayRow a(SolverVariable solverVariable, SolverVariable solverVariable2, int i, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int i2) {
        if (solverVariable2 == solverVariable3) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable2, -2.0f);
            return this;
        }
        if (f == 0.5f) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            if (i > 0 || i2 > 0) {
                this.b = (float) ((-i) + i2);
            }
        } else if (f <= BitmapDescriptorFactory.HUE_RED) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.b = (float) i;
        } else if (f >= 1.0f) {
            this.variables.put(solverVariable3, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.b = (float) i2;
        } else {
            float f2 = 1.0f - f;
            this.variables.put(solverVariable, f2 * 1.0f);
            this.variables.put(solverVariable2, f2 * -1.0f);
            this.variables.put(solverVariable3, -1.0f * f);
            this.variables.put(solverVariable4, 1.0f * f);
            if (i > 0 || i2 > 0) {
                this.b = (((float) (-i)) * f2) + (((float) i2) * f);
            }
        }
        return this;
    }

    public ArrayRow addError(LinearSystem linearSystem, int i) {
        this.variables.put(linearSystem.createErrorVariable(i, "ep"), 1.0f);
        this.variables.put(linearSystem.createErrorVariable(i, "em"), -1.0f);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public ArrayRow a(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, float f) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, 1.0f - f);
        this.variables.put(solverVariable3, f);
        return this;
    }

    public ArrayRow createRowDimensionRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, 1.0f);
        this.variables.put(solverVariable3, f);
        this.variables.put(solverVariable4, -f);
        return this;
    }

    public ArrayRow createRowWithAngle(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.variables.put(solverVariable3, 0.5f);
        this.variables.put(solverVariable4, 0.5f);
        this.variables.put(solverVariable, -0.5f);
        this.variables.put(solverVariable2, -0.5f);
        this.b = -f;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        return (this.a != null ? 4 : 0) + 4 + 4 + this.variables.b();
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        if (this.b < BitmapDescriptorFactory.HUE_RED) {
            this.b *= -1.0f;
            this.variables.a();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(LinearSystem linearSystem) {
        boolean z;
        SolverVariable a2 = this.variables.a(linearSystem);
        if (a2 == null) {
            z = true;
        } else {
            c(a2);
            z = false;
        }
        if (this.variables.a == 0) {
            this.d = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public SolverVariable b(SolverVariable solverVariable) {
        return this.variables.a((boolean[]) null, solverVariable);
    }

    /* access modifiers changed from: 0000 */
    public void c(SolverVariable solverVariable) {
        if (this.a != null) {
            this.variables.put(this.a, -1.0f);
            this.a = null;
        }
        float remove = this.variables.remove(solverVariable, true) * -1.0f;
        this.a = solverVariable;
        if (remove != 1.0f) {
            this.b /= remove;
            this.variables.a(remove);
        }
    }

    public boolean isEmpty() {
        return this.a == null && this.b == BitmapDescriptorFactory.HUE_RED && this.variables.a == 0;
    }

    public SolverVariable getPivotCandidate(LinearSystem linearSystem, boolean[] zArr) {
        return this.variables.a(zArr, (SolverVariable) null);
    }

    public void clear() {
        this.variables.clear();
        this.a = null;
        this.b = BitmapDescriptorFactory.HUE_RED;
    }

    public void initFromRow(Row row) {
        if (row instanceof ArrayRow) {
            ArrayRow arrayRow = (ArrayRow) row;
            this.a = null;
            this.variables.clear();
            for (int i = 0; i < arrayRow.variables.a; i++) {
                this.variables.a(arrayRow.variables.a(i), arrayRow.variables.b(i), true);
            }
        }
    }

    public void addError(SolverVariable solverVariable) {
        float f = 1.0f;
        if (solverVariable.strength != 1) {
            if (solverVariable.strength == 2) {
                f = 1000.0f;
            } else if (solverVariable.strength == 3) {
                f = 1000000.0f;
            } else if (solverVariable.strength == 4) {
                f = 1.0E9f;
            } else if (solverVariable.strength == 5) {
                f = 1.0E12f;
            }
        }
        this.variables.put(solverVariable, f);
    }

    public SolverVariable getKey() {
        return this.a;
    }
}
