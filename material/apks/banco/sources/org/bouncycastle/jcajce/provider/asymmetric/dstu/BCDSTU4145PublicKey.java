package org.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.ua.DSTU4145Params;
import org.bouncycastle.asn1.ua.DSTU4145PointEncoder;
import org.bouncycastle.asn1.ua.UAObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class BCDSTU4145PublicKey implements ECPublicKey, ECPointEncoder, org.bouncycastle.jce.interfaces.ECPublicKey {
    static final long serialVersionUID = 7026240464295649314L;
    private String a = "DSTU4145";
    private boolean b;
    private transient ECPoint c;
    private transient ECParameterSpec d;
    private transient DSTU4145Params e;

    public BCDSTU4145PublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters) {
        this.a = str;
        this.c = eCPublicKeyParameters.getQ();
        this.d = null;
    }

    public BCDSTU4145PublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.a = str;
        this.c = eCPublicKeyParameters.getQ();
        if (eCParameterSpec == null) {
            this.d = a(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters);
        } else {
            this.d = eCParameterSpec;
        }
    }

    public BCDSTU4145PublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.a = str;
        this.c = eCPublicKeyParameters.getQ();
        this.d = eCParameterSpec == null ? a(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters) : EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
    }

    public BCDSTU4145PublicKey(ECPublicKey eCPublicKey) {
        this.a = eCPublicKey.getAlgorithm();
        this.d = eCPublicKey.getParams();
        this.c = EC5Util.convertPoint(this.d, eCPublicKey.getW(), false);
    }

    public BCDSTU4145PublicKey(ECPublicKeySpec eCPublicKeySpec) {
        this.d = eCPublicKeySpec.getParams();
        this.c = EC5Util.convertPoint(this.d, eCPublicKeySpec.getW(), false);
    }

    BCDSTU4145PublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        a(subjectPublicKeyInfo);
    }

    public BCDSTU4145PublicKey(BCDSTU4145PublicKey bCDSTU4145PublicKey) {
        this.c = bCDSTU4145PublicKey.c;
        this.d = bCDSTU4145PublicKey.d;
        this.b = bCDSTU4145PublicKey.b;
        this.e = bCDSTU4145PublicKey.e;
    }

    public BCDSTU4145PublicKey(org.bouncycastle.jce.spec.ECPublicKeySpec eCPublicKeySpec) {
        ECParameterSpec eCParameterSpec;
        this.c = eCPublicKeySpec.getQ();
        if (eCPublicKeySpec.getParams() != null) {
            eCParameterSpec = EC5Util.convertSpec(EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed()), eCPublicKeySpec.getParams());
        } else {
            if (this.c.getCurve() == null) {
                this.c = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve().createPoint(this.c.getAffineXCoord().toBigInteger(), this.c.getAffineYCoord().toBigInteger());
            }
            eCParameterSpec = null;
        }
        this.d = eCParameterSpec;
    }

    private ECParameterSpec a(EllipticCurve ellipticCurve, ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(eCDomainParameters.getG().getAffineXCoord().toBigInteger(), eCDomainParameters.getG().getAffineYCoord().toBigInteger()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    /* JADX WARNING: type inference failed for: r13v6, types: [java.security.spec.ECParameterSpec] */
    /* JADX WARNING: type inference failed for: r9v2, types: [org.bouncycastle.jce.spec.ECParameterSpec] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo r13) {
        /*
            r12 = this;
            org.bouncycastle.asn1.DERBitString r0 = r13.getPublicKeyData()
            java.lang.String r1 = "DSTU4145"
            r12.a = r1
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x0151 }
            org.bouncycastle.asn1.ASN1Primitive r0 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r0)     // Catch:{ IOException -> 0x0151 }
            org.bouncycastle.asn1.ASN1OctetString r0 = (org.bouncycastle.asn1.ASN1OctetString) r0     // Catch:{ IOException -> 0x0151 }
            byte[] r0 = r0.getOctets()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r13.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.ua.UAObjectIdentifiers.dstu4145le
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0029
            r12.a(r0)
        L_0x0029:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r13.getAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getParameters()
            org.bouncycastle.asn1.ASN1Sequence r1 = (org.bouncycastle.asn1.ASN1Sequence) r1
            org.bouncycastle.asn1.ua.DSTU4145Params r1 = org.bouncycastle.asn1.ua.DSTU4145Params.getInstance(r1)
            r12.e = r1
            org.bouncycastle.asn1.ua.DSTU4145Params r1 = r12.e
            boolean r1 = r1.isNamedCurve()
            if (r1 == 0) goto L_0x006a
            org.bouncycastle.asn1.ua.DSTU4145Params r13 = r12.e
            org.bouncycastle.asn1.ASN1ObjectIdentifier r13 = r13.getNamedCurve()
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.ua.DSTU4145NamedCurves.getByOID(r13)
            org.bouncycastle.jce.spec.ECNamedCurveParameterSpec r9 = new org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
            java.lang.String r3 = r13.getId()
            org.bouncycastle.math.ec.ECCurve r4 = r1.getCurve()
            org.bouncycastle.math.ec.ECPoint r5 = r1.getG()
            java.math.BigInteger r6 = r1.getN()
            java.math.BigInteger r7 = r1.getH()
            byte[] r8 = r1.getSeed()
            r2 = r9
            r2.<init>(r3, r4, r5, r6, r7, r8)
            goto L_0x00cf
        L_0x006a:
            org.bouncycastle.asn1.ua.DSTU4145Params r1 = r12.e
            org.bouncycastle.asn1.ua.DSTU4145ECBinary r1 = r1.getECBinary()
            byte[] r2 = r1.getB()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r3 = r13.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r3.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = org.bouncycastle.asn1.ua.UAObjectIdentifiers.dstu4145le
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0087
            r12.a(r2)
        L_0x0087:
            org.bouncycastle.asn1.ua.DSTU4145BinaryField r3 = r1.getField()
            org.bouncycastle.math.ec.ECCurve$F2m r11 = new org.bouncycastle.math.ec.ECCurve$F2m
            int r5 = r3.getM()
            int r6 = r3.getK1()
            int r7 = r3.getK2()
            int r8 = r3.getK3()
            java.math.BigInteger r9 = r1.getA()
            java.math.BigInteger r10 = new java.math.BigInteger
            r3 = 1
            r10.<init>(r3, r2)
            r4 = r11
            r4.<init>(r5, r6, r7, r8, r9, r10)
            byte[] r2 = r1.getG()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r13 = r13.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r13 = r13.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.ua.UAObjectIdentifiers.dstu4145le
            boolean r13 = r13.equals(r3)
            if (r13 == 0) goto L_0x00c2
            r12.a(r2)
        L_0x00c2:
            org.bouncycastle.jce.spec.ECParameterSpec r9 = new org.bouncycastle.jce.spec.ECParameterSpec
            org.bouncycastle.math.ec.ECPoint r13 = org.bouncycastle.asn1.ua.DSTU4145PointEncoder.decodePoint(r11, r2)
            java.math.BigInteger r1 = r1.getN()
            r9.<init>(r11, r13, r1)
        L_0x00cf:
            org.bouncycastle.math.ec.ECCurve r13 = r9.getCurve()
            byte[] r1 = r9.getSeed()
            java.security.spec.EllipticCurve r4 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r13, r1)
            org.bouncycastle.math.ec.ECPoint r13 = org.bouncycastle.asn1.ua.DSTU4145PointEncoder.decodePoint(r13, r0)
            r12.c = r13
            org.bouncycastle.asn1.ua.DSTU4145Params r13 = r12.e
            boolean r13 = r13.isNamedCurve()
            if (r13 == 0) goto L_0x0121
            org.bouncycastle.jce.spec.ECNamedCurveSpec r13 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            org.bouncycastle.asn1.ua.DSTU4145Params r0 = r12.e
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r0.getNamedCurve()
            java.lang.String r3 = r0.getId()
            java.security.spec.ECPoint r5 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r0 = r9.getG()
            org.bouncycastle.math.ec.ECFieldElement r0 = r0.getAffineXCoord()
            java.math.BigInteger r0 = r0.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r1 = r9.getG()
            org.bouncycastle.math.ec.ECFieldElement r1 = r1.getAffineYCoord()
            java.math.BigInteger r1 = r1.toBigInteger()
            r5.<init>(r0, r1)
            java.math.BigInteger r6 = r9.getN()
            java.math.BigInteger r7 = r9.getH()
            r2 = r13
            r2.<init>(r3, r4, r5, r6, r7)
        L_0x011e:
            r12.d = r13
            return
        L_0x0121:
            java.security.spec.ECParameterSpec r13 = new java.security.spec.ECParameterSpec
            java.security.spec.ECPoint r0 = new java.security.spec.ECPoint
            org.bouncycastle.math.ec.ECPoint r1 = r9.getG()
            org.bouncycastle.math.ec.ECFieldElement r1 = r1.getAffineXCoord()
            java.math.BigInteger r1 = r1.toBigInteger()
            org.bouncycastle.math.ec.ECPoint r2 = r9.getG()
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getAffineYCoord()
            java.math.BigInteger r2 = r2.toBigInteger()
            r0.<init>(r1, r2)
            java.math.BigInteger r1 = r9.getN()
            java.math.BigInteger r2 = r9.getH()
            int r2 = r2.intValue()
            r13.<init>(r4, r0, r1, r2)
            goto L_0x011e
            return
        L_0x0151:
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "error recovering public key"
            r13.<init>(r0)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.dstu.BCDSTU4145PublicKey.a(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo):void");
    }

    private void a(byte[] bArr) {
        for (int i = 0; i < bArr.length / 2; i++) {
            byte b2 = bArr[i];
            bArr[i] = bArr[(bArr.length - 1) - i];
            bArr[(bArr.length - 1) - i] = b2;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        a(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }

    /* access modifiers changed from: 0000 */
    public org.bouncycastle.jce.spec.ECParameterSpec a() {
        return this.d != null ? EC5Util.convertSpec(this.d, this.b) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public ECPoint engineGetQ() {
        return this.c;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof BCDSTU4145PublicKey)) {
            return false;
        }
        BCDSTU4145PublicKey bCDSTU4145PublicKey = (BCDSTU4145PublicKey) obj;
        if (engineGetQ().equals(bCDSTU4145PublicKey.engineGetQ()) && a().equals(bCDSTU4145PublicKey.a())) {
            z = true;
        }
        return z;
    }

    public String getAlgorithm() {
        return this.a;
    }

    public byte[] getEncoded() {
        ASN1Encodable aSN1Encodable;
        if (this.e != null) {
            aSN1Encodable = this.e;
        } else if (this.d instanceof ECNamedCurveSpec) {
            aSN1Encodable = new DSTU4145Params(new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.d).getName()));
        } else {
            ECCurve convertCurve = EC5Util.convertCurve(this.d.getCurve());
            X9ECParameters x9ECParameters = new X9ECParameters(convertCurve, EC5Util.convertPoint(convertCurve, this.d.getGenerator(), this.b), this.d.getOrder(), BigInteger.valueOf((long) this.d.getCofactor()), this.d.getCurve().getSeed());
            aSN1Encodable = new X962Parameters(x9ECParameters);
        }
        try {
            return KeyUtil.getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(new AlgorithmIdentifier(UAObjectIdentifiers.dstu4145be, aSN1Encodable), (ASN1Encodable) new DEROctetString(DSTU4145PointEncoder.encodePoint(this.c))));
        } catch (IOException unused) {
            return null;
        }
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

    public byte[] getSbox() {
        return this.e != null ? this.e.getDKE() : DSTU4145Params.getDefaultDKE();
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
