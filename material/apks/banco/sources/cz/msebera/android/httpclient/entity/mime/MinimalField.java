package cz.msebera.android.httpclient.entity.mime;

public class MinimalField {
    private final String a;
    private final String b;

    public MinimalField(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String getName() {
        return this.a;
    }

    public String getBody() {
        return this.b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(": ");
        sb.append(this.b);
        return sb.toString();
    }
}
