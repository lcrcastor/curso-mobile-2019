package org.bouncycastle.crypto.tls;

public class UseSRTPData {
    private int[] a;
    private byte[] b;

    public UseSRTPData(int[] iArr, byte[] bArr) {
        if (iArr == null || iArr.length < 1 || iArr.length >= 32768) {
            throw new IllegalArgumentException("'protectionProfiles' must have length from 1 to (2^15 - 1)");
        }
        if (bArr == null) {
            bArr = TlsUtils.EMPTY_BYTES;
        } else if (bArr.length > 255) {
            throw new IllegalArgumentException("'mki' cannot be longer than 255 bytes");
        }
        this.a = iArr;
        this.b = bArr;
    }

    public byte[] getMki() {
        return this.b;
    }

    public int[] getProtectionProfiles() {
        return this.a;
    }
}
