package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@GwtIncompatible
final class JdkPattern extends CommonPattern implements Serializable {
    private static final long serialVersionUID = 0;
    private final Pattern a;

    static final class JdkMatcher extends CommonMatcher {
        final Matcher a;

        JdkMatcher(Matcher matcher) {
            this.a = (Matcher) Preconditions.checkNotNull(matcher);
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.a.matches();
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return this.a.find();
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i) {
            return this.a.find(i);
        }

        /* access modifiers changed from: 0000 */
        public int c() {
            return this.a.end();
        }

        /* access modifiers changed from: 0000 */
        public int d() {
            return this.a.start();
        }
    }

    JdkPattern(Pattern pattern) {
        this.a = (Pattern) Preconditions.checkNotNull(pattern);
    }

    /* access modifiers changed from: 0000 */
    public CommonMatcher a(CharSequence charSequence) {
        return new JdkMatcher(this.a.matcher(charSequence));
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        return this.a.pattern();
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.a.flags();
    }

    public String toString() {
        return this.a.toString();
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof JdkPattern)) {
            return false;
        }
        return this.a.equals(((JdkPattern) obj).a);
    }
}
