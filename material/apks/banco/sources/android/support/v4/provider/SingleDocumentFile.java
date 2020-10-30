package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;

@RequiresApi(19)
class SingleDocumentFile extends DocumentFile {
    private Context a;
    private Uri b;

    SingleDocumentFile(DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.a = context;
        this.b = uri;
    }

    public DocumentFile createFile(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    public DocumentFile createDirectory(String str) {
        throw new UnsupportedOperationException();
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

    public DocumentFile[] listFiles() {
        throw new UnsupportedOperationException();
    }

    public boolean renameTo(String str) {
        throw new UnsupportedOperationException();
    }
}
