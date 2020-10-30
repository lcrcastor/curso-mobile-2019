package com.crashlytics.android.core;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ReportUploader {
    static final Map<String, String> a = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");
    private static final FilenameFilter b = new FilenameFilter() {
        public boolean accept(File file, String str) {
            return str.endsWith(".cls") && !str.contains("Session");
        }
    };
    /* access modifiers changed from: private */
    public static final short[] c = {10, 20, 30, 60, 120, 300};
    private final Object d = new Object();
    private final CreateReportSpiCall e;
    private final String f;
    /* access modifiers changed from: private */
    public Thread g;

    class Worker extends BackgroundPriorityRunnable {
        private final float b;

        Worker(float f) {
            this.b = f;
        }

        public void onRun() {
            try {
                a();
            } catch (Exception e) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "An unexpected error occurred while attempting to upload crash reports.", e);
            }
            ReportUploader.this.g = null;
        }

        private void a() {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Starting report processing in ");
            sb.append(this.b);
            sb.append(" second(s)...");
            logger.d(str, sb.toString());
            if (this.b > BitmapDescriptorFactory.HUE_RED) {
                try {
                    Thread.sleep((long) (this.b * 1000.0f));
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            CrashlyticsCore instance = CrashlyticsCore.getInstance();
            CrashlyticsUncaughtExceptionHandler i = instance.i();
            List<Report> a2 = ReportUploader.this.a();
            if (!i.a()) {
                if (a2.isEmpty() || instance.t()) {
                    int i2 = 0;
                    while (!a2.isEmpty() && !CrashlyticsCore.getInstance().i().a()) {
                        Logger logger2 = Fabric.getLogger();
                        String str2 = CrashlyticsCore.TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Attempting to send ");
                        sb2.append(a2.size());
                        sb2.append(" report(s)");
                        logger2.d(str2, sb2.toString());
                        for (Report a3 : a2) {
                            ReportUploader.this.a(a3);
                        }
                        a2 = ReportUploader.this.a();
                        if (!a2.isEmpty()) {
                            int i3 = i2 + 1;
                            long j = (long) ReportUploader.c[Math.min(i2, ReportUploader.c.length - 1)];
                            Logger logger3 = Fabric.getLogger();
                            String str3 = CrashlyticsCore.TAG;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Report submisson: scheduling delayed retry in ");
                            sb3.append(j);
                            sb3.append(" seconds");
                            logger3.d(str3, sb3.toString());
                            try {
                                Thread.sleep(j * 1000);
                                i2 = i3;
                            } catch (InterruptedException unused2) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }
                    return;
                }
                Logger logger4 = Fabric.getLogger();
                String str4 = CrashlyticsCore.TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("User declined to send. Removing ");
                sb4.append(a2.size());
                sb4.append(" Report(s).");
                logger4.d(str4, sb4.toString());
                for (Report f : a2) {
                    f.f();
                }
            }
        }
    }

    public ReportUploader(String str, CreateReportSpiCall createReportSpiCall) {
        if (createReportSpiCall == null) {
            throw new IllegalArgumentException("createReportCall must not be null.");
        }
        this.e = createReportSpiCall;
        this.f = str;
    }

    public synchronized void a(float f2) {
        if (this.g == null) {
            this.g = new Thread(new Worker(f2), "Crashlytics Report Uploader");
            this.g.start();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Report report) {
        boolean z;
        synchronized (this.d) {
            z = false;
            try {
                boolean a2 = this.e.a(new CreateReportRequest(this.f, report));
                Logger logger = Fabric.getLogger();
                String str = CrashlyticsCore.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Crashlytics report upload ");
                sb.append(a2 ? "complete: " : "FAILED: ");
                sb.append(report.b());
                logger.i(str, sb.toString());
                if (a2) {
                    report.f();
                    z = true;
                }
            } catch (Exception e2) {
                Logger logger2 = Fabric.getLogger();
                String str2 = CrashlyticsCore.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Error occurred sending report ");
                sb2.append(report);
                logger2.e(str2, sb2.toString(), e2);
            }
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public List<Report> a() {
        File[] listFiles;
        File[] listFiles2;
        Fabric.getLogger().d(CrashlyticsCore.TAG, "Checking for crash reports...");
        CrashlyticsCore instance = CrashlyticsCore.getInstance();
        CrashlyticsUncaughtExceptionHandler i = instance.i();
        synchronized (this.d) {
            listFiles = instance.q().listFiles(b);
            listFiles2 = i.b().listFiles();
        }
        LinkedList linkedList = new LinkedList();
        for (File file : listFiles) {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Found crash report ");
            sb.append(file.getPath());
            logger.d(str, sb.toString());
            linkedList.add(new SessionReport(file));
        }
        HashMap hashMap = new HashMap();
        if (listFiles2 != null) {
            for (File file2 : listFiles2) {
                String a2 = CrashlyticsUncaughtExceptionHandler.a(file2);
                if (!hashMap.containsKey(a2)) {
                    hashMap.put(a2, new LinkedList());
                }
                ((List) hashMap.get(a2)).add(file2);
            }
        }
        for (String str2 : hashMap.keySet()) {
            Logger logger2 = Fabric.getLogger();
            String str3 = CrashlyticsCore.TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Found invalid session: ");
            sb2.append(str2);
            logger2.d(str3, sb2.toString());
            List list = (List) hashMap.get(str2);
            linkedList.add(new InvalidSessionReport(str2, (File[]) list.toArray(new File[list.size()])));
        }
        if (linkedList.isEmpty()) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "No reports found.");
        }
        return linkedList;
    }
}
