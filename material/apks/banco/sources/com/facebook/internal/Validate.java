package com.facebook.internal;

import java.util.Collection;

public final class Validate {
    public static void notNull(Object obj, String str) {
        if (obj == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Argument '");
            sb.append(str);
            sb.append("' cannot be null");
            throw new NullPointerException(sb.toString());
        }
    }

    public static <T> void notEmpty(Collection<T> collection, String str) {
        if (collection.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Container '");
            sb.append(str);
            sb.append("' cannot be empty");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static <T> void containsNoNulls(Collection<T> collection, String str) {
        notNull(collection, str);
        for (T t : collection) {
            if (t == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Container '");
                sb.append(str);
                sb.append("' cannot contain null values");
                throw new NullPointerException(sb.toString());
            }
        }
    }

    public static void containsNoNullOrEmpty(Collection<String> collection, String str) {
        notNull(collection, str);
        for (String str2 : collection) {
            if (str2 == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Container '");
                sb.append(str);
                sb.append("' cannot contain null values");
                throw new NullPointerException(sb.toString());
            } else if (str2.length() == 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Container '");
                sb2.append(str);
                sb2.append("' cannot contain empty values");
                throw new IllegalArgumentException(sb2.toString());
            }
        }
    }

    public static <T> void notEmptyAndContainsNoNulls(Collection<T> collection, String str) {
        containsNoNulls(collection, str);
        notEmpty(collection, str);
    }

    public static void notNullOrEmpty(String str, String str2) {
        if (Utility.isNullOrEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Argument '");
            sb.append(str2);
            sb.append("' cannot be null or empty");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static void oneOf(Object obj, String str, Object... objArr) {
        for (Object obj2 : objArr) {
            if (obj2 != null) {
                if (obj2.equals(obj)) {
                    return;
                }
            } else if (obj == null) {
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Argument '");
        sb.append(str);
        sb.append("' was not one of the allowed values");
        throw new IllegalArgumentException(sb.toString());
    }
}
