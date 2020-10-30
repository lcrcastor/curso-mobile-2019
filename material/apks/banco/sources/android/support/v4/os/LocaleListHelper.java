package android.support.v4.os;

import android.os.Build.VERSION;
import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

@RequiresApi(14)
@RestrictTo({Scope.LIBRARY_GROUP})
final class LocaleListHelper {
    private static final Locale[] c = new Locale[0];
    private static final LocaleListHelper d = new LocaleListHelper(new Locale[0]);
    private static final Locale e = new Locale("en", "XA");
    private static final Locale f = new Locale("ar", "XB");
    private static final Locale g = LocaleHelper.a("en-Latn");
    private static final Object h = new Object();
    @GuardedBy("sLock")
    private static LocaleListHelper i;
    @GuardedBy("sLock")
    private static LocaleListHelper j;
    @GuardedBy("sLock")
    private static LocaleListHelper k;
    @GuardedBy("sLock")
    private static Locale l;
    private final Locale[] a;
    @NonNull
    private final String b;

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public Locale a(int i2) {
        if (i2 < 0 || i2 >= this.a.length) {
            return null;
        }
        return this.a[i2];
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean a() {
        return this.a.length == 0;
    }

    /* access modifiers changed from: 0000 */
    @IntRange(from = 0)
    @RestrictTo({Scope.LIBRARY_GROUP})
    public int b() {
        return this.a.length;
    }

    /* access modifiers changed from: 0000 */
    @IntRange(from = -1)
    @RestrictTo({Scope.LIBRARY_GROUP})
    public int a(Locale locale) {
        for (int i2 = 0; i2 < this.a.length; i2++) {
            if (this.a[i2].equals(locale)) {
                return i2;
            }
        }
        return -1;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocaleListHelper)) {
            return false;
        }
        Locale[] localeArr = ((LocaleListHelper) obj).a;
        if (this.a.length != localeArr.length) {
            return false;
        }
        for (int i2 = 0; i2 < this.a.length; i2++) {
            if (!this.a[i2].equals(localeArr[i2])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i2 = 1;
        for (Locale hashCode : this.a) {
            i2 = (i2 * 31) + hashCode.hashCode();
        }
        return i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i2 = 0; i2 < this.a.length; i2++) {
            sb.append(this.a[i2]);
            if (i2 < this.a.length - 1) {
                sb.append(',');
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    @NonNull
    public String c() {
        return this.b;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    LocaleListHelper(@NonNull Locale... localeArr) {
        if (localeArr.length == 0) {
            this.a = c;
            this.b = "";
            return;
        }
        Locale[] localeArr2 = new Locale[localeArr.length];
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < localeArr.length) {
            Locale locale = localeArr[i2];
            if (locale == null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("list[");
                sb2.append(i2);
                sb2.append("] is null");
                throw new NullPointerException(sb2.toString());
            } else if (hashSet.contains(locale)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("list[");
                sb3.append(i2);
                sb3.append("] is a repetition");
                throw new IllegalArgumentException(sb3.toString());
            } else {
                Locale locale2 = (Locale) locale.clone();
                localeArr2[i2] = locale2;
                sb.append(LocaleHelper.a(locale2));
                if (i2 < localeArr.length - 1) {
                    sb.append(',');
                }
                hashSet.add(locale2);
                i2++;
            }
        }
        this.a = localeArr2;
        this.b = sb.toString();
    }

    private static String b(Locale locale) {
        if (VERSION.SDK_INT < 21) {
            return "";
        }
        String script = locale.getScript();
        return !script.isEmpty() ? script : "";
    }

    private static boolean c(Locale locale) {
        return e.equals(locale) || f.equals(locale);
    }

    @IntRange(from = 0, to = 1)
    private static int a(Locale locale, Locale locale2) {
        int i2 = 1;
        if (locale.equals(locale2)) {
            return 1;
        }
        if (!locale.getLanguage().equals(locale2.getLanguage()) || c(locale) || c(locale2)) {
            return 0;
        }
        String b2 = b(locale);
        if (!b2.isEmpty()) {
            return b2.equals(b(locale2)) ? 1 : 0;
        }
        String country = locale.getCountry();
        if (!country.isEmpty() && !country.equals(locale2.getCountry())) {
            i2 = 0;
        }
        return i2;
    }

    private int d(Locale locale) {
        for (int i2 = 0; i2 < this.a.length; i2++) {
            if (a(locale, this.a[i2]) > 0) {
                return i2;
            }
        }
        return SubsamplingScaleImageView.TILE_SIZE_AUTO;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001d, code lost:
        if (r5 < Integer.MAX_VALUE) goto L_0x0023;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(java.util.Collection<java.lang.String> r4, boolean r5) {
        /*
            r3 = this;
            java.util.Locale[] r0 = r3.a
            int r0 = r0.length
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L_0x0008
            return r1
        L_0x0008:
            java.util.Locale[] r0 = r3.a
            int r0 = r0.length
            if (r0 != 0) goto L_0x000f
            r4 = -1
            return r4
        L_0x000f:
            r0 = 2147483647(0x7fffffff, float:NaN)
            if (r5 == 0) goto L_0x0020
            java.util.Locale r5 = g
            int r5 = r3.d(r5)
            if (r5 != 0) goto L_0x001d
            return r1
        L_0x001d:
            if (r5 >= r0) goto L_0x0020
            goto L_0x0023
        L_0x0020:
            r5 = 2147483647(0x7fffffff, float:NaN)
        L_0x0023:
            java.util.Iterator r4 = r4.iterator()
        L_0x0027:
            boolean r2 = r4.hasNext()
            if (r2 == 0) goto L_0x0042
            java.lang.Object r2 = r4.next()
            java.lang.String r2 = (java.lang.String) r2
            java.util.Locale r2 = android.support.v4.os.LocaleHelper.a(r2)
            int r2 = r3.d(r2)
            if (r2 != 0) goto L_0x003e
            return r1
        L_0x003e:
            if (r2 >= r5) goto L_0x0027
            r5 = r2
            goto L_0x0027
        L_0x0042:
            if (r5 != r0) goto L_0x0045
            return r1
        L_0x0045:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.os.LocaleListHelper.a(java.util.Collection, boolean):int");
    }

    private Locale b(Collection<String> collection, boolean z) {
        int a2 = a(collection, z);
        if (a2 == -1) {
            return null;
        }
        return this.a[a2];
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    @RestrictTo({Scope.LIBRARY_GROUP})
    public Locale a(String[] strArr) {
        return b(Arrays.asList(strArr), false);
    }
}
