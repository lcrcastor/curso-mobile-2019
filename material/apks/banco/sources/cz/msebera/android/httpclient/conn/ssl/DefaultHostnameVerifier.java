package cz.msebera.android.httpclient.conn.ssl;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
import cz.msebera.android.httpclient.conn.util.PublicSuffixMatcher;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

@Immutable
public final class DefaultHostnameVerifier implements HostnameVerifier {
    private final PublicSuffixMatcher a;
    public HttpClientAndroidLog log;

    public DefaultHostnameVerifier(PublicSuffixMatcher publicSuffixMatcher) {
        this.log = new HttpClientAndroidLog(getClass());
        this.a = publicSuffixMatcher;
    }

    public DefaultHostnameVerifier() {
        this(null);
    }

    public final boolean verify(String str, SSLSession sSLSession) {
        try {
            verify(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
            return true;
        } catch (SSLException e) {
            if (this.log.isDebugEnabled()) {
                this.log.debug(e.getMessage(), e);
            }
            return false;
        }
    }

    public final void verify(String str, X509Certificate x509Certificate) {
        boolean isIPv4Address = InetAddressUtils.isIPv4Address(str);
        boolean isIPv6Address = InetAddressUtils.isIPv6Address(str);
        List a2 = a(x509Certificate, (isIPv4Address || isIPv6Address) ? 7 : 2);
        if (a2 == null || a2.isEmpty()) {
            String findMostSpecific = new DistinguishedNameParser(x509Certificate.getSubjectX500Principal()).findMostSpecific("cn");
            if (findMostSpecific == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Certificate subject for <");
                sb.append(str);
                sb.append("> doesn't contain ");
                sb.append("a common name and does not have alternative names");
                throw new SSLException(sb.toString());
            }
            a(str, findMostSpecific, this.a);
        } else if (isIPv4Address) {
            a(str, a2);
        } else if (isIPv6Address) {
            b(str, a2);
        } else {
            a(str, a2, this.a);
        }
    }

    static void a(String str, List<String> list) {
        int i = 0;
        while (i < list.size()) {
            if (!str.equals((String) list.get(i))) {
                i++;
            } else {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate for <");
        sb.append(str);
        sb.append("> doesn't match any ");
        sb.append("of the subject alternative names: ");
        sb.append(list);
        throw new SSLException(sb.toString());
    }

    static void b(String str, List<String> list) {
        String a2 = a(str);
        int i = 0;
        while (i < list.size()) {
            if (!a2.equals(a((String) list.get(i)))) {
                i++;
            } else {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate for <");
        sb.append(str);
        sb.append("> doesn't match any ");
        sb.append("of the subject alternative names: ");
        sb.append(list);
        throw new SSLException(sb.toString());
    }

    static void a(String str, List<String> list, PublicSuffixMatcher publicSuffixMatcher) {
        String lowerCase = str.toLowerCase(Locale.ROOT);
        int i = 0;
        while (i < list.size()) {
            if (!b(lowerCase, ((String) list.get(i)).toLowerCase(Locale.ROOT), publicSuffixMatcher)) {
                i++;
            } else {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate for <");
        sb.append(str);
        sb.append("> doesn't match any ");
        sb.append("of the subject alternative names: ");
        sb.append(list);
        throw new SSLException(sb.toString());
    }

    static void a(String str, String str2, PublicSuffixMatcher publicSuffixMatcher) {
        if (!b(str, str2, publicSuffixMatcher)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Certificate for <");
            sb.append(str);
            sb.append("> doesn't match ");
            sb.append("common name of the certificate subject: ");
            sb.append(str2);
            throw new SSLException(sb.toString());
        }
    }

    static boolean a(String str, String str2) {
        boolean z = false;
        if (str2 == null) {
            return false;
        }
        if (str.endsWith(str2) && (str.length() == str2.length() || str.charAt((str.length() - str2.length()) - 1) == '.')) {
            z = true;
        }
        return z;
    }

    private static boolean a(String str, String str2, PublicSuffixMatcher publicSuffixMatcher, boolean z) {
        if (publicSuffixMatcher != null && str.contains(".") && !a(str, publicSuffixMatcher.getDomainRoot(str2))) {
            return false;
        }
        int indexOf = str2.indexOf(42);
        if (indexOf == -1) {
            return str.equalsIgnoreCase(str2);
        }
        String substring = str2.substring(0, indexOf);
        String substring2 = str2.substring(indexOf + 1);
        if (!substring.isEmpty() && !str.startsWith(substring)) {
            return false;
        }
        if (!substring2.isEmpty() && !str.endsWith(substring2)) {
            return false;
        }
        if (!z || !str.substring(substring.length(), str.length() - substring2.length()).contains(".")) {
            return true;
        }
        return false;
    }

    static boolean b(String str, String str2, PublicSuffixMatcher publicSuffixMatcher) {
        return a(str, str2, publicSuffixMatcher, true);
    }

    static List<String> a(X509Certificate x509Certificate, int i) {
        Collection<List> collection;
        ArrayList arrayList = null;
        try {
            collection = x509Certificate.getSubjectAlternativeNames();
        } catch (CertificateParsingException unused) {
            collection = null;
        }
        if (collection != null) {
            for (List list : collection) {
                if (((Integer) list.get(0)).intValue() == i) {
                    String str = (String) list.get(1);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    static String a(String str) {
        if (str == null) {
            return str;
        }
        try {
            return InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException unused) {
            return str;
        }
    }
}
