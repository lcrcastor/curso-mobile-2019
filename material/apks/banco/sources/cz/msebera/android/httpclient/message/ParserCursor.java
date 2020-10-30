package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;

@NotThreadSafe
public class ParserCursor {
    private final int a;
    private final int b;
    private int c;

    public ParserCursor(int i, int i2) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Lower bound cannot be negative");
        } else if (i > i2) {
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        } else {
            this.a = i;
            this.b = i2;
            this.c = i;
        }
    }

    public int getLowerBound() {
        return this.a;
    }

    public int getUpperBound() {
        return this.b;
    }

    public int getPos() {
        return this.c;
    }

    public void updatePos(int i) {
        if (i < this.a) {
            StringBuilder sb = new StringBuilder();
            sb.append("pos: ");
            sb.append(i);
            sb.append(" < lowerBound: ");
            sb.append(this.a);
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i > this.b) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("pos: ");
            sb2.append(i);
            sb2.append(" > upperBound: ");
            sb2.append(this.b);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else {
            this.c = i;
        }
    }

    public boolean atEnd() {
        return this.c >= this.b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(Integer.toString(this.a));
        sb.append('>');
        sb.append(Integer.toString(this.c));
        sb.append('>');
        sb.append(Integer.toString(this.b));
        sb.append(']');
        return sb.toString();
    }
}
