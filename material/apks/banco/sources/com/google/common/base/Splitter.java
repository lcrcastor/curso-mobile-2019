package com.google.common.base;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@GwtCompatible(emulated = true)
public final class Splitter {
    /* access modifiers changed from: private */
    public final CharMatcher a;
    /* access modifiers changed from: private */
    public final boolean b;
    private final Strategy c;
    /* access modifiers changed from: private */
    public final int d;

    @Beta
    public static final class MapSplitter {
        private final Splitter a;
        private final Splitter b;

        private MapSplitter(Splitter splitter, Splitter splitter2) {
            this.a = splitter;
            this.b = (Splitter) Preconditions.checkNotNull(splitter2);
        }

        public Map<String, String> split(CharSequence charSequence) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (String str : this.a.split(charSequence)) {
                Iterator a2 = this.b.a((CharSequence) str);
                Preconditions.checkArgument(a2.hasNext(), "Chunk [%s] is not a valid entry", (Object) str);
                String str2 = (String) a2.next();
                Preconditions.checkArgument(!linkedHashMap.containsKey(str2), "Duplicate key [%s] found.", (Object) str2);
                Preconditions.checkArgument(a2.hasNext(), "Chunk [%s] is not a valid entry", (Object) str);
                linkedHashMap.put(str2, (String) a2.next());
                Preconditions.checkArgument(!a2.hasNext(), "Chunk [%s] is not a valid entry", (Object) str);
            }
            return Collections.unmodifiableMap(linkedHashMap);
        }
    }

    static abstract class SplittingIterator extends AbstractIterator<String> {
        final CharSequence c;
        final CharMatcher d;
        final boolean e;
        int f = 0;
        int g;

        /* access modifiers changed from: 0000 */
        public abstract int a(int i);

        /* access modifiers changed from: 0000 */
        public abstract int b(int i);

        protected SplittingIterator(Splitter splitter, CharSequence charSequence) {
            this.d = splitter.a;
            this.e = splitter.b;
            this.g = splitter.d;
            this.c = charSequence;
        }

        /* access modifiers changed from: protected */
        /* renamed from: c */
        public String a() {
            int i;
            int i2 = this.f;
            while (this.f != -1) {
                int a = a(this.f);
                if (a == -1) {
                    a = this.c.length();
                    this.f = -1;
                } else {
                    this.f = b(a);
                }
                if (this.f == i2) {
                    this.f++;
                    if (this.f >= this.c.length()) {
                        this.f = -1;
                    }
                } else {
                    while (i2 < a && this.d.matches(this.c.charAt(i2))) {
                        i2++;
                    }
                    while (i > i2 && this.d.matches(this.c.charAt(i - 1))) {
                        a = i - 1;
                    }
                    if (!this.e || i2 != i) {
                        if (this.g == 1) {
                            i = this.c.length();
                            this.f = -1;
                            while (i > i2 && this.d.matches(this.c.charAt(i - 1))) {
                                i--;
                            }
                        } else {
                            this.g--;
                        }
                        return this.c.subSequence(i2, i).toString();
                    }
                    i2 = this.f;
                }
            }
            return (String) b();
        }
    }

    interface Strategy {
        Iterator<String> b(Splitter splitter, CharSequence charSequence);
    }

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), SubsamplingScaleImageView.TILE_SIZE_AUTO);
    }

    private Splitter(Strategy strategy, boolean z, CharMatcher charMatcher, int i) {
        this.c = strategy;
        this.b = z;
        this.a = charMatcher;
        this.d = i;
    }

    public static Splitter on(char c2) {
        return on(CharMatcher.is(c2));
    }

    public static Splitter on(final CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(new Strategy() {
            /* renamed from: a */
            public SplittingIterator b(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    /* access modifiers changed from: 0000 */
                    public int b(int i) {
                        return i + 1;
                    }

                    /* access modifiers changed from: 0000 */
                    public int a(int i) {
                        return charMatcher.indexIn(this.c, i);
                    }
                };
            }
        });
    }

    public static Splitter on(final String str) {
        Preconditions.checkArgument(str.length() != 0, "The separator may not be the empty string.");
        if (str.length() == 1) {
            return on(str.charAt(0));
        }
        return new Splitter(new Strategy() {
            /* renamed from: a */
            public SplittingIterator b(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    public int a(int i) {
                        int length = str.length();
                        int length2 = this.c.length() - length;
                        while (i <= length2) {
                            int i2 = 0;
                            while (i2 < length) {
                                if (this.c.charAt(i2 + i) != str.charAt(i2)) {
                                    i++;
                                } else {
                                    i2++;
                                }
                            }
                            return i;
                        }
                        return -1;
                    }

                    public int b(int i) {
                        return i + str.length();
                    }
                };
            }
        });
    }

    @GwtIncompatible
    public static Splitter on(Pattern pattern) {
        return a((CommonPattern) new JdkPattern(pattern));
    }

    private static Splitter a(final CommonPattern commonPattern) {
        Preconditions.checkArgument(!commonPattern.a("").a(), "The pattern may not match the empty string: %s", (Object) commonPattern);
        return new Splitter(new Strategy() {
            /* renamed from: a */
            public SplittingIterator b(Splitter splitter, CharSequence charSequence) {
                final CommonMatcher a2 = commonPattern.a(charSequence);
                return new SplittingIterator(splitter, charSequence) {
                    public int a(int i) {
                        if (a2.a(i)) {
                            return a2.d();
                        }
                        return -1;
                    }

                    public int b(int i) {
                        return a2.c();
                    }
                };
            }
        });
    }

    @GwtIncompatible
    public static Splitter onPattern(String str) {
        return a(Platform.b(str));
    }

    public static Splitter fixedLength(final int i) {
        Preconditions.checkArgument(i > 0, "The length may not be less than 1");
        return new Splitter(new Strategy() {
            /* renamed from: a */
            public SplittingIterator b(Splitter splitter, CharSequence charSequence) {
                return new SplittingIterator(splitter, charSequence) {
                    public int b(int i) {
                        return i;
                    }

                    public int a(int i) {
                        int i2 = i + i;
                        if (i2 < this.c.length()) {
                            return i2;
                        }
                        return -1;
                    }
                };
            }
        });
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(this.c, true, this.a, this.d);
    }

    public Splitter limit(int i) {
        Preconditions.checkArgument(i > 0, "must be greater than zero: %s", i);
        return new Splitter(this.c, this.b, this.a, i);
    }

    public Splitter trimResults() {
        return trimResults(CharMatcher.whitespace());
    }

    public Splitter trimResults(CharMatcher charMatcher) {
        Preconditions.checkNotNull(charMatcher);
        return new Splitter(this.c, this.b, charMatcher, this.d);
    }

    public Iterable<String> split(final CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return Splitter.this.a(charSequence);
            }

            public String toString() {
                Joiner on = Joiner.on(", ");
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                StringBuilder appendTo = on.appendTo(sb, (Iterable<?>) this);
                appendTo.append(']');
                return appendTo.toString();
            }
        };
    }

    /* access modifiers changed from: private */
    public Iterator<String> a(CharSequence charSequence) {
        return this.c.b(this, charSequence);
    }

    @Beta
    public List<String> splitToList(CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        Iterator a2 = a(charSequence);
        ArrayList arrayList = new ArrayList();
        while (a2.hasNext()) {
            arrayList.add(a2.next());
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Beta
    public MapSplitter withKeyValueSeparator(String str) {
        return withKeyValueSeparator(on(str));
    }

    @Beta
    public MapSplitter withKeyValueSeparator(char c2) {
        return withKeyValueSeparator(on(c2));
    }

    @Beta
    public MapSplitter withKeyValueSeparator(Splitter splitter) {
        return new MapSplitter(splitter);
    }
}
