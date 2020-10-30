package org.bouncycastle.jcajce.provider.asymmetric.ec;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ECPoint;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class BCECPublicKey implements ECPublicKey, ECPointEncoder, org.bouncycastle.jce.interfaces.ECPublicKey {
    static final long serialVersionUID = 2422789860422731812L;
    private String a = "EC";
    private boolean b;
    private transient ECPoint c;
    private transient ECParameterSpec d;
    private transient ProviderConfiguration e;

    public BCECPublicKey(String str, ECPublicKeySpec eCPublicKeySpec, ProviderConfiguration providerConfiguration) {
        this.a = str;
        this.d = eCPublicKeySpec.getParams();
        this.c = EC5Util.convertPoint(this.d, eCPublicKeySpec.getW(), false);
        this.e = providerConfiguration;
    }

    BCECPublicKey(String str, SubjectPublicKeyInfo subjectPublicKeyInfo, ProviderConfiguration providerConfiguration) {
        this.a = str;
        this.e = providerConfiguration;
        a(subjectPublicKeyInfo);
    }

    public BCECPublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, ECParameterSpec eCParameterSpec, ProviderConfiguration providerConfiguration) {
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.a = str;
        this.c = eCPublicKeyParameters.getQ();
        if (eCParameterSpec == null) {
            this.d = a(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters);
        } else {
            this.d = eCParameterSpec;
        }
        this.e = providerConfiguration;
    }

    public BCECPublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, ProviderConfiguration providerConfiguration) {
        this.a = str;
        this.c = eCPublicKeyParameters.getQ();
        this.d = null;
        this.e = providerConfiguration;
    }

    public BCECPublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec, ProviderConfiguration providerConfiguration) {
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.a = str;
        this.d = eCParameterSpec == null ? a(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters) : EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        this.c = EC5Util.convertCurve(this.d.getCurve()).createPoint(eCPublicKeyParameters.getQ().getAffineXCoord().toBigInteger(), eCPublicKeyParameters.getQ().getAffineYCoord().toBigInteger());
        this.e = providerConfiguration;
    }

    public BCECPublicKey(String str, BCECPublicKey bCECPublicKey) {
        this.a = str;
        this.c = bCECPublicKey.c;
        this.d = bCECPublicKey.d;
        this.b = bCECPublicKey.b;
        this.e = bCECPublicKey.e;
    }

    public BCECPublicKey(String str, org.bouncycastle.jce.spec.ECPublicKeySpec eCPublicKeySpec, ProviderConfiguration providerConfiguration) {
        ECParameterSpec eCParameterSpec;
        this.a = str;
        this.c = eCPublicKeySpec.getQ();
        if (eCPublicKeySpec.getParams() != null) {
            EllipticCurve convertCurve = EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed());
            this.c = EC5Util.convertCurve(convertCurve).createPoint(eCPublicKeySpec.getQ().getAffineXCoord().toBigInteger(), eCPublicKeySpec.getQ().getAffineYCoord().toBigInteger());
            eCParameterSpec = EC5Util.convertSpec(convertCurve, eCPublicKeySpec.getParams());
        } else {
            if (this.c.getCurve() == null) {
                this.c = providerConfiguration.getEcImplicitlyCa().getCurve().createPoint(this.c.getXCoord().toBigInteger(), this.c.getYCoord().toBigInteger(), false);
            }
            eCParameterSpec = null;
        }
        this.d = eCParameterSpec;
        this.e = providerConfiguration;
    }

    public BCECPublicKey(ECPublicKey eCPublicKey, ProviderConfiguration providerConfiguration) {
        this.a = eCPublicKey.getAlgorithm();
        this.d = eCPublicKey.getParams();
        this.c = EC5Util.convertPoint(this.d, eCPublicKey.getW(), false);
    }

    private ECParameterSpec a(EllipticCurve ellipticCurve, ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(eCDomainParameters.getG().getAffineXCoord().toBigInteger(), eCDomainParameters.getG().getAffineYCoord().toBigInteger()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    /* JADX WARNING: type inference failed for: r3v4, types: [java.security.spec.ECParameterSpec] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo r11) {
        /*
            r10 = this;
            org.bouncycastle.asn1.x9.X962Parameters r0 = new org.bouncycastle.asn1.x9.X962Parameters
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r11.getAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getParameters()
            org.bouncycastle.asn1.ASN1Primitive r1 = (org.bouncycastle.asn1.ASN1Primitive) r1
            r0.<init>(r1)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L_0x005d
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r0
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(r0)
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r3 = r1.getSeed()
            java.security.spec.EllipticCurve r6 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r2, r3)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r3 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r5 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getCurveName(r0)
            java.security.spec.ECPoint r7 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r0 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r0 = r0.getAffineXCoord()
            java.math.BigInteger r0 = r0.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r4 = r1.getG()
            org.bouncycastle.math.ec.ECFieldElement r4 = r4.getAffineYCoord()
            java.math.BigInteger r4 = r4.toBigInteger()
            r7.<init>(r0, r4)
            java.math.BigInteger r8 = r1.getN()
            java.math.BigInteger r9 = r1.getH()
            r4 = r3
            r4.<init>(r5, r6, r7, r8, r9)
        L_0x005a:
            r10.d = r3
            goto L_0x00b4
        L_0x005d:
            boolean r1 = r0.isImplicitlyCA()
            if (r1 == 0) goto L_0x0071
            r0 = 0
            r10.d = r0
            org.bouncycastle.jcajce.provider.config.ProviderConfiguration r0 = r10.e
            org.bouncycastle.jce.spec.ECParameterSpec r0 = r0.getEcImplicitlyCa()
            org.bouncycastle.math.ec.ECCurve r2 = r0.getCurve()
            goto L_0x00b4
        L_0x0071:
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.asn1.x9.X9ECParameters.getInstance(r0)
            org.bouncycastle.math.ec.ECCurve r2 = r0.getCurve()
            byte[] r1 = r0.getSeed()
            java.security.spec.EllipticCurve r1 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r2, r1)
            java.security.spec.ECParameterSpec r3 = new java.security.spec.ECParameterSpec
            java.security.spec.ECPoint r4 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r5 = r0.getG()
            org.bouncycastle.math.ec.ECFieldElement r5 = r5.getAffineXCoord()
            java.math.BigInteger r5 = r5.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r6 = r0.getG()
            org.bouncycastle.math.ec.ECFieldElement r6 = r6.getAffineYCoord()
            java.math.BigInteger r6 = r6.toBigInteger()
            r4.<init>(r5, r6)
            java.math.BigInteger r5 = r0.getN()
            java.math.BigInteger r0 = r0.getH()
            int r0 = r0.intValue()
            r3.<init>(r1, r4, r5, r0)
            goto L_0x005a
        L_0x00b4:
            org.bouncycastle.asn1.DERBitString r11 = r11.getPublicKeyData()
            byte[] r11 = r11.getBytes()
            org.bouncycastle.asn1.DEROctetString r0 = new org.bouncycastle.asn1.DEROctetString
            r0.<init>(r11)
            r1 = 0
            byte r1 = r11[r1]
            r3 = 4
            if (r1 != r3) goto L_0x00f5
            r1 = 1
            byte r1 = r11[r1]
            int r3 = r11.length
            r4 = 2
            int r3 = r3 - r4
            if (r1 != r3) goto L_0x00f5
            byte r1 = r11[r4]
            r3 = 3
            if (r1 == r4) goto L_0x00d8
            byte r1 = r11[r4]
            if (r1 != r3) goto L_0x00f5
        L_0x00d8:
            org.bouncycastle.asn1.x9.X9IntegerConverter r1 = new org.bouncycastle.asn1.x9.X9IntegerConverter
            r1.<init>()
            int r1 = r1.getByteLength(r2)
            int r4 = r11.length
            int r4 = r4 - r3
            if (r1 < r4) goto L_0x00f5
            org.bouncycastle.asn1.ASN1Primitive r11 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r11)     // Catch:{ IOException -> 0x00ed }
            r0 = r11
            org.bouncycastle.asn1.ASN1OctetString r0 = (org.bouncycastle.asn1.ASN1OctetString) r0     // Catch:{ IOException -> 0x00ed }
            goto L_0x00f5
        L_0x00ed:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "error recovering public key"
            r11.<init>(r0)
            throw r11
        L_0x00f5:
            org.bouncycastle.asn1.x9.X9ECPoint r11 = new org.bouncycastle.asn1.x9.X9ECPoint
            r11.<init>(r2, r0)
            org.bouncycastle.math.ec.ECPoint r11 = r11.getPoint()
            r10.c = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey.a(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo):void");
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        a(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.e = BouncyCastleProvider.CONFIGURATION;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }

    /* access modifiers changed from: 0000 */
    public org.bouncycastle.jce.spec.ECParameterSpec a() {
        return this.d != null ? EC5Util.convertSpec(this.d, this.b) : this.e.getEcImplicitlyCa();
    }

    public ECPoint engineGetQ() {
        return this.c;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof BCECPublicKey)) {
            return false;
        }
        BCECPublicKey bCECPublicKey = (BCECPublicKey) obj;
        if (engineGetQ().equals(bCECPublicKey.engineGetQ()) && a().equals(bCECPublicKey.a())) {
            z = true;
        }
        return z;
    }

    public String getAlgorithm() {
        return this.a;
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
        ECCurve curve = engineGetQ().getCurve();
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, x962Parameters), ((ASN1OctetString) (this.d == null ? new X9ECPoint(curve.createPoint(getQ().getXCoord().toBigInteger(), getQ().getYCoord().toBigInteger(), this.b)) : new X9ECPoint(curve.createPoint(getQ().getAffineXCoord().toBigInteger(), getQ().getAffineYCoord().toBigInteger(), this.b))).toASN1Primitive()).getOctets()));
    }

    public String getFormat() {
        return "X.509";
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

    public ECPoint getQ() {
        return this.d == null ? this.c.getDetachedPoint() : this.c;
    }

    public java.security.spec.ECPoint getW() {
        return new java.security.spec.ECPoint(this.c.getAffineXCoord().toBigInteger(), this.c.getAffineYCoord().toBigInteger());
    }

    public int hashCode() {
        return engineGetQ().hashCode() ^ a().hashCode();
    }

    public void setPointFormat(String str) {
        this.b = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("EC Public Key");
        stringBuffer.append(property);
        stringBuffer.append("            X: ");
        stringBuffer.append(this.c.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(property);
        stringBuffer.append("            Y: ");
        stringBuffer.append(this.c.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(property);
        return stringBuffer.toString();
    }
}
