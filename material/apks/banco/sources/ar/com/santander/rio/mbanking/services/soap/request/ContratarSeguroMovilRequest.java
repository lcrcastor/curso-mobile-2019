package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroMovilRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ContratarSeguroMovilResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroMovilBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class ContratarSeguroMovilRequest extends BaseRequest implements IBeanRequestWS {
    private ContratarSeguroMovilRequestBean contratarSeguroMovilRequestBean;
    private ContratarSeguroMovilResponseBean contratarSeguroMovilResponseBean;

    public int getMethod() {
        return 1;
    }

    public ContratarSeguroMovilRequest(Context context, ContratarSeguroMovilRequestBean contratarSeguroMovilRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.contratarSeguroMovilRequestBean = contratarSeguroMovilRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ContratarSeguroMovilRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.contratarSeguroMovilRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.contratarSeguroMovilResponseBean == null) {
            this.contratarSeguroMovilResponseBean = new ContratarSeguroMovilResponseBean();
        }
        return this.contratarSeguroMovilResponseBean.getClass();
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
                ContratarSeguroMovilResponseBean contratarSeguroMovilResponseBean2 = new ContratarSeguroMovilResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                ContratarSeguroMovilBodyResponseBean contratarSeguroMovilBodyResponseBean = new ContratarSeguroMovilBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                contratarSeguroMovilBodyResponseBean.setFechaInicio(jSONObject3.has("fechaInicio") ? jSONObject3.getString("fechaInicio") : "");
                contratarSeguroMovilBodyResponseBean.setCodRamo(jSONObject3.has("codRamo") ? jSONObject3.getString("codRamo") : "");
                contratarSeguroMovilBodyResponseBean.setNumPoliza(jSONObject3.has("numPoliza") ? jSONObject3.getString("numPoliza") : "");
                contratarSeguroMovilBodyResponseBean.setNumCertificado(jSONObject3.has("numCertificado") ? jSONObject3.getString("numCertificado") : "");
                LeyendasBean leyendasBean = new LeyendasBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject3.has("listaLeyendas") && jSONObject3.getJSONObject("listaLeyendas").has("leyenda")) {
                    Object obj = jSONObject3.getJSONObject("listaLeyendas").get("leyenda");
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getLeyendaObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                    } else if (obj instanceof JSONObject) {
                        arrayList.add(getLeyendaObject(gson, jSONObject3.getJSONObject("listaLeyendas").getJSONObject("leyenda")));
                    }
                }
                leyendasBean.setListLeyendaBean(arrayList);
                contratarSeguroMovilBodyResponseBean.setLeyendas(leyendasBean);
                contratarSeguroMovilResponseBean2.header = privateHeaderBean;
                contratarSeguroMovilResponseBean2.contratarSeguroMovilBodyResponseBean = contratarSeguroMovilBodyResponseBean;
                onResponseBean(contratarSeguroMovilResponseBean2);
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
