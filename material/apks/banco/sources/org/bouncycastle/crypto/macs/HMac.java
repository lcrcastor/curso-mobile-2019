package org.bouncycastle.crypto.macs;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.Hashtable;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Memoable;

public class HMac implements Mac {
    private static Hashtable h = new Hashtable();
    private Digest a;
    private int b;
    private int c;
    private Memoable d;
    private Memoable e;
    private byte[] f;
    private byte[] g;

    static {
        h.put("GOST3411", Integers.valueOf(32));
        h.put("MD2", Integers.valueOf(16));
        h.put("MD4", Integers.valueOf(64));
        h.put(CommonUtils.MD5_INSTANCE, Integers.valueOf(64));
        h.put("RIPEMD128", Integers.valueOf(64));
        h.put("RIPEMD160", Integers.valueOf(64));
        h.put(CommonUtils.SHA1_INSTANCE, Integers.valueOf(64));
        h.put("SHA-224", Integers.valueOf(64));
        h.put("SHA-256", Integers.valueOf(64));
        h.put("SHA-384", Integers.valueOf(128));
        h.put("SHA-512", Integers.valueOf(128));
        h.put("Tiger", Integers.valueOf(64));
        h.put("Whirlpool", Integers.valueOf(64));
    }

    public HMac(Digest digest) {
        this(digest, a(digest));
    }

    private HMac(Digest digest, int i) {
        this.a = digest;
        this.b = digest.getDigestSize();
        this.c = i;
        this.f = new byte[this.c];
        this.g = new byte[(this.c + this.b)];
    }

    private static int a(Digest digest) {
        if (digest instanceof ExtendedDigest) {
            return ((ExtendedDigest) digest).getByteLength();
        }
        Integer num = (Integer) h.get(digest.getAlgorithmName());
        if (num != null) {
            return num.intValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown digest passed: ");
        sb.append(digest.getAlgorithmName());
        throw new IllegalArgumentException(sb.toString());
    }

    private static void a(byte[] bArr, int i, byte b2) {
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) (bArr[i2] ^ b2);
        }
    }

    public int doFinal(byte[] bArr, int i) {
        this.a.doFinal(this.g, this.c);
        if (this.e != null) {
            ((Memoable) this.a).reset(this.e);
            this.a.update(this.g, this.c, this.a.getDigestSize());
        } else {
            this.a.update(this.g, 0, this.g.length);
        }
        int doFinal = this.a.doFinal(bArr, i);
        for (int i2 = this.c; i2 < this.g.length; i2++) {
            this.g[i2] = 0;
        }
        if (this.d != null) {
            ((Memoable) this.a).reset(this.d);
            return doFinal;
        }
        this.a.update(this.f, 0, this.f.length);
        return doFinal;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getAlgorithmName());
        sb.append("/HMAC");
        return sb.toString();
    }

    public int getMacSize() {
        return this.b;
    }

    public Digest getUnderlyingDigest() {
        return this.a;
    }

    public void init(CipherParameters cipherParameters) {
        this.a.reset();
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        int length = key.length;
        if (length > this.c) {
            this.a.update(key, 0, length);
            this.a.doFinal(this.f, 0);
            length = this.b;
        } else {
            System.arraycopy(key, 0, this.f, 0, length);
        }
        while (length < this.f.length) {
            this.f[length] = 0;
            length++;
        }
        System.arraycopy(this.f, 0, this.g, 0, this.c);
        a(this.f, this.c, 54);
        a(this.g, this.c, 92);
        if (this.a instanceof Memoable) {
            this.e = ((Memoable) this.a).copy();
            ((Digest) this.e).update(this.g, 0, this.c);
        }
        this.a.update(this.f, 0, this.f.length);
        if (this.a instanceof Memoable) {
            this.d = ((Memoable) this.a).copy();
        }
    }

    public void reset() {
        this.a.reset();
        this.a.update(this.f, 0, this.f.length);
    }

    public void update(byte b2) {
        this.a.update(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
    }
}
