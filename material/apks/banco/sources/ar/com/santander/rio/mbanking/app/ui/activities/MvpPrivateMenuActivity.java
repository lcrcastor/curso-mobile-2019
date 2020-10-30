package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.view.View;
import ar.com.santander.rio.mbanking.app.base.IBaseView;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public abstract class MvpPrivateMenuActivity extends PrivateMenuActivity implements IBaseView {
    protected View mActionBar;
    @Inject
    protected Bus mBus;
    @Inject
    protected IDataManager mDataManager;

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        attachView();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        detachView();
    }

    public void handleWSError(Intent intent) {
        if (!intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
            setResult(-1, intent);
            finish();
        } else if (intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION).equalsIgnoreCase(WSErrorHandlerConstants.GO_TO_NEXT_ERROR_SCREEN)) {
            Intent intent2 = new Intent(getBaseContext(), ErrorActivity.class);
            intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
            intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE, intent.getSerializableExtra(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE));
            intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, intent.getIntExtra(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, 0));
            startActivityForResult(intent2, WSErrorHandlerConstants.INTENT_RES4_REQUEST_CODE);
        } else {
            setResult(-1, intent);
            finish();
        }
    }

    public void showProgressIndicator(String str) {
        showProgress(str);
    }

    public void dismissProgressIndicator() {
        dismissProgress();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        detachView();
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }
}
