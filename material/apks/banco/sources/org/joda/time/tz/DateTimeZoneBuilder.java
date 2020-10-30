package org.joda.time.tz;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.chrono.ISOChronology;

public class DateTimeZoneBuilder {
    private final ArrayList<RuleSet> a = new ArrayList<>(10);

    static final class DSTZone extends DateTimeZone {
        private static final long serialVersionUID = 6941492635554961361L;
        final int a;
        final Recurrence b;
        final Recurrence c;

        public boolean isFixed() {
            return false;
        }

        static DSTZone a(DataInput dataInput, String str) {
            return new DSTZone(str, (int) DateTimeZoneBuilder.a(dataInput), Recurrence.a(dataInput), Recurrence.a(dataInput));
        }

        DSTZone(String str, int i, Recurrence recurrence, Recurrence recurrence2) {
            super(str);
            this.a = i;
            this.b = recurrence;
            this.c = recurrence2;
        }

        public String getNameKey(long j) {
            return a(j).a();
        }

        public int getOffset(long j) {
            return this.a + a(j).b();
        }

        public int getStandardOffset(long j) {
            return this.a;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0016, code lost:
            if (r5 < 0) goto L_0x0018;
         */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0030  */
        /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long nextTransition(long r9) {
            /*
                r8 = this;
                int r0 = r8.a
                org.joda.time.tz.DateTimeZoneBuilder$Recurrence r1 = r8.b
                org.joda.time.tz.DateTimeZoneBuilder$Recurrence r2 = r8.c
                r3 = 0
                int r5 = r2.b()     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x0018 }
                long r5 = r1.a(r9, r0, r5)     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x0018 }
                int r7 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                if (r7 <= 0) goto L_0x0019
                int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
                if (r7 >= 0) goto L_0x0019
            L_0x0018:
                r5 = r9
            L_0x0019:
                int r1 = r1.b()     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x002b }
                long r0 = r2.a(r9, r0, r1)     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x002b }
                int r2 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                if (r2 <= 0) goto L_0x002a
                int r2 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
                if (r2 >= 0) goto L_0x002a
                goto L_0x002b
            L_0x002a:
                r9 = r0
            L_0x002b:
                int r0 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
                if (r0 <= 0) goto L_0x0030
                goto L_0x0031
            L_0x0030:
                r9 = r5
            L_0x0031:
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.tz.DateTimeZoneBuilder.DSTZone.nextTransition(long):long");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:6:0x001a, code lost:
            if (r7 > 0) goto L_0x001c;
         */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0033  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long previousTransition(long r11) {
            /*
                r10 = this;
                r0 = 1
                long r2 = r11 + r0
                int r11 = r10.a
                org.joda.time.tz.DateTimeZoneBuilder$Recurrence r12 = r10.b
                org.joda.time.tz.DateTimeZoneBuilder$Recurrence r4 = r10.c
                r5 = 0
                int r7 = r4.b()     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x001c }
                long r7 = r12.b(r2, r11, r7)     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x001c }
                int r9 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                if (r9 >= 0) goto L_0x001d
                int r9 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
                if (r9 <= 0) goto L_0x001d
            L_0x001c:
                r7 = r2
            L_0x001d:
                int r12 = r12.b()     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x002f }
                long r11 = r4.b(r2, r11, r12)     // Catch:{ ArithmeticException | IllegalArgumentException -> 0x002f }
                int r4 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                if (r4 >= 0) goto L_0x002e
                int r4 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
                if (r4 <= 0) goto L_0x002e
                goto L_0x002f
            L_0x002e:
                r2 = r11
            L_0x002f:
                int r11 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
                if (r11 <= 0) goto L_0x0034
                r2 = r7
            L_0x0034:
                r11 = 0
                long r11 = r2 - r0
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.tz.DateTimeZoneBuilder.DSTZone.previousTransition(long):long");
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DSTZone)) {
                return false;
            }
            DSTZone dSTZone = (DSTZone) obj;
            if (!getID().equals(dSTZone.getID()) || this.a != dSTZone.a || !this.b.equals(dSTZone.b) || !this.c.equals(dSTZone.c)) {
                z = false;
            }
            return z;
        }

        public void a(DataOutput dataOutput) {
            DateTimeZoneBuilder.a(dataOutput, (long) this.a);
            this.b.a(dataOutput);
            this.c.a(dataOutput);
        }

        private Recurrence a(long j) {
            long j2;
            int i = this.a;
            Recurrence recurrence = this.b;
            Recurrence recurrence2 = this.c;
            try {
                j2 = recurrence.a(j, i, recurrence2.b());
            } catch (ArithmeticException | IllegalArgumentException unused) {
                j2 = j;
            }
            try {
                j = recurrence2.a(j, i, recurrence.b());
            } catch (ArithmeticException | IllegalArgumentException unused2) {
            }
            return j2 > j ? recurrence : recurrence2;
        }
    }

    static final class OfYear {
        final char a;
        final int b;
        final int c;
        final int d;
        final boolean e;
        final int f;

        static OfYear a(DataInput dataInput) {
            OfYear ofYear = new OfYear((char) dataInput.readUnsignedByte(), dataInput.readUnsignedByte(), dataInput.readByte(), dataInput.readUnsignedByte(), dataInput.readBoolean(), (int) DateTimeZoneBuilder.a(dataInput));
            return ofYear;
        }

        OfYear(char c2, int i, int i2, int i3, boolean z, int i4) {
            if (c2 == 'u' || c2 == 'w' || c2 == 's') {
                this.a = c2;
                this.b = i;
                this.c = i2;
                this.d = i3;
                this.e = z;
                this.f = i4;
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown mode: ");
            sb.append(c2);
            throw new IllegalArgumentException(sb.toString());
        }

        public long a(int i, int i2, int i3) {
            if (this.a == 'w') {
                i2 += i3;
            } else if (this.a != 's') {
                i2 = 0;
            }
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            long c2 = c(instanceUTC, instanceUTC.millisOfDay().set(instanceUTC.monthOfYear().set(instanceUTC.year().set(0, i), this.b), this.f));
            if (this.d != 0) {
                c2 = d(instanceUTC, c2);
            }
            return c2 - ((long) i2);
        }

        public long a(long j, int i, int i2) {
            if (this.a == 'w') {
                i += i2;
            } else if (this.a != 's') {
                i = 0;
            }
            long j2 = (long) i;
            long j3 = j + j2;
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            long a2 = a(instanceUTC, instanceUTC.millisOfDay().add(instanceUTC.millisOfDay().set(instanceUTC.monthOfYear().set(j3, this.b), 0), this.f));
            if (this.d != 0) {
                a2 = d(instanceUTC, a2);
                if (a2 <= j3) {
                    a2 = d(instanceUTC, a(instanceUTC, instanceUTC.monthOfYear().set(instanceUTC.year().add(a2, 1), this.b)));
                }
            } else if (a2 <= j3) {
                a2 = a(instanceUTC, instanceUTC.year().add(a2, 1));
            }
            return a2 - j2;
        }

        public long b(long j, int i, int i2) {
            if (this.a == 'w') {
                i += i2;
            } else if (this.a != 's') {
                i = 0;
            }
            long j2 = (long) i;
            long j3 = j + j2;
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            long b2 = b(instanceUTC, instanceUTC.millisOfDay().add(instanceUTC.millisOfDay().set(instanceUTC.monthOfYear().set(j3, this.b), 0), this.f));
            if (this.d != 0) {
                b2 = d(instanceUTC, b2);
                if (b2 >= j3) {
                    b2 = d(instanceUTC, b(instanceUTC, instanceUTC.monthOfYear().set(instanceUTC.year().add(b2, -1), this.b)));
                }
            } else if (b2 >= j3) {
                b2 = b(instanceUTC, instanceUTC.year().add(b2, -1));
            }
            return b2 - j2;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OfYear)) {
                return false;
            }
            OfYear ofYear = (OfYear) obj;
            if (!(this.a == ofYear.a && this.b == ofYear.b && this.c == ofYear.c && this.d == ofYear.d && this.e == ofYear.e && this.f == ofYear.f)) {
                z = false;
            }
            return z;
        }

        public void a(DataOutput dataOutput) {
            dataOutput.writeByte(this.a);
            dataOutput.writeByte(this.b);
            dataOutput.writeByte(this.c);
            dataOutput.writeByte(this.d);
            dataOutput.writeBoolean(this.e);
            DateTimeZoneBuilder.a(dataOutput, (long) this.f);
        }

        private long a(Chronology chronology, long j) {
            try {
                return c(chronology, j);
            } catch (IllegalArgumentException e2) {
                if (this.b == 2 && this.c == 29) {
                    while (!chronology.year().isLeap(j)) {
                        j = chronology.year().add(j, 1);
                    }
                    return c(chronology, j);
                }
                throw e2;
            }
        }

        private long b(Chronology chronology, long j) {
            try {
                return c(chronology, j);
            } catch (IllegalArgumentException e2) {
                if (this.b == 2 && this.c == 29) {
                    while (!chronology.year().isLeap(j)) {
                        j = chronology.year().add(j, -1);
                    }
                    return c(chronology, j);
                }
                throw e2;
            }
        }

        private long c(Chronology chronology, long j) {
            if (this.c >= 0) {
                return chronology.dayOfMonth().set(j, this.c);
            }
            return chronology.dayOfMonth().add(chronology.monthOfYear().add(chronology.dayOfMonth().set(j, 1), 1), this.c);
        }

        private long d(Chronology chronology, long j) {
            int i = this.d - chronology.dayOfWeek().get(j);
            if (i == 0) {
                return j;
            }
            if (this.e) {
                if (i < 0) {
                    i += 7;
                }
            } else if (i > 0) {
                i -= 7;
            }
            return chronology.dayOfWeek().add(j, i);
        }
    }

    static final class PrecalculatedZone extends DateTimeZone {
        private static final long serialVersionUID = 7811976468055766265L;
        private final long[] a;
        private final int[] b;
        private final int[] c;
        private final String[] d;
        private final DSTZone e;

        public boolean isFixed() {
            return false;
        }

        static PrecalculatedZone a(DataInput dataInput, String str) {
            int i;
            int readUnsignedShort = dataInput.readUnsignedShort();
            String[] strArr = new String[readUnsignedShort];
            for (int i2 = 0; i2 < readUnsignedShort; i2++) {
                strArr[i2] = dataInput.readUTF();
            }
            int readInt = dataInput.readInt();
            long[] jArr = new long[readInt];
            int[] iArr = new int[readInt];
            int[] iArr2 = new int[readInt];
            String[] strArr2 = new String[readInt];
            for (int i3 = 0; i3 < readInt; i3++) {
                jArr[i3] = DateTimeZoneBuilder.a(dataInput);
                iArr[i3] = (int) DateTimeZoneBuilder.a(dataInput);
                iArr2[i3] = (int) DateTimeZoneBuilder.a(dataInput);
                if (readUnsignedShort < 256) {
                    try {
                        i = dataInput.readUnsignedByte();
                    } catch (ArrayIndexOutOfBoundsException unused) {
                        throw new IOException("Invalid encoding");
                    }
                } else {
                    i = dataInput.readUnsignedShort();
                }
                strArr2[i3] = strArr[i];
            }
            PrecalculatedZone precalculatedZone = new PrecalculatedZone(str, jArr, iArr, iArr2, strArr2, dataInput.readBoolean() ? DSTZone.a(dataInput, str) : null);
            return precalculatedZone;
        }

        static PrecalculatedZone a(String str, boolean z, ArrayList<Transition> arrayList, DSTZone dSTZone) {
            DSTZone dSTZone2;
            DSTZone dSTZone3;
            String str2 = str;
            DSTZone dSTZone4 = dSTZone;
            int size = arrayList.size();
            if (size == 0) {
                throw new IllegalArgumentException();
            }
            long[] jArr = new long[size];
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            String[] strArr = new String[size];
            int i = 0;
            Transition transition = null;
            int i2 = 0;
            while (i2 < size) {
                Transition transition2 = (Transition) arrayList.get(i2);
                if (!transition2.a(transition)) {
                    throw new IllegalArgumentException(str2);
                }
                jArr[i2] = transition2.a();
                iArr[i2] = transition2.c();
                iArr2[i2] = transition2.d();
                strArr[i2] = transition2.b();
                i2++;
                transition = transition2;
            }
            String[] strArr2 = new String[5];
            String[][] zoneStrings = new DateFormatSymbols(Locale.ENGLISH).getZoneStrings();
            String[] strArr3 = strArr2;
            for (String[] strArr4 : zoneStrings) {
                if (strArr4 != null && strArr4.length == 5 && str2.equals(strArr4[0])) {
                    strArr3 = strArr4;
                }
            }
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            while (i < strArr.length - 1) {
                String str3 = strArr[i];
                int i3 = i + 1;
                String str4 = strArr[i3];
                long j = (long) iArr[i];
                long j2 = (long) iArr[i3];
                String[] strArr5 = strArr;
                String str5 = str3;
                long j3 = (long) iArr2[i];
                int[] iArr3 = iArr;
                int[] iArr4 = iArr2;
                long j4 = (long) iArr2[i3];
                long j5 = jArr[i];
                long j6 = jArr[i3];
                long j7 = j;
                long[] jArr2 = jArr;
                int i4 = i3;
                String str6 = str4;
                Period period = r11;
                Period period2 = new Period(j5, j6, PeriodType.yearMonthDay(), (Chronology) instanceUTC);
                if (j7 != j2 && j3 == j4) {
                    String str7 = str5;
                    if (str7.equals(str6) && period.getYears() == 0 && period.getMonths() > 4 && period.getMonths() < 8 && str7.equals(strArr3[2]) && str7.equals(strArr3[4])) {
                        if (ZoneInfoLogger.verbose()) {
                            PrintStream printStream = System.out;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Fixing duplicate name key - ");
                            sb.append(str6);
                            printStream.println(sb.toString());
                            PrintStream printStream2 = System.out;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("     - ");
                            sb2.append(new DateTime(jArr2[i], (Chronology) instanceUTC));
                            sb2.append(" - ");
                            sb2.append(new DateTime(jArr2[i4], (Chronology) instanceUTC));
                            printStream2.println(sb2.toString());
                        }
                        if (j7 > j2) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str7);
                            sb3.append("-Summer");
                            strArr5[i] = sb3.toString().intern();
                        } else if (j7 < j2) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str6);
                            sb4.append("-Summer");
                            strArr5[i4] = sb4.toString().intern();
                            i = i4 + 1;
                            strArr = strArr5;
                            iArr = iArr3;
                            iArr2 = iArr4;
                            jArr = jArr2;
                            String str8 = str;
                            dSTZone4 = dSTZone;
                        }
                    }
                }
                i4 = i;
                i = i4 + 1;
                strArr = strArr5;
                iArr = iArr3;
                iArr2 = iArr4;
                jArr = jArr2;
                String str82 = str;
                dSTZone4 = dSTZone;
            }
            DSTZone dSTZone5 = dSTZone4;
            long[] jArr3 = jArr;
            int[] iArr5 = iArr;
            int[] iArr6 = iArr2;
            String[] strArr6 = strArr;
            if (dSTZone5 == null || !dSTZone5.b.a().equals(dSTZone5.c.a())) {
                dSTZone2 = dSTZone5;
            } else {
                if (ZoneInfoLogger.verbose()) {
                    PrintStream printStream3 = System.out;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Fixing duplicate recurrent name key - ");
                    sb5.append(dSTZone5.b.a());
                    printStream3.println(sb5.toString());
                }
                if (dSTZone5.b.b() > 0) {
                    dSTZone3 = new DSTZone(dSTZone.getID(), dSTZone5.a, dSTZone5.b.b("-Summer"), dSTZone5.c);
                } else {
                    dSTZone3 = new DSTZone(dSTZone.getID(), dSTZone5.a, dSTZone5.b, dSTZone5.c.b("-Summer"));
                }
                dSTZone2 = dSTZone3;
            }
            PrecalculatedZone precalculatedZone = new PrecalculatedZone(z ? str : "", jArr3, iArr5, iArr6, strArr6, dSTZone2);
            return precalculatedZone;
        }

        private PrecalculatedZone(String str, long[] jArr, int[] iArr, int[] iArr2, String[] strArr, DSTZone dSTZone) {
            super(str);
            this.a = jArr;
            this.b = iArr;
            this.c = iArr2;
            this.d = strArr;
            this.e = dSTZone;
        }

        public String getNameKey(long j) {
            long[] jArr = this.a;
            int binarySearch = Arrays.binarySearch(jArr, j);
            if (binarySearch >= 0) {
                return this.d[binarySearch];
            }
            int i = binarySearch ^ -1;
            if (i < jArr.length) {
                return i > 0 ? this.d[i - 1] : "UTC";
            }
            if (this.e == null) {
                return this.d[i - 1];
            }
            return this.e.getNameKey(j);
        }

        public int getOffset(long j) {
            long[] jArr = this.a;
            int binarySearch = Arrays.binarySearch(jArr, j);
            if (binarySearch >= 0) {
                return this.b[binarySearch];
            }
            int i = binarySearch ^ -1;
            if (i < jArr.length) {
                if (i > 0) {
                    return this.b[i - 1];
                }
                return 0;
            } else if (this.e == null) {
                return this.b[i - 1];
            } else {
                return this.e.getOffset(j);
            }
        }

        public int getStandardOffset(long j) {
            long[] jArr = this.a;
            int binarySearch = Arrays.binarySearch(jArr, j);
            if (binarySearch >= 0) {
                return this.c[binarySearch];
            }
            int i = binarySearch ^ -1;
            if (i < jArr.length) {
                if (i > 0) {
                    return this.c[i - 1];
                }
                return 0;
            } else if (this.e == null) {
                return this.c[i - 1];
            } else {
                return this.e.getStandardOffset(j);
            }
        }

        public long nextTransition(long j) {
            long[] jArr = this.a;
            int binarySearch = Arrays.binarySearch(jArr, j);
            int i = binarySearch >= 0 ? binarySearch + 1 : binarySearch ^ -1;
            if (i < jArr.length) {
                return jArr[i];
            }
            if (this.e == null) {
                return j;
            }
            long j2 = jArr[jArr.length - 1];
            if (j < j2) {
                j = j2;
            }
            return this.e.nextTransition(j);
        }

        public long previousTransition(long j) {
            long[] jArr = this.a;
            int binarySearch = Arrays.binarySearch(jArr, j);
            if (binarySearch >= 0) {
                return j > Long.MIN_VALUE ? j - 1 : j;
            }
            int i = binarySearch ^ -1;
            if (i < jArr.length) {
                if (i > 0) {
                    long j2 = jArr[i - 1];
                    if (j2 > Long.MIN_VALUE) {
                        return j2 - 1;
                    }
                }
                return j;
            }
            if (this.e != null) {
                long previousTransition = this.e.previousTransition(j);
                if (previousTransition < j) {
                    return previousTransition;
                }
            }
            long j3 = jArr[i - 1];
            return j3 > Long.MIN_VALUE ? j3 - 1 : j;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PrecalculatedZone)) {
                return false;
            }
            PrecalculatedZone precalculatedZone = (PrecalculatedZone) obj;
            if (!getID().equals(precalculatedZone.getID()) || !Arrays.equals(this.a, precalculatedZone.a) || !Arrays.equals(this.d, precalculatedZone.d) || !Arrays.equals(this.b, precalculatedZone.b) || !Arrays.equals(this.c, precalculatedZone.c) || (this.e != null ? !this.e.equals(precalculatedZone.e) : precalculatedZone.e != null)) {
                z = false;
            }
            return z;
        }

        public void a(DataOutput dataOutput) {
            int length = this.a.length;
            HashSet<String> hashSet = new HashSet<>();
            boolean z = false;
            for (int i = 0; i < length; i++) {
                hashSet.add(this.d[i]);
            }
            int size = hashSet.size();
            if (size > 65535) {
                throw new UnsupportedOperationException("String pool is too large");
            }
            String[] strArr = new String[size];
            int i2 = 0;
            for (String str : hashSet) {
                strArr[i2] = str;
                i2++;
            }
            dataOutput.writeShort(size);
            for (int i3 = 0; i3 < size; i3++) {
                dataOutput.writeUTF(strArr[i3]);
            }
            dataOutput.writeInt(length);
            for (int i4 = 0; i4 < length; i4++) {
                DateTimeZoneBuilder.a(dataOutput, this.a[i4]);
                DateTimeZoneBuilder.a(dataOutput, (long) this.b[i4]);
                DateTimeZoneBuilder.a(dataOutput, (long) this.c[i4]);
                String str2 = this.d[i4];
                int i5 = 0;
                while (true) {
                    if (i5 >= size) {
                        break;
                    } else if (!strArr[i5].equals(str2)) {
                        i5++;
                    } else if (size < 256) {
                        dataOutput.writeByte(i5);
                    } else {
                        dataOutput.writeShort(i5);
                    }
                }
            }
            if (this.e != null) {
                z = true;
            }
            dataOutput.writeBoolean(z);
            if (this.e != null) {
                this.e.a(dataOutput);
            }
        }

        public boolean a() {
            if (this.e != null) {
                return true;
            }
            long[] jArr = this.a;
            if (jArr.length <= 1) {
                return false;
            }
            double d2 = 0.0d;
            int i = 0;
            for (int i2 = 1; i2 < jArr.length; i2++) {
                long j = jArr[i2] - jArr[i2 - 1];
                if (j < 63158400000L) {
                    d2 += (double) j;
                    i++;
                }
            }
            if (i <= 0 || (d2 / ((double) i)) / 8.64E7d < 25.0d) {
                return false;
            }
            return true;
        }
    }

    static final class Recurrence {
        final OfYear a;
        final String b;
        final int c;

        static Recurrence a(DataInput dataInput) {
            return new Recurrence(OfYear.a(dataInput), dataInput.readUTF(), (int) DateTimeZoneBuilder.a(dataInput));
        }

        Recurrence(OfYear ofYear, String str, int i) {
            this.a = ofYear;
            this.b = str;
            this.c = i;
        }

        public long a(long j, int i, int i2) {
            return this.a.a(j, i, i2);
        }

        public long b(long j, int i, int i2) {
            return this.a.b(j, i, i2);
        }

        public String a() {
            return this.b;
        }

        public int b() {
            return this.c;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Recurrence)) {
                return false;
            }
            Recurrence recurrence = (Recurrence) obj;
            if (this.c != recurrence.c || !this.b.equals(recurrence.b) || !this.a.equals(recurrence.a)) {
                z = false;
            }
            return z;
        }

        public void a(DataOutput dataOutput) {
            this.a.a(dataOutput);
            dataOutput.writeUTF(this.b);
            DateTimeZoneBuilder.a(dataOutput, (long) this.c);
        }

        /* access modifiers changed from: 0000 */
        public Recurrence a(String str) {
            return new Recurrence(this.a, str, this.c);
        }

        /* access modifiers changed from: 0000 */
        public Recurrence b(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.b);
            sb.append(str);
            return a(sb.toString().intern());
        }
    }

    static final class Rule {
        final Recurrence a;
        final int b;
        final int c;

        Rule(Recurrence recurrence, int i, int i2) {
            this.a = recurrence;
            this.b = i;
            this.c = i2;
        }

        public int a() {
            return this.c;
        }

        public String b() {
            return this.a.a();
        }

        public int c() {
            return this.a.b();
        }

        public long a(long j, int i, int i2) {
            int i3;
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            int i4 = i + i2;
            if (j == Long.MIN_VALUE) {
                i3 = Integer.MIN_VALUE;
            } else {
                i3 = instanceUTC.year().get(j + ((long) i4));
            }
            long a2 = this.a.a(i3 < this.b ? (instanceUTC.year().set(0, this.b) - ((long) i4)) - 1 : j, i, i2);
            return (a2 <= j || instanceUTC.year().get(a2 + ((long) i4)) <= this.c) ? a2 : j;
        }
    }

    static final class RuleSet {
        private static final int a = (ISOChronology.getInstanceUTC().year().get(DateTimeUtils.currentTimeMillis()) + 100);
        private int b;
        private ArrayList<Rule> c;
        private String d;
        private int e;
        private int f;
        private OfYear g;

        RuleSet() {
            this.c = new ArrayList<>(10);
            this.f = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        }

        RuleSet(RuleSet ruleSet) {
            this.b = ruleSet.b;
            this.c = new ArrayList<>(ruleSet.c);
            this.d = ruleSet.d;
            this.e = ruleSet.e;
            this.f = ruleSet.f;
            this.g = ruleSet.g;
        }

        public void a(int i) {
            this.b = i;
        }

        public void a(String str, int i) {
            this.d = str;
            this.e = i;
        }

        public void a(Rule rule) {
            if (!this.c.contains(rule)) {
                this.c.add(rule);
            }
        }

        public void a(int i, OfYear ofYear) {
            this.f = i;
            this.g = ofYear;
        }

        public Transition a(long j) {
            if (this.d != null) {
                Transition transition = new Transition(j, this.d, this.b + this.e, this.b);
                return transition;
            }
            ArrayList<Rule> arrayList = new ArrayList<>(this.c);
            long j2 = Long.MIN_VALUE;
            int i = 0;
            Transition transition2 = null;
            while (true) {
                Transition a2 = a(j2, i);
                if (a2 == null) {
                    break;
                }
                long a3 = a2.a();
                if (a3 == j) {
                    transition2 = new Transition(j, a2);
                    break;
                } else if (a3 > j) {
                    if (transition2 == null) {
                        Iterator it = arrayList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Rule rule = (Rule) it.next();
                            if (rule.c() == 0) {
                                transition2 = new Transition(j, rule, this.b);
                                break;
                            }
                        }
                    }
                    if (transition2 == null) {
                        Transition transition3 = new Transition(j, a2.b(), this.b, this.b);
                        transition2 = transition3;
                    }
                } else {
                    transition2 = new Transition(j, a2);
                    i = a2.e();
                    j2 = a3;
                }
            }
            this.c = arrayList;
            return transition2;
        }

        public Transition a(long j, int i) {
            ISOChronology instanceUTC = ISOChronology.getInstanceUTC();
            Iterator it = this.c.iterator();
            long j2 = Long.MAX_VALUE;
            Rule rule = null;
            while (it.hasNext()) {
                Rule rule2 = (Rule) it.next();
                long a2 = rule2.a(j, this.b, i);
                if (a2 <= j) {
                    it.remove();
                } else if (a2 <= j2) {
                    rule = rule2;
                    j2 = a2;
                }
            }
            if (rule == null || instanceUTC.year().get(j2) >= a) {
                return null;
            }
            if (this.f >= Integer.MAX_VALUE || j2 < this.g.a(this.f, this.b, i)) {
                return new Transition(j2, rule, this.b);
            }
            return null;
        }

        public long b(int i) {
            if (this.f == Integer.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            return this.g.a(this.f, this.b, i);
        }

        public DSTZone a(String str) {
            if (this.c.size() == 2) {
                Rule rule = (Rule) this.c.get(0);
                Rule rule2 = (Rule) this.c.get(1);
                if (rule.a() == Integer.MAX_VALUE && rule2.a() == Integer.MAX_VALUE) {
                    return new DSTZone(str, this.b, rule.a, rule2.a);
                }
            }
            return null;
        }
    }

    static final class Transition {
        private final long a;
        private final String b;
        private final int c;
        private final int d;

        Transition(long j, Transition transition) {
            this.a = j;
            this.b = transition.b;
            this.c = transition.c;
            this.d = transition.d;
        }

        Transition(long j, Rule rule, int i) {
            this.a = j;
            this.b = rule.b();
            this.c = rule.c() + i;
            this.d = i;
        }

        Transition(long j, String str, int i, int i2) {
            this.a = j;
            this.b = str;
            this.c = i;
            this.d = i2;
        }

        public long a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }

        public int c() {
            return this.c;
        }

        public int d() {
            return this.d;
        }

        public int e() {
            return this.c - this.d;
        }

        public boolean a(Transition transition) {
            boolean z = true;
            if (transition == null) {
                return true;
            }
            if (this.a <= transition.a || (this.c == transition.c && this.b.equals(transition.b))) {
                z = false;
            }
            return z;
        }
    }

