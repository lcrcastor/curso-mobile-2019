package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSetMultimap.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.State;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible
@Beta
public final class ServiceManager {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger(ServiceManager.class.getName());
    /* access modifiers changed from: private */
    public static final Callback<Listener> b = new Callback<Listener>("healthy()") {
        /* access modifiers changed from: 0000 */
        public void a(Listener listener) {
            listener.healthy();
        }
    };
    /* access modifiers changed from: private */
    public static final Callback<Listener> c = new Callback<Listener>("stopped()") {
        /* access modifiers changed from: 0000 */
        public void a(Listener listener) {
            listener.stopped();
        }
    };
    private final ServiceManagerState d;
    private final ImmutableList<Service> e;

    static final class EmptyServiceManagerWarning extends Throwable {
        private EmptyServiceManagerWarning() {
        }
    }

    @Beta
    public static abstract class Listener {
        public void failure(Service service) {
        }

        public void healthy() {
        }

        public void stopped() {
        }
    }

    static final class NoOpService extends AbstractService {
        private NoOpService() {
        }

        /* access modifiers changed from: protected */
        public void doStart() {
            notifyStarted();
        }

        /* access modifiers changed from: protected */
        public void doStop() {
            notifyStopped();
        }
    }

    static final class ServiceListener extends com.google.common.util.concurrent.Service.Listener {
        final Service a;
        final WeakReference<ServiceManagerState> b;

        ServiceListener(Service service, WeakReference<ServiceManagerState> weakReference) {
            this.a = service;
            this.b = weakReference;
        }

        public void starting() {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.b.get();
            if (serviceManagerState != null) {
                serviceManagerState.a(this.a, State.NEW, State.STARTING);
                if (!(this.a instanceof NoOpService)) {
                    ServiceManager.a.log(Level.FINE, "Starting {0}.", this.a);
                }
            }
        }

        public void running() {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.b.get();
            if (serviceManagerState != null) {
                serviceManagerState.a(this.a, State.STARTING, State.RUNNING);
            }
        }

        public void stopping(State state) {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.b.get();
            if (serviceManagerState != null) {
                serviceManagerState.a(this.a, state, State.STOPPING);
            }
        }

