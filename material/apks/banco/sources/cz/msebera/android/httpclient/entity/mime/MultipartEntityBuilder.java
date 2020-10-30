package cz.msebera.android.httpclient.entity.mime;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.ContentBody;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.entity.mime.content.InputStreamBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.Args;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultipartEntityBuilder {
    private static final char[] a = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private ContentType b;
    private HttpMultipartMode c = HttpMultipartMode.STRICT;
    private String d = null;
    private Charset e = null;
    private List<FormBodyPart> f = null;

    public static MultipartEntityBuilder create() {
        return new MultipartEntityBuilder();
    }

    MultipartEntityBuilder() {
    }

    public MultipartEntityBuilder setMode(HttpMultipartMode httpMultipartMode) {
        this.c = httpMultipartMode;
        return this;
    }

    public MultipartEntityBuilder setLaxMode() {
        this.c = HttpMultipartMode.BROWSER_COMPATIBLE;
        return this;
    }

    public MultipartEntityBuilder setStrictMode() {
        this.c = HttpMultipartMode.STRICT;
        return this;
    }

    public MultipartEntityBuilder setBoundary(String str) {
        this.d = str;
        return this;
    }

    public MultipartEntityBuilder setMimeSubtype(String str) {
        Args.notBlank(str, "MIME subtype");
        StringBuilder sb = new StringBuilder();
        sb.append("multipart/");
        sb.append(str);
        this.b = ContentType.create(sb.toString());
        return this;
    }

    public MultipartEntityBuilder seContentType(ContentType contentType) {
        Args.notNull(contentType, "Content type");
        this.b = contentType;
        return this;
    }

    public MultipartEntityBuilder setCharset(Charset charset) {
        this.e = charset;
        return this;
    }

    public MultipartEntityBuilder addPart(FormBodyPart formBodyPart) {
        if (formBodyPart == null) {
            return this;
        }
        if (this.f == null) {
            this.f = new ArrayList();
        }
        this.f.add(formBodyPart);
        return this;
    }

    public MultipartEntityBuilder addPart(String str, ContentBody contentBody) {
        Args.notNull(str, "Name");
        Args.notNull(contentBody, "Content body");
        return addPart(FormBodyPartBuilder.create(str, contentBody).build());
    }

    public MultipartEntityBuilder addTextBody(String str, String str2, ContentType contentType) {
        return addPart(str, new StringBody(str2, contentType));
    }

    public MultipartEntityBuilder addTextBody(String str, String str2) {
        return addTextBody(str, str2, ContentType.DEFAULT_TEXT);
    }

    public MultipartEntityBuilder addBinaryBody(String str, byte[] bArr, ContentType contentType, String str2) {
        return addPart(str, new ByteArrayBody(bArr, contentType, str2));
    }

    public MultipartEntityBuilder addBinaryBody(String str, byte[] bArr) {
        return addBinaryBody(str, bArr, ContentType.DEFAULT_BINARY, (String) null);
    }

    public MultipartEntityBuilder addBinaryBody(String str, File file, ContentType contentType, String str2) {
        return addPart(str, new FileBody(file, contentType, str2));
    }

    public MultipartEntityBuilder addBinaryBody(String str, File file) {
        return addBinaryBody(str, file, ContentType.DEFAULT_BINARY, file != null ? file.getName() : null);
    }

    public MultipartEntityBuilder addBinaryBody(String str, InputStream inputStream, ContentType contentType, String str2) {
        return addPart(str, new InputStreamBody(inputStream, contentType, str2));
    }

    public MultipartEntityBuilder addBinaryBody(String str, InputStream inputStream) {
        return addBinaryBody(str, inputStream, ContentType.DEFAULT_BINARY, (String) null);
    }

    private String b() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int nextInt = random.nextInt(11) + 30;
        for (int i = 0; i < nextInt; i++) {
            sb.append(a[random.nextInt(a.length)]);
        }
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public MultipartFormEntity a() {
        ContentType contentType;
        List list;
        AbstractMultipartForm abstractMultipartForm;
        String str = this.d;
        if (str == null && this.b != null) {
            str = this.b.getParameter("boundary");
        }
        if (str == null) {
            str = b();
        }
        Charset charset = this.e;
        if (charset == null && this.b != null) {
            charset = this.b.getCharset();
        }
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(new BasicNameValuePair("boundary", str));
        if (charset != null) {
            arrayList.add(new BasicNameValuePair(HttpRequest.PARAM_CHARSET, charset.name()));
        }
        NameValuePair[] nameValuePairArr = (NameValuePair[]) arrayList.toArray(new NameValuePair[arrayList.size()]);
        if (this.b != null) {
            contentType = this.b.withParameters(nameValuePairArr);
        } else {
            contentType = ContentType.create("multipart/form-data", nameValuePairArr);
        }
        if (this.f != null) {
            list = new ArrayList(this.f);
        } else {
            list = Collections.emptyList();
        }
        switch (this.c != null ? this.c : HttpMultipartMode.STRICT) {
            case BROWSER_COMPATIBLE:
                abstractMultipartForm = new HttpBrowserCompatibleMultipart(charset, str, list);
                break;
            case RFC6532:
                abstractMultipartForm = new HttpRFC6532Multipart(charset, str, list);
                break;
            default:
                abstractMultipartForm = new HttpStrictMultipart(charset, str, list);
                break;
        }
        return new MultipartFormEntity(abstractMultipartForm, contentType, abstractMultipartForm.b());
    }

    public HttpEntity build() {
        return a();
    }
}
