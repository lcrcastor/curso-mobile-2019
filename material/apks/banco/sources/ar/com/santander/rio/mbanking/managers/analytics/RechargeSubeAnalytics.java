package ar.com.santander.rio.mbanking.managers.analytics;

import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import java.util.Map;

public class RechargeSubeAnalytics {

    public static class EventHit {
        private static EventBuilder a() {
            return new EventBuilder().setCategory("recarga-sube");
        }

        public static Map<String, String> rechargeAmount(String str) {
            return a().setAction("monto").setLabel(str).build();
        }

        public static Map<String, String> addSube() {
            return a().setAction("agregar-tarjeta").setLabel("agregar-tarjeta").build();
        }

        public static Map<String, String> rechargeVoucher() {
            return a().setAction("descargar").setLabel("comprobante").build();
        }
    }

    public static class Screen {
        public static String config() {
            return "/recarga/sube/configuracion-carga";
        }

        public static String confim() {
            return "/recarga/sube/confirmacion";
        }

        public static String fdkNoOk() {
            return "/recarga/sube/fdknook";
        }

        public static String fdkok() {
            return "/recarga/sube/fdkok";
        }

        public static String init() {
            return "/recarga/sube/inicio";
        }

        public static String register() {
            return "/recarga/sube/registrar-sube";
        }

        public static String voucher() {
            return "/recarga/sube/comprobante";
        }
    }
}
