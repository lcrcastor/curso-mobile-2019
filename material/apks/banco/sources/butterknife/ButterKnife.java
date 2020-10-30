package butterknife;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.Property;
import android.view.View;
import butterknife.internal.ButterKnifeProcessor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ButterKnife {
    static final Map<Class<?>, Method> a = new LinkedHashMap();
    static final Map<Class<?>, Method> b = new LinkedHashMap();
    static final Method c = null;
    private static boolean d = false;

    public interface Action<T extends View> {
        void apply(T t, int i);
    }

    public enum Finder {
        VIEW {
            public View findOptionalView(Object obj, int i) {
                return ((View) obj).findViewById(i);
            }

            /* access modifiers changed from: protected */
            public Context getContext(Object obj) {
                return ((View) obj).getContext();
            }
        },
        ACTIVITY {
            public View findOptionalView(Object obj, int i) {
                return ((Activity) obj).findViewById(i);
            }

            /* access modifiers changed from: protected */
            public Context getContext(Object obj) {
                return (Activity) obj;
            }
        },
        DIALOG {
            public View findOptionalView(Object obj, int i) {
                return ((Dialog) obj).findViewById(i);
            }

            /* access modifiers changed from: protected */
            public Context getContext(Object obj) {
                return ((Dialog) obj).getContext();
            }
        };

        public static <T extends View> T[] arrayOf(T... tArr) {
            return tArr;
        }

        public abstract View findOptionalView(Object obj, int i);

        /* access modifiers changed from: protected */
        public abstract Context getContext(Object obj);

        public static <T extends View> List<T> listOf(T... tArr) {
            return new ImmutableViewList(tArr);
        }

        public View findRequiredView(Object obj, int i, String str) {
            View findOptionalView = findOptionalView(obj, i);
            if (findOptionalView != null) {
                return findOptionalView;
            }
            String resourceEntryName = getContext(obj).getResources().getResourceEntryName(i);
            StringBuilder sb = new StringBuilder();
            sb.append("Required view '");
            sb.append(resourceEntryName);
            sb.append("' with ID ");
            sb.append(i);
            sb.append(" for ");
            sb.append(str);
            sb.append(" was not found. If this view is optional add '@Optional' annotation.");
            throw new IllegalStateException(sb.toString());
        }
    }

    public interface Setter<T extends View, V> {
        void set(T t, V v, int i);
    }

    private ButterKnife() {
        throw new AssertionError("No instances.");
    }

    public static void setDebug(boolean z) {
        d = z;
    }

    public static void inject(Activity activity) {
        a(activity, activity, Finder.ACTIVITY);
    }

    public static void inject(View view) {
        a(view, view, Finder.VIEW);
    }

    public static void inject(Dialog dialog) {
        a(dialog, dialog, Finder.DIALOG);
    }

    public static void inject(Object obj, Activity activity) {
        a(obj, activity, Finder.ACTIVITY);
    }

    public static void inject(Object obj, View view) {
        a(obj, view, Finder.VIEW);
    }

    public static void inject(Object obj, Dialog dialog) {
        a(obj, dialog, Finder.DIALOG);
    }

    public static void reset(Object obj) {
        Class cls = obj.getClass();
        try {
            if (d) {
                StringBuilder sb = new StringBuilder();
                sb.append("Looking up view injector for ");
                sb.append(cls.getName());
                Log.d("ButterKnife", sb.toString());
            }
            Method b2 = b(cls);
            if (b2 != null) {
                b2.invoke(null, new Object[]{obj});
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            e = e2;
            if (e instanceof InvocationTargetException) {
                e = e.getCause();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unable to reset views for ");
            sb2.append(obj);
            throw new RuntimeException(sb2.toString(), e);
        }
    }

    static void a(Object obj, Object obj2, Finder finder) {
        Class cls = obj.getClass();
        try {
            if (d) {
                StringBuilder sb = new StringBuilder();
                sb.append("Looking up view injector for ");
                sb.append(cls.getName());
                Log.d("ButterKnife", sb.toString());
            }
            Method a2 = a(cls);
            if (a2 != null) {
                a2.invoke(null, new Object[]{finder, obj, obj2});
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            e = e2;
            if (e instanceof InvocationTargetException) {
                e = e.getCause();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unable to inject views for ");
            sb2.append(obj);
            throw new RuntimeException(sb2.toString(), e);
        }
    }

    private static Method a(Class<?> cls) {
        Method method;
        Method method2 = (Method) a.get(cls);
        if (method2 != null) {
            if (d) {
                Log.d("ButterKnife", "HIT: Cached in injector map.");
            }
            return method2;
        }
        String name = cls.getName();
        if (name.startsWith(ButterKnifeProcessor.ANDROID_PREFIX) || name.startsWith(ButterKnifeProcessor.JAVA_PREFIX)) {
            if (d) {
                Log.d("ButterKnife", "MISS: Reached framework class. Abandoning search.");
            }
            return c;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(ButterKnifeProcessor.SUFFIX);
            method = Class.forName(sb.toString()).getMethod("inject", new Class[]{Finder.class, cls, Object.class});
            if (d) {
                Log.d("ButterKnife", "HIT: Class loaded injection class.");
            }
        } catch (ClassNotFoundException unused) {
            if (d) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Not found. Trying superclass ");
                sb2.append(cls.getSuperclass().getName());
                Log.d("ButterKnife", sb2.toString());
            }
            method = a(cls.getSuperclass());
        }
        a.put(cls, method);
        return method;
    }

    private static Method b(Class<?> cls) {
        Method method;
        Method method2 = (Method) b.get(cls);
        if (method2 != null) {
            if (d) {
                Log.d("ButterKnife", "HIT: Cached in injector map.");
            }
            return method2;
        }
        String name = cls.getName();
        if (name.startsWith(ButterKnifeProcessor.ANDROID_PREFIX) || name.startsWith(ButterKnifeProcessor.JAVA_PREFIX)) {
            if (d) {
                Log.d("ButterKnife", "MISS: Reached framework class. Abandoning search.");
            }
            return c;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(ButterKnifeProcessor.SUFFIX);
            method = Class.forName(sb.toString()).getMethod("reset", new Class[]{cls});
            if (d) {
                Log.d("ButterKnife", "HIT: Class loaded injection class.");
            }
        } catch (ClassNotFoundException unused) {
            if (d) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Not found. Trying superclass ");
                sb2.append(cls.getSuperclass().getName());
                Log.d("ButterKnife", sb2.toString());
            }
            method = b(cls.getSuperclass());
        }
        b.put(cls, method);
        return method;
    }

    public static <T extends View> void apply(List<T> list, Action<? super T> action) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            action.apply((View) list.get(i), i);
        }
    }

    public static <T extends View, V> void apply(List<T> list, Setter<? super T, V> setter, V v) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            setter.set((View) list.get(i), v, i);
        }
    }

    @TargetApi(14)
    public static <T extends View, V> void apply(List<T> list, Property<? super T, V> property, V v) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            property.set(list.get(i), v);
        }
    }

    public static <T extends View> T findById(View view, int i) {
        return view.findViewById(i);
    }

    public static <T extends View> T findById(Activity activity, int i) {
        return activity.findViewById(i);
    }

    public static <T extends View> T findById(Dialog dialog, int i) {
        return dialog.findViewById(i);
    }
}
