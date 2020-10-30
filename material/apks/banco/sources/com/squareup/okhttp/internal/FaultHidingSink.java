package com.squareup.okhttp.internal;

import java.io.IOException;
import okio.Buffer;
import okio.ForwardingSink;
import okio.Sink;

class FaultHidingSink extends ForwardingSink {
    private boolean a;

    /* access modifiers changed from: protected */
    public void a(IOException iOException) {
    }

    public FaultHidingSink(Sink sink) {
        super(sink);
    }

    public void write(Buffer buffer, long j) {
        if (this.a) {
            buffer.skip(j);
            return;
        }
        try {
            super.write(buffer, j);
        } catch (IOException e) {
            this.a = true;
            a(e);
        }
    }

    public void flush() {
        if (!this.a) {
            try {
                super.flush();
            } catch (IOException e) {
                this.a = true;
                a(e);
            }
        }
    }

    public void close() {
        if (!this.a) {
            try {
                super.close();
            } catch (IOException e) {
                this.a = true;
                a(e);
            }
        }
    }
}
