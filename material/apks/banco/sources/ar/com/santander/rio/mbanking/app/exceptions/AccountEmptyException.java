package ar.com.santander.rio.mbanking.app.exceptions;

import android.app.Dialog;

public class AccountEmptyException extends Exception {
    public Dialog getDialogException() {
        return null;
    }

    public AccountEmptyException(String str) {
        super(str);
    }
}
