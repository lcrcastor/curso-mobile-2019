package cz.msebera.android.httpclient.impl.entity;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.entity.BasicHttpEntity;
import cz.msebera.android.httpclient.entity.ContentLengthStrategy;
import cz.msebera.android.httpclient.impl.io.ChunkedInputStream;
import cz.msebera.android.httpclient.impl.io.ContentLengthInputStream;
import cz.msebera.android.httpclient.impl.io.IdentityInputStream;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.util.Args;

@Immutable
@Deprecated
public class EntityDeserializer {
    private final ContentLengthStrategy a;

    public EntityDeserializer(ContentLengthStrategy contentLengthStrategy) {
        this.a = (ContentLengthStrategy) Args.notNull(contentLengthStrategy, "Content length strategy");
    }

    /* access modifiers changed from: protected */
    public BasicHttpEntity doDeserialize(SessionInputBuffer sessionInputBuffer, HttpMessage httpMessage) {
        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        long determineLength = this.a.determineLength(httpMessage);
        if (determineLength == -2) {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(-1);
            basicHttpEntity.setContent(new ChunkedInputStream(sessionInputBuffer));
        } else if (determineLength == -1) {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(-1);
            basicHttpEntity.setContent(new IdentityInputStream(sessionInputBuffer));
        } else {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(determineLength);
            basicHttpEntity.setContent(new ContentLengthInputStream(sessionInputBuffer, determineLength));
        }
        Header firstHeader = httpMessage.getFirstHeader("Content-Type");
        if (firstHeader != null) {
            basicHttpEntity.setContentType(firstHeader);
        }
        Header firstHeader2 = httpMessage.getFirstHeader("Content-Encoding");
        if (firstHeader2 != null) {
            basicHttpEntity.setContentEncoding(firstHeader2);
        }
        return basicHttpEntity;
    }

    public HttpEntity deserialize(SessionInputBuffer sessionInputBuffer, HttpMessage httpMessage) {
        Args.notNull(sessionInputBuffer, "Session input buffer");
        Args.notNull(httpMessage, "HTTP message");
        return doDeserialize(sessionInputBuffer, httpMessage);
    }
}
