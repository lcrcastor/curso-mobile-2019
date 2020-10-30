package com.google.android.gms.internal;

import java.io.ByteArrayOutputStream;

public class zzaa extends ByteArrayOutputStream {
    private final zzu a;

    public zzaa(zzu zzu, int i) {
        this.a = zzu;
        this.buf = this.a.zzb(Math.max(i, 256));
    }

    private void a(int i) {
        if (this.count + i > this.buf.length) {
            byte[] zzb = this.a.zzb((this.count + i) * 2);
            System.arraycopy(this.buf, 0, zzb, 0, this.count);
            this.a.zza(this.buf);
            this.buf = zzb;
        }
    }

    public void close() {
        this.a.zza(this.buf);
        this.buf = null;
        super.close();
    }

    public void finalize() {
        this.a.zza(this.buf);
    }

    public synchronized void write(int i) {
        a(1);
        super.write(i);
    }

    public synchronized void write(byte[] bArr, int i, int i2) {
        a(i2);
        super.write(bArr, i, i2);
    }
}
