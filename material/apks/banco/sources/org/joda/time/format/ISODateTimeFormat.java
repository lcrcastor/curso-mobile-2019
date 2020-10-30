package org.joda.time.format;

import java.util.Collection;
import org.joda.time.DateTimeFieldType;

public class ISODateTimeFormat {

    static final class Constants {
        /* access modifiers changed from: private */
        public static final DateTimeFormatter A = ag();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter B = ah();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter C = ai();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter D = aj();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter E = ak();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter F = al();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter G = ap();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter H = aq();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter I = am();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter J = an();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter K = ao();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter L = ar();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter M = as();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter N = at();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter O = au();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter P = av();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter Q = aw();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter R = ax();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter S = ay();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter T = az();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter U = aA();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter V = aB();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter W = aC();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter X = aD();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter Y = Z();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter Z = ac();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter a = aR();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter aa = X();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ab = Y();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ac = aa();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ad = ab();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ae = ad();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter af = ae();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter ag = af();
        private static final DateTimeFormatter b = aS();
        private static final DateTimeFormatter c = aT();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter d = aU();
        private static final DateTimeFormatter e = aV();
        private static final DateTimeFormatter f = aW();
        private static final DateTimeFormatter g = aX();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter h = aZ();
        private static final DateTimeFormatter i = ba();
        private static final DateTimeFormatter j = bb();
        private static final DateTimeFormatter k = bc();
        private static final DateTimeFormatter l = bd();
        private static final DateTimeFormatter m = aY();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter n = aE();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter o = aF();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter p = aG();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter q = aH();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter r = aI();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter s = aJ();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter t = aK();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter u = aL();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter v = aM();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter w = aN();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter x = aO();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter y = aP();
        /* access modifiers changed from: private */
        public static final DateTimeFormatter z = aQ();

        Constants() {
        }

        private static DateTimeFormatter X() {
            if (aa != null) {
                return aa;
            }
            return new DateTimeFormatterBuilder().append(Z()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').append(bd()).toParser()).toFormatter();
        }

        private static DateTimeFormatter Y() {
            if (ab == null) {
                return Z().withZoneUTC();
            }
            return ab;
        }

        private static DateTimeFormatter Z() {
            if (Y != null) {
                return Y;
            }
            return new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(aR()).appendOptional(new DateTimeFormatterBuilder().append(aS()).appendOptional(aT().getParser()).toParser()).toParser(), new DateTimeFormatterBuilder().append(aU()).append(aV()).appendOptional(aW().getParser()).toParser(), new DateTimeFormatterBuilder().append(aR()).append(aX()).toParser()}).toFormatter();
        }

        private static DateTimeFormatter aa() {
            if (ac == null) {
                return new DateTimeFormatterBuilder().appendOptional(aY().getParser()).append(ac()).appendOptional(bd().getParser()).toFormatter();
            }
            return ac;
        }

        private static DateTimeFormatter ab() {
            if (ad == null) {
                return new DateTimeFormatterBuilder().appendOptional(aY().getParser()).append(ac()).toFormatter().withZoneUTC();
            }
            return ad;
        }

