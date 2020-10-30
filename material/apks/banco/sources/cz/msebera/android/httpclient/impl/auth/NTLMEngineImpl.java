package cz.msebera.android.httpclient.impl.auth;

import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.extras.Base64;
import cz.msebera.android.httpclient.util.CharsetUtils;
import cz.msebera.android.httpclient.util.EncodingUtils;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;

@NotThreadSafe
final class NTLMEngineImpl implements NTLMEngine {
    /* access modifiers changed from: private */
    public static final Charset a = CharsetUtils.lookup("UnicodeLittleUnmarked");
    /* access modifiers changed from: private */
    public static final Charset b = Consts.ASCII;
    private static final SecureRandom c;
    /* access modifiers changed from: private */
    public static final byte[] d;
    private static final Type1Message e = new Type1Message();

    public static class CipherGen {
        protected final byte[] challenge;
        protected byte[] clientChallenge;
        protected byte[] clientChallenge2;
        protected final String domain;
        protected byte[] lanManagerSessionKey;
        protected byte[] lm2SessionResponse;
        protected byte[] lmHash;
        protected byte[] lmResponse;
        protected byte[] lmUserSessionKey;
        protected byte[] lmv2Hash;
        protected byte[] lmv2Response;
        protected byte[] ntlm2SessionResponse;
        protected byte[] ntlm2SessionResponseUserSessionKey;
        protected byte[] ntlmHash;
        protected byte[] ntlmResponse;
        protected byte[] ntlmUserSessionKey;
        protected byte[] ntlmv2Blob;
        protected byte[] ntlmv2Hash;
        protected byte[] ntlmv2Response;
        protected byte[] ntlmv2UserSessionKey;
        protected final String password;
        protected byte[] secondaryKey;
        protected final String target;
        protected final byte[] targetInformation;
        protected byte[] timestamp;
        protected final String user;

        public CipherGen(String str, String str2, String str3, byte[] bArr, String str4, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte[] bArr6) {
            this.lmHash = null;
            this.lmResponse = null;
            this.ntlmHash = null;
            this.ntlmResponse = null;
            this.ntlmv2Hash = null;
            this.lmv2Hash = null;
            this.lmv2Response = null;
            this.ntlmv2Blob = null;
            this.ntlmv2Response = null;
            this.ntlm2SessionResponse = null;
            this.lm2SessionResponse = null;
            this.lmUserSessionKey = null;
            this.ntlmUserSessionKey = null;
            this.ntlmv2UserSessionKey = null;
            this.ntlm2SessionResponseUserSessionKey = null;
            this.lanManagerSessionKey = null;
            this.domain = str;
            this.target = str4;
            this.user = str2;
            this.password = str3;
            this.challenge = bArr;
            this.targetInformation = bArr2;
            this.clientChallenge = bArr3;
            this.clientChallenge2 = bArr4;
            this.secondaryKey = bArr5;
            this.timestamp = bArr6;
        }

        public CipherGen(String str, String str2, String str3, byte[] bArr, String str4, byte[] bArr2) {
            this(str, str2, str3, bArr, str4, bArr2, null, null, null, null);
        }

        public byte[] getClientChallenge() {
            if (this.clientChallenge == null) {
                this.clientChallenge = NTLMEngineImpl.f();
            }
            return this.clientChallenge;
        }

        public byte[] getClientChallenge2() {
            if (this.clientChallenge2 == null) {
                this.clientChallenge2 = NTLMEngineImpl.f();
            }
            return this.clientChallenge2;
        }

        public byte[] getSecondaryKey() {
            if (this.secondaryKey == null) {
                this.secondaryKey = NTLMEngineImpl.g();
            }
            return this.secondaryKey;
        }

        public byte[] getLMHash() {
            if (this.lmHash == null) {
                this.lmHash = NTLMEngineImpl.h(this.password);
            }
            return this.lmHash;
        }

        public byte[] getLMResponse() {
            if (this.lmResponse == null) {
                this.lmResponse = NTLMEngineImpl.d(getLMHash(), this.challenge);
            }
            return this.lmResponse;
        }

        public byte[] getNTLMHash() {
            if (this.ntlmHash == null) {
                this.ntlmHash = NTLMEngineImpl.i(this.password);
            }
            return this.ntlmHash;
        }

        public byte[] getNTLMResponse() {
            if (this.ntlmResponse == null) {
                this.ntlmResponse = NTLMEngineImpl.d(getNTLMHash(), this.challenge);
            }
            return this.ntlmResponse;
        }

        public byte[] getLMv2Hash() {
            if (this.lmv2Hash == null) {
                this.lmv2Hash = NTLMEngineImpl.c(this.domain, this.user, getNTLMHash());
            }
            return this.lmv2Hash;
        }

