package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class LoggerPrinter implements Printer {
    private final ThreadLocal<String> a = new ThreadLocal<>();
    private final List<LogAdapter> b = new ArrayList();

    LoggerPrinter() {
    }

    public Printer t(String str) {
        if (str != null) {
            this.a.set(str);
        }
        return this;
    }

    public void d(@NonNull String str, @Nullable Object... objArr) {
        a(3, null, str, objArr);
    }

    public void d(@Nullable Object obj) {
        a(3, null, Utils.a(obj), new Object[0]);
    }

    public void e(@NonNull String str, @Nullable Object... objArr) {
        e(null, str, objArr);
    }

    public void e(@Nullable Throwable th, @NonNull String str, @Nullable Object... objArr) {
        a(6, th, str, objArr);
    }

    public void w(@NonNull String str, @Nullable Object... objArr) {
        a(5, null, str, objArr);
    }

    public void i(@NonNull String str, @Nullable Object... objArr) {
        a(4, null, str, objArr);
    }

    public void v(@NonNull String str, @Nullable Object... objArr) {
        a(2, null, str, objArr);
    }

    public void wtf(@NonNull String str, @Nullable Object... objArr) {
        a(7, null, str, objArr);
    }

    public void json(@Nullable String str) {
        if (Utils.a((CharSequence) str)) {
            d("Empty/Null json content");
            return;
        }
        try {
            String trim = str.trim();
            if (trim.startsWith("{")) {
                d(new JSONObject(trim).toString(2));
            } else if (trim.startsWith("[")) {
                d(new JSONArray(trim).toString(2));
            } else {
                e("Invalid Json", new Object[0]);
            }
        } catch (JSONException unused) {
            e("Invalid Json", new Object[0]);
        }
    }

    public void xml(@Nullable String str) {
        if (Utils.a((CharSequence) str)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            StreamSource streamSource = new StreamSource(new StringReader(str));
            StreamResult streamResult = new StreamResult(new StringWriter());
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            newTransformer.setOutputProperty("indent", "yes");
            newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            newTransformer.transform(streamSource, streamResult);
            d(streamResult.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException unused) {
            e("Invalid xml", new Object[0]);
        }
    }

    public synchronized void log(int i, @Nullable String str, @Nullable String str2, @Nullable Throwable th) {
        if (!(th == null || str2 == null)) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(" : ");
                sb.append(Utils.a(th));
                str2 = sb.toString();
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (th != null && str2 == null) {
            str2 = Utils.a(th);
        }
        if (Utils.a((CharSequence) str2)) {
            str2 = "Empty/NULL log message";
        }
        for (LogAdapter logAdapter : this.b) {
            if (logAdapter.isLoggable(i, str)) {
                logAdapter.log(i, str, str2);
            }
        }
    }

    public void clearLogAdapters() {
        this.b.clear();
    }

    public void addAdapter(@NonNull LogAdapter logAdapter) {
        this.b.add(Utils.b(logAdapter));
    }

    private synchronized void a(int i, @Nullable Throwable th, @NonNull String str, @Nullable Object... objArr) {
        Utils.b(str);
        log(i, a(), a(str, objArr), th);
    }

    @Nullable
    private String a() {
        String str = (String) this.a.get();
        if (str == null) {
            return null;
        }
        this.a.remove();
        return str;
    }

    @NonNull
    private String a(@NonNull String str, @Nullable Object... objArr) {
        return (objArr == null || objArr.length == 0) ? str : String.format(str, objArr);
    }
}
