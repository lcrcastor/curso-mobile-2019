package okio;

public abstract class ForwardingSink implements Sink {
    private final Sink a;

    public ForwardingSink(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.a = sink;
    }

    public final Sink delegate() {
        return this.a;
    }

    public void write(Buffer buffer, long j) {
        this.a.write(buffer, j);
    }

    public void flush() {
        this.a.flush();
    }

    public Timeout timeout() {
        return this.a.timeout();
    }

    public void close() {
        this.a.close();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("(");
        sb.append(this.a.toString());
        sb.append(")");
        return sb.toString();
    }
}
