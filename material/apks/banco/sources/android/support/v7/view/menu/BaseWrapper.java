package android.support.v7.view.menu;

class BaseWrapper<T> {
    final T b;

    BaseWrapper(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.b = t;
    }

    public T getWrappedObject() {
        return this.b;
    }
}
