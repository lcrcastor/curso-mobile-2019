package org.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Collection;
import org.bouncycastle.x509.util.StreamParser;

public class X509StreamParser implements StreamParser {
    private Provider a;
    private X509StreamParserSpi b;

    private X509StreamParser(Provider provider, X509StreamParserSpi x509StreamParserSpi) {
        this.a = provider;
        this.b = x509StreamParserSpi;
    }

    private static X509StreamParser a(Implementation implementation) {
        return new X509StreamParser(implementation.b(), (X509StreamParserSpi) implementation.a());
    }

    public static X509StreamParser getInstance(String str) {
        try {
            return a(X509Util.b("X509StreamParser", str));
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchParserException(e.getMessage());
        }
    }

    public static X509StreamParser getInstance(String str, String str2) {
        return getInstance(str, X509Util.c(str2));
    }

    public static X509StreamParser getInstance(String str, Provider provider) {
        try {
            return a(X509Util.a("X509StreamParser", str, provider));
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchParserException(e.getMessage());
        }
    }

    public Provider getProvider() {
        return this.a;
    }

    public void init(InputStream inputStream) {
        this.b.engineInit(inputStream);
    }

    public void init(byte[] bArr) {
        this.b.engineInit(new ByteArrayInputStream(bArr));
    }

    public Object read() {
        return this.b.engineRead();
    }

    public Collection readAll() {
        return this.b.engineReadAll();
    }
}