        public byte[] getNTLMv2Hash() {
            if (this.ntlmv2Hash == null) {
                this.ntlmv2Hash = NTLMEngineImpl.d(this.domain, this.user, getNTLMHash());
            }
            return this.ntlmv2Hash;
        }

        public byte[] getTimestamp() {
            if (this.timestamp == null) {
                long currentTimeMillis = (System.currentTimeMillis() + 11644473600000L) * LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS;
                this.timestamp = new byte[8];
                for (int i = 0; i < 8; i++) {
                    this.timestamp[i] = (byte) ((int) currentTimeMillis);
                    currentTimeMillis >>>= 8;
                }
            }
            return this.timestamp;
        }

        public byte[] getNTLMv2Blob() {
            if (this.ntlmv2Blob == null) {
                this.ntlmv2Blob = NTLMEngineImpl.e(getClientChallenge2(), this.targetInformation, getTimestamp());
            }
            return this.ntlmv2Blob;
        }

        public byte[] getNTLMv2Response() {
            if (this.ntlmv2Response == null) {
                this.ntlmv2Response = NTLMEngineImpl.d(getNTLMv2Hash(), this.challenge, getNTLMv2Blob());
            }
            return this.ntlmv2Response;
        }

        public byte[] getLMv2Response() {
            if (this.lmv2Response == null) {
                this.lmv2Response = NTLMEngineImpl.d(getLMv2Hash(), this.challenge, getClientChallenge());
            }
            return this.lmv2Response;
        }

        public byte[] getNTLM2SessionResponse() {
            if (this.ntlm2SessionResponse == null) {
                this.ntlm2SessionResponse = NTLMEngineImpl.a(getNTLMHash(), this.challenge, getClientChallenge());
            }
            return this.ntlm2SessionResponse;
        }

        public byte[] getLM2SessionResponse() {
            if (this.lm2SessionResponse == null) {
                byte[] clientChallenge3 = getClientChallenge();
                this.lm2SessionResponse = new byte[24];
                System.arraycopy(clientChallenge3, 0, this.lm2SessionResponse, 0, clientChallenge3.length);
                Arrays.fill(this.lm2SessionResponse, clientChallenge3.length, this.lm2SessionResponse.length, 0);
            }
            return this.lm2SessionResponse;
        }

        public byte[] getLMUserSessionKey() {
            if (this.lmUserSessionKey == null) {
                this.lmUserSessionKey = new byte[16];
                System.arraycopy(getLMHash(), 0, this.lmUserSessionKey, 0, 8);
                Arrays.fill(this.lmUserSessionKey, 8, 16, 0);
            }
            return this.lmUserSessionKey;
        }

        public byte[] getNTLMUserSessionKey() {
            if (this.ntlmUserSessionKey == null) {
                MD4 md4 = new MD4();
                md4.a(getNTLMHash());
                this.ntlmUserSessionKey = md4.a();
            }
            return this.ntlmUserSessionKey;
        }

        public byte[] getNTLMv2UserSessionKey() {
            if (this.ntlmv2UserSessionKey == null) {
                byte[] nTLMv2Hash = getNTLMv2Hash();
                byte[] bArr = new byte[16];
                System.arraycopy(getNTLMv2Response(), 0, bArr, 0, 16);
                this.ntlmv2UserSessionKey = NTLMEngineImpl.a(bArr, nTLMv2Hash);
            }
            return this.ntlmv2UserSessionKey;
        }

        public byte[] getNTLM2SessionResponseUserSessionKey() {
            if (this.ntlm2SessionResponseUserSessionKey == null) {
                byte[] lM2SessionResponse = getLM2SessionResponse();
                byte[] bArr = new byte[(this.challenge.length + lM2SessionResponse.length)];
                System.arraycopy(this.challenge, 0, bArr, 0, this.challenge.length);
                System.arraycopy(lM2SessionResponse, 0, bArr, this.challenge.length, lM2SessionResponse.length);
                this.ntlm2SessionResponseUserSessionKey = NTLMEngineImpl.a(bArr, getNTLMUserSessionKey());
            }
            return this.ntlm2SessionResponseUserSessionKey;
        }

