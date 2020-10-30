package ar.com.santander.rio.mbanking.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.List;

public class AnimatedExpandableListView extends ExpandableListView {
    private static final String a = AnimatedExpandableListAdapter.class.getSimpleName();
    private AnimatedExpandableListAdapter b;

    public static abstract class AnimatedExpandableListAdapter extends BaseExpandableListAdapter {
        private SparseArray<GroupInfo> a = new SparseArray<>();
        private AnimatedExpandableListView b;

        public int getRealChildType(int i, int i2) {
            return 0;
        }

        public int getRealChildTypeCount() {
            return 1;
        }

        public abstract View getRealChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup);

        public abstract int getRealChildrenCount(int i);

        /* access modifiers changed from: private */
        public void a(AnimatedExpandableListView animatedExpandableListView) {
            this.b = animatedExpandableListView;
        }

        private GroupInfo a(int i) {
            GroupInfo groupInfo = (GroupInfo) this.a.get(i);
            if (groupInfo != null) {
                return groupInfo;
            }
            GroupInfo groupInfo2 = new GroupInfo();
            this.a.put(i, groupInfo2);
            return groupInfo2;
        }

        public void notifyGroupExpanded(int i) {
            a(i).d = -1;
        }

        /* access modifiers changed from: private */
        public void a(int i, int i2) {
            GroupInfo a2 = a(i);
            a2.a = true;
            a2.c = i2;
            a2.b = true;
        }

        /* access modifiers changed from: private */
        public void b(int i, int i2) {
            GroupInfo a2 = a(i);
            a2.a = true;
            a2.c = i2;
            a2.b = false;
        }

        /* access modifiers changed from: private */
        public void b(int i) {
            a(i).a = false;
        }

        public final int getChildType(int i, int i2) {
            if (a(i).a) {
                return 0;
            }
            return getRealChildType(i, i2) + 1;
        }

        public final int getChildTypeCount() {
            return getRealChildTypeCount() + 1;
        }

        /* access modifiers changed from: protected */
        public LayoutParams generateDefaultLayoutParams() {
            return new AbsListView.LayoutParams(-1, -2, 0);
        }

