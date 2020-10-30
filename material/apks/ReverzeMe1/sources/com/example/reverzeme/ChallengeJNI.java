package com.example.reverzeme;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.marcohc.toasteroid.Toasteroid;
import com.marcohc.toasteroid.Toasteroid.STYLES;

public class ChallengeJNI extends AppCompatActivity {
    EditText editTextPassword;

    public native String stringFromJNI();

    public native String stringOtherHalfKey(String str);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_hello_jni);
        ((Button) findViewById(R.id.buttonGo)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (ChallengeJNI.this.checkIfDeviceIsEmulator().booleanValue()) {
                    Toasteroid.show((Activity) ChallengeJNI.this, "This Device is not supported", STYLES.ERROR, 0);
                    return;
                }
                String pass = ChallengeJNI.this.stringFromJNI();
                Toasteroid.show((Activity) ChallengeJNI.this, "Verifying Password ...", STYLES.WARNING, 0);
                ChallengeJNI.this.editTextPassword = (EditText) ChallengeJNI.this.findViewById(R.id.editTextPassword);
                if (pass.equals(ChallengeJNI.this.editTextPassword.getText().toString())) {
                    Toasteroid.show((Activity) ChallengeJNI.this, "Your Flag is: " + ChallengeJNI.this.stringOtherHalfKey("s1r"), STYLES.ERROR, 1);
                } else {
                    Toasteroid.show((Activity) ChallengeJNI.this, "Wrong Password", STYLES.ERROR, 0);
                }
            }
        });
    }

    static {
        try {
            System.loadLibrary("hello-jni");
        } catch (UnsatisfiedLinkError ule) {
            Log.e("HelloC", "WARNING: Could not load native library: " + ule.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public Boolean checkIfDeviceIsEmulator() {
        if (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN) || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || ((Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT))) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }
}
