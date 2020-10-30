package com.twincoders.twinpush.sdk.communications.requests.statistics;

import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.ErrorListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import java.util.HashMap;
import org.json.JSONObject;

public class ReportStatisticsRequest extends TwinPushRequest {
    DefaultListener c;

    public ReportStatisticsRequest(String str, String str2, double d, double d2, DefaultListener defaultListener) {
        super(str, str2);
        this.c = defaultListener;
        this.httpMethod = HttpMethod.POST;
        addSegmentParam("report_statistics");
        HashMap hashMap = new HashMap();
        hashMap.put("latitude", Double.valueOf(d));
        hashMap.put("longitude", Double.valueOf(d2));
        addParam("device", hashMap);
    }

    public ErrorListener getListener() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        this.c.onSuccess();
    }
}
