package ar.com.santander.rio.mbanking.utils.prisma;

import android.content.Context;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class CredinRedirectRequest {
    public static final int MY_SOCKET_TIMEOUT_MS = 3000;
    public static final String TOKEN_KEY = "token";

    public static void postTokenLogin(Context context, String str, String str2, Listener<String> listener, ErrorListener errorListener) {
        final String str3;
        RequestQueue newRequestQueue = Volley.newRequestQueue(context);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("token", str);
        } catch (JSONException e) {
            errorListener.onErrorResponse(new VolleyError(e.getMessage(), new Throwable()));
        }
        jSONObject.toString();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("token=");
            sb.append(URLEncoder.encode(str, "UTF-8"));
            str3 = sb.toString();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            str3 = null;
        }
        AnonymousClass1 r1 = new StringRequest(1, str2, listener, errorListener) {
            public byte[] getBody() {
                try {
                    if (str3 == null) {
                        return null;
                    }
                    return str3.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public Map<String, String> getHeaders() {
                HashMap hashMap = new HashMap();
                hashMap.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                hashMap.put("Accept-Encoding", "gzip, deflate, br");
                return hashMap;
            }
        };
        r1.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1.0f));
        newRequestQueue.add(r1);
    }
}
