package org.joda.time.format;

import cz.msebera.android.httpclient.message.TokenParser;
import java.util.Arrays;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.IllegalInstantException;

public class DateTimeParserBucket {
    private final Chronology a;
    private final long b;
    private final Locale c;
    private final int d;
    private final DateTimeZone e;
    private final Integer f;
    /* access modifiers changed from: private */
    public DateTimeZone g;
    /* access modifiers changed from: private */
    public Integer h;
    private Integer i;
    /* access modifiers changed from: private */
    public SavedField[] j;
    /* access modifiers changed from: private */
    public int k;
    /* access modifiers changed from: private */
    public boolean l;
    private Object m;

    static class SavedField implements Comparable<SavedField> {
        DateTimeField a;
        int b;
        String c;
        Locale d;

        SavedField() {
        }

        /* access modifiers changed from: 0000 */
        public void a(DateTimeField dateTimeField, int i) {
            this.a = dateTimeField;
            this.b = i;
            this.c = null;
            this.d = null;
        }

        /* access modifiers changed from: 0000 */
        public void a(DateTimeField dateTimeField, String str, Locale locale) {
            this.a = dateTimeField;
            this.b = 0;
            this.c = str;
            this.d = locale;
        }

        /* access modifiers changed from: 0000 */
        public long a(long j, boolean z) {
            long j2;
            if (this.c == null) {
                j2 = this.a.setExtended(j, this.b);
            } else {
                j2 = this.a.set(j, this.c, this.d);
            }
            return z ? this.a.roundFloor(j2) : j2;
        }

        /* renamed from: a */
        public int compareTo(SavedField savedField) {
            DateTimeField dateTimeField = savedField.a;
            int a2 = DateTimeParserBucket.a(this.a.getRangeDurationField(), dateTimeField.getRangeDurationField());
            if (a2 != 0) {
                return a2;
            }
            return DateTimeParserBucket.a(this.a.getDurationField(), dateTimeField.getDurationField());
        }
    }

    class SavedState {
        final DateTimeZone a;
        final Integer b;
        final SavedField[] c;
        final int d;

        SavedState() {
            this.a = DateTimeParserBucket.this.g;
            this.b = DateTimeParserBucket.this.h;
            this.c = DateTimeParserBucket.this.j;
            this.d = DateTimeParserBucket.this.k;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(DateTimeParserBucket dateTimeParserBucket) {
            if (dateTimeParserBucket != DateTimeParserBucket.this) {
                return false;
            }
            dateTimeParserBucket.g = this.a;
            dateTimeParserBucket.h = this.b;
            dateTimeParserBucket.j = this.c;
            if (this.d < dateTimeParserBucket.k) {
                dateTimeParserBucket.l = true;
            }
            dateTimeParserBucket.k = this.d;
            return true;
        }
    }

    @Deprecated
    public DateTimeParserBucket(long j2, Chronology chronology, Locale locale) {
        this(j2, chronology, locale, null, 2000);
    }

    @Deprecated
    public DateTimeParserBucket(long j2, Chronology chronology, Locale locale, Integer num) {
        this(j2, chronology, locale, num, 2000);
    }

    public DateTimeParserBucket(long j2, Chronology chronology, Locale locale, Integer num, int i2) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        this.b = j2;
        this.e = chronology2.getZone();
        this.a = chronology2.withUTC();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        this.c = locale;
        this.d = i2;
        this.f = num;
        this.g = this.e;
        this.i = this.f;
        this.j = new SavedField[8];
    }

    public void reset() {
        this.g = this.e;
        this.h = null;
        this.i = this.f;
        this.k = 0;
        this.l = false;
        this.m = null;
    }

    public long parseMillis(DateTimeParser dateTimeParser, CharSequence charSequence) {
        reset();
        return a(DateTimeParserInternalParser.a(dateTimeParser), charSequence);
    }

    /* access modifiers changed from: 0000 */
    public long a(InternalParser internalParser, CharSequence charSequence) {
        int a2 = internalParser.a(this, charSequence, 0);
        if (a2 < 0) {
            a2 ^= -1;
        } else if (a2 >= charSequence.length()) {
            return computeMillis(true, charSequence);
        }
        throw new IllegalArgumentException(FormatUtils.a(charSequence.toString(), a2));
    }

    public Chronology getChronology() {
        return this.a;
    }

    public Locale getLocale() {
        return this.c;
    }

    public DateTimeZone getZone() {
        return this.g;
    }

    public void setZone(DateTimeZone dateTimeZone) {
        this.m = null;
        this.g = dateTimeZone;
    }

    @Deprecated
    public int getOffset() {
        if (this.h != null) {
            return this.h.intValue();
        }
        return 0;
    }

    public Integer getOffsetInteger() {
        return this.h;
    }

    @Deprecated
    public void setOffset(int i2) {
        this.m = null;
        this.h = Integer.valueOf(i2);
    }

    public void setOffset(Integer num) {
        this.m = null;
        this.h = num;
    }

