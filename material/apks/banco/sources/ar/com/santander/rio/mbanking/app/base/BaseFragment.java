package ar.com.santander.rio.mbanking.app.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.animation.Animation;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.activities.ErrorActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.OnBoardingTextStylingSet;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.inject.GraphRetriever;
import com.squareup.otto.Bus;
import dagger.ObjectGraph;
import java.util.List;
import javax.inject.Inject;

public abstract class BaseFragment extends Fragment implements BackEventListener {
    private String a = "";
    private boolean b = true;
    @Inject
    public Bus bus;
    private boolean c = false;
    private IErrorListener d;
    public ProgresIndicator progressIndicator;
    public boolean sDisableFragmentAnimations = false;

    public void clearScreenData() {
    }

    /* access modifiers changed from: protected */
    public List<Object> getModules() {
        return null;
    }

    public void showOnBoarding(int i, int i2, int i3, String str) {
    }

    public void showOnBoarding(int i, int i2, int i3, String str, OnBoardingTextStylingSet onBoardingTextStylingSet) {
    }

    public void setsDisableFragmentAnimations(boolean z) {
        this.sDisableFragmentAnimations = z;
    }

    public Animation onCreateAnimation(int i, boolean z, int i2) {
        if (!this.sDisableFragmentAnimations) {
            return super.onCreateAnimation(i, z, i2);
        }
        AnonymousClass1 r2 = new Animation() {
        };
        r2.setDuration(0);
        return r2;
    }

    public boolean isHasAction() {
        return this.c;
    }

    public void hasActionEnable() {
        this.c = true;
    }

    public void hasActionDisable() {
        this.c = false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        ObjectGraph from = GraphRetriever.from(context);
        List modules = getModules();
        if (modules != null) {
            from.plus(modules.toArray());
        }
        if (this.b) {
            from.inject(this);
            this.b = false;
        }
    }

    public void onStart() {
        super.onStart();
        this.bus.register(this);
    }

    public void onStop() {
        super.onStop();
        this.bus.unregister(this);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public IErrorListener getErrorListener() {
        return this.d;
    }

    public void setErrorListener(IErrorListener iErrorListener) {
        this.d = iErrorListener;
    }

    public String getTAG() {
        return this.a;
    }

    public void setTAG(String str) {
        this.a = str;
    }

    public void showProgress(String str) {
        try {
            getFragmentManager().executePendingTransactions();
        } catch (Exception unused) {
        }
        if (this.progressIndicator == null || !this.progressIndicator.isVisible()) {
            this.progressIndicator = ProgresIndicator.newInstance(str);
            this.progressIndicator.show(getFragmentManager(), str);
        }
    }

    public void dismissProgress() {
        if (this.progressIndicator != null) {
            try {
                this.progressIndicator.dismiss();
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void openMenu() {
        if (getActivity() instanceof SantanderRioMainActivity) {
            ((SantanderRioMainActivity) getActivity()).openMenu(getTAG());
        }
    }

    public void customErrorDialog(String str, String str2, final String str3) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
                if (str3 != null) {
                    ((SantanderRioMainActivity) BaseFragment.this.getActivity()).goToOption(str3);
                }
            }
        });
        newInstance.show(getFragmentManager(), "DialogNewVersion");
    }

