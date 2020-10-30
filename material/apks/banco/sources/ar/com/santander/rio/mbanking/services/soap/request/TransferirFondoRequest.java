package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.TransferirFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DisclaimerFondo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaDisclaimersFondo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferenciaDestinoFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferenciaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferenciaOrigenFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TransferirFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class TransferirFondoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private TransferirFondoRequestBean mTransferirFondoRequestBean;
    private TransferirFondoResponseBean mTransferirFondoResponseBean;

    public int getMethod() {
        return 1;
    }

    public TransferirFondoRequest(Context context, TransferirFondoRequestBean transferirFondoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mTransferirFondoRequestBean = transferirFondoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mTransferirFondoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mTransferirFondoResponseBean == null) {
            this.mTransferirFondoResponseBean = new TransferirFondoResponseBean();
        }
        return this.mTransferirFondoResponseBean.getClass();
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
                TransferirFondoResponseBean transferirFondoResponseBean = new TransferirFondoResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                TransferirFondoBodyResponseBean transferirFondoBodyResponseBean = new TransferirFondoBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                transferirFondoBodyResponseBean.setAlertaRiesgo(jSONObject3.has("alertaRiesgo") ? jSONObject3.getString("alertaRiesgo") : "");
                transferirFondoBodyResponseBean.setIdEvaluacionRiesgo(jSONObject3.has("idEvaluacionRiesgo") ? jSONObject3.getString("idEvaluacionRiesgo") : "");
                AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean = new AlertaEvaluacionRiesgoBean();
                if (jSONObject3.has("mensaje")) {
                    alertaEvaluacionRiesgoBean.setCabecera(jSONObject3.getJSONObject("mensaje").has("cabecera") ? jSONObject3.getJSONObject("mensaje").getString("cabecera") : "");
                    alertaEvaluacionRiesgoBean.setCantidadDisclaimer(jSONObject3.getJSONObject("mensaje").has("cantidadDisclaimer") ? jSONObject3.getJSONObject("mensaje").getString("cantidadDisclaimer") : "");
                    alertaEvaluacionRiesgoBean.setPie(jSONObject3.getJSONObject("mensaje").has("pie") ? jSONObject3.getJSONObject("mensaje").getString("pie") : "");
                    ListaDisclaimersFondo listaDisclaimersFondo = new ListaDisclaimersFondo();
                    ArrayList arrayList = new ArrayList();
                    if (jSONObject3.getJSONObject("mensaje").has("disclaimers") && jSONObject3.getJSONObject("mensaje").getJSONObject("disclaimers").has("disclaimer")) {
                        Object obj = jSONObject3.getJSONObject("mensaje").getJSONObject("disclaimers").get("disclaimer");
                        if (obj instanceof JSONArray) {
                            for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                                JSONObject jSONObject4 = ((JSONArray) obj).getJSONObject(i);
                                DisclaimerFondo disclaimerFondo = new DisclaimerFondo();
                                disclaimerFondo.setTexto(jSONObject4.has("texto") ? jSONObject4.getString("texto") : "");
                                disclaimerFondo.setTitulo(jSONObject4.has("titulo") ? jSONObject4.getString("titulo") : "");
                                arrayList.add(disclaimerFondo);
                            }
                        } else if (obj instanceof Object) {
                            JSONObject jSONObject5 = jSONObject3.getJSONObject("mensaje").getJSONObject("disclaimers").getJSONObject("disclaimer");
                            DisclaimerFondo disclaimerFondo2 = new DisclaimerFondo();
                            disclaimerFondo2.setTexto(jSONObject5.has("texto") ? jSONObject5.getString("texto") : "");
                            disclaimerFondo2.setTitulo(jSONObject5.has("titulo") ? jSONObject5.getString("titulo") : "");
                            arrayList.add(disclaimerFondo2);
                        }
                    }
                    listaDisclaimersFondo.setDisclaimer(arrayList);
                    alertaEvaluacionRiesgoBean.setDisclaimers(listaDisclaimersFondo);
                }
                transferirFondoBodyResponseBean.setMensaje(alertaEvaluacionRiesgoBean);
                TransferenciaFondosBean transferenciaFondosBean = new TransferenciaFondosBean();
                if (jSONObject3.has("transferencia")) {
                    transferenciaFondosBean.setNroCertificado(jSONObject3.getJSONObject("transferencia").has("nroCertificado") ? jSONObject3.getJSONObject("transferencia").getString("nroCertificado") : "");
                    TransferenciaOrigenFondosBean transferenciaOrigenFondosBean = new TransferenciaOrigenFondosBean();
                    if (jSONObject3.getJSONObject("transferencia").has("origen") && (jSONObject3.getJSONObject("transferencia").get("origen") instanceof JSONObject)) {
                        transferenciaOrigenFondosBean.setFecha(jSONObject3.getJSONObject("transferencia").getJSONObject("origen").has(TarjetasConstants.FECHA) ? jSONObject3.getJSONObject("transferencia").getJSONObject("origen").getString(TarjetasConstants.FECHA) : "");
                        transferenciaOrigenFondosBean.setHora(jSONObject3.getJSONObject("transferencia").getJSONObject("origen").has("hora") ? jSONObject3.getJSONObject("transferencia").getJSONObject("origen").getString("hora") : "");
                        transferenciaOrigenFondosBean.setIdFondoOrigen(jSONObject3.getJSONObject("transferencia").getJSONObject("origen").has("idFondoOrigen") ? jSONObject3.getJSONObject("transferencia").getJSONObject("origen").getString("idFondoOrigen") : "");
                        transferenciaOrigenFondosBean.setFondoOrigen(jSONObject3.getJSONObject("transferencia").getJSONObject("origen").has("fondoOrigen") ? jSONObject3.getJSONObject("transferencia").getJSONObject("origen").getString("fondoOrigen") : "");
                        transferenciaOrigenFondosBean.setImporte(jSONObject3.getJSONObject("transferencia").getJSONObject("origen").has("importeTransferencia") ? jSONObject3.getJSONObject("transferencia").getJSONObject("origen").getString("importeTransferencia") : "");
                        transferenciaOrigenFondosBean.setMoneda(jSONObject3.getJSONObject("transferencia").getJSONObject("origen").has(TarjetasConstants.MONEDA) ? jSONObject3.getJSONObject("transferencia").getJSONObject("origen").getString(TarjetasConstants.MONEDA) : "");
                        transferenciaOrigenFondosBean.setCantCuotaparte(jSONObject3.getJSONObject("transferencia").getJSONObject("origen").has("cantCuotaparte") ? jSONObject3.getJSONObject("transferencia").getJSONObject("origen").getString("cantCuotaparte") : "");
                    }
                    transferenciaFondosBean.setOrigen(transferenciaOrigenFondosBean);
                    TransferenciaDestinoFondosBean transferenciaDestinoFondosBean = new TransferenciaDestinoFondosBean();
                    if (jSONObject3.getJSONObject("transferencia").has("destino") && (jSONObject3.getJSONObject("transferencia").get("destino") instanceof JSONObject)) {
                        transferenciaDestinoFondosBean.setIdFondoDestino(jSONObject3.getJSONObject("transferencia").getJSONObject("destino").has("idFondoDestino") ? jSONObject3.getJSONObject("transferencia").getJSONObject("destino").getString("idFondoDestino") : "");
                        transferenciaDestinoFondosBean.setFondoDestino(jSONObject3.getJSONObject("transferencia").getJSONObject("destino").has("fondoDestino") ? jSONObject3.getJSONObject("transferencia").getJSONObject("destino").getString("fondoDestino") : "");
                        transferenciaDestinoFondosBean.setMoneda(jSONObject3.getJSONObject("transferencia").getJSONObject("destino").has("monedaDestino") ? jSONObject3.getJSONObject("transferencia").getJSONObject("destino").getString("monedaDestino") : "");
                    }
                    transferenciaFondosBean.setDestino(transferenciaDestinoFondosBean);
                    LegalesFondosBean legalesFondosBean = new LegalesFondosBean();
                    ArrayList arrayList2 = new ArrayList();
                    if (jSONObject3.getJSONObject("transferencia").has("legales") && jSONObject3.getJSONObject("transferencia").getJSONObject("legales").has("legal")) {
                        Object obj2 = jSONObject3.getJSONObject("transferencia").getJSONObject("legales").get("legal");
                        if (obj2 instanceof JSONArray) {
                            for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                                arrayList2.add(((JSONArray) obj2).getString(i2));
                            }
                        } else if (obj2 instanceof String) {
                            arrayList2.add((String) obj2);
                        }
                    }
                    transferenciaFondosBean.setLegales(legalesFondosBean);
                    legalesFondosBean.setLeyendaLegales(arrayList2);
                    transferirFondoBodyResponseBean.setTransferencia(transferenciaFondosBean);
                } else {
                    transferirFondoBodyResponseBean.setTransferencia(transferenciaFondosBean);
                }
                transferirFondoResponseBean.headerBean = privateHeaderBean;
                transferirFondoResponseBean.setTransferirFondoBodyResponseBean(transferirFondoBodyResponseBean);
                onResponseBean(transferirFondoResponseBean);
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
