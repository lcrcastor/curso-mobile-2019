package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.Weak;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

class Subscriber {
    @VisibleForTesting
    final Object a;
    /* access modifiers changed from: private */
    @Weak
    public EventBus b;
    private final Method c;
    private final Executor d;

    @VisibleForTesting
    static final class SynchronizedSubscriber extends Subscriber {
        private SynchronizedSubscriber(EventBus eventBus, Object obj, Method method) {
            super(eventBus, obj, method);
        }

        /* access modifiers changed from: 0000 */
        public void b(Object obj) {
            synchronized (this) {
                Subscriber.super.b(obj);
            }
        }
    }

    static Subscriber a(EventBus eventBus, Object obj, Method method) {
        return a(method) ? new Subscriber(eventBus, obj, method) : new SynchronizedSubscriber(eventBus, obj, method);
    }

    private Subscriber(EventBus eventBus, Object obj, Method method) {
        this.b = eventBus;
        this.a = Preconditions.checkNotNull(obj);
        this.c = method;
        method.setAccessible(true);
        this.d = eventBus.a();
    }

    /* access modifiers changed from: 0000 */
    public final void a(final Object obj) {
        this.d.execute(new Runnable() {
            public void run() {
                try {
                    Subscriber.this.b(obj);
                } catch (InvocationTargetException e) {
                    Subscriber.this.b.a(e.getCause(), Subscriber.this.c(obj));
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public void b(Object obj) {
        try {
            this.c.invoke(this.a, new Object[]{Preconditions.checkNotNull(obj)});
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Method rejected target/argument: ");
            sb.append(obj);
            throw new Error(sb.toString(), e);
        } catch (IllegalAccessException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Method became inaccessible: ");
            sb2.append(obj);
            throw new Error(sb2.toString(), e2);
        } catch (InvocationTargetException e3) {
            if (e3.getCause() instanceof Error) {
                throw ((Error) e3.getCause());
            }
            throw e3;
        }
    }

    /* access modifiers changed from: private */
    public SubscriberExceptionContext c(Object obj) {
        return new SubscriberExceptionContext(this.b, obj, this.a, this.c);
    }

    public final int hashCode() {
        return ((this.c.hashCode() + 31) * 31) + System.identityHashCode(this.a);
    }

    public final boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Subscriber)) {
            return false;
        }
        Subscriber subscriber = (Subscriber) obj;
        if (this.a == subscriber.a && this.c.equals(subscriber.c)) {
            z = true;
        }
        return z;
    }

    private static boolean a(Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }
}
