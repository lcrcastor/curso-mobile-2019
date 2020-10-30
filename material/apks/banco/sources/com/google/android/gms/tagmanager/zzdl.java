package com.google.android.gms.tagmanager;

class zzdl extends Number implements Comparable<zzdl> {
    private double a;
    private long b;
    private boolean c = false;

    private zzdl(double d) {
        this.a = d;
    }

    private zzdl(long j) {
        this.b = j;
    }

    public static zzdl a(long j) {
        return new zzdl(j);
    }

    public static zzdl a(Double d) {
        return new zzdl(d.doubleValue());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
        return new com.google.android.gms.tagmanager.zzdl(java.lang.Double.parseDouble(r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0023, code lost:
        throw new java.lang.NumberFormatException(java.lang.String.valueOf(r3).concat(" is not a valid TypedNumber"));
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.tagmanager.zzdl a(java.lang.String r3) {
        /*
            com.google.android.gms.tagmanager.zzdl r0 = new com.google.android.gms.tagmanager.zzdl     // Catch:{ NumberFormatException -> 0x000a }
            long r1 = java.lang.Long.parseLong(r3)     // Catch:{ NumberFormatException -> 0x000a }
            r0.<init>(r1)     // Catch:{ NumberFormatException -> 0x000a }
            return r0
        L_0x000a:
            com.google.android.gms.tagmanager.zzdl r0 = new com.google.android.gms.tagmanager.zzdl     // Catch:{ NumberFormatException -> 0x0014 }
            double r1 = java.lang.Double.parseDouble(r3)     // Catch:{ NumberFormatException -> 0x0014 }
            r0.<init>(r1)     // Catch:{ NumberFormatException -> 0x0014 }
            return r0
        L_0x0014:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r1 = " is not a valid TypedNumber"
            java.lang.String r3 = r3.concat(r1)
            r0.<init>(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzdl.a(java.lang.String):com.google.android.gms.tagmanager.zzdl");
    }

    /* renamed from: a */
    public int compareTo(zzdl zzdl) {
        return (!b() || !zzdl.b()) ? Double.compare(doubleValue(), zzdl.doubleValue()) : new Long(this.b).compareTo(Long.valueOf(zzdl.b));
    }

    public boolean a() {
        return !b();
    }

    public boolean b() {
        return this.c;
    }

    public byte byteValue() {
        return (byte) ((int) longValue());
    }

    public long c() {
        return b() ? this.b : (long) this.a;
    }

    public int d() {
        return (int) longValue();
    }

    public double doubleValue() {
        return b() ? (double) this.b : this.a;
    }

    public short e() {
        return (short) ((int) longValue());
    }

    public boolean equals(Object obj) {
        return (obj instanceof zzdl) && compareTo((zzdl) obj) == 0;
    }

    public float floatValue() {
        return (float) doubleValue();
    }

    public int hashCode() {
        return new Long(longValue()).hashCode();
    }

    public int intValue() {
        return d();
    }

    public long longValue() {
        return c();
    }

    public short shortValue() {
        return e();
    }

    public String toString() {
        return b() ? Long.toString(this.b) : Double.toString(this.a);
    }
}
