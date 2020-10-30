package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.services.soap.beans.StopDebitRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.StopDebitResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.StopDebitBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.StopDebitBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices;
import ar.com.santander.rio.mbanking.services.soap.versions.VStopDebit;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class StopDebitRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = StopDebitRequest.class.getName();
    private StopDebitRequestBean stopDebitRequestBean;
    private StopDebitResponseBean stopDebitResponseBean;

    public int getMethod() {
        return 1;
    }

    public StopDebitRequest(Context context, StopDebitRequestBean stopDebitRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.stopDebitRequestBean = stopDebitRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public StopDebitRequest(Context context, StopDebitBodyRequestBean stopDebitBodyRequestBean, ErrorRequestServer errorRequestServer, String str, String str2) {
        super(context);
        this.stopDebitRequestBean = new StopDebitRequestBean(getPrivateHeaderBean(VStopDebit.getData(EVersionServices.CURRENT), str, str2), stopDebitBodyRequestBean);
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.stopDebitRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.stopDebitResponseBean == null) {
            this.stopDebitResponseBean = new StopDebitResponseBean();
        }
        return this.stopDebitResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("parserResponse JSONObject ");
        sb.append(jSONObject.toString());
        Log.d(str, sb.toString());
        boolean parserResponse = super.parserResponse(jSONObject);
        String str2 = this.TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("responseOK ");
        sb2.append(parserResponse);
        Log.d(str2, sb2.toString());
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                StopDebitResponseBean stopDebitResponseBean2 = new StopDebitResponseBean();
                stopDebitResponseBean2.setHeaderBean((HeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), HeaderBean.class));
                StopDebitBodyResponseBean stopDebitBodyResponseBean = new StopDebitBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                String str3 = this.TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("numComprobante ");
                sb3.append(jSONObject3.getString("numComprobante"));
                Log.d(str3, sb3.toString());
                stopDebitBodyResponseBean.setNumComprobante(jSONObject3.getString("numComprobante"));
                String str4 = this.TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("fechaComprobante ");
                sb4.append(jSONObject3.getString("fechaComprobante"));
                Log.d(str4, sb4.toString());
                stopDebitBodyResponseBean.setFechaComprobante(jSONObject3.getString("fechaComprobante"));
                stopDebitResponseBean2.setStopDebitBodyResponseBean(stopDebitBodyResponseBean);
                onResponseBean(stopDebitResponseBean2);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
