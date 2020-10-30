package com.davemorrissey.labs.subscaleview.decoder;

import android.support.annotation.NonNull;

public interface DecoderFactory<T> {
    @NonNull
    T make();
}
