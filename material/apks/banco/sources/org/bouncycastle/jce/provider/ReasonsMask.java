package org.bouncycastle.jce.provider;

import org.bouncycastle.asn1.x509.ReasonFlags;

class ReasonsMask {
    static final ReasonsMask a = new ReasonsMask(33023);
    private int b;

    ReasonsMask() {
        this(0);
    }

    private ReasonsMask(int i) {
        this.b = i;
    }

    ReasonsMask(ReasonFlags reasonFlags) {
        this.b = reasonFlags.intValue();
    }

    /* access modifiers changed from: 0000 */
    public void a(ReasonsMask reasonsMask) {
        this.b = reasonsMask.b() | this.b;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.b == a.b;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public ReasonsMask b(ReasonsMask reasonsMask) {
        ReasonsMask reasonsMask2 = new ReasonsMask();
        reasonsMask2.a(new ReasonsMask(reasonsMask.b() & this.b));
        return reasonsMask2;
    }

    /* access modifiers changed from: 0000 */
    public boolean c(ReasonsMask reasonsMask) {
        return ((reasonsMask.b() ^ this.b) | this.b) != 0;
    }
}
