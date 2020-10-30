package com.facebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

public class NativeAppCallContentProvider extends ContentProvider {
    private static final String a = "com.facebook.NativeAppCallContentProvider";
    private final AttachmentDataSource b;

    interface AttachmentDataSource {
        File openAttachment(UUID uuid, String str);
    }

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public NativeAppCallContentProvider() {
        this(new NativeAppCallAttachmentStore());
    }

    NativeAppCallContentProvider(AttachmentDataSource attachmentDataSource) {
        this.b = attachmentDataSource;
    }

    public static String getAttachmentUrl(String str, UUID uuid, String str2) {
        return String.format("%s%s/%s/%s", new Object[]{"content://com.facebook.app.NativeAppCallContentProvider", str, uuid.toString(), str2});
    }

    public ParcelFileDescriptor openFile(Uri uri, String str) {
        Pair a2 = a(uri);
        if (a2 == null) {
            throw new FileNotFoundException();
        }
        try {
            return ParcelFileDescriptor.open(this.b.openAttachment((UUID) a2.first, (String) a2.second), 268435456);
        } catch (FileNotFoundException e) {
            String str2 = a;
            StringBuilder sb = new StringBuilder();
            sb.append("Got unexpected exception:");
            sb.append(e);
            Log.e(str2, sb.toString());
            throw e;
        }
    }

    /* access modifiers changed from: 0000 */
    public Pair<UUID, String> a(Uri uri) {
        try {
            String[] split = uri.getPath().substring(1).split("/");
            String str = split[0];
            return new Pair<>(UUID.fromString(str), split[1]);
        } catch (Exception unused) {
            return null;
        }
    }
}
