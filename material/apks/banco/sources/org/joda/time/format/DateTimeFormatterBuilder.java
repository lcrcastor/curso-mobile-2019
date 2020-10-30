package org.joda.time.format;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.MutableDateTime.Property;
import org.joda.time.ReadablePartial;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.PreciseDateTimeField;

public class DateTimeFormatterBuilder {
    private ArrayList<Object> a = new ArrayList<>();
    private Object b;

    static class CharacterLiteral implements InternalParser, InternalPrinter {
        private final char a;

        public int estimateParsedLength() {
            return 1;
        }

        public int estimatePrintedLength() {
            return 1;
        }

        CharacterLiteral(char c) {
            this.a = c;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            appendable.append(this.a);
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            appendable.append(this.a);
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            if (i >= charSequence.length()) {
                return i ^ -1;
            }
            char charAt = charSequence.charAt(i);
            char c = this.a;
            if (charAt != c) {
                char upperCase = Character.toUpperCase(charAt);
                char upperCase2 = Character.toUpperCase(c);
                if (!(upperCase == upperCase2 || Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2))) {
                    return i ^ -1;
                }
            }
            return i + 1;
        }
    }

    static class Composite implements InternalParser, InternalPrinter {
        private final InternalPrinter[] a;
        private final InternalParser[] b;
        private final int c;
        private final int d;

        Composite(List<Object> list) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            a(list, (List<Object>) arrayList, (List<Object>) arrayList2);
            if (arrayList.contains(null) || arrayList.isEmpty()) {
                this.a = null;
                this.c = 0;
            } else {
                int size = arrayList.size();
                this.a = new InternalPrinter[size];
                int i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    InternalPrinter internalPrinter = (InternalPrinter) arrayList.get(i2);
                    i += internalPrinter.estimatePrintedLength();
                    this.a[i2] = internalPrinter;
                }
                this.c = i;
            }
            if (arrayList2.contains(null) || arrayList2.isEmpty()) {
                this.b = null;
                this.d = 0;
                return;
            }
            int size2 = arrayList2.size();
            this.b = new InternalParser[size2];
            int i3 = 0;
            for (int i4 = 0; i4 < size2; i4++) {
                InternalParser internalParser = (InternalParser) arrayList2.get(i4);
                i3 += internalParser.estimateParsedLength();
                this.b[i4] = internalParser;
            }
            this.d = i3;
        }

        public int estimatePrintedLength() {
            return this.c;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            InternalPrinter[] internalPrinterArr = this.a;
            if (internalPrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            Locale locale2 = locale == null ? Locale.getDefault() : locale;
            for (InternalPrinter a2 : internalPrinterArr) {
                a2.a(appendable, j, chronology, i, dateTimeZone, locale2);
            }
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            InternalPrinter[] internalPrinterArr = this.a;
            if (internalPrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            for (InternalPrinter a2 : internalPrinterArr) {
                a2.a(appendable, readablePartial, locale);
            }
        }

        public int estimateParsedLength() {
            return this.d;
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            InternalParser[] internalParserArr = this.b;
            if (internalParserArr == null) {
                throw new UnsupportedOperationException();
            }
            int length = internalParserArr.length;
            for (int i2 = 0; i2 < length && i >= 0; i2++) {
                i = internalParserArr[i2].a(dateTimeParserBucket, charSequence, i);
            }
            return i;
        }

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return this.a != null;
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return this.b != null;
        }

        private void a(List<Object> list, List<Object> list2, List<Object> list3) {
            int size = list.size();
            for (int i = 0; i < size; i += 2) {
                Object obj = list.get(i);
                if (obj instanceof Composite) {
                    a(list2, ((Composite) obj).a);
                } else {
                    list2.add(obj);
                }
                Object obj2 = list.get(i + 1);
                if (obj2 instanceof Composite) {
                    a(list3, ((Composite) obj2).b);
                } else {
                    list3.add(obj2);
                }
            }
        }

        private void a(List<Object> list, Object[] objArr) {
            if (objArr != null) {
                for (Object add : objArr) {
                    list.add(add);
                }
            }
        }
    }

    static class FixedNumber extends PaddedNumber {
        protected FixedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            super(dateTimeFieldType, i, z, i);
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            int a = super.a(dateTimeParserBucket, charSequence, i);
            if (a < 0) {
                return a;
            }
            int i2 = this.b + i;
            if (a != i2) {
                if (this.c) {
                    char charAt = charSequence.charAt(i);
                    if (charAt == '-' || charAt == '+') {
                        i2++;
                    }
                }
                if (a > i2) {
                    return (i2 + 1) ^ -1;
                }
                if (a < i2) {
                    return a ^ -1;
                }
            }
            return a;
        }
    }

    static class Fraction implements InternalParser, InternalPrinter {
        protected int a;
        protected int b;
        private final DateTimeFieldType c;

        protected Fraction(DateTimeFieldType dateTimeFieldType, int i, int i2) {
            this.c = dateTimeFieldType;
            int i3 = 18;
            if (i2 <= 18) {
                i3 = i2;
            }
            this.a = i;
            this.b = i3;
        }

        public int estimatePrintedLength() {
            return this.b;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            a(appendable, j, chronology);
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            a(appendable, readablePartial.getChronology().set(readablePartial, 0), readablePartial.getChronology());
        }

        /* access modifiers changed from: protected */
        public void a(Appendable appendable, long j, Chronology chronology) {
            String str;
            DateTimeField field = this.c.getField(chronology);
            int i = this.a;
            try {
                long remainder = field.remainder(j);
                if (remainder == 0) {
                    while (true) {
                        i--;
                        if (i >= 0) {
                            appendable.append(TarjetasConstants.ULT_NUM_AMEX);
                        } else {
                            return;
                        }
                    }
                } else {
                    long[] a2 = a(remainder, field);
                    long j2 = a2[0];
                    int i2 = (int) a2[1];
                    if ((j2 & 2147483647L) == j2) {
                        str = Integer.toString((int) j2);
                    } else {
                        str = Long.toString(j2);
                    }
                    int length = str.length();
                    while (length < i2) {
                        appendable.append(TarjetasConstants.ULT_NUM_AMEX);
                        i--;
                        i2--;
                    }
                    if (i < i2) {
                        while (i < i2 && length > 1 && str.charAt(length - 1) == '0') {
                            i2--;
                            length--;
                        }
                        if (length < str.length()) {
                            for (int i3 = 0; i3 < length; i3++) {
                                appendable.append(str.charAt(i3));
                            }
                            return;
                        }
                    }
                    appendable.append(str);
                }
            } catch (RuntimeException unused) {
                DateTimeFormatterBuilder.a(appendable, i);
            }
        }

        private long[] a(long j, DateTimeField dateTimeField) {
            long j2;
            long unitMillis = dateTimeField.getDurationField().getUnitMillis();
            int i = this.b;
            while (true) {
                switch (i) {
                    case 1:
                        j2 = 10;
                        break;
                    case 2:
                        j2 = 100;
                        break;
                    case 3:
                        j2 = 1000;
                        break;
                    case 4:
                        j2 = LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS;
                        break;
                    case 5:
                        j2 = 100000;
                        break;
                    case 6:
                        j2 = 1000000;
                        break;
                    case 7:
                        j2 = 10000000;
                        break;
                    case 8:
                        j2 = 100000000;
                        break;
                    case 9:
                        j2 = 1000000000;
                        break;
                    case 10:
                        j2 = 10000000000L;
                        break;
                    case 11:
                        j2 = 100000000000L;
                        break;
                    case 12:
                        j2 = 1000000000000L;
                        break;
                    case 13:
                        j2 = 10000000000000L;
                        break;
                    case 14:
                        j2 = 100000000000000L;
                        break;
                    case 15:
                        j2 = 1000000000000000L;
                        break;
                    case 16:
                        j2 = 10000000000000000L;
                        break;
                    case 17:
                        j2 = 100000000000000000L;
                        break;
                    case 18:
                        j2 = 1000000000000000000L;
                        break;
                    default:
                        j2 = 1;
                        break;
                }
                if ((unitMillis * j2) / j2 == unitMillis) {
                    return new long[]{(j * j2) / unitMillis, (long) i};
                }
                i--;
            }
        }

        public int estimateParsedLength() {
            return this.b;
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            DateTimeField field = this.c.getField(dateTimeParserBucket.getChronology());
            int min = Math.min(this.b, charSequence.length() - i);
            long unitMillis = field.getDurationField().getUnitMillis() * 10;
            long j = 0;
            int i2 = 0;
            while (i2 < min) {
                char charAt = charSequence.charAt(i + i2);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i2++;
                unitMillis /= 10;
                j += ((long) (charAt - '0')) * unitMillis;
            }
            long j2 = j / 10;
            if (i2 == 0) {
                return i ^ -1;
            }
            if (j2 > 2147483647L) {
                return i ^ -1;
            }
            dateTimeParserBucket.saveField((DateTimeField) new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), MillisDurationField.INSTANCE, field.getDurationField()), (int) j2);
            return i + i2;
        }
    }

    static class MatchingParser implements InternalParser {
        private final InternalParser[] a;
        private final int b;

        MatchingParser(InternalParser[] internalParserArr) {
            this.a = internalParserArr;
            int length = internalParserArr.length;
            int i = 0;
            while (true) {
                length--;
                if (length >= 0) {
                    InternalParser internalParser = internalParserArr[length];
                    if (internalParser != null) {
                        int estimateParsedLength = internalParser.estimateParsedLength();
                        if (estimateParsedLength > i) {
                            i = estimateParsedLength;
                        }
                    }
                } else {
                    this.b = i;
                    return;
                }
            }
        }

        public int estimateParsedLength() {
            return this.b;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0036, code lost:
            return r8;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int a(org.joda.time.format.DateTimeParserBucket r10, java.lang.CharSequence r11, int r12) {
            /*
                r9 = this;
                org.joda.time.format.InternalParser[] r0 = r9.a
                int r1 = r0.length
                java.lang.Object r2 = r10.saveState()
                r3 = 0
                r4 = 0
                r5 = r12
                r7 = r5
                r6 = r4
                r4 = 0
            L_0x000d:
                if (r4 >= r1) goto L_0x0044
                r8 = r0[r4]
                if (r8 != 0) goto L_0x0018
                if (r5 > r12) goto L_0x0016
                return r12
            L_0x0016:
                r3 = 1
                goto L_0x0044
            L_0x0018:
                int r8 = r8.a(r10, r11, r12)
                if (r8 < r12) goto L_0x0037
                if (r8 <= r5) goto L_0x003e
                int r5 = r11.length()
                if (r8 >= r5) goto L_0x0036
                int r5 = r4 + 1
                if (r5 >= r1) goto L_0x0036
                r5 = r0[r5]
                if (r5 != 0) goto L_0x002f
                goto L_0x0036
            L_0x002f:
                java.lang.Object r5 = r10.saveState()
                r6 = r5
                r5 = r8
                goto L_0x003e
            L_0x0036:
                return r8
            L_0x0037:
                if (r8 >= 0) goto L_0x003e
                r8 = r8 ^ -1
                if (r8 <= r7) goto L_0x003e
                r7 = r8
            L_0x003e:
                r10.restoreState(r2)
                int r4 = r4 + 1
                goto L_0x000d
            L_0x0044:
                if (r5 > r12) goto L_0x004e
                if (r5 != r12) goto L_0x004b
                if (r3 == 0) goto L_0x004b
                goto L_0x004e
            L_0x004b:
                r10 = r7 ^ -1
                return r10
            L_0x004e:
                if (r6 == 0) goto L_0x0053
                r10.restoreState(r6)
            L_0x0053:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.MatchingParser.a(org.joda.time.format.DateTimeParserBucket, java.lang.CharSequence, int):int");
        }
    }

    static abstract class NumberFormatter implements InternalParser, InternalPrinter {
        protected final DateTimeFieldType a;
        protected final int b;
        protected final boolean c;

        NumberFormatter(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            this.a = dateTimeFieldType;
            this.b = i;
            this.c = z;
        }

        public int estimateParsedLength() {
            return this.b;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0061, code lost:
            r15 = r7;
            r7 = r6;
            r6 = r15;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int a(org.joda.time.format.DateTimeParserBucket r17, java.lang.CharSequence r18, int r19) {
            /*
                r16 = this;
                r0 = r16
                r1 = r18
                r2 = r19
                int r3 = r0.b
                int r4 = r18.length()
                int r4 = r4 - r2
                int r3 = java.lang.Math.min(r3, r4)
                r4 = 0
                r5 = r3
                r3 = 0
                r6 = 0
                r7 = 0
            L_0x0016:
                r8 = 48
                if (r3 >= r5) goto L_0x0061
                int r9 = r2 + r3
                char r10 = r1.charAt(r9)
                r11 = 57
                if (r3 != 0) goto L_0x0059
                r12 = 43
                r13 = 45
                if (r10 == r13) goto L_0x002c
                if (r10 != r12) goto L_0x0059
            L_0x002c:
                boolean r14 = r0.c
                if (r14 == 0) goto L_0x0059
                r6 = 1
                if (r10 != r13) goto L_0x0035
                r7 = 1
                goto L_0x0036
            L_0x0035:
                r7 = 0
            L_0x0036:
                if (r10 != r12) goto L_0x0039
                goto L_0x003a
            L_0x0039:
                r6 = 0
            L_0x003a:
                int r10 = r3 + 1
                if (r10 >= r5) goto L_0x0064
                int r9 = r9 + 1
                char r9 = r1.charAt(r9)
                if (r9 < r8) goto L_0x0064
                if (r9 <= r11) goto L_0x0049
                goto L_0x0064
            L_0x0049:
                int r5 = r5 + 1
                int r3 = r18.length()
                int r3 = r3 - r2
                int r5 = java.lang.Math.min(r5, r3)
                r3 = r10
                r15 = r7
                r7 = r6
                r6 = r15
                goto L_0x0016
            L_0x0059:
                if (r10 < r8) goto L_0x0061
                if (r10 <= r11) goto L_0x005e
                goto L_0x0061
            L_0x005e:
                int r3 = r3 + 1
                goto L_0x0016
            L_0x0061:
                r15 = r7
                r7 = r6
                r6 = r15
            L_0x0064:
                if (r3 != 0) goto L_0x0069
                r1 = r2 ^ -1
                return r1
            L_0x0069:
                r4 = 9
                if (r3 < r4) goto L_0x008e
                if (r6 == 0) goto L_0x007f
                int r4 = r2 + 1
                int r2 = r2 + r3
                java.lang.CharSequence r1 = r1.subSequence(r4, r2)
                java.lang.String r1 = r1.toString()
                int r1 = java.lang.Integer.parseInt(r1)
                goto L_0x00b6
            L_0x007f:
                int r3 = r3 + r2
                java.lang.CharSequence r1 = r1.subSequence(r2, r3)
                java.lang.String r1 = r1.toString()
                int r1 = java.lang.Integer.parseInt(r1)
                r2 = r3
                goto L_0x00b6
            L_0x008e:
                if (r7 != 0) goto L_0x0095
                if (r6 == 0) goto L_0x0093
                goto L_0x0095
            L_0x0093:
                r4 = r2
                goto L_0x0097
            L_0x0095:
                int r4 = r2 + 1
            L_0x0097:
                int r5 = r4 + 1
                char r4 = r1.charAt(r4)     // Catch:{ StringIndexOutOfBoundsException -> 0x00be }
                int r4 = r4 - r8
                int r2 = r2 + r3
            L_0x009f:
                if (r5 >= r2) goto L_0x00b1
                int r3 = r4 << 3
                int r4 = r4 << 1
                int r3 = r3 + r4
                int r4 = r5 + 1
                char r5 = r1.charAt(r5)
                int r3 = r3 + r5
                int r3 = r3 - r8
                r5 = r4
                r4 = r3
                goto L_0x009f
            L_0x00b1:
                if (r7 == 0) goto L_0x00b5
                int r1 = -r4
                goto L_0x00b6
            L_0x00b5:
                r1 = r4
            L_0x00b6:
                org.joda.time.DateTimeFieldType r3 = r0.a
                r4 = r17
                r4.saveField(r3, r1)
                return r2
            L_0x00be:
                r1 = r2 ^ -1
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.NumberFormatter.a(org.joda.time.format.DateTimeParserBucket, java.lang.CharSequence, int):int");
        }
    }

    static class PaddedNumber extends NumberFormatter {
        protected final int d;

        protected PaddedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z, int i2) {
            super(dateTimeFieldType, i, z);
            this.d = i2;
        }

        public int estimatePrintedLength() {
            return this.b;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            try {
                FormatUtils.appendPaddedInteger(appendable, this.a.getField(chronology).get(j), this.d);
            } catch (RuntimeException unused) {
                DateTimeFormatterBuilder.a(appendable, this.d);
            }
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            if (readablePartial.isSupported(this.a)) {
                try {
                    FormatUtils.appendPaddedInteger(appendable, readablePartial.get(this.a), this.d);
                } catch (RuntimeException unused) {
                    DateTimeFormatterBuilder.a(appendable, this.d);
                }
            } else {
                DateTimeFormatterBuilder.a(appendable, this.d);
            }
        }
    }

    static class StringLiteral implements InternalParser, InternalPrinter {
        private final String a;

        StringLiteral(String str) {
            this.a = str;
        }

        public int estimatePrintedLength() {
            return this.a.length();
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            appendable.append(this.a);
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            appendable.append(this.a);
        }

        public int estimateParsedLength() {
            return this.a.length();
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            return DateTimeFormatterBuilder.c(charSequence, i, this.a) ? i + this.a.length() : i ^ -1;
        }
    }

    static class TextField implements InternalParser, InternalPrinter {
        private static Map<Locale, Map<DateTimeFieldType, Object[]>> a = new ConcurrentHashMap();
        private final DateTimeFieldType b;
        private final boolean c;

        TextField(DateTimeFieldType dateTimeFieldType, boolean z) {
            this.b = dateTimeFieldType;
            this.c = z;
        }

        public int estimatePrintedLength() {
            return this.c ? 6 : 20;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            try {
                appendable.append(a(j, chronology, locale));
            } catch (RuntimeException unused) {
                appendable.append(65533);
            }
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            try {
                appendable.append(a(readablePartial, locale));
            } catch (RuntimeException unused) {
                appendable.append(65533);
            }
        }

        private String a(long j, Chronology chronology, Locale locale) {
            DateTimeField field = this.b.getField(chronology);
            if (this.c) {
                return field.getAsShortText(j, locale);
            }
            return field.getAsText(j, locale);
        }

        private String a(ReadablePartial readablePartial, Locale locale) {
            if (!readablePartial.isSupported(this.b)) {
                return "�";
            }
            DateTimeField field = this.b.getField(readablePartial.getChronology());
            if (this.c) {
                return field.getAsShortText(readablePartial, locale);
            }
            return field.getAsText(readablePartial, locale);
        }

        public int estimateParsedLength() {
            return estimatePrintedLength();
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            int i2;
            Map map;
            Locale locale = dateTimeParserBucket.getLocale();
            Map map2 = (Map) a.get(locale);
            if (map2 == null) {
                map2 = new ConcurrentHashMap();
                a.put(locale, map2);
            }
            Object[] objArr = (Object[]) map2.get(this.b);
            if (objArr == null) {
                Map concurrentHashMap = new ConcurrentHashMap(32);
                Property property = new MutableDateTime(0, DateTimeZone.UTC).property(this.b);
                int minimumValueOverall = property.getMinimumValueOverall();
                int maximumValueOverall = property.getMaximumValueOverall();
                if (maximumValueOverall - minimumValueOverall > 32) {
                    return i ^ -1;
                }
                i2 = property.getMaximumTextLength(locale);
                while (minimumValueOverall <= maximumValueOverall) {
                    property.set(minimumValueOverall);
                    concurrentHashMap.put(property.getAsShortText(locale), Boolean.TRUE);
                    concurrentHashMap.put(property.getAsShortText(locale).toLowerCase(locale), Boolean.TRUE);
                    concurrentHashMap.put(property.getAsShortText(locale).toUpperCase(locale), Boolean.TRUE);
                    concurrentHashMap.put(property.getAsText(locale), Boolean.TRUE);
                    concurrentHashMap.put(property.getAsText(locale).toLowerCase(locale), Boolean.TRUE);
                    concurrentHashMap.put(property.getAsText(locale).toUpperCase(locale), Boolean.TRUE);
                    minimumValueOverall++;
                }
                if ("en".equals(locale.getLanguage()) && this.b == DateTimeFieldType.era()) {
                    concurrentHashMap.put("BCE", Boolean.TRUE);
                    concurrentHashMap.put("bce", Boolean.TRUE);
                    concurrentHashMap.put("CE", Boolean.TRUE);
                    concurrentHashMap.put("ce", Boolean.TRUE);
                    i2 = 3;
                }
                map2.put(this.b, new Object[]{concurrentHashMap, Integer.valueOf(i2)});
                map = concurrentHashMap;
            } else {
                map = (Map) objArr[0];
                i2 = ((Integer) objArr[1]).intValue();
            }
            for (int min = Math.min(charSequence.length(), i2 + i); min > i; min--) {
                String obj = charSequence.subSequence(i, min).toString();
                if (map.containsKey(obj)) {
                    dateTimeParserBucket.saveField(this.b, obj, locale);
                    return min;
                }
            }
            return i ^ -1;
        }
    }

    enum TimeZoneId implements InternalParser, InternalPrinter {
        INSTANCE;
        
        static final int b = 0;
        private static final List<String> c = null;

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
        }

        static {
            int i;
            c = new ArrayList(DateTimeZone.getAvailableIDs());
            Collections.sort(c);
            for (String length : c) {
                i = Math.max(i, length.length());
            }
            b = i;
        }

        public int estimatePrintedLength() {
            return b;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            appendable.append(dateTimeZone != null ? dateTimeZone.getID() : "");
        }

        public int estimateParsedLength() {
            return b;
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            String str = null;
            for (int a = a(charSequence, i); a < c.size(); a++) {
                String str2 = (String) c.get(a);
                if (!DateTimeFormatterBuilder.b(charSequence, i, str2)) {
                    break;
                }
                if (str == null || str2.length() > str.length()) {
                    str = str2;
                }
            }
            if (str == null) {
                return i ^ -1;
            }
            dateTimeParserBucket.setZone(DateTimeZone.forID(str));
            return i + str.length();
        }

        private static int a(CharSequence charSequence, int i) {
            int size = c.size() - 1;
            int i2 = 0;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                int a = DateTimeFormatterBuilder.a(charSequence, i, (String) c.get(i3));
                if (a > 0) {
                    size = i3 - 1;
                } else if (a >= 0) {
                    return i3;
                } else {
                    i2 = i3 + 1;
                }
            }
            return i2;
        }
    }

    static class TimeZoneName implements InternalParser, InternalPrinter {
        private final Map<String, DateTimeZone> a;
        private final int b;

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
        }

        TimeZoneName(int i, Map<String, DateTimeZone> map) {
            this.b = i;
            this.a = map;
        }

        public int estimatePrintedLength() {
            return this.b == 1 ? 4 : 20;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            appendable.append(a(j - ((long) i), dateTimeZone, locale));
        }

        private String a(long j, DateTimeZone dateTimeZone, Locale locale) {
            if (dateTimeZone == null) {
                return "";
            }
            switch (this.b) {
                case 0:
                    return dateTimeZone.getName(j, locale);
                case 1:
                    return dateTimeZone.getShortName(j, locale);
                default:
                    return "";
            }
        }

        public int estimateParsedLength() {
            return this.b == 1 ? 4 : 20;
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            Map<String, DateTimeZone> map = this.a;
            if (map == null) {
                map = DateTimeUtils.getDefaultTimeZoneNames();
            }
            String str = null;
            for (String str2 : map.keySet()) {
                if (DateTimeFormatterBuilder.b(charSequence, i, str2) && (str == null || str2.length() > str.length())) {
                    str = str2;
                }
            }
            if (str == null) {
                return i ^ -1;
            }
            dateTimeParserBucket.setZone((DateTimeZone) map.get(str));
            return i + str.length();
        }
    }

    static class TimeZoneOffset implements InternalParser, InternalPrinter {
        private final String a;
        private final String b;
        private final boolean c;
        private final int d;
        private final int e;

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
        }

        TimeZoneOffset(String str, String str2, boolean z, int i, int i2) {
            this.a = str;
            this.b = str2;
            this.c = z;
            if (i <= 0 || i2 < i) {
                throw new IllegalArgumentException();
            }
            int i3 = 4;
            if (i > 4) {
                i2 = 4;
            } else {
                i3 = i;
            }
            this.d = i3;
            this.e = i2;
        }

        public int estimatePrintedLength() {
            int i = (this.d + 1) << 1;
            if (this.c) {
                i += this.d - 1;
            }
            return (this.a == null || this.a.length() <= i) ? i : this.a.length();
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            if (dateTimeZone != null) {
                if (i != 0 || this.a == null) {
                    if (i >= 0) {
                        appendable.append('+');
                    } else {
                        appendable.append('-');
                        i = -i;
                    }
                    int i2 = i / DateTimeConstants.MILLIS_PER_HOUR;
                    FormatUtils.appendPaddedInteger(appendable, i2, 2);
                    if (this.e != 1) {
                        int i3 = i - (i2 * DateTimeConstants.MILLIS_PER_HOUR);
                        if (i3 != 0 || this.d > 1) {
                            int i4 = i3 / DateTimeConstants.MILLIS_PER_MINUTE;
                            if (this.c) {
                                appendable.append(':');
                            }
                            FormatUtils.appendPaddedInteger(appendable, i4, 2);
                            if (this.e != 2) {
                                int i5 = i3 - (i4 * DateTimeConstants.MILLIS_PER_MINUTE);
                                if (i5 != 0 || this.d > 2) {
                                    int i6 = i5 / 1000;
                                    if (this.c) {
                                        appendable.append(':');
                                    }
                                    FormatUtils.appendPaddedInteger(appendable, i6, 2);
                                    if (this.e != 3) {
                                        int i7 = i5 - (i6 * 1000);
                                        if (i7 != 0 || this.d > 3) {
                                            if (this.c) {
                                                appendable.append('.');
                                            }
                                            FormatUtils.appendPaddedInteger(appendable, i7, 3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                appendable.append(this.a);
            }
        }

        public int estimateParsedLength() {
            return estimatePrintedLength();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0089, code lost:
            if (r6 <= '9') goto L_0x008b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x001e, code lost:
            if (r1 != '+') goto L_0x0021;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int a(org.joda.time.format.DateTimeParserBucket r12, java.lang.CharSequence r13, int r14) {
            /*
                r11 = this;
                int r0 = r13.length()
                int r0 = r0 - r14
                java.lang.String r1 = r11.b
                r2 = 43
                r3 = 45
                r4 = 0
                if (r1 == 0) goto L_0x0040
                java.lang.String r1 = r11.b
                int r1 = r1.length()
                if (r1 != 0) goto L_0x0029
                if (r0 <= 0) goto L_0x0021
                char r1 = r13.charAt(r14)
                if (r1 == r3) goto L_0x0040
                if (r1 != r2) goto L_0x0021
                goto L_0x0040
            L_0x0021:
                java.lang.Integer r13 = java.lang.Integer.valueOf(r4)
                r12.setOffset(r13)
                return r14
            L_0x0029:
                java.lang.String r1 = r11.b
                boolean r1 = org.joda.time.format.DateTimeFormatterBuilder.c(r13, r14, r1)
                if (r1 == 0) goto L_0x0040
                java.lang.Integer r13 = java.lang.Integer.valueOf(r4)
                r12.setOffset(r13)
                java.lang.String r12 = r11.b
                int r12 = r12.length()
                int r14 = r14 + r12
                return r14
            L_0x0040:
                r1 = 1
                if (r0 > r1) goto L_0x0046
                r12 = r14 ^ -1
                return r12
            L_0x0046:
                char r5 = r13.charAt(r14)
                if (r5 != r3) goto L_0x004e
                r2 = 1
                goto L_0x0051
            L_0x004e:
                if (r5 != r2) goto L_0x0134
                r2 = 0
            L_0x0051:
                int r0 = r0 + -1
                int r14 = r14 + r1
                r3 = 2
                int r5 = r11.a(r13, r14, r3)
                if (r5 >= r3) goto L_0x005e
                r12 = r14 ^ -1
                return r12
            L_0x005e:
                int r5 = org.joda.time.format.FormatUtils.a(r13, r14)
                r6 = 23
                if (r5 <= r6) goto L_0x0069
                r12 = r14 ^ -1
                return r12
            L_0x0069:
                r6 = 3600000(0x36ee80, float:5.044674E-39)
                int r5 = r5 * r6
                int r0 = r0 + -2
                int r14 = r14 + r3
                if (r0 > 0) goto L_0x0075
                goto L_0x0129
            L_0x0075:
                char r6 = r13.charAt(r14)
                r7 = 58
                r8 = 48
                if (r6 != r7) goto L_0x0085
                int r0 = r0 + -1
                int r14 = r14 + 1
                r4 = 1
                goto L_0x008b
            L_0x0085:
                if (r6 < r8) goto L_0x0129
                r9 = 57
                if (r6 > r9) goto L_0x0129
            L_0x008b:
                int r6 = r11.a(r13, r14, r3)
                if (r6 != 0) goto L_0x0095
                if (r4 != 0) goto L_0x0095
                goto L_0x0129
            L_0x0095:
                if (r6 >= r3) goto L_0x009a
                r12 = r14 ^ -1
                return r12
            L_0x009a:
                int r6 = org.joda.time.format.FormatUtils.a(r13, r14)
                r9 = 59
                if (r6 <= r9) goto L_0x00a5
                r12 = r14 ^ -1
                return r12
            L_0x00a5:
                r10 = 60000(0xea60, float:8.4078E-41)
                int r6 = r6 * r10
                int r5 = r5 + r6
                int r0 = r0 + -2
                int r14 = r14 + 2
                if (r0 > 0) goto L_0x00b3
                goto L_0x0129
            L_0x00b3:
                if (r4 == 0) goto L_0x00c1
                char r6 = r13.charAt(r14)
                if (r6 == r7) goto L_0x00bd
                goto L_0x0129
            L_0x00bd:
                int r0 = r0 + -1
                int r14 = r14 + 1
            L_0x00c1:
                int r6 = r11.a(r13, r14, r3)
                if (r6 != 0) goto L_0x00ca
                if (r4 != 0) goto L_0x00ca
                goto L_0x0129
            L_0x00ca:
                if (r6 >= r3) goto L_0x00cf
                r12 = r14 ^ -1
                return r12
            L_0x00cf:
                int r6 = org.joda.time.format.FormatUtils.a(r13, r14)
                if (r6 <= r9) goto L_0x00d8
                r12 = r14 ^ -1
                return r12
            L_0x00d8:
                int r6 = r6 * 1000
                int r5 = r5 + r6
                int r0 = r0 + -2
                int r14 = r14 + 2
                if (r0 > 0) goto L_0x00e2
                goto L_0x0129
            L_0x00e2:
                if (r4 == 0) goto L_0x00f7
                char r0 = r13.charAt(r14)
                r6 = 46
                if (r0 == r6) goto L_0x00f5
                char r0 = r13.charAt(r14)
                r6 = 44
                if (r0 == r6) goto L_0x00f5
                goto L_0x0129
            L_0x00f5:
                int r14 = r14 + 1
            L_0x00f7:
                r0 = 3
                int r0 = r11.a(r13, r14, r0)
                if (r0 != 0) goto L_0x0101
                if (r4 != 0) goto L_0x0101
                goto L_0x0129
            L_0x0101:
                if (r0 >= r1) goto L_0x0106
                r12 = r14 ^ -1
                return r12
            L_0x0106:
                int r4 = r14 + 1
                char r14 = r13.charAt(r14)
                int r14 = r14 - r8
                int r14 = r14 * 100
                int r5 = r5 + r14
                if (r0 <= r1) goto L_0x0128
                int r14 = r4 + 1
                char r1 = r13.charAt(r4)
                int r1 = r1 - r8
                int r1 = r1 * 10
                int r5 = r5 + r1
                if (r0 <= r3) goto L_0x0129
                int r0 = r14 + 1
                char r13 = r13.charAt(r14)
                int r13 = r13 - r8
                int r5 = r5 + r13
                r14 = r0
                goto L_0x0129
            L_0x0128:
                r14 = r4
            L_0x0129:
                if (r2 == 0) goto L_0x012c
                int r5 = -r5
            L_0x012c:
                java.lang.Integer r13 = java.lang.Integer.valueOf(r5)
                r12.setOffset(r13)
                return r14
            L_0x0134:
                r12 = r14 ^ -1
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.TimeZoneOffset.a(org.joda.time.format.DateTimeParserBucket, java.lang.CharSequence, int):int");
        }

        private int a(CharSequence charSequence, int i, int i2) {
            int i3 = 0;
            for (int min = Math.min(charSequence.length() - i, i2); min > 0; min--) {
                char charAt = charSequence.charAt(i + i3);
                if (charAt < '0' || charAt > '9') {
                    break;
                }
                i3++;
            }
            return i3;
        }
    }

    static class TwoDigitYear implements InternalParser, InternalPrinter {
        private final DateTimeFieldType a;
        private final int b;
        private final boolean c;

        public int estimatePrintedLength() {
            return 2;
        }

        TwoDigitYear(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            this.a = dateTimeFieldType;
            this.b = i;
            this.c = z;
        }

        public int estimateParsedLength() {
            return this.c ? 4 : 2;
        }

        public int a(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int i) {
            int i2;
            int i3;
            int i4;
            int length = charSequence.length() - i;
            if (this.c) {
                int i5 = i;
                int i6 = 0;
                boolean z = false;
                boolean z2 = false;
                while (i6 < length) {
                    char charAt = charSequence.charAt(i5 + i6);
                    if (i6 != 0 || (charAt != '-' && charAt != '+')) {
                        if (charAt < '0' || charAt > '9') {
                            break;
                        }
                        i6++;
                    } else {
                        z2 = charAt == '-';
                        if (z2) {
                            i6++;
                        } else {
                            i5++;
                            length--;
                        }
                        z = true;
                    }
                }
                if (i6 == 0) {
                    return i5 ^ -1;
                }
                if (z || i6 != 2) {
                    if (i6 >= 9) {
                        i3 = i6 + i5;
                        i4 = Integer.parseInt(charSequence.subSequence(i5, i3).toString());
                    } else {
                        int i7 = z2 ? i5 + 1 : i5;
                        int i8 = i7 + 1;
                        try {
                            int charAt2 = charSequence.charAt(i7) - TarjetasConstants.ULT_NUM_AMEX;
                            i3 = i6 + i5;
                            while (i8 < i3) {
                                i8++;
                                charAt2 = (((charAt2 << 3) + (charAt2 << 1)) + charSequence.charAt(i8)) - 48;
                            }
                            i4 = z2 ? -charAt2 : charAt2;
                        } catch (StringIndexOutOfBoundsException unused) {
                            return i5 ^ -1;
                        }
                    }
                    dateTimeParserBucket.saveField(this.a, i4);
                    return i3;
                }
                i = i5;
            } else if (Math.min(2, length) < 2) {
                return i ^ -1;
            }
            char charAt3 = charSequence.charAt(i);
            if (charAt3 < '0' || charAt3 > '9') {
                return i ^ -1;
            }
            int i9 = charAt3 - TarjetasConstants.ULT_NUM_AMEX;
            char charAt4 = charSequence.charAt(i + 1);
            if (charAt4 < '0' || charAt4 > '9') {
                return i ^ -1;
            }
            int i10 = (((i9 << 3) + (i9 << 1)) + charAt4) - 48;
            int i11 = this.b;
            if (dateTimeParserBucket.getPivotYear() != null) {
                i11 = dateTimeParserBucket.getPivotYear().intValue();
            }
            int i12 = i11 - 50;
            int i13 = 100;
            if (i12 >= 0) {
                i2 = i12 % 100;
            } else {
                i2 = ((i12 + 1) % 100) + 99;
            }
            if (i10 >= i2) {
                i13 = 0;
            }
            dateTimeParserBucket.saveField(this.a, i10 + ((i12 + i13) - i2));
            return i + 2;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            int a2 = a(j, chronology);
            if (a2 < 0) {
                appendable.append(65533);
                appendable.append(65533);
                return;
            }
            FormatUtils.appendPaddedInteger(appendable, a2, 2);
        }

        private int a(long j, Chronology chronology) {
            try {
                int i = this.a.getField(chronology).get(j);
                if (i < 0) {
                    i = -i;
                }
                return i % 100;
            } catch (RuntimeException unused) {
                return -1;
            }
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            int a2 = a(readablePartial);
            if (a2 < 0) {
                appendable.append(65533);
                appendable.append(65533);
                return;
            }
            FormatUtils.appendPaddedInteger(appendable, a2, 2);
        }

        private int a(ReadablePartial readablePartial) {
            if (readablePartial.isSupported(this.a)) {
                try {
                    int i = readablePartial.get(this.a);
                    if (i < 0) {
                        i = -i;
                    }
                    return i % 100;
                } catch (RuntimeException unused) {
                }
            }
            return -1;
        }
    }

    static class UnpaddedNumber extends NumberFormatter {
        protected UnpaddedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            super(dateTimeFieldType, i, z);
        }

        public int estimatePrintedLength() {
            return this.b;
        }

        public void a(Appendable appendable, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            try {
                FormatUtils.appendUnpaddedInteger(appendable, this.a.getField(chronology).get(j));
            } catch (RuntimeException unused) {
                appendable.append(65533);
            }
        }

        public void a(Appendable appendable, ReadablePartial readablePartial, Locale locale) {
            if (readablePartial.isSupported(this.a)) {
                try {
                    FormatUtils.appendUnpaddedInteger(appendable, readablePartial.get(this.a));
                } catch (RuntimeException unused) {
                    appendable.append(65533);
                }
            } else {
                appendable.append(65533);
            }
        }
    }

    public DateTimeFormatter toFormatter() {
        Object a2 = a();
        InternalParser internalParser = null;
        InternalPrinter internalPrinter = b(a2) ? (InternalPrinter) a2 : null;
        if (c(a2)) {
            internalParser = (InternalParser) a2;
        }
        if (internalPrinter != null || internalParser != null) {
            return new DateTimeFormatter(internalPrinter, internalParser);
        }
        throw new UnsupportedOperationException("Both printing and parsing not supported");
    }

    public DateTimePrinter toPrinter() {
        Object a2 = a();
        if (b(a2)) {
            return InternalPrinterDateTimePrinter.a((InternalPrinter) a2);
        }
        throw new UnsupportedOperationException("Printing is not supported");
    }

    public DateTimeParser toParser() {
        Object a2 = a();
        if (c(a2)) {
            return InternalParserDateTimeParser.a((InternalParser) a2);
        }
        throw new UnsupportedOperationException("Parsing is not supported");
    }

    public boolean canBuildFormatter() {
        return d(a());
    }

    public boolean canBuildPrinter() {
        return b(a());
    }

    public boolean canBuildParser() {
        return c(a());
    }

    public void clear() {
        this.b = null;
        this.a.clear();
    }

    public DateTimeFormatterBuilder append(DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter != null) {
            return a(dateTimeFormatter.a(), dateTimeFormatter.b());
        }
        throw new IllegalArgumentException("No formatter supplied");
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter) {
        a(dateTimePrinter);
        return a(DateTimePrinterInternalPrinter.a(dateTimePrinter), (InternalParser) null);
    }

    public DateTimeFormatterBuilder append(DateTimeParser dateTimeParser) {
        a(dateTimeParser);
        return a((InternalPrinter) null, DateTimeParserInternalParser.a(dateTimeParser));
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser) {
        a(dateTimePrinter);
        a(dateTimeParser);
        return a(DateTimePrinterInternalPrinter.a(dateTimePrinter), DateTimeParserInternalParser.a(dateTimeParser));
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser[] dateTimeParserArr) {
        if (dateTimePrinter != null) {
            a(dateTimePrinter);
        }
        if (dateTimeParserArr == null) {
            throw new IllegalArgumentException("No parsers supplied");
        }
        int length = dateTimeParserArr.length;
        int i = 0;
        if (length != 1) {
            InternalParser[] internalParserArr = new InternalParser[length];
            while (i < length - 1) {
                InternalParser a2 = DateTimeParserInternalParser.a(dateTimeParserArr[i]);
                internalParserArr[i] = a2;
                if (a2 == null) {
                    throw new IllegalArgumentException("Incomplete parser array");
                }
                i++;
            }
            internalParserArr[i] = DateTimeParserInternalParser.a(dateTimeParserArr[i]);
            return a(DateTimePrinterInternalPrinter.a(dateTimePrinter), (InternalParser) new MatchingParser(internalParserArr));
        } else if (dateTimeParserArr[0] != null) {
            return a(DateTimePrinterInternalPrinter.a(dateTimePrinter), DateTimeParserInternalParser.a(dateTimeParserArr[0]));
        } else {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    public DateTimeFormatterBuilder appendOptional(DateTimeParser dateTimeParser) {
        a(dateTimeParser);
        return a((InternalPrinter) null, (InternalParser) new MatchingParser(new InternalParser[]{DateTimeParserInternalParser.a(dateTimeParser), null}));
    }

    private void a(DateTimeParser dateTimeParser) {
        if (dateTimeParser == null) {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    private void a(DateTimePrinter dateTimePrinter) {
        if (dateTimePrinter == null) {
            throw new IllegalArgumentException("No printer supplied");
        }
    }

    private DateTimeFormatterBuilder a(Object obj) {
        this.b = null;
        this.a.add(obj);
        this.a.add(obj);
        return this;
    }

    private DateTimeFormatterBuilder a(InternalPrinter internalPrinter, InternalParser internalParser) {
        this.b = null;
        this.a.add(internalPrinter);
        this.a.add(internalParser);
        return this;
    }

    public DateTimeFormatterBuilder appendLiteral(char c) {
        return a((Object) new CharacterLiteral(c));
    }

    public DateTimeFormatterBuilder appendLiteral(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        switch (str.length()) {
            case 0:
                return this;
            case 1:
                return a((Object) new CharacterLiteral(str.charAt(0)));
            default:
                return a((Object) new StringLiteral(str));
        }
    }

    public DateTimeFormatterBuilder appendDecimal(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        } else if (i <= 1) {
            return a((Object) new UnpaddedNumber(dateTimeFieldType, i2, false));
        } else {
            return a((Object) new PaddedNumber(dateTimeFieldType, i2, false, i));
        }
    }

    public DateTimeFormatterBuilder appendFixedDecimal(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        } else if (i > 0) {
            return a((Object) new FixedNumber(dateTimeFieldType, i, false));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Illegal number of digits: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public DateTimeFormatterBuilder appendSignedDecimal(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        } else if (i <= 1) {
            return a((Object) new UnpaddedNumber(dateTimeFieldType, i2, true));
        } else {
            return a((Object) new PaddedNumber(dateTimeFieldType, i2, true, i));
        }
    }

    public DateTimeFormatterBuilder appendFixedSignedDecimal(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        } else if (i > 0) {
            return a((Object) new FixedNumber(dateTimeFieldType, i, true));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Illegal number of digits: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public DateTimeFormatterBuilder appendText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return a((Object) new TextField(dateTimeFieldType, false));
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public DateTimeFormatterBuilder appendShortText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return a((Object) new TextField(dateTimeFieldType, true));
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public DateTimeFormatterBuilder appendFraction(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i >= 0 && i2 > 0) {
            return a((Object) new Fraction(dateTimeFieldType, i, i2));
        }
        throw new IllegalArgumentException();
    }

    public DateTimeFormatterBuilder appendFractionOfSecond(int i, int i2) {
        return appendFraction(DateTimeFieldType.secondOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfMinute(int i, int i2) {
        return appendFraction(DateTimeFieldType.minuteOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfHour(int i, int i2) {
        return appendFraction(DateTimeFieldType.hourOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfDay(int i, int i2) {
        return appendFraction(DateTimeFieldType.dayOfYear(), i, i2);
    }

    public DateTimeFormatterBuilder appendMillisOfSecond(int i) {
        return appendDecimal(DateTimeFieldType.millisOfSecond(), i, 3);
    }

    public DateTimeFormatterBuilder appendMillisOfDay(int i) {
        return appendDecimal(DateTimeFieldType.millisOfDay(), i, 8);
    }

    public DateTimeFormatterBuilder appendSecondOfMinute(int i) {
        return appendDecimal(DateTimeFieldType.secondOfMinute(), i, 2);
    }

    public DateTimeFormatterBuilder appendSecondOfDay(int i) {
        return appendDecimal(DateTimeFieldType.secondOfDay(), i, 5);
    }

    public DateTimeFormatterBuilder appendMinuteOfHour(int i) {
        return appendDecimal(DateTimeFieldType.minuteOfHour(), i, 2);
    }

    public DateTimeFormatterBuilder appendMinuteOfDay(int i) {
        return appendDecimal(DateTimeFieldType.minuteOfDay(), i, 4);
    }

    public DateTimeFormatterBuilder appendHourOfDay(int i) {
        return appendDecimal(DateTimeFieldType.hourOfDay(), i, 2);
    }

    public DateTimeFormatterBuilder appendClockhourOfDay(int i) {
        return appendDecimal(DateTimeFieldType.clockhourOfDay(), i, 2);
    }

    public DateTimeFormatterBuilder appendHourOfHalfday(int i) {
        return appendDecimal(DateTimeFieldType.hourOfHalfday(), i, 2);
    }

    public DateTimeFormatterBuilder appendClockhourOfHalfday(int i) {
        return appendDecimal(DateTimeFieldType.clockhourOfHalfday(), i, 2);
    }

    public DateTimeFormatterBuilder appendDayOfWeek(int i) {
        return appendDecimal(DateTimeFieldType.dayOfWeek(), i, 1);
    }

    public DateTimeFormatterBuilder appendDayOfMonth(int i) {
        return appendDecimal(DateTimeFieldType.dayOfMonth(), i, 2);
    }

    public DateTimeFormatterBuilder appendDayOfYear(int i) {
        return appendDecimal(DateTimeFieldType.dayOfYear(), i, 3);
    }

    public DateTimeFormatterBuilder appendWeekOfWeekyear(int i) {
        return appendDecimal(DateTimeFieldType.weekOfWeekyear(), i, 2);
    }

    public DateTimeFormatterBuilder appendWeekyear(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.weekyear(), i, i2);
    }

    public DateTimeFormatterBuilder appendMonthOfYear(int i) {
        return appendDecimal(DateTimeFieldType.monthOfYear(), i, 2);
    }

    public DateTimeFormatterBuilder appendYear(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.year(), i, i2);
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int i) {
        return appendTwoDigitYear(i, false);
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int i, boolean z) {
        return a((Object) new TwoDigitYear(DateTimeFieldType.year(), i, z));
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int i) {
        return appendTwoDigitWeekyear(i, false);
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int i, boolean z) {
        return a((Object) new TwoDigitYear(DateTimeFieldType.weekyear(), i, z));
    }

    public DateTimeFormatterBuilder appendYearOfEra(int i, int i2) {
        return appendDecimal(DateTimeFieldType.yearOfEra(), i, i2);
    }

    public DateTimeFormatterBuilder appendYearOfCentury(int i, int i2) {
        return appendDecimal(DateTimeFieldType.yearOfCentury(), i, i2);
    }

    public DateTimeFormatterBuilder appendCenturyOfEra(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.centuryOfEra(), i, i2);
    }

    public DateTimeFormatterBuilder appendHalfdayOfDayText() {
        return appendText(DateTimeFieldType.halfdayOfDay());
    }

    public DateTimeFormatterBuilder appendDayOfWeekText() {
        return appendText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendDayOfWeekShortText() {
        return appendShortText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendMonthOfYearText() {
        return appendText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendMonthOfYearShortText() {
        return appendShortText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendEraText() {
        return appendText(DateTimeFieldType.era());
    }

    public DateTimeFormatterBuilder appendTimeZoneName() {
        return a((InternalPrinter) new TimeZoneName(0, null), (InternalParser) null);
    }

    public DateTimeFormatterBuilder appendTimeZoneName(Map<String, DateTimeZone> map) {
        TimeZoneName timeZoneName = new TimeZoneName(0, map);
        return a((InternalPrinter) timeZoneName, (InternalParser) timeZoneName);
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName() {
        return a((InternalPrinter) new TimeZoneName(1, null), (InternalParser) null);
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName(Map<String, DateTimeZone> map) {
        TimeZoneName timeZoneName = new TimeZoneName(1, map);
        return a((InternalPrinter) timeZoneName, (InternalParser) timeZoneName);
    }

    public DateTimeFormatterBuilder appendTimeZoneId() {
        return a((InternalPrinter) TimeZoneId.INSTANCE, (InternalParser) TimeZoneId.INSTANCE);
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String str, boolean z, int i, int i2) {
        TimeZoneOffset timeZoneOffset = new TimeZoneOffset(str, str, z, i, i2);
        return a((Object) timeZoneOffset);
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String str, String str2, boolean z, int i, int i2) {
        TimeZoneOffset timeZoneOffset = new TimeZoneOffset(str, str2, z, i, i2);
        return a((Object) timeZoneOffset);
    }

    public DateTimeFormatterBuilder appendPattern(String str) {
        DateTimeFormat.a(this, str);
        return this;
    }

    private Object a() {
        Object obj = this.b;
        if (obj == null) {
            if (this.a.size() == 2) {
                Object obj2 = this.a.get(0);
                Object obj3 = this.a.get(1);
                if (obj2 == null) {
                    obj = obj3;
                } else if (obj2 == obj3 || obj3 == null) {
                    obj = obj2;
                }
            }
            if (obj == null) {
                obj = new Composite(this.a);
            }
            this.b = obj;
        }
        return obj;
    }

    private boolean b(Object obj) {
        if (!(obj instanceof InternalPrinter)) {
            return false;
        }
        if (obj instanceof Composite) {
            return ((Composite) obj).a();
        }
        return true;
    }

    private boolean c(Object obj) {
        if (!(obj instanceof InternalParser)) {
            return false;
        }
        if (obj instanceof Composite) {
            return ((Composite) obj).b();
        }
        return true;
    }

    private boolean d(Object obj) {
        return b(obj) || c(obj);
    }

    static void a(Appendable appendable, int i) {
        while (true) {
            i--;
            if (i >= 0) {
                appendable.append(65533);
            } else {
                return;
            }
        }
    }

    static int a(CharSequence charSequence, int i, String str) {
        int length = charSequence.length() - i;
        int length2 = str.length();
        int min = Math.min(length, length2);
        for (int i2 = 0; i2 < min; i2++) {
            int charAt = str.charAt(i2) - charSequence.charAt(i + i2);
            if (charAt != 0) {
                return charAt;
            }
        }
        return length2 - length;
    }

    static boolean b(CharSequence charSequence, int i, String str) {
        int length = str.length();
        if (charSequence.length() - i < length) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (charSequence.charAt(i + i2) != str.charAt(i2)) {
                return false;
            }
        }
        return true;
    }

    static boolean c(CharSequence charSequence, int i, String str) {
        int length = str.length();
        if (charSequence.length() - i < length) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = charSequence.charAt(i + i2);
            char charAt2 = str.charAt(i2);
            if (charAt != charAt2) {
                char upperCase = Character.toUpperCase(charAt);
                char upperCase2 = Character.toUpperCase(charAt2);
                if (!(upperCase == upperCase2 || Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2))) {
                    return false;
                }
            }
        }
        return true;
    }
}
