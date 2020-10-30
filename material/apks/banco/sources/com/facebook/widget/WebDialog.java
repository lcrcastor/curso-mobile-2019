package com.facebook.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ar.com.santander.rio.mbanking.services.soap.versions.VRetryServer;
import com.facebook.FacebookDialogException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.android.Facebook;
import com.facebook.android.R;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;

public class WebDialog extends Dialog {
    public static final int DEFAULT_THEME = 16973840;
    private String a;
    /* access modifiers changed from: private */
    public String b;
    private OnCompleteListener c;
    /* access modifiers changed from: private */
    public WebView d;
    /* access modifiers changed from: private */
    public ProgressDialog e;
    /* access modifiers changed from: private */
    public ImageView f;
    /* access modifiers changed from: private */
    public FrameLayout g;
    private boolean h;
    /* access modifiers changed from: private */
    public boolean i;
    private boolean j;

    public static class Builder extends BuilderBase<Builder> {
        public /* bridge */ /* synthetic */ WebDialog build() {
            return super.build();
        }

        public Builder(Context context, String str) {
            super(context, str);
        }

        public Builder(Context context, Session session, String str, Bundle bundle) {
            super(context, session, str, bundle);
        }

        public Builder(Context context, String str, String str2, Bundle bundle) {
            super(context, str, str2, bundle);
        }
    }

    public static class BuilderBase<CONCRETE extends BuilderBase<?>> {
        private Context a;
        private Session b;
        private String c;
        private String d;
        private int e = WebDialog.DEFAULT_THEME;
        private OnCompleteListener f;
        private Bundle g;

        protected BuilderBase(Context context, String str) {
            Session activeSession = Session.getActiveSession();
            if (activeSession == null || !activeSession.isOpened()) {
                String metadataApplicationId = Utility.getMetadataApplicationId(context);
                if (metadataApplicationId != null) {
                    this.c = metadataApplicationId;
                } else {
                    throw new FacebookException("Attempted to create a builder without an open Active Session or a valid default Application ID.");
                }
            } else {
                this.b = activeSession;
            }
            a(context, str, null);
        }

        protected BuilderBase(Context context, Session session, String str, Bundle bundle) {
            Validate.notNull(session, SettingsJsonConstants.SESSION_KEY);
            if (!session.isOpened()) {
                throw new FacebookException("Attempted to use a Session that was not open.");
            }
            this.b = session;
            a(context, str, bundle);
        }

        protected BuilderBase(Context context, String str, String str2, Bundle bundle) {
            if (str == null) {
                str = Utility.getMetadataApplicationId(context);
            }
            Validate.notNullOrEmpty(str, "applicationId");
            this.c = str;
            a(context, str2, bundle);
        }

        public CONCRETE setTheme(int i) {
            this.e = i;
            return this;
        }

        public CONCRETE setOnCompleteListener(OnCompleteListener onCompleteListener) {
            this.f = onCompleteListener;
            return this;
        }

        public WebDialog build() {
            if (this.b == null || !this.b.isOpened()) {
                this.g.putString("app_id", this.c);
            } else {
                this.g.putString("app_id", this.b.getApplicationId());
                this.g.putString("access_token", this.b.getAccessToken());
            }
            WebDialog webDialog = new WebDialog(this.a, this.d, this.g, this.e, this.f);
            return webDialog;
        }

        /* access modifiers changed from: protected */
        public String getApplicationId() {
            return this.c;
        }

        /* access modifiers changed from: protected */
        public Context getContext() {
            return this.a;
        }

        /* access modifiers changed from: protected */
        public int getTheme() {
            return this.e;
        }

        /* access modifiers changed from: protected */
        public Bundle getParameters() {
            return this.g;
        }

        /* access modifiers changed from: protected */
        public OnCompleteListener getListener() {
            return this.f;
        }

        private void a(Context context, String str, Bundle bundle) {
            this.a = context;
            this.d = str;
            if (bundle != null) {
                this.g = bundle;
            } else {
                this.g = new Bundle();
            }
        }
    }

