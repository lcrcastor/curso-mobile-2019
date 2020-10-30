package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.Arrays;
import java.util.BitSet;

@GwtCompatible(emulated = true)
public abstract class CharMatcher implements Predicate<Character> {
    @Deprecated
    public static final CharMatcher ANY = any();
    @Deprecated
    public static final CharMatcher ASCII = ascii();
    @Deprecated
    public static final CharMatcher BREAKING_WHITESPACE = breakingWhitespace();
    @Deprecated
    public static final CharMatcher DIGIT = digit();
    @Deprecated
    public static final CharMatcher INVISIBLE = invisible();
    @Deprecated
    public static final CharMatcher JAVA_DIGIT = javaDigit();
    @Deprecated
    public static final CharMatcher JAVA_ISO_CONTROL = javaIsoControl();
    @Deprecated
    public static final CharMatcher JAVA_LETTER = javaLetter();
    @Deprecated
    public static final CharMatcher JAVA_LETTER_OR_DIGIT = javaLetterOrDigit();
    @Deprecated
    public static final CharMatcher JAVA_LOWER_CASE = javaLowerCase();
    @Deprecated
    public static final CharMatcher JAVA_UPPER_CASE = javaUpperCase();
    @Deprecated
    public static final CharMatcher NONE = none();
    @Deprecated
    public static final CharMatcher SINGLE_WIDTH = singleWidth();
    @Deprecated
    public static final CharMatcher WHITESPACE = whitespace();

    static final class And extends CharMatcher {
        final CharMatcher a;
        final CharMatcher b;

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        And(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.a = (CharMatcher) Preconditions.checkNotNull(charMatcher);
            this.b = (CharMatcher) Preconditions.checkNotNull(charMatcher2);
        }

