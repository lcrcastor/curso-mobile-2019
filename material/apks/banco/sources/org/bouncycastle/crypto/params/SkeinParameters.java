package org.bouncycastle.crypto.params;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.util.Integers;

public class SkeinParameters implements CipherParameters {
    public static final int PARAM_TYPE_CONFIG = 4;
    public static final int PARAM_TYPE_KEY = 0;
    public static final int PARAM_TYPE_KEY_IDENTIFIER = 16;
    public static final int PARAM_TYPE_MESSAGE = 48;
    public static final int PARAM_TYPE_NONCE = 20;
    public static final int PARAM_TYPE_OUTPUT = 63;
    public static final int PARAM_TYPE_PERSONALISATION = 8;
    public static final int PARAM_TYPE_PUBLIC_KEY = 12;
    /* access modifiers changed from: private */
    public Hashtable a;

    public static class Builder {
        private Hashtable a = new Hashtable();

        public Builder() {
        }

        public Builder(Hashtable hashtable) {
            Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                Integer num = (Integer) keys.nextElement();
                this.a.put(num, hashtable.get(num));
            }
        }

        public Builder(SkeinParameters skeinParameters) {
            Enumeration keys = skeinParameters.a.keys();
            while (keys.hasMoreElements()) {
                Integer num = (Integer) keys.nextElement();
                this.a.put(num, skeinParameters.a.get(num));
            }
        }

        public SkeinParameters build() {
            return new SkeinParameters(this.a);
        }

        public Builder set(int i, byte[] bArr) {
            if (bArr == null) {
                throw new IllegalArgumentException("Parameter value must not be null.");
            } else if (i != 0 && (i <= 4 || i >= 63 || i == 48)) {
                throw new IllegalArgumentException("Parameter types must be in the range 0,5..47,49..62.");
            } else if (i == 4) {
                throw new IllegalArgumentException("Parameter type 4 is reserved for internal use.");
            } else {
                this.a.put(Integers.valueOf(i), bArr);
                return this;
            }
        }

        public Builder setKey(byte[] bArr) {
            return set(0, bArr);
        }

        public Builder setKeyIdentifier(byte[] bArr) {
            return set(16, bArr);
        }

        public Builder setNonce(byte[] bArr) {
            return set(20, bArr);
        }

        public Builder setPersonalisation(Date date, String str, String str2) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
                outputStreamWriter.write(new SimpleDateFormat("YYYYMMDD").format(date));
                outputStreamWriter.write(UtilsCuentas.SEPARAOR2);
                outputStreamWriter.write(str);
                outputStreamWriter.write(UtilsCuentas.SEPARAOR2);
                outputStreamWriter.write(str2);
                outputStreamWriter.close();
                return set(8, byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Byte I/O failed: ");
                sb.append(e);
                throw new IllegalStateException(sb.toString());
            }
        }

        public Builder setPersonalisation(Date date, Locale locale, String str, String str2) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
                outputStreamWriter.write(new SimpleDateFormat("YYYYMMDD", locale).format(date));
                outputStreamWriter.write(UtilsCuentas.SEPARAOR2);
                outputStreamWriter.write(str);
                outputStreamWriter.write(UtilsCuentas.SEPARAOR2);
                outputStreamWriter.write(str2);
                outputStreamWriter.close();
                return set(8, byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Byte I/O failed: ");
                sb.append(e);
                throw new IllegalStateException(sb.toString());
            }
        }

        public Builder setPersonalisation(byte[] bArr) {
            return set(8, bArr);
        }

        public Builder setPublicKey(byte[] bArr) {
            return set(12, bArr);
        }
    }

    public SkeinParameters() {
        this(new Hashtable());
    }

    private SkeinParameters(Hashtable hashtable) {
        this.a = hashtable;
    }

    public byte[] getKey() {
        return (byte[]) this.a.get(Integers.valueOf(0));
    }

    public byte[] getKeyIdentifier() {
        return (byte[]) this.a.get(Integers.valueOf(16));
    }

    public byte[] getNonce() {
        return (byte[]) this.a.get(Integers.valueOf(20));
    }

    public Hashtable getParameters() {
        return this.a;
    }

    public byte[] getPersonalisation() {
        return (byte[]) this.a.get(Integers.valueOf(8));
    }

    public byte[] getPublicKey() {
        return (byte[]) this.a.get(Integers.valueOf(12));
    }
}
