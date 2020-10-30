package cz.msebera.android.httpclient.client.utils;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.bouncycastle.asn1.eac.EACTags;

@Immutable
public class URLEncodedUtils {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final BitSet a = new BitSet(256);
    private static final BitSet b = new BitSet(256);
    private static final BitSet c = new BitSet(256);
    private static final BitSet d = new BitSet(256);
    private static final BitSet e = new BitSet(256);
    private static final BitSet f = new BitSet(256);
    private static final BitSet g = new BitSet(256);

    public static List<NameValuePair> parse(URI uri, String str) {
        String rawQuery = uri.getRawQuery();
        if (rawQuery == null || rawQuery.isEmpty()) {
            return Collections.emptyList();
        }
        return parse(rawQuery, Charset.forName(str));
    }

    /* JADX INFO: finally extract failed */
    public static List<NameValuePair> parse(HttpEntity httpEntity) {
        ContentType contentType = ContentType.get(httpEntity);
        if (contentType == null || !contentType.getMimeType().equalsIgnoreCase("application/x-www-form-urlencoded")) {
            return Collections.emptyList();
        }
        long contentLength = httpEntity.getContentLength();
        Args.check(contentLength <= 2147483647L, "HTTP entity is too large");
        Charset charset = contentType.getCharset() != null ? contentType.getCharset() : HTTP.DEF_CONTENT_CHARSET;
        InputStream content = httpEntity.getContent();
        if (content == null) {
            return Collections.emptyList();
        }
        try {
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(contentLength > 0 ? (int) contentLength : 1024);
            InputStreamReader inputStreamReader = new InputStreamReader(content, charset);
            char[] cArr = new char[1024];
            while (true) {
                int read = inputStreamReader.read(cArr);
                if (read == -1) {
                    break;
                }
                charArrayBuffer.append(cArr, 0, read);
            }
            content.close();
            if (charArrayBuffer.length() == 0) {
                return Collections.emptyList();
            }
            return parse(charArrayBuffer, charset, '&');
        } catch (Throwable th) {
            content.close();
            throw th;
        }
    }

    public static boolean isEncoded(HttpEntity httpEntity) {
        Header contentType = httpEntity.getContentType();
        if (contentType != null) {
            HeaderElement[] elements = contentType.getElements();
            if (elements.length > 0) {
                return elements[0].getName().equalsIgnoreCase("application/x-www-form-urlencoded");
            }
        }
        return false;
    }

    @Deprecated
    public static void parse(List<NameValuePair> list, Scanner scanner, String str) {
        parse(list, scanner, "[&;]", str);
    }

    @Deprecated
    public static void parse(List<NameValuePair> list, Scanner scanner, String str, String str2) {
        String str3;
        String str4;
        scanner.useDelimiter(str);
        while (scanner.hasNext()) {
            String next = scanner.next();
            int indexOf = next.indexOf("=");
            if (indexOf != -1) {
                str4 = a(next.substring(0, indexOf).trim(), str2);
                str3 = a(next.substring(indexOf + 1).trim(), str2);
            } else {
                str4 = a(next.trim(), str2);
                str3 = null;
            }
            list.add(new BasicNameValuePair(str4, str3));
        }
    }

    public static List<NameValuePair> parse(String str, Charset charset) {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        return parse(charArrayBuffer, charset, '&', ';');
    }

    public static List<NameValuePair> parse(String str, Charset charset, char... cArr) {
        if (str == null) {
            return Collections.emptyList();
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        return parse(charArrayBuffer, charset, cArr);
    }

    public static List<NameValuePair> parse(CharArrayBuffer charArrayBuffer, Charset charset, char... cArr) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        TokenParser tokenParser = TokenParser.INSTANCE;
        BitSet bitSet = new BitSet();
        for (char c2 : cArr) {
            bitSet.set(c2);
        }
        ParserCursor parserCursor = new ParserCursor(0, charArrayBuffer.length());
        ArrayList arrayList = new ArrayList();
        while (!parserCursor.atEnd()) {
            bitSet.set(61);
            String parseToken = tokenParser.parseToken(charArrayBuffer, parserCursor, bitSet);
            String str = null;
            if (!parserCursor.atEnd()) {
                char charAt = charArrayBuffer.charAt(parserCursor.getPos());
                parserCursor.updatePos(parserCursor.getPos() + 1);
                if (charAt == '=') {
                    bitSet.clear(61);
                    str = tokenParser.parseValue(charArrayBuffer, parserCursor, bitSet);
                    if (!parserCursor.atEnd()) {
                        parserCursor.updatePos(parserCursor.getPos() + 1);
                    }
                }
            }
            if (!parseToken.isEmpty()) {
                arrayList.add(new BasicNameValuePair(d(parseToken, charset), d(str, charset)));
            }
        }
        return arrayList;
    }

    public static String format(List<? extends NameValuePair> list, String str) {
        return format(list, '&', str);
    }