        public void terminated(State state) {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.b.get();
            if (serviceManagerState != null) {
                if (!(this.a instanceof NoOpService)) {
                    ServiceManager.a.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.a, state});
                }
                serviceManagerState.a(this.a, state, State.TERMINATED);
            }
        }

        public void failed(State state, Throwable th) {
            ServiceManagerState serviceManagerState = (ServiceManagerState) this.b.get();
            if (serviceManagerState != null) {
                if (!(this.a instanceof NoOpService)) {
                    Logger a2 = ServiceManager.a;
                    Level level = Level.SEVERE;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Service ");
                    sb.append(this.a);
                    sb.append(" has failed in the ");
                    sb.append(state);
                    sb.append(" state.");
                    a2.log(level, sb.toString(), th);
                }
                serviceManagerState.a(this.a, state, State.FAILED);
            }
        }
    }

    static final class ServiceManagerState {
        final Monitor a = new Monitor();
        @GuardedBy("monitor")
        final SetMultimap<State, Service> b = MultimapBuilder.enumKeys(State.class).linkedHashSetValues().build();
        @GuardedBy("monitor")
        final Multiset<State> c = this.b.keys();
        @GuardedBy("monitor")
        final Map<Service, Stopwatch> d = Maps.newIdentityHashMap();
        @GuardedBy("monitor")
        boolean e;
        @GuardedBy("monitor")
        boolean f;
        final int g;
        final Guard h = new AwaitHealthGuard();
        final Guard i = new StoppedGuard();
        @GuardedBy("monitor")
        final List<ListenerCallQueue<Listener>> j = Collections.synchronizedList(new ArrayList());

        final class AwaitHealthGuard extends Guard {
            AwaitHealthGuard() {
                super(ServiceManagerState.this.a);
            }

            public boolean isSatisfied() {
                return ServiceManagerState.this.c.count(State.RUNNING) == ServiceManagerState.this.g || ServiceManagerState.this.c.contains(State.STOPPING) || ServiceManagerState.this.c.contains(State.TERMINATED) || ServiceManagerState.this.c.contains(State.FAILED);
            }
        }

        final class StoppedGuard extends Guard {
            StoppedGuard() {
                super(ServiceManagerState.this.a);
            }

            public boolean isSatisfied() {
                return ServiceManagerState.this.c.count(State.TERMINATED) + ServiceManagerState.this.c.count(State.FAILED) == ServiceManagerState.this.g;
            }
        }

        ServiceManagerState(ImmutableCollection<Service> immutableCollection) {
            this.g = immutableCollection.size();
            this.b.putAll(State.NEW, immutableCollection);
        }

        /* access modifiers changed from: 0000 */
        public void a(Service service) {
            this.a.enter();
            try {
                if (((Stopwatch) this.d.get(service)) == null) {
                    this.d.put(service, Stopwatch.createStarted());
                }
            } finally {
                this.a.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.a.enter();
            try {
                if (!this.f) {
                    this.e = true;
                    return;
                }
                ArrayList newArrayList = Lists.newArrayList();
                Iterator it = d().values().iterator();
                while (it.hasNext()) {
                    Service service = (Service) it.next();
                    if (service.state() != State.NEW) {
                        newArrayList.add(service);
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Services started transitioning asynchronously before the ServiceManager was constructed: ");
                sb.append(newArrayList);
                throw new IllegalArgumentException(sb.toString());
            } finally {
                this.a.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Listener listener, Executor executor) {
            Preconditions.checkNotNull(listener, "listener");
            Preconditions.checkNotNull(executor, "executor");
            this.a.enter();
            try {
                if (!this.i.isSatisfied()) {
                    this.j.add(new ListenerCallQueue(listener, executor));
                }
            } finally {
                this.a.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            this.a.enterWhenUninterruptibly(this.h);
            try {
                i();
            } finally {
                this.a.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(long j2, TimeUnit timeUnit) {
            this.a.enter();
            try {
                if (!this.a.waitForUninterruptibly(this.h, j2, timeUnit)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Timeout waiting for the services to become healthy. The following services have not started: ");
                    sb.append(Multimaps.filterKeys(this.b, Predicates.in(ImmutableSet.of(State.NEW, State.STARTING))));
                    throw new TimeoutException(sb.toString());
                }
                i();
            } finally {
                this.a.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            this.a.enterWhenUninterruptibly(this.i);
            this.a.leave();
        }

        /* access modifiers changed from: 0000 */
        public void b(long j2, TimeUnit timeUnit) {
            this.a.enter();
            try {
                if (!this.a.waitForUninterruptibly(this.i, j2, timeUnit)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Timeout waiting for the services to stop. The following services have not stopped: ");
                    sb.append(Multimaps.filterKeys(this.b, Predicates.not(Predicates.in(EnumSet.of(State.TERMINATED, State.FAILED)))));
                    throw new TimeoutException(sb.toString());
                }
            } finally {
                this.a.leave();
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: 0000 */
        public ImmutableMultimap<State, Service> d() {
            Builder builder = ImmutableSetMultimap.builder();
            this.a.enter();
            try {
                for (Entry entry : this.b.entries()) {
                    if (!(entry.getValue() instanceof NoOpService)) {
                        builder.put(entry);
                    }
                }
                this.a.leave();
                return builder.build();
            } catch (Throwable th) {
                this.a.leave();
                throw th;
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: 0000 */
        public ImmutableMap<Service, Long> e() {
            this.a.enter();
            try {
                ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(this.d.size());
                for (Entry entry : this.d.entrySet()) {
                    Service service = (Service) entry.getKey();
                    Stopwatch stopwatch = (Stopwatch) entry.getValue();
                    if (!stopwatch.isRunning() && !(service instanceof NoOpService)) {
                        newArrayListWithCapacity.add(Maps.immutableEntry(service, Long.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS))));
                    }
                }
                this.a.leave();
                Collections.sort(newArrayListWithCapacity, Ordering.natural().onResultOf(new Function<Entry<Service, Long>, Long>() {
                    /* renamed from: a */
                    public Long apply(Entry<Service, Long> entry) {
                        return (Long) entry.getValue();
                    }
                }));
                return ImmutableMap.copyOf((Iterable<? extends Entry<? extends K, ? extends V>>) newArrayListWithCapacity);
            } catch (Throwable th) {
                this.a.leave();
                throw th;
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Service service, State state, State state2) {
            Preconditions.checkNotNull(service);
            Preconditions.checkArgument(state != state2);
            this.a.enter();
            try {
                this.f = true;
                if (this.e) {
                    Preconditions.checkState(this.b.remove(state, service), "Service %s not at the expected location in the state map %s", (Object) service, (Object) state);
                    Preconditions.checkState(this.b.put(state2, service), "Service %s in the state map unexpectedly at %s", (Object) service, (Object) state2);
                    Stopwatch stopwatch = (Stopwatch) this.d.get(service);
                    if (stopwatch == null) {
                        stopwatch = Stopwatch.createStarted();
                        this.d.put(service, stopwatch);
                    }
                    if (state2.compareTo(State.RUNNING) >= 0 && stopwatch.isRunning()) {
                        stopwatch.stop();
                        if (!(service instanceof NoOpService)) {
                            ServiceManager.a.log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatch});
                        }
                    }
                    if (state2 == State.FAILED) {
                        b(service);
                    }
                    if (this.c.count(State.RUNNING) == this.g) {
                        g();
                    } else if (this.c.count(State.TERMINATED) + this.c.count(State.FAILED) == this.g) {
                        f();
                    }
                    this.a.leave();
                    h();
                }
            } finally {
                this.a.leave();
                h();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("monitor")
        public void f() {
            ServiceManager.c.a((Iterable<ListenerCallQueue<L>>) this.j);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("monitor")
        public void g() {
            ServiceManager.b.a((Iterable<ListenerCallQueue<L>>) this.j);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("monitor")
        public void b(final Service service) {
            StringBuilder sb = new StringBuilder();
            sb.append("failed({service=");
            sb.append(service);
            sb.append("})");
            new Callback<Listener>(sb.toString()) {
                /* access modifiers changed from: 0000 */
                public void a(Listener listener) {
                    listener.failure(service);
                }
            }.a((Iterable<ListenerCallQueue<L>>) this.j);
        }

        /* access modifiers changed from: 0000 */
        public void h() {
            Preconditions.checkState(!this.a.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
            for (int i2 = 0; i2 < this.j.size(); i2++) {
                ((ListenerCallQueue) this.j.get(i2)).a();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("monitor")
        public void i() {
            if (this.c.count(State.RUNNING) != this.g) {
                StringBuilder sb = new StringBuilder();
                sb.append("Expected to be healthy after starting. The following services are not running: ");
                sb.append(Multimaps.filterKeys(this.b, Predicates.not(Predicates.equalTo(State.RUNNING))));
                throw new IllegalStateException(sb.toString());
            }
        }
    }

    public ServiceManager(Iterable<? extends Service> iterable) {
        ImmutableList<Service> copyOf = ImmutableList.copyOf(iterable);
        if (copyOf.isEmpty()) {
            a.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new EmptyServiceManagerWarning());
            copyOf = ImmutableList.of(new NoOpService());
        }
        this.d = new ServiceManagerState(copyOf);
        this.e = copyOf;
        WeakReference weakReference = new WeakReference(this.d);
        Iterator it = copyOf.iterator();
        while (it.hasNext()) {
            Service service = (Service) it.next();
            service.addListener(new ServiceListener(service, weakReference), MoreExecutors.directExecutor());
            Preconditions.checkArgument(service.state() == State.NEW, "Can only manage NEW services, %s", (Object) service);
        }
        this.d.a();
    }

    public void addListener(Listener listener, Executor executor) {
        this.d.a(listener, executor);
    }

    public void addListener(Listener listener) {
        this.d.a(listener, MoreExecutors.directExecutor());
    }

    @CanIgnoreReturnValue
    public ServiceManager startAsync() {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            Service service = (Service) it.next();
            State state = service.state();
            Preconditions.checkState(state == State.NEW, "Service %s is %s, cannot start it.", (Object) service, (Object) state);
        }
        Iterator it2 = this.e.iterator();
        while (it2.hasNext()) {
            Service service2 = (Service) it2.next();
            try {
                this.d.a(service2);
                service2.startAsync();
            } catch (IllegalStateException e2) {
                Logger logger = a;
                Level level = Level.WARNING;
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to start Service ");
                sb.append(service2);
                logger.log(level, sb.toString(), e2);
            }
        }
        return this;
    }

    public void awaitHealthy() {
        this.d.b();
    }

    public void awaitHealthy(long j, TimeUnit timeUnit) {
        this.d.a(j, timeUnit);
    }

    @CanIgnoreReturnValue
    public ServiceManager stopAsync() {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            ((Service) it.next()).stopAsync();
        }
        return this;
    }

    public void awaitStopped() {
        this.d.c();
    }

    public void awaitStopped(long j, TimeUnit timeUnit) {
        this.d.b(j, timeUnit);
    }

    public boolean isHealthy() {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            if (!((Service) it.next()).isRunning()) {
                return false;
            }
        }
        return true;
    }

    public ImmutableMultimap<State, Service> servicesByState() {
        return this.d.d();
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.d.e();
    }

    public String toString() {
        return MoreObjects.toStringHelper(ServiceManager.class).add("services", (Object) Collections2.filter(this.e, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }
}
