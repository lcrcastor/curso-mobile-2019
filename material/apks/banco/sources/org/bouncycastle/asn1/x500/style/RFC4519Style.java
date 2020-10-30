package org.bouncycastle.asn1.x500.style;

import com.appsflyer.share.Constants;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameStyle;

public class RFC4519Style extends AbstractX500NameStyle {
    public static final X500NameStyle INSTANCE = new RFC4519Style();
    private static final Hashtable a = new Hashtable();
    private static final Hashtable b = new Hashtable();
    public static final ASN1ObjectIdentifier businessCategory = new ASN1ObjectIdentifier("2.5.4.15");
    public static final ASN1ObjectIdentifier c = new ASN1ObjectIdentifier("2.5.4.6");
    public static final ASN1ObjectIdentifier cn = new ASN1ObjectIdentifier("2.5.4.3");
    public static final ASN1ObjectIdentifier dc = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
    public static final ASN1ObjectIdentifier description = new ASN1ObjectIdentifier("2.5.4.13");
    public static final ASN1ObjectIdentifier destinationIndicator = new ASN1ObjectIdentifier("2.5.4.27");
    public static final ASN1ObjectIdentifier distinguishedName = new ASN1ObjectIdentifier("2.5.4.49");
    public static final ASN1ObjectIdentifier dnQualifier = new ASN1ObjectIdentifier("2.5.4.46");
    public static final ASN1ObjectIdentifier enhancedSearchGuide = new ASN1ObjectIdentifier("2.5.4.47");
    public static final ASN1ObjectIdentifier facsimileTelephoneNumber = new ASN1ObjectIdentifier("2.5.4.23");
    public static final ASN1ObjectIdentifier generationQualifier = new ASN1ObjectIdentifier("2.5.4.44");
    public static final ASN1ObjectIdentifier givenName = new ASN1ObjectIdentifier("2.5.4.42");
    public static final ASN1ObjectIdentifier houseIdentifier = new ASN1ObjectIdentifier("2.5.4.51");
    public static final ASN1ObjectIdentifier initials = new ASN1ObjectIdentifier("2.5.4.43");
    public static final ASN1ObjectIdentifier internationalISDNNumber = new ASN1ObjectIdentifier("2.5.4.25");
    public static final ASN1ObjectIdentifier l = new ASN1ObjectIdentifier("2.5.4.7");
    public static final ASN1ObjectIdentifier member = new ASN1ObjectIdentifier("2.5.4.31");
    public static final ASN1ObjectIdentifier name = new ASN1ObjectIdentifier("2.5.4.41");
    public static final ASN1ObjectIdentifier o = new ASN1ObjectIdentifier("2.5.4.10");
    public static final ASN1ObjectIdentifier ou = new ASN1ObjectIdentifier("2.5.4.11");
    public static final ASN1ObjectIdentifier owner = new ASN1ObjectIdentifier("2.5.4.32");
    public static final ASN1ObjectIdentifier physicalDeliveryOfficeName = new ASN1ObjectIdentifier("2.5.4.19");
    public static final ASN1ObjectIdentifier postOfficeBox = new ASN1ObjectIdentifier("2.5.4.18");
    public static final ASN1ObjectIdentifier postalAddress = new ASN1ObjectIdentifier("2.5.4.16");
    public static final ASN1ObjectIdentifier postalCode = new ASN1ObjectIdentifier("2.5.4.17");
    public static final ASN1ObjectIdentifier preferredDeliveryMethod = new ASN1ObjectIdentifier("2.5.4.28");
    public static final ASN1ObjectIdentifier registeredAddress = new ASN1ObjectIdentifier("2.5.4.26");
    public static final ASN1ObjectIdentifier roleOccupant = new ASN1ObjectIdentifier("2.5.4.33");
    public static final ASN1ObjectIdentifier searchGuide = new ASN1ObjectIdentifier("2.5.4.14");
    public static final ASN1ObjectIdentifier seeAlso = new ASN1ObjectIdentifier("2.5.4.34");
    public static final ASN1ObjectIdentifier serialNumber = new ASN1ObjectIdentifier("2.5.4.5");
    public static final ASN1ObjectIdentifier sn = new ASN1ObjectIdentifier("2.5.4.4");
    public static final ASN1ObjectIdentifier st = new ASN1ObjectIdentifier("2.5.4.8");
    public static final ASN1ObjectIdentifier street = new ASN1ObjectIdentifier("2.5.4.9");
    public static final ASN1ObjectIdentifier telephoneNumber = new ASN1ObjectIdentifier("2.5.4.20");
    public static final ASN1ObjectIdentifier teletexTerminalIdentifier = new ASN1ObjectIdentifier("2.5.4.22");
    public static final ASN1ObjectIdentifier telexNumber = new ASN1ObjectIdentifier("2.5.4.21");
    public static final ASN1ObjectIdentifier title = new ASN1ObjectIdentifier("2.5.4.12");
    public static final ASN1ObjectIdentifier uid = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
    public static final ASN1ObjectIdentifier uniqueMember = new ASN1ObjectIdentifier("2.5.4.50");
    public static final ASN1ObjectIdentifier userPassword = new ASN1ObjectIdentifier("2.5.4.35");
    public static final ASN1ObjectIdentifier x121Address = new ASN1ObjectIdentifier("2.5.4.24");
    public static final ASN1ObjectIdentifier x500UniqueIdentifier = new ASN1ObjectIdentifier("2.5.4.45");
    protected final Hashtable defaultLookUp = copyHashTable(b);
    protected final Hashtable defaultSymbols = copyHashTable(a);

