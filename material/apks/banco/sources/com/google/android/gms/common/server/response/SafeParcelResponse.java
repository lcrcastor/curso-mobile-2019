package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.common.util.zzq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SafeParcelResponse extends FastSafeParcelableJsonResponse {
    public static final zze CREATOR = new zze();
    private final int a;
    private final Parcel b;
    private final int c = 2;
    private final FieldMappingDictionary d;
    private final String e;
    private int f;
    private int g;

    SafeParcelResponse(int i, Parcel parcel, FieldMappingDictionary fieldMappingDictionary) {
        this.a = i;
        this.b = (Parcel) zzac.zzy(parcel);
        this.d = fieldMappingDictionary;
        this.e = this.d == null ? null : this.d.zzawg();
        this.f = 2;
    }

    private static SparseArray<Entry<String, Field<?, ?>>> a(Map<String, Field<?, ?>> map) {
        SparseArray<Entry<String, Field<?, ?>>> sparseArray = new SparseArray<>();
        for (Entry entry : map.entrySet()) {
            sparseArray.put(((Field) entry.getValue()).zzavy(), entry);
        }
        return sparseArray;
    }

    private void a(StringBuilder sb, int i, Object obj) {
        String str;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"");
                str = zzp.zzii(obj.toString());
                break;
            case 8:
                sb.append("\"");
                str = zzc.zzp((byte[]) obj);
                break;
            case 9:
                sb.append("\"");
                str = zzc.zzq((byte[]) obj);
                break;
            case 10:
                zzq.zza(sb, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                StringBuilder sb2 = new StringBuilder(26);
                sb2.append("Unknown type = ");
                sb2.append(i);
                throw new IllegalArgumentException(sb2.toString());
        }
        sb.append(str);
        sb.append("\"");
    }

    private void a(StringBuilder sb, Field<?, ?> field, Parcel parcel, int i) {
        Object obj;
        switch (field.zzavr()) {
            case 0:
                obj = Integer.valueOf(zza.zzg(parcel, i));
                break;
            case 1:
                obj = zza.zzk(parcel, i);
                break;
            case 2:
                obj = Long.valueOf(zza.zzi(parcel, i));
                break;
            case 3:
                obj = Float.valueOf(zza.zzl(parcel, i));
                break;
            case 4:
                obj = Double.valueOf(zza.zzn(parcel, i));
                break;
            case 5:
                obj = zza.zzp(parcel, i);
                break;
            case 6:
                obj = Boolean.valueOf(zza.zzc(parcel, i));
                break;
            case 7:
                obj = zza.zzq(parcel, i);
                break;
            case 8:
            case 9:
                obj = zza.zzt(parcel, i);
                break;
            case 10:
                obj = zzq(zza.zzs(parcel, i));
                break;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                int zzavr = field.zzavr();
                StringBuilder sb2 = new StringBuilder(36);
                sb2.append("Unknown field out type = ");
                sb2.append(zzavr);
                throw new IllegalArgumentException(sb2.toString());
        }
        a(sb, field, zza(field, obj));
    }

    private void a(StringBuilder sb, Field<?, ?> field, Object obj) {
        if (field.zzavv()) {
            a(sb, field, (ArrayList) obj);
        } else {
            a(sb, field.zzavq(), obj);
        }
    }

    private void a(StringBuilder sb, Field<?, ?> field, ArrayList<?> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            a(sb, field.zzavq(), arrayList.get(i));
        }
        sb.append("]");
    }

    private void a(StringBuilder sb, String str, Field<?, ?> field, Parcel parcel, int i) {
        sb.append("\"");
        sb.append(str);
        sb.append("\":");
        if (field.zzawb()) {
            a(sb, field, parcel, i);
        } else {
            b(sb, field, parcel, i);
        }
    }

    private void a(StringBuilder sb, Map<String, Field<?, ?>> map, Parcel parcel) {
        SparseArray a2 = a(map);
        sb.append('{');
        int zzcq = zza.zzcq(parcel);
        boolean z = false;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = zza.zzcp(parcel);
            Entry entry = (Entry) a2.get(zza.zzgv(zzcp));
            if (entry != null) {
                if (z) {
                    sb.append(",");
                }
                a(sb, (String) entry.getKey(), (Field) entry.getValue(), parcel, zzcp);
                z = true;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb2 = new StringBuilder(37);
            sb2.append("Overread allowed size end=");
            sb2.append(zzcq);
            throw new C0004zza(sb2.toString(), parcel);
        }
        sb.append('}');
    }

    /* JADX INFO: used method not loaded: com.google.android.gms.common.util.zzb.zza(java.lang.StringBuilder, java.lang.Object[]):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0074, code lost:
        com.google.android.gms.common.util.zzb.zza(r5, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007f, code lost:
        r6 = "]";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x011e, code lost:
        r5.append(r6);
        r6 = "\"";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0150, code lost:
        r5.append(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0153, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.lang.StringBuilder r5, com.google.android.gms.common.server.response.FastJsonResponse.Field<?, ?> r6, android.os.Parcel r7, int r8) {
        /*
            r4 = this;
            boolean r0 = r6.zzavw()
            r1 = 0
            if (r0 == 0) goto L_0x0083
            java.lang.String r0 = "["
            r5.append(r0)
            int r0 = r6.zzavr()
            switch(r0) {
                case 0: goto L_0x0078;
                case 1: goto L_0x0070;
                case 2: goto L_0x0068;
                case 3: goto L_0x0060;
                case 4: goto L_0x0058;
                case 5: goto L_0x0053;
                case 6: goto L_0x004b;
                case 7: goto L_0x0043;
                case 8: goto L_0x003b;
                case 9: goto L_0x003b;
                case 10: goto L_0x003b;
                case 11: goto L_0x001b;
                default: goto L_0x0013;
            }
        L_0x0013:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Unknown field type out."
            r5.<init>(r6)
            throw r5
        L_0x001b:
            android.os.Parcel[] r7 = com.google.android.gms.common.internal.safeparcel.zza.zzag(r7, r8)
            int r8 = r7.length
            r0 = 0
        L_0x0021:
            if (r0 >= r8) goto L_0x007f
            if (r0 <= 0) goto L_0x002a
            java.lang.String r2 = ","
            r5.append(r2)
        L_0x002a:
            r2 = r7[r0]
            r2.setDataPosition(r1)
            java.util.Map r2 = r6.zzawd()
            r3 = r7[r0]
            r4.a(r5, r2, r3)
            int r0 = r0 + 1
            goto L_0x0021
        L_0x003b:
            java.lang.UnsupportedOperationException r5 = new java.lang.UnsupportedOperationException
            java.lang.String r6 = "List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported"
            r5.<init>(r6)
            throw r5
        L_0x0043:
            java.lang.String[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzac(r7, r8)
            com.google.android.gms.common.util.zzb.zza(r5, r6)
            goto L_0x007f
        L_0x004b:
            boolean[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzv(r7, r8)
            com.google.android.gms.common.util.zzb.zza(r5, r6)
            goto L_0x007f
        L_0x0053:
            java.math.BigDecimal[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzab(r7, r8)
            goto L_0x0074
        L_0x0058:
            double[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzaa(r7, r8)
            com.google.android.gms.common.util.zzb.zza(r5, r6)
            goto L_0x007f
        L_0x0060:
            float[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzz(r7, r8)
            com.google.android.gms.common.util.zzb.zza(r5, r6)
            goto L_0x007f
        L_0x0068:
            long[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzx(r7, r8)
            com.google.android.gms.common.util.zzb.zza(r5, r6)
            goto L_0x007f
        L_0x0070:
            java.math.BigInteger[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzy(r7, r8)
        L_0x0074:
            com.google.android.gms.common.util.zzb.zza(r5, (T[]) r6)
            goto L_0x007f
        L_0x0078:
            int[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzw(r7, r8)
            com.google.android.gms.common.util.zzb.zza(r5, r6)
        L_0x007f:
            java.lang.String r6 = "]"
            goto L_0x0123
        L_0x0083:
            int r0 = r6.zzavr()
            switch(r0) {
                case 0: goto L_0x0154;
                case 1: goto L_0x014c;
                case 2: goto L_0x0144;
                case 3: goto L_0x013c;
                case 4: goto L_0x0134;
                case 5: goto L_0x012f;
                case 6: goto L_0x0127;
                case 7: goto L_0x0111;
                case 8: goto L_0x0103;
                case 9: goto L_0x00f5;
                case 10: goto L_0x00a1;
                case 11: goto L_0x0092;
                default: goto L_0x008a;
            }
        L_0x008a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Unknown field type out"
            r5.<init>(r6)
            throw r5
        L_0x0092:
            android.os.Parcel r7 = com.google.android.gms.common.internal.safeparcel.zza.zzaf(r7, r8)
            r7.setDataPosition(r1)
            java.util.Map r6 = r6.zzawd()
            r4.a(r5, r6, r7)
            return
        L_0x00a1:
            android.os.Bundle r6 = com.google.android.gms.common.internal.safeparcel.zza.zzs(r7, r8)
            java.util.Set r7 = r6.keySet()
            r7.size()
            java.lang.String r8 = "{"
            r5.append(r8)
            java.util.Iterator r7 = r7.iterator()
            r8 = 1
        L_0x00b6:
            boolean r0 = r7.hasNext()
            if (r0 == 0) goto L_0x00f2
            java.lang.Object r0 = r7.next()
            java.lang.String r0 = (java.lang.String) r0
            if (r8 != 0) goto L_0x00c9
            java.lang.String r8 = ","
            r5.append(r8)
        L_0x00c9:
            java.lang.String r8 = "\""
            r5.append(r8)
            r5.append(r0)
            java.lang.String r8 = "\""
            r5.append(r8)
            java.lang.String r8 = ":"
            r5.append(r8)
            java.lang.String r8 = "\""
            r5.append(r8)
            java.lang.String r8 = r6.getString(r0)
            java.lang.String r8 = com.google.android.gms.common.util.zzp.zzii(r8)
            r5.append(r8)
            java.lang.String r8 = "\""
            r5.append(r8)
            r8 = 0
            goto L_0x00b6
        L_0x00f2:
            java.lang.String r6 = "}"
            goto L_0x0123
        L_0x00f5:
            byte[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzt(r7, r8)
            java.lang.String r7 = "\""
            r5.append(r7)
            java.lang.String r6 = com.google.android.gms.common.util.zzc.zzq(r6)
            goto L_0x011e
        L_0x0103:
            byte[] r6 = com.google.android.gms.common.internal.safeparcel.zza.zzt(r7, r8)
            java.lang.String r7 = "\""
            r5.append(r7)
            java.lang.String r6 = com.google.android.gms.common.util.zzc.zzp(r6)
            goto L_0x011e
        L_0x0111:
            java.lang.String r6 = com.google.android.gms.common.internal.safeparcel.zza.zzq(r7, r8)
            java.lang.String r7 = "\""
            r5.append(r7)
            java.lang.String r6 = com.google.android.gms.common.util.zzp.zzii(r6)
        L_0x011e:
            r5.append(r6)
            java.lang.String r6 = "\""
        L_0x0123:
            r5.append(r6)
            return
        L_0x0127:
            boolean r6 = com.google.android.gms.common.internal.safeparcel.zza.zzc(r7, r8)
            r5.append(r6)
            return
        L_0x012f:
            java.math.BigDecimal r6 = com.google.android.gms.common.internal.safeparcel.zza.zzp(r7, r8)
            goto L_0x0150
        L_0x0134:
            double r6 = com.google.android.gms.common.internal.safeparcel.zza.zzn(r7, r8)
            r5.append(r6)
            return
        L_0x013c:
            float r6 = com.google.android.gms.common.internal.safeparcel.zza.zzl(r7, r8)
            r5.append(r6)
            return
        L_0x0144:
            long r6 = com.google.android.gms.common.internal.safeparcel.zza.zzi(r7, r8)
            r5.append(r6)
            return
        L_0x014c:
            java.math.BigInteger r6 = com.google.android.gms.common.internal.safeparcel.zza.zzk(r7, r8)
        L_0x0150:
            r5.append(r6)
            return
        L_0x0154:
            int r6 = com.google.android.gms.common.internal.safeparcel.zza.zzg(r7, r8)
            r5.append(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.SafeParcelResponse.b(java.lang.StringBuilder, com.google.android.gms.common.server.response.FastJsonResponse$Field, android.os.Parcel, int):void");
    }

    public static HashMap<String, String> zzq(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    /* access modifiers changed from: 0000 */
    public FieldMappingDictionary a() {
        switch (this.c) {
            case 0:
                return null;
            case 1:
                return this.d;
            case 2:
                return this.d;
            default:
                int i = this.c;
                StringBuilder sb = new StringBuilder(34);
                sb.append("Invalid creation type: ");
                sb.append(i);
                throw new IllegalStateException(sb.toString());
        }
    }

    public int getVersionCode() {
        return this.a;
    }

    public String toString() {
        zzac.zzb(this.d, (Object) "Cannot convert to JSON on client side.");
        Parcel zzawi = zzawi();
        zzawi.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        a(sb, this.d.zzie(this.e), zzawi);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze zze = CREATOR;
        zze.a(this, parcel, i);
    }

    public Map<String, Field<?, ?>> zzavs() {
        if (this.d == null) {
            return null;
        }
        return this.d.zzie(this.e);
    }

    public Parcel zzawi() {
        switch (this.f) {
            case 0:
                this.g = zzb.zzcr(this.b);
                break;
            case 1:
                break;
        }
        zzb.zzaj(this.b, this.g);
        this.f = 2;
        return this.b;
    }

    public Object zzia(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public boolean zzib(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }
}
