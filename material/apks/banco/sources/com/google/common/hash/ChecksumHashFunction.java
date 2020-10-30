package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.zip.Checksum;

final class ChecksumHashFunction extends AbstractStreamingHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final Supplier<? extends Checksum> a;
    /* access modifiers changed from: private */
    public final int b;
    private final String c;

    final class ChecksumHasher extends AbstractByteHasher {
        private final Checksum b;

        private ChecksumHasher(Checksum checksum) {
            this.b = (Checksum) Preconditions.checkNotNull(checksum);
        }

        /* access modifiers changed from: protected */
        public void a(byte b2) {
            this.b.update(b2);
        }

        /* access modifiers changed from: protected */
        public void a(byte[] bArr, int i, int i2) {
            this.b.update(bArr, i, i2);
        }

        public HashCode hash() {
            long value = this.b.getValue();
            if (ChecksumHashFunction.this.b == 32) {
                return HashCode.fromInt((int) value);
            }
            return HashCode.fromLong(value);
        }
    }

    ChecksumHashFunction(Supplier<? extends Checksum> supplier, int i, String str) {
        this.a = (Supplier) Preconditions.checkNotNull(supplier);
        Preconditions.checkArgument(i == 32 || i == 64, "bits (%s) must be either 32 or 64", i);
        this.b = i;
        this.c = (String) Preconditions.checkNotNull(str);
    }

    public int bits() {
        return this.b;
    }

    public Hasher newHasher() {
        return new ChecksumHasher((Checksum) this.a.get());
    }

    public String toString() {
        return this.c;
    }
}
