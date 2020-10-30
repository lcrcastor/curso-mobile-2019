package com.appsflyer;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;
import cz.msebera.android.httpclient.message.TokenParser;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;

final class a {
    private static char[] a = {'a', 50912, 36216, 21464, 6743, 57519, 42800, 28108, 13316, 64155, 49632, 34943, 20184, 5470, 56235, 41532, 26777, 12096, 62888, 48367, 33652, 18883, 4164, 54954, 40255, 25520, 10773, 61623, 47081, 32376, 17605, 2901, 53669, 38972, '1', 55369, 'y', 50935, 36197, 21459, 6677, 57483, 42777, 28111, 13332, 64154, 49619, 34898, 20192, 5467, 56233, 41505, 26771, 12084, 26621, 41328, 60129, 13392, 32198, 34620, 49403, 2632, 21462, 40197, 42534, 61366, 10582, 29387, 48226, 50686, 3915, 18583, 37463, 56176, 58547, 11868, 30623, 45421, 64171, 1144, 19906, 38659, 53285, 6585, 8970, 27804, 15838, 64351, 45255, 28263, 10216, 56592, 39567, 20595, 2464, 50994, 64541, 46567, 29558, 10493, 58895, 40840, 21805, 4776, 51214, 33108, 48841, 29816, 11756, 60184, 41117, 8855, 58390, 44942, 28974, 14497, 49753, 34246, 20282, 5870, 55401, 58120, 43656, 27689, 14241, 63808, 32961, 19000, 3531, 55151, 40466, 41373, 27455, 12976, 'a', 50912, 36216, 21464, 6743, 57519, 42800, 28108, 13343, 64141, 49570, 34907, 20187, 5455, 56234, 41521, 26804, 12047, 62863, 48353, 'a', 50912, 36216, 21464, 6743, 57519, 42800, 28108, 13342, 64155, 49656, 34868, 20221, 5444, 56237, 204, 45159, 30363, 'j', 50927, 36202, 21451, 6678, 57519, 42811, 28108, 13316, 64147, 49660, 34942, 20161, 5444, '/', 50925, 36221, 21449, 6736, 57507, 14891, 64673, 46902, 27123, 8270, 56041, 40313, 22473, 3679, 49362, 16985, 34044, 53091, 4563, 22601, 41647, 58683, 12181, 30255, 47260, 33781, 51813, 3266, 22360, 39351, 57383, 10900, 'F', 50927, 36213, 21446, 6749, 57506, 42868, 28054, 13343, 64222, 49643, 34943, 20188, 5398, 56231, 41523, 26755, 12038, 62873, 48298, 33640, 18887, 4160, 54954, 40304, 25513, 10757, 61582, 47072, 32310, 17601, 2890, 53667, 38955, 24236, 9502, 60305, 45801, 31098, 16280, 1552, 31470, 48226, 63457, 10602, 24799, 39484, 56745, 5898, 20117, 32795, 47968, 62199, 13408, 28623, 41277, 55479, 4608, 21892, 36628, 50807, 63992, 13120, 27347, 44088, 'C', 50918, 36217, 21449, 6739, 57525, 42785, 28047, 13346, 64155, 49642, 34934, 20173, 5461, 56240, 41495, 26776, 12045, 62873, 48378, 33644, 18895, 4187, 54956, 'F', 50927, 36213, 21446, 6749, 57506, 42868, 28043, 13342, 64136, 49635, 34929, 20173, 5398, 56246, 41527, 26758, 12034, 62873, 48361, 33644, 18883, 4176, 55010, 40253, 25531, 10776, 61586, 47079, 32370, 17540, 2885, 53673, 38970, 24244, 9546, 60317, 45822, 31095, 16327, 1600, 52426, 37669, 22965, 8198, 59084, 44452};
    private static long b = -9114525830008617330L;
    private static int c = 0;
    private static int d = 1;

    a() {
    }

