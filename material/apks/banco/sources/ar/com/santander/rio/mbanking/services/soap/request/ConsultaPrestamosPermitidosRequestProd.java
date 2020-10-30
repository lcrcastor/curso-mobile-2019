package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamoPermitidoProd;
import ar.com.santander.rio.mbanking.services.model.creditos.DatosPrestamosProd;
import ar.com.santander.rio.mbanking.services.model.creditos.PrestamosPermitidosProd;
import ar.com.santander.rio.mbanking.services.model.general.ListaLeyendaProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosRequestBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaPrestamosPermitidosResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AccountRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaPrestamosPermitidosBodyResponseBeanProd;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class ConsultaPrestamosPermitidosRequestProd extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaPrestamosPermitidosRequestProd.class.getName();
    private ConsultaPrestamosPermitidosRequestBeanProd consultaPrestamosPermitidosRequestBeanProd;
    private ConsultaPrestamosPermitidosResponseBeanProd consultaPrestamosPermitidosResponseBeanProd;

    public int getMethod() {
        return 1;
    }

    public ConsultaPrestamosPermitidosRequestProd(Context context, ConsultaPrestamosPermitidosRequestBeanProd consultaPrestamosPermitidosRequestBeanProd2, ErrorRequestServer errorRequestServer) {
        super(context);
        this.consultaPrestamosPermitidosRequestBeanProd = consultaPrestamosPermitidosRequestBeanProd2;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConsultaPrestamosPermitidosRequestProd(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.consultaPrestamosPermitidosRequestBeanProd;
    }

    public Class getBeanResponseClass() {
        if (this.consultaPrestamosPermitidosResponseBeanProd == null) {
            this.consultaPrestamosPermitidosResponseBeanProd = new ConsultaPrestamosPermitidosResponseBeanProd();
        }
        return this.consultaPrestamosPermitidosResponseBeanProd.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                if (jSONObject3.getJSONObject("prestPermitidos").get("datosPrestPerm") instanceof JSONObject) {
                    ConsultaPrestamosPermitidosResponseBeanProd consultaPrestamosPermitidosResponseBeanProd2 = new ConsultaPrestamosPermitidosResponseBeanProd();
                    ConsultaPrestamosPermitidosBodyResponseBeanProd consultaPrestamosPermitidosBodyResponseBeanProd = new ConsultaPrestamosPermitidosBodyResponseBeanProd();
                    consultaPrestamosPermitidosBodyResponseBeanProd.setDatosCuenta((AccountRequestBean) gson.fromJson(jSONObject3.getJSONObject("datosCuenta").toString(), AccountRequestBean.class));
                    consultaPrestamosPermitidosBodyResponseBeanProd.setDatosPrestamosProd((DatosPrestamosProd) gson.fromJson(jSONObject3.getJSONObject("datosPrest").toString(), DatosPrestamosProd.class));
                    consultaPrestamosPermitidosBodyResponseBeanProd.setListaLeyendaProd((ListaLeyendaProd) gson.fromJson(jSONObject3.getJSONObject("listaLeyendas").toString(), ListaLeyendaProd.class));
                    PrestamosPermitidosProd prestamosPermitidosProd = new PrestamosPermitidosProd();
                    ArrayList arrayList = new ArrayList();
                    arrayList.add((DatosPrestamoPermitidoProd) gson.fromJson(jSONObject3.getJSONObject("prestPermitidos").get("datosPrestPerm").toString(), DatosPrestamoPermitidoProd.class));
                    prestamosPermitidosProd.setListaDatosPrestamoPermitidoProd(arrayList);
                    consultaPrestamosPermitidosBodyResponseBeanProd.setPrestamosPermitidosProd(prestamosPermitidosProd);
                    consultaPrestamosPermitidosResponseBeanProd2.consultaPrestamosPermitidosBodyResponseBeanProd = consultaPrestamosPermitidosBodyResponseBeanProd;
                    onResponseBean(consultaPrestamosPermitidosResponseBeanProd2);
                } else {
                    onResponseBean((IBeanWS) gson.fromJson(jSONObject2.toString(), getBeanResponseClass()));
                }
            } catch (Exception unused) {
            }
        }
        return parserResponse;
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }
}
