package ar.com.santander.rio.mbanking.app.ui.fragments.publico;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.module.softtoken.ISoftTokenState;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;

class SoftTokenHandler {
    /* access modifiers changed from: private */
    public Context a;
    private SoftTokenManager b;
    /* access modifiers changed from: private */
    public AnalyticsManager c;
    private String d;

    public SoftTokenHandler(Context context, SoftTokenManager softTokenManager, AnalyticsManager analyticsManager) {
        this.a = context;
        this.c = analyticsManager;
        this.b = softTokenManager;
    }

    public void a(String str, ISoftTokenState iSoftTokenState) {
        if (this.b == null) {
            this.b = new SoftTokenManager(this.a);
        }
        if (!this.b.isSoftTokenAvailable().booleanValue()) {
            this.d = str;
            a();
            iSoftTokenState.isNotAvaliable();
            return;
        }
        iSoftTokenState.isAvaliable();
    }

    private void a() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(null, this.a.getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), this.d, this.a.getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), this.a.getResources().getString(R.string.USER200008_BTN));
        newInstance.setAutoClose(Boolean.valueOf(false));
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                if (!newInstance.getAutoClose().booleanValue()) {
                    newInstance.setAutoClose(Boolean.valueOf(true));
                    newInstance.closeDialog();
                }
            }

            public void onNegativeButton() {
                Intent intent = new Intent(SoftTokenHandler.this.a, InfoActivity.class);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, SoftTokenHandler.this.a.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG_TIT));
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, SoftTokenHandler.this.a.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG));
                SoftTokenHandler.this.c.trackScreen(SoftTokenHandler.this.a.getString(R.string.analytics_softtoken_ayuda_acerca_token_virtual));
                SoftTokenHandler.this.a.startActivity(intent);
            }
        });
        newInstance.show(((FragmentActivity) this.a).getSupportFragmentManager(), "DialogNewVersion");
    }
}
