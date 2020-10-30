package com.squareup.otto;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class Bus {
    public static final String DEFAULT_IDENTIFIER = "default";
    private final ConcurrentMap<Class<?>, Set<EventHandler>> a;
    private final ConcurrentMap<Class<?>, EventProducer> b;
    private final String c;
    private final ThreadEnforcer d;
    private final HandlerFinder e;
    private final ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>> f;
    private final ThreadLocal<Boolean> g;
    private final Map<Class<?>, Set<Class<?>>> h;

    static class EventWithHandler {
        final Object a;
        final EventHandler b;

        public EventWithHandler(Object obj, EventHandler eventHandler) {
            this.a = obj;
            this.b = eventHandler;
        }
    }

    public Bus() {
        this("default");
    }

    public Bus(String str) {
        this(ThreadEnforcer.MAIN, str);
    }

    public Bus(ThreadEnforcer threadEnforcer) {
        this(threadEnforcer, "default");
    }

    public Bus(ThreadEnforcer threadEnforcer, String str) {
        this(threadEnforcer, str, HandlerFinder.a);
    }

    Bus(ThreadEnforcer threadEnforcer, String str, HandlerFinder handlerFinder) {
        this.a = new ConcurrentHashMap();
        this.b = new ConcurrentHashMap();
        this.f = new ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>>() {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public ConcurrentLinkedQueue<EventWithHandler> initialValue() {
                return new ConcurrentLinkedQueue<>();
            }
        };
        this.g = new ThreadLocal<Boolean>() {
            /* access modifiers changed from: protected */
            /* renamed from: a */
            public Boolean initialValue() {
                return Boolean.valueOf(false);
            }
        };
        this.h = new HashMap();
        this.d = threadEnforcer;
        this.c = str;
        this.e = handlerFinder;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Bus \"");
        sb.append(this.c);
        sb.append("\"]");
        return sb.toString();
    }

    public void register(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        this.d.enforce(this);
        Map a2 = this.e.a(obj);
        for (Class cls : a2.keySet()) {
            EventProducer eventProducer = (EventProducer) a2.get(cls);
            EventProducer eventProducer2 = (EventProducer) this.b.putIfAbsent(cls, eventProducer);
            if (eventProducer2 != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Producer method for type ");
                sb.append(cls);
                sb.append(" found on type ");
                sb.append(eventProducer.a.getClass());
                sb.append(", but already registered by type ");
                sb.append(eventProducer2.a.getClass());
                sb.append(".");
                throw new IllegalArgumentException(sb.toString());
            }
            Set<EventHandler> set = (Set) this.a.get(cls);
            if (set != null && !set.isEmpty()) {
                for (EventHandler a3 : set) {
                    a(a3, eventProducer);
                }
            }
        }
        Map b2 = this.e.b(obj);
        for (Class cls2 : b2.keySet()) {
            Set set2 = (Set) this.a.get(cls2);
            if (set2 == null) {
                set2 = new CopyOnWriteArraySet();
                Set set3 = (Set) this.a.putIfAbsent(cls2, set2);
                if (set3 != null) {
                    set2 = set3;
                }
            }
            set2.addAll((Set) b2.get(cls2));
        }
        for (Entry entry : b2.entrySet()) {
            EventProducer eventProducer3 = (EventProducer) this.b.get((Class) entry.getKey());
            if (eventProducer3 != null && eventProducer3.a()) {
                for (EventHandler eventHandler : (Set) entry.getValue()) {
                    if (!eventProducer3.a()) {
                        break;
                    } else if (eventHandler.a()) {
                        a(eventHandler, eventProducer3);
                    }
                }
            }
        }
    }

    private void a(EventHandler eventHandler, EventProducer eventProducer) {
        Object obj;
        try {
            obj = eventProducer.c();
        } catch (InvocationTargetException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Producer ");
            sb.append(eventProducer);
            sb.append(" threw an exception.");
            a(sb.toString(), e2);
            obj = null;
        }
        if (obj != null) {
            dispatch(obj, eventHandler);
        }
    }

    public void unregister(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Object to unregister must not be null.");
        }
        this.d.enforce(this);
        for (Entry entry : this.e.a(obj).entrySet()) {
            Class cls = (Class) entry.getKey();
            EventProducer a2 = a(cls);
            EventProducer eventProducer = (EventProducer) entry.getValue();
            if (eventProducer == null || !eventProducer.equals(a2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Missing event producer for an annotated method. Is ");
                sb.append(obj.getClass());
                sb.append(" registered?");
                throw new IllegalArgumentException(sb.toString());
            }
            ((EventProducer) this.b.remove(cls)).b();
        }
        for (Entry entry2 : this.e.b(obj).entrySet()) {
            Set<EventHandler> b2 = b((Class) entry2.getKey());
            Collection collection = (Collection) entry2.getValue();
            if (b2 == null || !b2.containsAll(collection)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Missing event handler for an annotated method. Is ");
                sb2.append(obj.getClass());
                sb2.append(" registered?");
                throw new IllegalArgumentException(sb2.toString());
            }
            for (EventHandler eventHandler : b2) {
                if (collection.contains(eventHandler)) {
                    eventHandler.b();
                }
            }
            b2.removeAll(collection);
        }
    }

    public void post(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Event to post must not be null.");
        }
        this.d.enforce(this);
        boolean z = false;
        for (Class b2 : c(obj.getClass())) {
            Set<EventHandler> b3 = b(b2);
            if (b3 != null && !b3.isEmpty()) {
                z = true;
                for (EventHandler enqueueEvent : b3) {
                    enqueueEvent(obj, enqueueEvent);
                }
            }
        }
        if (!z && !(obj instanceof DeadEvent)) {
            post(new DeadEvent(this, obj));
        }
        dispatchQueuedEvents();
    }

    /* access modifiers changed from: protected */
    public void enqueueEvent(Object obj, EventHandler eventHandler) {
        ((ConcurrentLinkedQueue) this.f.get()).offer(new EventWithHandler(obj, eventHandler));
    }

    /* access modifiers changed from: protected */
    public void dispatchQueuedEvents() {
        if (!((Boolean) this.g.get()).booleanValue()) {
            this.g.set(Boolean.valueOf(true));
            while (true) {
                try {
                    EventWithHandler eventWithHandler = (EventWithHandler) ((ConcurrentLinkedQueue) this.f.get()).poll();
                    if (eventWithHandler != null) {
                        if (eventWithHandler.b.a()) {
                            dispatch(eventWithHandler.a, eventWithHandler.b);
                        }
                    } else {
                        return;
                    }
                } finally {
                    this.g.set(Boolean.valueOf(false));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatch(Object obj, EventHandler eventHandler) {
        try {
            eventHandler.a(obj);
        } catch (InvocationTargetException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Could not dispatch event: ");
            sb.append(obj.getClass());
            sb.append(" to handler ");
            sb.append(eventHandler);
            a(sb.toString(), e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public EventProducer a(Class<?> cls) {
        return (EventProducer) this.b.get(cls);
    }

    /* access modifiers changed from: 0000 */
    public Set<EventHandler> b(Class<?> cls) {
        return (Set) this.a.get(cls);
    }

    /* access modifiers changed from: 0000 */
    public Set<Class<?>> c(Class<?> cls) {
        Set<Class<?>> set = (Set) this.h.get(cls);
        if (set != null) {
            return set;
        }
        Set<Class<?>> d2 = d(cls);
        this.h.put(cls, d2);
        return d2;
    }

    private Set<Class<?>> d(Class<?> cls) {
        LinkedList linkedList = new LinkedList();
        HashSet hashSet = new HashSet();
        linkedList.add(cls);
        while (!linkedList.isEmpty()) {
            Class cls2 = (Class) linkedList.remove(0);
            hashSet.add(cls2);
            Class superclass = cls2.getSuperclass();
            if (superclass != null) {
                linkedList.add(superclass);
            }
        }
        return hashSet;
    }

    private static void a(String str, InvocationTargetException invocationTargetException) {
        Throwable cause = invocationTargetException.getCause();
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(cause.getMessage());
            throw new RuntimeException(sb.toString(), cause);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(": ");
        sb2.append(invocationTargetException.getMessage());
        throw new RuntimeException(sb2.toString(), invocationTargetException);
    }
}
