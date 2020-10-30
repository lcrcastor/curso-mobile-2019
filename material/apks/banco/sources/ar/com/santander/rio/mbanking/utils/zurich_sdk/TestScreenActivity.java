package ar.com.santander.rio.mbanking.utils.zurich_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultsKeys;
import com.zurich.lcd_test.TouchTestActivity;
import com.zurich.lockview.PatternLockView;
import com.zurich.lockview.PatternLockView.Dot;
import com.zurich.lockview.listener.PatternLockViewListener;
import java.util.List;

public class TestScreenActivity extends TouchTestActivity {
    public PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        public void onProgress(List<Dot> list) {
        }

        public void onStarted() {
        }

        public void onComplete(List<Dot> list) {
            Intent intent = new Intent();
            intent.putExtra(ResultsKeys.SCREEN_RESULT_VALUE, ResultValues.OK);
            TestScreenActivity.this.setResult(-1, intent);
            TestScreenActivity.this.finish();
        }

        public void onCleared() {
            Intent intent = new Intent();
            intent.putExtra(ResultsKeys.SCREEN_RESULT_VALUE, ResultValues.CLEARED);
            TestScreenActivity.this.setResult(-1, intent);
            TestScreenActivity.this.finish();
        }

        public void testFails() {
            Intent intent = new Intent();
            intent.putExtra(ResultsKeys.SCREEN_RESULT_VALUE, ResultValues.FAIL);
            TestScreenActivity.this.setResult(-1, intent);
            TestScreenActivity.this.finish();
        }
    };
    private PatternLockView n;
    private ActionBar o;

    public void configTest(long j, int i, int i2, int i3, int i4, int i5, String str, String str2, String str3, String str4, int i6, int i7) {
        super.configTest(j, i, i2, i3, i4, i5, str, str2, str3, str4, i6, i7);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.o = getSupportActionBar();
        if (this.o != null) {
            this.o.hide();
        }
        configTest(40000, 80, 100, 40, R.color.white, R.color.black, getBaseContext().getString(R.string.message_test_display), getBaseContext().getString(R.string.message_counter_time), null, null, R.color.white, R.color.white);
        this.n = (PatternLockView) findViewById(R.id.pattern_lock_view);
        setupTimer();
    }

    public void setupTimer() {
        this.n.addPatternLockListener(this.mPatternLockViewListener);
        super.setupTimer();
    }
}
