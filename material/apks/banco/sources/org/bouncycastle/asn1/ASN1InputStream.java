package org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.util.io.Streams;

public class ASN1InputStream extends FilterInputStream implements BERTags {
    private final int a;
    private final boolean b;
    private final byte[][] c;

    public ASN1InputStream(InputStream inputStream) {
        this(inputStream, StreamUtil.a(inputStream));
    }

    public ASN1InputStream(InputStream inputStream, int i) {
        this(inputStream, i, false);
    }

    public ASN1InputStream(InputStream inputStream, int i, boolean z) {
        super(inputStream);
        this.a = i;
        this.b = z;
        this.c = new byte[11][];
    }

    public ASN1InputStream(InputStream inputStream, boolean z) {
        this(inputStream, StreamUtil.a(inputStream), z);
    }

    public ASN1InputStream(byte[] bArr) {
        this((InputStream) new ByteArrayInputStream(bArr), bArr.length);
    }

    public ASN1InputStream(byte[] bArr, boolean z) {
        this(new ByteArrayInputStream(bArr), bArr.length, z);
    }

    static int a(InputStream inputStream, int i) {
        int i2 = i & 31;
        if (i2 != 31) {
            return i2;
        }
        int i3 = 0;
        int read = inputStream.read();
        if ((read & CertificateBody.profileType) == 0) {
            throw new IOException("corrupted stream - invalid high tag number found");
        }
        while (read >= 0 && (read & 128) != 0) {
            i3 = (i3 | (read & CertificateBody.profileType)) << 7;
            read = inputStream.read();
        }
        if (read >= 0) {
            return i3 | (read & CertificateBody.profileType);
        }
        throw new EOFException("EOF found inside tag value.");
    }

    static ASN1Primitive a(int i, DefiniteLengthInputStream definiteLengthInputStream, byte[][] bArr) {
        switch (i) {
            case 1:
                return ASN1Boolean.a(a(definiteLengthInputStream, bArr));
            case 2:
                return new ASN1Integer(definiteLengthInputStream.b(), false);
            case 3:
                return DERBitString.a(definiteLengthInputStream.a(), definiteLengthInputStream);
            case 4:
                return new DEROctetString(definiteLengthInputStream.b());
            case 5:
                return DERNull.INSTANCE;
            case 6:
                return ASN1ObjectIdentifier.a(a(definiteLengthInputStream, bArr));
            case 10:
                return ASN1Enumerated.a(a(definiteLengthInputStream, bArr));
            case 12:
                return new DERUTF8String(definiteLengthInputStream.b());
            case 18:
                return new DERNumericString(definiteLengthInputStream.b());
            case 19:
                return new DERPrintableString(definiteLengthInputStream.b());
            case 20:
                return new DERT61String(definiteLengthInputStream.b());
            case 22:
                return new DERIA5String(definiteLengthInputStream.b());
            case 23:
                return new ASN1UTCTime(definiteLengthInputStream.b());
            case 24:
                return new ASN1GeneralizedTime(definiteLengthInputStream.b());
            case 26:
                return new DERVisibleString(definiteLengthInputStream.b());
            case 27:
                return new DERGeneralString(definiteLengthInputStream.b());
            case 28:
                return new DERUniversalString(definiteLengthInputStream.b());
            case 30:
                return new DERBMPString(b(definiteLengthInputStream));
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("unknown tag ");
                sb.append(i);
                sb.append(" encountered");
                throw new IOException(sb.toString());
        }
    }

    private static byte[] a(DefiniteLengthInputStream definiteLengthInputStream, byte[][] bArr) {
        int a2 = definiteLengthInputStream.a();
        if (definiteLengthInputStream.a() >= bArr.length) {
            return definiteLengthInputStream.b();
        }
        byte[] bArr2 = bArr[a2];
        if (bArr2 == null) {
            bArr2 = new byte[a2];
            bArr[a2] = bArr2;
        }
        Streams.readFully(definiteLengthInputStream, bArr2);
        return bArr2;
    }

