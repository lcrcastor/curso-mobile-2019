package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.annotation.Nullable;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.eac.CertificateBody;

@Beta
public final class Hashing {
    /* access modifiers changed from: private */
    public static final int a = ((int) System.currentTimeMillis());

    static final class ConcatenatedHashFunction extends AbstractCompositeHashFunction {
        private final int b;

        private ConcatenatedHashFunction(HashFunction... hashFunctionArr) {
            super(hashFunctionArr);
            int i = 0;
            for (HashFunction hashFunction : hashFunctionArr) {
                i += hashFunction.bits();
                Preconditions.checkArgument(hashFunction.bits() % 8 == 0, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", hashFunction.bits(), (Object) hashFunction);
            }
            this.b = i;
        }

        /* access modifiers changed from: 0000 */
        public HashCode a(Hasher[] hasherArr) {
            byte[] bArr = new byte[(this.b / 8)];
            int i = 0;
            for (Hasher hash : hasherArr) {
                HashCode hash2 = hash.hash();
                i += hash2.writeBytesTo(bArr, i, hash2.bits() / 8);
            }
            return HashCode.a(bArr);
        }

        public int bits() {
            return this.b;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof ConcatenatedHashFunction)) {
                return false;
            }
            return Arrays.equals(this.a, ((ConcatenatedHashFunction) obj).a);
        }

