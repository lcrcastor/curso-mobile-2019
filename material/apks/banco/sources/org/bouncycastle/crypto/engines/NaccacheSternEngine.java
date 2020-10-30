package org.bouncycastle.crypto.engines;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Vector;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.NaccacheSternKeyParameters;
import org.bouncycastle.crypto.params.NaccacheSternPrivateKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

public class NaccacheSternEngine implements AsymmetricBlockCipher {
    private static BigInteger e = BigInteger.valueOf(0);
    private static BigInteger f = BigInteger.valueOf(1);
    private boolean a;
    private NaccacheSternKeyParameters b;
    private Vector[] c = null;
    private boolean d = false;

    private static BigInteger a(Vector vector, Vector vector2) {
        BigInteger bigInteger = e;
        BigInteger bigInteger2 = f;
        for (int i = 0; i < vector2.size(); i++) {
            bigInteger2 = bigInteger2.multiply((BigInteger) vector2.elementAt(i));
        }
        for (int i2 = 0; i2 < vector2.size(); i2++) {
            BigInteger bigInteger3 = (BigInteger) vector2.elementAt(i2);
            BigInteger divide = bigInteger2.divide(bigInteger3);
            bigInteger = bigInteger.add(divide.multiply(divide.modInverse(bigInteger3)).multiply((BigInteger) vector.elementAt(i2)));
        }
        return bigInteger.mod(bigInteger2);
    }

