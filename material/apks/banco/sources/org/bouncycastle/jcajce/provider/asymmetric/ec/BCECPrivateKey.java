package org.bouncycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;

public class BCECPrivateKey implements ECPrivateKey, ECPointEncoder, org.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 994553197664784084L;
    private String a = "EC";
    private boolean b;
    private transient BigInteger c;
    private transient ECParameterSpec d;
    private transient ProviderConfiguration e;
    private transient DERBitString f;
    private transient PKCS12BagAttributeCarrierImpl g = new PKCS12BagAttributeCarrierImpl();

    protected BCECPrivateKey() {
    }

    public BCECPrivateKey(String str, ECPrivateKeySpec eCPrivateKeySpec, ProviderConfiguration providerConfiguration) {
        this.a = str;
        this.c = eCPrivateKeySpec.getS();
        this.d = eCPrivateKeySpec.getParams();
        this.e = providerConfiguration;
    }

    BCECPrivateKey(String str, PrivateKeyInfo privateKeyInfo, ProviderConfiguration providerConfiguration) {
        this.a = str;
        this.e = providerConfiguration;
        a(privateKeyInfo);
    }

    public BCECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCECPublicKey bCECPublicKey, ECParameterSpec eCParameterSpec, ProviderConfiguration providerConfiguration) {
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.a = str;
        this.c = eCPrivateKeyParameters.getD();
        this.e = providerConfiguration;
        if (eCParameterSpec == null) {
            this.d = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), new ECPoint(parameters.getG().getAffineXCoord().toBigInteger(), parameters.getG().getAffineYCoord().toBigInteger()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.d = eCParameterSpec;
        }
        this.f = a(bCECPublicKey);
    }

    public BCECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCECPublicKey bCECPublicKey, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec, ProviderConfiguration providerConfiguration) {
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.a = str;
        this.c = eCPrivateKeyParameters.getD();
        this.e = providerConfiguration;
        if (eCParameterSpec == null) {
            this.d = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), new ECPoint(parameters.getG().getAffineXCoord().toBigInteger(), parameters.getG().getAffineYCoord().toBigInteger()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.d = EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        }
        this.f = a(bCECPublicKey);
    }

    public BCECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, ProviderConfiguration providerConfiguration) {
        this.a = str;
        this.c = eCPrivateKeyParameters.getD();
        this.d = null;
        this.e = providerConfiguration;
    }

    public BCECPrivateKey(String str, BCECPrivateKey bCECPrivateKey) {
        this.a = str;
        this.c = bCECPrivateKey.c;
        this.d = bCECPrivateKey.d;
        this.b = bCECPrivateKey.b;
        this.g = bCECPrivateKey.g;
        this.f = bCECPrivateKey.f;
        this.e = bCECPrivateKey.e;
    }

    public BCECPrivateKey(String str, org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec, ProviderConfiguration providerConfiguration) {
        this.a = str;
        this.c = eCPrivateKeySpec.getD();
        this.d = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
        this.e = providerConfiguration;
    }

    public BCECPrivateKey(ECPrivateKey eCPrivateKey, ProviderConfiguration providerConfiguration) {
        this.c = eCPrivateKey.getS();
        this.a = eCPrivateKey.getAlgorithm();
        this.d = eCPrivateKey.getParams();
        this.e = providerConfiguration;
    }

    private DERBitString a(BCECPublicKey bCECPublicKey) {
        try {
            return SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(bCECPublicKey.getEncoded())).getPublicKeyData();
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [java.security.spec.ECParameterSpec] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x00bc  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(org.bouncycastle.asn1.pkcs.PrivateKeyInfo r11) {
        /*
            r10 = this;
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X962Parameters r0 = org.bouncycastle.asn1.x9.X962Parameters.getInstance(r0)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L_0x005c
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(r0)
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r3 = r1.getSeed()
            java.security.spec.EllipticCurve r6 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r2, r3)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r2 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r5 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getCurveName(r0)
            java.security.spec.ECPoint r7 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r0 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r0 = r0.getAffineXCoord()
            java.math.BigInteger r0 = r0.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r3 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r3 = r3.getAffineYCoord()
            java.math.BigInteger r3 = r3.toBigInteger()
            r7.<init>(r0, r3)
            java.math.BigInteger r8 = r1.getN()
            java.math.BigInteger r9 = r1.getH()
            r4 = r2
            r4.<init>(r5, r6, r7, r8, r9)
        L_0x0059:
            r10.d = r2
            goto L_0x00a9
        L_0x005c:
            boolean r1 = r0.isImplicitlyCA()
            if (r1 == 0) goto L_0x0066
            r0 = 0
            r10.d = r0
            goto L_0x00a9
        L_0x0066:
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.asn1.x9.X9ECParameters.getInstance(r0)
            org.bouncycastle.math.ec.ECCurve r1 = r0.getCurve()
            byte[] r2 = r0.getSeed()
            java.security.spec.EllipticCurve r1 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r1, r2)
            java.security.spec.ECParameterSpec r2 = new java.security.spec.ECParameterSpec
            java.security.spec.ECPoint r3 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r4 = r0.getG()
            org.bouncycastle.math.ec.ECFieldElement r4 = r4.getAffineXCoord()
            java.math.BigInteger r4 = r4.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r5 = r0.getG()
            org.bouncycastle.math.ec.ECFieldElement r5 = r5.getAffineYCoord()
            java.math.BigInteger r5 = r5.toBigInteger()
            r3.<init>(r4, r5)
            java.math.BigInteger r4 = r0.getN()
            java.math.BigInteger r0 = r0.getH()
            int r0 = r0.intValue()
            r2.<init>(r1, r3, r4, r0)
            goto L_0x0059
        L_0x00a9:
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            boolean r0 = r11 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r0 == 0) goto L_0x00bc
            org.bouncycastle.asn1.ASN1Integer r11 = org.bouncycastle.asn1.ASN1Integer.getInstance(r11)
            java.math.BigInteger r11 = r11.getValue()
            r10.c = r11
            return
        L_0x00bc:
            org.bouncycastle.asn1.sec.ECPrivateKey r11 = org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(r11)
            java.math.BigInteger r0 = r11.getKey()
            r10.c = r0
            org.bouncycastle.asn1.DERBitString r11 = r11.getPublicKey()
            r10.f = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey.a(org.bouncycastle.asn1.pkcs.PrivateKeyInfo):void");
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        a(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.e = BouncyCastleProvider.CONFIGURATION;
        this.g = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }

    /* access modifiers changed from: 0000 */
    public org.bouncycastle.jce.spec.ECParameterSpec a() {
        return this.d != null ? EC5Util.convertSpec(this.d, this.b) : this.e.getEcImplicitlyCa();
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof BCECPrivateKey)) {
            return false;
        }
        BCECPrivateKey bCECPrivateKey = (BCECPrivateKey) obj;
        if (getD().equals(bCECPrivateKey.getD()) && a().equals(bCECPrivateKey.a())) {
            z = true;
        }
        return z;
    }

    public String getAlgorithm() {
        return this.a;
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.g.getBagAttribute(aSN1ObjectIdentifier);
    }

    public Enumeration getBagAttributeKeys() {
        return this.g.getBagAttributeKeys();
    }

    public BigInteger getD() {
        return this.c;
    }

    public byte[] getEncoded() {
        X962Parameters x962Parameters;
        if (this.d instanceof ECNamedCurveSpec) {
            ASN1ObjectIdentifier namedCurveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec) this.d).getName());
            if (namedCurveOid == null) {
                namedCurveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.d).getName());
            }
            x962Parameters = new X962Parameters(namedCurveOid);
        } else if (this.d == null) {
            x962Parameters = new X962Parameters((ASN1Primitive) DERNull.INSTANCE);
        } else {
            ECCurve convertCurve = EC5Util.convertCurve(this.d.getCurve());
            X9ECParameters x9ECParameters = new X9ECParameters(convertCurve, EC5Util.convertPoint(convertCurve, this.d.getGenerator(), this.b), this.d.getOrder(), BigInteger.valueOf((long) this.d.getCofactor()), this.d.getCurve().getSeed());
            x962Parameters = new X962Parameters(x9ECParameters);
        }
        try {
            return new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, x962Parameters), this.f != null ? new org.bouncycastle.asn1.sec.ECPrivateKey(getS(), this.f, x962Parameters) : new org.bouncycastle.asn1.sec.ECPrivateKey(getS(), x962Parameters)).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            return null;
        }
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public org.bouncycastle.jce.spec.ECParameterSpec getParameters() {
        if (this.d == null) {
            return null;
        }
        return EC5Util.convertSpec(this.d, this.b);
    }

    public ECParameterSpec getParams() {
        return this.d;
    }

    public BigInteger getS() {
        return this.c;
    }

    public int hashCode() {
        return getD().hashCode() ^ a().hashCode();
    }

    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.g.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    public void setPointFormat(String str) {
        this.b = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("EC Private Key");
        stringBuffer.append(property);
        stringBuffer.append("             S: ");
        stringBuffer.append(this.c.toString(16));
        stringBuffer.append(property);
        return stringBuffer.toString();
    }
}
