package org.bouncycastle.asn1.eac;

import com.google.common.primitives.UnsignedBytes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import org.bouncycastle.util.Arrays;

public class PackedDate {
    private byte[] a;

    public PackedDate(String str) {
        this.a = a(str);
    }

    public PackedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd'Z'");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.a = a(simpleDateFormat.format(date));
    }

    public PackedDate(Date date, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd'Z'", locale);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.a = a(simpleDateFormat.format(date));
    }

    PackedDate(byte[] bArr) {
        this.a = bArr;
    }

    private byte[] a(String str) {
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[6];
        for (int i = 0; i != 6; i++) {
            bArr[i] = (byte) (charArray[i] - '0');
        }
        return bArr;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PackedDate)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((PackedDate) obj).a);
    }

    public Date getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        StringBuilder sb = new StringBuilder();
        sb.append("20");
        sb.append(toString());
        return simpleDateFormat.parse(sb.toString());
    }

    public byte[] getEncoding() {
        return this.a;
    }

    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    public String toString() {
        char[] cArr = new char[this.a.length];
        for (int i = 0; i != cArr.length; i++) {
            cArr[i] = (char) ((this.a[i] & UnsignedBytes.MAX_VALUE) + 48);
        }
        return new String(cArr);
    }
}
