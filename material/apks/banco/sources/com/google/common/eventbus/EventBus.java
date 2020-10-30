package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
public class EventBus {
    private static final Logger a = Logger.getLogger(EventBus.class.getName());
    private final String b;
    private final Executor c;
    private final SubscriberExceptionHandler d;
    private final SubscriberRegistry e;
    private final Dispatcher f;

    static final class LoggingHandler implements SubscriberExceptionHandler {
        static final LoggingHandler a = new LoggingHandler();

        LoggingHandler() {
        }

        public void handleException(Throwable th, SubscriberExceptionContext subscriberExceptionContext) {
            Logger a2 = a(subscriberExceptionContext);
            if (a2.isLoggable(Level.SEVERE)) {
                a2.log(Level.SEVERE, b(subscriberExceptionContext), th);
            }
        }

        private static Logger a(SubscriberExceptionContext subscriberExceptionContext) {
            StringBuilder sb = new StringBuilder();
            sb.append(EventBus.class.getName());
            sb.append(".");
            sb.append(subscriberExceptionContext.getEventBus().identifier());
            return Logger.getLogger(sb.toString());
        }

        private static String b(SubscriberExceptionContext subscriberExceptionContext) {
            Method subscriberMethod = subscriberExceptionContext.getSubscriberMethod();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception thrown by subscriber method ");
            sb.append(subscriberMethod.getName());
            sb.append('(');
            sb.append(subscriberMethod.getParameterTypes()[0].getName());
            sb.append(')');
            sb.append(" on subscriber ");
            sb.append(subscriberExceptionContext.getSubscriber());
            sb.append(" when dispatching event: ");
            sb.append(subscriberExceptionContext.getEvent());
            return sb.toString();
        }
    }

    public EventBus() {
        this("default");
    }

    public EventBus(String str) {
        this(str, MoreExecutors.directExecutor(), Dispatcher.a(), LoggingHandler.a);
    }

    public EventBus(SubscriberExceptionHandler subscriberExceptionHandler) {
        this("default", MoreExecutors.directExecutor(), Dispatcher.a(), subscriberExceptionHandler);
    }

    EventBus(String str, Executor executor, Dispatcher dispatcher, SubscriberExceptionHandler subscriberExceptionHandler) {
        this.e = new SubscriberRegistry(this);
        this.b = (String) Preconditions.checkNotNull(str);
        this.c = (Executor) Preconditions.checkNotNull(executor);
        this.f = (Dispatcher) Preconditions.checkNotNull(dispatcher);
        this.d = (SubscriberExceptionHandler) Preconditions.checkNotNull(subscriberExceptionHandler);
    }

    public final String identifier() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public final Executor a() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public void a(Throwable th, SubscriberExceptionContext subscriberExceptionContext) {
        Preconditions.checkNotNull(th);
        Preconditions.checkNotNull(subscriberExceptionContext);
        try {
            this.d.handleException(th, subscriberExceptionContext);
        } catch (Throwable th2) {
            a.log(Level.SEVERE, String.format(Locale.ROOT, "Exception %s thrown while handling exception: %s", new Object[]{th2, th}), th2);
        }
    }

    public void register(Object obj) {
        this.e.a(obj);
    }

    public void unregister(Object obj) {
        this.e.b(obj);
    }

    public void post(Object obj) {
        Iterator c2 = this.e.c(obj);
        if (c2.hasNext()) {
            this.f.a(obj, c2);
        } else if (!(obj instanceof DeadEvent)) {
            post(new DeadEvent(this, obj));
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).addValue((Object) this.b).toString();
    }
}
