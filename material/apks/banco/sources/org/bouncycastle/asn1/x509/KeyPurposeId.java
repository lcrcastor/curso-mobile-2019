package org.bouncycastle.asn1.x509;

import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.TipoCuentaDestino;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;

public class KeyPurposeId extends ASN1Object {
    private static final ASN1ObjectIdentifier a = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.3");
    public static final KeyPurposeId anyExtendedKeyUsage = new KeyPurposeId(Extension.extendedKeyUsage.branch("0"));
    public static final KeyPurposeId id_kp_OCSPSigning = new KeyPurposeId(a.branch("9"));
    public static final KeyPurposeId id_kp_capwapAC = new KeyPurposeId(a.branch("18"));
    public static final KeyPurposeId id_kp_capwapWTP = new KeyPurposeId(a.branch("19"));
    public static final KeyPurposeId id_kp_clientAuth = new KeyPurposeId(a.branch("2"));
    public static final KeyPurposeId id_kp_codeSigning = new KeyPurposeId(a.branch("3"));
    public static final KeyPurposeId id_kp_dvcs = new KeyPurposeId(a.branch("10"));
    public static final KeyPurposeId id_kp_eapOverLAN = new KeyPurposeId(a.branch("14"));
    public static final KeyPurposeId id_kp_eapOverPPP = new KeyPurposeId(a.branch("13"));
    public static final KeyPurposeId id_kp_emailProtection = new KeyPurposeId(a.branch("4"));
    public static final KeyPurposeId id_kp_ipsecEndSystem = new KeyPurposeId(a.branch("5"));
    public static final KeyPurposeId id_kp_ipsecIKE = new KeyPurposeId(a.branch("17"));
    public static final KeyPurposeId id_kp_ipsecTunnel = new KeyPurposeId(a.branch("6"));
    public static final KeyPurposeId id_kp_ipsecUser = new KeyPurposeId(a.branch("7"));
    public static final KeyPurposeId id_kp_sbgpCertAAServerAuth = new KeyPurposeId(a.branch(TipoCuentaDestino.CAJA_AHORRO_PESOS));
    public static final KeyPurposeId id_kp_scvpClient = new KeyPurposeId(a.branch("16"));
    public static final KeyPurposeId id_kp_scvpServer = new KeyPurposeId(a.branch("15"));
    public static final KeyPurposeId id_kp_scvp_responder = new KeyPurposeId(a.branch(TipoCuentaDestino.CAJA_AHORRO_DOLARES));
    public static final KeyPurposeId id_kp_serverAuth = new KeyPurposeId(a.branch("1"));
    public static final KeyPurposeId id_kp_smartcardlogon = new KeyPurposeId(new ASN1ObjectIdentifier("1.3.6.1.4.1.311.20.2.2"));
    public static final KeyPurposeId id_kp_timeStamping = new KeyPurposeId(a.branch("8"));
    private ASN1ObjectIdentifier b;

    public KeyPurposeId(String str) {
        this(new ASN1ObjectIdentifier(str));
    }

    private KeyPurposeId(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.b = aSN1ObjectIdentifier;
    }

    public static KeyPurposeId getInstance(Object obj) {
        if (obj instanceof KeyPurposeId) {
            return (KeyPurposeId) obj;
        }
        if (obj != null) {
            return new KeyPurposeId(ASN1ObjectIdentifier.getInstance(obj));
        }
        return null;
    }

    public String getId() {
        return this.b.getId();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.b;
    }
}
