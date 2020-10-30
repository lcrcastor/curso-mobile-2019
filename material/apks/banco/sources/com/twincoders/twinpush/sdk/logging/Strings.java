package com.twincoders.twinpush.sdk.logging;

import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class Strings {
    public static <T> String joinAnd(String str, String str2, Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        Iterator it = collection.iterator();
        StringBuffer stringBuffer = new StringBuffer(toString(it.next()));
        int i = 1;
        while (it.hasNext()) {
            Object next = it.next();
            if (notEmpty(next)) {
                i++;
                stringBuffer.append(i == collection.size() ? str2 : str);
                stringBuffer.append(toString(next));
            }
        }
        return stringBuffer.toString();
    }

    public static <T> String joinAnd(String str, String str2, T... tArr) {
        return joinAnd(str, str2, (Collection<T>) Arrays.asList(tArr));
    }

    public static <T> String join(String str, Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        Iterator it = collection.iterator();
        StringBuffer stringBuffer = new StringBuffer(toString(it.next()));
        while (it.hasNext()) {
            Object next = it.next();
            if (notEmpty(next)) {
                stringBuffer.append(str);
                stringBuffer.append(toString(next));
            }
        }
        return stringBuffer.toString();
    }

    public static <T> String join(String str, T... tArr) {
        return join(str, (Collection<T>) Arrays.asList(tArr));
    }

    public static String toString(InputStream inputStream) {
        StringWriter stringWriter = new StringWriter();
        copy(new InputStreamReader(inputStream), stringWriter);
        return stringWriter.toString();
    }

    public static String toString(Reader reader) {
        StringWriter stringWriter = new StringWriter();
        copy(reader, stringWriter);
        return stringWriter.toString();
    }

    public static int copy(Reader reader, Writer writer) {
        long copyLarge = copyLarge(reader, writer);
        if (copyLarge > 2147483647L) {
            return -1;
        }
        return (int) copyLarge;
    }

    public static long copyLarge(Reader reader, Writer writer) {
        try {
            char[] cArr = new char[4096];
            long j = 0;
            while (true) {
                int read = reader.read(cArr);
                if (-1 == read) {
                    return j;
                }
                writer.write(cArr, 0, read);
                j += (long) read;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Object obj) {
        return toString(obj, "");
    }

    public static String toString(Object obj, String str) {
        if (obj == null) {
            return str;
        }
        if (obj instanceof InputStream) {
            return toString((InputStream) obj);
        }
        if (obj instanceof Reader) {
            return toString((Reader) obj);
        }
        if (obj instanceof Object[]) {
            return join(", ", (T[]) (Object[]) obj);
        }
        return obj instanceof Collection ? join(", ", (Collection) obj) : obj.toString();
    }

    public static boolean isEmpty(Object obj) {
        return toString(obj).trim().length() == 0;
    }

    public static boolean notEmpty(Object obj) {
        return toString(obj).trim().length() != 0;
    }

    public static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toHexString(b & UnsignedBytes.MAX_VALUE));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String capitalize(String str) {
        String strings = toString((Object) str);
        if (strings.length() < 2) {
            return strings.length() >= 1 ? strings.toUpperCase(Locale.getDefault()) : strings;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(strings.substring(0, 1).toUpperCase(Locale.getDefault()));
        sb.append(strings.substring(1));
        return sb.toString();
    }

    public static boolean equals(Object obj, Object obj2) {
        return toString(obj).equals(toString(obj2));
    }

    public static boolean equalsIgnoreCase(Object obj, Object obj2) {
        return toString(obj).toLowerCase(Locale.getDefault()).equals(toString(obj2).toLowerCase(Locale.getDefault()));
    }

    public static String[] chunk(String str, int i) {
        if (isEmpty(str) || i == 0) {
            return new String[0];
        }
        int length = str.length();
        int i2 = ((length - 1) / i) + 1;
        String[] strArr = new String[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = i3 * i;
            int i5 = i4 + i;
            if (i5 >= length) {
                i5 = length;
            }
            strArr[i3] = str.substring(i4, i5);
        }
        return strArr;
    }

    public static String namedFormat(String str, Map<String, String> map) {
        for (String str2 : map.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append('$');
            sb.append(str2);
            str = str.replace(sb.toString(), (CharSequence) map.get(str2));
        }
        return str;
    }

    public static String namedFormat(String str, Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new InvalidParameterException("You must include one value for each parameter");
        }
        HashMap hashMap = new HashMap(objArr.length / 2);
        for (int i = 0; i < objArr.length; i += 2) {
            hashMap.put(toString(objArr[i]), toString(objArr[i + 1]));
        }
        return namedFormat(str, (Map<String, String>) hashMap);
    }
}
