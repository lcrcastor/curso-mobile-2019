package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.model.general.DatosLiquidacion;
import ar.com.santander.rio.mbanking.services.model.general.DetalleLiquidacion;
import ar.com.santander.rio.mbanking.services.model.general.FechaLiquidacion;
import ar.com.santander.rio.mbanking.services.model.general.FechasLiquidacion;
import ar.com.santander.rio.mbanking.services.model.general.Liquidacion;
import ar.com.santander.rio.mbanking.services.model.general.Movimiento;
import ar.com.santander.rio.mbanking.services.model.general.Movimientos;
import ar.com.santander.rio.mbanking.services.model.general.Pago;
import ar.com.santander.rio.mbanking.services.model.general.Pagos;
import ar.com.santander.rio.mbanking.services.model.general.Saldo;
import ar.com.santander.rio.mbanking.services.model.general.Saldos;
import ar.com.santander.rio.mbanking.services.model.general.TarjetaResumen;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimoResumenTCRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.UltimoResumenTCResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class UltimoResumenTCRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = UltimoResumenTCRequest.class.getName();
    private UltimoResumenTCRequestBean requestBean;
    private UltimoResumenTCResponseBean responseBean;

    public int getMethod() {
        return 1;
    }

    public UltimoResumenTCRequest(Context context, UltimoResumenTCRequestBean ultimoResumenTCRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.requestBean = ultimoResumenTCRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.requestBean;
    }

    public Class getBeanResponseClass() {
        if (this.responseBean == null) {
            this.responseBean = new UltimoResumenTCResponseBean();
        }
        return this.responseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean z;
        JSONException jSONException;
        Exception exc;
        UltimoResumenTCRequest ultimoResumenTCRequest = this;
        boolean parserResponse = super.parserResponse(jSONObject);
        if (!parserResponse) {
            return parserResponse;
        }
        Gson gson = new Gson();
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
            UltimoResumenTCResponseBean ultimoResumenTCResponseBean = new UltimoResumenTCResponseBean();
            ultimoResumenTCResponseBean.setHeader((PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class));
            UltimoResumenTCBodyResponseBean ultimoResumenTCBodyResponseBean = new UltimoResumenTCBodyResponseBean();
            TarjetaResumen tarjetaResumen = new TarjetaResumen();
            Liquidacion liquidacion = new Liquidacion();
            Saldos saldos = new Saldos();
            FechasLiquidacion fechasLiquidacion = new FechasLiquidacion();
            Pagos pagos = new Pagos();
            DetalleLiquidacion detalleLiquidacion = new DetalleLiquidacion();
            Movimientos movimientos = new Movimientos();
            JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
            if (jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").has("anterior")) {
                String obj = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").get("anterior").toString();
                if (obj != null) {
                    liquidacion.setAnterior(obj);
                }
            }
            if (jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").has("proxima")) {
                String obj2 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").get("proxima").toString();
                if (obj2 != null) {
                    liquidacion.setProxima(obj2);
                }
            }
            if (jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").has("numero")) {
                String obj3 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").get("numero").toString();
                if (obj3 != null) {
                    liquidacion.setNumero(obj3);
                }
            }
            if (jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").has("resumen")) {
                String obj4 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").get("resumen").toString();
                if (obj4 != null) {
                    liquidacion.setResumen(obj4);
                }
            }
            if (jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject(PagoTarjetasConstants.HELP_AMOUNT_PAYABLE_TOTAL).has("content")) {
                String obj5 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject(PagoTarjetasConstants.HELP_AMOUNT_PAYABLE_TOTAL).get("content").toString();
                if (obj5 != null) {
                    liquidacion.setTotal(obj5);
                }
            }
            if (jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject(PagoTarjetasConstants.HELP_AMOUNT_PAYABLE_TOTAL).has("sessionID")) {
                String obj6 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject(PagoTarjetasConstants.HELP_AMOUNT_PAYABLE_TOTAL).get("sessionID").toString();
                if (obj6 != null) {
                    liquidacion.setSessionID(obj6);
                }
            }
            Object obj7 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject("saldos").get("saldo");
            ArrayList arrayList = new ArrayList();
            if (obj7 != null) {
                z = parserResponse;
                try {
                    if (obj7 instanceof JSONObject) {
                        arrayList.add((Saldo) gson.fromJson(obj7.toString(), Saldo.class));
                    } else if (obj7 instanceof JSONArray) {
                        int i = 0;
                        while (i < ((JSONArray) obj7).length()) {
                            try {
                                Object obj8 = obj7;
                                arrayList.add((Saldo) gson.fromJson(((JSONArray) obj7).get(i).toString(), Saldo.class));
                                i++;
                                obj7 = obj8;
                            } catch (JSONException e) {
                                jSONException = e;
                                ultimoResumenTCRequest = this;
                                StringBuilder sb = new StringBuilder();
                                sb.append(UltimoResumenTCRequest.class.getSimpleName());
                                sb.append(": ");
                                sb.append(jSONException.toString());
                                Log.d("@dev", sb.toString());
                                ultimoResumenTCRequest.onUnknowError(jSONException);
                                return z;
                            } catch (Exception e2) {
                                exc = e2;
                                ultimoResumenTCRequest = this;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(UltimoResumenTCRequest.class.getSimpleName());
                                sb2.append(": ");
                                sb2.append(exc.toString());
                                Log.d("@dev", sb2.toString());
                                ultimoResumenTCRequest.onUnknowError(exc);
                                return z;
                            }
                        }
                    }
                    saldos.setSaldos(arrayList);
                    liquidacion.setSaldos(saldos);
                } catch (JSONException e3) {
                    e = e3;
                } catch (Exception e4) {
                    e = e4;
                    exc = e;
                    StringBuilder sb22 = new StringBuilder();
                    sb22.append(UltimoResumenTCRequest.class.getSimpleName());
                    sb22.append(": ");
                    sb22.append(exc.toString());
                    Log.d("@dev", sb22.toString());
                    ultimoResumenTCRequest.onUnknowError(exc);
                    return z;
                }
            } else {
                z = parserResponse;
            }
            try {
                JSONObject jSONObject4 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject("datos");
                if (jSONObject4 != null) {
                    liquidacion.setDatos((DatosLiquidacion) gson.fromJson(jSONObject4.toString(), DatosLiquidacion.class));
                }
                Object obj9 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject("fechas").get(TarjetasConstants.FECHA);
                ArrayList arrayList2 = new ArrayList();
                if (obj9 != null) {
                    if (obj9 instanceof JSONObject) {
                        arrayList2.add((FechaLiquidacion) gson.fromJson(obj9.toString(), FechaLiquidacion.class));
                    } else if (obj9 instanceof JSONArray) {
                        for (int i2 = 0; i2 < ((JSONArray) obj9).length(); i2++) {
                            arrayList2.add((FechaLiquidacion) gson.fromJson(((JSONArray) obj9).get(i2).toString(), FechaLiquidacion.class));
                        }
                    }
                    fechasLiquidacion.setFechas(arrayList2);
                    liquidacion.setFechas(fechasLiquidacion);
                }
                Object obj10 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject("pagos").get("pago");
                ArrayList arrayList3 = new ArrayList();
                if (obj10 != null) {
                    if (obj10 instanceof JSONObject) {
                        arrayList3.add((Pago) gson.fromJson(obj10.toString(), Pago.class));
                    } else if (obj10 instanceof JSONArray) {
                        for (int i3 = 0; i3 < ((JSONArray) obj10).length(); i3++) {
                            arrayList3.add((Pago) gson.fromJson(((JSONArray) obj10).get(i3).toString(), Pago.class));
                        }
                    }
                    pagos.setPagos(arrayList3);
                    liquidacion.setPagos(pagos);
                }
                Object obj11 = jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject("detalleLiquidacion").get("movimiento");
                ArrayList arrayList4 = new ArrayList();
                if (obj11 != null) {
                    if (obj11 instanceof JSONObject) {
                        arrayList4.add((Movimiento) gson.fromJson(obj11.toString(), Movimiento.class));
                    } else if (obj11 instanceof JSONArray) {
                        for (int i4 = 0; i4 < ((JSONArray) obj11).length(); i4++) {
                            arrayList4.add((Movimiento) gson.fromJson(((JSONArray) obj11).get(i4).toString(), Movimiento.class));
                        }
                    }
                    movimientos.setMovimientos(arrayList4);
                    detalleLiquidacion.setMovimientos(movimientos);
                    detalleLiquidacion.setLegales(jSONObject3.getJSONObject("tarjetas").getJSONObject(TarjetasConstants.TARJETA).getJSONObject("liquidacion").getJSONObject("detalleLiquidacion").get("legales").toString());
                    liquidacion.setDetalleLiquidacion(detalleLiquidacion);
                }
                tarjetaResumen.setLiquidacion(liquidacion);
                ultimoResumenTCBodyResponseBean.setTarjeta(tarjetaResumen);
                ultimoResumenTCResponseBean.setBody(ultimoResumenTCBodyResponseBean);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("UltResRequest: ");
                sb3.append(ultimoResumenTCBodyResponseBean.toString());
                Log.d("@dev", sb3.toString());
                StringBuilder sb4 = new StringBuilder();
                sb4.append("UltResRequest: ");
                sb4.append(ultimoResumenTCBodyResponseBean.getTarjeta().toString());
                Log.d("@dev", sb4.toString());
                ultimoResumenTCRequest = this;
                ultimoResumenTCRequest.onResponseBean(ultimoResumenTCResponseBean);
                return z;
            } catch (JSONException e5) {
                e = e5;
                ultimoResumenTCRequest = this;
                jSONException = e;
                StringBuilder sb5 = new StringBuilder();
                sb5.append(UltimoResumenTCRequest.class.getSimpleName());
                sb5.append(": ");
                sb5.append(jSONException.toString());
                Log.d("@dev", sb5.toString());
                ultimoResumenTCRequest.onUnknowError(jSONException);
                return z;
            } catch (Exception e6) {
                e = e6;
                ultimoResumenTCRequest = this;
                exc = e;
                StringBuilder sb222 = new StringBuilder();
                sb222.append(UltimoResumenTCRequest.class.getSimpleName());
                sb222.append(": ");
                sb222.append(exc.toString());
                Log.d("@dev", sb222.toString());
                ultimoResumenTCRequest.onUnknowError(exc);
                return z;
            }
        } catch (JSONException e7) {
            e = e7;
            z = parserResponse;
            jSONException = e;
            StringBuilder sb52 = new StringBuilder();
            sb52.append(UltimoResumenTCRequest.class.getSimpleName());
            sb52.append(": ");
            sb52.append(jSONException.toString());
            Log.d("@dev", sb52.toString());
            ultimoResumenTCRequest.onUnknowError(jSONException);
            return z;
        } catch (Exception e8) {
            e = e8;
            z = parserResponse;
            exc = e;
            StringBuilder sb2222 = new StringBuilder();
            sb2222.append(UltimoResumenTCRequest.class.getSimpleName());
            sb2222.append(": ");
            sb2222.append(exc.toString());
            Log.d("@dev", sb2222.toString());
            ultimoResumenTCRequest.onUnknowError(exc);
            return z;
        }
    }
}
