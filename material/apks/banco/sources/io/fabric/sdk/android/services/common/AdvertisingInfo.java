package io.fabric.sdk.android.services.common;

class AdvertisingInfo {
    public final String a;
    public final boolean b;

    AdvertisingInfo(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdvertisingInfo advertisingInfo = (AdvertisingInfo) obj;
        if (this.b != advertisingInfo.b) {
            return false;
        }
        return this.a == null ? advertisingInfo.a == null : this.a.equals(advertisingInfo.a);
    }

    public int hashCode() {
        return ((this.a != null ? this.a.hashCode() : 0) * 31) + (this.b ? 1 : 0);
    }
}