        public boolean matches(char c) {
            return this.a.matches(c) && this.b.matches(c);
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.a.a(bitSet2);
            BitSet bitSet3 = new BitSet();
            this.b.a(bitSet3);
            bitSet2.and(bitSet3);
            bitSet.or(bitSet2);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharMatcher.and(");
            sb.append(this.a);
            sb.append(", ");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class Any extends NamedFastMatcher {
        static final Any a = new Any();

        public boolean matches(char c) {
            return true;
        }

        private Any() {
            super("CharMatcher.any()");
        }

        public int indexIn(CharSequence charSequence) {
            return charSequence.length() == 0 ? -1 : 0;
        }

        public int indexIn(CharSequence charSequence, int i) {
            int length = charSequence.length();
            Preconditions.checkPositionIndex(i, length);
            if (i == length) {
                return -1;
            }
            return i;
        }

        public int lastIndexIn(CharSequence charSequence) {
            return charSequence.length() - 1;
        }

        public boolean matchesAllOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        public boolean matchesNoneOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        public String removeFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        public String replaceFrom(CharSequence charSequence, char c) {
            char[] cArr = new char[charSequence.length()];
            Arrays.fill(cArr, c);
            return new String(cArr);
        }

        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            StringBuilder sb = new StringBuilder(charSequence.length() * charSequence2.length());
            for (int i = 0; i < charSequence.length(); i++) {
                sb.append(charSequence2);
            }
            return sb.toString();
        }

        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.length() == 0 ? "" : String.valueOf(c);
        }

        public String trimFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        public int countIn(CharSequence charSequence) {
            return charSequence.length();
        }

        public CharMatcher and(CharMatcher charMatcher) {
            return (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        public CharMatcher or(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        public CharMatcher negate() {
            return none();
        }
    }

    static final class AnyOf extends CharMatcher {
        private final char[] a;

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public AnyOf(CharSequence charSequence) {
            this.a = charSequence.toString().toCharArray();
            Arrays.sort(this.a);
        }

        public boolean matches(char c) {
            return Arrays.binarySearch(this.a, c) >= 0;
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            for (char c : this.a) {
                bitSet.set(c);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("CharMatcher.anyOf(\"");
            for (char a2 : this.a) {
                sb.append(CharMatcher.b(a2));
            }
            sb.append("\")");
            return sb.toString();
        }
    }

    static final class Ascii extends NamedFastMatcher {
        static final Ascii a = new Ascii();

        public boolean matches(char c) {
            return c <= 127;
        }

        Ascii() {
            super("CharMatcher.ascii()");
        }
    }

    @GwtIncompatible
    static final class BitSetMatcher extends NamedFastMatcher {
        private final BitSet a;

        private BitSetMatcher(BitSet bitSet, String str) {
            super(str);
            if (bitSet.length() + 64 < bitSet.size()) {
                bitSet = (BitSet) bitSet.clone();
            }
            this.a = bitSet;
        }

        public boolean matches(char c) {
            return this.a.get(c);
        }

        /* access modifiers changed from: 0000 */
        public void a(BitSet bitSet) {
            bitSet.or(this.a);
        }
    }

    static final class BreakingWhitespace extends CharMatcher {
        static final CharMatcher a = new BreakingWhitespace();

        public boolean matches(char c) {
            boolean z = true;
            if (!(c == ' ' || c == 133 || c == 5760)) {
                if (c == 8199) {
                    return false;
                }
                if (!(c == 8287 || c == 12288)) {
                    switch (c) {
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            break;
                        default:
                            switch (c) {
                                case 8232:
                                case 8233:
                                    break;
                                default:
                                    if (c < 8192 || c > 8202) {
                                        z = false;
                                    }
                                    return z;
                            }
                    }
                }
            }
            return true;
        }

        public String toString() {
            return "CharMatcher.breakingWhitespace()";
        }

        private BreakingWhitespace() {
        }

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }
    }

    static final class Digit extends RangesMatcher {
        static final Digit a = new Digit();

        private static char[] b() {
            return "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".toCharArray();
        }

        private static char[] c() {
            char[] cArr = new char["0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".length()];
            for (int i = 0; i < "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".length(); i++) {
                cArr[i] = (char) ("0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".charAt(i) + 9);
            }
            return cArr;
        }

        private Digit() {
            super("CharMatcher.digit()", b(), c());
        }
    }

    static abstract class FastMatcher extends CharMatcher {
        public final CharMatcher precomputed() {
            return this;
        }

        FastMatcher() {
        }

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }
    }

    static final class ForPredicate extends CharMatcher {
        private final Predicate<? super Character> a;

        ForPredicate(Predicate<? super Character> predicate) {
            this.a = (Predicate) Preconditions.checkNotNull(predicate);
        }

        public boolean matches(char c) {
            return this.a.apply(Character.valueOf(c));
        }

        public boolean apply(Character ch) {
            return this.a.apply(Preconditions.checkNotNull(ch));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharMatcher.forPredicate(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class InRange extends FastMatcher {
        private final char a;
        private final char b;

        InRange(char c, char c2) {
            Preconditions.checkArgument(c2 >= c);
            this.a = c;
            this.b = c2;
        }

        public boolean matches(char c) {
            return this.a <= c && c <= this.b;
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            bitSet.set(this.a, this.b + 1);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharMatcher.inRange('");
            sb.append(CharMatcher.b(this.a));
            sb.append("', '");
            sb.append(CharMatcher.b(this.b));
            sb.append("')");
            return sb.toString();
        }
    }

    static final class Invisible extends RangesMatcher {
        static final Invisible a = new Invisible();

        private Invisible() {
            super("CharMatcher.invisible()", "\u0000­؀؜۝܏ ᠎   ⁦⁧⁨⁩⁪　?﻿￹￺".toCharArray(), "  ­؄؜۝܏ ᠎‏ ⁤⁦⁧⁨⁩⁯　﻿￹￻".toCharArray());
        }
    }

    static final class Is extends FastMatcher {
        private final char a;

        Is(char c) {
            this.a = c;
        }

        public boolean matches(char c) {
            return c == this.a;
        }

        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString().replace(this.a, c);
        }

        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.a) ? this : none();
        }

        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.a) ? charMatcher : super.or(charMatcher);
        }

        public CharMatcher negate() {
            return isNot(this.a);
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            bitSet.set(this.a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharMatcher.is('");
            sb.append(CharMatcher.b(this.a));
            sb.append("')");
            return sb.toString();
        }
    }

    static final class IsEither extends FastMatcher {
        private final char a;
        private final char b;

        IsEither(char c, char c2) {
            this.a = c;
            this.b = c2;
        }

        public boolean matches(char c) {
            return c == this.a || c == this.b;
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            bitSet.set(this.a);
            bitSet.set(this.b);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharMatcher.anyOf(\"");
            sb.append(CharMatcher.b(this.a));
            sb.append(CharMatcher.b(this.b));
            sb.append("\")");
            return sb.toString();
        }
    }

    static final class IsNot extends FastMatcher {
        private final char a;

        IsNot(char c) {
            this.a = c;
        }

        public boolean matches(char c) {
            return c != this.a;
        }

        public CharMatcher and(CharMatcher charMatcher) {
            return charMatcher.matches(this.a) ? super.and(charMatcher) : charMatcher;
        }

        public CharMatcher or(CharMatcher charMatcher) {
            return charMatcher.matches(this.a) ? any() : this;
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            bitSet.set(0, this.a);
            bitSet.set(this.a + 1, 65536);
        }

        public CharMatcher negate() {
            return is(this.a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharMatcher.isNot('");
            sb.append(CharMatcher.b(this.a));
            sb.append("')");
            return sb.toString();
        }
    }

    static final class JavaDigit extends CharMatcher {
        static final JavaDigit a = new JavaDigit();

        public String toString() {
            return "CharMatcher.javaDigit()";
        }

        private JavaDigit() {
        }

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c) {
            return Character.isDigit(c);
        }
    }

    static final class JavaIsoControl extends NamedFastMatcher {
        static final JavaIsoControl a = new JavaIsoControl();

        public boolean matches(char c) {
            return c <= 31 || (c >= 127 && c <= 159);
        }

        private JavaIsoControl() {
            super("CharMatcher.javaIsoControl()");
        }
    }

    static final class JavaLetter extends CharMatcher {
        static final JavaLetter a = new JavaLetter();

        public String toString() {
            return "CharMatcher.javaLetter()";
        }

        private JavaLetter() {
        }

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c) {
            return Character.isLetter(c);
        }
    }

    static final class JavaLetterOrDigit extends CharMatcher {
        static final JavaLetterOrDigit a = new JavaLetterOrDigit();

        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }

        private JavaLetterOrDigit() {
        }

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
        }
    }

    static final class JavaLowerCase extends CharMatcher {
        static final JavaLowerCase a = new JavaLowerCase();

        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }

        private JavaLowerCase() {
        }

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c) {
            return Character.isLowerCase(c);
        }
    }