    public byte[] addCryptedBlocks(byte[] bArr, byte[] bArr2) {
        if (this.a) {
            if (bArr.length > getOutputBlockSize() || bArr2.length > getOutputBlockSize()) {
                throw new InvalidCipherTextException("BlockLength too large for simple addition.\n");
            }
        } else if (bArr.length > getInputBlockSize() || bArr2.length > getInputBlockSize()) {
            throw new InvalidCipherTextException("BlockLength too large for simple addition.\n");
        }
        BigInteger bigInteger = new BigInteger(1, bArr);
        BigInteger bigInteger2 = new BigInteger(1, bArr2);
        BigInteger mod = bigInteger.multiply(bigInteger2).mod(this.b.getModulus());
        if (this.d) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("c(m1) as BigInteger:....... ");
            sb.append(bigInteger);
            printStream.println(sb.toString());
            PrintStream printStream2 = System.out;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("c(m2) as BigInteger:....... ");
            sb2.append(bigInteger2);
            printStream2.println(sb2.toString());
            PrintStream printStream3 = System.out;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("c(m1)*c(m2)%n = c(m1+m2)%n: ");
            sb3.append(mod);
            printStream3.println(sb3.toString());
        }
        byte[] byteArray = this.b.getModulus().toByteArray();
        Arrays.fill(byteArray, 0);
        System.arraycopy(mod.toByteArray(), 0, byteArray, byteArray.length - mod.toByteArray().length, mod.toByteArray().length);
        return byteArray;
    }

    public byte[] encrypt(BigInteger bigInteger) {
        byte[] byteArray = this.b.getModulus().toByteArray();
        Arrays.fill(byteArray, 0);
        byte[] byteArray2 = this.b.getG().modPow(bigInteger, this.b.getModulus()).toByteArray();
        System.arraycopy(byteArray2, 0, byteArray, byteArray.length - byteArray2.length, byteArray2.length);
        if (this.d) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("Encrypted value is:  ");
            sb.append(new BigInteger(byteArray));
            printStream.println(sb.toString());
        }
        return byteArray;
    }

    public int getInputBlockSize() {
        return this.a ? ((this.b.getLowerSigmaBound() + 7) / 8) - 1 : this.b.getModulus().toByteArray().length;
    }

    public int getOutputBlockSize() {
        return this.a ? this.b.getModulus().toByteArray().length : ((this.b.getLowerSigmaBound() + 7) / 8) - 1;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.a = z;
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        this.b = (NaccacheSternKeyParameters) cipherParameters;
        if (!this.a) {
            if (this.d) {
                System.out.println("Constructing lookup Array");
            }
            NaccacheSternPrivateKeyParameters naccacheSternPrivateKeyParameters = (NaccacheSternPrivateKeyParameters) this.b;
            Vector smallPrimes = naccacheSternPrivateKeyParameters.getSmallPrimes();
            this.c = new Vector[smallPrimes.size()];
            for (int i = 0; i < smallPrimes.size(); i++) {
                BigInteger bigInteger = (BigInteger) smallPrimes.elementAt(i);
                int intValue = bigInteger.intValue();
                this.c[i] = new Vector();
                this.c[i].addElement(f);
                if (this.d) {
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Constructing lookup ArrayList for ");
                    sb.append(intValue);
                    printStream.println(sb.toString());
                }
                BigInteger bigInteger2 = e;
                for (int i2 = 1; i2 < intValue; i2++) {
                    bigInteger2 = bigInteger2.add(naccacheSternPrivateKeyParameters.getPhi_n());
                    this.c[i].addElement(naccacheSternPrivateKeyParameters.getG().modPow(bigInteger2.divide(bigInteger), naccacheSternPrivateKeyParameters.getModulus()));
                }
            }
        }
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) {
        if (this.b == null) {
            throw new IllegalStateException("NaccacheStern engine not initialised");
        } else if (i2 > getInputBlockSize() + 1) {
            throw new DataLengthException("input too large for Naccache-Stern cipher.\n");
        } else if (this.a || i2 >= getInputBlockSize()) {
            if (!(i == 0 && i2 == bArr.length)) {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, i, bArr2, 0, i2);
                bArr = bArr2;
            }
            BigInteger bigInteger = new BigInteger(1, bArr);
            if (this.d) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("input as BigInteger: ");
                sb.append(bigInteger);
                printStream.println(sb.toString());
            }
            if (this.a) {
                return encrypt(bigInteger);
            }
            Vector vector = new Vector();
            NaccacheSternPrivateKeyParameters naccacheSternPrivateKeyParameters = (NaccacheSternPrivateKeyParameters) this.b;
            Vector smallPrimes = naccacheSternPrivateKeyParameters.getSmallPrimes();
            for (int i3 = 0; i3 < smallPrimes.size(); i3++) {
                BigInteger modPow = bigInteger.modPow(naccacheSternPrivateKeyParameters.getPhi_n().divide((BigInteger) smallPrimes.elementAt(i3)), naccacheSternPrivateKeyParameters.getModulus());
                Vector vector2 = this.c[i3];
                if (this.c[i3].size() != ((BigInteger) smallPrimes.elementAt(i3)).intValue()) {
                    if (this.d) {
                        PrintStream printStream2 = System.out;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Prime is ");
                        sb2.append(smallPrimes.elementAt(i3));
                        sb2.append(", lookup table has size ");
                        sb2.append(vector2.size());
                        printStream2.println(sb2.toString());
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Error in lookup Array for ");
                    sb3.append(((BigInteger) smallPrimes.elementAt(i3)).intValue());
                    sb3.append(": Size mismatch. Expected ArrayList with length ");
                    sb3.append(((BigInteger) smallPrimes.elementAt(i3)).intValue());
                    sb3.append(" but found ArrayList of length ");
                    sb3.append(this.c[i3].size());
                    throw new InvalidCipherTextException(sb3.toString());
                }
                int indexOf = vector2.indexOf(modPow);
                if (indexOf == -1) {
                    if (this.d) {
                        PrintStream printStream3 = System.out;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Actual prime is ");
                        sb4.append(smallPrimes.elementAt(i3));
                        printStream3.println(sb4.toString());
                        PrintStream printStream4 = System.out;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("Decrypted value is ");
                        sb5.append(modPow);
                        printStream4.println(sb5.toString());
                        PrintStream printStream5 = System.out;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("LookupList for ");
                        sb6.append(smallPrimes.elementAt(i3));
                        sb6.append(" with size ");
                        sb6.append(this.c[i3].size());
                        sb6.append(" is: ");
                        printStream5.println(sb6.toString());
                        for (int i4 = 0; i4 < this.c[i3].size(); i4++) {
                            System.out.println(this.c[i3].elementAt(i4));
                        }
                    }
                    throw new InvalidCipherTextException("Lookup failed");
                }
                vector.addElement(BigInteger.valueOf((long) indexOf));
            }
            return a(vector, smallPrimes).toByteArray();
        } else {
            throw new InvalidCipherTextException("BlockLength does not match modulus for Naccache-Stern cipher.\n");
        }
    }

    public byte[] processData(byte[] bArr) {
        byte[] bArr2;
        if (this.d) {
            System.out.println();
        }
        if (bArr.length > getInputBlockSize()) {
            int inputBlockSize = getInputBlockSize();
            int outputBlockSize = getOutputBlockSize();
            if (this.d) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("Input blocksize is:  ");
                sb.append(inputBlockSize);
                sb.append(" bytes");
                printStream.println(sb.toString());
                PrintStream printStream2 = System.out;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Output blocksize is: ");
                sb2.append(outputBlockSize);
                sb2.append(" bytes");
                printStream2.println(sb2.toString());
                PrintStream printStream3 = System.out;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Data has length:.... ");
                sb3.append(bArr.length);
                sb3.append(" bytes");
                printStream3.println(sb3.toString());
            }
            byte[] bArr3 = new byte[(((bArr.length / inputBlockSize) + 1) * outputBlockSize)];
            int i = 0;
            int i2 = 0;
            while (i < bArr.length) {
                int i3 = i + inputBlockSize;
                if (i3 < bArr.length) {
                    int i4 = i3;
                    bArr2 = processBlock(bArr, i, inputBlockSize);
                    i = i4;
                } else {
                    bArr2 = processBlock(bArr, i, bArr.length - i);
                    i += bArr.length - i;
                }
                if (this.d) {
                    PrintStream printStream4 = System.out;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("new datapos is ");
                    sb4.append(i);
                    printStream4.println(sb4.toString());
                }
                if (bArr2 != null) {
                    System.arraycopy(bArr2, 0, bArr3, i2, bArr2.length);
                    i2 += bArr2.length;
                } else {
                    if (this.d) {
                        System.out.println("cipher returned null");
                    }
                    throw new InvalidCipherTextException("cipher returned null");
                }
            }
            byte[] bArr4 = new byte[i2];
            System.arraycopy(bArr3, 0, bArr4, 0, i2);
            if (this.d) {
                PrintStream printStream5 = System.out;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("returning ");
                sb5.append(bArr4.length);
                sb5.append(" bytes");
                printStream5.println(sb5.toString());
            }
            return bArr4;
        }
        if (this.d) {
            System.out.println("data size is less then input block size, processing directly");
        }
        return processBlock(bArr, 0, bArr.length);
    }

    public void setDebug(boolean z) {
        this.d = z;
    }
}
