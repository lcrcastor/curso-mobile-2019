package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSegurosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetSegurosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AsistenciaSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AsistenciasSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BotonSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatoSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetSegurosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LinkSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SeguroObjetoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SegurosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetSegurosRequest extends BaseRequest implements IBeanRequestWS {
    private GetSegurosRequestBean getSegurosRequestBean;
    private GetSegurosResponseBean getSegurosResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetSegurosRequest(Context context, GetSegurosRequestBean getSegurosRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.getSegurosRequestBean = getSegurosRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public GetSegurosRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.getSegurosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.getSegurosResponseBean == null) {
            this.getSegurosResponseBean = new GetSegurosResponseBean();
        }
        return this.getSegurosResponseBean.getClass();
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
                GetSegurosResponseBean getSegurosResponseBean2 = new GetSegurosResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetSegurosBodyResponseBean getSegurosBodyResponseBean = new GetSegurosBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                SegurosBean segurosBean = new SegurosBean();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                if (jSONObject3.has(Constants.DIR_STORAGE_SEGUROS)) {
                    if (!TextUtils.isEmpty(jSONObject3.get(Constants.DIR_STORAGE_SEGUROS).toString()) && jSONObject3.getJSONObject(Constants.DIR_STORAGE_SEGUROS).has("seguro")) {
                        Object obj = jSONObject3.getJSONObject(Constants.DIR_STORAGE_SEGUROS).get("seguro");
                        if (obj instanceof JSONArray) {
                            for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                                arrayList.add(getSeguroObject(gson, ((JSONArray) obj).getJSONObject(i)));
                            }
                        } else if (obj instanceof JSONObject) {
                            arrayList.add(getSeguroObject(gson, jSONObject3.getJSONObject(Constants.DIR_STORAGE_SEGUROS).getJSONObject("seguro")));
                        }
                    }
                    if (jSONObject3.getJSONObject(Constants.DIR_STORAGE_SEGUROS).has("listaObjetos")) {
                        JSONObject jSONObject4 = jSONObject3.getJSONObject(Constants.DIR_STORAGE_SEGUROS);
                        if (!TextUtils.isEmpty(jSONObject4.get("listaObjetos").toString()) && jSONObject4.getJSONObject("listaObjetos").has("seguro")) {
                            Object obj2 = jSONObject4.getJSONObject("listaObjetos").get("seguro");
                            if (obj2 instanceof JSONArray) {
                                for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                                    arrayList2.add(getSeguroObjeto(gson, ((JSONArray) obj2).getJSONObject(i2)));
                                }
                            } else if (obj2 instanceof JSONObject) {
                                arrayList2.add(getSeguroObjeto(gson, (JSONObject) obj2));
                            }
                        }
                    }
                }
                String str = "";
                String str2 = "";
                if (jSONObject3.has("urlSeguimiento") || jSONObject3.has("urlSiniestro")) {
                    str = jSONObject3.get("urlSeguimiento") != null ? jSONObject3.get("urlSeguimiento").toString() : null;
                    str2 = jSONObject3.get("urlSiniestro") != null ? jSONObject3.get("urlSiniestro").toString() : null;
                }
                ArrayList arrayList3 = new ArrayList();
                if (jSONObject3.has("listaBotones") && !TextUtils.isEmpty(jSONObject3.get("listaBotones").toString()) && jSONObject3.getJSONObject("listaBotones").has("boton")) {
                    Object obj3 = jSONObject3.getJSONObject("listaBotones").get("boton");
                    if (obj3 instanceof JSONArray) {
                        for (int i3 = 0; i3 < ((JSONArray) obj3).length(); i3++) {
                            arrayList3.add(getBotonesObject(gson, ((JSONArray) obj3).getJSONObject(i3)));
                        }
                    } else if (obj3 instanceof JSONObject) {
                        arrayList3.add(getBotonesObject(gson, jSONObject3.getJSONObject("listaBotones").getJSONObject("boton")));
                    }
                }
                LinkSeguroBean linkSeguroBean = new LinkSeguroBean();
                if (jSONObject3.has("linkSeguro")) {
                    linkSeguroBean = getLinkSeguroObject(jSONObject3.getJSONObject("linkSeguro"));
                }
                if (jSONObject3.has(Constants.DIR_STORAGE_SEGUROS)) {
                    segurosBean.setCantidad(jSONObject3.getJSONObject(Constants.DIR_STORAGE_SEGUROS).has("cantidad") ? jSONObject3.getJSONObject(Constants.DIR_STORAGE_SEGUROS).getString("cantidad") : "");
                }
                segurosBean.setListaSeguros(arrayList);
                segurosBean.setListaBotones(arrayList3);
                segurosBean.setListaObjetos(arrayList2);
                segurosBean.setLinkSeguroBean(linkSeguroBean);
                segurosBean.setUrlSeguimiento(str);
                segurosBean.setUrlSiniestro(str2);
                getSegurosBodyResponseBean.setSeguros(segurosBean);
                getSegurosResponseBean2.header = privateHeaderBean;
                getSegurosResponseBean2.getSegurosBodyResponseBean = getSegurosBodyResponseBean;
                onResponseBean(getSegurosResponseBean2);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private LinkSeguroBean getLinkSeguroObject(JSONObject jSONObject) {
        LinkSeguroBean linkSeguroBean = new LinkSeguroBean();
        try {
            linkSeguroBean.setOpcion(jSONObject.has("opcion") ? jSONObject.getString("opcion") : "");
            linkSeguroBean.setResCod(jSONObject.has("resCod") ? jSONObject.getString("resCod") : "");
            linkSeguroBean.setResDesc(jSONObject.has("resDesc") ? jSONObject.getString("resDesc") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return linkSeguroBean;
    }

    private BotonSeguroBean getBotonesObject(Gson gson, JSONObject jSONObject) {
        BotonSeguroBean botonSeguroBean = new BotonSeguroBean();
        try {
            botonSeguroBean.setDescripcion(jSONObject.has("descripcion") ? jSONObject.getString("descripcion") : "");
            botonSeguroBean.setNombre(jSONObject.has("nombre") ? jSONObject.getString("nombre") : "");
            botonSeguroBean.setUrl(jSONObject.has("url") ? jSONObject.getString("url") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return botonSeguroBean;
    }

    private SeguroBean getSeguroObject(Gson gson, JSONObject jSONObject) {
        SeguroBean seguroBean = new SeguroBean();
        try {
            seguroBean.setCodRamo(jSONObject.has("codRamo") ? jSONObject.getString("codRamo") : "");
            seguroBean.setCodProducto(jSONObject.has("codProducto") ? jSONObject.getString("codProducto") : "");
            seguroBean.setCodPlan(jSONObject.has("codPlan") ? jSONObject.getString("codPlan") : "");
            seguroBean.setNumPoliza(jSONObject.has("numPoliza") ? jSONObject.getString("numPoliza") : "");
            seguroBean.setNumCertificado(jSONObject.has("numCertificado") ? jSONObject.getString("numCertificado") : "");
            seguroBean.setTitulo(jSONObject.has("titulo") ? jSONObject.getString("titulo") : "");
            seguroBean.setDescCorta(jSONObject.has("descCorta") ? jSONObject.getString("descCorta") : "");
            seguroBean.setCuota(jSONObject.has("cuota") ? jSONObject.getString("cuota") : "");
            seguroBean.setAseguradora(jSONObject.has("aseguradora") ? jSONObject.getString("aseguradora") : "");
            seguroBean.setFechaInicio(jSONObject.has("fechaInicio") ? jSONObject.getString("fechaInicio") : "");
            seguroBean.setSumaAsegurada(jSONObject.has("sumaAsegurada") ? jSONObject.getString("sumaAsegurada") : "");
            seguroBean.setPropietario(jSONObject.has("propietario") ? jSONObject.getString("propietario") : "");
            seguroBean.setMedioPago(jSONObject.has("medioPago") ? jSONObject.getString("medioPago") : "");
            seguroBean.setIdDispositivo(jSONObject.has("idDispositivo") ? jSONObject.getString("idDispositivo") : "");
            seguroBean.setMarca(jSONObject.has("marca") ? jSONObject.getString("marca") : "");
            seguroBean.setModelo(jSONObject.has("modelo") ? jSONObject.getString("modelo") : "");
            seguroBean.setOcupacion(jSONObject.has("ocupacion") ? jSONObject.getString("ocupacion") : "");
            seguroBean.seteMail(jSONObject.has("eMail") ? jSONObject.getString("eMail") : "");
            seguroBean.setTipoEnvioPoliza(jSONObject.has("tipoEnvioPoliza") ? jSONObject.getString("tipoEnvioPoliza") : "");
            seguroBean.setSubtituloDatos(jSONObject.has("subtituloDatos") ? jSONObject.getString("subtituloDatos") : "");
            DatosSeguroBean datosSeguroBean = new DatosSeguroBean();
            ArrayList arrayList = new ArrayList();
            if (jSONObject.has("datos") && jSONObject.getJSONObject("datos").has("dato")) {
                Object obj = jSONObject.getJSONObject("datos").get("dato");
                if (obj instanceof JSONArray) {
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add(getDatoSeguroObject(gson, ((JSONArray) obj).getJSONObject(i)));
                    }
                } else if (obj instanceof JSONObject) {
                    arrayList.add(getDatoSeguroObject(gson, jSONObject.getJSONObject("datos").getJSONObject("dato")));
                }
            }
            datosSeguroBean.setListaDatosBean(arrayList);
            seguroBean.setDatos(datosSeguroBean);
            AsistenciasSeguroBean asistenciasSeguroBean = new AsistenciasSeguroBean();
            ArrayList arrayList2 = new ArrayList();
            if (jSONObject.has("asistencias") && jSONObject.getJSONObject("asistencias").has("asistencia")) {
                Object obj2 = jSONObject.getJSONObject("asistencias").get("asistencia");
                if (obj2 instanceof JSONArray) {
                    for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                        arrayList2.add(getAsistenciaSeguroObject(gson, ((JSONArray) obj2).getJSONObject(i2)));
                    }
                } else if (obj2 instanceof JSONObject) {
                    arrayList2.add(getAsistenciaSeguroObject(gson, jSONObject.getJSONObject("asistencias").getJSONObject("asistencia")));
                }
            }
            asistenciasSeguroBean.setListaAsistenciasBean(arrayList2);
            seguroBean.setAsistencias(asistenciasSeguroBean);
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return seguroBean;
    }

    private SeguroObjetoBean getSeguroObjeto(Gson gson, JSONObject jSONObject) {
        SeguroObjetoBean seguroObjetoBean = new SeguroObjetoBean();
        try {
            seguroObjetoBean.setCodFamilia(jSONObject.has("idFamilia") ? jSONObject.getString("idFamilia") : "");
            seguroObjetoBean.setNombreFamilia(jSONObject.has("nombreFamilia") ? jSONObject.getString("nombreFamilia") : "");
            seguroObjetoBean.setCodRamo(jSONObject.has("codRamo") ? jSONObject.getString("codRamo") : "");
            seguroObjetoBean.setCodProducto(jSONObject.has("codProducto") ? jSONObject.getString("codProducto") : "");
            seguroObjetoBean.setCodPlan(jSONObject.has("codPlan") ? jSONObject.getString("codPlan") : "");
            seguroObjetoBean.setNumPoliza(jSONObject.has("numPoliza") ? jSONObject.getString("numPoliza") : "");
            seguroObjetoBean.setNumCertificado(jSONObject.has("numCertificado") ? jSONObject.getString("numCertificado") : "");
            seguroObjetoBean.setTitulo(jSONObject.has("titulo") ? jSONObject.getString("titulo") : "");
            seguroObjetoBean.setDescCorta(jSONObject.has("descCorta") ? jSONObject.getString("descCorta") : "");
            seguroObjetoBean.setCuota(jSONObject.has("cuota") ? jSONObject.getString("cuota") : "");
            seguroObjetoBean.setAseguradora(jSONObject.has("aseguradora") ? jSONObject.getString("aseguradora") : "");
            seguroObjetoBean.setFechaInicio(jSONObject.has("fechaInicio") ? jSONObject.getString("fechaInicio") : "");
            seguroObjetoBean.setSumaAsegurada(jSONObject.has("sumaAsegurada") ? jSONObject.getString("sumaAsegurada") : "");
            seguroObjetoBean.setPropietario(jSONObject.has("propietario") ? jSONObject.getString("propietario") : "");
            seguroObjetoBean.setMedioPago(jSONObject.has("medioPago") ? jSONObject.getString("medioPago") : "");
            seguroObjetoBean.setOcupacion(jSONObject.has("ocupacion") ? jSONObject.getString("ocupacion") : "");
            seguroObjetoBean.seteMail(jSONObject.has("eMail") ? jSONObject.getString("eMail") : "");
            seguroObjetoBean.setTipoEnvioPoliza(jSONObject.has("tipoEnvioPoliza") ? jSONObject.getString("tipoEnvioPoliza") : "");
            seguroObjetoBean.setSubtituloDatos(jSONObject.has("subtituloDatos") ? jSONObject.getString("subtituloDatos") : "");
            DatosSeguroBean datosSeguroBean = new DatosSeguroBean();
            ArrayList arrayList = new ArrayList();
            if (jSONObject.has("datos") && jSONObject.getJSONObject("datos").has("dato")) {
                Object obj = jSONObject.getJSONObject("datos").get("dato");
                if (obj instanceof JSONArray) {
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add(getDatoSeguroObject(gson, ((JSONArray) obj).getJSONObject(i)));
                    }
                } else if (obj instanceof JSONObject) {
                    arrayList.add(getDatoSeguroObject(gson, jSONObject.getJSONObject("datos").getJSONObject("dato")));
                }
            }
            datosSeguroBean.setListaDatosBean(arrayList);
            seguroObjetoBean.setDatos(datosSeguroBean);
            AsistenciasSeguroBean asistenciasSeguroBean = new AsistenciasSeguroBean();
            ArrayList arrayList2 = new ArrayList();
            if (jSONObject.has("asistencias") && jSONObject.getJSONObject("asistencias").has("asistencia")) {
                Object obj2 = jSONObject.getJSONObject("asistencias").get("asistencia");
                if (obj2 instanceof JSONArray) {
                    for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                        arrayList2.add(getAsistenciaSeguroObject(gson, ((JSONArray) obj2).getJSONObject(i2)));
                    }
                } else if (obj2 instanceof JSONObject) {
                    arrayList2.add(getAsistenciaSeguroObject(gson, jSONObject.getJSONObject("asistencias").getJSONObject("asistencia")));
                }
            }
            asistenciasSeguroBean.setListaAsistenciasBean(arrayList2);
            seguroObjetoBean.setAsistencias(asistenciasSeguroBean);
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return seguroObjetoBean;
    }

    private DatoSeguroBean getDatoSeguroObject(Gson gson, JSONObject jSONObject) {
        DatoSeguroBean datoSeguroBean = new DatoSeguroBean();
        try {
            datoSeguroBean.setDesc(jSONObject.has(TarjetasConstants.DESC) ? jSONObject.getString(TarjetasConstants.DESC) : "");
            datoSeguroBean.setValor(jSONObject.has("valor") ? jSONObject.getString("valor") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return datoSeguroBean;
    }

    private AsistenciaSeguroBean getAsistenciaSeguroObject(Gson gson, JSONObject jSONObject) {
        AsistenciaSeguroBean asistenciaSeguroBean = new AsistenciaSeguroBean();
        try {
            asistenciaSeguroBean.setDesc(jSONObject.has(TarjetasConstants.DESC) ? jSONObject.getString(TarjetasConstants.DESC) : "");
            asistenciaSeguroBean.setTel(jSONObject.has("tel") ? jSONObject.getString("tel") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return asistenciaSeguroBean;
    }
}
