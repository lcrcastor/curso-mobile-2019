package org.joda.time.chrono;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.IllegalFieldValueException;

class GJLocaleSymbols {
    private static ConcurrentMap<Locale, GJLocaleSymbols> a = new ConcurrentHashMap();
    private final String[] b;
    private final String[] c;
    private final String[] d;
    private final String[] e;
    private final String[] f;
    private final String[] g;
    private final TreeMap<String, Integer> h;
    private final TreeMap<String, Integer> i;
    private final TreeMap<String, Integer> j;
    private final int k;
    private final int l;
    private final int m;
    private final int n;
    private final int o;
    private final int p;

    static GJLocaleSymbols a(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        GJLocaleSymbols gJLocaleSymbols = (GJLocaleSymbols) a.get(locale);
        if (gJLocaleSymbols != null) {
            return gJLocaleSymbols;
        }
        GJLocaleSymbols gJLocaleSymbols2 = new GJLocaleSymbols(locale);
        GJLocaleSymbols gJLocaleSymbols3 = (GJLocaleSymbols) a.putIfAbsent(locale, gJLocaleSymbols2);
        return gJLocaleSymbols3 != null ? gJLocaleSymbols3 : gJLocaleSymbols2;
    }

    private static String[] a(String[] strArr) {
        String[] strArr2 = new String[13];
        for (int i2 = 1; i2 < 13; i2++) {
            strArr2[i2] = strArr[i2 - 1];
        }
        return strArr2;
    }

    private static String[] b(String[] strArr) {
        String[] strArr2 = new String[8];
        int i2 = 1;
        while (i2 < 8) {
            strArr2[i2] = strArr[i2 < 7 ? i2 + 1 : 1];
            i2++;
        }
        return strArr2;
    }

    private static void a(TreeMap<String, Integer> treeMap, String[] strArr, Integer[] numArr) {
        int length = strArr.length;
        while (true) {
            length--;
            if (length >= 0) {
                String str = strArr[length];
                if (str != null) {
                    treeMap.put(str, numArr[length]);
                }
            } else {
                return;
            }
        }
    }

    private static void a(TreeMap<String, Integer> treeMap, int i2, int i3, Integer[] numArr) {
        while (i2 <= i3) {
            treeMap.put(String.valueOf(i2).intern(), numArr[i2]);
            i2++;
        }
    }

    private static int c(String[] strArr) {
        int length = strArr.length;
        int i2 = 0;
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            String str = strArr[length];
            if (str != null) {
                int length2 = str.length();
                if (length2 > i2) {
                    i2 = length2;
                }
            }
        }
    }

    private GJLocaleSymbols(Locale locale) {
        DateFormatSymbols dateFormatSymbols = DateTimeUtils.getDateFormatSymbols(locale);
        this.b = dateFormatSymbols.getEras();
        this.c = b(dateFormatSymbols.getWeekdays());
        this.d = b(dateFormatSymbols.getShortWeekdays());
        this.e = a(dateFormatSymbols.getMonths());
        this.f = a(dateFormatSymbols.getShortMonths());
        this.g = dateFormatSymbols.getAmPmStrings();
        Integer[] numArr = new Integer[13];
        for (int i2 = 0; i2 < 13; i2++) {
            numArr[i2] = Integer.valueOf(i2);
        }
        this.h = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        a(this.h, this.b, numArr);
        if ("en".equals(locale.getLanguage())) {
            this.h.put("BCE", numArr[0]);
            this.h.put("CE", numArr[1]);
        }
        this.i = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        a(this.i, this.c, numArr);
        a(this.i, this.d, numArr);
        a(this.i, 1, 7, numArr);
        this.j = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        a(this.j, this.e, numArr);
        a(this.j, this.f, numArr);
        a(this.j, 1, 12, numArr);
        this.k = c(this.b);
        this.l = c(this.c);
        this.m = c(this.d);
        this.n = c(this.e);
        this.o = c(this.f);
        this.p = c(this.g);
    }

    public String a(int i2) {
        return this.b[i2];
    }

    public int a(String str) {
        Integer num = (Integer) this.h.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.era(), str);
    }

    public int a() {
        return this.k;
    }

    public String b(int i2) {
        return this.e[i2];
    }

    public String c(int i2) {
        return this.f[i2];
    }

    public int b(String str) {
        Integer num = (Integer) this.j.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.monthOfYear(), str);
    }

    public int b() {
        return this.n;
    }

    public int c() {
        return this.o;
    }

    public String d(int i2) {
        return this.c[i2];
    }

    public String e(int i2) {
        return this.d[i2];
    }

    public int c(String str) {
        Integer num = (Integer) this.i.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.dayOfWeek(), str);
    }

    public int d() {
        return this.l;
    }

    public int e() {
        return this.m;
    }

    public String f(int i2) {
        return this.g[i2];
    }

    public int d(String str) {
        String[] strArr = this.g;
        int length = strArr.length;
        do {
            length--;
            if (length < 0) {
                throw new IllegalFieldValueException(DateTimeFieldType.halfdayOfDay(), str);
            }
        } while (!strArr[length].equalsIgnoreCase(str));
        return length;
    }

    public int f() {
        return this.p;
    }
}
