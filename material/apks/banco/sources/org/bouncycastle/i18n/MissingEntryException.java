package org.bouncycastle.i18n;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

public class MissingEntryException extends RuntimeException {
    private String a;
    protected final String key;
    protected final ClassLoader loader;
    protected final Locale locale;
    protected final String resource;

    public MissingEntryException(String str, String str2, String str3, Locale locale2, ClassLoader classLoader) {
        super(str);
        this.resource = str2;
        this.key = str3;
        this.locale = locale2;
        this.loader = classLoader;
    }

    public MissingEntryException(String str, Throwable th, String str2, String str3, Locale locale2, ClassLoader classLoader) {
        super(str, th);
        this.resource = str2;
        this.key = str3;
        this.locale = locale2;
        this.loader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.loader;
    }

    public String getDebugMsg() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Can not find entry ");
            sb.append(this.key);
            sb.append(" in resource file ");
            sb.append(this.resource);
            sb.append(" for the locale ");
            sb.append(this.locale);
            sb.append(".");
            this.a = sb.toString();
            if (this.loader instanceof URLClassLoader) {
                URL[] uRLs = ((URLClassLoader) this.loader).getURLs();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.a);
                sb2.append(" The following entries in the classpath were searched: ");
                this.a = sb2.toString();
                for (int i = 0; i != uRLs.length; i++) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.a);
                    sb3.append(uRLs[i]);
                    sb3.append(UtilsCuentas.SEPARAOR2);
                    this.a = sb3.toString();
                }
            }
        }
        return this.a;
    }

    public String getKey() {
        return this.key;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getResource() {
        return this.resource;
    }
}
