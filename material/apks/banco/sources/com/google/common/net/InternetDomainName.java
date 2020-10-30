package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
public final class InternetDomainName {
    private static final CharMatcher a = CharMatcher.anyOf(".。．｡");
    private static final Splitter b = Splitter.on('.');
    private static final Joiner c = Joiner.on('.');
    private static final CharMatcher g = CharMatcher.anyOf("-_");
    private static final CharMatcher h = CharMatcher.javaLetterOrDigit().or(g);
    private final String d;
    private final ImmutableList<String> e;
    private final int f;

    InternetDomainName(String str) {
        String lowerCase = Ascii.toLowerCase(a.replaceFrom((CharSequence) str, '.'));
        boolean z = true;
        if (lowerCase.endsWith(".")) {
            lowerCase = lowerCase.substring(0, lowerCase.length() - 1);
        }
        Preconditions.checkArgument(lowerCase.length() <= 253, "Domain name too long: '%s':", (Object) lowerCase);
        this.d = lowerCase;
        this.e = ImmutableList.copyOf(b.split(lowerCase));
        if (this.e.size() > 127) {
            z = false;
        }
        Preconditions.checkArgument(z, "Domain has too many parts: '%s'", (Object) lowerCase);
        Preconditions.checkArgument(a((List<String>) this.e), "Not a valid domain name: '%s'", (Object) lowerCase);
        this.f = a();
    }

    private int a() {
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            String join = c.join((Iterable<?>) this.e.subList(i, size));
            if (PublicSuffixPatterns.EXACT.containsKey(join)) {
                return i;
            }
            if (PublicSuffixPatterns.EXCLUDED.containsKey(join)) {
                return i + 1;
            }
            if (a(join)) {
                return i;
            }
        }
        return -1;
    }

    public static InternetDomainName from(String str) {
        return new InternetDomainName((String) Preconditions.checkNotNull(str));
    }

    private static boolean a(List<String> list) {
        int size = list.size() - 1;
        if (!a((String) list.get(size), true)) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!a((String) list.get(i), false)) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(String str, boolean z) {
        if (str.length() < 1 || str.length() > 63) {
            return false;
        }
        if (h.matchesAllOf(CharMatcher.ascii().retainFrom(str)) && !g.matches(str.charAt(0)) && !g.matches(str.charAt(str.length() - 1))) {
            return !z || !CharMatcher.digit().matches(str.charAt(0));
        }
        return false;
    }

    public ImmutableList<String> parts() {
        return this.e;
    }

    public boolean isPublicSuffix() {
        return this.f == 0;
    }

    public boolean hasPublicSuffix() {
        return this.f != -1;
    }

    public InternetDomainName publicSuffix() {
        if (hasPublicSuffix()) {
            return a(this.f);
        }
        return null;
    }

    public boolean isUnderPublicSuffix() {
        return this.f > 0;
    }

    public boolean isTopPrivateDomain() {
        return this.f == 1;
    }

    public InternetDomainName topPrivateDomain() {
        if (isTopPrivateDomain()) {
            return this;
        }
        Preconditions.checkState(isUnderPublicSuffix(), "Not under a public suffix: %s", (Object) this.d);
        return a(this.f - 1);
    }

    public boolean hasParent() {
        return this.e.size() > 1;
    }

    public InternetDomainName parent() {
        Preconditions.checkState(hasParent(), "Domain '%s' has no parent", (Object) this.d);
        return a(1);
    }

    private InternetDomainName a(int i) {
        return from(c.join((Iterable<?>) this.e.subList(i, this.e.size())));
    }

    public InternetDomainName child(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append((String) Preconditions.checkNotNull(str));
        sb.append(".");
        sb.append(this.d);
        return from(sb.toString());
    }

    public static boolean isValid(String str) {
        try {
            from(str);
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    private static boolean a(String str) {
        String[] split = str.split("\\.", 2);
        return split.length == 2 && PublicSuffixPatterns.UNDER.containsKey(split[1]);
    }

    public String toString() {
        return this.d;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof InternetDomainName)) {
            return false;
        }
        return this.d.equals(((InternetDomainName) obj).d);
    }

    public int hashCode() {
        return this.d.hashCode();
    }
}
