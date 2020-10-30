package ar.com.santander.rio.mbanking.services.events;

import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import com.android.volley.VolleyError;
import com.indra.httpclient.beans.IBeanWS;

public class WebServiceEvent {
    private IBeanWS beanResponse;
    private ErrorBodyBean errorBodyBean;
    private String messageToShow = "";
    private TypeResult result;
    public boolean retry;
    private String titleToShow = "";
    private TypeOption typeOption = TypeOption.INTERMDIATE_VIEW;
    private UnknownError unknownError;
    private VolleyError volleyError;

    public enum TypeOption {
        INITIAL_VIEW,
        INTERMDIATE_VIEW,
        TRANSACTIONAL_FINAL_VIEW,
        NO_TRANSACTIONAL_FINAL_VIEW
    }

    public enum TypeResult {
        OK,
        SERVER_ERROR,
        BEAN_ERROR_RES_1,
        BEAN_WARNING,
        BEAN_ERROR_RES_2,
        BEAN_ERROR_RES_3,
        BEAN_ERROR_RES_4,
        BEAN_ERROR_RES_5,
        BEAN_ERROR_RES_6,
        BEAN_ERROR_RES_7,
        BEAN_ERROR_RES_8,
        BEAN_ERROR_RES_9,
        UNKNOWN_ERROR
    }

    protected WebServiceEvent(TypeResult typeResult, IBeanWS iBeanWS) {
        this.result = typeResult;
        this.beanResponse = iBeanWS;
    }

    protected WebServiceEvent() {
    }

    public TypeResult getResult() {
        return this.result;
    }

    public void setResult(TypeResult typeResult) {
        this.result = typeResult;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setResult(ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean r2) {
        /*
            r1 = this;
            java.lang.String r2 = r2.res
            int r0 = r2.hashCode()
            switch(r0) {
                case 48: goto L_0x0067;
                case 49: goto L_0x005d;
                case 50: goto L_0x0053;
                case 51: goto L_0x0049;
                case 52: goto L_0x003f;
                case 53: goto L_0x0035;
                case 54: goto L_0x002b;
                case 55: goto L_0x0021;
                case 56: goto L_0x0016;
                case 57: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x0071
        L_0x000b:
            java.lang.String r0 = "9"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 9
            goto L_0x0072
        L_0x0016:
            java.lang.String r0 = "8"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 8
            goto L_0x0072
        L_0x0021:
            java.lang.String r0 = "7"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 7
            goto L_0x0072
        L_0x002b:
            java.lang.String r0 = "6"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 6
            goto L_0x0072
        L_0x0035:
            java.lang.String r0 = "5"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 5
            goto L_0x0072
        L_0x003f:
            java.lang.String r0 = "4"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 4
            goto L_0x0072
        L_0x0049:
            java.lang.String r0 = "3"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 3
            goto L_0x0072
        L_0x0053:
            java.lang.String r0 = "2"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 2
            goto L_0x0072
        L_0x005d:
            java.lang.String r0 = "1"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 1
            goto L_0x0072
        L_0x0067:
            java.lang.String r0 = "0"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0071
            r2 = 0
            goto L_0x0072
        L_0x0071:
            r2 = -1
        L_0x0072:
            switch(r2) {
                case 0: goto L_0x00a7;
                case 1: goto L_0x00a2;
                case 2: goto L_0x009d;
                case 3: goto L_0x0098;
                case 4: goto L_0x0093;
                case 5: goto L_0x008e;
                case 6: goto L_0x0089;
                case 7: goto L_0x0084;
                case 8: goto L_0x007f;
                case 9: goto L_0x007a;
                default: goto L_0x0075;
            }
        L_0x0075:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.UNKNOWN_ERROR
            r1.result = r2
            goto L_0x00ab
        L_0x007a:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_9
            r1.result = r2
            goto L_0x00ab
        L_0x007f:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_8
            r1.result = r2
            goto L_0x00ab
        L_0x0084:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_7
            r1.result = r2
            goto L_0x00ab
        L_0x0089:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_6
            r1.result = r2
            goto L_0x00ab
        L_0x008e:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_5
            r1.result = r2
            goto L_0x00ab
        L_0x0093:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_4
            r1.result = r2
            goto L_0x00ab
        L_0x0098:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_3
            r1.result = r2
            goto L_0x00ab
        L_0x009d:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_2
            r1.result = r2
            goto L_0x00ab
        L_0x00a2:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.BEAN_ERROR_RES_1
            r1.result = r2
            goto L_0x00ab
        L_0x00a7:
            ar.com.santander.rio.mbanking.services.events.WebServiceEvent$TypeResult r2 = ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult.OK
            r1.result = r2
        L_0x00ab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.services.events.WebServiceEvent.setResult(ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean):void");
    }

    public VolleyError getVolleyError() {
        return this.volleyError;
    }

    public void setVolleyError(VolleyError volleyError2) {
        this.volleyError = volleyError2;
    }

    public IBeanWS getBeanResponse() {
        return this.beanResponse;
    }

    public void setBeanResponse(IBeanWS iBeanWS) {
        this.beanResponse = iBeanWS;
    }

    public String getMessageToShow() {
        return this.messageToShow;
    }

    public void setMessageToShow(String str) {
        this.messageToShow = str;
    }

    public String getTitleToShow() {
        return this.titleToShow;
    }

    public void setTitleToShow(String str) {
        this.titleToShow = str;
    }

    public UnknownError getUnknownError() {
        return this.unknownError;
    }

    public void setUnknownError(UnknownError unknownError2) {
        this.unknownError = unknownError2;
    }

    public ErrorBodyBean getErrorBodyBean() {
        return this.errorBodyBean;
    }

    public void setErrorBodyBean(ErrorBodyBean errorBodyBean2) {
        this.errorBodyBean = errorBodyBean2;
    }

    public boolean isRetry() {
        return this.retry;
    }

    public void setRetry(boolean z) {
        this.retry = z;
    }

    public TypeOption getTypeOption() {
        return this.typeOption;
    }

    public void setTypeOption(TypeOption typeOption2) {
        this.typeOption = typeOption2;
    }

    public boolean needRetry() {
        return (this.result == TypeResult.BEAN_ERROR_RES_1 || this.result == TypeResult.SERVER_ERROR) && this.retry;
    }
}
