package org.bouncycastle.crypto.generators;

import com.appsflyer.BuildConfig;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import cz.msebera.android.httpclient.HttpStatus;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.NaccacheSternKeyGenerationParameters;
import org.bouncycastle.crypto.params.NaccacheSternKeyParameters;
import org.bouncycastle.crypto.params.NaccacheSternPrivateKeyParameters;
import org.bouncycastle.crypto.tls.CipherSuite;

public class NaccacheSternKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static int[] a = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, CertificateBody.profileType, 131, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, BuildConfig.AF_BUILD_VERSION, 389, 397, HttpStatus.SC_UNAUTHORIZED, HttpStatus.SC_CONFLICT, HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, HttpStatus.SC_SERVICE_UNAVAILABLE, 509, 521, 523, 541, 547, 557};
    private static final BigInteger c = BigInteger.valueOf(1);
    private NaccacheSternKeyGenerationParameters b;

    private static int a(SecureRandom secureRandom, int i) {
        int nextInt;
        int i2;
        if (((-i) & i) == i) {
            return (int) ((((long) i) * ((long) (secureRandom.nextInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO))) >> 31);
        }
        do {
            nextInt = secureRandom.nextInt() & SubsamplingScaleImageView.TILE_SIZE_AUTO;
            i2 = nextInt % i;
        } while ((nextInt - i2) + (i - 1) < 0);
        return i2;
    }

    private static BigInteger a(int i, int i2, SecureRandom secureRandom) {
        BigInteger bigInteger = new BigInteger(i, i2, secureRandom);
        while (bigInteger.bitLength() != i) {
            bigInteger = new BigInteger(i, i2, secureRandom);
        }
        return bigInteger;
    }

    private static Vector a(int i) {
        Vector vector = new Vector(i);
        for (int i2 = 0; i2 != i; i2++) {
            vector.addElement(BigInteger.valueOf((long) a[i2]));
        }
        return vector;
    }

    private static Vector a(Vector vector, SecureRandom secureRandom) {
        Vector vector2 = new Vector();
        Vector vector3 = new Vector();
        for (int i = 0; i < vector.size(); i++) {
            vector3.addElement(vector.elementAt(i));
        }
        vector2.addElement(vector3.elementAt(0));
        while (true) {
            vector3.removeElementAt(0);
            if (vector3.size() == 0) {
                return vector2;
            }
            vector2.insertElementAt(vector3.elementAt(0), a(secureRandom, vector2.size() + 1));
        }
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        long j;
        BigInteger a2;
        BigInteger add;
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger a3;
        BigInteger add2;
        BigInteger bigInteger3;
        BigInteger bigInteger4;
        BigInteger bigInteger5;
        long j2;
        BigInteger bigInteger6;
        boolean z;
        BigInteger bigInteger7;
        PrintStream printStream;
        StringBuilder sb;
        String str;
        long j3;
        BigInteger bigInteger8;
        int i;
        int strength = this.b.getStrength();
        SecureRandom random = this.b.getRandom();
        int certainty = this.b.getCertainty();
        boolean isDebug = this.b.isDebug();
        if (isDebug) {
            PrintStream printStream2 = System.out;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Fetching first ");
            sb2.append(this.b.getCntSmallPrimes());
            sb2.append(" primes.");
            printStream2.println(sb2.toString());
        }
        Vector a4 = a(a(this.b.getCntSmallPrimes()), random);
        BigInteger bigInteger9 = c;
        BigInteger bigInteger10 = c;
        BigInteger bigInteger11 = bigInteger9;
        for (int i2 = 0; i2 < a4.size() / 2; i2++) {
            bigInteger11 = bigInteger11.multiply((BigInteger) a4.elementAt(i2));
        }
        for (int size = a4.size() / 2; size < a4.size(); size++) {
            bigInteger10 = bigInteger10.multiply((BigInteger) a4.elementAt(size));
        }
        BigInteger multiply = bigInteger11.multiply(bigInteger10);
        int bitLength = (((strength - multiply.bitLength()) - 48) / 2) + 1;
        BigInteger a5 = a(bitLength, certainty, random);
        BigInteger a6 = a(bitLength, certainty, random);
        if (isDebug) {
            System.out.println("generating p and q");
        }
        BigInteger shiftLeft = a5.multiply(bigInteger11).shiftLeft(1);
        BigInteger shiftLeft2 = a6.multiply(bigInteger10).shiftLeft(1);
        long j4 = 0;
        while (true) {
            j = j4 + 1;
            a2 = a(24, certainty, random);
            add = a2.multiply(shiftLeft).add(c);
            if (!add.isProbablePrime(certainty)) {
                bigInteger2 = shiftLeft2;
                bigInteger = shiftLeft;
            } else {
                while (true) {
                    do {
                        a3 = a(24, certainty, random);
                    } while (a2.equals(a3));
                    bigInteger2 = shiftLeft2;
                    add2 = a3.multiply(shiftLeft2).add(c);
                    if (add2.isProbablePrime(certainty)) {
                        break;
                    }
                    int i3 = strength;
                    SecureRandom secureRandom = random;
                    shiftLeft2 = bigInteger2;
                }
                bigInteger = shiftLeft;
                if (multiply.gcd(a2.multiply(a3)).equals(c)) {
                    if (add.multiply(add2).bitLength() >= strength) {
                        break;
                    } else if (isDebug) {
                        PrintStream printStream3 = System.out;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("key size too small. Should be ");
                        sb3.append(strength);
                        sb3.append(" but is actually ");
                        sb3.append(add.multiply(add2).bitLength());
                        printStream3.println(sb3.toString());
                    }
                } else {
                    continue;
                }
            }
            j4 = j;
            shiftLeft2 = bigInteger2;
            shiftLeft = bigInteger;
        }
        if (isDebug) {
            PrintStream printStream4 = System.out;
            StringBuilder sb4 = new StringBuilder();
            bigInteger3 = a6;
            sb4.append("needed ");
            sb4.append(j);
            sb4.append(" tries to generate p and q.");
            printStream4.println(sb4.toString());
        } else {
            bigInteger3 = a6;
        }
        BigInteger multiply2 = add.multiply(add2);
        BigInteger multiply3 = add.subtract(c).multiply(add2.subtract(c));
        if (isDebug) {
            System.out.println("generating g");
        }
        long j5 = 0;
        while (true) {
            Vector vector = new Vector();
            bigInteger4 = add2;
            bigInteger5 = add;
            j2 = j5;
            int i4 = 0;
            while (i4 != a4.size()) {
                BigInteger divide = multiply3.divide((BigInteger) a4.elementAt(i4));
                while (true) {
                    j3 = j2 + 1;
                    bigInteger8 = new BigInteger(strength, certainty, random);
                    i = strength;
                    if (!bigInteger8.modPow(divide, multiply2).equals(c)) {
                        break;
                    }
                    j2 = j3;
                    strength = i;
                }
                vector.addElement(bigInteger8);
                i4++;
                j2 = j3;
                strength = i;
            }
            int i5 = strength;
            bigInteger6 = c;
            int i6 = 0;
            while (i6 < a4.size()) {
                SecureRandom secureRandom2 = random;
                bigInteger6 = bigInteger6.multiply(((BigInteger) vector.elementAt(i6)).modPow(multiply.divide((BigInteger) a4.elementAt(i6)), multiply2)).mod(multiply2);
                i6++;
                random = secureRandom2;
            }
            SecureRandom secureRandom3 = random;
            int i7 = 0;
            while (true) {
                if (i7 >= a4.size()) {
                    z = false;
                    break;
                } else if (bigInteger6.modPow(multiply3.divide((BigInteger) a4.elementAt(i7)), multiply2).equals(c)) {
                    if (isDebug) {
                        PrintStream printStream5 = System.out;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("g has order phi(n)/");
                        sb5.append(a4.elementAt(i7));
                        sb5.append("\n g: ");
                        sb5.append(bigInteger6);
                        printStream5.println(sb5.toString());
                    }
                    z = true;
                } else {
                    i7++;
                }
            }
            if (!z) {
                if (!bigInteger6.modPow(multiply3.divide(BigInteger.valueOf(4)), multiply2).equals(c)) {
                    if (!bigInteger6.modPow(multiply3.divide(a2), multiply2).equals(c)) {
                        if (!bigInteger6.modPow(multiply3.divide(a3), multiply2).equals(c)) {
                            if (!bigInteger6.modPow(multiply3.divide(a5), multiply2).equals(c)) {
                                bigInteger7 = bigInteger3;
                                if (!bigInteger6.modPow(multiply3.divide(bigInteger7), multiply2).equals(c)) {
                                    break;
                                }
                                if (isDebug) {
                                    PrintStream printStream6 = System.out;
                                    StringBuilder sb6 = new StringBuilder();
                                    sb6.append("g has order phi(n)/b\n g: ");
                                    sb6.append(bigInteger6);
                                    printStream6.println(sb6.toString());
                                }
                                bigInteger3 = bigInteger7;
                                j5 = j2;
                                add = bigInteger5;
                                add2 = bigInteger4;
                                strength = i5;
                                random = secureRandom3;
                            } else if (isDebug) {
                                printStream = System.out;
                                sb = new StringBuilder();
                                str = "g has order phi(n)/a\n g: ";
                            }
                        } else if (isDebug) {
                            printStream = System.out;
                            sb = new StringBuilder();
                            str = "g has order phi(n)/q'\n g: ";
                        }
                    } else if (isDebug) {
                        printStream = System.out;
                        sb = new StringBuilder();
                        str = "g has order phi(n)/p'\n g: ";
                    }
                } else if (isDebug) {
                    printStream = System.out;
                    sb = new StringBuilder();
                    str = "g has order phi(n)/4\n g:";
                }
                sb.append(str);
                sb.append(bigInteger6);
                printStream.println(sb.toString());
            }
            bigInteger7 = bigInteger3;
            bigInteger3 = bigInteger7;
            j5 = j2;
            add = bigInteger5;
            add2 = bigInteger4;
            strength = i5;
            random = secureRandom3;
        }
        if (isDebug) {
            PrintStream printStream7 = System.out;
            StringBuilder sb7 = new StringBuilder();
            sb7.append("needed ");
            sb7.append(j2);
            sb7.append(" tries to generate g");
            printStream7.println(sb7.toString());
            System.out.println();
            System.out.println("found new NaccacheStern cipher variables:");
            PrintStream printStream8 = System.out;
            StringBuilder sb8 = new StringBuilder();
            sb8.append("smallPrimes: ");
            sb8.append(a4);
            printStream8.println(sb8.toString());
            PrintStream printStream9 = System.out;
            StringBuilder sb9 = new StringBuilder();
            sb9.append("sigma:...... ");
            sb9.append(multiply);
            sb9.append(" (");
            sb9.append(multiply.bitLength());
            sb9.append(" bits)");
            printStream9.println(sb9.toString());
            PrintStream printStream10 = System.out;
            StringBuilder sb10 = new StringBuilder();
            sb10.append("a:.......... ");
            sb10.append(a5);
            printStream10.println(sb10.toString());
            PrintStream printStream11 = System.out;
            StringBuilder sb11 = new StringBuilder();
            sb11.append("b:.......... ");
            sb11.append(bigInteger7);
            printStream11.println(sb11.toString());
            PrintStream printStream12 = System.out;
            StringBuilder sb12 = new StringBuilder();
            sb12.append("p':......... ");
            sb12.append(a2);
            printStream12.println(sb12.toString());
            PrintStream printStream13 = System.out;
            StringBuilder sb13 = new StringBuilder();
            sb13.append("q':......... ");
            sb13.append(a3);
            printStream13.println(sb13.toString());
            PrintStream printStream14 = System.out;
            StringBuilder sb14 = new StringBuilder();
            sb14.append("p:.......... ");
            sb14.append(bigInteger5);
            printStream14.println(sb14.toString());
            PrintStream printStream15 = System.out;
            StringBuilder sb15 = new StringBuilder();
            sb15.append("q:.......... ");
            sb15.append(bigInteger4);
            printStream15.println(sb15.toString());
            PrintStream printStream16 = System.out;
            StringBuilder sb16 = new StringBuilder();
            sb16.append("n:.......... ");
            sb16.append(multiply2);
            printStream16.println(sb16.toString());
            PrintStream printStream17 = System.out;
            StringBuilder sb17 = new StringBuilder();
            sb17.append("phi(n):..... ");
            sb17.append(multiply3);
            printStream17.println(sb17.toString());
            PrintStream printStream18 = System.out;
            StringBuilder sb18 = new StringBuilder();
            sb18.append("g:.......... ");
            sb18.append(bigInteger6);
            printStream18.println(sb18.toString());
            System.out.println();
        }
        NaccacheSternKeyParameters naccacheSternKeyParameters = new NaccacheSternKeyParameters(false, bigInteger6, multiply2, multiply.bitLength());
        NaccacheSternPrivateKeyParameters naccacheSternPrivateKeyParameters = new NaccacheSternPrivateKeyParameters(bigInteger6, multiply2, multiply.bitLength(), a4, multiply3);
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) naccacheSternKeyParameters, (AsymmetricKeyParameter) naccacheSternPrivateKeyParameters);
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.b = (NaccacheSternKeyGenerationParameters) keyGenerationParameters;
    }
}
