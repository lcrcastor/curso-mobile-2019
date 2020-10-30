package org.bouncycastle.crypto.prng;

import java.security.SecureRandom;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.prng.drbg.CTRSP800DRBG;
import org.bouncycastle.crypto.prng.drbg.DualECPoints;
import org.bouncycastle.crypto.prng.drbg.DualECSP800DRBG;
import org.bouncycastle.crypto.prng.drbg.HMacSP800DRBG;
import org.bouncycastle.crypto.prng.drbg.HashSP800DRBG;
import org.bouncycastle.crypto.prng.drbg.SP80090DRBG;

public class SP800SecureRandomBuilder {
    private final SecureRandom a;
    private final EntropySourceProvider b;
    private byte[] c;
    private int d;
    private int e;

    static class CTRDRBGProvider implements DRBGProvider {
        private final BlockCipher a;
        private final int b;
        private final byte[] c;
        private final byte[] d;
        private final int e;

        public CTRDRBGProvider(BlockCipher blockCipher, int i, byte[] bArr, byte[] bArr2, int i2) {
            this.a = blockCipher;
            this.b = i;
            this.c = bArr;
            this.d = bArr2;
            this.e = i2;
        }

        public SP80090DRBG a(EntropySource entropySource) {
            CTRSP800DRBG ctrsp800drbg = new CTRSP800DRBG(this.a, this.b, this.e, entropySource, this.d, this.c);
            return ctrsp800drbg;
        }
    }

    static class ConfigurableDualECDRBGProvider implements DRBGProvider {
        private final DualECPoints[] a;
        private final Digest b;
        private final byte[] c;
        private final byte[] d;
        private final int e;

        public ConfigurableDualECDRBGProvider(DualECPoints[] dualECPointsArr, Digest digest, byte[] bArr, byte[] bArr2, int i) {
            this.a = new DualECPoints[dualECPointsArr.length];
            System.arraycopy(dualECPointsArr, 0, this.a, 0, dualECPointsArr.length);
            this.b = digest;
            this.c = bArr;
            this.d = bArr2;
            this.e = i;
        }

        public SP80090DRBG a(EntropySource entropySource) {
            DualECSP800DRBG dualECSP800DRBG = new DualECSP800DRBG(this.a, this.b, this.e, entropySource, this.d, this.c);
            return dualECSP800DRBG;
        }
    }

    static class DualECDRBGProvider implements DRBGProvider {
        private final Digest a;
        private final byte[] b;
        private final byte[] c;
        private final int d;

        public DualECDRBGProvider(Digest digest, byte[] bArr, byte[] bArr2, int i) {
            this.a = digest;
            this.b = bArr;
            this.c = bArr2;
            this.d = i;
        }

        public SP80090DRBG a(EntropySource entropySource) {
            DualECSP800DRBG dualECSP800DRBG = new DualECSP800DRBG(this.a, this.d, entropySource, this.c, this.b);
            return dualECSP800DRBG;
        }
    }

    static class HMacDRBGProvider implements DRBGProvider {
        private final Mac a;
        private final byte[] b;
        private final byte[] c;
        private final int d;

        public HMacDRBGProvider(Mac mac, byte[] bArr, byte[] bArr2, int i) {
            this.a = mac;
            this.b = bArr;
            this.c = bArr2;
            this.d = i;
        }

        public SP80090DRBG a(EntropySource entropySource) {
            HMacSP800DRBG hMacSP800DRBG = new HMacSP800DRBG(this.a, this.d, entropySource, this.c, this.b);
            return hMacSP800DRBG;
        }
    }

    static class HashDRBGProvider implements DRBGProvider {
        private final Digest a;
        private final byte[] b;
        private final byte[] c;
        private final int d;

        public HashDRBGProvider(Digest digest, byte[] bArr, byte[] bArr2, int i) {
            this.a = digest;
            this.b = bArr;
            this.c = bArr2;
            this.d = i;
        }

        public SP80090DRBG a(EntropySource entropySource) {
            HashSP800DRBG hashSP800DRBG = new HashSP800DRBG(this.a, this.d, entropySource, this.c, this.b);
            return hashSP800DRBG;
        }
    }

    public SP800SecureRandomBuilder() {
        this(new SecureRandom(), false);
    }

    public SP800SecureRandomBuilder(SecureRandom secureRandom, boolean z) {
        this.d = 256;
        this.e = 256;
        this.a = secureRandom;
        this.b = new BasicEntropySourceProvider(this.a, z);
    }

    public SP800SecureRandomBuilder(EntropySourceProvider entropySourceProvider) {
        this.d = 256;
        this.e = 256;
        this.a = null;
        this.b = entropySourceProvider;
    }

    public SP800SecureRandom buildCTR(BlockCipher blockCipher, int i, byte[] bArr, boolean z) {
        SecureRandom secureRandom = this.a;
        EntropySource entropySource = this.b.get(this.e);
        CTRDRBGProvider cTRDRBGProvider = new CTRDRBGProvider(blockCipher, i, bArr, this.c, this.d);
        return new SP800SecureRandom(secureRandom, entropySource, cTRDRBGProvider, z);
    }

    public SP800SecureRandom buildDualEC(Digest digest, byte[] bArr, boolean z) {
        return new SP800SecureRandom(this.a, this.b.get(this.e), new DualECDRBGProvider(digest, bArr, this.c, this.d), z);
    }

    public SP800SecureRandom buildDualEC(DualECPoints[] dualECPointsArr, Digest digest, byte[] bArr, boolean z) {
        SecureRandom secureRandom = this.a;
        EntropySource entropySource = this.b.get(this.e);
        ConfigurableDualECDRBGProvider configurableDualECDRBGProvider = new ConfigurableDualECDRBGProvider(dualECPointsArr, digest, bArr, this.c, this.d);
        return new SP800SecureRandom(secureRandom, entropySource, configurableDualECDRBGProvider, z);
    }

    public SP800SecureRandom buildHMAC(Mac mac, byte[] bArr, boolean z) {
        return new SP800SecureRandom(this.a, this.b.get(this.e), new HMacDRBGProvider(mac, bArr, this.c, this.d), z);
    }

    public SP800SecureRandom buildHash(Digest digest, byte[] bArr, boolean z) {
        return new SP800SecureRandom(this.a, this.b.get(this.e), new HashDRBGProvider(digest, bArr, this.c, this.d), z);
    }

    public SP800SecureRandomBuilder setEntropyBitsRequired(int i) {
        this.e = i;
        return this;
    }

    public SP800SecureRandomBuilder setPersonalizationString(byte[] bArr) {
        this.c = bArr;
        return this;
    }

    public SP800SecureRandomBuilder setSecurityStrength(int i) {
        this.d = i;
        return this;
    }
}
