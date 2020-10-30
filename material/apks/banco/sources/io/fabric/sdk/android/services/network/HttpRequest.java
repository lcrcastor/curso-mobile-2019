package io.fabric.sdk.android.services.network;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import com.google.common.base.Ascii;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;

public class HttpRequest {
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_ETAG = "ETag";
    public static final String HEADER_EXPIRES = "Expires";
    public static final String HEADER_IF_NONE_MATCH = "If-None-Match";
    public static final String HEADER_LAST_MODIFIED = "Last-Modified";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_SERVER = "Server";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";
    public static final String PARAM_CHARSET = "charset";
    private static final String[] a = new String[0];
    private static ConnectionFactory b = ConnectionFactory.DEFAULT;
    private HttpURLConnection c = null;
    private final String d;
    private RequestOutputStream e;
    private boolean f;
    private boolean g;
    private boolean h = true;
    private boolean i = false;
    /* access modifiers changed from: private */
    public int j = 8192;
    private String k;
    private int l;
    public final URL url;

    public static class Base64 {
        private static final byte[] a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

        private Base64() {
        }

        private static byte[] a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
            byte[] bArr3 = a;
            int i4 = 0;
            int i5 = (i2 > 0 ? (bArr[i] << Ascii.CAN) >>> 8 : 0) | (i2 > 1 ? (bArr[i + 1] << Ascii.CAN) >>> 16 : 0);
            if (i2 > 2) {
                i4 = (bArr[i + 2] << Ascii.CAN) >>> 24;
            }
            int i6 = i5 | i4;
            switch (i2) {
                case 1:
                    bArr2[i3] = bArr3[i6 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                    bArr2[i3 + 2] = 61;
                    bArr2[i3 + 3] = 61;
                    return bArr2;
                case 2:
                    bArr2[i3] = bArr3[i6 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                    bArr2[i3 + 2] = bArr3[(i6 >>> 6) & 63];
                    bArr2[i3 + 3] = 61;
                    return bArr2;
                case 3:
                    bArr2[i3] = bArr3[i6 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i6 >>> 12) & 63];
                    bArr2[i3 + 2] = bArr3[(i6 >>> 6) & 63];
                    bArr2[i3 + 3] = bArr3[i6 & 63];
                    return bArr2;
                default:
                    return bArr2;
            }
        }

        public static String encode(String str) {
            byte[] bArr;
            try {
                bArr = str.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException unused) {
                bArr = str.getBytes();
            }
            return encodeBytes(bArr);
        }

        public static String encodeBytes(byte[] bArr) {
            return encodeBytes(bArr, 0, bArr.length);
        }

        public static String encodeBytes(byte[] bArr, int i, int i2) {
            byte[] encodeBytesToBytes = encodeBytesToBytes(bArr, i, i2);
            try {
                return new String(encodeBytesToBytes, "US-ASCII");
            } catch (UnsupportedEncodingException unused) {
                return new String(encodeBytesToBytes);
            }
        }

