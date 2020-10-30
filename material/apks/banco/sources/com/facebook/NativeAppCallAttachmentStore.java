package com.facebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public final class NativeAppCallAttachmentStore implements AttachmentDataSource {
    private static final String a = "com.facebook.NativeAppCallAttachmentStore";
    private static File b;

    interface ProcessAttachment<T> {
        void a(T t, File file);
    }

    public void addAttachmentsForCall(Context context, UUID uuid, Map<String, Bitmap> map) {
        Validate.notNull(context, "context");
        Validate.notNull(uuid, "callId");
        Validate.containsNoNulls(map.values(), "imageAttachments");
        Validate.containsNoNullOrEmpty(map.keySet(), "imageAttachments");
        a(context, uuid, map, new ProcessAttachment<Bitmap>() {
            public void a(Bitmap bitmap, File file) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
                } finally {
                    Utility.closeQuietly(fileOutputStream);
                }
            }
        });
    }

    public void addAttachmentFilesForCall(Context context, UUID uuid, Map<String, File> map) {
        Validate.notNull(context, "context");
        Validate.notNull(uuid, "callId");
        Validate.containsNoNulls(map.values(), "mediaAttachmentFiles");
        Validate.containsNoNullOrEmpty(map.keySet(), "mediaAttachmentFiles");
        a(context, uuid, map, new ProcessAttachment<File>() {
            public void a(File file, File file2) {
                FileInputStream fileInputStream;
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    fileInputStream = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read > 0) {
                                fileOutputStream.write(bArr, 0, read);
                            } else {
                                Utility.closeQuietly(fileOutputStream);
                                Utility.closeQuietly(fileInputStream);
                                return;
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        Utility.closeQuietly(fileOutputStream);
                        Utility.closeQuietly(fileInputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = null;
                    Utility.closeQuietly(fileOutputStream);
                    Utility.closeQuietly(fileInputStream);
                    throw th;
                }
            }
        });
    }

    private <T> void a(Context context, UUID uuid, Map<String, T> map, ProcessAttachment<T> processAttachment) {
        if (map.size() != 0) {
            if (b == null) {
                c(context);
            }
            b(context);
            ArrayList<File> arrayList = new ArrayList<>();
            try {
                for (Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    Object value = entry.getValue();
                    File a2 = a(uuid, str, true);
                    arrayList.add(a2);
                    processAttachment.a(value, a2);
                }
            } catch (IOException e) {
                String str2 = a;
                StringBuilder sb = new StringBuilder();
                sb.append("Got unexpected exception:");
                sb.append(e);
                Log.e(str2, sb.toString());
                for (File delete : arrayList) {
                    try {
                        delete.delete();
                    } catch (Exception unused) {
                    }
                }
                throw new FacebookException((Throwable) e);
            }
        }
    }

    public void cleanupAttachmentsForCall(Context context, UUID uuid) {
        Utility.deleteDirectory(a(uuid, false));
    }

    public File openAttachment(UUID uuid, String str) {
        if (Utility.isNullOrEmpty(str) || uuid == null) {
            throw new FileNotFoundException();
        }
        try {
            return a(uuid, str, false);
        } catch (IOException unused) {
            throw new FileNotFoundException();
        }
    }

    static synchronized File a(Context context) {
        File file;
        synchronized (NativeAppCallAttachmentStore.class) {
            if (b == null) {
                b = new File(context.getCacheDir(), "com.facebook.NativeAppCallAttachmentStore.files");
            }
            file = b;
        }
        return file;
    }

    /* access modifiers changed from: 0000 */
    public File b(Context context) {
        File a2 = a(context);
        a2.mkdirs();
        return a2;
    }

    /* access modifiers changed from: 0000 */
    public File a(UUID uuid, boolean z) {
        if (b == null) {
            return null;
        }
        File file = new File(b, uuid.toString());
        if (z && !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /* access modifiers changed from: 0000 */
    public File a(UUID uuid, String str, boolean z) {
        File a2 = a(uuid, z);
        if (a2 == null) {
            return null;
        }
        try {
            return new File(a2, URLEncoder.encode(str, "UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(Context context) {
        Utility.deleteDirectory(a(context));
    }
}
