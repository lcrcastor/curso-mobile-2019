package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSeguroRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFirmaSeguroResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FirmaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFirmaSeguroBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class GetFirmaSeguroRequest extends BaseRequest implements IBeanRequestWS {
    private GetFirmaSeguroRequestBean mgetFirmaSeguroRequestBean;
    private GetFirmaSeguroResponseBean mgetFirmaSeguroResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetFirmaSeguroRequest(Context context, GetFirmaSeguroRequestBean getFirmaSeguroRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mgetFirmaSeguroRequestBean = getFirmaSeguroRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mgetFirmaSeguroRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mgetFirmaSeguroResponseBean == null) {
            this.mgetFirmaSeguroResponseBean = new GetFirmaSeguroResponseBean();
        }
        return this.mgetFirmaSeguroResponseBean.getClass();
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
                GetFirmaSeguroResponseBean getFirmaSeguroResponseBean = new GetFirmaSeguroResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetFirmaSeguroBodyResponseBean getFirmaSeguroBodyResponseBean = new GetFirmaSeguroBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                FirmaSeguroBean firmaSeguroBean = new FirmaSeguroBean();
                String str = "";
                if (jSONObject3.has("firmaSeguro") && !TextUtils.isEmpty(jSONObject3.get("firmaSeguro").toString())) {
                    str = jSONObject3.getString("firmaSeguro");
                }
                firmaSeguroBean.setFirmaSeguro(str);
                getFirmaSeguroBodyResponseBean.setFirmaSeguros(firmaSeguroBean);
                getFirmaSeguroResponseBean.header = privateHeaderBean;
                getFirmaSeguroResponseBean.setGetSegurosBodyResponseBean(getFirmaSeguroBodyResponseBean);
                onResponseBean(getFirmaSeguroResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
