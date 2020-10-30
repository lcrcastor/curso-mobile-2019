package org.joda.time.format;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import org.joda.time.DurationFieldType;
import org.joda.time.PeriodType;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadablePeriod;

public class PeriodFormatterBuilder {
    /* access modifiers changed from: private */
    public static final ConcurrentMap<String, Pattern> a = new ConcurrentHashMap();
    private int b;
    private int c;
    private int d;
    private boolean e;
    private PeriodFieldAffix f;
    private List<Object> g;
    private boolean h;
    private boolean i;
    private FieldFormatter[] j;

    static class Composite implements PeriodParser, PeriodPrinter {
        private final PeriodPrinter[] a;
        private final PeriodParser[] b;

        Composite(List<Object> list) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            a(list, arrayList, arrayList2);
            if (arrayList.size() <= 0) {
                this.a = null;
            } else {
                this.a = (PeriodPrinter[]) arrayList.toArray(new PeriodPrinter[arrayList.size()]);
            }
            if (arrayList2.size() <= 0) {
                this.b = null;
            } else {
                this.b = (PeriodParser[]) arrayList2.toArray(new PeriodParser[arrayList2.size()]);
            }
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            PeriodPrinter[] periodPrinterArr = this.a;
            int length = periodPrinterArr.length;
            int i2 = 0;
            while (i2 < i) {
                length--;
                if (length < 0) {
                    break;
                }
                i2 += periodPrinterArr[length].countFieldsToPrint(readablePeriod, SubsamplingScaleImageView.TILE_SIZE_AUTO, locale);
            }
            return i2;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter[] periodPrinterArr = this.a;
            int length = periodPrinterArr.length;
            int i = 0;
            while (true) {
                length--;
                if (length < 0) {
                    return i;
                }
                i += periodPrinterArr[length].calculatePrintedLength(readablePeriod, locale);
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            for (PeriodPrinter printTo : this.a) {
                printTo.printTo(stringBuffer, readablePeriod, locale);
            }
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) {
            for (PeriodPrinter printTo : this.a) {
                printTo.printTo(writer, readablePeriod, locale);
            }
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            PeriodParser[] periodParserArr = this.b;
            if (periodParserArr == null) {
                throw new UnsupportedOperationException();
            }
            int length = periodParserArr.length;
            for (int i2 = 0; i2 < length && i >= 0; i2++) {
                i = periodParserArr[i2].parseInto(readWritablePeriod, str, i, locale);
            }
            return i;
        }

