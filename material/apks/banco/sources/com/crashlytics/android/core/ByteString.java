package com.crashlytics.android.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

final class ByteString {
    public static final ByteString a = new ByteString(new byte[0]);
    private final byte[] b;
    private volatile int c = 0;

    private ByteString(byte[] bArr) {
        this.b = bArr;
    }

    public int a() {
        return this.b.length;
    }

    public static ByteString a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new ByteString(bArr2);
    }

    public static ByteString a(String str) {
        try {
            return new ByteString(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.", e);
        }
    }

    public void a(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.b, i, bArr, i2, i3);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ByteString)) {
            return false;
        }
        ByteString byteString = (ByteString) obj;
        int length = this.b.length;
        if (length != byteString.b.length) {
            return false;
        }
        byte[] bArr = this.b;
        byte[] bArr2 = byteString.b;
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = this.c;
        if (i == 0) {
            byte[] bArr = this.b;
            int length = this.b.length;
            int i2 = length;
            for (int i3 = 0; i3 < length; i3++) {
                i2 = (i2 * 31) + bArr[i3];
            }
            i = i2 == 0 ? 1 : i2;
            this.c = i;
        }
        return i;
    }

    public InputStream b() {
        return new ByteArrayInputStream(this.b);
    }
}
