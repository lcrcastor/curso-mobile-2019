package com.twincoders.twinpush.sdk.communications.asyhttp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

@SuppressLint({"DefaultLocale"})
public class PersistentCookieStore implements CookieStore {
    private final ConcurrentHashMap<String, Cookie> a = new ConcurrentHashMap<>();
    private final SharedPreferences b;

    public PersistentCookieStore(Context context) {
        String[] split;
        this.b = context.getSharedPreferences("CookiePrefsFile", 0);
        String string = this.b.getString("tp_names", null);
        if (string != null) {
            for (String str : TextUtils.split(string, ",")) {
                SharedPreferences sharedPreferences = this.b;
                StringBuilder sb = new StringBuilder();
                sb.append("tp_cookie_");
                sb.append(str);
                String string2 = sharedPreferences.getString(sb.toString(), null);
                if (string2 != null) {
                    Cookie decodeCookie = decodeCookie(string2);
                    if (decodeCookie != null) {
                        this.a.put(str, decodeCookie);
                    }
                }
            }
            clearExpired(new Date());
        }
    }

    public void addCookie(Cookie cookie) {
        StringBuilder sb = new StringBuilder();
        sb.append(cookie.getName());
        sb.append(cookie.getDomain());
        String sb2 = sb.toString();
        if (!cookie.isExpired(new Date())) {
            this.a.put(sb2, cookie);
        } else {
            this.a.remove(sb2);
        }
        Editor edit = this.b.edit();
        edit.putString("tp_names", TextUtils.join(",", this.a.keySet()));
        StringBuilder sb3 = new StringBuilder();
        sb3.append("tp_cookie_");
        sb3.append(sb2);
        edit.putString(sb3.toString(), encodeCookie(new SerializableCookie(cookie)));
        edit.commit();
    }

    public void clear() {
        this.a.clear();
        Editor edit = this.b.edit();
        for (String str : this.a.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("tp_cookie_");
            sb.append(str);
            edit.remove(sb.toString());
        }
        edit.remove("tp_names");
        edit.commit();
    }

    public boolean clearExpired(Date date) {
        Editor edit = this.b.edit();
        boolean z = false;
        for (Entry entry : this.a.entrySet()) {
            String str = (String) entry.getKey();
            if (((Cookie) entry.getValue()).isExpired(date)) {
                this.a.remove(str);
                StringBuilder sb = new StringBuilder();
                sb.append("tp_cookie_");
                sb.append(str);
                edit.remove(sb.toString());
                z = true;
            }
        }
        if (z) {
            edit.putString("tp_names", TextUtils.join(",", this.a.keySet()));
        }
        edit.commit();
        return z;
    }

    public List<Cookie> getCookies() {
        return new ArrayList(this.a.values());
    }

    /* access modifiers changed from: protected */
    public String encodeCookie(SerializableCookie serializableCookie) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(byteArrayOutputStream).writeObject(serializableCookie);
            return byteArrayToHexString(byteArrayOutputStream.toByteArray());
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Cookie decodeCookie(String str) {
        try {
            return ((SerializableCookie) new ObjectInputStream(new ByteArrayInputStream(hexStringToByteArray(str))).readObject()).getCookie();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public String byteArrayToHexString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b2 : bArr) {
            byte b3 = b2 & UnsignedBytes.MAX_VALUE;
            if (b3 < 16) {
                stringBuffer.append(TarjetasConstants.ULT_NUM_AMEX);
            }
            stringBuffer.append(Integer.toHexString(b3));
        }
        return stringBuffer.toString().toUpperCase();
    }

    /* access modifiers changed from: protected */
    public byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
