package com.vusecurity.vumobiletokensdk;

public class ServerException extends Exception {
    int a;

    public ServerException(int i, String str) {
        super(str);
        this.a = i;
    }

    public int getCode() {
        return this.a;
    }
}
