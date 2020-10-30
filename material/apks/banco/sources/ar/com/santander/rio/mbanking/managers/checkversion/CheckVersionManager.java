package ar.com.santander.rio.mbanking.managers.checkversion;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.CheckVersionEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.CheckVersionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CheckVersionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UpdateBean;

public class CheckVersionManager {
    AppCompatActivity a;
    SessionManager b;
    private String c = "0";

    public static class RESPONSE_CODES {
        public static final String ERROR = "-1";
        public static final String MAINTENANCE = "4";
        public static final String MANDATORY_UPDATE = "3";
        public static final String OK = "1";
        public static final String OPTIONAL_UPDATE = "2";
        public static final String WAITING = "0";
    }

    public CheckVersionManager(SessionManager sessionManager, AppCompatActivity appCompatActivity) {
        this.b = sessionManager;
        this.a = appCompatActivity;
    }

    public String check(CheckVersionEvent checkVersionEvent, IDialogListener iDialogListener) {
        try {
            if (checkVersionEvent.getResult() == TypeResult.OK) {
                CheckVersionResponseBean checkVersionResponseBean = (CheckVersionResponseBean) checkVersionEvent.getBeanResponse();
                this.c = getResult(checkVersionEvent);
                CheckVersionBodyResponseBean checkVersionBodyResponseBean = checkVersionResponseBean.getCheckVersionBodyResponseBean();
                if (!this.c.equals("1")) {
                    String str = this.c.equals("2") ? this.a.getString(R.string.MSG_USER0000048) : this.c.equals("3") ? this.a.getString(R.string.MSG_USER0000049) : "";
                    IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(this.a.getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), str.replace("[NRO_VERSION]", checkVersionBodyResponseBean.getUpdateBean().getVersion()), null, null, this.a.getString(R.string.IDX_ALERT_BTN_YES), this.a.getString(R.string.IDX_ALERT_BTN_NO), null);
                    newInstance.setDialogListener(iDialogListener);
                    newInstance.show(this.a.getSupportFragmentManager(), "Dialog");
                }
            } else if (checkVersionEvent.getResult() == TypeResult.BEAN_ERROR_RES_1) {
                IsbanDialogFragment newInstance2 = IsbanDialogFragment.newInstance(checkVersionEvent.getTitleToShow(), checkVersionEvent.getMessageToShow(), null, null, this.a.getString(R.string.IDX_ALERT_BTN_ACEPTO), null, null);
                newInstance2.setDialogListener(iDialogListener);
                newInstance2.show(this.a.getSupportFragmentManager(), "Dialog");
                this.c = "4";
            } else {
                this.c = RESPONSE_CODES.ERROR;
            }
        } catch (Exception e) {
            Toast.makeText(this.a, this.a.getString(R.string.MSG_USER000029_General_errorGenerico), 1).show();
            e.fillInStackTrace();
        }
        return this.c;
    }

    public boolean isLoginEnabled() {
        String result = getResult();
        return result.equals("1") || result.equals("2");
    }

    public boolean isUpdateAvailable() {
        String result = getResult();
        return result.equals("2") || result.equals("3");
    }

    public String getResult() {
        CheckVersionEvent checkVersionEvent = this.b.getCheckVersionEvent();
        return checkVersionEvent != null ? getResult(checkVersionEvent) : "0";
    }

    public String getResult(CheckVersionEvent checkVersionEvent) {
        if (checkVersionEvent != null) {
            try {
                if (checkVersionEvent.getBeanResponse() != null) {
                    return ((CheckVersionResponseBean) checkVersionEvent.getBeanResponse()).getCheckVersionBodyResponseBean().getResult();
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
        return RESPONSE_CODES.ERROR;
    }

    public UpdateBean getUpdateBean() {
        return getUpdateBean(this.b.getCheckVersionEvent());
    }

    public UpdateBean getUpdateBean(CheckVersionEvent checkVersionEvent) {
        if (checkVersionEvent != null) {
            try {
                if (checkVersionEvent.getBeanResponse() != null) {
                    return ((CheckVersionResponseBean) checkVersionEvent.getBeanResponse()).getCheckVersionBodyResponseBean().getUpdateBean();
                }
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
        return null;
    }
}