    public static DateTimeZone readFrom(InputStream inputStream, String str) {
        if (inputStream instanceof DataInput) {
            return readFrom((DataInput) inputStream, str);
        }
        return readFrom((DataInput) new DataInputStream(inputStream), str);
    }

    public static DateTimeZone readFrom(DataInput dataInput, String str) {
        int readUnsignedByte = dataInput.readUnsignedByte();
        if (readUnsignedByte == 67) {
            return CachedDateTimeZone.forZone(PrecalculatedZone.a(dataInput, str));
        }
        if (readUnsignedByte == 70) {
            DateTimeZone fixedDateTimeZone = new FixedDateTimeZone(str, dataInput.readUTF(), (int) a(dataInput), (int) a(dataInput));
            if (fixedDateTimeZone.equals(DateTimeZone.UTC)) {
                fixedDateTimeZone = DateTimeZone.UTC;
            }
            return fixedDateTimeZone;
        } else if (readUnsignedByte == 80) {
            return PrecalculatedZone.a(dataInput, str);
        } else {
            throw new IOException("Invalid encoding");
        }
    }

    static void a(DataOutput dataOutput, long j) {
        if (j % 1800000 == 0) {
            long j2 = j / 1800000;
            if (((j2 << 58) >> 58) == j2) {
                dataOutput.writeByte((int) (j2 & 63));
                return;
            }
        }
        if (j % 60000 == 0) {
            long j3 = j / 60000;
            if (((j3 << 34) >> 34) == j3) {
                dataOutput.writeInt(1073741824 | ((int) (j3 & 1073741823)));
                return;
            }
        }
        if (j % 1000 == 0) {
            long j4 = j / 1000;
            if (((j4 << 26) >> 26) == j4) {
                dataOutput.writeByte(((int) ((j4 >> 32) & 63)) | 128);
                dataOutput.writeInt((int) (j4 & -1));
                return;
            }
        }
        dataOutput.writeByte(j < 0 ? 255 : 192);
        dataOutput.writeLong(j);
    }

