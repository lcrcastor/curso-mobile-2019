package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
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

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String b(java.io.InputStream r5) {
        /*
            r4 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            int r1 = r5.read()
            r2 = 13
            if (r1 == r2) goto L_0x001b
            r3 = 10
            if (r1 == r3) goto L_0x001b
            if (r1 < 0) goto L_0x001b
            if (r1 != r2) goto L_0x0016
            goto L_0x0005
        L_0x0016:
            char r1 = (char) r1
            r0.append(r1)
            goto L_0x0005
        L_0x001b:
            if (r1 < 0) goto L_0x0023
            int r2 = r0.length()
            if (r2 == 0) goto L_0x0005
        L_0x0023:
            if (r1 >= 0) goto L_0x0027
            r5 = 0
            return r5
        L_0x0027:
            java.lang.String r5 = r0.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PEMUtil.b(java.io.InputStream):java.lang.String");
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
        ASN1Primitive readObject = new ASN1InputStream(Base64.decode(stringBuffer.toString())).readObject();
        if (readObject instanceof ASN1Sequence) {
            return (ASN1Sequence) readObject;
        }
        throw new IOException("malformed PEM data encountered");
    }
}
