package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaLeyendasRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaLeyendasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class ConsultaLeyendasRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaLeyendasRequest.class.getName();
    private ConsultaLeyendasRequestBean consultaLeyendasRequestBean;
    private ConsultaLeyendasResponseBean consultaLeyendasResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConsultaLeyendasRequest(Context context, ConsultaLeyendasRequestBean consultaLeyendasRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.consultaLeyendasRequestBean = consultaLeyendasRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consultaLeyendasRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.consultaLeyendasResponseBean == null) {
            this.consultaLeyendasResponseBean = new ConsultaLeyendasResponseBean();
        }
        return this.consultaLeyendasResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        ConsultaLeyendasResponseBean consultaLeyendasResponseBean2;
        ConsultaLeyendasResponseBean consultaLeyendasResponseBean3 = new ConsultaLeyendasResponseBean();
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            try {
                consultaLeyendasResponseBean2 = (ConsultaLeyendasResponseBean) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), ConsultaLeyendasResponseBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
                consultaLeyendasResponseBean2 = consultaLeyendasResponseBean3;
            }
            onResponseBean(consultaLeyendasResponseBean2);
        }
        return parserResponse;
    }
}
