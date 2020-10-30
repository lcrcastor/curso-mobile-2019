package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public final class Tile extends AbstractSafeParcelable {
    public static final zzo CREATOR = new zzo();
    private final int a;
    public final byte[] data;
    public final int height;
    public final int width;

    Tile(int i, int i2, int i3, byte[] bArr) {
        this.a = i;
        this.width = i2;
        this.height = i3;
        this.data = bArr;
    }

    public Tile(int i, int i2, byte[] bArr) {
        this(1, i, i2, bArr);
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzo.a(this, parcel, i);
    }
}
