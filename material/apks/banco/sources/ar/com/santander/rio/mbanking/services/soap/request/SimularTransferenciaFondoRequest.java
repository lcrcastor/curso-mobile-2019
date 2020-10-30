package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.SimularTransferenciaFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SimularTransferenciaFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class SimularTransferenciaFondoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private SimularTransferenciaFondoResponseBean mSimularTransferenciaFondoResponseBean;
    private TransferirFondoRequestBean mTransferirFondoRequestBean;

    public int getMethod() {
        return 1;
    }

    public SimularTransferenciaFondoRequest(Context context, TransferirFondoRequestBean transferirFondoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mTransferirFondoRequestBean = transferirFondoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mTransferirFondoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mSimularTransferenciaFondoResponseBean == null) {
            this.mSimularTransferenciaFondoResponseBean = new SimularTransferenciaFondoResponseBean();
        }
        return this.mSimularTransferenciaFondoResponseBean.getClass();
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
                SimularTransferenciaFondoResponseBean simularTransferenciaFondoResponseBean = new SimularTransferenciaFondoResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject3.getJSONObject("header").toString(), PrivateHeaderBean.class);
                SimularTransferenciaFondoBodyResponseBean simularTransferenciaFondoBodyResponseBean = new SimularTransferenciaFondoBodyResponseBean();
                JSONObject jSONObject4 = jSONObject3.getJSONObject("body");
                simularTransferenciaFondoBodyResponseBean.setImporteTransferir(jSONObject4.has("importeTransferencia") ? jSONObject4.getString("importeTransferencia") : "");
                simularTransferenciaFondoBodyResponseBean.setMoneda(jSONObject4.has(TarjetasConstants.MONEDA) ? jSONObject4.getString(TarjetasConstants.MONEDA) : "");
                simularTransferenciaFondoBodyResponseBean.setIdFondoOrigen(jSONObject4.has("idFondoOrigen") ? jSONObject4.getString("idFondoOrigen") : "");
                simularTransferenciaFondoBodyResponseBean.setNombreFondoOrigen(jSONObject4.has("nombreFondoOrigen") ? jSONObject4.getString("nombreFondoOrigen") : "");
                simularTransferenciaFondoBodyResponseBean.setCantCuotaOrigen(jSONObject4.has("cantCuotaOrigen") ? jSONObject4.getString("cantCuotaOrigen") : "");
                simularTransferenciaFondoBodyResponseBean.setComisionOrigen(jSONObject4.has("comisionOrigen") ? jSONObject4.getString("comisionOrigen") : "");
                simularTransferenciaFondoBodyResponseBean.setIdFondoDestino(jSONObject4.has("idFondoDestino") ? jSONObject4.getString("idFondoDestino") : "");
                simularTransferenciaFondoBodyResponseBean.setNombreDestino(jSONObject4.has("nombreDestino") ? jSONObject4.getString("nombreDestino") : "");
                simularTransferenciaFondoBodyResponseBean.setMonedaDestino(jSONObject4.has("monedaDestino") ? jSONObject4.getString("monedaDestino") : "");
                simularTransferenciaFondoBodyResponseBean.setComisionDestino(jSONObject4.has("comisionDestino") ? jSONObject4.getString("comisionDestino") : "");
                simularTransferenciaFondoBodyResponseBean.setTermCondiciones(jSONObject4.has("termCondiciones") ? jSONObject4.getString("termCondiciones") : "");
                simularTransferenciaFondoBodyResponseBean.setLeyendaEspecial(jSONObject4.has("leyendaEspecial") ? jSONObject4.getString("leyendaEspecial") : "");
                simularTransferenciaFondoBodyResponseBean.setReglamento(jSONObject4.has("reglamento") ? jSONObject4.getString("reglamento") : "");
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
                simularTransferenciaFondoBodyResponseBean.setListaLegales(legalesFondosBean);
                simularTransferenciaFondoResponseBean.setSimularTransferenciaFondoBodyResponseBean(simularTransferenciaFondoBodyResponseBean);
                simularTransferenciaFondoResponseBean.headerBean = privateHeaderBean;
                onResponseBean(simularTransferenciaFondoResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