    @Nullable
    static String a(Context context, long j) {
        String str;
        String str2;
        String str3;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        if (a(a(0, 34, 0).intern())) {
            int i = c + 89;
            d = i % 128;
            int i2 = i % 2;
            str = a(34, 1, 0);
        } else {
            str = a(35, 1, 55417);
        }
        sb2.append(str.intern());
        StringBuilder sb4 = new StringBuilder();
        String packageName = context.getPackageName();
        String b2 = b(packageName);
        sb2.append(a(34, 1, 0).intern());
        sb4.append(b2);
        if (a(context) == null) {
            sb2.append(a(35, 1, 55417).intern());
            sb4.append(packageName);
        } else {
            sb2.append(a(34, 1, 0).intern());
            sb4.append(packageName);
        }
        String b3 = b(context);
        if (b3 == null) {
            sb2.append(a(35, 1, 55417).intern());
            sb4.append(packageName);
        } else {
            sb2.append(a(34, 1, 0).intern());
            sb4.append(b3);
        }
        sb4.append(a(context, packageName));
        sb.append(sb4.toString());
        try {
            long j2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(a(36, 18, 0).intern(), Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            sb.append(simpleDateFormat.format(new Date(j2)));
            int i3 = c + 45;
            d = i3 % 128;
            int i4 = i3 % 2;
            sb.append(j);
            if (a(a(86, 25, 15807).intern())) {
                str2 = a(34, 1, 0).intern();
            } else {
                str2 = a(35, 1, 55417).intern();
                int i5 = d + 115;
                c = i5 % 128;
                int i6 = i5 % 2;
            }
            sb3.append(str2);
            sb3.append((a(a(111, 23, 8950).intern()) ? a(34, 1, 0) : a(35, 1, 55417)).intern());
            if (a(a(CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA, 20, 0).intern())) {
                int i7 = d + 9;
                c = i7 % 128;
                int i8 = i7 % 2;
                str3 = a(34, 1, 0).intern();
            } else {
                str3 = a(35, 1, 55417).intern();
                int i9 = c + 73;
                d = i9 % 128;
                int i10 = i9 % 2;
            }
            sb3.append(str3);
            sb3.append(((a(a(CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 15, 0).intern()) ? 24 : Matrix.MATRIX_TYPE_ZERO) != 'Z' ? a(34, 1, 0) : a(35, 1, 55417)).intern());
            String b4 = C0096r.b(C0096r.c(sb.toString()));
            String obj = sb2.toString();
            StringBuilder sb5 = new StringBuilder(b4);
            sb5.setCharAt(17, Integer.toString(Integer.parseInt(obj, 2), 16).charAt(0));
            String obj2 = sb5.toString();
            String obj3 = sb3.toString();
            StringBuilder sb6 = new StringBuilder(obj2);
            sb6.setCharAt(27, Integer.toString(Integer.parseInt(obj3, 2), 16).charAt(0));
            return a(sb6.toString(), Long.valueOf(j));
        } catch (NameNotFoundException unused) {
            return a(54, 32, 26527).intern();
        }
    }

    private static String a(String str, Long l) {
        int i = c + 29;
        d = i % 128;
        int i2 = i % 2;
        if ((str != null ? '!' : 'I') == '!' && l != null) {
            int i3 = c + 67;
            d = i3 % 128;
            if (i3 % 2 != 0 ? str.length() == 32 : str.length() == 115) {
                StringBuilder sb = new StringBuilder(str);
                String obj = l.toString();
                long j = 0;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                while (true) {
                    if ((i5 < obj.length() ? 'G' : TokenParser.ESCAPE) != 'G') {
                        break;
                    }
                    i6 += Character.getNumericValue(obj.charAt(i5));
                    i5++;
                }
                String hexString = Integer.toHexString(i6);
                sb.replace(7, hexString.length() + 7, hexString);
                while (i4 < sb.length()) {
                    long numericValue = j + ((long) Character.getNumericValue(sb.charAt(i4)));
                    i4++;
                    int i7 = d + 111;
                    c = i7 % 128;
                    int i8 = i7 % 2;
                    j = numericValue;
                }
                while (j > 100) {
                    j %= 100;
                }
                sb.insert(23, (int) j);
                if (j < 10) {
                    sb.insert(23, a(35, 1, 55417).intern());
                }
                return sb.toString();
            }
        }
        return a(54, 32, 26527).intern();
    }

    private static boolean a(String str) {
        int i = c + 79;
        d = i % 128;
        if ((i % 2 == 0 ? 'G' : 9) != 'G') {
            try {
                Class.forName(str);
                return true;
            } catch (ClassNotFoundException unused) {
                return false;
            }
        } else {
            Class.forName(str);
            return false;
        }
    }

    private static String b(String str) {
        if ((!str.contains(a(CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 1, 226).intern()) ? (char) 26 : 14) != 14) {
            int i = d + 25;
            c = i % 128;
            int i2 = i % 2;
            return str;
        }
        String[] split = str.split(a(CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 2, 45115).intern());
        int length = split.length;
        StringBuilder sb = new StringBuilder();
        int i3 = length - 1;
        sb.append(split[i3]);
        sb.append(a(CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 1, 226).intern());
        int i4 = 1;
        while (i4 < i3) {
            int i5 = c + 115;
            d = i5 % 128;
            if ((i5 % 2 == 0 ? '6' : 'G') != 'G') {
                sb.append(split[i4]);
                sb.append(a(19127, 1, 226).intern());
                i4 += 98;
            } else {
                sb.append(split[i4]);
                sb.append(a(CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 1, 226).intern());
                i4++;
            }
        }
        sb.append(split[0]);
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0022, code lost:
        if (java.lang.System.getProperties().containsKey(a(79, org.bouncycastle.asn1.eac.EACTags.SECURITY_SUPPORT_TEMPLATE, 0).intern()) != false) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0042, code lost:
        if ((java.lang.System.getProperties().containsKey(a(org.bouncycastle.crypto.tls.CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, 14, 0).intern()) ? 'C' : 'Y') != 'Y') goto L_0x0044;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(android.content.Context r7) {
        /*
            int r0 = c
            int r0 = r0 + 113
            int r1 = r0 % 128
            d = r1
            int r0 = r0 % 2
            r1 = 0
            r2 = 0
            if (r0 != 0) goto L_0x0025
            java.util.Properties r0 = java.lang.System.getProperties()
            r3 = 79
            r4 = 122(0x7a, float:1.71E-43)
            java.lang.String r3 = a(r3, r4, r2)
            java.lang.String r3 = r3.intern()
            boolean r0 = r0.containsKey(r3)
            if (r0 == 0) goto L_0x00c8
            goto L_0x0044
        L_0x0025:
            java.util.Properties r0 = java.lang.System.getProperties()
            r3 = 172(0xac, float:2.41E-43)
            r4 = 14
            java.lang.String r3 = a(r3, r4, r2)
            java.lang.String r3 = r3.intern()
            boolean r0 = r0.containsKey(r3)
            r3 = 89
            if (r0 == 0) goto L_0x0040
            r0 = 67
            goto L_0x0042
        L_0x0040:
            r0 = 89
        L_0x0042:
            if (r0 == r3) goto L_0x00c8
        L_0x0044:
            java.io.File r7 = r7.getCacheDir()     // Catch:{ Exception -> 0x0096 }
            java.lang.String r7 = r7.getPath()     // Catch:{ Exception -> 0x0096 }
            r0 = 186(0xba, float:2.6E-43)
            r3 = 6
            java.lang.String r0 = a(r0, r3, r2)     // Catch:{ Exception -> 0x0096 }
            java.lang.String r0 = r0.intern()     // Catch:{ Exception -> 0x0096 }
            java.lang.String r3 = ""
            java.lang.String r7 = r7.replace(r0, r3)     // Catch:{ Exception -> 0x0096 }
            r0 = 192(0xc0, float:2.69E-43)
            r3 = 10
            r4 = 14853(0x3a05, float:2.0813E-41)
            java.lang.String r0 = a(r0, r3, r4)     // Catch:{ Exception -> 0x0096 }
            java.lang.String r0 = r0.intern()     // Catch:{ Exception -> 0x0096 }
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0096 }
            java.util.regex.Matcher r7 = r0.matcher(r7)     // Catch:{ Exception -> 0x0096 }
            boolean r0 = r7.find()     // Catch:{ Exception -> 0x0096 }
            if (r0 == 0) goto L_0x00c8
            int r0 = d
            int r0 = r0 + 23
            int r3 = r0 % 128
            c = r3
            int r0 = r0 % 2
            r3 = 1
            if (r0 == 0) goto L_0x0088
            r0 = 0
            goto L_0x0089
        L_0x0088:
            r0 = 1
        L_0x0089:
            if (r0 == r3) goto L_0x0091
            java.lang.String r7 = r7.group(r2)     // Catch:{ Exception -> 0x0096 }
        L_0x008f:
            r1 = r7
            goto L_0x00c8
        L_0x0091:
            java.lang.String r7 = r7.group(r3)     // Catch:{ Exception -> 0x0096 }
            goto L_0x008f
        L_0x0096:
            r7 = move-exception
            com.appsflyer.y r0 = com.appsflyer.y.a()
            r3 = 202(0xca, float:2.83E-43)
            r4 = 17
            r5 = 16922(0x421a, float:2.3713E-41)
            java.lang.String r3 = a(r3, r4, r5)
            java.lang.String r3 = r3.intern()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r5 = 219(0xdb, float:3.07E-43)
            r6 = 41
            java.lang.String r2 = a(r5, r6, r2)
            java.lang.String r2 = r2.intern()
            r4.append(r2)
            r4.append(r7)
            java.lang.String r7 = r4.toString()
            r0.b(r3, r7)
            return r1
        L_0x00c8:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.a.a(android.content.Context):java.lang.String");
    }

    private static String b(Context context) {
        int i = d + EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY;
        c = i % 128;
        int i2 = i % 2;
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
            int i3 = c + 119;
            d = i3 % 128;
            if (i3 % 2 == 0) {
            }
            return str;
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    private static String a(Context context, String str) {
        char c2;
        String str2;
        int i = c + 113;
        d = i % 128;
        int i2 = i % 2;
        try {
            Iterator it = ((List) PackageManager.class.getDeclaredMethod(a(260, 24, 31369).intern(), new Class[]{Integer.TYPE}).invoke(context.getPackageManager(), new Object[]{Integer.valueOf(0)})).iterator();
            do {
                if (!it.hasNext()) {
                    return Boolean.FALSE.toString();
                }
                if (((ApplicationInfo) it.next()).packageName.equals(str)) {
                    c2 = 15;
                    continue;
                } else {
                    c2 = ']';
                    continue;
                }
            } while (c2 != 15);
            int i3 = d + EACTags.SECURE_MESSAGING_TEMPLATE;
            c = i3 % 128;
            if (i3 % 2 != 0) {
                str2 = Boolean.TRUE.toString();
                int i4 = 74 / 0;
            } else {
                str2 = Boolean.TRUE.toString();
            }
            return str2;
        } catch (IllegalAccessException e) {
            y a2 = y.a();
            String intern = a(284, 24, 0).intern();
            StringBuilder sb = new StringBuilder();
            sb.append(a(308, 47, 0).intern());
            sb.append(e);
            a2.b(intern, sb.toString());
        } catch (NoSuchMethodException e2) {
            y a3 = y.a();
            String intern2 = a(284, 24, 0).intern();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a(308, 47, 0).intern());
            sb2.append(e2);
            a3.b(intern2, sb2.toString());
        } catch (InvocationTargetException e3) {
            y a4 = y.a();
            String intern3 = a(284, 24, 0).intern();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a(308, 47, 0).intern());
            sb3.append(e3);
            a4.b(intern3, sb3.toString());
        }
    }

    private static String a(int i, int i2, char c2) {
        int i3 = d + 57;
        c = i3 % 128;
        int i4 = i3 % 2;
        char[] cArr = new char[i2];
        int i5 = 0;
        while (true) {
            if ((i5 < i2 ? 3 : '1') != 3) {
                return new String(cArr);
            }
            int i6 = d + 95;
            c = i6 % 128;
            int i7 = i6 % 2;
            cArr[i5] = (char) ((int) ((((long) a[i + i5]) ^ (((long) i5) * b)) ^ ((long) c2)));
            i5++;
        }
    }
}
