package com.facebook.internal;

import android.os.Bundle;
import com.facebook.widget.FacebookDialog.PendingCall;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class PendingCallStore {
    private static PendingCallStore a;
    private Map<String, PendingCall> b = new HashMap();

    public static PendingCallStore getInstance() {
        if (a == null) {
            a();
        }
        return a;
    }

    private static synchronized void a() {
        synchronized (PendingCallStore.class) {
            if (a == null) {
                a = new PendingCallStore();
            }
        }
    }

    public void trackPendingCall(PendingCall pendingCall) {
        if (pendingCall != null) {
            this.b.put(pendingCall.getCallId().toString(), pendingCall);
        }
    }

    public void stopTrackingPendingCall(UUID uuid) {
        if (uuid != null) {
            this.b.remove(uuid.toString());
        }
    }

    public PendingCall getPendingCallById(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return (PendingCall) this.b.get(uuid.toString());
    }

    public void saveInstanceState(Bundle bundle) {
        bundle.putStringArrayList("com.facebook.internal.PendingCallStore.callIdArrayKey", new ArrayList(this.b.keySet()));
        for (PendingCall pendingCall : this.b.values()) {
            bundle.putParcelable(a(pendingCall.getCallId().toString()), pendingCall);
        }
    }

    public void restoreFromSavedInstanceState(Bundle bundle) {
        ArrayList stringArrayList = bundle.getStringArrayList("com.facebook.internal.PendingCallStore.callIdArrayKey");
        if (stringArrayList != null) {
            Iterator it = stringArrayList.iterator();
            while (it.hasNext()) {
                PendingCall pendingCall = (PendingCall) bundle.getParcelable(a((String) it.next()));
                if (pendingCall != null) {
                    this.b.put(pendingCall.getCallId().toString(), pendingCall);
                }
            }
        }
    }

    private String a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("com.facebook.internal.PendingCallStore.");
        sb.append(str);
        return sb.toString();
    }
}
