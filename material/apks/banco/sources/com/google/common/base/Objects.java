package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import javax.annotation.Nullable;

@GwtCompatible
public final class Objects extends ExtraObjectsMethodsForWeb {

    @Deprecated
    public static final class ToStringHelper {
        private final String a;
        private final ValueHolder b;
        private ValueHolder c;
        private boolean d;

        static final class ValueHolder {
            String a;
            Object b;
            ValueHolder c;

            private ValueHolder() {
            }
        }

        private ToStringHelper(String str) {
            this.b = new ValueHolder();
            this.c = this.b;
            this.d = false;
            this.a = (String) Preconditions.checkNotNull(str);
        }

        @CanIgnoreReturnValue
        public ToStringHelper omitNullValues() {
            this.d = true;
            return this;
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, @Nullable Object obj) {
            return a(str, obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, boolean z) {
            return a(str, String.valueOf(z));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, char c2) {
            return a(str, String.valueOf(c2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, double d2) {
            return a(str, String.valueOf(d2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, float f) {
            return a(str, String.valueOf(f));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, int i) {
            return a(str, String.valueOf(i));
        }

        @CanIgnoreReturnValue
        public ToStringHelper add(String str, long j) {
            return a(str, String.valueOf(j));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(@Nullable Object obj) {
            return a(obj);
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(boolean z) {
            return a(String.valueOf(z));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(char c2) {
            return a(String.valueOf(c2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(double d2) {
            return a(String.valueOf(d2));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(float f) {
            return a(String.valueOf(f));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(int i) {
            return a(String.valueOf(i));
        }

        @CanIgnoreReturnValue
        public ToStringHelper addValue(long j) {
            return a(String.valueOf(j));
        }

        public String toString() {
            boolean z = this.d;
            String str = "";
            StringBuilder sb = new StringBuilder(32);
            sb.append(this.a);
            sb.append('{');
            for (ValueHolder valueHolder = this.b.c; valueHolder != null; valueHolder = valueHolder.c) {
                if (!z || valueHolder.b != null) {
                    sb.append(str);
                    str = ", ";
                    if (valueHolder.a != null) {
                        sb.append(valueHolder.a);
                        sb.append('=');
                    }
                    sb.append(valueHolder.b);
                }
            }
            sb.append('}');
            return sb.toString();
        }

        private ValueHolder a() {
            ValueHolder valueHolder = new ValueHolder();
            this.c.c = valueHolder;
            this.c = valueHolder;
            return valueHolder;
        }

        private ToStringHelper a(@Nullable Object obj) {
            a().b = obj;
            return this;
        }

        private ToStringHelper a(String str, @Nullable Object obj) {
            ValueHolder a2 = a();
            a2.b = obj;
            a2.a = (String) Preconditions.checkNotNull(str);
            return this;
        }
    }

    private Objects() {
    }

    public static boolean equal(@Nullable Object obj, @Nullable Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static int hashCode(@Nullable Object... objArr) {
        return Arrays.hashCode(objArr);
    }

    @Deprecated
    public static ToStringHelper toStringHelper(Object obj) {
        return new ToStringHelper(obj.getClass().getSimpleName());
    }

    @Deprecated
    public static ToStringHelper toStringHelper(Class<?> cls) {
        return new ToStringHelper(cls.getSimpleName());
    }

    @Deprecated
    public static ToStringHelper toStringHelper(String str) {
        return new ToStringHelper(str);
    }

    @Deprecated
    public static <T> T firstNonNull(@Nullable T t, @Nullable T t2) {
        return MoreObjects.firstNonNull(t, t2);
    }
}