    class DialogWebViewClient extends WebViewClient {
        private DialogWebViewClient() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:28:0x0088  */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x008e  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean shouldOverrideUrlLoading(android.webkit.WebView r6, java.lang.String r7) {
            /*
                r5 = this;
                java.lang.String r6 = "FacebookSDK.WebDialog"
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Redirect URL: "
                r0.append(r1)
                r0.append(r7)
                java.lang.String r0 = r0.toString()
                com.facebook.internal.Utility.logd(r6, r0)
                com.facebook.widget.WebDialog r6 = com.facebook.widget.WebDialog.this
                java.lang.String r6 = r6.b
                boolean r6 = r7.startsWith(r6)
                r0 = 1
                if (r6 == 0) goto L_0x009e
                com.facebook.widget.WebDialog r6 = com.facebook.widget.WebDialog.this
                android.os.Bundle r6 = r6.parseResponseUri(r7)
                java.lang.String r7 = "error"
                java.lang.String r7 = r6.getString(r7)
                if (r7 != 0) goto L_0x0037
                java.lang.String r7 = "error_type"
                java.lang.String r7 = r6.getString(r7)
            L_0x0037:
                java.lang.String r1 = "error_msg"
                java.lang.String r1 = r6.getString(r1)
                if (r1 != 0) goto L_0x0045
                java.lang.String r1 = "error_description"
                java.lang.String r1 = r6.getString(r1)
            L_0x0045:
                java.lang.String r2 = "error_code"
                java.lang.String r2 = r6.getString(r2)
                boolean r3 = com.facebook.internal.Utility.isNullOrEmpty(r2)
                r4 = -1
                if (r3 != 0) goto L_0x0057
                int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x0057 }
                goto L_0x0058
            L_0x0057:
                r2 = -1
            L_0x0058:
                boolean r3 = com.facebook.internal.Utility.isNullOrEmpty(r7)
                if (r3 == 0) goto L_0x006c
                boolean r3 = com.facebook.internal.Utility.isNullOrEmpty(r1)
                if (r3 == 0) goto L_0x006c
                if (r2 != r4) goto L_0x006c
                com.facebook.widget.WebDialog r7 = com.facebook.widget.WebDialog.this
                r7.sendSuccessToListener(r6)
                goto L_0x009d
            L_0x006c:
                if (r7 == 0) goto L_0x0084
                java.lang.String r6 = "access_denied"
                boolean r6 = r7.equals(r6)
                if (r6 != 0) goto L_0x007e
                java.lang.String r6 = "OAuthAccessDeniedException"
                boolean r6 = r7.equals(r6)
                if (r6 == 0) goto L_0x0084
            L_0x007e:
                com.facebook.widget.WebDialog r6 = com.facebook.widget.WebDialog.this
                r6.sendCancelToListener()
                goto L_0x009d
            L_0x0084:
                r6 = 4201(0x1069, float:5.887E-42)
                if (r2 != r6) goto L_0x008e
                com.facebook.widget.WebDialog r6 = com.facebook.widget.WebDialog.this
                r6.sendCancelToListener()
                goto L_0x009d
            L_0x008e:
                com.facebook.FacebookRequestError r6 = new com.facebook.FacebookRequestError
                r6.<init>(r2, r7, r1)
                com.facebook.widget.WebDialog r7 = com.facebook.widget.WebDialog.this
                com.facebook.FacebookServiceException r2 = new com.facebook.FacebookServiceException
                r2.<init>(r6, r1)
                r7.sendErrorToListener(r2)
            L_0x009d:
                return r0
            L_0x009e:
                java.lang.String r6 = "fbconnect://cancel"
                boolean r6 = r7.startsWith(r6)
                if (r6 == 0) goto L_0x00ac
                com.facebook.widget.WebDialog r6 = com.facebook.widget.WebDialog.this
                r6.sendCancelToListener()
                return r0
            L_0x00ac:
                java.lang.String r6 = "touch"
                boolean r6 = r7.contains(r6)
                if (r6 == 0) goto L_0x00b6
                r6 = 0
                return r6
            L_0x00b6:
                com.facebook.widget.WebDialog r6 = com.facebook.widget.WebDialog.this
                android.content.Context r6 = r6.getContext()
                android.content.Intent r1 = new android.content.Intent
                java.lang.String r2 = "android.intent.action.VIEW"
                android.net.Uri r7 = android.net.Uri.parse(r7)
                r1.<init>(r2, r7)
                r6.startActivity(r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.widget.WebDialog.DialogWebViewClient.shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String):boolean");
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            WebDialog.this.sendErrorToListener(new FacebookDialogException(str, i, str2));
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            sslErrorHandler.cancel();
            WebDialog.this.sendErrorToListener(new FacebookDialogException(null, -11, null));
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            StringBuilder sb = new StringBuilder();
            sb.append("Webview loading URL: ");
            sb.append(str);
            Utility.logd("FacebookSDK.WebDialog", sb.toString());
            super.onPageStarted(webView, str, bitmap);
            if (!WebDialog.this.i) {
                WebDialog.this.e.show();
            }
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!WebDialog.this.i) {
                WebDialog.this.e.dismiss();
            }
            WebDialog.this.g.setBackgroundColor(0);
            WebDialog.this.d.setVisibility(0);
            WebDialog.this.f.setVisibility(0);
        }
    }

    public static class FeedDialogBuilder extends BuilderBase<FeedDialogBuilder> {
        public /* bridge */ /* synthetic */ WebDialog build() {
            return super.build();
        }

        public FeedDialogBuilder(Context context) {
            super(context, "feed");
        }

        public FeedDialogBuilder(Context context, Session session) {
            super(context, session, "feed", (Bundle) null);
        }

        public FeedDialogBuilder(Context context, Session session, Bundle bundle) {
            super(context, session, "feed", bundle);
        }

        public FeedDialogBuilder(Context context, String str, Bundle bundle) {
            super(context, str, "feed", bundle);
        }

        public FeedDialogBuilder setFrom(String str) {
            getParameters().putString("from", str);
            return this;
        }

        public FeedDialogBuilder setTo(String str) {
            getParameters().putString(VRetryServer.nameService, str);
            return this;
        }

        public FeedDialogBuilder setLink(String str) {
            getParameters().putString("link", str);
            return this;
        }

        public FeedDialogBuilder setPicture(String str) {
            getParameters().putString("picture", str);
            return this;
        }

        public FeedDialogBuilder setSource(String str) {
            getParameters().putString("source", str);
            return this;
        }

        public FeedDialogBuilder setName(String str) {
            getParameters().putString("name", str);
            return this;
        }

        public FeedDialogBuilder setCaption(String str) {
            getParameters().putString("caption", str);
            return this;
        }

        public FeedDialogBuilder setDescription(String str) {
            getParameters().putString("description", str);
            return this;
        }
    }

