package org.bouncycastle.jce;

import java.security.cert.CertStoreParameters;
import java.security.cert.LDAPCertStoreParameters;
import org.bouncycastle.x509.X509StoreParameters;

public class X509LDAPCertStoreParameters implements CertStoreParameters, X509StoreParameters {
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;
    private String I;
    private String J;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;

    public static class Builder {
        /* access modifiers changed from: private */
        public String A;
        /* access modifiers changed from: private */
        public String B;
        /* access modifiers changed from: private */
        public String C;
        /* access modifiers changed from: private */
        public String D;
        /* access modifiers changed from: private */
        public String E;
        /* access modifiers changed from: private */
        public String F;
        /* access modifiers changed from: private */
        public String G;
        /* access modifiers changed from: private */
        public String H;
        /* access modifiers changed from: private */
        public String I;
        /* access modifiers changed from: private */
        public String J;
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public String c;
        /* access modifiers changed from: private */
        public String d;
        /* access modifiers changed from: private */
        public String e;
        /* access modifiers changed from: private */
        public String f;
        /* access modifiers changed from: private */
        public String g;
        /* access modifiers changed from: private */
        public String h;
        /* access modifiers changed from: private */
        public String i;
        /* access modifiers changed from: private */
        public String j;
        /* access modifiers changed from: private */
        public String k;
        /* access modifiers changed from: private */
        public String l;
        /* access modifiers changed from: private */
        public String m;
        /* access modifiers changed from: private */
        public String n;
        /* access modifiers changed from: private */
        public String o;
        /* access modifiers changed from: private */
        public String p;
        /* access modifiers changed from: private */
        public String q;
        /* access modifiers changed from: private */
        public String r;
        /* access modifiers changed from: private */
        public String s;
        /* access modifiers changed from: private */
        public String t;
        /* access modifiers changed from: private */
        public String u;
        /* access modifiers changed from: private */
        public String v;
        /* access modifiers changed from: private */
        public String w;
        /* access modifiers changed from: private */
        public String x;
        /* access modifiers changed from: private */
        public String y;
        /* access modifiers changed from: private */
        public String z;

        public Builder() {
            this("ldap://localhost:389", "");
        }

        public Builder(String str, String str2) {
            this.a = str;
            if (str2 == null) {
                this.b = "";
            } else {
                this.b = str2;
            }
            this.c = "userCertificate";
            this.d = "cACertificate";
            this.e = "crossCertificatePair";
            this.f = "certificateRevocationList";
            this.g = "deltaRevocationList";
            this.h = "authorityRevocationList";
            this.i = "attributeCertificateAttribute";
            this.j = "aACertificate";
            this.k = "attributeDescriptorCertificate";
            this.l = "attributeCertificateRevocationList";
            this.m = "attributeAuthorityRevocationList";
            this.n = "cn";
            this.o = "cn ou o";
            this.p = "cn ou o";
            this.q = "cn ou o";
            this.r = "cn ou o";
            this.s = "cn ou o";
            this.t = "cn";
            this.u = "cn o ou";
            this.v = "cn o ou";
            this.w = "cn o ou";
            this.x = "cn o ou";
            this.y = "cn";
            this.z = "o ou";
            this.A = "o ou";
            this.B = "o ou";
            this.C = "o ou";
            this.D = "o ou";
            this.E = "cn";
            this.F = "o ou";
            this.G = "o ou";
            this.H = "o ou";
            this.I = "o ou";
            this.J = "uid serialNumber cn";
        }

        public X509LDAPCertStoreParameters build() {
            if (this.n != null && this.o != null && this.p != null && this.q != null && this.r != null && this.s != null && this.t != null && this.u != null && this.v != null && this.w != null && this.x != null && this.y != null && this.z != null && this.A != null && this.B != null && this.C != null && this.D != null && this.E != null && this.F != null && this.G != null && this.H != null && this.I != null) {
                return new X509LDAPCertStoreParameters(this);
            }
            throw new IllegalArgumentException("Necessary parameters not specified.");
        }

