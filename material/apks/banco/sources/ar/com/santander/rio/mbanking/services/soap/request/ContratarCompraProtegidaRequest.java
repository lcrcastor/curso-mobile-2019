package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarCompraProtegidaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarCompraProtegidaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarCompraProtegidaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class ContratarCompraProtegidaRequest extends BaseRequest implements IBeanRequestWS {
    private ContratarCompraProtegidaRequestBean contratarCompraProtegidaRequestBean;
    private ContratarCompraProtegidaResponseBean contratarCompraProtegidaResponseBean;

    public int getMethod() {
        return 1;
    }

    public ContratarCompraProtegidaRequest(Context context, ContratarCompraProtegidaRequestBean contratarCompraProtegidaRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.contratarCompraProtegidaRequestBean = contratarCompraProtegidaRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ContratarCompraProtegidaRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.contratarCompraProtegidaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.contratarCompraProtegidaResponseBean == null) {
            this.contratarCompraProtegidaResponseBean = new ContratarCompraProtegidaResponseBean();
        }
        return this.contratarCompraProtegidaResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                ContratarCompraProtegidaResponseBean contratarCompraProtegidaResponseBean2 = new ContratarCompraProtegidaResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                ContratarCompraProtegidaBodyResponseBean contratarCompraProtegidaBodyResponseBean = new ContratarCompraProtegidaBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                contratarCompraProtegidaBodyResponseBean.setFechaInicio(jSONObject3.has("fechaInicio") ? jSONObject3.getString("fechaInicio") : "");
                contratarCompraProtegidaBodyResponseBean.setCodRamo(jSONObject3.has("codRamo") ? jSONObject3.getString("codRamo") : "");
                contratarCompraProtegidaBodyResponseBean.setNumPoliza(jSONObject3.has("numPoliza") ? jSONObject3.getString("numPoliza") : "");
                contratarCompraProtegidaBodyResponseBean.setNumCertificado(jSONObject3.has("numCertificado") ? jSONObject3.getString("numCertificado") : "");
                contratarCompraProtegidaResponseBean2.header = privateHeaderBean;
                contratarCompraProtegidaResponseBean2.contratarCompraProtegidaBodyResponseBean = contratarCompraProtegidaBodyResponseBean;
                onResponseBean(contratarCompraProtegidaResponseBean2);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private LeyendaBean getLeyendaObject(Gson gson, JSONObject jSONObject) {
        LeyendaBean leyendaBean = new LeyendaBean();
        try {
            leyendaBean.setIdLeyenda(jSONObject.has("idLeyenda") ? jSONObject.getString("idLeyenda") : "");
            leyendaBean.setTitulo(jSONObject.has("titulo") ? jSONObject.getString("titulo") : "");
            leyendaBean.setDescripcion(jSONObject.has("descripcion") ? jSONObject.getString("descripcion") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return leyendaBean;
    }
}
