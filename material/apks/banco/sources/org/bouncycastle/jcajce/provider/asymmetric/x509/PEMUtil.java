package org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.util.encoders.Base64;

public class PEMUtil {
    private final String a;
    private final String b;
    private final String c;
    private final String d;

    PEMUtil(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN ");
        sb.append(str);
        sb.append("-----");
        this.a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("-----BEGIN X509 ");
        sb2.append(str);
        sb2.append("-----");
        this.b = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("-----END ");
        sb3.append(str);
        sb3.append("-----");
        this.c = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append("-----END X509 ");
        sb4.append(str);
        sb4.append("-----");
        this.d = sb4.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String b(java.io.InputStream r4) {
        /*
            r3 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            int r1 = r4.read()
            r2 = 13
            if (r1 == r2) goto L_0x0018
            r2 = 10
            if (r1 == r2) goto L_0x0018
            if (r1 < 0) goto L_0x0018
            char r1 = (char) r1
            r0.append(r1)
            goto L_0x0005
        L_0x0018:
            if (r1 < 0) goto L_0x0020
            int r2 = r0.length()
            if (r2 == 0) goto L_0x0005
        L_0x0020:
            if (r1 >= 0) goto L_0x0024
            r4 = 0
            return r4
        L_0x0024:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.b(java.io.InputStream):java.lang.String");
    }

    /* access modifiers changed from: 0000 */
    public ASN1Sequence a(InputStream inputStream) {
        String b2;
        StringBuffer stringBuffer = new StringBuffer();
        do {
            b2 = b(inputStream);
            if (b2 == null || b2.startsWith(this.a)) {
            }
        } while (!b2.startsWith(this.b));
        while (true) {
            String b3 = b(inputStream);
            if (b3 != null && !b3.startsWith(this.c) && !b3.startsWith(this.d)) {
                stringBuffer.append(b3);
            }
        }
        if (stringBuffer.length() == 0) {
            return null;
        }
        try {
            return ASN1Sequence.getInstance(Base64.decode(stringBuffer.toString()));
        } catch (Exception unused) {
            throw new IOException("malformed PEM data encountered");
        }
    }
}
