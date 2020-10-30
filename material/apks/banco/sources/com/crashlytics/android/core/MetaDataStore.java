package com.crashlytics.android.core;

import com.appsflyer.AppsFlyerProperties;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

class MetaDataStore {
    private static final Charset a = Charset.forName("UTF-8");
    private final File b;

    public MetaDataStore(File file) {
        this.b = file;
    }

    public void a(String str, UserMetaData userMetaData) {
        File c = c(str);
        BufferedWriter bufferedWriter = null;
        try {
            String a2 = a(userMetaData);
            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(c), a));
            try {
                bufferedWriter2.write(a2);
                bufferedWriter2.flush();
                CommonUtils.closeOrLog(bufferedWriter2, "Failed to close user metadata file.");
            } catch (Exception e) {
                e = e;
                bufferedWriter = bufferedWriter2;
                try {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "Error serializing user metadata.", e);
                    CommonUtils.closeOrLog(bufferedWriter, "Failed to close user metadata file.");
                } catch (Throwable th) {
                    th = th;
                    CommonUtils.closeOrLog(bufferedWriter, "Failed to close user metadata file.");
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedWriter = bufferedWriter2;
                CommonUtils.closeOrLog(bufferedWriter, "Failed to close user metadata file.");
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error serializing user metadata.", e);
            CommonUtils.closeOrLog(bufferedWriter, "Failed to close user metadata file.");
        }
    }

    public UserMetaData a(String str) {
        File c = c(str);
        if (!c.exists()) {
            return UserMetaData.EMPTY;
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(c);
            try {
                UserMetaData e = e(CommonUtils.streamToString(fileInputStream2));
                CommonUtils.closeOrLog(fileInputStream2, "Failed to close user metadata file.");
                return e;
            } catch (Exception e2) {
                e = e2;
                fileInputStream = fileInputStream2;
                try {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
                    CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
                    return UserMetaData.EMPTY;
                } catch (Throwable th) {
                    th = th;
                    CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = fileInputStream2;
                CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
            return UserMetaData.EMPTY;
        }
    }

    public void a(String str, Map<String, String> map) {
        File d = d(str);
        BufferedWriter bufferedWriter = null;
        try {
            String a2 = a(map);
            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(d), a));
            try {
                bufferedWriter2.write(a2);
                bufferedWriter2.flush();
                CommonUtils.closeOrLog(bufferedWriter2, "Failed to close key/value metadata file.");
            } catch (Exception e) {
                e = e;
                bufferedWriter = bufferedWriter2;
                try {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "Error serializing key/value metadata.", e);
                    CommonUtils.closeOrLog(bufferedWriter, "Failed to close key/value metadata file.");
                } catch (Throwable th) {
                    th = th;
                    CommonUtils.closeOrLog(bufferedWriter, "Failed to close key/value metadata file.");
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedWriter = bufferedWriter2;
                CommonUtils.closeOrLog(bufferedWriter, "Failed to close key/value metadata file.");
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error serializing key/value metadata.", e);
            CommonUtils.closeOrLog(bufferedWriter, "Failed to close key/value metadata file.");
        }
    }

    public Map<String, String> b(String str) {
        File d = d(str);
        if (!d.exists()) {
            return Collections.emptyMap();
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(d);
            try {
                Map<String, String> f = f(CommonUtils.streamToString(fileInputStream2));
                CommonUtils.closeOrLog(fileInputStream2, "Failed to close user metadata file.");
                return f;
            } catch (Exception e) {
                e = e;
                fileInputStream = fileInputStream2;
                try {
                    Fabric.getLogger().e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
                    CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
                    return Collections.emptyMap();
                } catch (Throwable th) {
                    th = th;
                    CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = fileInputStream2;
                CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            Fabric.getLogger().e(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            CommonUtils.closeOrLog(fileInputStream, "Failed to close user metadata file.");
            return Collections.emptyMap();
        }
    }

    private File c(String str) {
        File file = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("user");
        sb.append(".meta");
        return new File(file, sb.toString());
    }

    private File d(String str) {
        File file = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("keys");
        sb.append(".meta");
        return new File(file, sb.toString());
    }

    private static UserMetaData e(String str) {
        JSONObject jSONObject = new JSONObject(str);
        return new UserMetaData(a(jSONObject, "userId"), a(jSONObject, "userName"), a(jSONObject, AppsFlyerProperties.USER_EMAIL));
    }

    private static String a(final UserMetaData userMetaData) {
        return new JSONObject() {
            {
                put("userId", userMetaData.f270id);
                put("userName", userMetaData.name);
                put(AppsFlyerProperties.USER_EMAIL, userMetaData.email);
            }
        }.toString();
    }

    private static Map<String, String> f(String str) {
        JSONObject jSONObject = new JSONObject(str);
        HashMap hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            hashMap.put(str2, a(jSONObject, str2));
        }
        return hashMap;
    }

    private static String a(Map<String, String> map) {
        return new JSONObject(map).toString();
    }

    private static String a(JSONObject jSONObject, String str) {
        if (!jSONObject.isNull(str)) {
            return jSONObject.optString(str, null);
        }
        return null;
    }
}
