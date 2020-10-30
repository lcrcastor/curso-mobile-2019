package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

class zzcg implements zzaw {
    /* access modifiers changed from: private */
    public static final String a = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", new Object[]{"gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time"});
    private final zzb b;
    private volatile zzad c;
    private final zzax d;
    /* access modifiers changed from: private */
    public final Context e;
    /* access modifiers changed from: private */
    public final String f;
    private long g;
    /* access modifiers changed from: private */
    public zze h;
    private final int i;

    class zza implements com.google.android.gms.tagmanager.zzdf.zza {
        zza() {
        }

        public void zza(zzas zzas) {
            zzcg.this.a(zzas.a());
        }

        public void zzb(zzas zzas) {
            zzcg.this.a(zzas.a());
            long a2 = zzas.a();
            StringBuilder sb = new StringBuilder(57);
            sb.append("Permanent failure dispatching hitId: ");
            sb.append(a2);
            zzbo.v(sb.toString());
        }

        public void zzc(zzas zzas) {
            long b = zzas.b();
            if (b == 0) {
                zzcg.this.a(zzas.a(), zzcg.this.h.currentTimeMillis());
                return;
            }
            if (b + 14400000 < zzcg.this.h.currentTimeMillis()) {
                zzcg.this.a(zzas.a());
                long a2 = zzas.a();
                StringBuilder sb = new StringBuilder(47);
                sb.append("Giving up on failed hitId: ");
                sb.append(a2);
                zzbo.v(sb.toString());
            }
        }
    }

    class zzb extends SQLiteOpenHelper {
        private boolean b;
        private long c = 0;

        zzb(Context context, String str) {
            super(context, str, null, 1);
        }

        /* JADX INFO: finally extract failed */
        private void a(SQLiteDatabase sQLiteDatabase) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
            HashSet hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (String add : columnNames) {
                    hashSet.add(add);
                }
                rawQuery.close();
                if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_time") || !hashSet.remove("hit_first_send_time")) {
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
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcg.zzb.a(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
        }

