package org.bouncycastle.asn1.x500.style;

import android.support.media.ExifInterface;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants.ClaseTarjeta;
import com.appsflyer.share.Constants;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;

public class BCStyle extends AbstractX500NameStyle {
    public static final ASN1ObjectIdentifier BUSINESS_CATEGORY = new ASN1ObjectIdentifier("2.5.4.15");
    public static final ASN1ObjectIdentifier C = new ASN1ObjectIdentifier("2.5.4.6");
    public static final ASN1ObjectIdentifier CN = new ASN1ObjectIdentifier("2.5.4.3");
    public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4");
    public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5");
    public static final ASN1ObjectIdentifier DATE_OF_BIRTH = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1");
    public static final ASN1ObjectIdentifier DC = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
    public static final ASN1ObjectIdentifier DMD_NAME = new ASN1ObjectIdentifier("2.5.4.54");
    public static final ASN1ObjectIdentifier DN_QUALIFIER = new ASN1ObjectIdentifier("2.5.4.46");
    public static final ASN1ObjectIdentifier E = EmailAddress;
    public static final ASN1ObjectIdentifier EmailAddress = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
    public static final ASN1ObjectIdentifier GENDER = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3");
    public static final ASN1ObjectIdentifier GENERATION = new ASN1ObjectIdentifier("2.5.4.44");
    public static final ASN1ObjectIdentifier GIVENNAME = new ASN1ObjectIdentifier("2.5.4.42");
    public static final ASN1ObjectIdentifier INITIALS = new ASN1ObjectIdentifier("2.5.4.43");
    public static final X500NameStyle INSTANCE = new BCStyle();
    public static final ASN1ObjectIdentifier L = new ASN1ObjectIdentifier("2.5.4.7");
    public static final ASN1ObjectIdentifier NAME = X509ObjectIdentifiers.id_at_name;
    public static final ASN1ObjectIdentifier NAME_AT_BIRTH = new ASN1ObjectIdentifier("1.3.36.8.3.14");
    public static final ASN1ObjectIdentifier O = new ASN1ObjectIdentifier("2.5.4.10");
    public static final ASN1ObjectIdentifier OU = new ASN1ObjectIdentifier("2.5.4.11");
    public static final ASN1ObjectIdentifier PLACE_OF_BIRTH = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2");
    public static final ASN1ObjectIdentifier POSTAL_ADDRESS = new ASN1ObjectIdentifier("2.5.4.16");
    public static final ASN1ObjectIdentifier POSTAL_CODE = new ASN1ObjectIdentifier("2.5.4.17");
    public static final ASN1ObjectIdentifier PSEUDONYM = new ASN1ObjectIdentifier("2.5.4.65");
    public static final ASN1ObjectIdentifier SERIALNUMBER = SN;
    public static final ASN1ObjectIdentifier SN = new ASN1ObjectIdentifier("2.5.4.5");
    public static final ASN1ObjectIdentifier ST = new ASN1ObjectIdentifier("2.5.4.8");
    public static final ASN1ObjectIdentifier STREET = new ASN1ObjectIdentifier("2.5.4.9");
    public static final ASN1ObjectIdentifier SURNAME = new ASN1ObjectIdentifier("2.5.4.4");
    public static final ASN1ObjectIdentifier T = new ASN1ObjectIdentifier("2.5.4.12");
    public static final ASN1ObjectIdentifier TELEPHONE_NUMBER = X509ObjectIdentifiers.id_at_telephoneNumber;
    public static final ASN1ObjectIdentifier UID = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
    public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER = new ASN1ObjectIdentifier("2.5.4.45");
    public static final ASN1ObjectIdentifier UnstructuredAddress = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
    public static final ASN1ObjectIdentifier UnstructuredName = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
    private static final Hashtable a = new Hashtable();
    private static final Hashtable b = new Hashtable();
    protected final Hashtable defaultLookUp = copyHashTable(b);
    protected final Hashtable defaultSymbols = copyHashTable(a);

