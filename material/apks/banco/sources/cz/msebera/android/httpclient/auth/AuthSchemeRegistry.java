package cz.msebera.android.httpclient.auth;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
@Deprecated
public final class AuthSchemeRegistry implements Lookup<AuthSchemeProvider> {
    private final ConcurrentHashMap<String, AuthSchemeFactory> a = new ConcurrentHashMap<>();

    public void register(String str, AuthSchemeFactory authSchemeFactory) {
        Args.notNull(str, "Name");
        Args.notNull(authSchemeFactory, "Authentication scheme factory");
        this.a.put(str.toLowerCase(Locale.ENGLISH), authSchemeFactory);
    }

    public void unregister(String str) {
        Args.notNull(str, "Name");
        this.a.remove(str.toLowerCase(Locale.ENGLISH));
    }

    public AuthScheme getAuthScheme(String str, HttpParams httpParams) {
        Args.notNull(str, "Name");
        AuthSchemeFactory authSchemeFactory = (AuthSchemeFactory) this.a.get(str.toLowerCase(Locale.ENGLISH));
        if (authSchemeFactory != null) {
            return authSchemeFactory.newInstance(httpParams);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unsupported authentication scheme: ");
        sb.append(str);
        throw new IllegalStateException(sb.toString());
    }

    public List<String> getSchemeNames() {
        return new ArrayList(this.a.keySet());
    }

    public void setItems(Map<String, AuthSchemeFactory> map) {
        if (map != null) {
            this.a.clear();
            this.a.putAll(map);
        }
    }

    public AuthSchemeProvider lookup(final String str) {
        return new AuthSchemeProvider() {
            public AuthScheme create(HttpContext httpContext) {
                return AuthSchemeRegistry.this.getAuthScheme(str, ((HttpRequest) httpContext.getAttribute("http.request")).getParams());
            }
        };
    }
}
