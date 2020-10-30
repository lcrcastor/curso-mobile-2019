package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConsultaTenenciaPreAutorizacionesRecibidasFragment;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDebinesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDebinesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDebinesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListDebinesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaDebinesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetDebinesRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetDebinesRequestBean mGetDebinesRequestBean;
    private GetDebinesResponseBean mGetDebinesResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetDebinesRequest(Context context, GetDebinesRequestBean getDebinesRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mGetDebinesRequestBean = getDebinesRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetDebinesRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetDebinesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetDebinesResponseBean == null) {
            this.mGetDebinesResponseBean = new GetDebinesResponseBean();
        }
        return this.mGetDebinesResponseBean.getClass();
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
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
                GetDebinesResponseBean getDebinesResponseBean = new GetDebinesResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetDebinesBodyResponseBean getDebinesBodyResponseBean = new GetDebinesBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                ListaDebinesBean listaDebinesBean = new ListaDebinesBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject3.has("debines") && !TextUtils.isEmpty(jSONObject3.get("debines").toString()) && jSONObject3.getJSONObject("debines").has("debin")) {
                    Object obj = jSONObject3.getJSONObject("debines").get("debin");
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getDebinBeanObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                    } else if (obj instanceof JSONObject) {
                        arrayList.add(getDebinBeanObject(gson, jSONObject3.getJSONObject("debines").getJSONObject("debin")));
                    }
                }
                getDebinesBodyResponseBean.setSiguientePagina(jSONObject3.has(ConsultaTenenciaPreAutorizacionesRecibidasFragment.SIGUIENTE_PAGINA) ? jSONObject3.getString(ConsultaTenenciaPreAutorizacionesRecibidasFragment.SIGUIENTE_PAGINA) : "");
                listaDebinesBean.setListDebinesBean(arrayList);
                getDebinesBodyResponseBean.setListaDebinesBean(listaDebinesBean);
                getDebinesBodyResponseBean.resCod = jSONObject3.has("resCod") ? jSONObject3.getString("resCod") : "";
                getDebinesBodyResponseBean.resDesc = jSONObject3.has("resDesc") ? jSONObject3.getString("resDesc") : "";
                getDebinesResponseBean.headerBean = privateHeaderBean;
                getDebinesResponseBean.getDebinesBodyResponseBean = getDebinesBodyResponseBean;
                onResponseBean(getDebinesResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private ListDebinesBean getDebinBeanObject(Gson gson, JSONObject jSONObject) {
        ListDebinesBean listDebinesBean = new ListDebinesBean();
        try {
            listDebinesBean.setMoneda(jSONObject.has(TarjetasConstants.MONEDA) ? jSONObject.getString(TarjetasConstants.MONEDA) : "");
            listDebinesBean.setIdDebin(jSONObject.has("idDebin") ? jSONObject.getString("idDebin") : "");
            listDebinesBean.setCodEstado(jSONObject.has("codEstado") ? jSONObject.getString("codEstado") : "");
            listDebinesBean.setFechaVencimiento(jSONObject.has("fechaVencimiento") ? jSONObject.getString("fechaVencimiento") : "");
            listDebinesBean.setImporte(jSONObject.has("importe") ? jSONObject.getString("importe") : "");
            listDebinesBean.setTitular(jSONObject.has("titular") ? jSONObject.getString("titular") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return listDebinesBean;
    }
}
