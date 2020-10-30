package ar.com.santander.rio.mbanking.utils.zurich_sdk;

import android.content.Intent;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultValues;
import ar.com.santander.rio.mbanking.utils.zurich_sdk.Constants.ResultsKeys;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import com.zurich.battery.BatteryActivity;

public class TestBatteryActivity extends BatteryActivity {
    public static final String GOOD_CONDITION_VALUE = "Good Condition";
    public static final String HEALTH_KEY = "health";

    public void onBatteryStatus(String str) {
        super.onBatteryStatus(str);
        try {
            if (a(str)) {
                Intent intent = new Intent();
                intent.putExtra(ResultsKeys.BATTERY_RESULT_VALUE, ResultValues.OK);
                setResult(-1, intent);
                finish();
                return;
            }
            Intent intent2 = new Intent();
            intent2.putExtra(ResultsKeys.BATTERY_RESULT_VALUE, ResultValues.FAIL);
            setResult(-1, intent2);
            finish();
        } catch (JSONException unused) {
            Intent intent3 = new Intent();
            intent3.putExtra(ResultsKeys.BATTERY_RESULT_VALUE, ResultValues.ERROR);
            setResult(0, intent3);
            finish();
        }
    }

    private boolean a(String str) {
        return new JSONObject(str).getString(HEALTH_KEY).equals(GOOD_CONDITION_VALUE);
    }
}
