package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable.Type;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem {
    public static final boolean FULL_DEBUG = false;
    private static int f = 1000;
    public static Metrics sMetrics;
    int a;
    ArrayRow[] b;
    int c;
    int d;
    final Cache e;
    private HashMap<String, SolverVariable> g;
    public boolean graphOptimizer;
    private Row h;
    private int i;
    private int j;
    private boolean[] k;
    private int l;
    private SolverVariable[] m;
    private int n;
    private ArrayRow[] o;
    private final Row p;

    interface Row {
        void addError(SolverVariable solverVariable);

        void clear();

        SolverVariable getKey();

        SolverVariable getPivotCandidate(LinearSystem linearSystem, boolean[] zArr);

        void initFromRow(Row row);
    }

    public LinearSystem() {
        this.a = 0;
        this.g = null;
        this.i = 32;
        this.j = this.i;
        this.b = null;
        this.graphOptimizer = false;
        this.k = new boolean[this.i];
        this.c = 1;
        this.d = 0;
        this.l = this.i;
        this.m = new SolverVariable[f];
        this.n = 0;
        this.o = new ArrayRow[this.i];
        this.b = new ArrayRow[this.i];
        b();
        this.e = new Cache();
        this.h = new GoalRow(this.e);
        this.p = new ArrayRow(this.e);
    }

    public void fillMetrics(Metrics metrics) {
        sMetrics = metrics;
    }

    public static Metrics getMetrics() {
        return sMetrics;
    }

    private void a() {
        this.i *= 2;
        this.b = (ArrayRow[]) Arrays.copyOf(this.b, this.i);
        this.e.c = (SolverVariable[]) Arrays.copyOf(this.e.c, this.i);
        this.k = new boolean[this.i];
        this.j = this.i;
        this.l = this.i;
        if (sMetrics != null) {
            Metrics metrics = sMetrics;
            metrics.tableSizeIncrease++;
            sMetrics.maxTableSize = Math.max(sMetrics.maxTableSize, (long) this.i);
            sMetrics.lastTableSize = sMetrics.maxTableSize;
        }
    }

    private void b() {
        for (int i2 = 0; i2 < this.b.length; i2++) {
            ArrayRow arrayRow = this.b[i2];
            if (arrayRow != null) {
                this.e.a.a(arrayRow);
            }
            this.b[i2] = null;
        }
    }

    public void reset() {
        for (SolverVariable solverVariable : this.e.c) {
            if (solverVariable != null) {
                solverVariable.reset();
            }
        }
        this.e.b.a(this.m, this.n);
        this.n = 0;
        Arrays.fill(this.e.c, null);
        if (this.g != null) {
            this.g.clear();
        }
        this.a = 0;
        this.h.clear();
        this.c = 1;
        for (int i2 = 0; i2 < this.d; i2++) {
            this.b[i2].c = false;
        }
        b();
        this.d = 0;
    }

    public SolverVariable createObjectVariable(Object obj) {
        SolverVariable solverVariable = null;
        if (obj == null) {
            return null;
        }
        if (this.c + 1 >= this.j) {
            a();
        }
        if (obj instanceof ConstraintAnchor) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) obj;
            solverVariable = constraintAnchor.getSolverVariable();
            if (solverVariable == null) {
                constraintAnchor.resetSolverVariable(this.e);
                solverVariable = constraintAnchor.getSolverVariable();
            }
            if (solverVariable.f234id == -1 || solverVariable.f234id > this.a || this.e.c[solverVariable.f234id] == null) {
                if (solverVariable.f234id != -1) {
                    solverVariable.reset();
                }
                this.a++;
                this.c++;
                solverVariable.f234id = this.a;
                solverVariable.c = Type.UNRESTRICTED;
                this.e.c[this.a] = solverVariable;
            }
        }
        return solverVariable;
    }

    public ArrayRow createRow() {
        ArrayRow arrayRow = (ArrayRow) this.e.a.a();
        if (arrayRow == null) {
            arrayRow = new ArrayRow(this.e);
        } else {
            arrayRow.reset();
        }
        SolverVariable.a();
        return arrayRow;
    }

    public SolverVariable createSlackVariable() {
        if (sMetrics != null) {
            Metrics metrics = sMetrics;
            metrics.slackvariables++;
        }
        if (this.c + 1 >= this.j) {
            a();
        }
        SolverVariable a2 = a(Type.SLACK, (String) null);
        this.a++;
        this.c++;
        a2.f234id = this.a;
        this.e.c[this.a] = a2;
        return a2;
    }

    public SolverVariable createExtraVariable() {
        if (sMetrics != null) {
            Metrics metrics = sMetrics;
            metrics.extravariables++;
        }
        if (this.c + 1 >= this.j) {
            a();
        }
        SolverVariable a2 = a(Type.SLACK, (String) null);
        this.a++;
        this.c++;
        a2.f234id = this.a;
        this.e.c[this.a] = a2;
        return a2;
    }

    private void a(ArrayRow arrayRow) {
        arrayRow.addError(this, 0);
    }

    private void a(ArrayRow arrayRow, int i2) {
        a(arrayRow, i2, 0);
    }

    /* access modifiers changed from: 0000 */
    public void a(ArrayRow arrayRow, int i2, int i3) {
        arrayRow.b(createErrorVariable(i3, null), i2);
    }

    public SolverVariable createErrorVariable(int i2, String str) {
        if (sMetrics != null) {
            Metrics metrics = sMetrics;
            metrics.errors++;
        }
        if (this.c + 1 >= this.j) {
            a();
        }
        SolverVariable a2 = a(Type.ERROR, str);
        this.a++;
        this.c++;
        a2.f234id = this.a;
        a2.strength = i2;
        this.e.c[this.a] = a2;
        this.h.addError(a2);
        return a2;
    }

    private SolverVariable a(Type type, String str) {
        SolverVariable solverVariable = (SolverVariable) this.e.b.a();
        if (solverVariable == null) {
            solverVariable = new SolverVariable(type, str);
            solverVariable.setType(type, str);
        } else {
            solverVariable.reset();
            solverVariable.setType(type, str);
        }
        if (this.n >= f) {
            f *= 2;
            this.m = (SolverVariable[]) Arrays.copyOf(this.m, f);
        }
        SolverVariable[] solverVariableArr = this.m;
        int i2 = this.n;
        this.n = i2 + 1;
        solverVariableArr[i2] = solverVariable;
        return solverVariable;
    }

    public int getObjectVariableValue(Object obj) {
        SolverVariable solverVariable = ((ConstraintAnchor) obj).getSolverVariable();
        if (solverVariable != null) {
            return (int) (solverVariable.computedValue + 0.5f);
        }
        return 0;
    }

    public void minimize() {
        if (sMetrics != null) {
            Metrics metrics = sMetrics;
            metrics.minimize++;
        }
        if (this.graphOptimizer) {
            if (sMetrics != null) {
                Metrics metrics2 = sMetrics;
                metrics2.graphOptimizer++;
            }
            boolean z = false;
            int i2 = 0;
            while (true) {
                if (i2 >= this.d) {
                    z = true;
                    break;
                } else if (!this.b[i2].d) {
                    break;
                } else {
                    i2++;
                }
            }
            if (!z) {
                a(this.h);
                return;
            }
            if (sMetrics != null) {
                Metrics metrics3 = sMetrics;
                metrics3.fullySolved++;
            }
            c();
            return;
        }
        a(this.h);
    }

    /* access modifiers changed from: 0000 */
    public void a(Row row) {
        if (sMetrics != null) {
            Metrics metrics = sMetrics;
            metrics.minimizeGoal++;
            sMetrics.maxVariables = Math.max(sMetrics.maxVariables, (long) this.c);
            sMetrics.maxRows = Math.max(sMetrics.maxRows, (long) this.d);
        }
        b((ArrayRow) row);
        b(row);
        a(row, false);
        c();
    }

    private final void b(ArrayRow arrayRow) {
        if (this.d > 0) {
            arrayRow.variables.a(arrayRow, this.b);
            if (arrayRow.variables.a == 0) {
                arrayRow.d = true;
            }
        }
    }

    public void addConstraint(ArrayRow arrayRow) {
        if (arrayRow != null) {
            if (sMetrics != null) {
                Metrics metrics = sMetrics;
                metrics.constraints++;
                if (arrayRow.d) {
                    Metrics metrics2 = sMetrics;
                    metrics2.simpleconstraints++;
                }
            }
            if (this.d + 1 >= this.l || this.c + 1 >= this.j) {
                a();
            }
            boolean z = false;
            if (!arrayRow.d) {
                b(arrayRow);
                if (!arrayRow.isEmpty()) {
                    arrayRow.d();
                    if (arrayRow.a(this)) {
                        SolverVariable createExtraVariable = createExtraVariable();
                        arrayRow.a = createExtraVariable;
                        c(arrayRow);
                        this.p.initFromRow(arrayRow);
                        a(this.p, true);
                        if (createExtraVariable.a == -1) {
                            if (arrayRow.a == createExtraVariable) {
                                SolverVariable b2 = arrayRow.b(createExtraVariable);
                                if (b2 != null) {
                                    if (sMetrics != null) {
                                        Metrics metrics3 = sMetrics;
                                        metrics3.pivots++;
                                    }
                                    arrayRow.c(b2);
                                }
                            }
                            if (!arrayRow.d) {
                                arrayRow.a.updateReferencesWithNewDefinition(arrayRow);
                            }
                            this.d--;
                        }
                        z = true;
                    }
                    if (!arrayRow.a()) {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (!z) {
                c(arrayRow);
            }
        }
    }

    private final void c(ArrayRow arrayRow) {
        if (this.b[this.d] != null) {
            this.e.a.a(this.b[this.d]);
        }
        this.b[this.d] = arrayRow;
        arrayRow.a.a = this.d;
        this.d++;
        arrayRow.a.updateReferencesWithNewDefinition(arrayRow);
    }

    private final int a(Row row, boolean z) {
        if (sMetrics != null) {
            Metrics metrics = sMetrics;
            metrics.optimize++;
        }
        for (int i2 = 0; i2 < this.c; i2++) {
            this.k[i2] = false;
        }
        boolean z2 = false;
        int i3 = 0;
        while (!z2) {
            if (sMetrics != null) {
                Metrics metrics2 = sMetrics;
                metrics2.iterations++;
            }
            i3++;
            if (i3 >= this.c * 2) {
                return i3;
            }
            if (row.getKey() != null) {
                this.k[row.getKey().f234id] = true;
            }
            SolverVariable pivotCandidate = row.getPivotCandidate(this, this.k);
            if (pivotCandidate != null) {
                if (this.k[pivotCandidate.f234id]) {
                    return i3;
                }
                this.k[pivotCandidate.f234id] = true;
            }
            if (pivotCandidate != null) {
                int i4 = -1;
                float f2 = Float.MAX_VALUE;
                for (int i5 = 0; i5 < this.d; i5++) {
                    ArrayRow arrayRow = this.b[i5];
                    if (arrayRow.a.c != Type.UNRESTRICTED && !arrayRow.d && arrayRow.a(pivotCandidate)) {
                        float f3 = arrayRow.variables.get(pivotCandidate);
                        if (f3 < BitmapDescriptorFactory.HUE_RED) {
                            float f4 = (-arrayRow.b) / f3;
                            if (f4 < f2) {
                                i4 = i5;
                                f2 = f4;
                            }
                        }
                    }
                }
                if (i4 > -1) {
                    ArrayRow arrayRow2 = this.b[i4];
                    arrayRow2.a.a = -1;
                    if (sMetrics != null) {
                        Metrics metrics3 = sMetrics;
                        metrics3.pivots++;
                    }
                    arrayRow2.c(pivotCandidate);
                    arrayRow2.a.a = i4;
                    arrayRow2.a.updateReferencesWithNewDefinition(arrayRow2);
                }
            }
            z2 = true;
        }
        return i3;
    }

    private int b(Row row) {
        float f2;
        boolean z;
        int i2 = 0;
        while (true) {
            int i3 = this.d;
            f2 = BitmapDescriptorFactory.HUE_RED;
            if (i2 >= i3) {
                z = false;
                break;
            } else if (this.b[i2].a.c != Type.UNRESTRICTED && this.b[i2].b < BitmapDescriptorFactory.HUE_RED) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (!z) {
            return 0;
        }
        boolean z2 = false;
        int i4 = 0;
        while (!z2) {
            if (sMetrics != null) {
                Metrics metrics = sMetrics;
                metrics.bfs++;
            }
            i4++;
            int i5 = 0;
            int i6 = -1;
            int i7 = -1;
            float f3 = Float.MAX_VALUE;
            int i8 = 0;
            while (i5 < this.d) {
                ArrayRow arrayRow = this.b[i5];
                if (arrayRow.a.c != Type.UNRESTRICTED && !arrayRow.d && arrayRow.b < f2) {
                    int i9 = 1;
                    while (i9 < this.c) {
                        SolverVariable solverVariable = this.e.c[i9];
                        float f4 = arrayRow.variables.get(solverVariable);
                        if (f4 > f2) {
                            int i10 = i8;
                            float f5 = f3;
                            int i11 = i7;
                            int i12 = i6;
                            for (int i13 = 0; i13 < 7; i13++) {
                                float f6 = solverVariable.b[i13] / f4;
                                if ((f6 < f5 && i13 == i10) || i13 > i10) {
                                    i11 = i9;
                                    i12 = i5;
                                    f5 = f6;
                                    i10 = i13;
                                }
                            }
                            i6 = i12;
                            i7 = i11;
                            f3 = f5;
                            i8 = i10;
                        }
                        i9++;
                        f2 = BitmapDescriptorFactory.HUE_RED;
                    }
                }
                i5++;
                f2 = BitmapDescriptorFactory.HUE_RED;
            }
            if (i6 != -1) {
                ArrayRow arrayRow2 = this.b[i6];
                arrayRow2.a.a = -1;
                if (sMetrics != null) {
                    Metrics metrics2 = sMetrics;
                    metrics2.pivots++;
                }
                arrayRow2.c(this.e.c[i7]);
                arrayRow2.a.a = i6;
                arrayRow2.a.updateReferencesWithNewDefinition(arrayRow2);
            } else {
                z2 = true;
            }
            if (i4 > this.c / 2) {
                z2 = true;
            }
            f2 = BitmapDescriptorFactory.HUE_RED;
        }
        return i4;
    }

    private void c() {
        for (int i2 = 0; i2 < this.d; i2++) {
            ArrayRow arrayRow = this.b[i2];
            arrayRow.a.computedValue = arrayRow.b;
        }
    }

    public void displayVariablesReadableRows() {
        d();
        String str = "";
        for (int i2 = 0; i2 < this.d; i2++) {
            if (this.b[i2].a.c == Type.UNRESTRICTED) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(this.b[i2].b());
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append("\n");
                str = sb3.toString();
            }
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append(this.h);
        sb4.append("\n");
        System.out.println(sb4.toString());
    }

    public int getMemoryUsed() {
        int i2 = 0;
        for (int i3 = 0; i3 < this.d; i3++) {
            if (this.b[i3] != null) {
                i2 += this.b[i3].c();
            }
        }
        return i2;
    }

    public int getNumEquations() {
        return this.d;
    }

    public int getNumVariables() {
        return this.a;
    }

    private void d() {
        StringBuilder sb = new StringBuilder();
        sb.append("Display Rows (");
        sb.append(this.d);
        sb.append("x");
        sb.append(this.c);
        sb.append(")\n");
        System.out.println(sb.toString());
    }

    public Cache getCache() {
        return this.e;
    }

    public void addGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowGreaterThan(solverVariable, solverVariable2, createSlackVariable, i2);
        if (i3 != 6) {
            a(createRow, (int) (createRow.variables.get(createSlackVariable) * -1.0f), i3);
        }
        addConstraint(createRow);
    }

    public void addGreaterThan(SolverVariable solverVariable, int i2) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowGreaterThan(solverVariable, i2, createSlackVariable);
        addConstraint(createRow);
    }

    public void addGreaterBarrier(SolverVariable solverVariable, SolverVariable solverVariable2, boolean z) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowGreaterThan(solverVariable, solverVariable2, createSlackVariable, 0);
        if (z) {
            a(createRow, (int) (createRow.variables.get(createSlackVariable) * -1.0f), 1);
        }
        addConstraint(createRow);
    }

    public void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowLowerThan(solverVariable, solverVariable2, createSlackVariable, i2);
        if (i3 != 6) {
            a(createRow, (int) (createRow.variables.get(createSlackVariable) * -1.0f), i3);
        }
        addConstraint(createRow);
    }

    public void addLowerBarrier(SolverVariable solverVariable, SolverVariable solverVariable2, boolean z) {
        ArrayRow createRow = createRow();
        SolverVariable createSlackVariable = createSlackVariable();
        createSlackVariable.strength = 0;
        createRow.createRowLowerThan(solverVariable, solverVariable2, createSlackVariable, 0);
        if (z) {
            a(createRow, (int) (createRow.variables.get(createSlackVariable) * -1.0f), 1);
        }
        addConstraint(createRow);
    }

    public void addCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, float f2, SolverVariable solverVariable3, SolverVariable solverVariable4, int i3, int i4) {
        int i5 = i4;
        ArrayRow createRow = createRow();
        createRow.a(solverVariable, solverVariable2, i2, f2, solverVariable3, solverVariable4, i3);
        if (i5 != 6) {
            createRow.addError(this, i5);
        }
        addConstraint(createRow);
    }

    public void addRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2, int i2) {
        ArrayRow createRow = createRow();
        createRow.createRowDimensionRatio(solverVariable, solverVariable2, solverVariable3, solverVariable4, f2);
        if (i2 != 6) {
            createRow.addError(this, i2);
        }
        addConstraint(createRow);
    }

    public ArrayRow addEquality(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        ArrayRow createRow = createRow();
        createRow.createRowEquals(solverVariable, solverVariable2, i2);
        if (i3 != 6) {
            createRow.addError(this, i3);
        }
        addConstraint(createRow);
        return createRow;
    }

    public void addEquality(SolverVariable solverVariable, int i2) {
        int i3 = solverVariable.a;
        if (solverVariable.a != -1) {
            ArrayRow arrayRow = this.b[i3];
            if (arrayRow.d) {
                arrayRow.b = (float) i2;
            } else if (arrayRow.variables.a == 0) {
                arrayRow.d = true;
                arrayRow.b = (float) i2;
            } else {
                ArrayRow createRow = createRow();
                createRow.createRowEquals(solverVariable, i2);
                addConstraint(createRow);
            }
        } else {
            ArrayRow createRow2 = createRow();
            createRow2.a(solverVariable, i2);
            addConstraint(createRow2);
        }
    }

    public void addEquality(SolverVariable solverVariable, int i2, int i3) {
        int i4 = solverVariable.a;
        if (solverVariable.a != -1) {
            ArrayRow arrayRow = this.b[i4];
            if (arrayRow.d) {
                arrayRow.b = (float) i2;
                return;
            }
            ArrayRow createRow = createRow();
            createRow.createRowEquals(solverVariable, i2);
            createRow.addError(this, i3);
            addConstraint(createRow);
            return;
        }
        ArrayRow createRow2 = createRow();
        createRow2.a(solverVariable, i2);
        createRow2.addError(this, i3);
        addConstraint(createRow2);
    }

    public static ArrayRow createRowEquals(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, int i2, boolean z) {
        ArrayRow createRow = linearSystem.createRow();
        createRow.createRowEquals(solverVariable, solverVariable2, i2);
        if (z) {
            linearSystem.a(createRow, 1);
        }
        return createRow;
    }

    public static ArrayRow createRowDimensionPercent(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, float f2, boolean z) {
        ArrayRow createRow = linearSystem.createRow();
        if (z) {
            linearSystem.a(createRow);
        }
        return createRow.a(solverVariable, solverVariable2, solverVariable3, f2);
    }

    public static ArrayRow createRowGreaterThan(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, int i2, boolean z) {
        SolverVariable createSlackVariable = linearSystem.createSlackVariable();
        ArrayRow createRow = linearSystem.createRow();
        createRow.createRowGreaterThan(solverVariable, solverVariable2, createSlackVariable, i2);
        if (z) {
            linearSystem.a(createRow, (int) (createRow.variables.get(createSlackVariable) * -1.0f));
        }
        return createRow;
    }

    public static ArrayRow createRowLowerThan(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, int i2, boolean z) {
        SolverVariable createSlackVariable = linearSystem.createSlackVariable();
        ArrayRow createRow = linearSystem.createRow();
        createRow.createRowLowerThan(solverVariable, solverVariable2, createSlackVariable, i2);
        if (z) {
            linearSystem.a(createRow, (int) (createRow.variables.get(createSlackVariable) * -1.0f));
        }
        return createRow;
    }

    public static ArrayRow createRowCentering(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, int i2, float f2, SolverVariable solverVariable3, SolverVariable solverVariable4, int i3, boolean z) {
        ArrayRow createRow = linearSystem.createRow();
        createRow.a(solverVariable, solverVariable2, i2, f2, solverVariable3, solverVariable4, i3);
        if (z) {
            createRow.addError(linearSystem, 4);
        }
        return createRow;
    }

    public void addCenterPoint(ConstraintWidget constraintWidget, ConstraintWidget constraintWidget2, float f2, int i2) {
        ConstraintWidget constraintWidget3 = constraintWidget;
        ConstraintWidget constraintWidget4 = constraintWidget2;
        SolverVariable createObjectVariable = createObjectVariable(constraintWidget3.getAnchor(ConstraintAnchor.Type.LEFT));
        SolverVariable createObjectVariable2 = createObjectVariable(constraintWidget3.getAnchor(ConstraintAnchor.Type.TOP));
        SolverVariable createObjectVariable3 = createObjectVariable(constraintWidget3.getAnchor(ConstraintAnchor.Type.RIGHT));
        SolverVariable createObjectVariable4 = createObjectVariable(constraintWidget3.getAnchor(ConstraintAnchor.Type.BOTTOM));
        SolverVariable createObjectVariable5 = createObjectVariable(constraintWidget4.getAnchor(ConstraintAnchor.Type.LEFT));
        SolverVariable createObjectVariable6 = createObjectVariable(constraintWidget4.getAnchor(ConstraintAnchor.Type.TOP));
        SolverVariable createObjectVariable7 = createObjectVariable(constraintWidget4.getAnchor(ConstraintAnchor.Type.RIGHT));
        SolverVariable createObjectVariable8 = createObjectVariable(constraintWidget4.getAnchor(ConstraintAnchor.Type.BOTTOM));
        ArrayRow createRow = createRow();
        double d2 = (double) f2;
        SolverVariable solverVariable = createObjectVariable3;
        double d3 = (double) i2;
        SolverVariable solverVariable2 = createObjectVariable7;
        createRow.createRowWithAngle(createObjectVariable2, createObjectVariable4, createObjectVariable6, createObjectVariable8, (float) (Math.sin(d2) * d3));
        addConstraint(createRow);
        ArrayRow createRow2 = createRow();
        createRow2.createRowWithAngle(createObjectVariable, solverVariable, createObjectVariable5, solverVariable2, (float) (Math.cos(d2) * d3));
        addConstraint(createRow2);
    }
}
