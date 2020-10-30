package dagger.internal;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import dagger.internal.Binding.InvalidBindingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

public final class Linker {
    /* access modifiers changed from: private */
    public static final Object a = new Object();
    private final Linker b;
    private final Queue<Binding<?>> c = new ArrayQueue();
    private boolean d = true;
    private final List<String> e = new ArrayList();
    private final Map<String, Binding<?>> f = new HashMap();
    private volatile Map<String, Binding<?>> g = null;
    private final Loader h;
    private final ErrorHandler i;

    static class DeferredBinding extends Binding<Object> {
        final ClassLoader a;
        final String b;
        final boolean c;

        private DeferredBinding(String str, ClassLoader classLoader, Object obj, boolean z) {
            super(null, null, false, obj);
            this.b = str;
            this.a = classLoader;
            this.c = z;
        }

        public void injectMembers(Object obj) {
            throw new UnsupportedOperationException("Deferred bindings must resolve first.");
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            throw new UnsupportedOperationException("Deferred bindings must resolve first.");
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("DeferredBinding[deferredKey=");
            sb.append(this.b);
            sb.append("]");
            return sb.toString();
        }
    }

    public interface ErrorHandler {
        public static final ErrorHandler NULL = new ErrorHandler() {
            public void handleErrors(List<String> list) {
            }
        };

        void handleErrors(List<String> list);
    }

    static class SingletonBinding<T> extends Binding<T> {
        private final Binding<T> a;
        private volatile Object b;

        /* access modifiers changed from: protected */
        public boolean b() {
            return true;
        }

        private SingletonBinding(Binding<T> binding) {
            super(binding.provideKey, binding.membersKey, true, binding.requiredBy);
            this.b = Linker.a;
            this.a = binding;
        }

        public void attach(Linker linker) {
            this.a.attach(linker);
        }

        public void injectMembers(T t) {
            this.a.injectMembers(t);
        }

        public T get() {
            if (this.b == Linker.a) {
                synchronized (this) {
                    if (this.b == Linker.a) {
                        this.b = this.a.get();
                    }
                }
            }
            return this.b;
        }

        public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
            this.a.getDependencies(set, set2);
        }

        public boolean isCycleFree() {
            return this.a.isCycleFree();
        }

        public boolean isLinked() {
            return this.a.isLinked();
        }

        public boolean isVisiting() {
            return this.a.isVisiting();
        }

        public boolean library() {
            return this.a.library();
        }

        public boolean dependedOn() {
            return this.a.dependedOn();
        }

        public void setCycleFree(boolean z) {
            this.a.setCycleFree(z);
        }

        public void setVisiting(boolean z) {
            this.a.setVisiting(z);
        }

        public void setLibrary(boolean z) {
            this.a.setLibrary(true);
        }

        public void setDependedOn(boolean z) {
            this.a.setDependedOn(z);
        }