    static long a(DataInput dataInput) {
        int readUnsignedByte = dataInput.readUnsignedByte();
        switch (readUnsignedByte >> 6) {
            case 1:
                return ((long) (dataInput.readUnsignedByte() | ((readUnsignedByte << 26) >> 2) | (dataInput.readUnsignedByte() << 16) | (dataInput.readUnsignedByte() << 8))) * 60000;
            case 2:
                return (((((long) readUnsignedByte) << 58) >> 26) | ((long) (dataInput.readUnsignedByte() << 24)) | ((long) (dataInput.readUnsignedByte() << 16)) | ((long) (dataInput.readUnsignedByte() << 8)) | ((long) dataInput.readUnsignedByte())) * 1000;
            case 3:
                return dataInput.readLong();
            default:
                return ((long) ((readUnsignedByte << 26) >> 26)) * 1800000;
        }
    }

    private static DateTimeZone a(String str, String str2, int i, int i2) {
        if (!"UTC".equals(str) || !str.equals(str2) || i != 0 || i2 != 0) {
            return new FixedDateTimeZone(str, str2, i, i2);
        }
        return DateTimeZone.UTC;
    }

    public DateTimeZoneBuilder addCutover(int i, char c, int i2, int i3, int i4, boolean z, int i5) {
        if (this.a.size() > 0) {
            OfYear ofYear = new OfYear(c, i2, i3, i4, z, i5);
            ((RuleSet) this.a.get(this.a.size() - 1)).a(i, ofYear);
        }
        this.a.add(new RuleSet());
        return this;
    }

