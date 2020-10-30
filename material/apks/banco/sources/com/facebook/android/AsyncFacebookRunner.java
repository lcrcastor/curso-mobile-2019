package com.facebook.android;

import android.content.Context;
import android.os.Bundle;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

@Deprecated
public class AsyncFacebookRunner {
    Facebook a;

    @Deprecated
    public interface RequestListener {
        void onComplete(String str, Object obj);

        void onFacebookError(FacebookError facebookError, Object obj);

        void onFileNotFoundException(FileNotFoundException fileNotFoundException, Object obj);

        void onIOException(IOException iOException, Object obj);

        void onMalformedURLException(MalformedURLException malformedURLException, Object obj);
    }

    public AsyncFacebookRunner(Facebook facebook) {
        this.a = facebook;
    }

    @Deprecated
    public void logout(final Context context, final RequestListener requestListener, final Object obj) {
        new Thread() {
            public void run() {
                try {
                    String a2 = AsyncFacebookRunner.this.a.a(context);
                    if (a2.length() != 0) {
                        if (!a2.equals(Reintento.Reintento_Falso)) {
                            requestListener.onComplete(a2, obj);
                            return;
                        }
                    }
                    requestListener.onFacebookError(new FacebookError("auth.expireSession failed"), obj);
                } catch (FileNotFoundException e) {
                    requestListener.onFileNotFoundException(e, obj);
                } catch (MalformedURLException e2) {
                    requestListener.onMalformedURLException(e2, obj);
                } catch (IOException e3) {
                    requestListener.onIOException(e3, obj);
                }
            }
        }.start();
    }

    @Deprecated
    public void logout(Context context, RequestListener requestListener) {
        logout(context, requestListener, null);
    }

    @Deprecated
    public void request(Bundle bundle, RequestListener requestListener, Object obj) {
        request(null, bundle, "GET", requestListener, obj);
    }

    @Deprecated
    public void request(Bundle bundle, RequestListener requestListener) {
        request(null, bundle, "GET", requestListener, null);
    }

    @Deprecated
    public void request(String str, RequestListener requestListener, Object obj) {
        request(str, new Bundle(), "GET", requestListener, obj);
    }

    @Deprecated
    public void request(String str, RequestListener requestListener) {
        request(str, new Bundle(), "GET", requestListener, null);
    }

    @Deprecated
    public void request(String str, Bundle bundle, RequestListener requestListener, Object obj) {
        request(str, bundle, "GET", requestListener, obj);
    }

    @Deprecated
    public void request(String str, Bundle bundle, RequestListener requestListener) {
        request(str, bundle, "GET", requestListener, null);
    }

    @Deprecated
    public void request(String str, Bundle bundle, String str2, RequestListener requestListener, Object obj) {
        final String str3 = str;
        final Bundle bundle2 = bundle;
        final String str4 = str2;
        final RequestListener requestListener2 = requestListener;
        final Object obj2 = obj;
        AnonymousClass2 r0 = new Thread() {
            public void run() {
                try {
                    requestListener2.onComplete(AsyncFacebookRunner.this.a.a(str3, bundle2, str4), obj2);
                } catch (FileNotFoundException e2) {
                    requestListener2.onFileNotFoundException(e2, obj2);
                } catch (MalformedURLException e3) {
                    requestListener2.onMalformedURLException(e3, obj2);
                } catch (IOException e4) {
                    requestListener2.onIOException(e4, obj2);
                }
            }
        };
        r0.start();
    }
}
