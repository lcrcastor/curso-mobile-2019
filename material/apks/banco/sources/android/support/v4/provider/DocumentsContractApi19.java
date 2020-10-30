package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

@RequiresApi(19)
class DocumentsContractApi19 {
    DocumentsContractApi19() {
    }

    public static boolean a(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri);
    }

    public static boolean b(Context context, Uri uri) {
        boolean z = false;
        if (!a(context, uri)) {
            return false;
        }
        if ((e(context, uri) & 512) != 0) {
            z = true;
        }
        return z;
    }

    public static String c(Context context, Uri uri) {
        return a(context, uri, "_display_name", (String) null);
    }

    private static String m(Context context, Uri uri) {
        return a(context, uri, "mime_type", (String) null);
    }

    public static String d(Context context, Uri uri) {
        String m = m(context, uri);
        if ("vnd.android.document/directory".equals(m)) {
            return null;
        }
        return m;
    }

    public static long e(Context context, Uri uri) {
        return a(context, uri, "flags", 0);
    }

    public static boolean f(Context context, Uri uri) {
        return "vnd.android.document/directory".equals(m(context, uri));
    }

    public static boolean g(Context context, Uri uri) {
        String m = m(context, uri);
        return !"vnd.android.document/directory".equals(m) && !TextUtils.isEmpty(m);
    }

    public static long h(Context context, Uri uri) {
        return a(context, uri, "last_modified", 0);
    }

    public static long i(Context context, Uri uri) {
        return a(context, uri, "_size", 0);
    }

    public static boolean j(Context context, Uri uri) {
        return context.checkCallingOrSelfUriPermission(uri, 1) == 0 && !TextUtils.isEmpty(m(context, uri));
    }

    public static boolean k(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 2) != 0) {
            return false;
        }
        String m = m(context, uri);
        int a = a(context, uri, "flags", 0);
        if (TextUtils.isEmpty(m)) {
            return false;
        }
        if ((a & 4) != 0) {
            return true;
        }
        if ("vnd.android.document/directory".equals(m) && (a & 8) != 0) {
            return true;
        }
        if (TextUtils.isEmpty(m) || (a & 2) == 0) {
            return false;
        }
        return true;
    }

    public static boolean l(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        boolean z = true;
        Cursor cursor = null;
        try {
            Cursor query = contentResolver.query(uri, new String[]{"document_id"}, null, null, null);
            try {
                if (query.getCount() <= 0) {
                    z = false;
                }
                a(query);
                return z;
            } catch (Exception e) {
                e = e;
                cursor = query;
                String str = "DocumentFile";
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed query: ");
                    sb.append(e);
                    Log.w(str, sb.toString());
                    a(cursor);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    a(cursor);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                a(cursor);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            String str2 = "DocumentFile";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed query: ");
            sb2.append(e);
            Log.w(str2, sb2.toString());
            a(cursor);
            return false;
        }
    }

    private static String a(Context context, Uri uri, String str, String str2) {
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            try {
                if (!query.moveToFirst() || query.isNull(0)) {
                    a(query);
                    return str2;
                }
                String string = query.getString(0);
                a(query);
                return string;
            } catch (Exception e) {
                e = e;
                cursor = query;
                String str3 = "DocumentFile";
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed query: ");
                    sb.append(e);
                    Log.w(str3, sb.toString());
                    a(cursor);
                    return str2;
                } catch (Throwable th) {
                    th = th;
                    a(cursor);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                a(cursor);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            String str32 = "DocumentFile";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed query: ");
            sb2.append(e);
            Log.w(str32, sb2.toString());
            a(cursor);
            return str2;
        }
    }

    private static int a(Context context, Uri uri, String str, int i) {
        return (int) a(context, uri, str, (long) i);
    }

    private static long a(Context context, Uri uri, String str, long j) {
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            try {
                if (!query.moveToFirst() || query.isNull(0)) {
                    a(query);
                    return j;
                }
                long j2 = query.getLong(0);
                a(query);
                return j2;
            } catch (Exception e) {
                e = e;
                cursor = query;
                String str2 = "DocumentFile";
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed query: ");
                    sb.append(e);
                    Log.w(str2, sb.toString());
                    a(cursor);
                    return j;
                } catch (Throwable th) {
                    th = th;
                    a(cursor);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = query;
                a(cursor);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            String str22 = "DocumentFile";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed query: ");
            sb2.append(e);
            Log.w(str22, sb2.toString());
            a(cursor);
            return j;
        }
    }

    private static void a(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }
}
