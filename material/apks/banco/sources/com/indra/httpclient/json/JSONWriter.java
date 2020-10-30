package com.indra.httpclient.json;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import java.io.IOException;
import java.io.Writer;

public class JSONWriter {
    private boolean a = false;
    private final JSONObject[] b = new JSONObject[200];
    private int c = 0;
    protected char mode = 'i';
    protected Writer writer;

    public JSONWriter(Writer writer2) {
        this.writer = writer2;
    }

    private JSONWriter a(String str) {
        if (str == null) {
            throw new JSONException("Null pointer");
        } else if (this.mode == 'o' || this.mode == 'a') {
            try {
                if (this.a && this.mode == 'a') {
                    this.writer.write(44);
                }
                this.writer.write(str);
                if (this.mode == 'o') {
                    this.mode = 'k';
                }
                this.a = true;
                return this;
            } catch (IOException e) {
                throw new JSONException((Throwable) e);
            }
        } else {
            throw new JSONException("Value out of sequence.");
        }
    }

    public JSONWriter array() {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            a((JSONObject) null);
            a("[");
            this.a = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }

    private JSONWriter a(char c2, char c3) {
        if (this.mode != c2) {
            throw new JSONException(c2 == 'a' ? "Misplaced endArray." : "Misplaced endObject.");
        }
        a(c2);
        try {
            this.writer.write(c3);
            this.a = true;
            return this;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }

    public JSONWriter endArray() {
        return a('a', ']');
    }

    public JSONWriter endObject() {
        return a('k', '}');
    }

    public JSONWriter key(String str) {
        if (str == null) {
            throw new JSONException("Null key.");
        } else if (this.mode == 'k') {
            try {
                this.b[this.c - 1].putOnce(str, Boolean.TRUE);
                if (this.a) {
                    this.writer.write(44);
                }
                this.writer.write(JSONObject.quote(str));
                this.writer.write(58);
                this.a = false;
                this.mode = 'o';
                return this;
            } catch (IOException e) {
                throw new JSONException((Throwable) e);
            }
        } else {
            throw new JSONException("Misplaced key.");
        }
    }

    public JSONWriter object() {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        if (this.mode == 'o' || this.mode == 'a') {
            a("{");
            a(new JSONObject());
            this.a = false;
            return this;
        }
        throw new JSONException("Misplaced object.");
    }

    private void a(char c2) {
        if (this.c <= 0) {
            throw new JSONException("Nesting error.");
        }
        char c3 = 'k';
        if ((this.b[this.c + -1] == null ? 'a' : 'k') != c2) {
            throw new JSONException("Nesting error.");
        }
        this.c--;
        if (this.c == 0) {
            c3 = 'd';
        } else if (this.b[this.c - 1] == null) {
            c3 = 'a';
        }
        this.mode = c3;
    }

    private void a(JSONObject jSONObject) {
        if (this.c >= 200) {
            throw new JSONException("Nesting too deep.");
        }
        this.b[this.c] = jSONObject;
        this.mode = jSONObject == null ? 'a' : 'k';
        this.c++;
    }

    public JSONWriter value(boolean z) {
        return a(z ? "true" : Reintento.Reintento_Falso);
    }

    public JSONWriter value(double d) {
        return value((Object) new Double(d));
    }

    public JSONWriter value(long j) {
        return a(Long.toString(j));
    }

    public JSONWriter value(Object obj) {
        return a(JSONObject.valueToString(obj));
    }
}
