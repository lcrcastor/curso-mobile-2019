package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesHomeRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetPromocionesHomeResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesHomeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionSucursalBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PromocionesBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetPromocionesHomeRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = GetPromocionesHomeRequest.class.getName();
    private GetPromocionesHomeRequestBean mGetPromocionesHomeRequestBean;
    private GetPromocionesHomeResponseBean mGetPromocionesHomeResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetPromocionesHomeRequest(Context context, GetPromocionesHomeRequestBean getPromocionesHomeRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mErrorRequestServer = errorRequestServer;
        this.mGetPromocionesHomeRequestBean = getPromocionesHomeRequestBean;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetPromocionesHomeRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetPromocionesHomeResponseBean == null) {
            this.mGetPromocionesHomeResponseBean = new GetPromocionesHomeResponseBean();
        }
        return this.mGetPromocionesHomeResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            GetPromocionesHomeResponseBean getPromocionesHomeResponseBean = new GetPromocionesHomeResponseBean();
            GetPromocionesHomeBodyResponseBean getPromocionesHomeBodyResponseBean = new GetPromocionesHomeBodyResponseBean();
            try {
                PromocionesBean promocionesBean = new PromocionesBean();
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").getJSONObject("body");
                if (jSONObject2.has("promociones")) {
                    Object obj = jSONObject2.get("promociones");
                    if (obj instanceof String) {
                        promocionesBean.setPromocionSucursalBeanList(new ArrayList());
                        getPromocionesHomeBodyResponseBean.setPromocionesBean(promocionesBean);
                    } else if (obj instanceof JSONObject) {
                        Object obj2 = ((JSONObject) obj).get("promocionSucursal");
                        if (obj2 instanceof JSONObject) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add((PromocionSucursalBean) gson.fromJson(((JSONObject) obj2).toString(), PromocionSucursalBean.class));
                            promocionesBean.setPromocionSucursalBeanList(arrayList);
                            getPromocionesHomeBodyResponseBean.setPromocionesBean(promocionesBean);
                        } else if (obj2 instanceof JSONArray) {
                            getPromocionesHomeBodyResponseBean.setPromocionesBean((PromocionesBean) gson.fromJson(((JSONObject) obj).toString(), PromocionesBean.class));
                        }
                    }
                } else {
                    promocionesBean.setPromocionSucursalBeanList(new ArrayList());
                    getPromocionesHomeBodyResponseBean.setPromocionesBean(promocionesBean);
                }
                getPromocionesHomeResponseBean.setGetPromocionesHomeBodyResponseBean(getPromocionesHomeBodyResponseBean);
                onResponseBean(getPromocionesHomeResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