    static {
        a.put(C, "C");
        a.put(O, "O");
        a.put(T, "T");
        a.put(OU, "OU");
        a.put(CN, "CN");
        a.put(L, ClaseTarjeta.PLATINUM);
        a.put(ST, "ST");
        a.put(SN, "SERIALNUMBER");
        a.put(EmailAddress, ExifInterface.LONGITUDE_EAST);
        a.put(DC, "DC");
        a.put(UID, "UID");
        a.put(STREET, "STREET");
        a.put(SURNAME, "SURNAME");
        a.put(GIVENNAME, "GIVENNAME");
        a.put(INITIALS, "INITIALS");
        a.put(GENERATION, "GENERATION");
        a.put(UnstructuredAddress, "unstructuredAddress");
        a.put(UnstructuredName, "unstructuredName");
        a.put(UNIQUE_IDENTIFIER, "UniqueIdentifier");
        a.put(DN_QUALIFIER, "DN");
        a.put(PSEUDONYM, "Pseudonym");
        a.put(POSTAL_ADDRESS, "PostalAddress");
        a.put(NAME_AT_BIRTH, "NameAtBirth");
        a.put(COUNTRY_OF_CITIZENSHIP, "CountryOfCitizenship");
        a.put(COUNTRY_OF_RESIDENCE, "CountryOfResidence");
        a.put(GENDER, "Gender");
        a.put(PLACE_OF_BIRTH, "PlaceOfBirth");
        a.put(DATE_OF_BIRTH, "DateOfBirth");
        a.put(POSTAL_CODE, "PostalCode");
        a.put(BUSINESS_CATEGORY, "BusinessCategory");
        a.put(TELEPHONE_NUMBER, "TelephoneNumber");
        a.put(NAME, "Name");
        b.put(Constants.URL_CAMPAIGN, C);
        b.put("o", O);
        b.put("t", T);
        b.put("ou", OU);
        b.put("cn", CN);
        b.put("l", L);
        b.put("st", ST);
        b.put("sn", SN);
        b.put("serialnumber", SN);
        b.put("street", STREET);
        b.put("emailaddress", E);
        b.put("dc", DC);
        b.put("e", E);
        b.put("uid", UID);
        b.put("surname", SURNAME);
        b.put("givenname", GIVENNAME);
        b.put("initials", INITIALS);
        b.put("generation", GENERATION);
        b.put("unstructuredaddress", UnstructuredAddress);
        b.put("unstructuredname", UnstructuredName);
        b.put("uniqueidentifier", UNIQUE_IDENTIFIER);
        b.put("dn", DN_QUALIFIER);
        b.put("pseudonym", PSEUDONYM);
        b.put("postaladdress", POSTAL_ADDRESS);
        b.put("nameofbirth", NAME_AT_BIRTH);
        b.put("countryofcitizenship", COUNTRY_OF_CITIZENSHIP);
        b.put("countryofresidence", COUNTRY_OF_RESIDENCE);
        b.put("gender", GENDER);
        b.put("placeofbirth", PLACE_OF_BIRTH);
        b.put("dateofbirth", DATE_OF_BIRTH);
        b.put("postalcode", POSTAL_CODE);
        b.put("businesscategory", BUSINESS_CATEGORY);
        b.put("telephonenumber", TELEPHONE_NUMBER);
        b.put("name", NAME);
    }

    protected BCStyle() {
    }

    public ASN1ObjectIdentifier attrNameToOID(String str) {
        return IETFUtils.decodeAttrName(str, this.defaultLookUp);
    }

    /* access modifiers changed from: protected */
    public ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        return (aSN1ObjectIdentifier.equals(EmailAddress) || aSN1ObjectIdentifier.equals(DC)) ? new DERIA5String(str) : aSN1ObjectIdentifier.equals(DATE_OF_BIRTH) ? new ASN1GeneralizedTime(str) : (aSN1ObjectIdentifier.equals(C) || aSN1ObjectIdentifier.equals(SN) || aSN1ObjectIdentifier.equals(DN_QUALIFIER) || aSN1ObjectIdentifier.equals(TELEPHONE_NUMBER)) ? new DERPrintableString(str) : super.encodeStringValue(aSN1ObjectIdentifier, str);
    }

    public RDN[] fromString(String str) {
        return IETFUtils.rDNsFromString(str, this);
    }

    public String[] oidToAttrNames(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return IETFUtils.findAttrNamesForOID(aSN1ObjectIdentifier, this.defaultLookUp);
    }

    public String oidToDisplayName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String) a.get(aSN1ObjectIdentifier);
    }

    public String toString(X500Name x500Name) {
        StringBuffer stringBuffer = new StringBuffer();
        RDN[] rDNs = x500Name.getRDNs();
        boolean z = true;
        for (RDN appendRDN : rDNs) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            IETFUtils.appendRDN(stringBuffer, appendRDN, this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
