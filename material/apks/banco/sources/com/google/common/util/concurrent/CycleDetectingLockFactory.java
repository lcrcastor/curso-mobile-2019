package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@GwtIncompatible
@CanIgnoreReturnValue
@ThreadSafe
@Beta
public class CycleDetectingLockFactory {
    private static final ConcurrentMap<Class<? extends Enum>, Map<? extends Enum, LockGraphNode>> b = new MapMaker().weakKeys().makeMap();
    /* access modifiers changed from: private */
    public static final Logger c = Logger.getLogger(CycleDetectingLockFactory.class.getName());
    private static final ThreadLocal<ArrayList<LockGraphNode>> d = new ThreadLocal<ArrayList<LockGraphNode>>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<LockGraphNode> initialValue() {
            return Lists.newArrayListWithCapacity(3);
        }
    };
    final Policy a;

    static class LockGraphNode {
        final Map<LockGraphNode, ExampleStackTrace> a = new MapMaker().weakKeys().makeMap();
        final Map<LockGraphNode, PotentialDeadlockException> b = new MapMaker().weakKeys().makeMap();
        final String c;

        LockGraphNode(String str) {
            this.c = (String) Preconditions.checkNotNull(str);
        }

        /* access modifiers changed from: 0000 */
        public String a() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public void a(Policy policy, List<LockGraphNode> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                a(policy, (LockGraphNode) list.get(i));
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Policy policy, LockGraphNode lockGraphNode) {
            Preconditions.checkState(this != lockGraphNode, "Attempted to acquire multiple locks with the same rank %s", (Object) lockGraphNode.a());
            if (!this.a.containsKey(lockGraphNode)) {
                PotentialDeadlockException potentialDeadlockException = (PotentialDeadlockException) this.b.get(lockGraphNode);
                if (potentialDeadlockException != null) {
                    policy.handlePotentialDeadlock(new PotentialDeadlockException(lockGraphNode, this, potentialDeadlockException.getConflictingStackTrace()));
                    return;
                }
                ExampleStackTrace a2 = lockGraphNode.a(this, Sets.newIdentityHashSet());
                if (a2 == null) {
                    this.a.put(lockGraphNode, new ExampleStackTrace(lockGraphNode, this));
                } else {
                    PotentialDeadlockException potentialDeadlockException2 = new PotentialDeadlockException(lockGraphNode, this, a2);
                    this.b.put(lockGraphNode, potentialDeadlockException2);
                    policy.handlePotentialDeadlock(potentialDeadlockException2);
                }
            }
        }

        @Nullable
        private ExampleStackTrace a(LockGraphNode lockGraphNode, Set<LockGraphNode> set) {
            if (!set.add(this)) {
                return null;
            }
            ExampleStackTrace exampleStackTrace = (ExampleStackTrace) this.a.get(lockGraphNode);
            if (exampleStackTrace != null) {
                return exampleStackTrace;
            }
            for (Entry entry : this.a.entrySet()) {
                LockGraphNode lockGraphNode2 = (LockGraphNode) entry.getKey();
                ExampleStackTrace a2 = lockGraphNode2.a(lockGraphNode, set);
                if (a2 != null) {
                    ExampleStackTrace exampleStackTrace2 = new ExampleStackTrace(lockGraphNode2, this);
                    exampleStackTrace2.setStackTrace(((ExampleStackTrace) entry.getValue()).getStackTrace());
                    exampleStackTrace2.initCause(a2);
                    return exampleStackTrace2;
                }
            }
            return null;
        }
    }

    interface CycleDetectingLock {
        LockGraphNode a();

        boolean b();
    }

    final class CycleDetectingReentrantLock extends ReentrantLock implements CycleDetectingLock {
        private final LockGraphNode b;

        private CycleDetectingReentrantLock(LockGraphNode lockGraphNode, boolean z) {
            super(z);
            this.b = (LockGraphNode) Preconditions.checkNotNull(lockGraphNode);
        }

        public LockGraphNode a() {
            return this.b;
        }

        public boolean b() {
            return isHeldByCurrentThread();
        }

        public void lock() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }

        public void lockInterruptibly() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }

        public boolean tryLock(long j, TimeUnit timeUnit) {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this);
            try {
                return super.tryLock(j, timeUnit);
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }
    }

    class CycleDetectingReentrantReadLock extends ReadLock {
        @Weak
        final CycleDetectingReentrantReadWriteLock a;

        CycleDetectingReentrantReadLock(CycleDetectingReentrantReadWriteLock cycleDetectingReentrantReadWriteLock) {
            super(cycleDetectingReentrantReadWriteLock);
            this.a = cycleDetectingReentrantReadWriteLock;
        }

        public void lock() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this.a);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }

        public void lockInterruptibly() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this.a);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this.a);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }

        public boolean tryLock(long j, TimeUnit timeUnit) {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this.a);
            try {
                return super.tryLock(j, timeUnit);
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }
    }

    final class CycleDetectingReentrantReadWriteLock extends ReentrantReadWriteLock implements CycleDetectingLock {
        private final CycleDetectingReentrantReadLock b;
        private final CycleDetectingReentrantWriteLock c;
        private final LockGraphNode d;

        private CycleDetectingReentrantReadWriteLock(LockGraphNode lockGraphNode, boolean z) {
            super(z);
            this.b = new CycleDetectingReentrantReadLock(this);
            this.c = new CycleDetectingReentrantWriteLock(this);
            this.d = (LockGraphNode) Preconditions.checkNotNull(lockGraphNode);
        }

        public ReadLock readLock() {
            return this.b;
        }

        public WriteLock writeLock() {
            return this.c;
        }

        public LockGraphNode a() {
            return this.d;
        }

        public boolean b() {
            return isWriteLockedByCurrentThread() || getReadHoldCount() > 0;
        }
    }

    class CycleDetectingReentrantWriteLock extends WriteLock {
        @Weak
        final CycleDetectingReentrantReadWriteLock a;

        CycleDetectingReentrantWriteLock(CycleDetectingReentrantReadWriteLock cycleDetectingReentrantReadWriteLock) {
            super(cycleDetectingReentrantReadWriteLock);
            this.a = cycleDetectingReentrantReadWriteLock;
        }

        public void lock() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this.a);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }

        public void lockInterruptibly() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this.a);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this.a);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }

        public boolean tryLock(long j, TimeUnit timeUnit) {
            CycleDetectingLockFactory.this.b((CycleDetectingLock) this.a);
            try {
                return super.tryLock(j, timeUnit);
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.c(this.a);
            }
        }
    }

    static class ExampleStackTrace extends IllegalStateException {
        static final StackTraceElement[] a = new StackTraceElement[0];
        static final Set<String> b = ImmutableSet.of(CycleDetectingLockFactory.class.getName(), ExampleStackTrace.class.getName(), LockGraphNode.class.getName());

        ExampleStackTrace(LockGraphNode lockGraphNode, LockGraphNode lockGraphNode2) {
            StringBuilder sb = new StringBuilder();
            sb.append(lockGraphNode.a());
            sb.append(" -> ");
            sb.append(lockGraphNode2.a());
            super(sb.toString());
            StackTraceElement[] stackTrace = getStackTrace();
            int length = stackTrace.length;
            int i = 0;
            while (i < length) {
                if (WithExplicitOrdering.class.getName().equals(stackTrace[i].getClassName())) {
                    setStackTrace(a);
                    return;
                } else if (!b.contains(stackTrace[i].getClassName())) {
                    setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i, length));
                    return;
                } else {
                    i++;
                }
            }
        }
    }

    @Beta
    public enum Policies implements Policy {
        THROW {
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                throw potentialDeadlockException;
            }
        },
        WARN {
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                CycleDetectingLockFactory.c.log(Level.SEVERE, "Detected potential deadlock", potentialDeadlockException);
            }
        },
        DISABLED {
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
            }
        }
    }

    @ThreadSafe
    @Beta
    public interface Policy {
        void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException);
    }

    @Beta
    public static final class PotentialDeadlockException extends ExampleStackTrace {
        private final ExampleStackTrace c;

        private PotentialDeadlockException(LockGraphNode lockGraphNode, LockGraphNode lockGraphNode2, ExampleStackTrace exampleStackTrace) {
            super(lockGraphNode, lockGraphNode2);
            this.c = exampleStackTrace;
            initCause(exampleStackTrace);
        }

        public ExampleStackTrace getConflictingStackTrace() {
            return this.c;
        }

        public String getMessage() {
            StringBuilder sb = new StringBuilder(super.getMessage());
            for (Throwable th = this.c; th != null; th = th.getCause()) {
                sb.append(", ");
                sb.append(th.getMessage());
            }
            return sb.toString();
        }
    }

    @Beta
    public static final class WithExplicitOrdering<E extends Enum<E>> extends CycleDetectingLockFactory {
        private final Map<E, LockGraphNode> b;

        @VisibleForTesting
        WithExplicitOrdering(Policy policy, Map<E, LockGraphNode> map) {
            super(policy);
            this.b = map;
        }

        public ReentrantLock newReentrantLock(E e) {
            return newReentrantLock(e, false);
        }

        public ReentrantLock newReentrantLock(E e, boolean z) {
            return this.a == Policies.DISABLED ? new ReentrantLock(z) : new CycleDetectingReentrantLock((LockGraphNode) this.b.get(e), z);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E e) {
            return newReentrantReadWriteLock(e, false);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E e, boolean z) {
            return this.a == Policies.DISABLED ? new ReentrantReadWriteLock(z) : new CycleDetectingReentrantReadWriteLock((LockGraphNode) this.b.get(e), z);
        }
    }

    public static CycleDetectingLockFactory newInstance(Policy policy) {
        return new CycleDetectingLockFactory(policy);
    }

    public ReentrantLock newReentrantLock(String str) {
        return newReentrantLock(str, false);
    }

    public ReentrantLock newReentrantLock(String str, boolean z) {
        return this.a == Policies.DISABLED ? new ReentrantLock(z) : new CycleDetectingReentrantLock(new LockGraphNode(str), z);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String str) {
        return newReentrantReadWriteLock(str, false);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String str, boolean z) {
        return this.a == Policies.DISABLED ? new ReentrantReadWriteLock(z) : new CycleDetectingReentrantReadWriteLock(new LockGraphNode(str), z);
    }

    public static <E extends Enum<E>> WithExplicitOrdering<E> newInstanceWithExplicitOrdering(Class<E> cls, Policy policy) {
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(policy);
        return new WithExplicitOrdering<>(policy, b(cls));
    }

    private static Map<? extends Enum, LockGraphNode> b(Class<? extends Enum> cls) {
        Map<? extends Enum, LockGraphNode> map = (Map) b.get(cls);
        if (map != null) {
            return map;
        }
        Map a2 = a(cls);
        return (Map) MoreObjects.firstNonNull((Map) b.putIfAbsent(cls, a2), a2);
    }

    @VisibleForTesting
    static <E extends Enum<E>> Map<E, LockGraphNode> a(Class<E> cls) {
        EnumMap newEnumMap = Maps.newEnumMap(cls);
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        int length = enumArr.length;
        ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(length);
        int i = 0;
        for (Enum enumR : enumArr) {
            LockGraphNode lockGraphNode = new LockGraphNode(a(enumR));
            newArrayListWithCapacity.add(lockGraphNode);
            newEnumMap.put(enumR, lockGraphNode);
        }
        for (int i2 = 1; i2 < length; i2++) {
            ((LockGraphNode) newArrayListWithCapacity.get(i2)).a((Policy) Policies.THROW, newArrayListWithCapacity.subList(0, i2));
        }
        while (i < length - 1) {
            i++;
            ((LockGraphNode) newArrayListWithCapacity.get(i)).a((Policy) Policies.DISABLED, newArrayListWithCapacity.subList(i, length));
        }
        return Collections.unmodifiableMap(newEnumMap);
    }

    private static String a(Enum<?> enumR) {
        StringBuilder sb = new StringBuilder();
        sb.append(enumR.getDeclaringClass().getSimpleName());
        sb.append(".");
        sb.append(enumR.name());
        return sb.toString();
    }

    private CycleDetectingLockFactory(Policy policy) {
        this.a = (Policy) Preconditions.checkNotNull(policy);
    }

    /* access modifiers changed from: private */
    public void b(CycleDetectingLock cycleDetectingLock) {
        if (!cycleDetectingLock.b()) {
            ArrayList arrayList = (ArrayList) d.get();
            LockGraphNode a2 = cycleDetectingLock.a();
            a2.a(this.a, (List<LockGraphNode>) arrayList);
            arrayList.add(a2);
        }
    }

    /* access modifiers changed from: private */
    public static void c(CycleDetectingLock cycleDetectingLock) {
        if (!cycleDetectingLock.b()) {
            ArrayList arrayList = (ArrayList) d.get();
            LockGraphNode a2 = cycleDetectingLock.a();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (arrayList.get(size) == a2) {
                    arrayList.remove(size);
                    return;
                }
            }
        }
    }
}
