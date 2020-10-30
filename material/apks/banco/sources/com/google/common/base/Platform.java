package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class Platform {
    private static final Logger a = Logger.getLogger(Platform.class.getName());
    private static final PatternCompiler b = b();

    static final class JdkPatternCompiler implements PatternCompiler {
        private JdkPatternCompiler() {
        }

        public CommonPattern a(String str) {
            return new JdkPattern(Pattern.compile(str));
        }
    }

    private Platform() {
    }

    static long a() {
        return System.nanoTime();
    }

    static CharMatcher a(CharMatcher charMatcher) {
        return charMatcher.a();
    }

    static <T extends Enum<T>> Optional<T> a(Class<T> cls, String str) {
        WeakReference weakReference = (WeakReference) Enums.a(cls).get(str);
        return weakReference == null ? Optional.absent() : Optional.of(cls.cast(weakReference.get()));
    }

    static String a(double d) {
        return String.format(Locale.ROOT, "%.4g", new Object[]{Double.valueOf(d)});
    }

    static boolean a(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    static CommonPattern b(String str) {
        Preconditions.checkNotNull(str);
        return b.a(str);
    }

    private static PatternCompiler b() {
        return new JdkPatternCompiler();
    }
}
