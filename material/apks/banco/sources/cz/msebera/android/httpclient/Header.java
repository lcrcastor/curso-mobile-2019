package cz.msebera.android.httpclient;

public interface Header {
    HeaderElement[] getElements();

    String getName();

    String getValue();
}
