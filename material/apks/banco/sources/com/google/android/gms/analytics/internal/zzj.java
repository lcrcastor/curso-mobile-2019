package com.google.android.gms.analytics.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzn;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzj extends zzd implements Closeable {
    /* access modifiers changed from: private */
    public static final String a = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{"hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id"});
    private static final String b = String.format("SELECT MAX(%s) FROM %s WHERE 1;", new Object[]{"hit_time", "hits2"});
    private final zza c;
    private final zzal d = new zzal(zzaan());
    /* access modifiers changed from: private */
    public final zzal e = new zzal(zzaan());

    class zza extends SQLiteOpenHelper {
        zza(Context context, String str) {
            super(context, str, null, 1);
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            Set b = b(sQLiteDatabase, "hits2");
            String[] strArr = {"hit_id", "hit_string", "hit_time", "hit_url"};
            for (int i = 0; i < 4; i++) {
                String str = strArr[i];
                if (!b.remove(str)) {
                    String str2 = "Database hits2 is missing required column: ";
                    String valueOf = String.valueOf(str);
                    throw new SQLiteException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
            boolean z = !b.remove("hit_app_id");
            if (!b.isEmpty()) {
                throw new SQLiteException("Database hits2 has extra columns");
            } else if (z) {
                sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id INTEGER");
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x0037  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x003d  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a(android.database.sqlite.SQLiteDatabase r12, java.lang.String r13) {
            /*
                r11 = this;
                r0 = 0
                r1 = 0
                java.lang.String r3 = "SQLITE_MASTER"
                r2 = 1
                java.lang.String[] r4 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x002d }
                java.lang.String r5 = "name"
                r4[r0] = r5     // Catch:{ SQLiteException -> 0x002d }
                java.lang.String r5 = "name=?"
                java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x002d }
                r6[r0] = r13     // Catch:{ SQLiteException -> 0x002d }
                r7 = 0
                r8 = 0
                r9 = 0
                r2 = r12
                android.database.Cursor r12 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x002d }
                boolean r1 = r12.moveToFirst()     // Catch:{ SQLiteException -> 0x0026, all -> 0x0023 }
                if (r12 == 0) goto L_0x0022
                r12.close()
            L_0x0022:
                return r1
            L_0x0023:
                r13 = move-exception
                r1 = r12
                goto L_0x003b
            L_0x0026:
                r1 = move-exception
                r10 = r1
                r1 = r12
                r12 = r10
                goto L_0x002e
            L_0x002b:
                r13 = move-exception
                goto L_0x003b
            L_0x002d:
                r12 = move-exception
            L_0x002e:
                com.google.android.gms.analytics.internal.zzj r2 = com.google.android.gms.analytics.internal.zzj.this     // Catch:{ all -> 0x002b }
                java.lang.String r3 = "Error querying for table"
                r2.zzc(r3, r13, r12)     // Catch:{ all -> 0x002b }
                if (r1 == 0) goto L_0x003a
                r1.close()
            L_0x003a:
                return r0
            L_0x003b:
                if (r1 == 0) goto L_0x0040
                r1.close()
            L_0x0040:
                throw r13
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.zza.a(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
        }

        private Set<String> b(SQLiteDatabase sQLiteDatabase, String str) {
            HashSet hashSet = new HashSet();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 22);
            sb.append("SELECT * FROM ");
            sb.append(str);
            sb.append(" LIMIT 0");
            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (String add : columnNames) {
                    hashSet.add(add);
                }
                return hashSet;
            } finally {
                rawQuery.close();
            }
        }

        private void b(SQLiteDatabase sQLiteDatabase) {
            Set b = b(sQLiteDatabase, "properties");
            String[] strArr = {"app_uid", "cid", "tid", "params", "adid", "hits_count"};
            for (int i = 0; i < 6; i++) {
                String str = strArr[i];
                if (!b.remove(str)) {
                    String str2 = "Database properties is missing required column: ";
                    String valueOf = String.valueOf(str);
                    throw new SQLiteException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
            if (!b.isEmpty()) {
                throw new SQLiteException("Database properties table has extra columns");
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            if (!zzj.this.e.a(3600000)) {
                throw new SQLiteException("Database open failed");
            }
            try {
                return super.getWritableDatabase();
            } catch (SQLiteException unused) {
                zzj.this.e.a();
                zzj.this.zzet("Opening the database failed, dropping the table and recreating it");
                zzj.this.getContext().getDatabasePath(zzj.this.m()).delete();
                try {
                    SQLiteDatabase writableDatabase = super.getWritableDatabase();
                    zzj.this.e.b();
                    return writableDatabase;
                } catch (SQLiteException e) {
                    zzj.this.zze("Failed to open freshly created database", e);
                    throw e;
                }
            }
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            zzx.zzfa(sQLiteDatabase.getPath());
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
            if (!a(sQLiteDatabase, "hits2")) {
                sQLiteDatabase.execSQL(zzj.a);
            } else {
                a(sQLiteDatabase);
            }
            if (!a(sQLiteDatabase, "properties")) {
                sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS properties ( app_uid INTEGER NOT NULL, cid TEXT NOT NULL, tid TEXT NOT NULL, params TEXT NOT NULL, adid INTEGER NOT NULL, hits_count INTEGER NOT NULL, PRIMARY KEY (app_uid, cid, tid)) ;");
            } else {
                b(sQLiteDatabase);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    zzj(zzf zzf) {
        super(zzf);
        this.c = new zza(zzf.getContext(), m());
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0033  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long a(java.lang.String r3, java.lang.String[] r4) {
        /*
            r2 = this;
            android.database.sqlite.SQLiteDatabase r0 = r2.j()
            r1 = 0
            android.database.Cursor r4 = r0.rawQuery(r3, r4)     // Catch:{ SQLiteException -> 0x002a }
            boolean r0 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x0024, all -> 0x0022 }
            if (r0 == 0) goto L_0x001a
            r0 = 0
            long r0 = r4.getLong(r0)     // Catch:{ SQLiteException -> 0x0024, all -> 0x0022 }
            if (r4 == 0) goto L_0x0019
            r4.close()
        L_0x0019:
            return r0
        L_0x001a:
            android.database.sqlite.SQLiteException r0 = new android.database.sqlite.SQLiteException     // Catch:{ SQLiteException -> 0x0024, all -> 0x0022 }
            java.lang.String r1 = "Database returned empty set"
            r0.<init>(r1)     // Catch:{ SQLiteException -> 0x0024, all -> 0x0022 }
            throw r0     // Catch:{ SQLiteException -> 0x0024, all -> 0x0022 }
        L_0x0022:
            r3 = move-exception
            goto L_0x0031
        L_0x0024:
            r0 = move-exception
            r1 = r4
            goto L_0x002b
        L_0x0027:
            r3 = move-exception
            r4 = r1
            goto L_0x0031
        L_0x002a:
            r0 = move-exception
        L_0x002b:
            java.lang.String r4 = "Database error"
            r2.zzd(r4, r3, r0)     // Catch:{ all -> 0x0027 }
            throw r0     // Catch:{ all -> 0x0027 }
        L_0x0031:
            if (r4 == 0) goto L_0x0036
            r4.close()
        L_0x0036:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.a(java.lang.String, java.lang.String[]):long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0031  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long a(java.lang.String r3, java.lang.String[] r4, long r5) {
        /*
            r2 = this;
            android.database.sqlite.SQLiteDatabase r0 = r2.j()
            r1 = 0
            android.database.Cursor r4 = r0.rawQuery(r3, r4)     // Catch:{ SQLiteException -> 0x0028 }
            boolean r0 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x0023, all -> 0x0020 }
            if (r0 == 0) goto L_0x001a
            r5 = 0
            long r5 = r4.getLong(r5)     // Catch:{ SQLiteException -> 0x0023, all -> 0x0020 }
            if (r4 == 0) goto L_0x0019
            r4.close()
        L_0x0019:
            return r5
        L_0x001a:
            if (r4 == 0) goto L_0x001f
            r4.close()
        L_0x001f:
            return r5
        L_0x0020:
            r3 = move-exception
            r1 = r4
            goto L_0x002f
        L_0x0023:
            r5 = move-exception
            r1 = r4
            goto L_0x0029
        L_0x0026:
            r3 = move-exception
            goto L_0x002f
        L_0x0028:
            r5 = move-exception
        L_0x0029:
            java.lang.String r4 = "Database error"
            r2.zzd(r4, r3, r5)     // Catch:{ all -> 0x0026 }
            throw r5     // Catch:{ all -> 0x0026 }
        L_0x002f:
            if (r1 == 0) goto L_0x0034
            r1.close()
        L_0x0034:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.a(java.lang.String, java.lang.String[], long):long");
    }

    private static String a(Map<String, String> map) {
        zzac.zzy(map);
        Builder builder = new Builder();
        for (Entry entry : map.entrySet()) {
            builder.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        String encodedQuery = builder.build().getEncodedQuery();
        return encodedQuery == null ? "" : encodedQuery;
    }

    private String b(zzab zzab) {
        return zzab.zzaes() ? zzaap().zzadh() : zzaap().zzadi();
    }

    private static String c(zzab zzab) {
        zzac.zzy(zzab);
        Builder builder = new Builder();
        for (Entry entry : zzab.zzm().entrySet()) {
            String str = (String) entry.getKey();
            if (!"ht".equals(str) && !"qt".equals(str) && !"AppUID".equals(str)) {
                builder.appendQueryParameter(str, (String) entry.getValue());
            }
        }
        String encodedQuery = builder.build().getEncodedQuery();
        return encodedQuery == null ? "" : encodedQuery;
    }

    private void l() {
        int zzadr = zzaap().zzadr();
        long f = f();
        if (f > ((long) (zzadr - 1))) {
            List a2 = a((f - ((long) zzadr)) + 1);
            zzd("Store full, deleting hits to make room, count", Integer.valueOf(a2.size()));
            a(a2);
        }
    }

    /* access modifiers changed from: private */
    public String m() {
        return !zzaap().zzact() ? zzaap().zzadt() : zzaap().zzacu() ? zzaap().zzadt() : zzaap().zzadu();
    }

    public long a(long j, String str, String str2) {
        zzac.zzhz(str);
        zzac.zzhz(str2);
        zzaax();
        zzyl();
        return a("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(j), str, str2}, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0077  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.lang.Long> a(long r14) {
        /*
            r13 = this;
            r13.zzyl()
            r13.zzaax()
            r0 = 0
            int r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r2 > 0) goto L_0x0011
            java.util.List r14 = java.util.Collections.emptyList()
            return r14
        L_0x0011:
            android.database.sqlite.SQLiteDatabase r0 = r13.j()
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r10 = 0
            java.lang.String r1 = "hits2"
            r2 = 1
            java.lang.String[] r3 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x0069 }
            java.lang.String r4 = "hit_id"
            r11 = 0
            r3[r11] = r4     // Catch:{ SQLiteException -> 0x0069 }
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.String r8 = "%s ASC"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ SQLiteException -> 0x0069 }
            java.lang.String r12 = "hit_id"
            r2[r11] = r12     // Catch:{ SQLiteException -> 0x0069 }
            java.lang.String r8 = java.lang.String.format(r8, r2)     // Catch:{ SQLiteException -> 0x0069 }
            java.lang.String r14 = java.lang.Long.toString(r14)     // Catch:{ SQLiteException -> 0x0069 }
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r14
            android.database.Cursor r14 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x0069 }
            boolean r15 = r14.moveToFirst()     // Catch:{ SQLiteException -> 0x0064, all -> 0x0061 }
            if (r15 == 0) goto L_0x005b
        L_0x004a:
            long r0 = r14.getLong(r11)     // Catch:{ SQLiteException -> 0x0064, all -> 0x0061 }
            java.lang.Long r15 = java.lang.Long.valueOf(r0)     // Catch:{ SQLiteException -> 0x0064, all -> 0x0061 }
            r9.add(r15)     // Catch:{ SQLiteException -> 0x0064, all -> 0x0061 }
            boolean r15 = r14.moveToNext()     // Catch:{ SQLiteException -> 0x0064, all -> 0x0061 }
            if (r15 != 0) goto L_0x004a
        L_0x005b:
            if (r14 == 0) goto L_0x0074
            r14.close()
            return r9
        L_0x0061:
            r15 = move-exception
            r10 = r14
            goto L_0x0075
        L_0x0064:
            r15 = move-exception
            r10 = r14
            goto L_0x006a
        L_0x0067:
            r15 = move-exception
            goto L_0x0075
        L_0x0069:
            r15 = move-exception
        L_0x006a:
            java.lang.String r14 = "Error selecting hit ids"
            r13.zzd(r14, r15)     // Catch:{ all -> 0x0067 }
            if (r10 == 0) goto L_0x0074
            r10.close()
        L_0x0074:
            return r9
        L_0x0075:
            if (r10 == 0) goto L_0x007a
            r10.close()
        L_0x007a:
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.a(long):java.util.List");
    }

    /* access modifiers changed from: 0000 */
    public Map<String, String> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith("?")) {
                String str2 = "?";
                String valueOf = String.valueOf(str);
                str = valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2);
            }
            return zzn.zza(new URI(str), "UTF-8");
        } catch (URISyntaxException e2) {
            zze("Error parsing hit parameters", e2);
            return new HashMap(0);
        }
    }

    public void a() {
        zzaax();
        j().beginTransaction();
    }

    public void a(long j, String str) {
        zzac.zzhz(str);
        zzaax();
        zzyl();
        int delete = j().delete("properties", "app_uid=? AND cid<>?", new String[]{String.valueOf(j), str});
        if (delete > 0) {
            zza("Deleted property records", Integer.valueOf(delete));
        }
    }

    public void a(zzab zzab) {
        zzac.zzy(zzab);
        zzyl();
        zzaax();
        String c2 = c(zzab);
        if (c2.length() > 8192) {
            zzaao().zza(zzab, "Hit length exceeds the maximum allowed size");
            return;
        }
        l();
        SQLiteDatabase j = j();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", c2);
        contentValues.put("hit_time", Long.valueOf(zzab.zzaeq()));
        contentValues.put("hit_app_id", Integer.valueOf(zzab.zzaeo()));
        contentValues.put("hit_url", b(zzab));
        try {
            long insert = j.insert("hits2", null, contentValues);
            if (insert == -1) {
                zzet("Failed to insert a hit (got -1)");
            } else {
                zzb("Hit saved to database. db-id, hit", Long.valueOf(insert), zzab);
            }
        } catch (SQLiteException e2) {
            zze("Error storing a hit", e2);
        }
    }

    public void a(zzh zzh) {
        zzac.zzy(zzh);
        zzaax();
        zzyl();
        SQLiteDatabase j = j();
        String a2 = a(zzh.zzm());
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_uid", Long.valueOf(zzh.zzabf()));
        contentValues.put("cid", zzh.zzxs());
        contentValues.put("tid", zzh.zzabg());
        contentValues.put("adid", Integer.valueOf(zzh.zzabh() ? 1 : 0));
        contentValues.put("hits_count", Long.valueOf(zzh.zzabi()));
        contentValues.put("params", a2);
        try {
            if (j.insertWithOnConflict("properties", null, contentValues, 5) == -1) {
                zzet("Failed to insert/update a property (got -1)");
            }
        } catch (SQLiteException e2) {
            zze("Error storing a property", e2);
        }
    }

    public void a(List<Long> list) {
        zzac.zzy(list);
        zzyl();
        zzaax();
        if (!list.isEmpty()) {
            StringBuilder sb = new StringBuilder("hit_id");
            sb.append(" in (");
            for (int i = 0; i < list.size(); i++) {
                Long l = (Long) list.get(i);
                if (l == null || l.longValue() == 0) {
                    throw new SQLiteException("Invalid hit id");
                }
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(l);
            }
            sb.append(")");
            String sb2 = sb.toString();
            try {
                SQLiteDatabase j = j();
                zza("Deleting dispatched hits. count", Integer.valueOf(list.size()));
                int delete = j.delete("hits2", sb2, null);
                if (delete != list.size()) {
                    zzb("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(delete), sb2);
                }
            } catch (SQLiteException e2) {
                zze("Error deleting hits", e2);
                throw e2;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.gms.analytics.internal.zzab> b(long r24) {
        /*
            r23 = this;
            r10 = r23
            r1 = 0
            int r5 = (r24 > r1 ? 1 : (r24 == r1 ? 0 : -1))
            r11 = 0
            r12 = 1
            if (r5 < 0) goto L_0x000c
            r1 = 1
            goto L_0x000d
        L_0x000c:
            r1 = 0
        L_0x000d:
            com.google.android.gms.common.internal.zzac.zzbs(r1)
            r23.zzyl()
            r23.zzaax()
            android.database.sqlite.SQLiteDatabase r13 = r23.j()
            r1 = 0
            java.lang.String r14 = "hits2"
            r2 = 5
            java.lang.String[] r15 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.lang.String r2 = "hit_id"
            r15[r11] = r2     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.lang.String r2 = "hit_time"
            r15[r12] = r2     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.lang.String r2 = "hit_string"
            r9 = 2
            r15[r9] = r2     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.lang.String r2 = "hit_url"
            r7 = 3
            r15[r7] = r2     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.lang.String r2 = "hit_app_id"
            r8 = 4
            r15[r8] = r2     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            java.lang.String r2 = "%s ASC"
            java.lang.Object[] r5 = new java.lang.Object[r12]     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.lang.String r6 = "hit_id"
            r5[r11] = r6     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.lang.String r20 = java.lang.String.format(r2, r5)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.lang.String r21 = java.lang.Long.toString(r24)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            android.database.Cursor r13 = r13.query(r14, r15, r16, r17, r18, r19, r20, r21)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x00a1 }
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x009f }
            r14.<init>()     // Catch:{ SQLiteException -> 0x009f }
            boolean r1 = r13.moveToFirst()     // Catch:{ SQLiteException -> 0x009f }
            if (r1 == 0) goto L_0x0099
        L_0x005e:
            long r15 = r13.getLong(r11)     // Catch:{ SQLiteException -> 0x009f }
            long r4 = r13.getLong(r12)     // Catch:{ SQLiteException -> 0x009f }
            java.lang.String r1 = r13.getString(r9)     // Catch:{ SQLiteException -> 0x009f }
            java.lang.String r2 = r13.getString(r7)     // Catch:{ SQLiteException -> 0x009f }
            int r17 = r13.getInt(r8)     // Catch:{ SQLiteException -> 0x009f }
            java.util.Map r3 = r10.a(r1)     // Catch:{ SQLiteException -> 0x009f }
            boolean r6 = com.google.android.gms.analytics.internal.zzao.zzfk(r2)     // Catch:{ SQLiteException -> 0x009f }
            com.google.android.gms.analytics.internal.zzab r2 = new com.google.android.gms.analytics.internal.zzab     // Catch:{ SQLiteException -> 0x009f }
            r1 = r2
            r11 = r2
            r2 = r10
            r18 = 3
            r19 = 4
            r7 = r15
            r15 = 2
            r9 = r17
            r1.<init>(r2, r3, r4, r6, r7, r9)     // Catch:{ SQLiteException -> 0x009f }
            r14.add(r11)     // Catch:{ SQLiteException -> 0x009f }
            boolean r1 = r13.moveToNext()     // Catch:{ SQLiteException -> 0x009f }
            if (r1 != 0) goto L_0x0094
            goto L_0x0099
        L_0x0094:
            r7 = 3
            r8 = 4
            r9 = 2
            r11 = 0
            goto L_0x005e
        L_0x0099:
            if (r13 == 0) goto L_0x009e
            r13.close()
        L_0x009e:
            return r14
        L_0x009f:
            r0 = move-exception
            goto L_0x00a7
        L_0x00a1:
            r0 = move-exception
            r13 = r1
        L_0x00a3:
            r1 = r0
            goto L_0x00b0
        L_0x00a5:
            r0 = move-exception
            r13 = r1
        L_0x00a7:
            r1 = r0
            java.lang.String r2 = "Error loading hits from the database"
            r10.zze(r2, r1)     // Catch:{ all -> 0x00ae }
            throw r1     // Catch:{ all -> 0x00ae }
        L_0x00ae:
            r0 = move-exception
            goto L_0x00a3
        L_0x00b0:
            if (r13 == 0) goto L_0x00b5
            r13.close()
        L_0x00b5:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.b(long):java.util.List");
    }

    /* access modifiers changed from: 0000 */
    public Map<String, String> b(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        String str2 = "?";
        try {
            String valueOf = String.valueOf(str);
            return zzn.zza(new URI(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2)), "UTF-8");
        } catch (URISyntaxException e2) {
            zze("Error parsing property parameters", e2);
            return new HashMap(0);
        }
    }

    public void b() {
        zzaax();
        j().setTransactionSuccessful();
    }

    public void c() {
        zzaax();
        j().endTransaction();
    }

    public void c(long j) {
        zzyl();
        zzaax();
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Long.valueOf(j));
        zza("Deleting hit, id", Long.valueOf(j));
        a((List<Long>) arrayList);
    }

    public void close() {
        String str;
        try {
            this.c.close();
        } catch (SQLiteException e2) {
            e = e2;
            str = "Sql error closing database";
            zze(str, e);
        } catch (IllegalStateException e3) {
            e = e3;
            str = "Error closing database";
            zze(str, e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00cd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.gms.analytics.internal.zzh> d(long r28) {
        /*
            r27 = this;
            r1 = r27
            r27.zzaax()
            r27.zzyl()
            android.database.sqlite.SQLiteDatabase r2 = r27.j()
            r3 = 5
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r3 = "cid"
            r12 = 0
            r4[r12] = r3     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r3 = "tid"
            r13 = 1
            r4[r13] = r3     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r3 = "adid"
            r14 = 2
            r4[r14] = r3     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r3 = "hits_count"
            r15 = 3
            r4[r15] = r3     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r3 = "params"
            r10 = 4
            r4[r10] = r3     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            com.google.android.gms.analytics.internal.zzr r3 = r27.zzaap()     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            int r9 = r3.zzads()     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r16 = java.lang.String.valueOf(r9)     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r5 = "app_uid=?"
            java.lang.String[] r6 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r3 = java.lang.String.valueOf(r28)     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            r6[r12] = r3     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.lang.String r3 = "properties"
            r7 = 0
            r8 = 0
            r17 = 0
            r11 = r9
            r9 = r17
            r10 = r16
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x00c0, all -> 0x00bc }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            r3.<init>()     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            boolean r4 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            if (r4 == 0) goto L_0x00a4
        L_0x0058:
            java.lang.String r4 = r2.getString(r12)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            java.lang.String r5 = r2.getString(r13)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            int r6 = r2.getInt(r14)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            if (r6 == 0) goto L_0x0069
            r23 = 1
            goto L_0x006b
        L_0x0069:
            r23 = 0
        L_0x006b:
            int r6 = r2.getInt(r15)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            long r6 = (long) r6     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            r8 = 4
            java.lang.String r9 = r2.getString(r8)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            java.util.Map r26 = r1.b(r9)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            boolean r9 = android.text.TextUtils.isEmpty(r4)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            if (r9 != 0) goto L_0x0099
            boolean r9 = android.text.TextUtils.isEmpty(r5)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            if (r9 == 0) goto L_0x0086
            goto L_0x0099
        L_0x0086:
            com.google.android.gms.analytics.internal.zzh r9 = new com.google.android.gms.analytics.internal.zzh     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            r18 = r9
            r19 = r28
            r21 = r4
            r22 = r5
            r24 = r6
            r18.<init>(r19, r21, r22, r23, r24, r26)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            r3.add(r9)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            goto L_0x009e
        L_0x0099:
            java.lang.String r6 = "Read property with empty client id or tracker id"
            r1.zzc(r6, r4, r5)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
        L_0x009e:
            boolean r4 = r2.moveToNext()     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            if (r4 != 0) goto L_0x0058
        L_0x00a4:
            int r4 = r3.size()     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
            if (r4 < r11) goto L_0x00af
            java.lang.String r4 = "Sending hits to too many properties. Campaign report might be incorrect"
            r1.zzes(r4)     // Catch:{ SQLiteException -> 0x00b8, all -> 0x00b5 }
        L_0x00af:
            if (r2 == 0) goto L_0x00b4
            r2.close()
        L_0x00b4:
            return r3
        L_0x00b5:
            r0 = move-exception
            r11 = r2
            goto L_0x00ca
        L_0x00b8:
            r0 = move-exception
            r11 = r2
            r2 = r0
            goto L_0x00c3
        L_0x00bc:
            r0 = move-exception
            r2 = r0
            r11 = 0
            goto L_0x00cb
        L_0x00c0:
            r0 = move-exception
            r2 = r0
            r11 = 0
        L_0x00c3:
            java.lang.String r3 = "Error loading hits from the database"
            r1.zze(r3, r2)     // Catch:{ all -> 0x00c9 }
            throw r2     // Catch:{ all -> 0x00c9 }
        L_0x00c9:
            r0 = move-exception
        L_0x00ca:
            r2 = r0
        L_0x00cb:
            if (r11 == 0) goto L_0x00d0
            r11.close()
        L_0x00d0:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.d(long):java.util.List");
    }

    public void d() {
        zzyl();
        zzaax();
        j().delete("hits2", null, null);
    }

    public void e() {
        zzyl();
        zzaax();
        j().delete("properties", null, null);
    }

    public long f() {
        zzyl();
        zzaax();
        return a("SELECT COUNT(*) FROM hits2", (String[]) null);
    }

    /* access modifiers changed from: 0000 */
    public boolean g() {
        return f() == 0;
    }

    public int h() {
        zzyl();
        zzaax();
        if (!this.d.a(86400000)) {
            return 0;
        }
        this.d.a();
        zzep("Deleting stale hits (if any)");
        int delete = j().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzaan().currentTimeMillis() - 2592000000L)});
        zza("Deleted stale hits, count", Integer.valueOf(delete));
        return delete;
    }

    public long i() {
        zzyl();
        zzaax();
        return a(b, (String[]) null, 0);
    }

    /* access modifiers changed from: 0000 */
    public SQLiteDatabase j() {
        try {
            return this.c.getWritableDatabase();
        } catch (SQLiteException e2) {
            zzd("Error opening database", e2);
            throw e2;
        }
    }

    /* access modifiers changed from: protected */
    public void zzym() {
    }
}
