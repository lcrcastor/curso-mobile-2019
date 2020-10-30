package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import java.util.concurrent.Executor;

@Beta
public class AsyncEventBus extends EventBus {
    public AsyncEventBus(String str, Executor executor) {
        super(str, executor, Dispatcher.b(), LoggingHandler.a);
    }

    public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
        super("default", executor, Dispatcher.b(), subscriberExceptionHandler);
    }

    public AsyncEventBus(Executor executor) {
        super("default", executor, Dispatcher.b(), LoggingHandler.a);
    }
}
