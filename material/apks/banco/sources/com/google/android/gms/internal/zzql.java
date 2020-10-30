package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class zzql extends GoogleApiClient {
    private final UnsupportedOperationException a;

    public zzql(String str) {
        this.a = new UnsupportedOperationException(str);
    }

    public ConnectionResult blockingConnect() {
        throw this.a;
    }

    public ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw this.a;
    }

    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        throw this.a;
    }

    public void connect() {
        throw this.a;
    }

    public void disconnect() {
        throw this.a;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        throw this.a;
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        throw this.a;
    }

    public boolean hasConnectedApi(@NonNull Api<?> api) {
        throw this.a;
    }

    public boolean isConnected() {
        throw this.a;
    }

    public boolean isConnecting() {
        throw this.a;
    }

    public boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
        throw this.a;
    }

    public boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        throw this.a;
    }

    public void reconnect() {
        throw this.a;
    }

    public void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        throw this.a;
    }

    public void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        throw this.a;
    }

    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        throw this.a;
    }

    public void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        throw this.a;
    }

    public void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        throw this.a;
    }
}
