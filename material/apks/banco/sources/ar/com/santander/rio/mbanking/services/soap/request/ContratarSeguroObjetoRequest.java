package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroObjetoResquestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class ContratarSeguroObjetoRequest extends BaseRequest implements IBeanRequestWS {
    private ContratarSeguroObjetoResponseBean mContratarSeguroObjetoResponseBean;
    private ContratarSeguroObjetoResquestBean mContratarSeguroObjetoResquestBean;

    public int getMethod() {
        return 1;
    }

    public ContratarSeguroObjetoRequest(Context context, ContratarSeguroObjetoResquestBean contratarSeguroObjetoResquestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mContratarSeguroObjetoResquestBean = contratarSeguroObjetoResquestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ContratarSeguroObjetoRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mContratarSeguroObjetoResquestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mContratarSeguroObjetoResponseBean == null) {
            this.mContratarSeguroObjetoResponseBean = new ContratarSeguroObjetoResponseBean();
        }
        return this.mContratarSeguroObjetoResponseBean.getClass();
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
                ContratarSeguroObjetoResponseBean contratarSeguroObjetoResponseBean = new ContratarSeguroObjetoResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("header");
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject3.toString(), PrivateHeaderBean.class);
                contratarSeguroObjetoResponseBean.setGetPreguntasFamiliaBodyResponseBean((ContratarSeguroObjetoBodyResponseBean) new Gson().fromJson(String.valueOf(jSONObject2.getJSONObject("body")), ContratarSeguroObjetoBodyResponseBean.class));
                contratarSeguroObjetoResponseBean.header = privateHeaderBean;
                onResponseBean(contratarSeguroObjetoResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