    public Integer getPivotYear() {
        return this.i;
    }

    @Deprecated
    public void setPivotYear(Integer num) {
        this.i = num;
    }

    public void saveField(DateTimeField dateTimeField, int i2) {
        a().a(dateTimeField, i2);
    }

    public void saveField(DateTimeFieldType dateTimeFieldType, int i2) {
        a().a(dateTimeFieldType.getField(this.a), i2);
    }

    public void saveField(DateTimeFieldType dateTimeFieldType, String str, Locale locale) {
        a().a(dateTimeFieldType.getField(this.a), str, locale);
    }

    private SavedField a() {
        SavedField[] savedFieldArr = this.j;
        int i2 = this.k;
        if (i2 == savedFieldArr.length || this.l) {
            SavedField[] savedFieldArr2 = new SavedField[(i2 == savedFieldArr.length ? i2 * 2 : savedFieldArr.length)];
            System.arraycopy(savedFieldArr, 0, savedFieldArr2, 0, i2);
            this.j = savedFieldArr2;
            this.l = false;
            savedFieldArr = savedFieldArr2;
        }
        this.m = null;
        SavedField savedField = savedFieldArr[i2];
        if (savedField == null) {
            savedField = new SavedField();
            savedFieldArr[i2] = savedField;
        }
        this.k = i2 + 1;
        return savedField;
    }

    public Object saveState() {
        if (this.m == null) {
            this.m = new SavedState();
        }
        return this.m;
    }

    public boolean restoreState(Object obj) {
        if (!(obj instanceof SavedState) || !((SavedState) obj).a(this)) {
            return false;
        }
        this.m = obj;
        return true;
    }

    public long computeMillis() {
        return computeMillis(false, (CharSequence) null);
    }

    public long computeMillis(boolean z) {
        return computeMillis(z, (CharSequence) null);
    }

    public long computeMillis(boolean z, String str) {
        return computeMillis(z, (CharSequence) str);
    }

    public long computeMillis(boolean z, CharSequence charSequence) {
        long j2;
        SavedField[] savedFieldArr = this.j;
        int i2 = this.k;
        if (this.l) {
            savedFieldArr = (SavedField[]) this.j.clone();
            this.j = savedFieldArr;
            this.l = false;
        }
        a(savedFieldArr, i2);
        if (i2 > 0) {
            DurationField field = DurationFieldType.months().getField(this.a);
            DurationField field2 = DurationFieldType.days().getField(this.a);
            DurationField durationField = savedFieldArr[0].a.getDurationField();
            if (a(durationField, field) >= 0 && a(durationField, field2) <= 0) {
                saveField(DateTimeFieldType.year(), this.d);
                return computeMillis(z, charSequence);
            }
        }
        long j3 = this.b;
        int i3 = 0;
        while (i3 < i2) {
            try {
                j3 = savedFieldArr[i3].a(j3, z);
                i3++;
            } catch (IllegalFieldValueException e2) {
                if (charSequence != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Cannot parse \"");
                    sb.append(charSequence);
                    sb.append(TokenParser.DQUOTE);
                    e2.prependMessage(sb.toString());
                }
                throw e2;
            }
        }
        if (z) {
            int i4 = 0;
            while (i4 < i2) {
                j3 = savedFieldArr[i4].a(j3, i4 == i2 + -1);
                i4++;
            }
        }
        if (this.h != null) {
            j2 = j3 - ((long) this.h.intValue());
        } else if (this.g != null) {
            int offsetFromLocal = this.g.getOffsetFromLocal(j3);
            long j4 = j3 - ((long) offsetFromLocal);
            if (offsetFromLocal != this.g.getOffset(j4)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Illegal instant due to time zone offset transition (");
                sb2.append(this.g);
                sb2.append(')');
                String sb3 = sb2.toString();
                if (charSequence != null) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Cannot parse \"");
                    sb4.append(charSequence);
                    sb4.append("\": ");
                    sb4.append(sb3);
                    sb3 = sb4.toString();
                }
                throw new IllegalInstantException(sb3);
            }
            j2 = j4;
        } else {
            j2 = j3;
        }
        return j2;
    }

    private static void a(SavedField[] savedFieldArr, int i2) {
        if (i2 > 10) {
            Arrays.sort(savedFieldArr, 0, i2);
            return;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = i3; i4 > 0; i4--) {
                int i5 = i4 - 1;
                if (savedFieldArr[i5].compareTo(savedFieldArr[i4]) <= 0) {
                    break;
                }
                SavedField savedField = savedFieldArr[i4];
                savedFieldArr[i4] = savedFieldArr[i5];
                savedFieldArr[i5] = savedField;
            }
        }
    }

    static int a(DurationField durationField, DurationField durationField2) {
        if (durationField == null || !durationField.isSupported()) {
            return (durationField2 == null || !durationField2.isSupported()) ? 0 : -1;
        }
        if (durationField2 == null || !durationField2.isSupported()) {
            return 1;
        }
        return -durationField.compareTo(durationField2);
    }
}
