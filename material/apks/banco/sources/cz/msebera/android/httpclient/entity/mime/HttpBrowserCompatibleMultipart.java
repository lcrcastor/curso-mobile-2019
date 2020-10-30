package cz.msebera.android.httpclient.entity.mime;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

class HttpBrowserCompatibleMultipart extends AbstractMultipartForm {
    private final List<FormBodyPart> c;

    public HttpBrowserCompatibleMultipart(Charset charset, String str, List<FormBodyPart> list) {
        super(charset, str);
        this.c = list;
    }

    public List<FormBodyPart> a() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void a(FormBodyPart formBodyPart, OutputStream outputStream) {
        Header header = formBodyPart.getHeader();
        a(header.getField("Content-Disposition"), this.a, outputStream);
        if (formBodyPart.getBody().getFilename() != null) {
            a(header.getField("Content-Type"), this.a, outputStream);
        }
    }
}