        public static byte[] encodeBytesToBytes(byte[] bArr, int i, int i2) {
            if (bArr == null) {
                throw new NullPointerException("Cannot serialize a null array.");
            } else if (i < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot have negative offset: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            } else if (i2 < 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Cannot have length offset: ");
                sb2.append(i2);
                throw new IllegalArgumentException(sb2.toString());
            } else if (i + i2 > bArr.length) {
                throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Cannot have offset of %d and length of %d with array of length %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(bArr.length)}));
            } else {
                int i3 = 4;
                int i4 = (i2 / 3) * 4;
                if (i2 % 3 <= 0) {
                    i3 = 0;
                }
                byte[] bArr2 = new byte[(i4 + i3)];
                int i5 = i2 - 2;
                int i6 = 0;
                int i7 = 0;
                while (i6 < i5) {
                    a(bArr, i6 + i, 3, bArr2, i7);
                    i6 += 3;
                    i7 += 4;
                }
                if (i6 < i2) {
                    a(bArr, i + i6, i2 - i6, bArr2, i7);
                    i7 += 4;
                }
                if (i7 > bArr2.length - 1) {
                    return bArr2;
                }
                byte[] bArr3 = new byte[i7];
                System.arraycopy(bArr2, 0, bArr3, 0, i7);
                return bArr3;
            }
        }
    }

    public static abstract class CloseOperation<V> extends Operation<V> {
        private final Closeable a;
        private final boolean b;

        protected CloseOperation(Closeable closeable, boolean z) {
            this.a = closeable;
            this.b = z;
        }

        /* access modifiers changed from: protected */
        public void done() {
            if (this.a instanceof Flushable) {
                ((Flushable) this.a).flush();
            }
            if (this.b) {
                try {
                    this.a.close();
                } catch (IOException unused) {
                }
            } else {
                this.a.close();
            }
        }
    }

    public interface ConnectionFactory {
        public static final ConnectionFactory DEFAULT = new ConnectionFactory() {
            public HttpURLConnection create(URL url) {
                return (HttpURLConnection) url.openConnection();
            }

            public HttpURLConnection create(URL url, Proxy proxy) {
                return (HttpURLConnection) url.openConnection(proxy);
            }
        };

        HttpURLConnection create(URL url);

        HttpURLConnection create(URL url, Proxy proxy);
    }

    public static abstract class FlushOperation<V> extends Operation<V> {
        private final Flushable a;

        protected FlushOperation(Flushable flushable) {
            this.a = flushable;
        }

        /* access modifiers changed from: protected */
        public void done() {
            this.a.flush();
        }
    }

    public static class HttpRequestException extends RuntimeException {
        private static final long serialVersionUID = -1170466989781746231L;

        protected HttpRequestException(IOException iOException) {
            super(iOException);
        }

        public IOException getCause() {
            return (IOException) super.getCause();
        }
    }

    public static abstract class Operation<V> implements Callable<V> {
        /* access modifiers changed from: protected */
        public abstract void done();

        /* access modifiers changed from: protected */
        public abstract V run();

        protected Operation() {
        }

        public V call() {
            boolean z = true;
            try {
                V run = run();
                try {
                    done();
                    return run;
                } catch (IOException e) {
                    throw new HttpRequestException(e);
                }
            } catch (HttpRequestException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new HttpRequestException(e3);
            } catch (Throwable th) {
                th = th;
                done();
                throw th;
            }
        }
    }

    public static class RequestOutputStream extends BufferedOutputStream {
        /* access modifiers changed from: private */
        public final CharsetEncoder a;

        public RequestOutputStream(OutputStream outputStream, String str, int i) {
            super(outputStream, i);
            this.a = Charset.forName(HttpRequest.b(str)).newEncoder();
        }

        public RequestOutputStream write(String str) {
            ByteBuffer encode = this.a.encode(CharBuffer.wrap(str));
            super.write(encode.array(), 0, encode.limit());
            return this;
        }
    }

    public HttpRequest trustAllCerts() {
        return this;
    }

    public HttpRequest trustAllHosts() {
        return this;
    }

    /* access modifiers changed from: private */
    public static String b(String str) {
        return (str == null || str.length() <= 0) ? "UTF-8" : str;
    }

    private static StringBuilder a(String str, StringBuilder sb) {
        if (str.indexOf(58) + 2 == str.lastIndexOf(47)) {
            sb.append('/');
        }
        return sb;
    }

    private static StringBuilder b(String str, StringBuilder sb) {
        int indexOf = str.indexOf(63);
        int length = sb.length() - 1;
        if (indexOf == -1) {
            sb.append('?');
        } else if (indexOf < length && str.charAt(length) != '&') {
            sb.append('&');
        }
        return sb;
    }

    public static void setConnectionFactory(ConnectionFactory connectionFactory) {
        if (connectionFactory == null) {
            b = ConnectionFactory.DEFAULT;
        } else {
            b = connectionFactory;
        }
    }

    public static String encode(CharSequence charSequence) {
        try {
            URL url2 = new URL(charSequence.toString());
            String host = url2.getHost();
            int port = url2.getPort();
            if (port != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(host);
                sb.append(':');
                sb.append(Integer.toString(port));
                host = sb.toString();
            }
            try {
                URI uri = new URI(url2.getProtocol(), host, url2.getPath(), url2.getQuery(), null);
                String aSCIIString = uri.toASCIIString();
                int indexOf = aSCIIString.indexOf(63);
                if (indexOf <= 0) {
                    return aSCIIString;
                }
                int i2 = indexOf + 1;
                if (i2 >= aSCIIString.length()) {
                    return aSCIIString;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(aSCIIString.substring(0, i2));
                sb2.append(aSCIIString.substring(i2).replace(Constants.SYMBOL_POSITIVE, "%2B"));
                return sb2.toString();
            } catch (URISyntaxException e2) {
                IOException iOException = new IOException("Parsing URI failed");
                iOException.initCause(e2);
                throw new HttpRequestException(iOException);
            }
        } catch (IOException e3) {
            throw new HttpRequestException(e3);
        }
    }

    public static String append(CharSequence charSequence, Map<?, ?> map) {
        String charSequence2 = charSequence.toString();
        if (map == null || map.isEmpty()) {
            return charSequence2;
        }
        StringBuilder sb = new StringBuilder(charSequence2);
        a(charSequence2, sb);
        b(charSequence2, sb);
        Iterator it = map.entrySet().iterator();
        Entry entry = (Entry) it.next();
        sb.append(entry.getKey().toString());
        sb.append('=');
        Object value = entry.getValue();
        if (value != null) {
            sb.append(value);
        }
        while (it.hasNext()) {
            sb.append('&');
            Entry entry2 = (Entry) it.next();
            sb.append(entry2.getKey().toString());
            sb.append('=');
            Object value2 = entry2.getValue();
            if (value2 != null) {
                sb.append(value2);
            }
        }
        return sb.toString();
    }

    public static String append(CharSequence charSequence, Object... objArr) {
        String charSequence2 = charSequence.toString();
        if (objArr == null || objArr.length == 0) {
            return charSequence2;
        }
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException("Must specify an even number of parameter names/values");
        }
        StringBuilder sb = new StringBuilder(charSequence2);
        a(charSequence2, sb);
        b(charSequence2, sb);
        sb.append(objArr[0]);
        sb.append('=');
        Object obj = objArr[1];
        if (obj != null) {
            sb.append(obj);
        }
        for (int i2 = 2; i2 < objArr.length; i2 += 2) {
            sb.append('&');
            sb.append(objArr[i2]);
            sb.append('=');
            Object obj2 = objArr[i2 + 1];
            if (obj2 != null) {
                sb.append(obj2);
            }
        }
        return sb.toString();
    }

    public static HttpRequest get(CharSequence charSequence) {
        return new HttpRequest(charSequence, "GET");
    }

    public static HttpRequest get(URL url2) {
        return new HttpRequest(url2, "GET");
    }

    public static HttpRequest get(CharSequence charSequence, Map<?, ?> map, boolean z) {
        String append = append(charSequence, map);
        if (z) {
            append = encode(append);
        }
        return get((CharSequence) append);
    }

    public static HttpRequest get(CharSequence charSequence, boolean z, Object... objArr) {
        String append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return get((CharSequence) append);
    }

    public static HttpRequest post(CharSequence charSequence) {
        return new HttpRequest(charSequence, "POST");
    }

    public static HttpRequest post(URL url2) {
        return new HttpRequest(url2, "POST");
    }

    public static HttpRequest post(CharSequence charSequence, Map<?, ?> map, boolean z) {
        String append = append(charSequence, map);
        if (z) {
            append = encode(append);
        }
        return post((CharSequence) append);
    }

    public static HttpRequest post(CharSequence charSequence, boolean z, Object... objArr) {
        String append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return post((CharSequence) append);
    }

    public static HttpRequest put(CharSequence charSequence) {
        return new HttpRequest(charSequence, "PUT");
    }

    public static HttpRequest put(URL url2) {
        return new HttpRequest(url2, "PUT");
    }

    public static HttpRequest put(CharSequence charSequence, Map<?, ?> map, boolean z) {
        String append = append(charSequence, map);
        if (z) {
            append = encode(append);
        }
        return put((CharSequence) append);
    }

    public static HttpRequest put(CharSequence charSequence, boolean z, Object... objArr) {
        String append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return put((CharSequence) append);
    }

    public static HttpRequest delete(CharSequence charSequence) {
        return new HttpRequest(charSequence, "DELETE");
    }

    public static HttpRequest delete(URL url2) {
        return new HttpRequest(url2, "DELETE");
    }

    public static HttpRequest delete(CharSequence charSequence, Map<?, ?> map, boolean z) {
        String append = append(charSequence, map);
        if (z) {
            append = encode(append);
        }
        return delete((CharSequence) append);
    }

    public static HttpRequest delete(CharSequence charSequence, boolean z, Object... objArr) {
        String append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return delete((CharSequence) append);
    }

    public static HttpRequest head(CharSequence charSequence) {
        return new HttpRequest(charSequence, "HEAD");
    }

    public static HttpRequest head(URL url2) {
        return new HttpRequest(url2, "HEAD");
    }

    public static HttpRequest head(CharSequence charSequence, Map<?, ?> map, boolean z) {
        String append = append(charSequence, map);
        if (z) {
            append = encode(append);
        }
        return head((CharSequence) append);
    }

    public static HttpRequest head(CharSequence charSequence, boolean z, Object... objArr) {
        String append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return head((CharSequence) append);
    }

    public static HttpRequest options(CharSequence charSequence) {
        return new HttpRequest(charSequence, "OPTIONS");
    }

    public static HttpRequest options(URL url2) {
        return new HttpRequest(url2, "OPTIONS");
    }

    public static HttpRequest trace(CharSequence charSequence) {
        return new HttpRequest(charSequence, "TRACE");
    }

    public static HttpRequest trace(URL url2) {
        return new HttpRequest(url2, "TRACE");
    }

    public static void keepAlive(boolean z) {
        a("http.keepAlive", Boolean.toString(z));
    }

    public static void proxyHost(String str) {
        a("http.proxyHost", str);
        a("https.proxyHost", str);
    }

    public static void proxyPort(int i2) {
        String num = Integer.toString(i2);
        a("http.proxyPort", num);
        a("https.proxyPort", num);
    }

    public static void nonProxyHosts(String... strArr) {
        if (strArr == null || strArr.length <= 0) {
            a("http.nonProxyHosts", (String) null);
            return;
        }
        StringBuilder sb = new StringBuilder();
        int length = strArr.length - 1;
        for (int i2 = 0; i2 < length; i2++) {
            sb.append(strArr[i2]);
            sb.append('|');
        }
        sb.append(strArr[length]);
        a("http.nonProxyHosts", sb.toString());
    }

    private static String a(final String str, final String str2) {
        PrivilegedAction privilegedAction;
        if (str2 != null) {
            privilegedAction = new PrivilegedAction<String>() {
                /* renamed from: a */
                public String run() {
                    return System.setProperty(str, str2);
                }
            };
        } else {
            privilegedAction = new PrivilegedAction<String>() {
                /* renamed from: a */
                public String run() {
                    return System.clearProperty(str);
                }
            };
        }
        return (String) AccessController.doPrivileged(privilegedAction);
    }

    public HttpRequest(CharSequence charSequence, String str) {
        try {
            this.url = new URL(charSequence.toString());
            this.d = str;
        } catch (MalformedURLException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest(URL url2, String str) {
        this.url = url2;
        this.d = str;
    }

    private Proxy a() {
        return new Proxy(Type.HTTP, new InetSocketAddress(this.k, this.l));
    }

    private HttpURLConnection b() {
        HttpURLConnection httpURLConnection;
        try {
            if (this.k != null) {
                httpURLConnection = b.create(this.url, a());
            } else {
                httpURLConnection = b.create(this.url);
            }
            httpURLConnection.setRequestMethod(this.d);
            return httpURLConnection;
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(method());
        sb.append(TokenParser.SP);
        sb.append(url());
        return sb.toString();
    }

    public HttpURLConnection getConnection() {
        if (this.c == null) {
            this.c = b();
        }
        return this.c;
    }

    public HttpRequest ignoreCloseExceptions(boolean z) {
        this.h = z;
        return this;
    }

    public boolean ignoreCloseExceptions() {
        return this.h;
    }

    public int code() {
        try {
            closeOutput();
            return getConnection().getResponseCode();
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest code(AtomicInteger atomicInteger) {
        atomicInteger.set(code());
        return this;
    }

    public boolean ok() {
        return 200 == code();
    }

    public boolean created() {
        return 201 == code();
    }

    public boolean serverError() {
        return 500 == code();
    }

    public boolean badRequest() {
        return 400 == code();
    }

    public boolean notFound() {
        return 404 == code();
    }

    public boolean notModified() {
        return 304 == code();
    }

    public String message() {
        try {
            closeOutput();
            return getConnection().getResponseMessage();
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest disconnect() {
        getConnection().disconnect();
        return this;
    }

    public HttpRequest chunk(int i2) {
        getConnection().setChunkedStreamingMode(i2);
        return this;
    }

    public HttpRequest bufferSize(int i2) {
        if (i2 < 1) {
            throw new IllegalArgumentException("Size must be greater than zero");
        }
        this.j = i2;
        return this;
    }

    public int bufferSize() {
        return this.j;
    }

    public HttpRequest uncompress(boolean z) {
        this.i = z;
        return this;
    }

    /* access modifiers changed from: protected */
    public ByteArrayOutputStream byteStream() {
        int contentLength = contentLength();
        if (contentLength > 0) {
            return new ByteArrayOutputStream(contentLength);
        }
        return new ByteArrayOutputStream();
    }

    public String body(String str) {
        ByteArrayOutputStream byteStream = byteStream();
        try {
            copy((InputStream) buffer(), (OutputStream) byteStream);
            return byteStream.toString(b(str));
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public String body() {
        return body(charset());
    }

    public HttpRequest body(AtomicReference<String> atomicReference) {
        atomicReference.set(body());
        return this;
    }

    public HttpRequest body(AtomicReference<String> atomicReference, String str) {
        atomicReference.set(body(str));
        return this;
    }

    public boolean isBodyEmpty() {
        return contentLength() == 0;
    }

    public byte[] bytes() {
        ByteArrayOutputStream byteStream = byteStream();
        try {
            copy((InputStream) buffer(), (OutputStream) byteStream);
            return byteStream.toByteArray();
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public BufferedInputStream buffer() {
        return new BufferedInputStream(stream(), this.j);
    }

    public InputStream stream() {
        InputStream inputStream;
        if (code() < 400) {
            try {
                inputStream = getConnection().getInputStream();
            } catch (IOException e2) {
                throw new HttpRequestException(e2);
            }
        } else {
            inputStream = getConnection().getErrorStream();
            if (inputStream == null) {
                try {
                    inputStream = getConnection().getInputStream();
                } catch (IOException e3) {
                    throw new HttpRequestException(e3);
                }
            }
        }
        if (!this.i || !ENCODING_GZIP.equals(contentEncoding())) {
            return inputStream;
        }
        try {
            return new GZIPInputStream(inputStream);
        } catch (IOException e4) {
            throw new HttpRequestException(e4);
        }
    }

    public InputStreamReader reader(String str) {
        try {
            return new InputStreamReader(stream(), b(str));
        } catch (UnsupportedEncodingException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public InputStreamReader reader() {
        return reader(charset());
    }

    public BufferedReader bufferedReader(String str) {
        return new BufferedReader(reader(str), this.j);
    }

    public BufferedReader bufferedReader() {
        return bufferedReader(charset());
    }

    public HttpRequest receive(File file) {
        try {
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), this.j);
            return (HttpRequest) new CloseOperation<HttpRequest>(this.h, bufferedOutputStream) {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public HttpRequest run() {
                    return HttpRequest.this.receive(bufferedOutputStream);
                }
            }.call();
        } catch (FileNotFoundException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest receive(OutputStream outputStream) {
        try {
            return copy((InputStream) buffer(), outputStream);
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest receive(PrintStream printStream) {
        return receive((OutputStream) printStream);
    }

    public HttpRequest receive(Appendable appendable) {
        final BufferedReader bufferedReader = bufferedReader();
        final Appendable appendable2 = appendable;
        AnonymousClass4 r0 = new CloseOperation<HttpRequest>(bufferedReader, this.h) {
            /* renamed from: a */
            public HttpRequest run() {
                CharBuffer allocate = CharBuffer.allocate(HttpRequest.this.j);
                while (true) {
                    int read = bufferedReader.read(allocate);
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    allocate.rewind();
                    appendable2.append(allocate, 0, read);
                    allocate.rewind();
                }
            }
        };
        return (HttpRequest) r0.call();
    }

    public HttpRequest receive(Writer writer) {
        final BufferedReader bufferedReader = bufferedReader();
        final Writer writer2 = writer;
        AnonymousClass5 r0 = new CloseOperation<HttpRequest>(bufferedReader, this.h) {
            /* renamed from: a */
            public HttpRequest run() {
                return HttpRequest.this.copy((Reader) bufferedReader, writer2);
            }
        };
        return (HttpRequest) r0.call();
    }

    public HttpRequest readTimeout(int i2) {
        getConnection().setReadTimeout(i2);
        return this;
    }

    public HttpRequest connectTimeout(int i2) {
        getConnection().setConnectTimeout(i2);
        return this;
    }

    public HttpRequest header(String str, String str2) {
        getConnection().setRequestProperty(str, str2);
        return this;
    }

    public HttpRequest header(String str, Number number) {
        return header(str, number != null ? number.toString() : null);
    }

    public HttpRequest headers(Map<String, String> map) {
        if (!map.isEmpty()) {
            for (Entry header : map.entrySet()) {
                header(header);
            }
        }
        return this;
    }

    public HttpRequest header(Entry<String, String> entry) {
        return header((String) entry.getKey(), (String) entry.getValue());
    }

    public String header(String str) {
        closeOutputQuietly();
        return getConnection().getHeaderField(str);
    }

    public Map<String, List<String>> headers() {
        closeOutputQuietly();
        return getConnection().getHeaderFields();
    }

    public long dateHeader(String str) {
        return dateHeader(str, -1);
    }

    public long dateHeader(String str, long j2) {
        closeOutputQuietly();
        return getConnection().getHeaderFieldDate(str, j2);
    }

    public int intHeader(String str) {
        return intHeader(str, -1);
    }

    public int intHeader(String str, int i2) {
        closeOutputQuietly();
        return getConnection().getHeaderFieldInt(str, i2);
    }

    public String[] headers(String str) {
        Map headers = headers();
        if (headers == null || headers.isEmpty()) {
            return a;
        }
        List list = (List) headers.get(str);
        if (list == null || list.isEmpty()) {
            return a;
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public String parameter(String str, String str2) {
        return getParam(header(str), str2);
    }

    public Map<String, String> parameters(String str) {
        return getParams(header(str));
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getParams(String str) {
        int i2;
        if (str == null || str.length() == 0) {
            return Collections.emptyMap();
        }
        int length = str.length();
        int indexOf = str.indexOf(59) + 1;
        if (indexOf == 0 || indexOf == length) {
            return Collections.emptyMap();
        }
        int indexOf2 = str.indexOf(59, indexOf);
        if (indexOf2 == -1) {
            indexOf2 = length;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (indexOf < i2) {
            int indexOf3 = str.indexOf(61, indexOf);
            if (indexOf3 != -1 && indexOf3 < i2) {
                String trim = str.substring(indexOf, indexOf3).trim();
                if (trim.length() > 0) {
                    String trim2 = str.substring(indexOf3 + 1, i2).trim();
                    int length2 = trim2.length();
                    if (length2 != 0) {
                        if (length2 > 2 && '\"' == trim2.charAt(0)) {
                            int i3 = length2 - 1;
                            if ('\"' == trim2.charAt(i3)) {
                                linkedHashMap.put(trim, trim2.substring(1, i3));
                            }
                        }
                        linkedHashMap.put(trim, trim2);
                    }
                }
            }
            indexOf = i2 + 1;
            i2 = str.indexOf(59, indexOf);
            if (i2 == -1) {
                i2 = length;
            }
        }
        return linkedHashMap;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006f A[SYNTHETIC] */
    protected java.lang.String getParam(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto L_0x0071
            int r1 = r9.length()
            if (r1 != 0) goto L_0x000a
            goto L_0x0071
        L_0x000a:
            int r1 = r9.length()
            r2 = 59
            int r3 = r9.indexOf(r2)
            r4 = 1
            int r3 = r3 + r4
            if (r3 == 0) goto L_0x0070
            if (r3 != r1) goto L_0x001b
            goto L_0x0070
        L_0x001b:
            int r5 = r9.indexOf(r2, r3)
            r6 = -1
            if (r5 != r6) goto L_0x0023
        L_0x0022:
            r5 = r1
        L_0x0023:
            if (r3 >= r5) goto L_0x006f
            r7 = 61
            int r7 = r9.indexOf(r7, r3)
            if (r7 == r6) goto L_0x0066
            if (r7 >= r5) goto L_0x0066
            java.lang.String r3 = r9.substring(r3, r7)
            java.lang.String r3 = r3.trim()
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0066
            int r7 = r7 + 1
            java.lang.String r3 = r9.substring(r7, r5)
            java.lang.String r3 = r3.trim()
            int r7 = r3.length()
            if (r7 == 0) goto L_0x0066
            r9 = 2
            if (r7 <= r9) goto L_0x0065
            r9 = 0
            char r9 = r3.charAt(r9)
            r10 = 34
            if (r10 != r9) goto L_0x0065
            int r7 = r7 - r4
            char r9 = r3.charAt(r7)
            if (r10 != r9) goto L_0x0065
            java.lang.String r9 = r3.substring(r4, r7)
            return r9
        L_0x0065:
            return r3
        L_0x0066:
            int r3 = r5 + 1
            int r5 = r9.indexOf(r2, r3)
            if (r5 != r6) goto L_0x0023
            goto L_0x0022
        L_0x006f:
            return r0
        L_0x0070:
            return r0
        L_0x0071:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.network.HttpRequest.getParam(java.lang.String, java.lang.String):java.lang.String");
    }

    public String charset() {
        return parameter("Content-Type", PARAM_CHARSET);
    }

    public HttpRequest userAgent(String str) {
        return header("User-Agent", str);
    }

    public HttpRequest referer(String str) {
        return header("Referer", str);
    }

    public HttpRequest useCaches(boolean z) {
        getConnection().setUseCaches(z);
        return this;
    }

    public HttpRequest acceptEncoding(String str) {
        return header("Accept-Encoding", str);
    }

    public HttpRequest acceptGzipEncoding() {
        return acceptEncoding(ENCODING_GZIP);
    }

    public HttpRequest acceptCharset(String str) {
        return header("Accept-Charset", str);
    }

    public String contentEncoding() {
        return header("Content-Encoding");
    }

    public String server() {
        return header("Server");
    }

    public long date() {
        return dateHeader("Date");
    }

    public String cacheControl() {
        return header("Cache-Control");
    }

    public String eTag() {
        return header("ETag");
    }

    public long expires() {
        return dateHeader("Expires");
    }

    public long lastModified() {
        return dateHeader("Last-Modified");
    }

    public String location() {
        return header("Location");
    }

    public HttpRequest authorization(String str) {
        return header("Authorization", str);
    }

    public HttpRequest proxyAuthorization(String str) {
        return header("Proxy-Authorization", str);
    }

    public HttpRequest basic(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Basic ");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(':');
        sb2.append(str2);
        sb.append(Base64.encode(sb2.toString()));
        return authorization(sb.toString());
    }

    public HttpRequest proxyBasic(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Basic ");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(':');
        sb2.append(str2);
        sb.append(Base64.encode(sb2.toString()));
        return proxyAuthorization(sb.toString());
    }

    public HttpRequest ifModifiedSince(long j2) {
        getConnection().setIfModifiedSince(j2);
        return this;
    }

    public HttpRequest ifNoneMatch(String str) {
        return header("If-None-Match", str);
    }

    public HttpRequest contentType(String str) {
        return contentType(str, null);
    }

    public HttpRequest contentType(String str, String str2) {
        if (str2 == null || str2.length() <= 0) {
            return header("Content-Type", str);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(HTTP.CHARSET_PARAM);
        sb.append(str2);
        return header("Content-Type", sb.toString());
    }

    public String contentType() {
        return header("Content-Type");
    }

    public int contentLength() {
        return intHeader("Content-Length");
    }

    public HttpRequest contentLength(String str) {
        return contentLength(Integer.parseInt(str));
    }

    public HttpRequest contentLength(int i2) {
        getConnection().setFixedLengthStreamingMode(i2);
        return this;
    }

    public HttpRequest accept(String str) {
        return header("Accept", str);
    }

    public HttpRequest acceptJson() {
        return accept("application/json");
    }

    /* access modifiers changed from: protected */
    public HttpRequest copy(InputStream inputStream, OutputStream outputStream) {
        final InputStream inputStream2 = inputStream;
        final OutputStream outputStream2 = outputStream;
        AnonymousClass6 r0 = new CloseOperation<HttpRequest>(inputStream, this.h) {
            /* renamed from: a */
            public HttpRequest run() {
                byte[] bArr = new byte[HttpRequest.this.j];
                while (true) {
                    int read = inputStream2.read(bArr);
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    outputStream2.write(bArr, 0, read);
                }
            }
        };
        return (HttpRequest) r0.call();
    }

    /* access modifiers changed from: protected */
    public HttpRequest copy(Reader reader, Writer writer) {
        final Reader reader2 = reader;
        final Writer writer2 = writer;
        AnonymousClass7 r0 = new CloseOperation<HttpRequest>(reader, this.h) {
            /* renamed from: a */
            public HttpRequest run() {
                char[] cArr = new char[HttpRequest.this.j];
                while (true) {
                    int read = reader2.read(cArr);
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    writer2.write(cArr, 0, read);
                }
            }
        };
        return (HttpRequest) r0.call();
    }

    /* access modifiers changed from: protected */
    public HttpRequest closeOutput() {
        if (this.e == null) {
            return this;
        }
        if (this.f) {
            this.e.write("\r\n--00content0boundary00--\r\n");
        }
        if (this.h) {
            try {
                this.e.close();
            } catch (IOException unused) {
            }
        } else {
            this.e.close();
        }
        this.e = null;
        return this;
    }

    /* access modifiers changed from: protected */
    public HttpRequest closeOutputQuietly() {
        try {
            return closeOutput();
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    /* access modifiers changed from: protected */
    public HttpRequest openOutput() {
        if (this.e != null) {
            return this;
        }
        getConnection().setDoOutput(true);
        this.e = new RequestOutputStream(getConnection().getOutputStream(), getParam(getConnection().getRequestProperty("Content-Type"), PARAM_CHARSET), this.j);
        return this;
    }

    /* access modifiers changed from: protected */
    public HttpRequest startPart() {
        if (!this.f) {
            this.f = true;
            contentType("multipart/form-data; boundary=00content0boundary00").openOutput();
            this.e.write("--00content0boundary00\r\n");
        } else {
            this.e.write("\r\n--00content0boundary00\r\n");
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public HttpRequest writePartHeader(String str, String str2) {
        return writePartHeader(str, str2, null);
    }

    /* access modifiers changed from: protected */
    public HttpRequest writePartHeader(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("form-data; name=\"");
        sb.append(str);
        if (str2 != null) {
            sb.append("\"; filename=\"");
            sb.append(str2);
        }
        sb.append(TokenParser.DQUOTE);
        partHeader("Content-Disposition", sb.toString());
        if (str3 != null) {
            partHeader("Content-Type", str3);
        }
        return send((CharSequence) "\r\n");
    }

    public HttpRequest part(String str, String str2) {
        return part(str, (String) null, str2);
    }

    public HttpRequest part(String str, String str2, String str3) {
        return part(str, str2, (String) null, str3);
    }

    public HttpRequest part(String str, String str2, String str3, String str4) {
        try {
            startPart();
            writePartHeader(str, str2, str3);
            this.e.write(str4);
            return this;
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest part(String str, Number number) {
        return part(str, (String) null, number);
    }

    public HttpRequest part(String str, String str2, Number number) {
        return part(str, str2, number != null ? number.toString() : null);
    }

    public HttpRequest part(String str, File file) {
        return part(str, (String) null, file);
    }

    public HttpRequest part(String str, String str2, File file) {
        return part(str, str2, (String) null, file);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0026 A[SYNTHETIC, Splitter:B:20:0x0026] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public io.fabric.sdk.android.services.network.HttpRequest part(java.lang.String r4, java.lang.String r5, java.lang.String r6, java.io.File r7) {
        /*
            r3 = this;
            r0 = 0
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x001d }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x001d }
            r2.<init>(r7)     // Catch:{ IOException -> 0x001d }
            r1.<init>(r2)     // Catch:{ IOException -> 0x001d }
            io.fabric.sdk.android.services.network.HttpRequest r4 = r3.part(r4, r5, r6, r1)     // Catch:{ IOException -> 0x0018, all -> 0x0015 }
            if (r1 == 0) goto L_0x0014
            r1.close()     // Catch:{ IOException -> 0x0014 }
        L_0x0014:
            return r4
        L_0x0015:
            r4 = move-exception
            r0 = r1
            goto L_0x0024
        L_0x0018:
            r4 = move-exception
            r0 = r1
            goto L_0x001e
        L_0x001b:
            r4 = move-exception
            goto L_0x0024
        L_0x001d:
            r4 = move-exception
        L_0x001e:
            io.fabric.sdk.android.services.network.HttpRequest$HttpRequestException r5 = new io.fabric.sdk.android.services.network.HttpRequest$HttpRequestException     // Catch:{ all -> 0x001b }
            r5.<init>(r4)     // Catch:{ all -> 0x001b }
            throw r5     // Catch:{ all -> 0x001b }
        L_0x0024:
            if (r0 == 0) goto L_0x0029
            r0.close()     // Catch:{ IOException -> 0x0029 }
        L_0x0029:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.network.HttpRequest.part(java.lang.String, java.lang.String, java.lang.String, java.io.File):io.fabric.sdk.android.services.network.HttpRequest");
    }

    public HttpRequest part(String str, InputStream inputStream) {
        return part(str, (String) null, (String) null, inputStream);
    }

    public HttpRequest part(String str, String str2, String str3, InputStream inputStream) {
        try {
            startPart();
            writePartHeader(str, str2, str3);
            copy(inputStream, (OutputStream) this.e);
            return this;
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest partHeader(String str, String str2) {
        return send((CharSequence) str).send((CharSequence) ": ").send((CharSequence) str2).send((CharSequence) "\r\n");
    }

    public HttpRequest send(File file) {
        try {
            return send((InputStream) new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest send(byte[] bArr) {
        return send((InputStream) new ByteArrayInputStream(bArr));
    }

    public HttpRequest send(InputStream inputStream) {
        try {
            openOutput();
            copy(inputStream, (OutputStream) this.e);
            return this;
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest send(final Reader reader) {
        try {
            openOutput();
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.e, this.e.a.charset());
            return (HttpRequest) new FlushOperation<HttpRequest>(outputStreamWriter) {
                /* access modifiers changed from: protected */
                /* renamed from: a */
                public HttpRequest run() {
                    return HttpRequest.this.copy(reader, outputStreamWriter);
                }
            }.call();
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest send(CharSequence charSequence) {
        try {
            openOutput();
            this.e.write(charSequence.toString());
            return this;
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public OutputStreamWriter writer() {
        try {
            openOutput();
            return new OutputStreamWriter(this.e, this.e.a.charset());
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest form(Map<?, ?> map) {
        return form(map, "UTF-8");
    }

    public HttpRequest form(Entry<?, ?> entry) {
        return form(entry, "UTF-8");
    }

    public HttpRequest form(Entry<?, ?> entry, String str) {
        return form(entry.getKey(), entry.getValue(), str);
    }

    public HttpRequest form(Object obj, Object obj2) {
        return form(obj, obj2, "UTF-8");
    }

    public HttpRequest form(Object obj, Object obj2, String str) {
        boolean z = !this.g;
        if (z) {
            contentType("application/x-www-form-urlencoded", str);
            this.g = true;
        }
        String b2 = b(str);
        try {
            openOutput();
            if (!z) {
                this.e.write(38);
            }
            this.e.write(URLEncoder.encode(obj.toString(), b2));
            this.e.write(61);
            if (obj2 != null) {
                this.e.write(URLEncoder.encode(obj2.toString(), b2));
            }
            return this;
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public HttpRequest form(Map<?, ?> map, String str) {
        if (!map.isEmpty()) {
            for (Entry form : map.entrySet()) {
                form(form, str);
            }
        }
        return this;
    }

    public URL url() {
        return getConnection().getURL();
    }

    public String method() {
        return getConnection().getRequestMethod();
    }

    public HttpRequest useProxy(String str, int i2) {
        if (this.c != null) {
            throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
        }
        this.k = str;
        this.l = i2;
        return this;
    }

    public HttpRequest followRedirects(boolean z) {
        getConnection().setInstanceFollowRedirects(z);
        return this;
    }
}
