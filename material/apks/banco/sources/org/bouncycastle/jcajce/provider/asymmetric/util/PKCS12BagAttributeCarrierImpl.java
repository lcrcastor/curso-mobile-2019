package org.bouncycastle.jcajce.provider.asymmetric.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class PKCS12BagAttributeCarrierImpl implements PKCS12BagAttributeCarrier {
    private Hashtable a;
    private Vector b;

    public PKCS12BagAttributeCarrierImpl() {
        this(new Hashtable(), new Vector());
    }

    PKCS12BagAttributeCarrierImpl(Hashtable hashtable, Vector vector) {
        this.a = hashtable;
        this.b = vector;
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (ASN1Encodable) this.a.get(aSN1ObjectIdentifier);
    }

    public Enumeration getBagAttributeKeys() {
        return this.b.elements();
    }

    public void readObject(ObjectInputStream objectInputStream) {
        Object readObject = objectInputStream.readObject();
        if (readObject instanceof Hashtable) {
            this.a = (Hashtable) readObject;
            this.b = (Vector) objectInputStream.readObject();
            return;
        }
        ASN1InputStream aSN1InputStream = new ASN1InputStream((byte[]) readObject);
        while (true) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) aSN1InputStream.readObject();
            if (aSN1ObjectIdentifier != null) {
                setBagAttribute(aSN1ObjectIdentifier, aSN1InputStream.readObject());
            } else {
                return;
            }
        }
    }

    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        if (this.a.containsKey(aSN1ObjectIdentifier)) {
            this.a.put(aSN1ObjectIdentifier, aSN1Encodable);
            return;
        }
        this.a.put(aSN1ObjectIdentifier, aSN1Encodable);
        this.b.addElement(aSN1ObjectIdentifier);
    }

    public void writeObject(ObjectOutputStream objectOutputStream) {
        Object byteArray;
        if (this.b.size() == 0) {
            objectOutputStream.writeObject(new Hashtable());
            byteArray = new Vector();
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ASN1OutputStream aSN1OutputStream = new ASN1OutputStream(byteArrayOutputStream);
            Enumeration bagAttributeKeys = getBagAttributeKeys();
            while (bagAttributeKeys.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) bagAttributeKeys.nextElement();
                aSN1OutputStream.writeObject(aSN1ObjectIdentifier);
                aSN1OutputStream.writeObject((ASN1Encodable) this.a.get(aSN1ObjectIdentifier));
            }
            byteArray = byteArrayOutputStream.toByteArray();
        }
        objectOutputStream.writeObject(byteArray);
    }
}
