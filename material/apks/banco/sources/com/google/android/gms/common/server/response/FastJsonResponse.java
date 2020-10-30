package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.common.util.zzq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FastJsonResponse {

    public static class Field<I, O> extends AbstractSafeParcelable {
        public static final zza CREATOR = new zza();
        protected final String DA;
        protected final int Dt;
        protected final boolean Du;
        protected final int Dv;
        protected final boolean Dw;
        protected final String Dx;
        protected final int Dy;
        protected final Class<? extends FastJsonResponse> Dz;
        private final int a;
        private FieldMappingDictionary b;
        /* access modifiers changed from: private */
        public zza<I, O> c;

        Field(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, ConverterWrapper converterWrapper) {
            this.a = i;
            this.Dt = i2;
            this.Du = z;
            this.Dv = i3;
            this.Dw = z2;
            this.Dx = str;
            this.Dy = i4;
            zza<I, O> zza = null;
            if (str2 == null) {
                this.Dz = null;
                this.DA = null;
            } else {
                this.Dz = SafeParcelResponse.class;
                this.DA = str2;
            }
            if (converterWrapper != null) {
                zza = converterWrapper.zzavo();
            }
            this.c = zza;
        }

        protected Field(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends FastJsonResponse> cls, zza<I, O> zza) {
            this.a = 1;
            this.Dt = i;
            this.Du = z;
            this.Dv = i2;
            this.Dw = z2;
            this.Dx = str;
            this.Dy = i3;
            this.Dz = cls;
            this.DA = cls == null ? null : cls.getCanonicalName();
            this.c = zza;
        }

        public static Field zza(String str, int i, zza<?, ?> zza, boolean z) {
            Field field = new Field(zza.zzavq(), z, zza.zzavr(), false, str, i, null, zza);
            return field;
        }

        public static <T extends FastJsonResponse> Field<T, T> zza(String str, int i, Class<T> cls) {
            Field field = new Field(11, false, 11, false, str, i, cls, null);
            return field;
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> zzb(String str, int i, Class<T> cls) {
            Field field = new Field(11, true, 11, true, str, i, cls, null);
            return field;
        }

        public static Field<Integer, Integer> zzk(String str, int i) {
            Field field = new Field(0, false, 0, false, str, i, null, null);
            return field;
        }

        public static Field<Boolean, Boolean> zzl(String str, int i) {
            Field field = new Field(6, false, 6, false, str, i, null, null);
            return field;
        }

        public static Field<String, String> zzm(String str, int i) {
            Field field = new Field(7, false, 7, false, str, i, null, null);
            return field;
        }

        /* access modifiers changed from: 0000 */
        public String a() {
            if (this.DA == null) {
                return null;
            }
            return this.DA;
        }

        /* access modifiers changed from: 0000 */
        public ConverterWrapper b() {
            if (this.c == null) {
                return null;
            }
            return ConverterWrapper.zza(this.c);
        }

        public I convertBack(O o) {
            return this.c.convertBack(o);
        }

        public int getVersionCode() {
            return this.a;
        }

        public String toString() {
            com.google.android.gms.common.internal.zzab.zza zzg = zzab.zzx(this).zzg("versionCode", Integer.valueOf(this.a)).zzg("typeIn", Integer.valueOf(this.Dt)).zzg("typeInArray", Boolean.valueOf(this.Du)).zzg("typeOut", Integer.valueOf(this.Dv)).zzg("typeOutArray", Boolean.valueOf(this.Dw)).zzg("outputFieldName", this.Dx).zzg("safeParcelFieldId", Integer.valueOf(this.Dy)).zzg("concreteTypeName", a());
            Class zzavz = zzavz();
            if (zzavz != null) {
                zzg.zzg("concreteType.class", zzavz.getCanonicalName());
            }
            if (this.c != null) {
                zzg.zzg("converterName", this.c.getClass().getCanonicalName());
            }
            return zzg.toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            zza zza = CREATOR;
            zza.a(this, parcel, i);
        }

        public void zza(FieldMappingDictionary fieldMappingDictionary) {
            this.b = fieldMappingDictionary;
        }

        public int zzavq() {
            return this.Dt;
        }

        public int zzavr() {
            return this.Dv;
        }

        public boolean zzavv() {
            return this.Du;
        }

        public boolean zzavw() {
            return this.Dw;
        }

        public String zzavx() {
            return this.Dx;
        }

        public int zzavy() {
            return this.Dy;
        }

        public Class<? extends FastJsonResponse> zzavz() {
            return this.Dz;
        }

        public boolean zzawb() {
            return this.c != null;
        }

        public Map<String, Field<?, ?>> zzawd() {
            zzac.zzy(this.DA);
            zzac.zzy(this.b);
            return this.b.zzie(this.DA);
        }
    }

    public interface zza<I, O> {
        I convertBack(O o);

        int zzavq();

        int zzavr();
    }

    private void a(StringBuilder sb, Field field, Object obj) {
        String str;
        if (field.zzavq() == 11) {
            str = ((FastJsonResponse) field.zzavz().cast(obj)).toString();
        } else if (field.zzavq() == 7) {
            sb.append("\"");
            sb.append(zzp.zzii((String) obj));
            str = "\"";
        } else {
            sb.append(obj);
            return;
        }
        sb.append(str);
    }

    private void a(StringBuilder sb, Field field, ArrayList<Object> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                a(sb, field, obj);
            }
        }
        sb.append("]");
    }

    public String toString() {
        String str;
        String str2;
        Map zzavs = zzavs();
        StringBuilder sb = new StringBuilder(100);
        for (String str3 : zzavs.keySet()) {
            Field field = (Field) zzavs.get(str3);
            if (zza(field)) {
                Object zza2 = zza(field, zzb(field));
                sb.append(sb.length() == 0 ? "{" : ",");
                sb.append("\"");
                sb.append(str3);
                sb.append("\":");
                if (zza2 == null) {
                    str2 = "null";
                } else {
                    switch (field.zzavr()) {
                        case 8:
                            sb.append("\"");
                            str = zzc.zzp((byte[]) zza2);
                            break;
                        case 9:
                            sb.append("\"");
                            str = zzc.zzq((byte[]) zza2);
                            break;
                        case 10:
                            zzq.zza(sb, (HashMap) zza2);
                            continue;
                        default:
                            if (!field.zzavv()) {
                                a(sb, field, zza2);
                                break;
                            } else {
                                a(sb, field, (ArrayList) zza2);
                                continue;
                            }
                    }
                    sb.append(str);
                    str2 = "\"";
                }
                sb.append(str2);
            }
        }
        sb.append(sb.length() > 0 ? "}" : "{}");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public <O, I> I zza(Field<I, O> field, Object obj) {
        return field.c != null ? field.convertBack(obj) : obj;
    }

    /* access modifiers changed from: protected */
    public boolean zza(Field field) {
        return field.zzavr() == 11 ? field.zzavw() ? zzid(field.zzavx()) : zzic(field.zzavx()) : zzib(field.zzavx());
    }

    public abstract Map<String, Field<?, ?>> zzavs();

    public HashMap<String, Object> zzavt() {
        return null;
    }

    public HashMap<String, Object> zzavu() {
        return null;
    }

    /* access modifiers changed from: protected */
    public Object zzb(Field field) {
        String zzavx = field.zzavx();
        if (field.zzavz() == null) {
            return zzia(field.zzavx());
        }
        zzac.zza(zzia(field.zzavx()) == null, "Concrete field shouldn't be value object: %s", field.zzavx());
        HashMap zzavu = field.zzavw() ? zzavu() : zzavt();
        if (zzavu != null) {
            return zzavu.get(zzavx);
        }
        try {
            char upperCase = Character.toUpperCase(zzavx.charAt(0));
            String valueOf = String.valueOf(zzavx.substring(1));
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 4);
            sb.append("get");
            sb.append(upperCase);
            sb.append(valueOf);
            return getClass().getMethod(sb.toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object zzia(String str);

    /* access modifiers changed from: protected */
    public abstract boolean zzib(String str);

    /* access modifiers changed from: protected */
    public boolean zzic(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    /* access modifiers changed from: protected */
    public boolean zzid(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }
}
