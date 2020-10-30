package org.bouncycastle.asn1.x509;

import android.support.media.ExifInterface;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants.ClaseTarjeta;
import com.appsflyer.share.Constants;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERUniversalString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class X509Name extends ASN1Object {
    public static final ASN1ObjectIdentifier BUSINESS_CATEGORY = new ASN1ObjectIdentifier("2.5.4.15");
    public static final ASN1ObjectIdentifier C = new ASN1ObjectIdentifier("2.5.4.6");
    public static final ASN1ObjectIdentifier CN = new ASN1ObjectIdentifier("2.5.4.3");
    public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4");
    public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5");
    public static final ASN1ObjectIdentifier DATE_OF_BIRTH = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1");
    public static final ASN1ObjectIdentifier DC = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
    public static final ASN1ObjectIdentifier DMD_NAME = new ASN1ObjectIdentifier("2.5.4.54");
    public static final ASN1ObjectIdentifier DN_QUALIFIER = new ASN1ObjectIdentifier("2.5.4.46");
    public static final Hashtable DefaultLookUp = new Hashtable();
    public static boolean DefaultReverse = false;
    public static final Hashtable DefaultSymbols = new Hashtable();
    public static final ASN1ObjectIdentifier E = EmailAddress;
    public static final ASN1ObjectIdentifier EmailAddress = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
    public static final ASN1ObjectIdentifier GENDER = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3");
    public static final ASN1ObjectIdentifier GENERATION = new ASN1ObjectIdentifier("2.5.4.44");
    public static final ASN1ObjectIdentifier GIVENNAME = new ASN1ObjectIdentifier("2.5.4.42");
    public static final ASN1ObjectIdentifier INITIALS = new ASN1ObjectIdentifier("2.5.4.43");
    public static final ASN1ObjectIdentifier L = new ASN1ObjectIdentifier("2.5.4.7");
    public static final ASN1ObjectIdentifier NAME = X509ObjectIdentifiers.id_at_name;
    public static final ASN1ObjectIdentifier NAME_AT_BIRTH = new ASN1ObjectIdentifier("1.3.36.8.3.14");
    public static final ASN1ObjectIdentifier O = new ASN1ObjectIdentifier("2.5.4.10");
    public static final Hashtable OIDLookUp = DefaultSymbols;
    public static final ASN1ObjectIdentifier OU = new ASN1ObjectIdentifier("2.5.4.11");
    public static final ASN1ObjectIdentifier PLACE_OF_BIRTH = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2");
    public static final ASN1ObjectIdentifier POSTAL_ADDRESS = new ASN1ObjectIdentifier("2.5.4.16");
    public static final ASN1ObjectIdentifier POSTAL_CODE = new ASN1ObjectIdentifier("2.5.4.17");
    public static final ASN1ObjectIdentifier PSEUDONYM = new ASN1ObjectIdentifier("2.5.4.65");
    public static final Hashtable RFC1779Symbols = new Hashtable();
    public static final Hashtable RFC2253Symbols = new Hashtable();
    public static final ASN1ObjectIdentifier SERIALNUMBER = SN;
    public static final ASN1ObjectIdentifier SN = new ASN1ObjectIdentifier("2.5.4.5");
    public static final ASN1ObjectIdentifier ST = new ASN1ObjectIdentifier("2.5.4.8");
    public static final ASN1ObjectIdentifier STREET = new ASN1ObjectIdentifier("2.5.4.9");
    public static final ASN1ObjectIdentifier SURNAME = new ASN1ObjectIdentifier("2.5.4.4");
    public static final Hashtable SymbolLookUp = DefaultLookUp;
    public static final ASN1ObjectIdentifier T = new ASN1ObjectIdentifier("2.5.4.12");
    public static final ASN1ObjectIdentifier TELEPHONE_NUMBER = X509ObjectIdentifiers.id_at_telephoneNumber;
    public static final ASN1ObjectIdentifier UID = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
    public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER = new ASN1ObjectIdentifier("2.5.4.45");
    public static final ASN1ObjectIdentifier UnstructuredAddress = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
    public static final ASN1ObjectIdentifier UnstructuredName = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
    private static final Boolean a = new Boolean(true);
    private static final Boolean b = new Boolean(false);
    private X509NameEntryConverter c;
    private Vector d;
    private Vector e;
    private Vector f;
    private ASN1Sequence g;
    private boolean h;
    private int i;

    static {
        DefaultSymbols.put(C, "C");
        DefaultSymbols.put(O, "O");
        DefaultSymbols.put(T, "T");
        DefaultSymbols.put(OU, "OU");
        DefaultSymbols.put(CN, "CN");
        DefaultSymbols.put(L, ClaseTarjeta.PLATINUM);
        DefaultSymbols.put(ST, "ST");
        DefaultSymbols.put(SN, "SERIALNUMBER");
        DefaultSymbols.put(EmailAddress, ExifInterface.LONGITUDE_EAST);
        DefaultSymbols.put(DC, "DC");
        DefaultSymbols.put(UID, "UID");
        DefaultSymbols.put(STREET, "STREET");
        DefaultSymbols.put(SURNAME, "SURNAME");
        DefaultSymbols.put(GIVENNAME, "GIVENNAME");
        DefaultSymbols.put(INITIALS, "INITIALS");
        DefaultSymbols.put(GENERATION, "GENERATION");
        DefaultSymbols.put(UnstructuredAddress, "unstructuredAddress");
        DefaultSymbols.put(UnstructuredName, "unstructuredName");
        DefaultSymbols.put(UNIQUE_IDENTIFIER, "UniqueIdentifier");
        DefaultSymbols.put(DN_QUALIFIER, "DN");
        DefaultSymbols.put(PSEUDONYM, "Pseudonym");
        DefaultSymbols.put(POSTAL_ADDRESS, "PostalAddress");
        DefaultSymbols.put(NAME_AT_BIRTH, "NameAtBirth");
        DefaultSymbols.put(COUNTRY_OF_CITIZENSHIP, "CountryOfCitizenship");
        DefaultSymbols.put(COUNTRY_OF_RESIDENCE, "CountryOfResidence");
        DefaultSymbols.put(GENDER, "Gender");
        DefaultSymbols.put(PLACE_OF_BIRTH, "PlaceOfBirth");
        DefaultSymbols.put(DATE_OF_BIRTH, "DateOfBirth");
        DefaultSymbols.put(POSTAL_CODE, "PostalCode");
        DefaultSymbols.put(BUSINESS_CATEGORY, "BusinessCategory");
        DefaultSymbols.put(TELEPHONE_NUMBER, "TelephoneNumber");
        DefaultSymbols.put(NAME, "Name");
        RFC2253Symbols.put(C, "C");
        RFC2253Symbols.put(O, "O");
        RFC2253Symbols.put(OU, "OU");
        RFC2253Symbols.put(CN, "CN");
        RFC2253Symbols.put(L, ClaseTarjeta.PLATINUM);
        RFC2253Symbols.put(ST, "ST");
        RFC2253Symbols.put(STREET, "STREET");
        RFC2253Symbols.put(DC, "DC");
        RFC2253Symbols.put(UID, "UID");
        RFC1779Symbols.put(C, "C");
        RFC1779Symbols.put(O, "O");
        RFC1779Symbols.put(OU, "OU");
        RFC1779Symbols.put(CN, "CN");
        RFC1779Symbols.put(L, ClaseTarjeta.PLATINUM);
        RFC1779Symbols.put(ST, "ST");
        RFC1779Symbols.put(STREET, "STREET");
        DefaultLookUp.put(Constants.URL_CAMPAIGN, C);
        DefaultLookUp.put("o", O);
        DefaultLookUp.put("t", T);
        DefaultLookUp.put("ou", OU);
        DefaultLookUp.put("cn", CN);
        DefaultLookUp.put("l", L);
        DefaultLookUp.put("st", ST);
        DefaultLookUp.put("sn", SN);
        DefaultLookUp.put("serialnumber", SN);
        DefaultLookUp.put("street", STREET);
        DefaultLookUp.put("emailaddress", E);
        DefaultLookUp.put("dc", DC);
        DefaultLookUp.put("e", E);
        DefaultLookUp.put("uid", UID);
        DefaultLookUp.put("surname", SURNAME);
        DefaultLookUp.put("givenname", GIVENNAME);
        DefaultLookUp.put("initials", INITIALS);
        DefaultLookUp.put("generation", GENERATION);
        DefaultLookUp.put("unstructuredaddress", UnstructuredAddress);
        DefaultLookUp.put("unstructuredname", UnstructuredName);
        DefaultLookUp.put("uniqueidentifier", UNIQUE_IDENTIFIER);
        DefaultLookUp.put("dn", DN_QUALIFIER);
        DefaultLookUp.put("pseudonym", PSEUDONYM);
        DefaultLookUp.put("postaladdress", POSTAL_ADDRESS);
        DefaultLookUp.put("nameofbirth", NAME_AT_BIRTH);
        DefaultLookUp.put("countryofcitizenship", COUNTRY_OF_CITIZENSHIP);
        DefaultLookUp.put("countryofresidence", COUNTRY_OF_RESIDENCE);
        DefaultLookUp.put("gender", GENDER);
        DefaultLookUp.put("placeofbirth", PLACE_OF_BIRTH);
        DefaultLookUp.put("dateofbirth", DATE_OF_BIRTH);
        DefaultLookUp.put("postalcode", POSTAL_CODE);
        DefaultLookUp.put("businesscategory", BUSINESS_CATEGORY);
        DefaultLookUp.put("telephonenumber", TELEPHONE_NUMBER);
        DefaultLookUp.put("name", NAME);
    }

    protected X509Name() {
        this.c = null;
        this.d = new Vector();
        this.e = new Vector();
        this.f = new Vector();
    }

    public X509Name(String str) {
        this(DefaultReverse, DefaultLookUp, str);
    }

    public X509Name(String str, X509NameEntryConverter x509NameEntryConverter) {
        this(DefaultReverse, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(Hashtable hashtable) {
        this((Vector) null, hashtable);
    }

    public X509Name(Vector vector, Hashtable hashtable) {
        this(vector, hashtable, (X509NameEntryConverter) new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Hashtable hashtable, X509NameEntryConverter x509NameEntryConverter) {
        this.c = null;
        this.d = new Vector();
        this.e = new Vector();
        this.f = new Vector();
        this.c = x509NameEntryConverter;
        if (vector != null) {
            for (int i2 = 0; i2 != vector.size(); i2++) {
                this.d.addElement(vector.elementAt(i2));
                this.f.addElement(b);
            }
        } else {
            Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                this.d.addElement(keys.nextElement());
                this.f.addElement(b);
            }
        }
        for (int i3 = 0; i3 != this.d.size(); i3++) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) this.d.elementAt(i3);
            if (hashtable.get(aSN1ObjectIdentifier) == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("No attribute for object id - ");
                sb.append(aSN1ObjectIdentifier.getId());
                sb.append(" - passed to distinguished name");
                throw new IllegalArgumentException(sb.toString());
            }
            this.e.addElement(hashtable.get(aSN1ObjectIdentifier));
        }
    }

    public X509Name(Vector vector, Vector vector2) {
        this(vector, vector2, (X509NameEntryConverter) new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Vector vector2, X509NameEntryConverter x509NameEntryConverter) {
        this.c = null;
        this.d = new Vector();
        this.e = new Vector();
        this.f = new Vector();
        this.c = x509NameEntryConverter;
        if (vector.size() != vector2.size()) {
            throw new IllegalArgumentException("oids vector must be same length as values.");
        }
        for (int i2 = 0; i2 < vector.size(); i2++) {
            this.d.addElement(vector.elementAt(i2));
            this.e.addElement(vector2.elementAt(i2));
            this.f.addElement(b);
        }
    }

    public X509Name(ASN1Sequence aSN1Sequence) {
        Vector vector;
        this.c = null;
        this.d = new Vector();
        this.e = new Vector();
        this.f = new Vector();
        this.g = aSN1Sequence;
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1Set instance = ASN1Set.getInstance(((ASN1Encodable) objects.nextElement()).toASN1Primitive());
            int i2 = 0;
            while (true) {
                if (i2 < instance.size()) {
                    ASN1Sequence instance2 = ASN1Sequence.getInstance(instance.getObjectAt(i2).toASN1Primitive());
                    if (instance2.size() != 2) {
                        throw new IllegalArgumentException("badly sized pair");
                    }
                    this.d.addElement(ASN1ObjectIdentifier.getInstance(instance2.getObjectAt(0)));
                    ASN1Encodable objectAt = instance2.getObjectAt(1);
                    if (!(objectAt instanceof ASN1String) || (objectAt instanceof DERUniversalString)) {
                        try {
                            Vector vector2 = this.e;
                            StringBuilder sb = new StringBuilder();
                            sb.append("#");
                            sb.append(a(Hex.encode(objectAt.toASN1Primitive().getEncoded(ASN1Encoding.DER))));
                            vector2.addElement(sb.toString());
                        } catch (IOException unused) {
                            throw new IllegalArgumentException("cannot encode value");
                        }
                    } else {
                        String string = ((ASN1String) objectAt).getString();
                        if (string.length() <= 0 || string.charAt(0) != '#') {
                            vector = this.e;
                        } else {
                            vector = this.e;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("\\");
                            sb2.append(string);
                            string = sb2.toString();
                        }
                        vector.addElement(string);
                    }
                    this.f.addElement(i2 != 0 ? a : b);
                    i2++;
                }
            }
        }
    }

    public X509Name(boolean z, String str) {
        this(z, DefaultLookUp, str);
    }

    public X509Name(boolean z, String str, X509NameEntryConverter x509NameEntryConverter) {
        this(z, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(boolean z, Hashtable hashtable, String str) {
        this(z, hashtable, str, new X509DefaultEntryConverter());
    }

    public X509Name(boolean z, Hashtable hashtable, String str, X509NameEntryConverter x509NameEntryConverter) {
        this.c = null;
        this.d = new Vector();
        this.e = new Vector();
        this.f = new Vector();
        this.c = x509NameEntryConverter;
        X509NameTokenizer x509NameTokenizer = new X509NameTokenizer(str);
        while (x509NameTokenizer.hasMoreTokens()) {
            String nextToken = x509NameTokenizer.nextToken();
            if (nextToken.indexOf(43) > 0) {
                X509NameTokenizer x509NameTokenizer2 = new X509NameTokenizer(nextToken, '+');
                String nextToken2 = x509NameTokenizer2.nextToken();
                Boolean bool = b;
                while (true) {
                    a(hashtable, nextToken2, bool);
                    if (!x509NameTokenizer2.hasMoreTokens()) {
                        break;
                    }
                    nextToken2 = x509NameTokenizer2.nextToken();
                    bool = a;
                }
            } else {
                a(hashtable, nextToken, b);
            }
        }
        if (z) {
            Vector vector = new Vector();
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();
            int i2 = 1;
            for (int i3 = 0; i3 < this.d.size(); i3++) {
                if (((Boolean) this.f.elementAt(i3)).booleanValue()) {
                    vector.insertElementAt(this.d.elementAt(i3), i2);
                    vector2.insertElementAt(this.e.elementAt(i3), i2);
                    vector3.insertElementAt(this.f.elementAt(i3), i2);
                    i2++;
                } else {
                    vector.insertElementAt(this.d.elementAt(i3), 0);
                    vector2.insertElementAt(this.e.elementAt(i3), 0);
                    vector3.insertElementAt(this.f.elementAt(i3), 0);
                    i2 = 1;
                }
            }
            this.d = vector;
            this.e = vector2;
            this.f = vector3;
        }
    }

    private String a(String str) {
        int i2;
        if (str.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return str.trim();
        }
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer(str.length());
        if (charArray[0] == '\\' && charArray[1] == '#') {
            i2 = 2;
            stringBuffer.append("\\#");
        } else {
            i2 = 0;
        }
        boolean z = false;
        boolean z2 = false;
        int i3 = 0;
        boolean z3 = false;
        while (i2 != charArray.length) {
            char c2 = charArray[i2];
            if (c2 != ' ') {
                z3 = true;
            }
            if (c2 == '\"') {
                if (!z) {
                    z2 = !z2;
                }
                stringBuffer.append(c2);
            } else if (c2 != '\\' || z || z2) {
                if (c2 == ' ' && !z && !z3) {
                    i2++;
                }
                stringBuffer.append(c2);
            } else {
                i3 = stringBuffer.length();
                z = true;
                i2++;
            }
            z = false;
            i2++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && i3 != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    private String a(byte[] bArr) {
        char[] cArr = new char[bArr.length];
        for (int i2 = 0; i2 != cArr.length; i2++) {
            cArr[i2] = (char) (bArr[i2] & UnsignedBytes.MAX_VALUE);
        }
        return new String(cArr);
    }

    private ASN1ObjectIdentifier a(String str, Hashtable hashtable) {
        String trim = str.trim();
        if (Strings.toUpperCase(trim).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(trim.substring(4));
        }
        if (trim.charAt(0) >= '0' && trim.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(trim);
        }
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) hashtable.get(Strings.toLowerCase(trim));
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown object id - ");
        sb.append(trim);
        sb.append(" - passed to distinguished name");
        throw new IllegalArgumentException(sb.toString());
    }

    private void a(StringBuffer stringBuffer, Hashtable hashtable, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        int i2;
        String str2 = (String) hashtable.get(aSN1ObjectIdentifier);
        if (str2 == null) {
            str2 = aSN1ObjectIdentifier.getId();
        }
        stringBuffer.append(str2);
        stringBuffer.append('=');
        int length = stringBuffer.length();
        stringBuffer.append(str);
        int length2 = stringBuffer.length();
        if (str.length() >= 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
            length += 2;
        }
        while (i2 < length2 && stringBuffer.charAt(i2) == ' ') {
            stringBuffer.insert(i2, "\\");
            length = i2 + 2;
            length2++;
        }
        while (true) {
            length2--;
            if (length2 > i2 && stringBuffer.charAt(length2) == ' ') {
                stringBuffer.insert(length2, TokenParser.ESCAPE);
            }
        }
        while (i2 <= length2) {
            char charAt = stringBuffer.charAt(i2);
            if (!(charAt == '\"' || charAt == '\\')) {
                switch (charAt) {
                    case '+':
                    case ',':
                        break;
                    default:
                        switch (charAt) {
                            case ';':
                            case '<':
                            case '=':
                            case '>':
                                break;
                            default:
                                i2++;
                                continue;
                                continue;
                        }
                }
            }
            stringBuffer.insert(i2, "\\");
            i2 += 2;
            length2++;
        }
    }

    private void a(Hashtable hashtable, String str, Boolean bool) {
        X509NameTokenizer x509NameTokenizer = new X509NameTokenizer(str, '=');
        String nextToken = x509NameTokenizer.nextToken();
        if (!x509NameTokenizer.hasMoreTokens()) {
            throw new IllegalArgumentException("badly formatted directory string");
        }
        String nextToken2 = x509NameTokenizer.nextToken();
        this.d.addElement(a(nextToken, hashtable));
        this.e.addElement(a(nextToken2));
        this.f.addElement(bool);
    }

    private boolean a(String str, String str2) {
        String b2 = b(str);
        String b3 = b(str2);
        return b2.equals(b3) || d(b2).equals(d(b3));
    }

    private String b(String str) {
        String lowerCase = Strings.toLowerCase(str.trim());
        if (lowerCase.length() <= 0 || lowerCase.charAt(0) != '#') {
            return lowerCase;
        }
        ASN1Primitive c2 = c(lowerCase);
        return c2 instanceof ASN1String ? Strings.toLowerCase(((ASN1String) c2).getString().trim()) : lowerCase;
    }

    private ASN1Primitive c(String str) {
        try {
            return ASN1Primitive.fromByteArray(Hex.decode(str.substring(1)));
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("unknown encoding in name: ");
            sb.append(e2);
            throw new IllegalStateException(sb.toString());
        }
    }

    private String d(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() != 0) {
            char charAt = str.charAt(0);
            stringBuffer.append(charAt);
            int i2 = 1;
            while (i2 < str.length()) {
                char charAt2 = str.charAt(i2);
                if (charAt != ' ' || charAt2 != ' ') {
                    stringBuffer.append(charAt2);
                }
                i2++;
                charAt = charAt2;
            }
        }
        return stringBuffer.toString();
    }

    public static X509Name getInstance(Object obj) {
        if (obj == null || (obj instanceof X509Name)) {
            return (X509Name) obj;
        }
        if (obj instanceof X500Name) {
            return new X509Name(ASN1Sequence.getInstance(((X500Name) obj).toASN1Primitive()));
        }
        if (obj != null) {
            return new X509Name(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static X509Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public boolean equals(Object obj) {
        int i2;
        int i3;
        boolean z;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            X509Name instance = getInstance(obj);
            int size = this.d.size();
            if (size != instance.d.size()) {
                return false;
            }
            boolean[] zArr = new boolean[size];
            int i4 = -1;
            if (this.d.elementAt(0).equals(instance.d.elementAt(0))) {
                i4 = size;
                i3 = 0;
                i2 = 1;
            } else {
                i3 = size - 1;
                i2 = -1;
            }
            while (i3 != i4) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) this.d.elementAt(i3);
                String str = (String) this.e.elementAt(i3);
                int i5 = 0;
                while (true) {
                    if (i5 >= size) {
                        z = false;
                        break;
                    } else if (!zArr[i5] && aSN1ObjectIdentifier.equals((ASN1ObjectIdentifier) instance.d.elementAt(i5)) && a(str, (String) instance.e.elementAt(i5))) {
                        zArr[i5] = true;
                        z = true;
                        break;
                    } else {
                        i5++;
                    }
                }
                if (!z) {
                    return false;
                }
                i3 += i2;
            }
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public boolean equals(Object obj, boolean z) {
        if (!z) {
            return equals(obj);
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            X509Name instance = getInstance(obj);
            int size = this.d.size();
            if (size != instance.d.size()) {
                return false;
            }
            for (int i2 = 0; i2 < size; i2++) {
                if (!((ASN1ObjectIdentifier) this.d.elementAt(i2)).equals((ASN1ObjectIdentifier) instance.d.elementAt(i2)) || !a((String) this.e.elementAt(i2), (String) instance.e.elementAt(i2))) {
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public Vector getOIDs() {
        Vector vector = new Vector();
        for (int i2 = 0; i2 != this.d.size(); i2++) {
            vector.addElement(this.d.elementAt(i2));
        }
        return vector;
    }

    public Vector getValues() {
        Vector vector = new Vector();
        for (int i2 = 0; i2 != this.e.size(); i2++) {
            vector.addElement(this.e.elementAt(i2));
        }
        return vector;
    }

    public Vector getValues(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Vector vector = new Vector();
        for (int i2 = 0; i2 != this.e.size(); i2++) {
            if (this.d.elementAt(i2).equals(aSN1ObjectIdentifier)) {
                String str = (String) this.e.elementAt(i2);
                if (str.length() > 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
                    str = str.substring(1);
                }
                vector.addElement(str);
            }
        }
        return vector;
    }

    public int hashCode() {
        if (this.h) {
            return this.i;
        }
        this.h = true;
        for (int i2 = 0; i2 != this.d.size(); i2++) {
            String d2 = d(b((String) this.e.elementAt(i2)));
            this.i ^= this.d.elementAt(i2).hashCode();
            this.i = d2.hashCode() ^ this.i;
        }
        return this.i;
    }

    public ASN1Primitive toASN1Primitive() {
        DERSequence dERSequence;
        if (this.g == null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            ASN1ObjectIdentifier aSN1ObjectIdentifier = null;
            int i2 = 0;
            while (i2 != this.d.size()) {
                ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
                ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) this.d.elementAt(i2);
                aSN1EncodableVector3.add(aSN1ObjectIdentifier2);
                aSN1EncodableVector3.add(this.c.getConvertedValue(aSN1ObjectIdentifier2, (String) this.e.elementAt(i2)));
                if (aSN1ObjectIdentifier == null || ((Boolean) this.f.elementAt(i2)).booleanValue()) {
                    dERSequence = new DERSequence(aSN1EncodableVector3);
                } else {
                    aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
                    aSN1EncodableVector2 = new ASN1EncodableVector();
                    dERSequence = new DERSequence(aSN1EncodableVector3);
                }
                aSN1EncodableVector2.add(dERSequence);
                i2++;
                aSN1ObjectIdentifier = aSN1ObjectIdentifier2;
            }
            aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
            this.g = new DERSequence(aSN1EncodableVector);
        }
        return this.g;
    }

    public String toString() {
        return toString(DefaultReverse, DefaultSymbols);
    }

    public String toString(boolean z, Hashtable hashtable) {
        StringBuffer stringBuffer = new StringBuffer();
        Vector vector = new Vector();
        StringBuffer stringBuffer2 = null;
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            if (((Boolean) this.f.elementAt(i2)).booleanValue()) {
                stringBuffer2.append('+');
                a(stringBuffer2, hashtable, (ASN1ObjectIdentifier) this.d.elementAt(i2), (String) this.e.elementAt(i2));
            } else {
                stringBuffer2 = new StringBuffer();
                a(stringBuffer2, hashtable, (ASN1ObjectIdentifier) this.d.elementAt(i2), (String) this.e.elementAt(i2));
                vector.addElement(stringBuffer2);
            }
        }
        boolean z2 = true;
        if (z) {
            for (int size = vector.size() - 1; size >= 0; size--) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(size).toString());
            }
        } else {
            for (int i3 = 0; i3 < vector.size(); i3++) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(i3).toString());
            }
        }
        return stringBuffer.toString();
    }
}
