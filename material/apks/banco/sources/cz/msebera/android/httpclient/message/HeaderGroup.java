package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderIterator;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@NotThreadSafe
public class HeaderGroup implements Serializable, Cloneable {
    private static final long serialVersionUID = 2608834160639271617L;
    private final Header[] a = new Header[0];
    private final List<Header> b = new ArrayList(16);

    public void clear() {
        this.b.clear();
    }

    public void addHeader(Header header) {
        if (header != null) {
            this.b.add(header);
        }
    }

    public void removeHeader(Header header) {
        if (header != null) {
            this.b.remove(header);
        }
    }

    public void updateHeader(Header header) {
        if (header != null) {
            for (int i = 0; i < this.b.size(); i++) {
                if (((Header) this.b.get(i)).getName().equalsIgnoreCase(header.getName())) {
                    this.b.set(i, header);
                    return;
                }
            }
            this.b.add(header);
        }
    }

    public void setHeaders(Header[] headerArr) {
        clear();
        if (headerArr != null) {
            Collections.addAll(this.b, headerArr);
        }
    }

    public Header getCondensedHeader(String str) {
        Header[] headers = getHeaders(str);
        if (headers.length == 0) {
            return null;
        }
        if (headers.length == 1) {
            return headers[0];
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
        charArrayBuffer.append(headers[0].getValue());
        for (int i = 1; i < headers.length; i++) {
            charArrayBuffer.append(", ");
            charArrayBuffer.append(headers[i].getValue());
        }
        return new BasicHeader(str.toLowerCase(Locale.ROOT), charArrayBuffer.toString());
    }

    public Header[] getHeaders(String str) {
        ArrayList arrayList = null;
        for (int i = 0; i < this.b.size(); i++) {
            Header header = (Header) this.b.get(i);
            if (header.getName().equalsIgnoreCase(str)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(header);
            }
        }
        return arrayList != null ? (Header[]) arrayList.toArray(new Header[arrayList.size()]) : this.a;
    }

    public Header getFirstHeader(String str) {
        for (int i = 0; i < this.b.size(); i++) {
            Header header = (Header) this.b.get(i);
            if (header.getName().equalsIgnoreCase(str)) {
                return header;
            }
        }
        return null;
    }

    public Header getLastHeader(String str) {
        for (int size = this.b.size() - 1; size >= 0; size--) {
            Header header = (Header) this.b.get(size);
            if (header.getName().equalsIgnoreCase(str)) {
                return header;
            }
        }
        return null;
    }

    public Header[] getAllHeaders() {
        return (Header[]) this.b.toArray(new Header[this.b.size()]);
    }

    public boolean containsHeader(String str) {
        for (int i = 0; i < this.b.size(); i++) {
            if (((Header) this.b.get(i)).getName().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public HeaderIterator iterator() {
        return new BasicListHeaderIterator(this.b, null);
    }

    public HeaderIterator iterator(String str) {
        return new BasicListHeaderIterator(this.b, str);
    }

    public HeaderGroup copy() {
        HeaderGroup headerGroup = new HeaderGroup();
        headerGroup.b.addAll(this.b);
        return headerGroup;
    }

    public Object clone() {
        return super.clone();
    }

    public String toString() {
        return this.b.toString();
    }
}
