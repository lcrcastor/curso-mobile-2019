package ar.com.santander.rio.mbanking.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.ViewFlipper;
import ar.com.santander.rio.mbanking.R;
import java.util.HashMap;
import java.util.Map.Entry;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ViewOnBoarding extends Activity {
    /* access modifiers changed from: private */
    public static String b;
    float a;
    private ViewFlipper c = null;
    /* access modifiers changed from: private */
    public Context d;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.d = this;
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra(getString(R.string.ONBOARDING_LAYOUT), 0);
        int intExtra2 = intent.getIntExtra(getString(R.string.ONBOARDING_FINALIZER_BUTTON), 0);
        int intExtra3 = intent.getIntExtra(getString(R.string.ONBOARDING_FLIPPER), 0);
        b = intent.getStringExtra(getString(R.string.ONBOARDING_PREFERENCE));
        setContentView(intExtra);
        if (intent.hasExtra(getString(R.string.ONBOARDING_TEXTSTYLINGSET))) {
            OnBoardingTextStylingSet onBoardingTextStylingSet = new OnBoardingTextStylingSet((HashMap) intent.getSerializableExtra(getString(R.string.ONBOARDING_TEXTSTYLINGSET)));
            if (!onBoardingTextStylingSet.isEmpty()) {
                for (Entry entry : onBoardingTextStylingSet.get().entrySet()) {
                    try {
                        View findViewById = findViewById(((Integer) entry.getKey()).intValue());
                        if (findViewById instanceof TextView) {
                            ((TextView) findViewById).setText((CharSequence) entry.getValue(), BufferType.SPANNABLE);
                        }
                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }
                }
            }
        }
        ((Button) findViewById(intExtra2)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!ViewOnBoarding.b.isEmpty()) {
                    PreferenceManager.getDefaultSharedPreferences(ViewOnBoarding.this.d).edit().putBoolean(ViewOnBoarding.b, true).commit();
                }
                ViewOnBoarding.this.finish();
                ViewOnBoarding.this.overridePendingTransition(0, 0);
            }
        });
        this.c = (ViewFlipper) findViewById(intExtra3);
        this.c.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        ViewOnBoarding.this.a = motionEvent.getX();
                        return true;
                    case 1:
                        float x = motionEvent.getX();
                        if (ViewOnBoarding.this.a < x) {
                            return ViewOnBoarding.this.swipeRight().booleanValue();
                        }
                        if (ViewOnBoarding.this.a > x) {
                            return ViewOnBoarding.this.swipeLeft().booleanValue();
                        }
                        break;
                }
                return false;
            }
        });
    }

    public Boolean swipeLeft() {
        if (this.c.getDisplayedChild() == this.c.getChildCount() - 1) {
            return Boolean.valueOf(true);
        }
        this.c.setInAnimation(this.d, R.anim.slide_in_right);
        this.c.setOutAnimation(this.d, R.anim.slide_out_left);
        this.c.showNext();
        return Boolean.valueOf(true);
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public Boolean swipeRight() {
        if (this.c.getDisplayedChild() == 0) {
            return Boolean.valueOf(true);
        }
        this.c.setInAnimation(this.d, R.anim.slide_in_left);
        this.c.setOutAnimation(this.d, R.anim.slide_out_right);
        this.c.showPrevious();
        return Boolean.valueOf(true);
    }

    public void onBackPressed() {
        swipeRight();
    }
}
