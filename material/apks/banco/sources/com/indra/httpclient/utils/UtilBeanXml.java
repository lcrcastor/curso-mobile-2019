package com.indra.httpclient.utils;

import android.util.Log;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import com.indra.httpclient.json.XML;

public class UtilBeanXml {
    public static String FOOTER_TAG = "</soap:Body></soap:Envelope>";
    public static String HEADER_TAG = "<soap:Envelope xsi:noNamespaceSchemaLocation=\"request.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">   <soap:Body>";

    public static String beanToStringXml(IBeanWS iBeanWS, String str) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(HEADER_TAG);
            sb.append(XML.toString(convertGsonToJsonObject(convertBeanToGsonString(iBeanWS)), "xml"));
            sb.append(FOOTER_TAG);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String beanToStringXml(String str, String str2) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(HEADER_TAG);
            sb.append(str);
            sb.append(FOOTER_TAG);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static IBeanWS stringXmlToBean(String str, Class cls) {
        try {
            return a(convertStringXmlToJsonObject(str, cls), cls);
        } catch (Exception e) {
            Log.e("@dev", "Error al convertir xml to bean", e);
            return null;
        }
    }

    public static String convertBeanToGsonString(IBeanWS iBeanWS) {
        return new Gson().toJson((Object) iBeanWS);
    }

    public static JSONObject convertGsonToJsonObject(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject convertStringXmlToJsonObject(String str, Class cls) {
        try {
            return XML.toJSONObject(str, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static IBeanWS a(JSONObject jSONObject, Class cls) {
        try {
            return (IBeanWS) new Gson().fromJson(jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml").toString(), cls);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("@dev", "Error al convertir el Json a Bean", e);
            return null;
        } catch (Exception e2) {
            Log.e("@dev", "Error al convertir el Json a Bean", e2);
            return null;
        }
    }
}
