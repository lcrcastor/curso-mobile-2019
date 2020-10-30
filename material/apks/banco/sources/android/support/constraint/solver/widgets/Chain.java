package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;

class Chain {
    Chain() {
    }

    static void a(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i) {
        int i2;
        ChainHead[] chainHeadArr;
        int i3;
        if (i == 0) {
            int i4 = constraintWidgetContainer.Q;
            chainHeadArr = constraintWidgetContainer.T;
            i2 = i4;
            i3 = 0;
        } else {
            i3 = 2;
            int i5 = constraintWidgetContainer.R;
            i2 = i5;
            chainHeadArr = constraintWidgetContainer.S;
        }
        for (int i6 = 0; i6 < i2; i6++) {
            ChainHead chainHead = chainHeadArr[i6];
            chainHead.define();
            if (!constraintWidgetContainer.optimizeFor(4)) {
                a(constraintWidgetContainer, linearSystem, i, i3, chainHead);
            } else if (!Optimizer.a(constraintWidgetContainer, linearSystem, i, i3, chainHead)) {
                a(constraintWidgetContainer, linearSystem, i, i3, chainHead);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (r2.F == 2) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        r5 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0053, code lost:
        if (r2.G == 2) goto L_0x0037;
     */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x03b3  */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x0479  */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x04d2  */
    /* JADX WARNING: Removed duplicated region for block: B:253:0x04d7  */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x04dd  */
    /* JADX WARNING: Removed duplicated region for block: B:257:0x04e2  */
    /* JADX WARNING: Removed duplicated region for block: B:259:0x04e6  */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x04f8  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0164  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0182  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(android.support.constraint.solver.widgets.ConstraintWidgetContainer r47, android.support.constraint.solver.LinearSystem r48, int r49, int r50, android.support.constraint.solver.widgets.ChainHead r51) {
        /*
            r0 = r47
            r9 = r48
            r1 = r51
            android.support.constraint.solver.widgets.ConstraintWidget r11 = r1.mFirst
            android.support.constraint.solver.widgets.ConstraintWidget r12 = r1.mLast
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r1.mFirstVisibleWidget
            android.support.constraint.solver.widgets.ConstraintWidget r14 = r1.mLastVisibleWidget
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r1.mHead
            float r3 = r1.mTotalWeight
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r1.mFirstMatchConstraintWidget
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r1.mLastMatchConstraintWidget
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r49]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r7 = 1
            if (r4 != r5) goto L_0x0021
            r4 = 1
            goto L_0x0022
        L_0x0021:
            r4 = 0
        L_0x0022:
            r5 = 2
            if (r49 != 0) goto L_0x0042
            int r8 = r2.F
            if (r8 != 0) goto L_0x002b
            r8 = 1
            goto L_0x002c
        L_0x002b:
            r8 = 0
        L_0x002c:
            int r6 = r2.F
            if (r6 != r7) goto L_0x0032
            r6 = 1
            goto L_0x0033
        L_0x0032:
            r6 = 0
        L_0x0033:
            int r7 = r2.F
            if (r7 != r5) goto L_0x0039
        L_0x0037:
            r5 = 1
            goto L_0x003a
        L_0x0039:
            r5 = 0
        L_0x003a:
            r7 = r5
            r18 = r6
            r17 = r8
            r6 = r11
            r5 = 0
            goto L_0x0056
        L_0x0042:
            int r6 = r2.G
            if (r6 != 0) goto L_0x0048
            r8 = 1
            goto L_0x0049
        L_0x0048:
            r8 = 0
        L_0x0049:
            int r6 = r2.G
            r7 = 1
            if (r6 != r7) goto L_0x0050
            r6 = 1
            goto L_0x0051
        L_0x0050:
            r6 = 0
        L_0x0051:
            int r7 = r2.G
            if (r7 != r5) goto L_0x0039
            goto L_0x0037
        L_0x0056:
            r21 = 0
            if (r5 != 0) goto L_0x0135
            android.support.constraint.solver.widgets.ConstraintAnchor[] r8 = r6.mListAnchors
            r8 = r8[r50]
            if (r4 != 0) goto L_0x0066
            if (r7 == 0) goto L_0x0063
            goto L_0x0066
        L_0x0063:
            r23 = 4
            goto L_0x0068
        L_0x0066:
            r23 = 1
        L_0x0068:
            int r24 = r8.getMargin()
            r25 = r3
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r8.c
            if (r3 == 0) goto L_0x007c
            if (r6 == r11) goto L_0x007c
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r8.c
            int r3 = r3.getMargin()
            int r24 = r24 + r3
        L_0x007c:
            r3 = r24
            if (r7 == 0) goto L_0x008a
            if (r6 == r11) goto L_0x008a
            if (r6 == r13) goto L_0x008a
            r27 = r2
            r26 = r5
            r5 = 6
            goto L_0x009a
        L_0x008a:
            if (r17 == 0) goto L_0x0094
            if (r4 == 0) goto L_0x0094
            r27 = r2
            r26 = r5
            r5 = 4
            goto L_0x009a
        L_0x0094:
            r27 = r2
            r26 = r5
            r5 = r23
        L_0x009a:
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r8.c
            if (r2 == 0) goto L_0x00c7
            if (r6 != r13) goto L_0x00af
            android.support.constraint.solver.SolverVariable r2 = r8.e
            r28 = r11
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r8.c
            android.support.constraint.solver.SolverVariable r11 = r11.e
            r29 = r7
            r7 = 5
            r9.addGreaterThan(r2, r11, r3, r7)
            goto L_0x00bd
        L_0x00af:
            r29 = r7
            r28 = r11
            android.support.constraint.solver.SolverVariable r2 = r8.e
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r8.c
            android.support.constraint.solver.SolverVariable r7 = r7.e
            r11 = 6
            r9.addGreaterThan(r2, r7, r3, r11)
        L_0x00bd:
            android.support.constraint.solver.SolverVariable r2 = r8.e
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r8.c
            android.support.constraint.solver.SolverVariable r7 = r7.e
            r9.addEquality(r2, r7, r3, r5)
            goto L_0x00cb
        L_0x00c7:
            r29 = r7
            r28 = r11
        L_0x00cb:
            if (r4 == 0) goto L_0x0102
            int r2 = r6.getVisibility()
            r3 = 8
            if (r2 == r3) goto L_0x00f1
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r6.mListDimensionBehaviors
            r2 = r2[r49]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r3) goto L_0x00f1
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r6.mListAnchors
            int r3 = r50 + 1
            r2 = r2[r3]
            android.support.constraint.solver.SolverVariable r2 = r2.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r6.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.SolverVariable r3 = r3.e
            r5 = 0
            r7 = 5
            r9.addGreaterThan(r2, r3, r5, r7)
            goto L_0x00f2
        L_0x00f1:
            r5 = 0
        L_0x00f2:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r6.mListAnchors
            r2 = r2[r50]
            android.support.constraint.solver.SolverVariable r2 = r2.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r0.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.SolverVariable r3 = r3.e
            r7 = 6
            r9.addGreaterThan(r2, r3, r5, r7)
        L_0x0102:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r6.mListAnchors
            int r3 = r50 + 1
            r2 = r2[r3]
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r2.c
            if (r2 == 0) goto L_0x0123
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r2.a
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r2.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            if (r3 == 0) goto L_0x0123
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r2.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r3.a
            if (r3 == r6) goto L_0x0121
            goto L_0x0123
        L_0x0121:
            r21 = r2
        L_0x0123:
            if (r21 == 0) goto L_0x012a
            r6 = r21
            r5 = r26
            goto L_0x012b
        L_0x012a:
            r5 = 1
        L_0x012b:
            r3 = r25
            r2 = r27
            r11 = r28
            r7 = r29
            goto L_0x0056
        L_0x0135:
            r27 = r2
            r25 = r3
            r29 = r7
            r28 = r11
            if (r14 == 0) goto L_0x0161
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r12.mListAnchors
            int r3 = r50 + 1
            r2 = r2[r3]
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r2.c
            if (r2 == 0) goto L_0x0161
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            r2 = r2[r3]
            android.support.constraint.solver.SolverVariable r5 = r2.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r12.mListAnchors
            r3 = r6[r3]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.SolverVariable r3 = r3.e
            int r2 = r2.getMargin()
            int r2 = -r2
            r8 = 5
            r9.addLowerThan(r5, r3, r2, r8)
            goto L_0x0162
        L_0x0161:
            r8 = 5
        L_0x0162:
            if (r4 == 0) goto L_0x017e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            int r2 = r50 + 1
            r0 = r0[r2]
            android.support.constraint.solver.SolverVariable r0 = r0.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r12.mListAnchors
            r3 = r3[r2]
            android.support.constraint.solver.SolverVariable r3 = r3.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r4 = r12.mListAnchors
            r2 = r4[r2]
            int r2 = r2.getMargin()
            r4 = 6
            r9.addGreaterThan(r0, r3, r2, r4)
        L_0x017e:
            java.util.ArrayList<android.support.constraint.solver.widgets.ConstraintWidget> r0 = r1.mWeightedMatchConstraintsWidgets
            if (r0 == 0) goto L_0x022e
            int r2 = r0.size()
            r7 = 1
            if (r2 <= r7) goto L_0x022e
            boolean r3 = r1.mHasUndefinedWeights
            if (r3 == 0) goto L_0x0196
            boolean r3 = r1.mHasComplexMatchWeights
            if (r3 != 0) goto L_0x0196
            int r3 = r1.mWidgetsMatchCount
            float r3 = (float) r3
            r25 = r3
        L_0x0196:
            r3 = 0
            r5 = r21
            r4 = 0
            r31 = 0
        L_0x019c:
            if (r4 >= r2) goto L_0x022e
            java.lang.Object r6 = r0.get(r4)
            android.support.constraint.solver.widgets.ConstraintWidget r6 = (android.support.constraint.solver.widgets.ConstraintWidget) r6
            float[] r11 = r6.J
            r11 = r11[r49]
            int r16 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r16 >= 0) goto L_0x01cc
            boolean r11 = r1.mHasComplexMatchWeights
            if (r11 == 0) goto L_0x01c6
            android.support.constraint.solver.widgets.ConstraintAnchor[] r11 = r6.mListAnchors
            int r16 = r50 + 1
            r11 = r11[r16]
            android.support.constraint.solver.SolverVariable r11 = r11.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r6.mListAnchors
            r6 = r6[r50]
            android.support.constraint.solver.SolverVariable r6 = r6.e
            r7 = 0
            r8 = 4
            r9.addEquality(r11, r6, r7, r8)
            r8 = 6
            r11 = 0
            goto L_0x01e4
        L_0x01c6:
            r8 = 4
            r7 = 1065353216(0x3f800000, float:1.0)
            r11 = 1065353216(0x3f800000, float:1.0)
            goto L_0x01cd
        L_0x01cc:
            r8 = 4
        L_0x01cd:
            int r7 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r7 != 0) goto L_0x01e7
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r6.mListAnchors
            int r11 = r50 + 1
            r7 = r7[r11]
            android.support.constraint.solver.SolverVariable r7 = r7.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r6.mListAnchors
            r6 = r6[r50]
            android.support.constraint.solver.SolverVariable r6 = r6.e
            r8 = 6
            r11 = 0
            r9.addEquality(r7, r6, r11, r8)
        L_0x01e4:
            r39 = r0
            goto L_0x0225
        L_0x01e7:
            r7 = 0
            r8 = 6
            if (r5 == 0) goto L_0x0220
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r5.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.SolverVariable r3 = r3.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r5 = r5.mListAnchors
            int r15 = r50 + 1
            r5 = r5[r15]
            android.support.constraint.solver.SolverVariable r5 = r5.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r6.mListAnchors
            r7 = r7[r50]
            android.support.constraint.solver.SolverVariable r7 = r7.e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r8 = r6.mListAnchors
            r8 = r8[r15]
            android.support.constraint.solver.SolverVariable r8 = r8.e
            r39 = r0
            android.support.constraint.solver.ArrayRow r0 = r48.createRow()
            r30 = r0
            r32 = r25
            r33 = r11
            r34 = r3
            r35 = r5
            r36 = r7
            r37 = r8
            r30.createRowEqualMatchDimensions(r31, r32, r33, r34, r35, r36, r37)
            r9.addConstraint(r0)
            goto L_0x0222
        L_0x0220:
            r39 = r0
        L_0x0222:
            r5 = r6
            r31 = r11
        L_0x0225:
            int r4 = r4 + 1
            r0 = r39
            r3 = 0
            r7 = 1
            r8 = 5
            goto L_0x019c
        L_0x022e:
            if (r13 == 0) goto L_0x029a
            if (r13 == r14) goto L_0x0234
            if (r29 == 0) goto L_0x029a
        L_0x0234:
            r11 = r28
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r11.mListAnchors
            r0 = r0[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            int r2 = r50 + 1
            r1 = r1[r2]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r11.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            if (r3 == 0) goto L_0x0251
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r11.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.SolverVariable r3 = r3.e
            goto L_0x0253
        L_0x0251:
            r3 = r21
        L_0x0253:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r4 = r12.mListAnchors
            r4 = r4[r2]
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            if (r4 == 0) goto L_0x0265
            android.support.constraint.solver.widgets.ConstraintAnchor[] r4 = r12.mListAnchors
            r4 = r4[r2]
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.SolverVariable r4 = r4.e
            r5 = r4
            goto L_0x0267
        L_0x0265:
            r5 = r21
        L_0x0267:
            if (r13 != r14) goto L_0x0271
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r13.mListAnchors
            r0 = r0[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            r1 = r1[r2]
        L_0x0271:
            if (r3 == 0) goto L_0x04be
            if (r5 == 0) goto L_0x04be
            if (r49 != 0) goto L_0x027d
            r2 = r27
            float r2 = r2.B
        L_0x027b:
            r4 = r2
            goto L_0x0282
        L_0x027d:
            r2 = r27
            float r2 = r2.C
            goto L_0x027b
        L_0x0282:
            int r6 = r0.getMargin()
            int r7 = r1.getMargin()
            android.support.constraint.solver.SolverVariable r2 = r0.e
            android.support.constraint.solver.SolverVariable r8 = r1.e
            r10 = 5
            r0 = r9
            r1 = r2
            r2 = r3
            r3 = r6
            r6 = r8
            r8 = r10
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x04be
        L_0x029a:
            r11 = r28
            if (r17 == 0) goto L_0x0398
            if (r13 == 0) goto L_0x0398
            int r0 = r1.mWidgetsMatchCount
            if (r0 <= 0) goto L_0x02ad
            int r0 = r1.mWidgetsCount
            int r1 = r1.mWidgetsMatchCount
            if (r0 != r1) goto L_0x02ad
            r38 = 1
            goto L_0x02af
        L_0x02ad:
            r38 = 0
        L_0x02af:
            r0 = r13
            r8 = r0
        L_0x02b1:
            if (r8 == 0) goto L_0x04be
            android.support.constraint.solver.widgets.ConstraintWidget[] r1 = r8.mListNextVisibleWidget
            r7 = r1[r49]
            if (r7 != 0) goto L_0x02c5
            if (r8 != r14) goto L_0x02bc
            goto L_0x02c5
        L_0x02bc:
            r15 = r7
            r16 = r8
        L_0x02bf:
            r20 = 6
            r22 = 4
            goto L_0x0393
        L_0x02c5:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            r1 = r1[r50]
            android.support.constraint.solver.SolverVariable r2 = r1.e
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r1.c
            if (r3 == 0) goto L_0x02d4
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r1.c
            android.support.constraint.solver.SolverVariable r3 = r3.e
            goto L_0x02d6
        L_0x02d4:
            r3 = r21
        L_0x02d6:
            if (r0 == r8) goto L_0x02e1
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r0.mListAnchors
            int r4 = r50 + 1
            r3 = r3[r4]
            android.support.constraint.solver.SolverVariable r3 = r3.e
            goto L_0x02f8
        L_0x02e1:
            if (r8 != r13) goto L_0x02f8
            if (r0 != r8) goto L_0x02f8
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r11.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            if (r3 == 0) goto L_0x02f6
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r11.mListAnchors
            r3 = r3[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.c
            android.support.constraint.solver.SolverVariable r3 = r3.e
            goto L_0x02f8
        L_0x02f6:
            r3 = r21
        L_0x02f8:
            int r1 = r1.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor[] r4 = r8.mListAnchors
            int r5 = r50 + 1
            r4 = r4[r5]
            int r4 = r4.getMargin()
            if (r7 == 0) goto L_0x031c
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r7.mListAnchors
            r6 = r6[r50]
            r40 = r7
            android.support.constraint.solver.SolverVariable r7 = r6.e
            r41 = r6
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r8.mListAnchors
            r6 = r6[r5]
            android.support.constraint.solver.SolverVariable r6 = r6.e
            r15 = r6
            r6 = r41
            goto L_0x0338
        L_0x031c:
            r40 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r12.mListAnchors
            r6 = r6[r5]
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.c
            if (r6 == 0) goto L_0x032b
            android.support.constraint.solver.SolverVariable r7 = r6.e
            r42 = r6
            goto L_0x032f
        L_0x032b:
            r42 = r6
            r7 = r21
        L_0x032f:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r8.mListAnchors
            r6 = r6[r5]
            android.support.constraint.solver.SolverVariable r6 = r6.e
            r15 = r6
            r6 = r42
        L_0x0338:
            if (r6 == 0) goto L_0x033f
            int r6 = r6.getMargin()
            int r4 = r4 + r6
        L_0x033f:
            if (r0 == 0) goto L_0x034a
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            r0 = r0[r5]
            int r0 = r0.getMargin()
            int r1 = r1 + r0
        L_0x034a:
            if (r2 == 0) goto L_0x038d
            if (r3 == 0) goto L_0x038d
            if (r7 == 0) goto L_0x038d
            if (r15 == 0) goto L_0x038d
            if (r8 != r13) goto L_0x035e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r13.mListAnchors
            r0 = r0[r50]
            int r0 = r0.getMargin()
            r6 = r0
            goto L_0x035f
        L_0x035e:
            r6 = r1
        L_0x035f:
            if (r8 != r14) goto L_0x036c
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r5]
            int r0 = r0.getMargin()
            r16 = r0
            goto L_0x036e
        L_0x036c:
            r16 = r4
        L_0x036e:
            if (r38 == 0) goto L_0x0373
            r19 = 6
            goto L_0x0375
        L_0x0373:
            r19 = 4
        L_0x0375:
            r4 = 1056964608(0x3f000000, float:0.5)
            r0 = r9
            r1 = r2
            r2 = r3
            r3 = r6
            r5 = r7
            r6 = r15
            r15 = r40
            r7 = r16
            r16 = r8
            r20 = 6
            r22 = 4
            r8 = r19
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x0393
        L_0x038d:
            r16 = r8
            r15 = r40
            goto L_0x02bf
        L_0x0393:
            r8 = r15
            r0 = r16
            goto L_0x02b1
        L_0x0398:
            r20 = 6
            r22 = 4
            if (r18 == 0) goto L_0x04be
            if (r13 == 0) goto L_0x04be
            int r0 = r1.mWidgetsMatchCount
            if (r0 <= 0) goto L_0x03ad
            int r0 = r1.mWidgetsCount
            int r1 = r1.mWidgetsMatchCount
            if (r0 != r1) goto L_0x03ad
            r38 = 1
            goto L_0x03af
        L_0x03ad:
            r38 = 0
        L_0x03af:
            r0 = r13
            r8 = r0
        L_0x03b1:
            if (r8 == 0) goto L_0x0460
            android.support.constraint.solver.widgets.ConstraintWidget[] r1 = r8.mListNextVisibleWidget
            r1 = r1[r49]
            if (r8 == r13) goto L_0x045a
            if (r8 == r14) goto L_0x045a
            if (r1 == 0) goto L_0x045a
            if (r1 != r14) goto L_0x03c2
            r7 = r21
            goto L_0x03c3
        L_0x03c2:
            r7 = r1
        L_0x03c3:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            r1 = r1[r50]
            android.support.constraint.solver.SolverVariable r2 = r1.e
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r1.c
            if (r3 == 0) goto L_0x03d1
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r1.c
            android.support.constraint.solver.SolverVariable r3 = r3.e
        L_0x03d1:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r0.mListAnchors
            int r4 = r50 + 1
            r3 = r3[r4]
            android.support.constraint.solver.SolverVariable r3 = r3.e
            int r1 = r1.getMargin()
            android.support.constraint.solver.widgets.ConstraintAnchor[] r5 = r8.mListAnchors
            r5 = r5[r4]
            int r5 = r5.getMargin()
            if (r7 == 0) goto L_0x03fd
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r7.mListAnchors
            r6 = r6[r50]
            r43 = r7
            android.support.constraint.solver.SolverVariable r7 = r6.e
            r44 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r6.c
            if (r7 == 0) goto L_0x03fa
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r6.c
            android.support.constraint.solver.SolverVariable r7 = r7.e
            goto L_0x041b
        L_0x03fa:
            r7 = r21
            goto L_0x041b
        L_0x03fd:
            r43 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r8.mListAnchors
            r6 = r6[r4]
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.c
            if (r6 == 0) goto L_0x040c
            android.support.constraint.solver.SolverVariable r7 = r6.e
            r45 = r6
            goto L_0x0410
        L_0x040c:
            r45 = r6
            r7 = r21
        L_0x0410:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r8.mListAnchors
            r6 = r6[r4]
            android.support.constraint.solver.SolverVariable r6 = r6.e
            r44 = r7
            r7 = r6
            r6 = r45
        L_0x041b:
            if (r6 == 0) goto L_0x0422
            int r6 = r6.getMargin()
            int r5 = r5 + r6
        L_0x0422:
            r15 = r5
            if (r0 == 0) goto L_0x042e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            r0 = r0[r4]
            int r0 = r0.getMargin()
            int r1 = r1 + r0
        L_0x042e:
            r4 = r1
            if (r38 == 0) goto L_0x0434
            r16 = 6
            goto L_0x0436
        L_0x0434:
            r16 = 4
        L_0x0436:
            if (r2 == 0) goto L_0x0453
            if (r3 == 0) goto L_0x0453
            if (r44 == 0) goto L_0x0453
            if (r7 == 0) goto L_0x0453
            r5 = 1056964608(0x3f000000, float:0.5)
            r0 = r9
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r44
            r6 = r7
            r19 = r43
            r7 = r15
            r15 = r8
            r10 = 5
            r8 = r16
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x0457
        L_0x0453:
            r15 = r8
            r19 = r43
            r10 = 5
        L_0x0457:
            r8 = r19
            goto L_0x045d
        L_0x045a:
            r15 = r8
            r10 = 5
            r8 = r1
        L_0x045d:
            r0 = r15
            goto L_0x03b1
        L_0x0460:
            r10 = 5
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r13.mListAnchors
            r0 = r0[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r11.mListAnchors
            r1 = r1[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r1.c
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            int r3 = r50 + 1
            r11 = r2[r3]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r12.mListAnchors
            r2 = r2[r3]
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.c
            if (r1 == 0) goto L_0x04ac
            if (r13 == r14) goto L_0x0487
            android.support.constraint.solver.SolverVariable r2 = r0.e
            android.support.constraint.solver.SolverVariable r1 = r1.e
            int r0 = r0.getMargin()
            r9.addEquality(r2, r1, r0, r10)
            goto L_0x04ac
        L_0x0487:
            if (r8 == 0) goto L_0x04ac
            android.support.constraint.solver.SolverVariable r2 = r0.e
            android.support.constraint.solver.SolverVariable r3 = r1.e
            int r4 = r0.getMargin()
            r5 = 1056964608(0x3f000000, float:0.5)
            android.support.constraint.solver.SolverVariable r6 = r11.e
            android.support.constraint.solver.SolverVariable r7 = r8.e
            int r15 = r11.getMargin()
            r16 = 5
            r0 = r9
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r15
            r10 = r8
            r8 = r16
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x04ad
        L_0x04ac:
            r10 = r8
        L_0x04ad:
            if (r10 == 0) goto L_0x04be
            if (r13 == r14) goto L_0x04be
            android.support.constraint.solver.SolverVariable r0 = r11.e
            android.support.constraint.solver.SolverVariable r1 = r10.e
            int r2 = r11.getMargin()
            int r2 = -r2
            r3 = 5
            r9.addEquality(r0, r1, r2, r3)
        L_0x04be:
            if (r17 != 0) goto L_0x04c2
            if (r18 == 0) goto L_0x0524
        L_0x04c2:
            if (r13 == 0) goto L_0x0524
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r13.mListAnchors
            r0 = r0[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r14.mListAnchors
            int r2 = r50 + 1
            r1 = r1[r2]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r0.c
            if (r3 == 0) goto L_0x04d7
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r0.c
            android.support.constraint.solver.SolverVariable r3 = r3.e
            goto L_0x04d9
        L_0x04d7:
            r3 = r21
        L_0x04d9:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.c
            if (r4 == 0) goto L_0x04e2
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.c
            android.support.constraint.solver.SolverVariable r4 = r4.e
            goto L_0x04e4
        L_0x04e2:
            r4 = r21
        L_0x04e4:
            if (r12 == r14) goto L_0x04f5
            android.support.constraint.solver.widgets.ConstraintAnchor[] r4 = r12.mListAnchors
            r4 = r4[r2]
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r4.c
            if (r5 == 0) goto L_0x04f3
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.c
            android.support.constraint.solver.SolverVariable r4 = r4.e
            goto L_0x04f5
        L_0x04f3:
            r4 = r21
        L_0x04f5:
            r5 = r4
            if (r13 != r14) goto L_0x0500
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r13.mListAnchors
            r0 = r0[r50]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            r1 = r1[r2]
        L_0x0500:
            if (r3 == 0) goto L_0x0524
            if (r5 == 0) goto L_0x0524
            r4 = 1056964608(0x3f000000, float:0.5)
            int r6 = r0.getMargin()
            if (r14 != 0) goto L_0x050d
            goto L_0x050e
        L_0x050d:
            r12 = r14
        L_0x050e:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r12.mListAnchors
            r2 = r7[r2]
            int r7 = r2.getMargin()
            android.support.constraint.solver.SolverVariable r2 = r0.e
            android.support.constraint.solver.SolverVariable r8 = r1.e
            r10 = 5
            r0 = r9
            r1 = r2
            r2 = r3
            r3 = r6
            r6 = r8
            r8 = r10
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x0524:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.Chain.a(android.support.constraint.solver.widgets.ConstraintWidgetContainer, android.support.constraint.solver.LinearSystem, int, int, android.support.constraint.solver.widgets.ChainHead):void");
    }
}
