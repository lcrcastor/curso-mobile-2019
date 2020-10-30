package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import org.bouncycastle.asn1.eac.CertificateBody;

public final class zzard {
    private final ByteBuffer a;

    public static class zza extends IOException {
        zza(int i, int i2) {
            StringBuilder sb = new StringBuilder(108);
            sb.append("CodedOutputStream was writing to a flat byte array and ran out of space (pos ");
            sb.append(i);
            sb.append(" limit ");
            sb.append(i2);
            sb.append(").");
            super(sb.toString());
        }
    }

    private zzard(ByteBuffer byteBuffer) {
        this.a = byteBuffer;
        this.a.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzard(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private static int a(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < 128) {
            i++;
        }
        int i2 = length;
        while (true) {
            if (i >= length) {
                break;
            }
            char charAt = charSequence.charAt(i);
            if (charAt >= 2048) {
                i2 += a(charSequence, i);
                break;
            }
            i2 += (127 - charAt) >>> 31;
            i++;
        }
        if (i2 >= length) {
            return i2;
        }
        long j = ((long) i2) + 4294967296L;
        StringBuilder sb = new StringBuilder(54);
        sb.append("UTF-8 length does not fit in int: ");
        sb.append(j);
        throw new IllegalArgumentException(sb.toString());
    }

    private static int a(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 2048) {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if (55296 <= charAt && charAt <= 57343) {
                    if (Character.codePointAt(charSequence, i) < 65536) {
                        StringBuilder sb = new StringBuilder(39);
                        sb.append("Unpaired surrogate at index ");
                        sb.append(i);
                        throw new IllegalArgumentException(sb.toString());
                    }
                    i++;
                }
            }
            i++;
        }
        return i2;
    }

    private static int a(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        int length = charSequence.length();
        int i4 = i2 + i;
        int i5 = 0;
        while (i5 < length) {
            int i6 = i5 + i;
            if (i6 >= i4) {
                break;
            }
            char charAt = charSequence.charAt(i5);
            if (charAt >= 128) {
                break;
            }
            bArr[i6] = (byte) charAt;
            i5++;
        }
        if (i5 == length) {
            return i + length;
        }
        int i7 = i + i5;
        while (i5 < length) {
            char charAt2 = charSequence.charAt(i5);
            if (charAt2 < 128 && i7 < i4) {
                i3 = i7 + 1;
                bArr[i7] = (byte) charAt2;
            } else if (charAt2 < 2048 && i7 <= i4 - 2) {
                int i8 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 6) | 960);
                i7 = i8 + 1;
                bArr[i8] = (byte) ((charAt2 & '?') | 128);
                i5++;
            } else if ((charAt2 < 55296 || 57343 < charAt2) && i7 <= i4 - 3) {
                int i9 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 >>> 12) | 480);
                int i10 = i9 + 1;
                bArr[i9] = (byte) (((charAt2 >>> 6) & 63) | 128);
                i3 = i10 + 1;
                bArr[i10] = (byte) ((charAt2 & '?') | 128);
            } else if (i7 <= i4 - 4) {
                int i11 = i5 + 1;
                if (i11 != charSequence.length()) {
                    char charAt3 = charSequence.charAt(i11);
                    if (!Character.isSurrogatePair(charAt2, charAt3)) {
                        i5 = i11;
                    } else {
                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                        int i12 = i7 + 1;
                        bArr[i7] = (byte) ((codePoint >>> 18) | 240);
                        int i13 = i12 + 1;
                        bArr[i12] = (byte) (((codePoint >>> 12) & 63) | 128);
                        int i14 = i13 + 1;
                        bArr[i13] = (byte) (((codePoint >>> 6) & 63) | 128);
                        i7 = i14 + 1;
                        bArr[i14] = (byte) ((codePoint & 63) | 128);
                        i5 = i11;
                        i5++;
                    }
                }
                int i15 = i5 - 1;
                StringBuilder sb = new StringBuilder(39);
                sb.append("Unpaired surrogate at index ");
                sb.append(i15);
                throw new IllegalArgumentException(sb.toString());
            } else {
                StringBuilder sb2 = new StringBuilder(37);
                sb2.append("Failed writing ");
                sb2.append(charAt2);
                sb2.append(" at index ");
                sb2.append(i7);
                throw new ArrayIndexOutOfBoundsException(sb2.toString());
            }
            i7 = i3;
            i5++;
        }
        return i7;
    }

    private static void a(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        } else if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(a(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
            } catch (ArrayIndexOutOfBoundsException e) {
                BufferOverflowException bufferOverflowException = new BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        } else {
            b(charSequence, byteBuffer);
        }
    }

    private static void b(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            char charAt = charSequence.charAt(i2);
            if (charAt >= 128) {
                if (charAt < 2048) {
                    i = (charAt >>> 6) | 960;
                } else if (charAt < 55296 || 57343 < charAt) {
                    byteBuffer.put((byte) ((charAt >>> 12) | 480));
                    i = ((charAt >>> 6) & 63) | 128;
                } else {
                    int i3 = i2 + 1;
                    if (i3 != charSequence.length()) {
                        char charAt2 = charSequence.charAt(i3);
                        if (!Character.isSurrogatePair(charAt, charAt2)) {
                            i2 = i3;
                        } else {
                            int codePoint = Character.toCodePoint(charAt, charAt2);
                            byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                            byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                            byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                            byteBuffer.put((byte) ((codePoint & 63) | 128));
                            i2 = i3;
                            i2++;
                        }
                    }
                    int i4 = i2 - 1;
                    StringBuilder sb = new StringBuilder(39);
                    sb.append("Unpaired surrogate at index ");
                    sb.append(i4);
                    throw new IllegalArgumentException(sb.toString());
                }
                byteBuffer.put((byte) i);
                charAt = (charAt & '?') | 128;
            }
            byteBuffer.put((byte) charAt);
            i2++;
        }
    }

    public static int zzag(int i, int i2) {
        return zzahl(i) + zzahi(i2);
    }

    public static int zzah(int i, int i2) {
        return zzahl(i) + zzahj(i2);
    }

    public static int zzahi(int i) {
        if (i >= 0) {
            return zzahn(i);
        }
        return 10;
    }

    public static int zzahj(int i) {
        return zzahn(zzahp(i));
    }

    public static int zzahl(int i) {
        return zzahn(zzarn.zzaj(i, 0));
    }

    public static int zzahn(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzahp(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static int zzb(int i, double d) {
        return zzahl(i) + zzp(d);
    }

    public static int zzb(int i, zzark zzark) {
        return (zzahl(i) * 2) + zzd(zzark);
    }

    public static int zzb(int i, byte[] bArr) {
        return zzahl(i) + zzbg(bArr);
    }

    public static zzard zzbe(byte[] bArr) {
        return zzc(bArr, 0, bArr.length);
    }

    public static int zzbg(byte[] bArr) {
        return zzahn(bArr.length) + bArr.length;
    }

    public static int zzc(int i, zzark zzark) {
        return zzahl(i) + zze(zzark);
    }

    public static zzard zzc(byte[] bArr, int i, int i2) {
        return new zzard(bArr, i, i2);
    }

    public static int zzd(int i, float f) {
        return zzahl(i) + zzl(f);
    }

    public static int zzd(zzark zzark) {
        return zzark.db();
    }

    public static int zzda(long j) {
        return zzdf(j);
    }

    public static int zzdb(long j) {
        return zzdf(j);
    }

    public static int zzdc(long j) {
        return 8;
    }

    public static int zzdd(long j) {
        return zzdf(zzdh(j));
    }

    public static int zzdf(long j) {
        if ((j & -128) == 0) {
            return 1;
        }
        if ((j & -16384) == 0) {
            return 2;
        }
        if ((j & -2097152) == 0) {
            return 3;
        }
        if ((j & -268435456) == 0) {
            return 4;
        }
        if ((j & -34359738368L) == 0) {
            return 5;
        }
        if ((j & -4398046511104L) == 0) {
            return 6;
        }
        if ((j & -562949953421312L) == 0) {
            return 7;
        }
        if ((j & -72057594037927936L) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public static long zzdh(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int zzdl(boolean z) {
        return 1;
    }

    public static int zze(int i, long j) {
        return zzahl(i) + zzda(j);
    }

    public static int zze(zzark zzark) {
        int db = zzark.db();
        return zzahn(db) + db;
    }

    public static int zzf(int i, long j) {
        return zzahl(i) + zzdb(j);
    }

    public static int zzg(int i, long j) {
        return zzahl(i) + zzdc(j);
    }

    public static int zzh(int i, long j) {
        return zzahl(i) + zzdd(j);
    }

    public static int zzk(int i, boolean z) {
        return zzahl(i) + zzdl(z);
    }

    public static int zzl(float f) {
        return 4;
    }

    public static int zzp(double d) {
        return 8;
    }

    public static int zzs(int i, String str) {
        return zzahl(i) + zzuy(str);
    }

    public static int zzuy(String str) {
        int a2 = a(str);
        return zzahn(a2) + a2;
    }

    public int cN() {
        return this.a.remaining();
    }

    public void cO() {
        if (cN() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void zza(int i, double d) {
        zzai(i, 1);
        zzo(d);
    }

    public void zza(int i, long j) {
        zzai(i, 0);
        zzcw(j);
    }

    public void zza(int i, zzark zzark) {
        zzai(i, 2);
        zzc(zzark);
    }

    public void zza(int i, byte[] bArr) {
        zzai(i, 2);
        zzbf(bArr);
    }

    public void zzae(int i, int i2) {
        zzai(i, 0);
        zzahg(i2);
    }

    public void zzaf(int i, int i2) {
        zzai(i, 0);
        zzahh(i2);
    }

    public void zzahg(int i) {
        if (i >= 0) {
            zzahm(i);
        } else {
            zzde((long) i);
        }
    }

    public void zzahh(int i) {
        zzahm(zzahp(i));
    }

    public void zzahk(int i) {
        zzc((byte) i);
    }

    public void zzahm(int i) {
        while ((i & -128) != 0) {
            zzahk((i & CertificateBody.profileType) | 128);
            i >>>= 7;
        }
        zzahk(i);
    }

    public void zzaho(int i) {
        if (this.a.remaining() < 4) {
            throw new zza(this.a.position(), this.a.limit());
        }
        this.a.putInt(i);
    }

    public void zzai(int i, int i2) {
        zzahm(zzarn.zzaj(i, i2));
    }

    public void zzb(int i, long j) {
        zzai(i, 0);
        zzcx(j);
    }

    public void zzb(zzark zzark) {
        zzark.zza(this);
    }

    public void zzbf(byte[] bArr) {
        zzahm(bArr.length);
        zzbh(bArr);
    }

    public void zzbh(byte[] bArr) {
        zzd(bArr, 0, bArr.length);
    }

    public void zzc(byte b) {
        if (!this.a.hasRemaining()) {
            throw new zza(this.a.position(), this.a.limit());
        }
        this.a.put(b);
    }

    public void zzc(int i, float f) {
        zzai(i, 5);
        zzk(f);
    }

    public void zzc(int i, long j) {
        zzai(i, 1);
        zzcy(j);
    }

    public void zzc(zzark zzark) {
        zzahm(zzark.da());
        zzark.zza(this);
    }

    public void zzcw(long j) {
        zzde(j);
    }

    public void zzcx(long j) {
        zzde(j);
    }

    public void zzcy(long j) {
        zzdg(j);
    }

    public void zzcz(long j) {
        zzde(zzdh(j));
    }

    public void zzd(int i, long j) {
        zzai(i, 0);
        zzcz(j);
    }

    public void zzd(byte[] bArr, int i, int i2) {
        if (this.a.remaining() >= i2) {
            this.a.put(bArr, i, i2);
            return;
        }
        throw new zza(this.a.position(), this.a.limit());
    }

    public void zzde(long j) {
        while ((j & -128) != 0) {
            zzahk((((int) j) & CertificateBody.profileType) | 128);
            j >>>= 7;
        }
        zzahk((int) j);
    }

    public void zzdg(long j) {
        if (this.a.remaining() < 8) {
            throw new zza(this.a.position(), this.a.limit());
        }
        this.a.putLong(j);
    }

    public void zzdk(boolean z) {
        zzahk(z ? 1 : 0);
    }

    public void zzj(int i, boolean z) {
        zzai(i, 0);
        zzdk(z);
    }

    public void zzk(float f) {
        zzaho(Float.floatToIntBits(f));
    }

    public void zzo(double d) {
        zzdg(Double.doubleToLongBits(d));
    }

    public void zzr(int i, String str) {
        zzai(i, 2);
        zzux(str);
    }

    public void zzux(String str) {
        try {
            int zzahn = zzahn(str.length());
            if (zzahn == zzahn(str.length() * 3)) {
                int position = this.a.position();
                if (this.a.remaining() < zzahn) {
                    throw new zza(position + zzahn, this.a.limit());
                }
                this.a.position(position + zzahn);
                a((CharSequence) str, this.a);
                int position2 = this.a.position();
                this.a.position(position);
                zzahm((position2 - position) - zzahn);
                this.a.position(position2);
                return;
            }
            zzahm(a(str));
            a((CharSequence) str, this.a);
        } catch (BufferOverflowException e) {
            zza zza2 = new zza(this.a.position(), this.a.limit());
            zza2.initCause(e);
            throw zza2;
        }
    }
}
