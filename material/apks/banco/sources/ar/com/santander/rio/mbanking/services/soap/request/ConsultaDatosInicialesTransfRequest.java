package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaDatosInicialesTransfRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaDatosInicialesTransfResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CargaInfoTransfBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaDatosInicialesTransfBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentasPropiasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestBSRBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.DatosCuentasDestOBBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendasBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public abstract class ConsultaDatosInicialesTransfRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = ConsultaDatosInicialesTransfRequest.class.getName();
    private ConsultaDatosInicialesTransfRequestBean mConsultaDatosInicialesTransfRequestBean;
    private ConsultaDatosInicialesTransfResponseBean mConsultaDatosInicialesTransfResponseBean;

    public int getMethod() {
        return 1;
    }

    public ConsultaDatosInicialesTransfRequest(Context context, ConsultaDatosInicialesTransfRequestBean consultaDatosInicialesTransfRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mConsultaDatosInicialesTransfRequestBean = consultaDatosInicialesTransfRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public ConsultaDatosInicialesTransfRequest(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mConsultaDatosInicialesTransfRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mConsultaDatosInicialesTransfResponseBean == null) {
            this.mConsultaDatosInicialesTransfResponseBean = new ConsultaDatosInicialesTransfResponseBean();
        }
        return this.mConsultaDatosInicialesTransfResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean z;
        boolean z2;
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("parserResponse JSON ");
        sb.append(jSONObject.toString());
        Log.d(str, sb.toString());
        boolean parserResponse = super.parserResponse(jSONObject);
        if (!parserResponse) {
            return parserResponse;
        }
        Gson gson = new Gson();
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
            String str2 = this.TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("parserResponse JSON xml ");
            sb2.append(jSONObject2.toString());
            Log.d(str2, sb2.toString());
            Log.d(this.TAG, "cargaInfoTransf {");
            JSONObject jSONObject3 = jSONObject2.getJSONObject("body").getJSONObject("cargaInfoTransf");
            String str3 = this.TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("fechaEjecucion ");
            sb3.append(jSONObject3.get("fechaEjecucion"));
            Log.d(str3, sb3.toString());
            Log.d(this.TAG, "---> LISTADO CUENTAS PROPIAS");
            JSONObject jSONObject4 = jSONObject3.getJSONObject("listaCuentasPropias");
            String str4 = this.TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("impMaxP ");
            sb4.append(jSONObject4.get("impMaxP"));
            Log.d(str4, sb4.toString());
            String str5 = this.TAG;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("impMaxD ");
            sb5.append(jSONObject4.get("impMaxD"));
            Log.d(str5, sb5.toString());
            Object obj = jSONObject4.get("datosCuenta");
            ArrayList arrayList = new ArrayList();
            if (obj instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                    String str6 = this.TAG;
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("datosCuenta PROPIAS ");
                    sb6.append(((JSONArray) obj).getJSONObject(i).toString());
                    Log.d(str6, sb6.toString());
                    arrayList.add((DatosCuentasBean) gson.fromJson(((JSONArray) obj).getJSONObject(i).toString(), DatosCuentasBean.class));
                }
            } else if (obj instanceof JSONObject) {
                DatosCuentasBean datosCuentasBean = (DatosCuentasBean) gson.fromJson(obj.toString(), DatosCuentasBean.class);
                String str7 = this.TAG;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("datosCuenta PROPIAS");
                sb7.append(obj.toString());
                Log.d(str7, sb7.toString());
                arrayList.add(datosCuentasBean);
            }
            CuentasPropiasBean cuentasPropiasBean = new CuentasPropiasBean(jSONObject4.get("impMaxP").toString(), jSONObject4.get("impMaxD").toString(), arrayList);
            Log.d(this.TAG, "---> LISTADO AGENDADOS");
            JSONObject jSONObject5 = jSONObject3.getJSONObject("listasAgendados");
            String str8 = this.TAG;
            StringBuilder sb8 = new StringBuilder();
            sb8.append("listadoCompleto ");
            sb8.append(jSONObject5.get("listadoCompleto"));
            Log.d(str8, sb8.toString());
            String str9 = "";
            if (jSONObject5.get("listadoCompleto").toString().equals("N")) {
                String str10 = this.TAG;
                StringBuilder sb9 = new StringBuilder();
                sb9.append("mensajeAgendados ");
                sb9.append(jSONObject5.get("mensajeAgendados").toString());
                Log.d(str10, sb9.toString());
                str9 = jSONObject5.get("mensajeAgendados").toString();
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            if (jSONObject5.has("listaAgDestBSR")) {
                Log.d(this.TAG, "---> LISTADO AGENDADOS TERCEROS");
                JSONObject jSONObject6 = jSONObject5.getJSONObject("listaAgDestBSR");
                String str11 = this.TAG;
                StringBuilder sb10 = new StringBuilder();
                sb10.append("listaAgDestBSR ");
                sb10.append(jSONObject5.getJSONObject("listaAgDestBSR").toString());
                Log.d(str11, sb10.toString());
                String str12 = this.TAG;
                StringBuilder sb11 = new StringBuilder();
                sb11.append("impMaxP ");
                sb11.append(jSONObject6.get("impMaxP"));
                Log.d(str12, sb11.toString());
                String str13 = this.TAG;
                StringBuilder sb12 = new StringBuilder();
                sb12.append("impMaxD ");
                sb12.append(jSONObject6.get("impMaxD"));
                Log.d(str13, sb12.toString());
                ArrayList arrayList4 = new ArrayList();
                if (jSONObject6.has("datosCuenta")) {
                    Object obj2 = jSONObject6.get("datosCuenta");
                    if (obj2 instanceof JSONArray) {
                        int i2 = 0;
                        while (i2 < ((JSONArray) obj2).length()) {
                            String str14 = this.TAG;
                            StringBuilder sb13 = new StringBuilder();
                            z = parserResponse;
                            try {
                                sb13.append("datosCuenta TERCEROS ");
                                sb13.append(((JSONArray) obj2).getJSONObject(i2).toString());
                                Log.d(str14, sb13.toString());
                                arrayList4.add((DatosCuentasDestBSRBean) gson.fromJson(((JSONArray) obj2).getJSONObject(i2).toString(), DatosCuentasDestBSRBean.class));
                                i2++;
                                parserResponse = z;
                            } catch (JSONException e) {
                                e = e;
                                onUnknowError(e);
                                return z;
                            }
                        }
                    } else {
                        z2 = parserResponse;
                        if (obj2 instanceof JSONObject) {
                            DatosCuentasDestBSRBean datosCuentasDestBSRBean = (DatosCuentasDestBSRBean) gson.fromJson(obj2.toString(), DatosCuentasDestBSRBean.class);
                            String str15 = this.TAG;
                            StringBuilder sb14 = new StringBuilder();
                            sb14.append("datosCuenta TERCEROS ");
                            sb14.append(datosCuentasDestBSRBean.getTipoDestino());
                            Log.d(str15, sb14.toString());
                            arrayList4.add(datosCuentasDestBSRBean);
                        }
                        arrayList2.add(new AgDestBSRBean(jSONObject6.get("impMaxP").toString(), jSONObject6.get("impMaxD").toString(), arrayList4));
                    }
                }
                z2 = parserResponse;
                arrayList2.add(new AgDestBSRBean(jSONObject6.get("impMaxP").toString(), jSONObject6.get("impMaxD").toString(), arrayList4));
            } else {
                z2 = parserResponse;
            }
            if (jSONObject5.has("listaAgDestOB")) {
                Log.d(this.TAG, "---> LISTADO AGENDADOS OTROS BANCOS");
                JSONObject jSONObject7 = jSONObject5.getJSONObject("listaAgDestOB");
                String str16 = this.TAG;
                StringBuilder sb15 = new StringBuilder();
                sb15.append("listaAgDestOB ");
                sb15.append(jSONObject5.getJSONObject("listaAgDestOB").toString());
                Log.d(str16, sb15.toString());
                String str17 = this.TAG;
                StringBuilder sb16 = new StringBuilder();
                sb16.append("impMaxP ");
                sb16.append(jSONObject7.get("impMaxP"));
                Log.d(str17, sb16.toString());
                String str18 = this.TAG;
                StringBuilder sb17 = new StringBuilder();
                sb17.append("impMaxD ");
                sb17.append(jSONObject7.get("impMaxD"));
                Log.d(str18, sb17.toString());
                ArrayList arrayList5 = new ArrayList();
                if (jSONObject7.has("datosCuenta")) {
                    Object obj3 = jSONObject7.get("datosCuenta");
                    if (obj3 instanceof JSONArray) {
                        for (int i3 = 0; i3 < ((JSONArray) obj3).length(); i3++) {
                            String str19 = this.TAG;
                            StringBuilder sb18 = new StringBuilder();
                            sb18.append("datosCuenta OTROS BANCOS ");
                            sb18.append(((JSONArray) obj3).getJSONObject(i3).toString());
                            Log.d(str19, sb18.toString());
                            arrayList5.add((DatosCuentasDestOBBean) gson.fromJson(((JSONArray) obj3).getJSONObject(i3).toString(), DatosCuentasDestOBBean.class));
                        }
                    } else if (obj3 instanceof JSONObject) {
                        DatosCuentasDestOBBean datosCuentasDestOBBean = (DatosCuentasDestOBBean) gson.fromJson(obj3.toString(), DatosCuentasDestOBBean.class);
                        String str20 = this.TAG;
                        StringBuilder sb19 = new StringBuilder();
                        sb19.append("datosCuenta OTROS BANCOS ");
                        sb19.append(datosCuentasDestOBBean.getTipoDestino());
                        Log.d(str20, sb19.toString());
                        arrayList5.add(datosCuentasDestOBBean);
                    }
                }
                arrayList3.add(new AgDestOBBean(jSONObject7.get("impMaxP").toString(), jSONObject7.get("impMaxD").toString(), arrayList5));
            }
            AgendadosBean agendadosBean = new AgendadosBean(jSONObject5.get("listadoCompleto").toString(), str9, arrayList2, arrayList3);
            Log.d(this.TAG, "---> LISTADO LEYENDAS");
            ArrayList arrayList6 = new ArrayList();
            if (jSONObject3.has("listaLeyendas")) {
                JSONObject jSONObject8 = jSONObject3.getJSONObject("listaLeyendas");
                String str21 = this.TAG;
                StringBuilder sb20 = new StringBuilder();
                sb20.append("listaLeyendas ");
                sb20.append(jSONObject3.getJSONObject("listaLeyendas").toString());
                Log.d(str21, sb20.toString());
                JSONArray jSONArray = jSONObject8.getJSONArray("leyenda");
                for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                    String str22 = this.TAG;
                    StringBuilder sb21 = new StringBuilder();
                    sb21.append("leyenda ");
                    sb21.append(jSONArray.get(i4).toString());
                    Log.d(str22, sb21.toString());
                    arrayList6.add((LeyendaBean) gson.fromJson(jSONArray.getJSONObject(i4).toString(), LeyendaBean.class));
                }
            }
            CargaInfoTransfBean cargaInfoTransfBean = new CargaInfoTransfBean(jSONObject3.get("fechaEjecucion").toString(), cuentasPropiasBean, agendadosBean, new LeyendasBean((List<LeyendaBean>) arrayList6));
            ConsultaDatosInicialesTransfResponseBean consultaDatosInicialesTransfResponseBean = new ConsultaDatosInicialesTransfResponseBean();
            consultaDatosInicialesTransfResponseBean.setConsultaDatosInicialesTransfBodyResponseBean(new ConsultaDatosInicialesTransfBodyResponseBean(cargaInfoTransfBean));
            onResponseBean(consultaDatosInicialesTransfResponseBean);
            return z2;
        } catch (JSONException e2) {
            e = e2;
            z = parserResponse;
            onUnknowError(e);
            return z;
        }
    }
}
