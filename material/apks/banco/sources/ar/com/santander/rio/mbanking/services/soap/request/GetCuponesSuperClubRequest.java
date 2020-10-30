package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCuponesSuperClubRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetCuponesSuperClubResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuponesSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCuponesSuperClubBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesAdheridosSuperClub;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LocalesSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ZonaSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ZonasSuperClubBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetCuponesSuperClubRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private final String TAG = GetCuponesSuperClubRequest.class.getName();
    private GetCuponesSuperClubRequestBean mGetCuponesSuperClubRequestBean;
    private GetCuponesSuperClubResponseBean mGetCuponesSuperClubResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetCuponesSuperClubRequest(Context context, GetCuponesSuperClubRequestBean getCuponesSuperClubRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetCuponesSuperClubRequestBean = getCuponesSuperClubRequestBean;
        this.mErrorRequestServer = errorRequestServer;
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

    public IBeanWS getBeanToRequest() {
        return this.mGetCuponesSuperClubRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetCuponesSuperClubResponseBean == null) {
            this.mGetCuponesSuperClubResponseBean = new GetCuponesSuperClubResponseBean();
        }
        return this.mGetCuponesSuperClubResponseBean.getClass();
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean z;
        Exception exc;
        GetCuponesSuperClubRequest getCuponesSuperClubRequest = this;
        boolean parserResponse = super.parserResponse(jSONObject);
        if (!parserResponse) {
            return parserResponse;
        }
        Gson gson = new Gson();
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
            GetCuponesSuperClubResponseBean getCuponesSuperClubResponseBean = new GetCuponesSuperClubResponseBean();
            PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
            GetCuponesSuperClubBodyResponseBean getCuponesSuperClubBodyResponseBean = new GetCuponesSuperClubBodyResponseBean();
            JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
            CuponesSuperClubBean cuponesSuperClubBean = new CuponesSuperClubBean();
            Object obj = jSONObject3.getJSONObject("cupones").get("cupon");
            if (obj instanceof JSONArray) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                    arrayList.add((CuponSuperClubBean) gson.fromJson(((JSONArray) obj).getJSONObject(i).toString(), CuponSuperClubBean.class));
                }
                cuponesSuperClubBean.cupon = arrayList;
                getCuponesSuperClubBodyResponseBean.cupones = cuponesSuperClubBean;
            } else if (obj instanceof JSONObject) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add((CuponSuperClubBean) gson.fromJson(obj.toString(), CuponSuperClubBean.class));
                cuponesSuperClubBean.cupon = arrayList2;
                getCuponesSuperClubBodyResponseBean.cupones = cuponesSuperClubBean;
            }
            LocalesAdheridosSuperClub localesAdheridosSuperClub = new LocalesAdheridosSuperClub();
            if (jSONObject3.has("localesAdheridos")) {
                localesAdheridosSuperClub.marca = jSONObject3.getJSONObject("localesAdheridos").getString("marca");
                ZonasSuperClubBean zonasSuperClubBean = new ZonasSuperClubBean();
                Object obj2 = jSONObject3.getJSONObject("localesAdheridos").getJSONObject("zonas").get("zona");
                if (obj2 instanceof JSONArray) {
                    ArrayList arrayList3 = new ArrayList();
                    int i2 = 0;
                    while (i2 < ((JSONArray) obj2).length()) {
                        LocalesSuperClubBean localesSuperClubBean = new LocalesSuperClubBean();
                        Object obj3 = ((JSONArray) obj2).getJSONObject(i2).getJSONObject("locales").get("local");
                        if (obj3 instanceof JSONArray) {
                            ArrayList arrayList4 = new ArrayList();
                            z = parserResponse;
                            int i3 = 0;
                            while (i3 < ((JSONArray) obj3).length()) {
                                try {
                                } catch (JSONException e) {
                                    e = e;
                                }
                                try {
                                    arrayList4.add((LocalSuperClubBean) gson.fromJson(((JSONArray) obj3).getJSONObject(i3).toString(), LocalSuperClubBean.class));
                                    i3++;
                                    getCuponesSuperClubRequest = this;
                                } catch (JSONException e2) {
                                    exc = e2;
                                    getCuponesSuperClubRequest = this;
                                    getCuponesSuperClubRequest.onUnknowError(exc);
                                    return z;
                                }
                            }
                            localesSuperClubBean.local = arrayList4;
                        } else {
                            z = parserResponse;
                            if (obj3 instanceof JSONObject) {
                                ArrayList arrayList5 = new ArrayList();
                                arrayList5.add((LocalSuperClubBean) gson.fromJson(obj3.toString(), LocalSuperClubBean.class));
                                localesSuperClubBean.local = arrayList5;
                            }
                        }
                        ZonaSuperClubBean zonaSuperClubBean = new ZonaSuperClubBean();
                        zonaSuperClubBean.nombreZona = ((JSONArray) obj2).getJSONObject(i2).getString("nombreZona");
                        zonaSuperClubBean.locales = localesSuperClubBean;
                        arrayList3.add(zonaSuperClubBean);
                        i2++;
                        parserResponse = z;
                        getCuponesSuperClubRequest = this;
                    }
                    z = parserResponse;
                    zonasSuperClubBean.zona = arrayList3;
                    localesAdheridosSuperClub.zonas = zonasSuperClubBean;
                    getCuponesSuperClubBodyResponseBean.localesAdheridos = localesAdheridosSuperClub;
                } else {
                    z = parserResponse;
                    if (obj2 instanceof JSONObject) {
                        ArrayList arrayList6 = new ArrayList();
                        LocalesSuperClubBean localesSuperClubBean2 = new LocalesSuperClubBean();
                        Object obj4 = ((JSONObject) obj2).getJSONObject("locales").get("local");
                        if (obj4 instanceof JSONArray) {
                            ArrayList arrayList7 = new ArrayList();
                            for (int i4 = 0; i4 < ((JSONArray) obj4).length(); i4++) {
                                arrayList7.add((LocalSuperClubBean) gson.fromJson(((JSONArray) obj4).getJSONObject(i4).toString(), LocalSuperClubBean.class));
                            }
                            localesSuperClubBean2.local = arrayList7;
                        } else if (obj4 instanceof JSONObject) {
                            ArrayList arrayList8 = new ArrayList();
                            arrayList8.add((LocalSuperClubBean) gson.fromJson(obj4.toString(), LocalSuperClubBean.class));
                            localesSuperClubBean2.local = arrayList8;
                        }
                        ZonaSuperClubBean zonaSuperClubBean2 = new ZonaSuperClubBean();
                        zonaSuperClubBean2.nombreZona = ((JSONObject) obj2).getString("nombreZona");
                        zonaSuperClubBean2.locales = localesSuperClubBean2;
                        arrayList6.add(zonaSuperClubBean2);
                        zonasSuperClubBean.zona = arrayList6;
                        localesAdheridosSuperClub.zonas = zonasSuperClubBean;
                        getCuponesSuperClubBodyResponseBean.localesAdheridos = localesAdheridosSuperClub;
                    }
                }
            } else {
                z = parserResponse;
            }
            try {
                getCuponesSuperClubResponseBean.headerBean = privateHeaderBean;
                getCuponesSuperClubResponseBean.getCuponesBodyResponseBean = getCuponesSuperClubBodyResponseBean;
                getCuponesSuperClubRequest = this;
                getCuponesSuperClubRequest.onResponseBean(getCuponesSuperClubResponseBean);
                return z;
            } catch (JSONException e3) {
                e = e3;
                getCuponesSuperClubRequest = this;
                exc = e;
                getCuponesSuperClubRequest.onUnknowError(exc);
                return z;
            }
        } catch (JSONException e4) {
            e = e4;
            z = parserResponse;
            exc = e;
            getCuponesSuperClubRequest.onUnknowError(exc);
            return z;
        }
    }
}
