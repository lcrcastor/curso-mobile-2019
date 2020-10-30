package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class AnimatorSet extends Animator {
    boolean b = false;
    /* access modifiers changed from: private */
    public ArrayList<Animator> c = new ArrayList<>();
    /* access modifiers changed from: private */
    public HashMap<Animator, Node> d = new HashMap<>();
    /* access modifiers changed from: private */
    public ArrayList<Node> e = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<Node> f = new ArrayList<>();
    private boolean g = true;
    private AnimatorSetListener h = null;
    /* access modifiers changed from: private */
    public boolean i = false;
    private long j = 0;
    private ValueAnimator k = null;
    private long l = -1;

    class AnimatorSetListener implements AnimatorListener {
        private AnimatorSet b;

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        AnimatorSetListener(AnimatorSet animatorSet) {
            this.b = animatorSet;
        }

        public void onAnimationCancel(Animator animator) {
            if (!AnimatorSet.this.b && AnimatorSet.this.c.size() == 0 && AnimatorSet.this.a != null) {
                int size = AnimatorSet.this.a.size();
                for (int i = 0; i < size; i++) {
                    ((AnimatorListener) AnimatorSet.this.a.get(i)).onAnimationCancel(this.b);
                }
            }
        }

        public void onAnimationEnd(Animator animator) {
            animator.removeListener(this);
            AnimatorSet.this.c.remove(animator);
            boolean z = true;
            ((Node) this.b.d.get(animator)).f = true;
            if (!AnimatorSet.this.b) {
                ArrayList c = this.b.f;
                int size = c.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    } else if (!((Node) c.get(i)).f) {
                        z = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (z) {
                    if (AnimatorSet.this.a != null) {
                        ArrayList arrayList = (ArrayList) AnimatorSet.this.a.clone();
                        int size2 = arrayList.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            ((AnimatorListener) arrayList.get(i2)).onAnimationEnd(this.b);
                        }
                    }
                    this.b.i = false;
                }
            }
        }
    }

    public class Builder {
        private Node b;

        Builder(Animator animator) {
            this.b = (Node) AnimatorSet.this.d.get(animator);
            if (this.b == null) {
                this.b = new Node(animator);
                AnimatorSet.this.d.put(animator, this.b);
                AnimatorSet.this.e.add(this.b);
            }
        }

        public Builder with(Animator animator) {
            Node node = (Node) AnimatorSet.this.d.get(animator);
            if (node == null) {
                node = new Node(animator);
                AnimatorSet.this.d.put(animator, node);
                AnimatorSet.this.e.add(node);
            }
            node.a(new Dependency(this.b, 0));
            return this;
        }

        public Builder before(Animator animator) {
            Node node = (Node) AnimatorSet.this.d.get(animator);
            if (node == null) {
                node = new Node(animator);
                AnimatorSet.this.d.put(animator, node);
                AnimatorSet.this.e.add(node);
            }
            node.a(new Dependency(this.b, 1));
            return this;
        }

        public Builder after(Animator animator) {
            Node node = (Node) AnimatorSet.this.d.get(animator);
            if (node == null) {
                node = new Node(animator);
                AnimatorSet.this.d.put(animator, node);
                AnimatorSet.this.e.add(node);
            }
            this.b.a(new Dependency(node, 1));
            return this;
        }

        public Builder after(long j) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(BitmapDescriptorFactory.HUE_RED, 1.0f);
            ofFloat.setDuration(j);
            after((Animator) ofFloat);
            return this;
        }
    }

    static class Dependency {
        public Node a;
        public int b;

        public Dependency(Node node, int i) {
            this.a = node;
            this.b = i;
        }
    }

    static class DependencyListener implements AnimatorListener {
        private AnimatorSet a;
        private Node b;
        private int c;

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public DependencyListener(AnimatorSet animatorSet, Node node, int i) {
            this.a = animatorSet;
            this.b = node;
            this.c = i;
        }

        public void onAnimationEnd(Animator animator) {
            if (this.c == 1) {
                a(animator);
            }
        }

        public void onAnimationStart(Animator animator) {
            if (this.c == 0) {
                a(animator);
            }
        }

        private void a(Animator animator) {
            if (!this.a.b) {
                Dependency dependency = null;
                int size = this.b.c.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    Dependency dependency2 = (Dependency) this.b.c.get(i);
                    if (dependency2.b == this.c && dependency2.a.a == animator) {
                        animator.removeListener(this);
                        dependency = dependency2;
                        break;
                    }
                    i++;
                }
                this.b.c.remove(dependency);
                if (this.b.c.size() == 0) {
                    this.b.a.start();
                    this.a.c.add(this.b.a);
                }
            }
        }
    }

    static class Node implements Cloneable {
        public Animator a;
        public ArrayList<Dependency> b = null;
        public ArrayList<Dependency> c = null;
        public ArrayList<Node> d = null;
        public ArrayList<Node> e = null;
        public boolean f = false;

        public Node(Animator animator) {
            this.a = animator;
        }

        public void a(Dependency dependency) {
            if (this.b == null) {
                this.b = new ArrayList<>();
                this.d = new ArrayList<>();
            }
            this.b.add(dependency);
            if (!this.d.contains(dependency.a)) {
                this.d.add(dependency.a);
            }
            Node node = dependency.a;
            if (node.e == null) {
                node.e = new ArrayList<>();
            }
            node.e.add(this);
        }

        /* renamed from: a */
        public Node clone() {
            try {
                Node node = (Node) super.clone();
                node.a = this.a.clone();
                return node;
            } catch (CloneNotSupportedException unused) {
                throw new AssertionError();
            }
        }
    }

    public void playTogether(Animator... animatorArr) {
        if (animatorArr != null) {
            this.g = true;
            Builder play = play(animatorArr[0]);
            for (int i2 = 1; i2 < animatorArr.length; i2++) {
                play.with(animatorArr[i2]);
            }
        }
    }

    public void playTogether(Collection<Animator> collection) {
        if (collection != null && collection.size() > 0) {
            this.g = true;
            Builder builder = null;
            for (Animator animator : collection) {
                if (builder == null) {
                    builder = play(animator);
                } else {
                    builder.with(animator);
                }
            }
        }
    }

    public void playSequentially(Animator... animatorArr) {
        if (animatorArr != null) {
            this.g = true;
            int i2 = 0;
            if (animatorArr.length == 1) {
                play(animatorArr[0]);
                return;
            }
            while (i2 < animatorArr.length - 1) {
                i2++;
                play(animatorArr[i2]).before(animatorArr[i2]);
            }
        }
    }

    public void playSequentially(List<Animator> list) {
        if (list != null && list.size() > 0) {
            this.g = true;
            int i2 = 0;
            if (list.size() == 1) {
                play((Animator) list.get(0));
                return;
            }
            while (i2 < list.size() - 1) {
                i2++;
                play((Animator) list.get(i2)).before((Animator) list.get(i2));
            }
        }
    }

    public ArrayList<Animator> getChildAnimations() {
        ArrayList<Animator> arrayList = new ArrayList<>();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            arrayList.add(((Node) it.next()).a);
        }
        return arrayList;
    }

    public void setTarget(Object obj) {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            Animator animator = ((Node) it.next()).a;
            if (animator instanceof AnimatorSet) {
                ((AnimatorSet) animator).setTarget(obj);
            } else if (animator instanceof ObjectAnimator) {
                ((ObjectAnimator) animator).setTarget(obj);
            }
        }
    }

    public void setInterpolator(Interpolator interpolator) {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((Node) it.next()).a.setInterpolator(interpolator);
        }
    }

    public Builder play(Animator animator) {
        if (animator == null) {
            return null;
        }
        this.g = true;
        return new Builder(animator);
    }

    public void cancel() {
        this.b = true;
        if (isStarted()) {
            ArrayList arrayList = null;
            if (this.a != null) {
                arrayList = (ArrayList) this.a.clone();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((AnimatorListener) it.next()).onAnimationCancel(this);
                }
            }
            if (this.k != null && this.k.isRunning()) {
                this.k.cancel();
            } else if (this.f.size() > 0) {
                Iterator it2 = this.f.iterator();
                while (it2.hasNext()) {
                    ((Node) it2.next()).a.cancel();
                }
            }
            if (arrayList != null) {
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    ((AnimatorListener) it3.next()).onAnimationEnd(this);
                }
            }
            this.i = false;
        }
    }

    public void end() {
        this.b = true;
        if (isStarted()) {
            if (this.f.size() != this.e.size()) {
                a();
                Iterator it = this.f.iterator();
                while (it.hasNext()) {
                    Node node = (Node) it.next();
                    if (this.h == null) {
                        this.h = new AnimatorSetListener(this);
                    }
                    node.a.addListener(this.h);
                }
            }
            if (this.k != null) {
                this.k.cancel();
            }
            if (this.f.size() > 0) {
                Iterator it2 = this.f.iterator();
                while (it2.hasNext()) {
                    ((Node) it2.next()).a.end();
                }
            }
            if (this.a != null) {
                Iterator it3 = ((ArrayList) this.a.clone()).iterator();
                while (it3.hasNext()) {
                    ((AnimatorListener) it3.next()).onAnimationEnd(this);
                }
            }
            this.i = false;
        }
    }

    public boolean isRunning() {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            if (((Node) it.next()).a.isRunning()) {
                return true;
            }
        }
        return false;
    }

    public boolean isStarted() {
        return this.i;
    }

    public long getStartDelay() {
        return this.j;
    }

    public void setStartDelay(long j2) {
        this.j = j2;
    }

    public long getDuration() {
        return this.l;
    }

    public AnimatorSet setDuration(long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException("duration must be a value of zero or greater");
        }
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((Node) it.next()).a.setDuration(j2);
        }
        this.l = j2;
        return this;
    }

    public void setupStartValues() {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((Node) it.next()).a.setupStartValues();
        }
    }

    public void setupEndValues() {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((Node) it.next()).a.setupEndValues();
        }
    }

    public void start() {
        this.b = false;
        this.i = true;
        a();
        int size = this.f.size();
        for (int i2 = 0; i2 < size; i2++) {
            Node node = (Node) this.f.get(i2);
            ArrayList listeners = node.a.getListeners();
            if (listeners != null && listeners.size() > 0) {
                Iterator it = new ArrayList(listeners).iterator();
                while (it.hasNext()) {
                    AnimatorListener animatorListener = (AnimatorListener) it.next();
                    if ((animatorListener instanceof DependencyListener) || (animatorListener instanceof AnimatorSetListener)) {
                        node.a.removeListener(animatorListener);
                    }
                }
            }
        }
        final ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < size; i3++) {
            Node node2 = (Node) this.f.get(i3);
            if (this.h == null) {
                this.h = new AnimatorSetListener(this);
            }
            if (node2.b == null || node2.b.size() == 0) {
                arrayList.add(node2);
            } else {
                int size2 = node2.b.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    Dependency dependency = (Dependency) node2.b.get(i4);
                    dependency.a.a.addListener(new DependencyListener(this, node2, dependency.b));
                }
                node2.c = (ArrayList) node2.b.clone();
            }
            node2.a.addListener(this.h);
        }
        if (this.j <= 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Node node3 = (Node) it2.next();
                node3.a.start();
                this.c.add(node3.a);
            }
        } else {
            this.k = ValueAnimator.ofFloat(BitmapDescriptorFactory.HUE_RED, 1.0f);
            this.k.setDuration(this.j);
            this.k.addListener(new AnimatorListenerAdapter() {
                boolean a = false;

                public void onAnimationCancel(Animator animator) {
                    this.a = true;
                }

                public void onAnimationEnd(Animator animator) {
                    if (!this.a) {
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            Node node = (Node) arrayList.get(i);
                            node.a.start();
                            AnimatorSet.this.c.add(node.a);
                        }
                    }
                }
            });
            this.k.start();
        }
        if (this.a != null) {
            ArrayList arrayList2 = (ArrayList) this.a.clone();
            int size3 = arrayList2.size();
            for (int i5 = 0; i5 < size3; i5++) {
                ((AnimatorListener) arrayList2.get(i5)).onAnimationStart(this);
            }
        }
        if (this.e.size() == 0 && this.j == 0) {
            this.i = false;
            if (this.a != null) {
                ArrayList arrayList3 = (ArrayList) this.a.clone();
                int size4 = arrayList3.size();
                for (int i6 = 0; i6 < size4; i6++) {
                    ((AnimatorListener) arrayList3.get(i6)).onAnimationEnd(this);
                }
            }
        }
    }

    public AnimatorSet clone() {
        AnimatorSet animatorSet = (AnimatorSet) super.clone();
        animatorSet.g = true;
        animatorSet.b = false;
        animatorSet.i = false;
        animatorSet.c = new ArrayList<>();
        animatorSet.d = new HashMap<>();
        animatorSet.e = new ArrayList<>();
        animatorSet.f = new ArrayList<>();
        HashMap hashMap = new HashMap();
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            Node node = (Node) it.next();
            Node a = node.clone();
            hashMap.put(node, a);
            animatorSet.e.add(a);
            animatorSet.d.put(a.a, a);
            ArrayList arrayList = null;
            a.b = null;
            a.c = null;
            a.e = null;
            a.d = null;
            ArrayList listeners = a.a.getListeners();
            if (listeners != null) {
                Iterator it2 = listeners.iterator();
                while (it2.hasNext()) {
                    AnimatorListener animatorListener = (AnimatorListener) it2.next();
                    if (animatorListener instanceof AnimatorSetListener) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(animatorListener);
                    }
                }
                if (arrayList != null) {
                    Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        listeners.remove((AnimatorListener) it3.next());
                    }
                }
            }
        }
        Iterator it4 = this.e.iterator();
        while (it4.hasNext()) {
            Node node2 = (Node) it4.next();
            Node node3 = (Node) hashMap.get(node2);
            if (node2.b != null) {
                Iterator it5 = node2.b.iterator();
                while (it5.hasNext()) {
                    Dependency dependency = (Dependency) it5.next();
                    node3.a(new Dependency((Node) hashMap.get(dependency.a), dependency.b));
                }
            }
        }
        return animatorSet;
    }

    private void a() {
        if (this.g) {
            this.f.clear();
            ArrayList arrayList = new ArrayList();
            int size = this.e.size();
            for (int i2 = 0; i2 < size; i2++) {
                Node node = (Node) this.e.get(i2);
                if (node.b == null || node.b.size() == 0) {
                    arrayList.add(node);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            while (arrayList.size() > 0) {
                int size2 = arrayList.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    Node node2 = (Node) arrayList.get(i3);
                    this.f.add(node2);
                    if (node2.e != null) {
                        int size3 = node2.e.size();
                        for (int i4 = 0; i4 < size3; i4++) {
                            Node node3 = (Node) node2.e.get(i4);
                            node3.d.remove(node2);
                            if (node3.d.size() == 0) {
                                arrayList2.add(node3);
                            }
                        }
                    }
                }
                arrayList.clear();
                arrayList.addAll(arrayList2);
                arrayList2.clear();
            }
            this.g = false;
            if (this.f.size() != this.e.size()) {
                throw new IllegalStateException("Circular dependencies cannot exist in AnimatorSet");
            }
            return;
        }
        int size4 = this.e.size();
        for (int i5 = 0; i5 < size4; i5++) {
            Node node4 = (Node) this.e.get(i5);
            if (node4.b != null && node4.b.size() > 0) {
                int size5 = node4.b.size();
                for (int i6 = 0; i6 < size5; i6++) {
                    Dependency dependency = (Dependency) node4.b.get(i6);
                    if (node4.d == null) {
                        node4.d = new ArrayList<>();
                    }
                    if (!node4.d.contains(dependency.a)) {
                        node4.d.add(dependency.a);
                    }
                }
            }
            node4.f = false;
        }
    }
}
