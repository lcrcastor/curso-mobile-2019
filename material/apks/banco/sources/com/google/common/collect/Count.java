package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible
final class Count implements Serializable {
    private int a;

    Count(int i) {
        this.a = i;
    }

    public int a() {
        return this.a;
    }

    public void a(int i) {
        this.a += i;
    }

    public int b(int i) {
        int i2 = this.a + i;
        this.a = i2;
        return i2;
    }

    public void c(int i) {
        this.a = i;
    }

    public int d(int i) {
        int i2 = this.a;
        this.a = i;
        return i2;
    }

    public int hashCode() {
        return this.a;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Count) && ((Count) obj).a == this.a;
    }

    public String toString() {
        return Integer.toString(this.a);
    }
}
