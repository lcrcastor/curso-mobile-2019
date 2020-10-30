package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzx;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class zzd {
    SharedPreferences a;
    Context b;

    public zzd(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    public zzd(Context context, String str) {
        this.b = context;
        this.a = context.getSharedPreferences(str, 4);
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("-no-backup");
        d(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    private String a(String str, String str2, String str3) {
        String valueOf = String.valueOf("|T|");
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(valueOf).length() + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append(valueOf);
        sb.append(str2);
        sb.append("|");
        sb.append(str3);
        return sb.toString();
    }

    private void d(String str) {
        File file = new File(zzx.getNoBackupFilesDir(this.b), str);
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i("InstanceID/Store", "App restored, clearing state");
                    InstanceIDListenerService.a(this.b, this);
                }
            } catch (IOException e) {
                if (Log.isLoggable("InstanceID/Store", 3)) {
                    String str2 = "InstanceID/Store";
                    String str3 = "Error creating file in no backup dir: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized String a(String str) {
        return this.a.getString(str, null);
    }

    /* access modifiers changed from: 0000 */
    public synchronized String a(String str, String str2) {
        SharedPreferences sharedPreferences;
        StringBuilder sb;
        sharedPreferences = this.a;
        String valueOf = String.valueOf("|S|");
        sb = new StringBuilder(String.valueOf(str).length() + 0 + String.valueOf(valueOf).length() + String.valueOf(str2).length());
        sb.append(str);
        sb.append(valueOf);
        sb.append(str2);
        return sharedPreferences.getString(sb.toString(), null);
    }

    /* access modifiers changed from: 0000 */
    public synchronized KeyPair a(String str, long j) {
        KeyPair zzboo;
        zzboo = zza.zzboo();
        Editor edit = this.a.edit();
        a(edit, str, "|P|", InstanceID.a(zzboo.getPublic().getEncoded()));
        a(edit, str, "|K|", InstanceID.a(zzboo.getPrivate().getEncoded()));
        a(edit, str, "cre", Long.toString(j));
        edit.commit();
        return zzboo;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(Editor editor, String str, String str2, String str3) {
        String valueOf = String.valueOf("|S|");
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 0 + String.valueOf(valueOf).length() + String.valueOf(str2).length());
        sb.append(str);
        sb.append(valueOf);
        sb.append(str2);
        editor.putString(sb.toString(), str3);
    }

    /* access modifiers changed from: 0000 */
    public void b(String str) {
        zzkr(String.valueOf(str).concat("|"));
    }

    /* access modifiers changed from: 0000 */
    public KeyPair c(String str) {
        String a2 = a(str, "|P|");
        String a3 = a(str, "|K|");
        if (a2 == null || a3 == null) {
            return null;
        }
        try {
            byte[] decode = Base64.decode(a2, 8);
            byte[] decode2 = Base64.decode(a3, 8);
            KeyFactory instance = KeyFactory.getInstance("RSA");
            return new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
            sb.append("Invalid key stored ");
            sb.append(valueOf);
            Log.w("InstanceID/Store", sb.toString());
            InstanceIDListenerService.a(this.b, this);
            return null;
        }
    }

    public boolean isEmpty() {
        return this.a.getAll().isEmpty();
    }

    public synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String a2 = a(str, str2, str3);
        Editor edit = this.a.edit();
        edit.putString(a2, str4);
        edit.putString("appVersion", str5);
        edit.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000));
        edit.commit();
    }

    public synchronized void zzbow() {
        this.a.edit().clear().commit();
    }

    public synchronized String zzh(String str, String str2, String str3) {
        return this.a.getString(a(str, str2, str3), null);
    }

    public synchronized void zzi(String str, String str2, String str3) {
        String a2 = a(str, str2, str3);
        Editor edit = this.a.edit();
        edit.remove(a2);
        edit.commit();
    }

    public synchronized void zzkr(String str) {
        Editor edit = this.a.edit();
        for (String str2 : this.a.getAll().keySet()) {
            if (str2.startsWith(str)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }

    public KeyPair zzks(String str) {
        return c(str);
    }

    public void zzku(String str) {
        zzkr(String.valueOf(str).concat("|T|"));
    }
}
