package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;

public class ChainHead {
    private int a;
    private boolean b = false;
    private boolean c;
    protected ConstraintWidget mFirst;
    protected ConstraintWidget mFirstMatchConstraintWidget;
    protected ConstraintWidget mFirstVisibleWidget;
    protected boolean mHasComplexMatchWeights;
    protected boolean mHasDefinedWeights;
    protected boolean mHasUndefinedWeights;
    protected ConstraintWidget mHead;
    protected ConstraintWidget mLast;
    protected ConstraintWidget mLastMatchConstraintWidget;
    protected ConstraintWidget mLastVisibleWidget;
    protected float mTotalWeight = BitmapDescriptorFactory.HUE_RED;
    protected ArrayList<ConstraintWidget> mWeightedMatchConstraintsWidgets;
    protected int mWidgetsCount;
    protected int mWidgetsMatchCount;

    public ChainHead(ConstraintWidget constraintWidget, int i, boolean z) {
        this.mFirst = constraintWidget;
        this.a = i;
        this.b = z;
    }

    private static boolean a(ConstraintWidget constraintWidget, int i) {
        return constraintWidget.getVisibility() != 8 && constraintWidget.mListDimensionBehaviors[i] == DimensionBehaviour.MATCH_CONSTRAINT && (constraintWidget.e[i] == 0 || constraintWidget.e[i] == 3);
    }

    private void a() {
        int i = this.a * 2;
        ConstraintWidget constraintWidget = this.mFirst;
        ConstraintWidget constraintWidget2 = this.mFirst;
        boolean z = false;
        ConstraintWidget constraintWidget3 = constraintWidget;
        boolean z2 = false;
        while (!z2) {
            this.mWidgetsCount++;
            ConstraintWidget constraintWidget4 = null;
            constraintWidget3.mListNextVisibleWidget[this.a] = null;
            constraintWidget3.mListNextMatchConstraintsWidget[this.a] = null;
            if (constraintWidget3.getVisibility() != 8) {
                if (this.mFirstVisibleWidget == null) {
                    this.mFirstVisibleWidget = constraintWidget3;
                }
                if (this.mLastVisibleWidget != null) {
                    this.mLastVisibleWidget.mListNextVisibleWidget[this.a] = constraintWidget3;
                }
                this.mLastVisibleWidget = constraintWidget3;
                if (constraintWidget3.mListDimensionBehaviors[this.a] == DimensionBehaviour.MATCH_CONSTRAINT && (constraintWidget3.e[this.a] == 0 || constraintWidget3.e[this.a] == 3 || constraintWidget3.e[this.a] == 2)) {
                    this.mWidgetsMatchCount++;
                    float f = constraintWidget3.J[this.a];
                    if (f > BitmapDescriptorFactory.HUE_RED) {
                        this.mTotalWeight += constraintWidget3.J[this.a];
                    }
                    if (a(constraintWidget3, this.a)) {
                        if (f < BitmapDescriptorFactory.HUE_RED) {
                            this.mHasUndefinedWeights = true;
                        } else {
                            this.mHasDefinedWeights = true;
                        }
                        if (this.mWeightedMatchConstraintsWidgets == null) {
                            this.mWeightedMatchConstraintsWidgets = new ArrayList<>();
                        }
                        this.mWeightedMatchConstraintsWidgets.add(constraintWidget3);
                    }
                    if (this.mFirstMatchConstraintWidget == null) {
                        this.mFirstMatchConstraintWidget = constraintWidget3;
                    }
                    if (this.mLastMatchConstraintWidget != null) {
                        this.mLastMatchConstraintWidget.mListNextMatchConstraintsWidget[this.a] = constraintWidget3;
                    }
                    this.mLastMatchConstraintWidget = constraintWidget3;
                }
            }
            ConstraintAnchor constraintAnchor = constraintWidget3.mListAnchors[i + 1].c;
            if (constraintAnchor != null) {
                ConstraintWidget constraintWidget5 = constraintAnchor.a;
                if (constraintWidget5.mListAnchors[i].c != null && constraintWidget5.mListAnchors[i].c.a == constraintWidget3) {
                    constraintWidget4 = constraintWidget5;
                }
            }
            if (constraintWidget4 != null) {
                constraintWidget3 = constraintWidget4;
            } else {
                z2 = true;
            }
        }
        this.mLast = constraintWidget3;
        if (this.a != 0 || !this.b) {
            this.mHead = this.mFirst;
        } else {
            this.mHead = this.mLast;
        }
        if (this.mHasDefinedWeights && this.mHasUndefinedWeights) {
            z = true;
        }
        this.mHasComplexMatchWeights = z;
    }

    public ConstraintWidget getFirst() {
        return this.mFirst;
    }

    public ConstraintWidget getFirstVisibleWidget() {
        return this.mFirstVisibleWidget;
    }

    public ConstraintWidget getLast() {
        return this.mLast;
    }

    public ConstraintWidget getLastVisibleWidget() {
        return this.mLastVisibleWidget;
    }

    public ConstraintWidget getHead() {
        return this.mHead;
    }

    public ConstraintWidget getFirstMatchConstraintWidget() {
        return this.mFirstMatchConstraintWidget;
    }

    public ConstraintWidget getLastMatchConstraintWidget() {
        return this.mLastMatchConstraintWidget;
    }

    public float getTotalWeight() {
        return this.mTotalWeight;
    }

    public void define() {
        if (!this.c) {
            a();
        }
        this.c = true;
    }
}
