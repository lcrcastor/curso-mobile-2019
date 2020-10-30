package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

@GwtIncompatible
@Beta
public abstract class AbstractService implements Service {
    private static final Callback<Listener> a = new Callback<Listener>("starting()") {
        /* access modifiers changed from: 0000 */
        public void a(Listener listener) {
            listener.starting();
        }
    };
    private static final Callback<Listener> b = new Callback<Listener>("running()") {
        /* access modifiers changed from: 0000 */
        public void a(Listener listener) {
            listener.running();
        }
    };
    private static final Callback<Listener> c = b(State.STARTING);
    private static final Callback<Listener> d = b(State.RUNNING);
    private static final Callback<Listener> e = a(State.NEW);
    private static final Callback<Listener> f = a(State.RUNNING);
    private static final Callback<Listener> g = a(State.STOPPING);
    /* access modifiers changed from: private */
    public final Monitor h = new Monitor();
    private final Guard i = new IsStartableGuard();
    private final Guard j = new IsStoppableGuard();
    private final Guard k = new HasReachedRunningGuard();
    private final Guard l = new IsStoppedGuard();
    @GuardedBy("monitor")
    private final List<ListenerCallQueue<Listener>> m = Collections.synchronizedList(new ArrayList());
    @GuardedBy("monitor")
    private volatile StateSnapshot n = new StateSnapshot(State.NEW);

    @Immutable
    static final class StateSnapshot {
        final State a;
        final boolean b;
        @Nullable
        final Throwable c;

        StateSnapshot(State state) {
            this(state, false, null);
        }

        StateSnapshot(State state, boolean z, @Nullable Throwable th) {
            boolean z2 = true;
            Preconditions.checkArgument(!z || state == State.STARTING, "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", (Object) state);
            if ((th != null) ^ (state == State.FAILED)) {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", (Object) state, (Object) th);
            this.a = state;
            this.b = z;
            this.c = th;
        }

        /* access modifiers changed from: 0000 */
        public State a() {
            if (!this.b || this.a != State.STARTING) {
                return this.a;
            }
            return State.STOPPING;
        }

        /* access modifiers changed from: 0000 */
        public Throwable b() {
            Preconditions.checkState(this.a == State.FAILED, "failureCause() is only valid if the service has failed, service is %s", (Object) this.a);
            return this.c;
        }
    }

    final class HasReachedRunningGuard extends Guard {
        HasReachedRunningGuard() {
            super(AbstractService.this.h);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(State.RUNNING) >= 0;
        }
    }

    final class IsStartableGuard extends Guard {
        IsStartableGuard() {
            super(AbstractService.this.h);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state() == State.NEW;
        }
    }

    final class IsStoppableGuard extends Guard {
        IsStoppableGuard() {
            super(AbstractService.this.h);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(State.RUNNING) <= 0;
        }
    }

