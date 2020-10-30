package com.appsflyer;

final class n {
    private b a;
    private String b;
    private boolean c;

    enum b {
        GOOGLE(0),
        AMAZON(1);
        
        private int c;

        private b(int i) {
            this.c = i;
        }

        public final String toString() {
            return String.valueOf(this.c);
        }
    }

    n(b bVar, String str, boolean z) {
        this.a = bVar;
        this.b = str;
        this.c = z;
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public final boolean b() {
        return this.c;
    }

    public final String toString() {
        return String.format("%s,%s", new Object[]{this.b, Boolean.valueOf(this.c)});
    }
}
