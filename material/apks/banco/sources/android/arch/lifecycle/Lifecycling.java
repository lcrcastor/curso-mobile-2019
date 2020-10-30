package android.arch.lifecycle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestrictTo({Scope.LIBRARY_GROUP})
public class Lifecycling {
    private static Map<Class, Integer> a = new HashMap();
    private static Map<Class, List<Constructor<? extends GeneratedAdapter>>> b = new HashMap();

    @NonNull
    static GenericLifecycleObserver a(Object obj) {
        if (obj instanceof FullLifecycleObserver) {
            return new FullLifecycleObserverAdapter((FullLifecycleObserver) obj);
        }
        if (obj instanceof GenericLifecycleObserver) {
            return (GenericLifecycleObserver) obj;
        }
        Class cls = obj.getClass();
        if (b(cls) != 2) {
            return new ReflectiveGenericLifecycleObserver(obj);
        }
        List list = (List) b.get(cls);
        if (list.size() == 1) {
            return new SingleGeneratedAdapterObserver(a((Constructor) list.get(0), obj));
        }
        GeneratedAdapter[] generatedAdapterArr = new GeneratedAdapter[list.size()];
        for (int i = 0; i < list.size(); i++) {
            generatedAdapterArr[i] = a((Constructor) list.get(i), obj);
        }
        return new CompositeGeneratedAdaptersObserver(generatedAdapterArr);
    }

    private static GeneratedAdapter a(Constructor<? extends GeneratedAdapter> constructor, Object obj) {
        try {
            return (GeneratedAdapter) constructor.newInstance(new Object[]{obj});
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    @Nullable
    private static Constructor<? extends GeneratedAdapter> a(Class<?> cls) {
        try {
            Package packageR = cls.getPackage();
            String canonicalName = cls.getCanonicalName();
            String name = packageR != null ? packageR.getName() : "";
            if (!name.isEmpty()) {
                canonicalName = canonicalName.substring(name.length() + 1);
            }
            String adapterName = getAdapterName(canonicalName);
            if (!name.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append(name);
                sb.append(".");
                sb.append(adapterName);
                adapterName = sb.toString();
            }
            Constructor<? extends GeneratedAdapter> declaredConstructor = Class.forName(adapterName).getDeclaredConstructor(new Class[]{cls});
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return declaredConstructor;
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static int b(Class<?> cls) {
        if (a.containsKey(cls)) {
            return ((Integer) a.get(cls)).intValue();
        }
        int c = c(cls);
        a.put(cls, Integer.valueOf(c));
        return c;
    }

    private static int c(Class<?> cls) {
        Class[] interfaces;
        if (cls.getCanonicalName() == null) {
            return 1;
        }
        Constructor a2 = a(cls);
        if (a2 != null) {
            b.put(cls, Collections.singletonList(a2));
            return 2;
        } else if (ClassesInfoCache.a.a(cls)) {
            return 1;
        } else {
            Class superclass = cls.getSuperclass();
            ArrayList arrayList = null;
            if (d(superclass)) {
                if (b(superclass) == 1) {
                    return 1;
                }
                arrayList = new ArrayList((Collection) b.get(superclass));
            }
            for (Class cls2 : cls.getInterfaces()) {
                if (d(cls2)) {
                    if (b(cls2) == 1) {
                        return 1;
                    }
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.addAll((Collection) b.get(cls2));
                }
            }
            if (arrayList == null) {
                return 1;
            }
            b.put(cls, arrayList);
            return 2;
        }
    }

    private static boolean d(Class<?> cls) {
        return cls != null && LifecycleObserver.class.isAssignableFrom(cls);
    }

    public static String getAdapterName(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.replace(".", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR));
        sb.append("_LifecycleAdapter");
        return sb.toString();
    }
}
