package com.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.facebook.android.R;

public class LoginActivity extends Activity {
    private static final String a = "com.facebook.LoginActivity";
    private String b;
    private AuthorizationClient c;
    private AuthorizationRequest d;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.com_facebook_login_activity_layout);
        if (bundle != null) {
            this.b = bundle.getString("callingPackage");
            this.c = (AuthorizationClient) bundle.getSerializable("authorizationClient");
        } else {
            this.b = getCallingPackage();
            this.c = new AuthorizationClient();
            this.d = (AuthorizationRequest) getIntent().getSerializableExtra("request");
        }
        this.c.a((Activity) this);
        this.c.a((OnCompletedListener) new OnCompletedListener() {
            public void a(Result result) {
                LoginActivity.this.a(result);
            }
        });
        this.c.a((BackgroundProcessingListener) new BackgroundProcessingListener() {
            public void a() {
                LoginActivity.this.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(0);
            }

            public void b() {
                LoginActivity.this.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
            }
        });
    }

    /* access modifiers changed from: private */
    public void a(Result result) {
        this.d = null;
        int i = result.a == Code.CANCEL ? 0 : -1;
        Bundle bundle = new Bundle();
        bundle.putSerializable("com.facebook.LoginActivity:Result", result);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(i, intent);
        finish();
    }

    public void onResume() {
        super.onResume();
        if (this.b == null) {
            Log.e(a, "Cannot call LoginActivity with a null calling package. This can occur if the launchMode of the caller is singleInstance.");
            finish();
            return;
        }
        this.c.a(this.d);
    }

    public void onPause() {
        super.onPause();
        this.c.c();
        findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("callingPackage", this.b);
        bundle.putSerializable("authorizationClient", this.c);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        this.c.a(i, i2, intent);
    }

    static Bundle a(AuthorizationRequest authorizationRequest) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("request", authorizationRequest);
        return bundle;
    }
}
