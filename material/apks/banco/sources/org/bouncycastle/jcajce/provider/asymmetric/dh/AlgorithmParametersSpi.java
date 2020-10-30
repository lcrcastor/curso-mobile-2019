package org.bouncycastle.jcajce.provider.asymmetric.dh;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.pkcs.DHParameter;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    DHParameterSpec a;

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded() {
        try {
            return new DHParameter(this.a.getP(), this.a.getG(), this.a.getL()).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            throw new RuntimeException("Error encoding DHParameters");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String str) {
        if (isASN1FormatString(str)) {
            return engineGetEncoded();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec engineGetParameterSpec(Class cls) {
        if (cls != null) {
            return localEngineGetParameterSpec(cls);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof DHParameterSpec)) {
            throw new InvalidParameterSpecException("DHParameterSpec required to initialise a Diffie-Hellman algorithm parameters object");
        }
        this.a = (DHParameterSpec) algorithmParameterSpec;
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr) {
        try {
            DHParameter instance = DHParameter.getInstance(bArr);
            this.a = instance.getL() != null ? new DHParameterSpec(instance.getP(), instance.getG(), instance.getL().intValue()) : new DHParameterSpec(instance.getP(), instance.getG());
        } catch (ClassCastException unused) {
            throw new IOException("Not a valid DH Parameter encoding.");
        } catch (ArrayIndexOutOfBoundsException unused2) {
            throw new IOException("Not a valid DH Parameter encoding.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr, String str) {
        if (isASN1FormatString(str)) {
            engineInit(bArr);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown parameter format ");
        sb.append(str);
        throw new IOException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public String engineToString() {
        return "Diffie-Hellman Parameters";
    }

    /* access modifiers changed from: protected */
    public boolean isASN1FormatString(String str) {
        return str == null || str.equals("ASN.1");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
        if (cls == DHParameterSpec.class) {
            return this.a;
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to DH parameters object.");
    }
}
