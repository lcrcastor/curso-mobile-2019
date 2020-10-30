package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetInfoAdmFondosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetInfoAdmFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetInfoAdmFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.HonorariosFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.HorariosFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesAdmFondos;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetInfoAdmFondosRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetInfoAdmFondosRequestBean mGetInfoAdmFondosRequestBean;
    private GetInfoAdmFondosResponseBean mGetInfoAdmFondosResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetInfoAdmFondosRequest(Context context, GetInfoAdmFondosRequestBean getInfoAdmFondosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetInfoAdmFondosRequestBean = getInfoAdmFondosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetInfoAdmFondosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetInfoAdmFondosResponseBean == null) {
            this.mGetInfoAdmFondosResponseBean = new GetInfoAdmFondosResponseBean();
        }
        return this.mGetInfoAdmFondosResponseBean.getClass();
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
                GetInfoAdmFondosResponseBean getInfoAdmFondosResponseBean = new GetInfoAdmFondosResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetInfoAdmFondosBodyResponseBean getInfoAdmFondosBodyResponseBean = new GetInfoAdmFondosBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                ListaFondosBean listaFondosBean = new ListaFondosBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject3.has(Constants.DIR_STORAGE_FUNDS) && jSONObject3.getJSONObject(Constants.DIR_STORAGE_FUNDS).has("fondo")) {
                    Object obj = jSONObject3.getJSONObject(Constants.DIR_STORAGE_FUNDS).get("fondo");
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getFondoObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                    } else if (obj instanceof JSONObject) {
                        arrayList.add(getFondoObject(gson, jSONObject3.getJSONObject(Constants.DIR_STORAGE_FUNDS).getJSONObject("fondo")));
                    }
                }
                listaFondosBean.setFondosBean(arrayList);
                getInfoAdmFondosBodyResponseBean.setListaFondosBean(listaFondosBean);
                LegalesAdmFondos legalesAdmFondos = new LegalesAdmFondos();
                if (jSONObject3.has("legales") && (jSONObject3.get("legales") instanceof Object)) {
                    String str = "";
                    String str2 = "";
                    if (jSONObject3.getJSONObject("legales").has("legalHonorarios")) {
                        str2 = jSONObject3.getJSONObject("legales").getString("legalHonorarios");
                    }
                    if (jSONObject3.getJSONObject("legales").has("legalHorarios")) {
                        str = jSONObject3.getJSONObject("legales").getString("legalHorarios");
                    }
                    legalesAdmFondos.setLegalHonorarios(str2);
                    legalesAdmFondos.setLegalHorarios(str);
                }
                getInfoAdmFondosBodyResponseBean.setLegales(legalesAdmFondos);
                getInfoAdmFondosResponseBean.headerBean = privateHeaderBean;
                getInfoAdmFondosResponseBean.getInfoAdmFondosBodyResponseBean = getInfoAdmFondosBodyResponseBean;
                onResponseBean(getInfoAdmFondosResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private FondoBean getFondoObject(Gson gson, JSONObject jSONObject) {
        JSONObject jSONObject2;
        String str;
        FondoBean fondoBean = new FondoBean();
        LegalesFondosBean legalesFondosBean = new LegalesFondosBean();
        try {
            fondoBean.setId(jSONObject.has("idFondo") ? jSONObject.getString("idFondo") : "");
            fondoBean.setNombre(jSONObject.has("nombre") ? jSONObject.getString("nombre") : "");
            fondoBean.setVariacionCotizaDiaria(jSONObject.has("variacionCotizaDiaria") ? jSONObject.getString("variacionCotizaDiaria") : "");
            fondoBean.setCantidadCuotapartes(jSONObject.has("cantidadCuotapartes") ? jSONObject.getString("cantidadCuotapartes") : "");
            fondoBean.setValorCuotaParte(jSONObject.has("valorCuotaparte") ? jSONObject.getString("valorCuotaparte") : "");
            fondoBean.setImporte(jSONObject.has("importe") ? jSONObject.getString("importe") : "");
            fondoBean.setMoneda(jSONObject.has(TarjetasConstants.MONEDA) ? jSONObject.getString(TarjetasConstants.MONEDA) : "");
            fondoBean.setPlazoPago(jSONObject.has("plazoPago") ? jSONObject.getString("plazoPago") : "");
            fondoBean.setHorarioDesde(jSONObject.has("horarioDesde") ? jSONObject.getString("horarioDesde") : "");
            fondoBean.setHorarioHasta(jSONObject.has("horarioHasta") ? jSONObject.getString("horarioHasta") : "");
            fondoBean.setValorCuota(jSONObject.has("valorCuota") ? jSONObject.getString("valorCuota") : "");
            fondoBean.setValorUltimoDia(jSONObject.has("valorUltimoDia") ? jSONObject.getString("valorUltimoDia") : "");
            fondoBean.setValorUltimoMes(jSONObject.has("valorUltimoMes") ? jSONObject.getString("valorUltimoMes") : "");
            fondoBean.setValorUltimoTrimestre(jSONObject.has("valorUltimoTrimestre") ? jSONObject.getString("valorUltimoTrimestre") : "");
            fondoBean.setValorUltimoAno(jSONObject.has("valorUltimoAno") ? jSONObject.getString("valorUltimoAno") : "");
            HonorariosFondosBean honorariosFondosBean = new HonorariosFondosBean();
            if (jSONObject.has("honorarios") && (jSONObject.get("honorarios") instanceof JSONObject)) {
                String string = jSONObject.getJSONObject("honorarios").has("admin") ? jSONObject.getJSONObject("honorarios").getString("admin") : "";
                String string2 = jSONObject.getJSONObject("honorarios").has("entrada") ? jSONObject.getJSONObject("honorarios").getString("entrada") : "";
                String string3 = jSONObject.getJSONObject("honorarios").has("salida") ? jSONObject.getJSONObject("honorarios").getString("salida") : "";
                honorariosFondosBean.setAdmin(string);
                honorariosFondosBean.setEntrada(string2);
                honorariosFondosBean.setSalida(string3);
            }
            fondoBean.setHonorarios(honorariosFondosBean);
            HorariosFondosBean horariosFondosBean = new HorariosFondosBean();
            if (jSONObject.has("horarios") && (jSONObject.get("horarios") instanceof JSONObject)) {
                String string4 = jSONObject.getJSONObject("horarios").has("apertura") ? jSONObject.getJSONObject("horarios").getString("apertura") : "";
                String string5 = jSONObject.getJSONObject("horarios").has("cierre") ? jSONObject.getJSONObject("horarios").getString("cierre") : "";
                horariosFondosBean.setApertura(string4);
                horariosFondosBean.setCierre(string5);
            }
            fondoBean.setHorarios(horariosFondosBean);
            if (jSONObject.has("leyendas") && (jSONObject.get("leyendas") instanceof JSONObject) && (jSONObject.getJSONObject("leyendas").has("leyendaLegal") || jSONObject.getJSONObject("leyendas").has("legal"))) {
                if (jSONObject.getJSONObject("leyendas").has("leyendaLegal")) {
                    jSONObject2 = jSONObject.getJSONObject("leyendas");
                    str = "leyendaLegal";
                } else {
                    jSONObject2 = jSONObject.getJSONObject("leyendas");
                    str = "legal";
                }
                Object obj = jSONObject2.get(str);
                if (obj instanceof JSONArray) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add((String) gson.fromJson(((JSONArray) obj).getJSONObject(i).toString(), String.class));
                    }
                    legalesFondosBean.setLeyendaLegales(arrayList);
                    fondoBean.setListaLegales(legalesFondosBean);
                } else if (obj instanceof String) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add((String) obj);
                    legalesFondosBean.setLeyendaLegales(arrayList2);
                    fondoBean.setListaLegales(legalesFondosBean);
                }
            }
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return fondoBean;
    }
}
