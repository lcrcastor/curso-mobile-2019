package dagger.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class SetBinding<T> extends Binding<Set<T>> {
    private final SetBinding<T> a;
    private final List<Binding<?>> b;

    public static <T> void add(BindingsGroup bindingsGroup, String str, Binding<?> binding) {
        a(bindingsGroup, str, binding).b.add(Linker.a(binding));
    }

    private static <T> SetBinding<T> a(BindingsGroup bindingsGroup, String str, Binding<?> binding) {
        Binding binding2 = bindingsGroup.get(str);
        if (binding2 instanceof SetBinding) {
            SetBinding<T> setBinding = (SetBinding) binding2;
            setBinding.setLibrary(setBinding.library() && binding.library());
            return setBinding;
        } else if (binding2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Duplicate:\n    ");
            sb.append(binding2);
            sb.append("\n    ");
            sb.append(binding);
            throw new IllegalArgumentException(sb.toString());
        } else {
            SetBinding setBinding2 = new SetBinding(str, binding.requiredBy);
            setBinding2.setLibrary(binding.library());
            bindingsGroup.contributeSetBinding(str, setBinding2);
            return (SetBinding) bindingsGroup.get(str);
        }
    }

    public SetBinding(String str, Object obj) {
        super(str, null, false, obj);
        this.a = null;
        this.b = new ArrayList();
    }

    public SetBinding(SetBinding<T> setBinding) {
        super(setBinding.provideKey, null, false, setBinding.requiredBy);
        this.a = setBinding;
        setLibrary(setBinding.library());
        setDependedOn(setBinding.dependedOn());
        this.b = new ArrayList();
    }

    public void attach(Linker linker) {
        for (Binding attach : this.b) {
            attach.attach(linker);
        }
    }

    public int size() {
        int i = 0;
        for (SetBinding setBinding = this; setBinding != null; setBinding = setBinding.a) {
            i += setBinding.b.size();
        }
        return i;
    }

    public Set<T> get() {
        ArrayList arrayList = new ArrayList();
        for (SetBinding setBinding = this; setBinding != null; setBinding = setBinding.a) {
            int size = setBinding.b.size();
            for (int i = 0; i < size; i++) {
                Binding binding = (Binding) setBinding.b.get(i);
                Object obj = binding.get();
                if (binding.provideKey.equals(this.provideKey)) {
                    arrayList.addAll((Set) obj);
                } else {
                    arrayList.add(obj);
                }
            }
        }
        return Collections.unmodifiableSet(new LinkedHashSet(arrayList));
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
        for (SetBinding setBinding = this; setBinding != null; setBinding = setBinding.a) {
            set.addAll(setBinding.b);
        }
    }

    public void injectMembers(Set<T> set) {
        throw new UnsupportedOperationException("Cannot inject members on a contributed Set<T>.");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SetBinding[");
        SetBinding setBinding = this;
        boolean z = true;
        while (setBinding != null) {
            int size = setBinding.b.size();
            boolean z2 = z;
            int i = 0;
            while (i < size) {
                if (!z2) {
                    sb.append(",");
                }
                sb.append(setBinding.b.get(i));
                i++;
                z2 = false;
            }
            setBinding = setBinding.a;
            z = z2;
        }
        sb.append("]");
        return sb.toString();
    }
}
