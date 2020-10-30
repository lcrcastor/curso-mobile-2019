package cz.msebera.android.httpclient.params;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
@Deprecated
public class BasicHttpParams extends AbstractHttpParams implements Serializable, Cloneable {
    private static final long serialVersionUID = -7086398485908701455L;
    private final Map<String, Object> a = new ConcurrentHashMap();

    public Object getParameter(String str) {
        return this.a.get(str);
    }

    public HttpParams setParameter(String str, Object obj) {
        if (str == null) {
            return this;
        }
        if (obj != null) {
            this.a.put(str, obj);
        } else {
            this.a.remove(str);
        }
        return this;
    }

    public boolean removeParameter(String str) {
        if (!this.a.containsKey(str)) {
            return false;
        }
        this.a.remove(str);
        return true;
    }

    public void setParameters(String[] strArr, Object obj) {
        for (String parameter : strArr) {
            setParameter(parameter, obj);
        }
    }

    public boolean isParameterSet(String str) {
        return getParameter(str) != null;
    }

    public boolean isParameterSetLocally(String str) {
        return this.a.get(str) != null;
    }

    public void clear() {
        this.a.clear();
    }

    public HttpParams copy() {
        try {
            return (HttpParams) clone();
        } catch (CloneNotSupportedException unused) {
            throw new UnsupportedOperationException("Cloning not supported");
        }
    }

    public Object clone() {
        BasicHttpParams basicHttpParams = (BasicHttpParams) super.clone();
        copyParams(basicHttpParams);
        return basicHttpParams;
    }

    public void copyParams(HttpParams httpParams) {
        for (Entry entry : this.a.entrySet()) {
            httpParams.setParameter((String) entry.getKey(), entry.getValue());
        }
    }

    public Set<String> getNames() {
        return new HashSet(this.a.keySet());
    }
}
