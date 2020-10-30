package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
enum PublicSuffixType {
    PRIVATE(':', ','),
    ICANN('!', '?');
    
    private final char c;
    private final char d;

    private PublicSuffixType(char c2, char c3) {
        this.c = c2;
        this.d = c3;
    }

    /* access modifiers changed from: 0000 */
    public char a() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public char b() {
        return this.c;
    }

    static PublicSuffixType a(char c2) {
        PublicSuffixType[] values;
        for (PublicSuffixType publicSuffixType : values()) {
            if (publicSuffixType.b() == c2 || publicSuffixType.a() == c2) {
                return publicSuffixType;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No enum corresponding to given code: ");
        sb.append(c2);
        throw new IllegalArgumentException(sb.toString());
    }
}
