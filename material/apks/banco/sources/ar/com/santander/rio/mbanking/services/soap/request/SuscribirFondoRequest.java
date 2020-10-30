package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscribirFondoRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.SuscribirFondoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AlertaEvaluacionRiesgoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DisclaimerFondo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaDisclaimersFondo;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscribirFondoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscripcionFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class SuscribirFondoRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private SuscribirFondoRequestBean mSuscribirFondoRequestBean;
    private SuscribirFondoResponseBean mSuscribirFondoResponseBean;

    public int getMethod() {
        return 1;
    }

    public SuscribirFondoRequest(Context context, SuscribirFondoRequestBean suscribirFondoRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mSuscribirFondoRequestBean = suscribirFondoRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mSuscribirFondoRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mSuscribirFondoResponseBean == null) {
            this.mSuscribirFondoResponseBean = new SuscribirFondoResponseBean();
        }
        return this.mSuscribirFondoResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        JSONObject jSONObject2;
        String str;
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject3 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                SuscribirFondoResponseBean suscribirFondoResponseBean = new SuscribirFondoResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject3.getJSONObject("header").toString(), PrivateHeaderBean.class);
                SuscribirFondoBodyResponseBean suscribirFondoBodyResponseBean = new SuscribirFondoBodyResponseBean();
                JSONObject jSONObject4 = jSONObject3.getJSONObject("body");
                suscribirFondoBodyResponseBean.setAlertaRiesgo(jSONObject4.has("alertaRiesgo") ? jSONObject4.getString("alertaRiesgo") : "");
                suscribirFondoBodyResponseBean.setIdEvaluacionRiesgo(jSONObject4.has("idEvaluacionRiesgo") ? jSONObject4.getString("idEvaluacionRiesgo") : "");
                AlertaEvaluacionRiesgoBean alertaEvaluacionRiesgoBean = new AlertaEvaluacionRiesgoBean();
                if (jSONObject4.has("mensaje")) {
                    alertaEvaluacionRiesgoBean.setCabecera(jSONObject4.getJSONObject("mensaje").has("cabecera") ? jSONObject4.getJSONObject("mensaje").getString("cabecera") : "");
                    alertaEvaluacionRiesgoBean.setCantidadDisclaimer(jSONObject4.getJSONObject("mensaje").has("cantidadDisclaimer") ? jSONObject4.getJSONObject("mensaje").getString("cantidadDisclaimer") : "");
                    alertaEvaluacionRiesgoBean.setPie(jSONObject4.getJSONObject("mensaje").has("pie") ? jSONObject4.getJSONObject("mensaje").getString("pie") : "");
                    ListaDisclaimersFondo listaDisclaimersFondo = new ListaDisclaimersFondo();
                    ArrayList arrayList = new ArrayList();
                    if (jSONObject4.getJSONObject("mensaje").has("disclaimers") && jSONObject4.getJSONObject("mensaje").getJSONObject("disclaimers").has("disclaimer")) {
                        Object obj = jSONObject4.getJSONObject("mensaje").getJSONObject("disclaimers").get("disclaimer");
                        if (obj instanceof JSONArray) {
                            for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                                JSONObject jSONObject5 = ((JSONArray) obj).getJSONObject(i);
                                DisclaimerFondo disclaimerFondo = new DisclaimerFondo();
                                disclaimerFondo.setTexto(jSONObject5.has("texto") ? jSONObject5.getString("texto") : "");
                                disclaimerFondo.setTitulo(jSONObject5.has("titulo") ? jSONObject5.getString("titulo") : "");
                                arrayList.add(disclaimerFondo);
                            }
                        } else if (obj instanceof Object) {
                            JSONObject jSONObject6 = jSONObject4.getJSONObject("mensaje").getJSONObject("disclaimers").getJSONObject("disclaimer");
                            DisclaimerFondo disclaimerFondo2 = new DisclaimerFondo();
                            disclaimerFondo2.setTexto(jSONObject6.has("texto") ? jSONObject6.getString("texto") : "");
                            disclaimerFondo2.setTitulo(jSONObject6.has("titulo") ? jSONObject6.getString("titulo") : "");
                            arrayList.add(disclaimerFondo2);
                        }
                    }
                    listaDisclaimersFondo.setDisclaimer(arrayList);
                    alertaEvaluacionRiesgoBean.setDisclaimers(listaDisclaimersFondo);
                }
                suscribirFondoBodyResponseBean.setMensaje(alertaEvaluacionRiesgoBean);
                SuscripcionFondosBean suscripcionFondosBean = new SuscripcionFondosBean();
                if (jSONObject4.has("suscripcion")) {
                    suscripcionFondosBean.setFecha(jSONObject4.getJSONObject("suscripcion").has(TarjetasConstants.FECHA) ? jSONObject4.getJSONObject("suscripcion").getString(TarjetasConstants.FECHA) : "");
                    suscripcionFondosBean.setHora(jSONObject4.getJSONObject("suscripcion").has("hora") ? jSONObject4.getJSONObject("suscripcion").getString("hora") : "");
                    suscripcionFondosBean.setIdFondo(jSONObject4.getJSONObject("suscripcion").has("idFondo") ? jSONObject4.getJSONObject("suscripcion").getString("idFondo") : "");
                    suscripcionFondosBean.setImporte(jSONObject4.getJSONObject("suscripcion").has("importe") ? jSONObject4.getJSONObject("suscripcion").getString("importe") : "");
                    suscripcionFondosBean.setNroCertificado(jSONObject4.getJSONObject("suscripcion").has("nroCertificado") ? jSONObject4.getJSONObject("suscripcion").getString("nroCertificado") : "");
                    LegalesFondosBean legalesFondosBean = new LegalesFondosBean();
                    ArrayList arrayList2 = new ArrayList();
                    if (jSONObject4.getJSONObject("suscripcion").has("legales") && (jSONObject4.getJSONObject("suscripcion").getJSONObject("legales").has("leyendaLegal") || jSONObject4.getJSONObject("suscripcion").getJSONObject("legales").has("legal"))) {
                        if (jSONObject4.getJSONObject("suscripcion").getJSONObject("legales").has("leyendaLegal")) {
                            jSONObject2 = jSONObject4.getJSONObject("suscripcion").getJSONObject("legales");
                            str = "leyendaLegal";
                        } else {
                            jSONObject2 = jSONObject4.getJSONObject("suscripcion").getJSONObject("legales");
                            str = "legal";
                        }
                        Object obj2 = jSONObject2.get(str);
                        if (obj2 instanceof JSONArray) {
                            for (int i2 = 0; i2 < ((JSONArray) obj2).length(); i2++) {
                                arrayList2.add(((JSONArray) obj2).getString(i2));
                            }
                        } else if (obj2 instanceof String) {
                            arrayList2.add((String) obj2);
                        }
                    }
                    legalesFondosBean.setLeyendaLegales(arrayList2);
                    suscripcionFondosBean.setLegales(legalesFondosBean);
                }
                suscribirFondoBodyResponseBean.setSuscripcion(suscripcionFondosBean);
                suscribirFondoResponseBean.headerBean = privateHeaderBean;
                suscribirFondoResponseBean.setSuscribirFondoBodyResponseBean(suscribirFondoBodyResponseBean);
                onResponseBean(suscribirFondoResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
