package com.indra.httpclient.json;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class JSONTokener {
    private long a;
    private boolean b;
    private long c;
    private long d;
    private char e;
    private Reader f;
    private boolean g;

    public static int dehexchar(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - TarjetasConstants.ULT_NUM_AMEX;
        }
        if (c2 >= 'A' && c2 <= 'F') {
            return c2 - '7';
        }
        if (c2 < 'a' || c2 > 'f') {
            return -1;
        }
        return c2 - 'W';
    }

    public JSONTokener(Reader reader) {
        if (!reader.markSupported()) {
            reader = new BufferedReader(reader);
        }
        this.f = reader;
        this.b = false;
        this.g = false;
        this.e = 0;
        this.c = 0;
        this.a = 1;
        this.d = 1;
    }

    public JSONTokener(InputStream inputStream) {
        this((Reader) new InputStreamReader(inputStream));
    }

    public JSONTokener(String str) {
        this((Reader) new StringReader(str));
    }

    public void back() {
        if (this.g || this.c <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.c--;
        this.a--;
        this.g = true;
        this.b = false;
    }

    public boolean end() {
        return this.b && !this.g;
    }

    public boolean more() {
        next();
        if (end()) {
            return false;
        }
        back();
        return true;
    }

    public char next() {
        int i = 0;
        if (this.g) {
            this.g = false;
            i = this.e;
        } else {
            try {
                int read = this.f.read();
                if (read <= 0) {
                    this.b = true;
                } else {
                    i = read;
                }
            } catch (IOException e2) {
                throw new JSONException((Throwable) e2);
            }
        }
        long j = 1;
        this.c++;
        if (this.e == 13) {
            this.d++;
            if (i == 10) {
                j = 0;
            }
            this.a = j;
        } else if (i == 10) {
            this.d++;
            this.a = 0;
        } else {
            this.a++;
        }
        this.e = (char) i;
        return this.e;
    }

    public char next(char c2) {
        char next = next();
        if (next == c2) {
            return next;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected '");
        sb.append(c2);
        sb.append("' and instead saw '");
        sb.append(next);
        sb.append("'");
        throw syntaxError(sb.toString());
    }

    public String next(int i) {
        if (i == 0) {
            return "";
        }
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = next();
            if (end()) {
                throw syntaxError("Substring bounds error");
            }
        }
        return new String(cArr);
    }

    public char nextClean() {
        char next;
        do {
            next = next();
            if (next == 0) {
                break;
            }
        } while (next <= ' ');
        return next;
    }

    public String nextString(char c2) {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            char next = next();
            if (next != 0 && next != 10 && next != 13) {
                if (next == '\\') {
                    char next2 = next();
                    if (next2 == '\"' || next2 == '\'' || next2 == '/' || next2 == '\\') {
                        stringBuffer.append(next2);
                    } else if (next2 == 'b') {
                        stringBuffer.append(8);
                    } else if (next2 == 'f') {
                        stringBuffer.append(12);
                    } else if (next2 == 'n') {
                        stringBuffer.append(10);
                    } else if (next2 != 'r') {
                        switch (next2) {
                            case 't':
                                stringBuffer.append(9);
                                break;
                            case 'u':
                                stringBuffer.append((char) Integer.parseInt(next(4), 16));
                                break;
                            default:
                                throw syntaxError("Illegal escape.");
                        }
                    } else {
                        stringBuffer.append(TokenParser.CR);
                    }
                } else if (next == c2) {
                    return stringBuffer.toString();
                } else {
                    stringBuffer.append(next);
                }
            }
        }
        throw syntaxError("Unterminated string");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String nextTo(char r4) {
        /*
            r3 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            char r1 = r3.next()
            if (r1 == r4) goto L_0x001a
            if (r1 == 0) goto L_0x001a
            r2 = 10
            if (r1 == r2) goto L_0x001a
            r2 = 13
            if (r1 != r2) goto L_0x0016
            goto L_0x001a
        L_0x0016:
            r0.append(r1)
            goto L_0x0005
        L_0x001a:
            if (r1 == 0) goto L_0x001f
            r3.back()
        L_0x001f:
            java.lang.String r4 = r0.toString()
            java.lang.String r4 = r4.trim()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.indra.httpclient.json.JSONTokener.nextTo(char):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String nextTo(java.lang.String r4) {
        /*
            r3 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            char r1 = r3.next()
            int r2 = r4.indexOf(r1)
            if (r2 >= 0) goto L_0x001e
            if (r1 == 0) goto L_0x001e
            r2 = 10
            if (r1 == r2) goto L_0x001e
            r2 = 13
            if (r1 != r2) goto L_0x001a
            goto L_0x001e
        L_0x001a:
            r0.append(r1)
            goto L_0x0005
        L_0x001e:
            if (r1 == 0) goto L_0x0023
            r3.back()
        L_0x0023:
            java.lang.String r4 = r0.toString()
            java.lang.String r4 = r4.trim()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.indra.httpclient.json.JSONTokener.nextTo(java.lang.String):java.lang.String");
    }

    public Object nextValue() {
        char nextClean = nextClean();
        if (nextClean == '\"' || nextClean == '\'') {
            return nextString(nextClean);
        }
        if (nextClean == '[') {
            back();
            return new JSONArray(this);
        } else if (nextClean != '{') {
            StringBuffer stringBuffer = new StringBuffer();
            while (nextClean >= ' ' && ",:]}/\\\"[{;=#".indexOf(nextClean) < 0) {
                stringBuffer.append(nextClean);
                nextClean = next();
            }
            back();
            String trim = stringBuffer.toString().trim();
            if (!"".equals(trim)) {
                return JSONObject.stringToValue(trim);
            }
            throw syntaxError("Missing value");
        } else {
            back();
            return new JSONObject(this);
        }
    }

    public char skipTo(char c2) {
        char next;
        try {
            long j = this.c;
            long j2 = this.a;
            long j3 = this.d;
            this.f.mark(1000000);
            do {
                next = next();
                if (next == 0) {
                    this.f.reset();
                    this.c = j;
                    this.a = j2;
                    this.d = j3;
                    return next;
                }
            } while (next != c2);
            back();
            return next;
        } catch (IOException e2) {
            throw new JSONException((Throwable) e2);
        }
    }

    public JSONException syntaxError(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(toString());
        return new JSONException(sb.toString());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" at ");
        sb.append(this.c);
        sb.append(" [character ");
        sb.append(this.a);
        sb.append(" line ");
        sb.append(this.d);
        sb.append("]");
        return sb.toString();
    }
}