        public Builder setAACertificateAttribute(String str) {
            this.j = str;
            return this;
        }

        public Builder setAACertificateSubjectAttributeName(String str) {
            this.F = str;
            return this;
        }

        public Builder setAttributeAuthorityRevocationListAttribute(String str) {
            this.m = str;
            return this;
        }

        public Builder setAttributeAuthorityRevocationListIssuerAttributeName(String str) {
            this.I = str;
            return this;
        }

        public Builder setAttributeCertificateAttributeAttribute(String str) {
            this.i = str;
            return this;
        }

        public Builder setAttributeCertificateAttributeSubjectAttributeName(String str) {
            this.E = str;
            return this;
        }

        public Builder setAttributeCertificateRevocationListAttribute(String str) {
            this.l = str;
            return this;
        }

        public Builder setAttributeCertificateRevocationListIssuerAttributeName(String str) {
            this.H = str;
            return this;
        }

        public Builder setAttributeDescriptorCertificateAttribute(String str) {
            this.k = str;
            return this;
        }

        public Builder setAttributeDescriptorCertificateSubjectAttributeName(String str) {
            this.G = str;
            return this;
        }

        public Builder setAuthorityRevocationListAttribute(String str) {
            this.h = str;
            return this;
        }

        public Builder setAuthorityRevocationListIssuerAttributeName(String str) {
            this.D = str;
            return this;
        }

        public Builder setCACertificateAttribute(String str) {
            this.d = str;
            return this;
        }

        public Builder setCACertificateSubjectAttributeName(String str) {
            this.z = str;
            return this;
        }

        public Builder setCertificateRevocationListAttribute(String str) {
            this.f = str;
            return this;
        }

        public Builder setCertificateRevocationListIssuerAttributeName(String str) {
            this.B = str;
            return this;
        }

        public Builder setCrossCertificateAttribute(String str) {
            this.e = str;
            return this;
        }

        public Builder setCrossCertificateSubjectAttributeName(String str) {
            this.A = str;
            return this;
        }

        public Builder setDeltaRevocationListAttribute(String str) {
            this.g = str;
            return this;
        }

        public Builder setDeltaRevocationListIssuerAttributeName(String str) {
            this.C = str;
            return this;
        }

        public Builder setLdapAACertificateAttributeName(String str) {
            this.u = str;
            return this;
        }

        public Builder setLdapAttributeAuthorityRevocationListAttributeName(String str) {
            this.x = str;
            return this;
        }

        public Builder setLdapAttributeCertificateAttributeAttributeName(String str) {
            this.t = str;
            return this;
        }

        public Builder setLdapAttributeCertificateRevocationListAttributeName(String str) {
            this.w = str;
            return this;
        }

        public Builder setLdapAttributeDescriptorCertificateAttributeName(String str) {
            this.v = str;
            return this;
        }

        public Builder setLdapAuthorityRevocationListAttributeName(String str) {
            this.s = str;
            return this;
        }

        public Builder setLdapCACertificateAttributeName(String str) {
            this.o = str;
            return this;
        }

        public Builder setLdapCertificateRevocationListAttributeName(String str) {
            this.q = str;
            return this;
        }

        public Builder setLdapCrossCertificateAttributeName(String str) {
            this.p = str;
            return this;
        }

        public Builder setLdapDeltaRevocationListAttributeName(String str) {
            this.r = str;
            return this;
        }

        public Builder setLdapUserCertificateAttributeName(String str) {
            this.n = str;
            return this;
        }

        public Builder setSearchForSerialNumberIn(String str) {
            this.J = str;
            return this;
        }

        public Builder setUserCertificateAttribute(String str) {
            this.c = str;
            return this;
        }

        public Builder setUserCertificateSubjectAttributeName(String str) {
            this.y = str;
            return this;
        }
    }

