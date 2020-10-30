package org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASN1StreamParser {
    private final InputStream a;
    private final int b;
    private final byte[][] c;

    public ASN1StreamParser(InputStream inputStream) {
        this(inputStream, StreamUtil.a(inputStream));
    }

    public ASN1StreamParser(InputStream inputStream, int i) {
        this.a = inputStream;
        this.b = i;
        this.c = new byte[11][];
    }

    public ASN1StreamParser(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    private void a(boolean z) {
        if (this.a instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) this.a).a(z);
        }
    }

    /* access modifiers changed from: 0000 */
    public ASN1Encodable a(int i) {
        if (i == 4) {
            return new BEROctetStringParser(this);
        }
        if (i == 8) {
            return new DERExternalParser(this);
        }
        switch (i) {
            case 16:
                return new BERSequenceParser(this);
            case 17:
                return new BERSetParser(this);
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("unknown BER object encountered: 0x");
                sb.append(Integer.toHexString(i));
                throw new ASN1Exception(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public ASN1Encodable a(boolean z, int i) {
        if (!(this.a instanceof IndefiniteLengthInputStream)) {
            if (z) {
                if (i == 4) {
                    return new BEROctetStringParser(this);
                }
                switch (i) {
                    case 16:
                        return new DERSequenceParser(this);
                    case 17:
                        return new DERSetParser(this);
                }
            } else if (i == 4) {
                return new DEROctetStringParser((DefiniteLengthInputStream) this.a);
            } else {
                switch (i) {
                    case 16:
                        throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
                    case 17:
                        throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
                }
            }
            throw new RuntimeException("implicit tagging not implemented");
        } else if (z) {
            return a(i);
        } else {
            throw new IOException("indefinite length primitive encoding encountered");
        }
    }

    /* access modifiers changed from: 0000 */
    public ASN1EncodableVector a() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while (true) {
            ASN1Encodable readObject = readObject();
            if (readObject == null) {
                return aSN1EncodableVector;
            }
            aSN1EncodableVector.add(readObject instanceof InMemoryRepresentable ? ((InMemoryRepresentable) readObject).getLoadedObject() : readObject.toASN1Primitive());
        }
    }

    /* access modifiers changed from: 0000 */
    public ASN1Primitive b(boolean z, int i) {
        if (!z) {
            return new DERTaggedObject(false, i, new DEROctetString(((DefiniteLengthInputStream) this.a).b()));
        }
        ASN1EncodableVector a2 = a();
        return this.a instanceof IndefiniteLengthInputStream ? a2.size() == 1 ? new BERTaggedObject(true, i, a2.get(0)) : new BERTaggedObject(false, i, BERFactory.a(a2)) : a2.size() == 1 ? new DERTaggedObject(true, i, a2.get(0)) : new DERTaggedObject(false, i, DERFactory.a(a2));
    }

    public ASN1Encodable readObject() {
        int read = this.a.read();
        if (read == -1) {
            return null;
        }
        boolean z = false;
        a(false);
        int a2 = ASN1InputStream.a(this.a, read);
        if ((read & 32) != 0) {
            z = true;
        }
        int b2 = ASN1InputStream.b(this.a, this.b);
        if (b2 >= 0) {
            DefiniteLengthInputStream definiteLengthInputStream = new DefiniteLengthInputStream(this.a, b2);
            if ((read & 64) != 0) {
                return new DERApplicationSpecific(z, a2, definiteLengthInputStream.b());
            }
            if ((read & 128) != 0) {
                return new BERTaggedObjectParser(z, a2, new ASN1StreamParser((InputStream) definiteLengthInputStream));
            }
            if (z) {
                if (a2 == 4) {
                    return new BEROctetStringParser(new ASN1StreamParser((InputStream) definiteLengthInputStream));
                }
                if (a2 == 8) {
                    return new DERExternalParser(new ASN1StreamParser((InputStream) definiteLengthInputStream));
                }
                switch (a2) {
                    case 16:
                        return new DERSequenceParser(new ASN1StreamParser((InputStream) definiteLengthInputStream));
                    case 17:
                        return new DERSetParser(new ASN1StreamParser((InputStream) definiteLengthInputStream));
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("unknown tag ");
                        sb.append(a2);
                        sb.append(" encountered");
                        throw new IOException(sb.toString());
                }
            } else if (a2 == 4) {
                return new DEROctetStringParser(definiteLengthInputStream);
            } else {
                try {
                    return ASN1InputStream.a(a2, definiteLengthInputStream, this.c);
                } catch (IllegalArgumentException e) {
                    throw new ASN1Exception("corrupted stream detected", e);
                }
            }
        } else if (!z) {
            throw new IOException("indefinite length primitive encoding encountered");
        } else {
            ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this.a, this.b), this.b);
            return (read & 64) != 0 ? new BERApplicationSpecificParser(a2, aSN1StreamParser) : (read & 128) != 0 ? new BERTaggedObjectParser(true, a2, aSN1StreamParser) : aSN1StreamParser.a(a2);
        }
    }
}
