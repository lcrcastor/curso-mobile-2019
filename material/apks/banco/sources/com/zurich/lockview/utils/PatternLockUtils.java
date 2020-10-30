package com.zurich.lockview.utils;

import com.zurich.lockview.PatternLockView;
import com.zurich.lockview.PatternLockView.Dot;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PatternLockUtils {
    private PatternLockUtils() {
        throw new AssertionError("You can not instantiate this class. Use its static utility methods instead");
    }

    public static String patternToString(PatternLockView patternLockView, List<Dot> list) {
        if (list == null) {
            return "";
        }
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            Dot dot = (Dot) list.get(i);
            sb.append((dot.getRow() * patternLockView.getDotCount()) + dot.getColumn());
        }
        return sb.toString();
    }

    public static List<Dot> stringToPattern(PatternLockView patternLockView, String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < str.length(); i++) {
            int numericValue = Character.getNumericValue(str.charAt(i));
            arrayList.add(Dot.of(numericValue / patternLockView.getDotCount(), numericValue % patternLockView.getDotCount()));
        }
        return arrayList;
    }

    public static String patternToSha1(PatternLockView patternLockView, List<Dot> list) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
            instance.update(patternToString(patternLockView, list).getBytes("UTF-8"));
            byte[] digest = instance.digest();
            BigInteger bigInteger = new BigInteger(1, digest);
            Locale locale = null;
            StringBuilder sb = new StringBuilder();
            sb.append("%0");
            sb.append(digest.length * 2);
            sb.append("x");
            return String.format(locale, sb.toString(), new Object[]{bigInteger}).toLowerCase();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        } catch (UnsupportedEncodingException unused2) {
            return null;
        }
    }

    public static String patternToMD5(PatternLockView patternLockView, List<Dot> list) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.update(patternToString(patternLockView, list).getBytes("UTF-8"));
            byte[] digest = instance.digest();
            BigInteger bigInteger = new BigInteger(1, digest);
            Locale locale = null;
            StringBuilder sb = new StringBuilder();
            sb.append("%0");
            sb.append(digest.length * 2);
            sb.append("x");
            return String.format(locale, sb.toString(), new Object[]{bigInteger}).toLowerCase();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        } catch (UnsupportedEncodingException unused2) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00e9, code lost:
        r16 = r15;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<com.zurich.lockview.PatternLockView.Dot> generateRandomPattern(com.zurich.lockview.PatternLockView r21, int r22) {
        /*
            r1 = r22
            if (r21 != 0) goto L_0x000c
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "PatternLockView can not be null."
            r0.<init>(r1)
            throw r0
        L_0x000c:
            if (r1 <= 0) goto L_0x01b1
            int r2 = r21.getDotCount()
            if (r1 <= r2) goto L_0x0016
            goto L_0x01b1
        L_0x0016:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            int r3 = r21.getDotCount()
            int r3 = com.zurich.lockview.utils.RandomUtils.randInt(r3)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            r2.add(r4)
        L_0x002a:
            int r4 = r2.size()
            if (r4 >= r1) goto L_0x018f
            int r4 = r21.getDotCount()
            int r4 = r3 / r4
            int r5 = r21.getDotCount()
            int r3 = r3 % r5
            int r5 = r21.getDotCount()
            int r5 = r5 - r4
            int r5 = java.lang.Math.max(r4, r5)
            int r6 = r21.getDotCount()
            int r6 = r6 - r3
            int r6 = java.lang.Math.max(r3, r6)
            int r5 = java.lang.Math.max(r5, r6)
            r7 = 1
            r8 = -1
        L_0x0053:
            if (r7 > r5) goto L_0x0183
            int r9 = r4 - r7
            int r10 = r3 - r7
            int r11 = r4 + r7
            int r12 = r3 + r7
            r13 = 4
            int[] r13 = com.zurich.lockview.utils.RandomUtils.randIntArray(r13)
            int r14 = r13.length
            r15 = 0
            r16 = r8
            r8 = 0
        L_0x0067:
            if (r8 >= r14) goto L_0x0170
            r17 = r13[r8]
            switch(r17) {
                case 0: goto L_0x012b;
                case 1: goto L_0x00ed;
                case 2: goto L_0x00b2;
                case 3: goto L_0x006f;
                default: goto L_0x006e;
            }
        L_0x006e:
            goto L_0x00ac
        L_0x006f:
            if (r10 < 0) goto L_0x00ac
            int r6 = r9 + 1
            int r6 = java.lang.Math.max(r15, r6)
            int r15 = r21.getDotCount()
            int r15 = java.lang.Math.min(r15, r11)
            int[] r6 = com.zurich.lockview.utils.RandomUtils.randIntArray(r6, r15)
            int r15 = r6.length
            r1 = 0
        L_0x0085:
            if (r1 >= r15) goto L_0x00ac
            r16 = r6[r1]
            int r17 = r21.getDotCount()
            int r16 = r16 * r17
            r18 = r3
            int r3 = r16 + r10
            r19 = r4
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            boolean r4 = r2.contains(r4)
            if (r4 == 0) goto L_0x00a8
            int r1 = r1 + 1
            r3 = r18
            r4 = r19
            r16 = -1
            goto L_0x0085
        L_0x00a8:
            r16 = r3
            goto L_0x0162
        L_0x00ac:
            r18 = r3
            r19 = r4
            goto L_0x0162
        L_0x00b2:
            r18 = r3
            r19 = r4
            int r1 = r21.getDotCount()
            if (r11 >= r1) goto L_0x0162
            r1 = 0
            int r3 = java.lang.Math.max(r1, r10)
            int r1 = r21.getDotCount()
            int r1 = java.lang.Math.min(r1, r12)
            int[] r1 = com.zurich.lockview.utils.RandomUtils.randIntArray(r3, r1)
            int r3 = r1.length
            r4 = 0
        L_0x00cf:
            if (r4 >= r3) goto L_0x0162
            r6 = r1[r4]
            int r15 = r21.getDotCount()
            int r15 = r15 * r11
            int r15 = r15 + r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r15)
            boolean r6 = r2.contains(r6)
            if (r6 == 0) goto L_0x00e9
            int r4 = r4 + 1
            r16 = -1
            goto L_0x00cf
        L_0x00e9:
            r16 = r15
            goto L_0x0162
        L_0x00ed:
            r18 = r3
            r19 = r4
            int r1 = r21.getDotCount()
            if (r12 >= r1) goto L_0x0162
            int r1 = r9 + 1
            r3 = 0
            int r1 = java.lang.Math.max(r3, r1)
            int r3 = r21.getDotCount()
            int r4 = r11 + 1
            int r3 = java.lang.Math.min(r3, r4)
            int[] r1 = com.zurich.lockview.utils.RandomUtils.randIntArray(r1, r3)
            int r3 = r1.length
            r4 = 0
        L_0x010e:
            if (r4 >= r3) goto L_0x0162
            r6 = r1[r4]
            int r15 = r21.getDotCount()
            int r6 = r6 * r15
            int r6 = r6 + r12
            java.lang.Integer r15 = java.lang.Integer.valueOf(r6)
            boolean r15 = r2.contains(r15)
            if (r15 == 0) goto L_0x0128
            int r4 = r4 + 1
            r16 = -1
            goto L_0x010e
        L_0x0128:
            r16 = r6
            goto L_0x0162
        L_0x012b:
            r18 = r3
            r19 = r4
            if (r9 < 0) goto L_0x0162
            r1 = 0
            int r3 = java.lang.Math.max(r1, r10)
            int r4 = r21.getDotCount()
            int r6 = r12 + 1
            int r4 = java.lang.Math.min(r4, r6)
            int[] r3 = com.zurich.lockview.utils.RandomUtils.randIntArray(r3, r4)
            int r4 = r3.length
            r6 = 0
        L_0x0146:
            if (r6 >= r4) goto L_0x0162
            r15 = r3[r6]
            int r16 = r21.getDotCount()
            int r16 = r16 * r9
            int r15 = r16 + r15
            java.lang.Integer r1 = java.lang.Integer.valueOf(r15)
            boolean r1 = r2.contains(r1)
            if (r1 == 0) goto L_0x00e9
            int r6 = r6 + 1
            r1 = 0
            r16 = -1
            goto L_0x0146
        L_0x0162:
            if (r16 < 0) goto L_0x0165
            goto L_0x0174
        L_0x0165:
            int r8 = r8 + 1
            r3 = r18
            r4 = r19
            r1 = r22
            r15 = 0
            goto L_0x0067
        L_0x0170:
            r18 = r3
            r19 = r4
        L_0x0174:
            r8 = r16
            if (r8 < 0) goto L_0x0179
            goto L_0x0183
        L_0x0179:
            int r7 = r7 + 1
            r3 = r18
            r4 = r19
            r1 = r22
            goto L_0x0053
        L_0x0183:
            r3 = r8
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            r2.add(r1)
            r1 = r22
            goto L_0x002a
        L_0x018f:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Iterator r1 = r2.iterator()
        L_0x0198:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x01b0
            java.lang.Object r2 = r1.next()
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            com.zurich.lockview.PatternLockView$Dot r2 = com.zurich.lockview.PatternLockView.Dot.of(r2)
            r0.add(r2)
            goto L_0x0198
        L_0x01b0:
            return r0
        L_0x01b1:
            java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Size must be in range [1, "
            r2.append(r3)
            int r0 = r21.getDotCount()
            r2.append(r0)
            java.lang.String r0 = "]"
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zurich.lockview.utils.PatternLockUtils.generateRandomPattern(com.zurich.lockview.PatternLockView, int):java.util.ArrayList");
    }
}
