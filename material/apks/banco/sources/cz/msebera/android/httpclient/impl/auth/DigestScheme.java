package cz.msebera.android.httpclient.impl.auth;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.base.Ascii;
import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.auth.AuthenticationException;
import cz.msebera.android.httpclient.auth.ChallengeState;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.auth.MalformedChallengeException;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;

@NotThreadSafe
public class DigestScheme extends RFC2617Scheme {
    private static final char[] a = {TarjetasConstants.ULT_NUM_AMEX, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final long serialVersionUID = 3883908186234566916L;
    private boolean b;
    private String c;
    private long d;
    private String e;
    private String f;
    private String g;

    public String getSchemeName() {
        return "digest";
    }

    public boolean isConnectionBased() {
        return false;
    }

    public DigestScheme(Charset charset) {
        super(charset);
        this.b = false;
    }

    @Deprecated
    public DigestScheme(ChallengeState challengeState) {
        super(challengeState);
    }

    public DigestScheme() {
        this(Consts.ASCII);
    }

    public void processChallenge(Header header) {
        super.processChallenge(header);
        this.b = true;
        if (getParameters().isEmpty()) {
            throw new MalformedChallengeException("Authentication challenge is empty");
        }
    }

    public boolean isComplete() {
        if ("true".equalsIgnoreCase(getParameter("stale"))) {
            return false;
        }
        return this.b;
    }

    public void overrideParamter(String str, String str2) {
        getParameters().put(str, str2);
    }

    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) {
        return authenticate(credentials, httpRequest, new BasicHttpContext());
    }

    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) {
        Args.notNull(credentials, "Credentials");
        Args.notNull(httpRequest, "HTTP request");
        if (getParameter("realm") == null) {
            throw new AuthenticationException("missing realm in challenge");
        } else if (getParameter("nonce") == null) {
            throw new AuthenticationException("missing nonce in challenge");
        } else {
            getParameters().put("methodname", httpRequest.getRequestLine().getMethod());
            getParameters().put("uri", httpRequest.getRequestLine().getUri());
            if (getParameter(io.fabric.sdk.android.services.network.HttpRequest.PARAM_CHARSET) == null) {
                getParameters().put(io.fabric.sdk.android.services.network.HttpRequest.PARAM_CHARSET, a(httpRequest));
            }
            return a(credentials, httpRequest);
        }
    }

    private static MessageDigest a(String str) {
        try {
            return MessageDigest.getInstance(str);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unsupported algorithm in HTTP Digest authentication: ");
            sb.append(str);
            throw new UnsupportedDigestAlgorithmException(sb.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x0258  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0272  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x02c0  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x02c6  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x030b  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x033f  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0352  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private cz.msebera.android.httpclient.Header a(cz.msebera.android.httpclient.auth.Credentials r23, cz.msebera.android.httpclient.HttpRequest r24) {
        /*
            r22 = this;
            r1 = r22
            r2 = r24
            java.lang.String r3 = "uri"
            java.lang.String r3 = r1.getParameter(r3)
            java.lang.String r4 = "realm"
            java.lang.String r4 = r1.getParameter(r4)
            java.lang.String r5 = "nonce"
            java.lang.String r5 = r1.getParameter(r5)
            java.lang.String r6 = "opaque"
            java.lang.String r6 = r1.getParameter(r6)
            java.lang.String r7 = "methodname"
            java.lang.String r7 = r1.getParameter(r7)
            java.lang.String r8 = "algorithm"
            java.lang.String r8 = r1.getParameter(r8)
            if (r8 != 0) goto L_0x002c
            java.lang.String r8 = "MD5"
        L_0x002c:
            java.util.HashSet r9 = new java.util.HashSet
            r10 = 8
            r9.<init>(r10)
            java.lang.String r10 = "qop"
            java.lang.String r10 = r1.getParameter(r10)
            r11 = -1
            if (r10 == 0) goto L_0x0075
            java.util.StringTokenizer r15 = new java.util.StringTokenizer
            java.lang.String r12 = ","
            r15.<init>(r10, r12)
        L_0x0043:
            boolean r12 = r15.hasMoreTokens()
            if (r12 == 0) goto L_0x005b
            java.lang.String r12 = r15.nextToken()
            java.lang.String r12 = r12.trim()
            java.util.Locale r14 = java.util.Locale.ROOT
            java.lang.String r12 = r12.toLowerCase(r14)
            r9.add(r12)
            goto L_0x0043
        L_0x005b:
            boolean r12 = r2 instanceof cz.msebera.android.httpclient.HttpEntityEnclosingRequest
            if (r12 == 0) goto L_0x0069
            java.lang.String r12 = "auth-int"
            boolean r12 = r9.contains(r12)
            if (r12 == 0) goto L_0x0069
            r12 = 1
            goto L_0x0076
        L_0x0069:
            java.lang.String r12 = "auth"
            boolean r12 = r9.contains(r12)
            if (r12 == 0) goto L_0x0073
            r12 = 2
            goto L_0x0076
        L_0x0073:
            r12 = -1
            goto L_0x0076
        L_0x0075:
            r12 = 0
        L_0x0076:
            if (r12 != r11) goto L_0x008f
            cz.msebera.android.httpclient.auth.AuthenticationException r2 = new cz.msebera.android.httpclient.auth.AuthenticationException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "None of the qop methods is supported: "
            r3.append(r4)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x008f:
            java.lang.String r10 = "charset"
            java.lang.String r10 = r1.getParameter(r10)
            if (r10 != 0) goto L_0x0099
            java.lang.String r10 = "ISO-8859-1"
        L_0x0099:
            java.lang.String r11 = "MD5-sess"
            boolean r11 = r8.equalsIgnoreCase(r11)
            if (r11 == 0) goto L_0x00a4
            java.lang.String r11 = "MD5"
            goto L_0x00a5
        L_0x00a4:
            r11 = r8
        L_0x00a5:
            java.security.MessageDigest r14 = a(r11)     // Catch:{ UnsupportedDigestAlgorithmException -> 0x038f }
            java.security.Principal r11 = r23.getUserPrincipal()
            java.lang.String r11 = r11.getName()
            java.lang.String r15 = r23.getPassword()
            java.lang.String r13 = r1.c
            boolean r13 = r5.equals(r13)
            r16 = r3
            if (r13 == 0) goto L_0x00ce
            long r2 = r1.d
            r17 = r6
            r18 = r7
            r19 = r12
            r6 = 1
            long r12 = r2 + r6
            r1.d = r12
            goto L_0x00dd
        L_0x00ce:
            r17 = r6
            r18 = r7
            r19 = r12
            r6 = 1
            r1.d = r6
            r2 = 0
            r1.e = r2
            r1.c = r5
        L_0x00dd:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 256(0x100, float:3.59E-43)
            r2.<init>(r3)
            java.util.Formatter r3 = new java.util.Formatter
            java.util.Locale r6 = java.util.Locale.US
            r3.<init>(r2, r6)
            java.lang.String r6 = "%08x"
            r7 = 1
            java.lang.Object[] r12 = new java.lang.Object[r7]
            r20 = r14
            long r13 = r1.d
            java.lang.Long r7 = java.lang.Long.valueOf(r13)
            r13 = 0
            r12[r13] = r7
            r3.format(r6, r12)
            r3.close()
            java.lang.String r3 = r2.toString()
            java.lang.String r6 = r1.e
            if (r6 != 0) goto L_0x010f
            java.lang.String r6 = createCnonce()
            r1.e = r6
        L_0x010f:
            r6 = 0
            r1.f = r6
            r1.g = r6
            java.lang.String r7 = "MD5-sess"
            boolean r7 = r8.equalsIgnoreCase(r7)
            r12 = 58
            if (r7 == 0) goto L_0x015e
            r7 = 0
            r2.setLength(r7)
            r2.append(r11)
            r2.append(r12)
            r2.append(r4)
            r2.append(r12)
            r2.append(r15)
            java.lang.String r13 = r2.toString()
            byte[] r13 = cz.msebera.android.httpclient.util.EncodingUtils.getBytes(r13, r10)
            r14 = r20
            byte[] r13 = r14.digest(r13)
            java.lang.String r13 = a(r13)
            r2.setLength(r7)
            r2.append(r13)
            r2.append(r12)
            r2.append(r5)
            r2.append(r12)
            java.lang.String r13 = r1.e
            r2.append(r13)
            java.lang.String r13 = r2.toString()
            r1.f = r13
            goto L_0x0179
        L_0x015e:
            r14 = r20
            r7 = 0
            r2.setLength(r7)
            r2.append(r11)
            r2.append(r12)
            r2.append(r4)
            r2.append(r12)
            r2.append(r15)
            java.lang.String r7 = r2.toString()
            r1.f = r7
        L_0x0179:
            java.lang.String r7 = r1.f
            byte[] r7 = cz.msebera.android.httpclient.util.EncodingUtils.getBytes(r7, r10)
            byte[] r7 = r14.digest(r7)
            java.lang.String r7 = a(r7)
            r13 = r19
            r15 = 2
            if (r13 != r15) goto L_0x01a9
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r9 = r18
            r6.append(r9)
            r6.append(r12)
            r9 = r16
            r6.append(r9)
            java.lang.String r6 = r6.toString()
            r1.g = r6
            r6 = r9
            r21 = r13
            goto L_0x0246
        L_0x01a9:
            r6 = r16
            r15 = r18
            r12 = 1
            if (r13 != r12) goto L_0x022e
            r21 = r13
            r12 = r24
            boolean r13 = r12 instanceof cz.msebera.android.httpclient.HttpEntityEnclosingRequest
            if (r13 == 0) goto L_0x01bf
            cz.msebera.android.httpclient.HttpEntityEnclosingRequest r12 = (cz.msebera.android.httpclient.HttpEntityEnclosingRequest) r12
            cz.msebera.android.httpclient.HttpEntity r12 = r12.getEntity()
            goto L_0x01c0
        L_0x01bf:
            r12 = 0
        L_0x01c0:
            if (r12 == 0) goto L_0x01f0
            boolean r13 = r12.isRepeatable()
            if (r13 != 0) goto L_0x01f0
            java.lang.String r12 = "auth"
            boolean r9 = r9.contains(r12)
            if (r9 == 0) goto L_0x01e8
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r15)
            r12 = 58
            r9.append(r12)
            r9.append(r6)
            java.lang.String r9 = r9.toString()
            r1.g = r9
            r12 = 2
            goto L_0x0248
        L_0x01e8:
            cz.msebera.android.httpclient.auth.AuthenticationException r2 = new cz.msebera.android.httpclient.auth.AuthenticationException
            java.lang.String r3 = "Qop auth-int cannot be used with a non-repeatable entity"
            r2.<init>(r3)
            throw r2
        L_0x01f0:
            cz.msebera.android.httpclient.impl.auth.HttpEntityDigester r9 = new cz.msebera.android.httpclient.impl.auth.HttpEntityDigester
            r9.<init>(r14)
            if (r12 == 0) goto L_0x01fe
            r12.writeTo(r9)     // Catch:{ IOException -> 0x01fb }
            goto L_0x01fe
        L_0x01fb:
            r0 = move-exception
            r2 = r0
            goto L_0x0226
        L_0x01fe:
            r9.close()     // Catch:{ IOException -> 0x01fb }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r15)
            r13 = 58
            r12.append(r13)
            r12.append(r6)
            r12.append(r13)
            byte[] r9 = r9.a()
            java.lang.String r9 = a(r9)
            r12.append(r9)
            java.lang.String r9 = r12.toString()
            r1.g = r9
            goto L_0x0246
        L_0x0226:
            cz.msebera.android.httpclient.auth.AuthenticationException r3 = new cz.msebera.android.httpclient.auth.AuthenticationException
            java.lang.String r4 = "I/O error reading entity content"
            r3.<init>(r4, r2)
            throw r3
        L_0x022e:
            r21 = r13
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r15)
            r12 = 58
            r9.append(r12)
            r9.append(r6)
            java.lang.String r9 = r9.toString()
            r1.g = r9
        L_0x0246:
            r12 = r21
        L_0x0248:
            java.lang.String r9 = r1.g
            byte[] r9 = cz.msebera.android.httpclient.util.EncodingUtils.getBytes(r9, r10)
            byte[] r9 = r14.digest(r9)
            java.lang.String r9 = a(r9)
            if (r12 != 0) goto L_0x0272
            r10 = 0
            r2.setLength(r10)
            r2.append(r7)
            r13 = 58
            r2.append(r13)
            r2.append(r5)
            r2.append(r13)
            r2.append(r9)
            java.lang.String r2 = r2.toString()
            goto L_0x02a7
        L_0x0272:
            r10 = 0
            r13 = 58
            r2.setLength(r10)
            r2.append(r7)
            r2.append(r13)
            r2.append(r5)
            r2.append(r13)
            r2.append(r3)
            r2.append(r13)
            java.lang.String r7 = r1.e
            r2.append(r7)
            r2.append(r13)
            r7 = 1
            if (r12 != r7) goto L_0x0298
            java.lang.String r7 = "auth-int"
            goto L_0x029a
        L_0x0298:
            java.lang.String r7 = "auth"
        L_0x029a:
            r2.append(r7)
            r2.append(r13)
            r2.append(r9)
            java.lang.String r2 = r2.toString()
        L_0x02a7:
            byte[] r2 = cz.msebera.android.httpclient.util.EncodingUtils.getAsciiBytes(r2)
            byte[] r2 = r14.digest(r2)
            java.lang.String r2 = a(r2)
            cz.msebera.android.httpclient.util.CharArrayBuffer r7 = new cz.msebera.android.httpclient.util.CharArrayBuffer
            r9 = 128(0x80, float:1.794E-43)
            r7.<init>(r9)
            boolean r9 = r22.isProxy()
            if (r9 == 0) goto L_0x02c6
            java.lang.String r9 = "Proxy-Authorization"
            r7.append(r9)
            goto L_0x02cb
        L_0x02c6:
            java.lang.String r9 = "Authorization"
            r7.append(r9)
        L_0x02cb:
            java.lang.String r9 = ": Digest "
            r7.append(r9)
            java.util.ArrayList r9 = new java.util.ArrayList
            r13 = 20
            r9.<init>(r13)
            cz.msebera.android.httpclient.message.BasicNameValuePair r13 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r14 = "username"
            r13.<init>(r14, r11)
            r9.add(r13)
            cz.msebera.android.httpclient.message.BasicNameValuePair r11 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r13 = "realm"
            r11.<init>(r13, r4)
            r9.add(r11)
            cz.msebera.android.httpclient.message.BasicNameValuePair r4 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r11 = "nonce"
            r4.<init>(r11, r5)
            r9.add(r4)
            cz.msebera.android.httpclient.message.BasicNameValuePair r4 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r5 = "uri"
            r4.<init>(r5, r6)
            r9.add(r4)
            cz.msebera.android.httpclient.message.BasicNameValuePair r4 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r5 = "response"
            r4.<init>(r5, r2)
            r9.add(r4)
            if (r12 == 0) goto L_0x0333
            cz.msebera.android.httpclient.message.BasicNameValuePair r2 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r4 = "qop"
            r5 = 1
            if (r12 != r5) goto L_0x0315
            java.lang.String r5 = "auth-int"
            goto L_0x0317
        L_0x0315:
            java.lang.String r5 = "auth"
        L_0x0317:
            r2.<init>(r4, r5)
            r9.add(r2)
            cz.msebera.android.httpclient.message.BasicNameValuePair r2 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r4 = "nc"
            r2.<init>(r4, r3)
            r9.add(r2)
            cz.msebera.android.httpclient.message.BasicNameValuePair r2 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r3 = "cnonce"
            java.lang.String r4 = r1.e
            r2.<init>(r3, r4)
            r9.add(r2)
        L_0x0333:
            cz.msebera.android.httpclient.message.BasicNameValuePair r2 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r3 = "algorithm"
            r2.<init>(r3, r8)
            r9.add(r2)
            if (r17 == 0) goto L_0x034b
            cz.msebera.android.httpclient.message.BasicNameValuePair r2 = new cz.msebera.android.httpclient.message.BasicNameValuePair
            java.lang.String r3 = "opaque"
            r4 = r17
            r2.<init>(r3, r4)
            r9.add(r2)
        L_0x034b:
            r2 = 0
        L_0x034c:
            int r3 = r9.size()
            if (r2 >= r3) goto L_0x0389
            java.lang.Object r3 = r9.get(r2)
            cz.msebera.android.httpclient.message.BasicNameValuePair r3 = (cz.msebera.android.httpclient.message.BasicNameValuePair) r3
            if (r2 <= 0) goto L_0x035f
            java.lang.String r4 = ", "
            r7.append(r4)
        L_0x035f:
            java.lang.String r4 = r3.getName()
            java.lang.String r5 = "nc"
            boolean r5 = r5.equals(r4)
            if (r5 != 0) goto L_0x037e
            java.lang.String r5 = "qop"
            boolean r5 = r5.equals(r4)
            if (r5 != 0) goto L_0x037e
            java.lang.String r5 = "algorithm"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L_0x037c
            goto L_0x037e
        L_0x037c:
            r4 = 0
            goto L_0x037f
        L_0x037e:
            r4 = 1
        L_0x037f:
            cz.msebera.android.httpclient.message.BasicHeaderValueFormatter r5 = cz.msebera.android.httpclient.message.BasicHeaderValueFormatter.INSTANCE
            r6 = 1
            r4 = r4 ^ r6
            r5.formatNameValuePair(r7, r3, r4)
            int r2 = r2 + 1
            goto L_0x034c
        L_0x0389:
            cz.msebera.android.httpclient.message.BufferedHeader r2 = new cz.msebera.android.httpclient.message.BufferedHeader
            r2.<init>(r7)
            return r2
        L_0x038f:
            cz.msebera.android.httpclient.auth.AuthenticationException r2 = new cz.msebera.android.httpclient.auth.AuthenticationException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unsuppported digest algorithm: "
            r3.append(r4)
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.auth.DigestScheme.a(cz.msebera.android.httpclient.auth.Credentials, cz.msebera.android.httpclient.HttpRequest):cz.msebera.android.httpclient.Header");
    }

    static String a(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[(length * 2)];
        for (int i = 0; i < length; i++) {
            byte b2 = bArr[i] & Ascii.SI;
            int i2 = i * 2;
            cArr[i2] = a[(bArr[i] & 240) >> 4];
            cArr[i2 + 1] = a[b2];
        }
        return new String(cArr);
    }

    public static String createCnonce() {
        byte[] bArr = new byte[8];
        new SecureRandom().nextBytes(bArr);
        return a(bArr);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DIGEST [complete=");
        sb.append(this.b);
        sb.append(", nonce=");
        sb.append(this.c);
        sb.append(", nc=");
        sb.append(this.d);
        sb.append("]");
        return sb.toString();
    }
}
