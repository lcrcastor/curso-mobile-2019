package org.bouncycastle.jce.provider;

import com.google.common.primitives.UnsignedBytes;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralSubtree;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Strings;

public class PKIXNameConstraintValidator {
    private Set a = new HashSet();
    private Set b = new HashSet();
    private Set c = new HashSet();
    private Set d = new HashSet();
    private Set e = new HashSet();
    private Set f;
    private Set g;
    private Set h;
    private Set i;
    private Set j;

    private int a(Collection collection) {
        int i2 = 0;
        if (collection == null) {
            return 0;
        }
        for (Object next : collection) {
            i2 += next instanceof byte[] ? Arrays.hashCode((byte[]) next) : next.hashCode();
        }
        return i2;
    }

    private static String a(String str) {
        String substring = str.substring(str.indexOf(58) + 1);
        if (substring.indexOf("//") != -1) {
            substring = substring.substring(substring.indexOf("//") + 2);
        }
        if (substring.lastIndexOf(58) != -1) {
            substring = substring.substring(0, substring.lastIndexOf(58));
        }
        String substring2 = substring.substring(substring.indexOf(58) + 1);
        String substring3 = substring2.substring(substring2.indexOf(64) + 1);
        return substring3.indexOf(47) != -1 ? substring3.substring(0, substring3.indexOf(47)) : substring3;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<byte[]>, for r4v0, types: [java.util.Set, java.util.Set<byte[]>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(java.util.Set<byte[]> r4) {
        /*
            r3 = this;
            java.lang.String r0 = ""
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "["
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.util.Iterator r4 = r4.iterator()
        L_0x0017:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x003c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.Object r0 = r4.next()
            byte[] r0 = (byte[]) r0
            java.lang.String r0 = r3.a(r0)
            r1.append(r0)
            java.lang.String r0 = ","
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x0017
        L_0x003c:
            int r4 = r0.length()
            r1 = 1
            if (r4 <= r1) goto L_0x004d
            r4 = 0
            int r2 = r0.length()
            int r2 = r2 - r1
            java.lang.String r0 = r0.substring(r4, r2)
        L_0x004d:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r0)
            java.lang.String r0 = "]"
            r4.append(r0)
            java.lang.String r4 = r4.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.a(java.util.Set):java.lang.String");
    }

    private String a(GeneralName generalName) {
        return DERIA5String.getInstance(generalName.getName()).getString();
    }

    private String a(byte[] bArr) {
        String str = "";
        for (int i2 = 0; i2 < bArr.length / 2; i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(Integer.toString(bArr[i2] & UnsignedBytes.MAX_VALUE));
            sb.append(".");
            str = sb.toString();
        }
        String substring = str.substring(0, str.length() - 1);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(substring);
        sb2.append("/");
        String sb3 = sb2.toString();
        for (int length = bArr.length / 2; length < bArr.length; length++) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb3);
            sb4.append(Integer.toString(bArr[length] & UnsignedBytes.MAX_VALUE));
            sb4.append(".");
            sb3 = sb4.toString();
        }
        return sb3.substring(0, sb3.length() - 1);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r3v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set a(java.util.Set<java.lang.String> r3, java.lang.String r4) {
        /*
            r2 = this;
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x000d
            if (r4 != 0) goto L_0x0009
            return r3
        L_0x0009:
            r3.add(r4)
            return r3
        L_0x000d:
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r3 = r3.iterator()
        L_0x0016:
            boolean r1 = r3.hasNext()
            if (r1 == 0) goto L_0x0026
            java.lang.Object r1 = r3.next()
            java.lang.String r1 = (java.lang.String) r1
            r2.a(r1, r4, r0)
            goto L_0x0016
        L_0x0026:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.a(java.util.Set, java.lang.String):java.util.Set");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.ASN1Sequence>, for r6v0, types: [java.util.Set<org.bouncycastle.asn1.ASN1Sequence>, java.util.Set] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, for r7v0, types: [java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set a(java.util.Set<org.bouncycastle.asn1.ASN1Sequence> r6, java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree> r7) {
        /*
            r5 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r7 = r7.iterator()
        L_0x0009:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto L_0x0051
            java.lang.Object r1 = r7.next()
            org.bouncycastle.asn1.x509.GeneralSubtree r1 = (org.bouncycastle.asn1.x509.GeneralSubtree) r1
            org.bouncycastle.asn1.x509.GeneralName r1 = r1.getBase()
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getName()
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            org.bouncycastle.asn1.ASN1Sequence r1 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r1)
            if (r6 != 0) goto L_0x002d
            if (r1 == 0) goto L_0x0009
            r0.add(r1)
            goto L_0x0009
        L_0x002d:
            java.util.Iterator r2 = r6.iterator()
        L_0x0031:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0009
            java.lang.Object r3 = r2.next()
            org.bouncycastle.asn1.ASN1Sequence r3 = (org.bouncycastle.asn1.ASN1Sequence) r3
            boolean r4 = a(r1, r3)
            if (r4 == 0) goto L_0x0047
            r0.add(r1)
            goto L_0x0031
        L_0x0047:
            boolean r4 = a(r3, r1)
            if (r4 == 0) goto L_0x0031
            r0.add(r3)
            goto L_0x0031
        L_0x0051:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.a(java.util.Set, java.util.Set):java.util.Set");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<byte[]>, for r3v0, types: [java.util.Set, java.util.Set<byte[]>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set a(java.util.Set<byte[]> r3, byte[] r4) {
        /*
            r2 = this;
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x000d
            if (r4 != 0) goto L_0x0009
            return r3
        L_0x0009:
            r3.add(r4)
            return r3
        L_0x000d:
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r3 = r3.iterator()
        L_0x0016:
            boolean r1 = r3.hasNext()
            if (r1 == 0) goto L_0x002a
            java.lang.Object r1 = r3.next()
            byte[] r1 = (byte[]) r1
            java.util.Set r1 = r2.a(r1, r4)
            r0.addAll(r1)
            goto L_0x0016
        L_0x002a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.a(java.util.Set, byte[]):java.util.Set");
    }

    private Set a(byte[] bArr, byte[] bArr2) {
        HashSet hashSet = new HashSet();
        if (Arrays.areEqual(bArr, bArr2)) {
            hashSet.add(bArr);
            return hashSet;
        }
        hashSet.add(bArr);
        hashSet.add(bArr2);
        return hashSet;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
        if (b(r5.substring(r4.indexOf(64) + 1), r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006e, code lost:
        if (b(r5, r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0079, code lost:
        if (b(r5, r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007b, code lost:
        r6.add(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0097, code lost:
        if (r5.substring(r4.indexOf(64) + 1).equalsIgnoreCase(r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a6, code lost:
        if (b(r4, r5) != false) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ad, code lost:
        if (r4.equalsIgnoreCase(r5) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001d, code lost:
        if (r4.equalsIgnoreCase(r5) != false) goto L_0x007b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r4, java.lang.String r5, java.util.Set r6) {
        /*
            r3 = this;
            r0 = 64
            int r1 = r4.indexOf(r0)
            r2 = -1
            if (r1 == r2) goto L_0x0036
            int r1 = r4.indexOf(r0)
            int r1 = r1 + 1
            java.lang.String r1 = r4.substring(r1)
            int r0 = r5.indexOf(r0)
            if (r0 == r2) goto L_0x0020
            boolean r0 = r4.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
        L_0x0020:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x002f
            boolean r0 = r3.b(r1, r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x0071
        L_0x002f:
            boolean r0 = r1.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x0071
        L_0x0036:
            java.lang.String r1 = "."
            boolean r1 = r4.startsWith(r1)
            if (r1 == 0) goto L_0x0083
            int r1 = r5.indexOf(r0)
            if (r1 == r2) goto L_0x0055
            int r0 = r4.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r5.substring(r0)
            boolean r0 = r3.b(r0, r4)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
        L_0x0055:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x0075
            boolean r0 = r3.b(r4, r5)
            if (r0 != 0) goto L_0x0071
            boolean r0 = r4.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x006a
            goto L_0x0071
        L_0x006a:
            boolean r0 = r3.b(r5, r4)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
        L_0x0071:
            r6.add(r5)
            return
        L_0x0075:
            boolean r0 = r3.b(r5, r4)
            if (r0 == 0) goto L_0x007f
        L_0x007b:
            r6.add(r4)
            return
        L_0x007f:
            r6.add(r4)
            goto L_0x0071
        L_0x0083:
            int r1 = r5.indexOf(r0)
            if (r1 == r2) goto L_0x009a
            int r0 = r4.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r5.substring(r0)
            boolean r0 = r0.equalsIgnoreCase(r4)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
        L_0x009a:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x00a9
            boolean r0 = r3.b(r4, r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x0071
        L_0x00a9:
            boolean r0 = r4.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.a(java.lang.String, java.lang.String, java.util.Set):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.ASN1Sequence>, for r2v0, types: [java.util.Set<org.bouncycastle.asn1.ASN1Sequence>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.util.Set<org.bouncycastle.asn1.ASN1Sequence> r2, org.bouncycastle.asn1.ASN1Sequence r3) {
        /*
            r1 = this;
            if (r2 != 0) goto L_0x0003
            return
        L_0x0003:
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L_0x0010
            int r0 = r3.size()
            if (r0 != 0) goto L_0x0010
            return
        L_0x0010:
            java.util.Iterator r2 = r2.iterator()
        L_0x0014:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0027
            java.lang.Object r0 = r2.next()
            org.bouncycastle.asn1.ASN1Sequence r0 = (org.bouncycastle.asn1.ASN1Sequence) r0
            boolean r0 = a(r3, r0)
            if (r0 == 0) goto L_0x0014
            return
        L_0x0027:
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r2 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r3 = "Subject distinguished name is not from a permitted subtree"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.a(java.util.Set, org.bouncycastle.asn1.ASN1Sequence):void");
    }

    private boolean a(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return (!(obj instanceof byte[]) || !(obj2 instanceof byte[])) ? obj.equals(obj2) : Arrays.areEqual((byte[]) obj, (byte[]) obj2);
    }

    private boolean a(String str, String str2) {
        String substring = str.substring(str.indexOf(64) + 1);
        if (str2.indexOf(64) != -1) {
            if (str.equalsIgnoreCase(str2)) {
                return true;
            }
        } else if (str2.charAt(0) != '.') {
            if (substring.equalsIgnoreCase(str2)) {
                return true;
            }
        } else if (b(substring, str2)) {
            return true;
        }
        return false;
    }

    private boolean a(Collection collection, Collection collection2) {
        boolean z;
        if (collection == collection2) {
            return true;
        }
        if (collection == null || collection2 == null || collection.size() != collection2.size()) {
            return false;
        }
        for (Object next : collection) {
            Iterator it = collection2.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (a(next, it.next())) {
                        z = true;
                        continue;
                        break;
                    }
                } else {
                    z = false;
                    continue;
                    break;
                }
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(ASN1Sequence aSN1Sequence, ASN1Sequence aSN1Sequence2) {
        if (aSN1Sequence2.size() < 1 || aSN1Sequence2.size() > aSN1Sequence.size()) {
            return false;
        }
        for (int size = aSN1Sequence2.size() - 1; size >= 0; size--) {
            if (!aSN1Sequence2.getObjectAt(size).equals(aSN1Sequence.getObjectAt(size))) {
                return false;
            }
        }
        return true;
    }

    private byte[][] a(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        int length = bArr.length;
        byte[] bArr5 = new byte[length];
        byte[] bArr6 = new byte[length];
        byte[] bArr7 = new byte[length];
        byte[] bArr8 = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            bArr5[i2] = (byte) (bArr[i2] & bArr2[i2]);
            bArr6[i2] = (byte) ((bArr[i2] & bArr2[i2]) | (bArr2[i2] ^ -1));
            bArr7[i2] = (byte) (bArr3[i2] & bArr4[i2]);
            bArr8[i2] = (byte) ((bArr3[i2] & bArr4[i2]) | (bArr4[i2] ^ -1));
        }
        return new byte[][]{bArr5, bArr6, bArr7, bArr8};
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r5v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, for r6v0, types: [java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set b(java.util.Set<java.lang.String> r5, java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree> r6) {
        /*
            r4 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r6 = r6.iterator()
        L_0x0009:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L_0x0039
            java.lang.Object r1 = r6.next()
            org.bouncycastle.asn1.x509.GeneralSubtree r1 = (org.bouncycastle.asn1.x509.GeneralSubtree) r1
            org.bouncycastle.asn1.x509.GeneralName r1 = r1.getBase()
            java.lang.String r1 = r4.a(r1)
            if (r5 != 0) goto L_0x0025
            if (r1 == 0) goto L_0x0009
            r0.add(r1)
            goto L_0x0009
        L_0x0025:
            java.util.Iterator r2 = r5.iterator()
        L_0x0029:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0009
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            r4.c(r1, r3, r0)
            goto L_0x0029
        L_0x0039:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.b(java.util.Set, java.util.Set):java.util.Set");
    }

    private Set b(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return Collections.EMPTY_SET;
        }
        byte[][] d2 = d(bArr, bArr2);
        byte[] bArr3 = d2[0];
        byte[] bArr4 = d2[1];
        byte[] bArr5 = d2[2];
        byte[] bArr6 = d2[3];
        byte[][] a2 = a(bArr3, bArr4, bArr5, bArr6);
        return h(f(a2[0], a2[2]), g(a2[1], a2[3])) == 1 ? Collections.EMPTY_SET : Collections.singleton(c(i(a2[0], a2[2]), i(bArr4, bArr6)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
        if (b(r5.substring(r4.indexOf(64) + 1), r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006e, code lost:
        if (b(r5, r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0079, code lost:
        if (b(r5, r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007b, code lost:
        r6.add(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0097, code lost:
        if (r5.substring(r4.indexOf(64) + 1).equalsIgnoreCase(r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a6, code lost:
        if (b(r4, r5) != false) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ad, code lost:
        if (r4.equalsIgnoreCase(r5) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001d, code lost:
        if (r4.equalsIgnoreCase(r5) != false) goto L_0x007b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.lang.String r4, java.lang.String r5, java.util.Set r6) {
        /*
            r3 = this;
            r0 = 64
            int r1 = r4.indexOf(r0)
            r2 = -1
            if (r1 == r2) goto L_0x0036
            int r1 = r4.indexOf(r0)
            int r1 = r1 + 1
            java.lang.String r1 = r4.substring(r1)
            int r0 = r5.indexOf(r0)
            if (r0 == r2) goto L_0x0020
            boolean r0 = r4.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
        L_0x0020:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x002f
            boolean r0 = r3.b(r1, r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x0071
        L_0x002f:
            boolean r0 = r1.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x0071
        L_0x0036:
            java.lang.String r1 = "."
            boolean r1 = r4.startsWith(r1)
            if (r1 == 0) goto L_0x0083
            int r1 = r5.indexOf(r0)
            if (r1 == r2) goto L_0x0055
            int r0 = r4.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r5.substring(r0)
            boolean r0 = r3.b(r0, r4)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
        L_0x0055:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x0075
            boolean r0 = r3.b(r4, r5)
            if (r0 != 0) goto L_0x0071
            boolean r0 = r4.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x006a
            goto L_0x0071
        L_0x006a:
            boolean r0 = r3.b(r5, r4)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
        L_0x0071:
            r6.add(r5)
            return
        L_0x0075:
            boolean r0 = r3.b(r5, r4)
            if (r0 == 0) goto L_0x007f
        L_0x007b:
            r6.add(r4)
            return
        L_0x007f:
            r6.add(r4)
            goto L_0x0071
        L_0x0083:
            int r1 = r5.indexOf(r0)
            if (r1 == r2) goto L_0x009a
            int r0 = r4.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r5.substring(r0)
            boolean r0 = r0.equalsIgnoreCase(r4)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
        L_0x009a:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x00a9
            boolean r0 = r3.b(r4, r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x0071
        L_0x00a9:
            boolean r0 = r4.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x007f
            goto L_0x007b
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.b(java.lang.String, java.lang.String, java.util.Set):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r3v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.util.Set<java.lang.String> r3, java.lang.String r4) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.Iterator r0 = r3.iterator()
        L_0x0007:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x001a
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r1 = r2.a(r4, r1)
            if (r1 == 0) goto L_0x0007
            return
        L_0x001a:
            int r4 = r4.length()
            if (r4 != 0) goto L_0x0027
            int r3 = r3.size()
            if (r3 != 0) goto L_0x0027
            return
        L_0x0027:
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r3 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r4 = "Subject email address is not from a permitted subtree."
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.b(java.util.Set, java.lang.String):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.ASN1Sequence>, for r2v0, types: [java.util.Set<org.bouncycastle.asn1.ASN1Sequence>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.util.Set<org.bouncycastle.asn1.ASN1Sequence> r2, org.bouncycastle.asn1.ASN1Sequence r3) {
        /*
            r1 = this;
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.util.Iterator r2 = r2.iterator()
        L_0x000b:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0025
            java.lang.Object r0 = r2.next()
            org.bouncycastle.asn1.ASN1Sequence r0 = (org.bouncycastle.asn1.ASN1Sequence) r0
            boolean r0 = a(r3, r0)
            if (r0 == 0) goto L_0x000b
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r2 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r3 = "Subject distinguished name is from an excluded subtree"
            r2.<init>(r3)
            throw r2
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.b(java.util.Set, org.bouncycastle.asn1.ASN1Sequence):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<byte[]>, for r3v0, types: [java.util.Set, java.util.Set<byte[]>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.util.Set<byte[]> r3, byte[] r4) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.Iterator r0 = r3.iterator()
        L_0x0007:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x001a
            java.lang.Object r1 = r0.next()
            byte[] r1 = (byte[]) r1
            boolean r1 = r2.e(r4, r1)
            if (r1 == 0) goto L_0x0007
            return
        L_0x001a:
            int r4 = r4.length
            if (r4 != 0) goto L_0x0024
            int r3 = r3.size()
            if (r3 != 0) goto L_0x0024
            return
        L_0x0024:
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r3 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r4 = "IP is not from a permitted subtree."
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.b(java.util.Set, byte[]):void");
    }

    private boolean b(String str, String str2) {
        if (str2.startsWith(".")) {
            str2 = str2.substring(1);
        }
        String[] split = Strings.split(str2, '.');
        String[] split2 = Strings.split(str, '.');
        if (split2.length <= split.length) {
            return false;
        }
        int length = split2.length - split.length;
        for (int i2 = -1; i2 < split.length; i2++) {
            if (i2 == -1) {
                if (split2[i2 + length].equals("")) {
                    return false;
                }
            } else if (!split[i2].equalsIgnoreCase(split2[i2 + length])) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<byte[]>, for r5v0, types: [java.util.Set, java.util.Set<byte[]>] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, for r6v0, types: [java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set c(java.util.Set<byte[]> r5, java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree> r6) {
        /*
            r4 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r6 = r6.iterator()
        L_0x0009:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L_0x0045
            java.lang.Object r1 = r6.next()
            org.bouncycastle.asn1.x509.GeneralSubtree r1 = (org.bouncycastle.asn1.x509.GeneralSubtree) r1
            org.bouncycastle.asn1.x509.GeneralName r1 = r1.getBase()
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getName()
            org.bouncycastle.asn1.ASN1OctetString r1 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r1)
            byte[] r1 = r1.getOctets()
            if (r5 != 0) goto L_0x002d
            if (r1 == 0) goto L_0x0009
            r0.add(r1)
            goto L_0x0009
        L_0x002d:
            java.util.Iterator r2 = r5.iterator()
        L_0x0031:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0009
            java.lang.Object r3 = r2.next()
            byte[] r3 = (byte[]) r3
            java.util.Set r3 = r4.b(r3, r1)
            r0.addAll(r3)
            goto L_0x0031
        L_0x0045:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.c(java.util.Set, java.util.Set):java.util.Set");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.ASN1Sequence>, for r4v0, types: [java.util.Set<org.bouncycastle.asn1.ASN1Sequence>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set c(java.util.Set<org.bouncycastle.asn1.ASN1Sequence> r4, org.bouncycastle.asn1.ASN1Sequence r5) {
        /*
            r3 = this;
            boolean r0 = r4.isEmpty()
            if (r0 == 0) goto L_0x000d
            if (r5 != 0) goto L_0x0009
            return r4
        L_0x0009:
            r4.add(r5)
            return r4
        L_0x000d:
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r4 = r4.iterator()
        L_0x0016:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x003a
            java.lang.Object r1 = r4.next()
            org.bouncycastle.asn1.ASN1Sequence r1 = (org.bouncycastle.asn1.ASN1Sequence) r1
            boolean r2 = a(r5, r1)
            if (r2 == 0) goto L_0x002c
            r0.add(r1)
            goto L_0x0016
        L_0x002c:
            boolean r2 = a(r1, r5)
            if (r2 == 0) goto L_0x0036
        L_0x0032:
            r0.add(r5)
            goto L_0x0016
        L_0x0036:
            r0.add(r1)
            goto L_0x0032
        L_0x003a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.c(java.util.Set, org.bouncycastle.asn1.ASN1Sequence):java.util.Set");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
        if (b(r5.substring(r4.indexOf(64) + 1), r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006e, code lost:
        if (b(r5, r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0079, code lost:
        if (b(r5, r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0093, code lost:
        if (r5.substring(r5.indexOf(64) + 1).equalsIgnoreCase(r4) != false) goto L_0x007b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(java.lang.String r4, java.lang.String r5, java.util.Set r6) {
        /*
            r3 = this;
            r0 = 64
            int r1 = r4.indexOf(r0)
            r2 = -1
            if (r1 == r2) goto L_0x0036
            int r1 = r4.indexOf(r0)
            int r1 = r1 + 1
            java.lang.String r1 = r4.substring(r1)
            int r0 = r5.indexOf(r0)
            if (r0 == r2) goto L_0x0020
            boolean r5 = r4.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x0020:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x002f
            boolean r5 = r3.b(r1, r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x002f:
            boolean r5 = r1.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x0036:
            java.lang.String r1 = "."
            boolean r1 = r4.startsWith(r1)
            if (r1 == 0) goto L_0x007f
            int r1 = r5.indexOf(r0)
            if (r1 == r2) goto L_0x0055
            int r0 = r4.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r5.substring(r0)
            boolean r4 = r3.b(r0, r4)
            if (r4 == 0) goto L_0x00ac
            goto L_0x007b
        L_0x0055:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x0075
            boolean r0 = r3.b(r4, r5)
            if (r0 != 0) goto L_0x0071
            boolean r0 = r4.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x006a
            goto L_0x0071
        L_0x006a:
            boolean r4 = r3.b(r5, r4)
            if (r4 == 0) goto L_0x00ac
            goto L_0x007b
        L_0x0071:
            r6.add(r4)
            return
        L_0x0075:
            boolean r4 = r3.b(r5, r4)
            if (r4 == 0) goto L_0x00ac
        L_0x007b:
            r6.add(r5)
            return
        L_0x007f:
            int r1 = r5.indexOf(r0)
            if (r1 == r2) goto L_0x0096
            int r0 = r5.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r5.substring(r0)
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x00ac
            goto L_0x007b
        L_0x0096:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x00a5
            boolean r5 = r3.b(r4, r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x00a5:
            boolean r5 = r4.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x00ac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.c(java.lang.String, java.lang.String, java.util.Set):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r2v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(java.util.Set<java.lang.String> r2, java.lang.String r3) {
        /*
            r1 = this;
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.util.Iterator r2 = r2.iterator()
        L_0x000b:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0025
            java.lang.Object r0 = r2.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r1.a(r3, r0)
            if (r0 == 0) goto L_0x000b
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r2 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r3 = "Email address is from an excluded subtree."
            r2.<init>(r3)
            throw r2
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.c(java.util.Set, java.lang.String):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<byte[]>, for r2v0, types: [java.util.Set, java.util.Set<byte[]>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(java.util.Set<byte[]> r2, byte[] r3) {
        /*
            r1 = this;
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.util.Iterator r2 = r2.iterator()
        L_0x000b:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0025
            java.lang.Object r0 = r2.next()
            byte[] r0 = (byte[]) r0
            boolean r0 = r1.e(r3, r0)
            if (r0 == 0) goto L_0x000b
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r2 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r3 = "IP is from an excluded subtree."
            r2.<init>(r3)
            throw r2
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.c(java.util.Set, byte[]):void");
    }

    private boolean c(String str, String str2) {
        String a2 = a(str);
        if (!str2.startsWith(".")) {
            if (a2.equalsIgnoreCase(str2)) {
                return true;
            }
        } else if (b(a2, str2)) {
            return true;
        }
        return false;
    }

    private byte[] c(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        byte[] bArr3 = new byte[(length * 2)];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        System.arraycopy(bArr2, 0, bArr3, length, length);
        return bArr3;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r6v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, for r7v0, types: [java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set d(java.util.Set<java.lang.String> r6, java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree> r7) {
        /*
            r5 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r7 = r7.iterator()
        L_0x0009:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto L_0x0049
            java.lang.Object r1 = r7.next()
            org.bouncycastle.asn1.x509.GeneralSubtree r1 = (org.bouncycastle.asn1.x509.GeneralSubtree) r1
            org.bouncycastle.asn1.x509.GeneralName r1 = r1.getBase()
            java.lang.String r1 = r5.a(r1)
            if (r6 != 0) goto L_0x0025
            if (r1 == 0) goto L_0x0009
            r0.add(r1)
            goto L_0x0009
        L_0x0025:
            java.util.Iterator r2 = r6.iterator()
        L_0x0029:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0009
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = r5.b(r3, r1)
            if (r4 == 0) goto L_0x003f
            r0.add(r3)
            goto L_0x0029
        L_0x003f:
            boolean r3 = r5.b(r1, r3)
            if (r3 == 0) goto L_0x0029
            r0.add(r1)
            goto L_0x0029
        L_0x0049:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.d(java.util.Set, java.util.Set):java.util.Set");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
        if (b(r5.substring(r4.indexOf(64) + 1), r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006e, code lost:
        if (b(r5, r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0079, code lost:
        if (b(r5, r4) != false) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0093, code lost:
        if (r5.substring(r5.indexOf(64) + 1).equalsIgnoreCase(r4) != false) goto L_0x007b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d(java.lang.String r4, java.lang.String r5, java.util.Set r6) {
        /*
            r3 = this;
            r0 = 64
            int r1 = r4.indexOf(r0)
            r2 = -1
            if (r1 == r2) goto L_0x0036
            int r1 = r4.indexOf(r0)
            int r1 = r1 + 1
            java.lang.String r1 = r4.substring(r1)
            int r0 = r5.indexOf(r0)
            if (r0 == r2) goto L_0x0020
            boolean r5 = r4.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x0020:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x002f
            boolean r5 = r3.b(r1, r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x002f:
            boolean r5 = r1.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x0036:
            java.lang.String r1 = "."
            boolean r1 = r4.startsWith(r1)
            if (r1 == 0) goto L_0x007f
            int r1 = r5.indexOf(r0)
            if (r1 == r2) goto L_0x0055
            int r0 = r4.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r5.substring(r0)
            boolean r4 = r3.b(r0, r4)
            if (r4 == 0) goto L_0x00ac
            goto L_0x007b
        L_0x0055:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x0075
            boolean r0 = r3.b(r4, r5)
            if (r0 != 0) goto L_0x0071
            boolean r0 = r4.equalsIgnoreCase(r5)
            if (r0 == 0) goto L_0x006a
            goto L_0x0071
        L_0x006a:
            boolean r4 = r3.b(r5, r4)
            if (r4 == 0) goto L_0x00ac
            goto L_0x007b
        L_0x0071:
            r6.add(r4)
            return
        L_0x0075:
            boolean r4 = r3.b(r5, r4)
            if (r4 == 0) goto L_0x00ac
        L_0x007b:
            r6.add(r5)
            return
        L_0x007f:
            int r1 = r5.indexOf(r0)
            if (r1 == r2) goto L_0x0096
            int r0 = r5.indexOf(r0)
            int r0 = r0 + 1
            java.lang.String r0 = r5.substring(r0)
            boolean r4 = r0.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x00ac
            goto L_0x007b
        L_0x0096:
            java.lang.String r0 = "."
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x00a5
            boolean r5 = r3.b(r4, r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x00a5:
            boolean r5 = r4.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x00ac
            goto L_0x0071
        L_0x00ac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.d(java.lang.String, java.lang.String, java.util.Set):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r4v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d(java.util.Set<java.lang.String> r4, java.lang.String r5) {
        /*
            r3 = this;
            if (r4 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.Iterator r0 = r4.iterator()
        L_0x0007:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0020
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = r3.b(r5, r1)
            if (r2 != 0) goto L_0x001f
            boolean r1 = r5.equalsIgnoreCase(r1)
            if (r1 == 0) goto L_0x0007
        L_0x001f:
            return
        L_0x0020:
            int r5 = r5.length()
            if (r5 != 0) goto L_0x002d
            int r4 = r4.size()
            if (r4 != 0) goto L_0x002d
            return
        L_0x002d:
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r4 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r5 = "DNS is not from a permitted subtree."
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.d(java.util.Set, java.lang.String):void");
    }

    private byte[][] d(byte[] bArr, byte[] bArr2) {
        int length = bArr.length / 2;
        byte[] bArr3 = new byte[length];
        byte[] bArr4 = new byte[length];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        System.arraycopy(bArr, length, bArr4, 0, length);
        byte[] bArr5 = new byte[length];
        byte[] bArr6 = new byte[length];
        System.arraycopy(bArr2, 0, bArr5, 0, length);
        System.arraycopy(bArr2, length, bArr6, 0, length);
        return new byte[][]{bArr3, bArr4, bArr5, bArr6};
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r5v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, for r6v0, types: [java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set e(java.util.Set<java.lang.String> r5, java.util.Set<org.bouncycastle.asn1.x509.GeneralSubtree> r6) {
        /*
            r4 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r6 = r6.iterator()
        L_0x0009:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L_0x0039
            java.lang.Object r1 = r6.next()
            org.bouncycastle.asn1.x509.GeneralSubtree r1 = (org.bouncycastle.asn1.x509.GeneralSubtree) r1
            org.bouncycastle.asn1.x509.GeneralName r1 = r1.getBase()
            java.lang.String r1 = r4.a(r1)
            if (r5 != 0) goto L_0x0025
            if (r1 == 0) goto L_0x0009
            r0.add(r1)
            goto L_0x0009
        L_0x0025:
            java.util.Iterator r2 = r5.iterator()
        L_0x0029:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0009
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            r4.d(r3, r1, r0)
            goto L_0x0029
        L_0x0039:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.e(java.util.Set, java.util.Set):java.util.Set");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r3v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void e(java.util.Set<java.lang.String> r3, java.lang.String r4) {
        /*
            r2 = this;
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.util.Iterator r3 = r3.iterator()
        L_0x000b:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x002b
            java.lang.Object r0 = r3.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r1 = r2.b(r4, r0)
            if (r1 != 0) goto L_0x0023
            boolean r0 = r4.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x000b
        L_0x0023:
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r3 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r4 = "DNS is from an excluded subtree."
            r3.<init>(r4)
            throw r3
        L_0x002b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.e(java.util.Set, java.lang.String):void");
    }

    private boolean e(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        if (length != bArr2.length / 2) {
            return false;
        }
        byte[] bArr3 = new byte[length];
        System.arraycopy(bArr2, length, bArr3, 0, length);
        byte[] bArr4 = new byte[length];
        byte[] bArr5 = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            bArr4[i2] = (byte) (bArr2[i2] & bArr3[i2]);
            bArr5[i2] = (byte) (bArr[i2] & bArr3[i2]);
        }
        return Arrays.areEqual(bArr4, bArr5);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r2v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f(java.util.Set<java.lang.String> r2, java.lang.String r3) {
        /*
            r1 = this;
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.util.Iterator r2 = r2.iterator()
        L_0x000b:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0025
            java.lang.Object r0 = r2.next()
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r1.c(r3, r0)
            if (r0 == 0) goto L_0x000b
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r2 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r3 = "URI is from an excluded subtree."
            r2.<init>(r3)
            throw r2
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.f(java.util.Set, java.lang.String):void");
    }

    private static byte[] f(byte[] bArr, byte[] bArr2) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if ((bArr[i2] & UnsignedBytes.MAX_VALUE) > (65535 & bArr2[i2])) {
                return bArr;
            }
        }
        return bArr2;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r3v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set g(java.util.Set<java.lang.String> r3, java.lang.String r4) {
        /*
            r2 = this;
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x000d
            if (r4 != 0) goto L_0x0009
            return r3
        L_0x0009:
            r3.add(r4)
            return r3
        L_0x000d:
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r3 = r3.iterator()
        L_0x0016:
            boolean r1 = r3.hasNext()
            if (r1 == 0) goto L_0x0026
            java.lang.Object r1 = r3.next()
            java.lang.String r1 = (java.lang.String) r1
            r2.b(r1, r4, r0)
            goto L_0x0016
        L_0x0026:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.g(java.util.Set, java.lang.String):java.util.Set");
    }

    private static byte[] g(byte[] bArr, byte[] bArr2) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if ((bArr[i2] & UnsignedBytes.MAX_VALUE) < (65535 & bArr2[i2])) {
                return bArr;
            }
        }
        return bArr2;
    }

    private static int h(byte[] bArr, byte[] bArr2) {
        if (Arrays.areEqual(bArr, bArr2)) {
            return 0;
        }
        return Arrays.areEqual(f(bArr, bArr2), bArr) ? 1 : -1;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r3v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h(java.util.Set<java.lang.String> r3, java.lang.String r4) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.Iterator r0 = r3.iterator()
        L_0x0007:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x001a
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r1 = r2.c(r4, r1)
            if (r1 == 0) goto L_0x0007
            return
        L_0x001a:
            int r4 = r4.length()
            if (r4 != 0) goto L_0x0027
            int r3 = r3.size()
            if (r3 != 0) goto L_0x0027
            return
        L_0x0027:
            org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException r3 = new org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r4 = "URI is not from a permitted subtree."
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.h(java.util.Set, java.lang.String):void");
    }

    private static byte[] i(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr3[i2] = (byte) (bArr[i2] | bArr2[i2]);
        }
        return bArr3;
    }

    public void addExcludedSubtree(GeneralSubtree generalSubtree) {
        GeneralName base = generalSubtree.getBase();
        switch (base.getTagNo()) {
            case 1:
                this.c = a(this.c, a(base));
                return;
            case 2:
                this.b = unionDNS(this.b, a(base));
                return;
            case 4:
                this.a = c(this.a, (ASN1Sequence) base.getName().toASN1Primitive());
                return;
            case 6:
                this.d = g(this.d, a(base));
                return;
            case 7:
                this.e = a(this.e, ASN1OctetString.getInstance(base.getName()).getOctets());
                return;
            default:
                return;
        }
    }

    public void checkExcluded(GeneralName generalName) {
        switch (generalName.getTagNo()) {
            case 1:
                c(this.c, a(generalName));
                return;
            case 2:
                e(this.b, DERIA5String.getInstance(generalName.getName()).getString());
                return;
            case 4:
                checkExcludedDN(ASN1Sequence.getInstance(generalName.getName().toASN1Primitive()));
                return;
            case 6:
                f(this.d, DERIA5String.getInstance(generalName.getName()).getString());
                return;
            case 7:
                c(this.e, ASN1OctetString.getInstance(generalName.getName()).getOctets());
                return;
            default:
                return;
        }
    }

    public void checkExcludedDN(ASN1Sequence aSN1Sequence) {
        b(this.a, aSN1Sequence);
    }

    public void checkPermitted(GeneralName generalName) {
        switch (generalName.getTagNo()) {
            case 1:
                b(this.h, a(generalName));
                return;
            case 2:
                d(this.g, DERIA5String.getInstance(generalName.getName()).getString());
                return;
            case 4:
                checkPermittedDN(ASN1Sequence.getInstance(generalName.getName().toASN1Primitive()));
                return;
            case 6:
                h(this.i, DERIA5String.getInstance(generalName.getName()).getString());
                return;
            case 7:
                b(this.j, ASN1OctetString.getInstance(generalName.getName()).getOctets());
                return;
            default:
                return;
        }
    }

    public void checkPermittedDN(ASN1Sequence aSN1Sequence) {
        a(this.f, aSN1Sequence);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof PKIXNameConstraintValidator)) {
            return false;
        }
        PKIXNameConstraintValidator pKIXNameConstraintValidator = (PKIXNameConstraintValidator) obj;
        if (a((Collection) pKIXNameConstraintValidator.a, (Collection) this.a) && a((Collection) pKIXNameConstraintValidator.b, (Collection) this.b) && a((Collection) pKIXNameConstraintValidator.c, (Collection) this.c) && a((Collection) pKIXNameConstraintValidator.e, (Collection) this.e) && a((Collection) pKIXNameConstraintValidator.d, (Collection) this.d) && a((Collection) pKIXNameConstraintValidator.f, (Collection) this.f) && a((Collection) pKIXNameConstraintValidator.g, (Collection) this.g) && a((Collection) pKIXNameConstraintValidator.h, (Collection) this.h) && a((Collection) pKIXNameConstraintValidator.j, (Collection) this.j) && a((Collection) pKIXNameConstraintValidator.i, (Collection) this.i)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return a((Collection) this.a) + a((Collection) this.b) + a((Collection) this.c) + a((Collection) this.e) + a((Collection) this.d) + a((Collection) this.f) + a((Collection) this.g) + a((Collection) this.h) + a((Collection) this.j) + a((Collection) this.i);
    }

    public void intersectEmptyPermittedSubtree(int i2) {
        switch (i2) {
            case 1:
                this.h = new HashSet();
                return;
            case 2:
                this.g = new HashSet();
                return;
            case 4:
                this.f = new HashSet();
                return;
            case 6:
                this.i = new HashSet();
                return;
            case 7:
                this.j = new HashSet();
                return;
            default:
                return;
        }
    }

    public void intersectPermittedSubtree(GeneralSubtree generalSubtree) {
        intersectPermittedSubtree(new GeneralSubtree[]{generalSubtree});
    }

    public void intersectPermittedSubtree(GeneralSubtree[] generalSubtreeArr) {
        HashMap hashMap = new HashMap();
        for (int i2 = 0; i2 != generalSubtreeArr.length; i2++) {
            GeneralSubtree generalSubtree = generalSubtreeArr[i2];
            Integer valueOf = Integers.valueOf(generalSubtree.getBase().getTagNo());
            if (hashMap.get(valueOf) == null) {
                hashMap.put(valueOf, new HashSet());
            }
            ((Set) hashMap.get(valueOf)).add(generalSubtree);
        }
        for (Entry entry : hashMap.entrySet()) {
            switch (((Integer) entry.getKey()).intValue()) {
                case 1:
                    this.h = b(this.h, (Set) entry.getValue());
                    break;
                case 2:
                    this.g = d(this.g, (Set) entry.getValue());
                    break;
                case 4:
                    this.f = a(this.f, (Set) entry.getValue());
                    break;
                case 6:
                    this.i = e(this.i, (Set) entry.getValue());
                    break;
                case 7:
                    this.j = c(this.j, (Set) entry.getValue());
                    break;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append("permitted:\n");
        String sb2 = sb.toString();
        if (this.f != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("DN:\n");
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(this.f.toString());
            sb5.append("\n");
            sb2 = sb5.toString();
        }
        if (this.g != null) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(sb2);
            sb6.append("DNS:\n");
            String sb7 = sb6.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(sb7);
            sb8.append(this.g.toString());
            sb8.append("\n");
            sb2 = sb8.toString();
        }
        if (this.h != null) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append(sb2);
            sb9.append("Email:\n");
            String sb10 = sb9.toString();
            StringBuilder sb11 = new StringBuilder();
            sb11.append(sb10);
            sb11.append(this.h.toString());
            sb11.append("\n");
            sb2 = sb11.toString();
        }
        if (this.i != null) {
            StringBuilder sb12 = new StringBuilder();
            sb12.append(sb2);
            sb12.append("URI:\n");
            String sb13 = sb12.toString();
            StringBuilder sb14 = new StringBuilder();
            sb14.append(sb13);
            sb14.append(this.i.toString());
            sb14.append("\n");
            sb2 = sb14.toString();
        }
        if (this.j != null) {
            StringBuilder sb15 = new StringBuilder();
            sb15.append(sb2);
            sb15.append("IP:\n");
            String sb16 = sb15.toString();
            StringBuilder sb17 = new StringBuilder();
            sb17.append(sb16);
            sb17.append(a(this.j));
            sb17.append("\n");
            sb2 = sb17.toString();
        }
        StringBuilder sb18 = new StringBuilder();
        sb18.append(sb2);
        sb18.append("excluded:\n");
        String sb19 = sb18.toString();
        if (!this.a.isEmpty()) {
            StringBuilder sb20 = new StringBuilder();
            sb20.append(sb19);
            sb20.append("DN:\n");
            String sb21 = sb20.toString();
            StringBuilder sb22 = new StringBuilder();
            sb22.append(sb21);
            sb22.append(this.a.toString());
            sb22.append("\n");
            sb19 = sb22.toString();
        }
        if (!this.b.isEmpty()) {
            StringBuilder sb23 = new StringBuilder();
            sb23.append(sb19);
            sb23.append("DNS:\n");
            String sb24 = sb23.toString();
            StringBuilder sb25 = new StringBuilder();
            sb25.append(sb24);
            sb25.append(this.b.toString());
            sb25.append("\n");
            sb19 = sb25.toString();
        }
        if (!this.c.isEmpty()) {
            StringBuilder sb26 = new StringBuilder();
            sb26.append(sb19);
            sb26.append("Email:\n");
            String sb27 = sb26.toString();
            StringBuilder sb28 = new StringBuilder();
            sb28.append(sb27);
            sb28.append(this.c.toString());
            sb28.append("\n");
            sb19 = sb28.toString();
        }
        if (!this.d.isEmpty()) {
            StringBuilder sb29 = new StringBuilder();
            sb29.append(sb19);
            sb29.append("URI:\n");
            String sb30 = sb29.toString();
            StringBuilder sb31 = new StringBuilder();
            sb31.append(sb30);
            sb31.append(this.d.toString());
            sb31.append("\n");
            sb19 = sb31.toString();
        }
        if (this.e.isEmpty()) {
            return sb19;
        }
        StringBuilder sb32 = new StringBuilder();
        sb32.append(sb19);
        sb32.append("IP:\n");
        String sb33 = sb32.toString();
        StringBuilder sb34 = new StringBuilder();
        sb34.append(sb33);
        sb34.append(a(this.e));
        sb34.append("\n");
        return sb34.toString();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.String>, for r4v0, types: [java.util.Set, java.util.Set<java.lang.String>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Set unionDNS(java.util.Set<java.lang.String> r4, java.lang.String r5) {
        /*
            r3 = this;
            boolean r0 = r4.isEmpty()
            if (r0 == 0) goto L_0x000d
            if (r5 != 0) goto L_0x0009
            return r4
        L_0x0009:
            r4.add(r5)
            return r4
        L_0x000d:
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r4 = r4.iterator()
        L_0x0016:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x003a
            java.lang.Object r1 = r4.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = r3.b(r1, r5)
            if (r2 == 0) goto L_0x002c
        L_0x0028:
            r0.add(r5)
            goto L_0x0016
        L_0x002c:
            boolean r2 = r3.b(r5, r1)
            if (r2 == 0) goto L_0x0036
            r0.add(r1)
            goto L_0x0016
        L_0x0036:
            r0.add(r1)
            goto L_0x0028
        L_0x003a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.PKIXNameConstraintValidator.unionDNS(java.util.Set, java.lang.String):java.util.Set");
    }
}
