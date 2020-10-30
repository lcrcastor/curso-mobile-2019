package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle.Event;
import android.support.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class ClassesInfoCache {
    static ClassesInfoCache a = new ClassesInfoCache();
    private final Map<Class, CallbackInfo> b = new HashMap();
    private final Map<Class, Boolean> c = new HashMap();

    static class CallbackInfo {
        final Map<Event, List<MethodReference>> a = new HashMap();
        final Map<MethodReference, Event> b;

        CallbackInfo(Map<MethodReference, Event> map) {
            this.b = map;
            for (Entry entry : map.entrySet()) {
                Event event = (Event) entry.getValue();
                List list = (List) this.a.get(event);
                if (list == null) {
                    list = new ArrayList();
                    this.a.put(event, list);
                }
                list.add(entry.getKey());
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(LifecycleOwner lifecycleOwner, Event event, Object obj) {
            a((List) this.a.get(event), lifecycleOwner, event, obj);
            a((List) this.a.get(Event.ON_ANY), lifecycleOwner, event, obj);
        }

        private static void a(List<MethodReference> list, LifecycleOwner lifecycleOwner, Event event, Object obj) {
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    ((MethodReference) list.get(size)).a(lifecycleOwner, event, obj);
                }
            }
        }
    }

    static class MethodReference {
        final int a;
        final Method b;

        MethodReference(int i, Method method) {
            this.a = i;
            this.b = method;
            this.b.setAccessible(true);
        }

        /* access modifiers changed from: 0000 */
        public void a(LifecycleOwner lifecycleOwner, Event event, Object obj) {
            try {
                switch (this.a) {
                    case 0:
                        this.b.invoke(obj, new Object[0]);
                        return;
                    case 1:
                        this.b.invoke(obj, new Object[]{lifecycleOwner});
                        return;
                    case 2:
                        this.b.invoke(obj, new Object[]{lifecycleOwner, event});
                        return;
                    default:
                        return;
                }
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Failed to call observer method", e.getCause());
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            MethodReference methodReference = (MethodReference) obj;
            if (this.a != methodReference.a || !this.b.getName().equals(methodReference.b.getName())) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return (this.a * 31) + this.b.getName().hashCode();
        }
    }

    ClassesInfoCache() {
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Class cls) {
        if (this.c.containsKey(cls)) {
            return ((Boolean) this.c.get(cls)).booleanValue();
        }
        Method[] c2 = c(cls);
        for (Method annotation : c2) {
            if (((OnLifecycleEvent) annotation.getAnnotation(OnLifecycleEvent.class)) != null) {
                a(cls, c2);
                return true;
            }
        }
        this.c.put(cls, Boolean.valueOf(false));
        return false;
    }

    private Method[] c(Class cls) {
        try {
            return cls.getDeclaredMethods();
        } catch (NoClassDefFoundError e) {
            throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e);
        }
    }

    /* access modifiers changed from: 0000 */
    public CallbackInfo b(Class cls) {
        CallbackInfo callbackInfo = (CallbackInfo) this.b.get(cls);
        if (callbackInfo != null) {
            return callbackInfo;
        }
        return a(cls, null);
    }

    private void a(Map<MethodReference, Event> map, MethodReference methodReference, Event event, Class cls) {
        Event event2 = (Event) map.get(methodReference);
        if (event2 != null && event != event2) {
            Method method = methodReference.b;
            StringBuilder sb = new StringBuilder();
            sb.append("Method ");
            sb.append(method.getName());
            sb.append(" in ");
            sb.append(cls.getName());
            sb.append(" already declared with different @OnLifecycleEvent value: previous");
            sb.append(" value ");
            sb.append(event2);
            sb.append(", new value ");
            sb.append(event);
            throw new IllegalArgumentException(sb.toString());
        } else if (event2 == null) {
            map.put(methodReference, event);
        }
    }

    private CallbackInfo a(Class cls, @Nullable Method[] methodArr) {
        int i;
        Class superclass = cls.getSuperclass();
        HashMap hashMap = new HashMap();
        if (superclass != null) {
            CallbackInfo b2 = b(superclass);
            if (b2 != null) {
                hashMap.putAll(b2.b);
            }
        }
        for (Class b3 : cls.getInterfaces()) {
            for (Entry entry : b(b3).b.entrySet()) {
                a(hashMap, (MethodReference) entry.getKey(), (Event) entry.getValue(), cls);
            }
        }
        if (methodArr == null) {
            methodArr = c(cls);
        }
        boolean z = false;
        for (Method method : methodArr) {
            OnLifecycleEvent onLifecycleEvent = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class);
            if (onLifecycleEvent != null) {
                Class[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length <= 0) {
                    i = 0;
                } else if (!parameterTypes[0].isAssignableFrom(LifecycleOwner.class)) {
                    throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                } else {
                    i = 1;
                }
                Event value = onLifecycleEvent.value();
                if (parameterTypes.length > 1) {
                    if (!parameterTypes[1].isAssignableFrom(Event.class)) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    } else if (value != Event.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    } else {
                        i = 2;
                    }
                }
                if (parameterTypes.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                a(hashMap, new MethodReference(i, method), value, cls);
                z = true;
            }
        }
        CallbackInfo callbackInfo = new CallbackInfo(hashMap);
        this.b.put(cls, callbackInfo);
        this.c.put(cls, Boolean.valueOf(z));
        return callbackInfo;
    }
}
