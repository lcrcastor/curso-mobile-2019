package cz.msebera.android.httpclient;

public interface HttpRequestFactory {
    HttpRequest newHttpRequest(RequestLine requestLine);

    HttpRequest newHttpRequest(String str, String str2);
}
