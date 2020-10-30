package org.bouncycastle.jce.provider;

import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.misc.NetscapeCertType;
import org.bouncycastle.asn1.misc.NetscapeRevocationURL;
import org.bouncycastle.asn1.misc.VerisignCzagExtension;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

public class X509CertificateObject extends X509Certificate implements PKCS12BagAttributeCarrier {
    private Certificate a;
    private BasicConstraints b;
    private boolean[] c;
    private boolean d;
    private int e;
    private PKCS12BagAttributeCarrier f = new PKCS12BagAttributeCarrierImpl();

    public X509CertificateObject(Certificate certificate) {
        this.a = certificate;
        try {
            byte[] a2 = a("2.5.29.19");
            if (a2 != null) {
                this.b = BasicConstraints.getInstance(ASN1Primitive.fromByteArray(a2));
            }
            try {
                byte[] a3 = a("2.5.29.15");
                if (a3 != null) {
                    DERBitString instance = DERBitString.getInstance(ASN1Primitive.fromByteArray(a3));
                    byte[] bytes = instance.getBytes();
                    int length = (bytes.length * 8) - instance.getPadBits();
                    int i = 9;
                    if (length >= 9) {
                        i = length;
                    }
                    this.c = new boolean[i];
                    for (int i2 = 0; i2 != length; i2++) {
                        this.c[i2] = (bytes[i2 / 8] & (128 >>> (i2 % 8))) != 0;
                    }
                    return;
                }
                this.c = null;
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("cannot construct KeyUsage: ");
                sb.append(e2);
                throw new CertificateParsingException(sb.toString());
            }
        } catch (Exception e3) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("cannot construct BasicConstraints: ");
            sb2.append(e3);
            throw new CertificateParsingException(sb2.toString());
        }
    }

    private int a() {
        try {
            byte[] encoded = getEncoded();
            int i = 0;
            for (int i2 = 1; i2 < encoded.length; i2++) {
                i += encoded[i2] * i2;
            }
            return i;
        } catch (CertificateEncodingException unused) {
            return 0;
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.Collection a(byte[] r5) {
        /*
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x00aa }
            r1.<init>()     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.asn1.ASN1Sequence r5 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r5)     // Catch:{ Exception -> 0x00aa }
            java.util.Enumeration r5 = r5.getObjects()     // Catch:{ Exception -> 0x00aa }
        L_0x0011:
            boolean r2 = r5.hasMoreElements()     // Catch:{ Exception -> 0x00aa }
            if (r2 == 0) goto L_0x009e
            java.lang.Object r2 = r5.nextElement()     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.asn1.x509.GeneralName r2 = org.bouncycastle.asn1.x509.GeneralName.getInstance(r2)     // Catch:{ Exception -> 0x00aa }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x00aa }
            r3.<init>()     // Catch:{ Exception -> 0x00aa }
            int r4 = r2.getTagNo()     // Catch:{ Exception -> 0x00aa }
            java.lang.Integer r4 = org.bouncycastle.util.Integers.valueOf(r4)     // Catch:{ Exception -> 0x00aa }
            r3.add(r4)     // Catch:{ Exception -> 0x00aa }
            int r4 = r2.getTagNo()     // Catch:{ Exception -> 0x00aa }
            switch(r4) {
                case 0: goto L_0x0078;
                case 1: goto L_0x006d;
                case 2: goto L_0x006d;
                case 3: goto L_0x0078;
                case 4: goto L_0x005e;
                case 5: goto L_0x0078;
                case 6: goto L_0x006d;
                case 7: goto L_0x0049;
                case 8: goto L_0x0039;
                default: goto L_0x0036;
            }     // Catch:{ Exception -> 0x00aa }
        L_0x0036:
            java.io.IOException r5 = new java.io.IOException     // Catch:{ Exception -> 0x00aa }
            goto L_0x0085
        L_0x0039:
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getName()     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r2)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r2 = r2.getId()     // Catch:{ Exception -> 0x00aa }
        L_0x0045:
            r3.add(r2)     // Catch:{ Exception -> 0x00aa }
            goto L_0x007d
        L_0x0049:
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getName()     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.asn1.ASN1OctetString r2 = org.bouncycastle.asn1.DEROctetString.getInstance(r2)     // Catch:{ Exception -> 0x00aa }
            byte[] r2 = r2.getOctets()     // Catch:{ Exception -> 0x00aa }
            java.net.InetAddress r2 = java.net.InetAddress.getByAddress(r2)     // Catch:{ UnknownHostException -> 0x0011 }
            java.lang.String r2 = r2.getHostAddress()     // Catch:{ UnknownHostException -> 0x0011 }
            goto L_0x0045
        L_0x005e:
            org.bouncycastle.asn1.x500.X500NameStyle r4 = org.bouncycastle.asn1.x500.style.RFC4519Style.INSTANCE     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getName()     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.asn1.x500.X500Name r2 = org.bouncycastle.asn1.x500.X500Name.getInstance(r4, r2)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00aa }
            goto L_0x0045
        L_0x006d:
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getName()     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.asn1.ASN1String r2 = (org.bouncycastle.asn1.ASN1String) r2     // Catch:{ Exception -> 0x00aa }
            java.lang.String r2 = r2.getString()     // Catch:{ Exception -> 0x00aa }
            goto L_0x0045
        L_0x0078:
            byte[] r2 = r2.getEncoded()     // Catch:{ Exception -> 0x00aa }
            goto L_0x0045
        L_0x007d:
            java.util.List r2 = java.util.Collections.unmodifiableList(r3)     // Catch:{ Exception -> 0x00aa }
            r1.add(r2)     // Catch:{ Exception -> 0x00aa }
            goto L_0x0011
        L_0x0085:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00aa }
            r0.<init>()     // Catch:{ Exception -> 0x00aa }
            java.lang.String r1 = "Bad tag number: "
            r0.append(r1)     // Catch:{ Exception -> 0x00aa }
            int r1 = r2.getTagNo()     // Catch:{ Exception -> 0x00aa }
            r0.append(r1)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00aa }
            r5.<init>(r0)     // Catch:{ Exception -> 0x00aa }
            throw r5     // Catch:{ Exception -> 0x00aa }
        L_0x009e:
            int r5 = r1.size()     // Catch:{ Exception -> 0x00aa }
            if (r5 != 0) goto L_0x00a5
            return r0
        L_0x00a5:
            java.util.Collection r5 = java.util.Collections.unmodifiableCollection(r1)     // Catch:{ Exception -> 0x00aa }
            return r5
        L_0x00aa:
            r5 = move-exception
            java.security.cert.CertificateParsingException r0 = new java.security.cert.CertificateParsingException
            java.lang.String r5 = r5.getMessage()
            r0.<init>(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.X509CertificateObject.a(byte[]):java.util.Collection");
    }

    private void a(PublicKey publicKey, Signature signature) {
        if (!a(this.a.getSignatureAlgorithm(), this.a.getTBSCertificate().getSignature())) {
            throw new CertificateException("signature algorithm in TBS cert not same as outer cert");
        }
        X509SignatureUtil.a(signature, this.a.getSignatureAlgorithm().getParameters());
        signature.initVerify(publicKey);
        signature.update(getTBSCertificate());
        if (!signature.verify(getSignature())) {
            throw new SignatureException("certificate does not verify with supplied key");
        }
    }

    private boolean a(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        if (!algorithmIdentifier.getAlgorithm().equals(algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        return algorithmIdentifier.getParameters() == null ? algorithmIdentifier2.getParameters() == null || algorithmIdentifier2.getParameters().equals(DERNull.INSTANCE) : algorithmIdentifier2.getParameters() == null ? algorithmIdentifier.getParameters() == null || algorithmIdentifier.getParameters().equals(DERNull.INSTANCE) : algorithmIdentifier.getParameters().equals(algorithmIdentifier2.getParameters());
    }

    private byte[] a(String str) {
        Extensions extensions = this.a.getTBSCertificate().getExtensions();
        if (extensions != null) {
            Extension extension = extensions.getExtension(new ASN1ObjectIdentifier(str));
            if (extension != null) {
                return extension.getExtnValue().getOctets();
            }
        }
        return null;
    }

    public void checkValidity() {
        checkValidity(new Date());
    }

    public void checkValidity(Date date) {
        if (date.getTime() > getNotAfter().getTime()) {
            StringBuilder sb = new StringBuilder();
            sb.append("certificate expired on ");
            sb.append(this.a.getEndDate().getTime());
            throw new CertificateExpiredException(sb.toString());
        } else if (date.getTime() < getNotBefore().getTime()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("certificate not valid till ");
            sb2.append(this.a.getStartDate().getTime());
            throw new CertificateNotYetValidException(sb2.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof java.security.cert.Certificate)) {
            return false;
        }
        try {
            return Arrays.areEqual(getEncoded(), ((java.security.cert.Certificate) obj).getEncoded());
        } catch (CertificateEncodingException unused) {
            return false;
        }
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.f.getBagAttribute(aSN1ObjectIdentifier);
    }

    public Enumeration getBagAttributeKeys() {
        return this.f.getBagAttributeKeys();
    }

    public int getBasicConstraints() {
        if (this.b == null || !this.b.isCA()) {
            return -1;
        }
        return this.b.getPathLenConstraint() == null ? SubsamplingScaleImageView.TILE_SIZE_AUTO : this.b.getPathLenConstraint().intValue();
    }

    public Set getCriticalExtensionOIDs() {
        if (getVersion() == 3) {
            HashSet hashSet = new HashSet();
            Extensions extensions = this.a.getTBSCertificate().getExtensions();
            if (extensions != null) {
                Enumeration oids = extensions.oids();
                while (oids.hasMoreElements()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
                    if (extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                        hashSet.add(aSN1ObjectIdentifier.getId());
                    }
                }
                return hashSet;
            }
        }
        return null;
    }

    public byte[] getEncoded() {
        try {
            return this.a.getEncoded(ASN1Encoding.DER);
        } catch (IOException e2) {
            throw new CertificateEncodingException(e2.toString());
        }
    }

    public List getExtendedKeyUsage() {
        byte[] a2 = a("2.5.29.37");
        if (a2 == null) {
            return null;
        }
        try {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) new ASN1InputStream(a2).readObject();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i != aSN1Sequence.size(); i++) {
                arrayList.add(((ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(i)).getId());
            }
            return Collections.unmodifiableList(arrayList);
        } catch (Exception unused) {
            throw new CertificateParsingException("error processing extended key usage extension");
        }
    }

    public byte[] getExtensionValue(String str) {
        Extensions extensions = this.a.getTBSCertificate().getExtensions();
        if (extensions != null) {
            Extension extension = extensions.getExtension(new ASN1ObjectIdentifier(str));
            if (extension != null) {
                try {
                    return extension.getExtnValue().getEncoded();
                } catch (Exception e2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("error parsing ");
                    sb.append(e2.toString());
                    throw new IllegalStateException(sb.toString());
                }
            }
        }
        return null;
    }

    public Collection getIssuerAlternativeNames() {
        return a(a(Extension.issuerAlternativeName.getId()));
    }

    public Principal getIssuerDN() {
        try {
            return new X509Principal(X500Name.getInstance(this.a.getIssuer().getEncoded()));
        } catch (IOException unused) {
            return null;
        }
    }

    public boolean[] getIssuerUniqueID() {
        DERBitString issuerUniqueId = this.a.getTBSCertificate().getIssuerUniqueId();
        if (issuerUniqueId == null) {
            return null;
        }
        byte[] bytes = issuerUniqueId.getBytes();
        boolean[] zArr = new boolean[((bytes.length * 8) - issuerUniqueId.getPadBits())];
        for (int i = 0; i != zArr.length; i++) {
            zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
        }
        return zArr;
    }

    public X500Principal getIssuerX500Principal() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ASN1OutputStream(byteArrayOutputStream).writeObject(this.a.getIssuer());
            return new X500Principal(byteArrayOutputStream.toByteArray());
        } catch (IOException unused) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    public boolean[] getKeyUsage() {
        return this.c;
    }

    public Set getNonCriticalExtensionOIDs() {
        if (getVersion() == 3) {
            HashSet hashSet = new HashSet();
            Extensions extensions = this.a.getTBSCertificate().getExtensions();
            if (extensions != null) {
                Enumeration oids = extensions.oids();
                while (oids.hasMoreElements()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
                    if (!extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                        hashSet.add(aSN1ObjectIdentifier.getId());
                    }
                }
                return hashSet;
            }
        }
        return null;
    }

    public Date getNotAfter() {
        return this.a.getEndDate().getDate();
    }

    public Date getNotBefore() {
        return this.a.getStartDate().getDate();
    }

    public PublicKey getPublicKey() {
        try {
            return BouncyCastleProvider.getPublicKey(this.a.getSubjectPublicKeyInfo());
        } catch (IOException unused) {
            return null;
        }
    }

    public BigInteger getSerialNumber() {
        return this.a.getSerialNumber().getValue();
    }

    public String getSigAlgName() {
        Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Alg.Alias.Signature.");
            sb.append(getSigAlgOID());
            String property = provider.getProperty(sb.toString());
            if (property != null) {
                return property;
            }
        }
        Provider[] providers = Security.getProviders();
        for (int i = 0; i != providers.length; i++) {
            Provider provider2 = providers[i];
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.Signature.");
            sb2.append(getSigAlgOID());
            String property2 = provider2.getProperty(sb2.toString());
            if (property2 != null) {
                return property2;
            }
        }
        return getSigAlgOID();
    }

    public String getSigAlgOID() {
        return this.a.getSignatureAlgorithm().getAlgorithm().getId();
    }

    public byte[] getSigAlgParams() {
        if (this.a.getSignatureAlgorithm().getParameters() != null) {
            try {
                return this.a.getSignatureAlgorithm().getParameters().toASN1Primitive().getEncoded(ASN1Encoding.DER);
            } catch (IOException unused) {
            }
        }
        return null;
    }

    public byte[] getSignature() {
        return this.a.getSignature().getBytes();
    }

    public Collection getSubjectAlternativeNames() {
        return a(a(Extension.subjectAlternativeName.getId()));
    }

    public Principal getSubjectDN() {
        return new X509Principal(X500Name.getInstance(this.a.getSubject().toASN1Primitive()));
    }

    public boolean[] getSubjectUniqueID() {
        DERBitString subjectUniqueId = this.a.getTBSCertificate().getSubjectUniqueId();
        if (subjectUniqueId == null) {
            return null;
        }
        byte[] bytes = subjectUniqueId.getBytes();
        boolean[] zArr = new boolean[((bytes.length * 8) - subjectUniqueId.getPadBits())];
        for (int i = 0; i != zArr.length; i++) {
            zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
        }
        return zArr;
    }

    public X500Principal getSubjectX500Principal() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ASN1OutputStream(byteArrayOutputStream).writeObject(this.a.getSubject());
            return new X500Principal(byteArrayOutputStream.toByteArray());
        } catch (IOException unused) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    public byte[] getTBSCertificate() {
        try {
            return this.a.getTBSCertificate().getEncoded(ASN1Encoding.DER);
        } catch (IOException e2) {
            throw new CertificateEncodingException(e2.toString());
        }
    }

    public int getVersion() {
        return this.a.getVersionNumber();
    }

    public boolean hasUnsupportedCriticalExtension() {
        if (getVersion() == 3) {
            Extensions extensions = this.a.getTBSCertificate().getExtensions();
            if (extensions != null) {
                Enumeration oids = extensions.oids();
                while (oids.hasMoreElements()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
                    String id2 = aSN1ObjectIdentifier.getId();
                    if (!id2.equals(RFC3280CertPathUtilities.KEY_USAGE) && !id2.equals(RFC3280CertPathUtilities.CERTIFICATE_POLICIES) && !id2.equals(RFC3280CertPathUtilities.POLICY_MAPPINGS) && !id2.equals(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY) && !id2.equals(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS) && !id2.equals(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT) && !id2.equals(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR) && !id2.equals(RFC3280CertPathUtilities.POLICY_CONSTRAINTS) && !id2.equals(RFC3280CertPathUtilities.BASIC_CONSTRAINTS) && !id2.equals(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME) && !id2.equals(RFC3280CertPathUtilities.NAME_CONSTRAINTS) && extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public synchronized int hashCode() {
        if (!this.d) {
            this.e = a();
            this.d = true;
        }
        return this.e;
    }

    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.f.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    public String toString() {
        Object verisignCzagExtension;
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("  [0]         Version: ");
        stringBuffer.append(getVersion());
        stringBuffer.append(property);
        stringBuffer.append("         SerialNumber: ");
        stringBuffer.append(getSerialNumber());
        stringBuffer.append(property);
        stringBuffer.append("             IssuerDN: ");
        stringBuffer.append(getIssuerDN());
        stringBuffer.append(property);
        stringBuffer.append("           Start Date: ");
        stringBuffer.append(getNotBefore());
        stringBuffer.append(property);
        stringBuffer.append("           Final Date: ");
        stringBuffer.append(getNotAfter());
        stringBuffer.append(property);
        stringBuffer.append("            SubjectDN: ");
        stringBuffer.append(getSubjectDN());
        stringBuffer.append(property);
        stringBuffer.append("           Public Key: ");
        stringBuffer.append(getPublicKey());
        stringBuffer.append(property);
        stringBuffer.append("  Signature Algorithm: ");
        stringBuffer.append(getSigAlgName());
        stringBuffer.append(property);
        byte[] signature = getSignature();
        stringBuffer.append("            Signature: ");
        stringBuffer.append(new String(Hex.encode(signature, 0, 20)));
        stringBuffer.append(property);
        for (int i = 20; i < signature.length; i += 20) {
            if (i < signature.length - 20) {
                stringBuffer.append("                       ");
                str = new String(Hex.encode(signature, i, 20));
            } else {
                stringBuffer.append("                       ");
                str = new String(Hex.encode(signature, i, signature.length - i));
            }
            stringBuffer.append(str);
            stringBuffer.append(property);
        }
        Extensions extensions = this.a.getTBSCertificate().getExtensions();
        if (extensions != null) {
            Enumeration oids = extensions.oids();
            if (oids.hasMoreElements()) {
                stringBuffer.append("       Extensions: \n");
            }
            while (oids.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
                Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                if (extension.getExtnValue() != null) {
                    ASN1InputStream aSN1InputStream = new ASN1InputStream(extension.getExtnValue().getOctets());
                    stringBuffer.append("                       critical(");
                    stringBuffer.append(extension.isCritical());
                    stringBuffer.append(") ");
                    try {
                        if (aSN1ObjectIdentifier.equals(Extension.basicConstraints)) {
                            verisignCzagExtension = BasicConstraints.getInstance(aSN1InputStream.readObject());
                        } else if (aSN1ObjectIdentifier.equals(Extension.keyUsage)) {
                            verisignCzagExtension = KeyUsage.getInstance(aSN1InputStream.readObject());
                        } else if (aSN1ObjectIdentifier.equals(MiscObjectIdentifiers.netscapeCertType)) {
                            verisignCzagExtension = new NetscapeCertType((DERBitString) aSN1InputStream.readObject());
                        } else if (aSN1ObjectIdentifier.equals(MiscObjectIdentifiers.netscapeRevocationURL)) {
                            verisignCzagExtension = new NetscapeRevocationURL((DERIA5String) aSN1InputStream.readObject());
                        } else if (aSN1ObjectIdentifier.equals(MiscObjectIdentifiers.verisignCzagExtension)) {
                            verisignCzagExtension = new VerisignCzagExtension((DERIA5String) aSN1InputStream.readObject());
                        } else {
                            stringBuffer.append(aSN1ObjectIdentifier.getId());
                            stringBuffer.append(" value = ");
                            stringBuffer.append(ASN1Dump.dumpAsString(aSN1InputStream.readObject()));
                            stringBuffer.append(property);
                        }
                        stringBuffer.append(verisignCzagExtension);
                        stringBuffer.append(property);
                    } catch (Exception unused) {
                        stringBuffer.append(aSN1ObjectIdentifier.getId());
                        stringBuffer.append(" value = ");
                        stringBuffer.append(PagoTarjetasConstants.ARS_AND_USD_FORMATTED_AMOUNTS_ASTERISK);
                    }
                }
                stringBuffer.append(property);
            }
        }
        return stringBuffer.toString();
    }

    public final void verify(PublicKey publicKey) {
        Signature signature;
        String a2 = X509SignatureUtil.a(this.a.getSignatureAlgorithm());
        try {
            signature = Signature.getInstance(a2, BouncyCastleProvider.PROVIDER_NAME);
        } catch (Exception unused) {
            signature = Signature.getInstance(a2);
        }
        a(publicKey, signature);
    }

    public final void verify(PublicKey publicKey, String str) {
        a(publicKey, Signature.getInstance(X509SignatureUtil.a(this.a.getSignatureAlgorithm()), str));
    }
}
