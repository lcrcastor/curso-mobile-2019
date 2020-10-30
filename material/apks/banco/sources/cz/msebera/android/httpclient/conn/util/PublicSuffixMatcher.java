package cz.msebera.android.httpclient.conn.util;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.net.IDN;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public final class PublicSuffixMatcher {
    private final Map<String, String> a;
    private final Map<String, String> b;

    public PublicSuffixMatcher(Collection<String> collection, Collection<String> collection2) {
        Args.notNull(collection, "Domain suffix rules");
        this.a = new ConcurrentHashMap(collection.size());
        for (String str : collection) {
            this.a.put(str, str);
        }
        if (collection2 != null) {
            this.b = new ConcurrentHashMap(collection2.size());
            for (String str2 : collection2) {
                this.b.put(str2, str2);
            }
            return;
        }
        this.b = null;
    }

    public String getDomainRoot(String str) {
        if (str == null || str.startsWith(".")) {
            return null;
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        String str2 = null;
        while (lowerCase != null) {
            if (this.b != null && this.b.containsKey(IDN.toUnicode(lowerCase))) {
                return lowerCase;
            }
            if (this.a.containsKey(IDN.toUnicode(lowerCase))) {
                break;
            }
            int indexOf = lowerCase.indexOf(46);
            String substring = indexOf != -1 ? lowerCase.substring(indexOf + 1) : null;
            if (substring != null) {
                Map<String, String> map = this.a;
                StringBuilder sb = new StringBuilder();
                sb.append("*.");
                sb.append(IDN.toUnicode(substring));
                if (map.containsKey(sb.toString())) {
                    break;
                }
            }
            if (indexOf != -1) {
                str2 = lowerCase;
            }
            lowerCase = substring;
        }
        return str2;
    }

    public boolean matches(String str) {
        boolean z = false;
        if (str == null) {
            return false;
        }
        if (str.startsWith(".")) {
            str = str.substring(1);
        }
        if (getDomainRoot(str) == null) {
            z = true;
        }
        return z;
    }
}
