package ar.com.santander.rio.mbanking.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.app.module.seguros.DetalleSeguroPresenter;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class UtilFile {
    private static ViewGroup a;
    public static DetalleSeguroPresenter detalleSeguroPresenter;

    public static File saveBitmap(String str, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(bitmap, byteArrayOutputStream);
        if (!isExternalStorageWritable() || !a(b())) {
            throw new Exception("Error al crear el directorio");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(b());
        sb.append(File.separator);
        sb.append(str);
        return a(new File(sb.toString()), byteArrayOutputStream);
    }

    public static File saveTmpBitmap(String str, Bitmap bitmap, Context context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(bitmap, byteArrayOutputStream);
        if (!isExternalStorageWritable() || !a(getPathReceiptTmp())) {
            throw new Exception("Error al crear el directorio");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        sb.append(File.separator);
        sb.append(str);
        return a(new File(sb.toString()), byteArrayOutputStream);
    }

    public static File savePDF(String str, File file, Context context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (isExternalStorageWritable() && a(getPathReceiptTmp())) {
            return a(file, byteArrayOutputStream);
        }
        throw new Exception("Error al crear el directorio");
    }

    private static void a(Bitmap bitmap, ByteArrayOutputStream byteArrayOutputStream) {
        bitmap.compress(CompressFormat.JPEG, 50, byteArrayOutputStream);
    }

    private static File a(File file, ByteArrayOutputStream byteArrayOutputStream) {
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.close();
        return file;
    }

    private static boolean a(String str) {
        File file = new File(str);
        if (file.exists()) {
            return true;
        }
        try {
            file.mkdirs();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append(Constants.DIR_EXTERNAL_STORAGE);
        return sb.toString();
    }

    private static String b() {
        StringBuilder sb = new StringBuilder();
        sb.append(a());
        sb.append(File.separator);
        sb.append(Constants.DIR_STORAGE_RECEIPTS);
        return sb.toString();
    }

    public static String getPathReceiptTmp() {
        StringBuilder sb = new StringBuilder();
        sb.append(a());
        sb.append(File.separator);
        sb.append(Constants.DIR_STORAGE_RECEIPTS_TMP);
        return sb.toString();
    }

    public static String getPathFundsDocumentacion() {
        StringBuilder sb = new StringBuilder();
        sb.append(a());
        sb.append(File.separator);
        sb.append(Constants.DIR_STORAGE_FUNDS);
        return sb.toString();
    }

    public static Bitmap getBitmapFromView(View view, ViewGroup viewGroup) {
        if (viewGroup != null) {
            a = viewGroup;
        }
        return getBitmapFromView(view);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap getBitmapFromView(android.view.View r8) {
        /*
            boolean r0 = r8 instanceof android.widget.ScrollView
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x001b
            r0 = r8
            android.widget.ScrollView r0 = (android.widget.ScrollView) r0
            android.view.View r3 = r0.getChildAt(r2)
            int r3 = r3.getHeight()
            android.view.View r0 = r0.getChildAt(r2)
            int r0 = r0.getWidth()
        L_0x0019:
            r4 = 0
            goto L_0x0073
        L_0x001b:
            boolean r0 = r8 instanceof android.widget.FrameLayout
            if (r0 == 0) goto L_0x0033
            r0 = r8
            android.widget.FrameLayout r0 = (android.widget.FrameLayout) r0
            android.view.View r3 = r0.getChildAt(r2)
            int r3 = r3.getHeight()
            android.view.View r0 = r0.getChildAt(r2)
            int r0 = r0.getWidth()
            goto L_0x0019
        L_0x0033:
            boolean r0 = r8 instanceof ar.com.santander.rio.mbanking.app.ui.forms.IFormViewGroup
            if (r0 == 0) goto L_0x006a
            r0 = r8
            ar.com.santander.rio.mbanking.app.ui.forms.IFormViewGroup r0 = (ar.com.santander.rio.mbanking.app.ui.forms.IFormViewGroup) r0
            int r3 = r0.getTotalHeight()
            int r0 = r8.getWidth()
            android.view.ViewGroup r4 = a
            if (r4 == 0) goto L_0x0019
            android.view.ViewGroup r4 = a
            r4.setDrawingCacheEnabled(r1)
            android.view.ViewGroup r4 = a
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r2)
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r2)
            r4.measure(r5, r6)
            android.view.ViewGroup r4 = a
            int r4 = r4.getMeasuredHeight()
            android.view.ViewGroup r5 = a
            r5.layout(r2, r2, r0, r4)
            android.view.ViewGroup r5 = a
            r5.buildDrawingCache(r1)
            int r3 = r3 + r4
            goto L_0x0073
        L_0x006a:
            int r3 = r8.getHeight()
            int r0 = r8.getWidth()
            goto L_0x0019
        L_0x0073:
            android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r3 = android.graphics.Bitmap.createBitmap(r0, r3, r5)
            android.graphics.Canvas r5 = new android.graphics.Canvas
            r5.<init>(r3)
            android.graphics.drawable.Drawable r6 = r8.getBackground()
            if (r6 == 0) goto L_0x0088
            r6.draw(r5)
            goto L_0x008c
        L_0x0088:
            r6 = -1
            r5.drawColor(r6)
        L_0x008c:
            boolean r6 = r8 instanceof ar.com.santander.rio.mbanking.app.ui.forms.IFormViewGroup
            if (r6 == 0) goto L_0x0096
            ar.com.santander.rio.mbanking.app.ui.forms.IFormViewGroup r8 = (ar.com.santander.rio.mbanking.app.ui.forms.IFormViewGroup) r8
            r8.drawCanvas(r5)
            goto L_0x0099
        L_0x0096:
            r8.draw(r5)
        L_0x0099:
            android.view.ViewGroup r8 = a
            if (r8 == 0) goto L_0x00c3
            android.view.ViewGroup r8 = a
            r8.setDrawingCacheEnabled(r1)
            android.view.ViewGroup r8 = a
            int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r2)
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r2)
            r8.measure(r6, r7)
            android.view.ViewGroup r8 = a
            r8.layout(r2, r2, r0, r4)
            android.view.ViewGroup r8 = a
            r8.buildDrawingCache(r1)
            android.view.ViewGroup r8 = a
            r8.draw(r5)
            android.view.ViewGroup r8 = a
            r8.getDrawingCache(r2)
        L_0x00c3:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.utils.UtilFile.getBitmapFromView(android.view.View):android.graphics.Bitmap");
    }

    public static boolean isExternalStorageWritable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageReadable() {
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public static boolean deleteDirectory(File file) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return true;
            }
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    deleteDirectory(listFiles[i]);
                } else {
                    listFiles[i].delete();
                }
            }
        }
        return file.delete();
    }

    public static boolean isBase64(String str) {
        return Pattern.compile("^([1-9][0-9]{0,2})?(\\.[0-9]?)?$").matcher(str).find();
    }

    public static File saveFileFromBase64(String str, String str2, Context context) {
        byte[] decode = Base64.decode(str, 0);
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!externalStoragePublicDirectory.exists()) {
            externalStoragePublicDirectory.mkdir();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(externalStoragePublicDirectory);
        sb.append(File.separator);
        sb.append(str2);
        File file = new File(sb.toString());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(decode);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void showPdf(File file, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName(), file), "application/pdf");
        intent.setFlags(1073741824);
        intent.setFlags(1);
        try {
            context.startActivity(Intent.createChooser(intent, "Open File"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean canDisplayPdf(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setType("application/pdf");
        return packageManager.queryIntentActivities(intent, 65536).size() > 0;
    }

    public static String downloadFundFile(String str, String str2, Context context) {
        try {
            DataInputStream dataInputStream = new DataInputStream(new URL(str2).openStream());
            byte[] bArr = new byte[1024];
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append(File.separator);
            sb.append(Constants.DIR_EXTERNAL_STORAGE);
            sb.append(File.separator);
            sb.append(Constants.DIR_STORAGE_FUNDS);
            sb.append(File.separator);
            sb.append(str);
            String sb2 = sb.toString();
            File file = new File(sb2);
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            while (true) {
                int read = dataInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.close();
                    return sb2;
                }
            }
        } catch (MalformedURLException e) {
            Log.e("SYNC getUpdate", "malformed url error", e);
            return "";
        } catch (IOException e2) {
            Log.e("SYNC getUpdate", "io error", e2);
            return "";
        } catch (SecurityException e3) {
            Log.e("SYNC getUpdate", "security error", e3);
            return "";
        } catch (Exception e4) {
            Log.e(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, "Other Download error", e4);
            return "";
        }
    }
}
