package com.indra.httpclient.json;

import java.util.HashMap;

public class XMLTokener extends JSONTokener {
    public static final HashMap entity = new HashMap(8);

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String str) {
        super(str);
    }

    public String nextCDATA() {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            char next = next();
            if (end()) {
                throw syntaxError("Unclosed CDATA");
            }
            stringBuffer.append(next);
            int length = stringBuffer.length() - 3;
            if (length >= 0 && stringBuffer.charAt(length) == ']' && stringBuffer.charAt(length + 1) == ']' && stringBuffer.charAt(length + 2) == '>') {
                stringBuffer.setLength(length);
                return stringBuffer.toString();
            }
        }
    }

    public Object nextContent() {
        char next;
        do {
            next = next();
        } while (Character.isWhitespace(next));
        if (next == 0) {
            return null;
        }
        if (next == '<') {
            return XML.LT;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (next != '<' && next != 0) {
            if (next == '&') {
                stringBuffer.append(nextEntity(next));
            } else {
                stringBuffer.append(next);
            }
            next = next();
        }
        back();
        return stringBuffer.toString().trim();
    }

    public Object nextEntity(char c) {
        char next;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            next = next();
            if (!Character.isLetterOrDigit(next) && next != '#') {
                break;
            }
            stringBuffer.append(Character.toLowerCase(next));
        }
        if (next == ';') {
            String stringBuffer2 = stringBuffer.toString();
            Object obj = entity.get(stringBuffer2);
            if (obj != null) {
                return obj;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(c);
            sb.append(stringBuffer2);
            sb.append(";");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Missing ';' in XML entity: &");
        sb2.append(stringBuffer);
        throw syntaxError(sb2.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x001a A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object nextMeta() {
        /*
            r4 = this;
        L_0x0000:
            char r0 = r4.next()
            boolean r1 = java.lang.Character.isWhitespace(r0)
            if (r1 != 0) goto L_0x0000
            if (r0 == 0) goto L_0x005e
            r1 = 39
            if (r0 == r1) goto L_0x004c
            r2 = 47
            if (r0 == r2) goto L_0x0049
            switch(r0) {
                case 33: goto L_0x0046;
                case 34: goto L_0x004c;
                default: goto L_0x0017;
            }
        L_0x0017:
            switch(r0) {
                case 60: goto L_0x0043;
                case 61: goto L_0x0040;
                case 62: goto L_0x003d;
                case 63: goto L_0x003a;
                default: goto L_0x001a;
            }
        L_0x001a:
            char r0 = r4.next()
            boolean r3 = java.lang.Character.isWhitespace(r0)
            if (r3 == 0) goto L_0x0027
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        L_0x0027:
            if (r0 == 0) goto L_0x0034
            if (r0 == r1) goto L_0x0034
            if (r0 == r2) goto L_0x0034
            switch(r0) {
                case 33: goto L_0x0034;
                case 34: goto L_0x0034;
                default: goto L_0x0030;
            }
        L_0x0030:
            switch(r0) {
                case 60: goto L_0x0034;
                case 61: goto L_0x0034;
                case 62: goto L_0x0034;
                case 63: goto L_0x0034;
                default: goto L_0x0033;
            }
        L_0x0033:
            goto L_0x001a
        L_0x0034:
            r4.back()
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        L_0x003a:
            java.lang.Character r0 = com.indra.httpclient.json.XML.QUEST
            return r0
        L_0x003d:
            java.lang.Character r0 = com.indra.httpclient.json.XML.GT
            return r0
        L_0x0040:
            java.lang.Character r0 = com.indra.httpclient.json.XML.EQ
            return r0
        L_0x0043:
            java.lang.Character r0 = com.indra.httpclient.json.XML.LT
            return r0
        L_0x0046:
            java.lang.Character r0 = com.indra.httpclient.json.XML.BANG
            return r0
        L_0x0049:
            java.lang.Character r0 = com.indra.httpclient.json.XML.SLASH
            return r0
        L_0x004c:
            char r1 = r4.next()
            if (r1 != 0) goto L_0x0059
            java.lang.String r0 = "Unterminated string"
            com.indra.httpclient.json.JSONException r0 = r4.syntaxError(r0)
            throw r0
        L_0x0059:
            if (r1 != r0) goto L_0x004c
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            return r0
        L_0x005e:
            java.lang.String r0 = "Misshaped meta tag"
            com.indra.httpclient.json.JSONException r0 = r4.syntaxError(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.indra.httpclient.json.XMLTokener.nextMeta():java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0046, code lost:
        back();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004d, code lost:
        return r3.toString();
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f A[LOOP_START, PHI: r0 
      PHI: (r0v13 char) = (r0v0 char), (r0v14 char) binds: [B:9:0x001a, B:22:0x0042] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object nextToken() {
        /*
            r5 = this;
        L_0x0000:
            char r0 = r5.next()
            boolean r1 = java.lang.Character.isWhitespace(r0)
            if (r1 != 0) goto L_0x0000
            if (r0 == 0) goto L_0x0099
            r1 = 39
            if (r0 == r1) goto L_0x0070
            r2 = 47
            if (r0 == r2) goto L_0x006d
            switch(r0) {
                case 33: goto L_0x006a;
                case 34: goto L_0x0070;
                default: goto L_0x0017;
            }
        L_0x0017:
            switch(r0) {
                case 60: goto L_0x0063;
                case 61: goto L_0x0060;
                case 62: goto L_0x005d;
                case 63: goto L_0x005a;
                default: goto L_0x001a;
            }
        L_0x001a:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
        L_0x001f:
            r3.append(r0)
            char r0 = r5.next()
            boolean r4 = java.lang.Character.isWhitespace(r0)
            if (r4 == 0) goto L_0x0031
            java.lang.String r0 = r3.toString()
            return r0
        L_0x0031:
            if (r0 == 0) goto L_0x0055
            if (r0 == r1) goto L_0x004e
            if (r0 == r2) goto L_0x0046
            r4 = 91
            if (r0 == r4) goto L_0x0046
            r4 = 93
            if (r0 == r4) goto L_0x0046
            switch(r0) {
                case 33: goto L_0x0046;
                case 34: goto L_0x004e;
                default: goto L_0x0042;
            }
        L_0x0042:
            switch(r0) {
                case 60: goto L_0x004e;
                case 61: goto L_0x0046;
                case 62: goto L_0x0046;
                case 63: goto L_0x0046;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x001f
        L_0x0046:
            r5.back()
            java.lang.String r0 = r3.toString()
            return r0
        L_0x004e:
            java.lang.String r0 = "Bad character in a name"
            com.indra.httpclient.json.JSONException r0 = r5.syntaxError(r0)
            throw r0
        L_0x0055:
            java.lang.String r0 = r3.toString()
            return r0
        L_0x005a:
            java.lang.Character r0 = com.indra.httpclient.json.XML.QUEST
            return r0
        L_0x005d:
            java.lang.Character r0 = com.indra.httpclient.json.XML.GT
            return r0
        L_0x0060:
            java.lang.Character r0 = com.indra.httpclient.json.XML.EQ
            return r0
        L_0x0063:
            java.lang.String r0 = "Misplaced '<'"
            com.indra.httpclient.json.JSONException r0 = r5.syntaxError(r0)
            throw r0
        L_0x006a:
            java.lang.Character r0 = com.indra.httpclient.json.XML.BANG
            return r0
        L_0x006d:
            java.lang.Character r0 = com.indra.httpclient.json.XML.SLASH
            return r0
        L_0x0070:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
        L_0x0075:
            char r2 = r5.next()
            if (r2 != 0) goto L_0x0082
            java.lang.String r0 = "Unterminated string"
            com.indra.httpclient.json.JSONException r0 = r5.syntaxError(r0)
            throw r0
        L_0x0082:
            if (r2 != r0) goto L_0x0089
            java.lang.String r0 = r1.toString()
            return r0
        L_0x0089:
            r3 = 38
            if (r2 != r3) goto L_0x0095
            java.lang.Object r2 = r5.nextEntity(r2)
            r1.append(r2)
            goto L_0x0075
        L_0x0095:
            r1.append(r2)
            goto L_0x0075
        L_0x0099:
            java.lang.String r0 = "Misshaped element"
            com.indra.httpclient.json.JSONException r0 = r5.syntaxError(r0)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.indra.httpclient.json.XMLTokener.nextToken():java.lang.Object");
    }

    public boolean skipPast(String str) {
        boolean z;
        int length = str.length();
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            char next = next();
            if (next == 0) {
                return false;
            }
            cArr[i] = next;
        }
        int i2 = 0;
        while (true) {
            int i3 = i2;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    z = true;
                    break;
                } else if (cArr[i3] != str.charAt(i4)) {
                    z = false;
                    break;
                } else {
                    i3++;
                    if (i3 >= length) {
                        i3 -= length;
                    }
                    i4++;
                }
            }
            if (z) {
                return true;
            }
            char next2 = next();
            if (next2 == 0) {
                return false;
            }
            cArr[i2] = next2;
            i2++;
            if (i2 >= length) {
                i2 -= length;
            }
        }
    }
}
