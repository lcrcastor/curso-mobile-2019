package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.client.cache.HttpCacheInvalidator;
import cz.msebera.android.httpclient.client.cache.HttpCacheStorage;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import java.io.File;

public class CachingHttpClientBuilder extends HttpClientBuilder {
    private ResourceFactory a;
    private HttpCacheStorage b;
    private File c;
    private CacheConfig d;
    private SchedulingStrategy e;
    private HttpCacheInvalidator f;
    private boolean g = true;

    public static CachingHttpClientBuilder create() {
        return new CachingHttpClientBuilder();
    }

    protected CachingHttpClientBuilder() {
    }

    public final CachingHttpClientBuilder setResourceFactory(ResourceFactory resourceFactory) {
        this.a = resourceFactory;
        return this;
    }

    public final CachingHttpClientBuilder setHttpCacheStorage(HttpCacheStorage httpCacheStorage) {
        this.b = httpCacheStorage;
        return this;
    }

    public final CachingHttpClientBuilder setCacheDir(File file) {
        this.c = file;
        return this;
    }

    public final CachingHttpClientBuilder setCacheConfig(CacheConfig cacheConfig) {
        this.d = cacheConfig;
        return this;
    }

    public final CachingHttpClientBuilder setSchedulingStrategy(SchedulingStrategy schedulingStrategy) {
        this.e = schedulingStrategy;
        return this;
    }

    public final CachingHttpClientBuilder setHttpCacheInvalidator(HttpCacheInvalidator httpCacheInvalidator) {
        this.f = httpCacheInvalidator;
        return this;
    }

    public CachingHttpClientBuilder setDeleteCache(boolean z) {
        this.g = z;
        return this;
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [cz.msebera.android.httpclient.client.cache.HttpCacheStorage] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r3v0, types: [cz.msebera.android.httpclient.client.cache.HttpCacheStorage] */
    /* JADX WARNING: type inference failed for: r1v10, types: [cz.msebera.android.httpclient.impl.client.cache.BasicHttpCacheStorage] */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r1v16 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cz.msebera.android.httpclient.impl.execchain.ClientExecChain decorateMainExec(cz.msebera.android.httpclient.impl.execchain.ClientExecChain r11) {
        /*
            r10 = this;
            cz.msebera.android.httpclient.impl.client.cache.CacheConfig r0 = r10.d
            if (r0 == 0) goto L_0x0007
            cz.msebera.android.httpclient.impl.client.cache.CacheConfig r0 = r10.d
            goto L_0x0009
        L_0x0007:
            cz.msebera.android.httpclient.impl.client.cache.CacheConfig r0 = cz.msebera.android.httpclient.impl.client.cache.CacheConfig.DEFAULT
        L_0x0009:
            cz.msebera.android.httpclient.client.cache.ResourceFactory r1 = r10.a
            if (r1 != 0) goto L_0x001e
            java.io.File r1 = r10.c
            if (r1 != 0) goto L_0x0017
            cz.msebera.android.httpclient.impl.client.cache.HeapResourceFactory r1 = new cz.msebera.android.httpclient.impl.client.cache.HeapResourceFactory
            r1.<init>()
            goto L_0x001e
        L_0x0017:
            cz.msebera.android.httpclient.impl.client.cache.FileResourceFactory r1 = new cz.msebera.android.httpclient.impl.client.cache.FileResourceFactory
            java.io.File r2 = r10.c
            r1.<init>(r2)
        L_0x001e:
            r2 = r1
            cz.msebera.android.httpclient.client.cache.HttpCacheStorage r1 = r10.b
            if (r1 != 0) goto L_0x0042
            java.io.File r1 = r10.c
            if (r1 != 0) goto L_0x002d
            cz.msebera.android.httpclient.impl.client.cache.BasicHttpCacheStorage r1 = new cz.msebera.android.httpclient.impl.client.cache.BasicHttpCacheStorage
            r1.<init>(r0)
            goto L_0x0042
        L_0x002d:
            cz.msebera.android.httpclient.impl.client.cache.ManagedHttpCacheStorage r1 = new cz.msebera.android.httpclient.impl.client.cache.ManagedHttpCacheStorage
            r1.<init>(r0)
            boolean r3 = r10.g
            if (r3 == 0) goto L_0x003f
            cz.msebera.android.httpclient.impl.client.cache.CachingHttpClientBuilder$1 r3 = new cz.msebera.android.httpclient.impl.client.cache.CachingHttpClientBuilder$1
            r3.<init>(r1)
            r10.addCloseable(r3)
            goto L_0x0042
        L_0x003f:
            r10.addCloseable(r1)
        L_0x0042:
            r3 = r1
            cz.msebera.android.httpclient.impl.client.cache.AsynchronousValidator r7 = r10.a(r0)
            cz.msebera.android.httpclient.impl.client.cache.CacheKeyGenerator r5 = new cz.msebera.android.httpclient.impl.client.cache.CacheKeyGenerator
            r5.<init>()
            cz.msebera.android.httpclient.client.cache.HttpCacheInvalidator r1 = r10.f
            if (r1 != 0) goto L_0x0055
            cz.msebera.android.httpclient.impl.client.cache.CacheInvalidator r1 = new cz.msebera.android.httpclient.impl.client.cache.CacheInvalidator
            r1.<init>(r5, r3)
        L_0x0055:
            r6 = r1
            cz.msebera.android.httpclient.impl.client.cache.CachingExec r8 = new cz.msebera.android.httpclient.impl.client.cache.CachingExec
            cz.msebera.android.httpclient.impl.client.cache.BasicHttpCache r9 = new cz.msebera.android.httpclient.impl.client.cache.BasicHttpCache
            r1 = r9
            r4 = r0
            r1.<init>(r2, r3, r4, r5, r6)
            r8.<init>(r11, r9, r0, r7)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.client.cache.CachingHttpClientBuilder.decorateMainExec(cz.msebera.android.httpclient.impl.execchain.ClientExecChain):cz.msebera.android.httpclient.impl.execchain.ClientExecChain");
    }

    private AsynchronousValidator a(CacheConfig cacheConfig) {
        if (cacheConfig.getAsynchronousWorkersMax() <= 0) {
            return null;
        }
        AsynchronousValidator asynchronousValidator = new AsynchronousValidator(b(cacheConfig));
        addCloseable(asynchronousValidator);
        return asynchronousValidator;
    }

    private SchedulingStrategy b(CacheConfig cacheConfig) {
        return this.e != null ? this.e : new ImmediateSchedulingStrategy(cacheConfig);
    }
}
