package com.facebook;

public class FacebookServiceException extends FacebookException {
    private static final long serialVersionUID = 1;
    private final FacebookRequestError a;

    public FacebookServiceException(FacebookRequestError facebookRequestError, String str) {
        super(str);
        this.a = facebookRequestError;
    }

    public final FacebookRequestError getRequestError() {
        return this.a;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{FacebookServiceException: ");
        sb.append("httpResponseCode: ");
        sb.append(this.a.getRequestStatusCode());
        sb.append(", facebookErrorCode: ");
        sb.append(this.a.getErrorCode());
        sb.append(", facebookErrorType: ");
        sb.append(this.a.getErrorType());
        sb.append(", message: ");
        sb.append(this.a.getErrorMessage());
        sb.append("}");
        return sb.toString();
    }
}
