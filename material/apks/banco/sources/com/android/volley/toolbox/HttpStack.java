package com.android.volley.toolbox;

import com.android.volley.Request;
import java.util.Map;
import org.apache.http.HttpResponse;

public interface HttpStack {
    HttpResponse performRequest(Request<?> request, Map<String, String> map);
}
