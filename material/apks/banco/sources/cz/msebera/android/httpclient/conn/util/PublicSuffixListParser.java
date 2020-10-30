package cz.msebera.android.httpclient.conn.util;

import cz.msebera.android.httpclient.annotation.Immutable;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;

@Immutable
public final class PublicSuffixListParser {
    public PublicSuffixList parse(Reader reader) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder(256);
        boolean z = true;
        while (z) {
            z = a(bufferedReader, sb);
            String sb2 = sb.toString();
            if (!sb2.isEmpty() && !sb2.startsWith("//")) {
                if (sb2.startsWith(".")) {
                    sb2 = sb2.substring(1);
                }
                boolean startsWith = sb2.startsWith("!");
                if (startsWith) {
                    sb2 = sb2.substring(1);
                }
                if (startsWith) {
                    arrayList2.add(sb2);
                } else {
                    arrayList.add(sb2);
                }
            }
        }
        return new PublicSuffixList(arrayList, arrayList2);
    }

    private boolean a(Reader reader, StringBuilder sb) {
        boolean z = false;
        sb.setLength(0);
        boolean z2 = false;
        do {
            int read = reader.read();
            if (read != -1) {
                char c = (char) read;
                if (c != 10) {
                    if (Character.isWhitespace(c)) {
                        z2 = true;
                    }
                    if (!z2) {
                        sb.append(c);
                    }
                }
            }
            if (read != -1) {
                z = true;
            }
            return z;
        } while (sb.length() <= 256);
        return false;
    }
}