    public interface OnCompleteListener {
        void onComplete(Bundle bundle, FacebookException facebookException);
    }

    public static class RequestsDialogBuilder extends BuilderBase<RequestsDialogBuilder> {
        public /* bridge */ /* synthetic */ WebDialog build() {
            return super.build();
        }

        public RequestsDialogBuilder(Context context) {
            super(context, "apprequests");
        }

        public RequestsDialogBuilder(Context context, Session session) {
            super(context, session, "apprequests", (Bundle) null);
        }

        public RequestsDialogBuilder(Context context, Session session, Bundle bundle) {
            super(context, session, "apprequests", bundle);
        }

        public RequestsDialogBuilder(Context context, String str, Bundle bundle) {
            super(context, str, "apprequests", bundle);
        }

        public RequestsDialogBuilder setMessage(String str) {
            getParameters().putString("message", str);
            return this;
        }

        public RequestsDialogBuilder setTo(String str) {
            getParameters().putString(VRetryServer.nameService, str);
            return this;
        }

        public RequestsDialogBuilder setData(String str) {
            getParameters().putString("data", str);
            return this;
        }

        public RequestsDialogBuilder setTitle(String str) {
            getParameters().putString("title", str);
            return this;
        }
    }

    private int a(int i2, float f2, int i3, int i4) {
        int i5 = (int) (((float) i2) / f2);
        double d2 = 0.5d;
        if (i5 <= i3) {
            d2 = 1.0d;
        } else if (i5 < i4) {
            d2 = 0.5d + ((((double) (i4 - i5)) / ((double) (i4 - i3))) * 0.5d);
        }
        return (int) (((double) i2) * d2);
    }

    public WebDialog(Context context, String str) {
        this(context, str, DEFAULT_THEME);
    }

    public WebDialog(Context context, String str, int i2) {
        super(context, i2);
        this.b = Facebook.REDIRECT_URI;
        this.h = false;
        this.i = false;
        this.j = false;
        this.a = str;
    }

