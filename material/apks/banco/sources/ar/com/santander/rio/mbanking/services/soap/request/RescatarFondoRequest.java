package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.RescatarFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.RescatarFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RescatarFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class RescatarFondoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private RescatarFondoRequestBean mRescatarFondoRequestBean;
    private RescatarFondoResponseBean mRescatarFondoResponseBean;

    public int getMethod() {
        return 1;
    }

    public RescatarFondoRequest(Context context, RescatarFondoRequestBean rescatarFondoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mRescatarFondoRequestBean = rescatarFondoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mRescatarFondoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mRescatarFondoResponseBean == null) {
            this.mRescatarFondoResponseBean = new RescatarFondoResponseBean();
        }
        return this.mRescatarFondoResponseBean.getClass();
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
                RescatarFondoResponseBean rescatarFondoResponseBean = new RescatarFondoResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject3.getJSONObject("header").toString(), PrivateHeaderBean.class);
                RescatarFondoBodyResponseBean rescatarFondoBodyResponseBean = new RescatarFondoBodyResponseBean();
                JSONObject jSONObject4 = jSONObject3.getJSONObject("body");
                rescatarFondoBodyResponseBean.setIdFondo(jSONObject4.has("idFondo") ? jSONObject4.getString("idFondo") : "");
                rescatarFondoBodyResponseBean.setFecha(jSONObject4.has(TarjetasConstants.FECHA) ? jSONObject4.getString(TarjetasConstants.FECHA) : "");
                rescatarFondoBodyResponseBean.setHora(jSONObject4.has("hora") ? jSONObject4.getString("hora") : "");
                rescatarFondoBodyResponseBean.setImporteRescate(jSONObject4.has("importeRescate") ? jSONObject4.getString("importeRescate") : "");
                rescatarFondoBodyResponseBean.setComision(jSONObject4.has("comision") ? jSONObject4.getString("comision") : "");
                rescatarFondoBodyResponseBean.setImporteNeto(jSONObject4.has("importeNeto") ? jSONObject4.getString("importeNeto") : "");
                rescatarFondoBodyResponseBean.setCantCuotaParte(jSONObject4.has("cantCuotaparte") ? jSONObject4.getString("cantCuotaparte") : "");
                rescatarFondoBodyResponseBean.setNroCertificado(jSONObject4.has("nroCertificado") ? jSONObject4.getString("nroCertificado") : "");
                rescatarFondoBodyResponseBean.setPlazoPago(jSONObject4.has("plazoPago") ? jSONObject4.getString("plazoPago") : "");
                rescatarFondoBodyResponseBean.setTermCondiciones(jSONObject4.has("termCondiciones") ? jSONObject4.getString("termCondiciones") : "");
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
                rescatarFondoBodyResponseBean.setLegales(legalesFondosBean);
                rescatarFondoResponseBean.setRescatarFondoBodyResponseBean(rescatarFondoBodyResponseBean);
                rescatarFondoResponseBean.headerBean = privateHeaderBean;
                onResponseBean(rescatarFondoResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
