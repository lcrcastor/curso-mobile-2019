package dagger.internal;

import dagger.MembersInjector;
import java.util.Set;
import javax.inject.Provider;

public abstract class Binding<T> implements MembersInjector<T>, Provider<T> {
    protected static final boolean IS_SINGLETON = true;
    protected static final boolean NOT_SINGLETON = false;
    public static final Binding<Object> UNRESOLVED = new Binding<Object>(null, null, false, null) {
        public Object get() {
            throw new AssertionError("Unresolved binding should never be called to inject.");
        }

        public void injectMembers(Object obj) {
            throw new AssertionError("Unresolved binding should never be called to inject.");
        }
    };
    private int a;
    public final String membersKey;
    public final String provideKey;
    public final Object requiredBy;

    public static class InvalidBindingException extends RuntimeException {
        public final String type;

        public InvalidBindingException(String str, String str2) {
            super(str2);
            this.type = str;
        }

        public InvalidBindingException(String str, String str2, Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append("Binding for ");
            sb.append(str);
            sb.append(" was invalid: ");
            sb.append(str2);
            super(sb.toString(), th);
            this.type = str;
        }
    }

    public void attach(Linker linker) {
    }

    public void getDependencies(Set<Binding<?>> set, Set<Binding<?>> set2) {
    }

    public void injectMembers(T t) {
    }

    protected Binding(String str, String str2, boolean z, Object obj) {
        if (!z || str != null) {
            this.provideKey = str;
            this.membersKey = str2;
            this.requiredBy = obj;
            this.a = z ? 1 : 0;
            return;
        }
        throw new InvalidBindingException(Keys.getClassName(str2), "is exclusively members injected and therefore cannot be scoped");
    }

    public T get() {
        StringBuilder sb = new StringBuilder();
        sb.append("No injectable constructor on ");
        sb.append(getClass().getName());
        throw new UnsupportedOperationException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.a |= 2;
    }

    public boolean isLinked() {
        return (this.a & 2) != 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return (this.a & 1) != 0;
    }

    public boolean isVisiting() {
        return (this.a & 4) != 0;
    }

    public void setVisiting(boolean z) {
        this.a = z ? this.a | 4 : this.a & -5;
    }

    public boolean isCycleFree() {
        return (this.a & 8) != 0;
    }

    public void setCycleFree(boolean z) {
        this.a = z ? this.a | 8 : this.a & -9;
    }

    public void setLibrary(boolean z) {
        this.a = z ? this.a | 32 : this.a & -33;
    }

    public boolean library() {
        return (this.a & 32) != 0;
    }

    public void setDependedOn(boolean z) {
        this.a = z ? this.a | 16 : this.a & -17;
    }

    public boolean dependedOn() {
        return (this.a & 16) != 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[provideKey=\"");
        sb.append(this.provideKey);
        sb.append("\", memberskey=\"");
        sb.append(this.membersKey);
        sb.append("\"]");
        return sb.toString();
    }
}
