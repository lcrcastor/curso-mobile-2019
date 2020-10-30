package org.bouncycastle.jcajce.provider.asymmetric.x509;

import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.TBSCertList.CRLEntry;
import org.bouncycastle.asn1.x509.X509Extension;

public class X509CRLEntryObject extends X509CRLEntry {
    private CRLEntry a;
    private X500Name b;
    private int c;
    private boolean d;

    protected X509CRLEntryObject(CRLEntry cRLEntry) {
        this.a = cRLEntry;
        this.b = null;
    }

    protected X509CRLEntryObject(CRLEntry cRLEntry, boolean z, X500Name x500Name) {
        this.a = cRLEntry;
        this.b = a(z, x500Name);
    }

    private Set a(boolean z) {
        Extensions extensions = this.a.getExtensions();
        if (extensions == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Enumeration oids = extensions.oids();
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
            if (z == extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                hashSet.add(aSN1ObjectIdentifier.getId());
            }
        }
        return hashSet;
    }

    private X500Name a(boolean z, X500Name x500Name) {
        if (!z) {
            return null;
        }
        Extension a2 = a(Extension.certificateIssuer);
        if (a2 == null) {
            return x500Name;
        }
        try {
            GeneralName[] names = GeneralNames.getInstance(a2.getParsedValue()).getNames();
            for (int i = 0; i < names.length; i++) {
                if (names[i].getTagNo() == 4) {
                    return X500Name.getInstance(names[i].getName());
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    private Extension a(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extensions extensions = this.a.getExtensions();
        if (extensions != null) {
            return extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509CRLEntryObject)) {
            return super.equals(this);
        }
        return this.a.equals(((X509CRLEntryObject) obj).a);
    }

    public X500Principal getCertificateIssuer() {
        if (this.b == null) {
            return null;
        }
        try {
            return new X500Principal(this.b.getEncoded());
        } catch (IOException unused) {
            return null;
        }
    }

    public Set getCriticalExtensionOIDs() {
        return a(true);
    }

    public byte[] getEncoded() {
        try {
            return this.a.getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new CRLException(e.toString());
        }
    }

    public byte[] getExtensionValue(String str) {
        Extension a2 = a(new ASN1ObjectIdentifier(str));
        if (a2 == null) {
            return null;
        }
        try {
            return a2.getExtnValue().getEncoded();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("error encoding ");
            sb.append(e.toString());
            throw new RuntimeException(sb.toString());
        }
    }

    public Set getNonCriticalExtensionOIDs() {
        return a(false);
    }

    public Date getRevocationDate() {
        return this.a.getRevocationDate().getDate();
    }

    public BigInteger getSerialNumber() {
        return this.a.getUserCertificate().getValue();
    }

    public boolean hasExtensions() {
        return this.a.getExtensions() != null;
    }

    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return criticalExtensionOIDs != null && !criticalExtensionOIDs.isEmpty();
    }

    public int hashCode() {
        if (!this.d) {
            this.c = super.hashCode();
            this.d = true;
        }
        return this.c;
    }

    public String toString() {
        Object instance;
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("      userCertificate: ");
        stringBuffer.append(getSerialNumber());
        stringBuffer.append(property);
        stringBuffer.append("       revocationDate: ");
        stringBuffer.append(getRevocationDate());
        stringBuffer.append(property);
        stringBuffer.append("       certificateIssuer: ");
        stringBuffer.append(getCertificateIssuer());
        stringBuffer.append(property);
        Extensions extensions = this.a.getExtensions();
        if (extensions != null) {
            Enumeration oids = extensions.oids();
            if (oids.hasMoreElements()) {
                String str = "   crlEntryExtensions:";
                loop0:
                while (true) {
                    stringBuffer.append(str);
                    while (true) {
                        stringBuffer.append(property);
                        while (true) {
                            if (!oids.hasMoreElements()) {
                                break loop0;
                            }
                            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
                            Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                            if (extension.getExtnValue() != null) {
                                ASN1InputStream aSN1InputStream = new ASN1InputStream(extension.getExtnValue().getOctets());
                                stringBuffer.append("                       critical(");
                                stringBuffer.append(extension.isCritical());
                                stringBuffer.append(") ");
                                try {
                                    if (aSN1ObjectIdentifier.equals(X509Extension.reasonCode)) {
                                        instance = CRLReason.getInstance(ASN1Enumerated.getInstance(aSN1InputStream.readObject()));
                                    } else if (aSN1ObjectIdentifier.equals(X509Extension.certificateIssuer)) {
                                        stringBuffer.append("Certificate issuer: ");
                                        instance = GeneralNames.getInstance(aSN1InputStream.readObject());
                                    } else {
                                        stringBuffer.append(aSN1ObjectIdentifier.getId());
                                        stringBuffer.append(" value = ");
                                        stringBuffer.append(ASN1Dump.dumpAsString(aSN1InputStream.readObject()));
                                        stringBuffer.append(property);
                                    }
                                    stringBuffer.append(instance);
                                    stringBuffer.append(property);
                                } catch (Exception unused) {
                                    stringBuffer.append(aSN1ObjectIdentifier.getId());
                                    stringBuffer.append(" value = ");
                                    str = PagoTarjetasConstants.ARS_AND_USD_FORMATTED_AMOUNTS_ASTERISK;
                                }
                            }
                        }
                    }
                }
            }
        }
        return stringBuffer.toString();
    }
}
