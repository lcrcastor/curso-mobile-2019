package dagger.internal;

import dagger.Lazy;

final class LazyBinding<T> extends Binding<Lazy<T>> {
    /* access modifiers changed from: private */
    public static final Object a = new Object();
    private final String b;
    private final ClassLoader c;
    /* access modifiers changed from: private */
    public Binding<T> d;

    public LazyBinding(String str, Object obj, ClassLoader classLoader, String str2) {
        super(str, null, false, obj);
        this.c = classLoader;
        this.b = str2;
    }

    public void attach(Linker linker) {
        this.d = linker.requestBinding(this.b, this.requiredBy, this.c);
    }

    /* renamed from: a */
    public void injectMembers(Lazy<T> lazy) {
        throw new UnsupportedOperationException();
    }

    /* renamed from: c */
    public Lazy<T> get() {
        return new Lazy<T>() {
            private volatile Object b = LazyBinding.a;

            public T get() {
                if (this.b == LazyBinding.a) {
                    synchronized (this) {
                        if (this.b == LazyBinding.a) {
                            this.b = LazyBinding.this.d.get();
                        }
                    }
                }
                return this.b;
            }
        };
    }
}
