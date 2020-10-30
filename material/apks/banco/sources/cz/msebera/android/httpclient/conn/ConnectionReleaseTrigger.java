package cz.msebera.android.httpclient.conn;

public interface ConnectionReleaseTrigger {
    void abortConnection();

    void releaseConnection();
}
