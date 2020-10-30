package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants.ClaseTarjeta;
import java.util.ArrayList;

public class ConstraintTableLayout extends ConstraintWidgetContainer {
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
    private boolean V = true;
    private int W = 0;
    private int X = 0;
    private int Y = 8;
    private ArrayList<VerticalSlice> Z = new ArrayList<>();
    private ArrayList<HorizontalSlice> aa = new ArrayList<>();
    private ArrayList<Guideline> ab = new ArrayList<>();
    private ArrayList<Guideline> ac = new ArrayList<>();
    private LinearSystem ad = null;

    class HorizontalSlice {
        ConstraintWidget a;
        ConstraintWidget b;

        HorizontalSlice() {
        }
    }

    class VerticalSlice {
        ConstraintWidget a;
        ConstraintWidget b;
        int c = 1;

        VerticalSlice() {
        }
    }

    public String getType() {
        return "ConstraintTableLayout";
    }

    public boolean handlesInternalConstraints() {
        return true;
    }

    public ConstraintTableLayout() {
    }

    public ConstraintTableLayout(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    public ConstraintTableLayout(int i, int i2) {
        super(i, i2);
    }

    public int getNumRows() {
        return this.X;
    }

    public int getNumCols() {
        return this.W;
    }

    public int getPadding() {
        return this.Y;
    }

    public String getColumnsAlignmentRepresentation() {
        int size = this.Z.size();
        String str = "";
        for (int i = 0; i < size; i++) {
            VerticalSlice verticalSlice = (VerticalSlice) this.Z.get(i);
            if (verticalSlice.c == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(ClaseTarjeta.PLATINUM);
                str = sb.toString();
            } else if (verticalSlice.c == 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("C");
                str = sb2.toString();
            } else if (verticalSlice.c == 3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append("F");
                str = sb3.toString();
            } else if (verticalSlice.c == 2) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append("R");
                str = sb4.toString();
            }
        }
        return str;
    }

    public String getColumnAlignmentRepresentation(int i) {
        VerticalSlice verticalSlice = (VerticalSlice) this.Z.get(i);
        if (verticalSlice.c == 1) {
            return ClaseTarjeta.PLATINUM;
        }
        if (verticalSlice.c == 0) {
            return "C";
        }
        if (verticalSlice.c == 3) {
            return "F";
        }
        return verticalSlice.c == 2 ? "R" : "!";
    }

    public void setNumCols(int i) {
        if (this.V && this.W != i) {
            this.W = i;
            b();
            setTableDimensions();
        }
    }

    public void setNumRows(int i) {
        if (!this.V && this.W != i) {
            this.X = i;
            c();
            setTableDimensions();
        }
    }

    public boolean isVerticalGrowth() {
        return this.V;
    }

    public void setVerticalGrowth(boolean z) {
        this.V = z;
    }

    public void setPadding(int i) {
        if (i > 1) {
            this.Y = i;
        }
    }

    public void setColumnAlignment(int i, int i2) {
        if (i < this.Z.size()) {
            ((VerticalSlice) this.Z.get(i)).c = i2;
            d();
        }
    }

    public void cycleColumnAlignment(int i) {
        VerticalSlice verticalSlice = (VerticalSlice) this.Z.get(i);
        switch (verticalSlice.c) {
            case 0:
                verticalSlice.c = 2;
                break;
            case 1:
                verticalSlice.c = 0;
                break;
            case 2:
                verticalSlice.c = 1;
                break;
        }
        d();
    }

