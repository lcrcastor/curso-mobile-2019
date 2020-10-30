package cz.msebera.android.httpclient.impl.auth;

@Deprecated
public interface SpnegoTokenGenerator {
    byte[] generateSpnegoDERObject(byte[] bArr);
}
