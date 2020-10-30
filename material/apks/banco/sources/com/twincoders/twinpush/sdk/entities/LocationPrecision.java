package com.twincoders.twinpush.sdk.entities;

import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import cz.msebera.android.httpclient.HttpStatus;

public enum LocationPrecision {
    FINE(1000, 5),
    HIGH(LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS, 10),
    MEDIUM(60000, 50),
    LOW(300000, HttpStatus.SC_INTERNAL_SERVER_ERROR),
    COARSE(3600000, 1000);
    
    long a;
    int b;

    private LocationPrecision(long j, int i) {
        this.a = j;
        this.b = i;
    }

    public long getMinUpdateTime() {
        return this.a;
    }

    public int getMinUpdateDistance() {
        return this.b;
    }
}
