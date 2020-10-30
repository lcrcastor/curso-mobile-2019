package org.bouncycastle.crypto.agreement.jpake;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Strings;

public class JPAKEUtil {
    static final BigInteger a = BigInteger.valueOf(0);
    static final BigInteger b = BigInteger.valueOf(1);

    private static BigInteger a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, String str, Digest digest) {
        digest.reset();
        b(digest, bigInteger);
        b(digest, bigInteger2);
        b(digest, bigInteger3);
        b(digest, str);
        byte[] bArr = new byte[digest.getDigestSize()];
        digest.doFinal(bArr, 0);
        return new BigInteger(bArr);
    }

    private static void a(Digest digest, String str) {
        byte[] uTF8ByteArray = Strings.toUTF8ByteArray(str);
        digest.update(uTF8ByteArray, 0, uTF8ByteArray.length);
        Arrays.fill(uTF8ByteArray, 0);
    }

    private static void a(Digest digest, BigInteger bigInteger) {
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(bigInteger);
        digest.update(asUnsignedByteArray, 0, asUnsignedByteArray.length);
        Arrays.fill(asUnsignedByteArray, 0);
    }

    private static void a(Mac mac, String str) {
        byte[] uTF8ByteArray = Strings.toUTF8ByteArray(str);
        mac.update(uTF8ByteArray, 0, uTF8ByteArray.length);
        Arrays.fill(uTF8ByteArray, 0);
    }

    private static void a(Mac mac, BigInteger bigInteger) {
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(bigInteger);
        mac.update(asUnsignedByteArray, 0, asUnsignedByteArray.length);
        Arrays.fill(asUnsignedByteArray, 0);
    }

    private static byte[] a(int i) {
        return new byte[]{(byte) (i >>> 24), (byte) (i >>> 16), (byte) (i >>> 8), (byte) i};
    }

    private static byte[] a(BigInteger bigInteger, Digest digest) {
        digest.reset();
        a(digest, bigInteger);
        a(digest, "JPAKE_KC");
        byte[] bArr = new byte[digest.getDigestSize()];
        digest.doFinal(bArr, 0);
        return bArr;
    }

    private static void b(Digest digest, String str) {
        byte[] uTF8ByteArray = Strings.toUTF8ByteArray(str);
        digest.update(a(uTF8ByteArray.length), 0, 4);
        digest.update(uTF8ByteArray, 0, uTF8ByteArray.length);
        Arrays.fill(uTF8ByteArray, 0);
    }

    private static void b(Digest digest, BigInteger bigInteger) {
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(bigInteger);
        digest.update(a(asUnsignedByteArray.length), 0, 4);
        digest.update(asUnsignedByteArray, 0, asUnsignedByteArray.length);
        Arrays.fill(asUnsignedByteArray, 0);
    }

    public static BigInteger calculateA(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        return bigInteger3.modPow(bigInteger4, bigInteger);
    }

    public static BigInteger calculateGA(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        return bigInteger2.multiply(bigInteger3).multiply(bigInteger4).mod(bigInteger);
    }

    public static BigInteger calculateGx(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        return bigInteger2.modPow(bigInteger3, bigInteger);
    }

    public static BigInteger calculateKeyingMaterial(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6) {
        return bigInteger3.modPow(bigInteger4.multiply(bigInteger5).negate().mod(bigInteger2), bigInteger).multiply(bigInteger6).modPow(bigInteger4, bigInteger);
    }

    public static BigInteger calculateMacTag(String str, String str2, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, Digest digest) {
        byte[] a2 = a(bigInteger5, digest);
        HMac hMac = new HMac(digest);
        byte[] bArr = new byte[hMac.getMacSize()];
        hMac.init(new KeyParameter(a2));
        a((Mac) hMac, "KC_1_U");
        a((Mac) hMac, str);
        a((Mac) hMac, str2);
        a((Mac) hMac, bigInteger);
        a((Mac) hMac, bigInteger2);
        a((Mac) hMac, bigInteger3);
        a((Mac) hMac, bigInteger4);
        hMac.doFinal(bArr, 0);
        Arrays.fill(a2, 0);
        return new BigInteger(bArr);
    }

    public static BigInteger calculateS(char[] cArr) {
        return new BigInteger(Strings.toUTF8ByteArray(cArr));
    }

    public static BigInteger calculateX2s(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        return bigInteger2.multiply(bigInteger3).mod(bigInteger);
    }

    public static BigInteger[] calculateZeroKnowledgeProof(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, String str, Digest digest, SecureRandom secureRandom) {
        BigInteger createRandomInRange = BigIntegers.createRandomInRange(a, bigInteger2.subtract(b), secureRandom);
        BigInteger modPow = bigInteger3.modPow(createRandomInRange, bigInteger);
        return new BigInteger[]{modPow, createRandomInRange.subtract(bigInteger5.multiply(a(bigInteger3, modPow, bigInteger4, str, digest))).mod(bigInteger2)};
    }

    public static BigInteger generateX1(BigInteger bigInteger, SecureRandom secureRandom) {
        return BigIntegers.createRandomInRange(a, bigInteger.subtract(b), secureRandom);
    }

    public static BigInteger generateX2(BigInteger bigInteger, SecureRandom secureRandom) {
        return BigIntegers.createRandomInRange(b, bigInteger.subtract(b), secureRandom);
    }

    public static void validateGa(BigInteger bigInteger) {
        if (bigInteger.equals(b)) {
            throw new CryptoException("ga is equal to 1.  It should not be.  The chances of this happening are on the order of 2^160 for a 160-bit q.  Try again.");
        }
    }

    public static void validateGx4(BigInteger bigInteger) {
        if (bigInteger.equals(b)) {
            throw new CryptoException("g^x validation failed.  g^x should not be 1.");
        }
    }

    public static void validateMacTag(String str, String str2, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, Digest digest, BigInteger bigInteger6) {
        if (!calculateMacTag(str2, str, bigInteger3, bigInteger4, bigInteger, bigInteger2, bigInteger5, digest).equals(bigInteger6)) {
            throw new CryptoException("Partner MacTag validation failed. Therefore, the password, MAC, or digest algorithm of each participant does not match.");
        }
    }

    public static void validateNotNull(Object obj, String str) {
        if (obj == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" must not be null");
            throw new NullPointerException(sb.toString());
        }
    }

    public static void validateParticipantIdsDiffer(String str, String str2) {
        if (str.equals(str2)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Both participants are using the same participantId (");
            sb.append(str);
            sb.append("). This is not allowed. ");
            sb.append("Each participant must use a unique participantId.");
            throw new CryptoException(sb.toString());
        }
    }

    public static void validateParticipantIdsEqual(String str, String str2) {
        if (!str.equals(str2)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Received payload from incorrect partner (");
            sb.append(str2);
            sb.append("). Expected to receive payload from ");
            sb.append(str);
            sb.append(".");
            throw new CryptoException(sb.toString());
        }
    }

    public static void validateZeroKnowledgeProof(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger[] bigIntegerArr, String str, Digest digest) {
        BigInteger bigInteger5 = bigIntegerArr[0];
        BigInteger bigInteger6 = bigIntegerArr[1];
        BigInteger a2 = a(bigInteger3, bigInteger5, bigInteger4, str, digest);
        if (bigInteger4.compareTo(a) != 1 || bigInteger4.compareTo(bigInteger) != -1 || bigInteger4.modPow(bigInteger2, bigInteger).compareTo(b) != 0 || bigInteger3.modPow(bigInteger6, bigInteger).multiply(bigInteger4.modPow(a2, bigInteger)).mod(bigInteger).compareTo(bigInteger5) != 0) {
            throw new CryptoException("Zero-knowledge proof validation failed");
        }
    }
}
