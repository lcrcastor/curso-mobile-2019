package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTarjPaisesRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTarjPaisesResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTarjPaisesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PaisesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetasMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuarioMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UsuariosMarcacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.versions.FechasInhabilitadasBean;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetTarjPaisesRequest extends BaseRequest implements IBeanRequestWS {
    private GetTarjPaisesRequestBean getTarjPaisesRequestBean;
    private GetTarjPaisesResponseBean getTarjPaisesResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetTarjPaisesRequest(Context context, GetTarjPaisesRequestBean getTarjPaisesRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.getTarjPaisesRequestBean = getTarjPaisesRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.getTarjPaisesRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.getTarjPaisesResponseBean == null) {
            this.getTarjPaisesResponseBean = new GetTarjPaisesResponseBean();
        }
        return this.getTarjPaisesResponseBean.getClass();
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
                GetTarjPaisesResponseBean getTarjPaisesResponseBean2 = new GetTarjPaisesResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetTarjPaisesBodyResponseBean getTarjPaisesBodyResponseBean = new GetTarjPaisesBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                getTarjPaisesBodyResponseBean.setFechaInicioMax(jSONObject3.has("fechaInicioMax") ? jSONObject3.getString("fechaInicioMax") : "");
                getTarjPaisesBodyResponseBean.setDuracionMax(jSONObject3.has("duracionMax") ? jSONObject3.getString("duracionMax") : "");
                PaisesBean paisesBean = new PaisesBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject3.has("paises") && jSONObject3.getJSONObject("paises").has("pais")) {
                    Object obj = jSONObject3.getJSONObject("paises").get("pais");
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getPaisObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                    } else if (obj instanceof JSONObject) {
                        arrayList.add(getPaisObject(gson, jSONObject3.getJSONObject("paises").getJSONObject("pais")));
                    }
                }
                paisesBean.setListaPaises(arrayList);
                getTarjPaisesBodyResponseBean.setPaises(paisesBean);
                UsuariosMarcacionBean usuariosMarcacionBean = new UsuariosMarcacionBean();
                ArrayList arrayList2 = new ArrayList();
                if (jSONObject3.has("usuarios") && jSONObject3.getJSONObject("usuarios").has("usuario")) {
                    Object obj2 = jSONObject3.getJSONObject("usuarios").get("usuario");
                    if (obj2 instanceof JSONArray) {
                        for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                            arrayList2.add(getUsuarioObject(gson, ((JSONArray) obj2).getJSONObject(i2)));
                        }
                    } else if (obj2 instanceof JSONObject) {
                        arrayList2.add(getUsuarioObject(gson, jSONObject3.getJSONObject("usuarios").getJSONObject("usuario")));
                    }
                }
                usuariosMarcacionBean.setListaUsuarios(arrayList2);
                getTarjPaisesBodyResponseBean.setUsuarios(usuariosMarcacionBean);
                getTarjPaisesResponseBean2.header = privateHeaderBean;
                getTarjPaisesResponseBean2.getTarjPaisesBodyResponseBean = getTarjPaisesBodyResponseBean;
                onResponseBean(getTarjPaisesResponseBean2);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private UsuarioMarcacionBean getUsuarioObject(Gson gson, JSONObject jSONObject) {
        UsuarioMarcacionBean usuarioMarcacionBean = new UsuarioMarcacionBean();
        try {
            usuarioMarcacionBean.setNombre(jSONObject.has("nombre") ? jSONObject.getString("nombre") : "");
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
            FechasInhabilitadasBean fechasInhabilitadasBean = new FechasInhabilitadasBean();
            ArrayList arrayList = new ArrayList();
            if (jSONObject.has("fechasInhabilitadas") && jSONObject.getJSONObject("fechasInhabilitadas").has(TarjetasConstants.FECHA)) {
                Object obj = jSONObject.getJSONObject("fechasInhabilitadas").get(TarjetasConstants.FECHA);
                if (obj instanceof JSONArray) {
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add(((JSONArray) obj).getString(i));
                    }
                } else if (obj instanceof JSONObject) {
                    arrayList.add(jSONObject.getJSONObject("fechasInhabilitadas").getString(TarjetasConstants.FECHA));
                }
            }
            fechasInhabilitadasBean.setListaFechas(arrayList);
            tarjetaMarcacionBean.setListaFechasInhabilitadas(fechasInhabilitadasBean);
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return tarjetaMarcacionBean;
    }
}
