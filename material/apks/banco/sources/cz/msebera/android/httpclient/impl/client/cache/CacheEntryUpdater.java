package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.util.Args;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

@Immutable
class CacheEntryUpdater {
    private final ResourceFactory a;

    CacheEntryUpdater() {
        this(new HeapResourceFactory());
    }

    CacheEntryUpdater(ResourceFactory resourceFactory) {
        this.a = resourceFactory;
    }

    public HttpCacheEntry a(String str, HttpCacheEntry httpCacheEntry, Date date, Date date2, HttpResponse httpResponse) {
        Args.check(httpResponse.getStatusLine().getStatusCode() == 304, "Response must have 304 status code");
        HttpCacheEntry httpCacheEntry2 = new HttpCacheEntry(date, date2, httpCacheEntry.getStatusLine(), a(httpCacheEntry, httpResponse), httpCacheEntry.getResource() != null ? this.a.copy(str, httpCacheEntry.getResource()) : null, httpCacheEntry.getRequestMethod());
        return httpCacheEntry2;
    }

    /* access modifiers changed from: protected */
    public Header[] a(HttpCacheEntry httpCacheEntry, HttpResponse httpResponse) {
        if (c(httpCacheEntry, httpResponse) && b(httpCacheEntry, httpResponse)) {
            return httpCacheEntry.getAllHeaders();
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(httpCacheEntry.getAllHeaders()));
        a((List<Header>) arrayList, httpResponse);
        a((List<Header>) arrayList, httpCacheEntry);
        arrayList.addAll(Arrays.asList(httpResponse.getAllHeaders()));
        return (Header[]) arrayList.toArray(new Header[arrayList.size()]);
    }

    private void a(List<Header> list, HttpResponse httpResponse) {
        Header[] allHeaders;
        for (Header header : httpResponse.getAllHeaders()) {
            ListIterator listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                if (((Header) listIterator.next()).getName().equals(header.getName())) {
                    listIterator.remove();
                }
            }
        }
    }

    private void a(List<Header> list, HttpCacheEntry httpCacheEntry) {
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if ("Warning".equals(((Header) listIterator.next()).getName())) {
                for (Header value : httpCacheEntry.getHeaders("Warning")) {
                    if (value.getValue().startsWith("1")) {
                        listIterator.remove();
                    }
                }
            }
        }
    }

    private boolean b(HttpCacheEntry httpCacheEntry, HttpResponse httpResponse) {
        Date parseDate = DateUtils.parseDate(httpCacheEntry.getFirstHeader("Date").getValue());
        Date parseDate2 = DateUtils.parseDate(httpResponse.getFirstHeader("Date").getValue());
        if (parseDate == null || parseDate2 == null || !parseDate.after(parseDate2)) {
            return false;
        }
        return true;
    }

    private boolean c(HttpCacheEntry httpCacheEntry, HttpResponse httpResponse) {
        return (httpCacheEntry.getFirstHeader("Date") == null || httpResponse.getFirstHeader("Date") == null) ? false : true;
    }
}
