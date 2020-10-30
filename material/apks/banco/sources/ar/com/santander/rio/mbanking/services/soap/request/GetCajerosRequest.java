package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCajerosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCajerosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CajeroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CajerosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCajerosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetCajerosRequest extends BaseRequest implements IBeanRequestWS {
    private GetCajerosRequestBean mGetCajerosRequestBean;
    private GetCajerosResponseBean mGetCajerosResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetCajerosRequest(Context context, GetCajerosRequestBean getCajerosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetCajerosRequestBean = getCajerosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetCajerosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetCajerosResponseBean == null) {
            this.mGetCajerosResponseBean = new GetCajerosResponseBean();
        }
        return this.mGetCajerosResponseBean.getClass();
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
            JSONObject jSONObject2 = null;
            try {
                JSONObject jSONObject3 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                try {
                    onResponseBean((GetCajerosResponseBean) gson.fromJson(jSONObject3.toString(), GetCajerosResponseBean.class));
                } catch (JsonSyntaxException unused) {
                    jSONObject2 = jSONObject3;
                }
            } catch (JsonSyntaxException unused2) {
                if (jSONObject2 != null) {
                    try {
                        GetCajerosResponseBean getCajerosResponseBean = new GetCajerosResponseBean();
                        getCajerosResponseBean.setHeaderBean((HeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), HeaderBean.class));
                        GetCajerosBodyResponseBean getCajerosBodyResponseBean = new GetCajerosBodyResponseBean();
                        JSONObject jSONObject4 = jSONObject2.getJSONObject("body");
                        getCajerosBodyResponseBean.setPagina(jSONObject4.getString("pagina"));
                        getCajerosBodyResponseBean.setPaginas(jSONObject4.getString("paginas"));
                        CajerosBean cajerosBean = new CajerosBean();
                        Object obj = jSONObject4.get("cajeros");
                        if (obj instanceof JSONObject) {
                            Object obj2 = ((JSONObject) obj).get("cajero");
                            if (obj2 != null) {
                                if (obj2 instanceof JSONArray) {
                                    cajerosBean = (CajerosBean) gson.fromJson(obj.toString(), CajerosBean.class);
                                } else if (obj2 instanceof JSONObject) {
                                    CajeroBean cajeroBean = (CajeroBean) gson.fromJson(obj2.toString(), CajeroBean.class);
                                    cajerosBean.setCajeroBeans(new ArrayList());
                                    cajerosBean.getCajeroBeans().add(cajeroBean);
                                }
                            }
                            getCajerosBodyResponseBean.setCajerosBeans(cajerosBean);
                            getCajerosResponseBean.setGetCajerosBodyResponseBean(getCajerosBodyResponseBean);
                            onResponseBean(getCajerosResponseBean);
                        }
                    } catch (JSONException e) {
                        onUnknowError(e);
                    }
                }
                return parserResponse;
            }
        }
        return parserResponse;
    }
}
