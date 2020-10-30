package okio;

public abstract class ForwardingSource implements Source {
    private final Source a;

    public ForwardingSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.a = source;
    }

    public final Source delegate() {
        return this.a;
    }

    public long read(Buffer buffer, long j) {
        return this.a.read(buffer, j);
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
