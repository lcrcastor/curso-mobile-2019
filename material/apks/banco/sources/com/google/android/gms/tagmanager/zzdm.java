package com.google.android.gms.tagmanager;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import com.google.android.gms.internal.zzai.zza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzdm {
    private static final Object a = null;
    private static Long b = new Long(0);
    private static Double c = new Double(0.0d);
    private static zzdl d = zzdl.a(0);
    private static String e = new String("");
    private static Boolean f = new Boolean(false);
    private static List<Object> g = new ArrayList(0);
    private static Map<Object, Object> h = new HashMap();
    private static zza i = zzat(e);

    private static zzdl a(String str) {
        try {
            return zzdl.a(str);
        } catch (NumberFormatException unused) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33);
            sb.append("Failed to convert '");
            sb.append(str);
            sb.append("' to a number.");
            zzbo.e(sb.toString());
            return d;
        }
    }

    private static boolean a(Object obj) {
        return (obj instanceof Double) || (obj instanceof Float) || ((obj instanceof zzdl) && ((zzdl) obj).a());
    }

    private static double b(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        zzbo.e("getDouble received non-Number");
        return 0.0d;
    }

    private static Long b(String str) {
        zzdl a2 = a(str);
        return a2 == d ? b : Long.valueOf(a2.longValue());
    }

    private static Double c(String str) {
        zzdl a2 = a(str);
        return a2 == d ? c : Double.valueOf(a2.doubleValue());
    }

    private static boolean c(Object obj) {
        return (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || ((obj instanceof zzdl) && ((zzdl) obj).b());
    }

    private static long d(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        zzbo.e("getInt64 received non-Number");
        return 0;
    }

    private static Boolean d(String str) {
        return "true".equalsIgnoreCase(str) ? Boolean.TRUE : Reintento.Reintento_Falso.equalsIgnoreCase(str) ? Boolean.FALSE : f;
    }

    public static String zzao(Object obj) {
        return obj == null ? e : obj.toString();
    }

    public static zzdl zzap(Object obj) {
        return obj instanceof zzdl ? (zzdl) obj : c(obj) ? zzdl.a(d(obj)) : a(obj) ? zzdl.a(Double.valueOf(b(obj))) : a(zzao(obj));
    }

    public static Long zzaq(Object obj) {
        return c(obj) ? Long.valueOf(d(obj)) : b(zzao(obj));
    }

    public static Double zzar(Object obj) {
        return a(obj) ? Double.valueOf(b(obj)) : c(zzao(obj));
    }

    public static Boolean zzas(Object obj) {
        return obj instanceof Boolean ? (Boolean) obj : d(zzao(obj));
    }

    public static zza zzat(Object obj) {
        String obj2;
        zza zza = new zza();
        if (obj instanceof zza) {
            return (zza) obj;
        }
        boolean z = false;
        if (obj instanceof String) {
            zza.type = 1;
            obj2 = (String) obj;
        } else {
            if (obj instanceof List) {
                zza.type = 2;
                List<Object> list = (List) obj;
                ArrayList arrayList = new ArrayList(list.size());
                boolean z2 = false;
                for (Object zzat : list) {
                    zza zzat2 = zzat(zzat);
                    if (zzat2 == i) {
                        return i;
                    }
                    z2 = z2 || zzat2.zzyd;
                    arrayList.add(zzat2);
                }
                zza.zzxu = (zza[]) arrayList.toArray(new zza[0]);
                z = z2;
            } else if (obj instanceof Map) {
                zza.type = 3;
                Set<Entry> entrySet = ((Map) obj).entrySet();
                ArrayList arrayList2 = new ArrayList(entrySet.size());
                ArrayList arrayList3 = new ArrayList(entrySet.size());
                boolean z3 = false;
                for (Entry entry : entrySet) {
                    zza zzat3 = zzat(entry.getKey());
                    zza zzat4 = zzat(entry.getValue());
                    if (zzat3 == i || zzat4 == i) {
                        return i;
                    }
                    z3 = z3 || zzat3.zzyd || zzat4.zzyd;
                    arrayList2.add(zzat3);
                    arrayList3.add(zzat4);
                }
                zza.zzxv = (zza[]) arrayList2.toArray(new zza[0]);
                zza.zzxw = (zza[]) arrayList3.toArray(new zza[0]);
                z = z3;
            } else if (a(obj)) {
                zza.type = 1;
                obj2 = obj.toString();
            } else if (c(obj)) {
                zza.type = 6;
                zza.zzxz = d(obj);
            } else if (obj instanceof Boolean) {
                zza.type = 8;
                zza.zzya = ((Boolean) obj).booleanValue();
            } else {
                String str = "Converting to Value from unknown object type: ";
                String valueOf = String.valueOf(obj == null ? "null" : obj.getClass().toString());
                zzbo.e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                return i;
            }
            zza.zzyd = z;
            return zza;
        }
        zza.string = obj2;
        zza.zzyd = z;
        return zza;
    }

    public static Object zzchf() {
        return null;
    }

    public static Long zzchg() {
        return b;
    }

    public static Double zzchh() {
        return c;
    }

    public static Boolean zzchi() {
        return f;
    }

    public static zzdl zzchj() {
        return d;
    }

    public static String zzchk() {
        return e;
    }

    public static zza zzchl() {
        return i;
    }

    public static String zzg(zza zza) {
        return zzao(zzl(zza));
    }

    public static zzdl zzh(zza zza) {
        return zzap(zzl(zza));
    }

    public static Long zzi(zza zza) {
        return zzaq(zzl(zza));
    }

    public static Double zzj(zza zza) {
        return zzar(zzl(zza));
    }

    public static Boolean zzk(zza zza) {
        return zzas(zzl(zza));
    }

    public static Object zzl(zza zza) {
        String str;
        if (zza == null) {
            return null;
        }
        int i2 = 0;
        switch (zza.type) {
            case 1:
                return zza.string;
            case 2:
                ArrayList arrayList = new ArrayList(zza.zzxu.length);
                zza[] zzaArr = zza.zzxu;
                int length = zzaArr.length;
                while (i2 < length) {
                    Object zzl = zzl(zzaArr[i2]);
                    if (zzl == null) {
                        return null;
                    }
                    arrayList.add(zzl);
                    i2++;
                }
                return arrayList;
            case 3:
                if (zza.zzxv.length != zza.zzxw.length) {
                    String str2 = "Converting an invalid value to object: ";
                    String valueOf = String.valueOf(zza.toString());
                    zzbo.e(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    return null;
                }
                HashMap hashMap = new HashMap(zza.zzxw.length);
                while (i2 < zza.zzxv.length) {
                    Object zzl2 = zzl(zza.zzxv[i2]);
                    Object zzl3 = zzl(zza.zzxw[i2]);
                    if (zzl2 == null || zzl3 == null) {
                        return null;
                    }
                    hashMap.put(zzl2, zzl3);
                    i2++;
                }
                return hashMap;
            case 4:
                str = "Trying to convert a macro reference to object";
                break;
            case 5:
                str = "Trying to convert a function id to object";
                break;
            case 6:
                return Long.valueOf(zza.zzxz);
            case 7:
                StringBuffer stringBuffer = new StringBuffer();
                zza[] zzaArr2 = zza.zzyb;
                int length2 = zzaArr2.length;
                while (i2 < length2) {
                    String zzg = zzg(zzaArr2[i2]);
                    if (zzg == e) {
                        return null;
                    }
                    stringBuffer.append(zzg);
                    i2++;
                }
                return stringBuffer.toString();
            case 8:
                return Boolean.valueOf(zza.zzya);
            default:
                int i3 = zza.type;
                StringBuilder sb = new StringBuilder(46);
                sb.append("Failed to convert a value of type: ");
                sb.append(i3);
                str = sb.toString();
                break;
        }
        zzbo.e(str);
        return null;
    }

    public static zza zzpy(String str) {
        zza zza = new zza();
        zza.type = 5;
        zza.zzxy = str;
        return zza;
    }
}
