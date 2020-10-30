package com.vusecurity.vumobiletokensdk;

import android.os.AsyncTask;
import cz.msebera.android.httpclient.HttpStatus;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum RequestManager {
    INSTANCE;
    
    private SeedCallback b;
    private TimeDeltaCallback c;

    class SeedRequestAsyncTask extends AsyncTask<String, Void, String> {
        private int b;

        private SeedRequestAsyncTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            String str = strArr[0];
            String str2 = strArr[1];
            String str3 = strArr[2];
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("/vuserver/activation.php?cupon=");
            sb.append(str2);
            sb.append("&bank=");
            sb.append(str3);
            sb.append("&callback=call");
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(sb.toString()).openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                this.b = httpURLConnection.getResponseCode();
                return (this.b < 200 || this.b >= 300) ? "Error getting response" : a(httpURLConnection.getInputStream(), HttpStatus.SC_INTERNAL_SERVER_ERROR);
            } catch (MalformedURLException e) {
                return e.getMessage();
            } catch (IOException e2) {
                return e2.getMessage();
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            int i;
            String str2;
            super.onPostExecute(str);
            int indexOf = str.indexOf("([\"") + 3;
            int indexOf2 = str.indexOf("\"])");
            if (indexOf <= 0 || indexOf >= indexOf2) {
                if (str.contains("URL Incorrecta - contacte Soporte Security")) {
                    i = VUMobileTokenSDK.ERROR_CODIGO_ASOCIACION_INCORRECTO;
                    str2 = "Código de Asociación incorrecto";
                } else {
                    str2 = str;
                    i = VUMobileTokenSDK.ERROR_CUPON_EXPIRADO;
                }
                RequestManager.this.a(i, str2);
                return;
            }
            RequestManager.this.a(str.substring(indexOf, indexOf2));
        }

        private String a(InputStream inputStream, int i) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            char[] cArr = new char[i];
            inputStreamReader.read(cArr);
            return new String(cArr);
        }
    }

    class TimeDeltaAsyncTask extends AsyncTask<String, Void, String> {
        private int b;

        private TimeDeltaAsyncTask() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doInBackground(String... strArr) {
            String str = strArr[0];
            String str2 = strArr[1];
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("/vuserver/activation.php?timesyncmanual=do&bank=");
            sb.append(str2);
            sb.append("&callback=call");
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(sb.toString()).openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                this.b = httpURLConnection.getResponseCode();
                return (this.b < 200 || this.b >= 300) ? "Error getting response" : a(httpURLConnection.getInputStream(), HttpStatus.SC_INTERNAL_SERVER_ERROR);
            } catch (MalformedURLException e) {
                return e.getMessage();
            } catch (IOException e2) {
                return e2.getMessage();
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            int indexOf = str.indexOf("([") + 2;
            int indexOf2 = str.indexOf("])");
            if (indexOf <= 0 || indexOf >= indexOf2) {
                RequestManager.this.b(VUMobileTokenSDK.ERROR_TIME_SYNC, str);
                return;
            }
            String[] split = str.substring(indexOf, indexOf2).split(",");
            try {
                RequestManager.this.a(Long.valueOf(Long.parseLong(split[0])).longValue(), Integer.valueOf(Integer.parseInt(split[1])).intValue());
            } catch (NumberFormatException unused) {
                RequestManager.this.b(VUMobileTokenSDK.ERROR_TIME_SYNC, str);
            }
        }

        private String a(InputStream inputStream, int i) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            char[] cArr = new char[i];
            inputStreamReader.read(cArr);
            return new String(cArr);
        }
    }

    public void a(SeedCallback seedCallback, String str, String str2, String str3) {
        this.b = seedCallback;
        new SeedRequestAsyncTask().execute(new String[]{str, str2, str3});
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        this.b.onResultsAvailable(str);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i, String str) {
        this.b.onError(new ServerException(i, str));
    }

    public void a(TimeDeltaCallback timeDeltaCallback, String str, String str2) {
        this.c = timeDeltaCallback;
        new TimeDeltaAsyncTask().execute(new String[]{str, str2});
    }

    /* access modifiers changed from: 0000 */
    public void a(long j, int i) {
        this.c.onResultsAvailable(Long.valueOf(((j / 1000) / 60) - ((System.currentTimeMillis() / 1000) / 60)).longValue(), i);
    }

    /* access modifiers changed from: 0000 */
    public void b(int i, String str) {
        this.c.onError(new ServerException(i, str));
    }
}
