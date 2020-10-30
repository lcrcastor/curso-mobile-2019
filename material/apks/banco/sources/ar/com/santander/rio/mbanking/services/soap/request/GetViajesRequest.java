package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.NuevoPagoServiciosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetViajesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetViajesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetViajesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuarioMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajeBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ViajesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetViajesRequest extends BaseRequest implements IBeanRequestWS {
    private GetViajesRequestBean getViajesRequestBean;
    private GetViajesResponseBean getViajesResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetViajesRequest(Context context, GetViajesRequestBean getViajesRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.getViajesRequestBean = getViajesRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetViajesRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.getViajesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.getViajesResponseBean == null) {
            this.getViajesResponseBean = new GetViajesResponseBean();
        }
        return this.getViajesResponseBean.getClass();
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
                GetViajesResponseBean getViajesResponseBean2 = new GetViajesResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetViajesBodyResponseBean getViajesBodyResponseBean = new GetViajesBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                getViajesBodyResponseBean.setAyuda(jSONObject3.has(NuevoPagoServiciosConstants.EXTRA_AYUDA) ? jSONObject3.getString(NuevoPagoServiciosConstants.EXTRA_AYUDA) : "");
                ViajesBean viajesBean = new ViajesBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject3.has("viajes") && jSONObject3.getJSONObject("viajes").has("viaje")) {
                    Object obj = jSONObject3.getJSONObject("viajes").get("viaje");
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getViajeObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                    } else if (obj instanceof JSONObject) {
                        arrayList.add(getViajeObject(gson, jSONObject3.getJSONObject("viajes").getJSONObject("viaje")));
                    }
                }
                viajesBean.setListaViajes(arrayList);
                getViajesBodyResponseBean.setViajes(viajesBean);
                getViajesResponseBean2.header = privateHeaderBean;
                getViajesResponseBean2.getViajesBodyResponseBean = getViajesBodyResponseBean;
                onResponseBean(getViajesResponseBean2);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private ViajeBean getViajeObject(Gson gson, JSONObject jSONObject) {
        ViajeBean viajeBean = new ViajeBean();
        try {
            viajeBean.setId(jSONObject.has("id") ? jSONObject.getString("id") : "");
            viajeBean.setFechaInicio(jSONObject.has("fechaInicio") ? jSONObject.getString("fechaInicio") : "");
            viajeBean.setFechaFin(jSONObject.has("fechaFin") ? jSONObject.getString("fechaFin") : "");
            viajeBean.setMail(jSONObject.has("mail") ? jSONObject.getString("mail") : "");
            viajeBean.setTotalDias(jSONObject.has("totalDias") ? jSONObject.getString("totalDias") : "");
            viajeBean.setTotalDestinos(jSONObject.has("totalDestinos") ? jSONObject.getString("totalDestinos") : "");
            viajeBean.setTotalTarjetas(jSONObject.has("totalTarjetas") ? jSONObject.getString("totalTarjetas") : "");
            viajeBean.setAcciones(jSONObject.has("acciones") ? jSONObject.getString("acciones") : "");
            viajeBean.setReintento(jSONObject.has("reintento") ? jSONObject.getString("reintento") : "");
            PaisesBean paisesBean = new PaisesBean();
            ArrayList arrayList = new ArrayList();
            if (jSONObject.has("paises") && jSONObject.getJSONObject("paises").has("pais")) {
                Object obj = jSONObject.getJSONObject("paises").get("pais");
                if (obj instanceof JSONArray) {
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add(getPaisObject(gson, ((JSONArray) obj).getJSONObject(i)));
                    }
                } else if (obj instanceof JSONObject) {
                    arrayList.add(getPaisObject(gson, jSONObject.getJSONObject("paises").getJSONObject("pais")));
                }
            }
            paisesBean.setListaPaises(arrayList);
            viajeBean.setPaises(paisesBean);
            UsuariosMarcacionBean usuariosMarcacionBean = new UsuariosMarcacionBean();
            ArrayList arrayList2 = new ArrayList();
            if (jSONObject.has("usuarios") && jSONObject.getJSONObject("usuarios").has("usuario")) {
                Object obj2 = jSONObject.getJSONObject("usuarios").get("usuario");
                if (obj2 instanceof JSONArray) {
                    for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                        arrayList2.add(getUsuarioObject(gson, ((JSONArray) obj2).getJSONObject(i2)));
                    }
                } else if (obj2 instanceof JSONObject) {
                    arrayList2.add(getUsuarioObject(gson, jSONObject.getJSONObject("usuarios").getJSONObject("usuario")));
                }
            }
            usuariosMarcacionBean.setListaUsuarios(arrayList2);
            viajeBean.setUsuarios(usuariosMarcacionBean);
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return viajeBean;
    }

    private UsuarioMarcacionBean getUsuarioObject(Gson gson, JSONObject jSONObject) {
        UsuarioMarcacionBean usuarioMarcacionBean = new UsuarioMarcacionBean();
        try {
            usuarioMarcacionBean.setNombre(jSONObject.has("nombre") ? jSONObject.getString("nombre") : "");
            usuarioMarcacionBean.setOtrasTarjetas(jSONObject.has("otrasTarjetas") ? jSONObject.getString("otrasTarjetas") : "");
            TarjetasMarcacionBean tarjetasMarcacionBean = new TarjetasMarcacionBean();
            ArrayList arrayList = new ArrayList();
            if (jSONObject.has("tarjetas") && jSONObject.getJSONObject("tarjetas").has(TarjetasConstants.TARJETA)) {
                Object obj = jSONObject.getJSONObject("tarjetas").get(TarjetasConstants.TARJETA);
                if (obj instanceof JSONArray) {
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add(getTarjetaObject(gson, ((JSONArray) obj).getJSONObject(i)));
                    }
                } else if (obj instanceof JSONObject) {
                    arrayList.add(getTarjetaObject(gson, jSONObject.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA)));
                }
            }
            tarjetasMarcacionBean.setListaTarjetas(arrayList);
            usuarioMarcacionBean.setTarjetas(tarjetasMarcacionBean);
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return usuarioMarcacionBean;
    }

    private PaisBean getPaisObject(Gson gson, JSONObject jSONObject) {
        PaisBean paisBean = new PaisBean();
        try {
            paisBean.setId(jSONObject.has("id") ? jSONObject.getString("id") : "");
            paisBean.setDescripcion(jSONObject.has("descripcion") ? jSONObject.getString("descripcion") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return paisBean;
    }

    private TarjetaMarcacionBean getTarjetaObject(Gson gson, JSONObject jSONObject) {
        TarjetaMarcacionBean tarjetaMarcacionBean = new TarjetaMarcacionBean();
        try {
            tarjetaMarcacionBean.setDescripcion(jSONObject.has("descripcion") ? jSONObject.getString("descripcion") : "");
            tarjetaMarcacionBean.setCondicion(jSONObject.has("condicion") ? jSONObject.getString("condicion") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return tarjetaMarcacionBean;
    }
}
