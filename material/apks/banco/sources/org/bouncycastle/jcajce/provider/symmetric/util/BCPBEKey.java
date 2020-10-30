package org.bouncycastle.jcajce.provider.symmetric.util;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class BCPBEKey implements PBEKey {
    String a;
    ASN1ObjectIdentifier b;
    int c;
    int d;
    int e;
    int f;
    CipherParameters g;
    PBEKeySpec h;
    boolean i = false;

    public BCPBEKey(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, int i3, int i4, int i5, PBEKeySpec pBEKeySpec, CipherParameters cipherParameters) {
        this.a = str;
        this.b = aSN1ObjectIdentifier;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = i5;
        this.h = pBEKeySpec;
        this.g = cipherParameters;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return this.i;
    }

    public String getAlgorithm() {
        return this.a;
    }

    public byte[] getEncoded() {
        if (this.g == null) {
            return this.c == 2 ? PBEParametersGenerator.PKCS12PasswordToBytes(this.h.getPassword()) : this.c == 5 ? PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(this.h.getPassword()) : PBEParametersGenerator.PKCS5PasswordToBytes(this.h.getPassword());
        }
        return ((KeyParameter) (this.g instanceof ParametersWithIV ? ((ParametersWithIV) this.g).getParameters() : this.g)).getKey();
    }

    public String getFormat() {
        return "RAW";
    }

    public int getIterationCount() {
        return this.h.getIterationCount();
    }

    public int getIvSize() {
        return this.f;
    }

    public ASN1ObjectIdentifier getOID() {
        return this.b;
    }

    public CipherParameters getParam() {
        return this.g;
    }

    public char[] getPassword() {
        return this.h.getPassword();
    }

    public byte[] getSalt() {
        return this.h.getSalt();
    }

    public void setTryWrongPKCS12Zero(boolean z) {
        this.i = z;
    }
}
