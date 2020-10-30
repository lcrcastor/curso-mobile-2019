package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.ByteString;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    private final Map<String, Set<ByteString>> a;

    public static final class Builder {
        /* access modifiers changed from: private */
        public final Map<String, Set<ByteString>> a = new LinkedHashMap();

        public Builder add(String str, String... strArr) {
            if (str == null) {
                throw new IllegalArgumentException("hostname == null");
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Set set = (Set) this.a.put(str, Collections.unmodifiableSet(linkedHashSet));
            if (set != null) {
                linkedHashSet.addAll(set);
            }
            for (String str2 : strArr) {
                if (!str2.startsWith("sha1/")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("pins must start with 'sha1/': ");
                    sb.append(str2);
                    throw new IllegalArgumentException(sb.toString());
                }
                ByteString decodeBase64 = ByteString.decodeBase64(str2.substring("sha1/".length()));
                if (decodeBase64 == null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("pins must be base64: ");
                    sb2.append(str2);
                    throw new IllegalArgumentException(sb2.toString());
                }
                linkedHashSet.add(decodeBase64);
            }
            return this;
        }

        public CertificatePinner build() {
            return new CertificatePinner(this);
        }
    }

    private CertificatePinner(Builder builder) {
        this.a = Util.immutableMap(builder.a);
    }

    public void check(String str, List<Certificate> list) {
        Set<ByteString> a2 = a(str);
        if (a2 != null) {
            int size = list.size();
            int i = 0;
            while (i < size) {
                if (!a2.contains(a((X509Certificate) list.get(i)))) {
                    i++;
                } else {
                    return;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Certificate pinning failure!");
            sb.append("\n  Peer certificate chain:");
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                X509Certificate x509Certificate = (X509Certificate) list.get(i2);
                sb.append("\n    ");
                sb.append(pin(x509Certificate));
                sb.append(": ");
                sb.append(x509Certificate.getSubjectDN().getName());
            }
            sb.append("\n  Pinned certificates for ");
            sb.append(str);
            sb.append(":");
            for (ByteString byteString : a2) {
                sb.append("\n    sha1/");
                sb.append(byteString.base64());
            }
            throw new SSLPeerUnverifiedException(sb.toString());
        }
    }

    public void check(String str, Certificate... certificateArr) {
        check(str, Arrays.asList(certificateArr));
    }

    /* access modifiers changed from: 0000 */
    public Set<ByteString> a(String str) {
        Set<ByteString> set;
        Set<ByteString> set2 = (Set) this.a.get(str);
        int indexOf = str.indexOf(46);
        if (indexOf != str.lastIndexOf(46)) {
            Map<String, Set<ByteString>> map = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append("*.");
            sb.append(str.substring(indexOf + 1));
            set = (Set) map.get(sb.toString());
        } else {
            set = null;
        }
        if (set2 == null && set == null) {
            return null;
        }
        if (set2 == null || set == null) {
            return set2 != null ? set2 : set;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(set2);
        linkedHashSet.addAll(set);
        return linkedHashSet;
    }

    public static String pin(Certificate certificate) {
        if (!(certificate instanceof X509Certificate)) {
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("sha1/");
        sb.append(a((X509Certificate) certificate).base64());
        return sb.toString();
    }

    private static ByteString a(X509Certificate x509Certificate) {
        return Util.sha1(ByteString.of(x509Certificate.getPublicKey().getEncoded()));
    }
}
