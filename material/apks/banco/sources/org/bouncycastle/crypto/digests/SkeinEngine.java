package org.bouncycastle.crypto.digests;

import com.google.common.primitives.Longs;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.engines.ThreefishEngine;
import org.bouncycastle.crypto.params.SkeinParameters;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Memoable;

public class SkeinEngine implements Memoable {
    public static final int SKEIN_1024 = 1024;
    public static final int SKEIN_256 = 256;
    public static final int SKEIN_512 = 512;
    private static final Hashtable c = new Hashtable();
    final ThreefishEngine a;
    long[] b;
    private final int d;
    private long[] e;
    private byte[] f;
    private Parameter[] g;
    private Parameter[] h;
    private final UBI i;
    private final byte[] j;

    static class Configuration {
        private byte[] a = new byte[32];

        public Configuration(long j) {
            this.a[0] = 83;
            this.a[1] = 72;
            this.a[2] = 65;
            this.a[3] = 51;
            this.a[4] = 1;
            this.a[5] = 0;
            ThreefishEngine.wordToBytes(j, this.a, 8);
        }

        public byte[] a() {
            return this.a;
        }
    }

    public static class Parameter {
        private int a;
        private byte[] b;

        public Parameter(int i, byte[] bArr) {
            this.a = i;
            this.b = bArr;
        }

        public int getType() {
            return this.a;
        }

        public byte[] getValue() {
            return this.b;
        }
    }

    class UBI {
        private final UbiTweak b = new UbiTweak();
        private byte[] c;
        private int d;
        private long[] e;

        public UBI(int i) {
            this.c = new byte[i];
            this.e = new long[(this.c.length / 8)];
        }

        private void b(long[] jArr) {
            SkeinEngine.this.a.init(true, SkeinEngine.this.b, this.b.e());
            for (int i = 0; i < this.e.length; i++) {
                this.e[i] = ThreefishEngine.bytesToWord(this.c, i * 8);
            }
            SkeinEngine.this.a.processBlock(this.e, jArr);
            for (int i2 = 0; i2 < jArr.length; i2++) {
                jArr[i2] = jArr[i2] ^ this.e[i2];
            }
        }

        public void a(int i) {
            this.b.a();
            this.b.a(i);
            this.d = 0;
        }

        public void a(UBI ubi) {
            this.c = Arrays.clone(ubi.c, this.c);
            this.d = ubi.d;
            this.e = Arrays.clone(ubi.e, this.e);
            this.b.a(ubi.b);
        }

        public void a(byte[] bArr, int i, int i2, long[] jArr) {
            int i3 = 0;
            while (i2 > i3) {
                if (this.d == this.c.length) {
                    b(jArr);
                    this.b.a(false);
                    this.d = 0;
                }
                int min = Math.min(i2 - i3, this.c.length - this.d);
                System.arraycopy(bArr, i + i3, this.c, this.d, min);
                i3 += min;
                this.d += min;
                this.b.b(min);
            }
        }

        public void a(long[] jArr) {
            for (int i = this.d; i < this.c.length; i++) {
                this.c[i] = 0;
            }
            this.b.b(true);
            b(jArr);
        }
    }

    static class UbiTweak {
        private long[] a = new long[2];
        private boolean b;

        public UbiTweak() {
            a();
        }

        public void a() {
            this.a[0] = 0;
            this.a[1] = 0;
            this.b = false;
            a(true);
        }

        public void a(int i) {
            this.a[1] = (this.a[1] & -274877906944L) | ((((long) i) & 63) << 56);
        }

        public void a(UbiTweak ubiTweak) {
            this.a = Arrays.clone(ubiTweak.a, this.a);
            this.b = ubiTweak.b;
        }

        public void a(boolean z) {
            if (z) {
                long[] jArr = this.a;
                jArr[1] = jArr[1] | Longs.MAX_POWER_OF_TWO;
                return;
            }
            long[] jArr2 = this.a;
            jArr2[1] = jArr2[1] & -4611686018427387905L;
        }

        public int b() {
            return (int) ((this.a[1] >>> 56) & 63);
        }

        public void b(int i) {
            if (this.b) {
                long[] jArr = {this.a[0] & 4294967295L, (this.a[0] >>> 32) & 4294967295L, this.a[1] & 4294967295L};
                long j = (long) i;
                for (int i2 = 0; i2 < jArr.length; i2++) {
                    long j2 = j + jArr[i2];
                    jArr[i2] = j2;
                    j = j2 >>> 32;
                }
                this.a[0] = ((jArr[1] & 4294967295L) << 32) | (jArr[0] & 4294967295L);
                this.a[1] = (this.a[1] & -4294967296L) | (jArr[2] & 4294967295L);
                return;
            }
            long j3 = this.a[0] + ((long) i);
            this.a[0] = j3;
            if (j3 > 9223372034707292160L) {
                this.b = true;
            }
        }

