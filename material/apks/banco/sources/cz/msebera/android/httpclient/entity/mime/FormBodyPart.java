package cz.msebera.android.httpclient.entity.mime;

import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.content.AbstractContentBody;
import cz.msebera.android.httpclient.entity.mime.content.ContentBody;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.Args;

public class FormBodyPart {
    private final String a;
    private final Header b;
    private final ContentBody c;

    FormBodyPart(String str, ContentBody contentBody, Header header) {
        Args.notNull(str, "Name");
        Args.notNull(contentBody, "Body");
        this.a = str;
        this.c = contentBody;
        if (header == null) {
            header = new Header();
        }
        this.b = header;
    }

    @Deprecated
    public FormBodyPart(String str, ContentBody contentBody) {
        Args.notNull(str, "Name");
        Args.notNull(contentBody, "Body");
        this.a = str;
        this.c = contentBody;
        this.b = new Header();
        generateContentDisp(contentBody);
        generateContentType(contentBody);
        generateTransferEncoding(contentBody);
    }

    public String getName() {
        return this.a;
    }

    public ContentBody getBody() {
        return this.c;
    }

    public Header getHeader() {
        return this.b;
    }

    public void addField(String str, String str2) {
        Args.notNull(str, "Field name");
        this.b.addField(new MinimalField(str, str2));
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void generateContentDisp(ContentBody contentBody) {
        StringBuilder sb = new StringBuilder();
        sb.append("form-data; name=\"");
        sb.append(getName());
        sb.append("\"");
        if (contentBody.getFilename() != null) {
            sb.append("; filename=\"");
            sb.append(contentBody.getFilename());
            sb.append("\"");
        }
        addField("Content-Disposition", sb.toString());
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void generateContentType(ContentBody contentBody) {
        ContentType contentType = contentBody instanceof AbstractContentBody ? ((AbstractContentBody) contentBody).getContentType() : null;
        if (contentType != null) {
            addField("Content-Type", contentType.toString());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(contentBody.getMimeType());
        if (contentBody.getCharset() != null) {
            sb.append(HTTP.CHARSET_PARAM);
            sb.append(contentBody.getCharset());
        }
        addField("Content-Type", sb.toString());
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void generateTransferEncoding(ContentBody contentBody) {
        addField(MIME.CONTENT_TRANSFER_ENC, contentBody.getTransferEncoding());
    }
}
