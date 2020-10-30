package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzc;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bouncycastle.crypto.tls.CipherSuite;

@KeepName
public final class DataHolder extends AbstractSafeParcelable implements Closeable {
    public static final Creator<DataHolder> CREATOR = new zze();
    private static final zza k = new zza(new String[0], null) {
        public zza zza(ContentValues contentValues) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }

        public zza zzb(HashMap<String, Object> hashMap) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }
    };
    Bundle a;
    int[] b;
    int c;
    boolean d;
    private final int e;
    private final String[] f;
    private final CursorWindow[] g;
    private final int h;
    private final Bundle i;
    private boolean j;

    public static class zza {
        /* access modifiers changed from: private */
        public final String[] a;
        /* access modifiers changed from: private */
        public final ArrayList<HashMap<String, Object>> b;
        private final String c;
        private final HashMap<Object, Integer> d;
        private boolean e;
        private String f;

        private zza(String[] strArr, String str) {
            this.a = (String[]) zzac.zzy(strArr);
            this.b = new ArrayList<>();
            this.c = str;
            this.d = new HashMap<>();
            this.e = false;
            this.f = null;
        }

        private int a(HashMap<String, Object> hashMap) {
            if (this.c == null) {
                return -1;
            }
            Object obj = hashMap.get(this.c);
            if (obj == null) {
                return -1;
            }
            Integer num = (Integer) this.d.get(obj);
            if (num != null) {
                return num.intValue();
            }
            this.d.put(obj, Integer.valueOf(this.b.size()));
            return -1;
        }

        public zza zza(ContentValues contentValues) {
            zzc.zzu(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Entry entry : contentValues.valueSet()) {
                hashMap.put((String) entry.getKey(), entry.getValue());
            }
            return zzb(hashMap);
        }

        public zza zzb(HashMap<String, Object> hashMap) {
            zzc.zzu(hashMap);
            int a2 = a(hashMap);
            if (a2 == -1) {
                this.b.add(hashMap);
            } else {
                this.b.remove(a2);
                this.b.add(a2, hashMap);
            }
            this.e = false;
            return this;
        }

        public DataHolder zzgd(int i) {
            return new DataHolder(this, i, (Bundle) null);
        }
    }

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    DataHolder(int i2, String[] strArr, CursorWindow[] cursorWindowArr, int i3, Bundle bundle) {
        this.d = false;
        this.j = true;
        this.e = i2;
        this.f = strArr;
        this.g = cursorWindowArr;
        this.h = i3;
        this.i = bundle;
    }

    private DataHolder(zza zza2, int i2, Bundle bundle) {
        this(zza2.a, a(zza2, -1), i2, bundle);
    }

    public DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.d = false;
        this.j = true;
        this.e = 1;
        this.f = (String[]) zzac.zzy(strArr);
        this.g = (CursorWindow[]) zzac.zzy(cursorWindowArr);
        this.h = i2;
        this.i = bundle;
        zzate();
    }

    private void a(String str, int i2) {
        if (this.a == null || !this.a.containsKey(str)) {
            String str2 = "No such column: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i2 < 0 || i2 >= this.c) {
            throw new CursorIndexOutOfBoundsException(i2, this.c);
        }
    }

    private static CursorWindow[] a(zza zza2, int i2) {
        long j2;
        if (zza2.a.length == 0) {
            return new CursorWindow[0];
        }
        List b2 = (i2 < 0 || i2 >= zza2.b.size()) ? zza2.b : zza2.b.subList(0, i2);
        int size = b2.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(zza2.a.length);
        CursorWindow cursorWindow2 = cursorWindow;
        int i3 = 0;
        boolean z = false;
        while (i3 < size) {
            try {
                if (!cursorWindow2.allocRow()) {
                    StringBuilder sb = new StringBuilder(72);
                    sb.append("Allocating additional cursor window for large data set (row ");
                    sb.append(i3);
                    sb.append(")");
                    Log.d("DataHolder", sb.toString());
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i3);
                    cursorWindow2.setNumColumns(zza2.a.length);
                    arrayList.add(cursorWindow2);
                    if (!cursorWindow2.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow2);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) b2.get(i3);
                boolean z2 = true;
                for (int i4 = 0; i4 < zza2.a.length && z2; i4++) {
                    String str = zza2.a[i4];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z2 = cursorWindow2.putNull(i3, i4);
                    } else if (obj instanceof String) {
                        z2 = cursorWindow2.putString((String) obj, i3, i4);
                    } else {
                        if (obj instanceof Long) {
                            j2 = ((Long) obj).longValue();
                        } else if (obj instanceof Integer) {
                            z2 = cursorWindow2.putLong((long) ((Integer) obj).intValue(), i3, i4);
                        } else if (obj instanceof Boolean) {
                            j2 = ((Boolean) obj).booleanValue() ? 1 : 0;
                        } else if (obj instanceof byte[]) {
                            z2 = cursorWindow2.putBlob((byte[]) obj, i3, i4);
                        } else if (obj instanceof Double) {
                            z2 = cursorWindow2.putDouble(((Double) obj).doubleValue(), i3, i4);
                        } else if (obj instanceof Float) {
                            z2 = cursorWindow2.putDouble((double) ((Float) obj).floatValue(), i3, i4);
                        } else {
                            String valueOf = String.valueOf(obj);
                            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 32 + String.valueOf(valueOf).length());
                            sb2.append("Unsupported object for column ");
                            sb2.append(str);
                            sb2.append(": ");
                            sb2.append(valueOf);
                            throw new IllegalArgumentException(sb2.toString());
                        }
                        z2 = cursorWindow2.putLong(j2, i3, i4);
                    }
                }
                if (z2) {
                    z = false;
                } else if (z) {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                } else {
                    StringBuilder sb3 = new StringBuilder(74);
                    sb3.append("Couldn't populate window data for row ");
                    sb3.append(i3);
                    sb3.append(" - allocating new window.");
                    Log.d("DataHolder", sb3.toString());
                    cursorWindow2.freeLastRow();
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i3);
                    cursorWindow2.setNumColumns(zza2.a.length);
                    arrayList.add(cursorWindow2);
                    i3--;
                    z = true;
                }
                i3++;
            } catch (RuntimeException e2) {
                int size2 = arrayList.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    ((CursorWindow) arrayList.get(i5)).close();
                }
                throw e2;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static DataHolder zza(int i2, Bundle bundle) {
        return new DataHolder(k, i2, bundle);
    }

    public static zza zzc(String[] strArr) {
        return new zza(strArr, null);
    }

    public static DataHolder zzgc(int i2) {
        return zza(i2, null);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public String[] b() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public CursorWindow[] c() {
        return this.g;
    }

    public void close() {
        synchronized (this) {
            if (!this.d) {
                this.d = true;
                for (CursorWindow close : this.g) {
                    close.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            if (this.j && this.g.length > 0 && !isClosed()) {
                close();
                String valueOf = String.valueOf(toString());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256);
                sb.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
                sb.append(valueOf);
                sb.append(")");
                Log.e("DataBuffer", sb.toString());
            }
        } finally {
            super.finalize();
        }
    }

    public int getCount() {
        return this.c;
    }

    public int getStatusCode() {
        return this.h;
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.d;
        }
        return z;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        zze.a(this, parcel, i2);
    }

    public void zza(String str, int i2, int i3, CharArrayBuffer charArrayBuffer) {
        a(str, i2);
        this.g[i3].copyStringToBuffer(i2, this.a.getInt(str), charArrayBuffer);
    }

    public Bundle zzasz() {
        return this.i;
    }

    public void zzate() {
        this.a = new Bundle();
        for (int i2 = 0; i2 < this.f.length; i2++) {
            this.a.putInt(this.f[i2], i2);
        }
        this.b = new int[this.g.length];
        int i3 = 0;
        for (int i4 = 0; i4 < this.g.length; i4++) {
            this.b[i4] = i3;
            i3 += this.g[i4].getNumRows() - (i3 - this.g[i4].getStartPosition());
        }
        this.c = i3;
    }

    public long zzb(String str, int i2, int i3) {
        a(str, i2);
        return this.g[i3].getLong(i2, this.a.getInt(str));
    }

    public int zzc(String str, int i2, int i3) {
        a(str, i2);
        return this.g[i3].getInt(i2, this.a.getInt(str));
    }

    public String zzd(String str, int i2, int i3) {
        a(str, i2);
        return this.g[i3].getString(i2, this.a.getInt(str));
    }

    public boolean zze(String str, int i2, int i3) {
        a(str, i2);
        return Long.valueOf(this.g[i3].getLong(i2, this.a.getInt(str))).longValue() == 1;
    }

    public float zzf(String str, int i2, int i3) {
        a(str, i2);
        return this.g[i3].getFloat(i2, this.a.getInt(str));
    }

    public byte[] zzg(String str, int i2, int i3) {
        a(str, i2);
        return this.g[i3].getBlob(i2, this.a.getInt(str));
    }

    public int zzgb(int i2) {
        int i3 = 0;
        zzac.zzbr(i2 >= 0 && i2 < this.c);
        while (true) {
            if (i3 >= this.b.length) {
                break;
            } else if (i2 < this.b[i3]) {
                i3--;
                break;
            } else {
                i3++;
            }
        }
        return i3 == this.b.length ? i3 - 1 : i3;
    }

    public Uri zzh(String str, int i2, int i3) {
        String zzd = zzd(str, i2, i3);
        if (zzd == null) {
            return null;
        }
        return Uri.parse(zzd);
    }

    public boolean zzhm(String str) {
        return this.a.containsKey(str);
    }

    public boolean zzi(String str, int i2, int i3) {
        a(str, i2);
        return this.g[i3].isNull(i2, this.a.getInt(str));
    }
}