        public void b(boolean z) {
            if (z) {
                long[] jArr = this.a;
                jArr[1] = jArr[1] | Long.MIN_VALUE;
                return;
            }
            long[] jArr2 = this.a;
            jArr2[1] = jArr2[1] & Long.MAX_VALUE;
        }

        public boolean c() {
            return (this.a[1] & Longs.MAX_POWER_OF_TWO) != 0;
        }

        public boolean d() {
            return (this.a[1] & Long.MIN_VALUE) != 0;
        }

        public long[] e() {
            return this.a;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(b());
            sb.append(" first: ");
            sb.append(c());
            sb.append(", final: ");
            sb.append(d());
            return sb.toString();
        }
    }

    static {
        a(256, 128, new long[]{-2228972824489528736L, -8629553674646093540L, 1155188648486244218L, -3677226592081559102L});
        a(256, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, new long[]{1450197650740764312L, 3081844928540042640L, -3136097061834271170L, 3301952811952417661L});
        a(256, 224, new long[]{-4176654842910610933L, -8688192972455077604L, -7364642305011795836L, 4056579644589979102L});
        a(256, 256, new long[]{-243853671043386295L, 3443677322885453875L, -5531612722399640561L, 7662005193972177513L});
        a(512, 128, new long[]{-6288014694233956526L, 2204638249859346602L, 3502419045458743507L, -4829063503441264548L, 983504137758028059L, 1880512238245786339L, -6715892782214108542L, 7602827311880509485L});
        a(512, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, new long[]{2934123928682216849L, -4399710721982728305L, 1684584802963255058L, 5744138295201861711L, 2444857010922934358L, -2807833639722848072L, -5121587834665610502L, 118355523173251694L});
        a(512, 224, new long[]{-3688341020067007964L, -3772225436291745297L, -8300862168937575580L, 4146387520469897396L, 1106145742801415120L, 7455425944880474941L, -7351063101234211863L, -7048981346965512457L});
        a(512, 384, new long[]{-6631894876634615969L, -5692838220127733084L, -7099962856338682626L, -2911352911530754598L, 2000907093792408677L, 9140007292425499655L, 6093301768906360022L, 2769176472213098488L});
        a(512, 512, new long[]{5261240102383538638L, 978932832955457283L, -8083517948103779378L, -7339365279355032399L, 6752626034097301424L, -1531723821829733388L, -7417126464950782685L, -5901786942805128141L});
    }

    public SkeinEngine(int i2, int i3) {
        this.j = new byte[1];
        if (i3 % 8 != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Output size must be a multiple of 8 bits. :");
            sb.append(i3);
            throw new IllegalArgumentException(sb.toString());
        }
        this.d = i3 / 8;
        this.a = new ThreefishEngine(i2);
        this.i = new UBI(this.a.getBlockSize());
    }

    public SkeinEngine(SkeinEngine skeinEngine) {
        this(skeinEngine.getBlockSize() * 8, skeinEngine.getOutputSize() * 8);
        a(skeinEngine);
    }

    private static Integer a(int i2, int i3) {
        return new Integer(i2 | (i3 << 16));
    }

    private void a() {
        long[] jArr = (long[]) c.get(a(getBlockSize(), getOutputSize()));
        if (this.f != null || jArr == null) {
            this.b = new long[(getBlockSize() / 8)];
            if (this.f != null) {
                a(0, this.f);
            }
            a(4, new Configuration((long) (this.d * 8)).a());
        } else {
            this.b = Arrays.clone(jArr);
        }
        if (this.g != null) {
            for (Parameter parameter : this.g) {
                a(parameter.getType(), parameter.getValue());
            }
        }
        this.e = Arrays.clone(this.b);
    }

    private void a(int i2) {
        this.i.a(i2);
    }

    private static void a(int i2, int i3, long[] jArr) {
        c.put(a(i2 / 8, i3 / 8), jArr);
    }

    private void a(int i2, byte[] bArr) {
        a(i2);
        this.i.a(bArr, 0, bArr.length, this.b);
        b();
    }

