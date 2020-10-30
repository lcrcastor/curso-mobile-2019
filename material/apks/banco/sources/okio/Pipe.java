package okio;

import java.io.IOException;

public final class Pipe {
    final long a;
    final Buffer b = new Buffer();
    boolean c;
    boolean d;
    private final Sink e = new PipeSink();
    private final Source f = new PipeSource();

    final class PipeSink implements Sink {
        final Timeout a = new Timeout();

        PipeSink() {
        }

        public void write(Buffer buffer, long j) {
            synchronized (Pipe.this.b) {
                if (Pipe.this.c) {
                    throw new IllegalStateException("closed");
                }
                while (j > 0) {
                    if (Pipe.this.d) {
                        throw new IOException("source is closed");
                    }
                    long size = Pipe.this.a - Pipe.this.b.size();
                    if (size == 0) {
                        this.a.waitUntilNotified(Pipe.this.b);
                    } else {
                        long min = Math.min(size, j);
                        Pipe.this.b.write(buffer, min);
                        long j2 = j - min;
                        Pipe.this.b.notifyAll();
                        j = j2;
                    }
                }
            }
        }

        public void flush() {
            synchronized (Pipe.this.b) {
                if (Pipe.this.c) {
                    throw new IllegalStateException("closed");
                } else if (Pipe.this.d && Pipe.this.b.size() > 0) {
                    throw new IOException("source is closed");
                }
            }
        }

        public void close() {
            synchronized (Pipe.this.b) {
                if (!Pipe.this.c) {
                    if (!Pipe.this.d || Pipe.this.b.size() <= 0) {
                        Pipe.this.c = true;
                        Pipe.this.b.notifyAll();
                        return;
                    }
                    throw new IOException("source is closed");
                }
            }
        }

        public Timeout timeout() {
            return this.a;
        }
    }

    final class PipeSource implements Source {
        final Timeout a = new Timeout();

        PipeSource() {
        }

        public long read(Buffer buffer, long j) {
            synchronized (Pipe.this.b) {
                if (Pipe.this.d) {
                    throw new IllegalStateException("closed");
                }
                while (Pipe.this.b.size() == 0) {
                    if (Pipe.this.c) {
                        return -1;
                    }
                    this.a.waitUntilNotified(Pipe.this.b);
                }
                long read = Pipe.this.b.read(buffer, j);
                Pipe.this.b.notifyAll();
                return read;
            }
        }

        public void close() {
            synchronized (Pipe.this.b) {
                Pipe.this.d = true;
                Pipe.this.b.notifyAll();
            }
        }

        public Timeout timeout() {
            return this.a;
        }
    }

    public Pipe(long j) {
        if (j < 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxBufferSize < 1: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = j;
    }

    public Source source() {
        return this.f;
    }

    public Sink sink() {
        return this.e;
    }
}
