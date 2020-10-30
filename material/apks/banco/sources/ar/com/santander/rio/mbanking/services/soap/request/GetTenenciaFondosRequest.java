package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaFondosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetTenenciaFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetTenenciaFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.HonorariosFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.HorariosFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCtaOperativaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetTenenciaFondosRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetTenenciaFondosRequestBean mGetTenenciaFondosRequestBean;
    private GetTenenciaFondosResponseBean mGetTenenciaFondosResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetTenenciaFondosRequest(Context context, GetTenenciaFondosRequestBean getTenenciaFondosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetTenenciaFondosRequestBean = getTenenciaFondosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetTenenciaFondosRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetTenenciaFondosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetTenenciaFondosResponseBean == null) {
            this.mGetTenenciaFondosResponseBean = new GetTenenciaFondosResponseBean();
        }
        return this.mGetTenenciaFondosResponseBean.getClass();
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
                GetTenenciaFondosResponseBean getTenenciaFondosResponseBean = new GetTenenciaFondosResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetTenenciaFondosBodyResponseBean getTenenciaFondosBodyResponseBean = new GetTenenciaFondosBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                getTenenciaFondosBodyResponseBean.setContratoSusc(jSONObject3.has("contratoSusc") ? jSONObject3.getString("contratoSusc") : "");
                getTenenciaFondosBodyResponseBean.setContratoTransf(jSONObject3.has("contratoTransf") ? jSONObject3.getString("contratoTransf") : "");
                ListaCuentasFondosBean listaCuentasFondosBean = new ListaCuentasFondosBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject3.has("cuentas") && jSONObject3.getJSONObject("cuentas").has("cuenta")) {
                    Object obj = jSONObject3.getJSONObject("cuentas").get("cuenta");
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getCuentaObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                    } else if (obj instanceof JSONObject) {
                        arrayList.add(getCuentaObject(gson, jSONObject3.getJSONObject("cuentas").getJSONObject("cuenta")));
                    }
                }
                listaCuentasFondosBean.setCuentasFondosBean(arrayList);
                getTenenciaFondosBodyResponseBean.setListaCuentasFondosBean(listaCuentasFondosBean);
                ListaCtaOperativaBean listaCtaOperativaBean = new ListaCtaOperativaBean();
                ArrayList arrayList2 = new ArrayList();
                if (jSONObject3.has("listaCtaOperativa") && jSONObject3.getJSONObject("listaCtaOperativa").has("cuentaOperativa")) {
                    Object obj2 = jSONObject3.getJSONObject("listaCtaOperativa").get("cuentaOperativa");
                    if (obj2 instanceof JSONArray) {
                        for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                            arrayList2.add(getCuentaOperativaObject(gson, ((JSONArray) obj2).getJSONObject(i2)));
                        }
                    } else if (obj2 instanceof JSONObject) {
                        arrayList2.add(getCuentaOperativaObject(gson, jSONObject3.getJSONObject("listaCtaOperativa").getJSONObject("cuentaOperativa")));
                    }
                }
                listaCtaOperativaBean.setCuentasOperativasBean(arrayList2);
                getTenenciaFondosBodyResponseBean.setListaCtaOperativaBean(listaCtaOperativaBean);
                ListaLeyendas listaLeyendas = new ListaLeyendas();
                ArrayList arrayList3 = new ArrayList();
                if (jSONObject3.has("listaLeyendas") && jSONObject3.getJSONObject("listaLeyendas").has("leyenda")) {
                    Object obj3 = jSONObject3.getJSONObject("listaLeyendas").get("leyenda");
                    if (obj3 instanceof JSONArray) {
                        for (int i3 = 0; i3 < ((JSONArray) obj3).length(); i3++) {
                            arrayList3.add(getLeyendaObject(gson, ((JSONArray) obj3).getJSONObject(i3)));
                        }
                    } else if (obj3 instanceof JSONObject) {
                        arrayList3.add(getLeyendaObject(gson, jSONObject3.getJSONObject("listaLeyenda").getJSONObject("leyenda")));
                    }
                }
                listaLeyendas.setLstLeyendas(arrayList3);
                getTenenciaFondosBodyResponseBean.setListaLeyendas(listaLeyendas);
                getTenenciaFondosResponseBean.headerBean = privateHeaderBean;
                getTenenciaFondosResponseBean.getTenenciaFondosBodyResponseBean = getTenenciaFondosBodyResponseBean;
                onResponseBean(getTenenciaFondosResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private CuentaFondosBean getCuentaObject(Gson gson, JSONObject jSONObject) {
        CuentaFondosBean cuentaFondosBean = new CuentaFondosBean();
        ListaFondosBean listaFondosBean = new ListaFondosBean();
        ArrayList arrayList = new ArrayList();
        try {
            cuentaFondosBean.setTipoCuenta(jSONObject.has("tipoCuenta") ? jSONObject.getString("tipoCuenta") : "08");
            cuentaFondosBean.setSucursalCuenta(jSONObject.has("sucursalCuenta") ? jSONObject.getString("sucursalCuenta") : "000");
            cuentaFondosBean.setNumero(jSONObject.has("numeroCuenta") ? jSONObject.getString("numeroCuenta") : "");
            cuentaFondosBean.setImporteDolares(jSONObject.has("importeDolares") ? jSONObject.getString("importeDolares") : "");
            cuentaFondosBean.setImportePesos(jSONObject.has("importePesos") ? jSONObject.getString("importePesos") : "");
            cuentaFondosBean.setMensajeCodigo(jSONObject.optString("msjCod", ""));
            cuentaFondosBean.setMensajeDescripcion(jSONObject.optString("msjDesc", ""));
            cuentaFondosBean.setMensajeTitulo(jSONObject.optString("msjTitulo", ""));
            if (jSONObject.has(Constants.DIR_STORAGE_FUNDS) && jSONObject.getJSONObject(Constants.DIR_STORAGE_FUNDS).has("fondo")) {
                Object obj = jSONObject.getJSONObject(Constants.DIR_STORAGE_FUNDS).get("fondo");
                if (obj instanceof JSONArray) {
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add(getFondoObject(gson, ((JSONArray) obj).getJSONObject(i)));
                    }
                } else if (obj instanceof JSONObject) {
                    arrayList.add(getFondoObject(gson, jSONObject.getJSONObject(Constants.DIR_STORAGE_FUNDS).getJSONObject("fondo")));
                }
            }
            listaFondosBean.setFondosBean(arrayList);
            cuentaFondosBean.setListaFondos(listaFondosBean);
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return cuentaFondosBean;
    }

    private FondoBean getFondoObject(Gson gson, JSONObject jSONObject) {
        FondoBean fondoBean = new FondoBean();
        new LegalesFondosBean();
        try {
            fondoBean.setId(jSONObject.has("idFondo") ? jSONObject.getString("idFondo") : "");
            fondoBean.setNombre(jSONObject.has("nombre") ? jSONObject.getString("nombre") : "");
            fondoBean.setAptoSuscrip(jSONObject.has("aptoSuscrip") ? jSONObject.getString("aptoSuscrip") : "");
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
            if (jSONObject.has("honorarios") && (jSONObject.get("honorarios") instanceof JSONObject)) {
                fondoBean.setHonorarios(new HonorariosFondosBean(jSONObject.getJSONObject("honorarios").has("admin") ? jSONObject.getJSONObject("honorarios").getString("admin") : "", jSONObject.getJSONObject("honorarios").has("entrada") ? jSONObject.getJSONObject("honorarios").getString("entrada") : "", jSONObject.getJSONObject("honorarios").has("salida") ? jSONObject.getJSONObject("honorarios").getString("salida") : ""));
            }
            if (jSONObject.has("horarios") && (jSONObject.get("horarios") instanceof JSONObject)) {
                fondoBean.setHorarios(new HorariosFondosBean(jSONObject.getJSONObject("horarios").has("apertura") ? jSONObject.getJSONObject("horarios").getString("apertura") : "", jSONObject.getJSONObject("horarios").has("cierre") ? jSONObject.getJSONObject("horarios").getString("cierre") : ""));
            }
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return fondoBean;
    }

    private CuentaOperativaBean getCuentaOperativaObject(Gson gson, JSONObject jSONObject) {
        CuentaOperativaBean cuentaOperativaBean = new CuentaOperativaBean();
        try {
            cuentaOperativaBean.setTipoCta(jSONObject.has("tipoCta") ? jSONObject.getString("tipoCta") : "");
            cuentaOperativaBean.setDescCtaDebito(jSONObject.has("descCtaDebito") ? jSONObject.getString("descCtaDebito") : "");
            cuentaOperativaBean.setDescCtaDestino(jSONObject.has("descCtaDestino") ? jSONObject.getString("descCtaDestino") : "");
            cuentaOperativaBean.setNumero(jSONObject.has("numero") ? jSONObject.getString("numero") : "");
            cuentaOperativaBean.setSucursal(jSONObject.has("sucursal") ? jSONObject.getString("sucursal") : "");
            cuentaOperativaBean.setMoneda(jSONObject.has(TarjetasConstants.MONEDA) ? jSONObject.getString(TarjetasConstants.MONEDA) : "");
            cuentaOperativaBean.setIdMoneda(jSONObject.has("idMoneda") ? jSONObject.getString("idMoneda") : "");
            cuentaOperativaBean.setTipoDescripcion(jSONObject.has("tipoDescripcion") ? jSONObject.getString("tipoDescripcion") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return cuentaOperativaBean;
    }

    private Leyenda getLeyendaObject(Gson gson, JSONObject jSONObject) {
        Leyenda leyenda = new Leyenda();
        try {
            leyenda.setIdLeyenda(jSONObject.has("idLeyenda") ? jSONObject.getString("idLeyenda") : "");
            leyenda.setTitulo(jSONObject.has("titulo") ? jSONObject.getString("titulo") : "");
            leyenda.setDescripcion(jSONObject.has("descripcion") ? jSONObject.getString("descripcion") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return leyenda;
    }
}
