package org.bouncycastle.asn1;

import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public abstract class ASN1Set extends ASN1Primitive {
    private Vector a;
    private boolean b;

    protected ASN1Set() {
        this.a = new Vector();
        this.b = false;
    }

    protected ASN1Set(ASN1Encodable aSN1Encodable) {
        this.a = new Vector();
        this.b = false;
        this.a.addElement(aSN1Encodable);
    }

    protected ASN1Set(ASN1EncodableVector aSN1EncodableVector, boolean z) {
        this.a = new Vector();
        this.b = false;
        for (int i = 0; i != aSN1EncodableVector.size(); i++) {
            this.a.addElement(aSN1EncodableVector.get(i));
        }
        if (z) {
            sort();
        }
    }

    protected ASN1Set(ASN1Encodable[] aSN1EncodableArr, boolean z) {
        this.a = new Vector();
        this.b = false;
        for (int i = 0; i != aSN1EncodableArr.length; i++) {
            this.a.addElement(aSN1EncodableArr[i]);
        }
        if (z) {
            sort();
        }
    }

    private ASN1Encodable a(Enumeration enumeration) {
        ASN1Encodable aSN1Encodable = (ASN1Encodable) enumeration.nextElement();
        return aSN1Encodable == null ? DERNull.INSTANCE : aSN1Encodable;
    }

    private boolean a(byte[] bArr, byte[] bArr2) {
        int min = Math.min(bArr.length, bArr2.length);
        boolean z = false;
        for (int i = 0; i != min; i++) {
            if (bArr[i] != bArr2[i]) {
                if ((bArr[i] & UnsignedBytes.MAX_VALUE) < (bArr2[i] & UnsignedBytes.MAX_VALUE)) {
                    z = true;
                }
                return z;
            }
        }
        if (min == bArr.length) {
            z = true;
        }
        return z;
    }

    private byte[] a(ASN1Encodable aSN1Encodable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ASN1OutputStream(byteArrayOutputStream).writeObject(aSN1Encodable);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }

    public static ASN1Set getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Set)) {
            return (ASN1Set) obj;
        }
        if (obj instanceof ASN1SetParser) {
            return getInstance(((ASN1SetParser) obj).toASN1Primitive());
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("failed to construct set from byte[]: ");
                sb.append(e.getMessage());
                throw new IllegalArgumentException(sb.toString());
            }
        } else {
            if (obj instanceof ASN1Encodable) {
                ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
                if (aSN1Primitive instanceof ASN1Set) {
                    return (ASN1Set) aSN1Primitive;
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("unknown object in getInstance: ");
            sb2.append(obj.getClass().getName());
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public static ASN1Set getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            if (aSN1TaggedObject.isExplicit()) {
                return (ASN1Set) aSN1TaggedObject.getObject();
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        } else if (aSN1TaggedObject.isExplicit()) {
            return aSN1TaggedObject instanceof BERTaggedObject ? new BERSet((ASN1Encodable) aSN1TaggedObject.getObject()) : new DLSet((ASN1Encodable) aSN1TaggedObject.getObject());
        } else {
            if (aSN1TaggedObject.getObject() instanceof ASN1Set) {
                return (ASN1Set) aSN1TaggedObject.getObject();
            }
            if (aSN1TaggedObject.getObject() instanceof ASN1Sequence) {
                ASN1Sequence aSN1Sequence = (ASN1Sequence) aSN1TaggedObject.getObject();
                return aSN1TaggedObject instanceof BERTaggedObject ? new BERSet(aSN1Sequence.toArray()) : new DLSet(aSN1Sequence.toArray());
            }
            StringBuilder sb = new StringBuilder();
            sb.append("unknown object in getInstance: ");
            sb.append(aSN1TaggedObject.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1Set)) {
            return false;
        }
        ASN1Set aSN1Set = (ASN1Set) aSN1Primitive;
        if (size() != aSN1Set.size()) {
            return false;
        }
        Enumeration objects = getObjects();
        Enumeration objects2 = aSN1Set.getObjects();
        while (objects.hasMoreElements()) {
            ASN1Encodable a2 = a(objects);
            ASN1Encodable a3 = a(objects2);
            ASN1Primitive aSN1Primitive2 = a2.toASN1Primitive();
            ASN1Primitive aSN1Primitive3 = a3.toASN1Primitive();
            if (aSN1Primitive2 != aSN1Primitive3 && !aSN1Primitive2.equals(aSN1Primitive3)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public ASN1Primitive b() {
        if (this.b) {
            DERSet dERSet = new DERSet();
            dERSet.a = this.a;
            return dERSet;
        }
        Vector vector = new Vector();
        for (int i = 0; i != this.a.size(); i++) {
            vector.addElement(this.a.elementAt(i));
        }
        DERSet dERSet2 = new DERSet();
        dERSet2.a = vector;
        dERSet2.sort();
        return dERSet2;
    }

    /* access modifiers changed from: 0000 */
    public ASN1Primitive c() {
        DLSet dLSet = new DLSet();
        dLSet.a = this.a;
        return dLSet;
    }

    /* access modifiers changed from: 0000 */
    public abstract void encode(ASN1OutputStream aSN1OutputStream);

    public ASN1Encodable getObjectAt(int i) {
        return (ASN1Encodable) this.a.elementAt(i);
    }

    public Enumeration getObjects() {
        return this.a.elements();
    }

    public int hashCode() {
        Enumeration objects = getObjects();
        int size = size();
        while (objects.hasMoreElements()) {
            size = (size * 17) ^ a(objects).hashCode();
        }
        return size;
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstructed() {
        return true;
    }

    public ASN1SetParser parser() {
        return new ASN1SetParser() {
            private final int c = ASN1Set.this.size();
            private int d;

            public ASN1Primitive getLoadedObject() {
                return this;
            }

            public ASN1Encodable readObject() {
                if (this.d == this.c) {
                    return null;
                }
                ASN1Set aSN1Set = ASN1Set.this;
                int i = this.d;
                this.d = i + 1;
                ASN1Encodable objectAt = aSN1Set.getObjectAt(i);
                if (objectAt instanceof ASN1Sequence) {
                    return ((ASN1Sequence) objectAt).parser();
                }
                if (objectAt instanceof ASN1Set) {
                    objectAt = ((ASN1Set) objectAt).parser();
                }
                return objectAt;
            }

            public ASN1Primitive toASN1Primitive() {
                return this;
            }
        };
    }

    public int size() {
        return this.a.size();
    }

    /* access modifiers changed from: protected */
    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:29)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    public void sort() {
        /*
            r9 = this;
            boolean r0 = r9.b
            if (r0 != 0) goto L_0x005e
            r0 = 1
            r9.b = r0
            java.util.Vector r1 = r9.a
            int r1 = r1.size()
            if (r1 <= r0) goto L_0x005e
            java.util.Vector r1 = r9.a
            int r1 = r1.size()
            int r1 = r1 - r0
            r2 = r1
            r1 = 1
        L_0x0018:
            if (r1 == 0) goto L_0x005e
            java.util.Vector r1 = r9.a
            r3 = 0
            java.lang.Object r1 = r1.elementAt(r3)
            org.bouncycastle.asn1.ASN1Encodable r1 = (org.bouncycastle.asn1.ASN1Encodable) r1
            byte[] r1 = r9.a(r1)
            r4 = 0
            r5 = 0
        L_0x0029:
            if (r3 == r2) goto L_0x005b
            java.util.Vector r6 = r9.a
            int r7 = r3 + 1
            java.lang.Object r6 = r6.elementAt(r7)
            org.bouncycastle.asn1.ASN1Encodable r6 = (org.bouncycastle.asn1.ASN1Encodable) r6
            byte[] r6 = r9.a(r6)
            boolean r8 = r9.a(r1, r6)
            if (r8 == 0) goto L_0x0041
            r1 = r6
            goto L_0x0059
        L_0x0041:
            java.util.Vector r4 = r9.a
            java.lang.Object r4 = r4.elementAt(r3)
            java.util.Vector r5 = r9.a
            java.util.Vector r6 = r9.a
            java.lang.Object r6 = r6.elementAt(r7)
            r5.setElementAt(r6, r3)
            java.util.Vector r5 = r9.a
            r5.setElementAt(r4, r7)
            r4 = r3
            r5 = 1
        L_0x0059:
            r3 = r7
            goto L_0x0029
        L_0x005b:
            r2 = r4
            r1 = r5
            goto L_0x0018
        L_0x005e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.ASN1Set.sort():void");
    }

    public ASN1Encodable[] toArray() {
        ASN1Encodable[] aSN1EncodableArr = new ASN1Encodable[size()];
        for (int i = 0; i != size(); i++) {
            aSN1EncodableArr[i] = getObjectAt(i);
        }
        return aSN1EncodableArr;
    }

    public String toString() {
        return this.a.toString();
    }
}
