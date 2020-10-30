package com.orhanobut.logger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DiskLogStrategy implements LogStrategy {
    @NonNull
    private final Handler a;

    static class WriteHandler extends Handler {
        @NonNull
        private final String a;
        private final int b;

        WriteHandler(@NonNull Looper looper, @NonNull String str, int i) {
            super((Looper) Utils.b(looper));
            this.a = (String) Utils.b(str);
            this.b = i;
        }

        public void handleMessage(@NonNull Message message) {
            FileWriter fileWriter;
            String str = (String) message.obj;
            try {
                fileWriter = new FileWriter(a(this.a, "logs"), true);
                try {
                    a(fileWriter, str);
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException unused) {
                }
            } catch (IOException unused2) {
                fileWriter = null;
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException unused3) {
                    }
                }
            }
        }

        private void a(@NonNull FileWriter fileWriter, @NonNull String str) {
            Utils.b(fileWriter);
            Utils.b(str);
            fileWriter.append(str);
        }

        private File a(@NonNull String str, @NonNull String str2) {
            Utils.b(str);
            Utils.b(str2);
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = null;
            File file3 = new File(file, String.format("%s_%s.csv", new Object[]{str2, Integer.valueOf(0)}));
            int i = 0;
            while (file3.exists()) {
                i++;
                File file4 = new File(file, String.format("%s_%s.csv", new Object[]{str2, Integer.valueOf(i)}));
                file2 = file3;
                file3 = file4;
            }
            return (file2 == null || file2.length() >= ((long) this.b)) ? file3 : file2;
        }
    }

    public DiskLogStrategy(@NonNull Handler handler) {
        this.a = (Handler) Utils.b(handler);
    }

    public void log(int i, @Nullable String str, @NonNull String str2) {
        Utils.b(str2);
        this.a.sendMessage(this.a.obtainMessage(i, str2));
    }
}
