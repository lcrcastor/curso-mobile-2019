package android.support.v4.app;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class FragmentTransition {
    private static final int[] a = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};
    private static final FragmentTransitionImpl b = (VERSION.SDK_INT >= 21 ? new FragmentTransitionCompat21() : null);
    private static final FragmentTransitionImpl c = b();

    static class FragmentContainerTransition {
        public Fragment a;
        public boolean b;
        public BackStackRecord c;
        public Fragment d;
        public boolean e;
        public BackStackRecord f;

        FragmentContainerTransition() {
        }
    }

    FragmentTransition() {
    }

    private static FragmentTransitionImpl b() {
        try {
            return (FragmentTransitionImpl) Class.forName("android.support.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    static void a(FragmentManagerImpl fragmentManagerImpl, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z) {
        if (fragmentManagerImpl.l >= 1) {
            SparseArray sparseArray = new SparseArray();
            for (int i3 = i; i3 < i2; i3++) {
                BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i3);
                if (((Boolean) arrayList2.get(i3)).booleanValue()) {
                    b(backStackRecord, sparseArray, z);
                } else {
                    a(backStackRecord, sparseArray, z);
                }
            }
            if (sparseArray.size() != 0) {
                View view = new View(fragmentManagerImpl.m.c());
                int size = sparseArray.size();
                for (int i4 = 0; i4 < size; i4++) {
                    int keyAt = sparseArray.keyAt(i4);
                    ArrayMap a2 = a(keyAt, arrayList, arrayList2, i, i2);
                    FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) sparseArray.valueAt(i4);
                    if (z) {
                        a(fragmentManagerImpl, keyAt, fragmentContainerTransition, view, a2);
                    } else {
                        b(fragmentManagerImpl, keyAt, fragmentContainerTransition, view, a2);
                    }
                }
            }
        }
    }

    private static ArrayMap<String, String> a(int i, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList<String> arrayList3;
        ArrayList arrayList4;
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i4);
            if (backStackRecord.b(i)) {
                boolean booleanValue = ((Boolean) arrayList2.get(i4)).booleanValue();
                if (backStackRecord.r != null) {
                    int size = backStackRecord.r.size();
                    if (booleanValue) {
                        arrayList3 = backStackRecord.r;
                        arrayList4 = backStackRecord.s;
                    } else {
                        ArrayList arrayList5 = backStackRecord.r;
                        arrayList3 = backStackRecord.s;
                        arrayList4 = arrayList5;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = (String) arrayList4.get(i5);
                        String str2 = (String) arrayList3.get(i5);
                        String str3 = (String) arrayMap.remove(str2);
                        if (str3 != null) {
                            arrayMap.put(str, str3);
                        } else {
                            arrayMap.put(str, str2);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    private static void a(FragmentManagerImpl fragmentManagerImpl, int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap) {
        Object obj;
        FragmentManagerImpl fragmentManagerImpl2 = fragmentManagerImpl;
        FragmentContainerTransition fragmentContainerTransition2 = fragmentContainerTransition;
        View view2 = view;
        ViewGroup viewGroup = fragmentManagerImpl2.n.onHasView() ? (ViewGroup) fragmentManagerImpl2.n.onFindViewById(i) : null;
        if (viewGroup != null) {
            Fragment fragment = fragmentContainerTransition2.a;
            Fragment fragment2 = fragmentContainerTransition2.d;
            FragmentTransitionImpl a2 = a(fragment2, fragment);
            if (a2 != null) {
                boolean z = fragmentContainerTransition2.b;
                boolean z2 = fragmentContainerTransition2.e;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                Object a3 = a(a2, fragment, z);
                Object b2 = b(a2, fragment2, z2);
                Object obj2 = a3;
                ViewGroup viewGroup2 = viewGroup;
                ArrayList arrayList3 = arrayList2;
                Object a4 = a(a2, viewGroup, view2, arrayMap, fragmentContainerTransition2, arrayList2, arrayList, a3, b2);
                Object obj3 = obj2;
                if (obj3 == null && a4 == null) {
                    obj = b2;
                    if (obj == null) {
                        return;
                    }
                } else {
                    obj = b2;
                }
                ArrayList b3 = b(a2, obj, fragment2, arrayList3, view2);
                ArrayList b4 = b(a2, obj3, fragment, arrayList, view2);
                b(b4, 4);
                Fragment fragment3 = fragment;
                ArrayList arrayList4 = b3;
                Object a5 = a(a2, obj3, obj, a4, fragment3, z);
                if (a5 != null) {
                    a(a2, obj, fragment2, arrayList4);
                    ArrayList a6 = a2.a(arrayList);
                    a2.scheduleRemoveTargets(a5, obj3, b4, obj, arrayList4, a4, arrayList);
                    ViewGroup viewGroup3 = viewGroup2;
                    a2.beginDelayedTransition(viewGroup3, a5);
                    a2.a(viewGroup3, arrayList3, arrayList, a6, arrayMap);
                    b(b4, 0);
                    a2.swapSharedElementTargets(a4, arrayList3, arrayList);
                }
            }
        }
    }

    private static void a(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment != null && obj != null && fragment.t && fragment.J && fragment.Y) {
            fragment.d(true);
            fragmentTransitionImpl.scheduleHideFragmentView(obj, fragment.getView(), arrayList);
            OneShotPreDrawListener.a(fragment.Q, new Runnable() {
                public void run() {
                    FragmentTransition.b(arrayList, 4);
                }
            });
        }
    }

    private static void b(FragmentManagerImpl fragmentManagerImpl, int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap) {
        Object obj;
        FragmentManagerImpl fragmentManagerImpl2 = fragmentManagerImpl;
        FragmentContainerTransition fragmentContainerTransition2 = fragmentContainerTransition;
        View view2 = view;
        ArrayMap<String, String> arrayMap2 = arrayMap;
        ViewGroup viewGroup = fragmentManagerImpl2.n.onHasView() ? (ViewGroup) fragmentManagerImpl2.n.onFindViewById(i) : null;
        if (viewGroup != null) {
            Fragment fragment = fragmentContainerTransition2.a;
            Fragment fragment2 = fragmentContainerTransition2.d;
            FragmentTransitionImpl a2 = a(fragment2, fragment);
            if (a2 != null) {
                boolean z = fragmentContainerTransition2.b;
                boolean z2 = fragmentContainerTransition2.e;
                Object a3 = a(a2, fragment, z);
                Object b2 = b(a2, fragment2, z2);
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = arrayList;
                Object obj2 = b2;
                Object obj3 = a3;
                FragmentTransitionImpl fragmentTransitionImpl = a2;
                Object b3 = b(a2, viewGroup, view2, arrayMap2, fragmentContainerTransition2, arrayList, arrayList2, a3, obj2);
                Object obj4 = obj3;
                if (obj4 == null && b3 == null) {
                    obj = obj2;
                    if (obj == null) {
                        return;
                    }
                } else {
                    obj = obj2;
                }
                ArrayList b4 = b(fragmentTransitionImpl, obj, fragment2, arrayList3, view2);
                Object obj5 = (b4 == null || b4.isEmpty()) ? null : obj;
                fragmentTransitionImpl.addTarget(obj4, view2);
                Object a4 = a(fragmentTransitionImpl, obj4, obj5, b3, fragment, fragmentContainerTransition2.b);
                if (a4 != null) {
                    ArrayList arrayList4 = new ArrayList();
                    FragmentTransitionImpl fragmentTransitionImpl2 = fragmentTransitionImpl;
                    ArrayList arrayList5 = arrayList4;
                    fragmentTransitionImpl2.scheduleRemoveTargets(a4, obj4, arrayList4, obj5, b4, b3, arrayList2);
                    a(fragmentTransitionImpl2, viewGroup, fragment, view2, arrayList2, obj4, arrayList5, obj5, b4);
                    ArrayList arrayList6 = arrayList2;
                    fragmentTransitionImpl.a((View) viewGroup, arrayList6, (Map<String, String>) arrayMap2);
                    fragmentTransitionImpl.beginDelayedTransition(viewGroup, a4);
                    fragmentTransitionImpl.a(viewGroup, arrayList6, (Map<String, String>) arrayMap2);
                }
            }
        }
    }

    private static void a(FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, Fragment fragment, View view, ArrayList<View> arrayList, Object obj, ArrayList<View> arrayList2, Object obj2, ArrayList<View> arrayList3) {
        final Object obj3 = obj;
        final FragmentTransitionImpl fragmentTransitionImpl2 = fragmentTransitionImpl;
        final View view2 = view;
        final Fragment fragment2 = fragment;
        final ArrayList<View> arrayList4 = arrayList;
        final ArrayList<View> arrayList5 = arrayList2;
        final ArrayList<View> arrayList6 = arrayList3;
        final Object obj4 = obj2;
        AnonymousClass2 r0 = new Runnable() {
            public void run() {
                if (obj3 != null) {
                    fragmentTransitionImpl2.removeTarget(obj3, view2);
                    arrayList5.addAll(FragmentTransition.b(fragmentTransitionImpl2, obj3, fragment2, arrayList4, view2));
                }
                if (arrayList6 != null) {
                    if (obj4 != null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(view2);
                        fragmentTransitionImpl2.replaceTargets(obj4, arrayList6, arrayList);
                    }
                    arrayList6.clear();
                    arrayList6.add(view2);
                }
            }
        };
        OneShotPreDrawListener.a(viewGroup, r0);
    }

    private static FragmentTransitionImpl a(Fragment fragment, Fragment fragment2) {
        ArrayList arrayList = new ArrayList();
        if (fragment != null) {
            Object exitTransition = fragment.getExitTransition();
            if (exitTransition != null) {
                arrayList.add(exitTransition);
            }
            Object returnTransition = fragment.getReturnTransition();
            if (returnTransition != null) {
                arrayList.add(returnTransition);
            }
            Object sharedElementReturnTransition = fragment.getSharedElementReturnTransition();
            if (sharedElementReturnTransition != null) {
                arrayList.add(sharedElementReturnTransition);
            }
        }
        if (fragment2 != null) {
            Object enterTransition = fragment2.getEnterTransition();
            if (enterTransition != null) {
                arrayList.add(enterTransition);
            }
            Object reenterTransition = fragment2.getReenterTransition();
            if (reenterTransition != null) {
                arrayList.add(reenterTransition);
            }
            Object sharedElementEnterTransition = fragment2.getSharedElementEnterTransition();
            if (sharedElementEnterTransition != null) {
                arrayList.add(sharedElementEnterTransition);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        if (b != null && a(b, (List<Object>) arrayList)) {
            return b;
        }
        if (c != null && a(c, (List<Object>) arrayList)) {
            return c;
        }
        if (b == null && c == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static boolean a(FragmentTransitionImpl fragmentTransitionImpl, List<Object> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!fragmentTransitionImpl.canHandle(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static Object a(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, Fragment fragment2, boolean z) {
        Object obj;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        if (z) {
            obj = fragment2.getSharedElementReturnTransition();
        } else {
            obj = fragment.getSharedElementEnterTransition();
        }
        return fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(obj));
    }

    private static Object a(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, boolean z) {
        Object obj;
        if (fragment == null) {
            return null;
        }
        if (z) {
            obj = fragment.getReenterTransition();
        } else {
            obj = fragment.getEnterTransition();
        }
        return fragmentTransitionImpl.cloneTransition(obj);
    }

    private static Object b(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, boolean z) {
        Object obj;
        if (fragment == null) {
            return null;
        }
        if (z) {
            obj = fragment.getReturnTransition();
        } else {
            obj = fragment.getExitTransition();
        }
        return fragmentTransitionImpl.cloneTransition(obj);
    }

    private static Object a(FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        Object obj4;
        final Rect rect;
        final View view2;
        final FragmentTransitionImpl fragmentTransitionImpl2 = fragmentTransitionImpl;
        View view3 = view;
        ArrayMap<String, String> arrayMap2 = arrayMap;
        FragmentContainerTransition fragmentContainerTransition2 = fragmentContainerTransition;
        ArrayList<View> arrayList3 = arrayList;
        ArrayList<View> arrayList4 = arrayList2;
        Object obj5 = obj;
        Fragment fragment = fragmentContainerTransition2.a;
        Fragment fragment2 = fragmentContainerTransition2.d;
        if (fragment != null) {
            fragment.getView().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        boolean z = fragmentContainerTransition2.b;
        if (arrayMap.isEmpty()) {
            obj3 = null;
        } else {
            obj3 = a(fragmentTransitionImpl2, fragment, fragment2, z);
        }
        ArrayMap b2 = b(fragmentTransitionImpl2, arrayMap2, obj3, fragmentContainerTransition2);
        ArrayMap c2 = c(fragmentTransitionImpl2, arrayMap2, obj3, fragmentContainerTransition2);
        if (arrayMap.isEmpty()) {
            if (b2 != null) {
                b2.clear();
            }
            if (c2 != null) {
                c2.clear();
            }
            obj4 = null;
        } else {
            a(arrayList3, b2, (Collection<String>) arrayMap.keySet());
            a(arrayList4, c2, arrayMap.values());
            obj4 = obj3;
        }
        if (obj5 == null && obj2 == null && obj4 == null) {
            return null;
        }
        b(fragment, fragment2, z, b2, true);
        if (obj4 != null) {
            arrayList4.add(view3);
            fragmentTransitionImpl2.setSharedElementTargets(obj4, view3, arrayList3);
            a(fragmentTransitionImpl2, obj4, obj2, b2, fragmentContainerTransition2.e, fragmentContainerTransition2.f);
            Rect rect2 = new Rect();
            View b3 = b(c2, fragmentContainerTransition2, obj5, z);
            if (b3 != null) {
                fragmentTransitionImpl2.setEpicenter(obj5, rect2);
            }
            rect = rect2;
            view2 = b3;
        } else {
            view2 = null;
            rect = null;
        }
        final Fragment fragment3 = fragment;
        final Fragment fragment4 = fragment2;
        final boolean z2 = z;
        final ArrayMap arrayMap3 = c2;
        AnonymousClass3 r0 = new Runnable() {
            public void run() {
                FragmentTransition.b(fragment3, fragment4, z2, arrayMap3, false);
                if (view2 != null) {
                    fragmentTransitionImpl2.getBoundsOnScreen(view2, rect);
                }
            }
        };
        OneShotPreDrawListener.a(viewGroup, r0);
        return obj4;
    }

    private static void a(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View view = (View) arrayMap.valueAt(size);
            if (collection.contains(ViewCompat.getTransitionName(view))) {
                arrayList.add(view);
            }
        }
    }

    private static Object b(FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        ArrayMap<String, String> arrayMap2;
        Object obj3;
        Object obj4;
        Rect rect;
        FragmentTransitionImpl fragmentTransitionImpl2 = fragmentTransitionImpl;
        FragmentContainerTransition fragmentContainerTransition2 = fragmentContainerTransition;
        final ArrayList<View> arrayList3 = arrayList;
        final Object obj5 = obj;
        Fragment fragment = fragmentContainerTransition2.a;
        Fragment fragment2 = fragmentContainerTransition2.d;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        boolean z = fragmentContainerTransition2.b;
        if (arrayMap.isEmpty()) {
            arrayMap2 = arrayMap;
            obj3 = null;
        } else {
            obj3 = a(fragmentTransitionImpl2, fragment, fragment2, z);
            arrayMap2 = arrayMap;
        }
        ArrayMap b2 = b(fragmentTransitionImpl2, arrayMap2, obj3, fragmentContainerTransition2);
        if (arrayMap.isEmpty()) {
            obj4 = null;
        } else {
            arrayList3.addAll(b2.values());
            obj4 = obj3;
        }
        if (obj5 == null && obj2 == null && obj4 == null) {
            return null;
        }
        b(fragment, fragment2, z, b2, true);
        if (obj4 != null) {
            rect = new Rect();
            fragmentTransitionImpl2.setSharedElementTargets(obj4, view, arrayList3);
            a(fragmentTransitionImpl2, obj4, obj2, b2, fragmentContainerTransition2.e, fragmentContainerTransition2.f);
            if (obj5 != null) {
                fragmentTransitionImpl2.setEpicenter(obj5, rect);
            }
        } else {
            rect = null;
        }
        final FragmentTransitionImpl fragmentTransitionImpl3 = fragmentTransitionImpl2;
        final ArrayMap<String, String> arrayMap3 = arrayMap2;
        final Object obj6 = obj4;
        final FragmentContainerTransition fragmentContainerTransition3 = fragmentContainerTransition2;
        AnonymousClass4 r13 = r0;
        final ArrayList<View> arrayList4 = arrayList2;
        final View view2 = view;
        final Fragment fragment3 = fragment;
        final Fragment fragment4 = fragment2;
        final boolean z2 = z;
        final Rect rect2 = rect;
        AnonymousClass4 r0 = new Runnable() {
            public void run() {
                ArrayMap a2 = FragmentTransition.c(fragmentTransitionImpl3, arrayMap3, obj6, fragmentContainerTransition3);
                if (a2 != null) {
                    arrayList4.addAll(a2.values());
                    arrayList4.add(view2);
                }
                FragmentTransition.b(fragment3, fragment4, z2, a2, false);
                if (obj6 != null) {
                    fragmentTransitionImpl3.swapSharedElementTargets(obj6, arrayList3, arrayList4);
                    View a3 = FragmentTransition.b(a2, fragmentContainerTransition3, obj5, z2);
                    if (a3 != null) {
                        fragmentTransitionImpl3.getBoundsOnScreen(a3, rect2);
                    }
                }
            }
        };
        OneShotPreDrawListener.a(viewGroup, r13);
        return obj4;
    }

    private static ArrayMap<String, View> b(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> arrayList;
        if (arrayMap.isEmpty() || obj == null) {
            arrayMap.clear();
            return null;
        }
        Fragment fragment = fragmentContainerTransition.d;
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragmentTransitionImpl.a((Map<String, View>) arrayMap2, fragment.getView());
        BackStackRecord backStackRecord = fragmentContainerTransition.f;
        if (fragmentContainerTransition.e) {
            sharedElementCallback = fragment.r();
            arrayList = backStackRecord.s;
        } else {
            sharedElementCallback = fragment.s();
            arrayList = backStackRecord.r;
        }
        arrayMap2.retainAll(arrayList);
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = (String) arrayList.get(size);
                View view = (View) arrayMap2.get(str);
                if (view == null) {
                    arrayMap.remove(str);
                } else if (!str.equals(ViewCompat.getTransitionName(view))) {
                    arrayMap.put(ViewCompat.getTransitionName(view), (String) arrayMap.remove(str));
                }
            }
        } else {
            arrayMap.retainAll(arrayMap2.keySet());
        }
        return arrayMap2;
    }

    /* access modifiers changed from: private */
    public static ArrayMap<String, View> c(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback sharedElementCallback;
        ArrayList<String> arrayList;
        Fragment fragment = fragmentContainerTransition.a;
        View view = fragment.getView();
        if (arrayMap.isEmpty() || obj == null || view == null) {
            arrayMap.clear();
            return null;
        }
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragmentTransitionImpl.a((Map<String, View>) arrayMap2, view);
        BackStackRecord backStackRecord = fragmentContainerTransition.c;
        if (fragmentContainerTransition.b) {
            sharedElementCallback = fragment.s();
            arrayList = backStackRecord.r;
        } else {
            sharedElementCallback = fragment.r();
            arrayList = backStackRecord.s;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
            arrayMap2.retainAll(arrayMap.values());
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = (String) arrayList.get(size);
                View view2 = (View) arrayMap2.get(str);
                if (view2 == null) {
                    String a2 = a(arrayMap, str);
                    if (a2 != null) {
                        arrayMap.remove(a2);
                    }
                } else if (!str.equals(ViewCompat.getTransitionName(view2))) {
                    String a3 = a(arrayMap, str);
                    if (a3 != null) {
                        arrayMap.put(a3, ViewCompat.getTransitionName(view2));
                    }
                }
            }
        } else {
            a(arrayMap, arrayMap2);
        }
        return arrayMap2;
    }

    private static String a(ArrayMap<String, String> arrayMap, String str) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(arrayMap.valueAt(i))) {
                return (String) arrayMap.keyAt(i);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static View b(ArrayMap<String, View> arrayMap, FragmentContainerTransition fragmentContainerTransition, Object obj, boolean z) {
        String str;
        BackStackRecord backStackRecord = fragmentContainerTransition.c;
        if (obj == null || arrayMap == null || backStackRecord.r == null || backStackRecord.r.isEmpty()) {
            return null;
        }
        if (z) {
            str = (String) backStackRecord.r.get(0);
        } else {
            str = (String) backStackRecord.s.get(0);
        }
        return (View) arrayMap.get(str);
    }

    private static void a(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, ArrayMap<String, View> arrayMap, boolean z, BackStackRecord backStackRecord) {
        String str;
        if (backStackRecord.r != null && !backStackRecord.r.isEmpty()) {
            if (z) {
                str = (String) backStackRecord.s.get(0);
            } else {
                str = (String) backStackRecord.r.get(0);
            }
            View view = (View) arrayMap.get(str);
            fragmentTransitionImpl.setEpicenter(obj, view);
            if (obj2 != null) {
                fragmentTransitionImpl.setEpicenter(obj2, view);
            }
        }
    }

    private static void a(ArrayMap<String, String> arrayMap, ArrayMap<String, View> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey((String) arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(Fragment fragment, Fragment fragment2, boolean z, ArrayMap<String, View> arrayMap, boolean z2) {
        SharedElementCallback sharedElementCallback;
        int i;
        if (z) {
            sharedElementCallback = fragment2.r();
        } else {
            sharedElementCallback = fragment.r();
        }
        if (sharedElementCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (arrayMap == null) {
                i = 0;
            } else {
                i = arrayMap.size();
            }
            for (int i2 = 0; i2 < i; i2++) {
                arrayList2.add(arrayMap.keyAt(i2));
                arrayList.add(arrayMap.valueAt(i2));
            }
            if (z2) {
                sharedElementCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                sharedElementCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    /* access modifiers changed from: private */
    public static ArrayList<View> b(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList<View> arrayList2 = new ArrayList<>();
        View view2 = fragment.getView();
        if (view2 != null) {
            fragmentTransitionImpl.a(arrayList2, view2);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        arrayList2.add(view);
        fragmentTransitionImpl.addTargets(obj, arrayList2);
        return arrayList2;
    }

    /* access modifiers changed from: private */
    public static void b(ArrayList<View> arrayList, int i) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((View) arrayList.get(size)).setVisibility(i);
            }
        }
    }

    private static Object a(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        boolean z2 = (obj == null || obj2 == null || fragment == null) ? true : z ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap();
        if (z2) {
            return fragmentTransitionImpl.mergeTransitionsTogether(obj2, obj, obj3);
        }
        return fragmentTransitionImpl.mergeTransitionsInSequence(obj2, obj, obj3);
    }

    public static void a(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) {
        int size = backStackRecord.b.size();
        for (int i = 0; i < size; i++) {
            a(backStackRecord, (Op) backStackRecord.b.get(i), sparseArray, false, z);
        }
    }

    public static void b(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) {
        if (backStackRecord.a.n.onHasView()) {
            for (int size = backStackRecord.b.size() - 1; size >= 0; size--) {
                a(backStackRecord, (Op) backStackRecord.b.get(size), sparseArray, true, z);
            }
        }
    }

    static boolean a() {
        return (b == null && c == null) ? false : true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0035, code lost:
        if (r10.t != false) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x006c, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x006e, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0079, code lost:
        r13 = r1;
        r1 = false;
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0088, code lost:
        if (r10.J == false) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x008a, code lost:
        r1 = true;
     */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0098  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.support.v4.app.BackStackRecord r16, android.support.v4.app.BackStackRecord.Op r17, android.util.SparseArray<android.support.v4.app.FragmentTransition.FragmentContainerTransition> r18, boolean r19, boolean r20) {
        /*
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            android.support.v4.app.Fragment r10 = r1.b
            if (r10 != 0) goto L_0x000d
            return
        L_0x000d:
            int r11 = r10.H
            if (r11 != 0) goto L_0x0012
            return
        L_0x0012:
            if (r3 == 0) goto L_0x001b
            int[] r4 = a
            int r1 = r1.a
            r1 = r4[r1]
            goto L_0x001d
        L_0x001b:
            int r1 = r1.a
        L_0x001d:
            r4 = 0
            r5 = 1
            if (r1 == r5) goto L_0x007d
            switch(r1) {
                case 3: goto L_0x0053;
                case 4: goto L_0x003b;
                case 5: goto L_0x0029;
                case 6: goto L_0x0053;
                case 7: goto L_0x007d;
                default: goto L_0x0024;
            }
        L_0x0024:
            r1 = 0
        L_0x0025:
            r12 = 0
            r13 = 0
            goto L_0x0090
        L_0x0029:
            if (r20 == 0) goto L_0x0038
            boolean r1 = r10.Y
            if (r1 == 0) goto L_0x008c
            boolean r1 = r10.J
            if (r1 != 0) goto L_0x008c
            boolean r1 = r10.t
            if (r1 == 0) goto L_0x008c
            goto L_0x008a
        L_0x0038:
            boolean r1 = r10.J
            goto L_0x008d
        L_0x003b:
            if (r20 == 0) goto L_0x004a
            boolean r1 = r10.Y
            if (r1 == 0) goto L_0x006e
            boolean r1 = r10.t
            if (r1 == 0) goto L_0x006e
            boolean r1 = r10.J
            if (r1 == 0) goto L_0x006e
        L_0x0049:
            goto L_0x006c
        L_0x004a:
            boolean r1 = r10.t
            if (r1 == 0) goto L_0x006e
            boolean r1 = r10.J
            if (r1 != 0) goto L_0x006e
            goto L_0x0049
        L_0x0053:
            if (r20 == 0) goto L_0x0070
            boolean r1 = r10.t
            if (r1 != 0) goto L_0x006e
            android.view.View r1 = r10.R
            if (r1 == 0) goto L_0x006e
            android.view.View r1 = r10.R
            int r1 = r1.getVisibility()
            if (r1 != 0) goto L_0x006e
            float r1 = r10.Z
            r6 = 0
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 < 0) goto L_0x006e
        L_0x006c:
            r1 = 1
            goto L_0x0079
        L_0x006e:
            r1 = 0
            goto L_0x0079
        L_0x0070:
            boolean r1 = r10.t
            if (r1 == 0) goto L_0x006e
            boolean r1 = r10.J
            if (r1 != 0) goto L_0x006e
            goto L_0x006c
        L_0x0079:
            r13 = r1
            r1 = 0
            r12 = 1
            goto L_0x0090
        L_0x007d:
            if (r20 == 0) goto L_0x0082
            boolean r1 = r10.X
            goto L_0x008d
        L_0x0082:
            boolean r1 = r10.t
            if (r1 != 0) goto L_0x008c
            boolean r1 = r10.J
            if (r1 != 0) goto L_0x008c
        L_0x008a:
            r1 = 1
            goto L_0x008d
        L_0x008c:
            r1 = 0
        L_0x008d:
            r4 = r1
            r1 = 1
            goto L_0x0025
        L_0x0090:
            java.lang.Object r6 = r2.get(r11)
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r6 = (android.support.v4.app.FragmentTransition.FragmentContainerTransition) r6
            if (r4 == 0) goto L_0x00a2
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r6 = a(r6, r2, r11)
            r6.a = r10
            r6.b = r3
            r6.c = r0
        L_0x00a2:
            r14 = r6
            r9 = 0
            if (r20 != 0) goto L_0x00ca
            if (r1 == 0) goto L_0x00ca
            if (r14 == 0) goto L_0x00b0
            android.support.v4.app.Fragment r1 = r14.d
            if (r1 != r10) goto L_0x00b0
            r14.d = r9
        L_0x00b0:
            android.support.v4.app.FragmentManagerImpl r4 = r0.a
            int r1 = r10.k
            if (r1 >= r5) goto L_0x00ca
            int r1 = r4.l
            if (r1 < r5) goto L_0x00ca
            boolean r1 = r0.t
            if (r1 != 0) goto L_0x00ca
            r4.f(r10)
            r6 = 1
            r7 = 0
            r8 = 0
            r1 = 0
            r5 = r10
            r9 = r1
            r4.a(r5, r6, r7, r8, r9)
        L_0x00ca:
            if (r13 == 0) goto L_0x00dc
            if (r14 == 0) goto L_0x00d2
            android.support.v4.app.Fragment r1 = r14.d
            if (r1 != 0) goto L_0x00dc
        L_0x00d2:
            android.support.v4.app.FragmentTransition$FragmentContainerTransition r14 = a(r14, r2, r11)
            r14.d = r10
            r14.e = r3
            r14.f = r0
        L_0x00dc:
            if (r20 != 0) goto L_0x00e9
            if (r12 == 0) goto L_0x00e9
            if (r14 == 0) goto L_0x00e9
            android.support.v4.app.Fragment r0 = r14.a
            if (r0 != r10) goto L_0x00e9
            r0 = 0
            r14.a = r0
        L_0x00e9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentTransition.a(android.support.v4.app.BackStackRecord, android.support.v4.app.BackStackRecord$Op, android.util.SparseArray, boolean, boolean):void");
    }

    private static FragmentContainerTransition a(FragmentContainerTransition fragmentContainerTransition, SparseArray<FragmentContainerTransition> sparseArray, int i) {
        if (fragmentContainerTransition != null) {
            return fragmentContainerTransition;
        }
        FragmentContainerTransition fragmentContainerTransition2 = new FragmentContainerTransition();
        sparseArray.put(i, fragmentContainerTransition2);
        return fragmentContainerTransition2;
    }
}
