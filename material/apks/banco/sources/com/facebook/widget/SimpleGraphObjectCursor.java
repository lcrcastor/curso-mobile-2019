package com.facebook.widget;

import android.database.CursorIndexOutOfBoundsException;
import com.facebook.model.GraphObject;
import java.util.ArrayList;
import java.util.Collection;

class SimpleGraphObjectCursor<T extends GraphObject> implements GraphObjectCursor<T> {
    private int a = -1;
    private boolean b = false;
    private ArrayList<T> c = new ArrayList<>();
    private boolean d = false;
    private boolean e = false;

    SimpleGraphObjectCursor() {
    }

    SimpleGraphObjectCursor(SimpleGraphObjectCursor<T> simpleGraphObjectCursor) {
        this.a = simpleGraphObjectCursor.a;
        this.b = simpleGraphObjectCursor.b;
        this.c = new ArrayList<>();
        this.c.addAll(simpleGraphObjectCursor.c);
        this.e = simpleGraphObjectCursor.e;
    }

    public void a(Collection<T> collection, boolean z) {
        this.c.addAll(collection);
        this.e |= z;
    }

    public boolean g() {
        return this.e;
    }

    public void a(boolean z) {
        this.e = z;
    }

    public boolean a() {
        return this.d;
    }

    public void b(boolean z) {
        this.d = z;
    }

    public int b() {
        return this.c.size();
    }

    public boolean a(int i) {
        int b2 = b();
        if (i >= b2) {
            this.a = b2;
            return false;
        } else if (i < 0) {
            this.a = -1;
            return false;
        } else {
            this.a = i;
            return true;
        }
    }

    public boolean c() {
        return a(0);
    }

    public boolean d() {
        return a(this.a + 1);
    }

    public T e() {
        if (this.a < 0) {
            throw new CursorIndexOutOfBoundsException("Before first object.");
        } else if (this.a < this.c.size()) {
            return (GraphObject) this.c.get(this.a);
        } else {
            throw new CursorIndexOutOfBoundsException("After last object.");
        }
    }

    public void f() {
        this.b = true;
    }

    public boolean h() {
        return this.b;
    }
}
