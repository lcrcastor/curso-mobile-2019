package ar.com.santander.rio.mbanking.managers.preferences;

import android.content.Context;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsEmpresaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetClasificadoresBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OcupacionesBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.WebViewHome;
import com.google.gson.Gson;

public class SettingsManager {
    public static final String CAJEROS_URL_HOME = "CAJEROS_URL_HOME";
    public static final String CHECK_VERSION = "CHECK_VERSION";
    public static final String PROMOCIONES_URL_HOME = "PROMOCIONES_URL_HOME";
    public static final String SUCURSALES_URL_HOME = "SUCURSALES_URL_HOME";
    private SettingsHelper a;

    public SettingsManager(Context context) {
        this.a = new SettingsHelper(context);
        a();
    }

    private void a() {
        if (!this.a.exists("isban.rio.prev_hash_code").booleanValue()) {
            setClasificadoresPrevHashCode("0");
        }
        if (!this.a.exists("isban.rio.primeringreso").booleanValue()) {
            setKeyPrimerIngreso(true);
        }
        if (!this.a.exists("isban.rio.cnsEmpresa.prev_hash_code").booleanValue()) {
            setCnsEmpresaPrevHashCode("");
        }
    }

    public String getClasificadoresPrevHashCode() {
        return this.a.getString("isban.rio.prev_hash_code", "0");
    }

    public void setClasificadoresPrevHashCode(String str) {
        this.a.putString("isban.rio.prev_hash_code", str);
    }

    public boolean getKeyPrimerIngreso() {
        return this.a.getBoolean("isban.rio.primeringreso", Boolean.valueOf(true)).booleanValue();
    }

    public void setKeyPrimerIngreso(boolean z) {
        this.a.putBoolean("isban.rio.primeringreso", Boolean.valueOf(z));
    }

    public String getCnsEmpresaPrevHashCode() {
        return getPagoServiciosEmpresas() != null ? this.a.getString("isban.rio.cnsEmpresa.prev_hash_code", "") : "";
    }

    public void setCnsEmpresaPrevHashCode(String str) {
        this.a.putString("isban.rio.cnsEmpresa.prev_hash_code", str);
    }

    public String getGetOcupacionesPrevHashCode() {
        return this.a.getString("KEY_GET_OCUPACIONES_PREV_HASH_CODE", "");
    }

    public void setGetOcupacionesPrevHashCode(String str) {
        this.a.putString("KEY_GET_OCUPACIONES_PREV_HASH_CODE", str);
    }

    public String getLastIdTerminal() {
        return this.a.getString("cPREF_LAST_IDTERMINAL", "");
    }

    public void setLastIdTerminal(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.a.putString("cPREF_LAST_IDTERMINAL", str);
        } else {
            this.a.putString("cPREF_LAST_IDTERMINAL", "");
        }
    }

    public OcupacionesBean getOcupaciones() {
        return (OcupacionesBean) new Gson().fromJson(this.a.getString("KEY_GET_OCUPACIONES", ""), OcupacionesBean.class);
    }

    public void setOcupaciones(OcupacionesBean ocupacionesBean) {
        this.a.putString("KEY_GET_OCUPACIONES", new Gson().toJson((Object) ocupacionesBean));
    }

    public GetClasificadoresBodyResponseBean getClasificadores() {
        return (GetClasificadoresBodyResponseBean) loadObject(GetClasificadoresBodyResponseBean.class);
    }

    public void setClasificadores(GetClasificadoresBodyResponseBean getClasificadoresBodyResponseBean) {
        saveObject(getClasificadoresBodyResponseBean);
    }

    public CnsEmpresaBodyResponseBean getPagoServiciosEmpresas() {
        return (CnsEmpresaBodyResponseBean) new Gson().fromJson(this.a.getString("isban.rio.empresas", ""), CnsEmpresaBodyResponseBean.class);
    }

    public void putBoolean(String str, boolean z) {
        this.a.putBoolean(str, Boolean.valueOf(z));
    }

    public boolean getBoolean(String str) {
        return this.a.getBoolean(str, Boolean.valueOf(false)).booleanValue();
    }

    public void setWebViewURLHome(WebViewHome webViewHome) {
        if (webViewHome != null) {
            if (webViewHome.getUrlCajeros() != null) {
                this.a.putString(CAJEROS_URL_HOME, webViewHome.getUrlCajeros());
            }
            if (webViewHome.getUrlPromociones() != null) {
                this.a.putString(PROMOCIONES_URL_HOME, webViewHome.getUrlPromociones());
            }
            if (webViewHome.getUrlSucursales() != null) {
                this.a.putString(SUCURSALES_URL_HOME, webViewHome.getUrlSucursales());
            }
        }
    }

    public void setPagoServiciosEmpresas(CnsEmpresaBodyResponseBean cnsEmpresaBodyResponseBean) {
        this.a.putString("isban.rio.empresas", new Gson().toJson((Object) cnsEmpresaBodyResponseBean));
    }

    private <T> String a(Class<T> cls) {
        return a(SessionManager.class, cls);
    }

    private String a(Class cls, Object obj) {
        return a(cls, obj.getClass());
    }

    private <T> String a(Class cls, Class<T> cls2) {
        if (cls == null) {
            return cls2.getName();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName());
        sb.append(".");
        sb.append(cls2.getSimpleName());
        return sb.toString();
    }

    public Boolean saveObject(Object obj) {
        return saveObject(SessionManager.class, obj, Boolean.valueOf(true));
    }

    public Boolean saveObject(Class cls, Object obj) {
        return saveObject(cls, obj, Boolean.valueOf(true));
    }

    public Boolean saveObject(Class cls, Object obj, Boolean bool) {
        if (obj == null) {
            return Boolean.valueOf(false);
        }
        try {
            String a2 = a(cls, obj);
            if (this.a.exists(a2).booleanValue()) {
                this.a.remove(a2);
            }
            this.a.putObject(a2, obj, bool);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    public Boolean saveStringObject(String str, String str2) {
        try {
            removeStringObject(str);
            this.a.putString(str, str2);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    public <T> T loadObject(Class<T> cls) {
        return this.a.getObject(a(cls), cls);
    }

    public <T> T loadObject(Class cls, Class<T> cls2) {
        return this.a.getObject(a(cls, cls2), cls2);
    }

    public String loadStringObject(String str) {
        return this.a.getString(str, "");
    }

    public <T> void removeObject(Class<T> cls) {
        this.a.remove(a(cls));
    }

    public <T> void removeObject(Class cls, Class<T> cls2) {
        this.a.remove(a(cls, cls2));
    }

    public <T> void removeStringObject(String str) {
        this.a.remove(str);
    }

    public String getKeyUniqueID() {
        return this.a.getString("KEY_UNIQUE_ID", "A000000000000000");
    }

    public void setKeyUniqueID(String str) {
        this.a.putString("KEY_UNIQUE_ID", str);
    }
}
