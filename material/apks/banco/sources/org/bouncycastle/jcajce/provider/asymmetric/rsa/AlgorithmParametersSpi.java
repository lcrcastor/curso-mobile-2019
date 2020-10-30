package org.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.provider.util.DigestFactory;

public abstract class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {

    public static class OAEP extends AlgorithmParametersSpi {
        OAEPParameterSpec a;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            try {
                return new RSAESOAEPparams(new AlgorithmIdentifier(DigestFactory.getOID(this.a.getDigestAlgorithm()), DERNull.INSTANCE), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(((MGF1ParameterSpec) this.a.getMGFParameters()).getDigestAlgorithm()), DERNull.INSTANCE)), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(((PSpecified) this.a.getPSource()).getValue()))).getEncoded(ASN1Encoding.DER);
            } catch (IOException unused) {
                throw new RuntimeException("Error encoding OAEPParameters");
            }
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
                return engineGetEncoded();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (!(algorithmParameterSpec instanceof OAEPParameterSpec)) {
                throw new InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
            }
            this.a = (OAEPParameterSpec) algorithmParameterSpec;
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr) {
            try {
                RSAESOAEPparams instance = RSAESOAEPparams.getInstance(bArr);
                this.a = new OAEPParameterSpec(instance.getHashAlgorithm().getAlgorithm().getId(), instance.getMaskGenAlgorithm().getAlgorithm().getId(), new MGF1ParameterSpec(AlgorithmIdentifier.getInstance(instance.getMaskGenAlgorithm().getParameters()).getAlgorithm().getId()), new PSpecified(ASN1OctetString.getInstance(instance.getPSourceAlgorithm().getParameters()).getOctets()));
            } catch (ClassCastException unused) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            } catch (ArrayIndexOutOfBoundsException unused2) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr, String str) {
            if (str.equalsIgnoreCase("X.509") || str.equalsIgnoreCase("ASN.1")) {
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
            return "OAEP Parameters";
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == OAEPParameterSpec.class && this.a != null) {
                return this.a;
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
        }
    }

    public static class PSS extends AlgorithmParametersSpi {
        PSSParameterSpec a;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            PSSParameterSpec pSSParameterSpec = this.a;
            return new RSASSAPSSparams(new AlgorithmIdentifier(DigestFactory.getOID(pSSParameterSpec.getDigestAlgorithm()), DERNull.INSTANCE), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(((MGF1ParameterSpec) pSSParameterSpec.getMGFParameters()).getDigestAlgorithm()), DERNull.INSTANCE)), new ASN1Integer((long) pSSParameterSpec.getSaltLength()), new ASN1Integer((long) pSSParameterSpec.getTrailerField())).getEncoded(ASN1Encoding.DER);
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String str) {
            if (str.equalsIgnoreCase("X.509") || str.equalsIgnoreCase("ASN.1")) {
                return engineGetEncoded();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (!(algorithmParameterSpec instanceof PSSParameterSpec)) {
                throw new InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
            }
            this.a = (PSSParameterSpec) algorithmParameterSpec;
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr) {
            try {
                RSASSAPSSparams instance = RSASSAPSSparams.getInstance(bArr);
                PSSParameterSpec pSSParameterSpec = new PSSParameterSpec(instance.getHashAlgorithm().getAlgorithm().getId(), instance.getMaskGenAlgorithm().getAlgorithm().getId(), new MGF1ParameterSpec(AlgorithmIdentifier.getInstance(instance.getMaskGenAlgorithm().getParameters()).getAlgorithm().getId()), instance.getSaltLength().intValue(), instance.getTrailerField().intValue());
                this.a = pSSParameterSpec;
            } catch (ClassCastException unused) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            } catch (ArrayIndexOutOfBoundsException unused2) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr, String str) {
            if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
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
            return "PSS Parameters";
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == PSSParameterSpec.class && this.a != null) {
                return this.a;
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
        }
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec engineGetParameterSpec(Class cls) {
        if (cls != null) {
            return localEngineGetParameterSpec(cls);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    /* access modifiers changed from: protected */
    public boolean isASN1FormatString(String str) {
        return str == null || str.equals("ASN.1");
    }

    /* access modifiers changed from: protected */
    public abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class cls);
}