    public void setColumnAlignment(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == 'L') {
                setColumnAlignment(i, 1);
            } else if (charAt == 'C') {
                setColumnAlignment(i, 0);
            } else if (charAt == 'F') {
                setColumnAlignment(i, 3);
            } else if (charAt == 'R') {
                setColumnAlignment(i, 2);
            } else {
                setColumnAlignment(i, 0);
            }
        }
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        return this.ab;
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        return this.ac;
    }

    public void addToSolver(LinearSystem linearSystem) {
        super.addToSolver(linearSystem);
        int size = this.mChildren.size();
        if (size != 0) {
            setTableDimensions();
            if (linearSystem == this.mSystem) {
                int size2 = this.ab.size();
                int i = 0;
                while (true) {
                    boolean z = true;
                    if (i >= size2) {
                        break;
                    }
                    Guideline guideline = (Guideline) this.ab.get(i);
                    if (getHorizontalDimensionBehaviour() != DimensionBehaviour.WRAP_CONTENT) {
                        z = false;
                    }
                    guideline.setPositionRelaxed(z);
                    guideline.addToSolver(linearSystem);
                    i++;
                }
                int size3 = this.ac.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    Guideline guideline2 = (Guideline) this.ac.get(i2);
                    guideline2.setPositionRelaxed(getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT);
                    guideline2.addToSolver(linearSystem);
                }
                for (int i3 = 0; i3 < size; i3++) {
                    ((ConstraintWidget) this.mChildren.get(i3)).addToSolver(linearSystem);
                }
            }
        }
    }

    public void setTableDimensions() {
        int size = this.mChildren.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += ((ConstraintWidget) this.mChildren.get(i2)).getContainerItemSkip();
        }
        int i3 = size + i;
        if (this.V) {
            if (this.W == 0) {
                setNumCols(1);
            }
            int i4 = i3 / this.W;
            if (this.W * i4 < i3) {
                i4++;
            }
            if (this.X != i4 || this.ab.size() != this.W - 1) {
                this.X = i4;
                c();
            } else {
                return;
            }
        } else {
            if (this.X == 0) {
                setNumRows(1);
            }
            int i5 = i3 / this.X;
            if (this.X * i5 < i3) {
                i5++;
            }
            if (this.W != i5 || this.ac.size() != this.X - 1) {
                this.W = i5;
                b();
            } else {
                return;
            }
        }
        d();
    }

    public void setDebugSolverName(LinearSystem linearSystem, String str) {
        this.ad = linearSystem;
        super.setDebugSolverName(linearSystem, str);
        a();
    }

    private void a() {
        if (this.ad != null) {
            int size = this.ab.size();
            for (int i = 0; i < size; i++) {
                Guideline guideline = (Guideline) this.ab.get(i);
                LinearSystem linearSystem = this.ad;
                StringBuilder sb = new StringBuilder();
                sb.append(getDebugName());
                sb.append(".VG");
                sb.append(i);
                guideline.setDebugSolverName(linearSystem, sb.toString());
            }
            int size2 = this.ac.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Guideline guideline2 = (Guideline) this.ac.get(i2);
                LinearSystem linearSystem2 = this.ad;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getDebugName());
                sb2.append(".HG");
                sb2.append(i2);
                guideline2.setDebugSolverName(linearSystem2, sb2.toString());
            }
        }
    }

    private void b() {
        this.Z.clear();
        float f = 100.0f / ((float) this.W);
        int i = 0;
        ConstraintWidget constraintWidget = this;
        float f2 = f;
        while (i < this.W) {
            VerticalSlice verticalSlice = new VerticalSlice();
            verticalSlice.a = constraintWidget;
            if (i < this.W - 1) {
                Guideline guideline = new Guideline();
                guideline.setOrientation(1);
                guideline.setParent(this);
                guideline.setGuidePercent((int) f2);
                f2 += f;
                verticalSlice.b = guideline;
                this.ab.add(guideline);
            } else {
                verticalSlice.b = this;
            }
            ConstraintWidget constraintWidget2 = verticalSlice.b;
            this.Z.add(verticalSlice);
            i++;
            constraintWidget = constraintWidget2;
        }
        a();
    }

    private void c() {
        this.aa.clear();
        float f = 100.0f / ((float) this.X);
        ConstraintWidget constraintWidget = this;
        float f2 = f;
        int i = 0;
        while (i < this.X) {
            HorizontalSlice horizontalSlice = new HorizontalSlice();
            horizontalSlice.a = constraintWidget;
            if (i < this.X - 1) {
                Guideline guideline = new Guideline();
                guideline.setOrientation(0);
                guideline.setParent(this);
                guideline.setGuidePercent((int) f2);
                f2 += f;
                horizontalSlice.b = guideline;
                this.ac.add(guideline);
            } else {
                horizontalSlice.b = this;
            }
            ConstraintWidget constraintWidget2 = horizontalSlice.b;
            this.aa.add(horizontalSlice);
            i++;
            constraintWidget = constraintWidget2;
        }
        a();
    }

    private void d() {
        int size = this.mChildren.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.mChildren.get(i2);
            int containerItemSkip = i + constraintWidget.getContainerItemSkip();
            HorizontalSlice horizontalSlice = (HorizontalSlice) this.aa.get(containerItemSkip / this.W);
            VerticalSlice verticalSlice = (VerticalSlice) this.Z.get(containerItemSkip % this.W);
            ConstraintWidget constraintWidget2 = verticalSlice.a;
            ConstraintWidget constraintWidget3 = verticalSlice.b;
            ConstraintWidget constraintWidget4 = horizontalSlice.a;
            ConstraintWidget constraintWidget5 = horizontalSlice.b;
            constraintWidget.getAnchor(Type.LEFT).connect(constraintWidget2.getAnchor(Type.LEFT), this.Y);
            if (constraintWidget3 instanceof Guideline) {
                constraintWidget.getAnchor(Type.RIGHT).connect(constraintWidget3.getAnchor(Type.LEFT), this.Y);
            } else {
                constraintWidget.getAnchor(Type.RIGHT).connect(constraintWidget3.getAnchor(Type.RIGHT), this.Y);
            }
            switch (verticalSlice.c) {
                case 1:
                    constraintWidget.getAnchor(Type.LEFT).setStrength(Strength.STRONG);
                    constraintWidget.getAnchor(Type.RIGHT).setStrength(Strength.WEAK);
                    break;
                case 2:
                    constraintWidget.getAnchor(Type.LEFT).setStrength(Strength.WEAK);
                    constraintWidget.getAnchor(Type.RIGHT).setStrength(Strength.STRONG);
                    break;
                case 3:
                    constraintWidget.setHorizontalDimensionBehaviour(DimensionBehaviour.MATCH_CONSTRAINT);
                    break;
            }
            constraintWidget.getAnchor(Type.TOP).connect(constraintWidget4.getAnchor(Type.TOP), this.Y);
            if (constraintWidget5 instanceof Guideline) {
                constraintWidget.getAnchor(Type.BOTTOM).connect(constraintWidget5.getAnchor(Type.TOP), this.Y);
            } else {
                constraintWidget.getAnchor(Type.BOTTOM).connect(constraintWidget5.getAnchor(Type.BOTTOM), this.Y);
            }
            i = containerItemSkip + 1;
        }
    }

    public void updateFromSolver(LinearSystem linearSystem) {
        super.updateFromSolver(linearSystem);
        if (linearSystem == this.mSystem) {
            int size = this.ab.size();
            for (int i = 0; i < size; i++) {
                ((Guideline) this.ab.get(i)).updateFromSolver(linearSystem);
            }
            int size2 = this.ac.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((Guideline) this.ac.get(i2)).updateFromSolver(linearSystem);
            }
        }
    }

    public void computeGuidelinesPercentPositions() {
        int size = this.ab.size();
        for (int i = 0; i < size; i++) {
            ((Guideline) this.ab.get(i)).a();
        }
        int size2 = this.ac.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((Guideline) this.ac.get(i2)).a();
        }
    }
}
