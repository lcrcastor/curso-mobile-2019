package android.support.constraint;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Guideline;
import android.support.media.ExifInterface;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout extends ViewGroup {
    public static final int DESIGN_INFO_ID = 0;
    public static final String VERSION = "ConstraintLayout-1.1.2";
    SparseArray<View> a = new SparseArray<>();
    ConstraintWidgetContainer b = new ConstraintWidgetContainer();
    int c = -1;
    int d = -1;
    int e = 0;
    int f = 0;
    private ArrayList<ConstraintHelper> g = new ArrayList<>(4);
    private final ArrayList<ConstraintWidget> h = new ArrayList<>(100);
    private int i = 0;
    private int j = 0;
    private int k = SubsamplingScaleImageView.TILE_SIZE_AUTO;
    private int l = SubsamplingScaleImageView.TILE_SIZE_AUTO;
    private boolean m = true;
    private int n = 3;
    private ConstraintSet o = null;
    private int p = -1;
    private HashMap<String, Integer> q = new HashMap<>();
    private int r = -1;
    private int s = -1;
    private Metrics t;

    public static class LayoutParams extends MarginLayoutParams {
        public static final int BASELINE = 5;
        public static final int BOTTOM = 4;
        public static final int CHAIN_PACKED = 2;
        public static final int CHAIN_SPREAD = 0;
        public static final int CHAIN_SPREAD_INSIDE = 1;
        public static final int END = 7;
        public static final int HORIZONTAL = 0;
        public static final int LEFT = 1;
        public static final int MATCH_CONSTRAINT = 0;
        public static final int MATCH_CONSTRAINT_PERCENT = 2;
        public static final int MATCH_CONSTRAINT_SPREAD = 0;
        public static final int MATCH_CONSTRAINT_WRAP = 1;
        public static final int PARENT_ID = 0;
        public static final int RIGHT = 2;
        public static final int START = 6;
        public static final int TOP = 3;
        public static final int UNSET = -1;
        public static final int VERTICAL = 1;
        float a = BitmapDescriptorFactory.HUE_RED;
        int b = 1;
        public int baselineToBaseline = -1;
        public int bottomToBottom = -1;
        public int bottomToTop = -1;
        boolean c = true;
        public float circleAngle = BitmapDescriptorFactory.HUE_RED;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public boolean constrainedHeight = false;
        public boolean constrainedWidth = false;
        boolean d = true;
        public String dimensionRatio = null;
        boolean e = false;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int endToEnd = -1;
        public int endToStart = -1;
        boolean f = false;
        boolean g = false;
        public int goneBottomMargin = -1;
        public int goneEndMargin = -1;
        public int goneLeftMargin = -1;
        public int goneRightMargin = -1;
        public int goneStartMargin = -1;
        public int goneTopMargin = -1;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        boolean h = false;
        public boolean helped = false;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        public float horizontalWeight = -1.0f;
        int i = -1;
        int j = -1;
        int k = -1;
        int l = -1;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        int m = -1;
        public int matchConstraintDefaultHeight = 0;
        public int matchConstraintDefaultWidth = 0;
        public int matchConstraintMaxHeight = 0;
        public int matchConstraintMaxWidth = 0;
        public int matchConstraintMinHeight = 0;
        public int matchConstraintMinWidth = 0;
        public float matchConstraintPercentHeight = 1.0f;
        public float matchConstraintPercentWidth = 1.0f;
        int n = -1;
        float o = 0.5f;
        public int orientation = -1;
        int p;
        int q;
        float r;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        ConstraintWidget s = new ConstraintWidget();
        public int startToEnd = -1;
        public int startToStart = -1;
        public int topToBottom = -1;
        public int topToTop = -1;
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        public float verticalWeight = -1.0f;

        static class Table {
            public static final SparseIntArray a = new SparseIntArray();

            private Table() {
            }

            static {
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                a.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                a.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                a.append(R.styleable.ConstraintLayout_Layout_android_orientation, 1);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                a.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                a.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
            }
        }

        public void reset() {
            if (this.s != null) {
                this.s.reset();
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.guideBegin = layoutParams.guideBegin;
            this.guideEnd = layoutParams.guideEnd;
            this.guidePercent = layoutParams.guidePercent;
            this.leftToLeft = layoutParams.leftToLeft;
            this.leftToRight = layoutParams.leftToRight;
            this.rightToLeft = layoutParams.rightToLeft;
            this.rightToRight = layoutParams.rightToRight;
            this.topToTop = layoutParams.topToTop;
            this.topToBottom = layoutParams.topToBottom;
            this.bottomToTop = layoutParams.bottomToTop;
            this.bottomToBottom = layoutParams.bottomToBottom;
            this.baselineToBaseline = layoutParams.baselineToBaseline;
            this.circleConstraint = layoutParams.circleConstraint;
            this.circleRadius = layoutParams.circleRadius;
            this.circleAngle = layoutParams.circleAngle;
            this.startToEnd = layoutParams.startToEnd;
            this.startToStart = layoutParams.startToStart;
            this.endToStart = layoutParams.endToStart;
            this.endToEnd = layoutParams.endToEnd;
            this.goneLeftMargin = layoutParams.goneLeftMargin;
            this.goneTopMargin = layoutParams.goneTopMargin;
            this.goneRightMargin = layoutParams.goneRightMargin;
            this.goneBottomMargin = layoutParams.goneBottomMargin;
            this.goneStartMargin = layoutParams.goneStartMargin;
            this.goneEndMargin = layoutParams.goneEndMargin;
            this.horizontalBias = layoutParams.horizontalBias;
            this.verticalBias = layoutParams.verticalBias;
            this.dimensionRatio = layoutParams.dimensionRatio;
            this.a = layoutParams.a;
            this.b = layoutParams.b;
            this.horizontalWeight = layoutParams.horizontalWeight;
            this.verticalWeight = layoutParams.verticalWeight;
            this.horizontalChainStyle = layoutParams.horizontalChainStyle;
            this.verticalChainStyle = layoutParams.verticalChainStyle;
            this.constrainedWidth = layoutParams.constrainedWidth;
            this.constrainedHeight = layoutParams.constrainedHeight;
            this.matchConstraintDefaultWidth = layoutParams.matchConstraintDefaultWidth;
            this.matchConstraintDefaultHeight = layoutParams.matchConstraintDefaultHeight;
            this.matchConstraintMinWidth = layoutParams.matchConstraintMinWidth;
            this.matchConstraintMaxWidth = layoutParams.matchConstraintMaxWidth;
            this.matchConstraintMinHeight = layoutParams.matchConstraintMinHeight;
            this.matchConstraintMaxHeight = layoutParams.matchConstraintMaxHeight;
            this.matchConstraintPercentWidth = layoutParams.matchConstraintPercentWidth;
            this.matchConstraintPercentHeight = layoutParams.matchConstraintPercentHeight;
            this.editorAbsoluteX = layoutParams.editorAbsoluteX;
            this.editorAbsoluteY = layoutParams.editorAbsoluteY;
            this.orientation = layoutParams.orientation;
            this.c = layoutParams.c;
            this.d = layoutParams.d;
            this.e = layoutParams.e;
            this.f = layoutParams.f;
            this.i = layoutParams.i;
            this.j = layoutParams.j;
            this.k = layoutParams.k;
            this.l = layoutParams.l;
            this.m = layoutParams.m;
            this.n = layoutParams.n;
            this.o = layoutParams.o;
            this.s = layoutParams.s;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            int i2;
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = obtainStyledAttributes.getIndex(i3);
                switch (Table.a.get(index)) {
                    case 1:
                        this.orientation = obtainStyledAttributes.getInt(index, this.orientation);
                        break;
                    case 2:
                        this.circleConstraint = obtainStyledAttributes.getResourceId(index, this.circleConstraint);
                        if (this.circleConstraint != -1) {
                            break;
                        } else {
                            this.circleConstraint = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 3:
                        this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, this.circleRadius);
                        break;
                    case 4:
                        this.circleAngle = obtainStyledAttributes.getFloat(index, this.circleAngle) % 360.0f;
                        if (this.circleAngle >= BitmapDescriptorFactory.HUE_RED) {
                            break;
                        } else {
                            this.circleAngle = (360.0f - this.circleAngle) % 360.0f;
                            break;
                        }
                    case 5:
                        this.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideBegin);
                        break;
                    case 6:
                        this.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideEnd);
                        break;
                    case 7:
                        this.guidePercent = obtainStyledAttributes.getFloat(index, this.guidePercent);
                        break;
                    case 8:
                        this.leftToLeft = obtainStyledAttributes.getResourceId(index, this.leftToLeft);
                        if (this.leftToLeft != -1) {
                            break;
                        } else {
                            this.leftToLeft = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 9:
                        this.leftToRight = obtainStyledAttributes.getResourceId(index, this.leftToRight);
                        if (this.leftToRight != -1) {
                            break;
                        } else {
                            this.leftToRight = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 10:
                        this.rightToLeft = obtainStyledAttributes.getResourceId(index, this.rightToLeft);
                        if (this.rightToLeft != -1) {
                            break;
                        } else {
                            this.rightToLeft = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 11:
                        this.rightToRight = obtainStyledAttributes.getResourceId(index, this.rightToRight);
                        if (this.rightToRight != -1) {
                            break;
                        } else {
                            this.rightToRight = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 12:
                        this.topToTop = obtainStyledAttributes.getResourceId(index, this.topToTop);
                        if (this.topToTop != -1) {
                            break;
                        } else {
                            this.topToTop = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 13:
                        this.topToBottom = obtainStyledAttributes.getResourceId(index, this.topToBottom);
                        if (this.topToBottom != -1) {
                            break;
                        } else {
                            this.topToBottom = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 14:
                        this.bottomToTop = obtainStyledAttributes.getResourceId(index, this.bottomToTop);
                        if (this.bottomToTop != -1) {
                            break;
                        } else {
                            this.bottomToTop = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 15:
                        this.bottomToBottom = obtainStyledAttributes.getResourceId(index, this.bottomToBottom);
                        if (this.bottomToBottom != -1) {
                            break;
                        } else {
                            this.bottomToBottom = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 16:
                        this.baselineToBaseline = obtainStyledAttributes.getResourceId(index, this.baselineToBaseline);
                        if (this.baselineToBaseline != -1) {
                            break;
                        } else {
                            this.baselineToBaseline = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 17:
                        this.startToEnd = obtainStyledAttributes.getResourceId(index, this.startToEnd);
                        if (this.startToEnd != -1) {
                            break;
                        } else {
                            this.startToEnd = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 18:
                        this.startToStart = obtainStyledAttributes.getResourceId(index, this.startToStart);
                        if (this.startToStart != -1) {
                            break;
                        } else {
                            this.startToStart = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 19:
                        this.endToStart = obtainStyledAttributes.getResourceId(index, this.endToStart);
                        if (this.endToStart != -1) {
                            break;
                        } else {
                            this.endToStart = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 20:
                        this.endToEnd = obtainStyledAttributes.getResourceId(index, this.endToEnd);
                        if (this.endToEnd != -1) {
                            break;
                        } else {
                            this.endToEnd = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 21:
                        this.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneLeftMargin);
                        break;
                    case 22:
                        this.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneTopMargin);
                        break;
                    case 23:
                        this.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneRightMargin);
                        break;
                    case 24:
                        this.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBottomMargin);
                        break;
                    case 25:
                        this.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneStartMargin);
                        break;
                    case 26:
                        this.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneEndMargin);
                        break;
                    case 27:
                        this.constrainedWidth = obtainStyledAttributes.getBoolean(index, this.constrainedWidth);
                        break;
                    case 28:
                        this.constrainedHeight = obtainStyledAttributes.getBoolean(index, this.constrainedHeight);
                        break;
                    case 29:
                        this.horizontalBias = obtainStyledAttributes.getFloat(index, this.horizontalBias);
                        break;
                    case 30:
                        this.verticalBias = obtainStyledAttributes.getFloat(index, this.verticalBias);
                        break;
                    case 31:
                        this.matchConstraintDefaultWidth = obtainStyledAttributes.getInt(index, 0);
                        if (this.matchConstraintDefaultWidth != 1) {
                            break;
                        } else {
                            Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                            break;
                        }
                    case 32:
                        this.matchConstraintDefaultHeight = obtainStyledAttributes.getInt(index, 0);
                        if (this.matchConstraintDefaultHeight != 1) {
                            break;
                        } else {
                            Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                            break;
                        }
                    case 33:
                        try {
                            this.matchConstraintMinWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMinWidth);
                            break;
                        } catch (Exception unused) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMinWidth) != -2) {
                                break;
                            } else {
                                this.matchConstraintMinWidth = -2;
                                break;
                            }
                        }
                    case 34:
                        try {
                            this.matchConstraintMaxWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMaxWidth);
                            break;
                        } catch (Exception unused2) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMaxWidth) != -2) {
                                break;
                            } else {
                                this.matchConstraintMaxWidth = -2;
                                break;
                            }
                        }
                    case 35:
                        this.matchConstraintPercentWidth = Math.max(BitmapDescriptorFactory.HUE_RED, obtainStyledAttributes.getFloat(index, this.matchConstraintPercentWidth));
                        break;
                    case 36:
                        try {
                            this.matchConstraintMinHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMinHeight);
                            break;
                        } catch (Exception unused3) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMinHeight) != -2) {
                                break;
                            } else {
                                this.matchConstraintMinHeight = -2;
                                break;
                            }
                        }
                    case 37:
                        try {
                            this.matchConstraintMaxHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMaxHeight);
                            break;
                        } catch (Exception unused4) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMaxHeight) != -2) {
                                break;
                            } else {
                                this.matchConstraintMaxHeight = -2;
                                break;
                            }
                        }
                    case 38:
                        this.matchConstraintPercentHeight = Math.max(BitmapDescriptorFactory.HUE_RED, obtainStyledAttributes.getFloat(index, this.matchConstraintPercentHeight));
                        break;
                    case 44:
                        this.dimensionRatio = obtainStyledAttributes.getString(index);
                        this.a = Float.NaN;
                        this.b = -1;
                        if (this.dimensionRatio == null) {
                            break;
                        } else {
                            int length = this.dimensionRatio.length();
                            int indexOf = this.dimensionRatio.indexOf(44);
                            if (indexOf <= 0 || indexOf >= length - 1) {
                                i2 = 0;
                            } else {
                                String substring = this.dimensionRatio.substring(0, indexOf);
                                if (substring.equalsIgnoreCase(ExifInterface.LONGITUDE_WEST)) {
                                    this.b = 0;
                                } else if (substring.equalsIgnoreCase("H")) {
                                    this.b = 1;
                                }
                                i2 = indexOf + 1;
                            }
                            int indexOf2 = this.dimensionRatio.indexOf(58);
                            if (indexOf2 >= 0 && indexOf2 < length - 1) {
                                String substring2 = this.dimensionRatio.substring(i2, indexOf2);
                                String substring3 = this.dimensionRatio.substring(indexOf2 + 1);
                                if (substring2.length() > 0 && substring3.length() > 0) {
                                    try {
                                        float parseFloat = Float.parseFloat(substring2);
                                        float parseFloat2 = Float.parseFloat(substring3);
                                        if (parseFloat > BitmapDescriptorFactory.HUE_RED && parseFloat2 > BitmapDescriptorFactory.HUE_RED) {
                                            if (this.b != 1) {
                                                this.a = Math.abs(parseFloat / parseFloat2);
                                                break;
                                            } else {
                                                this.a = Math.abs(parseFloat2 / parseFloat);
                                                break;
                                            }
                                        }
                                    } catch (NumberFormatException unused5) {
                                        break;
                                    }
                                }
                            } else {
                                String substring4 = this.dimensionRatio.substring(i2);
                                if (substring4.length() <= 0) {
                                    break;
                                } else {
                                    this.a = Float.parseFloat(substring4);
                                    break;
                                }
                            }
                        }
                        break;
                    case 45:
                        this.horizontalWeight = obtainStyledAttributes.getFloat(index, this.horizontalWeight);
                        break;
                    case 46:
                        this.verticalWeight = obtainStyledAttributes.getFloat(index, this.verticalWeight);
                        break;
                    case 47:
                        this.horizontalChainStyle = obtainStyledAttributes.getInt(index, 0);
                        break;
                    case 48:
                        this.verticalChainStyle = obtainStyledAttributes.getInt(index, 0);
                        break;
                    case 49:
                        this.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteX);
                        break;
                    case 50:
                        this.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteY);
                        break;
                }
            }
            obtainStyledAttributes.recycle();
            validate();
        }

        public void validate() {
            this.f = false;
            this.c = true;
            this.d = true;
            if (this.width == -2 && this.constrainedWidth) {
                this.c = false;
                this.matchConstraintDefaultWidth = 1;
            }
            if (this.height == -2 && this.constrainedHeight) {
                this.d = false;
                this.matchConstraintDefaultHeight = 1;
            }
            if (this.width == 0 || this.width == -1) {
                this.c = false;
                if (this.width == 0 && this.matchConstraintDefaultWidth == 1) {
                    this.width = -2;
                    this.constrainedWidth = true;
                }
            }
            if (this.height == 0 || this.height == -1) {
                this.d = false;
                if (this.height == 0 && this.matchConstraintDefaultHeight == 1) {
                    this.height = -2;
                    this.constrainedHeight = true;
                }
            }
            if (this.guidePercent != -1.0f || this.guideBegin != -1 || this.guideEnd != -1) {
                this.f = true;
                this.c = true;
                this.d = true;
                if (!(this.s instanceof Guideline)) {
                    this.s = new Guideline();
                }
                ((Guideline) this.s).setOrientation(this.orientation);
            }
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0050  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0059  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0062  */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x006a  */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0072  */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x0088  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0092  */
        @android.annotation.TargetApi(17)
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void resolveLayoutDirection(int r6) {
            /*
                r5 = this;
                int r0 = r5.leftMargin
                int r1 = r5.rightMargin
                super.resolveLayoutDirection(r6)
                r6 = -1
                r5.k = r6
                r5.l = r6
                r5.i = r6
                r5.j = r6
                r5.m = r6
                r5.n = r6
                int r2 = r5.goneLeftMargin
                r5.m = r2
                int r2 = r5.goneRightMargin
                r5.n = r2
                float r2 = r5.horizontalBias
                r5.o = r2
                int r2 = r5.guideBegin
                r5.p = r2
                int r2 = r5.guideEnd
                r5.q = r2
                float r2 = r5.guidePercent
                r5.r = r2
                int r2 = r5.getLayoutDirection()
                r3 = 0
                r4 = 1
                if (r4 != r2) goto L_0x0036
                r2 = 1
                goto L_0x0037
            L_0x0036:
                r2 = 0
            L_0x0037:
                if (r2 == 0) goto L_0x00ac
                int r2 = r5.startToEnd
                if (r2 == r6) goto L_0x0043
                int r2 = r5.startToEnd
                r5.k = r2
            L_0x0041:
                r3 = 1
                goto L_0x004c
            L_0x0043:
                int r2 = r5.startToStart
                if (r2 == r6) goto L_0x004c
                int r2 = r5.startToStart
                r5.l = r2
                goto L_0x0041
            L_0x004c:
                int r2 = r5.endToStart
                if (r2 == r6) goto L_0x0055
                int r2 = r5.endToStart
                r5.j = r2
                r3 = 1
            L_0x0055:
                int r2 = r5.endToEnd
                if (r2 == r6) goto L_0x005e
                int r2 = r5.endToEnd
                r5.i = r2
                r3 = 1
            L_0x005e:
                int r2 = r5.goneStartMargin
                if (r2 == r6) goto L_0x0066
                int r2 = r5.goneStartMargin
                r5.n = r2
            L_0x0066:
                int r2 = r5.goneEndMargin
                if (r2 == r6) goto L_0x006e
                int r2 = r5.goneEndMargin
                r5.m = r2
            L_0x006e:
                r2 = 1065353216(0x3f800000, float:1.0)
                if (r3 == 0) goto L_0x0078
                float r3 = r5.horizontalBias
                float r3 = r2 - r3
                r5.o = r3
            L_0x0078:
                boolean r3 = r5.f
                if (r3 == 0) goto L_0x00dc
                int r3 = r5.orientation
                if (r3 != r4) goto L_0x00dc
                float r3 = r5.guidePercent
                r4 = -1082130432(0xffffffffbf800000, float:-1.0)
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 == 0) goto L_0x0092
                float r3 = r5.guidePercent
                float r2 = r2 - r3
                r5.r = r2
                r5.p = r6
                r5.q = r6
                goto L_0x00dc
            L_0x0092:
                int r2 = r5.guideBegin
                if (r2 == r6) goto L_0x009f
                int r2 = r5.guideBegin
                r5.q = r2
                r5.p = r6
                r5.r = r4
                goto L_0x00dc
            L_0x009f:
                int r2 = r5.guideEnd
                if (r2 == r6) goto L_0x00dc
                int r2 = r5.guideEnd
                r5.p = r2
                r5.q = r6
                r5.r = r4
                goto L_0x00dc
            L_0x00ac:
                int r2 = r5.startToEnd
                if (r2 == r6) goto L_0x00b4
                int r2 = r5.startToEnd
                r5.j = r2
            L_0x00b4:
                int r2 = r5.startToStart
                if (r2 == r6) goto L_0x00bc
                int r2 = r5.startToStart
                r5.i = r2
            L_0x00bc:
                int r2 = r5.endToStart
                if (r2 == r6) goto L_0x00c4
                int r2 = r5.endToStart
                r5.k = r2
            L_0x00c4:
                int r2 = r5.endToEnd
                if (r2 == r6) goto L_0x00cc
                int r2 = r5.endToEnd
                r5.l = r2
            L_0x00cc:
                int r2 = r5.goneStartMargin
                if (r2 == r6) goto L_0x00d4
                int r2 = r5.goneStartMargin
                r5.m = r2
            L_0x00d4:
                int r2 = r5.goneEndMargin
                if (r2 == r6) goto L_0x00dc
                int r2 = r5.goneEndMargin
                r5.n = r2
            L_0x00dc:
                int r2 = r5.endToStart
                if (r2 != r6) goto L_0x012e
                int r2 = r5.endToEnd
                if (r2 != r6) goto L_0x012e
                int r2 = r5.startToStart
                if (r2 != r6) goto L_0x012e
                int r2 = r5.startToEnd
                if (r2 != r6) goto L_0x012e
                int r2 = r5.rightToLeft
                if (r2 == r6) goto L_0x00fd
                int r2 = r5.rightToLeft
                r5.k = r2
                int r2 = r5.rightMargin
                if (r2 > 0) goto L_0x010d
                if (r1 <= 0) goto L_0x010d
                r5.rightMargin = r1
                goto L_0x010d
            L_0x00fd:
                int r2 = r5.rightToRight
                if (r2 == r6) goto L_0x010d
                int r2 = r5.rightToRight
                r5.l = r2
                int r2 = r5.rightMargin
                if (r2 > 0) goto L_0x010d
                if (r1 <= 0) goto L_0x010d
                r5.rightMargin = r1
            L_0x010d:
                int r1 = r5.leftToLeft
                if (r1 == r6) goto L_0x011e
                int r6 = r5.leftToLeft
                r5.i = r6
                int r6 = r5.leftMargin
                if (r6 > 0) goto L_0x012e
                if (r0 <= 0) goto L_0x012e
                r5.leftMargin = r0
                goto L_0x012e
            L_0x011e:
                int r1 = r5.leftToRight
                if (r1 == r6) goto L_0x012e
                int r6 = r5.leftToRight
                r5.j = r6
                int r6 = r5.leftMargin
                if (r6 > 0) goto L_0x012e
                if (r0 <= 0) goto L_0x012e
                r5.leftMargin = r0
            L_0x012e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.ConstraintLayout.LayoutParams.resolveLayoutDirection(int):void");
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void setDesignInformation(int i2, Object obj, Object obj2) {
        if (i2 == 0 && (obj instanceof String) && (obj2 instanceof Integer)) {
            if (this.q == null) {
                this.q = new HashMap<>();
            }
            String str = (String) obj;
            int indexOf = str.indexOf("/");
            if (indexOf != -1) {
                str = str.substring(indexOf + 1);
            }
            this.q.put(str, Integer.valueOf(((Integer) obj2).intValue()));
        }
    }

    public Object getDesignInformation(int i2, Object obj) {
        if (i2 == 0 && (obj instanceof String)) {
            String str = (String) obj;
            if (this.q != null && this.q.containsKey(str)) {
                return this.q.get(str);
            }
        }
        return null;
    }

    public ConstraintLayout(Context context) {
        super(context);
        a((AttributeSet) null);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(attributeSet);
    }

    public void setId(int i2) {
        this.a.remove(getId());
        super.setId(i2);
        this.a.put(getId(), this);
    }

    private void a(AttributeSet attributeSet) {
        this.b.setCompanionWidget(this);
        this.a.put(getId(), this);
        this.o = null;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = obtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.i = obtainStyledAttributes.getDimensionPixelOffset(index, this.i);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.j = obtainStyledAttributes.getDimensionPixelOffset(index, this.j);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.k = obtainStyledAttributes.getDimensionPixelOffset(index, this.k);
                } else if (index == R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.l = obtainStyledAttributes.getDimensionPixelOffset(index, this.l);
                } else if (index == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.n = obtainStyledAttributes.getInt(index, this.n);
                } else if (index == R.styleable.ConstraintLayout_Layout_constraintSet) {
                    int resourceId = obtainStyledAttributes.getResourceId(index, 0);
                    try {
                        this.o = new ConstraintSet();
                        this.o.load(getContext(), resourceId);
                    } catch (NotFoundException unused) {
                        this.o = null;
                    }
                    this.p = resourceId;
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.b.setOptimizationLevel(this.n);
    }

    public void addView(View view, int i2, android.view.ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i2, layoutParams);
        if (VERSION.SDK_INT < 14) {
            onViewAdded(view);
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        if (VERSION.SDK_INT < 14) {
            onViewRemoved(view);
        }
    }

    public void onViewAdded(View view) {
        if (VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        ConstraintWidget viewWidget = getViewWidget(view);
        if ((view instanceof Guideline) && !(viewWidget instanceof Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.s = new Guideline();
            layoutParams.f = true;
            ((Guideline) layoutParams.s).setOrientation(layoutParams.orientation);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper constraintHelper = (ConstraintHelper) view;
            constraintHelper.validateParams();
            ((LayoutParams) view.getLayoutParams()).g = true;
            if (!this.g.contains(constraintHelper)) {
                this.g.add(constraintHelper);
            }
        }
        this.a.put(view.getId(), view);
        this.m = true;
    }

    public void onViewRemoved(View view) {
        if (VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.a.remove(view.getId());
        ConstraintWidget viewWidget = getViewWidget(view);
        this.b.remove(viewWidget);
        this.g.remove(view);
        this.h.remove(viewWidget);
        this.m = true;
    }

    public void setMinWidth(int i2) {
        if (i2 != this.i) {
            this.i = i2;
            requestLayout();
        }
    }

    public void setMinHeight(int i2) {
        if (i2 != this.j) {
            this.j = i2;
            requestLayout();
        }
    }

    public int getMinWidth() {
        return this.i;
    }

    public int getMinHeight() {
        return this.j;
    }

    public void setMaxWidth(int i2) {
        if (i2 != this.k) {
            this.k = i2;
            requestLayout();
        }
    }

    public void setMaxHeight(int i2) {
        if (i2 != this.l) {
            this.l = i2;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.k;
    }

    public int getMaxHeight() {
        return this.l;
    }

    private void a() {
        int childCount = getChildCount();
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            } else if (getChildAt(i2).isLayoutRequested()) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            this.h.clear();
            b();
        }
    }

    private void b() {
        int i2;
        int i3;
        int i4;
        float f2;
        int i5;
        int i6;
        boolean isInEditMode = isInEditMode();
        int childCount = getChildCount();
        boolean z = false;
        if (isInEditMode) {
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                try {
                    String resourceName = getResources().getResourceName(childAt.getId());
                    setDesignInformation(0, resourceName, Integer.valueOf(childAt.getId()));
                    int indexOf = resourceName.indexOf(47);
                    if (indexOf != -1) {
                        resourceName = resourceName.substring(indexOf + 1);
                    }
                    a(childAt.getId()).setDebugName(resourceName);
                } catch (NotFoundException unused) {
                }
            }
        }
        for (int i8 = 0; i8 < childCount; i8++) {
            ConstraintWidget viewWidget = getViewWidget(getChildAt(i8));
            if (viewWidget != null) {
                viewWidget.reset();
            }
        }
        if (this.p != -1) {
            for (int i9 = 0; i9 < childCount; i9++) {
                View childAt2 = getChildAt(i9);
                if (childAt2.getId() == this.p && (childAt2 instanceof Constraints)) {
                    this.o = ((Constraints) childAt2).getConstraintSet();
                }
            }
        }
        if (this.o != null) {
            this.o.a(this);
        }
        this.b.removeAllChildren();
        int size = this.g.size();
        if (size > 0) {
            for (int i10 = 0; i10 < size; i10++) {
                ((ConstraintHelper) this.g.get(i10)).updatePreLayout(this);
            }
        }
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt3 = getChildAt(i11);
            if (childAt3 instanceof Placeholder) {
                ((Placeholder) childAt3).updatePreLayout(this);
            }
        }
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt4 = getChildAt(i12);
            ConstraintWidget viewWidget2 = getViewWidget(childAt4);
            if (viewWidget2 != null) {
                LayoutParams layoutParams = (LayoutParams) childAt4.getLayoutParams();
                layoutParams.validate();
                if (layoutParams.helped) {
                    layoutParams.helped = z;
                } else if (isInEditMode) {
                    try {
                        String resourceName2 = getResources().getResourceName(childAt4.getId());
                        setDesignInformation(z ? 1 : 0, resourceName2, Integer.valueOf(childAt4.getId()));
                        a(childAt4.getId()).setDebugName(resourceName2.substring(resourceName2.indexOf("id/") + 3));
                    } catch (NotFoundException unused2) {
                    }
                }
                viewWidget2.setVisibility(childAt4.getVisibility());
                if (layoutParams.h) {
                    viewWidget2.setVisibility(8);
                }
                viewWidget2.setCompanionWidget(childAt4);
                this.b.add(viewWidget2);
                if (!layoutParams.d || !layoutParams.c) {
                    this.h.add(viewWidget2);
                }
                if (layoutParams.f) {
                    Guideline guideline = (Guideline) viewWidget2;
                    int i13 = layoutParams.p;
                    int i14 = layoutParams.q;
                    float f3 = layoutParams.r;
                    if (VERSION.SDK_INT < 17) {
                        i13 = layoutParams.guideBegin;
                        i14 = layoutParams.guideEnd;
                        f3 = layoutParams.guidePercent;
                    }
                    if (f3 != -1.0f) {
                        guideline.setGuidePercent(f3);
                    } else if (i13 != -1) {
                        guideline.setGuideBegin(i13);
                    } else if (i14 != -1) {
                        guideline.setGuideEnd(i14);
                    }
                } else if (layoutParams.leftToLeft != -1 || layoutParams.leftToRight != -1 || layoutParams.rightToLeft != -1 || layoutParams.rightToRight != -1 || layoutParams.startToStart != -1 || layoutParams.startToEnd != -1 || layoutParams.endToStart != -1 || layoutParams.endToEnd != -1 || layoutParams.topToTop != -1 || layoutParams.topToBottom != -1 || layoutParams.bottomToTop != -1 || layoutParams.bottomToBottom != -1 || layoutParams.baselineToBaseline != -1 || layoutParams.editorAbsoluteX != -1 || layoutParams.editorAbsoluteY != -1 || layoutParams.circleConstraint != -1 || layoutParams.width == -1 || layoutParams.height == -1) {
                    int i15 = layoutParams.i;
                    int i16 = layoutParams.j;
                    int i17 = layoutParams.k;
                    int i18 = layoutParams.l;
                    int i19 = layoutParams.m;
                    int i20 = layoutParams.n;
                    float f4 = layoutParams.o;
                    if (VERSION.SDK_INT < 17) {
                        int i21 = layoutParams.leftToLeft;
                        int i22 = layoutParams.leftToRight;
                        i17 = layoutParams.rightToLeft;
                        i18 = layoutParams.rightToRight;
                        int i23 = layoutParams.goneLeftMargin;
                        int i24 = layoutParams.goneRightMargin;
                        f4 = layoutParams.horizontalBias;
                        if (i21 == -1 && i22 == -1) {
                            if (layoutParams.startToStart != -1) {
                                i21 = layoutParams.startToStart;
                            } else if (layoutParams.startToEnd != -1) {
                                i22 = layoutParams.startToEnd;
                            }
                        }
                        int i25 = i22;
                        i15 = i21;
                        i4 = i25;
                        if (i17 == -1 && i18 == -1) {
                            if (layoutParams.endToStart != -1) {
                                i17 = layoutParams.endToStart;
                            } else if (layoutParams.endToEnd != -1) {
                                i18 = layoutParams.endToEnd;
                            }
                        }
                        i3 = i23;
                        i2 = i24;
                    } else {
                        i4 = i16;
                        i2 = i20;
                        i3 = i19;
                    }
                    int i26 = i18;
                    float f5 = f4;
                    int i27 = i17;
                    if (layoutParams.circleConstraint != -1) {
                        ConstraintWidget a2 = a(layoutParams.circleConstraint);
                        if (a2 != null) {
                            viewWidget2.connectCircularConstraint(a2, layoutParams.circleAngle, layoutParams.circleRadius);
                        }
                    } else {
                        if (i15 != -1) {
                            ConstraintWidget a3 = a(i15);
                            if (a3 != null) {
                                Type type = Type.LEFT;
                                f2 = f5;
                                Type type2 = Type.LEFT;
                                i6 = i26;
                                viewWidget2.immediateConnect(type, a3, type2, layoutParams.leftMargin, i3);
                            } else {
                                f2 = f5;
                                i6 = i26;
                            }
                            i5 = i6;
                        } else {
                            f2 = f5;
                            i5 = i26;
                            if (i4 != -1) {
                                ConstraintWidget a4 = a(i4);
                                if (a4 != null) {
                                    viewWidget2.immediateConnect(Type.LEFT, a4, Type.RIGHT, layoutParams.leftMargin, i3);
                                }
                            }
                        }
                        if (i27 != -1) {
                            ConstraintWidget a5 = a(i27);
                            if (a5 != null) {
                                viewWidget2.immediateConnect(Type.RIGHT, a5, Type.LEFT, layoutParams.rightMargin, i2);
                            }
                        } else if (i5 != -1) {
                            ConstraintWidget a6 = a(i5);
                            if (a6 != null) {
                                viewWidget2.immediateConnect(Type.RIGHT, a6, Type.RIGHT, layoutParams.rightMargin, i2);
                            }
                        }
                        if (layoutParams.topToTop != -1) {
                            ConstraintWidget a7 = a(layoutParams.topToTop);
                            if (a7 != null) {
                                viewWidget2.immediateConnect(Type.TOP, a7, Type.TOP, layoutParams.topMargin, layoutParams.goneTopMargin);
                            }
                        } else if (layoutParams.topToBottom != -1) {
                            ConstraintWidget a8 = a(layoutParams.topToBottom);
                            if (a8 != null) {
                                viewWidget2.immediateConnect(Type.TOP, a8, Type.BOTTOM, layoutParams.topMargin, layoutParams.goneTopMargin);
                            }
                        }
                        if (layoutParams.bottomToTop != -1) {
                            ConstraintWidget a9 = a(layoutParams.bottomToTop);
                            if (a9 != null) {
                                viewWidget2.immediateConnect(Type.BOTTOM, a9, Type.TOP, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                            }
                        } else if (layoutParams.bottomToBottom != -1) {
                            ConstraintWidget a10 = a(layoutParams.bottomToBottom);
                            if (a10 != null) {
                                viewWidget2.immediateConnect(Type.BOTTOM, a10, Type.BOTTOM, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                            }
                        }
                        if (layoutParams.baselineToBaseline != -1) {
                            View view = (View) this.a.get(layoutParams.baselineToBaseline);
                            ConstraintWidget a11 = a(layoutParams.baselineToBaseline);
                            if (!(a11 == null || view == null || !(view.getLayoutParams() instanceof LayoutParams))) {
                                LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                                layoutParams.e = true;
                                layoutParams2.e = true;
                                viewWidget2.getAnchor(Type.BASELINE).connect(a11.getAnchor(Type.BASELINE), 0, -1, Strength.STRONG, 0, true);
                                viewWidget2.getAnchor(Type.TOP).reset();
                                viewWidget2.getAnchor(Type.BOTTOM).reset();
                            }
                        }
                        float f6 = f2;
                        if (f6 >= BitmapDescriptorFactory.HUE_RED && f6 != 0.5f) {
                            viewWidget2.setHorizontalBiasPercent(f6);
                        }
                        if (layoutParams.verticalBias >= BitmapDescriptorFactory.HUE_RED && layoutParams.verticalBias != 0.5f) {
                            viewWidget2.setVerticalBiasPercent(layoutParams.verticalBias);
                        }
                    }
                    if (isInEditMode && !(layoutParams.editorAbsoluteX == -1 && layoutParams.editorAbsoluteY == -1)) {
                        viewWidget2.setOrigin(layoutParams.editorAbsoluteX, layoutParams.editorAbsoluteY);
                    }
                    if (layoutParams.c) {
                        viewWidget2.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                        viewWidget2.setWidth(layoutParams.width);
                    } else if (layoutParams.width == -1) {
                        viewWidget2.setHorizontalDimensionBehaviour(DimensionBehaviour.MATCH_PARENT);
                        viewWidget2.getAnchor(Type.LEFT).mMargin = layoutParams.leftMargin;
                        viewWidget2.getAnchor(Type.RIGHT).mMargin = layoutParams.rightMargin;
                    } else {
                        viewWidget2.setHorizontalDimensionBehaviour(DimensionBehaviour.MATCH_CONSTRAINT);
                        viewWidget2.setWidth(0);
                    }
                    if (layoutParams.d) {
                        z = false;
                        viewWidget2.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                        viewWidget2.setHeight(layoutParams.height);
                    } else if (layoutParams.height == -1) {
                        viewWidget2.setVerticalDimensionBehaviour(DimensionBehaviour.MATCH_PARENT);
                        viewWidget2.getAnchor(Type.TOP).mMargin = layoutParams.topMargin;
                        viewWidget2.getAnchor(Type.BOTTOM).mMargin = layoutParams.bottomMargin;
                        z = false;
                    } else {
                        viewWidget2.setVerticalDimensionBehaviour(DimensionBehaviour.MATCH_CONSTRAINT);
                        z = false;
                        viewWidget2.setHeight(0);
                    }
                    if (layoutParams.dimensionRatio != null) {
                        viewWidget2.setDimensionRatio(layoutParams.dimensionRatio);
                    }
                    viewWidget2.setHorizontalWeight(layoutParams.horizontalWeight);
                    viewWidget2.setVerticalWeight(layoutParams.verticalWeight);
                    viewWidget2.setHorizontalChainStyle(layoutParams.horizontalChainStyle);
                    viewWidget2.setVerticalChainStyle(layoutParams.verticalChainStyle);
                    viewWidget2.setHorizontalMatchStyle(layoutParams.matchConstraintDefaultWidth, layoutParams.matchConstraintMinWidth, layoutParams.matchConstraintMaxWidth, layoutParams.matchConstraintPercentWidth);
                    viewWidget2.setVerticalMatchStyle(layoutParams.matchConstraintDefaultHeight, layoutParams.matchConstraintMinHeight, layoutParams.matchConstraintMaxHeight, layoutParams.matchConstraintPercentHeight);
                }
            }
        }
    }

    private final ConstraintWidget a(int i2) {
        ConstraintWidget constraintWidget;
        if (i2 == 0) {
            return this.b;
        }
        View view = (View) this.a.get(i2);
        if (view == this) {
            return this.b;
        }
        if (view == null) {
            constraintWidget = null;
        } else {
            constraintWidget = ((LayoutParams) view.getLayoutParams()).s;
        }
        return constraintWidget;
    }

    public final ConstraintWidget getViewWidget(View view) {
        ConstraintWidget constraintWidget;
        if (view == this) {
            return this.b;
        }
        if (view == null) {
            constraintWidget = null;
        } else {
            constraintWidget = ((LayoutParams) view.getLayoutParams()).s;
        }
        return constraintWidget;
    }

    private void a(int i2, int i3) {
        boolean z;
        boolean z2;
        int i4;
        int i5;
        ConstraintLayout constraintLayout = this;
        int i6 = i2;
        int i7 = i3;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int childCount = getChildCount();
        int i8 = 0;
        while (i8 < childCount) {
            View childAt = constraintLayout.getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                ConstraintWidget constraintWidget = layoutParams.s;
                if (!layoutParams.f && !layoutParams.g) {
                    constraintWidget.setVisibility(childAt.getVisibility());
                    int i9 = layoutParams.width;
                    int i10 = layoutParams.height;
                    if (layoutParams.c || layoutParams.d || (!layoutParams.c && layoutParams.matchConstraintDefaultWidth == 1) || layoutParams.width == -1 || (!layoutParams.d && (layoutParams.matchConstraintDefaultHeight == 1 || layoutParams.height == -1))) {
                        if (i9 == 0) {
                            i4 = getChildMeasureSpec(i6, paddingLeft, -2);
                            z2 = true;
                        } else if (i9 == -1) {
                            i4 = getChildMeasureSpec(i6, paddingLeft, -1);
                            z2 = false;
                        } else {
                            z2 = i9 == -2;
                            i4 = getChildMeasureSpec(i6, paddingLeft, i9);
                        }
                        if (i10 == 0) {
                            i5 = getChildMeasureSpec(i7, paddingTop, -2);
                            z = true;
                        } else if (i10 == -1) {
                            i5 = getChildMeasureSpec(i7, paddingTop, -1);
                            z = false;
                        } else {
                            z = i10 == -2;
                            i5 = getChildMeasureSpec(i7, paddingTop, i10);
                        }
                        childAt.measure(i4, i5);
                        if (constraintLayout.t != null) {
                            constraintLayout.t.measures++;
                        }
                        constraintWidget.setWidthWrapContent(i9 == -2);
                        constraintWidget.setHeightWrapContent(i10 == -2);
                        i9 = childAt.getMeasuredWidth();
                        i10 = childAt.getMeasuredHeight();
                    } else {
                        z2 = false;
                        z = false;
                    }
                    constraintWidget.setWidth(i9);
                    constraintWidget.setHeight(i10);
                    if (z2) {
                        constraintWidget.setWrapWidth(i9);
                    }
                    if (z) {
                        constraintWidget.setWrapHeight(i10);
                    }
                    if (layoutParams.e) {
                        int baseline = childAt.getBaseline();
                        if (baseline != -1) {
                            constraintWidget.setBaselineDistance(baseline);
                        }
                    }
                }
            }
            i8++;
            constraintLayout = this;
            i6 = i2;
        }
    }

    private void c() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof Placeholder) {
                ((Placeholder) childAt).updatePostMeasure(this);
            }
        }
        int size = this.g.size();
        if (size > 0) {
            for (int i3 = 0; i3 < size; i3++) {
                ((ConstraintHelper) this.g.get(i3)).updatePostMeasure(this);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:110:0x0240  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x027e  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x02a5  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x02b2  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x02b9  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x02bb  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x02c1  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02c3  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x02d7  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x02dc  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x02e1  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x02e9  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x02f2  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x02fa  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x0305  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x0310  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r27, int r28) {
        /*
            r26 = this;
            r0 = r26
            r1 = r27
            r2 = r28
            int r3 = r26.getPaddingTop()
            int r4 = r26.getPaddingBottom()
            int r3 = r3 + r4
            int r4 = r26.getPaddingLeft()
            int r5 = r26.getPaddingRight()
            int r4 = r4 + r5
            int r5 = r26.getChildCount()
            r7 = 0
        L_0x001d:
            r8 = 1
            r10 = 8
            r12 = -2
            if (r7 >= r5) goto L_0x00f8
            android.view.View r14 = r0.getChildAt(r7)
            int r15 = r14.getVisibility()
            if (r15 != r10) goto L_0x0036
        L_0x002e:
            r19 = r3
            r20 = r4
            r21 = r5
            goto L_0x00ec
        L_0x0036:
            android.view.ViewGroup$LayoutParams r10 = r14.getLayoutParams()
            android.support.constraint.ConstraintLayout$LayoutParams r10 = (android.support.constraint.ConstraintLayout.LayoutParams) r10
            android.support.constraint.solver.widgets.ConstraintWidget r15 = r10.s
            boolean r6 = r10.f
            if (r6 != 0) goto L_0x002e
            boolean r6 = r10.g
            if (r6 == 0) goto L_0x0047
            goto L_0x002e
        L_0x0047:
            int r6 = r14.getVisibility()
            r15.setVisibility(r6)
            int r6 = r10.width
            int r13 = r10.height
            if (r6 == 0) goto L_0x00d8
            if (r13 != 0) goto L_0x0058
            goto L_0x00d8
        L_0x0058:
            if (r6 != r12) goto L_0x005d
            r16 = 1
            goto L_0x005f
        L_0x005d:
            r16 = 0
        L_0x005f:
            int r11 = getChildMeasureSpec(r1, r4, r6)
            if (r13 != r12) goto L_0x0068
            r17 = 1
            goto L_0x006a
        L_0x0068:
            r17 = 0
        L_0x006a:
            int r12 = getChildMeasureSpec(r2, r3, r13)
            r14.measure(r11, r12)
            android.support.constraint.solver.Metrics r11 = r0.t
            if (r11 == 0) goto L_0x0084
            android.support.constraint.solver.Metrics r11 = r0.t
            r19 = r3
            long r2 = r11.measures
            r20 = r4
            r21 = r5
            long r4 = r2 + r8
            r11.measures = r4
            goto L_0x008a
        L_0x0084:
            r19 = r3
            r20 = r4
            r21 = r5
        L_0x008a:
            r2 = -2
            if (r6 != r2) goto L_0x008f
            r3 = 1
            goto L_0x0090
        L_0x008f:
            r3 = 0
        L_0x0090:
            r15.setWidthWrapContent(r3)
            if (r13 != r2) goto L_0x0097
            r2 = 1
            goto L_0x0098
        L_0x0097:
            r2 = 0
        L_0x0098:
            r15.setHeightWrapContent(r2)
            int r2 = r14.getMeasuredWidth()
            int r3 = r14.getMeasuredHeight()
            r15.setWidth(r2)
            r15.setHeight(r3)
            if (r16 == 0) goto L_0x00ae
            r15.setWrapWidth(r2)
        L_0x00ae:
            if (r17 == 0) goto L_0x00b3
            r15.setWrapHeight(r3)
        L_0x00b3:
            boolean r4 = r10.e
            if (r4 == 0) goto L_0x00c1
            int r4 = r14.getBaseline()
            r5 = -1
            if (r4 == r5) goto L_0x00c1
            r15.setBaselineDistance(r4)
        L_0x00c1:
            boolean r4 = r10.c
            if (r4 == 0) goto L_0x00ec
            boolean r4 = r10.d
            if (r4 == 0) goto L_0x00ec
            android.support.constraint.solver.widgets.ResolutionDimension r4 = r15.getResolutionWidth()
            r4.resolve(r2)
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r15.getResolutionHeight()
            r2.resolve(r3)
            goto L_0x00ec
        L_0x00d8:
            r19 = r3
            r20 = r4
            r21 = r5
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r15.getResolutionWidth()
            r2.invalidate()
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r15.getResolutionHeight()
            r2.invalidate()
        L_0x00ec:
            int r7 = r7 + 1
            r3 = r19
            r4 = r20
            r5 = r21
            r2 = r28
            goto L_0x001d
        L_0x00f8:
            r19 = r3
            r20 = r4
            r21 = r5
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r2 = r0.b
            r2.solveGraph()
            r2 = r21
            r3 = 0
        L_0x0106:
            if (r3 >= r2) goto L_0x0323
            android.view.View r4 = r0.getChildAt(r3)
            int r5 = r4.getVisibility()
            if (r5 != r10) goto L_0x0120
        L_0x0112:
            r22 = r2
            r23 = r3
            r10 = r19
            r24 = r20
            r0 = -2
            r2 = -1
            r18 = r8
            goto L_0x0311
        L_0x0120:
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            android.support.constraint.ConstraintLayout$LayoutParams r5 = (android.support.constraint.ConstraintLayout.LayoutParams) r5
            android.support.constraint.solver.widgets.ConstraintWidget r6 = r5.s
            boolean r7 = r5.f
            if (r7 != 0) goto L_0x0112
            boolean r7 = r5.g
            if (r7 == 0) goto L_0x0131
            goto L_0x0112
        L_0x0131:
            int r7 = r4.getVisibility()
            r6.setVisibility(r7)
            int r7 = r5.width
            int r11 = r5.height
            if (r7 == 0) goto L_0x0141
            if (r11 == 0) goto L_0x0141
            goto L_0x0112
        L_0x0141:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r12 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor r12 = r6.getAnchor(r12)
            android.support.constraint.solver.widgets.ResolutionAnchor r12 = r12.getResolutionNode()
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r13 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r6.getAnchor(r13)
            android.support.constraint.solver.widgets.ResolutionAnchor r13 = r13.getResolutionNode()
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r6.getAnchor(r14)
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r14.getTarget()
            if (r14 == 0) goto L_0x016f
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r6.getAnchor(r14)
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r14.getTarget()
            if (r14 == 0) goto L_0x016f
            r14 = 1
            goto L_0x0170
        L_0x016f:
            r14 = 0
        L_0x0170:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r15 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor r15 = r6.getAnchor(r15)
            android.support.constraint.solver.widgets.ResolutionAnchor r15 = r15.getResolutionNode()
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r10 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r10 = r6.getAnchor(r10)
            android.support.constraint.solver.widgets.ResolutionAnchor r10 = r10.getResolutionNode()
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r8 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r6.getAnchor(r8)
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.getTarget()
            if (r8 == 0) goto L_0x019e
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r8 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r6.getAnchor(r8)
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.getTarget()
            if (r8 == 0) goto L_0x019e
            r8 = 1
            goto L_0x019f
        L_0x019e:
            r8 = 0
        L_0x019f:
            if (r7 != 0) goto L_0x01b5
            if (r11 != 0) goto L_0x01b5
            if (r14 == 0) goto L_0x01b5
            if (r8 == 0) goto L_0x01b5
            r22 = r2
            r23 = r3
            r10 = r19
            r24 = r20
            r0 = -2
            r2 = -1
            r18 = 1
            goto L_0x0311
        L_0x01b5:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r9 = r0.b
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r9.getHorizontalDimensionBehaviour()
            r22 = r2
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r9 == r2) goto L_0x01c3
            r2 = 1
            goto L_0x01c4
        L_0x01c3:
            r2 = 0
        L_0x01c4:
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r9 = r0.b
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r9.getVerticalDimensionBehaviour()
            r23 = r3
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r9 == r3) goto L_0x01d2
            r3 = 1
            goto L_0x01d3
        L_0x01d2:
            r3 = 0
        L_0x01d3:
            if (r2 != 0) goto L_0x01dc
            android.support.constraint.solver.widgets.ResolutionDimension r9 = r6.getResolutionWidth()
            r9.invalidate()
        L_0x01dc:
            if (r3 != 0) goto L_0x01e5
            android.support.constraint.solver.widgets.ResolutionDimension r9 = r6.getResolutionHeight()
            r9.invalidate()
        L_0x01e5:
            if (r7 != 0) goto L_0x0221
            if (r2 == 0) goto L_0x0216
            boolean r9 = r6.isSpreadWidth()
            if (r9 == 0) goto L_0x0216
            if (r14 == 0) goto L_0x0216
            boolean r9 = r12.isResolved()
            if (r9 == 0) goto L_0x0216
            boolean r9 = r13.isResolved()
            if (r9 == 0) goto L_0x0216
            float r7 = r13.getResolvedValue()
            float r9 = r12.getResolvedValue()
            float r7 = r7 - r9
            int r7 = (int) r7
            android.support.constraint.solver.widgets.ResolutionDimension r9 = r6.getResolutionWidth()
            r9.resolve(r7)
            r9 = r20
            int r12 = getChildMeasureSpec(r1, r9, r7)
            r13 = r2
            goto L_0x022d
        L_0x0216:
            r9 = r20
            r12 = -2
            int r2 = getChildMeasureSpec(r1, r9, r12)
            r12 = r2
            r2 = 1
            r13 = 0
            goto L_0x023e
        L_0x0221:
            r9 = r20
            r12 = -2
            r13 = -1
            if (r7 != r13) goto L_0x022f
            int r14 = getChildMeasureSpec(r1, r9, r13)
            r13 = r2
            r12 = r14
        L_0x022d:
            r2 = 0
            goto L_0x023e
        L_0x022f:
            if (r7 != r12) goto L_0x0233
            r12 = 1
            goto L_0x0234
        L_0x0233:
            r12 = 0
        L_0x0234:
            int r13 = getChildMeasureSpec(r1, r9, r7)
            r25 = r13
            r13 = r2
            r2 = r12
            r12 = r25
        L_0x023e:
            if (r11 != 0) goto L_0x027e
            if (r3 == 0) goto L_0x0271
            boolean r14 = r6.isSpreadHeight()
            if (r14 == 0) goto L_0x0271
            if (r8 == 0) goto L_0x0271
            boolean r8 = r15.isResolved()
            if (r8 == 0) goto L_0x0271
            boolean r8 = r10.isResolved()
            if (r8 == 0) goto L_0x0271
            float r8 = r10.getResolvedValue()
            float r10 = r15.getResolvedValue()
            float r8 = r8 - r10
            int r11 = (int) r8
            android.support.constraint.solver.widgets.ResolutionDimension r8 = r6.getResolutionHeight()
            r8.resolve(r11)
            r10 = r19
            r8 = r28
            int r14 = getChildMeasureSpec(r8, r10, r11)
            r15 = r3
            goto L_0x028d
        L_0x0271:
            r10 = r19
            r8 = r28
            r14 = -2
            int r3 = getChildMeasureSpec(r8, r10, r14)
            r14 = r3
            r3 = 1
            r15 = 0
            goto L_0x029e
        L_0x027e:
            r10 = r19
            r8 = r28
            r14 = -2
            r15 = -1
            if (r11 != r15) goto L_0x028f
            int r16 = getChildMeasureSpec(r8, r10, r15)
            r15 = r3
            r14 = r16
        L_0x028d:
            r3 = 0
            goto L_0x029e
        L_0x028f:
            if (r11 != r14) goto L_0x0293
            r14 = 1
            goto L_0x0294
        L_0x0293:
            r14 = 0
        L_0x0294:
            int r15 = getChildMeasureSpec(r8, r10, r11)
            r25 = r15
            r15 = r3
            r3 = r14
            r14 = r25
        L_0x029e:
            r4.measure(r12, r14)
            android.support.constraint.solver.Metrics r12 = r0.t
            if (r12 == 0) goto L_0x02b2
            android.support.constraint.solver.Metrics r12 = r0.t
            long r0 = r12.measures
            r24 = r9
            r18 = 1
            long r8 = r0 + r18
            r12.measures = r8
            goto L_0x02b6
        L_0x02b2:
            r24 = r9
            r18 = 1
        L_0x02b6:
            r0 = -2
            if (r7 != r0) goto L_0x02bb
            r1 = 1
            goto L_0x02bc
        L_0x02bb:
            r1 = 0
        L_0x02bc:
            r6.setWidthWrapContent(r1)
            if (r11 != r0) goto L_0x02c3
            r1 = 1
            goto L_0x02c4
        L_0x02c3:
            r1 = 0
        L_0x02c4:
            r6.setHeightWrapContent(r1)
            int r1 = r4.getMeasuredWidth()
            int r7 = r4.getMeasuredHeight()
            r6.setWidth(r1)
            r6.setHeight(r7)
            if (r2 == 0) goto L_0x02da
            r6.setWrapWidth(r1)
        L_0x02da:
            if (r3 == 0) goto L_0x02df
            r6.setWrapHeight(r7)
        L_0x02df:
            if (r13 == 0) goto L_0x02e9
            android.support.constraint.solver.widgets.ResolutionDimension r2 = r6.getResolutionWidth()
            r2.resolve(r1)
            goto L_0x02f0
        L_0x02e9:
            android.support.constraint.solver.widgets.ResolutionDimension r1 = r6.getResolutionWidth()
            r1.remove()
        L_0x02f0:
            if (r15 == 0) goto L_0x02fa
            android.support.constraint.solver.widgets.ResolutionDimension r1 = r6.getResolutionHeight()
            r1.resolve(r7)
            goto L_0x0301
        L_0x02fa:
            android.support.constraint.solver.widgets.ResolutionDimension r1 = r6.getResolutionHeight()
            r1.remove()
        L_0x0301:
            boolean r1 = r5.e
            if (r1 == 0) goto L_0x0310
            int r1 = r4.getBaseline()
            r2 = -1
            if (r1 == r2) goto L_0x0311
            r6.setBaselineDistance(r1)
            goto L_0x0311
        L_0x0310:
            r2 = -1
        L_0x0311:
            int r3 = r23 + 1
            r8 = r18
            r2 = r22
            r20 = r24
            r0 = r26
            r1 = r27
            r19 = r10
            r10 = 8
            goto L_0x0106
        L_0x0323:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.ConstraintLayout.b(int, int):void");
    }

    public void fillMetrics(Metrics metrics) {
        this.t = metrics;
        this.b.fillMetrics(metrics);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        boolean z;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16 = i2;
        int i17 = i3;
        System.currentTimeMillis();
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        int mode2 = MeasureSpec.getMode(i3);
        int size2 = MeasureSpec.getSize(i3);
        if (this.r != -1) {
            int i18 = this.s;
        }
        if (mode == 1073741824 && mode2 == 1073741824 && size == this.r) {
            int i19 = this.s;
        }
        boolean z2 = mode == this.e && mode2 == this.f;
        if (z2 && size == this.c) {
            int i20 = this.d;
        }
        if (z2 && mode == Integer.MIN_VALUE && mode2 == 1073741824 && size >= this.r) {
            int i21 = this.s;
        }
        if (z2 && mode == 1073741824 && mode2 == Integer.MIN_VALUE && size == this.r) {
            int i22 = this.s;
        }
        this.e = mode;
        this.f = mode2;
        this.c = size;
        this.d = size2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        this.b.setX(paddingLeft);
        this.b.setY(paddingTop);
        this.b.setMaxWidth(this.k);
        this.b.setMaxHeight(this.l);
        if (VERSION.SDK_INT >= 17) {
            this.b.setRtl(getLayoutDirection() == 1);
        }
        c(i2, i3);
        int width = this.b.getWidth();
        int height = this.b.getHeight();
        if (this.m) {
            this.m = false;
            a();
        }
        boolean z3 = (this.n & 8) == 8;
        if (z3) {
            this.b.preOptimize();
            this.b.optimizeForDimensions(width, height);
            b(i2, i3);
        } else {
            a(i2, i3);
        }
        c();
        if (getChildCount() > 0) {
            solveLinearSystem("First pass");
        }
        int size3 = this.h.size();
        int paddingBottom = paddingTop + getPaddingBottom();
        int paddingRight = paddingLeft + getPaddingRight();
        if (size3 > 0) {
            boolean z4 = this.b.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT;
            boolean z5 = this.b.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT;
            int max = Math.max(this.b.getWidth(), this.i);
            int max2 = Math.max(this.b.getHeight(), this.j);
            int i23 = max;
            int i24 = 0;
            boolean z6 = false;
            int i25 = 0;
            while (i24 < size3) {
                ConstraintWidget constraintWidget = (ConstraintWidget) this.h.get(i24);
                View view = (View) constraintWidget.getCompanionWidget();
                if (view == null) {
                    i10 = paddingRight;
                    i7 = width;
                    i8 = height;
                    i9 = size3;
                } else {
                    i9 = size3;
                    LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                    i8 = height;
                    if (layoutParams.g || layoutParams.f) {
                        i10 = paddingRight;
                        i7 = width;
                    } else {
                        i7 = width;
                        if (view.getVisibility() != 8 && (!z3 || !constraintWidget.getResolutionWidth().isResolved() || !constraintWidget.getResolutionHeight().isResolved())) {
                            if (layoutParams.width != -2 || !layoutParams.c) {
                                i11 = MeasureSpec.makeMeasureSpec(constraintWidget.getWidth(), 1073741824);
                            } else {
                                i11 = getChildMeasureSpec(i16, paddingRight, layoutParams.width);
                            }
                            if (layoutParams.height != -2 || !layoutParams.d) {
                                i12 = MeasureSpec.makeMeasureSpec(constraintWidget.getHeight(), 1073741824);
                            } else {
                                i12 = getChildMeasureSpec(i17, paddingBottom, layoutParams.height);
                            }
                            view.measure(i11, i12);
                            if (this.t != null) {
                                Metrics metrics = this.t;
                                i6 = paddingRight;
                                metrics.additionalMeasures++;
                            } else {
                                i6 = paddingRight;
                            }
                            int measuredWidth = view.getMeasuredWidth();
                            int measuredHeight = view.getMeasuredHeight();
                            if (measuredWidth != constraintWidget.getWidth()) {
                                constraintWidget.setWidth(measuredWidth);
                                if (z3) {
                                    constraintWidget.getResolutionWidth().resolve(measuredWidth);
                                }
                                if (z4 && constraintWidget.getRight() > i23) {
                                    i23 = Math.max(i23, constraintWidget.getRight() + constraintWidget.getAnchor(Type.RIGHT).getMargin());
                                }
                                z6 = true;
                            }
                            if (measuredHeight != constraintWidget.getHeight()) {
                                constraintWidget.setHeight(measuredHeight);
                                if (z3) {
                                    constraintWidget.getResolutionHeight().resolve(measuredHeight);
                                }
                                if (z5) {
                                    i15 = max2;
                                    if (constraintWidget.getBottom() > i15) {
                                        i14 = Math.max(i15, constraintWidget.getBottom() + constraintWidget.getAnchor(Type.BOTTOM).getMargin());
                                        i13 = i14;
                                        z6 = true;
                                    }
                                } else {
                                    i15 = max2;
                                }
                                i14 = i15;
                                i13 = i14;
                                z6 = true;
                            } else {
                                i13 = max2;
                            }
                            if (layoutParams.e) {
                                int baseline = view.getBaseline();
                                if (!(baseline == -1 || baseline == constraintWidget.getBaselineDistance())) {
                                    constraintWidget.setBaselineDistance(baseline);
                                    z6 = true;
                                }
                            }
                            if (VERSION.SDK_INT >= 11) {
                                i25 = combineMeasuredStates(i25, view.getMeasuredState());
                            } else {
                                int i26 = i25;
                            }
                            max2 = i13;
                            i24++;
                            size3 = i9;
                            height = i8;
                            width = i7;
                            paddingRight = i6;
                            i16 = i2;
                            i17 = i3;
                        } else {
                            i10 = paddingRight;
                        }
                    }
                }
                max2 = max2;
                i25 = i25;
                i24++;
                size3 = i9;
                height = i8;
                width = i7;
                paddingRight = i6;
                i16 = i2;
                i17 = i3;
            }
            i4 = paddingRight;
            int i27 = width;
            int i28 = height;
            int i29 = size3;
            int i30 = max2;
            i5 = i25;
            if (z6) {
                this.b.setWidth(i27);
                this.b.setHeight(i28);
                if (z3) {
                    this.b.solveGraph();
                }
                solveLinearSystem("2nd pass");
                if (this.b.getWidth() < i23) {
                    this.b.setWidth(i23);
                    z = true;
                } else {
                    z = false;
                }
                if (this.b.getHeight() < i30) {
                    this.b.setHeight(i30);
                    z = true;
                }
                if (z) {
                    solveLinearSystem("3rd pass");
                }
            }
            int i31 = i29;
            for (int i32 = 0; i32 < i31; i32++) {
                ConstraintWidget constraintWidget2 = (ConstraintWidget) this.h.get(i32);
                View view2 = (View) constraintWidget2.getCompanionWidget();
                if (view2 != null && (view2.getMeasuredWidth() != constraintWidget2.getWidth() || view2.getMeasuredHeight() != constraintWidget2.getHeight())) {
                    if (constraintWidget2.getVisibility() != 8) {
                        view2.measure(MeasureSpec.makeMeasureSpec(constraintWidget2.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(constraintWidget2.getHeight(), 1073741824));
                        if (this.t != null) {
                            this.t.additionalMeasures++;
                        }
                    }
                }
            }
        } else {
            i4 = paddingRight;
            i5 = 0;
        }
        int width2 = this.b.getWidth() + i4;
        int height2 = this.b.getHeight() + paddingBottom;
        if (VERSION.SDK_INT >= 11) {
            int resolveSizeAndState = resolveSizeAndState(height2, i3, i5 << 16) & ViewCompat.MEASURED_SIZE_MASK;
            int min = Math.min(this.k, resolveSizeAndState(width2, i2, i5) & ViewCompat.MEASURED_SIZE_MASK);
            int min2 = Math.min(this.l, resolveSizeAndState);
            if (this.b.isWidthMeasuredTooSmall()) {
                min |= 16777216;
            }
            if (this.b.isHeightMeasuredTooSmall()) {
                min2 |= 16777216;
            }
            setMeasuredDimension(min, min2);
            this.r = min;
            this.s = min2;
            return;
        }
        setMeasuredDimension(width2, height2);
        this.r = width2;
        this.s = height2;
    }

    private void c(int i2, int i3) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        int mode2 = MeasureSpec.getMode(i3);
        int size2 = MeasureSpec.getSize(i3);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        getLayoutParams();
        if (mode != Integer.MIN_VALUE) {
            if (mode == 0) {
                dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
            } else if (mode == 1073741824) {
                size = Math.min(this.k, size) - paddingLeft;
            }
            size = 0;
        } else {
            dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
        }
        if (mode2 != Integer.MIN_VALUE) {
            if (mode2 == 0) {
                dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
            } else if (mode2 == 1073741824) {
                size2 = Math.min(this.l, size2) - paddingTop;
            }
            size2 = 0;
        } else {
            dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
        }
        this.b.setMinWidth(0);
        this.b.setMinHeight(0);
        this.b.setHorizontalDimensionBehaviour(dimensionBehaviour);
        this.b.setWidth(size);
        this.b.setVerticalDimensionBehaviour(dimensionBehaviour2);
        this.b.setHeight(size2);
        this.b.setMinWidth((this.i - getPaddingLeft()) - getPaddingRight());
        this.b.setMinHeight((this.j - getPaddingTop()) - getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public void solveLinearSystem(String str) {
        this.b.layout();
        if (this.t != null) {
            this.t.resolutions++;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            ConstraintWidget constraintWidget = layoutParams.s;
            if ((childAt.getVisibility() != 8 || layoutParams.f || layoutParams.g || isInEditMode) && !layoutParams.h) {
                int drawX = constraintWidget.getDrawX();
                int drawY = constraintWidget.getDrawY();
                int width = constraintWidget.getWidth() + drawX;
                int height = constraintWidget.getHeight() + drawY;
                childAt.layout(drawX, drawY, width, height);
                if (childAt instanceof Placeholder) {
                    View content = ((Placeholder) childAt).getContent();
                    if (content != null) {
                        content.setVisibility(0);
                        content.layout(drawX, drawY, width, height);
                    }
                }
            }
        }
        int size = this.g.size();
        if (size > 0) {
            for (int i7 = 0; i7 < size; i7++) {
                ((ConstraintHelper) this.g.get(i7)).updatePostLayout(this);
            }
        }
    }

    public void setOptimizationLevel(int i2) {
        this.b.setOptimizationLevel(i2);
    }

    public int getOptimizationLevel() {
        return this.b.getOptimizationLevel();
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void setConstraintSet(ConstraintSet constraintSet) {
        this.o = constraintSet;
    }

    public View getViewById(int i2) {
        return (View) this.a.get(i2);
    }

    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int childCount = getChildCount();
            float width = (float) getWidth();
            float height = (float) getHeight();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                if (childAt.getVisibility() != 8) {
                    Object tag = childAt.getTag();
                    if (tag != null && (tag instanceof String)) {
                        String[] split = ((String) tag).split(",");
                        if (split.length == 4) {
                            int parseInt = Integer.parseInt(split[0]);
                            int parseInt2 = Integer.parseInt(split[1]);
                            int i3 = (int) ((((float) parseInt) / 1080.0f) * width);
                            int i4 = (int) ((((float) parseInt2) / 1920.0f) * height);
                            int parseInt3 = (int) ((((float) Integer.parseInt(split[2])) / 1080.0f) * width);
                            int parseInt4 = (int) ((((float) Integer.parseInt(split[3])) / 1920.0f) * height);
                            Paint paint = new Paint();
                            paint.setColor(SupportMenu.CATEGORY_MASK);
                            float f2 = (float) i3;
                            float f3 = (float) (i3 + parseInt3);
                            Canvas canvas2 = canvas;
                            float f4 = (float) i4;
                            float f5 = f2;
                            float f6 = f2;
                            float f7 = f4;
                            Paint paint2 = paint;
                            float f8 = f3;
                            Paint paint3 = paint2;
                            canvas2.drawLine(f5, f7, f8, f4, paint3);
                            float f9 = (float) (i4 + parseInt4);
                            float f10 = f3;
                            float f11 = f9;
                            canvas2.drawLine(f10, f7, f8, f11, paint3);
                            float f12 = f9;
                            float f13 = f6;
                            canvas2.drawLine(f10, f12, f13, f11, paint3);
                            float f14 = f6;
                            canvas2.drawLine(f14, f12, f13, f4, paint3);
                            Paint paint4 = paint2;
                            paint4.setColor(-16711936);
                            Paint paint5 = paint4;
                            float f15 = f3;
                            Paint paint6 = paint5;
                            canvas2.drawLine(f14, f4, f15, f9, paint6);
                            canvas2.drawLine(f14, f9, f15, f4, paint6);
                        }
                    }
                }
            }
        }
    }

    public void requestLayout() {
        super.requestLayout();
        this.m = true;
        this.r = -1;
        this.s = -1;
        this.c = -1;
        this.d = -1;
        this.e = 0;
        this.f = 0;
    }
}
