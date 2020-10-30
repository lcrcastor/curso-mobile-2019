package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.zza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class StringToIntConverter extends AbstractSafeParcelable implements zza<String, Integer> {
    public static final zzb CREATOR = new zzb();
    private final int a;
    private final HashMap<String, Integer> b;
    private final SparseArray<String> c;
    private final ArrayList<Entry> d;

    public static final class Entry extends AbstractSafeParcelable {
        public static final zzc CREATOR = new zzc();
        final int a;
        final String b;
        final int c;

        Entry(int i, String str, int i2) {
            this.a = i;
            this.b = str;
            this.c = i2;
        }

        Entry(String str, int i) {
            this.a = 1;
            this.b = str;
            this.c = i;
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzc zzc = CREATOR;
            zzc.a(this, parcel, i);
        }
    }

    public StringToIntConverter() {
        this.a = 1;
        this.b = new HashMap<>();
        this.c = new SparseArray<>();
        this.d = null;
    }

    StringToIntConverter(int i, ArrayList<Entry> arrayList) {
        this.a = i;
        this.b = new HashMap<>();
        this.c = new SparseArray<>();
        this.d = null;
        a(arrayList);
    }

    private void a(ArrayList<Entry> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzj(entry.b, entry.c);
        }
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public ArrayList<Entry> b() {
        ArrayList<Entry> arrayList = new ArrayList<>();
        for (String str : this.b.keySet()) {
            arrayList.add(new Entry(str, ((Integer) this.b.get(str)).intValue()));
        }
        return arrayList;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb zzb = CREATOR;
        zzb.a(this, parcel, i);
    }

    public int zzavq() {
        return 7;
    }

    public int zzavr() {
        return 0;
    }

    /* renamed from: zzd */
    public String convertBack(Integer num) {
        String str = (String) this.c.get(num.intValue());
        return (str != null || !this.b.containsKey("gms_unknown")) ? str : "gms_unknown";
    }

    public StringToIntConverter zzj(String str, int i) {
        this.b.put(str, Integer.valueOf(i));
        this.c.put(i, str);
        return this;
    }
}
