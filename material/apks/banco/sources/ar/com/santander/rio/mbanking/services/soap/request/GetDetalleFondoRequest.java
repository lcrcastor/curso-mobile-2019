package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetalleFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.HonorariosFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.InformacionFondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCotizacionesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetDetalleFondoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetDetalleFondoRequestBean mGetDetalleFondoRequestBean;
    private GetDetalleFondoResponseBean mGetDetalleFondoResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetDetalleFondoRequest(Context context, GetDetalleFondoRequestBean getDetalleFondoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetDetalleFondoRequestBean = getDetalleFondoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetDetalleFondoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetDetalleFondoResponseBean == null) {
            this.mGetDetalleFondoResponseBean = new GetDetalleFondoResponseBean();
        }
        return this.mGetDetalleFondoResponseBean.getClass();
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
                GetDetalleFondoResponseBean getDetalleFondoResponseBean = new GetDetalleFondoResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetDetalleFondoBodyResponseBean getDetalleFondoBodyResponseBean = new GetDetalleFondoBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                InformacionFondoBean informacionFondoBean = new InformacionFondoBean();
                if (jSONObject3.has("informacionFondo")) {
                    LegalesFondosBean legalesFondosBean = new LegalesFondosBean();
                    ListaCotizacionesFondosBean listaCotizacionesFondosBean = new ListaCotizacionesFondosBean();
                    informacionFondoBean.setId(jSONObject3.getJSONObject("informacionFondo").has("idFondo") ? jSONObject3.getJSONObject("informacionFondo").getString("idFondo") : "");
                    informacionFondoBean.setNombre(jSONObject3.getJSONObject("informacionFondo").has("nombre") ? jSONObject3.getJSONObject("informacionFondo").getString("nombre") : "");
                    informacionFondoBean.setDescripcionCorta(jSONObject3.getJSONObject("informacionFondo").has("descripcionCorta") ? jSONObject3.getJSONObject("informacionFondo").getString("descripcionCorta") : "");
                    informacionFondoBean.setDescripcionLarga(jSONObject3.getJSONObject("informacionFondo").has("descripcionLarga") ? jSONObject3.getJSONObject("informacionFondo").getString("descripcionLarga") : "");
                    informacionFondoBean.setValorCuotaParte(jSONObject3.getJSONObject("informacionFondo").has("valorCuotaparte") ? jSONObject3.getJSONObject("informacionFondo").getString("valorCuotaparte") : "");
                    informacionFondoBean.setMoneda(jSONObject3.getJSONObject("informacionFondo").has(TarjetasConstants.MONEDA) ? jSONObject3.getJSONObject("informacionFondo").getString(TarjetasConstants.MONEDA) : "");
                    informacionFondoBean.setPlazoPago(jSONObject3.getJSONObject("informacionFondo").has("plazoPago") ? jSONObject3.getJSONObject("informacionFondo").getString("plazoPago") : "");
                    informacionFondoBean.setHorarioDesde(jSONObject3.getJSONObject("informacionFondo").has("horarioDesde") ? jSONObject3.getJSONObject("informacionFondo").getString("horarioDesde") : "");
                    informacionFondoBean.setHorarioHasta(jSONObject3.getJSONObject("informacionFondo").has("horarioHasta") ? jSONObject3.getJSONObject("informacionFondo").getString("horarioHasta") : "");
                    informacionFondoBean.setReglamento(jSONObject3.getJSONObject("informacionFondo").has("reglamento") ? jSONObject3.getJSONObject("informacionFondo").getString("reglamento") : "");
                    informacionFondoBean.setCartera(jSONObject3.getJSONObject("informacionFondo").has("cartera") ? jSONObject3.getJSONObject("informacionFondo").getString("cartera") : "");
                    if (jSONObject3.getJSONObject("informacionFondo").has("cotizaciones") && jSONObject3.getJSONObject("informacionFondo").getJSONObject("cotizaciones").has("cotizacion")) {
                        Object obj = jSONObject3.getJSONObject("informacionFondo").getJSONObject("cotizaciones").get("cotizacion");
                        if (obj instanceof JSONArray) {
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                                arrayList.add(getCotizacionObject(gson, ((JSONArray) obj).getJSONObject(i)));
                            }
                            listaCotizacionesFondosBean.setCotizacionesFondosBean(arrayList);
                            informacionFondoBean.setListaCotizacionesFondosBean(listaCotizacionesFondosBean);
                        } else if (obj instanceof JSONObject) {
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(getCotizacionObject(gson, jSONObject3.getJSONObject("informacionFondo").getJSONObject("cotizaciones").getJSONObject("cotizacion")));
                            listaCotizacionesFondosBean.setCotizacionesFondosBean(arrayList2);
                            informacionFondoBean.setListaCotizacionesFondosBean(listaCotizacionesFondosBean);
                        }
                    }
                    if (jSONObject3.getJSONObject("informacionFondo").has("legales") && jSONObject3.getJSONObject("informacionFondo").getJSONObject("legales").has("leyendaLegal")) {
                        Object obj2 = jSONObject3.getJSONObject("informacionFondo").getJSONObject("legales").get("leyendaLegal");
                        if (obj2 instanceof JSONArray) {
                            ArrayList arrayList3 = new ArrayList();
                            for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                                arrayList3.add(((JSONArray) obj2).getString(i2));
                            }
                            legalesFondosBean.setLeyendaLegales(arrayList3);
                            informacionFondoBean.setLegalesFondosBean(legalesFondosBean);
                        } else if (obj2 instanceof String) {
                            ArrayList arrayList4 = new ArrayList();
                            arrayList4.add((String) obj2);
                            legalesFondosBean.setLeyendaLegales(arrayList4);
                            informacionFondoBean.setLegalesFondosBean(legalesFondosBean);
                        }
                    }
                    if (jSONObject3.getJSONObject("informacionFondo").has("honorarios") && (jSONObject3.getJSONObject("informacionFondo").get("honorarios") instanceof JSONObject)) {
                        informacionFondoBean.setHonorariosFondosBean(new HonorariosFondosBean(jSONObject3.getJSONObject("informacionFondo").getJSONObject("honorarios").has("admin") ? jSONObject3.getJSONObject("informacionFondo").getJSONObject("honorarios").getString("admin") : "", jSONObject3.getJSONObject("informacionFondo").getJSONObject("honorarios").has("entrada") ? jSONObject3.getJSONObject("informacionFondo").getJSONObject("honorarios").getString("entrada") : "", jSONObject3.getJSONObject("informacionFondo").getJSONObject("honorarios").has("salida") ? jSONObject3.getJSONObject("informacionFondo").getJSONObject("honorarios").getString("salida") : ""));
                    }
                    getDetalleFondoBodyResponseBean.setInformacionFondo(informacionFondoBean);
                } else {
                    getDetalleFondoBodyResponseBean.setInformacionFondo(informacionFondoBean);
                }
                getDetalleFondoResponseBean.headerBean = privateHeaderBean;
                getDetalleFondoResponseBean.getDetalleFondoBodyResponseBean = getDetalleFondoBodyResponseBean;
                onResponseBean(getDetalleFondoResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private CotizacionFondosBean getCotizacionObject(Gson gson, JSONObject jSONObject) {
        CotizacionFondosBean cotizacionFondosBean = new CotizacionFondosBean();
        try {
            cotizacionFondosBean.setDetalle(jSONObject.has("detalle") ? jSONObject.getString("detalle") : "");
            cotizacionFondosBean.setValor(jSONObject.has("valor") ? jSONObject.getString("valor") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return cotizacionFondosBean;
    }
}
