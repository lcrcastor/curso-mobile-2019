package org.bouncycastle.crypto.tls;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.io.Streams;

public class TlsUtils {
    public static final byte[] EMPTY_BYTES = new byte[0];
    public static final Integer EXT_signature_algorithms = Integers.valueOf(13);
    static final byte[] a = {67, 76, 78, 84};
    static final byte[] b = {83, 82, 86, 82};
    static final byte[][] c = a();

    public static byte[] PRF(TlsContext tlsContext, byte[] bArr, String str, byte[] bArr2, int i) {
        if (tlsContext.getServerVersion().isSSL()) {
            throw new IllegalStateException("No PRF available for SSLv3 session");
        }
        byte[] byteArray = Strings.toByteArray(str);
        byte[] a2 = a(byteArray, bArr2);
        int prfAlgorithm = tlsContext.getSecurityParameters().getPrfAlgorithm();
        if (prfAlgorithm == 0) {
            return a(bArr, byteArray, a2, i);
        }
        byte[] bArr3 = new byte[i];
        a(createPRFHash(prfAlgorithm), bArr, a2, bArr3);
        return bArr3;
    }

    public static byte[] PRF_legacy(byte[] bArr, String str, byte[] bArr2, int i) {
        byte[] byteArray = Strings.toByteArray(str);
        return a(bArr, byteArray, a(byteArray, bArr2), i);
    }

    private static Vector a(Object obj) {
        Vector vector = new Vector(1);
        vector.addElement(obj);
        return vector;
    }

    static short a(Certificate certificate, Certificate certificate2) {
        if (certificate.isEmpty()) {
            return -1;
        }
        Certificate certificateAt = certificate.getCertificateAt(0);
        try {
            AsymmetricKeyParameter createKey = PublicKeyFactory.createKey(certificateAt.getSubjectPublicKeyInfo());
            if (createKey.isPrivate()) {
                throw new TlsFatalAlert(80);
            } else if (createKey instanceof RSAKeyParameters) {
                a(certificateAt, 128);
                return 1;
            } else if (createKey instanceof DSAPublicKeyParameters) {
                a(certificateAt, 128);
                return 2;
            } else {
                if (createKey instanceof ECPublicKeyParameters) {
                    a(certificateAt, 128);
                    return 64;
                }
                throw new TlsFatalAlert(43);
            }
        } catch (Exception unused) {
        }
    }

    static void a(Certificate certificate, int i) {
        Extensions extensions = certificate.getTBSCertificate().getExtensions();
        if (extensions != null) {
            KeyUsage fromExtensions = KeyUsage.fromExtensions(extensions);
            if (fromExtensions != null && (fromExtensions.getBytes()[0] & UnsignedBytes.MAX_VALUE & i) != i) {
                throw new TlsFatalAlert(46);
            }
        }
    }

    static void a(Digest digest, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        HMac hMac = new HMac(digest);
        hMac.init(new KeyParameter(bArr));
        int digestSize = digest.getDigestSize();
        int length = ((bArr3.length + digestSize) - 1) / digestSize;
        byte[] bArr4 = new byte[hMac.getMacSize()];
        byte[] bArr5 = new byte[hMac.getMacSize()];
        byte[] bArr6 = bArr2;
        int i = 0;
        while (i < length) {
            hMac.update(bArr6, 0, bArr6.length);
            hMac.doFinal(bArr4, 0);
            hMac.update(bArr4, 0, bArr4.length);
            hMac.update(bArr2, 0, bArr2.length);
            hMac.doFinal(bArr5, 0);
            int i2 = digestSize * i;
            System.arraycopy(bArr5, 0, bArr3, i2, Math.min(digestSize, bArr3.length - i2));
            i++;
            bArr6 = bArr4;
        }
    }

    static void a(TlsHandshakeHash tlsHandshakeHash, Vector vector) {
        if (vector != null) {
            for (int i = 0; i < vector.size(); i++) {
                tlsHandshakeHash.trackHashAlgorithm(((SignatureAndHashAlgorithm) vector.elementAt(i)).getHash());
            }
        }
    }

    static byte[] a(TlsContext tlsContext, int i) {
        SecurityParameters securityParameters = tlsContext.getSecurityParameters();
        byte[] masterSecret = securityParameters.getMasterSecret();
        byte[] a2 = a(securityParameters.getServerRandom(), securityParameters.getClientRandom());
        return isSSL(tlsContext) ? a(masterSecret, a2, i) : PRF(tlsContext, masterSecret, ExporterLabel.key_expansion, a2, i);
    }

    static byte[] a(TlsContext tlsContext, String str, byte[] bArr) {
        if (isSSL(tlsContext)) {
            return bArr;
        }
        SecurityParameters securityParameters = tlsContext.getSecurityParameters();
        return PRF(tlsContext, securityParameters.getMasterSecret(), str, bArr, securityParameters.getVerifyDataLength());
    }

    static byte[] a(TlsContext tlsContext, byte[] bArr) {
        SecurityParameters securityParameters = tlsContext.getSecurityParameters();
        byte[] a2 = a(securityParameters.getClientRandom(), securityParameters.getServerRandom());
        return isSSL(tlsContext) ? b(bArr, a2) : PRF(tlsContext, bArr, ExporterLabel.master_secret, a2, 48);
    }

