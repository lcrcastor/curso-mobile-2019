package dagger.internal;

final class BuiltInBinding<T> extends Binding<T> {
    private final String a;
    private final ClassLoader b;
    private Binding<?> c;

    public BuiltInBinding(String str, Object obj, ClassLoader classLoader, String str2) {
        super(str, null, false, obj);
        this.b = classLoader;
        this.a = str2;
    }

    public void attach(Linker linker) {
        this.c = linker.requestBinding(this.a, this.requiredBy, this.b);
    }

    public void injectMembers(T t) {
        throw new UnsupportedOperationException();
    }

    public T get() {
        return this.c;
    }
}
