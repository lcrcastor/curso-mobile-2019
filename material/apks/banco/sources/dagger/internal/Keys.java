package dagger.internal;

import butterknife.internal.ButterKnifeProcessor;
import dagger.Lazy;
import dagger.MembersInjector;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;
import javax.inject.Provider;
import javax.inject.Qualifier;

public final class Keys {
    private static final String a;
    private static final String b;
    private static final String c;
    private static final String d;
    private static final Memoizer<Class<? extends Annotation>, Boolean> e = new Memoizer<Class<? extends Annotation>, Boolean>() {
        /* access modifiers changed from: protected */
        public Boolean a(Class<? extends Annotation> cls) {
            return Boolean.valueOf(cls.isAnnotationPresent(Qualifier.class));
        }
    };

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Provider.class.getCanonicalName());
        sb.append("<");
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(MembersInjector.class.getCanonicalName());
        sb2.append("<");
        b = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(Lazy.class.getCanonicalName());
        sb3.append("<");
        c = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(Set.class.getCanonicalName());
        sb4.append("<");
        d = sb4.toString();
    }

    Keys() {
    }

    public static String get(Type type) {
        return a(type, (Annotation) null);
    }

    public static String getMembersKey(Class<?> cls) {
        return "members/".concat(cls.getName());
    }

    private static String a(Type type, Annotation annotation) {
        Type a2 = a(type);
        if (annotation == null && (a2 instanceof Class)) {
            Class cls = (Class) a2;
            if (!cls.isArray()) {
                return cls.getName();
            }
        }
        StringBuilder sb = new StringBuilder();
        if (annotation != null) {
            sb.append(annotation);
            sb.append("/");
        }
        a(a2, sb, true);
        return sb.toString();
    }

    public static String getSetKey(Type type, Annotation[] annotationArr, Object obj) {
        Annotation a2 = a(annotationArr, obj);
        Type a3 = a(type);
        StringBuilder sb = new StringBuilder();
        if (a2 != null) {
            sb.append(a2);
            sb.append("/");
        }
        sb.append(d);
        a(a3, sb, true);
        sb.append(">");
        return sb.toString();
    }

    public static String get(Type type, Annotation[] annotationArr, Object obj) {
        return a(type, a(annotationArr, obj));
    }

    private static Annotation a(Annotation[] annotationArr, Object obj) {
        Annotation annotation = null;
        for (Annotation annotation2 : annotationArr) {
            if (((Boolean) e.b(annotation2.annotationType())).booleanValue()) {
                if (annotation != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Too many qualifier annotations on ");
                    sb.append(obj);
                    throw new IllegalArgumentException(sb.toString());
                }
                annotation = annotation2;
            }
        }
        return annotation;
    }

    private static void a(Type type, StringBuilder sb, boolean z) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            if (cls.isArray()) {
                a((Type) cls.getComponentType(), sb, false);
                sb.append("[]");
            } else if (!cls.isPrimitive()) {
                sb.append(cls.getName());
            } else if (z) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Uninjectable type ");
                sb2.append(cls.getName());
                throw new UnsupportedOperationException(sb2.toString());
            } else {
                sb.append(cls.getName());
            }
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            a(parameterizedType.getRawType(), sb, true);
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            sb.append("<");
            for (int i = 0; i < actualTypeArguments.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                a(actualTypeArguments[i], sb, true);
            }
            sb.append(">");
        } else if (type instanceof GenericArrayType) {
            a(((GenericArrayType) type).getGenericComponentType(), sb, false);
            sb.append("[]");
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Uninjectable type ");
            sb3.append(type);
            throw new UnsupportedOperationException(sb3.toString());
        }
    }

    static String a(String str) {
        int c2 = c(str);
        if (a(str, c2, a)) {
            return a(str, c2, str.substring(0, c2), a);
        }
        if (a(str, c2, b)) {
            return a(str, c2, "members/", b);
        }
        return null;
    }

    static String b(String str) {
        int c2 = c(str);
        if (a(str, c2, c)) {
            return a(str, c2, str.substring(0, c2), c);
        }
        return null;
    }

    private static int c(String str) {
        if (str.startsWith("@")) {
            return str.lastIndexOf(47) + 1;
        }
        return 0;
    }

    private static String a(String str, int i, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str.substring(i + str3.length(), str.length() - 1));
        return sb.toString();
    }

    private static boolean a(String str, int i, String str2) {
        return str.regionMatches(i, str2, 0, str2.length());
    }

    public static boolean isAnnotated(String str) {
        return str.startsWith("@");
    }

    public static String getClassName(String str) {
        int i;
        if (str.startsWith("@") || str.startsWith("members/")) {
            i = str.lastIndexOf(47) + 1;
        } else {
            i = 0;
        }
        if (str.indexOf(60, i) == -1 && str.indexOf(91, i) == -1) {
            return str.substring(i);
        }
        return null;
    }

    public static boolean isPlatformType(String str) {
        return str.startsWith(ButterKnifeProcessor.JAVA_PREFIX) || str.startsWith("javax.") || str.startsWith(ButterKnifeProcessor.ANDROID_PREFIX);
    }

    private static Type a(Type type) {
        if (type == Byte.TYPE) {
            return Byte.class;
        }
        if (type == Short.TYPE) {
            return Short.class;
        }
        if (type == Integer.TYPE) {
            return Integer.class;
        }
        if (type == Long.TYPE) {
            return Long.class;
        }
        if (type == Character.TYPE) {
            return Character.class;
        }
        if (type == Boolean.TYPE) {
            return Boolean.class;
        }
        if (type == Float.TYPE) {
            return Float.class;
        }
        if (type == Double.TYPE) {
            return Double.class;
        }
        return type == Void.TYPE ? Void.class : type;
    }
}