    final class IsStoppedGuard extends Guard {
        IsStoppedGuard() {
            super(AbstractService.this.h);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().a();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void doStart();

    /* access modifiers changed from: protected */
    public abstract void doStop();

    private static Callback<Listener> a(final State state) {
        StringBuilder sb = new StringBuilder();
        sb.append("terminated({from = ");
        sb.append(state);
        sb.append("})");
        return new Callback<Listener>(sb.toString()) {
            /* access modifiers changed from: 0000 */
            public void a(Listener listener) {
                listener.terminated(state);
            }
        };
    }

    private static Callback<Listener> b(final State state) {
        StringBuilder sb = new StringBuilder();
        sb.append("stopping({from = ");
        sb.append(state);
        sb.append("})");
        return new Callback<Listener>(sb.toString()) {
            /* access modifiers changed from: 0000 */
            public void a(Listener listener) {
                listener.stopping(state);
            }
        };
    }

    protected AbstractService() {
    }

    @CanIgnoreReturnValue
    public final Service startAsync() {
        if (this.h.enterIf(this.i)) {
            try {
                this.n = new StateSnapshot(State.STARTING);
                b();
                doStart();
            } catch (Throwable th) {
                this.h.leave();
                a();
                throw th;
            }
            this.h.leave();
            a();
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Service ");
        sb.append(this);
        sb.append(" has already been started");
        throw new IllegalStateException(sb.toString());
    }

    @CanIgnoreReturnValue
    public final Service stopAsync() {
        if (this.h.enterIf(this.j)) {
            try {
                State state = state();
                switch (state) {
                    case NEW:
                        this.n = new StateSnapshot(State.TERMINATED);
                        e(State.NEW);
                        break;
                    case STARTING:
                        this.n = new StateSnapshot(State.STARTING, true, null);
                        d(State.STARTING);
                        break;
                    case RUNNING:
                        this.n = new StateSnapshot(State.STOPPING);
                        d(State.RUNNING);
                        doStop();
                        break;
                    case STOPPING:
                    case TERMINATED:
                    case FAILED:
                        StringBuilder sb = new StringBuilder();
                        sb.append("isStoppable is incorrectly implemented, saw: ");
                        sb.append(state);
                        throw new AssertionError(sb.toString());
                    default:
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Unexpected state: ");
                        sb2.append(state);
                        throw new AssertionError(sb2.toString());
                }
            } catch (Throwable th) {
                this.h.leave();
                a();
                throw th;
            }
            this.h.leave();
            a();
        }
        return this;
    }

    public final void awaitRunning() {
        this.h.enterWhenUninterruptibly(this.k);
        try {
            c(State.RUNNING);
        } finally {
            this.h.leave();
        }
    }

    public final void awaitRunning(long j2, TimeUnit timeUnit) {
        if (this.h.enterWhenUninterruptibly(this.k, j2, timeUnit)) {
            try {
                c(State.RUNNING);
            } finally {
                this.h.leave();
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Timed out waiting for ");
            sb.append(this);
            sb.append(" to reach the RUNNING state.");
            throw new TimeoutException(sb.toString());
        }
    }

    public final void awaitTerminated() {
        this.h.enterWhenUninterruptibly(this.l);
        try {
            c(State.TERMINATED);
        } finally {
            this.h.leave();
        }
    }

    public final void awaitTerminated(long j2, TimeUnit timeUnit) {
        if (this.h.enterWhenUninterruptibly(this.l, j2, timeUnit)) {
            try {
                c(State.TERMINATED);
            } finally {
                this.h.leave();
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Timed out waiting for ");
            sb.append(this);
            sb.append(" to reach a terminal state. ");
            sb.append("Current state: ");
            sb.append(state());
            throw new TimeoutException(sb.toString());
        }
    }

    @GuardedBy("monitor")
    private void c(State state) {
        State state2 = state();
        if (state2 == state) {
            return;
        }
        if (state2 == State.FAILED) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected the service ");
            sb.append(this);
            sb.append(" to be ");
            sb.append(state);
            sb.append(", but the service has FAILED");
            throw new IllegalStateException(sb.toString(), failureCause());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Expected the service ");
        sb2.append(this);
        sb2.append(" to be ");
        sb2.append(state);
        sb2.append(", but was ");
        sb2.append(state2);
        throw new IllegalStateException(sb2.toString());
    }

    /* access modifiers changed from: protected */
    public final void notifyStarted() {
        this.h.enter();
        try {
            if (this.n.a != State.STARTING) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot notifyStarted() when the service is ");
                sb.append(this.n.a);
                IllegalStateException illegalStateException = new IllegalStateException(sb.toString());
                notifyFailed(illegalStateException);
                throw illegalStateException;
            }
            if (this.n.b) {
                this.n = new StateSnapshot(State.STOPPING);
                doStop();
            } else {
                this.n = new StateSnapshot(State.RUNNING);
                c();
            }
        } finally {
            this.h.leave();
            a();
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyStopped() {
        this.h.enter();
        try {
            State state = this.n.a;
            if (state == State.STOPPING || state == State.RUNNING) {
                this.n = new StateSnapshot(State.TERMINATED);
                e(state);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot notifyStopped() when the service is ");
            sb.append(state);
            IllegalStateException illegalStateException = new IllegalStateException(sb.toString());
            notifyFailed(illegalStateException);
            throw illegalStateException;
        } finally {
            this.h.leave();
            a();
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyFailed(Throwable th) {
        Preconditions.checkNotNull(th);
        this.h.enter();
        try {
            State state = state();
            switch (state) {
                case NEW:
                case TERMINATED:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed while in state:");
                    sb.append(state);
                    throw new IllegalStateException(sb.toString(), th);
                case STARTING:
                case RUNNING:
                case STOPPING:
                    this.n = new StateSnapshot(State.FAILED, false, th);
                    a(state, th);
                    break;
                case FAILED:
                    break;
                default:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unexpected state: ");
                    sb2.append(state);
                    throw new AssertionError(sb2.toString());
            }
        } finally {
            this.h.leave();
            a();
        }
    }

    public final boolean isRunning() {
        return state() == State.RUNNING;
    }

    public final State state() {
        return this.n.a();
    }

    public final Throwable failureCause() {
        return this.n.b();
    }

    public final void addListener(Listener listener, Executor executor) {
        Preconditions.checkNotNull(listener, "listener");
        Preconditions.checkNotNull(executor, "executor");
        this.h.enter();
        try {
            if (!state().a()) {
                this.m.add(new ListenerCallQueue(listener, executor));
            }
        } finally {
            this.h.leave();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(state());
        sb.append("]");
        return sb.toString();
    }

    private void a() {
        if (!this.h.isOccupiedByCurrentThread()) {
            for (int i2 = 0; i2 < this.m.size(); i2++) {
                ((ListenerCallQueue) this.m.get(i2)).a();
            }
        }
    }

    @GuardedBy("monitor")
    private void b() {
        a.a((Iterable<ListenerCallQueue<L>>) this.m);
    }

    @GuardedBy("monitor")
    private void c() {
        b.a((Iterable<ListenerCallQueue<L>>) this.m);
    }

    @GuardedBy("monitor")
    private void d(State state) {
        if (state == State.STARTING) {
            c.a((Iterable<ListenerCallQueue<L>>) this.m);
        } else if (state == State.RUNNING) {
            d.a((Iterable<ListenerCallQueue<L>>) this.m);
        } else {
            throw new AssertionError();
        }
    }

    @GuardedBy("monitor")
    private void e(State state) {
        int i2 = AnonymousClass6.a[state.ordinal()];
        if (i2 != 1) {
            switch (i2) {
                case 3:
                    f.a((Iterable<ListenerCallQueue<L>>) this.m);
                    return;
                case 4:
                    g.a((Iterable<ListenerCallQueue<L>>) this.m);
                    return;
                default:
                    throw new AssertionError();
            }
        } else {
            e.a((Iterable<ListenerCallQueue<L>>) this.m);
        }
    }

    @GuardedBy("monitor")
    private void a(final State state, final Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append("failed({from = ");
        sb.append(state);
        sb.append(", cause = ");
        sb.append(th);
        sb.append("})");
        new Callback<Listener>(sb.toString()) {
            /* access modifiers changed from: 0000 */
            public void a(Listener listener) {
                listener.failed(state, th);
            }
        }.a((Iterable<ListenerCallQueue<L>>) this.m);
    }
}