    public DateTimeZoneBuilder setStandardOffset(int i) {
        a().a(i);
        return this;
    }

    public DateTimeZoneBuilder setFixedSavings(String str, int i) {
        a().a(str, i);
        return this;
    }

    public DateTimeZoneBuilder addRecurringSavings(String str, int i, int i2, int i3, char c, int i4, int i5, int i6, boolean z, int i7) {
        int i8 = i2;
        int i9 = i3;
        if (i8 <= i9) {
            OfYear ofYear = new OfYear(c, i4, i5, i6, z, i7);
            a().a(new Rule(new Recurrence(ofYear, str, i), i8, i9));
        }
        return this;
    }

    private RuleSet a() {
        if (this.a.size() == 0) {
            addCutover(Integer.MIN_VALUE, 'w', 1, 1, 0, false, 0);
        }
        return (RuleSet) this.a.get(this.a.size() - 1);
    }

    public DateTimeZone toDateTimeZone(String str, boolean z) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList();
        long j = Long.MIN_VALUE;
        int size = this.a.size();
        DSTZone dSTZone = null;
        for (int i = 0; i < size; i++) {
            RuleSet ruleSet = (RuleSet) this.a.get(i);
            Transition a2 = ruleSet.a(j);
            if (a2 != null) {
                a(arrayList, a2);
                long a3 = a2.a();
                int e = a2.e();
                RuleSet ruleSet2 = new RuleSet(ruleSet);
                while (true) {
                    Transition a4 = ruleSet2.a(a3, e);
                    if (a4 == null || (a(arrayList, a4) && dSTZone != null)) {
                        j = ruleSet2.b(e);
                    } else {
                        long a5 = a4.a();
                        int e2 = a4.e();
                        if (dSTZone == null && i == size - 1) {
                            dSTZone = ruleSet2.a(str);
                        }
                        e = e2;
                        a3 = a5;
                    }
                }
                j = ruleSet2.b(e);
            }
        }
        if (arrayList.size() == 0) {
            if (dSTZone != null) {
                return dSTZone;
            }
            return a(str, "UTC", 0, 0);
        } else if (arrayList.size() == 1 && dSTZone == null) {
            Transition transition = (Transition) arrayList.get(0);
            return a(str, transition.b(), transition.c(), transition.d());
        } else {
            PrecalculatedZone a6 = PrecalculatedZone.a(str, z, arrayList, dSTZone);
            return a6.a() ? CachedDateTimeZone.forZone(a6) : a6;
        }
    }

