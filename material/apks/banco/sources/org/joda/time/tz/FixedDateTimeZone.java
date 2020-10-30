package org.joda.time.tz;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.joda.time.DateTimeZone;

public final class FixedDateTimeZone extends DateTimeZone {
    private static final long serialVersionUID = -3513011772763289092L;
    private final String a;
    private final int b;
    private final int c;

    public boolean isFixed() {
        return true;
    }

    public long nextTransition(long j) {
        return j;
    }

    public long previousTransition(long j) {
        return j;
    }

    public FixedDateTimeZone(String str, String str2, int i, int i2) {
        super(str);
        this.a = str2;
        this.b = i;
        this.c = i2;
    }

    public String getNameKey(long j) {
        return this.a;
    }

    public int getOffset(long j) {
        return this.b;
    }

    public int getStandardOffset(long j) {
        return this.c;
    }

    public int getOffsetFromLocal(long j) {
        return this.b;
    }

    public TimeZone toTimeZone() {
        String id2 = getID();
        if (id2.length() != 6 || (!id2.startsWith(Constants.SYMBOL_POSITIVE) && !id2.startsWith("-"))) {
            return new SimpleTimeZone(this.b, getID());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("GMT");
        sb.append(getID());
        return TimeZone.getTimeZone(sb.toString());
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FixedDateTimeZone)) {
            return false;
        }
        FixedDateTimeZone fixedDateTimeZone = (FixedDateTimeZone) obj;
        if (!(getID().equals(fixedDateTimeZone.getID()) && this.c == fixedDateTimeZone.c && this.b == fixedDateTimeZone.b)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return getID().hashCode() + (this.c * 37) + (this.b * 31);
    }
}
