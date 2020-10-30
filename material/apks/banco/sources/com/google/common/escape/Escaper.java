package com.google.common.escape;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;

@GwtCompatible
public abstract class Escaper {
    private final Function<String, String> a = new Function<String, String>() {
        /* renamed from: a */
        public String apply(String str) {
            return Escaper.this.escape(str);
        }
    };

    public abstract String escape(String str);

    protected Escaper() {
    }

    public final Function<String, String> asFunction() {
        return this.a;
    }
}