    private boolean a(ArrayList<Transition> arrayList, Transition transition) {
        int size = arrayList.size();
        if (size == 0) {
            arrayList.add(transition);
            return true;
        }
        int i = size - 1;
        Transition transition2 = (Transition) arrayList.get(i);
        int i2 = 0;
        if (!transition.a(transition2)) {
            return false;
        }
        if (size >= 2) {
            i2 = ((Transition) arrayList.get(size - 2)).c();
        }
        if (transition.a() + ((long) transition2.c()) != transition2.a() + ((long) i2)) {
            arrayList.add(transition);
            return true;
        }
        arrayList.remove(i);
        return a(arrayList, transition);
    }

    public void writeTo(String str, OutputStream outputStream) {
        if (outputStream instanceof DataOutput) {
            writeTo(str, (DataOutput) outputStream);
            return;
        }
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        writeTo(str, (DataOutput) dataOutputStream);
        dataOutputStream.flush();
    }

    public void writeTo(String str, DataOutput dataOutput) {
        DateTimeZone dateTimeZone = toDateTimeZone(str, false);
        if (dateTimeZone instanceof FixedDateTimeZone) {
            dataOutput.writeByte(70);
            dataOutput.writeUTF(dateTimeZone.getNameKey(0));
            a(dataOutput, (long) dateTimeZone.getOffset(0));
            a(dataOutput, (long) dateTimeZone.getStandardOffset(0));
            return;
        }
        if (dateTimeZone instanceof CachedDateTimeZone) {
            dataOutput.writeByte(67);
            dateTimeZone = ((CachedDateTimeZone) dateTimeZone).getUncachedZone();
        } else {
            dataOutput.writeByte(80);
        }
        ((PrecalculatedZone) dateTimeZone).a(dataOutput);
    }
}
