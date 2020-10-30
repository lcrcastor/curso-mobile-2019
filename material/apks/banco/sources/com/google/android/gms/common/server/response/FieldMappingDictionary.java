package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FieldMappingDictionary extends AbstractSafeParcelable {
    public static final zzc CREATOR = new zzc();
    private final int a;
    private final HashMap<String, Map<String, Field<?, ?>>> b;
    private final ArrayList<Entry> c = null;
    private final String d;

    public static class Entry extends AbstractSafeParcelable {
        public static final zzd CREATOR = new zzd();
        final int a;
        final String b;
        final ArrayList<FieldMapPair> c;

        Entry(int i, String str, ArrayList<FieldMapPair> arrayList) {
            this.a = i;
            this.b = str;
            this.c = arrayList;
        }

        Entry(String str, Map<String, Field<?, ?>> map) {
            this.a = 1;
            this.b = str;
            this.c = a(map);
        }

        private static ArrayList<FieldMapPair> a(Map<String, Field<?, ?>> map) {
            if (map == null) {
                return null;
            }
            ArrayList<FieldMapPair> arrayList = new ArrayList<>();
            for (String str : map.keySet()) {
                arrayList.add(new FieldMapPair(str, (Field) map.get(str)));
            }
            return arrayList;
        }

        /* access modifiers changed from: 0000 */
        public HashMap<String, Field<?, ?>> a() {
            HashMap<String, Field<?, ?>> hashMap = new HashMap<>();
            int size = this.c.size();
            for (int i = 0; i < size; i++) {
                FieldMapPair fieldMapPair = (FieldMapPair) this.c.get(i);
                hashMap.put(fieldMapPair.b, fieldMapPair.c);
            }
            return hashMap;
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzd zzd = CREATOR;
            zzd.a(this, parcel, i);
        }
    }

    public static class FieldMapPair extends AbstractSafeParcelable {
        public static final zzb CREATOR = new zzb();
        final int a;
        final String b;
        final Field<?, ?> c;

        FieldMapPair(int i, String str, Field<?, ?> field) {
            this.a = i;
            this.b = str;
            this.c = field;
        }

        FieldMapPair(String str, Field<?, ?> field) {
            this.a = 1;
            this.b = str;
            this.c = field;
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzb zzb = CREATOR;
            zzb.a(this, parcel, i);
        }
    }

    FieldMappingDictionary(int i, ArrayList<Entry> arrayList, String str) {
        this.a = i;
        this.b = a(arrayList);
        this.d = (String) zzac.zzy(str);
        zzawe();
    }

    private static HashMap<String, Map<String, Field<?, ?>>> a(ArrayList<Entry> arrayList) {
        HashMap<String, Map<String, Field<?, ?>>> hashMap = new HashMap<>();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Entry entry = (Entry) arrayList.get(i);
            hashMap.put(entry.b, entry.a());
        }
        return hashMap;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public ArrayList<Entry> b() {
        ArrayList<Entry> arrayList = new ArrayList<>();
        for (String str : this.b.keySet()) {
            arrayList.add(new Entry(str, (Map) this.b.get(str)));
        }
        return arrayList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.b.keySet()) {
            sb.append(str);
            sb.append(":\n");
            Map map = (Map) this.b.get(str);
            for (String str2 : map.keySet()) {
                sb.append("  ");
                sb.append(str2);
                sb.append(": ");
                sb.append(map.get(str2));
            }
        }
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc zzc = CREATOR;
        zzc.a(this, parcel, i);
    }

    public void zzawe() {
        for (String str : this.b.keySet()) {
            Map map = (Map) this.b.get(str);
            for (String str2 : map.keySet()) {
                ((Field) map.get(str2)).zza(this);
            }
        }
    }

    public String zzawg() {
        return this.d;
    }

    public Map<String, Field<?, ?>> zzie(String str) {
        return (Map) this.b.get(str);
    }
}
