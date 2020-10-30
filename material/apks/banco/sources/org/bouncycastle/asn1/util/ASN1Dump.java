package org.bouncycastle.asn1.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.BERApplicationSpecific;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERExternal;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERT61String;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.DERVisibleString;
import org.bouncycastle.util.encoders.Hex;

public class ASN1Dump {
    private static String a(String str, String str2, boolean z, ASN1Primitive aSN1Primitive, String str3) {
        DERApplicationSpecific dERApplicationSpecific = (DERApplicationSpecific) aSN1Primitive;
        StringBuffer stringBuffer = new StringBuffer();
        if (dERApplicationSpecific.isConstructed()) {
            try {
                ASN1Sequence instance = ASN1Sequence.getInstance(dERApplicationSpecific.getObject(16));
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(str);
                sb.append(" ApplicationSpecific[");
                sb.append(dERApplicationSpecific.getApplicationTag());
                sb.append("]");
                sb.append(str3);
                stringBuffer.append(sb.toString());
                Enumeration objects = instance.getObjects();
                while (objects.hasMoreElements()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append("    ");
                    a(sb2.toString(), z, (ASN1Primitive) objects.nextElement(), stringBuffer);
                }
            } catch (IOException e) {
                stringBuffer.append(e);
            }
            return stringBuffer.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(str);
        sb3.append(" ApplicationSpecific[");
        sb3.append(dERApplicationSpecific.getApplicationTag());
        sb3.append("] (");
        sb3.append(new String(Hex.encode(dERApplicationSpecific.getContents())));
        sb3.append(")");
        sb3.append(str3);
        return sb3.toString();
    }

    private static String a(String str, byte[] bArr) {
        String a;
        String property = System.getProperty("line.separator");
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("    ");
        String sb2 = sb.toString();
        stringBuffer.append(property);
        for (int i = 0; i < bArr.length; i += 32) {
            if (bArr.length - i > 32) {
                stringBuffer.append(sb2);
                stringBuffer.append(new String(Hex.encode(bArr, i, 32)));
                stringBuffer.append("    ");
                a = a(bArr, i, 32);
            } else {
                stringBuffer.append(sb2);
                stringBuffer.append(new String(Hex.encode(bArr, i, bArr.length - i)));
                for (int length = bArr.length - i; length != 32; length++) {
                    stringBuffer.append("  ");
                }
                stringBuffer.append("    ");
                a = a(bArr, i, bArr.length - i);
            }
            stringBuffer.append(a);
            stringBuffer.append(property);
        }
        return stringBuffer.toString();
    }

    private static String a(byte[] bArr, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = i; i3 != i + i2; i3++) {
            if (bArr[i3] >= 32 && bArr[i3] <= 126) {
                stringBuffer.append((char) bArr[i3]);
            }
        }
        return stringBuffer.toString();
    }

