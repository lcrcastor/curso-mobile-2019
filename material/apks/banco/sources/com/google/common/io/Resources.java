package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@GwtIncompatible
@Beta
public final class Resources {

    static final class UrlByteSource extends ByteSource {
        private final URL a;

        private UrlByteSource(URL url) {
            this.a = (URL) Preconditions.checkNotNull(url);
        }

        public InputStream openStream() {
            return this.a.openStream();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Resources.asByteSource(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    private Resources() {
    }

    public static ByteSource asByteSource(URL url) {
        return new UrlByteSource(url);
    }

    public static CharSource asCharSource(URL url, Charset charset) {
        return asByteSource(url).asCharSource(charset);
    }

    public static byte[] toByteArray(URL url) {
        return asByteSource(url).read();
    }

    public static String toString(URL url, Charset charset) {
        return asCharSource(url, charset).read();
    }

    @CanIgnoreReturnValue
    public static <T> T readLines(URL url, Charset charset, LineProcessor<T> lineProcessor) {
        return asCharSource(url, charset).readLines(lineProcessor);
    }

    public static List<String> readLines(URL url, Charset charset) {
        return (List) readLines(url, charset, new LineProcessor<List<String>>() {
            final List<String> a = Lists.newArrayList();

            public boolean processLine(String str) {
                this.a.add(str);
                return true;
            }

            /* renamed from: a */
            public List<String> getResult() {
                return this.a;
            }
        });
    }

    public static void copy(URL url, OutputStream outputStream) {
        asByteSource(url).copyTo(outputStream);
    }

    @CanIgnoreReturnValue
    public static URL getResource(String str) {
        URL resource = ((ClassLoader) MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader())).getResource(str);
        Preconditions.checkArgument(resource != null, "resource %s not found.", (Object) str);
        return resource;
    }

    public static URL getResource(Class<?> cls, String str) {
        URL resource = cls.getResource(str);
        Preconditions.checkArgument(resource != null, "resource %s relative to %s not found.", (Object) str, (Object) cls.getName());
        return resource;
    }
}
