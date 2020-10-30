package io.fabric.sdk.android.services.events;

public interface EventTransform<T> {
    byte[] toBytes(T t);
}
