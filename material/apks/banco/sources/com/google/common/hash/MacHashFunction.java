package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

final class MacHashFunction extends AbstractStreamingHashFunction {
    private final Mac a;
    private final Key b;
    private final String c;
    private final int d = (this.a.getMacLength() * 8);
    private final boolean e = a(this.a);

    static final class MacHasher extends AbstractByteHasher {
        private final Mac a;
        private boolean b;

        private MacHasher(Mac mac) {
            this.a = mac;
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
            Preconditions.checkState(!this.b, "Cannot re-use a Hasher after calling hash() on it");
        }

        public HashCode hash() {
            a();
            this.b = true;
            return HashCode.a(this.a.doFinal());
        }
    }

    MacHashFunction(String str, Key key, String str2) {
        this.a = a(str, key);
        this.b = (Key) Preconditions.checkNotNull(key);
        this.c = (String) Preconditions.checkNotNull(str2);
    }

    public int bits() {
        return this.d;
    }

    private static boolean a(Mac mac) {
        try {
            mac.clone();
            return true;
        } catch (CloneNotSupportedException unused) {
            return false;
        }
    }

    private static Mac a(String str, Key key) {
        try {
            Mac instance = Mac.getInstance(str);
            instance.init(key);
            return instance;
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException(e2);
        } catch (InvalidKeyException e3) {
            throw new IllegalArgumentException(e3);
        }
    }

    public Hasher newHasher() {
        if (this.e) {
            try {
                return new MacHasher((Mac) this.a.clone());
            } catch (CloneNotSupportedException unused) {
            }
        }
        return new MacHasher(a(this.a.getAlgorithm(), this.b));
    }

    public String toString() {
        return this.c;
    }
}
