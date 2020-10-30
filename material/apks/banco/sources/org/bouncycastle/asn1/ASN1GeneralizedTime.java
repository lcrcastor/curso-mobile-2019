package org.bouncycastle.asn1;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.soap.constants.ConstantsWS;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;
import org.joda.time.DateTimeConstants;

public class ASN1GeneralizedTime extends ASN1Primitive {
    private byte[] a;

    public ASN1GeneralizedTime(String str) {
        this.a = Strings.toByteArray(str);
        try {
            getDate();
        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid date string: ");
            sb.append(e.getMessage());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public ASN1GeneralizedTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.a = Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1GeneralizedTime(Date date, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'", locale);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.a = Strings.toByteArray(simpleDateFormat.format(date));
    }

    ASN1GeneralizedTime(byte[] bArr) {
        this.a = bArr;
    }

    private String a(int i) {
        if (i >= 10) {
            return Integer.toString(i);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0");
        sb.append(i);
        return sb.toString();
    }

    private String d() {
        String str = Constants.SYMBOL_POSITIVE;
        TimeZone timeZone = TimeZone.getDefault();
        int rawOffset = timeZone.getRawOffset();
        if (rawOffset < 0) {
            str = "-";
            rawOffset = -rawOffset;
        }
        int i = rawOffset / DateTimeConstants.MILLIS_PER_HOUR;
        int i2 = (rawOffset - (((i * 60) * 60) * 1000)) / DateTimeConstants.MILLIS_PER_MINUTE;
        try {
            if (timeZone.useDaylightTime() && timeZone.inDaylightTime(getDate())) {
                i += str.equals(Constants.SYMBOL_POSITIVE) ? 1 : -1;
            }
        } catch (ParseException unused) {
        }
        StringBuilder sb = new StringBuilder();
        sb.append("GMT");
        sb.append(str);
        sb.append(a(i));
        sb.append(":");
        sb.append(a(i2));
        return sb.toString();
    }

    private boolean e() {
        for (int i = 0; i != this.a.length; i++) {
            if (this.a[i] == 46 && i == 14) {
                return true;
            }
        }
        return false;
    }

    public static ASN1GeneralizedTime getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1GeneralizedTime)) {
            return (ASN1GeneralizedTime) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1GeneralizedTime) fromByteArray((byte[]) obj);
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("encoding error in getInstance: ");
                sb.append(e.toString());
                throw new IllegalArgumentException(sb.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("illegal object in getInstance: ");
            sb2.append(obj.getClass().getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static ASN1GeneralizedTime getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z || (object instanceof ASN1GeneralizedTime)) ? getInstance(object) : new ASN1GeneralizedTime(((ASN1OctetString) object).getOctets());
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        int length = this.a.length;
        return StreamUtil.a(length) + 1 + length;
    }

    /* access modifiers changed from: 0000 */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1GeneralizedTime)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((ASN1GeneralizedTime) aSN1Primitive).a);
    }

    /* access modifiers changed from: 0000 */
    public void encode(ASN1OutputStream aSN1OutputStream) {
        aSN1OutputStream.a(24, this.a);
    }

    public Date getDate() {
        SimpleDateFormat simpleDateFormat;
        SimpleTimeZone simpleTimeZone;
        String sb;
        StringBuilder sb2;
        String fromByteArray = Strings.fromByteArray(this.a);
        if (fromByteArray.endsWith("Z")) {
            simpleDateFormat = e() ? new SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'") : new SimpleDateFormat("yyyyMMddHHmmss'Z'");
            simpleTimeZone = new SimpleTimeZone(0, "Z");
        } else if (fromByteArray.indexOf(45) > 0 || fromByteArray.indexOf(43) > 0) {
            fromByteArray = getTime();
            simpleDateFormat = e() ? new SimpleDateFormat("yyyyMMddHHmmss.SSSz") : new SimpleDateFormat("yyyyMMddHHmmssz");
            simpleTimeZone = new SimpleTimeZone(0, "Z");
        } else {
            simpleDateFormat = e() ? new SimpleDateFormat("yyyyMMddHHmmss.SSS") : new SimpleDateFormat(ConstantsWS.FORMAT_DATETIME_REQUEST);
            simpleTimeZone = new SimpleTimeZone(0, TimeZone.getDefault().getID());
        }
        simpleDateFormat.setTimeZone(simpleTimeZone);
        if (e()) {
            String substring = fromByteArray.substring(14);
            int i = 1;
            while (i < substring.length()) {
                char charAt = substring.charAt(i);
                if ('0' > charAt || charAt > '9') {
                    break;
                }
                i++;
            }
            int i2 = i - 1;
            if (i2 > 3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(substring.substring(0, 4));
                sb3.append(substring.substring(i));
                sb = sb3.toString();
                sb2 = new StringBuilder();
            } else if (i2 == 1) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(substring.substring(0, i));
                sb4.append("00");
                sb4.append(substring.substring(i));
                sb = sb4.toString();
                sb2 = new StringBuilder();
            } else if (i2 == 2) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(substring.substring(0, i));
                sb5.append("0");
                sb5.append(substring.substring(i));
                sb = sb5.toString();
                sb2 = new StringBuilder();
            }
            sb2.append(fromByteArray.substring(0, 14));
            sb2.append(sb);
            fromByteArray = sb2.toString();
        }
        return simpleDateFormat.parse(fromByteArray);
    }

    public String getTime() {
        String fromByteArray = Strings.fromByteArray(this.a);
        if (fromByteArray.charAt(fromByteArray.length() - 1) == 'Z') {
            StringBuilder sb = new StringBuilder();
            sb.append(fromByteArray.substring(0, fromByteArray.length() - 1));
            sb.append("GMT+00:00");
            return sb.toString();
        }
        int length = fromByteArray.length() - 5;
        char charAt = fromByteArray.charAt(length);
        if (charAt == '-' || charAt == '+') {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(fromByteArray.substring(0, length));
            sb2.append("GMT");
            int i = length + 3;
            sb2.append(fromByteArray.substring(length, i));
            sb2.append(":");
            sb2.append(fromByteArray.substring(i));
            return sb2.toString();
        }
        int length2 = fromByteArray.length() - 3;
        char charAt2 = fromByteArray.charAt(length2);
        if (charAt2 == '-' || charAt2 == '+') {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(fromByteArray.substring(0, length2));
            sb3.append("GMT");
            sb3.append(fromByteArray.substring(length2));
            sb3.append(":00");
            return sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(fromByteArray);
        sb4.append(d());
        return sb4.toString();
    }

    public String getTimeString() {
        return Strings.fromByteArray(this.a);
    }

    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return false;
    }
}
