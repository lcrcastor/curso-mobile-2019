package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer extends WidgetContainer {
    static boolean ALLOW_ROOT_GROUP = true;
    private static final boolean DEBUG = false;
    private static final boolean USE_DIRECT_CHAIN_RESOLUTION = true;
    private static final boolean USE_SNAPSHOT = true;
    private static final boolean USE_THREAD = false;
    protected LinearSystem mBackgroundSystem = null;
    private ConstraintWidget[] mHorizontalChainsArray = new ConstraintWidget[4];
    private int mHorizontalChainsSize = 0;
    private ConstraintWidget[] mMatchConstraintsChainedWidgets = new ConstraintWidget[4];
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    private Snapshot mSnapshot;
    protected LinearSystem mSystem = new LinearSystem();
    private ConstraintWidget[] mVerticalChainsArray = new ConstraintWidget[4];
    private int mVerticalChainsSize = 0;
    int mWrapHeight;
    int mWrapWidth;

    public ConstraintWidgetContainer() {
    }

    public ConstraintWidgetContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ConstraintWidgetContainer(int width, int height) {
        super(width, height);
    }

    public String getType() {
        return "ConstraintLayout";
    }

    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        super.reset();
    }

    public static ConstraintWidgetContainer createContainer(ConstraintWidgetContainer container, String name, ArrayList<ConstraintWidget> widgets, int padding) {
        Rectangle bounds = getBounds(widgets);
        if (bounds.width == 0 || bounds.height == 0) {
            return null;
        }
        if (padding > 0) {
            int maxPadding = Math.min(bounds.x, bounds.y);
            if (padding > maxPadding) {
                padding = maxPadding;
            }
            bounds.grow(padding, padding);
        }
        container.setOrigin(bounds.x, bounds.y);
        container.setDimension(bounds.width, bounds.height);
        container.setDebugName(name);
        ConstraintWidget parent = ((ConstraintWidget) widgets.get(0)).getParent();
        int widgetsSize = widgets.size();
        for (int i = 0; i < widgetsSize; i++) {
            ConstraintWidget widget = (ConstraintWidget) widgets.get(i);
            if (widget.getParent() == parent) {
                container.add(widget);
                widget.setX(widget.getX() - bounds.x);
                widget.setY(widget.getY() - bounds.y);
            }
        }
        return container;
    }

    public void addChildrenToSolver(LinearSystem system, int group) {
        addToSolver(system, group);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof ConstraintWidgetContainer) {
                DimensionBehaviour horizontalBehaviour = widget.mHorizontalDimensionBehaviour;
                DimensionBehaviour verticalBehaviour = widget.mVerticalDimensionBehaviour;
                if (horizontalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
                if (verticalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
                widget.addToSolver(system, group);
                if (horizontalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(horizontalBehaviour);
                }
                if (verticalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(verticalBehaviour);
                }
            } else {
                widget.addToSolver(system, group);
            }
        }
        if (this.mHorizontalChainsSize > 0) {
            applyHorizontalChain(system);
        }
        if (this.mVerticalChainsSize > 0) {
            applyVerticalChain(system);
        }
    }

    private void applyHorizontalChain(LinearSystem system) {
        for (int i = 0; i < this.mHorizontalChainsSize; i++) {
            ConstraintWidget first = this.mHorizontalChainsArray[i];
            int numMatchConstraints = countMatchConstraintsChainedWidgets(this.mHorizontalChainsArray[i], 0);
            boolean chainPacked = first.mHorizontalChainPacked && numMatchConstraints == 0;
            ConstraintWidget widget = first;
            if (widget.mHorizontalChainFixedPosition && !chainPacked) {
                applyDirectResolutionHorizontalChain(system, numMatchConstraints, widget);
            } else if (numMatchConstraints == 0) {
                ConstraintWidget previous = null;
                while (true) {
                    if (previous != null && (widget.mLeft.mTarget == null || widget.mLeft.mTarget.mOwner != previous)) {
                        break;
                    }
                    int leftMargin = widget.mLeft.getMargin();
                    int rightMargin = widget.mRight.getMargin();
                    SolverVariable left = widget.mLeft.mSolverVariable;
                    SolverVariable leftTarget = widget.mLeft.mTarget != null ? widget.mLeft.mTarget.mSolverVariable : null;
                    SolverVariable right = widget.mRight.mSolverVariable;
                    SolverVariable rightTarget = widget.mRight.mTarget != null ? widget.mRight.mTarget.mSolverVariable : null;
                    int margin = leftMargin;
                    if (previous != null) {
                        margin += previous.mRight.getMargin();
                    }
                    if (leftTarget != null) {
                        if (!chainPacked || widget == first) {
                            system.addGreaterThan(left, leftTarget, margin);
                        } else {
                            system.addEquality(left, leftTarget, margin);
                        }
                    }
                    if (rightTarget != null) {
                        int margin2 = rightMargin;
                        ConstraintAnchor nextLeft = widget.mRight.mTarget.mOwner.mLeft;
                        ConstraintWidget nextLeftTarget = nextLeft.mTarget != null ? nextLeft.mTarget.mOwner : null;
                        if (nextLeftTarget == widget) {
                            margin2 += nextLeft.getMargin();
                        }
                        if (!chainPacked || nextLeftTarget != widget) {
                            system.addLowerThan(right, rightTarget, -margin2);
                        } else {
                            system.addEquality(right, rightTarget, -margin2);
                        }
                        if (!chainPacked && leftTarget != null) {
                            system.addCentering(left, leftTarget, leftMargin, 0.5f, rightTarget, right, rightMargin);
                        }
                    }
                    previous = widget;
                    if (rightTarget == null) {
                        break;
                    }
                    widget = widget.mRight.mTarget.mOwner;
                }
                if (chainPacked) {
                    int leftMargin2 = first.mLeft.getMargin();
                    int rightMargin2 = previous.mRight.getMargin();
                    system.addCentering(first.mLeft.mSolverVariable, first.mLeft.mTarget != null ? first.mLeft.mTarget.mSolverVariable : null, leftMargin2, first.mHorizontalBiasPercent, previous.mRight.mTarget != null ? previous.mRight.mTarget.mSolverVariable : null, previous.mRight.mSolverVariable, rightMargin2);
                }
            } else {
                ConstraintWidget previous2 = null;
                float totalWeights = 0.0f;
                while (true) {
                    if (previous2 == null || (widget.mLeft.mTarget != null && widget.mLeft.mTarget.mOwner == previous2)) {
                        if (widget.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) {
                            int margin3 = widget.mLeft.getMargin();
                            if (previous2 != null) {
                                margin3 += previous2.mRight.getMargin();
                            }
                            system.addGreaterThan(widget.mLeft.mSolverVariable, widget.mLeft.mTarget.mSolverVariable, margin3);
                            int margin4 = widget.mRight.getMargin();
                            if (widget.mRight.mTarget.mOwner.mLeft.mTarget != null && widget.mRight.mTarget.mOwner.mLeft.mTarget.mOwner == widget) {
                                margin4 += widget.mRight.mTarget.mOwner.mLeft.getMargin();
                            }
                            system.addLowerThan(widget.mRight.mSolverVariable, widget.mRight.mTarget.mSolverVariable, -margin4);
                        } else {
                            totalWeights += widget.mHorizontalWeight;
                        }
                        previous2 = widget;
                        widget = widget.mRight.mTarget.mOwner;
                    }
                }
                if (numMatchConstraints == 1) {
                    ConstraintWidget w = this.mMatchConstraintsChainedWidgets[0];
                    system.addEquality(w.mLeft.mSolverVariable, w.mLeft.mTarget.mSolverVariable, w.mLeft.getMargin());
                    system.addEquality(w.mRight.mSolverVariable, w.mRight.mTarget.mSolverVariable, w.mRight.getMargin() * -1);
                    system.addLowerThan(w.mRight.mSolverVariable, w.mRight.mTarget.mSolverVariable, 0);
                } else {
                    for (int j = 0; j < numMatchConstraints - 1; j++) {
                        ConstraintWidget current = this.mMatchConstraintsChainedWidgets[j];
                        ConstraintWidget nextWidget = this.mMatchConstraintsChainedWidgets[j + 1];
                        SolverVariable left2 = current.mLeft.mSolverVariable;
                        SolverVariable right2 = current.mRight.mSolverVariable;
                        SolverVariable nextLeft2 = nextWidget.mLeft.mSolverVariable;
                        SolverVariable nextRight = nextWidget.mRight.mSolverVariable;
                        int margin5 = current.mLeft.getMargin();
                        if (!(current.mLeft.mTarget == null || current.mLeft.mTarget.mOwner.mRight.mTarget == null || current.mLeft.mTarget.mOwner.mRight.mTarget.mOwner != current)) {
                            margin5 += current.mLeft.mTarget.mOwner.mRight.getMargin();
                        }
                        system.addGreaterThan(left2, current.mLeft.mTarget.mSolverVariable, margin5);
                        int margin6 = current.mRight.getMargin();
                        if (!(current.mRight.mTarget == null || current.mRight.mTarget.mOwner.mLeft.mTarget == null || current.mRight.mTarget.mOwner.mLeft.mTarget.mOwner != current)) {
                            margin6 += current.mRight.mTarget.mOwner.mLeft.getMargin();
                        }
                        system.addLowerThan(right2, current.mRight.mTarget.mSolverVariable, -margin6);
                        if (j + 1 == numMatchConstraints - 1) {
                            int margin7 = nextWidget.mLeft.getMargin();
                            if (!(nextWidget.mLeft.mTarget == null || nextWidget.mLeft.mTarget.mOwner.mRight.mTarget == null || nextWidget.mLeft.mTarget.mOwner.mRight.mTarget.mOwner != nextWidget)) {
                                margin7 += nextWidget.mLeft.mTarget.mOwner.mRight.getMargin();
                            }
                            system.addGreaterThan(nextLeft2, nextWidget.mLeft.mTarget.mSolverVariable, margin7);
                            int margin8 = nextWidget.mRight.getMargin();
                            if (!(nextWidget.mRight.mTarget == null || nextWidget.mRight.mTarget.mOwner.mLeft.mTarget == null || nextWidget.mRight.mTarget.mOwner.mLeft.mTarget.mOwner != nextWidget)) {
                                margin8 += nextWidget.mRight.mTarget.mOwner.mLeft.getMargin();
                            }
                            system.addLowerThan(nextRight, nextWidget.mRight.mTarget.mSolverVariable, -margin8);
                        }
                        ArrayRow row = system.createRow();
                        row.createRowEqualDimension(current.mHorizontalWeight, totalWeights, nextWidget.mHorizontalWeight, left2, current.mLeft.getMargin(), right2, current.mRight.getMargin(), nextLeft2, nextWidget.mLeft.getMargin(), nextRight, nextWidget.mRight.getMargin());
                        system.addConstraint(row);
                    }
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r22v7 */
    /* JADX WARNING: type inference failed for: r22v8 */
    /* JADX WARNING: type inference failed for: r0v53, types: [android.support.constraint.solver.widgets.ConstraintWidget] */
    /* JADX WARNING: type inference failed for: r22v10 */
    /* JADX WARNING: type inference failed for: r22v16 */
    /* JADX WARNING: type inference failed for: r22v17 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r22v7
      assigns: []
      uses: []
      mth insns count: 280
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
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void applyDirectResolutionHorizontalChain(android.support.constraint.solver.LinearSystem r20, int r21, android.support.constraint.solver.widgets.ConstraintWidget r22) {
        /*
            r19 = this;
            r7 = r22
            r16 = 0
            r6 = 0
            r10 = 0
            r3 = 0
            r15 = 0
        L_0x0008:
            if (r22 == 0) goto L_0x00c0
            int r3 = r3 + 1
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r0.mHorizontalDimensionBehaviour
            r17 = r0
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r18 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r0 = r17
            r1 = r18
            if (r0 == r1) goto L_0x00b4
            int r17 = r22.getWidth()
            int r16 = r16 + r17
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x00ae
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            int r17 = r17.getMargin()
        L_0x0038:
            int r16 = r16 + r17
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x00b1
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            int r17 = r17.getMargin()
        L_0x0052:
            int r16 = r16 + r17
        L_0x0054:
            r10 = r22
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x00bd
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r22 = r0
        L_0x0076:
            if (r22 == 0) goto L_0x0008
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x00aa
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x0008
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r17 = r0
            r0 = r17
            if (r0 == r10) goto L_0x0008
        L_0x00aa:
            r22 = 0
            goto L_0x0008
        L_0x00ae:
            r17 = 0
            goto L_0x0038
        L_0x00b1:
            r17 = 0
            goto L_0x0052
        L_0x00b4:
            r0 = r22
            float r0 = r0.mHorizontalWeight
            r17 = r0
            float r15 = r15 + r17
            goto L_0x0054
        L_0x00bd:
            r22 = 0
            goto L_0x0076
        L_0x00c0:
            r8 = 0
            if (r10 == 0) goto L_0x0105
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r10.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x0203
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r10.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r17 = r0
            int r8 = r17.getX()
        L_0x00e3:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r10.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x0105
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r10.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r0.mOwner
            r0 = r19
            if (r5 != r0) goto L_0x0105
            int r8 = r19.getRight()
        L_0x0105:
            int r17 = r8 - r6
            r0 = r17
            float r14 = (float) r0
            r0 = r16
            float r0 = (float) r0
            r17 = r0
            float r13 = r14 - r17
            int r17 = r3 + 1
            r0 = r17
            float r0 = (float) r0
            r17 = r0
            float r12 = r13 / r17
            r22 = r7
            r4 = 0
            if (r21 != 0) goto L_0x0206
            r4 = r12
        L_0x0120:
            if (r22 == 0) goto L_0x023d
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x020f
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            int r9 = r17.getMargin()
        L_0x013a:
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x0212
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            int r11 = r17.getMargin()
        L_0x0152:
            float r0 = (float) r9
            r17 = r0
            float r4 = r4 + r17
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            r0 = r17
            android.support.constraint.solver.SolverVariable r0 = r0.mSolverVariable
            r17 = r0
            int r0 = (int) r4
            r18 = r0
            r0 = r20
            r1 = r17
            r2 = r18
            r0.addEquality(r1, r2)
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r0.mHorizontalDimensionBehaviour
            r17 = r0
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r18 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r0 = r17
            r1 = r18
            if (r0 != r1) goto L_0x022d
            r17 = 0
            int r17 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r17 != 0) goto L_0x0215
            float r0 = (float) r9
            r17 = r0
            float r17 = r12 - r17
            float r0 = (float) r11
            r18 = r0
            float r17 = r17 - r18
            float r4 = r4 + r17
        L_0x018f:
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.SolverVariable r0 = r0.mSolverVariable
            r17 = r0
            int r0 = (int) r4
            r18 = r0
            r0 = r20
            r1 = r17
            r2 = r18
            r0.addEquality(r1, r2)
            if (r21 != 0) goto L_0x01aa
            float r4 = r4 + r12
        L_0x01aa:
            float r0 = (float) r11
            r17 = r0
            float r4 = r4 + r17
            r10 = r22
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x023a
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mRight
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r22 = r0
        L_0x01d1:
            if (r22 == 0) goto L_0x01f9
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x01f9
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r17 = r0
            r0 = r17
            if (r0 == r10) goto L_0x01f9
            r22 = 0
        L_0x01f9:
            r0 = r22
            r1 = r19
            if (r0 != r1) goto L_0x0120
            r22 = 0
            goto L_0x0120
        L_0x0203:
            r8 = 0
            goto L_0x00e3
        L_0x0206:
            r0 = r21
            float r0 = (float) r0
            r17 = r0
            float r12 = r13 / r17
            goto L_0x0120
        L_0x020f:
            r9 = 0
            goto L_0x013a
        L_0x0212:
            r11 = 0
            goto L_0x0152
        L_0x0215:
            r0 = r22
            float r0 = r0.mHorizontalWeight
            r17 = r0
            float r17 = r17 * r13
            float r17 = r17 / r15
            float r0 = (float) r9
            r18 = r0
            float r17 = r17 - r18
            float r0 = (float) r11
            r18 = r0
            float r17 = r17 - r18
            float r4 = r4 + r17
            goto L_0x018f
        L_0x022d:
            int r17 = r22.getWidth()
            r0 = r17
            float r0 = (float) r0
            r17 = r0
            float r4 = r4 + r17
            goto L_0x018f
        L_0x023a:
            r22 = 0
            goto L_0x01d1
        L_0x023d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.applyDirectResolutionHorizontalChain(android.support.constraint.solver.LinearSystem, int, android.support.constraint.solver.widgets.ConstraintWidget):void");
    }

    private void applyVerticalChain(LinearSystem system) {
        for (int i = 0; i < this.mVerticalChainsSize; i++) {
            ConstraintWidget first = this.mVerticalChainsArray[i];
            int numMatchConstraints = countMatchConstraintsChainedWidgets(this.mVerticalChainsArray[i], 1);
            boolean chainPacked = first.mVerticalChainPacked && numMatchConstraints == 0;
            ConstraintWidget widget = first;
            if (widget.mVerticalChainFixedPosition && !chainPacked) {
                applyDirectResolutionVerticalChain(system, numMatchConstraints, widget);
            } else if (numMatchConstraints == 0) {
                ConstraintWidget previous = null;
                while (true) {
                    if (previous != null && (widget.mTop.mTarget == null || widget.mTop.mTarget.mOwner != previous)) {
                        break;
                    }
                    int topMargin = widget.mTop.getMargin();
                    int bottomMargin = widget.mBottom.getMargin();
                    SolverVariable top = widget.mTop.mSolverVariable;
                    SolverVariable topTarget = widget.mTop.mTarget != null ? widget.mTop.mTarget.mSolverVariable : null;
                    SolverVariable bottom = widget.mBottom.mSolverVariable;
                    SolverVariable bottomTarget = widget.mBottom.mTarget != null ? widget.mBottom.mTarget.mSolverVariable : null;
                    int margin = topMargin;
                    if (previous != null) {
                        margin += previous.mBottom.getMargin();
                    }
                    if (topTarget != null) {
                        if (!chainPacked || widget == first) {
                            system.addGreaterThan(top, topTarget, margin);
                        } else {
                            system.addEquality(top, topTarget, margin);
                        }
                    }
                    if (bottomTarget != null) {
                        int margin2 = bottomMargin;
                        ConstraintAnchor nextTop = widget.mBottom.mTarget.mOwner.mTop;
                        ConstraintWidget nextTopTarget = nextTop.mTarget != null ? nextTop.mTarget.mOwner : null;
                        if (nextTopTarget == widget) {
                            margin2 += nextTop.getMargin();
                        }
                        if (!chainPacked || nextTopTarget != widget) {
                            system.addLowerThan(bottom, bottomTarget, -margin2);
                        } else {
                            system.addEquality(bottom, bottomTarget, -margin2);
                        }
                        if (!chainPacked && topTarget != null) {
                            system.addCentering(top, topTarget, topMargin, 0.5f, bottomTarget, bottom, bottomMargin);
                        }
                    }
                    previous = widget;
                    if (bottomTarget == null) {
                        break;
                    }
                    widget = widget.mBottom.mTarget.mOwner;
                }
                if (chainPacked) {
                    int topMargin2 = first.mTop.getMargin();
                    int bottomMargin2 = previous.mBottom.getMargin();
                    system.addCentering(first.mTop.mSolverVariable, first.mTop.mTarget != null ? first.mTop.mTarget.mSolverVariable : null, topMargin2, first.mVerticalBiasPercent, previous.mBottom.mTarget != null ? previous.mBottom.mTarget.mSolverVariable : null, previous.mBottom.mSolverVariable, bottomMargin2);
                }
            } else {
                ConstraintWidget previous2 = null;
                float totalWeights = 0.0f;
                while (true) {
                    if (previous2 == null || (widget.mTop.mTarget != null && widget.mTop.mTarget.mOwner == previous2)) {
                        if (widget.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) {
                            int margin3 = widget.mTop.getMargin();
                            if (previous2 != null) {
                                margin3 += previous2.mBottom.getMargin();
                            }
                            system.addGreaterThan(widget.mTop.mSolverVariable, widget.mTop.mTarget.mSolverVariable, margin3);
                            int margin4 = widget.mBottom.getMargin();
                            if (widget.mBottom.mTarget.mOwner.mTop.mTarget != null && widget.mBottom.mTarget.mOwner.mTop.mTarget.mOwner == widget) {
                                margin4 += widget.mBottom.mTarget.mOwner.mTop.getMargin();
                            }
                            system.addLowerThan(widget.mBottom.mSolverVariable, widget.mBottom.mTarget.mSolverVariable, -margin4);
                        } else {
                            totalWeights += widget.mVerticalWeight;
                        }
                        previous2 = widget;
                        widget = widget.mBottom.mTarget.mOwner;
                    }
                }
                if (numMatchConstraints == 1) {
                    ConstraintWidget w = this.mMatchConstraintsChainedWidgets[0];
                    system.addEquality(w.mTop.mSolverVariable, w.mTop.mTarget.mSolverVariable, w.mTop.getMargin());
                    system.addEquality(w.mBottom.mSolverVariable, w.mBottom.mTarget.mSolverVariable, w.mBottom.getMargin() * -1);
                    system.addLowerThan(w.mBottom.mSolverVariable, w.mBottom.mTarget.mSolverVariable, 0);
                } else {
                    for (int j = 0; j < numMatchConstraints - 1; j++) {
                        ConstraintWidget current = this.mMatchConstraintsChainedWidgets[j];
                        ConstraintWidget nextWidget = this.mMatchConstraintsChainedWidgets[j + 1];
                        SolverVariable top2 = current.mTop.mSolverVariable;
                        SolverVariable bottom2 = current.mBottom.mSolverVariable;
                        SolverVariable nextLeft = nextWidget.mTop.mSolverVariable;
                        SolverVariable nextRight = nextWidget.mBottom.mSolverVariable;
                        int margin5 = current.mTop.getMargin();
                        if (!(current.mTop.mTarget == null || current.mTop.mTarget.mOwner.mBottom.mTarget == null || current.mTop.mTarget.mOwner.mBottom.mTarget.mOwner != current)) {
                            margin5 += current.mTop.mTarget.mOwner.mBottom.getMargin();
                        }
                        system.addGreaterThan(top2, current.mTop.mTarget.mSolverVariable, margin5);
                        int margin6 = current.mBottom.getMargin();
                        if (!(current.mBottom.mTarget == null || current.mBottom.mTarget.mOwner.mTop.mTarget == null || current.mBottom.mTarget.mOwner.mTop.mTarget.mOwner != current)) {
                            margin6 += current.mBottom.mTarget.mOwner.mTop.getMargin();
                        }
                        system.addLowerThan(bottom2, current.mBottom.mTarget.mSolverVariable, -margin6);
                        if (j + 1 == numMatchConstraints - 1) {
                            int margin7 = nextWidget.mTop.getMargin();
                            if (!(nextWidget.mTop.mTarget == null || nextWidget.mTop.mTarget.mOwner.mBottom.mTarget == null || nextWidget.mTop.mTarget.mOwner.mBottom.mTarget.mOwner != nextWidget)) {
                                margin7 += nextWidget.mTop.mTarget.mOwner.mBottom.getMargin();
                            }
                            system.addGreaterThan(nextLeft, nextWidget.mTop.mTarget.mSolverVariable, margin7);
                            int margin8 = nextWidget.mBottom.getMargin();
                            if (!(nextWidget.mBottom.mTarget == null || nextWidget.mBottom.mTarget.mOwner.mTop.mTarget == null || nextWidget.mBottom.mTarget.mOwner.mTop.mTarget.mOwner != nextWidget)) {
                                margin8 += nextWidget.mBottom.mTarget.mOwner.mTop.getMargin();
                            }
                            system.addLowerThan(nextRight, nextWidget.mBottom.mTarget.mSolverVariable, -margin8);
                        }
                        ArrayRow row = system.createRow();
                        row.createRowEqualDimension(current.mVerticalWeight, totalWeights, nextWidget.mVerticalWeight, top2, current.mTop.getMargin(), bottom2, current.mBottom.getMargin(), nextLeft, nextWidget.mTop.getMargin(), nextRight, nextWidget.mBottom.getMargin());
                        system.addConstraint(row);
                    }
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r22v7 */
    /* JADX WARNING: type inference failed for: r22v8 */
    /* JADX WARNING: type inference failed for: r0v53, types: [android.support.constraint.solver.widgets.ConstraintWidget] */
    /* JADX WARNING: type inference failed for: r22v10 */
    /* JADX WARNING: type inference failed for: r22v16 */
    /* JADX WARNING: type inference failed for: r22v17 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r22v7
      assigns: []
      uses: []
      mth insns count: 280
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
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void applyDirectResolutionVerticalChain(android.support.constraint.solver.LinearSystem r20, int r21, android.support.constraint.solver.widgets.ConstraintWidget r22) {
        /*
            r19 = this;
            r8 = r22
            r16 = 0
            r7 = 0
            r10 = 0
            r4 = 0
            r15 = 0
        L_0x0008:
            if (r22 == 0) goto L_0x00c0
            int r4 = r4 + 1
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r0.mVerticalDimensionBehaviour
            r17 = r0
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r18 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r0 = r17
            r1 = r18
            if (r0 == r1) goto L_0x00b4
            int r17 = r22.getHeight()
            int r16 = r16 + r17
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x00ae
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            int r17 = r17.getMargin()
        L_0x0038:
            int r16 = r16 + r17
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x00b1
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            int r17 = r17.getMargin()
        L_0x0052:
            int r16 = r16 + r17
        L_0x0054:
            r10 = r22
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x00bd
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r22 = r0
        L_0x0076:
            if (r22 == 0) goto L_0x0008
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x00aa
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x0008
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r17 = r0
            r0 = r17
            if (r0 == r10) goto L_0x0008
        L_0x00aa:
            r22 = 0
            goto L_0x0008
        L_0x00ae:
            r17 = 0
            goto L_0x0038
        L_0x00b1:
            r17 = 0
            goto L_0x0052
        L_0x00b4:
            r0 = r22
            float r0 = r0.mVerticalWeight
            r17 = r0
            float r15 = r15 + r17
            goto L_0x0054
        L_0x00bd:
            r22 = 0
            goto L_0x0076
        L_0x00c0:
            r9 = 0
            if (r10 == 0) goto L_0x0105
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r10.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x0203
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r10.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r17 = r0
            int r9 = r17.getX()
        L_0x00e3:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r10.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x0105
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r10.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r6 = r0.mOwner
            r0 = r19
            if (r6 != r0) goto L_0x0105
            int r9 = r19.getBottom()
        L_0x0105:
            int r17 = r9 - r7
            r0 = r17
            float r14 = (float) r0
            r0 = r16
            float r0 = (float) r0
            r17 = r0
            float r12 = r14 - r17
            int r17 = r4 + 1
            r0 = r17
            float r0 = (float) r0
            r17 = r0
            float r11 = r12 / r17
            r22 = r8
            r5 = 0
            if (r21 != 0) goto L_0x0206
            r5 = r11
        L_0x0120:
            if (r22 == 0) goto L_0x023d
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x020f
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            int r13 = r17.getMargin()
        L_0x013a:
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x0212
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            int r3 = r17.getMargin()
        L_0x0152:
            float r0 = (float) r13
            r17 = r0
            float r5 = r5 + r17
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            r0 = r17
            android.support.constraint.solver.SolverVariable r0 = r0.mSolverVariable
            r17 = r0
            int r0 = (int) r5
            r18 = r0
            r0 = r20
            r1 = r17
            r2 = r18
            r0.addEquality(r1, r2)
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r0.mVerticalDimensionBehaviour
            r17 = r0
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r18 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r0 = r17
            r1 = r18
            if (r0 != r1) goto L_0x022d
            r17 = 0
            int r17 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r17 != 0) goto L_0x0215
            float r0 = (float) r13
            r17 = r0
            float r17 = r11 - r17
            float r0 = (float) r3
            r18 = r0
            float r17 = r17 - r18
            float r5 = r5 + r17
        L_0x018f:
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.SolverVariable r0 = r0.mSolverVariable
            r17 = r0
            int r0 = (int) r5
            r18 = r0
            r0 = r20
            r1 = r17
            r2 = r18
            r0.addEquality(r1, r2)
            if (r21 != 0) goto L_0x01aa
            float r5 = r5 + r11
        L_0x01aa:
            float r0 = (float) r3
            r17 = r0
            float r5 = r5 + r17
            r10 = r22
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x023a
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r22 = r0
        L_0x01d1:
            if (r22 == 0) goto L_0x01f9
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            if (r17 == 0) goto L_0x01f9
            r0 = r22
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTop
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            r17 = r0
            r0 = r17
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.mOwner
            r17 = r0
            r0 = r17
            if (r0 == r10) goto L_0x01f9
            r22 = 0
        L_0x01f9:
            r0 = r22
            r1 = r19
            if (r0 != r1) goto L_0x0120
            r22 = 0
            goto L_0x0120
        L_0x0203:
            r9 = 0
            goto L_0x00e3
        L_0x0206:
            r0 = r21
            float r0 = (float) r0
            r17 = r0
            float r11 = r12 / r17
            goto L_0x0120
        L_0x020f:
            r13 = 0
            goto L_0x013a
        L_0x0212:
            r3 = 0
            goto L_0x0152
        L_0x0215:
            r0 = r22
            float r0 = r0.mVerticalWeight
            r17 = r0
            float r17 = r17 * r12
            float r17 = r17 / r15
            float r0 = (float) r13
            r18 = r0
            float r17 = r17 - r18
            float r0 = (float) r3
            r18 = r0
            float r17 = r17 - r18
            float r5 = r5 + r17
            goto L_0x018f
        L_0x022d:
            int r17 = r22.getHeight()
            r0 = r17
            float r0 = (float) r0
            r17 = r0
            float r5 = r5 + r17
            goto L_0x018f
        L_0x023a:
            r22 = 0
            goto L_0x01d1
        L_0x023d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.applyDirectResolutionVerticalChain(android.support.constraint.solver.LinearSystem, int, android.support.constraint.solver.widgets.ConstraintWidget):void");
    }

    public void updateChildrenFromSolver(LinearSystem system, int group) {
        updateFromSolver(system, group);
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ((ConstraintWidget) this.mChildren.get(i)).updateFromSolver(system, group);
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        this.mPaddingLeft = left;
        this.mPaddingTop = top;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
    }

    public void layout() {
        int prex = this.mX;
        int prey = this.mY;
        int width = getWidth();
        int height = getHeight();
        if (this.mParent != null) {
            if (this.mSnapshot == null) {
                this.mSnapshot = new Snapshot(this);
            }
            this.mSnapshot.updateFrom(this);
            setX(this.mPaddingLeft);
            setY(this.mPaddingTop);
            resetAnchors();
            resetSolverVariables(this.mSystem.getCache());
        } else {
            this.mX = 0;
            this.mY = 0;
        }
        resetChains();
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof WidgetContainer) {
                ((WidgetContainer) widget).layout();
            }
        }
        try {
            this.mSystem.reset();
            addChildrenToSolver(this.mSystem, Integer.MAX_VALUE);
            this.mSystem.minimize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateChildrenFromSolver(this.mSystem, Integer.MAX_VALUE);
        if (this.mParent != null) {
            int width2 = getWidth();
            int height2 = getHeight();
            this.mSnapshot.applyTo(this);
            setWidth(this.mPaddingLeft + width2 + this.mPaddingRight);
            setHeight(this.mPaddingTop + height2 + this.mPaddingBottom);
        } else {
            this.mX = prex;
            this.mY = prey;
        }
        resetSolverVariables(this.mSystem.getCache());
        if (this == getRootConstraintContainer()) {
            updateDrawPosition();
        }
    }

    static int setGroup(ConstraintAnchor anchor, int group) {
        int oldGroup = anchor.mGroup;
        if (anchor.mOwner.getParent() == null) {
            return group;
        }
        if (oldGroup <= group) {
            return oldGroup;
        }
        anchor.mGroup = group;
        ConstraintAnchor opposite = anchor.getOpposite();
        ConstraintAnchor target = anchor.mTarget;
        if (opposite != null) {
            group = setGroup(opposite, group);
        }
        if (target != null) {
            group = setGroup(target, group);
        }
        if (opposite != null) {
            group = setGroup(opposite, group);
        }
        anchor.mGroup = group;
        return group;
    }

    public int layoutFindGroupsSimple() {
        int size = this.mChildren.size();
        for (int j = 0; j < size; j++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(j);
            widget.mLeft.mGroup = 0;
            widget.mRight.mGroup = 0;
            widget.mTop.mGroup = 1;
            widget.mBottom.mGroup = 1;
            widget.mBaseline.mGroup = 1;
        }
        return 2;
    }

    public void findWrapRecursive(ConstraintWidget widget) {
        int w = widget.getWrapWidth();
        int distToRight = w;
        int distToLeft = w;
        ConstraintWidget leftWidget = null;
        ConstraintWidget rightWidget = null;
        widget.mVisited = true;
        if (widget.mRight.isConnected() || widget.mLeft.isConnected()) {
            if (widget.mRight.mTarget != null) {
                rightWidget = widget.mRight.mTarget.getOwner();
                distToRight += widget.mRight.getMargin();
                if (!rightWidget.isRoot() && !rightWidget.mVisited) {
                    findWrapRecursive(rightWidget);
                }
            }
            if (widget.mLeft.isConnected()) {
                leftWidget = widget.mLeft.mTarget.getOwner();
                distToLeft += widget.mLeft.getMargin();
                if (!leftWidget.isRoot() && !leftWidget.mVisited) {
                    findWrapRecursive(leftWidget);
                }
            }
            if (widget.mRight.mTarget != null && !rightWidget.isRoot()) {
                if (widget.mRight.mTarget.mType == Type.RIGHT) {
                    distToRight += rightWidget.mDistToRight - rightWidget.getWrapWidth();
                } else if (widget.mRight.mTarget.getType() == Type.LEFT) {
                    distToRight += rightWidget.mDistToRight;
                }
            }
            if (widget.mLeft.mTarget != null && !leftWidget.isRoot()) {
                if (widget.mLeft.mTarget.getType() == Type.LEFT) {
                    distToLeft += leftWidget.mDistToLeft - leftWidget.getWrapWidth();
                } else if (widget.mLeft.mTarget.getType() == Type.RIGHT) {
                    distToLeft += leftWidget.mDistToLeft;
                }
            }
        } else {
            distToLeft += widget.getX();
        }
        widget.mDistToLeft = distToLeft;
        widget.mDistToRight = distToRight;
        int h = widget.getWrapHeight();
        int distToTop = h;
        int distToBottom = h;
        ConstraintWidget topWidget = null;
        if (widget.mBaseline.mTarget == null && widget.mTop.mTarget == null && widget.mBottom.mTarget == null) {
            distToTop += widget.getY();
        } else if (widget.mBaseline.isConnected()) {
            ConstraintWidget baseLineWidget = widget.mBaseline.mTarget.getOwner();
            if (!baseLineWidget.mVisited) {
                findWrapRecursive(baseLineWidget);
            }
            if (baseLineWidget.mDistToBottom > distToBottom) {
                distToBottom = baseLineWidget.mDistToBottom;
            }
            if (baseLineWidget.mDistToTop > distToTop) {
                distToTop = baseLineWidget.mDistToTop;
            }
            widget.mDistToTop = distToTop;
            widget.mDistToBottom = distToBottom;
            return;
        } else {
            if (widget.mTop.isConnected()) {
                topWidget = widget.mTop.mTarget.getOwner();
                distToTop += widget.mTop.getMargin();
                if (!topWidget.isRoot() && !topWidget.mVisited) {
                    findWrapRecursive(topWidget);
                }
            }
            ConstraintWidget bottomWidget = null;
            if (widget.mBottom.isConnected()) {
                bottomWidget = widget.mBottom.mTarget.getOwner();
                distToBottom += widget.mBottom.getMargin();
                if (!bottomWidget.isRoot() && !bottomWidget.mVisited) {
                    findWrapRecursive(bottomWidget);
                }
            }
            if (widget.mTop.mTarget != null && !topWidget.isRoot()) {
                if (widget.mTop.mTarget.getType() == Type.TOP) {
                    distToTop += topWidget.mDistToTop - topWidget.getWrapHeight();
                } else if (widget.mTop.mTarget.getType() == Type.BOTTOM) {
                    distToTop += topWidget.mDistToTop;
                }
            }
            if (widget.mBottom.mTarget != null && !bottomWidget.isRoot()) {
                if (widget.mBottom.mTarget.getType() == Type.BOTTOM) {
                    distToBottom += bottomWidget.mDistToBottom - bottomWidget.getWrapHeight();
                } else if (widget.mBottom.mTarget.getType() == Type.TOP) {
                    distToBottom += bottomWidget.mDistToBottom;
                }
            }
        }
        widget.mDistToTop = distToTop;
        widget.mDistToBottom = distToBottom;
    }

    public void findWrapSize(ArrayList<ConstraintWidget> children) {
        int maxTopDist = 0;
        int maxLeftDist = 0;
        int maxRightDist = 0;
        int maxBottomDist = 0;
        int maxConnectWidth = 0;
        int maxConnectHeight = 0;
        int size = children.size();
        for (int j = 0; j < size; j++) {
            ConstraintWidget widget = (ConstraintWidget) children.get(j);
            if (!widget.isRoot()) {
                if (!widget.mVisited) {
                    findWrapRecursive(widget);
                }
                int connectWidth = (widget.mDistToLeft + widget.mDistToRight) - widget.getWrapWidth();
                int connectHeight = (widget.mDistToTop + widget.mDistToBottom) - widget.getWrapHeight();
                maxLeftDist = Math.max(maxLeftDist, widget.mDistToLeft);
                maxRightDist = Math.max(maxRightDist, widget.mDistToRight);
                maxBottomDist = Math.max(maxBottomDist, widget.mDistToBottom);
                maxTopDist = Math.max(maxTopDist, widget.mDistToTop);
                maxConnectWidth = Math.max(maxConnectWidth, connectWidth);
                maxConnectHeight = Math.max(maxConnectHeight, connectHeight);
            }
        }
        this.mWrapWidth = Math.max(Math.max(maxLeftDist, maxRightDist), maxConnectWidth);
        this.mWrapHeight = Math.max(Math.max(maxTopDist, maxBottomDist), maxConnectHeight);
        for (int j2 = 0; j2 < size; j2++) {
            ((ConstraintWidget) children.get(j2)).mVisited = false;
        }
    }

    public int layoutFindGroups() {
        int index;
        Type[] dir = {Type.LEFT, Type.RIGHT, Type.TOP, Type.BASELINE, Type.BOTTOM};
        int label = 1;
        int size = this.mChildren.size();
        for (int j = 0; j < size; j++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(j);
            ConstraintAnchor anchor = widget.mLeft;
            if (anchor.mTarget == null) {
                anchor.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor, label) == label) {
                label++;
            }
            ConstraintAnchor anchor2 = widget.mTop;
            if (anchor2.mTarget == null) {
                anchor2.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor2, label) == label) {
                label++;
            }
            ConstraintAnchor anchor3 = widget.mRight;
            if (anchor3.mTarget == null) {
                anchor3.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor3, label) == label) {
                label++;
            }
            ConstraintAnchor anchor4 = widget.mBottom;
            if (anchor4.mTarget == null) {
                anchor4.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor4, label) == label) {
                label++;
            }
            ConstraintAnchor anchor5 = widget.mBaseline;
            if (anchor5.mTarget == null) {
                anchor5.mGroup = Integer.MAX_VALUE;
            } else if (setGroup(anchor5, label) == label) {
                label++;
            }
        }
        boolean notDone = true;
        int count = 0;
        int fix = 0;
        while (notDone) {
            notDone = false;
            count++;
            for (int j2 = 0; j2 < size; j2++) {
                ConstraintWidget widget2 = (ConstraintWidget) this.mChildren.get(j2);
                for (Type type : dir) {
                    ConstraintAnchor anchor6 = null;
                    switch (type) {
                        case LEFT:
                            anchor6 = widget2.mLeft;
                            break;
                        case TOP:
                            anchor6 = widget2.mTop;
                            break;
                        case RIGHT:
                            anchor6 = widget2.mRight;
                            break;
                        case BOTTOM:
                            anchor6 = widget2.mBottom;
                            break;
                        case BASELINE:
                            anchor6 = widget2.mBaseline;
                            break;
                    }
                    ConstraintAnchor target = anchor6.mTarget;
                    if (target != null) {
                        if (!(target.mOwner.getParent() == null || target.mGroup == anchor6.mGroup)) {
                            int i = anchor6.mGroup > target.mGroup ? target.mGroup : anchor6.mGroup;
                            anchor6.mGroup = i;
                            target.mGroup = i;
                            fix++;
                            notDone = true;
                        }
                        ConstraintAnchor opposite = target.getOpposite();
                        if (!(opposite == null || opposite.mGroup == anchor6.mGroup)) {
                            int i2 = anchor6.mGroup > opposite.mGroup ? opposite.mGroup : anchor6.mGroup;
                            anchor6.mGroup = i2;
                            opposite.mGroup = i2;
                            fix++;
                            notDone = true;
                        }
                    }
                }
            }
        }
        int[] table = new int[((this.mChildren.size() * dir.length) + 1)];
        Arrays.fill(table, -1);
        int j3 = 0;
        int index2 = 0;
        while (j3 < size) {
            ConstraintWidget widget3 = (ConstraintWidget) this.mChildren.get(j3);
            ConstraintAnchor anchor7 = widget3.mLeft;
            if (anchor7.mGroup != Integer.MAX_VALUE) {
                int g = anchor7.mGroup;
                if (table[g] == -1) {
                    index = index2 + 1;
                    table[g] = index2;
                } else {
                    index = index2;
                }
                anchor7.mGroup = table[g];
            } else {
                index = index2;
            }
            ConstraintAnchor anchor8 = widget3.mTop;
            if (anchor8.mGroup != Integer.MAX_VALUE) {
                int g2 = anchor8.mGroup;
                if (table[g2] == -1) {
                    int index3 = index + 1;
                    table[g2] = index;
                    index = index3;
                }
                anchor8.mGroup = table[g2];
            }
            ConstraintAnchor anchor9 = widget3.mRight;
            if (anchor9.mGroup != Integer.MAX_VALUE) {
                int g3 = anchor9.mGroup;
                if (table[g3] == -1) {
                    int index4 = index + 1;
                    table[g3] = index;
                    index = index4;
                }
                anchor9.mGroup = table[g3];
            }
            ConstraintAnchor anchor10 = widget3.mBottom;
            if (anchor10.mGroup != Integer.MAX_VALUE) {
                int g4 = anchor10.mGroup;
                if (table[g4] == -1) {
                    int index5 = index + 1;
                    table[g4] = index;
                    index = index5;
                }
                anchor10.mGroup = table[g4];
            }
            ConstraintAnchor anchor11 = widget3.mBaseline;
            if (anchor11.mGroup != Integer.MAX_VALUE) {
                int g5 = anchor11.mGroup;
                if (table[g5] == -1) {
                    int index6 = index + 1;
                    table[g5] = index;
                    index = index6;
                }
                anchor11.mGroup = table[g5];
            }
            j3++;
            index2 = index;
        }
        return index2;
    }

    public void layoutWithGroup(int numOfGroups) {
        int prex = this.mX;
        int prey = this.mY;
        if (this.mParent != null) {
            if (this.mSnapshot == null) {
                this.mSnapshot = new Snapshot(this);
            }
            this.mSnapshot.updateFrom(this);
            this.mX = 0;
            this.mY = 0;
            resetAnchors();
            resetSolverVariables(this.mSystem.getCache());
        } else {
            this.mX = 0;
            this.mY = 0;
        }
        int count = this.mChildren.size();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof WidgetContainer) {
                ((WidgetContainer) widget).layout();
            }
        }
        this.mLeft.mGroup = 0;
        this.mRight.mGroup = 0;
        this.mTop.mGroup = 1;
        this.mBottom.mGroup = 1;
        this.mSystem.reset();
        for (int i2 = 0; i2 < numOfGroups; i2++) {
            try {
                addToSolver(this.mSystem, i2);
                this.mSystem.minimize();
                updateFromSolver(this.mSystem, i2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateFromSolver(this.mSystem, -2);
        }
        if (this.mParent != null) {
            int width = getWidth();
            int height = getHeight();
            this.mSnapshot.applyTo(this);
            setWidth(width);
            setHeight(height);
        } else {
            this.mX = prex;
            this.mY = prey;
        }
        if (this == getRootConstraintContainer()) {
            updateDrawPosition();
        }
    }

    public boolean isAnimating() {
        if (super.isAnimating()) {
            return true;
        }
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            if (((ConstraintWidget) this.mChildren.get(i)).isAnimating()) {
                return true;
            }
        }
        return false;
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        ArrayList<Guideline> guidelines = new ArrayList<>();
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 1) {
                    guidelines.add(guideline);
                }
            }
        }
        return guidelines;
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        ArrayList<Guideline> guidelines = new ArrayList<>();
        int mChildrenSize = this.mChildren.size();
        for (int i = 0; i < mChildrenSize; i++) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 0) {
                    guidelines.add(guideline);
                }
            }
        }
        return guidelines;
    }

    public LinearSystem getSystem() {
        return this.mSystem;
    }

    private void resetChains() {
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
    }

    /* access modifiers changed from: 0000 */
    public void addChain(ConstraintWidget constraintWidget, int type) {
        ConstraintWidget widget = constraintWidget;
        if (type == 0) {
            while (widget.mLeft.mTarget != null && widget.mLeft.mTarget.mOwner.mRight.mTarget != null && widget.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == widget) {
                widget = widget.mLeft.mTarget.mOwner;
            }
            addHorizontalChain(widget);
        } else if (type == 1) {
            while (widget.mTop.mTarget != null && widget.mTop.mTarget.mOwner.mBottom.mTarget != null && widget.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == widget) {
                widget = widget.mTop.mTarget.mOwner;
            }
            addVerticalChain(widget);
        }
    }

    private void addHorizontalChain(ConstraintWidget widget) {
        int i = 0;
        while (i < this.mHorizontalChainsSize) {
            if (this.mHorizontalChainsArray[i] != widget) {
                i++;
            } else {
                return;
            }
        }
        if (this.mHorizontalChainsSize + 1 >= this.mHorizontalChainsArray.length) {
            this.mHorizontalChainsArray = (ConstraintWidget[]) Arrays.copyOf(this.mHorizontalChainsArray, this.mHorizontalChainsArray.length * 2);
        }
        this.mHorizontalChainsArray[this.mHorizontalChainsSize] = widget;
        this.mHorizontalChainsSize++;
    }

    private void addVerticalChain(ConstraintWidget widget) {
        int i = 0;
        while (i < this.mVerticalChainsSize) {
            if (this.mVerticalChainsArray[i] != widget) {
                i++;
            } else {
                return;
            }
        }
        if (this.mVerticalChainsSize + 1 >= this.mVerticalChainsArray.length) {
            this.mVerticalChainsArray = (ConstraintWidget[]) Arrays.copyOf(this.mVerticalChainsArray, this.mVerticalChainsArray.length * 2);
        }
        this.mVerticalChainsArray[this.mVerticalChainsSize] = widget;
        this.mVerticalChainsSize++;
    }

    private int countMatchConstraintsChainedWidgets(ConstraintWidget widget, int direction) {
        int count = 0;
        if (direction == 0) {
            boolean fixedPosition = true;
            ConstraintWidget first = widget;
            if (!(widget.mLeft.mTarget == null || widget.mLeft.mTarget.mOwner == this)) {
                fixedPosition = false;
            }
            while (widget.mRight.mTarget != null) {
                if (widget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (count + 1 >= this.mMatchConstraintsChainedWidgets.length) {
                        this.mMatchConstraintsChainedWidgets = (ConstraintWidget[]) Arrays.copyOf(this.mMatchConstraintsChainedWidgets, this.mMatchConstraintsChainedWidgets.length * 2);
                    }
                    int count2 = count + 1;
                    this.mMatchConstraintsChainedWidgets[count] = widget;
                    count = count2;
                }
                if (widget.mRight.mTarget.mOwner.mLeft.mTarget == null || widget.mRight.mTarget.mOwner.mLeft.mTarget.mOwner != widget) {
                    break;
                }
                widget = widget.mRight.mTarget.mOwner;
            }
            if (!(widget.mRight.mTarget == null || widget.mRight.mTarget.mOwner == this)) {
                fixedPosition = false;
            }
            first.mHorizontalChainFixedPosition = fixedPosition;
        } else {
            boolean fixedPosition2 = true;
            ConstraintWidget first2 = widget;
            if (!(widget.mTop.mTarget == null || widget.mTop.mTarget.mOwner == this)) {
                fixedPosition2 = false;
            }
            while (widget.mBottom.mTarget != null) {
                if (widget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (count + 1 >= this.mMatchConstraintsChainedWidgets.length) {
                        this.mMatchConstraintsChainedWidgets = (ConstraintWidget[]) Arrays.copyOf(this.mMatchConstraintsChainedWidgets, this.mMatchConstraintsChainedWidgets.length * 2);
                    }
                    int count3 = count + 1;
                    this.mMatchConstraintsChainedWidgets[count] = widget;
                    count = count3;
                }
                if (widget.mBottom.mTarget.mOwner.mTop.mTarget == null || widget.mBottom.mTarget.mOwner.mTop.mTarget.mOwner != widget) {
                    break;
                }
                widget = widget.mBottom.mTarget.mOwner;
            }
            if (!(widget.mBottom.mTarget == null || widget.mBottom.mTarget.mOwner == this)) {
                fixedPosition2 = false;
            }
            first2.mVerticalChainFixedPosition = fixedPosition2;
        }
        return count;
    }
}
