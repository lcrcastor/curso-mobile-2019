package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.EntityUtils;

@Immutable
public class BasicResponseHandler extends AbstractResponseHandler<String> {
    public String handleEntity(HttpEntity httpEntity) {
        return EntityUtils.toString(httpEntity);
    }

    public String handleResponse(HttpResponse httpResponse) {
        return (String) super.handleResponse(httpResponse);
    }
}
