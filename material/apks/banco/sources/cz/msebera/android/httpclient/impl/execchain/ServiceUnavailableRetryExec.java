package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.ServiceUnavailableRetryStrategy;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.util.Args;

@Immutable
public class ServiceUnavailableRetryExec implements ClientExecChain {
    private final ClientExecChain a;
    private final ServiceUnavailableRetryStrategy b;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public ServiceUnavailableRetryExec(ClientExecChain clientExecChain, ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        Args.notNull(clientExecChain, "HTTP request executor");
        Args.notNull(serviceUnavailableRetryStrategy, "Retry strategy");
        this.a = clientExecChain;
        this.b = serviceUnavailableRetryStrategy;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:8|9|10) */
    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0048, code lost:
        throw new java.io.InterruptedIOException();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x003c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cz.msebera.android.httpclient.client.methods.CloseableHttpResponse execute(cz.msebera.android.httpclient.conn.routing.HttpRoute r9, cz.msebera.android.httpclient.client.methods.HttpRequestWrapper r10, cz.msebera.android.httpclient.client.protocol.HttpClientContext r11, cz.msebera.android.httpclient.client.methods.HttpExecutionAware r12) {
        /*
            r8 = this;
            cz.msebera.android.httpclient.Header[] r0 = r10.getAllHeaders()
            r1 = 1
        L_0x0005:
            cz.msebera.android.httpclient.impl.execchain.ClientExecChain r2 = r8.a
            cz.msebera.android.httpclient.client.methods.CloseableHttpResponse r2 = r2.execute(r9, r10, r11, r12)
            cz.msebera.android.httpclient.client.ServiceUnavailableRetryStrategy r3 = r8.b     // Catch:{ RuntimeException -> 0x0050 }
            boolean r3 = r3.retryRequest(r2, r1, r11)     // Catch:{ RuntimeException -> 0x0050 }
            if (r3 == 0) goto L_0x004f
            r2.close()     // Catch:{ RuntimeException -> 0x0050 }
            cz.msebera.android.httpclient.client.ServiceUnavailableRetryStrategy r3 = r8.b     // Catch:{ RuntimeException -> 0x0050 }
            long r3 = r3.getRetryInterval()     // Catch:{ RuntimeException -> 0x0050 }
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x0049
            cz.msebera.android.httpclient.extras.HttpClientAndroidLog r5 = r8.log     // Catch:{ InterruptedException -> 0x003c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x003c }
            r6.<init>()     // Catch:{ InterruptedException -> 0x003c }
            java.lang.String r7 = "Wait for "
            r6.append(r7)     // Catch:{ InterruptedException -> 0x003c }
            r6.append(r3)     // Catch:{ InterruptedException -> 0x003c }
            java.lang.String r6 = r6.toString()     // Catch:{ InterruptedException -> 0x003c }
            r5.trace(r6)     // Catch:{ InterruptedException -> 0x003c }
            java.lang.Thread.sleep(r3)     // Catch:{ InterruptedException -> 0x003c }
            goto L_0x0049
        L_0x003c:
            java.lang.Thread r9 = java.lang.Thread.currentThread()     // Catch:{ RuntimeException -> 0x0050 }
            r9.interrupt()     // Catch:{ RuntimeException -> 0x0050 }
            java.io.InterruptedIOException r9 = new java.io.InterruptedIOException     // Catch:{ RuntimeException -> 0x0050 }
            r9.<init>()     // Catch:{ RuntimeException -> 0x0050 }
            throw r9     // Catch:{ RuntimeException -> 0x0050 }
        L_0x0049:
            r10.setHeaders(r0)     // Catch:{ RuntimeException -> 0x0050 }
            int r1 = r1 + 1
            goto L_0x0005
        L_0x004f:
            return r2
        L_0x0050:
            r9 = move-exception
            r2.close()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.execchain.ServiceUnavailableRetryExec.execute(cz.msebera.android.httpclient.conn.routing.HttpRoute, cz.msebera.android.httpclient.client.methods.HttpRequestWrapper, cz.msebera.android.httpclient.client.protocol.HttpClientContext, cz.msebera.android.httpclient.client.methods.HttpExecutionAware):cz.msebera.android.httpclient.client.methods.CloseableHttpResponse");
    }
}
