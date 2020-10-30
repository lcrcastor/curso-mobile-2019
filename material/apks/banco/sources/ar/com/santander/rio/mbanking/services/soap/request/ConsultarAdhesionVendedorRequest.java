package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarAdhesionVendedorRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultarAdhesionVendedorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarAdhesionVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCuentasVendedorBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class ConsultarAdhesionVendedorRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private ConsultarAdhesionVendedorRequestBean mConsultarAdhesionVendedorRequestBean;
    private ConsultarAdhesionVendedorResponseBean mConsultarAdhesionVendedorResponseBean;

    public int getMethod() {
        return 1;
    }

    public void onResponseBean(IBeanWS iBeanWS) {
    }

    public void onUnknowError(Exception exc) {
    }

    public ConsultarAdhesionVendedorRequest(Context context, ConsultarAdhesionVendedorRequestBean consultarAdhesionVendedorRequestBean, ErrorRequestServer errorRequestServer) {
        super(context, false);
        this.mConsultarAdhesionVendedorRequestBean = consultarAdhesionVendedorRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConsultarAdhesionVendedorRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public IBeanWS getBeanToRequest() {
        return this.mConsultarAdhesionVendedorRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mConsultarAdhesionVendedorResponseBean == null) {
            this.mConsultarAdhesionVendedorResponseBean = new ConsultarAdhesionVendedorResponseBean();
        }
        return this.mConsultarAdhesionVendedorResponseBean.getClass();
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
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
                ConsultarAdhesionVendedorResponseBean consultarAdhesionVendedorResponseBean = new ConsultarAdhesionVendedorResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                ConsultarAdhesionVendedorBodyResponseBean consultarAdhesionVendedorBodyResponseBean = new ConsultarAdhesionVendedorBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                ListaCuentasVendedorBean listaCuentasVendedorBean = new ListaCuentasVendedorBean();
                ArrayList arrayList = new ArrayList();
                if (jSONObject3.has("cuentasVendedor") && !TextUtils.isEmpty(jSONObject3.get("cuentasVendedor").toString()) && jSONObject3.getJSONObject("cuentasVendedor").has("cuentaVendedor")) {
                    Object obj = jSONObject3.getJSONObject("cuentasVendedor").get("cuentaVendedor");
                    if (obj instanceof JSONArray) {
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getCuentaVendedorObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                    } else if (obj instanceof JSONObject) {
                        arrayList.add(getCuentaVendedorObject(gson, jSONObject3.getJSONObject("cuentasVendedor").getJSONObject("cuentaVendedor")));
                    }
                }
                listaCuentasVendedorBean.setCuentaVendedor(arrayList);
                consultarAdhesionVendedorBodyResponseBean.setListaCuentasVendedorBean(listaCuentasVendedorBean);
                consultarAdhesionVendedorBodyResponseBean.resCod = jSONObject3.has("resCod") ? jSONObject3.getString("resCod") : "";
                consultarAdhesionVendedorBodyResponseBean.resDesc = jSONObject3.has("resDesc") ? jSONObject3.getString("resDesc") : "";
                consultarAdhesionVendedorBodyResponseBean.setLeyendaDebin(jSONObject3.has("leyendaDebin") ? jSONObject3.getString("leyendaDebin") : "");
                consultarAdhesionVendedorResponseBean.headerBean = privateHeaderBean;
                consultarAdhesionVendedorResponseBean.consultarAdhesionVendedorBodyResponseBean = consultarAdhesionVendedorBodyResponseBean;
                onResponseBean(consultarAdhesionVendedorResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private CuentaVendedor getCuentaVendedorObject(Gson gson, JSONObject jSONObject) {
        CuentaVendedor cuentaVendedor = new CuentaVendedor();
        try {
            cuentaVendedor.setNumero(jSONObject.has("numero") ? jSONObject.getString("numero") : "");
            cuentaVendedor.setSucursal(jSONObject.has("sucursal") ? jSONObject.getString("sucursal") : "");
            cuentaVendedor.setTipo(jSONObject.has("tipo") ? jSONObject.getString("tipo") : "");
            cuentaVendedor.setAlias(jSONObject.has("alias") ? jSONObject.getString("alias") : "");
            cuentaVendedor.setBanco(jSONObject.has("banco") ? jSONObject.getString("banco") : "");
            cuentaVendedor.setCbu(jSONObject.has("cbu") ? jSONObject.getString("cbu") : "");
            cuentaVendedor.setNumeroCuenta(jSONObject.has("numeroCuenta") ? jSONObject.getString("numeroCuenta") : "");
            cuentaVendedor.setTipoCuenta(jSONObject.has("tipoCuenta") ? jSONObject.getString("tipoCuenta") : "");
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return cuentaVendedor;
    }
}
