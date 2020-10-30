package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.SolicitudPrestamoPreacordadoResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class SolicitudPrestamoPreacordadoRequestProd extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = SolicitudPrestamoPreacordadoRequestProd.class.getName();
    private SolicitudPrestamoPreacordadoRequestBeanProd solicitudPrestamoPreacordadoRequestBeanProd;
    private SolicitudPrestamoPreacordadoResponseBeanProd solicitudPrestamoPreacordadoResponseBeanProd;

    public int getMethod() {
        return 1;
    }

    public SolicitudPrestamoPreacordadoRequestProd(Context context, SolicitudPrestamoPreacordadoRequestBeanProd solicitudPrestamoPreacordadoRequestBeanProd2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.solicitudPrestamoPreacordadoRequestBeanProd = solicitudPrestamoPreacordadoRequestBeanProd2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public SolicitudPrestamoPreacordadoRequestProd(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.solicitudPrestamoPreacordadoRequestBeanProd;
    }

    public Class getBeanResponseClass() {
        if (this.solicitudPrestamoPreacordadoResponseBeanProd == null) {
            this.solicitudPrestamoPreacordadoResponseBeanProd = new SolicitudPrestamoPreacordadoResponseBeanProd();
        }
        return this.solicitudPrestamoPreacordadoResponseBeanProd.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            try {
                onResponseBean((SolicitudPrestamoPreacordadoResponseBeanProd) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), SolicitudPrestamoPreacordadoResponseBeanProd.class));
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
