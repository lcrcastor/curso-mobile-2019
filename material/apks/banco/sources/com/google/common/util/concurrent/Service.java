package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@Beta
public interface Service {

    @Beta
    public static abstract class Listener {
        public void failed(State state, Throwable th) {
        }

        public void running() {
        }

        public void starting() {
        }

        public void stopping(State state) {
        }

        public void terminated(State state) {
        }
    }

    @Beta
    public enum State {
        NEW {
            /* access modifiers changed from: 0000 */
            public boolean a() {
                return false;
            }
        },
        STARTING {
            /* access modifiers changed from: 0000 */
            public boolean a() {
                return false;
            }
        },
        RUNNING {
            /* access modifiers changed from: 0000 */
            public boolean a() {
                return false;
            }
        },
        STOPPING {
            /* access modifiers changed from: 0000 */
            public boolean a() {
                return false;
            }
        },
        TERMINATED {
            /* access modifiers changed from: 0000 */
            public boolean a() {
                return true;
            }
        },
        FAILED {
            /* access modifiers changed from: 0000 */
            public boolean a() {
                return true;
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract boolean a();
    }

    void addListener(Listener listener, Executor executor);

    void awaitRunning();

    void awaitRunning(long j, TimeUnit timeUnit);

    void awaitTerminated();

    void awaitTerminated(long j, TimeUnit timeUnit);

    Throwable failureCause();

    boolean isRunning();

    @CanIgnoreReturnValue
    Service startAsync();

    State state();

    @CanIgnoreReturnValue
    Service stopAsync();
}