    public static String format(List<? extends NameValuePair> list, char c2, String str) {
        StringBuilder sb = new StringBuilder();
        for (NameValuePair nameValuePair : list) {
            String b2 = b(nameValuePair.getName(), str);
            String b3 = b(nameValuePair.getValue(), str);
            if (sb.length() > 0) {
                sb.append(c2);
            }
            sb.append(b2);
            if (b3 != null) {
                sb.append("=");
                sb.append(b3);
            }
        }
        return sb.toString();
    }

    public static String format(Iterable<? extends NameValuePair> iterable, Charset charset) {
        return format(iterable, '&', charset);
    }

    public static String format(Iterable<? extends NameValuePair> iterable, char c2, Charset charset) {
        StringBuilder sb = new StringBuilder();
        for (NameValuePair nameValuePair : iterable) {
            String e2 = e(nameValuePair.getName(), charset);
            String e3 = e(nameValuePair.getValue(), charset);
            if (sb.length() > 0) {
                sb.append(c2);
            }
            sb.append(e2);
            if (e3 != null) {
                sb.append("=");
                sb.append(e3);
            }
        }
        return sb.toString();
    }

    static {
        for (int i = 97; i <= 122; i++) {
            a.set(i);
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            a.set(i2);
        }
        for (int i3 = 48; i3 <= 57; i3++) {
            a.set(i3);
        }
        a.set(95);
        a.set(45);
        a.set(46);
        a.set(42);
        g.or(a);
        a.set(33);
        a.set(EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE);
        a.set(39);
        a.set(40);
        a.set(41);
        b.set(44);
        b.set(59);
        b.set(58);
        b.set(36);
        b.set(38);
        b.set(43);
        b.set(61);
        c.or(a);
        c.or(b);
        d.or(a);
        d.set(47);
        d.set(59);
        d.set(58);
        d.set(64);
        d.set(38);
        d.set(61);
        d.set(43);
        d.set(36);
        d.set(44);
        f.set(59);
        f.set(47);
        f.set(63);
        f.set(58);
        f.set(64);
        f.set(38);
        f.set(61);
        f.set(43);
        f.set(36);
        f.set(44);
        f.set(91);
        f.set(93);
        e.or(f);
        e.or(a);
    }

    private static String a(String str, Charset charset, BitSet bitSet, boolean z) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        ByteBuffer encode = charset.encode(str);
        while (encode.hasRemaining()) {
            byte b2 = encode.get() & UnsignedBytes.MAX_VALUE;
            if (bitSet.get(b2)) {
                sb.append((char) b2);
            } else if (!z || b2 != 32) {
                sb.append(Constants.SYMBOL_PERCENTAGE);
                char upperCase = Character.toUpperCase(Character.forDigit((b2 >> 4) & 15, 16));
                char upperCase2 = Character.toUpperCase(Character.forDigit(b2 & Ascii.SI, 16));
                sb.append(upperCase);
                sb.append(upperCase2);
            } else {
                sb.append('+');
            }
        }
        return sb.toString();
    }

    private static String a(String str, Charset charset, boolean z) {
        if (str == null) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(str.length());
        CharBuffer wrap = CharBuffer.wrap(str);
        while (wrap.hasRemaining()) {
            char c2 = wrap.get();
            if (c2 == '%' && wrap.remaining() >= 2) {
                char c3 = wrap.get();
                char c4 = wrap.get();
                int digit = Character.digit(c3, 16);
                int digit2 = Character.digit(c4, 16);
                if (digit == -1 || digit2 == -1) {
                    allocate.put(37);
                    allocate.put((byte) c3);
                    allocate.put((byte) c4);
                } else {
                    allocate.put((byte) ((digit << 4) + digit2));
                }
            } else if (!z || c2 != '+') {
                allocate.put((byte) c2);
            } else {
                allocate.put(32);
            }
        }
        allocate.flip();
        return charset.decode(allocate).toString();
    }

    private static String a(String str, String str2) {
        if (str == null) {
            return null;
        }
        return a(str, str2 != null ? Charset.forName(str2) : Consts.UTF_8, true);
    }

    private static String d(String str, Charset charset) {
        if (str == null) {
            return null;
        }
        if (charset == null) {
            charset = Consts.UTF_8;
        }
        return a(str, charset, true);
    }

    private static String b(String str, String str2) {
        if (str == null) {
            return null;
        }
        return a(str, str2 != null ? Charset.forName(str2) : Consts.UTF_8, g, true);
    }

    private static String e(String str, Charset charset) {
        if (str == null) {
            return null;
        }
        if (charset == null) {
            charset = Consts.UTF_8;
        }
        return a(str, charset, g, true);
    }

    static String a(String str, Charset charset) {
        return a(str, charset, c, false);
    }

    static String b(String str, Charset charset) {
        return a(str, charset, e, false);
    }

    static String c(String str, Charset charset) {
        return a(str, charset, d, false);
    }
}
