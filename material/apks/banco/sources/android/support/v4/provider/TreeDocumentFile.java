package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;

@RequiresApi(21)
class TreeDocumentFile extends DocumentFile {
    private Context a;
    private Uri b;

    TreeDocumentFile(DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.a = context;
        this.b = uri;
    }

    public DocumentFile createFile(String str, String str2) {
        Uri a2 = a(this.a, this.b, str, str2);
        if (a2 != null) {
            return new TreeDocumentFile(this, this.a, a2);
        }
        return null;
    }

    private static Uri a(Context context, Uri uri, String str, String str2) {
        try {
            return DocumentsContract.createDocument(context.getContentResolver(), uri, str, str2);
        } catch (Exception unused) {
            return null;
        }
    }

    public DocumentFile createDirectory(String str) {
        Uri a2 = a(this.a, this.b, "vnd.android.document/directory", str);
        if (a2 != null) {
            return new TreeDocumentFile(this, this.a, a2);
        }
        return null;
    }

    public Uri getUri() {
        return this.b;
    }

    public String getName() {
        return DocumentsContractApi19.c(this.a, this.b);
    }

    public String getType() {
        return DocumentsContractApi19.d(this.a, this.b);
    }

    public boolean isDirectory() {
        return DocumentsContractApi19.f(this.a, this.b);
    }

    public boolean isFile() {
        return DocumentsContractApi19.g(this.a, this.b);
    }

    public boolean isVirtual() {
        return DocumentsContractApi19.b(this.a, this.b);
    }

    public long lastModified() {
        return DocumentsContractApi19.h(this.a, this.b);
    }

    public long length() {
        return DocumentsContractApi19.i(this.a, this.b);
    }

    public boolean canRead() {
        return DocumentsContractApi19.j(this.a, this.b);
    }

    public boolean canWrite() {
        return DocumentsContractApi19.k(this.a, this.b);
    }

    public boolean delete() {
        try {
            return DocumentsContract.deleteDocument(this.a.getContentResolver(), this.b);
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean exists() {
        return DocumentsContractApi19.l(this.a, this.b);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0073 A[LOOP:1: B:19:0x0070->B:21:0x0073, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.support.v4.provider.DocumentFile[] listFiles() {
        /*
            r9 = this;
            android.content.Context r0 = r9.a
            android.content.ContentResolver r1 = r0.getContentResolver()
            android.net.Uri r0 = r9.b
            android.net.Uri r2 = r9.b
            java.lang.String r2 = android.provider.DocumentsContract.getDocumentId(r2)
            android.net.Uri r2 = android.provider.DocumentsContract.buildChildDocumentsUriUsingTree(r0, r2)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3 = 1
            r7 = 0
            r8 = 0
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x0047 }
            java.lang.String r4 = "document_id"
            r3[r7] = r4     // Catch:{ Exception -> 0x0047 }
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x0047 }
        L_0x0027:
            boolean r2 = r1.moveToNext()     // Catch:{ Exception -> 0x0042, all -> 0x003f }
            if (r2 == 0) goto L_0x003b
            java.lang.String r2 = r1.getString(r7)     // Catch:{ Exception -> 0x0042, all -> 0x003f }
            android.net.Uri r3 = r9.b     // Catch:{ Exception -> 0x0042, all -> 0x003f }
            android.net.Uri r2 = android.provider.DocumentsContract.buildDocumentUriUsingTree(r3, r2)     // Catch:{ Exception -> 0x0042, all -> 0x003f }
            r0.add(r2)     // Catch:{ Exception -> 0x0042, all -> 0x003f }
            goto L_0x0027
        L_0x003b:
            a(r1)
            goto L_0x0061
        L_0x003f:
            r0 = move-exception
            r8 = r1
            goto L_0x0082
        L_0x0042:
            r2 = move-exception
            r8 = r1
            goto L_0x0048
        L_0x0045:
            r0 = move-exception
            goto L_0x0082
        L_0x0047:
            r2 = move-exception
        L_0x0048:
            java.lang.String r1 = "DocumentFile"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0045 }
            r3.<init>()     // Catch:{ all -> 0x0045 }
            java.lang.String r4 = "Failed query: "
            r3.append(r4)     // Catch:{ all -> 0x0045 }
            r3.append(r2)     // Catch:{ all -> 0x0045 }
            java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x0045 }
            android.util.Log.w(r1, r2)     // Catch:{ all -> 0x0045 }
            a(r8)
        L_0x0061:
            int r1 = r0.size()
            android.net.Uri[] r1 = new android.net.Uri[r1]
            java.lang.Object[] r0 = r0.toArray(r1)
            android.net.Uri[] r0 = (android.net.Uri[]) r0
            int r1 = r0.length
            android.support.v4.provider.DocumentFile[] r1 = new android.support.v4.provider.DocumentFile[r1]
        L_0x0070:
            int r2 = r0.length
            if (r7 >= r2) goto L_0x0081
            android.support.v4.provider.TreeDocumentFile r2 = new android.support.v4.provider.TreeDocumentFile
            android.content.Context r3 = r9.a
            r4 = r0[r7]
            r2.<init>(r9, r3, r4)
            r1[r7] = r2
            int r7 = r7 + 1
            goto L_0x0070
        L_0x0081:
            return r1
        L_0x0082:
            a(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.provider.TreeDocumentFile.listFiles():android.support.v4.provider.DocumentFile[]");
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

    public boolean renameTo(String str) {
        try {
            Uri renameDocument = DocumentsContract.renameDocument(this.a.getContentResolver(), this.b, str);
            if (renameDocument == null) {
                return false;
            }
            this.b = renameDocument;
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
