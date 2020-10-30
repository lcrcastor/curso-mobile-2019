package okio;

import javax.annotation.Nullable;

final class Segment {
    final byte[] a;
    int b;
    int c;
    boolean d;
    boolean e;
    Segment f;
    Segment g;

    Segment() {
        this.a = new byte[8192];
        this.e = true;
        this.d = false;
    }

    Segment(byte[] bArr, int i, int i2, boolean z, boolean z2) {
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.d = z;
        this.e = z2;
    }

    /* access modifiers changed from: 0000 */
    public Segment a() {
        this.d = true;
        Segment segment = new Segment(this.a, this.b, this.c, true, false);
        return segment;
    }

    /* access modifiers changed from: 0000 */
    public Segment b() {
        Segment segment = new Segment((byte[]) this.a.clone(), this.b, this.c, false, true);
        return segment;
    }

    @Nullable
    public Segment c() {
        Segment segment = this.f != this ? this.f : null;
        this.g.f = this.f;
        this.f.g = this.g;
        this.f = null;
        this.g = null;
        return segment;
    }

    public Segment a(Segment segment) {
        segment.g = this;
        segment.f = this.f;
        this.f.g = segment;
        this.f = segment;
        return segment;
    }

    public Segment a(int i) {
        Segment segment;
        if (i <= 0 || i > this.c - this.b) {
            throw new IllegalArgumentException();
        }
        if (i >= 1024) {
            segment = a();
        } else {
            segment = SegmentPool.a();
            System.arraycopy(this.a, this.b, segment.a, 0, i);
        }
        segment.c = segment.b + i;
        this.b += i;
        this.g.a(segment);
        return segment;
    }

    public void d() {
        if (this.g == this) {
            throw new IllegalStateException();
        } else if (this.g.e) {
            int i = this.c - this.b;
            if (i <= (8192 - this.g.c) + (this.g.d ? 0 : this.g.b)) {
                a(this.g, i);
                c();
                SegmentPool.a(this);
            }
        }
    }

    public void a(Segment segment, int i) {
        if (!segment.e) {
            throw new IllegalArgumentException();
        }
        if (segment.c + i > 8192) {
            if (segment.d) {
                throw new IllegalArgumentException();
            } else if ((segment.c + i) - segment.b > 8192) {
                throw new IllegalArgumentException();
            } else {
                System.arraycopy(segment.a, segment.b, segment.a, 0, segment.c - segment.b);
                segment.c -= segment.b;
                segment.b = 0;
            }
        }
        System.arraycopy(this.a, this.b, segment.a, segment.c, i);
        segment.c += i;
        this.b += i;
    }
}