    static int b(InputStream inputStream, int i) {
        int read = inputStream.read();
        if (read < 0) {
            throw new EOFException("EOF found when length expected");
        } else if (read == 128) {
            return -1;
        } else {
            if (read > 127) {
                int i2 = read & CertificateBody.profileType;
                if (i2 > 4) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("DER length more than 4 bytes: ");
                    sb.append(i2);
                    throw new IOException(sb.toString());
                }
                int i3 = 0;
                for (int i4 = 0; i4 < i2; i4++) {
                    int read2 = inputStream.read();
                    if (read2 < 0) {
                        throw new EOFException("EOF found reading length");
                    }
                    i3 = (i3 << 8) + read2;
                }
                if (i3 < 0) {
                    throw new IOException("corrupted stream - negative length found");
                } else if (i3 >= i) {
                    throw new IOException("corrupted stream - out of bounds length found");
                } else {
                    read = i3;
                }
            }
            return read;
        }
    }

    private static char[] b(DefiniteLengthInputStream definiteLengthInputStream) {
        int a2 = definiteLengthInputStream.a() / 2;
        char[] cArr = new char[a2];
        int i = 0;
        while (i < a2) {
            int read = definiteLengthInputStream.read();
            if (read < 0) {
                return cArr;
            }
            int read2 = definiteLengthInputStream.read();
            if (read2 < 0) {
                return cArr;
            }
            int i2 = i + 1;
            cArr[i] = (char) ((read << 8) | (read2 & 255));
            i = i2;
        }
        return cArr;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public ASN1EncodableVector a(DefiniteLengthInputStream definiteLengthInputStream) {
        return new ASN1InputStream((InputStream) definiteLengthInputStream).b();
    }

    /* access modifiers changed from: 0000 */
    public ASN1EncodableVector b() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while (true) {
            ASN1Primitive readObject = readObject();
            if (readObject == null) {
                return aSN1EncodableVector;
            }
            aSN1EncodableVector.add(readObject);
        }
    }

    /* access modifiers changed from: protected */
    public ASN1Primitive buildObject(int i, int i2, int i3) {
        boolean z = (i & 32) != 0;
        DefiniteLengthInputStream definiteLengthInputStream = new DefiniteLengthInputStream(this, i3);
        if ((i & 64) != 0) {
            return new DERApplicationSpecific(z, i2, definiteLengthInputStream.b());
        }
        if ((i & 128) != 0) {
            return new ASN1StreamParser((InputStream) definiteLengthInputStream).b(z, i2);
        }
        if (!z) {
            return a(i2, definiteLengthInputStream, this.c);
        }
        if (i2 == 4) {
            ASN1EncodableVector a2 = a(definiteLengthInputStream);
            ASN1OctetString[] aSN1OctetStringArr = new ASN1OctetString[a2.size()];
            for (int i4 = 0; i4 != aSN1OctetStringArr.length; i4++) {
                aSN1OctetStringArr[i4] = (ASN1OctetString) a2.get(i4);
            }
            return new BEROctetString(aSN1OctetStringArr);
        } else if (i2 == 8) {
            return new DERExternal(a(definiteLengthInputStream));
        } else {
            switch (i2) {
                case 16:
                    return this.b ? new LazyEncodedSequence(definiteLengthInputStream.b()) : DERFactory.a(a(definiteLengthInputStream));
                case 17:
                    return DERFactory.b(a(definiteLengthInputStream));
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("unknown tag ");
                    sb.append(i2);
                    sb.append(" encountered");
                    throw new IOException(sb.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void readFully(byte[] bArr) {
        if (Streams.readFully(this, bArr) != bArr.length) {
            throw new EOFException("EOF encountered in middle of object");
        }
    }

    /* access modifiers changed from: protected */
    public int readLength() {
        return b(this, this.a);
    }

    public ASN1Primitive readObject() {
        int read = read();
        if (read > 0) {
            int a2 = a((InputStream) this, read);
            boolean z = (read & 32) != 0;
            int readLength = readLength();
            if (readLength >= 0) {
                try {
                    return buildObject(read, a2, readLength);
                } catch (IllegalArgumentException e) {
                    throw new ASN1Exception("corrupted stream detected", e);
                }
            } else if (!z) {
                throw new IOException("indefinite length primitive encoding encountered");
            } else {
                ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this, this.a), this.a);
                if ((read & 64) != 0) {
                    return new BERApplicationSpecificParser(a2, aSN1StreamParser).getLoadedObject();
                }
                if ((read & 128) != 0) {
                    return new BERTaggedObjectParser(true, a2, aSN1StreamParser).getLoadedObject();
                }
                if (a2 == 4) {
                    return new BEROctetStringParser(aSN1StreamParser).getLoadedObject();
                }
                if (a2 == 8) {
                    return new DERExternalParser(aSN1StreamParser).getLoadedObject();
                }
                switch (a2) {
                    case 16:
                        return new BERSequenceParser(aSN1StreamParser).getLoadedObject();
                    case 17:
                        return new BERSetParser(aSN1StreamParser).getLoadedObject();
                    default:
                        throw new IOException("unknown BER object encountered");
                }
            }
        } else if (read != 0) {
            return null;
        } else {
            throw new IOException("unexpected end-of-contents marker");
        }
    }
}
