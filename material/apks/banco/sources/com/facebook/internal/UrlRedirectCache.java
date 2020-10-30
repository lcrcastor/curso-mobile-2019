package com.facebook.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache.Limits;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

class UrlRedirectCache {
    static final String a = "UrlRedirectCache";
    private static final String b;
    private static volatile FileLruCache c;

    UrlRedirectCache() {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append("_Redirect");
        b = sb.toString();
    }

    static synchronized FileLruCache a(Context context) {
        FileLruCache fileLruCache;
        synchronized (UrlRedirectCache.class) {
            if (c == null) {
                c = new FileLruCache(context.getApplicationContext(), a, new Limits());
            }
            fileLruCache = c;
        }
        return fileLruCache;
    }

    static URI a(Context context, URI uri) {
        InputStreamReader inputStreamReader;
        if (uri == null) {
            return null;
        }
        String uri2 = uri.toString();
        try {
            FileLruCache a2 = a(context);
            InputStreamReader inputStreamReader2 = null;
            boolean z = false;
            while (true) {
                try {
                    InputStream inputStream = a2.get(uri2, b);
                    if (inputStream == null) {
                        break;
                    }
                    z = true;
                    inputStreamReader = new InputStreamReader(inputStream);
                    try {
                        char[] cArr = new char[128];
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            int read = inputStreamReader.read(cArr, 0, cArr.length);
                            if (read <= 0) {
                                break;
                            }
                            sb.append(cArr, 0, read);
                        }
                        Utility.closeQuietly(inputStreamReader);
                        inputStreamReader2 = inputStreamReader;
                        uri2 = sb.toString();
                    } catch (IOException | URISyntaxException unused) {
                        Utility.closeQuietly(inputStreamReader);
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        Utility.closeQuietly(inputStreamReader);
                        throw th;
                    }
                } catch (IOException | URISyntaxException unused2) {
                    inputStreamReader = inputStreamReader2;
                    Utility.closeQuietly(inputStreamReader);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    inputStreamReader = inputStreamReader2;
                    Utility.closeQuietly(inputStreamReader);
                    throw th;
                }
            }
            if (z) {
                URI uri3 = new URI(uri2);
                Utility.closeQuietly(inputStreamReader2);
                return uri3;
            }
            Utility.closeQuietly(inputStreamReader2);
            return null;
        } catch (IOException | URISyntaxException unused3) {
            inputStreamReader = null;
            Utility.closeQuietly(inputStreamReader);
            return null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamReader = null;
            Utility.closeQuietly(inputStreamReader);
            throw th;
        }
    }

    static void a(Context context, URI uri, URI uri2) {
        if (uri != null && uri2 != null) {
            OutputStream outputStream = null;
            try {
                OutputStream openPutStream = a(context).openPutStream(uri.toString(), b);
                try {
                    openPutStream.write(uri2.toString().getBytes());
                    Utility.closeQuietly(openPutStream);
                } catch (IOException unused) {
                    outputStream = openPutStream;
                    Utility.closeQuietly(outputStream);
                } catch (Throwable th) {
                    th = th;
                    outputStream = openPutStream;
                    Utility.closeQuietly(outputStream);
                    throw th;
                }
            } catch (IOException unused2) {
                Utility.closeQuietly(outputStream);
            } catch (Throwable th2) {
                th = th2;
                Utility.closeQuietly(outputStream);
                throw th;
            }
        }
    }

    static void b(Context context) {
        try {
            a(context).clearCache();
        } catch (IOException e) {
            LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
            String str = a;
            StringBuilder sb = new StringBuilder();
            sb.append("clearCache failed ");
            sb.append(e.getMessage());
            Logger.log(loggingBehavior, 5, str, sb.toString());
        }
    }
}
