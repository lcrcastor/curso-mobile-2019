package com.google.android.gms.tagmanager;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class zzcj {
    private static zzcj a;
    private volatile zza b;
    private volatile String c;
    private volatile String d;
    private volatile String e;

    enum zza {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    zzcj() {
        e();
    }

    static zzcj a() {
        zzcj zzcj;
        synchronized (zzcj.class) {
            if (a == null) {
                a = new zzcj();
            }
            zzcj = a;
        }
        return zzcj;
    }

    private String a(String str) {
        return str.split("&")[0].split("=")[1];
    }

    private String b(Uri uri) {
        return uri.getQuery().replace("&gtm_debug=x", "");
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean a(Uri uri) {
        try {
            String decode = URLDecoder.decode(uri.toString(), "UTF-8");
            if (decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                String str = "Container preview url: ";
                String valueOf = String.valueOf(decode);
                zzbo.v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                this.b = decode.matches(".*?&gtm_debug=x$") ? zza.CONTAINER_DEBUG : zza.CONTAINER;
                this.e = b(uri);
                if (this.b == zza.CONTAINER || this.b == zza.CONTAINER_DEBUG) {
                    String valueOf2 = String.valueOf("/r?");
                    String valueOf3 = String.valueOf(this.e);
                    this.d = valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2);
                }
                this.c = a(this.e);
                return true;
            } else if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                String str2 = "Invalid preview uri: ";
                String valueOf4 = String.valueOf(decode);
                zzbo.zzdf(valueOf4.length() != 0 ? str2.concat(valueOf4) : new String(str2));
                return false;
            } else if (!a(uri.getQuery()).equals(this.c)) {
                return false;
            } else {
                String str3 = "Exit preview mode for container: ";
                String valueOf5 = String.valueOf(this.c);
                zzbo.v(valueOf5.length() != 0 ? str3.concat(valueOf5) : new String(str3));
                this.b = zza.NONE;
                this.d = null;
                return true;
            }
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public zza b() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public String c() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public String d() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        this.b = zza.NONE;
        this.d = null;
        this.c = null;
        this.e = null;
    }
}
