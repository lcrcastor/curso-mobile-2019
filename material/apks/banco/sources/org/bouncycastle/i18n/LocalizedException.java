package org.bouncycastle.i18n;

import java.util.Locale;

public class LocalizedException extends Exception {
    private Throwable a;
    protected ErrorBundle message;

    public LocalizedException(ErrorBundle errorBundle) {
        super(errorBundle.getText(Locale.getDefault()));
        this.message = errorBundle;
    }

    public LocalizedException(ErrorBundle errorBundle, Throwable th) {
        super(errorBundle.getText(Locale.getDefault()));
        this.message = errorBundle;
        this.a = th;
    }

    public Throwable getCause() {
        return this.a;
    }

    public ErrorBundle getErrorMessage() {
        return this.message;
    }
}