    static {
        a.put(businessCategory, "businessCategory");
        a.put(c, Constants.URL_CAMPAIGN);
        a.put(cn, "cn");
        a.put(dc, "dc");
        a.put(description, "description");
        a.put(destinationIndicator, "destinationIndicator");
        a.put(distinguishedName, "distinguishedName");
        a.put(dnQualifier, "dnQualifier");
        a.put(enhancedSearchGuide, "enhancedSearchGuide");
        a.put(facsimileTelephoneNumber, "facsimileTelephoneNumber");
        a.put(generationQualifier, "generationQualifier");
        a.put(givenName, "givenName");
        a.put(houseIdentifier, "houseIdentifier");
        a.put(initials, "initials");
        a.put(internationalISDNNumber, "internationalISDNNumber");
        a.put(l, "l");
        a.put(member, "member");
        a.put(name, "name");
        a.put(o, "o");
        a.put(ou, "ou");
        a.put(owner, "owner");
        a.put(physicalDeliveryOfficeName, "physicalDeliveryOfficeName");
        a.put(postalAddress, "postalAddress");
        a.put(postalCode, "postalCode");
        a.put(postOfficeBox, "postOfficeBox");
        a.put(preferredDeliveryMethod, "preferredDeliveryMethod");
        a.put(registeredAddress, "registeredAddress");
        a.put(roleOccupant, "roleOccupant");
        a.put(searchGuide, "searchGuide");
        a.put(seeAlso, "seeAlso");
        a.put(serialNumber, "serialNumber");
        a.put(sn, "sn");
        a.put(st, "st");
        a.put(street, "street");
        a.put(telephoneNumber, "telephoneNumber");
        a.put(teletexTerminalIdentifier, "teletexTerminalIdentifier");
        a.put(telexNumber, "telexNumber");
        a.put(title, "title");
        a.put(uid, "uid");
        a.put(uniqueMember, "uniqueMember");
        a.put(userPassword, "userPassword");
        a.put(x121Address, "x121Address");
        a.put(x500UniqueIdentifier, "x500UniqueIdentifier");
        b.put("businesscategory", businessCategory);
        b.put(Constants.URL_CAMPAIGN, c);
        b.put("cn", cn);
        b.put("dc", dc);
        b.put("description", description);
        b.put("destinationindicator", destinationIndicator);
        b.put("distinguishedname", distinguishedName);
        b.put("dnqualifier", dnQualifier);
        b.put("enhancedsearchguide", enhancedSearchGuide);
        b.put("facsimiletelephonenumber", facsimileTelephoneNumber);
        b.put("generationqualifier", generationQualifier);
        b.put("givenname", givenName);
        b.put("houseidentifier", houseIdentifier);
        b.put("initials", initials);
        b.put("internationalisdnnumber", internationalISDNNumber);
        b.put("l", l);
        b.put("member", member);
        b.put("name", name);
        b.put("o", o);
        b.put("ou", ou);
        b.put("owner", owner);
        b.put("physicaldeliveryofficename", physicalDeliveryOfficeName);
        b.put("postaladdress", postalAddress);
        b.put("postalcode", postalCode);
        b.put("postofficebox", postOfficeBox);
        b.put("preferreddeliverymethod", preferredDeliveryMethod);
        b.put("registeredaddress", registeredAddress);
        b.put("roleoccupant", roleOccupant);
        b.put("searchguide", searchGuide);
        b.put("seealso", seeAlso);
        b.put("serialnumber", serialNumber);
        b.put("sn", sn);
        b.put("st", st);
        b.put("street", street);
        b.put("telephonenumber", telephoneNumber);
        b.put("teletexterminalidentifier", teletexTerminalIdentifier);
        b.put("telexnumber", telexNumber);
        b.put("title", title);
        b.put("uid", uid);
        b.put("uniquemember", uniqueMember);
        b.put("userpassword", userPassword);
        b.put("x121address", x121Address);
        b.put("x500uniqueidentifier", x500UniqueIdentifier);
    }

    protected RFC4519Style() {
    }

    public ASN1ObjectIdentifier attrNameToOID(String str) {
        return IETFUtils.decodeAttrName(str, this.defaultLookUp);
    }

    /* access modifiers changed from: protected */
    public ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        return aSN1ObjectIdentifier.equals(dc) ? new DERIA5String(str) : (aSN1ObjectIdentifier.equals(c) || aSN1ObjectIdentifier.equals(serialNumber) || aSN1ObjectIdentifier.equals(dnQualifier) || aSN1ObjectIdentifier.equals(telephoneNumber)) ? new DERPrintableString(str) : super.encodeStringValue(aSN1ObjectIdentifier, str);
    }

    public RDN[] fromString(String str) {
        RDN[] rDNsFromString = IETFUtils.rDNsFromString(str, this);
        RDN[] rdnArr = new RDN[rDNsFromString.length];
        for (int i = 0; i != rDNsFromString.length; i++) {
            rdnArr[(rdnArr.length - i) - 1] = rDNsFromString[i];
        }
        return rdnArr;
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
        for (int length = rDNs.length - 1; length >= 0; length--) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            IETFUtils.appendRDN(stringBuffer, rDNs[length], this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
