package ar.com.santander.rio.mbanking.managers.analytics;

import android.text.Html;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;

public class InsuranceAnalytics {

    public static class Screens {
        public static String TCObjectsInsurance() {
            return "/seguros/objetos/terminos-y-condiciones";
        }

        public static String objectInsurance() {
            return "/seguros/objetos/seleccion-objeto";
        }

        public static String suggestObject() {
            return "/seguros/objetos/sugerir-objeto";
        }

        public static String objectToContract(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("/seguros/objetos/");
            sb.append(Html.fromHtml(str).toString().toLowerCase().trim().replace(UtilsCuentas.SEPARAOR2, "-"));
            return sb.toString();
        }

        public static String insuranceQuote(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(objectToContract(str));
            sb.append("/cotizar");
            return sb.toString();
        }

        public static String objectState(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(objectToContract(str));
            sb.append("/estado");
            return sb.toString();
        }

        public static String objectPhoto(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(objectToContract(str));
            sb.append("/foto-objeto");
            return sb.toString();
        }

        public static String contractInsurance(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(objectToContract(str));
            sb.append("/contratar");
            return sb.toString();
        }

        public static String confirmInsurance(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(objectToContract(str));
            sb.append("/confirmar-contratar");
            return sb.toString();
        }

        public static String insuranceVoucher(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(objectToContract(str));
            sb.append("/comprobante");
            return sb.toString();
        }

        public static String objectLocation(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(objectToContract(str));
            sb.append("/ubicacion");
            return sb.toString();
        }
    }
}
