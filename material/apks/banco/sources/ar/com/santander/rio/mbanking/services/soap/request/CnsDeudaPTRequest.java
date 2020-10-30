package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.Cuentas;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.model.general.Tarjetas;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaPTRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.CnsDeudaPTResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaPTBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaPTBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices;
import ar.com.santander.rio.mbanking.services.soap.versions.VCnsDeuda;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class CnsDeudaPTRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = CnsDeudaPTRequest.class.getName();
    private CnsDeudaPTRequestBean cnsDeudaPTRequestBean;
    private CnsDeudaPTResponseBean cnsDeudaPTResponseBean;

    public int getMethod() {
        return 1;
    }

    public CnsDeudaPTRequest(Context context, CnsDeudaPTRequestBean cnsDeudaPTRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.cnsDeudaPTRequestBean = cnsDeudaPTRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public CnsDeudaPTRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public CnsDeudaPTRequest(Context context, CnsDeudaPTBodyRequestBean cnsDeudaPTBodyRequestBean, ErrorRequestServer errorRequestServer, String str, String str2) {
        super(context);
        this.cnsDeudaPTRequestBean = new CnsDeudaPTRequestBean(getPrivateHeaderBean(VCnsDeuda.getData(EVersionServices.CURRENT), str, str2), cnsDeudaPTBodyRequestBean);
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.cnsDeudaPTRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.cnsDeudaPTResponseBean == null) {
            this.cnsDeudaPTResponseBean = new CnsDeudaPTResponseBean();
        }
        return this.cnsDeudaPTResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("parserResponse JSONObject ");
        sb.append(jSONObject.toString());
        Log.d(str, sb.toString());
        boolean parserResponse = super.parserResponse(jSONObject);
        String str2 = this.TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("responseOK ");
        sb2.append(parserResponse);
        Log.d(str2, sb2.toString());
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                CnsDeudaPTResponseBean cnsDeudaPTResponseBean2 = new CnsDeudaPTResponseBean();
                cnsDeudaPTResponseBean2.setHeaderBean((HeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), HeaderBean.class));
                CnsDeudaPTBodyResponseBean cnsDeudaPTBodyResponseBean = new CnsDeudaPTBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                String str3 = this.TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("horaBancaria ");
                sb3.append(jSONObject3.getString("horaBancaria"));
                Log.d(str3, sb3.toString());
                cnsDeudaPTBodyResponseBean.setHoraBancaria(jSONObject3.getString("horaBancaria"));
                if (jSONObject3.has("mensajeHBancario")) {
                    String str4 = this.TAG;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("mensajeHBancario ");
                    sb4.append(jSONObject3.getString("mensajeHBancario"));
                    Log.d(str4, sb4.toString());
                    cnsDeudaPTBodyResponseBean.setMensajeHBancario(jSONObject3.getString("mensajeHBancario"));
                }
                String str5 = this.TAG;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("fechaActual ");
                sb5.append(jSONObject3.getString("fechaActual"));
                Log.d(str5, sb5.toString());
                cnsDeudaPTBodyResponseBean.setFechaActual(jSONObject3.getString("fechaActual"));
                Cuentas cuentas = new Cuentas();
                String str6 = this.TAG;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("formaMoneda ");
                sb6.append(jSONObject3.getJSONObject("cuentas").getString("formaMoneda"));
                Log.d(str6, sb6.toString());
                cuentas.setFormaMoneda(jSONObject3.getJSONObject("cuentas").getString("formaMoneda"));
                Object obj = jSONObject3.getJSONObject("cuentas").get("cta");
                if (obj instanceof JSONArray) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        Cuenta cuenta = (Cuenta) gson.fromJson(((JSONArray) obj).getJSONObject(i).toString(), Cuenta.class);
                        String str7 = this.TAG;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("cuenta.getNumero() ");
                        sb7.append(cuenta.getNumero());
                        Log.d(str7, sb7.toString());
                        arrayList.add(cuenta);
                    }
                    cuentas.setCuentas(arrayList);
                    cnsDeudaPTBodyResponseBean.setCuentas(cuentas);
                } else if (obj instanceof JSONObject) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add((Cuenta) gson.fromJson(obj.toString(), Cuenta.class));
                    cuentas.setCuentas(arrayList2);
                    cnsDeudaPTBodyResponseBean.setCuentas(cuentas);
                }
                String str8 = this.TAG;
                StringBuilder sb8 = new StringBuilder();
                sb8.append("tarjetas ");
                sb8.append(jSONObject3.getJSONObject("tarjetas").get(TarjetasConstants.TARJETA).toString());
                Log.d(str8, sb8.toString());
                Tarjetas tarjetas = new Tarjetas();
                Object obj2 = jSONObject3.getJSONObject("tarjetas").get(TarjetasConstants.TARJETA);
                if (obj2 instanceof JSONArray) {
                    ArrayList arrayList3 = new ArrayList();
                    for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                        Tarjeta tarjeta = (Tarjeta) gson.fromJson(((JSONArray) obj2).getJSONObject(i2).toString(), Tarjeta.class);
                        String str9 = this.TAG;
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("tarjeta.getNumTarjeta() ");
                        sb9.append(tarjeta.getNumTarjeta());
                        Log.d(str9, sb9.toString());
                        arrayList3.add(tarjeta);
                    }
                    tarjetas.setTarjetas(arrayList3);
                    cnsDeudaPTBodyResponseBean.setTarjetas(tarjetas);
                } else if (obj2 instanceof JSONObject) {
                    ArrayList arrayList4 = new ArrayList();
                    arrayList4.add((Tarjeta) gson.fromJson(obj2.toString(), Tarjeta.class));
                    tarjetas.setTarjetas(arrayList4);
                    cnsDeudaPTBodyResponseBean.setTarjetas(tarjetas);
                }
                if (jSONObject3.has("stop_mensaje_mes")) {
                    String str10 = this.TAG;
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append("stop_mensaje_mes ");
                    sb10.append(jSONObject3.getString("stop_mensaje_mes"));
                    Log.d(str10, sb10.toString());
                    cnsDeudaPTBodyResponseBean.setStopMensajeMes(jSONObject3.getString("stop_mensaje_mes"));
                }
                if (jSONObject3.has("stop_recordatorio")) {
                    String str11 = this.TAG;
                    StringBuilder sb11 = new StringBuilder();
                    sb11.append("stop_recordatorio ");
                    sb11.append(jSONObject3.getString("stop_recordatorio"));
                    Log.d(str11, sb11.toString());
                    cnsDeudaPTBodyResponseBean.setStopRecordatorio(jSONObject3.getString("stop_recordatorio"));
                }
                if (jSONObject3.has("cotizacionDolar")) {
                    String str12 = this.TAG;
                    StringBuilder sb12 = new StringBuilder();
                    sb12.append("cotizacionDolar ");
                    sb12.append(jSONObject3.getString("cotizacionDolar"));
                    Log.d(str12, sb12.toString());
                    cnsDeudaPTBodyResponseBean.setCotizacionDolar(jSONObject3.getString("cotizacionDolar"));
                }
                cnsDeudaPTResponseBean2.setCnsDeudaPTBodyResponseBean(cnsDeudaPTBodyResponseBean);
                onResponseBean(cnsDeudaPTResponseBean2);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
