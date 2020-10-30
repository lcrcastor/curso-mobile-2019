package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoTarjetaRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.PagoTarjetaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoTarjetaBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoTarjetaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.HeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import ar.com.santander.rio.mbanking.services.soap.versions.EVersionServices;
import ar.com.santander.rio.mbanking.services.soap.versions.VPagoTarjeta;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;

public abstract class PagoTarjetaRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = PagoTarjetaRequest.class.getName();
    private PagoTarjetaRequestBean pagoTarjetaRequestBean;
    private PagoTarjetaResponseBean pagoTarjetaResponseBean;

    public int getMethod() {
        return 1;
    }

    public PagoTarjetaRequest(Context context, PagoTarjetaRequestBean pagoTarjetaRequestBean2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.pagoTarjetaRequestBean = pagoTarjetaRequestBean2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public PagoTarjetaRequest(Context context, PagoTarjetaBodyRequestBean pagoTarjetaBodyRequestBean, ErrorRequestServer errorRequestServer, String str, String str2) {
        super(context);
        this.pagoTarjetaRequestBean = new PagoTarjetaRequestBean(getPrivateHeaderBean(VPagoTarjeta.getData(EVersionServices.CURRENT), str, str2), pagoTarjetaBodyRequestBean);
        this.mErrorRequestServer = errorRequestServer;
    }

    public PagoTarjetaRequest(ErrorRequestServer errorRequestServer) {
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.pagoTarjetaRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.pagoTarjetaResponseBean == null) {
            this.pagoTarjetaResponseBean = new PagoTarjetaResponseBean();
        }
        return this.pagoTarjetaResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("parserResponse JSONObject ");
        sb.append(jSONObject.toString());
        Log.d(str, sb.toString());
        boolean parserResponse = super.parserResponse(jSONObject);
        String str2 = this.TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("responseOK ");
        sb2.append(parserResponse);
        Log.d(str2, sb2.toString());
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                PagoTarjetaResponseBean pagoTarjetaResponseBean2 = new PagoTarjetaResponseBean();
                pagoTarjetaResponseBean2.setHeaderBean((HeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), HeaderBean.class));
                PagoTarjetaBodyResponseBean pagoTarjetaBodyResponseBean = new PagoTarjetaBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                String str3 = this.TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("fechaOperacion ");
                sb3.append(jSONObject3.getString("fechaOperacion"));
                Log.d(str3, sb3.toString());
                pagoTarjetaBodyResponseBean.setFechaOperacion(jSONObject3.getString("fechaOperacion"));
                String str4 = this.TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("numComprobante ");
                sb4.append(jSONObject3.getString("numComprobante"));
                Log.d(str4, sb4.toString());
                pagoTarjetaBodyResponseBean.setNumComprobante(jSONObject3.getString("numComprobante"));
                String str5 = this.TAG;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("tipoPagoTC ");
                sb5.append(jSONObject3.getString("tipoPagoTC"));
                Log.d(str5, sb5.toString());
                pagoTarjetaBodyResponseBean.setTipoPagoTC(jSONObject3.getString("tipoPagoTC"));
                String str6 = this.TAG;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("importePagoTC ");
                sb6.append(jSONObject3.getString("importePagoTC"));
                Log.d(str6, sb6.toString());
                pagoTarjetaBodyResponseBean.setImportePagoTC(jSONObject3.getString("importePagoTC"));
                String str7 = this.TAG;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("importeDolares ");
                sb7.append(jSONObject3.getString("importeDolares"));
                Log.d(str7, sb7.toString());
                pagoTarjetaBodyResponseBean.setImporteDolares(jSONObject3.getString("importeDolares"));
                String str8 = this.TAG;
                StringBuilder sb8 = new StringBuilder();
                sb8.append("tipoCuentaDebito ");
                sb8.append(jSONObject3.getString("tipoCuentaDebito"));
                Log.d(str8, sb8.toString());
                pagoTarjetaBodyResponseBean.setTipoCuentaDebito(jSONObject3.getString("tipoCuentaDebito"));
                try {
                    String str9 = this.TAG;
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append("cuentaBDolares ");
                    sb9.append(jSONObject3.getString("cuentaBDolares"));
                    Log.d(str9, sb9.toString());
                    pagoTarjetaBodyResponseBean.setCuentaBDolares(jSONObject3.getString("cuentaBDolares"));
                } catch (JSONException unused) {
                    pagoTarjetaBodyResponseBean.setCuentaBDolares("");
                }
                pagoTarjetaResponseBean2.setPagoTarjetaBodyResponseBean(pagoTarjetaBodyResponseBean);
                onResponseBean(pagoTarjetaResponseBean2);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }
}
