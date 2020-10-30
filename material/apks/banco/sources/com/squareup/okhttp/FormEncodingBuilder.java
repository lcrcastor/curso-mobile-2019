package com.squareup.okhttp;

import okio.Buffer;

public final class FormEncodingBuilder {
    private static final MediaType a = MediaType.parse("application/x-www-form-urlencoded");
    private final Buffer b = new Buffer();

    public FormEncodingBuilder add(String str, String str2) {
        if (this.b.size() > 0) {
            this.b.writeByte(38);
        }
        HttpUrl.a(this.b, str, 0, str.length(), " \"'<>#&=", false, true);
        this.b.writeByte(61);
        HttpUrl.a(this.b, str2, 0, str2.length(), " \"'<>#&=", false, true);
        return this;
    }

    public FormEncodingBuilder addEncoded(String str, String str2) {
        if (this.b.size() > 0) {
            this.b.writeByte(38);
        }
        HttpUrl.a(this.b, str, 0, str.length(), " \"'<>#&=", true, true);
        this.b.writeByte(61);
        HttpUrl.a(this.b, str2, 0, str2.length(), " \"'<>#&=", true, true);
        return this;
    }

    public RequestBody build() {
        if (this.b.size() != 0) {
            return RequestBody.create(a, this.b.snapshot());
        }
        throw new IllegalStateException("Form encoded body must have at least one part.");
    }
}