        /* access modifiers changed from: protected */
        public void a() {
            this.a.a();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("@Singleton/");
            sb.append(this.a.toString());
            return sb.toString();
        }
    }

    public Linker(Linker linker, Loader loader, ErrorHandler errorHandler) {
        if (loader == null) {
            throw new NullPointerException("plugin");
        } else if (errorHandler == null) {
            throw new NullPointerException("errorHandler");
        } else {
            this.b = linker;
            this.h = loader;
            this.i = errorHandler;
        }
    }

    public void installBindings(BindingsGroup bindingsGroup) {
        if (this.g != null) {
            throw new IllegalStateException("Cannot install further bindings after calling linkAll().");
        }
        for (Entry entry : bindingsGroup.entrySet()) {
            this.f.put(entry.getKey(), a((Binding) entry.getValue()));
        }
    }

    public Map<String, Binding<?>> linkAll() {
        b();
        if (this.g != null) {
            return this.g;
        }
        for (Binding binding : this.f.values()) {
            if (!binding.isLinked()) {
                this.c.add(binding);
            }
        }
        linkRequested();
        this.g = Collections.unmodifiableMap(this.f);
        return this.g;
    }

    public Map<String, Binding<?>> fullyLinkedBindings() {
        return this.g;
    }

    public void linkRequested() {
        b();
        while (true) {
            Binding binding = (Binding) this.c.poll();
            if (binding == null) {
                try {
                    this.i.handleErrors(this.e);
                    return;
                } finally {
                    this.e.clear();
                }
            } else if (binding instanceof DeferredBinding) {
                DeferredBinding deferredBinding = (DeferredBinding) binding;
                String str = deferredBinding.b;
                boolean z = deferredBinding.c;
                if (this.f.containsKey(str)) {
                    continue;
                } else {
                    try {
                        Binding a2 = a(str, binding.requiredBy, deferredBinding.a, z);
                        a2.setLibrary(binding.library());
                        a2.setDependedOn(binding.dependedOn());
                        if (str.equals(a2.provideKey) || str.equals(a2.membersKey)) {
                            Binding a3 = a(a2);
                            this.c.add(a3);
                            b(a3);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unable to create binding for ");
                            sb.append(str);
                            throw new IllegalStateException(sb.toString());
                        }
                    } catch (InvalidBindingException e2) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(e2.type);
                        sb2.append(UtilsCuentas.SEPARAOR2);
                        sb2.append(e2.getMessage());
                        sb2.append(" required by ");
                        sb2.append(binding.requiredBy);
                        a(sb2.toString());
                        this.f.put(str, Binding.UNRESOLVED);
                    } catch (UnsupportedOperationException e3) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Unsupported: ");
                        sb3.append(e3.getMessage());
                        sb3.append(" required by ");
                        sb3.append(binding.requiredBy);
                        a(sb3.toString());
                        this.f.put(str, Binding.UNRESOLVED);
                    } catch (IllegalArgumentException e4) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(e4.getMessage());
                        sb4.append(" required by ");
                        sb4.append(binding.requiredBy);
                        a(sb4.toString());
                        this.f.put(str, Binding.UNRESOLVED);
                    } catch (RuntimeException e5) {
                        throw e5;
                    } catch (Exception e6) {
                        throw new RuntimeException(e6);
                    }
                }
            } else {
                this.d = true;
                binding.attach(this);
                if (this.d) {
                    binding.a();
                } else {
                    this.c.add(binding);
                }
            }
        }
    }

    private void b() {
        if (!Thread.holdsLock(this)) {
            throw new AssertionError();
        }
    }

    private Binding<?> a(String str, Object obj, ClassLoader classLoader, boolean z) {
        String a2 = Keys.a(str);
        if (a2 != null) {
            return new BuiltInBinding(str, obj, classLoader, a2);
        }
        String b2 = Keys.b(str);
        if (b2 != null) {
            return new LazyBinding(str, obj, classLoader, b2);
        }
        String className = Keys.getClassName(str);
        if (className == null || Keys.isAnnotated(str)) {
            throw new IllegalArgumentException(str);
        }
        Binding<?> atInjectBinding = this.h.getAtInjectBinding(str, className, classLoader, z);
        if (atInjectBinding != null) {
            return atInjectBinding;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("could not be bound with key ");
        sb.append(str);
        throw new InvalidBindingException(className, sb.toString());
    }

    @Deprecated
    public Binding<?> requestBinding(String str, Object obj) {
        return requestBinding(str, obj, getClass().getClassLoader(), true, true);
    }

    public Binding<?> requestBinding(String str, Object obj, ClassLoader classLoader) {
        return requestBinding(str, obj, classLoader, true, true);
    }

    @Deprecated
    public Binding<?> requestBinding(String str, Object obj, boolean z, boolean z2) {
        return requestBinding(str, obj, getClass().getClassLoader(), z, z2);
    }

    public Binding<?> requestBinding(String str, Object obj, ClassLoader classLoader, boolean z, boolean z2) {
        b();
        Linker linker = this;
        Binding binding = null;
        while (true) {
            if (linker == null) {
                break;
            }
            binding = (Binding) linker.f.get(str);
            if (binding == null) {
                linker = linker.b;
            } else if (linker != this && !binding.isLinked()) {
                throw new AssertionError();
            }
        }
        if (binding == null) {
            DeferredBinding deferredBinding = new DeferredBinding(str, classLoader, obj, z);
            deferredBinding.setLibrary(z2);
            deferredBinding.setDependedOn(true);
            this.c.add(deferredBinding);
            this.d = false;
            return null;
        }
        if (!binding.isLinked()) {
            this.c.add(binding);
        }
        binding.setLibrary(z2);
        binding.setDependedOn(true);
        return binding;
    }

    private <T> void b(Binding<T> binding) {
        if (binding.provideKey != null) {
            a(this.f, binding.provideKey, binding);
        }
        if (binding.membersKey != null) {
            a(this.f, binding.membersKey, binding);
        }
    }

    static <T> Binding<T> a(Binding<T> binding) {
        return (!binding.b() || (binding instanceof SingletonBinding)) ? binding : new SingletonBinding(binding);
    }

    private <K, V> void a(Map<K, V> map, K k, V v) {
        Object put = map.put(k, v);
        if (put != null) {
            map.put(k, put);
        }
    }

    private void a(String str) {
        this.e.add(str);
    }
}
