package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.util.Strings;

public class ServerName {
    protected Object name;
    protected short nameType;

    public ServerName(short s, Object obj) {
        if (!isCorrectType(s, obj)) {
            throw new IllegalArgumentException("'name' is not an instance of the correct type");
        }
        this.nameType = s;
        this.name = obj;
    }

    protected static boolean isCorrectType(short s, Object obj) {
        if (s == 0) {
            return obj instanceof String;
        }
        throw new IllegalArgumentException("'name' is an unsupported value");
    }

    public static ServerName parse(InputStream inputStream) {
        short readUint8 = TlsUtils.readUint8(inputStream);
        if (readUint8 != 0) {
            throw new TlsFatalAlert(50);
        }
        byte[] readOpaque16 = TlsUtils.readOpaque16(inputStream);
        if (readOpaque16.length >= 1) {
            return new ServerName(readUint8, Strings.fromUTF8ByteArray(readOpaque16));
        }
        throw new TlsFatalAlert(50);
    }

    public void encode(OutputStream outputStream) {
        TlsUtils.writeUint8(this.nameType, outputStream);
        if (this.nameType != 0) {
            throw new TlsFatalAlert(80);
        }
        byte[] uTF8ByteArray = Strings.toUTF8ByteArray((String) this.name);
        if (uTF8ByteArray.length < 1) {
            throw new TlsFatalAlert(80);
        }
        TlsUtils.writeOpaque16(uTF8ByteArray, outputStream);
    }

    public String getHostName() {
        if (isCorrectType(0, this.name)) {
            return (String) this.name;
        }
        throw new IllegalStateException("'name' is not a HostName string");
    }

    public Object getName() {
        return this.name;
    }

    public short getNameType() {
        return this.nameType;
    }
}
