package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafg.zzc;
import com.google.android.gms.internal.zzafg.zzd;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

class zzbh {
    public static zzc a(String str) {
        zza b = b(new JSONObject(str));
        zzd zzckv = zzc.zzckv();
        for (int i = 0; i < b.zzxv.length; i++) {
            zzckv.zzc(zzafg.zza.zzckt().zzb(zzag.INSTANCE_NAME.toString(), b.zzxv[i]).zzb(zzag.FUNCTION.toString(), zzdm.zzpy(zzn.a())).zzb(zzn.b(), b.zzxw[i]).zzcku());
        }
        return zzckv.zzckx();
    }

    static Object a(Object obj) {
        if (obj instanceof JSONArray) {
            throw new RuntimeException("JSONArrays are not supported");
        } else if (JSONObject.NULL.equals(obj)) {
            throw new RuntimeException("JSON nulls are not supported");
        } else if (!(obj instanceof JSONObject)) {
            return obj;
        } else {
            JSONObject jSONObject = (JSONObject) obj;
            HashMap hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                hashMap.put(str, a(jSONObject.get(str)));
            }
            return hashMap;
        }
    }

    private static zza b(Object obj) {
        return zzdm.zzat(a(obj));
    }
}
