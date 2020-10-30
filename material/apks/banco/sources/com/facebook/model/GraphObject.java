package com.facebook.model;

import com.facebook.FacebookGraphObjectException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface GraphObject {

    public static final class Factory {
        private static final HashSet<Class<?>> a = new HashSet<>();
        private static final SimpleDateFormat[] b = {new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US), new SimpleDateFormat("yyyy-MM-dd", Locale.US)};

        static final class GraphObjectListImpl<T> extends AbstractList<T> implements GraphObjectList<T> {
            private final JSONArray a;
            private final Class<?> b;

            public GraphObjectListImpl(JSONArray jSONArray, Class<?> cls) {
                Validate.notNull(jSONArray, "state");
                Validate.notNull(cls, "itemType");
                this.a = jSONArray;
                this.b = cls;
            }

            public String toString() {
                return String.format("GraphObjectList{itemType=%s, state=%s}", new Object[]{this.b.getSimpleName(), this.a});
            }

            public void add(int i, T t) {
                if (i < 0) {
                    throw new IndexOutOfBoundsException();
                } else if (i < size()) {
                    throw new UnsupportedOperationException("Only adding items at the end of the list is supported.");
                } else {
                    a(i, t);
                }
            }

            public T set(int i, T t) {
                a(i);
                T t2 = get(i);
                a(i, t);
                return t2;
            }

            public int hashCode() {
                return this.a.hashCode();
            }

            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                return this.a.equals(((GraphObjectListImpl) obj).a);
            }

            public T get(int i) {
                a(i);
                return Factory.a(this.a.opt(i), this.b, null);
            }

            public int size() {
                return this.a.length();
            }

            public final <U extends GraphObject> GraphObjectList<U> castToListOf(Class<U> cls) {
                if (!GraphObject.class.isAssignableFrom(this.b)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Can't cast GraphObjectCollection of non-GraphObject type ");
                    sb.append(this.b);
                    throw new FacebookGraphObjectException(sb.toString());
                } else if (cls.isAssignableFrom(this.b)) {
                    return this;
                } else {
                    return Factory.createList(this.a, cls);
                }
            }

            public final JSONArray getInnerJSONArray() {
                return this.a;
            }

            public void clear() {
                throw new UnsupportedOperationException();
            }

            public boolean remove(Object obj) {
                throw new UnsupportedOperationException();
            }

            public boolean removeAll(Collection<?> collection) {
                throw new UnsupportedOperationException();
            }

            public boolean retainAll(Collection<?> collection) {
                throw new UnsupportedOperationException();
            }

            private void a(int i) {
                if (i < 0 || i >= this.a.length()) {
                    throw new IndexOutOfBoundsException();
                }
            }

            private void a(int i, T t) {
                try {
                    this.a.put(i, Factory.b((Object) t));
                } catch (JSONException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        static final class GraphObjectProxy extends ProxyBase<JSONObject> {
            private final Class<?> b;

            public GraphObjectProxy(JSONObject jSONObject, Class<?> cls) {
                super(jSONObject);
                this.b = cls;
            }

            public String toString() {
                return String.format("GraphObject{graphObjectClass=%s, state=%s}", new Object[]{this.b.getSimpleName(), this.a});
            }

            public final Object invoke(Object obj, Method method, Object[] objArr) {
                Class<GraphObject> declaringClass = method.getDeclaringClass();
                if (declaringClass == Object.class) {
                    return a(obj, method, objArr);
                }
                if (declaringClass == Map.class) {
                    return a(method, objArr);
                }
                if (declaringClass == GraphObject.class) {
                    return b(obj, method, objArr);
                }
                if (GraphObject.class.isAssignableFrom(declaringClass)) {
                    return b(method, objArr);
                }
                return a(method);
            }

            private final Object a(Method method, Object[] objArr) {
                Map map;
                String name = method.getName();
                if (name.equals("clear")) {
                    JsonUtil.a((JSONObject) this.a);
                    return null;
                }
                boolean z = false;
                if (name.equals("containsKey")) {
                    return Boolean.valueOf(((JSONObject) this.a).has(objArr[0]));
                }
                if (name.equals("containsValue")) {
                    return Boolean.valueOf(JsonUtil.a((JSONObject) this.a, objArr[0]));
                }
                if (name.equals("entrySet")) {
                    return JsonUtil.b((JSONObject) this.a);
                }
                if (name.equals("get")) {
                    return ((JSONObject) this.a).opt(objArr[0]);
                }
                if (name.equals("isEmpty")) {
                    if (((JSONObject) this.a).length() == 0) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                } else if (name.equals("keySet")) {
                    return JsonUtil.c((JSONObject) this.a);
                } else {
                    if (name.equals("put")) {
                        return a(objArr);
                    }
                    if (name.equals("putAll")) {
                        if (objArr[0] instanceof Map) {
                            map = objArr[0];
                        } else if (!(objArr[0] instanceof GraphObject)) {
                            return null;
                        } else {
                            map = objArr[0].asMap();
                        }
                        JsonUtil.a((JSONObject) this.a, map);
                        return null;
                    } else if (name.equals(ProductAction.ACTION_REMOVE)) {
                        ((JSONObject) this.a).remove(objArr[0]);
                        return null;
                    } else if (name.equals("size")) {
                        return Integer.valueOf(((JSONObject) this.a).length());
                    } else {
                        if (name.equals("values")) {
                            return JsonUtil.d((JSONObject) this.a);
                        }
                        return a(method);
                    }
                }
            }

            private final Object b(Object obj, Method method, Object[] objArr) {
                String name = method.getName();
                if (name.equals("cast")) {
                    Class cls = objArr[0];
                    if (cls == null || !cls.isAssignableFrom(this.b)) {
                        return Factory.b(cls, (JSONObject) this.a);
                    }
                    return obj;
                } else if (name.equals("getInnerJSONObject")) {
                    return ((GraphObjectProxy) Proxy.getInvocationHandler(obj)).a;
                } else {
                    if (name.equals("asMap")) {
                        return Factory.b((JSONObject) this.a);
                    }
                    if (name.equals("getProperty")) {
                        return ((JSONObject) this.a).opt(objArr[0]);
                    }
                    if (name.equals("getPropertyAs")) {
                        return Factory.a(((JSONObject) this.a).opt(objArr[0]), objArr[1], null);
                    }
                    if (name.equals("getPropertyAsList")) {
                        Object opt = ((JSONObject) this.a).opt(objArr[0]);
                        final Class cls2 = objArr[1];
                        return Factory.a(opt, GraphObjectList.class, new ParameterizedType() {
                            public Type getOwnerType() {
                                return null;
                            }

                            public Type[] getActualTypeArguments() {
                                return new Type[]{cls2};
                            }

                            public Type getRawType() {
                                return GraphObjectList.class;
                            }
                        });
                    } else if (name.equals("setProperty")) {
                        return a(objArr);
                    } else {
                        if (!name.equals("removeProperty")) {
                            return a(method);
                        }
                        ((JSONObject) this.a).remove(objArr[0]);
                        return null;
                    }
                }
            }

            private Object a(CreateGraphObject createGraphObject, Object obj) {
                if (createGraphObject == null || Utility.isNullOrEmpty(createGraphObject.value())) {
                    return obj;
                }
                String value = createGraphObject.value();
                if (List.class.isAssignableFrom(obj.getClass())) {
                    GraphObjectList createList = Factory.createList(GraphObject.class);
                    for (Object next : (List) obj) {
                        GraphObject create = Factory.create();
                        create.setProperty(value, next);
                        createList.add(create);
                    }
                    return createList;
                }
                GraphObject create2 = Factory.create();
                create2.setProperty(value, obj);
                return create2;
            }

            private final Object b(Method method, Object[] objArr) {
                String name = method.getName();
                int length = method.getParameterTypes().length;
                PropertyName propertyName = (PropertyName) method.getAnnotation(PropertyName.class);
                String value = propertyName != null ? propertyName.value() : Factory.a(name.substring(3));
                ParameterizedType parameterizedType = null;
                if (length == 0) {
                    Object opt = ((JSONObject) this.a).opt(value);
                    Class returnType = method.getReturnType();
                    Type genericReturnType = method.getGenericReturnType();
                    if (genericReturnType instanceof ParameterizedType) {
                        parameterizedType = (ParameterizedType) genericReturnType;
                    }
                    return Factory.a(opt, returnType, parameterizedType);
                } else if (length != 1) {
                    return a(method);
                } else {
                    ((JSONObject) this.a).putOpt(value, Factory.b(a((CreateGraphObject) method.getAnnotation(CreateGraphObject.class), objArr[0])));
                    return null;
                }
            }

            private Object a(Object[] objArr) {
                try {
                    ((JSONObject) this.a).putOpt(objArr[0], Factory.b(objArr[1]));
                    return null;
                } catch (JSONException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        static abstract class ProxyBase<STATE> implements InvocationHandler {
            protected final STATE a;

            protected ProxyBase(STATE state) {
                this.a = state;
            }

            /* access modifiers changed from: protected */
            public final Object a(Method method) {
                StringBuilder sb = new StringBuilder();
                sb.append(getClass().getName());
                sb.append(" got an unexpected method signature: ");
                sb.append(method.toString());
                throw new FacebookGraphObjectException(sb.toString());
            }

            /* access modifiers changed from: protected */
            public final Object a(Object obj, Method method, Object[] objArr) {
                String name = method.getName();
                if (name.equals("equals")) {
                    Object obj2 = objArr[0];
                    if (obj2 == null) {
                        return Boolean.valueOf(false);
                    }
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(obj2);
                    if (!(invocationHandler instanceof GraphObjectProxy)) {
                        return Boolean.valueOf(false);
                    }
                    return Boolean.valueOf(this.a.equals(((GraphObjectProxy) invocationHandler).a));
                } else if (name.equals("toString")) {
                    return toString();
                } else {
                    return method.invoke(this.a, objArr);
                }
            }
        }

        private Factory() {
        }

        public static GraphObject create(JSONObject jSONObject) {
            return create(jSONObject, GraphObject.class);
        }

        public static <T extends GraphObject> T create(JSONObject jSONObject, Class<T> cls) {
            return b(cls, jSONObject);
        }

        public static GraphObject create() {
            return create(GraphObject.class);
        }

        public static <T extends GraphObject> T create(Class<T> cls) {
            return b(cls, new JSONObject());
        }

        public static boolean hasSameId(GraphObject graphObject, GraphObject graphObject2) {
            if (graphObject == null || graphObject2 == null || !graphObject.asMap().containsKey("id") || !graphObject2.asMap().containsKey("id")) {
                return false;
            }
            if (graphObject.equals(graphObject2)) {
                return true;
            }
            Object property = graphObject.getProperty("id");
            Object property2 = graphObject2.getProperty("id");
            if (property == null || property2 == null || !(property instanceof String) || !(property2 instanceof String)) {
                return false;
            }
            return property.equals(property2);
        }

        public static <T> GraphObjectList<T> createList(JSONArray jSONArray, Class<T> cls) {
            return new GraphObjectListImpl(jSONArray, cls);
        }

        public static <T> GraphObjectList<T> createList(Class<T> cls) {
            return createList(new JSONArray(), cls);
        }

        /* access modifiers changed from: private */
        public static <T extends GraphObject> T b(Class<T> cls, JSONObject jSONObject) {
            c(cls);
            return (GraphObject) Proxy.newProxyInstance(GraphObject.class.getClassLoader(), new Class[]{cls}, new GraphObjectProxy(jSONObject, cls));
        }

        /* access modifiers changed from: private */
        public static Map<String, Object> b(JSONObject jSONObject) {
            return (Map) Proxy.newProxyInstance(GraphObject.class.getClassLoader(), new Class[]{Map.class}, new GraphObjectProxy(jSONObject, Map.class));
        }

        private static synchronized <T extends GraphObject> boolean a(Class<T> cls) {
            boolean contains;
            synchronized (Factory.class) {
                contains = a.contains(cls);
            }
            return contains;
        }

        private static synchronized <T extends GraphObject> void b(Class<T> cls) {
            synchronized (Factory.class) {
                a.add(cls);
            }
        }

        private static <T extends GraphObject> void c(Class<T> cls) {
            Method[] methods;
            if (!a(cls)) {
                if (!cls.isInterface()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Factory can only wrap interfaces, not class: ");
                    sb.append(cls.getName());
                    throw new FacebookGraphObjectException(sb.toString());
                }
                for (Method method : cls.getMethods()) {
                    String name = method.getName();
                    int length = method.getParameterTypes().length;
                    Class returnType = method.getReturnType();
                    boolean isAnnotationPresent = method.isAnnotationPresent(PropertyName.class);
                    if (!method.getDeclaringClass().isAssignableFrom(GraphObject.class)) {
                        if (length == 1 && returnType == Void.TYPE) {
                            if (isAnnotationPresent) {
                                if (!Utility.isNullOrEmpty(((PropertyName) method.getAnnotation(PropertyName.class)).value())) {
                                }
                            } else if (name.startsWith("set") && name.length() > 3) {
                            }
                        } else if (length == 0 && returnType != Void.TYPE) {
                            if (isAnnotationPresent) {
                                if (!Utility.isNullOrEmpty(((PropertyName) method.getAnnotation(PropertyName.class)).value())) {
                                }
                            } else if (name.startsWith("get") && name.length() > 3) {
                            }
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Factory can't proxy method: ");
                        sb2.append(method.toString());
                        throw new FacebookGraphObjectException(sb2.toString());
                    }
                }
                b(cls);
            }
        }

        static <U> U a(Object obj, Class<U> cls, ParameterizedType parameterizedType) {
            int i = 0;
            if (obj != null) {
                Class cls2 = obj.getClass();
                if (cls.isAssignableFrom(cls2) || cls.isPrimitive()) {
                    return obj;
                }
                if (GraphObject.class.isAssignableFrom(cls)) {
                    if (JSONObject.class.isAssignableFrom(cls2)) {
                        return b(cls, (JSONObject) obj);
                    }
                    if (GraphObject.class.isAssignableFrom(cls2)) {
                        return ((GraphObject) obj).cast(cls);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Can't create GraphObject from ");
                    sb.append(cls2.getName());
                    throw new FacebookGraphObjectException(sb.toString());
                } else if (!Iterable.class.equals(cls) && !Collection.class.equals(cls) && !List.class.equals(cls) && !GraphObjectList.class.equals(cls)) {
                    if (String.class.equals(cls)) {
                        if (Double.class.isAssignableFrom(cls2) || Float.class.isAssignableFrom(cls2)) {
                            return String.format("%f", new Object[]{obj});
                        } else if (Number.class.isAssignableFrom(cls2)) {
                            return String.format("%d", new Object[]{obj});
                        }
                    } else if (Date.class.equals(cls) && String.class.isAssignableFrom(cls2)) {
                        SimpleDateFormat[] simpleDateFormatArr = b;
                        int length = simpleDateFormatArr.length;
                        while (i < length) {
                            try {
                                U parse = simpleDateFormatArr[i].parse((String) obj);
                                if (parse != null) {
                                    return parse;
                                }
                                i++;
                            } catch (ParseException unused) {
                            }
                        }
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Can't convert type");
                    sb2.append(cls2.getName());
                    sb2.append(" to ");
                    sb2.append(cls.getName());
                    throw new FacebookGraphObjectException(sb2.toString());
                } else if (parameterizedType == null) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("can't infer generic type of: ");
                    sb3.append(cls.toString());
                    throw new FacebookGraphObjectException(sb3.toString());
                } else {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (actualTypeArguments == null || actualTypeArguments.length != 1 || !(actualTypeArguments[0] instanceof Class)) {
                        throw new FacebookGraphObjectException("Expect collection properties to be of a type with exactly one generic parameter.");
                    }
                    Class cls3 = (Class) actualTypeArguments[0];
                    if (JSONArray.class.isAssignableFrom(cls2)) {
                        return createList((JSONArray) obj, cls3);
                    }
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Can't create Collection from ");
                    sb4.append(cls2.getName());
                    throw new FacebookGraphObjectException(sb4.toString());
                }
            } else if (Boolean.TYPE.equals(cls)) {
                return Boolean.valueOf(false);
            } else {
                if (Character.TYPE.equals(cls)) {
                    return Character.valueOf(0);
                }
                if (cls.isPrimitive()) {
                    return Integer.valueOf(0);
                }
                return null;
            }
        }

        static String a(String str) {
            return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(Locale.US);
        }

        /* access modifiers changed from: private */
        public static Object b(Object obj) {
            if (obj == null) {
                return null;
            }
            Class cls = obj.getClass();
            if (GraphObject.class.isAssignableFrom(cls)) {
                return ((GraphObject) obj).getInnerJSONObject();
            }
            if (GraphObjectList.class.isAssignableFrom(cls)) {
                return ((GraphObjectList) obj).getInnerJSONArray();
            }
            if (!Iterable.class.isAssignableFrom(cls)) {
                return obj;
            }
            JSONArray jSONArray = new JSONArray();
            for (Object next : (Iterable) obj) {
                if (GraphObject.class.isAssignableFrom(next.getClass())) {
                    jSONArray.put(((GraphObject) next).getInnerJSONObject());
                } else {
                    jSONArray.put(next);
                }
            }
            return jSONArray;
        }
    }

    Map<String, Object> asMap();

    <T extends GraphObject> T cast(Class<T> cls);

    JSONObject getInnerJSONObject();

    Object getProperty(String str);

    <T extends GraphObject> T getPropertyAs(String str, Class<T> cls);

    <T extends GraphObject> GraphObjectList<T> getPropertyAsList(String str, Class<T> cls);

    void removeProperty(String str);

    void setProperty(String str, Object obj);
}