    public WebDialog(Context context, String str, Bundle bundle, int i2, OnCompleteListener onCompleteListener) {
        super(context, i2);
        this.b = Facebook.REDIRECT_URI;
        this.h = false;
        this.i = false;
        this.j = false;
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, Facebook.REDIRECT_URI);
        bundle.putString(ServerProtocol.DIALOG_PARAM_DISPLAY, ServerProtocol.FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH);
        String dialogAuthority = ServerProtocol.getDialogAuthority();
        StringBuilder sb = new StringBuilder();
        sb.append(ServerProtocol.getAPIVersion());
        sb.append("/");
        sb.append(ServerProtocol.DIALOG_PATH);
        sb.append(str);
        this.a = Utility.buildUri(dialogAuthority, sb.toString(), bundle).toString();
        this.c = onCompleteListener;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.c = onCompleteListener;
    }

    public OnCompleteListener getOnCompleteListener() {
        return this.c;
    }

    public void dismiss() {
        if (!this.j) {
            this.j = true;
            if (!this.h) {
                sendCancelToListener();
            }
            if (this.d != null) {
                this.d.stopLoading();
            }
            if (!this.i) {
                if (this.e.isShowing()) {
                    this.e.dismiss();
                }
                super.dismiss();
            }
        }
    }

    public void onDetachedFromWindow() {
        this.i = true;
        super.onDetachedFromWindow();
    }

    public void onAttachedToWindow() {
        this.i = false;
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.e = new ProgressDialog(getContext());
        this.e.requestWindowFeature(1);
        this.e.setMessage(getContext().getString(R.string.com_facebook_loading));
        this.e.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                WebDialog.this.dismiss();
            }
        });
        requestWindowFeature(1);
        this.g = new FrameLayout(getContext());
        a();
        getWindow().setGravity(17);
        getWindow().setSoftInputMode(16);
        b();
        a((this.f.getDrawable().getIntrinsicWidth() / 2) + 1);
        this.g.addView(this.f, new LayoutParams(-2, -2));
        setContentView(this.g);
    }

    /* access modifiers changed from: protected */
    public void setExpectedRedirectUrl(String str) {
        this.b = str;
    }

    public Bundle parseResponseUri(String str) {
        Uri parse = Uri.parse(str);
        Bundle parseUrlQueryString = Utility.parseUrlQueryString(parse.getQuery());
        parseUrlQueryString.putAll(Utility.parseUrlQueryString(parse.getFragment()));
        return parseUrlQueryString;
    }

    /* access modifiers changed from: protected */
    public boolean isListenerCalled() {
        return this.h;
    }

    /* access modifiers changed from: protected */
    public WebView getWebView() {
        return this.d;
    }

    private void a() {
        Display defaultDisplay = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        getWindow().setLayout(Math.min(a(displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.widthPixels : displayMetrics.heightPixels, displayMetrics.density, 480, 800), displayMetrics.widthPixels), Math.min(a(displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.heightPixels : displayMetrics.widthPixels, displayMetrics.density, 800, 1280), displayMetrics.heightPixels));
    }

    /* access modifiers changed from: protected */
    public void sendSuccessToListener(Bundle bundle) {
        if (this.c != null && !this.h) {
            this.h = true;
            this.c.onComplete(bundle, null);
            dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void sendErrorToListener(Throwable th) {
        FacebookException facebookException;
        if (this.c != null && !this.h) {
            this.h = true;
            if (th instanceof FacebookException) {
                facebookException = (FacebookException) th;
            } else {
                facebookException = new FacebookException(th);
            }
            this.c.onComplete(null, facebookException);
            dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void sendCancelToListener() {
        sendErrorToListener(new FacebookOperationCanceledException());
    }

    private void b() {
        this.f = new ImageView(getContext());
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                WebDialog.this.dismiss();
            }
        });
        this.f.setImageDrawable(getContext().getResources().getDrawable(R.drawable.com_facebook_close));
        this.f.setVisibility(4);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void a(int i2) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        this.d = new WebView(getContext()) {
            public void onWindowFocusChanged(boolean z) {
                try {
                    super.onWindowFocusChanged(z);
                } catch (NullPointerException unused) {
                }
            }
        };
        this.d.setVerticalScrollBarEnabled(false);
        this.d.setHorizontalScrollBarEnabled(false);
        this.d.setWebViewClient(new DialogWebViewClient());
        this.d.getSettings().setJavaScriptEnabled(true);
        this.d.loadUrl(this.a);
        this.d.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.d.setVisibility(4);
        this.d.getSettings().setSavePassword(false);
        this.d.getSettings().setSaveFormData(false);
        linearLayout.setPadding(i2, i2, i2, i2);
        linearLayout.addView(this.d);
        linearLayout.setBackgroundColor(-872415232);
        this.g.addView(linearLayout);
    }
}
