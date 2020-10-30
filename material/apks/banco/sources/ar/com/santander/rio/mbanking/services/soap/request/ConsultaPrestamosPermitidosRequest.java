package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class ConsultaPrestamosPermitidosRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaPrestamosPermitidosRequest.class.getName();
    private ConsultaPrestamosPermitidosRequestBean consultaPrestamosPermitidosRequestBean;
    private ConsultaPrestamosPermitidosResponseBean consultaPrestamosPermitidosResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConsultaPrestamosPermitidosRequest(Context context, ConsultaPrestamosPermitidosRequestBean consultaPrestamosPermitidosRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.consultaPrestamosPermitidosRequestBean = consultaPrestamosPermitidosRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConsultaPrestamosPermitidosRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consultaPrestamosPermitidosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.consultaPrestamosPermitidosResponseBean == null) {
            this.consultaPrestamosPermitidosResponseBean = new ConsultaPrestamosPermitidosResponseBean();
        }
        return this.consultaPrestamosPermitidosResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                if (jSONObject3.has("prestPermitidos")) {
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("prestPermitidos");
                    if (jSONObject4.has("datosPrestPerm")) {
                        JSONArray jSONArray = new JSONArray();
                        try {
                            jSONArray = jSONObject4.getJSONArray("datosPrestPerm");
                        } catch (JSONException unused) {
                            jSONArray.put((Object) jSONObject4.getJSONObject("datosPrestPerm"));
                        }
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject jSONObject5 = (JSONObject) jSONArray.get(i);
                            if (jSONObject5.has("listaDestinoPrest")) {
                                JSONObject jSONObject6 = jSONObject5.getJSONObject("listaDestinoPrest");
                                if (jSONObject6.has("listaDestino")) {
                                    JSONArray jSONArray2 = new JSONArray();
                                    try {
                                        jSONArray2 = jSONObject6.getJSONArray("listaDestino");
                                    } catch (JSONException unused2) {
                                        jSONArray2.put((Object) jSONObject6.getJSONObject("listaDestino"));
                                    }
                                    jSONObject6.remove("listaDestino");
                                    jSONObject6.put("listaDestino", (Object) jSONArray2);
                                }
                                jSONObject5.remove("listaDestinoPrest");
                                jSONObject5.put("listaDestinoPrest", (Object) jSONObject6);
                            }
                            jSONArray.put(i, (Object) jSONObject5);
                        }
                        jSONObject4.remove("datosPrestPerm");
                        jSONObject4.put("datosPrestPerm", (Object) jSONArray);
                        jSONObject3.remove("prestPermitidos");
                        jSONObject3.put("prestPermitidos", (Object) jSONObject4);
                    }
                }
                if (jSONObject3.has("listaLeyendas")) {
                    JSONObject jSONObject7 = jSONObject3.getJSONObject("listaLeyendas");
                    if (jSONObject7.has("leyenda")) {
                        JSONArray jSONArray3 = new JSONArray();
                        try {
                            jSONArray3 = jSONObject7.getJSONArray("leyenda");
                        } catch (JSONException unused3) {
                            jSONArray3.put((Object) jSONObject7.getJSONObject("leyenda"));
                        }
                        jSONObject7.remove("leyenda");
                        jSONObject7.put("leyenda", (Object) jSONArray3);
                        jSONObject3.remove("listaLeyendas");
                        jSONObject3.put("listaLeyendas", (Object) jSONObject7);
                    }
                }
                onResponseBean((IBeanWS) gson.fromJson(jSONObject2.toString(), getBeanResponseClass()));
            } catch (Exception e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
