package com.facebook.android;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.json.JSONObject;

public final class Util {
    @Deprecated
    public static String encodePostBody(Bundle bundle, String str) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : bundle.keySet()) {
            Object obj = bundle.get(str2);
            if (obj instanceof String) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Content-Disposition: form-data; name=\"");
                sb2.append(str2);
                sb2.append("\"\r\n\r\n");
                sb2.append((String) obj);
                sb.append(sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append("\r\n--");
                sb3.append(str);
                sb3.append("\r\n");
                sb.append(sb3.toString());
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static String encodeUrl(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str : bundle.keySet()) {
            if (bundle.get(str) instanceof String) {
                if (z) {
                    z = false;
                } else {
                    sb.append("&");
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(URLEncoder.encode(str));
                sb2.append("=");
                sb2.append(URLEncoder.encode(bundle.getString(str)));
                sb.append(sb2.toString());
            }
        }
        return sb.toString();
    }

    @Deprecated
    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                try {
                    if (split2.length == 2) {
                        bundle.putString(URLDecoder.decode(split2[0], "UTF-8"), URLDecoder.decode(split2[1], "UTF-8"));
                    } else if (split2.length == 1) {
                        bundle.putString(URLDecoder.decode(split2[0], "UTF-8"), "");
                    }
                } catch (UnsupportedEncodingException unused) {
                }
            }
        }
        return bundle;
    }

    @Deprecated
    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str.replace("fbconnect", HttpHost.DEFAULT_SCHEME_NAME));
            Bundle decodeUrl = decodeUrl(url.getQuery());
            decodeUrl.putAll(decodeUrl(url.getRef()));
            return decodeUrl;
        } catch (MalformedURLException unused) {
            return new Bundle();
        }
    }

    @Deprecated
    public static String openUrl(String str, String str2, Bundle bundle) {
        String str3 = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
        String str4 = "\r\n";
        if (str2.equals("GET")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("?");
            sb.append(encodeUrl(bundle));
            str = sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(" URL: ");
        sb2.append(str);
        Utility.logd("Facebook-Util", sb2.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(System.getProperties().getProperty("http.agent"));
        sb3.append(" FacebookAndroidSDK");
        httpURLConnection.setRequestProperty("User-Agent", sb3.toString());
        if (!str2.equals("GET")) {
            Bundle bundle2 = new Bundle();
            for (String str5 : bundle.keySet()) {
                Object obj = bundle.get(str5);
                if (obj instanceof byte[]) {
                    bundle2.putByteArray(str5, (byte[]) obj);
                }
            }
            if (!bundle.containsKey("method")) {
                bundle.putString("method", str2);
            }
            if (bundle.containsKey("access_token")) {
                bundle.putString("access_token", URLDecoder.decode(bundle.getString("access_token")));
            }
            httpURLConnection.setRequestMethod("POST");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("multipart/form-data;boundary=");
            sb4.append(str3);
            httpURLConnection.setRequestProperty("Content-Type", sb4.toString());
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Connection", HTTP.CONN_KEEP_ALIVE);
            httpURLConnection.connect();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            try {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("--");
                sb5.append(str3);
                sb5.append(str4);
                bufferedOutputStream.write(sb5.toString().getBytes());
                bufferedOutputStream.write(encodePostBody(bundle, str3).getBytes());
                StringBuilder sb6 = new StringBuilder();
                sb6.append(str4);
                sb6.append("--");
                sb6.append(str3);
                sb6.append(str4);
                bufferedOutputStream.write(sb6.toString().getBytes());
                if (!bundle2.isEmpty()) {
                    for (String str6 : bundle2.keySet()) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("Content-Disposition: form-data; filename=\"");
                        sb7.append(str6);
                        sb7.append("\"");
                        sb7.append(str4);
                        bufferedOutputStream.write(sb7.toString().getBytes());
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("Content-Type: content/unknown");
                        sb8.append(str4);
                        sb8.append(str4);
                        bufferedOutputStream.write(sb8.toString().getBytes());
                        bufferedOutputStream.write(bundle2.getByteArray(str6));
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append(str4);
                        sb9.append("--");
                        sb9.append(str3);
                        sb9.append(str4);
                        bufferedOutputStream.write(sb9.toString().getBytes());
                    }
                }
                bufferedOutputStream.flush();
            } finally {
                bufferedOutputStream.close();
            }
        }
        try {
            return a(httpURLConnection.getInputStream());
        } catch (FileNotFoundException unused) {
            return a(httpURLConnection.getErrorStream());
        }
    }

    @Deprecated
    private static String a(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1000);
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                sb.append(readLine);
            } else {
                inputStream.close();
                return sb.toString();
            }
        }
    }

    @Deprecated
    public static JSONObject parseJson(String str) {
        if (str.equals(Reintento.Reintento_Falso)) {
            throw new FacebookError("request failed");
        }
        if (str.equals("true")) {
            str = "{value : true}";
        }
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("error")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("error");
            throw new FacebookError(jSONObject2.getString("message"), jSONObject2.getString("type"), 0);
        } else if (jSONObject.has(NativeProtocol.BRIDGE_ARG_ERROR_CODE) && jSONObject.has("error_msg")) {
            throw new FacebookError(jSONObject.getString("error_msg"), "", Integer.parseInt(jSONObject.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE)));
        } else if (jSONObject.has(NativeProtocol.BRIDGE_ARG_ERROR_CODE)) {
            throw new FacebookError("request failed", "", Integer.parseInt(jSONObject.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE)));
        } else if (jSONObject.has("error_msg")) {
            throw new FacebookError(jSONObject.getString("error_msg"));
        } else if (!jSONObject.has("error_reason")) {
            return jSONObject;
        } else {
            throw new FacebookError(jSONObject.getString("error_reason"));
        }
    }

    @Deprecated
    public static void showAlert(Context context, String str, String str2) {
        Builder builder = new Builder(context);
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.create().show();
    }
}
