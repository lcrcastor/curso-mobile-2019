package com.rsa.mobilesdk.sdk;

import android.location.Location;
import android.net.wifi.WifiInfo;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager.RESPONSE_CODES;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class DeviceInfo {
    Date a = null;
    String b = null;
    String c = null;
    String d = null;
    GeoLocationInfo e = null;
    String f = null;
    boolean g = false;
    String h = null;
    String i = null;
    String j = null;
    String k = null;
    String l = null;
    WiFiNetworksData m = null;
    String n = null;
    String o = null;
    String p = null;
    String q = null;
    String r = null;
    String s = null;
    String t = null;
    int u;
    int v;
    String w = null;
    private int x;

    class GeoLocationInfo {
        public double a;
        public boolean b;
        public double c;
        public boolean d;
        public double e;
        public boolean f;
        public double g;
        public boolean h;
        public double i;
        public boolean j;
        public long k;
        public double l;
        public boolean m;
        public double n;
        public boolean o;
        public int p;

        GeoLocationInfo() {
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            this.b = false;
            this.d = false;
            this.f = false;
            this.h = false;
            this.j = false;
            this.k = 0;
            this.m = false;
            this.o = false;
        }

        /* access modifiers changed from: 0000 */
        public void a(Location location, int i2) {
            a();
            this.p = i2;
            if (location != null) {
                this.c = location.getLatitude();
                this.d = true;
                this.a = location.getLongitude();
                this.b = true;
                if (location.hasAccuracy()) {
                    this.e = (double) location.getAccuracy();
                    this.f = true;
                }
                if (location.hasAltitude()) {
                    this.g = location.getAltitude();
                    this.h = true;
                    if (location.hasAccuracy()) {
                        this.i = (double) location.getAccuracy();
                        this.j = true;
                    }
                }
                if (location.hasBearing()) {
                    this.l = (double) location.getBearing();
                    this.m = true;
                }
                if (location.hasSpeed()) {
                    this.n = (double) location.getSpeed();
                    this.o = true;
                }
                this.k = location.getTime();
            }
        }
    }

    public class WiFiNetworksData {
        public String mBBSID = null;
        public String mChannel = null;
        public String mSSID = null;
        public int mSignalStrength = 0;
        public String mStationName = null;

        public WiFiNetworksData() {
        }

        public void set(WifiInfo wifiInfo) {
            this.mStationName = null;
            this.mBBSID = wifiInfo.getBSSID();
            this.mSignalStrength = wifiInfo.getRssi();
            this.mChannel = null;
            this.mSSID = wifiInfo.getSSID();
            if (this.mSSID == null) {
                return;
            }
            if (this.mSSID.contains("<") || this.mSSID.contains(">") || this.mSSID.equalsIgnoreCase("0x")) {
                this.mSSID = null;
            }
        }

        public void setNoPermission() {
            this.mStationName = RESPONSE_CODES.ERROR;
            this.mBBSID = RESPONSE_CODES.ERROR;
            this.mSignalStrength = -1;
            this.mChannel = RESPONSE_CODES.ERROR;
            this.mSSID = RESPONSE_CODES.ERROR;
        }

        public void reset() {
            this.mStationName = null;
            this.mBBSID = null;
            this.mSignalStrength = 0;
            this.mChannel = null;
            this.mSSID = null;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Station Name: ");
            stringBuffer.append(Utils.a(this.mStationName));
            stringBuffer.append("\n");
            stringBuffer.append("BBSID: ");
            stringBuffer.append(Utils.a(this.mBBSID));
            stringBuffer.append("\n");
            stringBuffer.append("Signal Strength: ");
            stringBuffer.append(this.mSignalStrength);
            stringBuffer.append("\n");
            stringBuffer.append("Channel: ");
            stringBuffer.append(Utils.a(this.mChannel));
            stringBuffer.append("\n");
            stringBuffer.append("SSID: ");
            stringBuffer.append(Utils.a(this.mSSID));
            stringBuffer.append("\n");
            return stringBuffer.toString();
        }
    }

    public DeviceInfo(int i2) {
        if (i2 == 2) {
            this.e = new GeoLocationInfo();
            this.m = new WiFiNetworksData();
        }
        this.x = i2;
    }

    public void a() {
        if (this.e != null) {
            this.e.a();
        }
        if (this.m != null) {
            this.m.reset();
        }
    }

    private String a(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (!(charAt == '\"' || charAt == '&' || charAt == '<' || charAt == '>' || charAt == '\'')) {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    public String a(boolean z, HashMap<String, Object> hashMap) {
        try {
            JSONObject jSONObject = new JSONObject();
            if (z) {
                jSONObject.put(JSONStringConstants.COLLECTION_TIMESTAMP, a(Utils.a(this.a)));
            }
            if (this.b != null) {
                jSONObject.put(JSONStringConstants.HARDWARE_ID, a(this.b));
            }
            if (this.c != null) {
                jSONObject.put(JSONStringConstants.SIM_ID, a(this.c));
            }
            if (this.d != null) {
                jSONObject.put(JSONStringConstants.PHONE_NUMBER, a(this.d));
            }
            if (this.e != null) {
                if (this.e.p == 0 && !this.e.b && !this.e.d && !this.e.m && !this.e.o && !this.e.f && !this.e.h && !this.e.j) {
                    this.e.p = 2;
                }
                JSONObject jSONObject2 = new JSONObject();
                if (this.e.b) {
                    jSONObject2.put(JSONStringConstants.LONGITUDE, Utils.a(this.e.a));
                }
                if (this.e.d) {
                    jSONObject2.put(JSONStringConstants.LATITUDE, Utils.a(this.e.c));
                }
                if (this.e.f) {
                    int round = (int) Math.round(this.e.e);
                    String str = JSONStringConstants.HORIZONTAL_ACCURACY;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(round);
                    jSONObject2.put(str, sb.toString());
                }
                if (this.e.h) {
                    int round2 = (int) Math.round(this.e.g);
                    String str2 = JSONStringConstants.ALTITUDE;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("");
                    sb2.append(round2);
                    jSONObject2.put(str2, sb2.toString());
                }
                if (this.e.j) {
                    int round3 = (int) Math.round(this.e.i);
                    String str3 = JSONStringConstants.ALTITUDE_ACCURACY;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(round3);
                    jSONObject2.put(str3, sb3.toString());
                }
                long j2 = this.e.k;
                String str4 = JSONStringConstants.GEO_LOCATION_TIMESTAMP;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("");
                sb4.append(j2);
                jSONObject2.put(str4, a(sb4.toString()));
                if (this.e.m) {
                    jSONObject2.put(JSONStringConstants.HEADING, Utils.a(this.e.l));
                }
                if (this.e.o) {
                    int round4 = (int) Math.round(this.e.n);
                    String str5 = JSONStringConstants.SPEED;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("");
                    sb5.append(round4);
                    jSONObject2.put(str5, sb5.toString());
                }
                String str6 = JSONStringConstants.STATUS;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("");
                sb6.append(this.e.p);
                jSONObject2.put(str6, sb6.toString());
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(jSONObject2);
                jSONObject.put(JSONStringConstants.GEO_LOCATION_INFO, jSONArray);
            }
            if (this.f != null) {
                jSONObject.put(JSONStringConstants.DEVICE_MODEL, a(this.f));
            }
            if (this.x == 1 || this.x == 2) {
                jSONObject.put(JSONStringConstants.MULTITASKING_SUPPORTED, this.g);
            }
            if (this.h != null) {
                jSONObject.put(JSONStringConstants.DEVICE_NAME, a(this.h));
            }
            if (this.i != null) {
                jSONObject.put(JSONStringConstants.DEVICE_SYSTEM_NAME, a(this.i));
            }
            if (this.j != null) {
                jSONObject.put(JSONStringConstants.DEVICE_SYSTEM_VERSION, a(this.j));
            }
            if (this.k != null) {
                jSONObject.put(JSONStringConstants.LANGUAGES, a(this.k));
            }
            if (this.l != null) {
                jSONObject.put(JSONStringConstants.WIFI_MAC_ADDRESS, a(this.l));
            }
            if (this.m != null) {
                JSONObject jSONObject3 = new JSONObject();
                if (this.m.mStationName != null) {
                    jSONObject3.put(JSONStringConstants.STATION_NAME, a(this.m.mStationName));
                }
                if (this.m.mBBSID != null) {
                    jSONObject3.put(JSONStringConstants.BBSID, a(this.m.mBBSID));
                }
                String str7 = JSONStringConstants.SIGNAL_STRENGTH;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("");
                sb7.append(this.m.mSignalStrength);
                jSONObject3.put(str7, sb7.toString());
                String str8 = JSONStringConstants.CHANNEL;
                StringBuilder sb8 = new StringBuilder();
                sb8.append("");
                sb8.append(a(this.m.mChannel));
                jSONObject3.put(str8, sb8.toString());
                if (this.m.mSSID != null) {
                    jSONObject3.put(JSONStringConstants.SSID, a(this.m.mSSID));
                }
                if (jSONObject3.length() > 0) {
                    jSONObject.put("WiFiNetworksData", jSONObject3);
                }
            }
            if (this.n != null) {
                jSONObject.put(JSONStringConstants.CELL_TOWER_ID, a(this.n));
            }
            if (this.o != null) {
                jSONObject.put(JSONStringConstants.LOCATION_AREA_CODE, a(this.o));
            }
            if (this.p != null) {
                jSONObject.put(JSONStringConstants.SCREEN_SIZE, a(this.p));
            }
            if (this.q != null) {
                jSONObject.put(JSONStringConstants.RSA_APPLICATION_KEY, a(this.q));
            }
            if (this.s != null) {
                jSONObject.put(JSONStringConstants.MCC, a(this.s));
            }
            if (this.t != null) {
                jSONObject.put(JSONStringConstants.MNC, a(this.t));
            }
            if (this.w != null) {
                jSONObject.put(JSONStringConstants.OS_ID, a(this.w));
            }
            jSONObject.put(JSONStringConstants.SDK_VERSION, a(BuildConfig.VERSION_NAME));
            jSONObject.put(JSONStringConstants.COMPROMISED, this.u);
            jSONObject.put(JSONStringConstants.EMULATOR, this.v);
            if (hashMap != null) {
                for (Entry entry : hashMap.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        jSONObject.put((String) entry.getKey(), a((String) entry.getValue()));
                    } else {
                        jSONObject.put((String) entry.getKey(), entry.getValue());
                    }
                }
            }
            return jSONObject.toString(0);
        } catch (JSONException e2) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("JSONStringFailure:");
            sb9.append(e2.toString());
            return sb9.toString();
        }
    }
}