    static final class JavaUpperCase extends CharMatcher {
        static final JavaUpperCase a = new JavaUpperCase();

        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }

        private JavaUpperCase() {
        }

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        public boolean matches(char c) {
            return Character.isUpperCase(c);
        }
    }

    static abstract class NamedFastMatcher extends FastMatcher {
        private final String a;

        NamedFastMatcher(String str) {
            this.a = (String) Preconditions.checkNotNull(str);
        }

        public final String toString() {
            return this.a;
        }
    }

    static class Negated extends CharMatcher {
        final CharMatcher c;

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        Negated(CharMatcher charMatcher) {
            this.c = (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        public boolean matches(char c2) {
            return !this.c.matches(c2);
        }

        public boolean matchesAllOf(CharSequence charSequence) {
            return this.c.matchesNoneOf(charSequence);
        }

        public boolean matchesNoneOf(CharSequence charSequence) {
            return this.c.matchesAllOf(charSequence);
        }

        public int countIn(CharSequence charSequence) {
            return charSequence.length() - this.c.countIn(charSequence);
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.c.a(bitSet2);
            bitSet2.flip(0, 65536);
            bitSet.or(bitSet2);
        }

        public CharMatcher negate() {
            return this.c;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.c);
            sb.append(".negate()");
            return sb.toString();
        }
    }

    static class NegatedFastMatcher extends Negated {
        public final CharMatcher precomputed() {
            return this;
        }

        NegatedFastMatcher(CharMatcher charMatcher) {
            super(charMatcher);
        }
    }

    static final class None extends NamedFastMatcher {
        static final None a = new None();

        public boolean matches(char c) {
            return false;
        }

        private None() {
            super("CharMatcher.none()");
        }

        public int indexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        public int indexIn(CharSequence charSequence, int i) {
            Preconditions.checkPositionIndex(i, charSequence.length());
            return -1;
        }

        public int lastIndexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        public boolean matchesAllOf(CharSequence charSequence) {
            return charSequence.length() == 0;
        }

        public boolean matchesNoneOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        public String removeFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            Preconditions.checkNotNull(charSequence2);
            return charSequence.toString();
        }

        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        public String trimFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        public String trimLeadingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        public String trimTrailingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        public int countIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return 0;
        }

        public CharMatcher and(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        public CharMatcher or(CharMatcher charMatcher) {
            return (CharMatcher) Preconditions.checkNotNull(charMatcher);
        }

        public CharMatcher negate() {
            return any();
        }
    }

    static final class Or extends CharMatcher {
        final CharMatcher a;
        final CharMatcher b;

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        Or(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.a = (CharMatcher) Preconditions.checkNotNull(charMatcher);
            this.b = (CharMatcher) Preconditions.checkNotNull(charMatcher2);
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            this.a.a(bitSet);
            this.b.a(bitSet);
        }

        public boolean matches(char c) {
            return this.a.matches(c) || this.b.matches(c);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharMatcher.or(");
            sb.append(this.a);
            sb.append(", ");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static class RangesMatcher extends CharMatcher {
        private final String a;
        private final char[] b;
        private final char[] c;

        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return CharMatcher.super.apply((Character) obj);
        }

        RangesMatcher(String str, char[] cArr, char[] cArr2) {
            this.a = str;
            this.b = cArr;
            this.c = cArr2;
            Preconditions.checkArgument(cArr.length == cArr2.length);
            int i = 0;
            while (i < cArr.length) {
                Preconditions.checkArgument(cArr[i] <= cArr2[i]);
                int i2 = i + 1;
                if (i2 < cArr.length) {
                    Preconditions.checkArgument(cArr2[i] < cArr[i2]);
                }
                i = i2;
            }
        }

        public boolean matches(char c2) {
            int binarySearch = Arrays.binarySearch(this.b, c2);
            boolean z = true;
            if (binarySearch >= 0) {
                return true;
            }
            int i = (binarySearch ^ -1) - 1;
            if (i < 0 || c2 > this.c[i]) {
                z = false;
            }
            return z;
        }

        public String toString() {
            return this.a;
        }
    }

    static final class SingleWidth extends RangesMatcher {
        static final SingleWidth a = new SingleWidth();

        private SingleWidth() {
            super("CharMatcher.singleWidth()", "\u0000־א׳؀ݐ฀Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ๿₯℺﷿﻿ￜ".toCharArray());
        }
    }

    @VisibleForTesting
    static final class Whitespace extends NamedFastMatcher {
        static final int a = Integer.numberOfLeadingZeros(" 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　".length() - 1);
        static final Whitespace b = new Whitespace();

        Whitespace() {
            super("CharMatcher.whitespace()");
        }

        public boolean matches(char c) {
            return " 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　".charAt((48906 * c) >>> a) == c;
        }

        /* access modifiers changed from: 0000 */
        @GwtIncompatible
        public void a(BitSet bitSet) {
            for (int i = 0; i < " 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　".length(); i++) {
                bitSet.set(" 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　".charAt(i));
            }
        }
    }

    @GwtIncompatible
    private static boolean a(int i, int i2) {
        return i <= 1023 && i2 > (i * 4) * 16;
    }

    public abstract boolean matches(char c);

    public static CharMatcher any() {
        return Any.a;
    }

    public static CharMatcher none() {
        return None.a;
    }

    public static CharMatcher whitespace() {
        return Whitespace.b;
    }

    public static CharMatcher breakingWhitespace() {
        return BreakingWhitespace.a;
    }

    public static CharMatcher ascii() {
        return Ascii.a;
    }

    public static CharMatcher digit() {
        return Digit.a;
    }

    public static CharMatcher javaDigit() {
        return JavaDigit.a;
    }

    public static CharMatcher javaLetter() {
        return JavaLetter.a;
    }

    public static CharMatcher javaLetterOrDigit() {
        return JavaLetterOrDigit.a;
    }

    public static CharMatcher javaUpperCase() {
        return JavaUpperCase.a;
    }

    public static CharMatcher javaLowerCase() {
        return JavaLowerCase.a;
    }

    public static CharMatcher javaIsoControl() {
        return JavaIsoControl.a;
    }

    public static CharMatcher invisible() {
        return Invisible.a;
    }

    public static CharMatcher singleWidth() {
        return SingleWidth.a;
    }

    public static CharMatcher is(char c) {
        return new Is(c);
    }

    public static CharMatcher isNot(char c) {
        return new IsNot(c);
    }

    public static CharMatcher anyOf(CharSequence charSequence) {
        switch (charSequence.length()) {
            case 0:
                return none();
            case 1:
                return is(charSequence.charAt(0));
            case 2:
                return a(charSequence.charAt(0), charSequence.charAt(1));
            default:
                return new AnyOf(charSequence);
        }
    }

    public static CharMatcher noneOf(CharSequence charSequence) {
        return anyOf(charSequence).negate();
    }

    public static CharMatcher inRange(char c, char c2) {
        return new InRange(c, c2);
    }

    public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
        return predicate instanceof CharMatcher ? (CharMatcher) predicate : new ForPredicate(predicate);
    }

    protected CharMatcher() {
    }

    public CharMatcher negate() {
        return new Negated(this);
    }

    public CharMatcher and(CharMatcher charMatcher) {
        return new And(this, charMatcher);
    }

    public CharMatcher or(CharMatcher charMatcher) {
        return new Or(this, charMatcher);
    }

    public CharMatcher precomputed() {
        return Platform.a(this);
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public CharMatcher a() {
        String str;
        BitSet bitSet = new BitSet();
        a(bitSet);
        int cardinality = bitSet.cardinality();
        if (cardinality * 2 <= 65536) {
            return a(cardinality, bitSet, toString());
        }
        bitSet.flip(0, 65536);
        int i = 65536 - cardinality;
        String str2 = ".negate()";
        final String charMatcher = toString();
        if (charMatcher.endsWith(str2)) {
            str = charMatcher.substring(0, charMatcher.length() - str2.length());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(charMatcher);
            sb.append(str2);
            str = sb.toString();
        }
        return new NegatedFastMatcher(a(i, bitSet, str)) {
            public String toString() {
                return charMatcher;
            }
        };
    }

    @GwtIncompatible
    private static CharMatcher a(int i, BitSet bitSet, String str) {
        switch (i) {
            case 0:
                return none();
            case 1:
                return is((char) bitSet.nextSetBit(0));
            case 2:
                char nextSetBit = (char) bitSet.nextSetBit(0);
                return a(nextSetBit, (char) bitSet.nextSetBit(nextSetBit + 1));
            default:
                return a(i, bitSet.length()) ? SmallCharMatcher.a(bitSet, str) : new BitSetMatcher(bitSet, str);
        }
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public void a(BitSet bitSet) {
        for (int i = 65535; i >= 0; i--) {
            if (matches((char) i)) {
                bitSet.set(i);
            }
        }
    }

    public boolean matchesAnyOf(CharSequence charSequence) {
        return !matchesNoneOf(charSequence);
    }

    public boolean matchesAllOf(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!matches(charSequence.charAt(length))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence charSequence) {
        return indexIn(charSequence) == -1;
    }

    public int indexIn(CharSequence charSequence) {
        return indexIn(charSequence, 0);
    }

    public int indexIn(CharSequence charSequence, int i) {
        int length = charSequence.length();
        Preconditions.checkPositionIndex(i, length);
        while (i < length) {
            if (matches(charSequence.charAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexIn(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (matches(charSequence.charAt(length))) {
                return length;
            }
        }
        return -1;
    }

    public int countIn(CharSequence charSequence) {
        int i = 0;
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            if (matches(charSequence.charAt(i2))) {
                i++;
            }
        }
        return i;
    }

    public String removeFrom(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        int indexIn = indexIn(charSequence2);
        if (indexIn == -1) {
            return charSequence2;
        }
        char[] charArray = charSequence2.toCharArray();
        int i = 1;
        while (true) {
            indexIn++;
            while (indexIn != charArray.length) {
                if (matches(charArray[indexIn])) {
                    i++;
                } else {
                    charArray[indexIn - i] = charArray[indexIn];
                    indexIn++;
                }
            }
            return new String(charArray, 0, indexIn - i);
        }
    }

    public String retainFrom(CharSequence charSequence) {
        return negate().removeFrom(charSequence);
    }

    public String replaceFrom(CharSequence charSequence, char c) {
        String charSequence2 = charSequence.toString();
        int indexIn = indexIn(charSequence2);
        if (indexIn == -1) {
            return charSequence2;
        }
        char[] charArray = charSequence2.toCharArray();
        charArray[indexIn] = c;
        while (true) {
            indexIn++;
            if (indexIn >= charArray.length) {
                return new String(charArray);
            }
            if (matches(charArray[indexIn])) {
                charArray[indexIn] = c;
            }
        }
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence2.length();
        if (length == 0) {
            return removeFrom(charSequence);
        }
        int i = 0;
        if (length == 1) {
            return replaceFrom(charSequence, charSequence2.charAt(0));
        }
        String charSequence3 = charSequence.toString();
        int indexIn = indexIn(charSequence3);
        if (indexIn == -1) {
            return charSequence3;
        }
        int length2 = charSequence3.length();
        StringBuilder sb = new StringBuilder(((length2 * 3) / 2) + 16);
        do {
            sb.append(charSequence3, i, indexIn);
            sb.append(charSequence2);
            i = indexIn + 1;
            indexIn = indexIn(charSequence3, i);
        } while (indexIn != -1);
        sb.append(charSequence3, i, length2);
        return sb.toString();
    }

    public String trimFrom(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && matches(charSequence.charAt(i))) {
            i++;
        }
        int i2 = length - 1;
        while (i2 > i && matches(charSequence.charAt(i2))) {
            i2--;
        }
        return charSequence.subSequence(i, i2 + 1).toString();
    }

    public String trimLeadingFrom(CharSequence charSequence) {
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!matches(charSequence.charAt(i))) {
                return charSequence.subSequence(i, length).toString();
            }
        }
        return "";
    }

    public String trimTrailingFrom(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!matches(charSequence.charAt(length))) {
                return charSequence.subSequence(0, length + 1).toString();
            }
        }
        return "";
    }

    public String collapseFrom(CharSequence charSequence, char c) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (matches(charAt)) {
                if (charAt != c || (i != length - 1 && matches(charSequence.charAt(i + 1)))) {
                    StringBuilder sb = new StringBuilder(length);
                    sb.append(charSequence, 0, i);
                    sb.append(c);
                    return a(charSequence, i + 1, length, c, sb, true);
                }
                i++;
            }
            i++;
        }
        return charSequence.toString();
    }

    public String trimAndCollapseFrom(CharSequence charSequence, char c) {
        int length = charSequence.length();
        int i = length - 1;
        int i2 = 0;
        while (i2 < length && matches(charSequence.charAt(i2))) {
            i2++;
        }
        int i3 = i;
        while (i3 > i2 && matches(charSequence.charAt(i3))) {
            i3--;
        }
        if (i2 == 0 && i3 == i) {
            return collapseFrom(charSequence, c);
        }
        int i4 = i3 + 1;
        return a(charSequence, i2, i4, c, new StringBuilder(i4 - i2), false);
    }

    private String a(CharSequence charSequence, int i, int i2, char c, StringBuilder sb, boolean z) {
        while (i < i2) {
            char charAt = charSequence.charAt(i);
            if (!matches(charAt)) {
                sb.append(charAt);
                z = false;
            } else if (!z) {
                sb.append(c);
                z = true;
            }
            i++;
        }
        return sb.toString();
    }

    @Deprecated
    public boolean apply(Character ch) {
        return matches(ch.charValue());
    }

    public String toString() {
        return super.toString();
    }

    /* access modifiers changed from: private */
    public static String b(char c) {
        String str = "0123456789ABCDEF";
        char[] cArr = {TokenParser.ESCAPE, 'u', 0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            cArr[5 - i] = str.charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(cArr);
    }

    private static IsEither a(char c, char c2) {
        return new IsEither(c, c2);
    }
}
