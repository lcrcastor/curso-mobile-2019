package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BitmapTeleporter extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<BitmapTeleporter> CREATOR = new zza();
    final int a;
    ParcelFileDescriptor b;
    final int c;
    private Bitmap d;
    private boolean e;
    private File f;

    BitmapTeleporter(int i, ParcelFileDescriptor parcelFileDescriptor, int i2) {
        this.a = i;
        this.b = parcelFileDescriptor;
        this.c = i2;
        this.d = null;
        this.e = false;
    }

    public BitmapTeleporter(Bitmap bitmap) {
        this.a = 1;
        this.b = null;
        this.c = 0;
        this.d = bitmap;
        this.e = true;
    }

    private FileOutputStream a() {
        if (this.f == null) {
            throw new IllegalStateException("setTempDir() must be called before writing this object to a parcel");
        }
        try {
            File createTempFile = File.createTempFile("teleporter", ".tmp", this.f);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
                this.b = ParcelFileDescriptor.open(createTempFile, 268435456);
                createTempFile.delete();
                return fileOutputStream;
            } catch (FileNotFoundException unused) {
                throw new IllegalStateException("Temporary file is somehow already deleted");
            }
        } catch (IOException e2) {
            throw new IllegalStateException("Could not create temporary file", e2);
        }
    }

    private void a(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e2) {
            Log.w("BitmapTeleporter", "Could not close stream", e2);
        }
    }

    public void release() {
        if (!this.e) {
            try {
                this.b.close();
            } catch (IOException e2) {
                Log.w("BitmapTeleporter", "Could not close PFD", e2);
            }
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.b == null) {
            Bitmap bitmap = this.d;
            ByteBuffer allocate = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
            bitmap.copyPixelsToBuffer(allocate);
            byte[] array = allocate.array();
            DataOutputStream dataOutputStream = new DataOutputStream(a());
            try {
                dataOutputStream.writeInt(array.length);
                dataOutputStream.writeInt(bitmap.getWidth());
                dataOutputStream.writeInt(bitmap.getHeight());
                dataOutputStream.writeUTF(bitmap.getConfig().toString());
                dataOutputStream.write(array);
                a(dataOutputStream);
            } catch (IOException e2) {
                throw new IllegalStateException("Could not write into unlinked file", e2);
            } catch (Throwable th) {
                a(dataOutputStream);
                throw th;
            }
        }
        zza.a(this, parcel, i | 1);
        this.b = null;
    }

    public Bitmap zzata() {
        if (!this.e) {
            DataInputStream dataInputStream = new DataInputStream(new AutoCloseInputStream(this.b));
            try {
                byte[] bArr = new byte[dataInputStream.readInt()];
                int readInt = dataInputStream.readInt();
                int readInt2 = dataInputStream.readInt();
                Config valueOf = Config.valueOf(dataInputStream.readUTF());
                dataInputStream.read(bArr);
                a(dataInputStream);
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                Bitmap createBitmap = Bitmap.createBitmap(readInt, readInt2, valueOf);
                createBitmap.copyPixelsFromBuffer(wrap);
                this.d = createBitmap;
                this.e = true;
            } catch (IOException e2) {
                throw new IllegalStateException("Could not read from parcel file descriptor", e2);
            } catch (Throwable th) {
                a(dataInputStream);
                throw th;
            }
        }
        return this.d;
    }

    public void zzd(File file) {
        if (file == null) {
            throw new NullPointerException("Cannot set null temp directory");
        }
        this.f = file;
    }
}
