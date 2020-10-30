package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.data.DataHolder.zza;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class zzd<T extends SafeParcelable> extends AbstractDataBuffer<T> {
    private static final String[] a = {"data"};
    private final Creator<T> b;

    public zzd(DataHolder dataHolder, Creator<T> creator) {
        super(dataHolder);
        this.b = creator;
    }

    public static <T extends SafeParcelable> void zza(zza zza, T t) {
        Parcel obtain = Parcel.obtain();
        t.writeToParcel(obtain, 0);
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", obtain.marshall());
        zza.zza(contentValues);
        obtain.recycle();
    }

    public static zza zzatd() {
        return DataHolder.zzc(a);
    }

    /* renamed from: zzga */
    public T get(int i) {
        byte[] zzg = this.xi.zzg("data", i, this.xi.zzgb(i));
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(zzg, 0, zzg.length);
        obtain.setDataPosition(0);
        T t = (SafeParcelable) this.b.createFromParcel(obtain);
        obtain.recycle();
        return t;
    }
}
