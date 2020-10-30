package net.danlew.android.joda;

import android.util.Log;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResUtils {
    private static Map<Class<?>, Map<String, Integer>> a = new ConcurrentHashMap();

    private static String a(String str) {
        File file = new File(str);
        ArrayList arrayList = new ArrayList();
        do {
            arrayList.add(file.getName());
            file = file.getParentFile();
        } while (file != null);
        StringBuffer stringBuffer = new StringBuffer();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            }
            stringBuffer.append((String) arrayList.get(size));
        }
        return stringBuffer.toString().replace('-', '_').replace(Constants.SYMBOL_POSITIVE, "plus").toLowerCase(Locale.US);
    }

    public static String getTzResource(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("joda_");
        sb.append(a(str));
        return sb.toString();
    }

    public static String getZoneInfoMapResource() {
        StringBuilder sb = new StringBuilder();
        sb.append("joda_");
        sb.append(a("ZoneInfoMap"));
        return sb.toString();
    }

    public static int getIdentifier(Class<?> cls, String str) {
        Map map;
        if (!a.containsKey(cls)) {
            map = new ConcurrentHashMap();
            a.put(cls, map);
        } else {
            map = (Map) a.get(cls);
        }
        if (map.containsKey(str)) {
            return ((Integer) map.get(str)).intValue();
        }
        try {
            int i = cls.getField(str).getInt(null);
            if (i != 0) {
                map.put(str, Integer.valueOf(i));
            }
            return i;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to retrieve identifier: type=");
            sb.append(cls);
            sb.append(" name=");
            sb.append(str);
            Log.e("JodaTimeAndroid", sb.toString(), e);
            return 0;
        }
    }
}