    private void a(long j2, byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[8];
        ThreefishEngine.wordToBytes(j2, bArr2, 0);
        long[] jArr = new long[this.b.length];
        a(63);
        this.i.a(bArr2, 0, bArr2.length, jArr);
        this.i.a(jArr);
        int i4 = ((i3 + 8) - 1) / 8;
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5 * 8;
            int min = Math.min(8, i3 - i6);
            if (min == 8) {
                ThreefishEngine.wordToBytes(jArr[i5], bArr, i6 + i2);
            } else {
                ThreefishEngine.wordToBytes(jArr[i5], bArr2, 0);
                System.arraycopy(bArr2, 0, bArr, i6 + i2, min);
            }
        }
    }

    private void a(Hashtable hashtable) {
        Enumeration keys = hashtable.keys();
        Vector vector = new Vector();
        Vector vector2 = new Vector();
        while (keys.hasMoreElements()) {
            Integer num = (Integer) keys.nextElement();
            byte[] bArr = (byte[]) hashtable.get(num);
            if (num.intValue() == 0) {
                this.f = bArr;
            } else if (num.intValue() < 48) {
                vector.addElement(new Parameter(num.intValue(), bArr));
            } else {
                vector2.addElement(new Parameter(num.intValue(), bArr));
            }
        }
        this.g = new Parameter[vector.size()];
        vector.copyInto(this.g);
        a(this.g);
        this.h = new Parameter[vector2.size()];
        vector2.copyInto(this.h);
        a(this.h);
    }

    private void a(SkeinEngine skeinEngine) {
        this.i.a(skeinEngine.i);
        this.b = Arrays.clone(skeinEngine.b, this.b);
        this.e = Arrays.clone(skeinEngine.e, this.e);
        this.f = Arrays.clone(skeinEngine.f, this.f);
        this.g = a(skeinEngine.g, this.g);
        this.h = a(skeinEngine.h, this.h);
    }

    private static void a(Parameter[] parameterArr) {
        if (parameterArr != null) {
            for (int i2 = 1; i2 < parameterArr.length; i2++) {
                Parameter parameter = parameterArr[i2];
                int i3 = i2;
                while (i3 > 0) {
                    int i4 = i3 - 1;
                    if (parameter.getType() >= parameterArr[i4].getType()) {
                        break;
                    }
                    parameterArr[i3] = parameterArr[i4];
                    i3 = i4;
                }
                parameterArr[i3] = parameter;
            }
        }
    }

    private static Parameter[] a(Parameter[] parameterArr, Parameter[] parameterArr2) {
        if (parameterArr == null) {
            return null;
        }
        if (parameterArr2 == null || parameterArr2.length != parameterArr.length) {
            parameterArr2 = new Parameter[parameterArr.length];
        }
        System.arraycopy(parameterArr, 0, parameterArr2, 0, parameterArr2.length);
        return parameterArr2;
    }

    private void b() {
        this.i.a(this.b);
    }

    private void c() {
        if (this.i == null) {
            throw new IllegalArgumentException("Skein engine is not initialised.");
        }
    }

    public Memoable copy() {
        return new SkeinEngine(this);
    }

    public int doFinal(byte[] bArr, int i2) {
        c();
        if (bArr.length < this.d + i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Output buffer is too short to hold output of ");
            sb.append(this.d);
            sb.append(" bytes");
            throw new DataLengthException(sb.toString());
        }
        b();
        if (this.h != null) {
            for (Parameter parameter : this.h) {
                a(parameter.getType(), parameter.getValue());
            }
        }
        int blockSize = getBlockSize();
        int i3 = ((this.d + blockSize) - 1) / blockSize;
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i4 * blockSize;
            a((long) i4, bArr, i2 + i5, Math.min(blockSize, this.d - i5));
        }
        reset();
        return this.d;
    }

    public int getBlockSize() {
        return this.a.getBlockSize();
    }

    public int getOutputSize() {
        return this.d;
    }

    public void init(SkeinParameters skeinParameters) {
        this.b = null;
        this.f = null;
        this.g = null;
        this.h = null;
        if (skeinParameters != null) {
            if (skeinParameters.getKey().length < 16) {
                throw new IllegalArgumentException("Skein key must be at least 128 bits.");
            }
            a(skeinParameters.getParameters());
        }
        a();
        a(48);
    }

    public void reset() {
        System.arraycopy(this.e, 0, this.b, 0, this.b.length);
        a(48);
    }

    public void reset(Memoable memoable) {
        SkeinEngine skeinEngine = (SkeinEngine) memoable;
        if (getBlockSize() == skeinEngine.getBlockSize() && this.d == skeinEngine.d) {
            a(skeinEngine);
            return;
        }
        throw new IllegalArgumentException("Incompatible parameters in provided SkeinEngine.");
    }

    public void update(byte b2) {
        this.j[0] = b2;
        update(this.j, 0, 1);
    }

    public void update(byte[] bArr, int i2, int i3) {
        c();
        this.i.a(bArr, i2, i3, this.b);
    }
}
