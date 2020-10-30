package cz.msebera.android.httpclient.extras;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.support.media.ExifInterface;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.security.Security;

@TargetApi(4)
public final class PRNGFixes {
    private static final byte[] a = f();

    public static class LinuxPRNGSecureRandom extends SecureRandomSpi {
        private static final File a = new File("/dev/urandom");
        private static final Object b = new Object();
        private static DataInputStream c;
        private static OutputStream d;
        private boolean e;

        /* access modifiers changed from: protected */
        public void engineSetSeed(byte[] bArr) {
            OutputStream b2;
            try {
                synchronized (b) {
                    b2 = b();
                }
                b2.write(bArr);
                b2.flush();
            } catch (IOException unused) {
                try {
                    String simpleName = PRNGFixes.class.getSimpleName();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to mix seed into ");
                    sb.append(a);
                    Log.w(simpleName, sb.toString());
                } catch (Throwable th) {
                    this.e = true;
                    throw th;
                }
            }
            this.e = true;
        }

        /* access modifiers changed from: protected */
        public void engineNextBytes(byte[] bArr) {
            DataInputStream a2;
            if (!this.e) {
                engineSetSeed(PRNGFixes.d());
            }
            try {
                synchronized (b) {
                    a2 = a();
                }
                synchronized (a2) {
                    a2.readFully(bArr);
                }
            } catch (IOException e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to read from ");
                sb.append(a);
                throw new SecurityException(sb.toString(), e2);
            }
        }

        /* access modifiers changed from: protected */
        public byte[] engineGenerateSeed(int i) {
            byte[] bArr = new byte[i];
            engineNextBytes(bArr);
            return bArr;
        }

        private DataInputStream a() {
            DataInputStream dataInputStream;
            synchronized (b) {
                if (c == null) {
                    try {
                        c = new DataInputStream(new FileInputStream(a));
                    } catch (IOException e2) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Failed to open ");
                        sb.append(a);
                        sb.append(" for reading");
                        throw new SecurityException(sb.toString(), e2);
                    }
                }
                dataInputStream = c;
            }
            return dataInputStream;
        }

        private OutputStream b() {
            OutputStream outputStream;
            synchronized (b) {
                if (d == null) {
                    d = new FileOutputStream(a);
                }
                outputStream = d;
            }
            return outputStream;
        }
    }

    static class LinuxPRNGSecureRandomProvider extends Provider {
        public LinuxPRNGSecureRandomProvider() {
            super("LinuxPRNG", 1.0d, "A Linux-specific random number provider that uses /dev/urandom");
            put("SecureRandom.SHA1PRNG", LinuxPRNGSecureRandom.class.getName());
            put("SecureRandom.SHA1PRNG ImplementedIn", ExifInterface.TAG_SOFTWARE);
        }
    }

    private PRNGFixes() {
    }

    public static void apply() {
        b();
        c();
    }

    private static void b() {
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 18) {
            try {
                Class.forName("org.apache.harmony.xnet.provider.jsse.NativeCrypto").getMethod("RAND_seed", new Class[]{byte[].class}).invoke(null, new Object[]{d()});
                int intValue = ((Integer) Class.forName("org.apache.harmony.xnet.provider.jsse.NativeCrypto").getMethod("RAND_load_file", new Class[]{String.class, Long.TYPE}).invoke(null, new Object[]{"/dev/urandom", Integer.valueOf(1024)})).intValue();
                if (intValue != 1024) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unexpected number of bytes read from Linux PRNG: ");
                    sb.append(intValue);
                    throw new IOException(sb.toString());
                }
            } catch (Exception e) {
                throw new SecurityException("Failed to seed OpenSSL PRNG", e);
            }
        }
    }

    private static void c() {
        if (VERSION.SDK_INT <= 18) {
            Provider[] providers = Security.getProviders("SecureRandom.SHA1PRNG");
            if (providers == null || providers.length < 1 || !LinuxPRNGSecureRandomProvider.class.equals(providers[0].getClass())) {
                Security.insertProviderAt(new LinuxPRNGSecureRandomProvider(), 1);
            }
            SecureRandom secureRandom = new SecureRandom();
            if (!LinuxPRNGSecureRandomProvider.class.equals(secureRandom.getProvider().getClass())) {
                StringBuilder sb = new StringBuilder();
                sb.append("new SecureRandom() backed by wrong Provider: ");
                sb.append(secureRandom.getProvider().getClass());
                throw new SecurityException(sb.toString());
            }
            try {
                SecureRandom instance = SecureRandom.getInstance("SHA1PRNG");
                if (!LinuxPRNGSecureRandomProvider.class.equals(instance.getProvider().getClass())) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("SecureRandom.getInstance(\"SHA1PRNG\") backed by wrong Provider: ");
                    sb2.append(instance.getProvider().getClass());
                    throw new SecurityException(sb2.toString());
                }
            } catch (NoSuchAlgorithmException e) {
                throw new SecurityException("SHA1PRNG not available", e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static byte[] d() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeLong(System.currentTimeMillis());
            dataOutputStream.writeLong(System.nanoTime());
            dataOutputStream.writeInt(Process.myPid());
            dataOutputStream.writeInt(Process.myUid());
            dataOutputStream.write(a);
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SecurityException("Failed to generate seed", e);
        }
    }

    private static String e() {
        try {
            return (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception unused) {
            return null;
        }
    }

    private static byte[] f() {
        StringBuilder sb = new StringBuilder();
        String str = Build.FINGERPRINT;
        if (str != null) {
            sb.append(str);
        }
        String e = e();
        if (e != null) {
            sb.append(e);
        }
        try {
            return sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("UTF-8 encoding not supported");
        }
    }
}