    /* access modifiers changed from: protected */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean activityResultHandler(int r5, android.content.Intent r6, java.lang.String r7) {
        /*
            r4 = this;
            r0 = 1
            r1 = -1
            r2 = 0
            if (r5 != r1) goto L_0x0023
            if (r6 == 0) goto L_0x0023
            java.lang.String r3 = "PrivateMenuSelectedOptionPosition"
            boolean r3 = r6.hasExtra(r3)     // Catch:{ Exception -> 0x0020 }
            if (r3 == 0) goto L_0x0023
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()     // Catch:{ Exception -> 0x0020 }
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5     // Catch:{ Exception -> 0x0020 }
            java.lang.String r7 = "PrivateMenuSelectedOptionPosition"
            int r6 = r6.getIntExtra(r7, r2)     // Catch:{ Exception -> 0x0020 }
            r5.onPrivateMenuOptionSelectedInNestedActivity(r6)     // Catch:{ Exception -> 0x0020 }
            goto L_0x00d0
        L_0x0020:
            r5 = move-exception
            goto L_0x00c7
        L_0x0023:
            if (r5 != r1) goto L_0x00cf
            if (r6 == 0) goto L_0x00cf
            java.lang.String r5 = "WS_ERROR_DO_ACTION"
            boolean r5 = r6.hasExtra(r5)     // Catch:{ Exception -> 0x0020 }
            if (r5 == 0) goto L_0x00cf
            if (r7 == 0) goto L_0x0037
            boolean r5 = r7.isEmpty()     // Catch:{ Exception -> 0x0020 }
            if (r5 == 0) goto L_0x0039
        L_0x0037:
            java.lang.String r7 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS     // Catch:{ Exception -> 0x0020 }
        L_0x0039:
            java.lang.String r5 = "WS_ERROR_DO_ACTION"
            java.lang.String r5 = r6.getStringExtra(r5)     // Catch:{ Exception -> 0x0020 }
            int r3 = r5.hashCode()     // Catch:{ Exception -> 0x0020 }
            switch(r3) {
                case -1667304550: goto L_0x006f;
                case -1442009346: goto L_0x0065;
                case -1365838438: goto L_0x005b;
                case -171755572: goto L_0x0051;
                case 4216548: goto L_0x0047;
                default: goto L_0x0046;
            }     // Catch:{ Exception -> 0x0020 }
        L_0x0046:
            goto L_0x0079
        L_0x0047:
            java.lang.String r3 = "GO_TO_HOME_ERROR_CLOCK"
            boolean r5 = r5.equals(r3)     // Catch:{ Exception -> 0x0020 }
            if (r5 == 0) goto L_0x0079
            r5 = 4
            goto L_0x007a
        L_0x0051:
            java.lang.String r3 = "GO_TO_HOME"
            boolean r5 = r5.equals(r3)     // Catch:{ Exception -> 0x0020 }
            if (r5 == 0) goto L_0x0079
            r5 = 0
            goto L_0x007a
        L_0x005b:
            java.lang.String r3 = "GO_TO_HOME_FUNCIONALIDAD"
            boolean r5 = r5.equals(r3)     // Catch:{ Exception -> 0x0020 }
            if (r5 == 0) goto L_0x0079
            r5 = 1
            goto L_0x007a
        L_0x0065:
            java.lang.String r3 = "GO_TO_CUENTAS"
            boolean r5 = r5.equals(r3)     // Catch:{ Exception -> 0x0020 }
            if (r5 == 0) goto L_0x0079
            r5 = 2
            goto L_0x007a
        L_0x006f:
            java.lang.String r3 = "GO_TO_HOME_ERROR_FRAGMENT"
            boolean r5 = r5.equals(r3)     // Catch:{ Exception -> 0x0020 }
            if (r5 == 0) goto L_0x0079
            r5 = 3
            goto L_0x007a
        L_0x0079:
            r5 = -1
        L_0x007a:
            switch(r5) {
                case 0: goto L_0x00b7;
                case 1: goto L_0x00ad;
                case 2: goto L_0x00a1;
                case 3: goto L_0x0091;
                case 4: goto L_0x007e;
                default: goto L_0x007d;
            }     // Catch:{ Exception -> 0x0020 }
        L_0x007d:
            goto L_0x00d0
        L_0x007e:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()     // Catch:{ Exception -> 0x0020 }
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5     // Catch:{ Exception -> 0x0020 }
            java.lang.String r7 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r6 = r6.getStringExtra(r7)     // Catch:{ Exception -> 0x0020 }
            r7 = 2131231076(0x7f080164, float:1.8078223E38)
            r5.setErrorFragment(r6, r7)     // Catch:{ Exception -> 0x0020 }
            goto L_0x00d0
        L_0x0091:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()     // Catch:{ Exception -> 0x0020 }
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5     // Catch:{ Exception -> 0x0020 }
            java.lang.String r7 = "INTENT_EXTRA_MESSAGE"
            java.lang.String r6 = r6.getStringExtra(r7)     // Catch:{ Exception -> 0x0020 }
            r5.setErrorFragment(r6)     // Catch:{ Exception -> 0x0020 }
            goto L_0x00d0
        L_0x00a1:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()     // Catch:{ Exception -> 0x0020 }
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5     // Catch:{ Exception -> 0x0020 }
            java.lang.String r6 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS     // Catch:{ Exception -> 0x0020 }
            r5.goToOption(r6)     // Catch:{ Exception -> 0x0020 }
            goto L_0x00d0
        L_0x00ad:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()     // Catch:{ Exception -> 0x0020 }
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5     // Catch:{ Exception -> 0x0020 }
            r5.goToOption(r7)     // Catch:{ Exception -> 0x0020 }
            goto L_0x00d0
        L_0x00b7:
            android.support.v4.app.FragmentActivity r5 = r4.getActivity()     // Catch:{ Exception -> 0x0020 }
            ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity r5 = (ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity) r5     // Catch:{ Exception -> 0x0020 }
            java.lang.String r7 = "INTENT_EXTRA_BACK_ANIMATION"
            boolean r6 = r6.getBooleanExtra(r7, r2)     // Catch:{ Exception -> 0x0020 }
            r5.gotoHome(r6)     // Catch:{ Exception -> 0x0020 }
            goto L_0x00d0
        L_0x00c7:
            java.lang.String r6 = "ACTIVITY_RESULT_HANDLER"
            java.lang.String r7 = "activityResultHandler: "
            android.util.Log.e(r6, r7, r5)
            goto L_0x00d0
        L_0x00cf:
            r0 = 0
        L_0x00d0:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.base.BaseFragment.activityResultHandler(int, android.content.Intent, java.lang.String):boolean");
    }

    public void handleWSError(Intent intent) {
        if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
            String stringExtra = intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION);
            char c2 = 65535;
            switch (stringExtra.hashCode()) {
                case -1667304550:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_HOME_ERROR_FRAGMENT)) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -1442009346:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_CUENTAS)) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -1365838438:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_HOME_FUNCIONALIDAD)) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -171755572:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_LOGIN)) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 4216548:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_HOME_ERROR_CLOCK)) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 1330612898:
                    if (stringExtra.equals(WSErrorHandlerConstants.GO_TO_NEXT_ERROR_SCREEN)) {
                        c2 = 5;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    ((SantanderRioMainActivity) getActivity()).gotoHome();
                    return;
                case 2:
                    ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.CUENTAS);
                    return;
                case 3:
                    if (intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE) != null) {
                        ((SantanderRioMainActivity) getActivity()).setErrorFragment(intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE), intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE));
                        return;
                    } else {
                        ((SantanderRioMainActivity) getActivity()).setErrorFragment(intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                        return;
                    }
                case 4:
                    if (getTAG().equals(FragmentConstants.FONDOS_INVERSION)) {
                        ((SantanderRioMainActivity) getActivity()).setErrorFragment(intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE), (int) R.drawable.ico_reloj_gris, (int) R.string.IDXX_PRIVATEMENU_BTN_INVESTMENT_FUNDS);
                        return;
                    } else {
                        ((SantanderRioMainActivity) getActivity()).setErrorFragment(intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE), (int) R.drawable.ico_reloj_gris, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE));
                        return;
                    }
                case 5:
                    Intent intent2 = new Intent(getActivity(), ErrorActivity.class);
                    intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                    intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE, intent.getSerializableExtra(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE));
                    intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, intent.getIntExtra(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, 0));
                    startActivityForResult(intent2, WSErrorHandlerConstants.INTENT_RES4_REQUEST_CODE);
                    return;
                default:
                    return;
            }
        }
    }

    public void onBackPressed() {
        switchDrawer();
    }

    public void switchDrawer() {
        try {
            if (getActivity() instanceof SantanderRioMainActivity) {
                ((SantanderRioMainActivity) getActivity()).hideKeyboard();
                ((SantanderRioMainActivity) getActivity()).switchDrawer();
            }
        } catch (Exception e) {
            Log.e("@dev", "Error al switchear el Drawer del menú", e);
        }
    }
}
