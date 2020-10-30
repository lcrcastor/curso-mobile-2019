package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import java.util.Map;

public class CTypeDocument {
    public static String getNameDocument(String str) {
        return (String) a().get(str);
    }

    private static Map<String, String> a() {
        return LoginConstants.TIPOS_DOCUMENTO;
    }
}