        public byte[] getLanManagerSessionKey() {
            if (this.lanManagerSessionKey == null) {
                try {
                    byte[] bArr = new byte[14];
                    System.arraycopy(getLMHash(), 0, bArr, 0, 8);
                    Arrays.fill(bArr, 8, bArr.length, -67);
                    Key a = NTLMEngineImpl.g(bArr, 0);
                    Key a2 = NTLMEngineImpl.g(bArr, 7);
                    byte[] bArr2 = new byte[8];
                    System.arraycopy(getLMResponse(), 0, bArr2, 0, bArr2.length);
                    Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
                    instance.init(1, a);
                    byte[] doFinal = instance.doFinal(bArr2);
                    Cipher instance2 = Cipher.getInstance("DES/ECB/NoPadding");
                    instance2.init(1, a2);
                    byte[] doFinal2 = instance2.doFinal(bArr2);
                    this.lanManagerSessionKey = new byte[16];
                    System.arraycopy(doFinal, 0, this.lanManagerSessionKey, 0, doFinal.length);
                    System.arraycopy(doFinal2, 0, this.lanManagerSessionKey, doFinal.length, doFinal2.length);
                } catch (Exception e) {
                    throw new NTLMEngineException(e.getMessage(), e);
                }
            }
            return this.lanManagerSessionKey;
        }
    }

    static class HMACMD5 {
        protected byte[] a;
        protected byte[] b;
        protected MessageDigest c;

        HMACMD5(byte[] bArr) {
            try {
                this.c = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
                this.a = new byte[64];
                this.b = new byte[64];
                int length = bArr.length;
                if (length > 64) {
                    this.c.update(bArr);
                    bArr = this.c.digest();
                    length = bArr.length;
                }
                int i = 0;
                while (i < length) {
                    this.a[i] = (byte) (54 ^ bArr[i]);
                    this.b[i] = (byte) (92 ^ bArr[i]);
                    i++;
                }
                while (i < 64) {
                    this.a[i] = 54;
                    this.b[i] = 92;
                    i++;
                }
                this.c.reset();
                this.c.update(this.a);
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Error getting md5 message digest implementation: ");
                sb.append(e.getMessage());
                throw new NTLMEngineException(sb.toString(), e);
            }
        }

        /* access modifiers changed from: 0000 */
        public byte[] a() {
            byte[] digest = this.c.digest();
            this.c.update(this.b);
            return this.c.digest(digest);
        }

        /* access modifiers changed from: 0000 */
        public void a(byte[] bArr) {
            this.c.update(bArr);
        }
    }

    static class MD4 {
        protected int a = 1732584193;
        protected int b = -271733879;
        protected int c = -1732584194;
        protected int d = 271733878;
        protected long e = 0;
        protected byte[] f = new byte[64];

        MD4() {
        }

        /* access modifiers changed from: 0000 */
        public void a(byte[] bArr) {
            int i = (int) (this.e & 63);
            int i2 = 0;
            while ((bArr.length - i2) + i >= this.f.length) {
                int length = this.f.length - i;
                System.arraycopy(bArr, i2, this.f, i, length);
                this.e += (long) length;
                i2 += length;
                b();
                i = 0;
            }
            if (i2 < bArr.length) {
                int length2 = bArr.length - i2;
                System.arraycopy(bArr, i2, this.f, i, length2);
                this.e += (long) length2;
            }
        }

        /* access modifiers changed from: 0000 */
        public byte[] a() {
            int i = (int) (this.e & 63);
            int i2 = i < 56 ? 56 - i : 120 - i;
            byte[] bArr = new byte[(i2 + 8)];
            bArr[0] = UnsignedBytes.MAX_POWER_OF_TWO;
            for (int i3 = 0; i3 < 8; i3++) {
                bArr[i2 + i3] = (byte) ((int) ((this.e * 8) >>> (i3 * 8)));
            }
            a(bArr);
            byte[] bArr2 = new byte[16];
            NTLMEngineImpl.a(bArr2, this.a, 0);
            NTLMEngineImpl.a(bArr2, this.b, 4);
            NTLMEngineImpl.a(bArr2, this.c, 8);
            NTLMEngineImpl.a(bArr2, this.d, 12);
            return bArr2;
        }

