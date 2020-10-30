package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

final class MessageDigestHashFunction extends AbstractStreamingHashFunction implements Serializable {
    private final MessageDigest a;
    private final int b;
    private final boolean c;
    private final String d;

    static final class MessageDigestHasher extends AbstractByteHasher {
        private final MessageDigest a;
        private final int b;
        private boolean c;

        private MessageDigestHasher(MessageDigest messageDigest, int i) {
            this.a = messageDigest;
            this.b = i;
        }

        /* access modifiers changed from: protected */
        public void a(byte b2) {
            a();
            this.a.update(b2);
        }

        /* access modifiers changed from: protected */
        public void a(byte[] bArr) {
            a();
            this.a.update(bArr);
        }

        /* access modifiers changed from: protected */
        public void a(byte[] bArr, int i, int i2) {
            a();
            this.a.update(bArr, i, i2);
        }

        private void a() {
            Preconditions.checkState(!this.c, "Cannot re-use a Hasher after calling hash() on it");
        }

        public HashCode hash() {
            a();
            this.c = true;
            return HashCode.a(this.b == this.a.getDigestLength() ? this.a.digest() : Arrays.copyOf(this.a.digest(), this.b));
        }
    }

    static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final String a;
        private final int b;
        private final String c;

        private SerializedForm(String str, int i, String str2) {
            this.a = str;
            this.b = i;
            this.c = str2;
        }

        private Object readResolve() {
            return new MessageDigestHashFunction(this.a, this.b, this.c);
        }
    }

    MessageDigestHashFunction(String str, String str2) {
        this.a = a(str);
        this.b = this.a.getDigestLength();
        this.d = (String) Preconditions.checkNotNull(str2);
        this.c = a(this.a);
    }

    MessageDigestHashFunction(String str, int i, String str2) {
        this.d = (String) Preconditions.checkNotNull(str2);
        this.a = a(str);
        int digestLength = this.a.getDigestLength();
        Preconditions.checkArgument(i >= 4 && i <= digestLength, "bytes (%s) must be >= 4 and < %s", i, digestLength);
        this.b = i;
        this.c = a(this.a);
    }

    private static boolean a(MessageDigest messageDigest) {
        try {
            messageDigest.clone();
            return true;
        } catch (CloneNotSupportedException unused) {
            return false;
        }
    }

    public int bits() {
        return this.b * 8;
    }

    public String toString() {
        return this.d;
    }

    private static MessageDigest a(String str) {
        try {
            return MessageDigest.getInstance(str);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public Hasher newHasher() {
        if (this.c) {
            try {
                return new MessageDigestHasher((MessageDigest) this.a.clone(), this.b);
            } catch (CloneNotSupportedException unused) {
            }
        }
        return new MessageDigestHasher(a(this.a.getAlgorithm()), this.b);
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new SerializedForm(this.a.getAlgorithm(), this.b, this.d);
    }
}
