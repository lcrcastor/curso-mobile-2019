package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmPreautorizacionCompradorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.AbmPreautorizacionCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmPreautorizacionCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class AbmPreautorizacionCompradorRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private AbmPreautorizacionCompradorRequestBean abmPreautorizacionCompradorRequestBean;
    private AbmPreautorizacionCompradorResponseBean abmPreautorizacionCompradorResponseBean;

    public int getMethod() {
        return 1;
    }

    public AbmPreautorizacionCompradorRequest(Context context, AbmPreautorizacionCompradorRequestBean abmPreautorizacionCompradorRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.abmPreautorizacionCompradorRequestBean = abmPreautorizacionCompradorRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public AbmPreautorizacionCompradorResponseBean getAbmPreautorizacionCompradorResponseBean() {
        return this.abmPreautorizacionCompradorResponseBean;
    }

    public void setAbmPreautorizacionCompradorResponseBean(AbmPreautorizacionCompradorResponseBean abmPreautorizacionCompradorResponseBean2) {
        this.abmPreautorizacionCompradorResponseBean = abmPreautorizacionCompradorResponseBean2;
    }

    public AbmPreautorizacionCompradorRequestBean getAbmPreautorizacionCompradorRequestBean() {
        return this.abmPreautorizacionCompradorRequestBean;
    }

    public void setAbmPreautorizacionCompradorRequestBean(AbmPreautorizacionCompradorRequestBean abmPreautorizacionCompradorRequestBean2) {
        this.abmPreautorizacionCompradorRequestBean = abmPreautorizacionCompradorRequestBean2;
    }

    public AbmPreautorizacionCompradorRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public Class getBeanResponseClass() {
        if (this.abmPreautorizacionCompradorResponseBean == null) {
            this.abmPreautorizacionCompradorResponseBean = new AbmPreautorizacionCompradorResponseBean();
        }
        return this.abmPreautorizacionCompradorResponseBean.getClass();
    }

    public IBeanWS getBeanToRequest() {
        return this.abmPreautorizacionCompradorRequestBean;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("header");
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject3.toString(), PrivateHeaderBean.class);
                AbmPreautorizacionCompradorBodyResponseBean abmPreautorizacionCompradorBodyResponseBean = (AbmPreautorizacionCompradorBodyResponseBean) gson.fromJson(jSONObject2.getJSONObject("body").toString(), AbmPreautorizacionCompradorBodyResponseBean.class);
                AbmPreautorizacionCompradorResponseBean abmPreautorizacionCompradorResponseBean2 = new AbmPreautorizacionCompradorResponseBean();
                abmPreautorizacionCompradorResponseBean2.setHeaderBean(privateHeaderBean);
                abmPreautorizacionCompradorResponseBean2.setAbmPreautorizacionCompradorResponseBean(abmPreautorizacionCompradorBodyResponseBean);
                onResponseBean(abmPreautorizacionCompradorResponseBean2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return parserResponse;
    }
}
