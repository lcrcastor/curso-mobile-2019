package com.zurich.lcd_test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import com.zurich.lockview.PatternLockView;
import com.zurich.lockview.PatternLockView.Dot;
import com.zurich.lockview.listener.PatternLockViewListener;
import com.zurich.lockview.utils.PatternLockUtils;
import com.zurich.lockview.utils.ResourceUtils;
import java.util.List;

public class TouchTestActivity extends AppCompatActivity implements PatternLockViewListener {
    public static String messageKO = "";
    public static String messageOK = "";
    protected PatternLockView mPatternLockView;
    /* access modifiers changed from: private */
    public TextView n;
    private TextView o;
    /* access modifiers changed from: private */
    public long p = 30000;
    /* access modifiers changed from: private */
    public long q = this.p;
    /* access modifiers changed from: private */
    public boolean r = false;
    private TextView s;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.lcd_screen_layout);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(6);
        this.mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        this.n = (TextView) findViewById(R.id.counterText);
        this.s = (TextView) findViewById(R.id.counterTextLabel);
        this.o = (TextView) findViewById(R.id.resultText);
        this.mPatternLockView.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                decorView.setSystemUiVisibility(6);
            }
        });
    }

    public void setupTimer() {
        this.mPatternLockView.addPatternLockListener(this);
        AnonymousClass2 r1 = new CountDownTimer(this.q, 1000) {
            public void onTick(long j) {
                TextView a2 = TouchTestActivity.this.n;
                StringBuilder sb = new StringBuilder();
                sb.append(j / 1000);
                sb.append("");
                a2.setText(sb.toString());
            }

            public void onFinish() {
                TouchTestActivity.this.q = TouchTestActivity.this.p;
                if (!TouchTestActivity.this.r) {
                    Toast.makeText(TouchTestActivity.this, TouchTestActivity.messageKO, 0).show();
                    TouchTestActivity.this.finish();
                }
            }
        };
        r1.start();
    }

    public void configTest(long j, int i, int i2, int i3, int i4, int i5, String str, String str2, String str3, String str4, int i6, int i7) {
        this.p = j;
        this.n.setTextColor(ContextCompat.getColor(this, i7));
        this.s.setTextColor(ContextCompat.getColor(this, i7));
        this.s.setText(str4);
        this.o.setTextColor(ContextCompat.getColor(this, i6));
        this.o.setText(str3);
        messageOK = str;
        messageKO = str2;
        this.o.setText(str3);
        this.q = j;
        this.mPatternLockView.setBackgroundResource(i5);
        this.mPatternLockView.setDotNormalSize(i);
        this.mPatternLockView.setPathWidth(i3);
        this.mPatternLockView.setDotSelectedSize(i2);
        this.mPatternLockView.setNormalStateColor(ResourceUtils.getColor(this, i4));
    }

    public void onStarted() {
        Log.d("TouchTestActivity", "onStarted: ");
    }

    public void onProgress(List<Dot> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("Pattern progress: ");
        sb.append(PatternLockUtils.patternToString(this.mPatternLockView, list));
        Log.d("TouchTestActivity", sb.toString());
    }

    public void onComplete(List<Dot> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("Pattern complete: ");
        sb.append(PatternLockUtils.patternToString(this.mPatternLockView, list));
        Log.d("TouchTestActivity", sb.toString());
        this.r = true;
        finish();
    }

    public void onCleared() {
        Log.d("TouchTestActivity", "onCleared: ");
    }

    public void testFails() {
        this.r = true;
        finish();
    }
}
