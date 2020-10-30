package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.zzc;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import java.lang.reflect.Field;

public final class zzsu {
    public static final zzb OA = new zzb() {
        public C0030zzb zza(Context context, String str, zza zza) {
            int i;
            C0030zzb zzb = new C0030zzb();
            zzb.OF = zza.zzaa(context, str);
            zzb.OG = zza.zzc(context, str, true);
            if (zzb.OF == 0 && zzb.OG == 0) {
                i = 0;
            } else if (zzb.OF >= zzb.OG) {
                i = -1;
            } else {
                zzb.OH = 1;
                return zzb;
            }
            zzb.OH = i;
            return zzb;
        }
    };
    public static final zzb OB = new zzb() {
        public C0030zzb zza(Context context, String str, zza zza) {
            int i;
            C0030zzb zzb = new C0030zzb();
            zzb.OF = zza.zzaa(context, str);
            zzb.OG = zza.zzc(context, str, true);
            if (zzb.OF == 0 && zzb.OG == 0) {
                i = 0;
            } else if (zzb.OG >= zzb.OF) {
                zzb.OH = 1;
                return zzb;
            } else {
                i = -1;
            }
            zzb.OH = i;
            return zzb;
        }
    };
    public static final zzb OC = new zzb() {
        public C0030zzb zza(Context context, String str, zza zza) {
            C0030zzb zzb = new C0030zzb();
            zzb.OF = zza.zzaa(context, str);
            zzb.OG = zzb.OF != 0 ? zza.zzc(context, str, false) : zza.zzc(context, str, true);
            if (zzb.OF == 0 && zzb.OG == 0) {
                zzb.OH = 0;
                return zzb;
            } else if (zzb.OG >= zzb.OF) {
                zzb.OH = 1;
                return zzb;
            } else {
                zzb.OH = -1;
                return zzb;
            }
        }
    };
    public static final zzb Oy = new zzb() {
        public C0030zzb zza(Context context, String str, zza zza) {
            C0030zzb zzb = new C0030zzb();
            zzb.OG = zza.zzc(context, str, true);
            if (zzb.OG != 0) {
                zzb.OH = 1;
                return zzb;
            }
            zzb.OF = zza.zzaa(context, str);
            if (zzb.OF != 0) {
                zzb.OH = -1;
            }
            return zzb;
        }
    };
    public static final zzb Oz = new zzb() {
        public C0030zzb zza(Context context, String str, zza zza) {
            C0030zzb zzb = new C0030zzb();
            zzb.OF = zza.zzaa(context, str);
            if (zzb.OF != 0) {
                zzb.OH = -1;
                return zzb;
            }
            zzb.OG = zza.zzc(context, str, true);
            if (zzb.OG != 0) {
                zzb.OH = 1;
            }
            return zzb;
        }
    };
    private static zzsv a;
    private static final zza b = new zza() {
        public int zzaa(Context context, String str) {
            return zzsu.zzaa(context, str);
        }

        public int zzc(Context context, String str, boolean z) {
            return zzsu.zzc(context, str, z);
        }
    };
    private final Context c;

    public static class zza extends Exception {
        private zza(String str) {
            super(str);
        }

        private zza(String str, Throwable th) {
            super(str, th);
        }
    }

    public interface zzb {

        public interface zza {
            int zzaa(Context context, String str);

            int zzc(Context context, String str, boolean z);
        }

        /* renamed from: com.google.android.gms.internal.zzsu$zzb$zzb reason: collision with other inner class name */
        public static class C0030zzb {
            public int OF = 0;
            public int OG = 0;
            public int OH = 0;
        }

        C0030zzb zza(Context context, String str, zza zza2);
    }

    private zzsu(Context context) {
        this.c = (Context) zzac.zzy(context);
    }

