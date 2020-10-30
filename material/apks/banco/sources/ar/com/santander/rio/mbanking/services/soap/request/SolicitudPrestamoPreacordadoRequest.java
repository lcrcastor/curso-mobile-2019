package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class SolicitudPrestamoPreacordadoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = SolicitudPrestamoPreacordadoRequest.class.getName();
    private SolicitudPrestamoPreacordadoRequestBean solicitudPrestamoPreacordadoRequestBean;
    private SolicitudPrestamoPreacordadoResponseBean solicitudPrestamoPreacordadoResponseBean;

    public int getMethod() {
        return 1;
    }

    public SolicitudPrestamoPreacordadoRequest(Context context, SolicitudPrestamoPreacordadoRequestBean solicitudPrestamoPreacordadoRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.solicitudPrestamoPreacordadoRequestBean = solicitudPrestamoPreacordadoRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public SolicitudPrestamoPreacordadoRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.solicitudPrestamoPreacordadoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.solicitudPrestamoPreacordadoResponseBean == null) {
            this.solicitudPrestamoPreacordadoResponseBean = new SolicitudPrestamoPreacordadoResponseBean();
        }
        return this.solicitudPrestamoPreacordadoResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            try {
                onResponseBean((SolicitudPrestamoPreacordadoResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), SolicitudPrestamoPreacordadoResponseBean.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return parserResponse;
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
