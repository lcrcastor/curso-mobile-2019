package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNotificacionesPushRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetNotificacionesPushResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetNotificacionesPushBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.NotificacionPushBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.NotificacionesPushBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetNotificacionesPushRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetNotificacionesPushRequestBean mGetNotificacionesPushRequestBean;
    private GetNotificacionesPushResponseBean mGetNotificacionesPushResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetNotificacionesPushRequest(Context context, GetNotificacionesPushRequestBean getNotificacionesPushRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetNotificacionesPushRequestBean = getNotificacionesPushRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetNotificacionesPushRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetNotificacionesPushRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetNotificacionesPushResponseBean == null) {
            this.mGetNotificacionesPushResponseBean = new GetNotificacionesPushResponseBean();
        }
        return this.mGetNotificacionesPushResponseBean.getClass();
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
                GetNotificacionesPushResponseBean getNotificacionesPushResponseBean = new GetNotificacionesPushResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetNotificacionesPushBodyResponseBean getNotificacionesPushBodyResponseBean = new GetNotificacionesPushBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                getNotificacionesPushBodyResponseBean.setMasNov(jSONObject3.has("masNov") ? jSONObject3.getString("masNov") : "N");
                NotificacionesPushBean notificacionesPushBean = new NotificacionesPushBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject3.has("notificaciones") && jSONObject3.get("notificaciones") != "" && jSONObject3.getJSONObject("notificaciones").has("notificacion")) {
                    Object obj = jSONObject3.getJSONObject("notificaciones").get("notificacion");
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            try {
                                arrayList.add(gson.fromJson(((JSONArray) obj).getJSONObject(i).toString(), NotificacionPushBean.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (obj instanceof JSONObject) {
                        arrayList.add(gson.fromJson(obj.toString(), NotificacionPushBean.class));
                    }
                }
                notificacionesPushBean.setNotificacion(arrayList);
                getNotificacionesPushBodyResponseBean.setNotificaciones(notificacionesPushBean);
                getNotificacionesPushResponseBean.setGetNotificacionesPushBodyResponseBean(getNotificacionesPushBodyResponseBean);
                getNotificacionesPushResponseBean.setHeaderBean(privateHeaderBean);
                onResponseBean(getNotificacionesPushResponseBean);
            } catch (JSONException e2) {
                onUnknowError(e2);
            }
        }
        return parserResponse;
    }
}