        private void a(List<Object> list, List<Object> list2, List<Object> list3) {
            int size = list.size();
            for (int i = 0; i < size; i += 2) {
                Object obj = list.get(i);
                if (obj instanceof PeriodPrinter) {
                    if (obj instanceof Composite) {
                        a(list2, ((Composite) obj).a);
                    } else {
                        list2.add(obj);
                    }
                }
                Object obj2 = list.get(i + 1);
                if (obj2 instanceof PeriodParser) {
                    if (obj2 instanceof Composite) {
                        a(list3, ((Composite) obj2).b);
                    } else {
                        list3.add(obj2);
                    }
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

    static class CompositeAffix extends IgnorableAffix {
        private final PeriodFieldAffix a;
        private final PeriodFieldAffix b;
        private final String[] c;

        CompositeAffix(PeriodFieldAffix periodFieldAffix, PeriodFieldAffix periodFieldAffix2) {
            String[] a2;
            String[] a3;
            this.a = periodFieldAffix;
            this.b = periodFieldAffix2;
            HashSet hashSet = new HashSet();
            for (String str : this.a.a()) {
                for (String str2 : this.b.a()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(str2);
                    hashSet.add(sb.toString());
                }
            }
            this.c = (String[]) hashSet.toArray(new String[hashSet.size()]);
        }

        public int a(int i) {
            return this.a.a(i) + this.b.a(i);
        }

        public void a(StringBuffer stringBuffer, int i) {
            this.a.a(stringBuffer, i);
            this.b.a(stringBuffer, i);
        }

        public void a(Writer writer, int i) {
            this.a.a(writer, i);
            this.b.a(writer, i);
        }

        public int a(String str, int i) {
            int a2 = this.a.a(str, i);
            if (a2 >= 0) {
                a2 = this.b.a(str, a2);
                if (a2 >= 0 && a(a(str, a2) - a2, str, i)) {
                    return i ^ -1;
                }
            }
            return a2;
        }

        public int b(String str, int i) {
            int b2 = this.a.b(str, i);
            if (b2 >= 0) {
                int b3 = this.b.b(str, this.a.a(str, b2));
                if (b3 < 0 || !a(this.b.a(str, b3) - b2, str, i)) {
                    return b2 > 0 ? b2 : b3;
                }
            }
            return i ^ -1;
        }

        public String[] a() {
            return (String[]) this.c.clone();
        }
    }

    static class FieldFormatter implements PeriodParser, PeriodPrinter {
        private final int a;
        private final int b;
        private final int c;
        private final boolean d;
        private final int e;
        private final FieldFormatter[] f;
        private final PeriodFieldAffix g;
        private final PeriodFieldAffix h;

        FieldFormatter(int i, int i2, int i3, boolean z, int i4, FieldFormatter[] fieldFormatterArr, PeriodFieldAffix periodFieldAffix, PeriodFieldAffix periodFieldAffix2) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = z;
            this.e = i4;
            this.f = fieldFormatterArr;
            this.g = periodFieldAffix;
            this.h = periodFieldAffix2;
        }

        FieldFormatter(FieldFormatter fieldFormatter, PeriodFieldAffix periodFieldAffix) {
            this.a = fieldFormatter.a;
            this.b = fieldFormatter.b;
            this.c = fieldFormatter.c;
            this.d = fieldFormatter.d;
            this.e = fieldFormatter.e;
            this.f = fieldFormatter.f;
            this.g = fieldFormatter.g;
            if (fieldFormatter.h != null) {
                periodFieldAffix = new CompositeAffix(fieldFormatter.h, periodFieldAffix);
            }
            this.h = periodFieldAffix;
        }

        public void a(FieldFormatter[] fieldFormatterArr) {
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            for (FieldFormatter fieldFormatter : fieldFormatterArr) {
                if (fieldFormatter != null && !equals(fieldFormatter)) {
                    hashSet.add(fieldFormatter.g);
                    hashSet2.add(fieldFormatter.h);
                }
            }
            if (this.g != null) {
                this.g.a((Set<PeriodFieldAffix>) hashSet);
            }
            if (this.h != null) {
                this.h.a((Set<PeriodFieldAffix>) hashSet2);
            }
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            if (i <= 0) {
                return 0;
            }
            return (this.b == 4 || a(readablePeriod) != Long.MAX_VALUE) ? 1 : 0;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            int i;
            long a2 = a(readablePeriod);
            if (a2 == Long.MAX_VALUE) {
                return 0;
            }
            int max = Math.max(FormatUtils.calculateDigitCount(a2), this.a);
            if (this.e >= 8) {
                if (a2 < 0) {
                    i = 5;
                } else {
                    i = 4;
                }
                max = Math.max(max, i) + 1;
                if (this.e == 9 && Math.abs(a2) % 1000 == 0) {
                    max -= 4;
                }
                a2 /= 1000;
            }
            int i2 = (int) a2;
            if (this.g != null) {
                max += this.g.a(i2);
            }
            if (this.h != null) {
                max += this.h.a(i2);
            }
            return max;
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            long a2 = a(readablePeriod);
            if (a2 != Long.MAX_VALUE) {
                int i = (int) a2;
                if (this.e >= 8) {
                    i = (int) (a2 / 1000);
                }
                if (this.g != null) {
                    this.g.a(stringBuffer, i);
                }
                int length = stringBuffer.length();
                int i2 = this.a;
                if (i2 <= 1) {
                    FormatUtils.appendUnpaddedInteger(stringBuffer, i);
                } else {
                    FormatUtils.appendPaddedInteger(stringBuffer, i, i2);
                }
                if (this.e >= 8) {
                    int abs = (int) (Math.abs(a2) % 1000);
                    if (this.e == 8 || abs > 0) {
                        if (a2 < 0 && a2 > -1000) {
                            stringBuffer.insert(length, '-');
                        }
                        stringBuffer.append('.');
                        FormatUtils.appendPaddedInteger(stringBuffer, abs, 3);
                    }
                }
                if (this.h != null) {
                    this.h.a(stringBuffer, i);
                }
            }
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) {
            long a2 = a(readablePeriod);
            if (a2 != Long.MAX_VALUE) {
                int i = (int) a2;
                if (this.e >= 8) {
                    i = (int) (a2 / 1000);
                }
                if (this.g != null) {
                    this.g.a(writer, i);
                }
                int i2 = this.a;
                if (i2 <= 1) {
                    FormatUtils.writeUnpaddedInteger(writer, i);
                } else {
                    FormatUtils.writePaddedInteger(writer, i, i2);
                }
                if (this.e >= 8) {
                    int abs = (int) (Math.abs(a2) % 1000);
                    if (this.e == 8 || abs > 0) {
                        writer.write(46);
                        FormatUtils.writePaddedInteger(writer, abs, 3);
                    }
                }
                if (this.h != null) {
                    this.h.a(writer, i);
                }
            }
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            int i2;
            int i3;
            int i4;
            ReadWritablePeriod readWritablePeriod2 = readWritablePeriod;
            String str2 = str;
            int i5 = i;
            boolean z = this.b == 4;
            if (i5 >= str.length()) {
                return z ? i5 ^ -1 : i5;
            }
            if (this.g != null) {
                i5 = this.g.a(str2, i5);
                if (i5 < 0) {
                    return !z ? i5 ^ -1 : i5;
                }
                z = true;
            }
            if (this.h == null || z) {
                i2 = -1;
            } else {
                i2 = this.h.b(str2, i5);
                if (i2 < 0) {
                    return !z ? i2 ^ -1 : i2;
                }
                z = true;
            }
            if (!z && !a(readWritablePeriod.getPeriodType(), this.e)) {
                return i5;
            }
            if (i2 > 0) {
                i3 = Math.min(this.c, i2 - i5);
            } else {
                i3 = Math.min(this.c, str.length() - i5);
            }
            int i6 = i5;
            int i7 = 0;
            int i8 = -1;
            boolean z2 = false;
            boolean z3 = false;
            while (i7 < i3) {
                int i9 = i6 + i7;
                char charAt = str2.charAt(i9);
                if (i7 == 0 && ((charAt == '-' || charAt == '+') && !this.d)) {
                    z3 = charAt == '-';
                    int i10 = i7 + 1;
                    if (i10 >= i3) {
                        break;
                    }
                    char charAt2 = str2.charAt(i9 + 1);
                    if (charAt2 < '0' || charAt2 > '9') {
                        break;
                    }
                    if (z3) {
                        i7 = i10;
                    } else {
                        i6++;
                    }
                    i3 = Math.min(i3 + 1, str.length() - i6);
                } else {
                    if (charAt < '0' || charAt > '9') {
                        if ((charAt != '.' && charAt != ',') || ((this.e != 8 && this.e != 9) || i8 >= 0)) {
                            break;
                        }
                        int i11 = i9 + 1;
                        i3 = Math.min(i3 + 1, str.length() - i6);
                        i8 = i11;
                    } else {
                        z2 = true;
                    }
                    i7++;
                }
            }
            if (!z2) {
                return i6 ^ -1;
            }
            if (i2 >= 0 && i6 + i7 != i2) {
                return i6;
            }
            if (this.e != 8 && this.e != 9) {
                a(readWritablePeriod2, this.e, a(str2, i6, i7));
            } else if (i8 < 0) {
                a(readWritablePeriod2, 6, a(str2, i6, i7));
                a(readWritablePeriod2, 7, 0);
            } else {
                int a2 = a(str2, i6, (i8 - i6) - 1);
                a(readWritablePeriod2, 6, a2);
                int i12 = (i6 + i7) - i8;
                if (i12 <= 0) {
                    i4 = 0;
                } else {
                    if (i12 >= 3) {
                        i4 = a(str2, i8, 3);
                    } else {
                        int a3 = a(str2, i8, i12);
                        i4 = i12 == 1 ? a3 * 100 : a3 * 10;
                    }
                    if (z3 || a2 < 0) {
                        i4 = -i4;
                    }
                }
                a(readWritablePeriod2, 7, i4);
            }
            int i13 = i6 + i7;
            if (i13 >= 0 && this.h != null) {
                i13 = this.h.a(str2, i13);
            }
            return i13;
        }

        private int a(String str, int i, int i2) {
            if (i2 >= 10) {
                return Integer.parseInt(str.substring(i, i2 + i));
            }
            boolean z = false;
            if (i2 <= 0) {
                return 0;
            }
            int i3 = i + 1;
            char charAt = str.charAt(i);
            int i4 = i2 - 1;
            if (charAt == '-') {
                i4--;
                if (i4 < 0) {
                    return 0;
                }
                int i5 = i3 + 1;
                char charAt2 = str.charAt(i3);
                i3 = i5;
                charAt = charAt2;
                z = true;
            }
            int i6 = charAt - '0';
            while (true) {
                int i7 = i4 - 1;
                if (i4 <= 0) {
                    break;
                }
                int i8 = (i6 << 3) + (i6 << 1);
                i3++;
                i6 = (i8 + str.charAt(i3)) - 48;
                i4 = i7;
            }
            if (z) {
                i6 = -i6;
            }
            return i6;
        }

        /* access modifiers changed from: 0000 */
        public long a(ReadablePeriod readablePeriod) {
            PeriodType periodType;
            long j;
            if (this.b == 4) {
                periodType = null;
            } else {
                periodType = readablePeriod.getPeriodType();
            }
            if (periodType != null && !a(periodType, this.e)) {
                return Long.MAX_VALUE;
            }
            switch (this.e) {
                case 0:
                    j = (long) readablePeriod.get(DurationFieldType.years());
                    break;
                case 1:
                    j = (long) readablePeriod.get(DurationFieldType.months());
                    break;
                case 2:
                    j = (long) readablePeriod.get(DurationFieldType.weeks());
                    break;
                case 3:
                    j = (long) readablePeriod.get(DurationFieldType.days());
                    break;
                case 4:
                    j = (long) readablePeriod.get(DurationFieldType.hours());
                    break;
                case 5:
                    j = (long) readablePeriod.get(DurationFieldType.minutes());
                    break;
                case 6:
                    j = (long) readablePeriod.get(DurationFieldType.seconds());
                    break;
                case 7:
                    j = (long) readablePeriod.get(DurationFieldType.millis());
                    break;
                case 8:
                case 9:
                    j = (((long) readablePeriod.get(DurationFieldType.seconds())) * 1000) + ((long) readablePeriod.get(DurationFieldType.millis()));
                    break;
                default:
                    return Long.MAX_VALUE;
            }
            if (j == 0) {
                int i = this.b;
                if (i != 5) {
                    switch (i) {
                        case 1:
                            if (!b(readablePeriod) || this.f[this.e] != this) {
                                return Long.MAX_VALUE;
                            }
                            int min = Math.min(this.e, 8) - 1;
                            while (min >= 0 && min <= 9) {
                                if (a(periodType, min) && this.f[min] != null) {
                                    return Long.MAX_VALUE;
                                }
                                min--;
                            }
                            break;
                        case 2:
                            if (b(readablePeriod) && this.f[this.e] == this) {
                                int i2 = this.e;
                                while (true) {
                                    i2++;
                                    if (i2 > 9) {
                                        break;
                                    } else if (a(periodType, i2) && this.f[i2] != null) {
                                        return Long.MAX_VALUE;
                                    }
                                }
                            } else {
                                return Long.MAX_VALUE;
                            }
                            break;
                    }
                } else {
                    return Long.MAX_VALUE;
                }
            }
            return j;
        }

        /* access modifiers changed from: 0000 */
        public boolean b(ReadablePeriod readablePeriod) {
            int size = readablePeriod.size();
            for (int i = 0; i < size; i++) {
                if (readablePeriod.getValue(i) != 0) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(PeriodType periodType, int i) {
            boolean z = false;
            switch (i) {
                case 0:
                    return periodType.isSupported(DurationFieldType.years());
                case 1:
                    return periodType.isSupported(DurationFieldType.months());
                case 2:
                    return periodType.isSupported(DurationFieldType.weeks());
                case 3:
                    return periodType.isSupported(DurationFieldType.days());
                case 4:
                    return periodType.isSupported(DurationFieldType.hours());
                case 5:
                    return periodType.isSupported(DurationFieldType.minutes());
                case 6:
                    return periodType.isSupported(DurationFieldType.seconds());
                case 7:
                    return periodType.isSupported(DurationFieldType.millis());
                case 8:
                case 9:
                    if (periodType.isSupported(DurationFieldType.seconds()) || periodType.isSupported(DurationFieldType.millis())) {
                        z = true;
                    }
                    return z;
                default:
                    return false;
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(ReadWritablePeriod readWritablePeriod, int i, int i2) {
            switch (i) {
                case 0:
                    readWritablePeriod.setYears(i2);
                    return;
                case 1:
                    readWritablePeriod.setMonths(i2);
                    return;
                case 2:
                    readWritablePeriod.setWeeks(i2);
                    return;
                case 3:
                    readWritablePeriod.setDays(i2);
                    return;
                case 4:
                    readWritablePeriod.setHours(i2);
                    return;
                case 5:
                    readWritablePeriod.setMinutes(i2);
                    return;
                case 6:
                    readWritablePeriod.setSeconds(i2);
                    return;
                case 7:
                    readWritablePeriod.setMillis(i2);
                    return;
                default:
                    return;
            }
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return this.e;
        }
    }

    static abstract class IgnorableAffix implements PeriodFieldAffix {
        private volatile String[] a;

        IgnorableAffix() {
        }

        public void a(Set<PeriodFieldAffix> set) {
            String[] a2;
            String[] a3;
            if (this.a == null) {
                String str = null;
                int i = SubsamplingScaleImageView.TILE_SIZE_AUTO;
                for (String str2 : a()) {
                    if (str2.length() < i) {
                        i = str2.length();
                        str = str2;
                    }
                }
                HashSet hashSet = new HashSet();
                for (PeriodFieldAffix periodFieldAffix : set) {
                    if (periodFieldAffix != null) {
                        for (String str3 : periodFieldAffix.a()) {
                            if (str3.length() > i || (str3.equalsIgnoreCase(str) && !str3.equals(str))) {
                                hashSet.add(str3);
                            }
                        }
                    }
                }
                this.a = (String[]) hashSet.toArray(new String[hashSet.size()]);
            }
        }

        /* access modifiers changed from: protected */
        public boolean a(int i, String str, int i2) {
            String[] strArr;
            if (this.a != null) {
                for (String str2 : this.a) {
                    int length = str2.length();
                    if ((i < length && str.regionMatches(true, i2, str2, 0, length)) || (i == length && str.regionMatches(false, i2, str2, 0, length))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class Literal implements PeriodParser, PeriodPrinter {
        static final Literal a = new Literal("");
        private final String b;

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            return 0;
        }

        Literal(String str) {
            this.b = str;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            return this.b.length();
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            stringBuffer.append(this.b);
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) {
            writer.write(this.b);
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            return str.regionMatches(true, i, this.b, 0, this.b.length()) ? i + this.b.length() : i ^ -1;
        }
    }

    interface PeriodFieldAffix {
        int a(int i);

        int a(String str, int i);

        void a(Writer writer, int i);

        void a(StringBuffer stringBuffer, int i);

        void a(Set<PeriodFieldAffix> set);

        String[] a();

        int b(String str, int i);
    }

    static class PluralAffix extends IgnorableAffix {
        private final String a;
        private final String b;

        PluralAffix(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public int a(int i) {
            return (i == 1 ? this.a : this.b).length();
        }

        public void a(StringBuffer stringBuffer, int i) {
            stringBuffer.append(i == 1 ? this.a : this.b);
        }

        public void a(Writer writer, int i) {
            writer.write(i == 1 ? this.a : this.b);
        }

        public int a(String str, int i) {
            String str2;
            String str3 = this.b;
            String str4 = this.a;
            if (str3.length() < str4.length()) {
                str2 = str3;
                str3 = str4;
            } else {
                str2 = str4;
            }
            if (str.regionMatches(true, i, str3, 0, str3.length()) && !a(str3.length(), str, i)) {
                return i + str3.length();
            }
            return (!str.regionMatches(true, i, str2, 0, str2.length()) || a(str2.length(), str, i)) ? i ^ -1 : i + str2.length();
        }

        public int b(String str, int i) {
            String str2;
            String str3;
            String str4 = str;
            String str5 = this.b;
            String str6 = this.a;
            if (str5.length() < str6.length()) {
                str2 = str5;
                str3 = str6;
            } else {
                str3 = str5;
                str2 = str6;
            }
            int length = str3.length();
            int length2 = str2.length();
            int length3 = str4.length();
            for (int i2 = i; i2 < length3; i2++) {
                if (str4.regionMatches(true, i2, str3, 0, length) && !a(str3.length(), str4, i2)) {
                    return i2;
                }
                if (str4.regionMatches(true, i2, str2, 0, length2) && !a(str2.length(), str4, i2)) {
                    return i2;
                }
            }
            return i ^ -1;
        }

        public String[] a() {
            return new String[]{this.a, this.b};
        }
    }

    static class RegExAffix extends IgnorableAffix {
        private static final Comparator<String> a = new Comparator<String>() {
            /* renamed from: a */
            public int compare(String str, String str2) {
                return str2.length() - str.length();
            }
        };
        private final String[] b;
        private final Pattern[] c;
        private final String[] d;

        RegExAffix(String[] strArr, String[] strArr2) {
            this.b = (String[]) strArr2.clone();
            this.c = new Pattern[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                Pattern pattern = (Pattern) PeriodFormatterBuilder.a.get(strArr[i]);
                if (pattern == null) {
                    pattern = Pattern.compile(strArr[i]);
                    PeriodFormatterBuilder.a.putIfAbsent(strArr[i], pattern);
                }
                this.c[i] = pattern;
            }
            this.d = (String[]) this.b.clone();
            Arrays.sort(this.d, a);
        }

        private int b(int i) {
            String valueOf = String.valueOf(i);
            for (int i2 = 0; i2 < this.c.length; i2++) {
                if (this.c[i2].matcher(valueOf).matches()) {
                    return i2;
                }
            }
            return this.c.length - 1;
        }

        public int a(int i) {
            return this.b[b(i)].length();
        }

        public void a(StringBuffer stringBuffer, int i) {
            stringBuffer.append(this.b[b(i)]);
        }

        public void a(Writer writer, int i) {
            writer.write(this.b[b(i)]);
        }

        public int a(String str, int i) {
            String[] strArr;
            for (String str2 : this.d) {
                if (str.regionMatches(true, i, str2, 0, str2.length()) && !a(str2.length(), str, i)) {
                    return i + str2.length();
                }
            }
            return i ^ -1;
        }

        public int b(String str, int i) {
            String[] strArr;
            int length = str.length();
            for (int i2 = i; i2 < length; i2++) {
                for (String str2 : this.d) {
                    if (str.regionMatches(true, i2, str2, 0, str2.length()) && !a(str2.length(), str, i2)) {
                        return i2;
                    }
                }
            }
            return i ^ -1;
        }

        public String[] a() {
            return (String[]) this.b.clone();
        }
    }

    static class Separator implements PeriodParser, PeriodPrinter {
        private final String a;
        private final String b;
        private final String[] c;
        private final boolean d;
        private final boolean e;
        private final PeriodPrinter f;
        /* access modifiers changed from: private */
        public volatile PeriodPrinter g;
        private final PeriodParser h;
        /* access modifiers changed from: private */
        public volatile PeriodParser i;

        Separator(String str, String str2, String[] strArr, PeriodPrinter periodPrinter, PeriodParser periodParser, boolean z, boolean z2) {
            this.a = str;
            this.b = str2;
            if ((str2 == null || str.equals(str2)) && (strArr == null || strArr.length == 0)) {
                this.c = new String[]{str};
            } else {
                TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                treeSet.add(str);
                treeSet.add(str2);
                if (strArr != null) {
                    int length = strArr.length;
                    while (true) {
                        length--;
                        if (length < 0) {
                            break;
                        }
                        treeSet.add(strArr[length]);
                    }
                }
                ArrayList arrayList = new ArrayList(treeSet);
                Collections.reverse(arrayList);
                this.c = (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            this.f = periodPrinter;
            this.h = periodParser;
            this.d = z;
            this.e = z2;
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i2, Locale locale) {
            int countFieldsToPrint = this.f.countFieldsToPrint(readablePeriod, i2, locale);
            return countFieldsToPrint < i2 ? countFieldsToPrint + this.g.countFieldsToPrint(readablePeriod, i2, locale) : countFieldsToPrint;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter periodPrinter = this.f;
            PeriodPrinter periodPrinter2 = this.g;
            int calculatePrintedLength = periodPrinter.calculatePrintedLength(readablePeriod, locale) + periodPrinter2.calculatePrintedLength(readablePeriod, locale);
            if (!this.d) {
                return (!this.e || periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) <= 0) ? calculatePrintedLength : calculatePrintedLength + this.a.length();
            }
            if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) <= 0) {
                return calculatePrintedLength;
            }
            if (!this.e) {
                return calculatePrintedLength + this.a.length();
            }
            int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
            if (countFieldsToPrint <= 0) {
                return calculatePrintedLength;
            }
            return calculatePrintedLength + (countFieldsToPrint > 1 ? this.a : this.b).length();
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter periodPrinter = this.f;
            PeriodPrinter periodPrinter2 = this.g;
            periodPrinter.printTo(stringBuffer, readablePeriod, locale);
            if (this.d) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                    if (this.e) {
                        int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
                        if (countFieldsToPrint > 0) {
                            stringBuffer.append(countFieldsToPrint > 1 ? this.a : this.b);
                        }
                    } else {
                        stringBuffer.append(this.a);
                    }
                }
            } else if (this.e && periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                stringBuffer.append(this.a);
            }
            periodPrinter2.printTo(stringBuffer, readablePeriod, locale);
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter periodPrinter = this.f;
            PeriodPrinter periodPrinter2 = this.g;
            periodPrinter.printTo(writer, readablePeriod, locale);
            if (this.d) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                    if (this.e) {
                        int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, 2, locale);
                        if (countFieldsToPrint > 0) {
                            writer.write(countFieldsToPrint > 1 ? this.a : this.b);
                        }
                    } else {
                        writer.write(this.a);
                    }
                }
            } else if (this.e && periodPrinter2.countFieldsToPrint(readablePeriod, 1, locale) > 0) {
                writer.write(this.a);
            }
            periodPrinter2.printTo(writer, readablePeriod, locale);
        }

        /* JADX WARNING: Removed duplicated region for block: B:22:0x0057 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0058  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.ReadWritablePeriod r19, java.lang.String r20, int r21, java.util.Locale r22) {
            /*
                r18 = this;
                r0 = r18
                r1 = r19
                r8 = r20
                r2 = r21
                r9 = r22
                org.joda.time.format.PeriodParser r3 = r0.h
                int r10 = r3.parseInto(r1, r8, r2, r9)
                if (r10 >= 0) goto L_0x0013
                return r10
            L_0x0013:
                r11 = -1
                if (r10 <= r2) goto L_0x004c
                java.lang.String[] r13 = r0.c
                int r14 = r13.length
                r15 = 0
            L_0x001a:
                if (r15 >= r14) goto L_0x004c
                r7 = r13[r15]
                if (r7 == 0) goto L_0x003c
                int r2 = r7.length()
                if (r2 == 0) goto L_0x003c
                r3 = 1
                r6 = 0
                int r16 = r7.length()
                r2 = r8
                r4 = r10
                r5 = r7
                r12 = r7
                r7 = r16
                boolean r2 = r2.regionMatches(r3, r4, r5, r6, r7)
                if (r2 == 0) goto L_0x0039
                goto L_0x003d
            L_0x0039:
                int r15 = r15 + 1
                goto L_0x001a
            L_0x003c:
                r12 = r7
            L_0x003d:
                if (r12 != 0) goto L_0x0042
                r17 = 0
                goto L_0x0048
            L_0x0042:
                int r2 = r12.length()
                r17 = r2
            L_0x0048:
                int r10 = r10 + r17
                r12 = 1
                goto L_0x004f
            L_0x004c:
                r12 = 0
                r17 = -1
            L_0x004f:
                org.joda.time.format.PeriodParser r2 = r0.i
                int r1 = r2.parseInto(r1, r8, r10, r9)
                if (r1 >= 0) goto L_0x0058
                return r1
            L_0x0058:
                if (r12 == 0) goto L_0x0061
                if (r1 != r10) goto L_0x0061
                if (r17 <= 0) goto L_0x0061
                r1 = r10 ^ -1
                return r1
            L_0x0061:
                if (r1 <= r10) goto L_0x006c
                if (r12 != 0) goto L_0x006c
                boolean r2 = r0.d
                if (r2 != 0) goto L_0x006c
                r1 = r10 ^ -1
                return r1
            L_0x006c:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.PeriodFormatterBuilder.Separator.parseInto(org.joda.time.ReadWritablePeriod, java.lang.String, int, java.util.Locale):int");
        }

        /* access modifiers changed from: 0000 */
        public Separator a(PeriodPrinter periodPrinter, PeriodParser periodParser) {
            this.g = periodPrinter;
            this.i = periodParser;
            return this;
        }
    }

    static class SimpleAffix extends IgnorableAffix {
        private final String a;

        SimpleAffix(String str) {
            this.a = str;
        }

        public int a(int i) {
            return this.a.length();
        }

        public void a(StringBuffer stringBuffer, int i) {
            stringBuffer.append(this.a);
        }

        public void a(Writer writer, int i) {
            writer.write(this.a);
        }

        public int a(String str, int i) {
            String str2 = this.a;
            int length = str2.length();
            return (!str.regionMatches(true, i, str2, 0, length) || a(length, str, i)) ? i ^ -1 : i + length;
        }

        public int b(String str, int i) {
            String str2 = this.a;
            int length = str2.length();
            int length2 = str.length();
            int i2 = i;
            while (i2 < length2) {
                if (str.regionMatches(true, i2, str2, 0, length) && !a(length, str, i2)) {
                    return i2;
                }
                switch (str.charAt(i2)) {
                    case '+':
                    case ',':
                    case '-':
                    case '.':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        i2++;
                }
                return i ^ -1;
            }
            return i ^ -1;
        }

        public String[] a() {
            return new String[]{this.a};
        }
    }

    public PeriodFormatterBuilder() {
        clear();
    }

    public PeriodFormatter toFormatter() {
        FieldFormatter[] fieldFormatterArr;
        PeriodFormatter a2 = a(this.g, this.h, this.i);
        for (FieldFormatter fieldFormatter : this.j) {
            if (fieldFormatter != null) {
                fieldFormatter.a(this.j);
            }
        }
        this.j = (FieldFormatter[]) this.j.clone();
        return a2;
    }

    public PeriodPrinter toPrinter() {
        if (this.h) {
            return null;
        }
        return toFormatter().getPrinter();
    }

    public PeriodParser toParser() {
        if (this.i) {
            return null;
        }
        return toFormatter().getParser();
    }

    public void clear() {
        this.b = 1;
        this.c = 2;
        this.d = 10;
        this.e = false;
        this.f = null;
        if (this.g == null) {
            this.g = new ArrayList();
        } else {
            this.g.clear();
        }
        this.h = false;
        this.i = false;
        this.j = new FieldFormatter[10];
    }

    public PeriodFormatterBuilder append(PeriodFormatter periodFormatter) {
        if (periodFormatter == null) {
            throw new IllegalArgumentException("No formatter supplied");
        }
        b();
        a(periodFormatter.getPrinter(), periodFormatter.getParser());
        return this;
    }

    public PeriodFormatterBuilder append(PeriodPrinter periodPrinter, PeriodParser periodParser) {
        if (periodPrinter == null && periodParser == null) {
            throw new IllegalArgumentException("No printer or parser supplied");
        }
        b();
        a(periodPrinter, periodParser);
        return this;
    }

    public PeriodFormatterBuilder appendLiteral(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        b();
        Literal literal = new Literal(str);
        a((PeriodPrinter) literal, (PeriodParser) literal);
        return this;
    }

    public PeriodFormatterBuilder minimumPrintedDigits(int i2) {
        this.b = i2;
        return this;
    }

    public PeriodFormatterBuilder maximumParsedDigits(int i2) {
        this.d = i2;
        return this;
    }

    public PeriodFormatterBuilder rejectSignedValues(boolean z) {
        this.e = z;
        return this;
    }

    public PeriodFormatterBuilder printZeroRarelyLast() {
        this.c = 2;
        return this;
    }

    public PeriodFormatterBuilder printZeroRarelyFirst() {
        this.c = 1;
        return this;
    }

    public PeriodFormatterBuilder printZeroIfSupported() {
        this.c = 3;
        return this;
    }

    public PeriodFormatterBuilder printZeroAlways() {
        this.c = 4;
        return this;
    }

    public PeriodFormatterBuilder printZeroNever() {
        this.c = 5;
        return this;
    }

    public PeriodFormatterBuilder appendPrefix(String str) {
        if (str != null) {
            return a((PeriodFieldAffix) new SimpleAffix(str));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendPrefix(String str, String str2) {
        if (str != null && str2 != null) {
            return a((PeriodFieldAffix) new PluralAffix(str, str2));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendPrefix(String[] strArr, String[] strArr2) {
        if (strArr != null && strArr2 != null && strArr.length >= 1 && strArr.length == strArr2.length) {
            return a((PeriodFieldAffix) new RegExAffix(strArr, strArr2));
        }
        throw new IllegalArgumentException();
    }

    private PeriodFormatterBuilder a(PeriodFieldAffix periodFieldAffix) {
        if (periodFieldAffix == null) {
            throw new IllegalArgumentException();
        }
        if (this.f != null) {
            periodFieldAffix = new CompositeAffix(this.f, periodFieldAffix);
        }
        this.f = periodFieldAffix;
        return this;
    }

    public PeriodFormatterBuilder appendYears() {
        a(0);
        return this;
    }

    public PeriodFormatterBuilder appendMonths() {
        a(1);
        return this;
    }

    public PeriodFormatterBuilder appendWeeks() {
        a(2);
        return this;
    }

    public PeriodFormatterBuilder appendDays() {
        a(3);
        return this;
    }

    public PeriodFormatterBuilder appendHours() {
        a(4);
        return this;
    }

    public PeriodFormatterBuilder appendMinutes() {
        a(5);
        return this;
    }

    public PeriodFormatterBuilder appendSeconds() {
        a(6);
        return this;
    }

    public PeriodFormatterBuilder appendSecondsWithMillis() {
        a(8);
        return this;
    }

    public PeriodFormatterBuilder appendSecondsWithOptionalMillis() {
        a(9);
        return this;
    }

    public PeriodFormatterBuilder appendMillis() {
        a(7);
        return this;
    }

    public PeriodFormatterBuilder appendMillis3Digit() {
        a(7, 3);
        return this;
    }

    private void a(int i2) {
        a(i2, this.b);
    }

    private void a(int i2, int i3) {
        FieldFormatter fieldFormatter = new FieldFormatter(i3, this.c, this.d, this.e, i2, this.j, this.f, null);
        a((PeriodPrinter) fieldFormatter, (PeriodParser) fieldFormatter);
        this.j[i2] = fieldFormatter;
        this.f = null;
    }

    public PeriodFormatterBuilder appendSuffix(String str) {
        if (str != null) {
            return b(new SimpleAffix(str));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendSuffix(String str, String str2) {
        if (str != null && str2 != null) {
            return b(new PluralAffix(str, str2));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendSuffix(String[] strArr, String[] strArr2) {
        if (strArr != null && strArr2 != null && strArr.length >= 1 && strArr.length == strArr2.length) {
            return b(new RegExAffix(strArr, strArr2));
        }
        throw new IllegalArgumentException();
    }

    private PeriodFormatterBuilder b(PeriodFieldAffix periodFieldAffix) {
        Object obj;
        Object obj2 = null;
        if (this.g.size() > 0) {
            obj2 = this.g.get(this.g.size() - 2);
            obj = this.g.get(this.g.size() - 1);
        } else {
            obj = null;
        }
        if (obj2 == null || obj == null || obj2 != obj || !(obj2 instanceof FieldFormatter)) {
            throw new IllegalStateException("No field to apply suffix to");
        }
        b();
        FieldFormatter fieldFormatter = new FieldFormatter((FieldFormatter) obj2, periodFieldAffix);
        this.g.set(this.g.size() - 2, fieldFormatter);
        this.g.set(this.g.size() - 1, fieldFormatter);
        this.j[fieldFormatter.a()] = fieldFormatter;
        return this;
    }

    public PeriodFormatterBuilder appendSeparator(String str) {
        return a(str, str, null, true, true);
    }

    public PeriodFormatterBuilder appendSeparatorIfFieldsAfter(String str) {
        return a(str, str, null, false, true);
    }

    public PeriodFormatterBuilder appendSeparatorIfFieldsBefore(String str) {
        return a(str, str, null, true, false);
    }

    public PeriodFormatterBuilder appendSeparator(String str, String str2) {
        return a(str, str2, null, true, true);
    }

    public PeriodFormatterBuilder appendSeparator(String str, String str2, String[] strArr) {
        return a(str, str2, strArr, true, true);
    }

    private PeriodFormatterBuilder a(String str, String str2, String[] strArr, boolean z, boolean z2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException();
        }
        b();
        List<Object> list = this.g;
        if (list.size() == 0) {
            if (z2 && !z) {
                Separator separator = new Separator(str, str2, strArr, Literal.a, Literal.a, z, z2);
                a((PeriodPrinter) separator, (PeriodParser) separator);
            }
            return this;
        }
        Separator separator2 = null;
        int size = list.size();
        while (true) {
            int i2 = size - 1;
            if (i2 < 0) {
                break;
            } else if (list.get(i2) instanceof Separator) {
                separator2 = (Separator) list.get(i2);
                list = list.subList(i2 + 1, list.size());
                break;
            } else {
                size = i2 - 1;
            }
        }
        List<Object> list2 = list;
        if (separator2 == null || list2.size() != 0) {
            Object[] a2 = a(list2);
            list2.clear();
            Separator separator3 = new Separator(str, str2, strArr, (PeriodPrinter) a2[0], (PeriodParser) a2[1], z, z2);
            list2.add(separator3);
            list2.add(separator3);
            return this;
        }
        throw new IllegalStateException("Cannot have two adjacent separators");
    }

    private void b() {
        if (this.f != null) {
            throw new IllegalStateException("Prefix not followed by field");
        }
        this.f = null;
    }

    private PeriodFormatterBuilder a(PeriodPrinter periodPrinter, PeriodParser periodParser) {
        this.g.add(periodPrinter);
        this.g.add(periodParser);
        boolean z = false;
        this.h = (periodPrinter == null) | this.h;
        boolean z2 = this.i;
        if (periodParser == null) {
            z = true;
        }
        this.i = z2 | z;
        return this;
    }

    private static PeriodFormatter a(List<Object> list, boolean z, boolean z2) {
        if (!z || !z2) {
            int size = list.size();
            if (size >= 2 && (list.get(0) instanceof Separator)) {
                Separator separator = (Separator) list.get(0);
                if (separator.i == null && separator.g == null) {
                    PeriodFormatter a2 = a(list.subList(2, size), z, z2);
                    Separator a3 = separator.a(a2.getPrinter(), a2.getParser());
                    return new PeriodFormatter(a3, a3);
                }
            }
            Object[] a4 = a(list);
            if (z) {
                return new PeriodFormatter(null, (PeriodParser) a4[1]);
            }
            if (z2) {
                return new PeriodFormatter((PeriodPrinter) a4[0], null);
            }
            return new PeriodFormatter((PeriodPrinter) a4[0], (PeriodParser) a4[1]);
        }
        throw new IllegalStateException("Builder has created neither a printer nor a parser");
    }

    private static Object[] a(List<Object> list) {
        switch (list.size()) {
            case 0:
                return new Object[]{Literal.a, Literal.a};
            case 1:
                return new Object[]{list.get(0), list.get(1)};
            default:
                Composite composite = new Composite(list);
                return new Object[]{composite, composite};
        }
    }
}
