package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.util.Arrays;

public class GOST28147ParameterSpec implements AlgorithmParameterSpec {
    private static Map c = new HashMap();
    private byte[] a;
    private byte[] b;

    static {
        c.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_A_ParamSet, "E-A");
        c.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_B_ParamSet, "E-B");
        c.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_C_ParamSet, "E-C");
        c.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_D_ParamSet, "E-D");
    }

    public GOST28147ParameterSpec(String str) {
        this.a = null;
        this.b = null;
        this.b = GOST28147Engine.getSBox(str);
    }

    public GOST28147ParameterSpec(String str, byte[] bArr) {
        this(str);
        this.a = new byte[bArr.length];
        System.arraycopy(bArr, 0, this.a, 0, bArr.length);
    }

    public GOST28147ParameterSpec(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        this(a(aSN1ObjectIdentifier));
        this.a = Arrays.clone(bArr);
    }

    public GOST28147ParameterSpec(byte[] bArr) {
        this.a = null;
        this.b = null;
        this.b = new byte[bArr.length];
        System.arraycopy(bArr, 0, this.b, 0, bArr.length);
    }

    public GOST28147ParameterSpec(byte[] bArr, byte[] bArr2) {
        this(bArr);
        this.a = new byte[bArr2.length];
        System.arraycopy(bArr2, 0, this.a, 0, bArr2.length);
    }

    private static String a(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) c.get(aSN1ObjectIdentifier);
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown OID: ");
        sb.append(aSN1ObjectIdentifier);
        throw new IllegalArgumentException(sb.toString());
    }

    public byte[] getIV() {
        if (this.a == null) {
            return null;
        }
        byte[] bArr = new byte[this.a.length];
        System.arraycopy(this.a, 0, bArr, 0, bArr.length);
        return bArr;
    }

    public byte[] getSbox() {
        return this.b;
    }
}
