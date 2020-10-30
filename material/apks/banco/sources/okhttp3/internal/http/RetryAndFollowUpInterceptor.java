package okhttp3.internal.http;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import cz.msebera.android.httpclient.HttpStatus;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpRetryException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http2.ConnectionShutdownException;

public final class RetryAndFollowUpInterceptor implements Interceptor {
    private final OkHttpClient a;
    private final boolean b;
    private volatile StreamAllocation c;
    private Object d;
    private volatile boolean e;

    public RetryAndFollowUpInterceptor(OkHttpClient okHttpClient, boolean z) {
        this.a = okHttpClient;
        this.b = z;
    }

    public void cancel() {
        this.e = true;
        StreamAllocation streamAllocation = this.c;
        if (streamAllocation != null) {
            streamAllocation.cancel();
        }
    }

    public boolean isCanceled() {
        return this.e;
    }

    public void setCallStackTrace(Object obj) {
        this.d = obj;
    }

    public StreamAllocation streamAllocation() {
        return this.c;
    }

    public Response intercept(Chain chain) {
        Request request = chain.request();
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        Call call = realInterceptorChain.call();
        EventListener eventListener = realInterceptorChain.eventListener();
        StreamAllocation streamAllocation = new StreamAllocation(this.a.connectionPool(), a(request.url()), call, eventListener, this.d);
        this.c = streamAllocation;
        Response response = null;
        int i = 0;
        while (!this.e) {
            try {
                Response proceed = realInterceptorChain.proceed(request, streamAllocation, null, null);
                Response build = response != null ? proceed.newBuilder().priorResponse(response.newBuilder().body(null).build()).build() : proceed;
                try {
                    Request a2 = a(build, streamAllocation.route());
                    if (a2 == null) {
                        if (!this.b) {
                            streamAllocation.release();
                        }
                        return build;
                    }
                    Util.closeQuietly((Closeable) build.body());
                    int i2 = i + 1;
                    if (i2 > 20) {
                        streamAllocation.release();
                        StringBuilder sb = new StringBuilder();
                        sb.append("Too many follow-up requests: ");
                        sb.append(i2);
                        throw new ProtocolException(sb.toString());
                    } else if (a2.body() instanceof UnrepeatableRequestBody) {
                        streamAllocation.release();
                        throw new HttpRetryException("Cannot retry streamed HTTP body", build.code());
                    } else {
                        if (!a(build, a2.url())) {
                            streamAllocation.release();
                            streamAllocation = new StreamAllocation(this.a.connectionPool(), a(a2.url()), call, eventListener, this.d);
                            this.c = streamAllocation;
                        } else if (streamAllocation.codec() != null) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Closing the body of ");
                            sb2.append(build);
                            sb2.append(" didn't close its backing stream. Bad interceptor?");
                            throw new IllegalStateException(sb2.toString());
                        }
                        response = build;
                        request = a2;
                        i = i2;
                    }
                } catch (IOException e2) {
                    streamAllocation.release();
                    throw e2;
                }
            } catch (RouteException e3) {
                if (!a(e3.getLastConnectException(), streamAllocation, false, request)) {
                    throw e3.getFirstConnectException();
                }
            } catch (IOException e4) {
                if (!a(e4, streamAllocation, !(e4 instanceof ConnectionShutdownException), request)) {
                    throw e4;
                }
            } catch (Throwable th) {
                streamAllocation.streamFailed(null);
                streamAllocation.release();
                throw th;
            }
        }
        streamAllocation.release();
        throw new IOException("Canceled");
    }

    private Address a(HttpUrl httpUrl) {
        CertificatePinner certificatePinner;
        HostnameVerifier hostnameVerifier;
        SSLSocketFactory sSLSocketFactory;
        if (httpUrl.isHttps()) {
            SSLSocketFactory sslSocketFactory = this.a.sslSocketFactory();
            hostnameVerifier = this.a.hostnameVerifier();
            sSLSocketFactory = sslSocketFactory;
            certificatePinner = this.a.certificatePinner();
        } else {
            sSLSocketFactory = null;
            hostnameVerifier = null;
            certificatePinner = null;
        }
        Address address = new Address(httpUrl.host(), httpUrl.port(), this.a.dns(), this.a.socketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, this.a.proxyAuthenticator(), this.a.proxy(), this.a.protocols(), this.a.connectionSpecs(), this.a.proxySelector());
        return address;
    }

    private boolean a(IOException iOException, StreamAllocation streamAllocation, boolean z, Request request) {
        streamAllocation.streamFailed(iOException);
        if (!this.a.retryOnConnectionFailure()) {
            return false;
        }
        if ((!z || !(request.body() instanceof UnrepeatableRequestBody)) && a(iOException, z) && streamAllocation.hasMoreRoutes()) {
            return true;
        }
        return false;
    }

    private boolean a(IOException iOException, boolean z) {
        boolean z2 = false;
        if (iOException instanceof ProtocolException) {
            return false;
        }
        if (iOException instanceof InterruptedIOException) {
            if ((iOException instanceof SocketTimeoutException) && !z) {
                z2 = true;
            }
            return z2;
        } else if ((!(iOException instanceof SSLHandshakeException) || !(iOException.getCause() instanceof CertificateException)) && !(iOException instanceof SSLPeerUnverifiedException)) {
            return true;
        } else {
            return false;
        }
    }

    private Request a(Response response, Route route) {
        Proxy proxy;
        if (response == null) {
            throw new IllegalStateException();
        }
        int code = response.code();
        String method = response.request().method();
        RequestBody requestBody = null;
        switch (code) {
            case HttpStatus.SC_MULTIPLE_CHOICES /*300*/:
            case HttpStatus.SC_MOVED_PERMANENTLY /*301*/:
            case HttpStatus.SC_MOVED_TEMPORARILY /*302*/:
            case HttpStatus.SC_SEE_OTHER /*303*/:
                break;
            case 307:
            case 308:
                if (!method.equals("GET") && !method.equals("HEAD")) {
                    return null;
                }
            case HttpStatus.SC_UNAUTHORIZED /*401*/:
                return this.a.authenticator().authenticate(route, response);
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /*407*/:
                if (route != null) {
                    proxy = route.proxy();
                } else {
                    proxy = this.a.proxy();
                }
                if (proxy.type() == Type.HTTP) {
                    return this.a.proxyAuthenticator().authenticate(route, response);
                }
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            case HttpStatus.SC_REQUEST_TIMEOUT /*408*/:
                if (!this.a.retryOnConnectionFailure() || (response.request().body() instanceof UnrepeatableRequestBody)) {
                    return null;
                }
                if ((response.priorResponse() == null || response.priorResponse().code() != 408) && a(response, 0) <= 0) {
                    return response.request();
                }
                return null;
            case HttpStatus.SC_SERVICE_UNAVAILABLE /*503*/:
                if ((response.priorResponse() == null || response.priorResponse().code() != 503) && a(response, (int) SubsamplingScaleImageView.TILE_SIZE_AUTO) == 0) {
                    return response.request();
                }
                return null;
            default:
                return null;
        }
        if (!this.a.followRedirects()) {
            return null;
        }
        String header = response.header("Location");
        if (header == null) {
            return null;
        }
        HttpUrl resolve = response.request().url().resolve(header);
        if (resolve == null) {
            return null;
        }
        if (!resolve.scheme().equals(response.request().url().scheme()) && !this.a.followSslRedirects()) {
            return null;
        }
        Builder newBuilder = response.request().newBuilder();
        if (HttpMethod.permitsRequestBody(method)) {
            boolean redirectsWithBody = HttpMethod.redirectsWithBody(method);
            if (HttpMethod.redirectsToGet(method)) {
                newBuilder.method("GET", null);
            } else {
                if (redirectsWithBody) {
                    requestBody = response.request().body();
                }
                newBuilder.method(method, requestBody);
            }
            if (!redirectsWithBody) {
                newBuilder.removeHeader("Transfer-Encoding");
                newBuilder.removeHeader("Content-Length");
                newBuilder.removeHeader("Content-Type");
            }
        }
        if (!a(response, resolve)) {
            newBuilder.removeHeader("Authorization");
        }
        return newBuilder.url(resolve).build();
    }

    private int a(Response response, int i) {
        String header = response.header("Retry-After");
        if (header == null) {
            return i;
        }
        return header.matches("\\d+") ? Integer.valueOf(header).intValue() : SubsamplingScaleImageView.TILE_SIZE_AUTO;
    }

    private boolean a(Response response, HttpUrl httpUrl) {
        HttpUrl url = response.request().url();
        return url.host().equals(httpUrl.host()) && url.port() == httpUrl.port() && url.scheme().equals(httpUrl.scheme());
    }
}
