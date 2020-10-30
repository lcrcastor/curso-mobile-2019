package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel.DatosCuota;
import ar.com.santander.rio.mbanking.services.model.ProximasCuotasModel.ProximasCuotas;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleCuotaTenenciaCreditoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetProximasCuotasCreditosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetProximasCuotasResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetProximasCuotasBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetProximasCuotasCreditosRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetDetalleCuotaTenenciaCreditoResponseBean mGetDetalleCuotaTenenciaCreditoResponseBean;
    private GetProximasCuotasCreditosRequestBean mGetProximasCuotasCreditosRequestBean;

    public int getMethod() {
        return 1;
    }

    public GetProximasCuotasCreditosRequest(Context context) {
        super(context, false);
    }

    public GetProximasCuotasCreditosRequest(Context context, GetProximasCuotasCreditosRequestBean getProximasCuotasCreditosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mGetProximasCuotasCreditosRequestBean = getProximasCuotasCreditosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetProximasCuotasCreditosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetDetalleCuotaTenenciaCreditoResponseBean == null) {
            this.mGetDetalleCuotaTenenciaCreditoResponseBean = new GetDetalleCuotaTenenciaCreditoResponseBean();
        }
        return this.mGetDetalleCuotaTenenciaCreditoResponseBean.getClass();
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
                GetProximasCuotasResponseBean getProximasCuotasResponseBean = new GetProximasCuotasResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetProximasCuotasBodyResponseBean getProximasCuotasBodyResponseBean = new GetProximasCuotasBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                ProximasCuotas proximasCuotas = new ProximasCuotas();
                Object obj = jSONObject3.getJSONObject("proximasCuotasCredito").get("datosCuota");
                if (obj instanceof JSONArray) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add((DatosCuota) gson.fromJson(String.valueOf(((JSONArray) obj).getJSONObject(i)), DatosCuota.class));
                        proximasCuotas.datosCuota = arrayList;
                    }
                    getProximasCuotasBodyResponseBean.proximasCuotas = proximasCuotas;
                } else if (obj instanceof JSONObject) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add((DatosCuota) gson.fromJson(String.valueOf(obj), DatosCuota.class));
                    proximasCuotas.datosCuota = arrayList2;
                    getProximasCuotasBodyResponseBean.proximasCuotas = proximasCuotas;
                }
                getProximasCuotasResponseBean.headerBean = privateHeaderBean;
                getProximasCuotasResponseBean.mGetProximasCuotasBodyResponseBean = getProximasCuotasBodyResponseBean;
                onResponseBean(getProximasCuotasResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
