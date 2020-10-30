package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable.Type;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.PrintStream;
import java.util.Arrays;

public class ArrayLinkedVariables {
    int a = 0;
    private final ArrayRow b;
    private final Cache c;
    private int d = 8;
    private SolverVariable e = null;
    private int[] f = new int[this.d];
    private int[] g = new int[this.d];
    private float[] h = new float[this.d];
    private int i = -1;
    private int j = -1;
    private boolean k = false;

    ArrayLinkedVariables(ArrayRow arrayRow, Cache cache) {
        this.b = arrayRow;
        this.c = cache;
    }

    public final void put(SolverVariable solverVariable, float f2) {
        if (f2 == BitmapDescriptorFactory.HUE_RED) {
            remove(solverVariable, true);
        } else if (this.i == -1) {
            this.i = 0;
            this.h[this.i] = f2;
            this.f[this.i] = solverVariable.f234id;
            this.g[this.i] = -1;
            solverVariable.usageInRowCount++;
            solverVariable.addToRow(this.b);
            this.a++;
            if (!this.k) {
                this.j++;
                if (this.j >= this.f.length) {
                    this.k = true;
                    this.j = this.f.length - 1;
                }
            }
        } else {
            int i2 = this.i;
            int i3 = 0;
            int i4 = -1;
            while (i2 != -1 && i3 < this.a) {
                if (this.f[i2] == solverVariable.f234id) {
                    this.h[i2] = f2;
                    return;
                }
                if (this.f[i2] < solverVariable.f234id) {
                    i4 = i2;
                }
                i2 = this.g[i2];
                i3++;
            }
            int i5 = this.j + 1;
            if (this.k) {
                if (this.f[this.j] == -1) {
                    i5 = this.j;
                } else {
                    i5 = this.f.length;
                }
            }
            if (i5 >= this.f.length && this.a < this.f.length) {
                int i6 = 0;
                while (true) {
                    if (i6 >= this.f.length) {
                        break;
                    } else if (this.f[i6] == -1) {
                        i5 = i6;
                        break;
                    } else {
                        i6++;
                    }
                }
            }
            if (i5 >= this.f.length) {
                i5 = this.f.length;
                this.d *= 2;
                this.k = false;
                this.j = i5 - 1;
                this.h = Arrays.copyOf(this.h, this.d);
                this.f = Arrays.copyOf(this.f, this.d);
                this.g = Arrays.copyOf(this.g, this.d);
            }
            this.f[i5] = solverVariable.f234id;
            this.h[i5] = f2;
            if (i4 != -1) {
                this.g[i5] = this.g[i4];
                this.g[i4] = i5;
            } else {
                this.g[i5] = this.i;
                this.i = i5;
            }
            solverVariable.usageInRowCount++;
            solverVariable.addToRow(this.b);
            this.a++;
            if (!this.k) {
                this.j++;
            }
            if (this.a >= this.f.length) {
                this.k = true;
            }
            if (this.j >= this.f.length) {
                this.k = true;
                this.j = this.f.length - 1;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(SolverVariable solverVariable, float f2, boolean z) {
        if (f2 != BitmapDescriptorFactory.HUE_RED) {
            if (this.i == -1) {
                this.i = 0;
                this.h[this.i] = f2;
                this.f[this.i] = solverVariable.f234id;
                this.g[this.i] = -1;
                solverVariable.usageInRowCount++;
                solverVariable.addToRow(this.b);
                this.a++;
                if (!this.k) {
                    this.j++;
                    if (this.j >= this.f.length) {
                        this.k = true;
                        this.j = this.f.length - 1;
                    }
                }
                return;
            }
            int i2 = this.i;
            int i3 = 0;
            int i4 = -1;
            while (i2 != -1 && i3 < this.a) {
                if (this.f[i2] == solverVariable.f234id) {
                    float[] fArr = this.h;
                    fArr[i2] = fArr[i2] + f2;
                    if (this.h[i2] == BitmapDescriptorFactory.HUE_RED) {
                        if (i2 == this.i) {
                            this.i = this.g[i2];
                        } else {
                            this.g[i4] = this.g[i2];
                        }
                        if (z) {
                            solverVariable.removeFromRow(this.b);
                        }
                        if (this.k) {
                            this.j = i2;
                        }
                        solverVariable.usageInRowCount--;
                        this.a--;
                    }
                    return;
                }
                if (this.f[i2] < solverVariable.f234id) {
                    i4 = i2;
                }
                i2 = this.g[i2];
                i3++;
            }
            int i5 = this.j + 1;
            if (this.k) {
                if (this.f[this.j] == -1) {
                    i5 = this.j;
                } else {
                    i5 = this.f.length;
                }
            }
            if (i5 >= this.f.length && this.a < this.f.length) {
                int i6 = 0;
                while (true) {
                    if (i6 >= this.f.length) {
                        break;
                    } else if (this.f[i6] == -1) {
                        i5 = i6;
                        break;
                    } else {
                        i6++;
                    }
                }
            }
            if (i5 >= this.f.length) {
                i5 = this.f.length;
                this.d *= 2;
                this.k = false;
                this.j = i5 - 1;
                this.h = Arrays.copyOf(this.h, this.d);
                this.f = Arrays.copyOf(this.f, this.d);
                this.g = Arrays.copyOf(this.g, this.d);
            }
            this.f[i5] = solverVariable.f234id;
            this.h[i5] = f2;
            if (i4 != -1) {
                this.g[i5] = this.g[i4];
                this.g[i4] = i5;
            } else {
                this.g[i5] = this.i;
                this.i = i5;
            }
            solverVariable.usageInRowCount++;
            solverVariable.addToRow(this.b);
            this.a++;
            if (!this.k) {
                this.j++;
            }
            if (this.j >= this.f.length) {
                this.k = true;
                this.j = this.f.length - 1;
            }
        }
    }

    public final float remove(SolverVariable solverVariable, boolean z) {
        if (this.e == solverVariable) {
            this.e = null;
        }
        if (this.i == -1) {
            return BitmapDescriptorFactory.HUE_RED;
        }
        int i2 = this.i;
        int i3 = 0;
        int i4 = -1;
        while (i2 != -1 && i3 < this.a) {
            if (this.f[i2] == solverVariable.f234id) {
                if (i2 == this.i) {
                    this.i = this.g[i2];
                } else {
                    this.g[i4] = this.g[i2];
                }
                if (z) {
                    solverVariable.removeFromRow(this.b);
                }
                solverVariable.usageInRowCount--;
                this.a--;
                this.f[i2] = -1;
                if (this.k) {
                    this.j = i2;
                }
                return this.h[i2];
            }
            i3++;
            i4 = i2;
            i2 = this.g[i2];
        }
        return BitmapDescriptorFactory.HUE_RED;
    }

    public final void clear() {
        int i2 = this.i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            SolverVariable solverVariable = this.c.c[this.f[i2]];
            if (solverVariable != null) {
                solverVariable.removeFromRow(this.b);
            }
            i2 = this.g[i2];
            i3++;
        }
        this.i = -1;
        this.j = -1;
        this.k = false;
        this.a = 0;
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(SolverVariable solverVariable) {
        if (this.i == -1) {
            return false;
        }
        int i2 = this.i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            if (this.f[i2] == solverVariable.f234id) {
                return true;
            }
            i2 = this.g[i2];
            i3++;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        int i2 = this.i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            float[] fArr = this.h;
            fArr[i2] = fArr[i2] * -1.0f;
            i2 = this.g[i2];
            i3++;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(float f2) {
        int i2 = this.i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            float[] fArr = this.h;
            fArr[i2] = fArr[i2] / f2;
            i2 = this.g[i2];
            i3++;
        }
    }

    private boolean a(SolverVariable solverVariable, LinearSystem linearSystem) {
        return solverVariable.usageInRowCount <= 1;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0094 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.support.constraint.solver.SolverVariable a(android.support.constraint.solver.LinearSystem r15) {
        /*
            r14 = this;
            int r0 = r14.i
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = r1
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
        L_0x000a:
            r9 = -1
            if (r0 == r9) goto L_0x009c
            int r9 = r14.a
            if (r2 >= r9) goto L_0x009c
            float[] r9 = r14.h
            r9 = r9[r0]
            r10 = 981668463(0x3a83126f, float:0.001)
            android.support.constraint.solver.Cache r11 = r14.c
            android.support.constraint.solver.SolverVariable[] r11 = r11.c
            int[] r12 = r14.f
            r12 = r12[r0]
            r11 = r11[r12]
            int r12 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r12 >= 0) goto L_0x0038
            r10 = -1165815185(0xffffffffba83126f, float:-0.001)
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 <= 0) goto L_0x0046
            float[] r9 = r14.h
            r9[r0] = r3
            android.support.constraint.solver.ArrayRow r9 = r14.b
            r11.removeFromRow(r9)
        L_0x0036:
            r9 = 0
            goto L_0x0046
        L_0x0038:
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 >= 0) goto L_0x0046
            float[] r9 = r14.h
            r9[r0] = r3
            android.support.constraint.solver.ArrayRow r9 = r14.b
            r11.removeFromRow(r9)
            goto L_0x0036
        L_0x0046:
            int r10 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            r12 = 1
            if (r10 == 0) goto L_0x0094
            android.support.constraint.solver.SolverVariable$Type r10 = r11.c
            android.support.constraint.solver.SolverVariable$Type r13 = android.support.constraint.solver.SolverVariable.Type.UNRESTRICTED
            if (r10 != r13) goto L_0x0070
            if (r1 != 0) goto L_0x005b
            boolean r1 = r14.a(r11, r15)
        L_0x0057:
            r6 = r1
            r5 = r9
            r1 = r11
            goto L_0x0094
        L_0x005b:
            int r10 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r10 <= 0) goto L_0x0064
            boolean r1 = r14.a(r11, r15)
            goto L_0x0057
        L_0x0064:
            if (r6 != 0) goto L_0x0094
            boolean r10 = r14.a(r11, r15)
            if (r10 == 0) goto L_0x0094
            r5 = r9
            r1 = r11
            r6 = 1
            goto L_0x0094
        L_0x0070:
            if (r1 != 0) goto L_0x0094
            int r10 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r10 >= 0) goto L_0x0094
            if (r4 != 0) goto L_0x0080
            boolean r4 = r14.a(r11, r15)
        L_0x007c:
            r8 = r4
            r7 = r9
            r4 = r11
            goto L_0x0094
        L_0x0080:
            int r10 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r10 <= 0) goto L_0x0089
            boolean r4 = r14.a(r11, r15)
            goto L_0x007c
        L_0x0089:
            if (r8 != 0) goto L_0x0094
            boolean r10 = r14.a(r11, r15)
            if (r10 == 0) goto L_0x0094
            r7 = r9
            r4 = r11
            r8 = 1
        L_0x0094:
            int[] r9 = r14.g
            r0 = r9[r0]
            int r2 = r2 + 1
            goto L_0x000a
        L_0x009c:
            if (r1 == 0) goto L_0x009f
            return r1
        L_0x009f:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.ArrayLinkedVariables.a(android.support.constraint.solver.LinearSystem):android.support.constraint.solver.SolverVariable");
    }

    /* access modifiers changed from: 0000 */
    public final void a(ArrayRow arrayRow, ArrayRow arrayRow2, boolean z) {
        int i2 = this.i;
        while (true) {
            int i3 = 0;
            while (i2 != -1 && i3 < this.a) {
                if (this.f[i2] == arrayRow2.a.f234id) {
                    float f2 = this.h[i2];
                    remove(arrayRow2.a, z);
                    ArrayLinkedVariables arrayLinkedVariables = arrayRow2.variables;
                    int i4 = arrayLinkedVariables.i;
                    int i5 = 0;
                    while (i4 != -1 && i5 < arrayLinkedVariables.a) {
                        a(this.c.c[arrayLinkedVariables.f[i4]], arrayLinkedVariables.h[i4] * f2, z);
                        i4 = arrayLinkedVariables.g[i4];
                        i5++;
                    }
                    arrayRow.b += arrayRow2.b * f2;
                    if (z) {
                        arrayRow2.a.removeFromRow(arrayRow);
                    }
                    i2 = this.i;
                } else {
                    i2 = this.g[i2];
                    i3++;
                }
            }
            return;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ArrayRow arrayRow, ArrayRow[] arrayRowArr) {
        int i2 = this.i;
        while (true) {
            int i3 = 0;
            while (i2 != -1 && i3 < this.a) {
                SolverVariable solverVariable = this.c.c[this.f[i2]];
                if (solverVariable.a != -1) {
                    float f2 = this.h[i2];
                    remove(solverVariable, true);
                    ArrayRow arrayRow2 = arrayRowArr[solverVariable.a];
                    if (!arrayRow2.d) {
                        ArrayLinkedVariables arrayLinkedVariables = arrayRow2.variables;
                        int i4 = arrayLinkedVariables.i;
                        int i5 = 0;
                        while (i4 != -1 && i5 < arrayLinkedVariables.a) {
                            a(this.c.c[arrayLinkedVariables.f[i4]], arrayLinkedVariables.h[i4] * f2, true);
                            i4 = arrayLinkedVariables.g[i4];
                            i5++;
                        }
                    }
                    arrayRow.b += arrayRow2.b * f2;
                    arrayRow2.a.removeFromRow(arrayRow);
                    i2 = this.i;
                } else {
                    i2 = this.g[i2];
                    i3++;
                }
            }
            return;
        }
    }

    /* access modifiers changed from: 0000 */
    public SolverVariable a(boolean[] zArr, SolverVariable solverVariable) {
        int i2 = this.i;
        int i3 = 0;
        SolverVariable solverVariable2 = null;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        while (i2 != -1 && i3 < this.a) {
            if (this.h[i2] < BitmapDescriptorFactory.HUE_RED) {
                SolverVariable solverVariable3 = this.c.c[this.f[i2]];
                if ((zArr == null || !zArr[solverVariable3.f234id]) && solverVariable3 != solverVariable && (solverVariable3.c == Type.SLACK || solverVariable3.c == Type.ERROR)) {
                    float f3 = this.h[i2];
                    if (f3 < f2) {
                        solverVariable2 = solverVariable3;
                        f2 = f3;
                    }
                }
            }
            i2 = this.g[i2];
            i3++;
        }
        return solverVariable2;
    }

    /* access modifiers changed from: 0000 */
    public final SolverVariable a(int i2) {
        int i3 = this.i;
        int i4 = 0;
        while (i3 != -1 && i4 < this.a) {
            if (i4 == i2) {
                return this.c.c[this.f[i3]];
            }
            i3 = this.g[i3];
            i4++;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final float b(int i2) {
        int i3 = this.i;
        int i4 = 0;
        while (i3 != -1 && i4 < this.a) {
            if (i4 == i2) {
                return this.h[i3];
            }
            i3 = this.g[i3];
            i4++;
        }
        return BitmapDescriptorFactory.HUE_RED;
    }

    public final float get(SolverVariable solverVariable) {
        int i2 = this.i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            if (this.f[i2] == solverVariable.f234id) {
                return this.h[i2];
            }
            i2 = this.g[i2];
            i3++;
        }
        return BitmapDescriptorFactory.HUE_RED;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return (this.f.length * 4 * 3) + 0 + 36;
    }

    public void display() {
        int i2 = this.a;
        System.out.print("{ ");
        for (int i3 = 0; i3 < i2; i3++) {
            SolverVariable a2 = a(i3);
            if (a2 != null) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(a2);
                sb.append(" = ");
                sb.append(b(i3));
                sb.append(UtilsCuentas.SEPARAOR2);
                printStream.print(sb.toString());
            }
        }
        System.out.println(" }");
    }

    public String toString() {
        String str = "";
        int i2 = this.i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" -> ");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(this.h[i2]);
            sb3.append(" : ");
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(this.c.c[this.f[i2]]);
            str = sb5.toString();
            i2 = this.g[i2];
            i3++;
        }
        return str;
    }
}