    static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    static byte[] a(byte[] bArr, byte[] bArr2, int i) {
        Digest createHash = createHash(1);
        Digest createHash2 = createHash(2);
        int digestSize = createHash.getDigestSize();
        byte[] bArr3 = new byte[createHash2.getDigestSize()];
        byte[] bArr4 = new byte[(i + digestSize)];
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            byte[] bArr5 = c[i3];
            createHash2.update(bArr5, 0, bArr5.length);
            createHash2.update(bArr, 0, bArr.length);
            createHash2.update(bArr2, 0, bArr2.length);
            createHash2.doFinal(bArr3, 0);
            createHash.update(bArr, 0, bArr.length);
            createHash.update(bArr3, 0, bArr3.length);
            createHash.doFinal(bArr4, i2);
            i2 += digestSize;
            i3++;
        }
        byte[] bArr6 = new byte[i];
        System.arraycopy(bArr4, 0, bArr6, 0, i);
        return bArr6;
    }

    static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        int length = (bArr.length + 1) / 2;
        byte[] bArr4 = new byte[length];
        byte[] bArr5 = new byte[length];
        System.arraycopy(bArr, 0, bArr4, 0, length);
        System.arraycopy(bArr, bArr.length - length, bArr5, 0, length);
        byte[] bArr6 = new byte[i];
        byte[] bArr7 = new byte[i];
        a(createHash(1), bArr4, bArr3, bArr6);
        a(createHash(2), bArr5, bArr3, bArr7);
        for (int i2 = 0; i2 < i; i2++) {
            bArr6[i2] = (byte) (bArr6[i2] ^ bArr7[i2]);
        }
        return bArr6;
    }

    private static byte[][] a() {
        byte[][] bArr = new byte[10][];
        int i = 0;
        while (i < 10) {
            int i2 = i + 1;
            byte[] bArr2 = new byte[i2];
            Arrays.fill(bArr2, (byte) (i + 65));
            bArr[i] = bArr2;
            i = i2;
        }
        return bArr;
    }

    public static void addSignatureAlgorithmsExtension(Hashtable hashtable, Vector vector) {
        hashtable.put(EXT_signature_algorithms, createSignatureAlgorithmsExtension(vector));
    }

    static byte[] b(byte[] bArr, byte[] bArr2) {
        Digest createHash = createHash(1);
        Digest createHash2 = createHash(2);
        int digestSize = createHash.getDigestSize();
        byte[] bArr3 = new byte[createHash2.getDigestSize()];
        byte[] bArr4 = new byte[(digestSize * 3)];
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            byte[] bArr5 = c[i2];
            createHash2.update(bArr5, 0, bArr5.length);
            createHash2.update(bArr, 0, bArr.length);
            createHash2.update(bArr2, 0, bArr2.length);
            createHash2.doFinal(bArr3, 0);
            createHash.update(bArr, 0, bArr.length);
            createHash.update(bArr3, 0, bArr3.length);
            createHash.doFinal(bArr4, i);
            i += digestSize;
        }
        return bArr4;
    }

    public static void checkUint16(int i) {
        if (!isValidUint16(i)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint16(long j) {
        if (!isValidUint16(j)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint24(int i) {
        if (!isValidUint24(i)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint24(long j) {
        if (!isValidUint24(j)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint32(long j) {
        if (!isValidUint32(j)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint48(long j) {
        if (!isValidUint48(j)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint64(long j) {
        if (!isValidUint64(j)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint8(int i) {
        if (!isValidUint8(i)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint8(long j) {
        if (!isValidUint8(j)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static void checkUint8(short s) {
        if (!isValidUint8(s)) {
            throw new TlsFatalAlert(80);
        }
    }

    public static Digest cloneHash(short s, Digest digest) {
        switch (s) {
            case 1:
                return new MD5Digest((MD5Digest) digest);
            case 2:
                return new SHA1Digest((SHA1Digest) digest);
            case 3:
                return new SHA224Digest((SHA224Digest) digest);
            case 4:
                return new SHA256Digest((SHA256Digest) digest);
            case 5:
                return new SHA384Digest((SHA384Digest) digest);
            case 6:
                return new SHA512Digest((SHA512Digest) digest);
            default:
                throw new IllegalArgumentException("unknown HashAlgorithm");
        }
    }

    public static Digest clonePRFHash(int i, Digest digest) {
        return i != 0 ? cloneHash(getHashAlgorithmForPRFAlgorithm(i), digest) : new CombinedHash((CombinedHash) digest);
    }

    public static Digest createHash(short s) {
        switch (s) {
            case 1:
                return new MD5Digest();
            case 2:
                return new SHA1Digest();
            case 3:
                return new SHA224Digest();
            case 4:
                return new SHA256Digest();
            case 5:
                return new SHA384Digest();
            case 6:
                return new SHA512Digest();
            default:
                throw new IllegalArgumentException("unknown HashAlgorithm");
        }
    }

    public static Digest createPRFHash(int i) {
        return i != 0 ? createHash(getHashAlgorithmForPRFAlgorithm(i)) : new CombinedHash();
    }

    public static byte[] createSignatureAlgorithmsExtension(Vector vector) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encodeSupportedSignatureAlgorithms(vector, false, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static TlsSigner createTlsSigner(short s) {
        if (s == 64) {
            return new TlsECDSASigner();
        }
        switch (s) {
            case 1:
                return new TlsRSASigner();
            case 2:
                return new TlsDSSSigner();
            default:
                throw new IllegalArgumentException("'clientCertificateType' is not a type with signing capability");
        }
    }

    public static byte[] encodeOpaque8(byte[] bArr) {
        checkUint8(bArr.length);
        return Arrays.prepend(bArr, (byte) bArr.length);
    }

    public static void encodeSupportedSignatureAlgorithms(Vector vector, boolean z, OutputStream outputStream) {
        if (vector == null || vector.size() < 1 || vector.size() >= 32768) {
            throw new IllegalArgumentException("'supportedSignatureAlgorithms' must have length from 1 to (2^15 - 1)");
        }
        int size = vector.size() * 2;
        checkUint16(size);
        writeUint16(size, outputStream);
        int i = 0;
        while (i < vector.size()) {
            SignatureAndHashAlgorithm signatureAndHashAlgorithm = (SignatureAndHashAlgorithm) vector.elementAt(i);
            if (z || signatureAndHashAlgorithm.getSignature() != 0) {
                signatureAndHashAlgorithm.encode(outputStream);
                i++;
            } else {
                throw new IllegalArgumentException("SignatureAlgorithm.anonymous MUST NOT appear in the signature_algorithms extension");
            }
        }
    }

    public static byte[] encodeUint16ArrayWithUint16Length(int[] iArr) {
        byte[] bArr = new byte[((iArr.length * 2) + 2)];
        writeUint16ArrayWithUint16Length(iArr, bArr, 0);
        return bArr;
    }

    public static byte[] encodeUint8ArrayWithUint8Length(short[] sArr) {
        byte[] bArr = new byte[(sArr.length + 1)];
        writeUint8ArrayWithUint8Length(sArr, bArr, 0);
        return bArr;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0017, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0013, code lost:
        return 2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getCipherType(int r1) {
        /*
            int r1 = getEncryptionAlgorithm(r1)
            switch(r1) {
                case 1: goto L_0x0016;
                case 2: goto L_0x0016;
                case 3: goto L_0x0014;
                case 4: goto L_0x0014;
                case 5: goto L_0x0014;
                case 6: goto L_0x0014;
                case 7: goto L_0x0014;
                case 8: goto L_0x0014;
                case 9: goto L_0x0014;
                case 10: goto L_0x0012;
                case 11: goto L_0x0012;
                case 12: goto L_0x0014;
                case 13: goto L_0x0014;
                case 14: goto L_0x0014;
                case 15: goto L_0x0012;
                case 16: goto L_0x0012;
                case 17: goto L_0x0012;
                case 18: goto L_0x0012;
                case 19: goto L_0x0012;
                case 20: goto L_0x0012;
                default: goto L_0x0007;
            }
        L_0x0007:
            switch(r1) {
                case 100: goto L_0x0016;
                case 101: goto L_0x0016;
                case 102: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            org.bouncycastle.crypto.tls.TlsFatalAlert r1 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r0 = 80
            r1.<init>(r0)
            throw r1
        L_0x0012:
            r1 = 2
            return r1
        L_0x0014:
            r1 = 1
            return r1
        L_0x0016:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.TlsUtils.getCipherType(int):int");
    }

    public static Vector getDefaultDSSSignatureAlgorithms() {
        return a(new SignatureAndHashAlgorithm(2, 2));
    }

    public static Vector getDefaultECDSASignatureAlgorithms() {
        return a(new SignatureAndHashAlgorithm(2, 3));
    }

    public static Vector getDefaultRSASignatureAlgorithms() {
        return a(new SignatureAndHashAlgorithm(2, 1));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x005f, code lost:
        return 20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0062, code lost:
        return 19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0063, code lost:
        return 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0064, code lost:
        return 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0065, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0066, code lost:
        return 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0069, code lost:
        return 11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x006c, code lost:
        return 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0071, code lost:
        return 7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0074, code lost:
        return 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0075, code lost:
        return 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0076, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0077, code lost:
        return 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0078, code lost:
        return 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0079, code lost:
        return 2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getEncryptionAlgorithm(int r6) {
        /*
            r0 = 0
            switch(r6) {
                case 1: goto L_0x007a;
                case 2: goto L_0x007a;
                default: goto L_0x0004;
            }
        L_0x0004:
            r1 = 2
            switch(r6) {
                case 4: goto L_0x0079;
                case 5: goto L_0x0079;
                default: goto L_0x0008;
            }
        L_0x0008:
            r2 = 8
            switch(r6) {
                case 44: goto L_0x007a;
                case 45: goto L_0x007a;
                case 46: goto L_0x007a;
                case 47: goto L_0x0078;
                case 48: goto L_0x0078;
                case 49: goto L_0x0078;
                case 50: goto L_0x0078;
                case 51: goto L_0x0078;
                default: goto L_0x000d;
            }
        L_0x000d:
            r3 = 9
            switch(r6) {
                case 53: goto L_0x0077;
                case 54: goto L_0x0077;
                case 55: goto L_0x0077;
                case 56: goto L_0x0077;
                case 57: goto L_0x0077;
                default: goto L_0x0012;
            }
        L_0x0012:
            r4 = 12
            switch(r6) {
                case 59: goto L_0x0076;
                case 60: goto L_0x0075;
                case 61: goto L_0x0074;
                case 62: goto L_0x0075;
                case 63: goto L_0x0075;
                case 64: goto L_0x0075;
                case 65: goto L_0x0073;
                case 66: goto L_0x0073;
                case 67: goto L_0x0073;
                case 68: goto L_0x0073;
                case 69: goto L_0x0073;
                default: goto L_0x0017;
            }
        L_0x0017:
            switch(r6) {
                case 103: goto L_0x0075;
                case 104: goto L_0x0074;
                case 105: goto L_0x0074;
                case 106: goto L_0x0074;
                case 107: goto L_0x0074;
                default: goto L_0x001a;
            }
        L_0x001a:
            r5 = 13
            switch(r6) {
                case 132: goto L_0x0072;
                case 133: goto L_0x0072;
                case 134: goto L_0x0072;
                case 135: goto L_0x0072;
                case 136: goto L_0x0072;
                default: goto L_0x001f;
            }
        L_0x001f:
            switch(r6) {
                case 138: goto L_0x0079;
                case 139: goto L_0x0070;
                case 140: goto L_0x0078;
                case 141: goto L_0x0077;
                case 142: goto L_0x0079;
                case 143: goto L_0x0070;
                case 144: goto L_0x0078;
                case 145: goto L_0x0077;
                case 146: goto L_0x0079;
                case 147: goto L_0x0070;
                case 148: goto L_0x0078;
                case 149: goto L_0x0077;
                case 150: goto L_0x006d;
                case 151: goto L_0x006d;
                case 152: goto L_0x006d;
                case 153: goto L_0x006d;
                case 154: goto L_0x006d;
                default: goto L_0x0022;
            }
        L_0x0022:
            switch(r6) {
                case 156: goto L_0x006a;
                case 157: goto L_0x0067;
                case 158: goto L_0x006a;
                case 159: goto L_0x0067;
                case 160: goto L_0x006a;
                case 161: goto L_0x0067;
                case 162: goto L_0x006a;
                case 163: goto L_0x0067;
                case 164: goto L_0x006a;
                case 165: goto L_0x0067;
                default: goto L_0x0025;
            }
        L_0x0025:
            switch(r6) {
                case 168: goto L_0x006a;
                case 169: goto L_0x0067;
                case 170: goto L_0x006a;
                case 171: goto L_0x0067;
                case 172: goto L_0x006a;
                case 173: goto L_0x0067;
                case 174: goto L_0x0075;
                case 175: goto L_0x0066;
                case 176: goto L_0x0076;
                case 177: goto L_0x0065;
                case 178: goto L_0x0075;
                case 179: goto L_0x0066;
                case 180: goto L_0x0076;
                case 181: goto L_0x0065;
                case 182: goto L_0x0075;
                case 183: goto L_0x0066;
                case 184: goto L_0x0076;
                case 185: goto L_0x0065;
                case 186: goto L_0x0064;
                case 187: goto L_0x0064;
                case 188: goto L_0x0064;
                case 189: goto L_0x0064;
                case 190: goto L_0x0064;
                default: goto L_0x0028;
            }
        L_0x0028:
            switch(r6) {
                case 192: goto L_0x0063;
                case 193: goto L_0x0063;
                case 194: goto L_0x0063;
                case 195: goto L_0x0063;
                case 196: goto L_0x0063;
                default: goto L_0x002b;
            }
        L_0x002b:
            switch(r6) {
                case 49153: goto L_0x007a;
                case 49154: goto L_0x0079;
                case 49155: goto L_0x0070;
                case 49156: goto L_0x0078;
                case 49157: goto L_0x0077;
                case 49158: goto L_0x007a;
                case 49159: goto L_0x0079;
                case 49160: goto L_0x0070;
                case 49161: goto L_0x0078;
                case 49162: goto L_0x0077;
                case 49163: goto L_0x007a;
                case 49164: goto L_0x0079;
                case 49165: goto L_0x0070;
                case 49166: goto L_0x0078;
                case 49167: goto L_0x0077;
                case 49168: goto L_0x007a;
                case 49169: goto L_0x0079;
                case 49170: goto L_0x0070;
                case 49171: goto L_0x0078;
                case 49172: goto L_0x0077;
                default: goto L_0x002e;
            }
        L_0x002e:
            switch(r6) {
                case 49178: goto L_0x0070;
                case 49179: goto L_0x0070;
                case 49180: goto L_0x0070;
                case 49181: goto L_0x0078;
                case 49182: goto L_0x0078;
                case 49183: goto L_0x0078;
                case 49184: goto L_0x0077;
                case 49185: goto L_0x0077;
                case 49186: goto L_0x0077;
                case 49187: goto L_0x0075;
                case 49188: goto L_0x0066;
                case 49189: goto L_0x0075;
                case 49190: goto L_0x0066;
                case 49191: goto L_0x0075;
                case 49192: goto L_0x0066;
                case 49193: goto L_0x0075;
                case 49194: goto L_0x0066;
                case 49195: goto L_0x006a;
                case 49196: goto L_0x0067;
                case 49197: goto L_0x006a;
                case 49198: goto L_0x0067;
                case 49199: goto L_0x006a;
                case 49200: goto L_0x0067;
                case 49201: goto L_0x006a;
                case 49202: goto L_0x0067;
                case 49203: goto L_0x0079;
                case 49204: goto L_0x0070;
                case 49205: goto L_0x0078;
                case 49206: goto L_0x0077;
                case 49207: goto L_0x0075;
                case 49208: goto L_0x0066;
                case 49209: goto L_0x007a;
                case 49210: goto L_0x0076;
                case 49211: goto L_0x0065;
                default: goto L_0x0031;
            }
        L_0x0031:
            switch(r6) {
                case 49266: goto L_0x0064;
                case 49267: goto L_0x0063;
                case 49268: goto L_0x0064;
                case 49269: goto L_0x0063;
                case 49270: goto L_0x0064;
                case 49271: goto L_0x0063;
                case 49272: goto L_0x0064;
                case 49273: goto L_0x0063;
                case 49274: goto L_0x0060;
                case 49275: goto L_0x005d;
                case 49276: goto L_0x0060;
                case 49277: goto L_0x005d;
                case 49278: goto L_0x0060;
                case 49279: goto L_0x005d;
                case 49280: goto L_0x0060;
                case 49281: goto L_0x005d;
                case 49282: goto L_0x0060;
                case 49283: goto L_0x005d;
                default: goto L_0x0034;
            }
        L_0x0034:
            switch(r6) {
                case 49286: goto L_0x0060;
                case 49287: goto L_0x005d;
                case 49288: goto L_0x0060;
                case 49289: goto L_0x005d;
                case 49290: goto L_0x0060;
                case 49291: goto L_0x005d;
                case 49292: goto L_0x0060;
                case 49293: goto L_0x005d;
                case 49294: goto L_0x0060;
                case 49295: goto L_0x005d;
                case 49296: goto L_0x0060;
                case 49297: goto L_0x005d;
                case 49298: goto L_0x0060;
                case 49299: goto L_0x005d;
                case 49300: goto L_0x0064;
                case 49301: goto L_0x0063;
                case 49302: goto L_0x0064;
                case 49303: goto L_0x0063;
                case 49304: goto L_0x0064;
                case 49305: goto L_0x0063;
                case 49306: goto L_0x0064;
                case 49307: goto L_0x0063;
                case 49308: goto L_0x005a;
                case 49309: goto L_0x0057;
                case 49310: goto L_0x005a;
                case 49311: goto L_0x0057;
                case 49312: goto L_0x0054;
                case 49313: goto L_0x0051;
                case 49314: goto L_0x0054;
                case 49315: goto L_0x0051;
                case 49316: goto L_0x005a;
                case 49317: goto L_0x0057;
                case 49318: goto L_0x005a;
                case 49319: goto L_0x0057;
                case 49320: goto L_0x0054;
                case 49321: goto L_0x0051;
                case 49322: goto L_0x0054;
                case 49323: goto L_0x0051;
                default: goto L_0x0037;
            }
        L_0x0037:
            switch(r6) {
                case 52243: goto L_0x004e;
                case 52244: goto L_0x004e;
                case 52245: goto L_0x004e;
                default: goto L_0x003a;
            }
        L_0x003a:
            switch(r6) {
                case 58384: goto L_0x004b;
                case 58385: goto L_0x0048;
                case 58386: goto L_0x004b;
                case 58387: goto L_0x0048;
                case 58388: goto L_0x004b;
                case 58389: goto L_0x0048;
                case 58390: goto L_0x004b;
                case 58391: goto L_0x0048;
                case 58392: goto L_0x004b;
                case 58393: goto L_0x0048;
                case 58394: goto L_0x004b;
                case 58395: goto L_0x0048;
                case 58396: goto L_0x004b;
                case 58397: goto L_0x0048;
                case 58398: goto L_0x004b;
                case 58399: goto L_0x0048;
                default: goto L_0x003d;
            }
        L_0x003d:
            switch(r6) {
                case 10: goto L_0x0070;
                case 13: goto L_0x0070;
                case 16: goto L_0x0070;
                case 19: goto L_0x0070;
                case 22: goto L_0x0070;
                case 24: goto L_0x0079;
                case 49174: goto L_0x0079;
                default: goto L_0x0040;
            }
        L_0x0040:
            org.bouncycastle.crypto.tls.TlsFatalAlert r6 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r0 = 80
            r6.<init>(r0)
            throw r6
        L_0x0048:
            r6 = 101(0x65, float:1.42E-43)
            return r6
        L_0x004b:
            r6 = 100
            return r6
        L_0x004e:
            r6 = 102(0x66, float:1.43E-43)
            return r6
        L_0x0051:
            r6 = 18
            return r6
        L_0x0054:
            r6 = 16
            return r6
        L_0x0057:
            r6 = 17
            return r6
        L_0x005a:
            r6 = 15
            return r6
        L_0x005d:
            r6 = 20
            return r6
        L_0x0060:
            r6 = 19
            return r6
        L_0x0063:
            return r5
        L_0x0064:
            return r4
        L_0x0065:
            return r0
        L_0x0066:
            return r3
        L_0x0067:
            r6 = 11
            return r6
        L_0x006a:
            r6 = 10
            return r6
        L_0x006d:
            r6 = 14
            return r6
        L_0x0070:
            r6 = 7
            return r6
        L_0x0072:
            return r5
        L_0x0073:
            return r4
        L_0x0074:
            return r3
        L_0x0075:
            return r2
        L_0x0076:
            return r0
        L_0x0077:
            return r3
        L_0x0078:
            return r2
        L_0x0079:
            return r1
        L_0x007a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.TlsUtils.getEncryptionAlgorithm(int):int");
    }

    public static byte[] getExtensionData(Hashtable hashtable, Integer num) {
        if (hashtable == null) {
            return null;
        }
        return (byte[]) hashtable.get(num);
    }

    public static short getHashAlgorithmForPRFAlgorithm(int i) {
        switch (i) {
            case 0:
                throw new IllegalArgumentException("legacy PRF not a valid algorithm");
            case 1:
                return 4;
            case 2:
                return 5;
            default:
                throw new IllegalArgumentException("unknown PRFAlgorithm");
        }
    }

    public static ProtocolVersion getMinimumVersion(int i) {
        switch (i) {
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
                break;
            default:
                switch (i) {
                    case 103:
                    case 104:
                    case 105:
                    case 106:
                    case 107:
                        break;
                    default:
                        switch (i) {
                            case CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256 /*156*/:
                            case CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384 /*157*/:
                            case CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256 /*158*/:
                            case CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384 /*159*/:
                            case CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256 /*160*/:
                            case CipherSuite.TLS_DH_RSA_WITH_AES_256_GCM_SHA384 /*161*/:
                            case CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256 /*162*/:
                            case CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384 /*163*/:
                            case CipherSuite.TLS_DH_DSS_WITH_AES_128_GCM_SHA256 /*164*/:
                            case CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384 /*165*/:
                                break;
                            default:
                                switch (i) {
                                    case 168:
                                    case CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384 /*169*/:
                                    case CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256 /*170*/:
                                    case CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384 /*171*/:
                                    case CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256 /*172*/:
                                    case CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384 /*173*/:
                                        break;
                                    default:
                                        switch (i) {
                                            case CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256 /*186*/:
                                            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256 /*187*/:
                                            case 188:
                                            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256 /*189*/:
                                            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256 /*190*/:
                                            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256 /*191*/:
                                            case 192:
                                            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256 /*193*/:
                                            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256 /*194*/:
                                            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256 /*195*/:
                                            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256 /*196*/:
                                            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256 /*197*/:
                                                break;
                                            default:
                                                switch (i) {
                                                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 /*49187*/:
                                                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384 /*49188*/:
                                                    case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256 /*49189*/:
                                                    case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384 /*49190*/:
                                                    case CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256 /*49191*/:
                                                    case CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384 /*49192*/:
                                                    case CipherSuite.TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256 /*49193*/:
                                                    case CipherSuite.TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384 /*49194*/:
                                                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 /*49195*/:
                                                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 /*49196*/:
                                                    case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256 /*49197*/:
                                                    case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384 /*49198*/:
                                                    case CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 /*49199*/:
                                                    case CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 /*49200*/:
                                                    case CipherSuite.TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256 /*49201*/:
                                                    case CipherSuite.TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384 /*49202*/:
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256 /*49266*/:
                                                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384 /*49267*/:
                                                            case CipherSuite.TLS_ECDH_ECDSA_WITH_CAMELLIA_128_CBC_SHA256 /*49268*/:
                                                            case CipherSuite.TLS_ECDH_ECDSA_WITH_CAMELLIA_256_CBC_SHA384 /*49269*/:
                                                            case CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256 /*49270*/:
                                                            case CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384 /*49271*/:
                                                            case CipherSuite.TLS_ECDH_RSA_WITH_CAMELLIA_128_CBC_SHA256 /*49272*/:
                                                            case CipherSuite.TLS_ECDH_RSA_WITH_CAMELLIA_256_CBC_SHA384 /*49273*/:
                                                            case CipherSuite.TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256 /*49274*/:
                                                            case CipherSuite.TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384 /*49275*/:
                                                            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256 /*49276*/:
                                                            case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384 /*49277*/:
                                                            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_128_GCM_SHA256 /*49278*/:
                                                            case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_GCM_SHA384 /*49279*/:
                                                            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256 /*49280*/:
                                                            case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384 /*49281*/:
                                                            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_GCM_SHA256 /*49282*/:
                                                            case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_GCM_SHA384 /*49283*/:
                                                            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_GCM_SHA256 /*49284*/:
                                                            case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_GCM_SHA384 /*49285*/:
                                                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256 /*49286*/:
                                                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384 /*49287*/:
                                                            case CipherSuite.TLS_ECDH_ECDSA_WITH_CAMELLIA_128_GCM_SHA256 /*49288*/:
                                                            case CipherSuite.TLS_ECDH_ECDSA_WITH_CAMELLIA_256_GCM_SHA384 /*49289*/:
                                                            case CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256 /*49290*/:
                                                            case CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384 /*49291*/:
                                                            case CipherSuite.TLS_ECDH_RSA_WITH_CAMELLIA_128_GCM_SHA256 /*49292*/:
                                                            case CipherSuite.TLS_ECDH_RSA_WITH_CAMELLIA_256_GCM_SHA384 /*49293*/:
                                                            case CipherSuite.TLS_PSK_WITH_CAMELLIA_128_GCM_SHA256 /*49294*/:
                                                            case CipherSuite.TLS_PSK_WITH_CAMELLIA_256_GCM_SHA384 /*49295*/:
                                                            case CipherSuite.TLS_DHE_PSK_WITH_CAMELLIA_128_GCM_SHA256 /*49296*/:
                                                            case CipherSuite.TLS_DHE_PSK_WITH_CAMELLIA_256_GCM_SHA384 /*49297*/:
                                                            case CipherSuite.TLS_RSA_PSK_WITH_CAMELLIA_128_GCM_SHA256 /*49298*/:
                                                            case CipherSuite.TLS_RSA_PSK_WITH_CAMELLIA_256_GCM_SHA384 /*49299*/:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case CipherSuite.TLS_RSA_WITH_AES_128_CCM /*49308*/:
                                                                    case CipherSuite.TLS_RSA_WITH_AES_256_CCM /*49309*/:
                                                                    case CipherSuite.TLS_DHE_RSA_WITH_AES_128_CCM /*49310*/:
                                                                    case CipherSuite.TLS_DHE_RSA_WITH_AES_256_CCM /*49311*/:
                                                                    case CipherSuite.TLS_RSA_WITH_AES_128_CCM_8 /*49312*/:
                                                                    case CipherSuite.TLS_RSA_WITH_AES_256_CCM_8 /*49313*/:
                                                                    case CipherSuite.TLS_DHE_RSA_WITH_AES_128_CCM_8 /*49314*/:
                                                                    case CipherSuite.TLS_DHE_RSA_WITH_AES_256_CCM_8 /*49315*/:
                                                                    case CipherSuite.TLS_PSK_WITH_AES_128_CCM /*49316*/:
                                                                    case CipherSuite.TLS_PSK_WITH_AES_256_CCM /*49317*/:
                                                                    case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CCM /*49318*/:
                                                                    case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CCM /*49319*/:
                                                                    case CipherSuite.TLS_PSK_WITH_AES_128_CCM_8 /*49320*/:
                                                                    case CipherSuite.TLS_PSK_WITH_AES_256_CCM_8 /*49321*/:
                                                                    case CipherSuite.TLS_PSK_DHE_WITH_AES_128_CCM_8 /*49322*/:
                                                                    case CipherSuite.TLS_PSK_DHE_WITH_AES_256_CCM_8 /*49323*/:
                                                                        break;
                                                                    default:
                                                                        switch (i) {
                                                                            case CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 /*52243*/:
                                                                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 /*52244*/:
                                                                            case CipherSuite.TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256 /*52245*/:
                                                                                break;
                                                                            default:
                                                                                return ProtocolVersion.SSLv3;
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
        return ProtocolVersion.TLSv12;
    }

    public static ASN1ObjectIdentifier getOIDForHashAlgorithm(short s) {
        switch (s) {
            case 1:
                return PKCSObjectIdentifiers.md5;
            case 2:
                return X509ObjectIdentifiers.id_SHA1;
            case 3:
                return NISTObjectIdentifiers.id_sha224;
            case 4:
                return NISTObjectIdentifiers.id_sha256;
            case 5:
                return NISTObjectIdentifiers.id_sha384;
            case 6:
                return NISTObjectIdentifiers.id_sha512;
            default:
                throw new IllegalArgumentException("unknown HashAlgorithm");
        }
    }

    public static Vector getSignatureAlgorithmsExtension(Hashtable hashtable) {
        byte[] extensionData = getExtensionData(hashtable, EXT_signature_algorithms);
        if (extensionData == null) {
            return null;
        }
        return readSignatureAlgorithmsExtension(extensionData);
    }

    public static boolean hasExpectedEmptyExtensionData(Hashtable hashtable, Integer num, short s) {
        byte[] extensionData = getExtensionData(hashtable, num);
        if (extensionData == null) {
            return false;
        }
        if (extensionData.length == 0) {
            return true;
        }
        throw new TlsFatalAlert(s);
    }

    public static boolean hasSigningCapability(short s) {
        if (s != 64) {
            switch (s) {
                case 1:
                case 2:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public static TlsSession importSession(byte[] bArr, SessionParameters sessionParameters) {
        return new TlsSessionImpl(bArr, sessionParameters);
    }

    public static boolean isAEADCipherSuite(int i) {
        return 2 == getCipherType(i);
    }

    public static boolean isBlockCipherSuite(int i) {
        return 1 == getCipherType(i);
    }

    public static boolean isSSL(TlsContext tlsContext) {
        return tlsContext.getServerVersion().isSSL();
    }

    public static boolean isSignatureAlgorithmsExtensionAllowed(ProtocolVersion protocolVersion) {
        return ProtocolVersion.TLSv12.isEqualOrEarlierVersionOf(protocolVersion.getEquivalentTLSVersion());
    }

    public static boolean isStreamCipherSuite(int i) {
        return getCipherType(i) == 0;
    }

    public static boolean isTLSv11(TlsContext tlsContext) {
        return ProtocolVersion.TLSv11.isEqualOrEarlierVersionOf(tlsContext.getServerVersion().getEquivalentTLSVersion());
    }

    public static boolean isTLSv12(TlsContext tlsContext) {
        return ProtocolVersion.TLSv12.isEqualOrEarlierVersionOf(tlsContext.getServerVersion().getEquivalentTLSVersion());
    }

    public static boolean isValidCipherSuiteForVersion(int i, ProtocolVersion protocolVersion) {
        return getMinimumVersion(i).isEqualOrEarlierVersionOf(protocolVersion.getEquivalentTLSVersion());
    }

    public static boolean isValidUint16(int i) {
        return (65535 & i) == i;
    }

    public static boolean isValidUint16(long j) {
        return (j & 65535) == j;
    }

    public static boolean isValidUint24(int i) {
        return (16777215 & i) == i;
    }

    public static boolean isValidUint24(long j) {
        return (j & 16777215) == j;
    }

    public static boolean isValidUint32(long j) {
        return (j & 4294967295L) == j;
    }

    public static boolean isValidUint48(long j) {
        return (j & 281474976710655L) == j;
    }

    public static boolean isValidUint64(long j) {
        return true;
    }

    public static boolean isValidUint8(int i) {
        return (i & 255) == i;
    }

    public static boolean isValidUint8(long j) {
        return (j & 255) == j;
    }

    public static boolean isValidUint8(short s) {
        return (s & 255) == s;
    }

    public static Vector parseSupportedSignatureAlgorithms(boolean z, InputStream inputStream) {
        int readUint16 = readUint16(inputStream);
        if (readUint16 < 2 || (readUint16 & 1) != 0) {
            throw new TlsFatalAlert(50);
        }
        int i = readUint16 / 2;
        Vector vector = new Vector(i);
        int i2 = 0;
        while (i2 < i) {
            SignatureAndHashAlgorithm parse = SignatureAndHashAlgorithm.parse(inputStream);
            if (z || parse.getSignature() != 0) {
                vector.addElement(parse);
                i2++;
            } else {
                throw new TlsFatalAlert(47);
            }
        }
        return vector;
    }

    public static ASN1Primitive readASN1Object(byte[] bArr) {
        ASN1InputStream aSN1InputStream = new ASN1InputStream(bArr);
        ASN1Primitive readObject = aSN1InputStream.readObject();
        if (readObject == null) {
            throw new TlsFatalAlert(50);
        } else if (aSN1InputStream.readObject() == null) {
            return readObject;
        } else {
            throw new TlsFatalAlert(50);
        }
    }

    public static byte[] readAllOrNothing(int i, InputStream inputStream) {
        if (i < 1) {
            return EMPTY_BYTES;
        }
        byte[] bArr = new byte[i];
        int readFully = Streams.readFully(inputStream, bArr);
        if (readFully == 0) {
            return null;
        }
        if (readFully == i) {
            return bArr;
        }
        throw new EOFException();
    }

    public static ASN1Primitive readDERObject(byte[] bArr) {
        ASN1Primitive readASN1Object = readASN1Object(bArr);
        if (Arrays.areEqual(readASN1Object.getEncoded(ASN1Encoding.DER), bArr)) {
            return readASN1Object;
        }
        throw new TlsFatalAlert(50);
    }

    public static void readFully(byte[] bArr, InputStream inputStream) {
        int length = bArr.length;
        if (length > 0 && length != Streams.readFully(inputStream, bArr)) {
            throw new EOFException();
        }
    }

    public static byte[] readFully(int i, InputStream inputStream) {
        if (i < 1) {
            return EMPTY_BYTES;
        }
        byte[] bArr = new byte[i];
        if (i == Streams.readFully(inputStream, bArr)) {
            return bArr;
        }
        throw new EOFException();
    }

    public static byte[] readOpaque16(InputStream inputStream) {
        return readFully(readUint16(inputStream), inputStream);
    }

    public static byte[] readOpaque24(InputStream inputStream) {
        return readFully(readUint24(inputStream), inputStream);
    }

    public static byte[] readOpaque8(InputStream inputStream) {
        return readFully((int) readUint8(inputStream), inputStream);
    }

    public static Vector readSignatureAlgorithmsExtension(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        Vector parseSupportedSignatureAlgorithms = parseSupportedSignatureAlgorithms(false, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        return parseSupportedSignatureAlgorithms;
    }

    public static int readUint16(InputStream inputStream) {
        int read = inputStream.read();
        int read2 = inputStream.read();
        if (read2 >= 0) {
            return read2 | (read << 8);
        }
        throw new EOFException();
    }

    public static int readUint16(byte[] bArr, int i) {
        return (bArr[i + 1] & UnsignedBytes.MAX_VALUE) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << 8);
    }

    public static int[] readUint16Array(int i, InputStream inputStream) {
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = readUint16(inputStream);
        }
        return iArr;
    }

    public static int readUint24(InputStream inputStream) {
        int read = inputStream.read();
        int read2 = inputStream.read();
        int read3 = inputStream.read();
        if (read3 >= 0) {
            return read3 | (read << 16) | (read2 << 8);
        }
        throw new EOFException();
    }

    public static int readUint24(byte[] bArr, int i) {
        int i2 = i + 1;
        return (bArr[i2 + 1] & UnsignedBytes.MAX_VALUE) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << 8);
    }

    public static long readUint32(InputStream inputStream) {
        int read = inputStream.read();
        int read2 = inputStream.read();
        int read3 = inputStream.read();
        int read4 = inputStream.read();
        if (read4 >= 0) {
            return ((long) (read4 | (read << 2) | (read2 << 16) | (read3 << 8))) & 4294967295L;
        }
        throw new EOFException();
    }

    public static long readUint32(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return ((long) ((bArr[i3 + 1] & UnsignedBytes.MAX_VALUE) | ((bArr[i] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((bArr[i2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((bArr[i3] & UnsignedBytes.MAX_VALUE) << 8))) & 4294967295L;
    }

    public static long readUint48(InputStream inputStream) {
        return ((((long) readUint24(inputStream)) & 4294967295L) << 24) | (((long) readUint24(inputStream)) & 4294967295L);
    }

    public static long readUint48(byte[] bArr, int i) {
        return ((((long) readUint24(bArr, i)) & 4294967295L) << 24) | (((long) readUint24(bArr, i + 3)) & 4294967295L);
    }

    public static short readUint8(InputStream inputStream) {
        int read = inputStream.read();
        if (read >= 0) {
            return (short) read;
        }
        throw new EOFException();
    }

    public static short readUint8(byte[] bArr, int i) {
        return (short) bArr[i];
    }

    public static short[] readUint8Array(int i, InputStream inputStream) {
        short[] sArr = new short[i];
        for (int i2 = 0; i2 < i; i2++) {
            sArr[i2] = readUint8(inputStream);
        }
        return sArr;
    }

    public static ProtocolVersion readVersion(InputStream inputStream) {
        int read = inputStream.read();
        int read2 = inputStream.read();
        if (read2 >= 0) {
            return ProtocolVersion.get(read, read2);
        }
        throw new EOFException();
    }

    public static ProtocolVersion readVersion(byte[] bArr, int i) {
        return ProtocolVersion.get(bArr[i] & UnsignedBytes.MAX_VALUE, bArr[i + 1] & UnsignedBytes.MAX_VALUE);
    }

    public static int readVersionRaw(InputStream inputStream) {
        int read = inputStream.read();
        int read2 = inputStream.read();
        if (read2 >= 0) {
            return read2 | (read << 8);
        }
        throw new EOFException();
    }

    public static int readVersionRaw(byte[] bArr, int i) {
        return bArr[i + 1] | (bArr[i] << 8);
    }

    public static void writeGMTUnixTime(byte[] bArr, int i) {
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        bArr[i] = (byte) (currentTimeMillis >>> 24);
        bArr[i + 1] = (byte) (currentTimeMillis >>> 16);
        bArr[i + 2] = (byte) (currentTimeMillis >>> 8);
        bArr[i + 3] = (byte) currentTimeMillis;
    }

    public static void writeOpaque16(byte[] bArr, OutputStream outputStream) {
        checkUint16(bArr.length);
        writeUint16(bArr.length, outputStream);
        outputStream.write(bArr);
    }

    public static void writeOpaque24(byte[] bArr, OutputStream outputStream) {
        checkUint24(bArr.length);
        writeUint24(bArr.length, outputStream);
        outputStream.write(bArr);
    }

    public static void writeOpaque8(byte[] bArr, OutputStream outputStream) {
        checkUint8(bArr.length);
        writeUint8(bArr.length, outputStream);
        outputStream.write(bArr);
    }

    public static void writeUint16(int i, OutputStream outputStream) {
        outputStream.write(i >>> 8);
        outputStream.write(i);
    }

    public static void writeUint16(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 8);
        bArr[i2 + 1] = (byte) i;
    }

    public static void writeUint16Array(int[] iArr, OutputStream outputStream) {
        for (int writeUint16 : iArr) {
            writeUint16(writeUint16, outputStream);
        }
    }

    public static void writeUint16Array(int[] iArr, byte[] bArr, int i) {
        for (int writeUint16 : iArr) {
            writeUint16(writeUint16, bArr, i);
            i += 2;
        }
    }

    public static void writeUint16ArrayWithUint16Length(int[] iArr, OutputStream outputStream) {
        int length = iArr.length * 2;
        checkUint16(length);
        writeUint16(length, outputStream);
        writeUint16Array(iArr, outputStream);
    }

    public static void writeUint16ArrayWithUint16Length(int[] iArr, byte[] bArr, int i) {
        int length = iArr.length * 2;
        checkUint16(length);
        writeUint16(length, bArr, i);
        writeUint16Array(iArr, bArr, i + 2);
    }

    public static void writeUint24(int i, OutputStream outputStream) {
        outputStream.write((byte) (i >>> 16));
        outputStream.write((byte) (i >>> 8));
        outputStream.write((byte) i);
    }

    public static void writeUint24(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 16);
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2 + 2] = (byte) i;
    }

    public static void writeUint32(long j, OutputStream outputStream) {
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) j));
    }

    public static void writeUint32(long j, byte[] bArr, int i) {
        bArr[i] = (byte) ((int) (j >>> 24));
        bArr[i + 1] = (byte) ((int) (j >>> 16));
        bArr[i + 2] = (byte) ((int) (j >>> 8));
        bArr[i + 3] = (byte) ((int) j);
    }

    public static void writeUint48(long j, OutputStream outputStream) {
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) j));
    }

    public static void writeUint48(long j, byte[] bArr, int i) {
        bArr[i] = (byte) ((int) (j >>> 40));
        bArr[i + 1] = (byte) ((int) (j >>> 32));
        bArr[i + 2] = (byte) ((int) (j >>> 24));
        bArr[i + 3] = (byte) ((int) (j >>> 16));
        bArr[i + 4] = (byte) ((int) (j >>> 8));
        bArr[i + 5] = (byte) ((int) j);
    }

    public static void writeUint64(long j, OutputStream outputStream) {
        outputStream.write((byte) ((int) (j >>> 56)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) j));
    }

    public static void writeUint64(long j, byte[] bArr, int i) {
        bArr[i] = (byte) ((int) (j >>> 56));
        bArr[i + 1] = (byte) ((int) (j >>> 48));
        bArr[i + 2] = (byte) ((int) (j >>> 40));
        bArr[i + 3] = (byte) ((int) (j >>> 32));
        bArr[i + 4] = (byte) ((int) (j >>> 24));
        bArr[i + 5] = (byte) ((int) (j >>> 16));
        bArr[i + 6] = (byte) ((int) (j >>> 8));
        bArr[i + 7] = (byte) ((int) j);
    }

    public static void writeUint8(int i, OutputStream outputStream) {
        outputStream.write(i);
    }

    public static void writeUint8(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
    }

    public static void writeUint8(short s, OutputStream outputStream) {
        outputStream.write(s);
    }

    public static void writeUint8(short s, byte[] bArr, int i) {
        bArr[i] = (byte) s;
    }

    public static void writeUint8Array(short[] sArr, OutputStream outputStream) {
        for (short writeUint8 : sArr) {
            writeUint8(writeUint8, outputStream);
        }
    }

    public static void writeUint8Array(short[] sArr, byte[] bArr, int i) {
        for (short writeUint8 : sArr) {
            writeUint8(writeUint8, bArr, i);
            i++;
        }
    }

    public static void writeUint8ArrayWithUint8Length(short[] sArr, OutputStream outputStream) {
        checkUint8(sArr.length);
        writeUint8(sArr.length, outputStream);
        writeUint8Array(sArr, outputStream);
    }

    public static void writeUint8ArrayWithUint8Length(short[] sArr, byte[] bArr, int i) {
        checkUint8(sArr.length);
        writeUint8(sArr.length, bArr, i);
        writeUint8Array(sArr, bArr, i + 1);
    }

    public static void writeVersion(ProtocolVersion protocolVersion, OutputStream outputStream) {
        outputStream.write(protocolVersion.getMajorVersion());
        outputStream.write(protocolVersion.getMinorVersion());
    }

    public static void writeVersion(ProtocolVersion protocolVersion, byte[] bArr, int i) {
        bArr[i] = (byte) protocolVersion.getMajorVersion();
        bArr[i + 1] = (byte) protocolVersion.getMinorVersion();
    }
}
