package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

public class zza extends zzd {
    public static boolean au;
    private Info a;
    private final zzal b;
    private String c;
    private boolean d = false;
    private Object e = new Object();

    zza(zzf zzf) {
        super(zzf);
        this.b = new zzal(zzf.zzaan());
    }

    private synchronized Info a() {
        if (this.b.a(1000)) {
            this.b.a();
            Info zzaad = zzaad();
            if (!a(this.a, zzaad)) {
                zzet("Failed to reset client id on adid change. Not using adid");
                zzaad = new Info("", false);
            }
            this.a = zzaad;
        }
        return this.a;
    }

    private static String a(String str) {
        MessageDigest zzfi = zzao.zzfi(CommonUtils.MD5_INSTANCE);
        if (zzfi == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzfi.digest(str.getBytes()))});
    }

    private boolean a(Info info, Info info2) {
        String str = null;
        CharSequence id2 = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id2)) {
            return true;
        }
        String zzacm = zzaat().zzacm();
        synchronized (this.e) {
            if (!this.d) {
                this.c = zzaae();
                this.d = true;
            } else if (TextUtils.isEmpty(this.c)) {
                if (info != null) {
                    str = info.getId();
                }
                if (str == null) {
                    String valueOf = String.valueOf(id2);
                    String valueOf2 = String.valueOf(zzacm);
                    boolean b2 = b(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
                    return b2;
                }
                String valueOf3 = String.valueOf(str);
                String valueOf4 = String.valueOf(zzacm);
                this.c = a(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3));
            }
            String valueOf5 = String.valueOf(id2);
            String valueOf6 = String.valueOf(zzacm);
            String a2 = a(valueOf6.length() != 0 ? valueOf5.concat(valueOf6) : new String(valueOf5));
            if (TextUtils.isEmpty(a2)) {
                return false;
            }
            if (a2.equals(this.c)) {
                return true;
            }
            if (!TextUtils.isEmpty(this.c)) {
                zzep("Resetting the client id because Advertising Id changed.");
                zzacm = zzaat().a();
                zza("New client Id", zzacm);
            }
            String valueOf7 = String.valueOf(id2);
            String valueOf8 = String.valueOf(zzacm);
            boolean b3 = b(valueOf8.length() != 0 ? valueOf7.concat(valueOf8) : new String(valueOf7));
            return b3;
        }
    }

    private boolean b(String str) {
        try {
            String a2 = a(str);
            zzep("Storing hashed adid.");
            FileOutputStream openFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            openFileOutput.write(a2.getBytes());
            openFileOutput.close();
            this.c = a2;
            return true;
        } catch (IOException e2) {
            zze("Error creating hash file", e2);
            return false;
        }
    }

    public String zzaab() {
        zzaax();
        Info a2 = a();
        String id2 = a2 != null ? a2.getId() : null;
        if (TextUtils.isEmpty(id2)) {
            return null;
        }
        return id2;
    }

    /* access modifiers changed from: protected */
    public Info zzaad() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException unused) {
            zzes("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Throwable th) {
            if (!au) {
                au = true;
                zzd("Error getting advertiser id", th);
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public String zzaae() {
        String str = null;
        try {
            FileInputStream openFileInput = getContext().openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int read = openFileInput.read(bArr, 0, 128);
            if (openFileInput.available() > 0) {
                zzes("Hash file seems corrupted, deleting it.");
                openFileInput.close();
                getContext().deleteFile("gaClientIdData");
                return null;
            } else if (read <= 0) {
                zzep("Hash file is empty.");
                openFileInput.close();
                return null;
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                } catch (FileNotFoundException unused) {
                } catch (IOException e2) {
                    e = e2;
                    str = str2;
                    zzd("Error reading Hash file, deleting it", e);
                    getContext().deleteFile("gaClientIdData");
                    return str;
                }
                return str2;
            }
        } catch (FileNotFoundException unused2) {
            return str;
        } catch (IOException e3) {
            e = e3;
            zzd("Error reading Hash file, deleting it", e);
            getContext().deleteFile("gaClientIdData");
            return str;
        }
    }

    /* access modifiers changed from: protected */
    public void zzym() {
    }

    public boolean zzzq() {
        zzaax();
        Info a2 = a();
        if (a2 != null) {
            return !a2.isLimitAdTrackingEnabled();
        }
        return false;
    }
}
