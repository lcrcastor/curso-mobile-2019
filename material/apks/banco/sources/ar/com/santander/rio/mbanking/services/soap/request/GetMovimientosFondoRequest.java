package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetMovimientosFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetMovimientosFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetMovimientosFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaMovimientosFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MovimientoFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetMovimientosFondoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetMovimientosFondoRequestBean mGetMovimientosFondoRequestBean;
    private GetMovimientosFondoResponseBean mGetMovimientosFondoResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetMovimientosFondoRequest(Context context, GetMovimientosFondoRequestBean getMovimientosFondoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetMovimientosFondoRequestBean = getMovimientosFondoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetMovimientosFondoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetMovimientosFondoResponseBean == null) {
            this.mGetMovimientosFondoResponseBean = new GetMovimientosFondoResponseBean();
        }
        return this.mGetMovimientosFondoResponseBean.getClass();
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
                GetMovimientosFondoResponseBean getMovimientosFondoResponseBean = new GetMovimientosFondoResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetMovimientosFondoBodyResponseBean getMovimientosFondoBodyResponseBean = new GetMovimientosFondoBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                getMovimientosFondoBodyResponseBean.setIdFondo(jSONObject3.has("idFondo") ? jSONObject3.getString("idFondo") : "");
                ListaMovimientosFondosBean listaMovimientosFondosBean = new ListaMovimientosFondosBean();
                if (!jSONObject3.has("movimientos") || !jSONObject3.getJSONObject("movimientos").has("movimiento")) {
                    listaMovimientosFondosBean.setMovimientosFondosBean(new ArrayList());
                    getMovimientosFondoBodyResponseBean.setListaMovimientosFondosBean(listaMovimientosFondosBean);
                } else {
                    Object obj = jSONObject3.getJSONObject("movimientos").get("movimiento");
                    if (obj instanceof JSONArray) {
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getMovimientoObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                        listaMovimientosFondosBean.setMovimientosFondosBean(arrayList);
                        getMovimientosFondoBodyResponseBean.setListaMovimientosFondosBean(listaMovimientosFondosBean);
                    } else if (obj instanceof JSONObject) {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(getMovimientoObject(gson, jSONObject3.getJSONObject("movimientos").getJSONObject("movimiento")));
                        listaMovimientosFondosBean.setMovimientosFondosBean(arrayList2);
                        getMovimientosFondoBodyResponseBean.setListaMovimientosFondosBean(listaMovimientosFondosBean);
                    }
                }
                getMovimientosFondoResponseBean.headerBean = privateHeaderBean;
                getMovimientosFondoResponseBean.getMovimientosFondoBodyResponseBean = getMovimientosFondoBodyResponseBean;
                onResponseBean(getMovimientosFondoResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private MovimientoFondosBean getMovimientoObject(Gson gson, JSONObject jSONObject) {
        MovimientoFondosBean movimientoFondosBean = new MovimientoFondosBean();
        try {
            movimientoFondosBean.setFecha(jSONObject.has(TarjetasConstants.FECHA) ? jSONObject.getString(TarjetasConstants.FECHA) : "");
            movimientoFondosBean.setConcepto(jSONObject.has("concepto") ? jSONObject.getString("concepto") : "");
            movimientoFondosBean.setImporte(jSONObject.has("importe") ? jSONObject.getString("importe") : "");
            movimientoFondosBean.setCertificado(jSONObject.has("certificado") ? jSONObject.getString("certificado") : "");
            movimientoFondosBean.setCuotapartes(jSONObject.has("cuotaPartes") ? jSONObject.getString("cuotaPartes") : "");
            movimientoFondosBean.setCotizacion(jSONObject.has("cotizacion") ? jSONObject.getString("cotizacion") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return movimientoFondosBean;
    }
}
