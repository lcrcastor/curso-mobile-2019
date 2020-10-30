package com.crashlytics.android.core;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.settings.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CrashlyticsUncaughtExceptionHandler implements UncaughtExceptionHandler {
    static final FilenameFilter a = new FilenameFilter() {
        public boolean accept(File file, String str) {
            return str.length() == ".cls".length() + 35 && str.endsWith(".cls");
        }
    };
    static final Comparator<File> b = new Comparator<File>() {
        /* renamed from: a */
        public int compare(File file, File file2) {
            return file2.getName().compareTo(file.getName());
        }
    };
    static final Comparator<File> c = new Comparator<File>() {
        /* renamed from: a */
        public int compare(File file, File file2) {
            return file.getName().compareTo(file2.getName());
        }
    };
    static final FilenameFilter d = new FilenameFilter() {
        public boolean accept(File file, String str) {
            return CrashlyticsUncaughtExceptionHandler.e.matcher(str).matches();
        }
    };
    /* access modifiers changed from: private */
    public static final Pattern e = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
    /* access modifiers changed from: private */
    public static final Map<String, String> f = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
    private static final String[] g = {"SessionUser", "SessionApp", "SessionOS", "SessionDevice"};
    private final AtomicInteger h = new AtomicInteger(0);
    private final UncaughtExceptionHandler i;
    private final FileStore j;
    /* access modifiers changed from: private */
    public final AtomicBoolean k;
    private final CrashlyticsExecutorServiceWrapper l;
    private final IdManager m;
    private final StackTraceTrimmingStrategy n;
    /* access modifiers changed from: private */
    public final CrashlyticsCore o;
    /* access modifiers changed from: private */
    public final LogFileManager p;
    private final DevicePowerStateListener q;
    private final String r;
    private final File s;

    static class AnySessionPartFileFilter implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        public boolean accept(File file, String str) {
            return !CrashlyticsUncaughtExceptionHandler.a.accept(file, str) && CrashlyticsUncaughtExceptionHandler.e.matcher(str).matches();
        }
    }

    static class FileNameContainsFilter implements FilenameFilter {
        private final String a;

        public FileNameContainsFilter(String str) {
            this.a = str;
        }

        public boolean accept(File file, String str) {
            return str.contains(this.a) && !str.endsWith(".cls_temp");
        }
    }

    static final class SendSessionRunnable implements Runnable {
        private final CrashlyticsCore a;
        private final File b;

        public SendSessionRunnable(CrashlyticsCore crashlyticsCore, File file) {
            this.a = crashlyticsCore;
            this.b = file;
        }

        public void run() {
            if (CommonUtils.canTryConnection(this.a.getContext())) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Attempting to send crash report at time of crash...");
                CreateReportSpiCall a2 = this.a.a(Settings.getInstance().awaitSettingsData());
                if (a2 != null) {
                    new ReportUploader(this.a.c(), a2).a((Report) new SessionReport(this.b, CrashlyticsUncaughtExceptionHandler.f));
                }
            }
        }
    }

    static class SessionPartFileFilter implements FilenameFilter {
        private final String a;

        public SessionPartFileFilter(String str) {
            this.a = str;
        }

        public boolean accept(File file, String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(".cls");
            boolean z = false;
            if (str.equals(sb.toString())) {
                return false;
            }
            if (str.contains(this.a) && !str.endsWith(".cls_temp")) {
                z = true;
            }
            return z;
        }
    }

    CrashlyticsUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler, CrashlyticsExecutorServiceWrapper crashlyticsExecutorServiceWrapper, IdManager idManager, UnityVersionProvider unityVersionProvider, FileStore fileStore, CrashlyticsCore crashlyticsCore) {
        this.i = uncaughtExceptionHandler;
        this.l = crashlyticsExecutorServiceWrapper;
        this.m = idManager;
        this.o = crashlyticsCore;
        this.r = unityVersionProvider.a();
        this.j = fileStore;
        this.s = new File(r(), "invalidClsFiles");
        this.k = new AtomicBoolean(false);
        Context context = crashlyticsCore.getContext();
        this.p = new LogFileManager(context, fileStore);
        this.q = new DevicePowerStateListener(context);
        this.n = new MiddleOutFallbackStrategy(1024, new RemoveRepeatsStrategy(10));
    }

    public synchronized void uncaughtException(final Thread thread, final Throwable th) {
        AtomicBoolean atomicBoolean;
        this.k.set(true);
        try {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Crashlytics is handling uncaught exception \"");
            sb.append(th);
            sb.append("\" from thread ");
            sb.append(thread.getName());
            logger.d(str, sb.toString());
            this.q.b();
            final Date date = new Date();
            this.l.a((Callable<T>) new Callable<Void>() {
                /* renamed from: a */
                public Void call() {
                    CrashlyticsUncaughtExceptionHandler.this.a(date, thread, th);
                    return null;
                }
            });
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
            this.i.uncaughtException(thread, th);
            atomicBoolean = this.k;
        } catch (Exception e2) {
            try {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the uncaught exception handler", e2);
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
                this.i.uncaughtException(thread, th);
                atomicBoolean = this.k;
            } catch (Throwable th2) {
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
                this.i.uncaughtException(thread, th);
                this.k.set(false);
                throw th2;
            }
        }
        atomicBoolean.set(false);
        return;
    }

    /* access modifiers changed from: private */
    public void a(Date date, Thread thread, Throwable th) {
        this.o.u();
        b(date, thread, th);
        e();
        m();
        g();
        if (!this.o.r()) {
            q();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.k.get();
    }

    /* access modifiers changed from: 0000 */
    public File b() {
        return this.s;
    }

    /* access modifiers changed from: 0000 */
    public void a(final long j2, final String str) {
        this.l.b(new Callable<Void>() {
            /* renamed from: a */
            public Void call() {
                if (!CrashlyticsUncaughtExceptionHandler.this.k.get()) {
                    CrashlyticsUncaughtExceptionHandler.this.p.a(j2, str);
                }
                return null;
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void a(final Thread thread, final Throwable th) {
        final Date date = new Date();
        this.l.a((Runnable) new Runnable() {
            public void run() {
                if (!CrashlyticsUncaughtExceptionHandler.this.k.get()) {
                    CrashlyticsUncaughtExceptionHandler.this.c(date, thread, th);
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void a(final String str, final String str2, final String str3) {
        this.l.b(new Callable<Void>() {
            /* renamed from: a */
            public Void call() {
                new MetaDataStore(CrashlyticsUncaughtExceptionHandler.this.r()).a(CrashlyticsUncaughtExceptionHandler.this.k(), new UserMetaData(str, str2, str3));
                return null;
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void a(final Map<String, String> map) {
        this.l.b(new Callable<Void>() {
            /* renamed from: a */
            public Void call() {
                new MetaDataStore(CrashlyticsUncaughtExceptionHandler.this.r()).a(CrashlyticsUncaughtExceptionHandler.this.k(), map);
                return null;
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.l.b(new Callable<Void>() {
            /* renamed from: a */
            public Void call() {
                CrashlyticsUncaughtExceptionHandler.this.m();
                return null;
            }
        });
    }

    /* access modifiers changed from: private */
    public String k() {
        File[] o2 = o();
        if (o2.length > 0) {
            return a(o2[0]);
        }
        return null;
    }

    private String l() {
        File[] o2 = o();
        if (o2.length > 1) {
            return a(o2[1]);
        }
        return null;
    }

    static String a(File file) {
        return file.getName().substring(0, 35);
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return ((Boolean) this.l.a((Callable<T>) new Callable<Boolean>() {
            /* renamed from: a */
            public Boolean call() {
                if (CrashlyticsUncaughtExceptionHandler.this.k.get()) {
                    Fabric.getLogger().d(CrashlyticsCore.TAG, "Skipping session finalization because a crash has already occurred.");
                    return Boolean.FALSE;
                }
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Finalizing previously open sessions.");
                SessionEventData p = CrashlyticsUncaughtExceptionHandler.this.o.p();
                if (p != null) {
                    CrashlyticsUncaughtExceptionHandler.this.a(p);
                }
                CrashlyticsUncaughtExceptionHandler.this.a(true);
                Fabric.getLogger().d(CrashlyticsCore.TAG, "Closed all previously open sessions");
                return Boolean.TRUE;
            }
        })).booleanValue();
    }

    /* access modifiers changed from: private */
    public void m() {
        Date date = new Date();
        String clsuuid = new CLSUUID(this.m).toString();
        Logger logger = Fabric.getLogger();
        String str = CrashlyticsCore.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Opening an new session with ID ");
        sb.append(clsuuid);
        logger.d(str, sb.toString());
        a(clsuuid, date);
        c(clsuuid);
        d(clsuuid);
        e(clsuuid);
        this.p.a(clsuuid);
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        a(false);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Incorrect type for immutable var: ssa=boolean, code=int, for r3v0, types: [int, boolean] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r3) {
        /*
            r2 = this;
            int r0 = r3 + 8
            r2.a(r0)
            java.io.File[] r0 = r2.o()
            int r1 = r0.length
            if (r1 > r3) goto L_0x0018
            io.fabric.sdk.android.Logger r3 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r0 = "CrashlyticsCore"
            java.lang.String r1 = "No open sessions to be closed."
            r3.d(r0, r1)
            return
        L_0x0018:
            r1 = r0[r3]
            java.lang.String r1 = a(r1)
            r2.f(r1)
            com.crashlytics.android.core.CrashlyticsCore r1 = r2.o
            io.fabric.sdk.android.services.settings.SessionSettingsData r1 = com.crashlytics.android.core.CrashlyticsCore.v()
            if (r1 != 0) goto L_0x0035
            io.fabric.sdk.android.Logger r3 = io.fabric.sdk.android.Fabric.getLogger()
            java.lang.String r0 = "CrashlyticsCore"
            java.lang.String r1 = "Unable to close session. Settings are not loaded."
            r3.d(r0, r1)
            return
        L_0x0035:
            int r1 = r1.maxCustomExceptionEvents
            r2.a(r0, r3, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.a(boolean):void");
    }

    private void a(File[] fileArr, int i2, int i3) {
        Fabric.getLogger().d(CrashlyticsCore.TAG, "Closing open sessions.");
        while (i2 < fileArr.length) {
            File file = fileArr[i2];
            String a2 = a(file);
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Closing session: ");
            sb.append(a2);
            logger.d(str, sb.toString());
            a(file, a2, i3);
            i2++;
        }
    }

    private void a(ClsFileOutputStream clsFileOutputStream) {
        if (clsFileOutputStream != null) {
            try {
                clsFileOutputStream.a();
            } catch (IOException e2) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Error closing session file stream in the presence of an exception", e2);
            }
        }
    }

    private void a(String str) {
        for (File delete : b(str)) {
            delete.delete();
        }
    }

    private File[] b(String str) {
        return a((FilenameFilter) new SessionPartFileFilter(str));
    }

    private File[] n() {
        return a(a);
    }

    /* access modifiers changed from: 0000 */
    public File[] f() {
        return a((FilenameFilter) new FileNameContainsFilter("BeginSession"));
    }

    private File[] o() {
        File[] f2 = f();
        Arrays.sort(f2, b);
        return f2;
    }

    /* access modifiers changed from: private */
    public File[] a(FilenameFilter filenameFilter) {
        return a(r(), filenameFilter);
    }

    private File[] a(File file, FilenameFilter filenameFilter) {
        return b(file.listFiles(filenameFilter));
    }

    private File[] b(File file) {
        return b(file.listFiles());
    }

    private File[] b(File[] fileArr) {
        return fileArr == null ? new File[0] : fileArr;
    }

    private void a(String str, int i2) {
        File r2 = r();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("SessionEvent");
        Utils.a(r2, new FileNameContainsFilter(sb.toString()), i2, c);
    }

    /* access modifiers changed from: 0000 */
    public void g() {
        Utils.a(r(), a, 4, c);
    }

    private void a(int i2) {
        HashSet hashSet = new HashSet();
        File[] o2 = o();
        int min = Math.min(i2, o2.length);
        for (int i3 = 0; i3 < min; i3++) {
            hashSet.add(a(o2[i3]));
        }
        this.p.a((Set<String>) hashSet);
        a(a((FilenameFilter) new AnySessionPartFileFilter()), (Set<String>) hashSet);
    }

    private void a(File[] fileArr, Set<String> set) {
        for (File file : fileArr) {
            String name = file.getName();
            Matcher matcher = e.matcher(name);
            if (!matcher.matches()) {
                Logger logger = Fabric.getLogger();
                String str = CrashlyticsCore.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Deleting unknown file: ");
                sb.append(name);
                logger.d(str, sb.toString());
                file.delete();
                return;
            }
            if (!set.contains(matcher.group(1))) {
                Logger logger2 = Fabric.getLogger();
                String str2 = CrashlyticsCore.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Trimming session file: ");
                sb2.append(name);
                logger2.d(str2, sb2.toString());
                file.delete();
            }
        }
    }

    private File[] a(String str, File[] fileArr, int i2) {
        if (fileArr.length <= i2) {
            return fileArr;
        }
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Trimming down to %d logged exceptions.", new Object[]{Integer.valueOf(i2)}));
        a(str, i2);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("SessionEvent");
        return a((FilenameFilter) new FileNameContainsFilter(sb.toString()));
    }

    /* access modifiers changed from: 0000 */
    public void h() {
        this.l.a((Runnable) new Runnable() {
            public void run() {
                CrashlyticsUncaughtExceptionHandler.this.a(CrashlyticsUncaughtExceptionHandler.this.a(ClsFileOutputStream.a));
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void a(File[] fileArr) {
        File[] a2;
        final HashSet hashSet = new HashSet();
        for (File file : fileArr) {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Found invalid session part file: ");
            sb.append(file);
            logger.d(str, sb.toString());
            hashSet.add(a(file));
        }
        if (!hashSet.isEmpty()) {
            if (!this.s.exists()) {
                this.s.mkdir();
            }
            for (File file2 : a((FilenameFilter) new FilenameFilter() {
                public boolean accept(File file, String str) {
                    if (str.length() < 35) {
                        return false;
                    }
                    return hashSet.contains(str.substring(0, 35));
                }
            })) {
                Logger logger2 = Fabric.getLogger();
                String str2 = CrashlyticsCore.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Moving session file: ");
                sb2.append(file2);
                logger2.d(str2, sb2.toString());
                if (!file2.renameTo(new File(this.s, file2.getName()))) {
                    Logger logger3 = Fabric.getLogger();
                    String str3 = CrashlyticsCore.TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Could not move session file. Deleting ");
                    sb3.append(file2);
                    logger3.d(str3, sb3.toString());
                    file2.delete();
                }
            }
            p();
        }
    }

    private void p() {
        if (this.s.exists()) {
            File[] a2 = a(this.s, ClsFileOutputStream.a);
            Arrays.sort(a2, Collections.reverseOrder());
            HashSet hashSet = new HashSet();
            for (int i2 = 0; i2 < a2.length && hashSet.size() < 4; i2++) {
                hashSet.add(a(a2[i2]));
            }
            a(b(this.s), (Set<String>) hashSet);
        }
    }

    private void b(Date date, Thread thread, Throwable th) {
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream codedOutputStream = null;
        try {
            String k2 = k();
            if (k2 == null) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a fatal exception while no session was open.", null);
                CommonUtils.flushOrLog(null, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
                return;
            }
            CrashlyticsCore.b(k2, th.getClass().getName());
            File r2 = r();
            StringBuilder sb = new StringBuilder();
            sb.append(k2);
            sb.append("SessionCrash");
            clsFileOutputStream = new ClsFileOutputStream(r2, sb.toString());
            try {
                CodedOutputStream a2 = CodedOutputStream.a((OutputStream) clsFileOutputStream);
                try {
                    a(a2, date, thread, th, "crash", true);
                    CommonUtils.flushOrLog(a2, "Failed to flush to session begin file.");
                } catch (Exception e2) {
                    e = e2;
                    codedOutputStream = a2;
                    try {
                        Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e);
                        CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    codedOutputStream = a2;
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e);
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            }
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
        } catch (Exception e4) {
            e = e4;
            clsFileOutputStream = null;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the fatal exception logger", e);
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void a(SessionEventData sessionEventData) {
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream codedOutputStream = null;
        try {
            String l2 = l();
            if (l2 == null) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a native crash while no session was open.", null);
                CommonUtils.flushOrLog(null, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
                return;
            }
            CrashlyticsCore.b(l2, String.format(Locale.US, "<native-crash [%s (%s)]>", new Object[]{sessionEventData.signal.code, sessionEventData.signal.name}));
            File r2 = r();
            StringBuilder sb = new StringBuilder();
            sb.append(l2);
            sb.append("SessionCrash");
            clsFileOutputStream = new ClsFileOutputStream(r2, sb.toString());
            try {
                CodedOutputStream a2 = CodedOutputStream.a((OutputStream) clsFileOutputStream);
                try {
                    NativeCrashWriter.a(sessionEventData, new LogFileManager(this.o.getContext(), this.j, l2), new MetaDataStore(r()).b(l2), a2);
                    CommonUtils.flushOrLog(a2, "Failed to flush to session begin file.");
                } catch (Exception e2) {
                    e = e2;
                    codedOutputStream = a2;
                    try {
                        Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the native crash logger", e);
                        CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                    } catch (Throwable th) {
                        th = th;
                        CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    codedOutputStream = a2;
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the native crash logger", e);
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            }
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
        } catch (Exception e4) {
            e = e4;
            clsFileOutputStream = null;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the native crash logger", e);
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
        } catch (Throwable th3) {
            th = th3;
            clsFileOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close fatal exception file output stream.");
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void c(Date date, Thread thread, Throwable th) {
        ClsFileOutputStream clsFileOutputStream;
        String k2 = k();
        CodedOutputStream codedOutputStream = null;
        if (k2 == null) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Tried to write a non-fatal exception while no session was open.", null);
            return;
        }
        CrashlyticsCore.a(k2, th.getClass().getName());
        try {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Crashlytics is logging non-fatal exception \"");
            sb.append(th);
            sb.append("\" from thread ");
            sb.append(thread.getName());
            logger.d(str, sb.toString());
            String padWithZerosToMaxIntWidth = CommonUtils.padWithZerosToMaxIntWidth(this.h.getAndIncrement());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(k2);
            sb2.append("SessionEvent");
            sb2.append(padWithZerosToMaxIntWidth);
            clsFileOutputStream = new ClsFileOutputStream(r(), sb2.toString());
            try {
                CodedOutputStream a2 = CodedOutputStream.a((OutputStream) clsFileOutputStream);
                try {
                    a(a2, date, thread, th, "error", false);
                    CommonUtils.flushOrLog(a2, "Failed to flush to non-fatal file.");
                } catch (Exception e2) {
                    e = e2;
                    codedOutputStream = a2;
                    try {
                        Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e);
                        CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
                        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
                        a(k2, 64);
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
                        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    codedOutputStream = a2;
                    CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e);
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
                a(k2, 64);
            }
        } catch (Exception e4) {
            e = e4;
            clsFileOutputStream = null;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the non-fatal exception logger", e);
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
            a(k2, 64);
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to non-fatal file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
            throw th;
        }
        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close non-fatal file output stream.");
        try {
            a(k2, 64);
        } catch (Exception e5) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred when trimming non-fatal files.", e5);
        }
    }

    private void a(String str, Date date) {
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream a2;
        CodedOutputStream codedOutputStream = null;
        try {
            File r2 = r();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("BeginSession");
            clsFileOutputStream = new ClsFileOutputStream(r2, sb.toString());
            try {
                a2 = CodedOutputStream.a((OutputStream) clsFileOutputStream);
            } catch (Throwable th) {
                th = th;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close begin session file.");
                throw th;
            }
            try {
                SessionProtobufHelper.a(a2, str, String.format(Locale.US, "Crashlytics Android SDK/%s", new Object[]{this.o.getVersion()}), date.getTime() / 1000);
                CommonUtils.flushOrLog(a2, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close begin session file.");
            } catch (Throwable th2) {
                th = th2;
                codedOutputStream = a2;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close begin session file.");
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            clsFileOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close begin session file.");
            throw th;
        }
    }

    private void c(String str) {
        CodedOutputStream codedOutputStream;
        ClsFileOutputStream clsFileOutputStream;
        Throwable th;
        try {
            File r2 = r();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("SessionApp");
            clsFileOutputStream = new ClsFileOutputStream(r2, sb.toString());
            try {
                codedOutputStream = CodedOutputStream.a((OutputStream) clsFileOutputStream);
            } catch (Throwable th2) {
                th = th2;
                codedOutputStream = null;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session app file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session app file.");
                throw th;
            }
            try {
                SessionProtobufHelper.a(codedOutputStream, this.m.getAppIdentifier(), this.o.c(), this.o.f(), this.o.e(), this.m.getAppInstallIdentifier(), DeliveryMechanism.determineFrom(this.o.d()).getId(), this.r);
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session app file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session app file.");
            } catch (Throwable th3) {
                th = th3;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session app file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session app file.");
                throw th;
            }
        } catch (Throwable th4) {
            clsFileOutputStream = null;
            th = th4;
            codedOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session app file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session app file.");
            throw th;
        }
    }

    private void d(String str) {
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream a2;
        CodedOutputStream codedOutputStream = null;
        try {
            File r2 = r();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("SessionOS");
            clsFileOutputStream = new ClsFileOutputStream(r2, sb.toString());
            try {
                a2 = CodedOutputStream.a((OutputStream) clsFileOutputStream);
            } catch (Throwable th) {
                th = th;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session OS file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session OS file.");
                throw th;
            }
            try {
                SessionProtobufHelper.a(a2, CommonUtils.isRooted(this.o.getContext()));
                CommonUtils.flushOrLog(a2, "Failed to flush to session OS file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session OS file.");
            } catch (Throwable th2) {
                Throwable th3 = th2;
                codedOutputStream = a2;
                th = th3;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session OS file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session OS file.");
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session OS file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session OS file.");
            throw th;
        }
    }

    private void e(String str) {
        CodedOutputStream codedOutputStream;
        ClsFileOutputStream clsFileOutputStream;
        try {
            File r2 = r();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("SessionDevice");
            clsFileOutputStream = new ClsFileOutputStream(r2, sb.toString());
            try {
                codedOutputStream = CodedOutputStream.a((OutputStream) clsFileOutputStream);
            } catch (Throwable th) {
                th = th;
                codedOutputStream = null;
                Throwable th2 = th;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session device info.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session device file.");
                throw th2;
            }
            try {
                Context context = this.o.getContext();
                StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
                CodedOutputStream codedOutputStream2 = codedOutputStream;
                SessionProtobufHelper.a(codedOutputStream2, this.m.getDeviceUUID(), CommonUtils.getCpuArchitectureInt(), Build.MODEL, Runtime.getRuntime().availableProcessors(), CommonUtils.getTotalRamInBytes(), ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()), CommonUtils.isEmulator(context), this.m.getDeviceIdentifiers(), CommonUtils.getDeviceState(context), Build.MANUFACTURER, Build.PRODUCT);
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session device info.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session device file.");
            } catch (Throwable th3) {
                th = th3;
                Throwable th22 = th;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session device info.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session device file.");
                throw th22;
            }
        } catch (Throwable th4) {
            th = th4;
            clsFileOutputStream = null;
            codedOutputStream = null;
            Throwable th222 = th;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session device info.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session device file.");
            throw th222;
        }
    }

    private void f(String str) {
        ClsFileOutputStream clsFileOutputStream;
        CodedOutputStream a2;
        CodedOutputStream codedOutputStream = null;
        try {
            File r2 = r();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("SessionUser");
            clsFileOutputStream = new ClsFileOutputStream(r2, sb.toString());
            try {
                a2 = CodedOutputStream.a((OutputStream) clsFileOutputStream);
            } catch (Throwable th) {
                th = th;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session user file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session user file.");
                throw th;
            }
            try {
                UserMetaData g2 = g(str);
                if (g2.isEmpty()) {
                    CommonUtils.flushOrLog(a2, "Failed to flush session user file.");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session user file.");
                    return;
                }
                SessionProtobufHelper.a(a2, g2.f270id, g2.name, g2.email);
                CommonUtils.flushOrLog(a2, "Failed to flush session user file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session user file.");
            } catch (Throwable th2) {
                th = th2;
                codedOutputStream = a2;
                CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session user file.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session user file.");
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            clsFileOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session user file.");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session user file.");
            throw th;
        }
    }

    /* JADX WARNING: type inference failed for: r6v2, types: [boolean] */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v2, types: [boolean]
      assigns: []
      uses: [boolean, ?[int, byte, short, char]]
      mth insns count: 75
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.crashlytics.android.core.CodedOutputStream r26, java.util.Date r27, java.lang.Thread r28, java.lang.Throwable r29, java.lang.String r30, boolean r31) {
        /*
            r25 = this;
            r0 = r25
            com.crashlytics.android.core.TrimmedThrowableData r5 = new com.crashlytics.android.core.TrimmedThrowableData
            com.crashlytics.android.core.StackTraceTrimmingStrategy r1 = r0.n
            r2 = r29
            r5.<init>(r2, r1)
            com.crashlytics.android.core.CrashlyticsCore r1 = r0.o
            android.content.Context r1 = r1.getContext()
            long r2 = r27.getTime()
            r6 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 / r6
            java.lang.Float r16 = io.fabric.sdk.android.services.common.CommonUtils.getBatteryLevel(r1)
            com.crashlytics.android.core.DevicePowerStateListener r4 = r0.q
            boolean r4 = r4.a()
            int r17 = io.fabric.sdk.android.services.common.CommonUtils.getBatteryVelocity(r1, r4)
            boolean r18 = io.fabric.sdk.android.services.common.CommonUtils.getProximitySensorEnabled(r1)
            android.content.res.Resources r4 = r1.getResources()
            android.content.res.Configuration r4 = r4.getConfiguration()
            int r13 = r4.orientation
            long r6 = io.fabric.sdk.android.services.common.CommonUtils.getTotalRamInBytes()
            long r8 = io.fabric.sdk.android.services.common.CommonUtils.calculateFreeRamInBytes(r1)
            long r19 = r6 - r8
            java.io.File r4 = android.os.Environment.getDataDirectory()
            java.lang.String r4 = r4.getPath()
            long r21 = io.fabric.sdk.android.services.common.CommonUtils.calculateUsedDiskSpaceInBytes(r4)
            java.lang.String r4 = r1.getPackageName()
            android.app.ActivityManager$RunningAppProcessInfo r12 = io.fabric.sdk.android.services.common.CommonUtils.getAppProcessInfo(r4, r1)
            java.util.LinkedList r9 = new java.util.LinkedList
            r9.<init>()
            java.lang.StackTraceElement[] r7 = r5.c
            com.crashlytics.android.core.CrashlyticsCore r4 = r0.o
            java.lang.String r15 = r4.h()
            io.fabric.sdk.android.services.common.IdManager r4 = r0.m
            java.lang.String r14 = r4.getAppIdentifier()
            r4 = 0
            if (r31 == 0) goto L_0x00a3
            java.util.Map r8 = java.lang.Thread.getAllStackTraces()
            int r10 = r8.size()
            java.lang.Thread[] r10 = new java.lang.Thread[r10]
            java.util.Set r8 = r8.entrySet()
            java.util.Iterator r8 = r8.iterator()
        L_0x007a:
            boolean r11 = r8.hasNext()
            if (r11 == 0) goto L_0x00a0
            java.lang.Object r11 = r8.next()
            java.util.Map$Entry r11 = (java.util.Map.Entry) r11
            java.lang.Object r23 = r11.getKey()
            java.lang.Thread r23 = (java.lang.Thread) r23
            r10[r4] = r23
            com.crashlytics.android.core.StackTraceTrimmingStrategy r6 = r0.n
            java.lang.Object r11 = r11.getValue()
            java.lang.StackTraceElement[] r11 = (java.lang.StackTraceElement[]) r11
            java.lang.StackTraceElement[] r6 = r6.a(r11)
            r9.add(r6)
            r6 = 1
            int r4 = r4 + r6
            goto L_0x007a
        L_0x00a0:
            r6 = 1
            r8 = r10
            goto L_0x00a7
        L_0x00a3:
            r6 = 1
            java.lang.Thread[] r4 = new java.lang.Thread[r4]
            r8 = r4
        L_0x00a7:
            java.lang.String r4 = "com.crashlytics.CollectCustomKeys"
            boolean r1 = io.fabric.sdk.android.services.common.CommonUtils.getBooleanResourceValue(r1, r4, r6)
            if (r1 != 0) goto L_0x00b6
            java.util.TreeMap r1 = new java.util.TreeMap
            r1.<init>()
        L_0x00b4:
            r10 = r1
            goto L_0x00ca
        L_0x00b6:
            com.crashlytics.android.core.CrashlyticsCore r1 = r0.o
            java.util.Map r1 = r1.a()
            if (r1 == 0) goto L_0x00b4
            int r4 = r1.size()
            if (r4 <= r6) goto L_0x00b4
            java.util.TreeMap r4 = new java.util.TreeMap
            r4.<init>(r1)
            r10 = r4
        L_0x00ca:
            com.crashlytics.android.core.LogFileManager r11 = r0.p
            r1 = r26
            r4 = r30
            r6 = r28
            com.crashlytics.android.core.SessionProtobufHelper.a(r1, r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r21)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.a(com.crashlytics.android.core.CodedOutputStream, java.util.Date, java.lang.Thread, java.lang.Throwable, java.lang.String, boolean):void");
    }

    private void a(File file, String str, int i2) {
        Logger logger = Fabric.getLogger();
        String str2 = CrashlyticsCore.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Collecting session parts for ID ");
        sb.append(str);
        logger.d(str2, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("SessionCrash");
        File[] a2 = a((FilenameFilter) new FileNameContainsFilter(sb2.toString()));
        boolean z = a2 != null && a2.length > 0;
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Session %s has fatal exception: %s", new Object[]{str, Boolean.valueOf(z)}));
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append("SessionEvent");
        File[] a3 = a((FilenameFilter) new FileNameContainsFilter(sb3.toString()));
        boolean z2 = a3 != null && a3.length > 0;
        Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Session %s has non-fatal exceptions: %s", new Object[]{str, Boolean.valueOf(z2)}));
        if (z || z2) {
            a(file, str, a(str, a3, i2), z ? a2[0] : null);
        } else {
            Logger logger2 = Fabric.getLogger();
            String str3 = CrashlyticsCore.TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("No events present for session ID ");
            sb4.append(str);
            logger2.d(str3, sb4.toString());
        }
        Logger logger3 = Fabric.getLogger();
        String str4 = CrashlyticsCore.TAG;
        StringBuilder sb5 = new StringBuilder();
        sb5.append("Removing session part files for ID ");
        sb5.append(str);
        logger3.d(str4, sb5.toString());
        a(str);
    }

    private void a(File file, String str, File[] fileArr, File file2) {
        CodedOutputStream codedOutputStream;
        ClsFileOutputStream clsFileOutputStream;
        boolean z = file2 != null;
        CodedOutputStream codedOutputStream2 = null;
        try {
            clsFileOutputStream = new ClsFileOutputStream(r(), str);
            try {
                codedOutputStream = CodedOutputStream.a((OutputStream) clsFileOutputStream);
                try {
                    Logger logger = Fabric.getLogger();
                    String str2 = CrashlyticsCore.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Collecting SessionStart data for session ID ");
                    sb.append(str);
                    logger.d(str2, sb.toString());
                    a(codedOutputStream, file);
                    codedOutputStream.a(4, new Date().getTime() / 1000);
                    codedOutputStream.a(5, z);
                    codedOutputStream.a(11, 1);
                    codedOutputStream.b(12, 3);
                    a(codedOutputStream, str);
                    a(codedOutputStream, fileArr, str);
                    if (z) {
                        a(codedOutputStream, file2);
                    }
                    CommonUtils.flushOrLog(codedOutputStream, "Error flushing session file stream");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
                } catch (Exception e2) {
                    e = e2;
                    codedOutputStream2 = codedOutputStream;
                    try {
                        Logger logger2 = Fabric.getLogger();
                        String str3 = CrashlyticsCore.TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Failed to write session file for session ID: ");
                        sb2.append(str);
                        logger2.e(str3, sb2.toString(), e);
                        CommonUtils.flushOrLog(codedOutputStream2, "Error flushing session file stream");
                        a(clsFileOutputStream);
                    } catch (Throwable th) {
                        th = th;
                        codedOutputStream = codedOutputStream2;
                        CommonUtils.flushOrLog(codedOutputStream, "Error flushing session file stream");
                        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(codedOutputStream, "Error flushing session file stream");
                    CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Logger logger22 = Fabric.getLogger();
                String str32 = CrashlyticsCore.TAG;
                StringBuilder sb22 = new StringBuilder();
                sb22.append("Failed to write session file for session ID: ");
                sb22.append(str);
                logger22.e(str32, sb22.toString(), e);
                CommonUtils.flushOrLog(codedOutputStream2, "Error flushing session file stream");
                a(clsFileOutputStream);
            }
        } catch (Exception e4) {
            e = e4;
            clsFileOutputStream = null;
            Logger logger222 = Fabric.getLogger();
            String str322 = CrashlyticsCore.TAG;
            StringBuilder sb222 = new StringBuilder();
            sb222.append("Failed to write session file for session ID: ");
            sb222.append(str);
            logger222.e(str322, sb222.toString(), e);
            CommonUtils.flushOrLog(codedOutputStream2, "Error flushing session file stream");
            a(clsFileOutputStream);
        } catch (Throwable th3) {
            th = th3;
            clsFileOutputStream = null;
            codedOutputStream = null;
            CommonUtils.flushOrLog(codedOutputStream, "Error flushing session file stream");
            CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close CLS file");
            throw th;
        }
    }

    private static void a(CodedOutputStream codedOutputStream, File[] fileArr, String str) {
        Arrays.sort(fileArr, CommonUtils.FILE_MODIFIED_COMPARATOR);
        for (File file : fileArr) {
            try {
                Fabric.getLogger().d(CrashlyticsCore.TAG, String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", new Object[]{str, file.getName()}));
                a(codedOutputStream, file);
            } catch (Exception e2) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "Error writting non-fatal to session.", e2);
            }
        }
    }

    private void a(CodedOutputStream codedOutputStream, String str) {
        String[] strArr;
        for (String str2 : g) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str2);
            File[] a2 = a((FilenameFilter) new FileNameContainsFilter(sb.toString()));
            if (a2.length == 0) {
                Logger logger = Fabric.getLogger();
                String str3 = CrashlyticsCore.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Can't find ");
                sb2.append(str2);
                sb2.append(" data for session ID ");
                sb2.append(str);
                logger.e(str3, sb2.toString(), null);
            } else {
                Logger logger2 = Fabric.getLogger();
                String str4 = CrashlyticsCore.TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Collecting ");
                sb3.append(str2);
                sb3.append(" data for session ID ");
                sb3.append(str);
                logger2.d(str4, sb3.toString());
                a(codedOutputStream, a2[0]);
            }
        }
    }

    private static void a(CodedOutputStream codedOutputStream, File file) {
        FileInputStream fileInputStream;
        if (!file.exists()) {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Tried to include a file that doesn't exist: ");
            sb.append(file.getName());
            logger.e(str, sb.toString(), null);
            return;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                a((InputStream) fileInputStream, codedOutputStream, (int) file.length());
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream.");
            } catch (Throwable th) {
                th = th;
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream.");
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream.");
            throw th;
        }
    }

    private static void a(InputStream inputStream, CodedOutputStream codedOutputStream, int i2) {
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < bArr.length) {
            int read = inputStream.read(bArr, i3, bArr.length - i3);
            if (read < 0) {
                break;
            }
            i3 += read;
        }
        codedOutputStream.a(bArr);
    }

    private UserMetaData g(String str) {
        return a() ? new UserMetaData(this.o.j(), this.o.l(), this.o.k()) : new MetaDataStore(r()).a(str);
    }

    private void q() {
        for (File sendSessionRunnable : n()) {
            this.l.a((Runnable) new SendSessionRunnable(this.o, sendSessionRunnable));
        }
    }

    /* access modifiers changed from: private */
    public File r() {
        return this.j.getFilesDir();
    }
}
