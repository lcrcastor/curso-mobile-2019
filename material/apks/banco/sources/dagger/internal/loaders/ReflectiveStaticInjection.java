package dagger.internal.loaders;

import dagger.internal.Binding;
import dagger.internal.Keys;
import dagger.internal.Linker;
import dagger.internal.StaticInjection;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import javax.inject.Inject;

public final class ReflectiveStaticInjection extends StaticInjection {
    private final ClassLoader a;
    private final Field[] b;
    private Binding<?>[] c;

    private ReflectiveStaticInjection(ClassLoader classLoader, Field[] fieldArr) {
        this.b = fieldArr;
        this.a = classLoader;
    }

    public void attach(Linker linker) {
        this.c = new Binding[this.b.length];
        for (int i = 0; i < this.b.length; i++) {
            Field field = this.b[i];
            this.c[i] = linker.requestBinding(Keys.get(field.getGenericType(), field.getAnnotations(), field), field, this.a);
        }
    }

    public void inject() {
        int i = 0;
        while (i < this.b.length) {
            try {
                this.b[i].set(null, this.c[i].get());
                i++;
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }

    public static StaticInjection create(Class<?> cls) {
        Field[] declaredFields;
        ArrayList arrayList = new ArrayList();
        for (Field field : cls.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                arrayList.add(field);
            }
        }
        if (!arrayList.isEmpty()) {
            return new ReflectiveStaticInjection(cls.getClassLoader(), (Field[]) arrayList.toArray(new Field[arrayList.size()]));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No static injections: ");
        sb.append(cls.getName());
        throw new IllegalArgumentException(sb.toString());
    }
}
