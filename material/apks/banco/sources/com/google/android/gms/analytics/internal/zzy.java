package com.google.android.gms.analytics.internal;

import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzrs;
import org.joda.time.DateTimeConstants;

public final class zzy {
    public static zza<String> cA = zza.a("analytics.compression_strategy.k", zzo.GZIP.name());
    public static zza<Integer> cB = zza.a("analytics.max_hits_per_request.k", 20);
    public static zza<Integer> cC = zza.a("analytics.max_hit_length.k", 8192);
    public static zza<Integer> cD = zza.a("analytics.max_post_length.k", 8192);
    public static zza<Integer> cE = zza.a("analytics.max_batch_post_length", 8192);
    public static zza<String> cF = zza.a("analytics.fallback_responses.k", "404,502");
    public static zza<Integer> cG = zza.a("analytics.batch_retry_interval.seconds.k", 3600);
    public static zza<Long> cH = zza.a("analytics.service_monitor_interval", 86400000);
    public static zza<Integer> cI = zza.a("analytics.http_connection.connect_timeout_millis", (int) DateTimeConstants.MILLIS_PER_MINUTE);
    public static zza<Integer> cJ = zza.a("analytics.http_connection.read_timeout_millis", 61000);
    public static zza<Long> cK = zza.a("analytics.campaigns.time_limit", 86400000);
    public static zza<String> cL = zza.a("analytics.first_party_experiment_id", "");
    public static zza<Integer> cM = zza.a("analytics.first_party_experiment_variant", 0);
    public static zza<Boolean> cN = zza.a("analytics.test.disable_receiver", false);
    public static zza<Long> cO = zza.a("analytics.service_client.idle_disconnect_millis", (long) LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS, (long) LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS);
    public static zza<Long> cP = zza.a("analytics.service_client.connect_timeout_millis", (long) LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
    public static zza<Long> cQ = zza.a("analytics.service_client.second_connect_delay_millis", (long) LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
    public static zza<Long> cR = zza.a("analytics.service_client.unexpected_reconnect_millis", 60000);
    public static zza<Long> cS = zza.a("analytics.service_client.reconnect_throttle_millis", 1800000);
    public static zza<Long> cT = zza.a("analytics.monitoring.sample_period_millis", 86400000);
    public static zza<Long> cU = zza.a("analytics.initialization_warning_threshold", (long) LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
    public static zza<Boolean> ce = zza.a("analytics.service_enabled", false);
    public static zza<Boolean> cf = zza.a("analytics.service_client_enabled", true);
    public static zza<String> cg = zza.a("analytics.log_tag", "GAv4", "GAv4-SVC");
    public static zza<Long> ch = zza.a("analytics.max_tokens", 60);
    public static zza<Float> ci = zza.a("analytics.tokens_per_sec", 0.5f);
    public static zza<Integer> cj = zza.a("analytics.max_stored_hits", 2000, 20000);
    public static zza<Integer> ck = zza.a("analytics.max_stored_hits_per_app", 2000);
    public static zza<Integer> cl = zza.a("analytics.max_stored_properties_per_app", 100);
    public static zza<Long> cm = zza.a("analytics.local_dispatch_millis", 1800000, 120000);
    public static zza<Long> cn = zza.a("analytics.initial_local_dispatch_millis", (long) LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS, (long) LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
    public static zza<Long> co = zza.a("analytics.min_local_dispatch_millis", 120000);
    public static zza<Long> cp = zza.a("analytics.max_local_dispatch_millis", 7200000);
    public static zza<Long> cq = zza.a("analytics.dispatch_alarm_millis", 7200000);
    public static zza<Long> cr = zza.a("analytics.max_dispatch_alarm_millis", 32400000);
    public static zza<Integer> cs = zza.a("analytics.max_hits_per_dispatch", 20);
    public static zza<Integer> ct = zza.a("analytics.max_hits_per_batch", 20);
    public static zza<String> cu = zza.a("analytics.insecure_host", "http://www.google-analytics.com");
    public static zza<String> cv = zza.a("analytics.secure_host", "https://ssl.google-analytics.com");
    public static zza<String> cw = zza.a("analytics.simple_endpoint", "/collect");
    public static zza<String> cx = zza.a("analytics.batching_endpoint", "/batch");
    public static zza<Integer> cy = zza.a("analytics.max_get_length", 2036);

    /* renamed from: cz reason: collision with root package name */
    public static zza<String> f273cz = zza.a("analytics.batching_strategy.k", zzm.BATCH_BY_COUNT.name(), zzm.BATCH_BY_COUNT.name());

    public static final class zza<V> {
        private final V a;
        private final zzrs<V> b;

        private zza(zzrs<V> zzrs, V v) {
            zzac.zzy(zzrs);
            this.b = zzrs;
            this.a = v;
        }

        static zza<Float> a(String str, float f) {
            return a(str, f, f);
        }

        static zza<Float> a(String str, float f, float f2) {
            return new zza<>(zzrs.zza(str, Float.valueOf(f2)), Float.valueOf(f));
        }

        static zza<Integer> a(String str, int i) {
            return a(str, i, i);
        }

        static zza<Integer> a(String str, int i, int i2) {
            return new zza<>(zzrs.zza(str, Integer.valueOf(i2)), Integer.valueOf(i));
        }

        static zza<Long> a(String str, long j) {
            return a(str, j, j);
        }

        static zza<Long> a(String str, long j, long j2) {
            return new zza<>(zzrs.zza(str, Long.valueOf(j2)), Long.valueOf(j));
        }

        static zza<String> a(String str, String str2) {
            return a(str, str2, str2);
        }

        static zza<String> a(String str, String str2, String str3) {
            return new zza<>(zzrs.zzab(str, str3), str2);
        }

        static zza<Boolean> a(String str, boolean z) {
            return a(str, z, z);
        }

        static zza<Boolean> a(String str, boolean z, boolean z2) {
            return new zza<>(zzrs.zzm(str, z2), Boolean.valueOf(z));
        }

        public V get() {
            return this.a;
        }
    }
}