        /* access modifiers changed from: protected */
        public void b() {
            int[] iArr = new int[16];
            for (int i = 0; i < 16; i++) {
                int i2 = i * 4;
                iArr[i] = (this.f[i2] & UnsignedBytes.MAX_VALUE) + ((this.f[i2 + 1] & UnsignedBytes.MAX_VALUE) << 8) + ((this.f[i2 + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) + ((this.f[i2 + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
            }
            int i3 = this.a;
            int i4 = this.b;
            int i5 = this.c;
            int i6 = this.d;
            a(iArr);
            b(iArr);
            c(iArr);
            this.a += i3;
            this.b += i4;
            this.c += i5;
            this.d += i6;
        }

        /* access modifiers changed from: protected */
        public void a(int[] iArr) {
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.a(this.b, this.c, this.d) + iArr[0], 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.a(this.a, this.b, this.c) + iArr[1], 7);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.a(this.d, this.a, this.b) + iArr[2], 11);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.a(this.c, this.d, this.a) + iArr[3], 19);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.a(this.b, this.c, this.d) + iArr[4], 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.a(this.a, this.b, this.c) + iArr[5], 7);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.a(this.d, this.a, this.b) + iArr[6], 11);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.a(this.c, this.d, this.a) + iArr[7], 19);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.a(this.b, this.c, this.d) + iArr[8], 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.a(this.a, this.b, this.c) + iArr[9], 7);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.a(this.d, this.a, this.b) + iArr[10], 11);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.a(this.c, this.d, this.a) + iArr[11], 19);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.a(this.b, this.c, this.d) + iArr[12], 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.a(this.a, this.b, this.c) + iArr[13], 7);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.a(this.d, this.a, this.b) + iArr[14], 11);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.a(this.c, this.d, this.a) + iArr[15], 19);
        }

        /* access modifiers changed from: protected */
        public void b(int[] iArr) {
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.b(this.b, this.c, this.d) + iArr[0] + 1518500249, 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.b(this.a, this.b, this.c) + iArr[4] + 1518500249, 5);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.b(this.d, this.a, this.b) + iArr[8] + 1518500249, 9);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.b(this.c, this.d, this.a) + iArr[12] + 1518500249, 13);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.b(this.b, this.c, this.d) + iArr[1] + 1518500249, 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.b(this.a, this.b, this.c) + iArr[5] + 1518500249, 5);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.b(this.d, this.a, this.b) + iArr[9] + 1518500249, 9);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.b(this.c, this.d, this.a) + iArr[13] + 1518500249, 13);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.b(this.b, this.c, this.d) + iArr[2] + 1518500249, 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.b(this.a, this.b, this.c) + iArr[6] + 1518500249, 5);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.b(this.d, this.a, this.b) + iArr[10] + 1518500249, 9);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.b(this.c, this.d, this.a) + iArr[14] + 1518500249, 13);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.b(this.b, this.c, this.d) + iArr[3] + 1518500249, 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.b(this.a, this.b, this.c) + iArr[7] + 1518500249, 5);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.b(this.d, this.a, this.b) + iArr[11] + 1518500249, 9);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.b(this.c, this.d, this.a) + iArr[15] + 1518500249, 13);
        }

        /* access modifiers changed from: protected */
        public void c(int[] iArr) {
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.c(this.b, this.c, this.d) + iArr[0] + 1859775393, 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.c(this.a, this.b, this.c) + iArr[8] + 1859775393, 9);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.c(this.d, this.a, this.b) + iArr[4] + 1859775393, 11);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.c(this.c, this.d, this.a) + iArr[12] + 1859775393, 15);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.c(this.b, this.c, this.d) + iArr[2] + 1859775393, 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.c(this.a, this.b, this.c) + iArr[10] + 1859775393, 9);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.c(this.d, this.a, this.b) + iArr[6] + 1859775393, 11);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.c(this.c, this.d, this.a) + iArr[14] + 1859775393, 15);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.c(this.b, this.c, this.d) + iArr[1] + 1859775393, 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.c(this.a, this.b, this.c) + iArr[9] + 1859775393, 9);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.c(this.d, this.a, this.b) + iArr[5] + 1859775393, 11);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.c(this.c, this.d, this.a) + iArr[13] + 1859775393, 15);
            this.a = NTLMEngineImpl.a(this.a + NTLMEngineImpl.c(this.b, this.c, this.d) + iArr[3] + 1859775393, 3);
            this.d = NTLMEngineImpl.a(this.d + NTLMEngineImpl.c(this.a, this.b, this.c) + iArr[11] + 1859775393, 9);
            this.c = NTLMEngineImpl.a(this.c + NTLMEngineImpl.c(this.d, this.a, this.b) + iArr[7] + 1859775393, 11);
            this.b = NTLMEngineImpl.a(this.b + NTLMEngineImpl.c(this.c, this.d, this.a) + iArr[15] + 1859775393, 15);
        }
    }

    static class NTLMMessage {
        private byte[] a;
        private int b;

        NTLMMessage() {
            this.a = null;
            this.b = 0;
        }

        NTLMMessage(String str, int i) {
            this.a = null;
            this.b = 0;
            this.a = Base64.decode(str.getBytes(NTLMEngineImpl.b), 2);
            if (this.a.length < NTLMEngineImpl.d.length) {
                throw new NTLMEngineException("NTLM message decoding error - packet too short");
            }
            for (int i2 = 0; i2 < NTLMEngineImpl.d.length; i2++) {
                if (this.a[i2] != NTLMEngineImpl.d[i2]) {
                    throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
                }
            }
            int a2 = a(NTLMEngineImpl.d.length);
            if (a2 != i) {
                StringBuilder sb = new StringBuilder();
                sb.append("NTLM type ");
                sb.append(Integer.toString(i));
                sb.append(" message expected - instead got type ");
                sb.append(Integer.toString(a2));
                throw new NTLMEngineException(sb.toString());
            }
            this.b = this.a.length;
        }

        /* access modifiers changed from: protected */
        public int a() {
            return this.b;
        }

        /* access modifiers changed from: protected */
        public void a(byte[] bArr, int i) {
            if (this.a.length < bArr.length + i) {
                throw new NTLMEngineException("NTLM: Message too short");
            }
            System.arraycopy(this.a, i, bArr, 0, bArr.length);
        }

        /* access modifiers changed from: protected */
        public int a(int i) {
            return NTLMEngineImpl.d(this.a, i);
        }

        /* access modifiers changed from: protected */
        public byte[] b(int i) {
            return NTLMEngineImpl.f(this.a, i);
        }

        /* access modifiers changed from: protected */
        public void a(int i, int i2) {
            this.a = new byte[i];
            this.b = 0;
            a(NTLMEngineImpl.d);
            d(i2);
        }

        /* access modifiers changed from: protected */
        public void a(byte b2) {
            this.a[this.b] = b2;
            this.b++;
        }

        /* access modifiers changed from: protected */
        public void a(byte[] bArr) {
            if (bArr != null) {
                for (byte b2 : bArr) {
                    this.a[this.b] = b2;
                    this.b++;
                }
            }
        }

        /* access modifiers changed from: protected */
        public void c(int i) {
            a((byte) (i & 255));
            a((byte) ((i >> 8) & 255));
        }

        /* access modifiers changed from: protected */
        public void d(int i) {
            a((byte) (i & 255));
            a((byte) ((i >> 8) & 255));
            a((byte) ((i >> 16) & 255));
            a((byte) ((i >> 24) & 255));
        }

        /* access modifiers changed from: 0000 */
        public String b() {
            byte[] bArr;
            if (this.a.length > this.b) {
                bArr = new byte[this.b];
                System.arraycopy(this.a, 0, bArr, 0, this.b);
            } else {
                bArr = this.a;
            }
            return EncodingUtils.getAsciiString(Base64.encode(bArr, 2));
        }
    }

    static class Type1Message extends NTLMMessage {
        private final byte[] a = null;
        private final byte[] b = null;

        Type1Message() {
        }

        /* access modifiers changed from: 0000 */
        public String b() {
            a(40, 1);
            d(-1576500735);
            c(0);
            c(0);
            d(40);
            c(0);
            c(0);
            d(40);
            c(261);
            d(2600);
            c(3840);
            if (this.a != null) {
                a(this.a);
            }
            if (this.b != null) {
                a(this.b);
            }
            return super.b();
        }
    }

    static class Type2Message extends NTLMMessage {
        protected byte[] a = new byte[8];
        protected String b;
        protected byte[] c;
        protected int d;

        Type2Message(String str) {
            super(str, 2);
            a(this.a, 24);
            this.d = a(20);
            if ((this.d & 1) == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("NTLM type 2 message indicates no support for Unicode. Flags are: ");
                sb.append(Integer.toString(this.d));
                throw new NTLMEngineException(sb.toString());
            }
            this.b = null;
            if (a() >= 20) {
                byte[] b2 = b(12);
                if (b2.length != 0) {
                    try {
                        this.b = new String(b2, "UnicodeLittleUnmarked");
                    } catch (UnsupportedEncodingException e) {
                        throw new NTLMEngineException(e.getMessage(), e);
                    }
                }
            }
            this.c = null;
            if (a() >= 48) {
                byte[] b3 = b(40);
                if (b3.length != 0) {
                    this.c = b3;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public byte[] c() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public String d() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public byte[] e() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public int f() {
            return this.d;
        }
    }

    static class Type3Message extends NTLMMessage {
        protected int a;
        protected byte[] b;
        protected byte[] c;
        protected byte[] d;
        protected byte[] e;
        protected byte[] f;
        protected byte[] g;

        Type3Message(String str, String str2, String str3, String str4, byte[] bArr, int i, String str5, byte[] bArr2) {
            byte[] bArr3;
            int i2 = i;
            this.a = i2;
            String c2 = NTLMEngineImpl.f(str2);
            String d2 = NTLMEngineImpl.g(str);
            CipherGen cipherGen = new CipherGen(d2, str3, str4, bArr, str5, bArr2);
            if ((8388608 & i2) != 0 && bArr2 != null && str5 != null) {
                try {
                    this.f = cipherGen.getNTLMv2Response();
                    this.e = cipherGen.getLMv2Response();
                    if ((i2 & 128) != 0) {
                        bArr3 = cipherGen.getLanManagerSessionKey();
                    } else {
                        bArr3 = cipherGen.getNTLMv2UserSessionKey();
                    }
                } catch (NTLMEngineException unused) {
                    this.f = new byte[0];
                    this.e = cipherGen.getLMResponse();
                    if ((i2 & 128) != 0) {
                        bArr3 = cipherGen.getLanManagerSessionKey();
                    } else {
                        bArr3 = cipherGen.getLMUserSessionKey();
                    }
                }
            } else if ((524288 & i2) != 0) {
                this.f = cipherGen.getNTLM2SessionResponse();
                this.e = cipherGen.getLM2SessionResponse();
                if ((i2 & 128) != 0) {
                    bArr3 = cipherGen.getLanManagerSessionKey();
                } else {
                    bArr3 = cipherGen.getNTLM2SessionResponseUserSessionKey();
                }
            } else {
                this.f = cipherGen.getNTLMResponse();
                this.e = cipherGen.getLMResponse();
                if ((i2 & 128) != 0) {
                    bArr3 = cipherGen.getLanManagerSessionKey();
                } else {
                    bArr3 = cipherGen.getNTLMUserSessionKey();
                }
            }
            byte[] bArr4 = null;
            if ((i2 & 16) == 0) {
                this.g = null;
            } else if ((i2 & 1073741824) != 0) {
                this.g = NTLMEngineImpl.b(cipherGen.getSecondaryKey(), bArr3);
            } else {
                this.g = bArr3;
            }
            if (NTLMEngineImpl.a == null) {
                throw new NTLMEngineException("Unicode not supported");
            }
            this.c = c2 != null ? c2.getBytes(NTLMEngineImpl.a) : null;
            if (d2 != null) {
                bArr4 = d2.toUpperCase(Locale.ROOT).getBytes(NTLMEngineImpl.a);
            }
            this.b = bArr4;
            this.d = str3.getBytes(NTLMEngineImpl.a);
        }

        /* access modifiers changed from: 0000 */
        public String b() {
            int length = this.f.length;
            int length2 = this.e.length;
            int i = 0;
            int length3 = this.b != null ? this.b.length : 0;
            int length4 = this.c != null ? this.c.length : 0;
            int length5 = this.d.length;
            if (this.g != null) {
                i = this.g.length;
            }
            int i2 = length2 + 72;
            int i3 = i2 + length;
            int i4 = i3 + length3;
            int i5 = i4 + length5;
            int i6 = i5 + length4;
            a(i6 + i, 3);
            c(length2);
            c(length2);
            d(72);
            c(length);
            c(length);
            d(i2);
            c(length3);
            c(length3);
            d(i3);
            c(length5);
            c(length5);
            d(i4);
            c(length4);
            c(length4);
            d(i5);
            c(i);
            c(i);
            d(i6);
            d((this.a & 128) | (this.a & 512) | (this.a & 524288) | 33554432 | (this.a & 32768) | (this.a & 32) | (this.a & 16) | (this.a & PKIFailureInfo.duplicateCertReq) | (this.a & Integer.MIN_VALUE) | (this.a & 1073741824) | (this.a & 8388608) | (this.a & 1) | (this.a & 4));
            c(261);
            d(2600);
            c(3840);
            a(this.e);
            a(this.f);
            a(this.b);
            a(this.d);
            a(this.c);
            if (this.g != null) {
                a(this.g);
            }
            return super.b();
        }
    }

    static int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    static int a(int i, int i2, int i3) {
        return ((i ^ -1) & i3) | (i2 & i);
    }

    static int b(int i, int i2, int i3) {
        return (i & i3) | (i & i2) | (i2 & i3);
    }

    static int c(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    NTLMEngineImpl() {
    }

    static {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception unused) {
            secureRandom = null;
        }
        c = secureRandom;
        byte[] bytes = "NTLMSSP".getBytes(Consts.ASCII);
        d = new byte[(bytes.length + 1)];
        System.arraycopy(bytes, 0, d, 0, bytes.length);
        d[bytes.length] = 0;
    }

    static String a(String str, String str2) {
        return e.b();
    }

    static String a(String str, String str2, String str3, String str4, byte[] bArr, int i, String str5, byte[] bArr2) {
        Type3Message type3Message = new Type3Message(str4, str3, str, str2, bArr, i, str5, bArr2);
        return type3Message.b();
    }

    private static String e(String str) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(".");
        return indexOf != -1 ? str.substring(0, indexOf) : str;
    }

    /* access modifiers changed from: private */
    public static String f(String str) {
        return e(str);
    }

    /* access modifiers changed from: private */
    public static String g(String str) {
        return e(str);
    }

    /* access modifiers changed from: private */
    public static int d(byte[] bArr, int i) {
        if (bArr.length < i + 4) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
        }
        return ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    private static int e(byte[] bArr, int i) {
        if (bArr.length < i + 2) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
        }
        return ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | (bArr[i] & UnsignedBytes.MAX_VALUE);
    }

    /* access modifiers changed from: private */
    public static byte[] f(byte[] bArr, int i) {
        int e2 = e(bArr, i);
        int d2 = d(bArr, i + 4);
        if (bArr.length < d2 + e2) {
            throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
        }
        byte[] bArr2 = new byte[e2];
        System.arraycopy(bArr, d2, bArr2, 0, e2);
        return bArr2;
    }

    /* access modifiers changed from: private */
    public static byte[] f() {
        if (c == null) {
            throw new NTLMEngineException("Random generator not available");
        }
        byte[] bArr = new byte[8];
        synchronized (c) {
            c.nextBytes(bArr);
        }
        return bArr;
    }

    /* access modifiers changed from: private */
    public static byte[] g() {
        if (c == null) {
            throw new NTLMEngineException("Random generator not available");
        }
        byte[] bArr = new byte[16];
        synchronized (c) {
            c.nextBytes(bArr);
        }
        return bArr;
    }

    static byte[] a(byte[] bArr, byte[] bArr2) {
        HMACMD5 hmacmd5 = new HMACMD5(bArr2);
        hmacmd5.a(bArr);
        return hmacmd5.a();
    }

    static byte[] b(byte[] bArr, byte[] bArr2) {
        try {
            Cipher instance = Cipher.getInstance("RC4");
            instance.init(1, new SecretKeySpec(bArr2, "RC4"));
            return instance.doFinal(bArr);
        } catch (Exception e2) {
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.update(bArr2);
            instance.update(bArr3);
            byte[] digest = instance.digest();
            byte[] bArr4 = new byte[8];
            System.arraycopy(digest, 0, bArr4, 0, 8);
            return d(bArr, bArr4);
        } catch (Exception e2) {
            if (e2 instanceof NTLMEngineException) {
                throw ((NTLMEngineException) e2);
            }
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: private */
    public static byte[] h(String str) {
        try {
            byte[] bytes = str.toUpperCase(Locale.ROOT).getBytes(Consts.ASCII);
            byte[] bArr = new byte[14];
            System.arraycopy(bytes, 0, bArr, 0, Math.min(bytes.length, 14));
            Key g = g(bArr, 0);
            Key g2 = g(bArr, 7);
            byte[] bytes2 = "KGS!@#$%".getBytes(Consts.ASCII);
            Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
            instance.init(1, g);
            byte[] doFinal = instance.doFinal(bytes2);
            instance.init(1, g2);
            byte[] doFinal2 = instance.doFinal(bytes2);
            byte[] bArr2 = new byte[16];
            System.arraycopy(doFinal, 0, bArr2, 0, 8);
            System.arraycopy(doFinal2, 0, bArr2, 8, 8);
            return bArr2;
        } catch (Exception e2) {
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: private */
    public static byte[] i(String str) {
        if (a == null) {
            throw new NTLMEngineException("Unicode not supported");
        }
        byte[] bytes = str.getBytes(a);
        MD4 md4 = new MD4();
        md4.a(bytes);
        return md4.a();
    }

    /* access modifiers changed from: private */
    public static byte[] c(String str, String str2, byte[] bArr) {
        if (a == null) {
            throw new NTLMEngineException("Unicode not supported");
        }
        HMACMD5 hmacmd5 = new HMACMD5(bArr);
        hmacmd5.a(str2.toUpperCase(Locale.ROOT).getBytes(a));
        if (str != null) {
            hmacmd5.a(str.toUpperCase(Locale.ROOT).getBytes(a));
        }
        return hmacmd5.a();
    }

    /* access modifiers changed from: private */
    public static byte[] d(String str, String str2, byte[] bArr) {
        if (a == null) {
            throw new NTLMEngineException("Unicode not supported");
        }
        HMACMD5 hmacmd5 = new HMACMD5(bArr);
        hmacmd5.a(str2.toUpperCase(Locale.ROOT).getBytes(a));
        if (str != null) {
            hmacmd5.a(str.getBytes(a));
        }
        return hmacmd5.a();
    }

    /* access modifiers changed from: private */
    public static byte[] d(byte[] bArr, byte[] bArr2) {
        try {
            byte[] bArr3 = new byte[21];
            System.arraycopy(bArr, 0, bArr3, 0, 16);
            Key g = g(bArr3, 0);
            Key g2 = g(bArr3, 7);
            Key g3 = g(bArr3, 14);
            Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
            instance.init(1, g);
            byte[] doFinal = instance.doFinal(bArr2);
            instance.init(1, g2);
            byte[] doFinal2 = instance.doFinal(bArr2);
            instance.init(1, g3);
            byte[] doFinal3 = instance.doFinal(bArr2);
            byte[] bArr4 = new byte[24];
            System.arraycopy(doFinal, 0, bArr4, 0, 8);
            System.arraycopy(doFinal2, 0, bArr4, 8, 8);
            System.arraycopy(doFinal3, 0, bArr4, 16, 8);
            return bArr4;
        } catch (Exception e2) {
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: private */
    public static byte[] d(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        HMACMD5 hmacmd5 = new HMACMD5(bArr);
        hmacmd5.a(bArr2);
        hmacmd5.a(bArr3);
        byte[] a2 = hmacmd5.a();
        byte[] bArr4 = new byte[(a2.length + bArr3.length)];
        System.arraycopy(a2, 0, bArr4, 0, a2.length);
        System.arraycopy(bArr3, 0, bArr4, a2.length, bArr3.length);
        return bArr4;
    }

    /* access modifiers changed from: private */
    public static byte[] e(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = {1, 1, 0, 0};
        byte[] bArr5 = {0, 0, 0, 0};
        byte[] bArr6 = {0, 0, 0, 0};
        byte[] bArr7 = {0, 0, 0, 0};
        byte[] bArr8 = new byte[(bArr4.length + bArr5.length + bArr3.length + 8 + bArr6.length + bArr2.length + bArr7.length)];
        System.arraycopy(bArr4, 0, bArr8, 0, bArr4.length);
        int length = bArr4.length + 0;
        System.arraycopy(bArr5, 0, bArr8, length, bArr5.length);
        int length2 = length + bArr5.length;
        System.arraycopy(bArr3, 0, bArr8, length2, bArr3.length);
        int length3 = length2 + bArr3.length;
        System.arraycopy(bArr, 0, bArr8, length3, 8);
        int i = length3 + 8;
        System.arraycopy(bArr6, 0, bArr8, i, bArr6.length);
        int length4 = i + bArr6.length;
        System.arraycopy(bArr2, 0, bArr8, length4, bArr2.length);
        System.arraycopy(bArr7, 0, bArr8, length4 + bArr2.length, bArr7.length);
        int length5 = bArr7.length;
        return bArr8;
    }

    /* access modifiers changed from: private */
    public static Key g(byte[] bArr, int i) {
        byte[] bArr2 = new byte[7];
        System.arraycopy(bArr, i, bArr2, 0, 7);
        byte[] bArr3 = {bArr2[0], (byte) ((bArr2[0] << 7) | ((bArr2[1] & UnsignedBytes.MAX_VALUE) >>> 1)), (byte) ((bArr2[1] << 6) | ((bArr2[2] & UnsignedBytes.MAX_VALUE) >>> 2)), (byte) ((bArr2[2] << 5) | ((bArr2[3] & UnsignedBytes.MAX_VALUE) >>> 3)), (byte) ((bArr2[3] << 4) | ((bArr2[4] & UnsignedBytes.MAX_VALUE) >>> 4)), (byte) ((bArr2[4] << 3) | ((bArr2[5] & UnsignedBytes.MAX_VALUE) >>> 5)), (byte) ((bArr2[5] << 2) | ((bArr2[6] & UnsignedBytes.MAX_VALUE) >>> 6)), (byte) (bArr2[6] << 1)};
        a(bArr3);
        return new SecretKeySpec(bArr3, "DES");
    }

    private static void a(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            byte b2 = bArr[i];
            if ((((b2 >>> 1) ^ ((((((b2 >>> 7) ^ (b2 >>> 6)) ^ (b2 >>> 5)) ^ (b2 >>> 4)) ^ (b2 >>> 3)) ^ (b2 >>> 2))) & 1) == 0) {
                bArr[i] = (byte) (bArr[i] | 1);
            } else {
                bArr[i] = (byte) (bArr[i] & -2);
            }
        }
    }

    static void a(byte[] bArr, int i, int i2) {
        bArr[i2] = (byte) (i & 255);
        bArr[i2 + 1] = (byte) ((i >> 8) & 255);
        bArr[i2 + 2] = (byte) ((i >> 16) & 255);
        bArr[i2 + 3] = (byte) ((i >> 24) & 255);
    }

    public String generateType1Msg(String str, String str2) {
        return a(str2, str);
    }

    public String generateType3Msg(String str, String str2, String str3, String str4, String str5) {
        Type2Message type2Message = new Type2Message(str5);
        return a(str, str2, str4, str3, type2Message.c(), type2Message.f(), type2Message.d(), type2Message.e());
    }
}
