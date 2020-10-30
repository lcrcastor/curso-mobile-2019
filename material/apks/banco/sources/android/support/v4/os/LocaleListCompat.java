package android.support.v4.os;

import android.os.Build.VERSION;
import android.os.LocaleList;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.Size;
import java.util.Locale;

public final class LocaleListCompat {
    static final LocaleListInterface a;
    private static final LocaleListCompat b = new LocaleListCompat();

    @RequiresApi(24)
    static class LocaleListCompatApi24Impl implements LocaleListInterface {
        private LocaleList a = new LocaleList(new Locale[0]);

        LocaleListCompatApi24Impl() {
        }

        public void a(@NonNull Locale... localeArr) {
            this.a = new LocaleList(localeArr);
        }

        public Object a() {
            return this.a;
        }

        public Locale a(int i) {
            return this.a.get(i);
        }

        public boolean b() {
            return this.a.isEmpty();
        }

        @IntRange(from = 0)
        public int c() {
            return this.a.size();
        }

        @IntRange(from = -1)
        public int a(Locale locale) {
            return this.a.indexOf(locale);
        }

        public boolean equals(Object obj) {
            return this.a.equals(((LocaleListCompat) obj).unwrap());
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            return this.a.toString();
        }

        public String d() {
            return this.a.toLanguageTags();
        }

        @Nullable
        public Locale a(String[] strArr) {
            if (this.a != null) {
                return this.a.getFirstMatch(strArr);
            }
            return null;
        }
    }

    static class LocaleListCompatBaseImpl implements LocaleListInterface {
        private LocaleListHelper a = new LocaleListHelper(new Locale[0]);

        LocaleListCompatBaseImpl() {
        }

        public void a(@NonNull Locale... localeArr) {
            this.a = new LocaleListHelper(localeArr);
        }

        public Object a() {
            return this.a;
        }

        public Locale a(int i) {
            return this.a.a(i);
        }

        public boolean b() {
            return this.a.a();
        }

        @IntRange(from = 0)
        public int c() {
            return this.a.b();
        }

        @IntRange(from = -1)
        public int a(Locale locale) {
            return this.a.a(locale);
        }

        public boolean equals(Object obj) {
            return this.a.equals(((LocaleListCompat) obj).unwrap());
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            return this.a.toString();
        }

        public String d() {
            return this.a.c();
        }

        @Nullable
        public Locale a(String[] strArr) {
            if (this.a != null) {
                return this.a.a(strArr);
            }
            return null;
        }
    }

    static {
        if (VERSION.SDK_INT >= 24) {
            a = new LocaleListCompatApi24Impl();
        } else {
            a = new LocaleListCompatBaseImpl();
        }
    }

    private LocaleListCompat() {
    }

    @RequiresApi(24)
    public static LocaleListCompat wrap(Object obj) {
        LocaleListCompat localeListCompat = new LocaleListCompat();
        if (obj instanceof LocaleList) {
            localeListCompat.a((LocaleList) obj);
        }
        return localeListCompat;
    }

    @Nullable
    public Object unwrap() {
        return a.a();
    }

    public static LocaleListCompat create(@NonNull Locale... localeArr) {
        LocaleListCompat localeListCompat = new LocaleListCompat();
        localeListCompat.a(localeArr);
        return localeListCompat;
    }

    public Locale get(int i) {
        return a.a(i);
    }

    public boolean isEmpty() {
        return a.b();
    }

    @IntRange(from = 0)
    public int size() {
        return a.c();
    }

    @IntRange(from = -1)
    public int indexOf(Locale locale) {
        return a.a(locale);
    }

    @NonNull
    public String toLanguageTags() {
        return a.d();
    }

    public Locale getFirstMatch(String[] strArr) {
        return a.a(strArr);
    }

    @NonNull
    public static LocaleListCompat getEmptyLocaleList() {
        return b;
    }

    @NonNull
    public static LocaleListCompat forLanguageTags(@Nullable String str) {
        Locale locale;
        if (str == null || str.isEmpty()) {
            return getEmptyLocaleList();
        }
        String[] split = str.split(",");
        Locale[] localeArr = new Locale[split.length];
        for (int i = 0; i < localeArr.length; i++) {
            if (VERSION.SDK_INT >= 21) {
                locale = Locale.forLanguageTag(split[i]);
            } else {
                locale = LocaleHelper.a(split[i]);
            }
            localeArr[i] = locale;
        }
        LocaleListCompat localeListCompat = new LocaleListCompat();
        localeListCompat.a(localeArr);
        return localeListCompat;
    }

    @Size(min = 1)
    @NonNull
    public static LocaleListCompat getAdjustedDefault() {
        if (VERSION.SDK_INT >= 24) {
            return wrap(LocaleList.getAdjustedDefault());
        }
        return create(Locale.getDefault());
    }

    @Size(min = 1)
    @NonNull
    public static LocaleListCompat getDefault() {
        if (VERSION.SDK_INT >= 24) {
            return wrap(LocaleList.getDefault());
        }
        return create(Locale.getDefault());
    }

    public boolean equals(Object obj) {
        return a.equals(obj);
    }

    public int hashCode() {
        return a.hashCode();
    }

    public String toString() {
        return a.toString();
    }

    @RequiresApi(24)
    private void a(LocaleList localeList) {
        int size = localeList.size();
        if (size > 0) {
            Locale[] localeArr = new Locale[size];
            for (int i = 0; i < size; i++) {
                localeArr[i] = localeList.get(i);
            }
            a.a(localeArr);
        }
    }

    private void a(Locale... localeArr) {
        a.a(localeArr);
    }
}
