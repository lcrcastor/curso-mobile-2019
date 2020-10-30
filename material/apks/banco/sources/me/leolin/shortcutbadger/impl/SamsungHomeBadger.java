package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.util.CloseHelper;

public class SamsungHomeBadger implements Badger {
    private static final String[] a = {"_id", "class"};
    private DefaultBadger b;

    public SamsungHomeBadger() {
        if (VERSION.SDK_INT >= 21) {
            this.b = new DefaultBadger();
        }
    }

    public void executeBadge(Context context, ComponentName componentName, int i) {
        Cursor cursor;
        if (this.b == null || !this.b.a(context)) {
            Uri parse = Uri.parse("content://com.sec.badge/apps?notify=true");
            ContentResolver contentResolver = context.getContentResolver();
            try {
                ContentResolver contentResolver2 = contentResolver;
                Uri uri = parse;
                cursor = contentResolver2.query(uri, a, "package=?", new String[]{componentName.getPackageName()}, null);
                if (cursor != null) {
                    try {
                        String className = componentName.getClassName();
                        boolean z = false;
                        while (cursor.moveToNext()) {
                            contentResolver.update(parse, a(componentName, i, false), "_id=?", new String[]{String.valueOf(cursor.getInt(0))});
                            if (className.equals(cursor.getString(cursor.getColumnIndex("class")))) {
                                z = true;
                            }
                        }
                        if (!z) {
                            contentResolver.insert(parse, a(componentName, i, true));
                        }
                    } catch (Throwable th) {
                        th = th;
                        CloseHelper.close(cursor);
                        throw th;
                    }
                }
                CloseHelper.close(cursor);
            } catch (Throwable th2) {
                th = th2;
                cursor = null;
                CloseHelper.close(cursor);
                throw th;
            }
        } else {
            this.b.executeBadge(context, componentName, i);
        }
    }

    private ContentValues a(ComponentName componentName, int i, boolean z) {
        ContentValues contentValues = new ContentValues();
        if (z) {
            contentValues.put("package", componentName.getPackageName());
            contentValues.put("class", componentName.getClassName());
        }
        contentValues.put("badgecount", Integer.valueOf(i));
        return contentValues;
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.sec.android.app.launcher", "com.sec.android.app.twlauncher"});
    }
}
