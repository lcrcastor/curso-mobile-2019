package com.google.android.gms.internal;

import java.math.BigInteger;

public final class zzaon extends zzaoh {
    private static final Class<?>[] a = {Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object b;

    public zzaon(Boolean bool) {
        a((Object) bool);
    }

    public zzaon(Number number) {
        a((Object) number);
    }

    zzaon(Object obj) {
        a(obj);
    }

    public zzaon(String str) {
        a((Object) str);
    }

    private static boolean a(zzaon zzaon) {
        if (!(zzaon.b instanceof Number)) {
            return false;
        }
        Number number = (Number) zzaon.b;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    private static boolean b(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        Class cls = obj.getClass();
        for (Class<?> isAssignableFrom : a) {
            if (isAssignableFrom.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public Boolean a() {
        return (Boolean) this.b;
    }

    /* access modifiers changed from: 0000 */
    public void a(Object obj) {
        if (obj instanceof Character) {
            obj = String.valueOf(((Character) obj).charValue());
        } else {
            zzaoz.zzbs((obj instanceof Number) || b(obj));
        }
        this.b = obj;
    }

    public Number aQ() {
        return this.b instanceof String ? new zzape((String) this.b) : (Number) this.b;
    }

    public String aR() {
        return bb() ? aQ().toString() : ba() ? a().toString() : (String) this.b;
    }

    public boolean ba() {
        return this.b instanceof Boolean;
    }

    public boolean bb() {
        return this.b instanceof Number;
    }

    public boolean bc() {
        return this.b instanceof String;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzaon zzaon = (zzaon) obj;
        if (this.b == null) {
            return zzaon.b == null;
        }
        if (a(this) && a(zzaon)) {
            return aQ().longValue() == zzaon.aQ().longValue();
        }
        if (!(this.b instanceof Number) || !(zzaon.b instanceof Number)) {
            return this.b.equals(zzaon.b);
        }
        double doubleValue = aQ().doubleValue();
        double doubleValue2 = zzaon.aQ().doubleValue();
        if (doubleValue != doubleValue2) {
            if (Double.isNaN(doubleValue) && Double.isNaN(doubleValue2)) {
                return true;
            }
            z = false;
        }
        return z;
    }

    public boolean getAsBoolean() {
        return ba() ? a().booleanValue() : Boolean.parseBoolean(aR());
    }

    public double getAsDouble() {
        return bb() ? aQ().doubleValue() : Double.parseDouble(aR());
    }

    public int getAsInt() {
        return bb() ? aQ().intValue() : Integer.parseInt(aR());
    }

    public long getAsLong() {
        return bb() ? aQ().longValue() : Long.parseLong(aR());
    }

    public int hashCode() {
        if (this.b == null) {
            return 31;
        }
        if (a(this)) {
            long longValue = aQ().longValue();
            return (int) (longValue ^ (longValue >>> 32));
        } else if (!(this.b instanceof Number)) {
            return this.b.hashCode();
        } else {
            long doubleToLongBits = Double.doubleToLongBits(aQ().doubleValue());
            return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
        }
    }
}
