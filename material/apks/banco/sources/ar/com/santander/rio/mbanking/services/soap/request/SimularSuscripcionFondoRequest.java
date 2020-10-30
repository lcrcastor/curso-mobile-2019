package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularSuscripcionFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscribirFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularSuscripcionFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class SimularSuscripcionFondoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private SimularSuscripcionFondoResponseBean mSimularSuscripcionFondoResponseBean;
    private SuscribirFondoRequestBean mSuscribirFondoRequestBean;

    public int getMethod() {
        return 1;
    }

    public SimularSuscripcionFondoRequest(Context context, SuscribirFondoRequestBean suscribirFondoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mSuscribirFondoRequestBean = suscribirFondoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mSuscribirFondoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mSimularSuscripcionFondoResponseBean == null) {
            this.mSimularSuscripcionFondoResponseBean = new SimularSuscripcionFondoResponseBean();
        }
        return this.mSimularSuscripcionFondoResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        JSONObject jSONObject2;
        String str;
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject3 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                SimularSuscripcionFondoResponseBean simularSuscripcionFondoResponseBean = new SimularSuscripcionFondoResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject3.getJSONObject("header").toString(), PrivateHeaderBean.class);
                SimularSuscripcionFondoBodyResponseBean simularSuscripcionFondoBodyResponseBean = new SimularSuscripcionFondoBodyResponseBean();
                JSONObject jSONObject4 = jSONObject3.getJSONObject("body");
                simularSuscripcionFondoBodyResponseBean.setIdFondo(jSONObject4.has("idFondo") ? jSONObject4.getString("idFondo") : "");
                simularSuscripcionFondoBodyResponseBean.setImporteSusc(jSONObject4.has("importeSusc") ? jSONObject4.getString("importeSusc") : "");
                simularSuscripcionFondoBodyResponseBean.setPorcentComision(jSONObject4.has("porcentComision") ? jSONObject4.getString("porcentComision") : "");
                simularSuscripcionFondoBodyResponseBean.setCotizaCambio(jSONObject4.has("cotizaCambio") ? jSONObject4.getString("cotizaCambio") : "");
                simularSuscripcionFondoBodyResponseBean.setTermCondiciones(jSONObject4.has("termCondiciones") ? jSONObject4.getString("termCondiciones") : "");
                simularSuscripcionFondoBodyResponseBean.setReglamento(jSONObject4.has("reglamento") ? jSONObject4.getString("reglamento") : "");
                simularSuscripcionFondoBodyResponseBean.setLeyendaEspecial(jSONObject4.has("leyendaEspecial") ? jSONObject4.getString("leyendaEspecial") : "");
                LegalesFondosBean legalesFondosBean = new LegalesFondosBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject4.has("legales") && (jSONObject4.getJSONObject("legales").has("leyendaLegal") || jSONObject4.getJSONObject("legales").has("legal"))) {
                    if (jSONObject4.getJSONObject("legales").has("leyendaLegal")) {
                        jSONObject2 = jSONObject4.getJSONObject("legales");
                        str = "leyendaLegal";
                    } else {
                        jSONObject2 = jSONObject4.getJSONObject("legales");
                        str = "legal";
                    }
                    Object obj = jSONObject2.get(str);
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(((JSONArray) obj).getString(i));
                        }
                    } else if (obj instanceof String) {
                        arrayList.add((String) obj);
                    }
                }
                legalesFondosBean.setLeyendaLegales(arrayList);
                simularSuscripcionFondoBodyResponseBean.setListaLegales(legalesFondosBean);
                simularSuscripcionFondoResponseBean.setSimularSuscripcionFondoBodyResponseBean(simularSuscripcionFondoBodyResponseBean);
                simularSuscripcionFondoResponseBean.headerBean = privateHeaderBean;
                onResponseBean(simularSuscripcionFondoResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
