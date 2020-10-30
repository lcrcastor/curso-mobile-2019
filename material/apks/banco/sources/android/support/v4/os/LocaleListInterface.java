package android.support.v4.os;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.Locale;

@RestrictTo({Scope.LIBRARY_GROUP})
interface LocaleListInterface {
    @IntRange(from = -1)
    int a(Locale locale);

    Object a();

    Locale a(int i);

    @Nullable
    Locale a(String[] strArr);

    void a(@NonNull Locale... localeArr);

    boolean b();

    @IntRange(from = 0)
    int c();

    String d();

    boolean equals(Object obj);

    int hashCode();

    String toString();
}
