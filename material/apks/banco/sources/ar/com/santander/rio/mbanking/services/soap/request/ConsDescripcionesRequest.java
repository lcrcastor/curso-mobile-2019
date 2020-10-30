package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsDescriptionRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsDescriptionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class ConsDescripcionesRequest extends BaseRequest implements IBeanRequestWS {
    private final String TAG = ConsDescripcionesRequest.class.getName();
    private ConsDescriptionRequestBean consDescriptionRequestBean;
    private ConsDescriptionResponseBean consDescriptionResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConsDescripcionesRequest(Context context, ConsDescriptionRequestBean consDescriptionRequestBean2, ErrorRequestServer errorRequestServer, String str, String str2) {
        super(context);
        this.consDescriptionRequestBean = consDescriptionRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consDescriptionRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.consDescriptionResponseBean == null) {
            this.consDescriptionResponseBean = new ConsDescriptionResponseBean();
        }
        return this.consDescriptionResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        ListTableBean listTableBean;
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            ConsDescriptionResponseBean consDescriptionResponseBean2 = new ConsDescriptionResponseBean();
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                consDescriptionResponseBean2.headerBean = (HeaderBean) gson.fromJson(jSONObject2.getJSONObject("body").toString(), HeaderBean.class);
                Object obj = jSONObject2.getJSONObject("body").get("listaTablas");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                    try {
                        listTableBean = (ListTableBean) gson.fromJson(((JSONArray) obj).get(i).toString(), ListTableBean.class);
                    } catch (JsonSyntaxException unused) {
                        if (((JSONObject) ((JSONArray) obj).get(i)).has("listaGrupos")) {
                            ListGroupBean listGroupBean = (ListGroupBean) gson.fromJson(((JSONObject) ((JSONArray) obj).get(i)).get("listaGrupos").toString(), ListGroupBean.class);
                            listTableBean = new ListTableBean();
                            listTableBean.listGroupBeans = new ArrayList();
                            listTableBean.listGroupBeans.add(listGroupBean);
                            listTableBean.idTable = ((JSONObject) ((JSONArray) obj).get(i)).get("idTabla").toString();
                        } else {
                            listTableBean = null;
                        }
                    }
                    if (listTableBean != null) {
                        arrayList.add(listTableBean);
                    }
                }
                consDescriptionResponseBean2.consDescriptionBodyResponseBean.listTableBeans = arrayList;
                onResponseBean(consDescriptionResponseBean2);
            } catch (Exception e) {
                Log.e("@dev", "Error al parsear consDescripciones", e);
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
