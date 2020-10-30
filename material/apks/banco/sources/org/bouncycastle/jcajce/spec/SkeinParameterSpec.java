package org.bouncycastle.jcajce.spec;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.spec.AlgorithmParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;

public class SkeinParameterSpec implements AlgorithmParameterSpec {
    public static final int PARAM_TYPE_CONFIG = 4;
    public static final int PARAM_TYPE_KEY = 0;
    public static final int PARAM_TYPE_KEY_IDENTIFIER = 16;
    public static final int PARAM_TYPE_MESSAGE = 48;
    public static final int PARAM_TYPE_NONCE = 20;
    public static final int PARAM_TYPE_OUTPUT = 63;
    public static final int PARAM_TYPE_PERSONALISATION = 8;
    public static final int PARAM_TYPE_PUBLIC_KEY = 12;
    /* access modifiers changed from: private */
    public Map a;

    public static class Builder {
        private Map a = new HashMap();

        public Builder() {
        }

        public Builder(SkeinParameterSpec skeinParameterSpec) {
            for (Integer num : skeinParameterSpec.a.keySet()) {
                this.a.put(num, skeinParameterSpec.a.get(num));
            }
        }

        public SkeinParameterSpec build() {
            return new SkeinParameterSpec(this.a);
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

    public SkeinParameterSpec() {
        this(new HashMap());
    }

    private SkeinParameterSpec(Map map) {
        this.a = Collections.unmodifiableMap(map);
    }

    public byte[] getKey() {
        return Arrays.clone((byte[]) this.a.get(Integers.valueOf(0)));
    }

    public byte[] getKeyIdentifier() {
        return Arrays.clone((byte[]) this.a.get(Integers.valueOf(16)));
    }

    public byte[] getNonce() {
        return Arrays.clone((byte[]) this.a.get(Integers.valueOf(20)));
    }

    public Map getParameters() {
        return this.a;
    }

    public byte[] getPersonalisation() {
        return Arrays.clone((byte[]) this.a.get(Integers.valueOf(8)));
    }

    public byte[] getPublicKey() {
        return Arrays.clone((byte[]) this.a.get(Integers.valueOf(12)));
    }
}