    private static zzsu a(Context context, String str) {
        String str2 = "DynamiteModule";
        String str3 = "Selected local version of ";
        String valueOf = String.valueOf(str);
        Log.i(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        return new zzsu(context.getApplicationContext());
    }

    private static zzsu a(Context context, String str, int i) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51);
        sb.append("Selected remote version of ");
        sb.append(str);
        sb.append(", version >= ");
        sb.append(i);
        Log.i("DynamiteModule", sb.toString());
        zzsv a2 = a(context);
        if (a2 == null) {
            throw new zza("Failed to create IDynamiteLoader.");
        }
        try {
            zzd zza2 = a2.zza(zze.zzac(context), str, i);
            if (zze.zzae(zza2) != null) {
                return new zzsu((Context) zze.zzae(zza2));
            }
            throw new zza("Failed to load remote module.");
        } catch (RemoteException e) {
            throw new zza("Failed to load remote module.", e);
        }
    }

    private static zzsv a(Context context) {
        synchronized (zzsu.class) {
            if (a != null) {
                zzsv zzsv = a;
                return zzsv;
            } else if (zzc.zzapd().isGooglePlayServicesAvailable(context) != 0) {
                return null;
            } else {
                try {
                    zzsv zzff = com.google.android.gms.internal.zzsv.zza.zzff((IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance());
                    if (zzff != null) {
                        a = zzff;
                        return zzff;
                    }
                } catch (Exception e) {
                    String str = "DynamiteModule";
                    String str2 = "Failed to load IDynamiteLoader from GmsCore: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
        }
        return null;
    }

    public static zzsu zza(Context context, zzb zzb2, String str) {
        C0030zzb zza2 = zzb2.zza(context, str, b);
        int i = zza2.OF;
        int i2 = zza2.OG;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str).length());
        sb.append("Considering local module ");
        sb.append(str);
        sb.append(":");
        sb.append(i);
        sb.append(" and remote module ");
        sb.append(str);
        sb.append(":");
        sb.append(i2);
        Log.i("DynamiteModule", sb.toString());
        if (zza2.OH == 0 || ((zza2.OH == -1 && zza2.OF == 0) || (zza2.OH == 1 && zza2.OG == 0))) {
            int i3 = zza2.OF;
            int i4 = zza2.OG;
            StringBuilder sb2 = new StringBuilder(91);
            sb2.append("No acceptable module found. Local version is ");
            sb2.append(i3);
            sb2.append(" and remote version is ");
            sb2.append(i4);
            sb2.append(".");
            throw new zza(sb2.toString());
        } else if (zza2.OH == -1) {
            return a(context, str);
        } else {
            if (zza2.OH == 1) {
                try {
                    return a(context, str, zza2.OG);
                } catch (zza e) {
                    String str2 = "DynamiteModule";
                    String str3 = "Failed to load remote module: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                    if (zza2.OF != 0) {
                        final int i5 = zza2.OF;
                        if (zzb2.zza(context, str, new zza() {
                            public int zzaa(Context context, String str) {
                                return i5;
                            }

                            public int zzc(Context context, String str, boolean z) {
                                return 0;
                            }
                        }).OH == -1) {
                            return a(context, str);
                        }
                    }
                    throw new zza("Remote load failed. No local fallback found.", e);
                }
            } else {
                int i6 = zza2.OH;
                StringBuilder sb3 = new StringBuilder(47);
                sb3.append("VersionPolicy returned invalid code:");
                sb3.append(i6);
                throw new zza(sb3.toString());
            }
        }
    }

    public static int zzaa(Context context, String str) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            String valueOf = String.valueOf("com.google.android.gms.dynamite.descriptors.");
            String valueOf2 = String.valueOf("ModuleDescriptor");
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(str).length() + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append(str);
            sb.append(".");
            sb.append(valueOf2);
            Class loadClass = classLoader.loadClass(sb.toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                return declaredField2.getInt(null);
            }
            String valueOf3 = String.valueOf(declaredField.get(null));
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 51 + String.valueOf(str).length());
            sb2.append("Module descriptor id '");
            sb2.append(valueOf3);
            sb2.append("' didn't match expected id '");
            sb2.append(str);
            sb2.append("'");
            Log.e("DynamiteModule", sb2.toString());
            return 0;
        } catch (ClassNotFoundException unused) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 45);
            sb3.append("Local module descriptor class for ");
            sb3.append(str);
            sb3.append(" not found.");
            Log.w("DynamiteModule", sb3.toString());
            return 0;
        } catch (Exception e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load module descriptor class: ";
            String valueOf4 = String.valueOf(e.getMessage());
            Log.e(str2, valueOf4.length() != 0 ? str3.concat(valueOf4) : new String(str3));
            return 0;
        }
    }

    public static int zzab(Context context, String str) {
        return zzc(context, str, false);
    }

    public static int zzc(Context context, String str, boolean z) {
        zzsv a2 = a(context);
        if (a2 == null) {
            return 0;
        }
        try {
            return a2.zza(zze.zzac(context), str, z);
        } catch (RemoteException e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to retrieve remote module version: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return 0;
        }
    }

    public Context zzbdy() {
        return this.c;
    }

    public IBinder zzjd(String str) {
        try {
            return (IBinder) this.c.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String str2 = "Failed to instantiate module class: ";
            String valueOf = String.valueOf(str);
            throw new zza(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
        }
    }
}
