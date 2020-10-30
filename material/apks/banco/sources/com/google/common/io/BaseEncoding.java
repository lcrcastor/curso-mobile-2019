package com.google.common.io;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public abstract class BaseEncoding {
    private static final BaseEncoding a = new Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", Character.valueOf('='));
    private static final BaseEncoding b = new Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", Character.valueOf('='));
    private static final BaseEncoding c = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", Character.valueOf('='));
    private static final BaseEncoding d = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", Character.valueOf('='));
    private static final BaseEncoding e = new Base16Encoding("base16()", "0123456789ABCDEF");

    static final class Alphabet extends CharMatcher {
        final int a;
        final int b;
        final int c;
        final int d;
        private final String e;
        /* access modifiers changed from: private */
        public final char[] f;
        private final byte[] g;
        private final boolean[] h;

        Alphabet(String str, char[] cArr) {
            this.e = (String) Preconditions.checkNotNull(str);
            this.f = (char[]) Preconditions.checkNotNull(cArr);
            try {
                this.b = IntMath.log2(cArr.length, RoundingMode.UNNECESSARY);
                int min = Math.min(8, Integer.lowestOneBit(this.b));
                try {
                    this.c = 8 / min;
                    this.d = this.b / min;
                    this.a = cArr.length - 1;
                    byte[] bArr = new byte[128];
                    Arrays.fill(bArr, -1);
                    for (int i = 0; i < cArr.length; i++) {
                        char c2 = cArr[i];
                        Preconditions.checkArgument(CharMatcher.ascii().matches(c2), "Non-ASCII character: %s", c2);
                        Preconditions.checkArgument(bArr[c2] == -1, "Duplicate character: %s", c2);
                        bArr[c2] = (byte) i;
                    }
                    this.g = bArr;
                    boolean[] zArr = new boolean[this.c];
                    for (int i2 = 0; i2 < this.d; i2++) {
                        zArr[IntMath.divide(i2 * 8, this.b, RoundingMode.CEILING)] = true;
                    }
                    this.h = zArr;
                } catch (ArithmeticException e2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Illegal alphabet ");
                    sb.append(new String(cArr));
                    throw new IllegalArgumentException(sb.toString(), e2);
                }
            } catch (ArithmeticException e3) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Illegal alphabet length ");
                sb2.append(cArr.length);
                throw new IllegalArgumentException(sb2.toString(), e3);
            }
        }

        /* access modifiers changed from: 0000 */
        public char a(int i) {
            return this.f[i];
        }

        /* access modifiers changed from: 0000 */
        public boolean b(int i) {
            return this.h[i % this.c];
        }

        /* access modifiers changed from: 0000 */
        public boolean b(char c2) {
            return c2 <= 127 && this.g[c2] != -1;
        }

        /* access modifiers changed from: 0000 */
        public int c(char c2) {
            Object obj;
            if (c2 <= 127 && this.g[c2] != -1) {
                return this.g[c2];
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Unrecognized character: ");
            if (CharMatcher.invisible().matches(c2)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("0x");
                sb2.append(Integer.toHexString(c2));
                obj = sb2.toString();
            } else {
                obj = Character.valueOf(c2);
            }
            sb.append(obj);
            throw new DecodingException(sb.toString());
        }

        private boolean d() {
            for (char isLowerCase : this.f) {
                if (Ascii.isLowerCase(isLowerCase)) {
                    return true;
                }
            }
            return false;
        }

        private boolean e() {
            for (char isUpperCase : this.f) {
                if (Ascii.isUpperCase(isUpperCase)) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public Alphabet b() {
            if (!d()) {
                return this;
            }
            Preconditions.checkState(!e(), "Cannot call upperCase() on a mixed-case alphabet");
            char[] cArr = new char[this.f.length];
            for (int i = 0; i < this.f.length; i++) {
                cArr[i] = Ascii.toUpperCase(this.f[i]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.e);
            sb.append(".upperCase()");
            return new Alphabet(sb.toString(), cArr);
        }

        /* access modifiers changed from: 0000 */
        public Alphabet c() {
            if (!e()) {
                return this;
            }
            Preconditions.checkState(!d(), "Cannot call lowerCase() on a mixed-case alphabet");
            char[] cArr = new char[this.f.length];
            for (int i = 0; i < this.f.length; i++) {
                cArr[i] = Ascii.toLowerCase(this.f[i]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.e);
            sb.append(".lowerCase()");
            return new Alphabet(sb.toString(), cArr);
        }

        public boolean matches(char c2) {
            return CharMatcher.ascii().matches(c2) && this.g[c2] != -1;
        }

        public String toString() {
            return this.e;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof Alphabet)) {
                return false;
            }
            return Arrays.equals(this.f, ((Alphabet) obj).f);
        }

        public int hashCode() {
            return Arrays.hashCode(this.f);
        }
    }

    static final class Base16Encoding extends StandardBaseEncoding {
        final char[] a;

        Base16Encoding(String str, String str2) {
            this(new Alphabet(str, str2.toCharArray()));
        }

        private Base16Encoding(Alphabet alphabet) {
            super(alphabet, null);
            this.a = new char[512];
            Preconditions.checkArgument(alphabet.f.length == 16);
            for (int i = 0; i < 256; i++) {
                this.a[i] = alphabet.a(i >>> 4);
                this.a[i | 256] = alphabet.a(i & 15);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Appendable appendable, byte[] bArr, int i, int i2) {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            for (int i3 = 0; i3 < i2; i3++) {
                byte b = bArr[i + i3] & UnsignedBytes.MAX_VALUE;
                appendable.append(this.a[b]);
                appendable.append(this.a[b | Ascii.NUL]);
            }
        }

        /* access modifiers changed from: 0000 */
        public int a(byte[] bArr, CharSequence charSequence) {
            Preconditions.checkNotNull(bArr);
            if (charSequence.length() % 2 == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid input length ");
                sb.append(charSequence.length());
                throw new DecodingException(sb.toString());
            }
            int i = 0;
            int i2 = 0;
            while (i < charSequence.length()) {
                int i3 = i2 + 1;
                bArr[i2] = (byte) ((this.b.c(charSequence.charAt(i)) << 4) | this.b.c(charSequence.charAt(i + 1)));
                i += 2;
                i2 = i3;
            }
            return i2;
        }

        /* access modifiers changed from: 0000 */
        public BaseEncoding a(Alphabet alphabet, @Nullable Character ch) {
            return new Base16Encoding(alphabet);
        }
    }

    static final class Base64Encoding extends StandardBaseEncoding {
        Base64Encoding(String str, String str2, @Nullable Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        private Base64Encoding(Alphabet alphabet, @Nullable Character ch) {
            super(alphabet, ch);
            Preconditions.checkArgument(alphabet.f.length == 64);
        }

        /* access modifiers changed from: 0000 */
        public void a(Appendable appendable, byte[] bArr, int i, int i2) {
            Preconditions.checkNotNull(appendable);
            int i3 = i + i2;
            Preconditions.checkPositionIndexes(i, i3, bArr.length);
            while (i2 >= 3) {
                int i4 = i + 1;
                int i5 = i4 + 1;
                byte b = ((bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i4] & UnsignedBytes.MAX_VALUE) << 8);
                int i6 = i5 + 1;
                byte b2 = b | (bArr[i5] & UnsignedBytes.MAX_VALUE);
                appendable.append(this.b.a(b2 >>> Ascii.DC2));
                appendable.append(this.b.a((b2 >>> Ascii.FF) & 63));
                appendable.append(this.b.a((b2 >>> 6) & 63));
                appendable.append(this.b.a((int) b2 & 63));
                i2 -= 3;
                i = i6;
            }
            if (i < i3) {
                b(appendable, bArr, i, i3 - i);
            }
        }

        /* access modifiers changed from: 0000 */
        public int a(byte[] bArr, CharSequence charSequence) {
            Preconditions.checkNotNull(bArr);
            String trimTrailingFrom = a().trimTrailingFrom(charSequence);
            if (!this.b.b(trimTrailingFrom.length())) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid input length ");
                sb.append(trimTrailingFrom.length());
                throw new DecodingException(sb.toString());
            }
            int i = 0;
            int i2 = 0;
            while (i < trimTrailingFrom.length()) {
                int i3 = i + 1;
                int i4 = i3 + 1;
                int c = (this.b.c(trimTrailingFrom.charAt(i)) << 18) | (this.b.c(trimTrailingFrom.charAt(i3)) << 12);
                int i5 = i2 + 1;
                bArr[i2] = (byte) (c >>> 16);
                if (i4 < trimTrailingFrom.length()) {
                    int i6 = i4 + 1;
                    int c2 = c | (this.b.c(trimTrailingFrom.charAt(i4)) << 6);
                    i2 = i5 + 1;
                    bArr[i5] = (byte) ((c2 >>> 8) & 255);
                    if (i6 < trimTrailingFrom.length()) {
                        i4 = i6 + 1;
                        i5 = i2 + 1;
                        bArr[i2] = (byte) ((c2 | this.b.c(trimTrailingFrom.charAt(i6))) & 255);
                    } else {
                        i = i6;
                    }
                }
                i2 = i5;
                i = i4;
            }
            return i2;
        }

        /* access modifiers changed from: 0000 */
        public BaseEncoding a(Alphabet alphabet, @Nullable Character ch) {
            return new Base64Encoding(alphabet, ch);
        }
    }

    static class StandardBaseEncoding extends BaseEncoding {
        private transient BaseEncoding a;
        final Alphabet b;
        @Nullable
        final Character c;
        private transient BaseEncoding d;

        StandardBaseEncoding(String str, String str2, @Nullable Character ch) {
            this(new Alphabet(str, str2.toCharArray()), ch);
        }

        StandardBaseEncoding(Alphabet alphabet, @Nullable Character ch) {
            this.b = (Alphabet) Preconditions.checkNotNull(alphabet);
            Preconditions.checkArgument(ch == null || !alphabet.matches(ch.charValue()), "Padding character %s was already in alphabet", (Object) ch);
            this.c = ch;
        }

        /* access modifiers changed from: 0000 */
        public CharMatcher a() {
            return this.c == null ? CharMatcher.none() : CharMatcher.is(this.c.charValue());
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            return this.b.c * IntMath.divide(i, this.b.d, RoundingMode.CEILING);
        }

        @GwtIncompatible
        public OutputStream encodingStream(final Writer writer) {
            Preconditions.checkNotNull(writer);
            return new OutputStream() {
                int a = 0;
                int b = 0;
                int c = 0;

                public void write(int i) {
                    this.a <<= 8;
                    this.a = (i & 255) | this.a;
                    this.b += 8;
                    while (this.b >= StandardBaseEncoding.this.b.b) {
                        writer.write(StandardBaseEncoding.this.b.a((this.a >> (this.b - StandardBaseEncoding.this.b.b)) & StandardBaseEncoding.this.b.a));
                        this.c++;
                        this.b -= StandardBaseEncoding.this.b.b;
                    }
                }

                public void flush() {
                    writer.flush();
                }

                public void close() {
                    if (this.b > 0) {
                        writer.write(StandardBaseEncoding.this.b.a((this.a << (StandardBaseEncoding.this.b.b - this.b)) & StandardBaseEncoding.this.b.a));
                        this.c++;
                        if (StandardBaseEncoding.this.c != null) {
                            while (this.c % StandardBaseEncoding.this.b.c != 0) {
                                writer.write(StandardBaseEncoding.this.c.charValue());
                                this.c++;
                            }
                        }
                    }
                    writer.close();
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public void a(Appendable appendable, byte[] bArr, int i, int i2) {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            while (i3 < i2) {
                b(appendable, bArr, i + i3, Math.min(this.b.d, i2 - i3));
                i3 += this.b.d;
            }
        }

        /* access modifiers changed from: 0000 */
        public void b(Appendable appendable, byte[] bArr, int i, int i2) {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            Preconditions.checkArgument(i2 <= this.b.d);
            long j = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                j = (j | ((long) (bArr[i + i4] & UnsignedBytes.MAX_VALUE))) << 8;
            }
            int i5 = ((i2 + 1) * 8) - this.b.b;
            while (i3 < i2 * 8) {
                appendable.append(this.b.a(((int) (j >>> (i5 - i3))) & this.b.a));
                i3 += this.b.b;
            }
            if (this.c != null) {
                while (i3 < this.b.d * 8) {
                    appendable.append(this.c.charValue());
                    i3 += this.b.b;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public int b(int i) {
            return (int) (((((long) this.b.b) * ((long) i)) + 7) / 8);
        }

        public boolean canDecode(CharSequence charSequence) {
            String trimTrailingFrom = a().trimTrailingFrom(charSequence);
            if (!this.b.b(trimTrailingFrom.length())) {
                return false;
            }
            for (int i = 0; i < trimTrailingFrom.length(); i++) {
                if (!this.b.b(trimTrailingFrom.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: 0000 */
        public int a(byte[] bArr, CharSequence charSequence) {
            Preconditions.checkNotNull(bArr);
            String trimTrailingFrom = a().trimTrailingFrom(charSequence);
            if (!this.b.b(trimTrailingFrom.length())) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid input length ");
                sb.append(trimTrailingFrom.length());
                throw new DecodingException(sb.toString());
            }
            int i = 0;
            for (int i2 = 0; i2 < trimTrailingFrom.length(); i2 += this.b.c) {
                long j = 0;
                int i3 = 0;
                for (int i4 = 0; i4 < this.b.c; i4++) {
                    j <<= this.b.b;
                    if (i2 + i4 < trimTrailingFrom.length()) {
                        long c2 = j | ((long) this.b.c(trimTrailingFrom.charAt(i3 + i2)));
                        i3++;
                        j = c2;
                    }
                }
                int i5 = (this.b.d * 8) - (i3 * this.b.b);
                int i6 = (this.b.d - 1) * 8;
                while (i6 >= i5) {
                    int i7 = i + 1;
                    bArr[i] = (byte) ((int) ((j >>> i6) & 255));
                    i6 -= 8;
                    i = i7;
                }
            }
            return i;
        }

        @GwtIncompatible
        public InputStream decodingStream(final Reader reader) {
            Preconditions.checkNotNull(reader);
            return new InputStream() {
                int a = 0;
                int b = 0;
                int c = 0;
                boolean d = false;
                final CharMatcher e = StandardBaseEncoding.this.a();

                public int read() {
                    while (true) {
                        int read = reader.read();
                        if (read != -1) {
                            this.c++;
                            char c2 = (char) read;
                            if (this.e.matches(c2)) {
                                if (this.d || (this.c != 1 && StandardBaseEncoding.this.b.b(this.c - 1))) {
                                    this.d = true;
                                }
                            } else if (this.d) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Expected padding character but found '");
                                sb.append(c2);
                                sb.append("' at index ");
                                sb.append(this.c);
                                throw new DecodingException(sb.toString());
                            } else {
                                this.a <<= StandardBaseEncoding.this.b.b;
                                this.a = StandardBaseEncoding.this.b.c(c2) | this.a;
                                this.b += StandardBaseEncoding.this.b.b;
                                if (this.b >= 8) {
                                    this.b -= 8;
                                    return (this.a >> this.b) & 255;
                                }
                            }
                        } else if (this.d || StandardBaseEncoding.this.b.b(this.c)) {
                            return -1;
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Invalid input length ");
                            sb2.append(this.c);
                            throw new DecodingException(sb2.toString());
                        }
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Padding cannot start at index ");
                    sb3.append(this.c);
                    throw new DecodingException(sb3.toString());
                }

                public void close() {
                    reader.close();
                }
            };
        }

        public BaseEncoding omitPadding() {
            return this.c == null ? this : a(this.b, (Character) null);
        }

        public BaseEncoding withPadChar(char c2) {
            return (8 % this.b.b == 0 || (this.c != null && this.c.charValue() == c2)) ? this : a(this.b, Character.valueOf(c2));
        }

        public BaseEncoding withSeparator(String str, int i) {
            Preconditions.checkArgument(a().or(this.b).matchesNoneOf(str), "Separator (%s) cannot contain alphabet or padding characters", (Object) str);
            return new SeparatedBaseEncoding(this, str, i);
        }

        public BaseEncoding upperCase() {
            BaseEncoding baseEncoding = this.a;
            if (baseEncoding == 0) {
                Alphabet b2 = this.b.b();
                BaseEncoding a2 = b2 == this.b ? this : a(b2, this.c);
                this.a = a2;
                baseEncoding = a2;
            }
            return baseEncoding;
        }

        public BaseEncoding lowerCase() {
            BaseEncoding baseEncoding = this.d;
            if (baseEncoding == 0) {
                Alphabet c2 = this.b.c();
                BaseEncoding a2 = c2 == this.b ? this : a(c2, this.c);
                this.d = a2;
                baseEncoding = a2;
            }
            return baseEncoding;
        }

        /* access modifiers changed from: 0000 */
        public BaseEncoding a(Alphabet alphabet, @Nullable Character ch) {
            return new StandardBaseEncoding(alphabet, ch);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("BaseEncoding.");
            sb.append(this.b.toString());
            if (8 % this.b.b != 0) {
                if (this.c == null) {
                    sb.append(".omitPadding()");
                } else {
                    sb.append(".withPadChar('");
                    sb.append(this.c);
                    sb.append("')");
                }
            }
            return sb.toString();
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof StandardBaseEncoding)) {
                return false;
            }
            StandardBaseEncoding standardBaseEncoding = (StandardBaseEncoding) obj;
            if (this.b.equals(standardBaseEncoding.b) && Objects.equal(this.c, standardBaseEncoding.c)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return this.b.hashCode() ^ Objects.hashCode(this.c);
        }
    }

    public static final class DecodingException extends IOException {
        DecodingException(String str) {
            super(str);
        }
    }

    static final class SeparatedBaseEncoding extends BaseEncoding {
        private final BaseEncoding a;
        private final String b;
        private final int c;
        private final CharMatcher d;

        SeparatedBaseEncoding(BaseEncoding baseEncoding, String str, int i) {
            this.a = (BaseEncoding) Preconditions.checkNotNull(baseEncoding);
            this.b = (String) Preconditions.checkNotNull(str);
            this.c = i;
            Preconditions.checkArgument(i > 0, "Cannot add a separator after every %s chars", i);
            this.d = CharMatcher.anyOf(str).precomputed();
        }

        /* access modifiers changed from: 0000 */
        public CharMatcher a() {
            return this.a.a();
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            int a2 = this.a.a(i);
            return a2 + (this.b.length() * IntMath.divide(Math.max(0, a2 - 1), this.c, RoundingMode.FLOOR));
        }

        @GwtIncompatible
        public OutputStream encodingStream(Writer writer) {
            return this.a.encodingStream(a(writer, this.b, this.c));
        }

        /* access modifiers changed from: 0000 */
        public void a(Appendable appendable, byte[] bArr, int i, int i2) {
            this.a.a(a(appendable, this.b, this.c), bArr, i, i2);
        }

        /* access modifiers changed from: 0000 */
        public int b(int i) {
            return this.a.b(i);
        }

        public boolean canDecode(CharSequence charSequence) {
            return this.a.canDecode(this.d.removeFrom(charSequence));
        }

        /* access modifiers changed from: 0000 */
        public int a(byte[] bArr, CharSequence charSequence) {
            return this.a.a(bArr, (CharSequence) this.d.removeFrom(charSequence));
        }

        @GwtIncompatible
        public InputStream decodingStream(Reader reader) {
            return this.a.decodingStream(a(reader, this.d));
        }

        public BaseEncoding omitPadding() {
            return this.a.omitPadding().withSeparator(this.b, this.c);
        }

        public BaseEncoding withPadChar(char c2) {
            return this.a.withPadChar(c2).withSeparator(this.b, this.c);
        }

        public BaseEncoding withSeparator(String str, int i) {
            throw new UnsupportedOperationException("Already have a separator");
        }

        public BaseEncoding upperCase() {
            return this.a.upperCase().withSeparator(this.b, this.c);
        }

        public BaseEncoding lowerCase() {
            return this.a.lowerCase().withSeparator(this.b, this.c);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(".withSeparator(\"");
            sb.append(this.b);
            sb.append("\", ");
            sb.append(this.c);
            sb.append(")");
            return sb.toString();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract int a(int i);

    /* access modifiers changed from: 0000 */
    public abstract int a(byte[] bArr, CharSequence charSequence);

    /* access modifiers changed from: 0000 */
    public abstract CharMatcher a();

    /* access modifiers changed from: 0000 */
    public abstract void a(Appendable appendable, byte[] bArr, int i, int i2);

    /* access modifiers changed from: 0000 */
    public abstract int b(int i);

    public abstract boolean canDecode(CharSequence charSequence);

    @GwtIncompatible
    public abstract InputStream decodingStream(Reader reader);

    @GwtIncompatible
    public abstract OutputStream encodingStream(Writer writer);

    public abstract BaseEncoding lowerCase();

    public abstract BaseEncoding omitPadding();

    public abstract BaseEncoding upperCase();

    public abstract BaseEncoding withPadChar(char c2);

    public abstract BaseEncoding withSeparator(String str, int i);

    BaseEncoding() {
    }

    public String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public final String encode(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        StringBuilder sb = new StringBuilder(a(i2));
        try {
            a(sb, bArr, i, i2);
            return sb.toString();
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }

    @GwtIncompatible
    public final ByteSink encodingSink(final CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink() {
            public OutputStream openStream() {
                return BaseEncoding.this.encodingStream(charSink.openStream());
            }
        };
    }

    private static byte[] a(byte[] bArr, int i) {
        if (i == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    public final byte[] decode(CharSequence charSequence) {
        try {
            return a(charSequence);
        } catch (DecodingException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public final byte[] a(CharSequence charSequence) {
        String trimTrailingFrom = a().trimTrailingFrom(charSequence);
        byte[] bArr = new byte[b(trimTrailingFrom.length())];
        return a(bArr, a(bArr, (CharSequence) trimTrailingFrom));
    }

    @GwtIncompatible
    public final ByteSource decodingSource(final CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource() {
            public InputStream openStream() {
                return BaseEncoding.this.decodingStream(charSource.openStream());
            }
        };
    }

    public static BaseEncoding base64() {
        return a;
    }

    public static BaseEncoding base64Url() {
        return b;
    }

    public static BaseEncoding base32() {
        return c;
    }

    public static BaseEncoding base32Hex() {
        return d;
    }

    public static BaseEncoding base16() {
        return e;
    }

    @GwtIncompatible
    static Reader a(final Reader reader, final CharMatcher charMatcher) {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(charMatcher);
        return new Reader() {
            public int read() {
                int read;
                do {
                    read = reader.read();
                    if (read == -1) {
                        break;
                    }
                } while (charMatcher.matches((char) read));
                return read;
            }

            public int read(char[] cArr, int i, int i2) {
                throw new UnsupportedOperationException();
            }

            public void close() {
                reader.close();
            }
        };
    }

    static Appendable a(final Appendable appendable, final String str, final int i) {
        Preconditions.checkNotNull(appendable);
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(i > 0);
        return new Appendable() {
            int a = i;

            public Appendable append(char c2) {
                if (this.a == 0) {
                    appendable.append(str);
                    this.a = i;
                }
                appendable.append(c2);
                this.a--;
                return this;
            }

            public Appendable append(CharSequence charSequence, int i, int i2) {
                throw new UnsupportedOperationException();
            }

            public Appendable append(CharSequence charSequence) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @GwtIncompatible
    static Writer a(final Writer writer, String str, int i) {
        final Appendable a2 = a((Appendable) writer, str, i);
        return new Writer() {
            public void write(int i) {
                a2.append((char) i);
            }

            public void write(char[] cArr, int i, int i2) {
                throw new UnsupportedOperationException();
            }

            public void flush() {
                writer.flush();
            }

            public void close() {
                writer.close();
            }
        };
    }
}