        public int hashCode() {
            return (Arrays.hashCode(this.a) * 31) + this.b;
        }
    }

    static class Adler32Holder {
        static final HashFunction a = Hashing.b(ChecksumType.ADLER_32, "Hashing.adler32()");

        private Adler32Holder() {
        }
    }

    enum ChecksumType implements Supplier<Checksum> {
        CRC_32(32) {
            /* renamed from: a */
            public Checksum get() {
                return new CRC32();
            }
        },
        ADLER_32(32) {
            /* renamed from: a */
            public Checksum get() {
                return new Adler32();
            }
        };
        
        /* access modifiers changed from: private */
        public final int c;

        /* renamed from: a */
        public abstract Checksum get();

        private ChecksumType(int i) {
            this.c = i;
        }
    }

    static class Crc32Holder {
        static final HashFunction a = Hashing.b(ChecksumType.CRC_32, "Hashing.crc32()");

        private Crc32Holder() {
        }
    }

    static final class Crc32cHolder {
        static final HashFunction a = new Crc32cHashFunction();

        private Crc32cHolder() {
        }
    }

    static class FarmHashFingerprint64Holder {
        static final HashFunction a = new FarmHashFingerprint64();

        private FarmHashFingerprint64Holder() {
        }
    }

    static final class LinearCongruentialGenerator {
        private long a;

        public LinearCongruentialGenerator(long j) {
            this.a = j;
        }

        public double a() {
            this.a = (this.a * 2862933555777941757L) + 1;
            return ((double) (((int) (this.a >>> 33)) + 1)) / 2.147483648E9d;
        }
    }

    static class Md5Holder {
        static final HashFunction a = new MessageDigestHashFunction(CommonUtils.MD5_INSTANCE, "Hashing.md5()");

        private Md5Holder() {
        }
    }

    static class Murmur3_128Holder {
        static final HashFunction a = new Murmur3_128HashFunction(0);
        static final HashFunction b = Hashing.murmur3_128(Hashing.a);

        private Murmur3_128Holder() {
        }
    }

    static class Murmur3_32Holder {
        static final HashFunction a = new Murmur3_32HashFunction(0);
        static final HashFunction b = Hashing.murmur3_32(Hashing.a);

        private Murmur3_32Holder() {
        }
    }

    static class Sha1Holder {
        static final HashFunction a = new MessageDigestHashFunction(CommonUtils.SHA1_INSTANCE, "Hashing.sha1()");

        private Sha1Holder() {
        }
    }

    static class Sha256Holder {
        static final HashFunction a = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");

        private Sha256Holder() {
        }
    }

    static class Sha384Holder {
        static final HashFunction a = new MessageDigestHashFunction("SHA-384", "Hashing.sha384()");

        private Sha384Holder() {
        }
    }

    static class Sha512Holder {
        static final HashFunction a = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");

        private Sha512Holder() {
        }
    }

    static class SipHash24Holder {
        static final HashFunction a;

        private SipHash24Holder() {
        }

        static {
            SipHashFunction sipHashFunction = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
            a = sipHashFunction;
        }
    }

    public static HashFunction goodFastHash(int i) {
        int a2 = a(i);
        if (a2 == 32) {
            return Murmur3_32Holder.b;
        }
        if (a2 <= 128) {
            return Murmur3_128Holder.b;
        }
        int i2 = (a2 + CertificateBody.profileType) / 128;
        HashFunction[] hashFunctionArr = new HashFunction[i2];
        hashFunctionArr[0] = Murmur3_128Holder.b;
        int i3 = a;
        for (int i4 = 1; i4 < i2; i4++) {
            i3 += 1500450271;
            hashFunctionArr[i4] = murmur3_128(i3);
        }
        return new ConcatenatedHashFunction(hashFunctionArr);
    }

    public static HashFunction murmur3_32(int i) {
        return new Murmur3_32HashFunction(i);
    }

    public static HashFunction murmur3_32() {
        return Murmur3_32Holder.a;
    }

    public static HashFunction murmur3_128(int i) {
        return new Murmur3_128HashFunction(i);
    }

    public static HashFunction murmur3_128() {
        return Murmur3_128Holder.a;
    }

    public static HashFunction sipHash24() {
        return SipHash24Holder.a;
    }

    public static HashFunction sipHash24(long j, long j2) {
        SipHashFunction sipHashFunction = new SipHashFunction(2, 4, j, j2);
        return sipHashFunction;
    }

    public static HashFunction md5() {
        return Md5Holder.a;
    }

    public static HashFunction sha1() {
        return Sha1Holder.a;
    }

    public static HashFunction sha256() {
        return Sha256Holder.a;
    }

    public static HashFunction sha384() {
        return Sha384Holder.a;
    }

    public static HashFunction sha512() {
        return Sha512Holder.a;
    }

    public static HashFunction hmacMd5(Key key) {
        return new MacHashFunction("HmacMD5", key, a("hmacMd5", key));
    }

    public static HashFunction hmacMd5(byte[] bArr) {
        return hmacMd5((Key) new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacMD5"));
    }

    public static HashFunction hmacSha1(Key key) {
        return new MacHashFunction("HmacSHA1", key, a("hmacSha1", key));
    }

    public static HashFunction hmacSha1(byte[] bArr) {
        return hmacSha1((Key) new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA1"));
    }

    public static HashFunction hmacSha256(Key key) {
        return new MacHashFunction("HmacSHA256", key, a("hmacSha256", key));
    }

    public static HashFunction hmacSha256(byte[] bArr) {
        return hmacSha256((Key) new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA256"));
    }

    public static HashFunction hmacSha512(Key key) {
        return new MacHashFunction("HmacSHA512", key, a("hmacSha512", key));
    }

    public static HashFunction hmacSha512(byte[] bArr) {
        return hmacSha512((Key) new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA512"));
    }

    private static String a(String str, Key key) {
        return String.format("Hashing.%s(Key[algorithm=%s, format=%s])", new Object[]{str, key.getAlgorithm(), key.getFormat()});
    }

    public static HashFunction crc32c() {
        return Crc32cHolder.a;
    }

    public static HashFunction crc32() {
        return Crc32Holder.a;
    }

    public static HashFunction adler32() {
        return Adler32Holder.a;
    }

    /* access modifiers changed from: private */
    public static HashFunction b(ChecksumType checksumType, String str) {
        return new ChecksumHashFunction(checksumType, checksumType.c, str);
    }

    public static HashFunction farmHashFingerprint64() {
        return FarmHashFingerprint64Holder.a;
    }

    public static int consistentHash(HashCode hashCode, int i) {
        return consistentHash(hashCode.padToLong(), i);
    }

    public static int consistentHash(long j, int i) {
        int i2 = 0;
        Preconditions.checkArgument(i > 0, "buckets must be positive: %s", i);
        LinearCongruentialGenerator linearCongruentialGenerator = new LinearCongruentialGenerator(j);
        while (true) {
            int a2 = (int) (((double) (i2 + 1)) / linearCongruentialGenerator.a());
            if (a2 < 0 || a2 >= i) {
                return i2;
            }
            i2 = a2;
        }
        return i2;
    }

    public static HashCode combineOrdered(Iterable<HashCode> iterable) {
        Iterator it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] bArr = new byte[(((HashCode) it.next()).bits() / 8)];
        for (HashCode asBytes : iterable) {
            byte[] asBytes2 = asBytes.asBytes();
            Preconditions.checkArgument(asBytes2.length == bArr.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < asBytes2.length; i++) {
                bArr[i] = (byte) ((bArr[i] * 37) ^ asBytes2[i]);
            }
        }
        return HashCode.a(bArr);
    }

    public static HashCode combineUnordered(Iterable<HashCode> iterable) {
        Iterator it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] bArr = new byte[(((HashCode) it.next()).bits() / 8)];
        for (HashCode asBytes : iterable) {
            byte[] asBytes2 = asBytes.asBytes();
            Preconditions.checkArgument(asBytes2.length == bArr.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < asBytes2.length; i++) {
                bArr[i] = (byte) (bArr[i] + asBytes2[i]);
            }
        }
        return HashCode.a(bArr);
    }

    static int a(int i) {
        Preconditions.checkArgument(i > 0, "Number of bits must be positive");
        return (i + 31) & -32;
    }

    public static HashFunction concatenating(HashFunction hashFunction, HashFunction hashFunction2, HashFunction... hashFunctionArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(hashFunction);
        arrayList.add(hashFunction2);
        for (HashFunction add : hashFunctionArr) {
            arrayList.add(add);
        }
        return new ConcatenatedHashFunction((HashFunction[]) arrayList.toArray(new HashFunction[0]));
    }

    public static HashFunction concatenating(Iterable<HashFunction> iterable) {
        Preconditions.checkNotNull(iterable);
        ArrayList arrayList = new ArrayList();
        for (HashFunction add : iterable) {
            arrayList.add(add);
        }
        Preconditions.checkArgument(arrayList.size() > 0, "number of hash functions (%s) must be > 0", arrayList.size());
        return new ConcatenatedHashFunction((HashFunction[]) arrayList.toArray(new HashFunction[0]));
    }

    private Hashing() {
    }
}
