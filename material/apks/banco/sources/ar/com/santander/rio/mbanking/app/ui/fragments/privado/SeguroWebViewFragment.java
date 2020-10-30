package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.ui.activities.ContratarSeguroActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BotonSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSeguroBodyResponseBean;

public class SeguroWebViewFragment extends BaseFragment {
    public static final int FILECHOOSER_RESULTCODE = 1111;
    WebView a;
    Fragment b = new Fragment();
    private String c;
    /* access modifiers changed from: private */
    public AppCompatActivity d;
    private GetFirmaSeguroBodyResponseBean e;
    private BotonSeguroBean f = null;
    /* access modifiers changed from: private */
    public ValueCallback<Uri[]> g;

    @SuppressLint({"ValidFragment"})
    public SeguroWebViewFragment(GetFirmaSeguroBodyResponseBean getFirmaSeguroBodyResponseBean, BotonSeguroBean botonSeguroBean) {
        this.e = getFirmaSeguroBodyResponseBean;
        this.f = botonSeguroBean;
    }

    @SuppressLint({"ValidFragment"})
    public SeguroWebViewFragment(GetFirmaSeguroBodyResponseBean getFirmaSeguroBodyResponseBean, String str) {
        this.e = getFirmaSeguroBodyResponseBean;
        this.c = str;
    }

    public SeguroWebViewFragment() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof ContratarSeguroActivity) {
            this.d = (ContratarSeguroActivity) getActivity();
        } else {
            this.d = (SantanderRioMainActivity) getActivity();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_seguro_web_view, viewGroup, false);
        if (this.d instanceof ContratarSeguroActivity) {
            ((ContratarSeguroActivity) this.d).configActionBarDefault();
        } else if (this.d instanceof SantanderRioMainActivity) {
            ((SantanderRioMainActivity) this.d).configActionBarDefault();
        }
        b(inflate);
        return inflate;
    }

    private void b(View view) {
        this.a = (WebView) view.findViewById(R.id.wbv_seguro);
        if (this.f != null) {
            if (this.f.getUrl() != null) {
                try {
                    initWebView(this.f.getUrl().concat(this.e.getFirmaSeguros().getFirmaSeguro()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    dismissProgress();
                }
            }
        } else if (!this.c.isEmpty()) {
            try {
                initWebView(this.c.concat(this.e.getFirmaSeguros().getFirmaSeguro()));
            } catch (Exception e3) {
                e3.printStackTrace();
                dismissProgress();
            }
        }
    }

    public void initWebView(String str) {
        this.a.getSettings().setJavaScriptEnabled(true);
        this.a.getSettings().setDomStorageEnabled(true);
        this.a.getSettings().setSupportMultipleWindows(true);
        this.a.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                return super.shouldOverrideUrlLoading(webView, webResourceRequest);
            }

            public void onPageFinished(WebView webView, String str) {
                SeguroWebViewFragment.this.dismissProgress();
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                SeguroWebViewFragment.this.dismissProgress();
            }

            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                SeguroWebViewFragment.this.dismissProgress();
            }
        });
        this.a.setWebChromeClient(new WebChromeClient() {
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                SeguroWebViewFragment.this.g = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("*/*");
                SeguroWebViewFragment.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), SeguroWebViewFragment.FILECHOOSER_RESULTCODE);
                return true;
            }

            public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
                Message obtainMessage = webView.getHandler().obtainMessage();
                webView.requestFocusNodeHref(obtainMessage);
                String string = obtainMessage.getData().getString("url");
                if (string != null) {
                    Uri parse = Uri.parse(string);
                    if (!string.startsWith("http://") && !string.startsWith("https://")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("http://");
                        sb.append(string);
                        parse = Uri.parse(sb.toString());
                    }
                    Intent intent = new Intent("android.intent.action.VIEW", parse);
                    if (intent.resolveActivity(SeguroWebViewFragment.this.d.getPackageManager()) != null) {
                        SeguroWebViewFragment.this.startActivity(intent);
                    }
                }
                return true;
            }
        });
        this.a.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                if (str.contains("blob:")) {
                    str = str.replace("blob:", "");
                }
                SeguroWebViewFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        });
        this.a.loadUrl(str);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1111) {
            return;
        }
        if (i2 == 0) {
            this.g.onReceiveValue(null);
        } else if (i2 == -1 && intent.getData() != null) {
            this.g.onReceiveValue(new Uri[]{intent.getData()});
        }
    }

    public void onBackPressed() {
        y();
    }

    private void y() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
