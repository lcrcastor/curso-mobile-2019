package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class zzx implements zzc {
    /* access modifiers changed from: private */
    public static final String a = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", new Object[]{"datalayer", "ID", "key", TarjetasConstants.VALUE, ClientCookie.EXPIRES_ATTR});
    private final Executor b;
    /* access modifiers changed from: private */
    public final Context c;
    private zza d;
    private zze e;
    private int f;

    class zza extends SQLiteOpenHelper {
        zza(Context context, String str) {
            super(context, str, null, 1);
        }

        /* JADX INFO: finally extract failed */
        private void a(SQLiteDatabase sQLiteDatabase) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            HashSet hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (String add : columnNames) {
                    hashSet.add(add);
                }
                rawQuery.close();
                if (!hashSet.remove("key") || !hashSet.remove(TarjetasConstants.VALUE) || !hashSet.remove("ID") || !hashSet.remove(ClientCookie.EXPIRES_ATTR)) {
                    throw new SQLiteException("Database column missing");
                } else if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } catch (Throwable th) {
                rawQuery.close();
                throw th;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x0036 A[Catch:{ all -> 0x0028 }] */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x003b A[Catch:{ all -> 0x0028 }] */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0045  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x004b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a(java.lang.String r11, android.database.sqlite.SQLiteDatabase r12) {
            /*
                r10 = this;
                r0 = 0
                r1 = 0
                java.lang.String r3 = "SQLITE_MASTER"
                r2 = 1
                java.lang.String[] r4 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x002a }
                java.lang.String r5 = "name"
                r4[r0] = r5     // Catch:{ SQLiteException -> 0x002a }
                java.lang.String r5 = "name=?"
                java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x002a }
                r6[r0] = r11     // Catch:{ SQLiteException -> 0x002a }
                r7 = 0
                r8 = 0
                r9 = 0
                r2 = r12
                android.database.Cursor r12 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x002a }
                boolean r1 = r12.moveToFirst()     // Catch:{ SQLiteException -> 0x0026, all -> 0x0023 }
                if (r12 == 0) goto L_0x0022
                r12.close()
            L_0x0022:
                return r1
            L_0x0023:
                r11 = move-exception
                r1 = r12
                goto L_0x0049
            L_0x0026:
                r1 = r12
                goto L_0x002a
            L_0x0028:
                r11 = move-exception
                goto L_0x0049
            L_0x002a:
                java.lang.String r12 = "Error querying for table "
                java.lang.String r11 = java.lang.String.valueOf(r11)     // Catch:{ all -> 0x0028 }
                int r2 = r11.length()     // Catch:{ all -> 0x0028 }
                if (r2 == 0) goto L_0x003b
                java.lang.String r11 = r12.concat(r11)     // Catch:{ all -> 0x0028 }
                goto L_0x0040
            L_0x003b:
                java.lang.String r11 = new java.lang.String     // Catch:{ all -> 0x0028 }
                r11.<init>(r12)     // Catch:{ all -> 0x0028 }
            L_0x0040:
                com.google.android.gms.tagmanager.zzbo.zzdf(r11)     // Catch:{ all -> 0x0028 }
                if (r1 == 0) goto L_0x0048
                r1.close()
            L_0x0048:
                return r0
            L_0x0049:
                if (r1 == 0) goto L_0x004e
                r1.close()
            L_0x004e:
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzx.zza.a(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
        }

        public SQLiteDatabase getWritableDatabase() {
            SQLiteDatabase sQLiteDatabase;
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException unused) {
                zzx.this.c.getDatabasePath("google_tagmanager.db").delete();
                sQLiteDatabase = null;
            }
            return sQLiteDatabase == null ? super.getWritableDatabase() : sQLiteDatabase;
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            zzan.a(sQLiteDatabase.getPath());
        }

        public void onOpen(SQLiteDatabase sQLiteDatabase) {
            if (VERSION.SDK_INT < 15) {
                Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                } finally {
                    rawQuery.close();
                }
            }
            if (!a("datalayer", sQLiteDatabase)) {
                sQLiteDatabase.execSQL(zzx.a);
            } else {
                a(sQLiteDatabase);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    static class zzb {
        final String a;
        final byte[] b;

        zzb(String str, byte[] bArr) {
            this.a = str;
            this.b = bArr;
        }

        public String toString() {
            String str = this.a;
            int hashCode = Arrays.hashCode(this.b);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 54);
            sb.append("KeyAndSerialized: key = ");
            sb.append(str);
            sb.append(" serialized hash = ");
            sb.append(hashCode);
            return sb.toString();
        }
    }

    public zzx(Context context) {
        this(context, zzh.zzaxj(), "google_tagmanager.db", 2000, Executors.newSingleThreadExecutor());
    }

    zzx(Context context, zze zze, String str, int i, Executor executor) {
        this.c = context;
        this.e = zze;
        this.f = i;
        this.b = executor;
        this.d = new zza(this.c, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0020 A[SYNTHETIC, Splitter:B:15:0x0020] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x002a A[SYNTHETIC, Splitter:B:24:0x002a] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0034 A[SYNTHETIC, Splitter:B:33:0x0034] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Object a(byte[] r5) {
        /*
            r4 = this;
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r5)
            r5 = 0
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x0031, ClassNotFoundException -> 0x0027, all -> 0x001d }
            r1.<init>(r0)     // Catch:{ IOException -> 0x0031, ClassNotFoundException -> 0x0027, all -> 0x001d }
            java.lang.Object r2 = r1.readObject()     // Catch:{ IOException -> 0x0032, ClassNotFoundException -> 0x0028, all -> 0x0018 }
            if (r1 == 0) goto L_0x0014
            r1.close()     // Catch:{ IOException -> 0x0017 }
        L_0x0014:
            r0.close()     // Catch:{ IOException -> 0x0017 }
        L_0x0017:
            return r2
        L_0x0018:
            r5 = move-exception
            r3 = r1
            r1 = r5
            r5 = r3
            goto L_0x001e
        L_0x001d:
            r1 = move-exception
        L_0x001e:
            if (r5 == 0) goto L_0x0023
            r5.close()     // Catch:{ IOException -> 0x0026 }
        L_0x0023:
            r0.close()     // Catch:{ IOException -> 0x0026 }
        L_0x0026:
            throw r1
        L_0x0027:
            r1 = r5
        L_0x0028:
            if (r1 == 0) goto L_0x002d
            r1.close()     // Catch:{ IOException -> 0x0030 }
        L_0x002d:
            r0.close()     // Catch:{ IOException -> 0x0030 }
        L_0x0030:
            return r5
        L_0x0031:
            r1 = r5
        L_0x0032:
            if (r1 == 0) goto L_0x0037
            r1.close()     // Catch:{ IOException -> 0x003a }
        L_0x0037:
            r0.close()     // Catch:{ IOException -> 0x003a }
        L_0x003a:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzx.a(byte[]):java.lang.Object");
    }

    private List<zza> a(List<zzb> list) {
        ArrayList arrayList = new ArrayList();
        for (zzb zzb2 : list) {
            arrayList.add(new zza(zzb2.a, a(zzb2.b)));
        }
        return arrayList;
    }

    private void a(int i) {
        int d2 = (d() - this.f) + i;
        if (d2 > 0) {
            List b2 = b(d2);
            int size = b2.size();
            StringBuilder sb = new StringBuilder(64);
            sb.append("DataLayer store full, deleting ");
            sb.append(size);
            sb.append(" entries to make room.");
            zzbo.zzde(sb.toString());
            a((String[]) b2.toArray(new String[0]));
        }
    }

    private void a(long j) {
        SQLiteDatabase c2 = c("Error opening database for deleteOlderThan.");
        if (c2 != null) {
            try {
                int delete = c2.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)});
                StringBuilder sb = new StringBuilder(33);
                sb.append("Deleted ");
                sb.append(delete);
                sb.append(" expired items");
                zzbo.v(sb.toString());
            } catch (SQLiteException unused) {
                zzbo.zzdf("Error deleting old entries.");
            }
        }
    }

    private void a(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase c2 = c("Error opening database for deleteEntries.");
            if (c2 != null) {
                try {
                    c2.delete("datalayer", String.format("%s in (%s)", new Object[]{"ID", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                } catch (SQLiteException unused) {
                    String str = "Error deleting entries ";
                    String valueOf = String.valueOf(Arrays.toString(strArr));
                    zzbo.zzdf(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0021 A[SYNTHETIC, Splitter:B:15:0x0021] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x002b A[SYNTHETIC, Splitter:B:24:0x002b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] a(java.lang.Object r4) {
        /*
            r3 = this;
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r1 = 0
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0028, all -> 0x001e }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0028, all -> 0x001e }
            r2.writeObject(r4)     // Catch:{ IOException -> 0x0029, all -> 0x001b }
            byte[] r4 = r0.toByteArray()     // Catch:{ IOException -> 0x0029, all -> 0x001b }
            if (r2 == 0) goto L_0x0017
            r2.close()     // Catch:{ IOException -> 0x001a }
        L_0x0017:
            r0.close()     // Catch:{ IOException -> 0x001a }
        L_0x001a:
            return r4
        L_0x001b:
            r4 = move-exception
            r1 = r2
            goto L_0x001f
        L_0x001e:
            r4 = move-exception
        L_0x001f:
            if (r1 == 0) goto L_0x0024
            r1.close()     // Catch:{ IOException -> 0x0027 }
        L_0x0024:
            r0.close()     // Catch:{ IOException -> 0x0027 }
        L_0x0027:
            throw r4
        L_0x0028:
            r2 = r1
        L_0x0029:
            if (r2 == 0) goto L_0x002e
            r2.close()     // Catch:{ IOException -> 0x0031 }
        L_0x002e:
            r0.close()     // Catch:{ IOException -> 0x0031 }
        L_0x0031:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzx.a(java.lang.Object):byte[]");
    }

    /* access modifiers changed from: private */
    public List<zza> b() {
        try {
            a(this.e.currentTimeMillis());
            return a(c());
        } finally {
            e();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0078 A[Catch:{ all -> 0x0065 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007d A[Catch:{ all -> 0x0065 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.String> b(int r15) {
        /*
            r14 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            if (r15 > 0) goto L_0x000d
            java.lang.String r15 = "Invalid maxEntries specified. Skipping."
            com.google.android.gms.tagmanager.zzbo.zzdf(r15)
            return r0
        L_0x000d:
            java.lang.String r1 = "Error opening database for peekEntryIds."
            android.database.sqlite.SQLiteDatabase r2 = r14.c(r1)
            if (r2 != 0) goto L_0x0016
            return r0
        L_0x0016:
            r1 = 0
            java.lang.String r3 = "datalayer"
            r4 = 1
            java.lang.String[] r5 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0067 }
            java.lang.String r6 = "ID"
            r11 = 0
            r5[r11] = r6     // Catch:{ SQLiteException -> 0x0067 }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            java.lang.String r10 = "%s ASC"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ SQLiteException -> 0x0067 }
            java.lang.String r12 = "ID"
            r4[r11] = r12     // Catch:{ SQLiteException -> 0x0067 }
            java.lang.String r10 = java.lang.String.format(r10, r4)     // Catch:{ SQLiteException -> 0x0067 }
            java.lang.String r15 = java.lang.Integer.toString(r15)     // Catch:{ SQLiteException -> 0x0067 }
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r15
            android.database.Cursor r15 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x0067 }
            boolean r1 = r15.moveToFirst()     // Catch:{ SQLiteException -> 0x0060, all -> 0x005d }
            if (r1 == 0) goto L_0x0057
        L_0x0046:
            long r1 = r15.getLong(r11)     // Catch:{ SQLiteException -> 0x0060, all -> 0x005d }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ SQLiteException -> 0x0060, all -> 0x005d }
            r0.add(r1)     // Catch:{ SQLiteException -> 0x0060, all -> 0x005d }
            boolean r1 = r15.moveToNext()     // Catch:{ SQLiteException -> 0x0060, all -> 0x005d }
            if (r1 != 0) goto L_0x0046
        L_0x0057:
            if (r15 == 0) goto L_0x008a
            r15.close()
            return r0
        L_0x005d:
            r0 = move-exception
            r1 = r15
            goto L_0x008b
        L_0x0060:
            r1 = move-exception
            r13 = r1
            r1 = r15
            r15 = r13
            goto L_0x0068
        L_0x0065:
            r0 = move-exception
            goto L_0x008b
        L_0x0067:
            r15 = move-exception
        L_0x0068:
            java.lang.String r2 = "Error in peekEntries fetching entryIds: "
            java.lang.String r15 = r15.getMessage()     // Catch:{ all -> 0x0065 }
            java.lang.String r15 = java.lang.String.valueOf(r15)     // Catch:{ all -> 0x0065 }
            int r3 = r15.length()     // Catch:{ all -> 0x0065 }
            if (r3 == 0) goto L_0x007d
            java.lang.String r15 = r2.concat(r15)     // Catch:{ all -> 0x0065 }
            goto L_0x0082
        L_0x007d:
            java.lang.String r15 = new java.lang.String     // Catch:{ all -> 0x0065 }
            r15.<init>(r2)     // Catch:{ all -> 0x0065 }
        L_0x0082:
            com.google.android.gms.tagmanager.zzbo.zzdf(r15)     // Catch:{ all -> 0x0065 }
            if (r1 == 0) goto L_0x008a
            r1.close()
        L_0x008a:
            return r0
        L_0x008b:
            if (r1 == 0) goto L_0x0090
            r1.close()
        L_0x0090:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzx.b(int):java.util.List");
    }

    private List<zzb> b(List<zza> list) {
        ArrayList arrayList = new ArrayList();
        for (zza zza2 : list) {
            arrayList.add(new zzb(zza2.a, a(zza2.b)));
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        SQLiteDatabase c2 = c("Error opening database for clearKeysWithPrefix.");
        if (c2 != null) {
            try {
                int delete = c2.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, String.valueOf(str).concat(".%")});
                StringBuilder sb = new StringBuilder(25);
                sb.append("Cleared ");
                sb.append(delete);
                sb.append(" items");
                zzbo.v(sb.toString());
            } catch (SQLiteException e2) {
                String valueOf = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(valueOf).length());
                sb2.append("Error deleting entries with key prefix: ");
                sb2.append(str);
                sb2.append(" (");
                sb2.append(valueOf);
                sb2.append(").");
                zzbo.zzdf(sb2.toString());
            } catch (Throwable th) {
                e();
                throw th;
            }
            e();
        }
    }

    /* access modifiers changed from: private */
    public synchronized void b(List<zzb> list, long j) {
        try {
            long currentTimeMillis = this.e.currentTimeMillis();
            a(currentTimeMillis);
            a(list.size());
            c(list, currentTimeMillis + j);
            e();
        } catch (Throwable th) {
            e();
            throw th;
        }
    }

    private SQLiteDatabase c(String str) {
        try {
            return this.d.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzbo.zzdf(str);
            return null;
        }
    }

    private List<zzb> c() {
        SQLiteDatabase c2 = c("Error opening database for loadSerialized.");
        ArrayList arrayList = new ArrayList();
        if (c2 == null) {
            return arrayList;
        }
        Cursor query = c2.query("datalayer", new String[]{"key", TarjetasConstants.VALUE}, null, null, null, null, "ID", null);
        while (query.moveToNext()) {
            try {
                arrayList.add(new zzb(query.getString(0), query.getBlob(1)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    private void c(List<zzb> list, long j) {
        SQLiteDatabase c2 = c("Error opening database for writeEntryToDatabase.");
        if (c2 != null) {
            for (zzb zzb2 : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ClientCookie.EXPIRES_ATTR, Long.valueOf(j));
                contentValues.put("key", zzb2.a);
                contentValues.put(TarjetasConstants.VALUE, zzb2.b);
                c2.insert("datalayer", null, contentValues);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int d() {
        /*
            r4 = this;
            java.lang.String r0 = "Error opening database for getNumStoredEntries."
            android.database.sqlite.SQLiteDatabase r0 = r4.c(r0)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            r2 = 0
            java.lang.String r3 = "SELECT COUNT(*) from datalayer"
            android.database.Cursor r0 = r0.rawQuery(r3, r2)     // Catch:{ SQLiteException -> 0x0029 }
            boolean r2 = r0.moveToFirst()     // Catch:{ SQLiteException -> 0x0025, all -> 0x0022 }
            if (r2 == 0) goto L_0x001c
            long r2 = r0.getLong(r1)     // Catch:{ SQLiteException -> 0x0025, all -> 0x0022 }
            int r1 = (int) r2
        L_0x001c:
            if (r0 == 0) goto L_0x0033
            r0.close()
            return r1
        L_0x0022:
            r1 = move-exception
            r2 = r0
            goto L_0x0034
        L_0x0025:
            r2 = r0
            goto L_0x0029
        L_0x0027:
            r1 = move-exception
            goto L_0x0034
        L_0x0029:
            java.lang.String r0 = "Error getting numStoredEntries"
            com.google.android.gms.tagmanager.zzbo.zzdf(r0)     // Catch:{ all -> 0x0027 }
            if (r2 == 0) goto L_0x0033
            r2.close()
        L_0x0033:
            return r1
        L_0x0034:
            if (r2 == 0) goto L_0x0039
            r2.close()
        L_0x0039:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzx.d():int");
    }

    private void e() {
        try {
            this.d.close();
        } catch (SQLiteException unused) {
        }
    }

    public void a(final com.google.android.gms.tagmanager.DataLayer.zzc.zza zza2) {
        this.b.execute(new Runnable() {
            public void run() {
                zza2.zzai(zzx.this.b());
            }
        });
    }

    public void a(final String str) {
        this.b.execute(new Runnable() {
            public void run() {
                zzx.this.b(str);
            }
        });
    }

    public void a(List<zza> list, final long j) {
        final List b2 = b(list);
        this.b.execute(new Runnable() {
            public void run() {
                zzx.this.b(b2, j);
            }
        });
    }
}
