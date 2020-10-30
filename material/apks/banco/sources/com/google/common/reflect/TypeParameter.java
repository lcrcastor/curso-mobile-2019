package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import javax.annotation.Nullable;

@Beta
public abstract class TypeParameter<T> extends TypeCapture<T> {
    final TypeVariable<?> a;

    protected TypeParameter() {
        Type a2 = a();
        Preconditions.checkArgument(a2 instanceof TypeVariable, "%s should be a type variable.", (Object) a2);
        this.a = (TypeVariable) a2;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final boolean equals(@Nullable Object obj) {
        if (!(obj instanceof TypeParameter)) {
            return false;
        }
        return this.a.equals(((TypeParameter) obj).a);
    }

    public String toString() {
        return this.a.toString();
    }
}
