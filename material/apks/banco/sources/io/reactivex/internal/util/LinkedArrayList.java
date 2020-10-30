package io.reactivex.internal.util;

import java.util.ArrayList;

public class LinkedArrayList {
    final int h;
    Object[] i;
    Object[] j;
    volatile int k;
    int l;

    public LinkedArrayList(int i2) {
        this.h = i2;
    }

    public void add(Object obj) {
        if (this.k == 0) {
            this.i = new Object[(this.h + 1)];
            this.j = this.i;
            this.i[0] = obj;
            this.l = 1;
            this.k = 1;
        } else if (this.l == this.h) {
            Object[] objArr = new Object[(this.h + 1)];
            objArr[0] = obj;
            this.j[this.h] = objArr;
            this.j = objArr;
            this.l = 1;
            this.k++;
        } else {
            this.j[this.l] = obj;
            this.l++;
            this.k++;
        }
    }

    public Object[] head() {
        return this.i;
    }

    public int size() {
        return this.k;
    }

    public String toString() {
        int i2 = this.h;
        int i3 = this.k;
        ArrayList arrayList = new ArrayList(i3 + 1);
        Object[] head = head();
        int i4 = 0;
        while (true) {
            int i5 = 0;
            while (i4 < i3) {
                arrayList.add(head[i5]);
                i4++;
                i5++;
                if (i5 == i2) {
                    head = head[i2];
                }
            }
            return arrayList.toString();
        }
    }
}
