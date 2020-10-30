package org.bouncycastle.asn1.eac;

import java.io.UnsupportedEncodingException;

public class CertificateHolderReference {
    private String a;
    private String b;
    private String c;

    public CertificateHolderReference(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    CertificateHolderReference(byte[] bArr) {
        try {
            String str = new String(bArr, "ISO-8859-1");
            this.a = str.substring(0, 2);
            this.b = str.substring(2, str.length() - 5);
            this.c = str.substring(str.length() - 5);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.toString());
        }
    }

    public String getCountryCode() {
        return this.a;
    }

    public byte[] getEncoded() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(this.b);
        sb.append(this.c);
        try {
            return sb.toString().getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.toString());
        }
    }

    public String getHolderMnemonic() {
        return this.b;
    }

    public String getSequenceNumber() {
        return this.c;
    }
}
