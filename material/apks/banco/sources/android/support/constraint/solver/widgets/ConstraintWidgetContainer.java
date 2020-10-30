package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer extends WidgetContainer {
    int M;
    int N;
    int O;
    int P;
    int Q = 0;
    int R = 0;
    ChainHead[] S = new ChainHead[4];
    ChainHead[] T = new ChainHead[4];
    int U = 0;
    private boolean V = false;
    private Snapshot W;
    private int X = 3;
    private boolean Y = false;
    private boolean Z = false;
    protected LinearSystem mSystem = new LinearSystem();

    public String getType() {
        return "ConstraintLayout";
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    public void fillMetrics(Metrics metrics) {
        this.mSystem.fillMetrics(metrics);
    }

    public ConstraintWidgetContainer() {
    }

    public ConstraintWidgetContainer(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    public ConstraintWidgetContainer(int i, int i2) {
        super(i, i2);
    }

    public void setOptimizationLevel(int i) {
        this.X = i;
    }

    public int getOptimizationLevel() {
        return this.X;
    }

    public boolean optimizeFor(int i) {
        return (this.X & i) == i;
    }

    public void reset() {
        this.mSystem.reset();
        this.M = 0;
        this.O = 0;
        this.N = 0;
        this.P = 0;
        super.reset();
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.Y;
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.Z;
    }

    public boolean addChildrenToSolver(LinearSystem linearSystem) {
        addToSolver(linearSystem);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.mChildren.get(i);
            if (constraintWidget instanceof ConstraintWidgetContainer) {
                DimensionBehaviour dimensionBehaviour = constraintWidget.mListDimensionBehaviors[0];
                DimensionBehaviour dimensionBehaviour2 = constraintWidget.mListDimensionBehaviors[1];
                if (dimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
                if (dimensionBehaviour2 == DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
                constraintWidget.addToSolver(linearSystem);
                if (dimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (dimensionBehaviour2 == DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setVerticalDimensionBehaviour(dimensionBehaviour2);
                }
            } else {
                Optimizer.a(this, linearSystem, constraintWidget);
                constraintWidget.addToSolver(linearSystem);
            }
        }
        if (this.Q > 0) {
            Chain.a(this, linearSystem, 0);
        }
        if (this.R > 0) {
            Chain.a(this, linearSystem, 1);
        }
        return true;
    }

    public void updateChildrenFromSolver(LinearSystem linearSystem, boolean[] zArr) {
        zArr[2] = false;
        updateFromSolver(linearSystem);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.mChildren.get(i);
            constraintWidget.updateFromSolver(linearSystem);
            if (constraintWidget.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.getWidth() < constraintWidget.getWrapWidth()) {
                zArr[2] = true;
            }
            if (constraintWidget.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.getHeight() < constraintWidget.getWrapHeight()) {
                zArr[2] = true;
            }
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.M = i;
        this.N = i2;
        this.O = i3;
        this.P = i4;
    }

    public void setRtl(boolean z) {
        this.V = z;
    }

    public boolean isRtl() {
        return this.V;
    }

    public void analyze(int i) {
        super.analyze(i);
        int size = this.mChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((ConstraintWidget) this.mChildren.get(i2)).analyze(i);
        }
    }

    /* JADX WARNING: type inference failed for: r12v8, types: [boolean] */
    /* JADX WARNING: type inference failed for: r12v12 */
    /* JADX WARNING: type inference failed for: r12v13 */
    /* JADX WARNING: type inference failed for: r12v28 */
    /* JADX WARNING: type inference failed for: r12v29 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r12v8, types: [boolean]
      assigns: []
      uses: [?[int, short, byte, char], boolean]
      mth insns count: 270
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00e4  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x013b  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01b8  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01d4  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01e1  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01e4  */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void layout() {
        /*
            r18 = this;
            r1 = r18
            int r2 = r1.mX
            int r3 = r1.mY
            int r4 = r18.getWidth()
            r5 = 0
            int r4 = java.lang.Math.max(r5, r4)
            int r6 = r18.getHeight()
            int r6 = java.lang.Math.max(r5, r6)
            r1.Y = r5
            r1.Z = r5
            android.support.constraint.solver.widgets.ConstraintWidget r7 = r1.x
            if (r7 == 0) goto L_0x0046
            android.support.constraint.solver.widgets.Snapshot r7 = r1.W
            if (r7 != 0) goto L_0x002a
            android.support.constraint.solver.widgets.Snapshot r7 = new android.support.constraint.solver.widgets.Snapshot
            r7.<init>(r1)
            r1.W = r7
        L_0x002a:
            android.support.constraint.solver.widgets.Snapshot r7 = r1.W
            r7.updateFrom(r1)
            int r7 = r1.M
            r1.setX(r7)
            int r7 = r1.N
            r1.setY(r7)
            r18.resetAnchors()
            android.support.constraint.solver.LinearSystem r7 = r1.mSystem
            android.support.constraint.solver.Cache r7 = r7.getCache()
            r1.resetSolverVariables(r7)
            goto L_0x004a
        L_0x0046:
            r1.mX = r5
            r1.mY = r5
        L_0x004a:
            int r7 = r1.X
            r8 = 8
            r9 = 1
            if (r7 == 0) goto L_0x0062
            boolean r7 = r1.optimizeFor(r8)
            if (r7 != 0) goto L_0x005a
            r18.optimizeReset()
        L_0x005a:
            r18.optimize()
            android.support.constraint.solver.LinearSystem r7 = r1.mSystem
            r7.graphOptimizer = r9
            goto L_0x0066
        L_0x0062:
            android.support.constraint.solver.LinearSystem r7 = r1.mSystem
            r7.graphOptimizer = r5
        L_0x0066:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r1.mListDimensionBehaviors
            r7 = r7[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r10 = r1.mListDimensionBehaviors
            r10 = r10[r5]
            r18.a()
            java.util.ArrayList r11 = r1.mChildren
            int r11 = r11.size()
            r12 = 0
        L_0x0078:
            if (r12 >= r11) goto L_0x008e
            java.util.ArrayList r13 = r1.mChildren
            java.lang.Object r13 = r13.get(r12)
            android.support.constraint.solver.widgets.ConstraintWidget r13 = (android.support.constraint.solver.widgets.ConstraintWidget) r13
            boolean r14 = r13 instanceof android.support.constraint.solver.widgets.WidgetContainer
            if (r14 == 0) goto L_0x008b
            android.support.constraint.solver.widgets.WidgetContainer r13 = (android.support.constraint.solver.widgets.WidgetContainer) r13
            r13.layout()
        L_0x008b:
            int r12 = r12 + 1
            goto L_0x0078
        L_0x008e:
            r12 = 1
            r13 = 0
            r14 = 0
        L_0x0091:
            if (r12 == 0) goto L_0x0228
            int r13 = r13 + r9
            android.support.constraint.solver.LinearSystem r15 = r1.mSystem     // Catch:{ Exception -> 0x00c5 }
            r15.reset()     // Catch:{ Exception -> 0x00c5 }
            android.support.constraint.solver.LinearSystem r15 = r1.mSystem     // Catch:{ Exception -> 0x00c5 }
            r1.createObjectVariables(r15)     // Catch:{ Exception -> 0x00c5 }
            r15 = 0
        L_0x009f:
            if (r15 >= r11) goto L_0x00b4
            java.util.ArrayList r8 = r1.mChildren     // Catch:{ Exception -> 0x00c5 }
            java.lang.Object r8 = r8.get(r15)     // Catch:{ Exception -> 0x00c5 }
            android.support.constraint.solver.widgets.ConstraintWidget r8 = (android.support.constraint.solver.widgets.ConstraintWidget) r8     // Catch:{ Exception -> 0x00c5 }
            android.support.constraint.solver.LinearSystem r9 = r1.mSystem     // Catch:{ Exception -> 0x00c5 }
            r8.createObjectVariables(r9)     // Catch:{ Exception -> 0x00c5 }
            int r15 = r15 + 1
            r8 = 8
            r9 = 1
            goto L_0x009f
        L_0x00b4:
            android.support.constraint.solver.LinearSystem r8 = r1.mSystem     // Catch:{ Exception -> 0x00c5 }
            boolean r8 = r1.addChildrenToSolver(r8)     // Catch:{ Exception -> 0x00c5 }
            if (r8 == 0) goto L_0x00e1
            android.support.constraint.solver.LinearSystem r9 = r1.mSystem     // Catch:{ Exception -> 0x00c2 }
            r9.minimize()     // Catch:{ Exception -> 0x00c2 }
            goto L_0x00e1
        L_0x00c2:
            r0 = move-exception
            r12 = r8
            goto L_0x00c6
        L_0x00c5:
            r0 = move-exception
        L_0x00c6:
            r8 = r0
            r8.printStackTrace()
            java.io.PrintStream r9 = java.lang.System.out
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r5 = "EXCEPTION : "
            r15.append(r5)
            r15.append(r8)
            java.lang.String r5 = r15.toString()
            r9.println(r5)
            r8 = r12
        L_0x00e1:
            r5 = 2
            if (r8 == 0) goto L_0x00ef
            android.support.constraint.solver.LinearSystem r8 = r1.mSystem
            boolean[] r9 = android.support.constraint.solver.widgets.Optimizer.a
            r1.updateChildrenFromSolver(r8, r9)
        L_0x00eb:
            r5 = 8
            r9 = 2
            goto L_0x0139
        L_0x00ef:
            android.support.constraint.solver.LinearSystem r8 = r1.mSystem
            r1.updateFromSolver(r8)
            r8 = 0
        L_0x00f5:
            if (r8 >= r11) goto L_0x0136
            java.util.ArrayList r9 = r1.mChildren
            java.lang.Object r9 = r9.get(r8)
            android.support.constraint.solver.widgets.ConstraintWidget r9 = (android.support.constraint.solver.widgets.ConstraintWidget) r9
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r12 = r9.mListDimensionBehaviors
            r15 = 0
            r12 = r12[r15]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r15 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r12 != r15) goto L_0x0118
            int r12 = r9.getWidth()
            int r15 = r9.getWrapWidth()
            if (r12 >= r15) goto L_0x0118
            boolean[] r8 = android.support.constraint.solver.widgets.Optimizer.a
            r12 = 1
            r8[r5] = r12
            goto L_0x00eb
        L_0x0118:
            r12 = 1
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r15 = r9.mListDimensionBehaviors
            r15 = r15[r12]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r15 != r5) goto L_0x0131
            int r5 = r9.getHeight()
            int r9 = r9.getWrapHeight()
            if (r5 >= r9) goto L_0x0131
            boolean[] r5 = android.support.constraint.solver.widgets.Optimizer.a
            r9 = 2
            r5[r9] = r12
            goto L_0x0137
        L_0x0131:
            r9 = 2
            int r8 = r8 + 1
            r5 = 2
            goto L_0x00f5
        L_0x0136:
            r9 = 2
        L_0x0137:
            r5 = 8
        L_0x0139:
            if (r13 >= r5) goto L_0x01a6
            boolean[] r8 = android.support.constraint.solver.widgets.Optimizer.a
            boolean r8 = r8[r9]
            if (r8 == 0) goto L_0x01a6
            r8 = 0
            r9 = 0
            r12 = 0
        L_0x0144:
            if (r8 >= r11) goto L_0x016a
            java.util.ArrayList r15 = r1.mChildren
            java.lang.Object r15 = r15.get(r8)
            android.support.constraint.solver.widgets.ConstraintWidget r15 = (android.support.constraint.solver.widgets.ConstraintWidget) r15
            int r5 = r15.mX
            int r16 = r15.getWidth()
            int r5 = r5 + r16
            int r9 = java.lang.Math.max(r9, r5)
            int r5 = r15.mY
            int r15 = r15.getHeight()
            int r5 = r5 + r15
            int r12 = java.lang.Math.max(r12, r5)
            int r8 = r8 + 1
            r5 = 8
            goto L_0x0144
        L_0x016a:
            int r5 = r1.mMinWidth
            int r5 = java.lang.Math.max(r5, r9)
            int r8 = r1.mMinHeight
            int r8 = java.lang.Math.max(r8, r12)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r10 != r9) goto L_0x018d
            int r9 = r18.getWidth()
            if (r9 >= r5) goto L_0x018d
            r1.setWidth(r5)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r1.mListDimensionBehaviors
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r12 = 0
            r5[r12] = r9
            r5 = 1
            r9 = 1
            goto L_0x018f
        L_0x018d:
            r9 = r14
            r5 = 0
        L_0x018f:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r7 != r12) goto L_0x01a8
            int r12 = r18.getHeight()
            if (r12 >= r8) goto L_0x01a8
            r1.setHeight(r8)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r1.mListDimensionBehaviors
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r9 = 1
            r5[r9] = r8
            r5 = 1
            r9 = 1
            goto L_0x01a8
        L_0x01a6:
            r9 = r14
            r5 = 0
        L_0x01a8:
            int r8 = r1.mMinWidth
            int r12 = r18.getWidth()
            int r8 = java.lang.Math.max(r8, r12)
            int r12 = r18.getWidth()
            if (r8 <= r12) goto L_0x01c4
            r1.setWidth(r8)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r1.mListDimensionBehaviors
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r9 = 0
            r5[r9] = r8
            r5 = 1
            r9 = 1
        L_0x01c4:
            int r8 = r1.mMinHeight
            int r12 = r18.getHeight()
            int r8 = java.lang.Math.max(r8, r12)
            int r12 = r18.getHeight()
            if (r8 <= r12) goto L_0x01e1
            r1.setHeight(r8)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r1.mListDimensionBehaviors
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r12 = 1
            r5[r12] = r8
            r5 = 1
            r9 = 1
            goto L_0x01e2
        L_0x01e1:
            r12 = 1
        L_0x01e2:
            if (r9 != 0) goto L_0x0220
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r8 = r1.mListDimensionBehaviors
            r14 = 0
            r8 = r8[r14]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r15 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r8 != r15) goto L_0x0202
            if (r4 <= 0) goto L_0x0202
            int r8 = r18.getWidth()
            if (r8 <= r4) goto L_0x0202
            r1.Y = r12
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r1.mListDimensionBehaviors
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r5[r14] = r8
            r1.setWidth(r4)
            r5 = 1
            r9 = 1
        L_0x0202:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r8 = r1.mListDimensionBehaviors
            r8 = r8[r12]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r8 != r14) goto L_0x0220
            if (r6 <= 0) goto L_0x0220
            int r8 = r18.getHeight()
            if (r8 <= r6) goto L_0x0220
            r1.Z = r12
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r1.mListDimensionBehaviors
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r5[r12] = r8
            r1.setHeight(r6)
            r12 = 1
            r14 = 1
            goto L_0x0222
        L_0x0220:
            r12 = r5
            r14 = r9
        L_0x0222:
            r5 = 0
            r8 = 8
            r9 = 1
            goto L_0x0091
        L_0x0228:
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r1.x
            if (r4 == 0) goto L_0x0258
            int r2 = r1.mMinWidth
            int r3 = r18.getWidth()
            int r2 = java.lang.Math.max(r2, r3)
            int r3 = r1.mMinHeight
            int r4 = r18.getHeight()
            int r3 = java.lang.Math.max(r3, r4)
            android.support.constraint.solver.widgets.Snapshot r4 = r1.W
            r4.applyTo(r1)
            int r4 = r1.M
            int r2 = r2 + r4
            int r4 = r1.O
            int r2 = r2 + r4
            r1.setWidth(r2)
            int r2 = r1.N
            int r3 = r3 + r2
            int r2 = r1.P
            int r3 = r3 + r2
            r1.setHeight(r3)
            goto L_0x025c
        L_0x0258:
            r1.mX = r2
            r1.mY = r3
        L_0x025c:
            if (r14 == 0) goto L_0x0268
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.mListDimensionBehaviors
            r3 = 0
            r2[r3] = r10
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.mListDimensionBehaviors
            r3 = 1
            r2[r3] = r7
        L_0x0268:
            android.support.constraint.solver.LinearSystem r2 = r1.mSystem
            android.support.constraint.solver.Cache r2 = r2.getCache()
            r1.resetSolverVariables(r2)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r2 = r18.getRootConstraintContainer()
            if (r1 != r2) goto L_0x027a
            r18.updateDrawPosition()
        L_0x027a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.layout():void");
    }

    public void preOptimize() {
        optimizeReset();
        analyze(this.X);
    }

    public void solveGraph() {
        ResolutionAnchor resolutionNode = getAnchor(Type.LEFT).getResolutionNode();
        ResolutionAnchor resolutionNode2 = getAnchor(Type.TOP).getResolutionNode();
        resolutionNode.resolve(null, BitmapDescriptorFactory.HUE_RED);
        resolutionNode2.resolve(null, BitmapDescriptorFactory.HUE_RED);
    }

    public void resetGraph() {
        ResolutionAnchor resolutionNode = getAnchor(Type.LEFT).getResolutionNode();
        ResolutionAnchor resolutionNode2 = getAnchor(Type.TOP).getResolutionNode();
        resolutionNode.invalidateAnchors();
        resolutionNode2.invalidateAnchors();
        resolutionNode.resolve(null, BitmapDescriptorFactory.HUE_RED);
        resolutionNode2.resolve(null, BitmapDescriptorFactory.HUE_RED);
    }

    public void optimizeForDimensions(int i, int i2) {
        if (!(this.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT || this.a == null)) {
            this.a.resolve(i);
        }
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.WRAP_CONTENT && this.b != null) {
            this.b.resolve(i2);
        }
    }

    public void optimizeReset() {
        int size = this.mChildren.size();
        resetResolutionNodes();
        for (int i = 0; i < size; i++) {
            ((ConstraintWidget) this.mChildren.get(i)).resetResolutionNodes();
        }
    }

    public void optimize() {
        if (!optimizeFor(8)) {
            analyze(this.X);
        }
        solveGraph();
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        ArrayList<Guideline> arrayList = new ArrayList<>();
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.mChildren.get(i);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 1) {
                    arrayList.add(guideline);
                }
            }
        }
        return arrayList;
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        ArrayList<Guideline> arrayList = new ArrayList<>();
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.mChildren.get(i);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 0) {
                    arrayList.add(guideline);
                }
            }
        }
        return arrayList;
    }

    public LinearSystem getSystem() {
        return this.mSystem;
    }

    private void a() {
        this.Q = 0;
        this.R = 0;
    }

    /* access modifiers changed from: 0000 */
    public void a(ConstraintWidget constraintWidget, int i) {
        if (i == 0) {
            a(constraintWidget);
        } else if (i == 1) {
            b(constraintWidget);
        }
    }

    private void a(ConstraintWidget constraintWidget) {
        if (this.Q + 1 >= this.T.length) {
            this.T = (ChainHead[]) Arrays.copyOf(this.T, this.T.length * 2);
        }
        this.T[this.Q] = new ChainHead(constraintWidget, 0, isRtl());
        this.Q++;
    }

    private void b(ConstraintWidget constraintWidget) {
        if (this.R + 1 >= this.S.length) {
            this.S = (ChainHead[]) Arrays.copyOf(this.S, this.S.length * 2);
        }
        this.S[this.R] = new ChainHead(constraintWidget, 1, isRtl());
        this.R++;
    }
}