    static void a(String str, boolean z, ASN1Primitive aSN1Primitive, StringBuffer stringBuffer) {
        String str2;
        StringBuilder sb;
        String obj;
        StringBuilder sb2;
        BigInteger value;
        String str3;
        String time;
        byte[] bytes;
        StringBuilder sb3;
        int length;
        String property = System.getProperty("line.separator");
        if (aSN1Primitive instanceof ASN1Sequence) {
            Enumeration objects = ((ASN1Sequence) aSN1Primitive).getObjects();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append("    ");
            String sb5 = sb4.toString();
            stringBuffer.append(str);
            String str4 = aSN1Primitive instanceof BERSequence ? "BER Sequence" : aSN1Primitive instanceof DERSequence ? "DER Sequence" : "Sequence";
            loop1:
            while (true) {
                stringBuffer.append(str4);
                stringBuffer.append(property);
                while (objects.hasMoreElements()) {
                    Object nextElement = objects.nextElement();
                    if (nextElement == null || nextElement.equals(DERNull.INSTANCE)) {
                        stringBuffer.append(sb5);
                        str4 = "NULL";
                    } else {
                        a(sb5, z, nextElement instanceof ASN1Primitive ? (ASN1Primitive) nextElement : ((ASN1Encodable) nextElement).toASN1Primitive(), stringBuffer);
                    }
                }
                break loop1;
            }
        } else {
            if (aSN1Primitive instanceof ASN1TaggedObject) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(str);
                sb6.append("    ");
                String sb7 = sb6.toString();
                stringBuffer.append(str);
                stringBuffer.append(aSN1Primitive instanceof BERTaggedObject ? "BER Tagged [" : "Tagged [");
                ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
                stringBuffer.append(Integer.toString(aSN1TaggedObject.getTagNo()));
                stringBuffer.append(']');
                if (!aSN1TaggedObject.isExplicit()) {
                    stringBuffer.append(" IMPLICIT ");
                }
                stringBuffer.append(property);
                if (aSN1TaggedObject.isEmpty()) {
                    stringBuffer.append(sb7);
                    stringBuffer.append("EMPTY");
                } else {
                    a(sb7, z, aSN1TaggedObject.getObject(), stringBuffer);
                    return;
                }
            } else if (aSN1Primitive instanceof ASN1Set) {
                Enumeration objects2 = ((ASN1Set) aSN1Primitive).getObjects();
                StringBuilder sb8 = new StringBuilder();
                sb8.append(str);
                sb8.append("    ");
                String sb9 = sb8.toString();
                stringBuffer.append(str);
                String str5 = aSN1Primitive instanceof BERSet ? "BER Set" : "DER Set";
                loop3:
                while (true) {
                    stringBuffer.append(str5);
                    stringBuffer.append(property);
                    while (objects2.hasMoreElements()) {
                        Object nextElement2 = objects2.nextElement();
                        if (nextElement2 == null) {
                            stringBuffer.append(sb9);
                            str5 = "NULL";
                        } else {
                            a(sb9, z, nextElement2 instanceof ASN1Primitive ? (ASN1Primitive) nextElement2 : ((ASN1Encodable) nextElement2).toASN1Primitive(), stringBuffer);
                        }
                    }
                    break loop3;
                }
            } else {
                if (aSN1Primitive instanceof ASN1OctetString) {
                    ASN1OctetString aSN1OctetString = (ASN1OctetString) aSN1Primitive;
                    if (aSN1Primitive instanceof BEROctetString) {
                        sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append("BER Constructed Octet String");
                        sb3.append("[");
                        length = aSN1OctetString.getOctets().length;
                    } else {
                        sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append("DER Octet String");
                        sb3.append("[");
                        length = aSN1OctetString.getOctets().length;
                    }
                    sb3.append(length);
                    sb3.append("] ");
                    stringBuffer.append(sb3.toString());
                    if (z) {
                        bytes = aSN1OctetString.getOctets();
                    }
                } else {
                    if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
                        sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append("ObjectIdentifier(");
                        sb2.append(((ASN1ObjectIdentifier) aSN1Primitive).getId());
                    } else if (aSN1Primitive instanceof ASN1Boolean) {
                        sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append("Boolean(");
                        sb2.append(((ASN1Boolean) aSN1Primitive).isTrue());
                    } else {
                        if (aSN1Primitive instanceof ASN1Integer) {
                            sb2 = new StringBuilder();
                            sb2.append(str);
                            sb2.append("Integer(");
                            value = ((ASN1Integer) aSN1Primitive).getValue();
                        } else if (aSN1Primitive instanceof DERBitString) {
                            DERBitString dERBitString = (DERBitString) aSN1Primitive;
                            StringBuilder sb10 = new StringBuilder();
                            sb10.append(str);
                            sb10.append("DER Bit String");
                            sb10.append("[");
                            sb10.append(dERBitString.getBytes().length);
                            sb10.append(", ");
                            sb10.append(dERBitString.getPadBits());
                            sb10.append("] ");
                            stringBuffer.append(sb10.toString());
                            if (z) {
                                bytes = dERBitString.getBytes();
                            }
                        } else {
                            if (aSN1Primitive instanceof DERIA5String) {
                                sb = new StringBuilder();
                                sb.append(str);
                                sb.append("IA5String(");
                                time = ((DERIA5String) aSN1Primitive).getString();
                            } else if (aSN1Primitive instanceof DERUTF8String) {
                                sb = new StringBuilder();
                                sb.append(str);
                                sb.append("UTF8String(");
                                time = ((DERUTF8String) aSN1Primitive).getString();
                            } else if (aSN1Primitive instanceof DERPrintableString) {
                                sb = new StringBuilder();
                                sb.append(str);
                                sb.append("PrintableString(");
                                time = ((DERPrintableString) aSN1Primitive).getString();
                            } else if (aSN1Primitive instanceof DERVisibleString) {
                                sb = new StringBuilder();
                                sb.append(str);
                                sb.append("VisibleString(");
                                time = ((DERVisibleString) aSN1Primitive).getString();
                            } else if (aSN1Primitive instanceof DERBMPString) {
                                sb = new StringBuilder();
                                sb.append(str);
                                sb.append("BMPString(");
                                time = ((DERBMPString) aSN1Primitive).getString();
                            } else if (aSN1Primitive instanceof DERT61String) {
                                sb = new StringBuilder();
                                sb.append(str);
                                sb.append("T61String(");
                                time = ((DERT61String) aSN1Primitive).getString();
                            } else if (aSN1Primitive instanceof ASN1UTCTime) {
                                sb = new StringBuilder();
                                sb.append(str);
                                sb.append("UTCTime(");
                                time = ((ASN1UTCTime) aSN1Primitive).getTime();
                            } else if (aSN1Primitive instanceof ASN1GeneralizedTime) {
                                sb = new StringBuilder();
                                sb.append(str);
                                sb.append("GeneralizedTime(");
                                time = ((ASN1GeneralizedTime) aSN1Primitive).getTime();
                            } else {
                                if (aSN1Primitive instanceof BERApplicationSpecific) {
                                    str3 = ASN1Encoding.BER;
                                } else if (aSN1Primitive instanceof DERApplicationSpecific) {
                                    str3 = ASN1Encoding.DER;
                                } else if (aSN1Primitive instanceof ASN1Enumerated) {
                                    ASN1Enumerated aSN1Enumerated = (ASN1Enumerated) aSN1Primitive;
                                    sb2 = new StringBuilder();
                                    sb2.append(str);
                                    sb2.append("DER Enumerated(");
                                    value = aSN1Enumerated.getValue();
                                } else if (aSN1Primitive instanceof DERExternal) {
                                    DERExternal dERExternal = (DERExternal) aSN1Primitive;
                                    StringBuilder sb11 = new StringBuilder();
                                    sb11.append(str);
                                    sb11.append("External ");
                                    sb11.append(property);
                                    stringBuffer.append(sb11.toString());
                                    StringBuilder sb12 = new StringBuilder();
                                    sb12.append(str);
                                    sb12.append("    ");
                                    String sb13 = sb12.toString();
                                    if (dERExternal.getDirectReference() != null) {
                                        StringBuilder sb14 = new StringBuilder();
                                        sb14.append(sb13);
                                        sb14.append("Direct Reference: ");
                                        sb14.append(dERExternal.getDirectReference().getId());
                                        sb14.append(property);
                                        stringBuffer.append(sb14.toString());
                                    }
                                    if (dERExternal.getIndirectReference() != null) {
                                        StringBuilder sb15 = new StringBuilder();
                                        sb15.append(sb13);
                                        sb15.append("Indirect Reference: ");
                                        sb15.append(dERExternal.getIndirectReference().toString());
                                        sb15.append(property);
                                        stringBuffer.append(sb15.toString());
                                    }
                                    if (dERExternal.getDataValueDescriptor() != null) {
                                        a(sb13, z, dERExternal.getDataValueDescriptor(), stringBuffer);
                                    }
                                    StringBuilder sb16 = new StringBuilder();
                                    sb16.append(sb13);
                                    sb16.append("Encoding: ");
                                    sb16.append(dERExternal.getEncoding());
                                    sb16.append(property);
                                    stringBuffer.append(sb16.toString());
                                    a(sb13, z, dERExternal.getExternalContent(), stringBuffer);
                                    return;
                                } else {
                                    sb = new StringBuilder();
                                    sb.append(str);
                                    obj = aSN1Primitive.toString();
                                    sb.append(obj);
                                    sb.append(property);
                                    str2 = sb.toString();
                                    stringBuffer.append(str2);
                                    return;
                                }
                                str2 = a(str3, str, z, aSN1Primitive, property);
                                stringBuffer.append(str2);
                                return;
                            }
                            sb.append(time);
                            obj = ") ";
                            sb.append(obj);
                            sb.append(property);
                            str2 = sb.toString();
                            stringBuffer.append(str2);
                            return;
                        }
                        sb2.append(value);
                    }
                    obj = ")";
                    sb.append(obj);
                    sb.append(property);
                    str2 = sb.toString();
                    stringBuffer.append(str2);
                    return;
                }
                str2 = a(str, bytes);
                stringBuffer.append(str2);
                return;
            }
            stringBuffer.append(property);
        }
    }

    public static String dumpAsString(Object obj) {
        return dumpAsString(obj, false);
    }

    public static String dumpAsString(Object obj, boolean z) {
        String str;
        ASN1Primitive aSN1Primitive;
        StringBuffer stringBuffer = new StringBuffer();
        if (obj instanceof ASN1Primitive) {
            str = "";
            aSN1Primitive = (ASN1Primitive) obj;
        } else if (obj instanceof ASN1Encodable) {
            str = "";
            aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("unknown object type ");
            sb.append(obj.toString());
            return sb.toString();
        }
        a(str, z, aSN1Primitive, stringBuffer);
        return stringBuffer.toString();
    }
}