        private static DateTimeFormatter ac() {
            if (Z != null) {
                return Z;
            }
            DateTimeParser parser = new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().appendLiteral('.').toParser(), new DateTimeFormatterBuilder().appendLiteral(',').toParser()}).toParser();
            return new DateTimeFormatterBuilder().append(aZ()).append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(ba()).append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(bb()).appendOptional(new DateTimeFormatterBuilder().append(parser).appendFractionOfSecond(1, 9).toParser()).toParser(), new DateTimeFormatterBuilder().append(parser).appendFractionOfMinute(1, 9).toParser(), null}).toParser(), new DateTimeFormatterBuilder().append(parser).appendFractionOfHour(1, 9).toParser(), null}).toFormatter();
        }

        private static DateTimeFormatter ad() {
            if (ae != null) {
                return ae;
            }
            DateTimeParser parser = new DateTimeFormatterBuilder().appendLiteral('T').append(ac()).appendOptional(bd().getParser()).toParser();
            return new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{parser, ae().getParser()}).toFormatter();
        }

        private static DateTimeFormatter ae() {
            if (af != null) {
                return af;
            }
            return new DateTimeFormatterBuilder().append(Z()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').appendOptional(ac().getParser()).appendOptional(bd().getParser()).toParser()).toFormatter();
        }

        private static DateTimeFormatter af() {
            if (ag != null) {
                return ag;
            }
            return new DateTimeFormatterBuilder().append(Z()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').append(ac()).toParser()).toFormatter().withZoneUTC();
        }

        private static DateTimeFormatter ag() {
            if (A == null) {
                return new DateTimeFormatterBuilder().append(aL()).append(bd()).toFormatter();
            }
            return A;
        }

        private static DateTimeFormatter ah() {
            if (B == null) {
                return new DateTimeFormatterBuilder().append(aJ()).append(bd()).toFormatter();
            }
            return B;
        }

        private static DateTimeFormatter ai() {
            if (C == null) {
                return new DateTimeFormatterBuilder().append(aY()).append(ag()).toFormatter();
            }
            return C;
        }

        private static DateTimeFormatter aj() {
            if (D == null) {
                return new DateTimeFormatterBuilder().append(aY()).append(ah()).toFormatter();
            }
            return D;
        }

        private static DateTimeFormatter ak() {
            if (E == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(ai()).toFormatter();
            }
            return E;
        }

        private static DateTimeFormatter al() {
            if (F == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(aj()).toFormatter();
            }
            return F;
        }

        private static DateTimeFormatter am() {
            if (I == null) {
                return new DateTimeFormatterBuilder().append(aR()).append(aX()).toFormatter();
            }
            return I;
        }

        private static DateTimeFormatter an() {
            if (J == null) {
                return new DateTimeFormatterBuilder().append(am()).append(ai()).toFormatter();
            }
            return J;
        }

        private static DateTimeFormatter ao() {
            if (K == null) {
                return new DateTimeFormatterBuilder().append(am()).append(aj()).toFormatter();
            }
            return K;
        }

        private static DateTimeFormatter ap() {
            if (G == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.weekDate()).append(ai()).toFormatter();
            }
            return G;
        }

        private static DateTimeFormatter aq() {
            if (H == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.weekDate()).append(aj()).toFormatter();
            }
            return H;
        }

        private static DateTimeFormatter ar() {
            if (L == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 4).appendFixedDecimal(DateTimeFieldType.monthOfYear(), 2).appendFixedDecimal(DateTimeFieldType.dayOfMonth(), 2).toFormatter();
            }
            return L;
        }

        private static DateTimeFormatter as() {
            if (M == null) {
                return new DateTimeFormatterBuilder().appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendLiteral('.').appendFractionOfSecond(3, 9).appendTimeZoneOffset("Z", false, 2, 2).toFormatter();
            }
            return M;
        }

        private static DateTimeFormatter at() {
            if (N == null) {
                return new DateTimeFormatterBuilder().appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendTimeZoneOffset("Z", false, 2, 2).toFormatter();
            }
            return N;
        }

        private static DateTimeFormatter au() {
            if (O == null) {
                return new DateTimeFormatterBuilder().append(aY()).append(as()).toFormatter();
            }
            return O;
        }

        private static DateTimeFormatter av() {
            if (P == null) {
                return new DateTimeFormatterBuilder().append(aY()).append(at()).toFormatter();
            }
            return P;
        }

        private static DateTimeFormatter aw() {
            if (Q == null) {
                return new DateTimeFormatterBuilder().append(ar()).append(au()).toFormatter();
            }
            return Q;
        }

        private static DateTimeFormatter ax() {
            if (R == null) {
                return new DateTimeFormatterBuilder().append(ar()).append(av()).toFormatter();
            }
            return R;
        }

        private static DateTimeFormatter ay() {
            if (S == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 4).appendFixedDecimal(DateTimeFieldType.dayOfYear(), 3).toFormatter();
            }
            return S;
        }

        private static DateTimeFormatter az() {
            if (T == null) {
                return new DateTimeFormatterBuilder().append(ay()).append(au()).toFormatter();
            }
            return T;
        }

        private static DateTimeFormatter aA() {
            if (U == null) {
                return new DateTimeFormatterBuilder().append(ay()).append(av()).toFormatter();
            }
            return U;
        }

        private static DateTimeFormatter aB() {
            if (V == null) {
                return new DateTimeFormatterBuilder().appendWeekyear(4, 4).appendLiteral('W').appendFixedDecimal(DateTimeFieldType.weekOfWeekyear(), 2).appendFixedDecimal(DateTimeFieldType.dayOfWeek(), 1).toFormatter();
            }
            return V;
        }

        private static DateTimeFormatter aC() {
            if (W == null) {
                return new DateTimeFormatterBuilder().append(aB()).append(au()).toFormatter();
            }
            return W;
        }

        private static DateTimeFormatter aD() {
            if (X == null) {
                return new DateTimeFormatterBuilder().append(aB()).append(av()).toFormatter();
            }
            return X;
        }

        private static DateTimeFormatter aE() {
            if (n == null) {
                return new DateTimeFormatterBuilder().append(aR()).append(aS()).toFormatter();
            }
            return n;
        }

        private static DateTimeFormatter aF() {
            if (o == null) {
                return new DateTimeFormatterBuilder().append(aR()).append(aS()).append(aT()).toFormatter();
            }
            return o;
        }

        private static DateTimeFormatter aG() {
            if (p == null) {
                return new DateTimeFormatterBuilder().append(aU()).append(aV()).toFormatter();
            }
            return p;
        }

        private static DateTimeFormatter aH() {
            if (q == null) {
                return new DateTimeFormatterBuilder().append(aU()).append(aV()).append(aW()).toFormatter();
            }
            return q;
        }

        private static DateTimeFormatter aI() {
            if (r == null) {
                return new DateTimeFormatterBuilder().append(aZ()).append(ba()).toFormatter();
            }
            return r;
        }

        private static DateTimeFormatter aJ() {
            if (s == null) {
                return new DateTimeFormatterBuilder().append(aZ()).append(ba()).append(bb()).toFormatter();
            }
            return s;
        }

        private static DateTimeFormatter aK() {
            if (t == null) {
                return new DateTimeFormatterBuilder().append(aZ()).append(ba()).append(bb()).appendLiteral('.').appendFractionOfSecond(3, 3).toFormatter();
            }
            return t;
        }

        private static DateTimeFormatter aL() {
            if (u == null) {
                return new DateTimeFormatterBuilder().append(aZ()).append(ba()).append(bb()).append(bc()).toFormatter();
            }
            return u;
        }

        private static DateTimeFormatter aM() {
            if (v == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(aY()).append(ISODateTimeFormat.hour()).toFormatter();
            }
            return v;
        }

        private static DateTimeFormatter aN() {
            if (w == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(aY()).append(aI()).toFormatter();
            }
            return w;
        }

        private static DateTimeFormatter aO() {
            if (x == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(aY()).append(aJ()).toFormatter();
            }
            return x;
        }

        private static DateTimeFormatter aP() {
            if (y == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(aY()).append(aK()).toFormatter();
            }
            return y;
        }

        private static DateTimeFormatter aQ() {
            if (z == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(aY()).append(aL()).toFormatter();
            }
            return z;
        }

        private static DateTimeFormatter aR() {
            if (a == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 9).toFormatter();
            }
            return a;
        }

        private static DateTimeFormatter aS() {
            if (b == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendMonthOfYear(2).toFormatter();
            }
            return b;
        }

        private static DateTimeFormatter aT() {
            if (c == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfMonth(2).toFormatter();
            }
            return c;
        }

        private static DateTimeFormatter aU() {
            if (d == null) {
                return new DateTimeFormatterBuilder().appendWeekyear(4, 9).toFormatter();
            }
            return d;
        }

        private static DateTimeFormatter aV() {
            if (e == null) {
                return new DateTimeFormatterBuilder().appendLiteral("-W").appendWeekOfWeekyear(2).toFormatter();
            }
            return e;
        }

        private static DateTimeFormatter aW() {
            if (f == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfWeek(1).toFormatter();
            }
            return f;
        }

        private static DateTimeFormatter aX() {
            if (g == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfYear(3).toFormatter();
            }
            return g;
        }

        private static DateTimeFormatter aY() {
            if (m == null) {
                return new DateTimeFormatterBuilder().appendLiteral('T').toFormatter();
            }
            return m;
        }

        private static DateTimeFormatter aZ() {
            if (h == null) {
                return new DateTimeFormatterBuilder().appendHourOfDay(2).toFormatter();
            }
            return h;
        }

        private static DateTimeFormatter ba() {
            if (i == null) {
                return new DateTimeFormatterBuilder().appendLiteral(':').appendMinuteOfHour(2).toFormatter();
            }
            return i;
        }

        private static DateTimeFormatter bb() {
            if (j == null) {
                return new DateTimeFormatterBuilder().appendLiteral(':').appendSecondOfMinute(2).toFormatter();
            }
            return j;
        }

        private static DateTimeFormatter bc() {
            if (k == null) {
                return new DateTimeFormatterBuilder().appendLiteral('.').appendFractionOfSecond(3, 9).toFormatter();
            }
            return k;
        }

        private static DateTimeFormatter bd() {
            if (l == null) {
                return new DateTimeFormatterBuilder().appendTimeZoneOffset("Z", true, 2, 4).toFormatter();
            }
            return l;
        }
    }

    protected ISODateTimeFormat() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b9 A[SYNTHETIC, Splitter:B:35:0x00b9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.joda.time.format.DateTimeFormatter forFields(java.util.Collection<org.joda.time.DateTimeFieldType> r8, boolean r9, boolean r10) {
        /*
            if (r8 == 0) goto L_0x00c1
            int r0 = r8.size()
            if (r0 != 0) goto L_0x000a
            goto L_0x00c1
        L_0x000a:
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>(r8)
            int r1 = r0.size()
            org.joda.time.format.DateTimeFormatterBuilder r7 = new org.joda.time.format.DateTimeFormatterBuilder
            r7.<init>()
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.monthOfYear()
            boolean r2 = r0.contains(r2)
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x002a
            boolean r2 = a(r7, r0, r9, r10)
        L_0x0028:
            r5 = r2
            goto L_0x008c
        L_0x002a:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.dayOfYear()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L_0x0039
            boolean r2 = b(r7, r0, r9, r10)
            goto L_0x0028
        L_0x0039:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.weekOfWeekyear()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L_0x0048
            boolean r2 = c(r7, r0, r9, r10)
            goto L_0x0028
        L_0x0048:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.dayOfMonth()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L_0x0057
            boolean r2 = a(r7, r0, r9, r10)
            goto L_0x0028
        L_0x0057:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.dayOfWeek()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L_0x0066
            boolean r2 = c(r7, r0, r9, r10)
            goto L_0x0028
        L_0x0066:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.year()
            boolean r2 = r0.remove(r2)
            if (r2 == 0) goto L_0x0079
            org.joda.time.format.DateTimeFormatter r2 = org.joda.time.format.ISODateTimeFormat.Constants.a
            r7.append(r2)
        L_0x0077:
            r5 = 1
            goto L_0x008c
        L_0x0079:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.weekyear()
            boolean r2 = r0.remove(r2)
            if (r2 == 0) goto L_0x008b
            org.joda.time.format.DateTimeFormatter r2 = org.joda.time.format.ISODateTimeFormat.Constants.d
            r7.append(r2)
            goto L_0x0077
        L_0x008b:
            r5 = 0
        L_0x008c:
            int r2 = r0.size()
            if (r2 >= r1) goto L_0x0094
            r6 = 1
            goto L_0x0095
        L_0x0094:
            r6 = 0
        L_0x0095:
            r1 = r7
            r2 = r0
            r3 = r9
            r4 = r10
            a(r1, r2, r3, r4, r5, r6)
            boolean r9 = r7.canBuildFormatter()
            if (r9 != 0) goto L_0x00b9
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "No valid format for fields: "
            r10.append(r0)
            r10.append(r8)
            java.lang.String r8 = r10.toString()
            r9.<init>(r8)
            throw r9
        L_0x00b9:
            r8.retainAll(r0)     // Catch:{ UnsupportedOperationException -> 0x00bc }
        L_0x00bc:
            org.joda.time.format.DateTimeFormatter r8 = r7.toFormatter()
            return r8
        L_0x00c1:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "The fields must not be null or empty"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.ISODateTimeFormat.forFields(java.util.Collection, boolean, boolean):org.joda.time.format.DateTimeFormatter");
    }

    private static boolean a(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z, boolean z2) {
        if (collection.remove(DateTimeFieldType.year())) {
            dateTimeFormatterBuilder.append(Constants.a);
            if (collection.remove(DateTimeFieldType.monthOfYear())) {
                if (collection.remove(DateTimeFieldType.dayOfMonth())) {
                    a(dateTimeFormatterBuilder, z);
                    dateTimeFormatterBuilder.appendMonthOfYear(2);
                    a(dateTimeFormatterBuilder, z);
                    dateTimeFormatterBuilder.appendDayOfMonth(2);
                } else {
                    dateTimeFormatterBuilder.appendLiteral('-');
                    dateTimeFormatterBuilder.appendMonthOfYear(2);
                    return true;
                }
            } else if (!collection.remove(DateTimeFieldType.dayOfMonth())) {
                return true;
            } else {
                a(collection, z2);
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendDayOfMonth(2);
            }
        } else if (collection.remove(DateTimeFieldType.monthOfYear())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendMonthOfYear(2);
            if (!collection.remove(DateTimeFieldType.dayOfMonth())) {
                return true;
            }
            a(dateTimeFormatterBuilder, z);
            dateTimeFormatterBuilder.appendDayOfMonth(2);
        } else if (collection.remove(DateTimeFieldType.dayOfMonth())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfMonth(2);
        }
        return false;
    }

    private static boolean b(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z, boolean z2) {
        if (collection.remove(DateTimeFieldType.year())) {
            dateTimeFormatterBuilder.append(Constants.a);
            if (!collection.remove(DateTimeFieldType.dayOfYear())) {
                return true;
            }
            a(dateTimeFormatterBuilder, z);
            dateTimeFormatterBuilder.appendDayOfYear(3);
        } else if (collection.remove(DateTimeFieldType.dayOfYear())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfYear(3);
        }
        return false;
    }

    private static boolean c(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z, boolean z2) {
        if (collection.remove(DateTimeFieldType.weekyear())) {
            dateTimeFormatterBuilder.append(Constants.d);
            if (collection.remove(DateTimeFieldType.weekOfWeekyear())) {
                a(dateTimeFormatterBuilder, z);
                dateTimeFormatterBuilder.appendLiteral('W');
                dateTimeFormatterBuilder.appendWeekOfWeekyear(2);
                if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                    return true;
                }
                a(dateTimeFormatterBuilder, z);
                dateTimeFormatterBuilder.appendDayOfWeek(1);
            } else if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                return true;
            } else {
                a(collection, z2);
                a(dateTimeFormatterBuilder, z);
                dateTimeFormatterBuilder.appendLiteral('W');
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendDayOfWeek(1);
            }
        } else if (collection.remove(DateTimeFieldType.weekOfWeekyear())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('W');
            dateTimeFormatterBuilder.appendWeekOfWeekyear(2);
            if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                return true;
            }
            a(dateTimeFormatterBuilder, z);
            dateTimeFormatterBuilder.appendDayOfWeek(1);
        } else if (collection.remove(DateTimeFieldType.dayOfWeek())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('W');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfWeek(1);
        }
        return false;
    }

    private static void a(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean remove = collection.remove(DateTimeFieldType.hourOfDay());
        boolean remove2 = collection.remove(DateTimeFieldType.minuteOfHour());
        boolean remove3 = collection.remove(DateTimeFieldType.secondOfMinute());
        boolean remove4 = collection.remove(DateTimeFieldType.millisOfSecond());
        if (remove || remove2 || remove3 || remove4) {
            if (remove || remove2 || remove3 || remove4) {
                if (z2 && z3) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("No valid ISO8601 format for fields because Date was reduced precision: ");
                    sb.append(collection);
                    throw new IllegalArgumentException(sb.toString());
                } else if (z4) {
                    dateTimeFormatterBuilder.appendLiteral('T');
                }
            }
            if ((!remove || !remove2 || !remove3) && (!remove || remove3 || remove4)) {
                if (z2 && z4) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("No valid ISO8601 format for fields because Time was truncated: ");
                    sb2.append(collection);
                    throw new IllegalArgumentException(sb2.toString());
                } else if ((remove || ((!remove2 || !remove3) && ((!remove2 || remove4) && !remove3))) && z2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("No valid ISO8601 format for fields: ");
                    sb3.append(collection);
                    throw new IllegalArgumentException(sb3.toString());
                }
            }
            if (remove) {
                dateTimeFormatterBuilder.appendHourOfDay(2);
            } else if (remove2 || remove3 || remove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (z && remove && remove2) {
                dateTimeFormatterBuilder.appendLiteral(':');
            }
            if (remove2) {
                dateTimeFormatterBuilder.appendMinuteOfHour(2);
            } else if (remove3 || remove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (z && remove2 && remove3) {
                dateTimeFormatterBuilder.appendLiteral(':');
            }
            if (remove3) {
                dateTimeFormatterBuilder.appendSecondOfMinute(2);
            } else if (remove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (remove4) {
                dateTimeFormatterBuilder.appendLiteral('.');
                dateTimeFormatterBuilder.appendMillisOfSecond(3);
            }
        }
    }

    private static void a(Collection<DateTimeFieldType> collection, boolean z) {
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append("No valid ISO8601 format for fields: ");
            sb.append(collection);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static void a(DateTimeFormatterBuilder dateTimeFormatterBuilder, boolean z) {
        if (z) {
            dateTimeFormatterBuilder.appendLiteral('-');
        }
    }

    public static DateTimeFormatter dateParser() {
        return Constants.aa;
    }

    public static DateTimeFormatter localDateParser() {
        return Constants.ab;
    }

    public static DateTimeFormatter dateElementParser() {
        return Constants.Y;
    }

    public static DateTimeFormatter timeParser() {
        return Constants.ac;
    }

    public static DateTimeFormatter localTimeParser() {
        return Constants.ad;
    }

    public static DateTimeFormatter timeElementParser() {
        return Constants.Z;
    }

    public static DateTimeFormatter dateTimeParser() {
        return Constants.ae;
    }

    public static DateTimeFormatter dateOptionalTimeParser() {
        return Constants.af;
    }

    public static DateTimeFormatter localDateOptionalTimeParser() {
        return Constants.ag;
    }

    public static DateTimeFormatter date() {
        return yearMonthDay();
    }

    public static DateTimeFormatter time() {
        return Constants.A;
    }

    public static DateTimeFormatter timeNoMillis() {
        return Constants.B;
    }

    public static DateTimeFormatter tTime() {
        return Constants.C;
    }

    public static DateTimeFormatter tTimeNoMillis() {
        return Constants.D;
    }

    public static DateTimeFormatter dateTime() {
        return Constants.E;
    }

    public static DateTimeFormatter dateTimeNoMillis() {
        return Constants.F;
    }

    public static DateTimeFormatter ordinalDate() {
        return Constants.I;
    }

    public static DateTimeFormatter ordinalDateTime() {
        return Constants.J;
    }

    public static DateTimeFormatter ordinalDateTimeNoMillis() {
        return Constants.K;
    }

    public static DateTimeFormatter weekDate() {
        return Constants.q;
    }

    public static DateTimeFormatter weekDateTime() {
        return Constants.G;
    }

    public static DateTimeFormatter weekDateTimeNoMillis() {
        return Constants.H;
    }

    public static DateTimeFormatter basicDate() {
        return Constants.L;
    }

    public static DateTimeFormatter basicTime() {
        return Constants.M;
    }

    public static DateTimeFormatter basicTimeNoMillis() {
        return Constants.N;
    }

    public static DateTimeFormatter basicTTime() {
        return Constants.O;
    }

    public static DateTimeFormatter basicTTimeNoMillis() {
        return Constants.P;
    }

    public static DateTimeFormatter basicDateTime() {
        return Constants.Q;
    }

    public static DateTimeFormatter basicDateTimeNoMillis() {
        return Constants.R;
    }

    public static DateTimeFormatter basicOrdinalDate() {
        return Constants.S;
    }

    public static DateTimeFormatter basicOrdinalDateTime() {
        return Constants.T;
    }

    public static DateTimeFormatter basicOrdinalDateTimeNoMillis() {
        return Constants.U;
    }

    public static DateTimeFormatter basicWeekDate() {
        return Constants.V;
    }

    public static DateTimeFormatter basicWeekDateTime() {
        return Constants.W;
    }

    public static DateTimeFormatter basicWeekDateTimeNoMillis() {
        return Constants.X;
    }

    public static DateTimeFormatter year() {
        return Constants.a;
    }

    public static DateTimeFormatter yearMonth() {
        return Constants.n;
    }

    public static DateTimeFormatter yearMonthDay() {
        return Constants.o;
    }

    public static DateTimeFormatter weekyear() {
        return Constants.d;
    }

    public static DateTimeFormatter weekyearWeek() {
        return Constants.p;
    }

    public static DateTimeFormatter weekyearWeekDay() {
        return Constants.q;
    }

    public static DateTimeFormatter hour() {
        return Constants.h;
    }

    public static DateTimeFormatter hourMinute() {
        return Constants.r;
    }

    public static DateTimeFormatter hourMinuteSecond() {
        return Constants.s;
    }

    public static DateTimeFormatter hourMinuteSecondMillis() {
        return Constants.t;
    }

    public static DateTimeFormatter hourMinuteSecondFraction() {
        return Constants.u;
    }

    public static DateTimeFormatter dateHour() {
        return Constants.v;
    }

    public static DateTimeFormatter dateHourMinute() {
        return Constants.w;
    }

    public static DateTimeFormatter dateHourMinuteSecond() {
        return Constants.x;
    }

    public static DateTimeFormatter dateHourMinuteSecondMillis() {
        return Constants.y;
    }

    public static DateTimeFormatter dateHourMinuteSecondFraction() {
        return Constants.z;
    }
}
