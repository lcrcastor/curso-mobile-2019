package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator extends SimpleItemAnimator {
    private static TimeInterpolator i;
    ArrayList<ArrayList<ViewHolder>> a = new ArrayList<>();
    ArrayList<ArrayList<MoveInfo>> b = new ArrayList<>();
    ArrayList<ArrayList<ChangeInfo>> c = new ArrayList<>();
    ArrayList<ViewHolder> d = new ArrayList<>();
    ArrayList<ViewHolder> e = new ArrayList<>();
    ArrayList<ViewHolder> f = new ArrayList<>();
    ArrayList<ViewHolder> g = new ArrayList<>();
    private ArrayList<ViewHolder> j = new ArrayList<>();
    private ArrayList<ViewHolder> k = new ArrayList<>();
    private ArrayList<MoveInfo> l = new ArrayList<>();
    private ArrayList<ChangeInfo> m = new ArrayList<>();

    static class ChangeInfo {
        public ViewHolder a;
        public ViewHolder b;
        public int c;
        public int d;
        public int e;
        public int f;

        private ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2) {
            this.a = viewHolder;
            this.b = viewHolder2;
        }

        ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
            this(viewHolder, viewHolder2);
            this.c = i;
            this.d = i2;
            this.e = i3;
            this.f = i4;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ChangeInfo{oldHolder=");
            sb.append(this.a);
            sb.append(", newHolder=");
            sb.append(this.b);
            sb.append(", fromX=");
            sb.append(this.c);
            sb.append(", fromY=");
            sb.append(this.d);
            sb.append(", toX=");
            sb.append(this.e);
            sb.append(", toY=");
            sb.append(this.f);
            sb.append('}');
            return sb.toString();
        }
    }

    static class MoveInfo {
        public ViewHolder a;
        public int b;
        public int c;
        public int d;
        public int e;

        MoveInfo(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
            this.a = viewHolder;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
        }
    }

    public void runPendingAnimations() {
        boolean z = !this.j.isEmpty();
        boolean z2 = !this.l.isEmpty();
        boolean z3 = !this.m.isEmpty();
        boolean z4 = !this.k.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator it = this.j.iterator();
            while (it.hasNext()) {
                c((ViewHolder) it.next());
            }
            this.j.clear();
            if (z2) {
                final ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.l);
                this.b.add(arrayList);
                this.l.clear();
                AnonymousClass1 r6 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            MoveInfo moveInfo = (MoveInfo) it.next();
                            DefaultItemAnimator.this.a(moveInfo.a, moveInfo.b, moveInfo.c, moveInfo.d, moveInfo.e);
                        }
                        arrayList.clear();
                        DefaultItemAnimator.this.b.remove(arrayList);
                    }
                };
                if (z) {
                    ViewCompat.postOnAnimationDelayed(((MoveInfo) arrayList.get(0)).a.itemView, r6, getRemoveDuration());
                } else {
                    r6.run();
                }
            }
            if (z3) {
                final ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(this.m);
                this.c.add(arrayList2);
                this.m.clear();
                AnonymousClass2 r62 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            DefaultItemAnimator.this.a((ChangeInfo) it.next());
                        }
                        arrayList2.clear();
                        DefaultItemAnimator.this.c.remove(arrayList2);
                    }
                };
                if (z) {
                    ViewCompat.postOnAnimationDelayed(((ChangeInfo) arrayList2.get(0)).a.itemView, r62, getRemoveDuration());
                } else {
                    r62.run();
                }
            }
            if (z4) {
                final ArrayList arrayList3 = new ArrayList();
                arrayList3.addAll(this.k);
                this.a.add(arrayList3);
                this.k.clear();
                AnonymousClass3 r5 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            DefaultItemAnimator.this.a((ViewHolder) it.next());
                        }
                        arrayList3.clear();
                        DefaultItemAnimator.this.a.remove(arrayList3);
                    }
                };
                if (z || z2 || z3) {
                    long j2 = 0;
                    long removeDuration = z ? getRemoveDuration() : 0;
                    long moveDuration = z2 ? getMoveDuration() : 0;
                    if (z3) {
                        j2 = getChangeDuration();
                    }
                    ViewCompat.postOnAnimationDelayed(((ViewHolder) arrayList3.get(0)).itemView, r5, removeDuration + Math.max(moveDuration, j2));
                } else {
                    r5.run();
                }
            }
        }
    }

    public boolean animateRemove(ViewHolder viewHolder) {
        d(viewHolder);
        this.j.add(viewHolder);
        return true;
    }

    private void c(final ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator animate = view.animate();
        this.f.add(viewHolder);
        animate.setDuration(getRemoveDuration()).alpha(BitmapDescriptorFactory.HUE_RED).setListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                DefaultItemAnimator.this.dispatchRemoveStarting(viewHolder);
            }

            public void onAnimationEnd(Animator animator) {
                animate.setListener(null);
                view.setAlpha(1.0f);
                DefaultItemAnimator.this.dispatchRemoveFinished(viewHolder);
                DefaultItemAnimator.this.f.remove(viewHolder);
                DefaultItemAnimator.this.a();
            }
        }).start();
    }

    public boolean animateAdd(ViewHolder viewHolder) {
        d(viewHolder);
        viewHolder.itemView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.k.add(viewHolder);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void a(final ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator animate = view.animate();
        this.d.add(viewHolder);
        animate.alpha(1.0f).setDuration(getAddDuration()).setListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                DefaultItemAnimator.this.dispatchAddStarting(viewHolder);
            }

            public void onAnimationCancel(Animator animator) {
                view.setAlpha(1.0f);
            }

            public void onAnimationEnd(Animator animator) {
                animate.setListener(null);
                DefaultItemAnimator.this.dispatchAddFinished(viewHolder);
                DefaultItemAnimator.this.d.remove(viewHolder);
                DefaultItemAnimator.this.a();
            }
        }).start();
    }

    public boolean animateMove(ViewHolder viewHolder, int i2, int i3, int i4, int i5) {
        View view = viewHolder.itemView;
        int translationX = i2 + ((int) viewHolder.itemView.getTranslationX());
        int translationY = i3 + ((int) viewHolder.itemView.getTranslationY());
        d(viewHolder);
        int i6 = i4 - translationX;
        int i7 = i5 - translationY;
        if (i6 == 0 && i7 == 0) {
            dispatchMoveFinished(viewHolder);
            return false;
        }
        if (i6 != 0) {
            view.setTranslationX((float) (-i6));
        }
        if (i7 != 0) {
            view.setTranslationY((float) (-i7));
        }
        ArrayList<MoveInfo> arrayList = this.l;
        MoveInfo moveInfo = new MoveInfo(viewHolder, translationX, translationY, i4, i5);
        arrayList.add(moveInfo);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void a(ViewHolder viewHolder, int i2, int i3, int i4, int i5) {
        final View view = viewHolder.itemView;
        final int i6 = i4 - i2;
        final int i7 = i5 - i3;
        if (i6 != 0) {
            view.animate().translationX(BitmapDescriptorFactory.HUE_RED);
        }
        if (i7 != 0) {
            view.animate().translationY(BitmapDescriptorFactory.HUE_RED);
        }
        final ViewPropertyAnimator animate = view.animate();
        this.e.add(viewHolder);
        ViewPropertyAnimator duration = animate.setDuration(getMoveDuration());
        final ViewHolder viewHolder2 = viewHolder;
        AnonymousClass6 r0 = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                DefaultItemAnimator.this.dispatchMoveStarting(viewHolder2);
            }

            public void onAnimationCancel(Animator animator) {
                if (i6 != 0) {
                    view.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                }
                if (i7 != 0) {
                    view.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                }
            }

            public void onAnimationEnd(Animator animator) {
                animate.setListener(null);
                DefaultItemAnimator.this.dispatchMoveFinished(viewHolder2);
                DefaultItemAnimator.this.e.remove(viewHolder2);
                DefaultItemAnimator.this.a();
            }
        };
        duration.setListener(r0).start();
    }

    public boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, int i2, int i3, int i4, int i5) {
        if (viewHolder == viewHolder2) {
            return animateMove(viewHolder, i2, i3, i4, i5);
        }
        float translationX = viewHolder.itemView.getTranslationX();
        float translationY = viewHolder.itemView.getTranslationY();
        float alpha = viewHolder.itemView.getAlpha();
        d(viewHolder);
        int i6 = (int) (((float) (i4 - i2)) - translationX);
        int i7 = (int) (((float) (i5 - i3)) - translationY);
        viewHolder.itemView.setTranslationX(translationX);
        viewHolder.itemView.setTranslationY(translationY);
        viewHolder.itemView.setAlpha(alpha);
        if (viewHolder2 != null) {
            d(viewHolder2);
            viewHolder2.itemView.setTranslationX((float) (-i6));
            viewHolder2.itemView.setTranslationY((float) (-i7));
            viewHolder2.itemView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        }
        ArrayList<ChangeInfo> arrayList = this.m;
        ChangeInfo changeInfo = new ChangeInfo(viewHolder, viewHolder2, i2, i3, i4, i5);
        arrayList.add(changeInfo);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void a(final ChangeInfo changeInfo) {
        final View view;
        ViewHolder viewHolder = changeInfo.a;
        final View view2 = null;
        if (viewHolder == null) {
            view = null;
        } else {
            view = viewHolder.itemView;
        }
        ViewHolder viewHolder2 = changeInfo.b;
        if (viewHolder2 != null) {
            view2 = viewHolder2.itemView;
        }
        if (view != null) {
            final ViewPropertyAnimator duration = view.animate().setDuration(getChangeDuration());
            this.g.add(changeInfo.a);
            duration.translationX((float) (changeInfo.e - changeInfo.c));
            duration.translationY((float) (changeInfo.f - changeInfo.d));
            duration.alpha(BitmapDescriptorFactory.HUE_RED).setListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.a, true);
                }

                public void onAnimationEnd(Animator animator) {
                    duration.setListener(null);
                    view.setAlpha(1.0f);
                    view.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                    view.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.a, true);
                    DefaultItemAnimator.this.g.remove(changeInfo.a);
                    DefaultItemAnimator.this.a();
                }
            }).start();
        }
        if (view2 != null) {
            final ViewPropertyAnimator animate = view2.animate();
            this.g.add(changeInfo.b);
            animate.translationX(BitmapDescriptorFactory.HUE_RED).translationY(BitmapDescriptorFactory.HUE_RED).setDuration(getChangeDuration()).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.b, false);
                }

                public void onAnimationEnd(Animator animator) {
                    animate.setListener(null);
                    view2.setAlpha(1.0f);
                    view2.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                    view2.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.b, false);
                    DefaultItemAnimator.this.g.remove(changeInfo.b);
                    DefaultItemAnimator.this.a();
                }
            }).start();
        }
    }

    private void a(List<ChangeInfo> list, ViewHolder viewHolder) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ChangeInfo changeInfo = (ChangeInfo) list.get(size);
            if (a(changeInfo, viewHolder) && changeInfo.a == null && changeInfo.b == null) {
                list.remove(changeInfo);
            }
        }
    }

    private void b(ChangeInfo changeInfo) {
        if (changeInfo.a != null) {
            a(changeInfo, changeInfo.a);
        }
        if (changeInfo.b != null) {
            a(changeInfo, changeInfo.b);
        }
    }

    private boolean a(ChangeInfo changeInfo, ViewHolder viewHolder) {
        boolean z = false;
        if (changeInfo.b == viewHolder) {
            changeInfo.b = null;
        } else if (changeInfo.a != viewHolder) {
            return false;
        } else {
            changeInfo.a = null;
            z = true;
        }
        viewHolder.itemView.setAlpha(1.0f);
        viewHolder.itemView.setTranslationX(BitmapDescriptorFactory.HUE_RED);
        viewHolder.itemView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        dispatchChangeFinished(viewHolder, z);
        return true;
    }

    public void endAnimation(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        view.animate().cancel();
        int size = this.l.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else if (((MoveInfo) this.l.get(size)).a == viewHolder) {
                view.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                view.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                dispatchMoveFinished(viewHolder);
                this.l.remove(size);
            }
        }
        a((List<ChangeInfo>) this.m, viewHolder);
        if (this.j.remove(viewHolder)) {
            view.setAlpha(1.0f);
            dispatchRemoveFinished(viewHolder);
        }
        if (this.k.remove(viewHolder)) {
            view.setAlpha(1.0f);
            dispatchAddFinished(viewHolder);
        }
        for (int size2 = this.c.size() - 1; size2 >= 0; size2--) {
            ArrayList arrayList = (ArrayList) this.c.get(size2);
            a((List<ChangeInfo>) arrayList, viewHolder);
            if (arrayList.isEmpty()) {
                this.c.remove(size2);
            }
        }
        for (int size3 = this.b.size() - 1; size3 >= 0; size3--) {
            ArrayList arrayList2 = (ArrayList) this.b.get(size3);
            int size4 = arrayList2.size() - 1;
            while (true) {
                if (size4 < 0) {
                    break;
                } else if (((MoveInfo) arrayList2.get(size4)).a == viewHolder) {
                    view.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    view.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                    dispatchMoveFinished(viewHolder);
                    arrayList2.remove(size4);
                    if (arrayList2.isEmpty()) {
                        this.b.remove(size3);
                    }
                } else {
                    size4--;
                }
            }
        }
        for (int size5 = this.a.size() - 1; size5 >= 0; size5--) {
            ArrayList arrayList3 = (ArrayList) this.a.get(size5);
            if (arrayList3.remove(viewHolder)) {
                view.setAlpha(1.0f);
                dispatchAddFinished(viewHolder);
                if (arrayList3.isEmpty()) {
                    this.a.remove(size5);
                }
            }
        }
        this.f.remove(viewHolder);
        this.d.remove(viewHolder);
        this.g.remove(viewHolder);
        this.e.remove(viewHolder);
        a();
    }

    private void d(ViewHolder viewHolder) {
        if (i == null) {
            i = new ValueAnimator().getInterpolator();
        }
        viewHolder.itemView.animate().setInterpolator(i);
        endAnimation(viewHolder);
    }

    public boolean isRunning() {
        return !this.k.isEmpty() || !this.m.isEmpty() || !this.l.isEmpty() || !this.j.isEmpty() || !this.e.isEmpty() || !this.f.isEmpty() || !this.d.isEmpty() || !this.g.isEmpty() || !this.b.isEmpty() || !this.a.isEmpty() || !this.c.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    public void endAnimations() {
        int size = this.l.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            MoveInfo moveInfo = (MoveInfo) this.l.get(size);
            View view = moveInfo.a.itemView;
            view.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            view.setTranslationX(BitmapDescriptorFactory.HUE_RED);
            dispatchMoveFinished(moveInfo.a);
            this.l.remove(size);
        }
        for (int size2 = this.j.size() - 1; size2 >= 0; size2--) {
            dispatchRemoveFinished((ViewHolder) this.j.get(size2));
            this.j.remove(size2);
        }
        int size3 = this.k.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            ViewHolder viewHolder = (ViewHolder) this.k.get(size3);
            viewHolder.itemView.setAlpha(1.0f);
            dispatchAddFinished(viewHolder);
            this.k.remove(size3);
        }
        for (int size4 = this.m.size() - 1; size4 >= 0; size4--) {
            b((ChangeInfo) this.m.get(size4));
        }
        this.m.clear();
        if (isRunning()) {
            for (int size5 = this.b.size() - 1; size5 >= 0; size5--) {
                ArrayList arrayList = (ArrayList) this.b.get(size5);
                for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                    MoveInfo moveInfo2 = (MoveInfo) arrayList.get(size6);
                    View view2 = moveInfo2.a.itemView;
                    view2.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    view2.setTranslationX(BitmapDescriptorFactory.HUE_RED);
                    dispatchMoveFinished(moveInfo2.a);
                    arrayList.remove(size6);
                    if (arrayList.isEmpty()) {
                        this.b.remove(arrayList);
                    }
                }
            }
            for (int size7 = this.a.size() - 1; size7 >= 0; size7--) {
                ArrayList arrayList2 = (ArrayList) this.a.get(size7);
                for (int size8 = arrayList2.size() - 1; size8 >= 0; size8--) {
                    ViewHolder viewHolder2 = (ViewHolder) arrayList2.get(size8);
                    viewHolder2.itemView.setAlpha(1.0f);
                    dispatchAddFinished(viewHolder2);
                    arrayList2.remove(size8);
                    if (arrayList2.isEmpty()) {
                        this.a.remove(arrayList2);
                    }
                }
            }
            for (int size9 = this.c.size() - 1; size9 >= 0; size9--) {
                ArrayList arrayList3 = (ArrayList) this.c.get(size9);
                for (int size10 = arrayList3.size() - 1; size10 >= 0; size10--) {
                    b((ChangeInfo) arrayList3.get(size10));
                    if (arrayList3.isEmpty()) {
                        this.c.remove(arrayList3);
                    }
                }
            }
            a((List<ViewHolder>) this.f);
            a((List<ViewHolder>) this.e);
            a((List<ViewHolder>) this.d);
            a((List<ViewHolder>) this.g);
            dispatchAnimationsFinished();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(List<ViewHolder> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ((ViewHolder) list.get(size)).itemView.animate().cancel();
        }
    }

    public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
        return !list.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, list);
    }
}