    private X509LDAPCertStoreParameters(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
        this.k = builder.k;
        this.l = builder.l;
        this.m = builder.m;
        this.n = builder.n;
        this.o = builder.o;
        this.p = builder.p;
        this.q = builder.q;
        this.r = builder.r;
        this.s = builder.s;
        this.t = builder.t;
        this.u = builder.u;
        this.v = builder.v;
        this.w = builder.w;
        this.x = builder.x;
        this.y = builder.y;
        this.z = builder.z;
        this.A = builder.A;
        this.B = builder.B;
        this.C = builder.C;
        this.D = builder.D;
        this.E = builder.E;
        this.F = builder.F;
        this.G = builder.G;
        this.H = builder.H;
        this.I = builder.I;
        this.J = builder.J;
    }

    private int a(int i2, Object obj) {
        return (i2 * 29) + (obj == null ? 0 : obj.hashCode());
    }

    private boolean a(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    public static X509LDAPCertStoreParameters getInstance(LDAPCertStoreParameters lDAPCertStoreParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("ldap://");
        sb.append(lDAPCertStoreParameters.getServerName());
        sb.append(":");
        sb.append(lDAPCertStoreParameters.getPort());
        return new Builder(sb.toString(), "").build();
    }

    public Object clone() {
        return this;
    }

    public boolean equal(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509LDAPCertStoreParameters)) {
            return false;
        }
        X509LDAPCertStoreParameters x509LDAPCertStoreParameters = (X509LDAPCertStoreParameters) obj;
        return a((Object) this.a, (Object) x509LDAPCertStoreParameters.a) && a((Object) this.b, (Object) x509LDAPCertStoreParameters.b) && a((Object) this.c, (Object) x509LDAPCertStoreParameters.c) && a((Object) this.d, (Object) x509LDAPCertStoreParameters.d) && a((Object) this.e, (Object) x509LDAPCertStoreParameters.e) && a((Object) this.f, (Object) x509LDAPCertStoreParameters.f) && a((Object) this.g, (Object) x509LDAPCertStoreParameters.g) && a((Object) this.h, (Object) x509LDAPCertStoreParameters.h) && a((Object) this.i, (Object) x509LDAPCertStoreParameters.i) && a((Object) this.j, (Object) x509LDAPCertStoreParameters.j) && a((Object) this.k, (Object) x509LDAPCertStoreParameters.k) && a((Object) this.l, (Object) x509LDAPCertStoreParameters.l) && a((Object) this.m, (Object) x509LDAPCertStoreParameters.m) && a((Object) this.n, (Object) x509LDAPCertStoreParameters.n) && a((Object) this.o, (Object) x509LDAPCertStoreParameters.o) && a((Object) this.p, (Object) x509LDAPCertStoreParameters.p) && a((Object) this.q, (Object) x509LDAPCertStoreParameters.q) && a((Object) this.r, (Object) x509LDAPCertStoreParameters.r) && a((Object) this.s, (Object) x509LDAPCertStoreParameters.s) && a((Object) this.t, (Object) x509LDAPCertStoreParameters.t) && a((Object) this.u, (Object) x509LDAPCertStoreParameters.u) && a((Object) this.v, (Object) x509LDAPCertStoreParameters.v) && a((Object) this.w, (Object) x509LDAPCertStoreParameters.w) && a((Object) this.x, (Object) x509LDAPCertStoreParameters.x) && a((Object) this.y, (Object) x509LDAPCertStoreParameters.y) && a((Object) this.z, (Object) x509LDAPCertStoreParameters.z) && a((Object) this.A, (Object) x509LDAPCertStoreParameters.A) && a((Object) this.B, (Object) x509LDAPCertStoreParameters.B) && a((Object) this.C, (Object) x509LDAPCertStoreParameters.C) && a((Object) this.D, (Object) x509LDAPCertStoreParameters.D) && a((Object) this.E, (Object) x509LDAPCertStoreParameters.E) && a((Object) this.F, (Object) x509LDAPCertStoreParameters.F) && a((Object) this.G, (Object) x509LDAPCertStoreParameters.G) && a((Object) this.H, (Object) x509LDAPCertStoreParameters.H) && a((Object) this.I, (Object) x509LDAPCertStoreParameters.I) && a((Object) this.J, (Object) x509LDAPCertStoreParameters.J);
    }

    public String getAACertificateAttribute() {
        return this.j;
    }

    public String getAACertificateSubjectAttributeName() {
        return this.F;
    }

    public String getAttributeAuthorityRevocationListAttribute() {
        return this.m;
    }

    public String getAttributeAuthorityRevocationListIssuerAttributeName() {
        return this.I;
    }

    public String getAttributeCertificateAttributeAttribute() {
        return this.i;
    }

    public String getAttributeCertificateAttributeSubjectAttributeName() {
        return this.E;
    }

    public String getAttributeCertificateRevocationListAttribute() {
        return this.l;
    }

    public String getAttributeCertificateRevocationListIssuerAttributeName() {
        return this.H;
    }

    public String getAttributeDescriptorCertificateAttribute() {
        return this.k;
    }

    public String getAttributeDescriptorCertificateSubjectAttributeName() {
        return this.G;
    }

    public String getAuthorityRevocationListAttribute() {
        return this.h;
    }

    public String getAuthorityRevocationListIssuerAttributeName() {
        return this.D;
    }

    public String getBaseDN() {
        return this.b;
    }

    public String getCACertificateAttribute() {
        return this.d;
    }

    public String getCACertificateSubjectAttributeName() {
        return this.z;
    }

    public String getCertificateRevocationListAttribute() {
        return this.f;
    }

    public String getCertificateRevocationListIssuerAttributeName() {
        return this.B;
    }

    public String getCrossCertificateAttribute() {
        return this.e;
    }

    public String getCrossCertificateSubjectAttributeName() {
        return this.A;
    }

    public String getDeltaRevocationListAttribute() {
        return this.g;
    }

    public String getDeltaRevocationListIssuerAttributeName() {
        return this.C;
    }

    public String getLdapAACertificateAttributeName() {
        return this.u;
    }

    public String getLdapAttributeAuthorityRevocationListAttributeName() {
        return this.x;
    }

    public String getLdapAttributeCertificateAttributeAttributeName() {
        return this.t;
    }

    public String getLdapAttributeCertificateRevocationListAttributeName() {
        return this.w;
    }

    public String getLdapAttributeDescriptorCertificateAttributeName() {
        return this.v;
    }

    public String getLdapAuthorityRevocationListAttributeName() {
        return this.s;
    }

    public String getLdapCACertificateAttributeName() {
        return this.o;
    }

    public String getLdapCertificateRevocationListAttributeName() {
        return this.q;
    }

    public String getLdapCrossCertificateAttributeName() {
        return this.p;
    }

    public String getLdapDeltaRevocationListAttributeName() {
        return this.r;
    }

    public String getLdapURL() {
        return this.a;
    }

    public String getLdapUserCertificateAttributeName() {
        return this.n;
    }

    public String getSearchForSerialNumberIn() {
        return this.J;
    }

    public String getUserCertificateAttribute() {
        return this.c;
    }

    public String getUserCertificateSubjectAttributeName() {
        return this.y;
    }

    public int hashCode() {
        return a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(a(0, (Object) this.c), (Object) this.d), (Object) this.e), (Object) this.f), (Object) this.g), (Object) this.h), (Object) this.i), (Object) this.j), (Object) this.k), (Object) this.l), (Object) this.m), (Object) this.n), (Object) this.o), (Object) this.p), (Object) this.q), (Object) this.r), (Object) this.s), (Object) this.t), (Object) this.u), (Object) this.v), (Object) this.w), (Object) this.x), (Object) this.y), (Object) this.z), (Object) this.A), (Object) this.B), (Object) this.C), (Object) this.D), (Object) this.E), (Object) this.F), (Object) this.G), (Object) this.H), (Object) this.I), (Object) this.J);
    }
}
