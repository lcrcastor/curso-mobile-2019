package okhttp3;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    private final Set<Pin> a;
    @Nullable
    private final CertificateChainCleaner b;

    public static final class Builder {
        private final List<Pin> a = new ArrayList();

        public Builder add(String str, String... strArr) {
            if (str == null) {
                throw new NullPointerException("pattern == null");
            }
            for (String pin : strArr) {
                this.a.add(new Pin(str, pin));
            }
            return this;
        }

        public CertificatePinner build() {
            return new CertificatePinner(new LinkedHashSet(this.a), null);
        }
    }

    static final class Pin {
        final String a;
        final String b;
        final String c;
        final ByteString d;

        Pin(String str, String str2) {
            String str3;
            this.a = str;
            if (str.startsWith("*.")) {
                StringBuilder sb = new StringBuilder();
                sb.append("http://");
                sb.append(str.substring("*.".length()));
                str3 = HttpUrl.get(sb.toString()).host();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("http://");
                sb2.append(str);
                str3 = HttpUrl.get(sb2.toString()).host();
            }
            this.b = str3;
            if (str2.startsWith("sha1/")) {
                this.c = "sha1/";
                this.d = ByteString.decodeBase64(str2.substring("sha1/".length()));
            } else if (str2.startsWith("sha256/")) {
                this.c = "sha256/";
                this.d = ByteString.decodeBase64(str2.substring("sha256/".length()));
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("pins must start with 'sha256/' or 'sha1/': ");
                sb3.append(str2);
                throw new IllegalArgumentException(sb3.toString());
            }
            if (this.d == null) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("pins must be base64: ");
                sb4.append(str2);
                throw new IllegalArgumentException(sb4.toString());
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0030, code lost:
            if (r11.regionMatches(false, r0 + 1, r10.b, 0, r10.b.length()) != false) goto L_0x0034;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(java.lang.String r11) {
            /*
                r10 = this;
                java.lang.String r0 = r10.a
                java.lang.String r1 = "*."
                boolean r0 = r0.startsWith(r1)
                if (r0 == 0) goto L_0x0035
                r0 = 46
                int r0 = r11.indexOf(r0)
                int r1 = r11.length()
                int r1 = r1 - r0
                r2 = 1
                int r1 = r1 - r2
                java.lang.String r3 = r10.b
                int r3 = r3.length()
                if (r1 != r3) goto L_0x0033
                r5 = 0
                int r6 = r0 + 1
                java.lang.String r7 = r10.b
                r8 = 0
                java.lang.String r0 = r10.b
                int r9 = r0.length()
                r4 = r11
                boolean r11 = r4.regionMatches(r5, r6, r7, r8, r9)
                if (r11 == 0) goto L_0x0033
                goto L_0x0034
            L_0x0033:
                r2 = 0
            L_0x0034:
                return r2
            L_0x0035:
                java.lang.String r0 = r10.b
                boolean r11 = r11.equals(r0)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.CertificatePinner.Pin.a(java.lang.String):boolean");
        }

        public boolean equals(Object obj) {
            if (obj instanceof Pin) {
                Pin pin = (Pin) obj;
                if (this.a.equals(pin.a) && this.c.equals(pin.c) && this.d.equals(pin.d)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return ((((527 + this.a.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.c);
            sb.append(this.d.base64());
            return sb.toString();
        }
    }

    CertificatePinner(Set<Pin> set, @Nullable CertificateChainCleaner certificateChainCleaner) {
        this.a = set;
        this.b = certificateChainCleaner;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001c, code lost:
        if (r3.a.equals(r4.a) != false) goto L_0x0020;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@javax.annotation.Nullable java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 1
            if (r4 != r3) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r4 instanceof okhttp3.CertificatePinner
            if (r1 == 0) goto L_0x001f
            okhttp3.internal.tls.CertificateChainCleaner r1 = r3.b
            okhttp3.CertificatePinner r4 = (okhttp3.CertificatePinner) r4
            okhttp3.internal.tls.CertificateChainCleaner r2 = r4.b
            boolean r1 = okhttp3.internal.Util.equal(r1, r2)
            if (r1 == 0) goto L_0x001f
            java.util.Set<okhttp3.CertificatePinner$Pin> r1 = r3.a
            java.util.Set<okhttp3.CertificatePinner$Pin> r4 = r4.a
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L_0x001f
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.CertificatePinner.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return ((this.b != null ? this.b.hashCode() : 0) * 31) + this.a.hashCode();
    }

    public void check(String str, List<Certificate> list) {
        List a2 = a(str);
        if (!a2.isEmpty()) {
            if (this.b != null) {
                list = this.b.clean(list, str);
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                X509Certificate x509Certificate = (X509Certificate) list.get(i);
                int size2 = a2.size();
                ByteString byteString = null;
                ByteString byteString2 = null;
                for (int i2 = 0; i2 < size2; i2++) {
                    Pin pin = (Pin) a2.get(i2);
                    if (pin.c.equals("sha256/")) {
                        if (byteString == null) {
                            byteString = b(x509Certificate);
                        }
                        if (pin.d.equals(byteString)) {
                            return;
                        }
                    } else if (pin.c.equals("sha1/")) {
                        if (byteString2 == null) {
                            byteString2 = a(x509Certificate);
                        }
                        if (pin.d.equals(byteString2)) {
                            return;
                        }
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("unsupported hashAlgorithm: ");
                        sb.append(pin.c);
                        throw new AssertionError(sb.toString());
                    }
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Certificate pinning failure!");
            sb2.append("\n  Peer certificate chain:");
            int size3 = list.size();
            for (int i3 = 0; i3 < size3; i3++) {
                X509Certificate x509Certificate2 = (X509Certificate) list.get(i3);
                sb2.append("\n    ");
                sb2.append(pin(x509Certificate2));
                sb2.append(": ");
                sb2.append(x509Certificate2.getSubjectDN().getName());
            }
            sb2.append("\n  Pinned certificates for ");
            sb2.append(str);
            sb2.append(":");
            int size4 = a2.size();
            for (int i4 = 0; i4 < size4; i4++) {
                Pin pin2 = (Pin) a2.get(i4);
                sb2.append("\n    ");
                sb2.append(pin2);
            }
            throw new SSLPeerUnverifiedException(sb2.toString());
        }
    }

    public void check(String str, Certificate... certificateArr) {
        check(str, Arrays.asList(certificateArr));
    }

    /* access modifiers changed from: 0000 */
    public List<Pin> a(String str) {
        List<Pin> emptyList = Collections.emptyList();
        for (Pin pin : this.a) {
            if (pin.a(str)) {
                if (emptyList.isEmpty()) {
                    emptyList = new ArrayList<>();
                }
                emptyList.add(pin);
            }
        }
        return emptyList;
    }

    /* access modifiers changed from: 0000 */
    public CertificatePinner a(@Nullable CertificateChainCleaner certificateChainCleaner) {
        if (Util.equal(this.b, certificateChainCleaner)) {
            return this;
        }
        return new CertificatePinner(this.a, certificateChainCleaner);
    }

    public static String pin(Certificate certificate) {
        if (!(certificate instanceof X509Certificate)) {
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("sha256/");
        sb.append(b((X509Certificate) certificate).base64());
        return sb.toString();
    }

    static ByteString a(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha1();
    }

    static ByteString b(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha256();
    }
}
