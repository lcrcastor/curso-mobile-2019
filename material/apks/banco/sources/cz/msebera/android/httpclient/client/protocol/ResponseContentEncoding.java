package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.entity.DecompressingEntity;
import cz.msebera.android.httpclient.client.entity.DeflateInputStream;
import cz.msebera.android.httpclient.client.entity.InputStreamFactory;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.config.RegistryBuilder;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.protocol.HttpContext;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.InputStream;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

@Immutable
public class ResponseContentEncoding implements HttpResponseInterceptor {
    public static final String UNCOMPRESSED = "http.client.response.uncompressed";
    private static final InputStreamFactory a = new InputStreamFactory() {
        public InputStream create(InputStream inputStream) {
            return new GZIPInputStream(inputStream);
        }
    };
    private static final InputStreamFactory b = new InputStreamFactory() {
        public InputStream create(InputStream inputStream) {
            return new DeflateInputStream(inputStream);
        }
    };
    private final Lookup<InputStreamFactory> c;

    public ResponseContentEncoding(Lookup<InputStreamFactory> lookup) {
        if (lookup == null) {
            lookup = RegistryBuilder.create().register(HttpRequest.ENCODING_GZIP, a).register("x-gzip", a).register("deflate", b).build();
        }
        this.c = lookup;
    }

    public ResponseContentEncoding() {
        this(null);
    }

    public void process(HttpResponse httpResponse, HttpContext httpContext) {
        HeaderElement[] elements;
        HttpEntity entity = httpResponse.getEntity();
        if (HttpClientContext.adapt(httpContext).getRequestConfig().isDecompressionEnabled() && entity != null && entity.getContentLength() != 0) {
            Header contentEncoding = entity.getContentEncoding();
            if (contentEncoding != null) {
                for (HeaderElement headerElement : contentEncoding.getElements()) {
                    String lowerCase = headerElement.getName().toLowerCase(Locale.ROOT);
                    InputStreamFactory inputStreamFactory = (InputStreamFactory) this.c.lookup(lowerCase);
                    if (inputStreamFactory != null) {
                        httpResponse.setEntity(new DecompressingEntity(httpResponse.getEntity(), inputStreamFactory));
                        httpResponse.removeHeaders("Content-Length");
                        httpResponse.removeHeaders("Content-Encoding");
                        httpResponse.removeHeaders("Content-MD5");
                    } else if (!HTTP.IDENTITY_CODING.equals(lowerCase)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unsupported Content-Coding: ");
                        sb.append(headerElement.getName());
                        throw new HttpException(sb.toString());
                    }
                }
            }
        }
    }
}
