package org.bouncycastle.crypto.tls;

import java.util.Vector;

class DTLSReassembler {
    private final short a;
    private final byte[] b;
    private Vector c = new Vector();

    static class Range {
        private int a;
        private int b;

        Range(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public int a() {
            return this.a;
        }

        public void a(int i) {
            this.a = i;
        }

        public int b() {
            return this.b;
        }

        public void b(int i) {
            this.b = i;
        }
    }

    DTLSReassembler(short s, int i) {
        this.a = s;
        this.b = new byte[i];
        this.c.addElement(new Range(0, i));
    }

    /* access modifiers changed from: 0000 */
    public short a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public void a(short s, int i, byte[] bArr, int i2, int i3, int i4) {
        int i5 = i3 + i4;
        if (this.a == s && this.b.length == i && i5 <= i) {
            int i6 = 0;
            if (i4 == 0) {
                if (i3 == 0 && !this.c.isEmpty() && ((Range) this.c.firstElement()).b() == 0) {
                    this.c.removeElementAt(0);
                }
                return;
            }
            while (i6 < this.c.size()) {
                Range range = (Range) this.c.elementAt(i6);
                if (range.a() < i5) {
                    if (range.b() > i3) {
                        int max = Math.max(range.a(), i3);
                        int min = Math.min(range.b(), i5);
                        System.arraycopy(bArr, (i2 + max) - i3, this.b, max, min - max);
                        if (max != range.a()) {
                            if (min != range.b()) {
                                i6++;
                                this.c.insertElementAt(new Range(min, range.b()), i6);
                            }
                            range.b(max);
                        } else if (min == range.b()) {
                            int i7 = i6 - 1;
                            this.c.removeElementAt(i6);
                            i6 = i7;
                        } else {
                            range.a(min);
                        }
                    }
                    i6++;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public byte[] b() {
        if (this.c.isEmpty()) {
            return this.b;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.c.removeAllElements();
        this.c.addElement(new Range(0, this.b.length));
    }
}
