package com.google.gson;

import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class JsonPrimitive extends JsonElement {
    private static final Class<?>[] a = {Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object b;

    public JsonPrimitive deepCopy() {
        return this;
    }

    public JsonPrimitive(Boolean bool) {
        a((Object) bool);
    }

    public JsonPrimitive(Number number) {
        a((Object) number);
    }

    public JsonPrimitive(String str) {
        a((Object) str);
    }

    public JsonPrimitive(Character ch) {
        a((Object) ch);
    }

    JsonPrimitive(Object obj) {
        a(obj);
    }

    /* access modifiers changed from: 0000 */
    public void a(Object obj) {
        if (obj instanceof Character) {
            this.b = String.valueOf(((Character) obj).charValue());
            return;
        }
        C$Gson$Preconditions.checkArgument((obj instanceof Number) || b(obj));
        this.b = obj;
    }

    public boolean isBoolean() {
        return this.b instanceof Boolean;
    }

    /* access modifiers changed from: 0000 */
    public Boolean a() {
        return (Boolean) this.b;
    }

    public boolean getAsBoolean() {
        if (isBoolean()) {
            return a().booleanValue();
        }
        return Boolean.parseBoolean(getAsString());
    }

    public boolean isNumber() {
        return this.b instanceof Number;
    }

    public Number getAsNumber() {
        return this.b instanceof String ? new LazilyParsedNumber((String) this.b) : (Number) this.b;
    }

    public boolean isString() {
        return this.b instanceof String;
    }

    public String getAsString() {
        if (isNumber()) {
            return getAsNumber().toString();
        }
        if (isBoolean()) {
            return a().toString();
        }
        return (String) this.b;
    }

    public double getAsDouble() {
        return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
    }

    public BigDecimal getAsBigDecimal() {
        return this.b instanceof BigDecimal ? (BigDecimal) this.b : new BigDecimal(this.b.toString());
    }

    public BigInteger getAsBigInteger() {
        if (this.b instanceof BigInteger) {
            return (BigInteger) this.b;
        }
        return new BigInteger(this.b.toString());
    }

    public float getAsFloat() {
        return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
    }

    public long getAsLong() {
        return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
    }

    public short getAsShort() {
        return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
    }

    public int getAsInt() {
        return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
    }

    public byte getAsByte() {
        return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
    }

    public char getAsCharacter() {
        return getAsString().charAt(0);
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

    public int hashCode() {
        if (this.b == null) {
            return 31;
        }
        if (a(this)) {
            long longValue = getAsNumber().longValue();
            return (int) (longValue ^ (longValue >>> 32));
        } else if (!(this.b instanceof Number)) {
            return this.b.hashCode();
        } else {
            long doubleToLongBits = Double.doubleToLongBits(getAsNumber().doubleValue());
            return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
        }
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        JsonPrimitive jsonPrimitive = (JsonPrimitive) obj;
        if (this.b == null) {
            if (jsonPrimitive.b != null) {
                z = false;
            }
            return z;
        } else if (a(this) && a(jsonPrimitive)) {
            if (getAsNumber().longValue() != jsonPrimitive.getAsNumber().longValue()) {
                z = false;
            }
            return z;
        } else if (!(this.b instanceof Number) || !(jsonPrimitive.b instanceof Number)) {
            return this.b.equals(jsonPrimitive.b);
        } else {
            double doubleValue = getAsNumber().doubleValue();
            double doubleValue2 = jsonPrimitive.getAsNumber().doubleValue();
            if (doubleValue != doubleValue2 && (!Double.isNaN(doubleValue) || !Double.isNaN(doubleValue2))) {
                z = false;
            }
            return z;
        }
    }

    private static boolean a(JsonPrimitive jsonPrimitive) {
        boolean z = false;
        if (!(jsonPrimitive.b instanceof Number)) {
            return false;
        }
        Number number = (Number) jsonPrimitive.b;
        if ((number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte)) {
            z = true;
        }
        return z;
    }
}
