package org.bouncycastle.asn1.x509;

import ar.com.santander.rio.mbanking.services.soap.constants.ConstantsWS;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.DERGeneralizedTime;
import org.bouncycastle.asn1.DERUTCTime;

public class Time extends ASN1Object implements ASN1Choice {
    ASN1Primitive a;

    public Time(Date date) {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, "Z");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConstantsWS.FORMAT_DATETIME_REQUEST);
        simpleDateFormat.setTimeZone(simpleTimeZone);
        StringBuilder sb = new StringBuilder();
        sb.append(simpleDateFormat.format(date));
        sb.append("Z");
        String sb2 = sb.toString();
        int parseInt = Integer.parseInt(sb2.substring(0, 4));
        this.a = (parseInt < 1950 || parseInt > 2049) ? new DERGeneralizedTime(sb2) : new DERUTCTime(sb2.substring(2));
    }

    public Time(Date date, Locale locale) {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, "Z");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConstantsWS.FORMAT_DATETIME_REQUEST, locale);
        simpleDateFormat.setTimeZone(simpleTimeZone);
        StringBuilder sb = new StringBuilder();
        sb.append(simpleDateFormat.format(date));
        sb.append("Z");
        String sb2 = sb.toString();
        int parseInt = Integer.parseInt(sb2.substring(0, 4));
        this.a = (parseInt < 1950 || parseInt > 2049) ? new DERGeneralizedTime(sb2) : new DERUTCTime(sb2.substring(2));
    }

    public Time(ASN1Primitive aSN1Primitive) {
        if ((aSN1Primitive instanceof ASN1UTCTime) || (aSN1Primitive instanceof ASN1GeneralizedTime)) {
            this.a = aSN1Primitive;
            return;
        }
        throw new IllegalArgumentException("unknown object passed to Time");
    }

    public static Time getInstance(Object obj) {
        if (obj == null || (obj instanceof Time)) {
            return (Time) obj;
        }
        if (obj instanceof ASN1UTCTime) {
            return new Time((ASN1Primitive) (ASN1UTCTime) obj);
        }
        if (obj instanceof ASN1GeneralizedTime) {
            return new Time((ASN1Primitive) (ASN1GeneralizedTime) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown object in factory: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static Time getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public Date getDate() {
        try {
            return this.a instanceof ASN1UTCTime ? ((ASN1UTCTime) this.a).getAdjustedDate() : ((ASN1GeneralizedTime) this.a).getDate();
        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid date string: ");
            sb.append(e.getMessage());
            throw new IllegalStateException(sb.toString());
        }
    }

    public String getTime() {
        return this.a instanceof ASN1UTCTime ? ((ASN1UTCTime) this.a).getAdjustedTime() : ((ASN1GeneralizedTime) this.a).getTime();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }

    public String toString() {
        return getTime();
    }
}
