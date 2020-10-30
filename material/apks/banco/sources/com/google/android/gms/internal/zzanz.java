package com.google.android.gms.internal;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.lang.reflect.Field;
import java.util.Locale;

public enum zzanz implements zzaoa {
    IDENTITY {
        public String zzc(Field field) {
            return field.getName();
        }
    },
    UPPER_CAMEL_CASE {
        public String zzc(Field field) {
            return zzanz.b(field.getName());
        }
    },
    UPPER_CAMEL_CASE_WITH_SPACES {
        public String zzc(Field field) {
            return zzanz.b(zzanz.b(field.getName(), UtilsCuentas.SEPARAOR2));
        }
    },
    LOWER_CASE_WITH_UNDERSCORES {
        public String zzc(Field field) {
            return zzanz.b(field.getName(), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).toLowerCase(Locale.ENGLISH);
        }
    },
    LOWER_CASE_WITH_DASHES {
        public String zzc(Field field) {
            return zzanz.b(field.getName(), "-").toLowerCase(Locale.ENGLISH);
        }
    };

    private static String a(char c, String str, int i) {
        if (i >= str.length()) {
            return String.valueOf(c);
        }
        String valueOf = String.valueOf(str.substring(i));
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1);
        sb.append(c);
        sb.append(valueOf);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static String b(String str) {
        char charAt;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            charAt = str.charAt(i);
            if (i < str.length() - 1 && !Character.isLetter(charAt)) {
                sb.append(charAt);
                i++;
            }
        }
        if (i == str.length()) {
            return sb.toString();
        }
        if (!Character.isUpperCase(charAt)) {
            sb.append(a(Character.toUpperCase(charAt), str, i + 1));
            str = sb.toString();
        }
        return str;
    }

    /* access modifiers changed from: private */
    public static String b(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt) && sb.length() != 0) {
                sb.append(str2);
            }
            sb.append(charAt);
        }
        return sb.toString();
    }
}
