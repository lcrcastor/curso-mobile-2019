package cz.msebera.android.httpclient;

import java.nio.charset.CharacterCodingException;

public class MessageConstraintException extends CharacterCodingException {
    private static final long serialVersionUID = 6077207720446368695L;
    private final String a;

    public MessageConstraintException(String str) {
        this.a = str;
    }

    public String getMessage() {
        return this.a;
    }
}
