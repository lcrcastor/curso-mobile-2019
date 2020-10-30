package android.support.transition;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class TransitionManager {
    private static Transition a = new AutoTransition();
    private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> d = new ThreadLocal<>();
    /* access modifiers changed from: private */
    public static ArrayList<ViewGroup> e = new ArrayList<>();
    private ArrayMap<Scene, Transition> b = new ArrayMap<>();
    private ArrayMap<Scene, ArrayMap<Scene, Transition>> c = new ArrayMap<>();

    static class MultiListener implements OnAttachStateChangeListener, OnPreDrawListener {
        Transition a;
        ViewGroup b;

        public void onViewAttachedToWindow(View view) {
        }

        MultiListener(Transition transition, ViewGroup viewGroup) {
            this.a = transition;
            this.b = viewGroup;
        }

        private void a() {
            this.b.getViewTreeObserver().removeOnPreDrawListener(this);
            this.b.removeOnAttachStateChangeListener(this);
        }

        public void onViewDetachedFromWindow(View view) {
            a();
            TransitionManager.e.remove(this.b);
            ArrayList arrayList = (ArrayList) TransitionManager.a().get(this.b);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((Transition) it.next()).resume(this.b);
                }
            }
            this.a.a(true);
        }

        public boolean onPreDraw() {
            a();
            if (!TransitionManager.e.remove(this.b)) {
                return true;
            }
            final ArrayMap a2 = TransitionManager.a();
            ArrayList arrayList = (ArrayList) a2.get(this.b);
            ArrayList arrayList2 = null;
            if (arrayList == null) {
                arrayList = new ArrayList();
                a2.put(this.b, arrayList);
            } else if (arrayList.size() > 0) {
                arrayList2 = new ArrayList(arrayList);
            }
            arrayList.add(this.a);
            this.a.addListener(new TransitionListenerAdapter() {
                public void onTransitionEnd(@NonNull Transition transition) {
                    ((ArrayList) a2.get(MultiListener.this.b)).remove(transition);
                }
            });
            this.a.a(this.b, false);
            if (arrayList2 != null) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((Transition) it.next()).resume(this.b);
                }
            }
            this.a.a(this.b);
            return true;
        }
    }

    public void setTransition(@NonNull Scene scene, @Nullable Transition transition) {
        this.b.put(scene, transition);
    }

    public void setTransition(@NonNull Scene scene, @NonNull Scene scene2, @Nullable Transition transition) {
        ArrayMap arrayMap = (ArrayMap) this.c.get(scene2);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            this.c.put(scene2, arrayMap);
        }
        arrayMap.put(scene, transition);
    }

    private Transition a(Scene scene) {
        ViewGroup sceneRoot = scene.getSceneRoot();
        if (sceneRoot != null) {
            Scene a2 = Scene.a(sceneRoot);
            if (a2 != null) {
                ArrayMap arrayMap = (ArrayMap) this.c.get(scene);
                if (arrayMap != null) {
                    Transition transition = (Transition) arrayMap.get(a2);
                    if (transition != null) {
                        return transition;
                    }
                }
            }
        }
        Transition transition2 = (Transition) this.b.get(scene);
        if (transition2 == null) {
            transition2 = a;
        }
        return transition2;
    }

    private static void a(Scene scene, Transition transition) {
        ViewGroup sceneRoot = scene.getSceneRoot();
        if (e.contains(sceneRoot)) {
            return;
        }
        if (transition == null) {
            scene.enter();
            return;
        }
        e.add(sceneRoot);
        Transition clone = transition.clone();
        clone.c(sceneRoot);
        Scene a2 = Scene.a(sceneRoot);
        if (a2 != null && a2.a()) {
            clone.b(true);
        }
        b(sceneRoot, clone);
        scene.enter();
        a(sceneRoot, clone);
    }

    static ArrayMap<ViewGroup, ArrayList<Transition>> a() {
        WeakReference weakReference = (WeakReference) d.get();
        if (weakReference == null || weakReference.get() == null) {
            WeakReference weakReference2 = new WeakReference(new ArrayMap());
            d.set(weakReference2);
            weakReference = weakReference2;
        }
        return (ArrayMap) weakReference.get();
    }

    private static void a(ViewGroup viewGroup, Transition transition) {
        if (transition != null && viewGroup != null) {
            MultiListener multiListener = new MultiListener(transition, viewGroup);
            viewGroup.addOnAttachStateChangeListener(multiListener);
            viewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
        }
    }

    private static void b(ViewGroup viewGroup, Transition transition) {
        ArrayList arrayList = (ArrayList) a().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((Transition) it.next()).pause(viewGroup);
            }
        }
        if (transition != null) {
            transition.a(viewGroup, true);
        }
        Scene a2 = Scene.a(viewGroup);
        if (a2 != null) {
            a2.exit();
        }
    }

    public void transitionTo(@NonNull Scene scene) {
        a(scene, a(scene));
    }

    public static void go(@NonNull Scene scene) {
        a(scene, a);
    }

    public static void go(@NonNull Scene scene, @Nullable Transition transition) {
        a(scene, transition);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup) {
        beginDelayedTransition(viewGroup, null);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup, @Nullable Transition transition) {
        if (!e.contains(viewGroup) && ViewCompat.isLaidOut(viewGroup)) {
            e.add(viewGroup);
            if (transition == null) {
                transition = a;
            }
            Transition clone = transition.clone();
            b(viewGroup, clone);
            Scene.a(viewGroup, null);
            a(viewGroup, clone);
        }
    }

    public static void endTransitions(ViewGroup viewGroup) {
        e.remove(viewGroup);
        ArrayList arrayList = (ArrayList) a().get(viewGroup);
        if (arrayList != null && !arrayList.isEmpty()) {
            ArrayList arrayList2 = new ArrayList(arrayList);
            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                ((Transition) arrayList2.get(size)).b(viewGroup);
            }
        }
    }
}
