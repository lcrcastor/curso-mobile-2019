package com.crashlytics.android.core;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import io.fabric.sdk.android.services.common.QueueFile.ElementReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

class QueueFileLogStore implements FileLogStore {
    private final File a;
    private final int b;
    private QueueFile c;

    public QueueFileLogStore(File file, int i) {
        this.a = file;
        this.b = i;
    }

    public void a(long j, String str) {
        d();
        b(j, str);
    }

    public ByteString a() {
        if (!this.a.exists()) {
            return null;
        }
        d();
        if (this.c == null) {
            return null;
        }
        final int[] iArr = {0};
        final byte[] bArr = new byte[this.c.usedBytes()];
        try {
            this.c.forEach(new ElementReader() {
                public void read(InputStream inputStream, int i) {
                    try {
                        inputStream.read(bArr, iArr[0], i);
                        int[] iArr = iArr;
                        iArr[0] = iArr[0] + i;
                    } finally {
                        inputStream.close();
                    }
                }
            });
        } catch (IOException e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "A problem occurred while reading the Crashlytics log file.", e);
        }
        return ByteString.a(bArr, 0, iArr[0]);
    }

    public void b() {
        CommonUtils.closeOrLog(this.c, "There was a problem closing the Crashlytics log file.");
        this.c = null;
    }

    public void c() {
        b();
        this.a.delete();
    }

    private void d() {
        if (this.c == null) {
            try {
                this.c = new QueueFile(this.a);
            } catch (IOException e) {
                Logger logger = Fabric.getLogger();
                String str = CrashlyticsCore.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Could not open log file: ");
                sb.append(this.a);
                logger.e(str, sb.toString(), e);
            }
        }
    }

    private void b(long j, String str) {
        if (this.c != null) {
            if (str == null) {
                str = "null";
            }
            try {
                int i = this.b / 4;
                if (str.length() > i) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("...");
                    sb.append(str.substring(str.length() - i));
                    str = sb.toString();
                }
                this.c.add(String.format(Locale.US, "%d %s%n", new Object[]{Long.valueOf(j), str.replaceAll("\r", UtilsCuentas.SEPARAOR2).replaceAll("\n", UtilsCuentas.SEPARAOR2)}).getBytes("UTF-8"));
                while (!this.c.isEmpty() && this.c.usedBytes() > this.b) {
                    this.c.remove();
                }
            } catch (IOException e) {
                Fabric.getLogger().e(CrashlyticsCore.TAG, "There was a problem writing to the Crashlytics log.", e);
            }
        }
    }
}
