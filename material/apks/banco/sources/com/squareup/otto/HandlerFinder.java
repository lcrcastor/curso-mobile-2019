package com.squareup.otto;

import java.util.Map;
import java.util.Set;

interface HandlerFinder {
    public static final HandlerFinder a = new HandlerFinder() {
        public Map<Class<?>, EventProducer> a(Object obj) {
            return AnnotatedHandlerFinder.a(obj);
        }

        public Map<Class<?>, Set<EventHandler>> b(Object obj) {
            return AnnotatedHandlerFinder.b(obj);
        }
    };

    Map<Class<?>, EventProducer> a(Object obj);

    Map<Class<?>, Set<EventHandler>> b(Object obj);
}
