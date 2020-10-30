package android.support.v7.widget;

import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.List;

class AdapterHelper implements Callback {
    final ArrayList<UpdateOp> a;
    final ArrayList<UpdateOp> b;
    final Callback c;
    Runnable d;
    final boolean e;
    final OpReorderer f;
    private Pool<UpdateOp> g;
    private int h;

    interface Callback {
        ViewHolder a(int i);

        void a(int i, int i2);

        void a(int i, int i2, Object obj);

        void a(UpdateOp updateOp);

        void b(int i, int i2);

        void b(UpdateOp updateOp);

        void c(int i, int i2);

        void d(int i, int i2);
    }

    static class UpdateOp {
        int a;
        int b;
        Object c;
        int d;

        UpdateOp(int i, int i2, int i3, Object obj) {
            this.a = i;
            this.b = i2;
            this.d = i3;
            this.c = obj;
        }

        /* access modifiers changed from: 0000 */
        public String a() {
            int i = this.a;
            if (i == 4) {
                return "up";
            }
            if (i == 8) {
                return "mv";
            }
            switch (i) {
                case 1:
                    return ProductAction.ACTION_ADD;
                case 2:
                    return "rm";
                default:
                    return "??";
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append("[");
            sb.append(a());
            sb.append(",s:");
            sb.append(this.b);
            sb.append("c:");
            sb.append(this.d);
            sb.append(",p:");
            sb.append(this.c);
            sb.append("]");
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            UpdateOp updateOp = (UpdateOp) obj;
            if (this.a != updateOp.a) {
                return false;
            }
            if (this.a == 8 && Math.abs(this.d - this.b) == 1 && this.d == updateOp.b && this.b == updateOp.d) {
                return true;
            }
            if (this.d != updateOp.d || this.b != updateOp.b) {
                return false;
            }
            if (this.c != null) {
                if (!this.c.equals(updateOp.c)) {
                    return false;
                }
            } else if (updateOp.c != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.a * 31) + this.b) * 31) + this.d;
        }
    }

    AdapterHelper(Callback callback) {
        this(callback, false);
    }

    AdapterHelper(Callback callback, boolean z) {
        this.g = new SimplePool(30);
        this.a = new ArrayList<>();
        this.b = new ArrayList<>();
        this.h = 0;
        this.c = callback;
        this.e = z;
        this.f = new OpReorderer(this);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        a((List<UpdateOp>) this.a);
        a((List<UpdateOp>) this.b);
        this.h = 0;
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.f.a(this.a);
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = (UpdateOp) this.a.get(i);
            int i2 = updateOp.a;
            if (i2 == 4) {
                d(updateOp);
            } else if (i2 != 8) {
                switch (i2) {
                    case 1:
                        f(updateOp);
                        break;
                    case 2:
                        c(updateOp);
                        break;
                }
            } else {
                b(updateOp);
            }
            if (this.d != null) {
                this.d.run();
            }
        }
        this.a.clear();
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            this.c.b((UpdateOp) this.b.get(i));
        }
        a((List<UpdateOp>) this.b);
        this.h = 0;
    }

    private void b(UpdateOp updateOp) {
        g(updateOp);
    }

    private void c(UpdateOp updateOp) {
        char c2;
        boolean z;
        boolean z2;
        int i = updateOp.b;
        int i2 = updateOp.b + updateOp.d;
        int i3 = updateOp.b;
        int i4 = 0;
        char c3 = 65535;
        while (i3 < i2) {
            if (this.c.a(i3) != null || d(i3)) {
                if (c3 == 0) {
                    e(a(2, i, i4, null));
                    z2 = true;
                } else {
                    z2 = false;
                }
                c2 = 1;
            } else {
                if (c3 == 1) {
                    g(a(2, i, i4, null));
                    z = true;
                } else {
                    z = false;
                }
                c2 = 0;
            }
            if (z) {
                i3 -= i4;
                i2 -= i4;
                i4 = 1;
            } else {
                i4++;
            }
            i3++;
            c3 = c2;
        }
        if (i4 != updateOp.d) {
            a(updateOp);
            updateOp = a(2, i, i4, null);
        }
        if (c3 == 0) {
            e(updateOp);
        } else {
            g(updateOp);
        }
    }

    private void d(UpdateOp updateOp) {
        int i = updateOp.b;
        int i2 = updateOp.b + updateOp.d;
        char c2 = 65535;
        int i3 = i;
        int i4 = 0;
        for (int i5 = updateOp.b; i5 < i2; i5++) {
            if (this.c.a(i5) != null || d(i5)) {
                if (c2 == 0) {
                    e(a(4, i3, i4, updateOp.c));
                    i3 = i5;
                    i4 = 0;
                }
                c2 = 1;
            } else {
                if (c2 == 1) {
                    g(a(4, i3, i4, updateOp.c));
                    i3 = i5;
                    i4 = 0;
                }
                c2 = 0;
            }
            i4++;
        }
        if (i4 != updateOp.d) {
            Object obj = updateOp.c;
            a(updateOp);
            updateOp = a(4, i3, i4, obj);
        }
        if (c2 == 0) {
            e(updateOp);
        } else {
            g(updateOp);
        }
    }

    private void e(UpdateOp updateOp) {
        int i;
        if (updateOp.a == 1 || updateOp.a == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int d2 = d(updateOp.b, updateOp.a);
        int i2 = updateOp.b;
        int i3 = updateOp.a;
        if (i3 == 2) {
            i = 0;
        } else if (i3 != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("op should be remove or update.");
            sb.append(updateOp);
            throw new IllegalArgumentException(sb.toString());
        } else {
            i = 1;
        }
        int i4 = d2;
        int i5 = i2;
        int i6 = 1;
        for (int i7 = 1; i7 < updateOp.d; i7++) {
            int d3 = d(updateOp.b + (i * i7), updateOp.a);
            int i8 = updateOp.a;
            if (i8 == 2 ? d3 == i4 : i8 == 4 && d3 == i4 + 1) {
                i6++;
            } else {
                UpdateOp a2 = a(updateOp.a, i4, i6, updateOp.c);
                a(a2, i5);
                a(a2);
                if (updateOp.a == 4) {
                    i5 += i6;
                }
                i4 = d3;
                i6 = 1;
            }
        }
        Object obj = updateOp.c;
        a(updateOp);
        if (i6 > 0) {
            UpdateOp a3 = a(updateOp.a, i4, i6, obj);
            a(a3, i5);
            a(a3);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(UpdateOp updateOp, int i) {
        this.c.a(updateOp);
        int i2 = updateOp.a;
        if (i2 == 2) {
            this.c.a(i, updateOp.d);
        } else if (i2 != 4) {
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        } else {
            this.c.a(i, updateOp.d, updateOp.c);
        }
    }

    private int d(int i, int i2) {
        int i3;
        int i4;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            UpdateOp updateOp = (UpdateOp) this.b.get(size);
            if (updateOp.a == 8) {
                if (updateOp.b < updateOp.d) {
                    i4 = updateOp.b;
                    i3 = updateOp.d;
                } else {
                    i4 = updateOp.d;
                    i3 = updateOp.b;
                }
                if (i < i4 || i > i3) {
                    if (i < updateOp.b) {
                        if (i2 == 1) {
                            updateOp.b++;
                            updateOp.d++;
                        } else if (i2 == 2) {
                            updateOp.b--;
                            updateOp.d--;
                        }
                    }
                } else if (i4 == updateOp.b) {
                    if (i2 == 1) {
                        updateOp.d++;
                    } else if (i2 == 2) {
                        updateOp.d--;
                    }
                    i++;
                } else {
                    if (i2 == 1) {
                        updateOp.b++;
                    } else if (i2 == 2) {
                        updateOp.b--;
                    }
                    i--;
                }
            } else if (updateOp.b <= i) {
                if (updateOp.a == 1) {
                    i -= updateOp.d;
                } else if (updateOp.a == 2) {
                    i += updateOp.d;
                }
            } else if (i2 == 1) {
                updateOp.b++;
            } else if (i2 == 2) {
                updateOp.b--;
            }
        }
        for (int size2 = this.b.size() - 1; size2 >= 0; size2--) {
            UpdateOp updateOp2 = (UpdateOp) this.b.get(size2);
            if (updateOp2.a == 8) {
                if (updateOp2.d == updateOp2.b || updateOp2.d < 0) {
                    this.b.remove(size2);
                    a(updateOp2);
                }
            } else if (updateOp2.d <= 0) {
                this.b.remove(size2);
                a(updateOp2);
            }
        }
        return i;
    }

    private boolean d(int i) {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            UpdateOp updateOp = (UpdateOp) this.b.get(i2);
            if (updateOp.a == 8) {
                if (a(updateOp.d, i2 + 1) == i) {
                    return true;
                }
            } else if (updateOp.a == 1) {
                int i3 = updateOp.b + updateOp.d;
                for (int i4 = updateOp.b; i4 < i3; i4++) {
                    if (a(i4, i2 + 1) == i) {
                        return true;
                    }
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    private void f(UpdateOp updateOp) {
        g(updateOp);
    }

    private void g(UpdateOp updateOp) {
        this.b.add(updateOp);
        int i = updateOp.a;
        if (i == 4) {
            this.c.a(updateOp.b, updateOp.d, updateOp.c);
        } else if (i != 8) {
            switch (i) {
                case 1:
                    this.c.c(updateOp.b, updateOp.d);
                    return;
                case 2:
                    this.c.b(updateOp.b, updateOp.d);
                    return;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unknown update op type for ");
                    sb.append(updateOp);
                    throw new IllegalArgumentException(sb.toString());
            }
        } else {
            this.c.d(updateOp.b, updateOp.d);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        return this.a.size() > 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i) {
        return (i & this.h) != 0;
    }

    /* access modifiers changed from: 0000 */
    public int b(int i) {
        return a(i, 0);
    }

    /* access modifiers changed from: 0000 */
    public int a(int i, int i2) {
        int size = this.b.size();
        while (i2 < size) {
            UpdateOp updateOp = (UpdateOp) this.b.get(i2);
            if (updateOp.a == 8) {
                if (updateOp.b == i) {
                    i = updateOp.d;
                } else {
                    if (updateOp.b < i) {
                        i--;
                    }
                    if (updateOp.d <= i) {
                        i++;
                    }
                }
            } else if (updateOp.b > i) {
                continue;
            } else if (updateOp.a == 2) {
                if (i < updateOp.b + updateOp.d) {
                    return -1;
                }
                i -= updateOp.d;
            } else if (updateOp.a == 1) {
                i += updateOp.d;
            }
            i2++;
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i, int i2, Object obj) {
        boolean z = false;
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(4, i, i2, obj));
        this.h |= 4;
        if (this.a.size() == 1) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean b(int i, int i2) {
        boolean z = false;
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(1, i, i2, null));
        this.h |= 1;
        if (this.a.size() == 1) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean c(int i, int i2) {
        boolean z = false;
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(2, i, i2, null));
        this.h |= 2;
        if (this.a.size() == 1) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i, int i2, int i3) {
        boolean z = false;
        if (i == i2) {
            return false;
        }
        if (i3 != 1) {
            throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        this.a.add(a(8, i, i2, null));
        this.h |= 8;
        if (this.a.size() == 1) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        c();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = (UpdateOp) this.a.get(i);
            int i2 = updateOp.a;
            if (i2 == 4) {
                this.c.b(updateOp);
                this.c.a(updateOp.b, updateOp.d, updateOp.c);
            } else if (i2 != 8) {
                switch (i2) {
                    case 1:
                        this.c.b(updateOp);
                        this.c.c(updateOp.b, updateOp.d);
                        break;
                    case 2:
                        this.c.b(updateOp);
                        this.c.a(updateOp.b, updateOp.d);
                        break;
                }
            } else {
                this.c.b(updateOp);
                this.c.d(updateOp.b, updateOp.d);
            }
            if (this.d != null) {
                this.d.run();
            }
        }
        a((List<UpdateOp>) this.a);
        this.h = 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0047, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int c(int r6) {
        /*
            r5 = this;
            java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r0 = r5.a
            int r0 = r0.size()
            r1 = 0
        L_0x0007:
            if (r1 >= r0) goto L_0x004a
            java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r2 = r5.a
            java.lang.Object r2 = r2.get(r1)
            android.support.v7.widget.AdapterHelper$UpdateOp r2 = (android.support.v7.widget.AdapterHelper.UpdateOp) r2
            int r3 = r2.a
            r4 = 8
            if (r3 == r4) goto L_0x0034
            switch(r3) {
                case 1: goto L_0x002c;
                case 2: goto L_0x001b;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x0047
        L_0x001b:
            int r3 = r2.b
            if (r3 > r6) goto L_0x0047
            int r3 = r2.b
            int r4 = r2.d
            int r3 = r3 + r4
            if (r3 <= r6) goto L_0x0028
            r6 = -1
            return r6
        L_0x0028:
            int r2 = r2.d
            int r6 = r6 - r2
            goto L_0x0047
        L_0x002c:
            int r3 = r2.b
            if (r3 > r6) goto L_0x0047
            int r2 = r2.d
            int r6 = r6 + r2
            goto L_0x0047
        L_0x0034:
            int r3 = r2.b
            if (r3 != r6) goto L_0x003b
            int r6 = r2.d
            goto L_0x0047
        L_0x003b:
            int r3 = r2.b
            if (r3 >= r6) goto L_0x0041
            int r6 = r6 + -1
        L_0x0041:
            int r2 = r2.d
            if (r2 > r6) goto L_0x0047
            int r6 = r6 + 1
        L_0x0047:
            int r1 = r1 + 1
            goto L_0x0007
        L_0x004a:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AdapterHelper.c(int):int");
    }

    /* access modifiers changed from: 0000 */
    public boolean f() {
        return !this.b.isEmpty() && !this.a.isEmpty();
    }

    public UpdateOp a(int i, int i2, int i3, Object obj) {
        UpdateOp updateOp = (UpdateOp) this.g.acquire();
        if (updateOp == null) {
            return new UpdateOp(i, i2, i3, obj);
        }
        updateOp.a = i;
        updateOp.b = i2;
        updateOp.d = i3;
        updateOp.c = obj;
        return updateOp;
    }

    public void a(UpdateOp updateOp) {
        if (!this.e) {
            updateOp.c = null;
            this.g.release(updateOp);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(List<UpdateOp> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            a((UpdateOp) list.get(i));
        }
        list.clear();
    }
}