        public SQLiteDatabase getWritableDatabase() {
            if (!this.b || this.c + 3600000 <= zzcg.this.h.currentTimeMillis()) {
                SQLiteDatabase sQLiteDatabase = null;
                this.b = true;
                this.c = zzcg.this.h.currentTimeMillis();
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                } catch (SQLiteException unused) {
                    zzcg.this.e.getDatabasePath(zzcg.this.f).delete();
                }
                if (sQLiteDatabase == null) {
                    sQLiteDatabase = super.getWritableDatabase();
                }
                this.b = false;
                return sQLiteDatabase;
            }
            throw new SQLiteException("Database creation failed");
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
            if (!a("gtm_hits", sQLiteDatabase)) {
                sQLiteDatabase.execSQL(zzcg.a);
            } else {
                a(sQLiteDatabase);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    zzcg(zzax zzax, Context context) {
        this(zzax, context, "gtm_urls.db", 2000);
    }

    zzcg(zzax zzax, Context context, String str, int i2) {
        this.e = context.getApplicationContext();
        this.f = str;
        this.d = zzax;
        this.h = zzh.zzaxj();
        this.b = new zzb(this.e, this.f);
        this.c = new zzdf(this.e, new zza());
        this.g = 0;
        this.i = i2;
    }

    private SQLiteDatabase a(String str) {
        try {
            return this.b.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzbo.zzdf(str);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void a(long j) {
        a(new String[]{String.valueOf(j)});
    }

    /* access modifiers changed from: private */
    public void a(long j, long j2) {
        SQLiteDatabase a2 = a("Error opening database for getNumStoredHits.");
        if (a2 != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_first_send_time", Long.valueOf(j2));
            try {
                a2.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
            } catch (SQLiteException unused) {
                StringBuilder sb = new StringBuilder(69);
                sb.append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ");
                sb.append(j);
                zzbo.zzdf(sb.toString());
                a(j);
            }
        }
    }

    private void b(long j, String str) {
        SQLiteDatabase a2 = a("Error opening database for putHit");
        if (a2 != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_time", Long.valueOf(j));
            contentValues.put("hit_url", str);
            contentValues.put("hit_first_send_time", Integer.valueOf(0));
            try {
                a2.insert("gtm_hits", null, contentValues);
                this.d.a(false);
            } catch (SQLiteException unused) {
                zzbo.zzdf("Error storing hit");
            }
        }
    }

    private void f() {
        int c2 = (c() - this.i) + 1;
        if (c2 > 0) {
            List a2 = a(c2);
            int size = a2.size();
            StringBuilder sb = new StringBuilder(51);
            sb.append("Store full, deleting ");
            sb.append(size);
            sb.append(" hits to make room.");
            zzbo.v(sb.toString());
            a((String[]) a2.toArray(new String[0]));
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0078 A[Catch:{ all -> 0x0065 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007d A[Catch:{ all -> 0x0065 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.lang.String> a(int r15) {
        /*
            r14 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            if (r15 > 0) goto L_0x000d
            java.lang.String r15 = "Invalid maxHits specified. Skipping"
            com.google.android.gms.tagmanager.zzbo.zzdf(r15)
            return r0
        L_0x000d:
            java.lang.String r1 = "Error opening database for peekHitIds."
            android.database.sqlite.SQLiteDatabase r2 = r14.a(r1)
            if (r2 != 0) goto L_0x0016
            return r0
        L_0x0016:
            r1 = 0
            java.lang.String r3 = "gtm_hits"
            r4 = 1
            java.lang.String[] r5 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0067 }
            java.lang.String r6 = "hit_id"
            r11 = 0
            r5[r11] = r6     // Catch:{ SQLiteException -> 0x0067 }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            java.lang.String r10 = "%s ASC"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ SQLiteException -> 0x0067 }
            java.lang.String r12 = "hit_id"
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
            java.lang.String r2 = "Error in peekHits fetching hitIds: "
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcg.a(int):java.util.List");
    }

    public void a() {
        zzbo.v("GTM Dispatch running...");
        if (this.c.a()) {
            List b2 = b(40);
            if (b2.isEmpty()) {
                zzbo.v("...nothing to dispatch");
                this.d.a(true);
                return;
            }
            this.c.a(b2);
            if (d() > 0) {
                zzdc.a().dispatch();
            }
        }
    }

    public void a(long j, String str) {
        b();
        f();
        b(j, str);
    }

    /* access modifiers changed from: 0000 */
    public void a(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase a2 = a("Error opening database for deleteHits.");
            if (a2 != null) {
                boolean z = true;
                try {
                    a2.delete("gtm_hits", String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                    zzax zzax = this.d;
                    if (c() != 0) {
                        z = false;
                    }
                    zzax.a(z);
                } catch (SQLiteException unused) {
                    zzbo.zzdf("Error deleting hits");
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        long currentTimeMillis = this.h.currentTimeMillis();
        boolean z = false;
        if (currentTimeMillis <= this.g + 86400000) {
            return 0;
        }
        this.g = currentTimeMillis;
        SQLiteDatabase a2 = a("Error opening database for deleteStaleHits.");
        if (a2 == null) {
            return 0;
        }
        int delete = a2.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.h.currentTimeMillis() - 2592000000L)});
        zzax zzax = this.d;
        if (c() == 0) {
            z = true;
        }
        zzax.a(z);
        return delete;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:74:0x015b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x015c, code lost:
        r1 = r0;
        r13 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0162, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0163, code lost:
        r11 = r1;
        r13 = r12;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0145 A[EDGE_INSN: B:100:0x0145->B:65:0x0145 ?: BREAK  
    EDGE_INSN: B:100:0x0145->B:65:0x0145 ?: BREAK  
    EDGE_INSN: B:100:0x0145->B:65:0x0145 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0145 A[EDGE_INSN: B:100:0x0145->B:65:0x0145 ?: BREAK  
    EDGE_INSN: B:100:0x0145->B:65:0x0145 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0111 A[Catch:{ all -> 0x014b }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0116 A[Catch:{ all -> 0x014b }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x012d A[Catch:{ all -> 0x014b }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x014f  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x015b A[ExcHandler: all (r0v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:6:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0181 A[Catch:{ all -> 0x0194 }] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0186 A[Catch:{ all -> 0x0194 }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0190  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0198  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.gms.tagmanager.zzas> b(int r19) {
        /*
            r18 = this;
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.lang.String r2 = "Error opening database for peekHits"
            r3 = r18
            android.database.sqlite.SQLiteDatabase r2 = r3.a(r2)
            if (r2 != 0) goto L_0x0010
            return r1
        L_0x0010:
            java.lang.String r5 = "gtm_hits"
            r4 = 3
            java.lang.String[] r6 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            java.lang.String r4 = "hit_id"
            r14 = 0
            r6[r14] = r4     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            java.lang.String r4 = "hit_time"
            r15 = 1
            r6[r15] = r4     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            java.lang.String r4 = "hit_first_send_time"
            r12 = 2
            r6[r12] = r4     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r4 = "%s ASC"
            java.lang.Object[] r11 = new java.lang.Object[r15]     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            java.lang.String r16 = "hit_id"
            r11[r14] = r16     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            java.lang.String r11 = java.lang.String.format(r4, r11)     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            java.lang.String r16 = java.lang.Integer.toString(r19)     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            r4 = r2
            r13 = 2
            r12 = r16
            android.database.Cursor r12 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ SQLiteException -> 0x016d, all -> 0x0169 }
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x0162, all -> 0x015b }
            r11.<init>()     // Catch:{ SQLiteException -> 0x0162, all -> 0x015b }
            boolean r1 = r12.moveToFirst()     // Catch:{ SQLiteException -> 0x0153, all -> 0x015b }
            if (r1 == 0) goto L_0x0071
        L_0x004b:
            com.google.android.gms.tagmanager.zzas r1 = new com.google.android.gms.tagmanager.zzas     // Catch:{ SQLiteException -> 0x006c, all -> 0x0067 }
            long r5 = r12.getLong(r14)     // Catch:{ SQLiteException -> 0x006c, all -> 0x0067 }
            long r7 = r12.getLong(r15)     // Catch:{ SQLiteException -> 0x006c, all -> 0x0067 }
            long r9 = r12.getLong(r13)     // Catch:{ SQLiteException -> 0x006c, all -> 0x0067 }
            r4 = r1
            r4.<init>(r5, r7, r9)     // Catch:{ SQLiteException -> 0x006c, all -> 0x0067 }
            r11.add(r1)     // Catch:{ SQLiteException -> 0x006c, all -> 0x0067 }
            boolean r1 = r12.moveToNext()     // Catch:{ SQLiteException -> 0x006c, all -> 0x0067 }
            if (r1 != 0) goto L_0x004b
            goto L_0x0071
        L_0x0067:
            r0 = move-exception
            r1 = r0
            r13 = r12
            goto L_0x0196
        L_0x006c:
            r0 = move-exception
            r1 = r0
            r13 = r12
            goto L_0x0171
        L_0x0071:
            if (r12 == 0) goto L_0x0076
            r12.close()
        L_0x0076:
            java.lang.String r5 = "gtm_hits"
            java.lang.String[] r6 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x00fc, all -> 0x00f7 }
            java.lang.String r1 = "hit_id"
            r6[r14] = r1     // Catch:{ SQLiteException -> 0x00fc, all -> 0x00f7 }
            java.lang.String r1 = "hit_url"
            r6[r15] = r1     // Catch:{ SQLiteException -> 0x00fc, all -> 0x00f7 }
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r1 = "%s ASC"
            java.lang.Object[] r4 = new java.lang.Object[r15]     // Catch:{ SQLiteException -> 0x00fc, all -> 0x00f7 }
            java.lang.String r13 = "hit_id"
            r4[r14] = r13     // Catch:{ SQLiteException -> 0x00fc, all -> 0x00f7 }
            java.lang.String r1 = java.lang.String.format(r1, r4)     // Catch:{ SQLiteException -> 0x00fc, all -> 0x00f7 }
            java.lang.String r13 = java.lang.Integer.toString(r19)     // Catch:{ SQLiteException -> 0x00fc, all -> 0x00f7 }
            r4 = r2
            r2 = r11
            r11 = r1
            r16 = r12
            r12 = r13
            android.database.Cursor r12 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ SQLiteException -> 0x00f2, all -> 0x00ed }
            boolean r1 = r12.moveToFirst()     // Catch:{ SQLiteException -> 0x00eb }
            if (r1 == 0) goto L_0x00e5
            r1 = 0
        L_0x00a7:
            r4 = r12
            android.database.sqlite.SQLiteCursor r4 = (android.database.sqlite.SQLiteCursor) r4     // Catch:{ SQLiteException -> 0x00eb }
            android.database.CursorWindow r4 = r4.getWindow()     // Catch:{ SQLiteException -> 0x00eb }
            int r4 = r4.getNumRows()     // Catch:{ SQLiteException -> 0x00eb }
            if (r4 <= 0) goto L_0x00c2
            java.lang.Object r4 = r2.get(r1)     // Catch:{ SQLiteException -> 0x00eb }
            com.google.android.gms.tagmanager.zzas r4 = (com.google.android.gms.tagmanager.zzas) r4     // Catch:{ SQLiteException -> 0x00eb }
            java.lang.String r5 = r12.getString(r15)     // Catch:{ SQLiteException -> 0x00eb }
            r4.a(r5)     // Catch:{ SQLiteException -> 0x00eb }
            goto L_0x00dd
        L_0x00c2:
            java.lang.String r4 = "HitString for hitId %d too large.  Hit will be deleted."
            java.lang.Object[] r5 = new java.lang.Object[r15]     // Catch:{ SQLiteException -> 0x00eb }
            java.lang.Object r6 = r2.get(r1)     // Catch:{ SQLiteException -> 0x00eb }
            com.google.android.gms.tagmanager.zzas r6 = (com.google.android.gms.tagmanager.zzas) r6     // Catch:{ SQLiteException -> 0x00eb }
            long r6 = r6.a()     // Catch:{ SQLiteException -> 0x00eb }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ SQLiteException -> 0x00eb }
            r5[r14] = r6     // Catch:{ SQLiteException -> 0x00eb }
            java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch:{ SQLiteException -> 0x00eb }
            com.google.android.gms.tagmanager.zzbo.zzdf(r4)     // Catch:{ SQLiteException -> 0x00eb }
        L_0x00dd:
            int r1 = r1 + 1
            boolean r4 = r12.moveToNext()     // Catch:{ SQLiteException -> 0x00eb }
            if (r4 != 0) goto L_0x00a7
        L_0x00e5:
            if (r12 == 0) goto L_0x00ea
            r12.close()
        L_0x00ea:
            return r2
        L_0x00eb:
            r0 = move-exception
            goto L_0x0100
        L_0x00ed:
            r0 = move-exception
            r1 = r0
            r12 = r16
            goto L_0x014d
        L_0x00f2:
            r0 = move-exception
            r1 = r0
            r12 = r16
            goto L_0x0101
        L_0x00f7:
            r0 = move-exception
            r16 = r12
        L_0x00fa:
            r1 = r0
            goto L_0x014d
        L_0x00fc:
            r0 = move-exception
            r2 = r11
            r16 = r12
        L_0x0100:
            r1 = r0
        L_0x0101:
            java.lang.String r4 = "Error in peekHits fetching hit url: "
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x014b }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x014b }
            int r5 = r1.length()     // Catch:{ all -> 0x014b }
            if (r5 == 0) goto L_0x0116
            java.lang.String r1 = r4.concat(r1)     // Catch:{ all -> 0x014b }
            goto L_0x011b
        L_0x0116:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x014b }
            r1.<init>(r4)     // Catch:{ all -> 0x014b }
        L_0x011b:
            com.google.android.gms.tagmanager.zzbo.zzdf(r1)     // Catch:{ all -> 0x014b }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x014b }
            r1.<init>()     // Catch:{ all -> 0x014b }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x014b }
        L_0x0127:
            boolean r4 = r2.hasNext()     // Catch:{ all -> 0x014b }
            if (r4 == 0) goto L_0x0145
            java.lang.Object r4 = r2.next()     // Catch:{ all -> 0x014b }
            com.google.android.gms.tagmanager.zzas r4 = (com.google.android.gms.tagmanager.zzas) r4     // Catch:{ all -> 0x014b }
            java.lang.String r5 = r4.c()     // Catch:{ all -> 0x014b }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x014b }
            if (r5 == 0) goto L_0x0141
            if (r14 == 0) goto L_0x0140
            goto L_0x0145
        L_0x0140:
            r14 = 1
        L_0x0141:
            r1.add(r4)     // Catch:{ all -> 0x014b }
            goto L_0x0127
        L_0x0145:
            if (r12 == 0) goto L_0x014a
            r12.close()
        L_0x014a:
            return r1
        L_0x014b:
            r0 = move-exception
            goto L_0x00fa
        L_0x014d:
            if (r12 == 0) goto L_0x0152
            r12.close()
        L_0x0152:
            throw r1
        L_0x0153:
            r0 = move-exception
            r2 = r11
            r16 = r12
            r1 = r0
            r13 = r16
            goto L_0x0171
        L_0x015b:
            r0 = move-exception
            r16 = r12
            r1 = r0
            r13 = r16
            goto L_0x0196
        L_0x0162:
            r0 = move-exception
            r16 = r12
            r11 = r1
            r13 = r16
            goto L_0x0170
        L_0x0169:
            r0 = move-exception
            r1 = r0
            r13 = 0
            goto L_0x0196
        L_0x016d:
            r0 = move-exception
            r11 = r1
            r13 = 0
        L_0x0170:
            r1 = r0
        L_0x0171:
            java.lang.String r2 = "Error in peekHits fetching hitIds: "
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x0194 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0194 }
            int r4 = r1.length()     // Catch:{ all -> 0x0194 }
            if (r4 == 0) goto L_0x0186
            java.lang.String r1 = r2.concat(r1)     // Catch:{ all -> 0x0194 }
            goto L_0x018b
        L_0x0186:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x0194 }
            r1.<init>(r2)     // Catch:{ all -> 0x0194 }
        L_0x018b:
            com.google.android.gms.tagmanager.zzbo.zzdf(r1)     // Catch:{ all -> 0x0194 }
            if (r13 == 0) goto L_0x0193
            r13.close()
        L_0x0193:
            return r11
        L_0x0194:
            r0 = move-exception
            r1 = r0
        L_0x0196:
            if (r13 == 0) goto L_0x019b
            r13.close()
        L_0x019b:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcg.b(int):java.util.List");
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int c() {
        /*
            r4 = this;
            java.lang.String r0 = "Error opening database for getNumStoredHits."
            android.database.sqlite.SQLiteDatabase r0 = r4.a(r0)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            r2 = 0
            java.lang.String r3 = "SELECT COUNT(*) from gtm_hits"
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
            java.lang.String r0 = "Error getting numStoredHits"
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcg.c():int");
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0042  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int d() {
        /*
            r10 = this;
            java.lang.String r0 = "Error opening database for getNumStoredHits."
            android.database.sqlite.SQLiteDatabase r1 = r10.a(r0)
            r0 = 0
            if (r1 != 0) goto L_0x000a
            return r0
        L_0x000a:
            r9 = 0
            java.lang.String r2 = "gtm_hits"
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x0035 }
            java.lang.String r4 = "hit_id"
            r3[r0] = r4     // Catch:{ SQLiteException -> 0x0035 }
            r4 = 1
            java.lang.String r5 = "hit_first_send_time"
            r3[r4] = r5     // Catch:{ SQLiteException -> 0x0035 }
            java.lang.String r4 = "hit_first_send_time=0"
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x0035 }
            int r2 = r1.getCount()     // Catch:{ SQLiteException -> 0x0031, all -> 0x002e }
            if (r1 == 0) goto L_0x002c
            r1.close()
        L_0x002c:
            r0 = r2
            return r0
        L_0x002e:
            r0 = move-exception
            r9 = r1
            goto L_0x0040
        L_0x0031:
            r9 = r1
            goto L_0x0035
        L_0x0033:
            r0 = move-exception
            goto L_0x0040
        L_0x0035:
            java.lang.String r1 = "Error getting num untried hits"
            com.google.android.gms.tagmanager.zzbo.zzdf(r1)     // Catch:{ all -> 0x0033 }
            if (r9 == 0) goto L_0x003f
            r9.close()
        L_0x003f:
            return r0
        L_0x0040:
            if (r9 == 0) goto L_0x0045
            r9.close()
        L_0x0045:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcg.d():int");
    }
}
