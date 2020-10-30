package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.model.general.Autorizaciones;
import ar.com.santander.rio.mbanking.services.model.general.DatosTarjeta;
import ar.com.santander.rio.mbanking.services.model.general.DocumentLimites;
import ar.com.santander.rio.mbanking.services.model.general.FechaLiquidacion;
import ar.com.santander.rio.mbanking.services.model.general.Limite;
import ar.com.santander.rio.mbanking.services.model.general.Limites;
import ar.com.santander.rio.mbanking.services.model.general.SaldoenCuenta;
import ar.com.santander.rio.mbanking.services.model.general.TarjetaLimites;
import ar.com.santander.rio.mbanking.services.soap.beans.LimitesYDisponiblesTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.LimitesYDisponiblesTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class LimitesYDisponiblesTCRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private static final String TAG = "ar.com.santander.rio.mbanking.services.soap.request.LimitesYDisponiblesTCRequest";
    private LimitesYDisponiblesTCRequestBean limitesYDisponiblesTCRequestBean;
    private LimitesYDisponiblesTCResponseBean limitesYDisponiblesTCResponseBean;

    public int getMethod() {
        return 1;
    }

    public LimitesYDisponiblesTCRequest(Context context, LimitesYDisponiblesTCRequestBean limitesYDisponiblesTCRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.limitesYDisponiblesTCRequestBean = limitesYDisponiblesTCRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.limitesYDisponiblesTCRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.limitesYDisponiblesTCResponseBean == null) {
            this.limitesYDisponiblesTCResponseBean = new LimitesYDisponiblesTCResponseBean();
        }
        return this.limitesYDisponiblesTCResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean z;
        boolean parserResponse = super.parserResponse(jSONObject);
        if (!parserResponse) {
            return parserResponse;
        }
        Gson gson = new Gson();
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
            LimitesYDisponiblesTCResponseBean limitesYDisponiblesTCResponseBean2 = new LimitesYDisponiblesTCResponseBean();
            limitesYDisponiblesTCResponseBean2.setHeader((PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class));
            LimitesYDisponiblesTCBodyResponseBean limitesYDisponiblesTCBodyResponseBean = new LimitesYDisponiblesTCBodyResponseBean();
            TarjetaLimites tarjetaLimites = new TarjetaLimites();
            DocumentLimites documentLimites = new DocumentLimites();
            new DatosTarjeta();
            new Autorizaciones();
            SaldoenCuenta saldoenCuenta = new SaldoenCuenta();
            Limites limites = new Limites();
            new FechaLiquidacion();
            JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("responseBody ");
            sb.append(jSONObject3.toString());
            Log.d(str, sb.toString());
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("datos ");
            sb2.append(jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("document").getJSONObject("datos").toString());
            Log.d(str2, sb2.toString());
            DatosTarjeta datosTarjeta = (DatosTarjeta) gson.fromJson(jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("document").getJSONObject("datos").toString(), DatosTarjeta.class);
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("autorizaciones ");
            sb3.append(jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("document").getJSONObject("autorizaciones").toString());
            Log.d(str3, sb3.toString());
            Autorizaciones autorizaciones = (Autorizaciones) gson.fromJson(jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("document").getJSONObject("autorizaciones").toString(), Autorizaciones.class);
            Object obj = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("document").getJSONObject("saldoenCuenta").getJSONObject("limites").get("limite");
            ArrayList arrayList = new ArrayList();
            if (obj != null) {
                if (obj instanceof JSONObject) {
                    arrayList.add((Limite) gson.fromJson(obj.toString(), Limite.class));
                } else if (obj instanceof JSONArray) {
                    int i = 0;
                    z = parserResponse;
                    while (i < ((JSONArray) obj).length()) {
                        try {
                            Object obj2 = obj;
                            arrayList.add((Limite) gson.fromJson(((JSONArray) obj).get(i).toString(), Limite.class));
                            i++;
                            obj = obj2;
                        } catch (JSONException e) {
                            e = e;
                            JSONException jSONException = e;
                            onUnknowError(jSONException);
                            Log.d("@dev", jSONException.toString());
                            return z;
                        } catch (Exception e2) {
                            e = e2;
                            Exception exc = e;
                            onUnknowError(exc);
                            Log.d("@dev", exc.toString());
                            return z;
                        }
                    }
                    limites.setLimites(arrayList);
                    saldoenCuenta.setLimites(limites);
                }
                z = parserResponse;
                limites.setLimites(arrayList);
                saldoenCuenta.setLimites(limites);
            } else {
                z = parserResponse;
            }
            saldoenCuenta.setLimitesUnificados(jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("document").getJSONObject("saldoenCuenta").get("limitesUnificados").toString());
            FechaLiquidacion fechaLiquidacion = (FechaLiquidacion) gson.fromJson(jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("document").getJSONObject("fechas").toString(), FechaLiquidacion.class);
            documentLimites.setDatos(datosTarjeta);
            documentLimites.setAutorizaciones(autorizaciones);
            documentLimites.setSaldoenCuenta(saldoenCuenta);
            documentLimites.setFechas(fechaLiquidacion);
            tarjetaLimites.setDocument(documentLimites);
            limitesYDisponiblesTCBodyResponseBean.setTarjetas(tarjetaLimites);
            limitesYDisponiblesTCResponseBean2.setBody(limitesYDisponiblesTCBodyResponseBean);
            onResponseBean(limitesYDisponiblesTCResponseBean2);
            return z;
        } catch (JSONException e3) {
            e = e3;
            z = parserResponse;
            JSONException jSONException2 = e;
            onUnknowError(jSONException2);
            Log.d("@dev", jSONException2.toString());
            return z;
        } catch (Exception e4) {
            e = e4;
            z = parserResponse;
            Exception exc2 = e;
            onUnknowError(exc2);
            Log.d("@dev", exc2.toString());
            return z;
        }
    }
}