        public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
            int i3;
            int i4;
            GroupInfo a2 = a(i);
            if (a2.a) {
                View view2 = view;
                if (!(view2 instanceof DummyView)) {
                    view2 = new DummyView(viewGroup.getContext());
                    view2.setLayoutParams(new AbsListView.LayoutParams(-1, 0));
                }
                View view3 = view2;
                if (i2 < a2.c) {
                    view3.getLayoutParams().height = 0;
                    return view3;
                }
                ExpandableListView expandableListView = (ExpandableListView) viewGroup;
                final DummyView dummyView = (DummyView) view3;
                dummyView.a();
                dummyView.a(expandableListView.getDivider(), viewGroup.getMeasuredWidth(), expandableListView.getDividerHeight());
                int makeMeasureSpec = MeasureSpec.makeMeasureSpec(viewGroup.getWidth(), 1073741824);
                int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, 0);
                int height = viewGroup.getHeight();
                int realChildrenCount = getRealChildrenCount(i);
                int i5 = a2.c;
                int i6 = 0;
                while (true) {
                    if (i5 >= realChildrenCount) {
                        i3 = 1;
                        i4 = i6;
                        break;
                    }
                    i3 = 1;
                    int i7 = i5;
                    int i8 = realChildrenCount;
                    int i9 = height;
                    View realChildView = getRealChildView(i, i5, i5 == realChildrenCount + -1, null, viewGroup);
                    AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) realChildView.getLayoutParams();
                    if (layoutParams == null) {
                        layoutParams = (AbsListView.LayoutParams) generateDefaultLayoutParams();
                        realChildView.setLayoutParams(layoutParams);
                    }
                    int i10 = layoutParams.height;
                    realChildView.measure(makeMeasureSpec, i10 > 0 ? MeasureSpec.makeMeasureSpec(i10, 1073741824) : makeMeasureSpec2);
                    int measuredHeight = i6 + realChildView.getMeasuredHeight();
                    if (measuredHeight >= i9) {
                        dummyView.a(realChildView);
                        i4 = measuredHeight + (((i8 - i7) - 1) * (measuredHeight / (i7 + 1)));
                        break;
                    }
                    dummyView.a(realChildView);
                    i5 = i7 + 1;
                    i6 = measuredHeight;
                    height = i9;
                    realChildrenCount = i8;
                }
                Object tag = dummyView.getTag();
                int intValue = tag == null ? 0 : ((Integer) tag).intValue();
                if (!a2.b || intValue == i3) {
                    int i11 = i;
                    if (!a2.b && intValue != 2) {
                        if (a2.d == -1) {
                            a2.d = i4;
                        }
                        final GroupInfo groupInfo = a2;
                        ExpandAnimation expandAnimation = new ExpandAnimation(dummyView, a2.d, 0, groupInfo);
                        expandAnimation.setDuration((long) this.b.getAnimationDuration());
                        final int i12 = i11;
                        final ExpandableListView expandableListView2 = expandableListView;
                        final DummyView dummyView2 = dummyView;
                        AnonymousClass2 r0 = new AnimationListener() {
                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationStart(Animation animation) {
                            }

                            public void onAnimationEnd(Animation animation) {
                                AnimatedExpandableListAdapter.this.b(i12);
                                expandableListView2.collapseGroup(i12);
                                AnimatedExpandableListAdapter.this.notifyDataSetChanged();
                                groupInfo.d = -1;
                                dummyView2.setTag(Integer.valueOf(0));
                            }
                        };
                        expandAnimation.setAnimationListener(r0);
                        dummyView.startAnimation(expandAnimation);
                        dummyView.setTag(Integer.valueOf(2));
                    }
                } else {
                    ExpandAnimation expandAnimation2 = new ExpandAnimation(dummyView, 0, i4, a2);
                    expandAnimation2.setDuration((long) this.b.getAnimationDuration());
                    final int i13 = i;
                    expandAnimation2.setAnimationListener(new AnimationListener() {
                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationStart(Animation animation) {
                        }

                        public void onAnimationEnd(Animation animation) {
                            AnimatedExpandableListAdapter.this.b(i13);
                            AnimatedExpandableListAdapter.this.notifyDataSetChanged();
                            dummyView.setTag(Integer.valueOf(0));
                        }
                    });
                    dummyView.startAnimation(expandAnimation2);
                    dummyView.setTag(Integer.valueOf(i3));
                }
                return view3;
            }
            int i14 = i;
            int i15 = i2;
            View view4 = view;
            return getRealChildView(i, i2, z, view, viewGroup);
        }

        public int getChildrenCount(int i) {
            GroupInfo a2 = a(i);
            if (a2.a) {
                return a2.c + 1;
            }
            return getRealChildrenCount(i);
        }
    }

    static class DummyView extends View {
        private List<View> a = new ArrayList();
        private Drawable b;
        private int c;
        private int d;

        public DummyView(Context context) {
            super(context);
        }

        public void a(Drawable drawable, int i, int i2) {
            if (drawable != null) {
                this.b = drawable;
                this.c = i;
                this.d = i2;
                drawable.setBounds(0, 0, i, i2);
            }
        }

        public void a(View view) {
            view.layout(0, 0, getWidth(), view.getMeasuredHeight());
            this.a.add(view);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            int size = this.a.size();
            for (int i5 = 0; i5 < size; i5++) {
                View view = (View) this.a.get(i5);
                view.layout(i, i2, view.getMeasuredWidth() + i, view.getMeasuredHeight() + i2);
            }
        }

        public void a() {
            this.a.clear();
        }

        public void dispatchDraw(Canvas canvas) {
            canvas.save();
            if (this.b != null) {
                this.b.setBounds(0, 0, this.c, this.d);
            }
            int size = this.a.size();
            for (int i = 0; i < size; i++) {
                View view = (View) this.a.get(i);
                canvas.save();
                canvas.clipRect(0, 0, getWidth(), view.getMeasuredHeight());
                view.draw(canvas);
                canvas.restore();
                if (this.b != null) {
                    this.b.draw(canvas);
                    canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) this.d);
                }
                canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) view.getMeasuredHeight());
            }
            canvas.restore();
        }
    }

    static class ExpandAnimation extends Animation {
        private int a;
        private int b;
        private View c;
        private GroupInfo d;

        private ExpandAnimation(View view, int i, int i2, GroupInfo groupInfo) {
            this.a = i;
            this.b = i2 - i;
            this.c = view;
            this.d = groupInfo;
            this.c.getLayoutParams().height = i;
            this.c.requestLayout();
        }

        /* access modifiers changed from: protected */
        public void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            if (f < 1.0f) {
                int i = this.a + ((int) (((float) this.b) * f));
                this.c.getLayoutParams().height = i;
                this.d.d = i;
                this.c.requestLayout();
                return;
            }
            int i2 = this.a + this.b;
            this.c.getLayoutParams().height = i2;
            this.d.d = i2;
            this.c.requestLayout();
        }
    }

    static class GroupInfo {
        boolean a;
        boolean b;
        int c;
        int d;

        private GroupInfo() {
            this.a = false;
            this.b = false;
            this.d = -1;
        }
    }

    /* access modifiers changed from: private */
    public int getAnimationDuration() {
        return HttpStatus.SC_MULTIPLE_CHOICES;
    }

    public AnimatedExpandableListView(Context context) {
        super(context);
    }

    public AnimatedExpandableListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AnimatedExpandableListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setAdapter(ExpandableListAdapter expandableListAdapter) {
        super.setAdapter(expandableListAdapter);
        if (expandableListAdapter instanceof AnimatedExpandableListAdapter) {
            this.b = (AnimatedExpandableListAdapter) expandableListAdapter;
            this.b.a(this);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(expandableListAdapter.toString());
        sb.append(" must implement AnimatedExpandableListAdapter");
        throw new ClassCastException(sb.toString());
    }

    @SuppressLint({"NewApi"})
    public boolean expandGroupWithAnimation(int i) {
        if ((i == this.b.getGroupCount() - 1) && VERSION.SDK_INT >= 14) {
            return expandGroup(i, true);
        }
        int flatListPosition = getFlatListPosition(getPackedPositionForGroup(i));
        if (flatListPosition != -1) {
            int firstVisiblePosition = flatListPosition - getFirstVisiblePosition();
            if (firstVisiblePosition < getChildCount() && getChildAt(firstVisiblePosition).getBottom() >= getBottom()) {
                this.b.notifyGroupExpanded(i);
                return expandGroup(i);
            }
        }
        this.b.a(i, 0);
        return expandGroup(i);
    }

    public boolean collapseGroupWithAnimation(int i) {
        int flatListPosition = getFlatListPosition(getPackedPositionForGroup(i));
        if (flatListPosition != -1) {
            int firstVisiblePosition = flatListPosition - getFirstVisiblePosition();
            if (firstVisiblePosition < 0 || firstVisiblePosition >= getChildCount()) {
                return collapseGroup(i);
            }
            if (getChildAt(firstVisiblePosition).getBottom() >= getBottom()) {
                return collapseGroup(i);
            }
        }
        long expandableListPosition = getExpandableListPosition(getFirstVisiblePosition());
        int packedPositionChild = getPackedPositionChild(expandableListPosition);
        int packedPositionGroup = getPackedPositionGroup(expandableListPosition);
        if (packedPositionChild == -1 || packedPositionGroup != i) {
            packedPositionChild = 0;
        }
        this.b.b(i, packedPositionChild);
        this.b.notifyDataSetChanged();
        return isGroupExpanded(i);
    }
}
