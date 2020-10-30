package ar.com.santander.rio.mbanking.managers.session;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.commons.CAccounts;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants.ClaseTarjeta;
import ar.com.santander.rio.mbanking.components.RatingBarDialog;
import ar.com.santander.rio.mbanking.managers.preferences.PreferenceKeys.UserInfo;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.services.events.CheckVersionEvent;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.services.model.general.Filtro;
import ar.com.santander.rio.mbanking.services.model.general.Prestamo;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AgendadosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CargaDatosInicialesExtEnvBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CnsDeudaPTBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsDescriptionBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultarAdhesionVendedorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaVendedor;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetClasificadoresBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetOcupacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPromocionesHomeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimitesYDisponiblesTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TarjetaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UltimoResumenTCBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.environments.ManagerTypeEnvironment;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.LatLng;
import com.jmperezra.accordion_multilevel.items.ItemState;
import com.rsa.mobilesdk.sdk.MobileAPI;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

public class SessionManager {
    private static MobileAPI a;
    public static LatLng location;
    public String aceptaTyCRescatarFondo = "";
    private LinkedHashMap<String, Filtro> b;
    private SettingsManager c;
    public CnsDeudaPTBodyResponseBean cnsDeuda;
    public ConsDescriptionBodyResponseBean consDescripciones;
    private CheckVersionEvent d;
    private Boolean e = Boolean.valueOf(false);
    private boolean f = false;
    private boolean g = true;
    private Context h;
    private boolean i = false;
    public String importe = "";
    private boolean j = false;
    private List<ItemState> k;
    private List<ItemState> l;
    private List<ItemState> m;
    private List<ItemState> n;
    private List<ItemState> o;
    private List<ItemState> p;
    public String tipoCuenta = "";

    public SessionManager(Context context, SettingsManager settingsManager) {
        this.h = context;
        this.c = settingsManager;
    }

    private static void a(Activity activity) {
        a = MobileAPI.getInstance(activity);
        a.initSDK(a());
    }

    public static String getRSAModuleData(Activity activity) {
        String str;
        try {
            a(activity);
            str = a.collectInfo();
            try {
                a.destroy();
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            str = null;
            Log.e("RSA_COLLECT_DATA", "Collection error");
            return str;
        }
        return str;
    }

    private static Properties a() {
        Properties properties = new Properties();
        properties.setProperty(MobileAPI.CONFIGURATION_KEY, "2");
        properties.setProperty(MobileAPI.TIMEOUT_MINUTES_KEY, "2");
        properties.setProperty(MobileAPI.BEST_LOCATION_AGE_MINUTES_KEY, "3");
        properties.setProperty(MobileAPI.MAX_LOCATION_AGE_DAYS_KEY, "2");
        properties.setProperty(MobileAPI.ADD_TIMESTAMP_KEY, "1");
        properties.setProperty(MobileAPI.MAX_ACCURACY_KEY, "50");
        return properties;
    }

    public void setToken(String str) {
        this.c.saveStringObject("TOKEN", str);
    }

    public String getToken() {
        return this.c.loadStringObject("TOKEN");
    }

    public static LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng latLng) {
        location = latLng;
    }

    public List<ItemState> getFiltrosSucursales() {
        return this.m;
    }

    public void setFiltrosSucursales(List<ItemState> list) {
        this.m = list;
    }

    public String getMenuAction() {
        return this.c.loadStringObject("isban.rio.menu_action");
    }

    public boolean isValidateDisplayOK() {
        return this.f;
    }

    private void b() {
        this.f = false;
    }

    public void setValidateDisplayOK(boolean z) {
        this.f = z;
    }

    public void setMenuAction(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.c.saveStringObject("isban.rio.menu_action", str);
        } else {
            this.c.removeStringObject("isban.rio.menu_action");
        }
    }

    public GetClasificadoresBodyResponseBean getGetClasificadores() {
        return (GetClasificadoresBodyResponseBean) this.c.loadObject(GetClasificadoresBodyResponseBean.class);
    }

    public void setGetClasificadores(GetClasificadoresBodyResponseBean getClasificadoresBodyResponseBean) {
        if (getClasificadoresBodyResponseBean != null) {
            this.c.saveObject(getClasificadoresBodyResponseBean);
        } else {
            this.c.removeObject(GetClasificadoresBodyResponseBean.class);
        }
    }

    public CheckVersionEvent getCheckVersionEvent() {
        return this.d;
    }

    public void setCheckVersionEvent(CheckVersionEvent checkVersionEvent) {
        this.d = checkVersionEvent;
    }

    public void setCheckVersion(boolean z) {
        this.c.putBoolean(SettingsManager.CHECK_VERSION, z);
    }

    public boolean getCheckVersion() {
        return this.c.getBoolean(SettingsManager.CHECK_VERSION);
    }

    public Boolean getCheckVersionWarningRaised() {
        return this.e;
    }

    public void setCheckVersionWarningRaised(Boolean bool) {
        this.e = bool;
    }

    public ConsDescriptionBodyResponseBean getConsDescripciones() {
        return (ConsDescriptionBodyResponseBean) this.c.loadObject(ConsDescriptionBodyResponseBean.class);
    }

    public void setConsDescripciones(ConsDescriptionBodyResponseBean consDescriptionBodyResponseBean) {
        if (consDescriptionBodyResponseBean != null) {
            this.c.saveObject(consDescriptionBodyResponseBean);
        } else {
            this.c.removeObject(ConsDescriptionBodyResponseBean.class);
        }
    }

    public GetPromocionesHomeBodyResponseBean getGetPromocionesHome() {
        return (GetPromocionesHomeBodyResponseBean) this.c.loadObject(GetPromocionesHomeBodyResponseBean.class);
    }

    public void setGetPromocionesHome(GetPromocionesHomeBodyResponseBean getPromocionesHomeBodyResponseBean) {
        if (getPromocionesHomeBodyResponseBean != null) {
            this.c.saveObject(getPromocionesHomeBodyResponseBean);
        } else {
            this.c.removeObject(GetPromocionesHomeBodyResponseBean.class);
        }
    }

    public LoginUnicoBodyResponseBean getLoginUnico() {
        return (LoginUnicoBodyResponseBean) this.c.loadObject(LoginUnicoBodyResponseBean.class);
    }

    public void setLoginUnico(LoginUnicoBodyResponseBean loginUnicoBodyResponseBean) {
        this.c.removeObject(LoginUnicoBodyResponseBean.class);
        setNup(null);
        setSession(null);
        g();
        cleanTransferenciasData();
        h();
        if (loginUnicoBodyResponseBean != null) {
            this.c.saveObject(loginUnicoBodyResponseBean);
        }
        if (loginUnicoBodyResponseBean != null && loginUnicoBodyResponseBean.getSesionUsuario() != null && loginUnicoBodyResponseBean.getDatosPersonales() != null) {
            String format = String.format("%s %s", new Object[]{loginUnicoBodyResponseBean.getDatosPersonales().getNombre(), loginUnicoBodyResponseBean.getDatosPersonales().getApellido()});
            String nup = loginUnicoBodyResponseBean.getDatosPersonales().getNup();
            String sesionUsuario = loginUnicoBodyResponseBean.getSesionUsuario();
            setSession(loginUnicoBodyResponseBean.getSesionUsuario());
            setNup(loginUnicoBodyResponseBean.getDatosPersonales().getNup());
            this.i = true;
            setCustomErrorLogging(format, nup, sesionUsuario);
        }
    }

    public void setCustomErrorLogging(String str, String str2, String str3) {
        Crashlytics.setUserName(str);
        Crashlytics.setUserIdentifier(str2);
        Crashlytics.setString("sessionId", str3);
        Crashlytics.setString("environment", ManagerTypeEnvironment.getCurrentEnvironment(this.h).toString());
    }

    public List<ItemState> getFiltrosCajero() {
        return this.l;
    }

    public void setFiltrosCajero(List<ItemState> list) {
        this.l = list;
    }

    public boolean usuarioPoseeCuentas() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        return (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getCuentas() == null || loginUnico.getProductos().getCuentas().getCuentas() == null) ? false : true;
    }

    public boolean usuarioPoseeCuentasBP() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        return (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getCuentasBP() == null || loginUnico.getProductos().getCuentasBP().getCuentasBP() == null) ? false : true;
    }

    public boolean usuarioTieneCuentas() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        return (loginUnico == null || loginUnico.getProductos() == null || ((loginUnico.getProductos().getCuentas() == null || loginUnico.getProductos().getCuentas().getCuentas() == null) && (loginUnico.getProductos().getCuentasBP() == null || loginUnico.getProductos().getCuentasBP().getCuentasBP() == null))) ? false : true;
    }

    public boolean usuarioTieneCuentasOperativas() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getCuentas() == null || loginUnico.getProductos().getCuentas().getCuentas() == null || loginUnico.getProductos().getCuentas().getCuentas().size() <= 0) {
            return false;
        }
        for (Cuenta isAccountOperational : loginUnico.getProductos().getCuentas().getCuentas()) {
            if (CAccounts.getInstance(this).isAccountOperational(isAccountOperational).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public boolean usuarioTieneCuentasOperativasEnPesos() {
        getLoginUnico();
        List listAccountsUserInCurrencyFromSession = CAccounts.getInstance(this).getListAccountsUserInCurrencyFromSession(Constants.SYMBOL_CURRENCY_PESOS);
        return (listAccountsUserInCurrencyFromSession == null || listAccountsUserInCurrencyFromSession.size() == 0) ? false : true;
    }

    public boolean usuarioTieneCuentasEnPesos() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getCuentas() == null || loginUnico.getProductos().getCuentas().getCuentas() == null || new UtilAccount().getAccountsPesosInfo(this).size() <= 0) {
            return false;
        }
        return true;
    }

    public List<Cuenta> getCuentasEnPesos() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getCuentas() == null || loginUnico.getProductos().getCuentas().getCuentas() == null) {
            return new ArrayList();
        }
        return new UtilAccount().getAccountsPesosInfo(this);
    }

    public boolean usuarioTieneCuentasTitular() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getCuentas() == null || loginUnico.getProductos().getCuentas().getCuentas() == null || loginUnico.getProductos().getCuentas().getCuentas().size() <= 0) {
            return false;
        }
        boolean z = false;
        for (int i2 = 0; i2 < loginUnico.getProductos().getCuentas().getCuentas().size(); i2++) {
            if (((Cuenta) loginUnico.getProductos().getCuentas().getCuentas().get(i2)).getCodigoTitularidad().equals(PagoTarjetasConstants.CREDIT_CARD_CODE_TI)) {
                z = true;
            }
        }
        return z;
    }

    public boolean usuarioTieneTarjetas() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas().size() <= 0) {
            return false;
        }
        return true;
    }

    public boolean usuarioTieneTarjetasCreditoHabilitadas() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas().size() <= 0) {
            return false;
        }
        for (Tarjeta tarjeta : loginUnico.getProductos().getTarjetas().getTarjetas()) {
            if ((tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_VISA) || tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_AMEX)) && !tarjeta.getClase().equalsIgnoreCase("T")) {
                return true;
            }
        }
        return false;
    }

    public boolean usuarioTieneTarjetasDebito() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        return (loginUnico.getProductos().getTarjetasDebito() == null || loginUnico.getProductos().getTarjetasDebito().getTarjetas() == null || loginUnico.getProductos().getTarjetasDebito().getTarjetas().size() <= 0) ? false : true;
    }

    public boolean usuarioTieneTarjetasNoMastercard() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas().size() <= 0) {
            return false;
        }
        for (Tarjeta tipo : loginUnico.getProductos().getTarjetas().getTarjetas()) {
            if (!tipo.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                return true;
            }
        }
        return false;
    }

    public boolean usuarioTieneTarjetasTitularNoRecargables() {
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (loginUnico == null || loginUnico.getProductos() == null || loginUnico.getProductos().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas().size() <= 0) {
            return false;
        }
        for (int i2 = 0; i2 < loginUnico.getProductos().getTarjetas().getTarjetas().size(); i2++) {
            Tarjeta tarjeta = (Tarjeta) loginUnico.getProductos().getTarjetas().getTarjetas().get(i2);
            if (tarjeta.getCodigoTitularidad().equals(PagoTarjetasConstants.CREDIT_CARD_CODE_TI) && !tarjeta.getClase().equals("T")) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<TarjetaBean> getTarjetasAsegurables() {
        ArrayList<TarjetaBean> arrayList = new ArrayList<>();
        LoginUnicoBodyResponseBean loginUnico = getLoginUnico();
        if (!(loginUnico == null || loginUnico.getProductos() == null)) {
            if (!(loginUnico.getProductos().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas() == null || loginUnico.getProductos().getTarjetas().getTarjetas().size() <= 0)) {
                for (int i2 = 0; i2 < loginUnico.getProductos().getTarjetas().getTarjetas().size(); i2++) {
                    Tarjeta tarjeta = (Tarjeta) loginUnico.getProductos().getTarjetas().getTarjetas().get(i2);
                    if (tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_VISA) && !tarjeta.getClase().equalsIgnoreCase("H") && !tarjeta.getClase().equalsIgnoreCase(ClaseTarjeta.PLATINUM) && !tarjeta.getClase().equalsIgnoreCase("P")) {
                        arrayList.add(new TarjetaBean(tarjeta.getTipo(), tarjeta.getNroTarjetaCredito(), tarjeta.getNumero()));
                    } else if (tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_AMEX) && !tarjeta.getClase().equalsIgnoreCase("H") && !tarjeta.getClase().equalsIgnoreCase(ClaseTarjeta.PLATINUM)) {
                        arrayList.add(new TarjetaBean(tarjeta.getTipo(), tarjeta.getNroTarjetaCredito(), tarjeta.getNumero()));
                    }
                }
            }
            if (!(loginUnico.getProductos().getTarjetasDebito() == null || loginUnico.getProductos().getTarjetasDebito().getTarjetas() == null || loginUnico.getProductos().getTarjetasDebito().getTarjetas().size() <= 0)) {
                for (int i3 = 0; i3 < loginUnico.getProductos().getTarjetasDebito().getTarjetas().size(); i3++) {
                    Tarjeta tarjeta2 = (Tarjeta) loginUnico.getProductos().getTarjetasDebito().getTarjetas().get(i3);
                    arrayList.add(new TarjetaBean(tarjeta2.getTipo(), tarjeta2.getNroTarjetaDebito(), tarjeta2.getNumero()));
                }
            }
        }
        return arrayList;
    }

    public LinkedHashMap<String, Filtro> getFiltrosDias() {
        return this.b;
    }

    public void setFiltrosDias(LinkedHashMap<String, Filtro> linkedHashMap) {
        this.b = linkedHashMap;
    }

    public List<ItemState> getFiltrosPromocionesRublos() {
        return this.o;
    }

    public void setFiltrosPromocionesRublos(List<ItemState> list) {
        this.o = list;
    }

    public List<ItemState> getFiltrosPromocionesMPago() {
        return this.p;
    }

    public void setFiltrosPromocionesMPago(List<ItemState> list) {
        this.p = list;
    }

    public CnsDeudaPTBodyResponseBean getCnsDeuda() {
        return (CnsDeudaPTBodyResponseBean) this.c.loadObject(CnsDeudaPTBodyResponseBean.class);
    }

    public void setCnsDeuda(CnsDeudaPTBodyResponseBean cnsDeudaPTBodyResponseBean) {
        if (cnsDeudaPTBodyResponseBean != null) {
            this.c.saveObject(cnsDeudaPTBodyResponseBean);
        } else {
            this.c.removeObject(CnsDeudaPTBodyResponseBean.class);
        }
    }

    public CargaDatosInicialesExtEnvBodyResponseBean getDatosInicialesExtExv() {
        return (CargaDatosInicialesExtEnvBodyResponseBean) this.c.loadObject(CargaDatosInicialesExtEnvBodyResponseBean.class);
    }

    public void setDatosInicialesExtExv(CargaDatosInicialesExtEnvBodyResponseBean cargaDatosInicialesExtEnvBodyResponseBean) {
        if (cargaDatosInicialesExtEnvBodyResponseBean != null) {
            this.c.saveObject(cargaDatosInicialesExtEnvBodyResponseBean);
        } else {
            this.c.removeObject(CargaDatosInicialesExtEnvBodyResponseBean.class);
        }
    }

    public UltimoResumenTCBodyResponseBean getUltimoResumenTC() {
        return (UltimoResumenTCBodyResponseBean) this.c.loadObject(UltimoResumenTCBodyResponseBean.class);
    }

    public void setUltimoResumenTC(UltimoResumenTCBodyResponseBean ultimoResumenTCBodyResponseBean) {
        if (ultimoResumenTCBodyResponseBean != null) {
            this.c.saveObject(ultimoResumenTCBodyResponseBean);
        } else {
            this.c.removeObject(UltimoResumenTCBodyResponseBean.class);
        }
    }

    public LimitesYDisponiblesTCBodyResponseBean getLimitesYDisponiblesTC() {
        return (LimitesYDisponiblesTCBodyResponseBean) this.c.loadObject(LimitesYDisponiblesTCBodyResponseBean.class);
    }

    public void setLimitesYDisponiblesTC(LimitesYDisponiblesTCBodyResponseBean limitesYDisponiblesTCBodyResponseBean) {
        if (limitesYDisponiblesTCBodyResponseBean != null) {
            this.c.saveObject(limitesYDisponiblesTCBodyResponseBean);
        } else {
            this.c.removeObject(LimitesYDisponiblesTCBodyResponseBean.class);
        }
    }

    public Tarjeta getSelected_tarjeta() {
        return (Tarjeta) this.c.loadObject(Tarjeta.class);
    }

    public void setSelected_tarjeta(Tarjeta tarjeta) {
        if (tarjeta != null) {
            this.c.saveObject(tarjeta);
        } else {
            this.c.removeObject(Tarjeta.class);
        }
    }

    public String getTipoCuenta() {
        return this.c.loadStringObject("KEY_TIPO_CUENTA");
    }

    public void setTipoCuenta(String str) {
        this.c.saveStringObject("KEY_TIPO_CUENTA", str);
    }

    public String getDestinatario() {
        return this.c.loadStringObject("KEY_DESTINATARIO");
    }

    public void setDestinatario(String str) {
        this.c.saveStringObject("KEY_DESTINATARIO", str);
    }

    public String getCuentaOrigenDebito() {
        return this.c.loadStringObject("KEY_CUENTA_ORIGEN_DEBITO");
    }

    public void setCuentaOrigenDebito(String str) {
        this.c.saveStringObject("KEY_CUENTA_ORIGEN_DEBITO", str);
    }

    public String getCuentaOrigenDestino() {
        return this.c.loadStringObject("KEY_CUENTA_ORIGEN_DESTINO");
    }

    public void setCuentaOrigenDestino(String str) {
        this.c.saveStringObject("KEY_CUENTA_ORIGEN_DESTINO", str);
    }

    public PushNotification getPendingPushNotification() {
        return (PushNotification) this.c.loadObject(PushNotification.class);
    }

    public void setPendingPushNotification(PushNotification pushNotification) {
        if (pushNotification != null) {
            this.c.saveObject(pushNotification);
        } else {
            this.c.removeObject(PushNotification.class);
        }
    }

    public String getNup() {
        return this.c.loadStringObject("isban.rio.nup");
    }

    public void setNup(String str) {
        if (str != null) {
            this.c.saveStringObject("isban.rio.nup", str);
        } else {
            this.c.removeStringObject("isban.rio.nup");
        }
    }

    public String getSession() {
        return this.c.loadStringObject("isban.rio.session");
    }

    public void setSession(String str) {
        if (str != null) {
            this.c.saveStringObject("isban.rio.session", str);
        } else {
            this.c.removeStringObject("isban.rio.session");
        }
    }

    public List<ItemState> getFiltrosPromociones() {
        return this.n;
    }

    public void setFiltrosPromociones(List<ItemState> list) {
        this.n = list;
    }

    public List<ItemState> getFiltrosTipoCajero() {
        return this.k;
    }

    public void setFiltrosTipoCajero(List<ItemState> list) {
        this.k = list;
    }

    public boolean getFlagMustShowPopUp() {
        return this.i;
    }

    public void setFlagMustShowPopUp(boolean z) {
        this.i = z;
    }

    public boolean isShowLeyenda() {
        return this.g;
    }

    public void setShowLeyenda(boolean z) {
        this.g = z;
    }

    public GetOcupacionesBodyResponseBean getListaOcupaciones() {
        return (GetOcupacionesBodyResponseBean) this.c.loadObject(GetOcupacionesBodyResponseBean.class);
    }

    public void setListaOcupaciones(GetOcupacionesBodyResponseBean getOcupacionesBodyResponseBean) {
        if (getOcupacionesBodyResponseBean != null) {
            this.c.saveObject(getOcupacionesBodyResponseBean);
        } else {
            this.c.removeObject(GetOcupacionesBodyResponseBean.class);
        }
    }

    public List<CuentaVendedor> getCuentasDEBINAdheridas() {
        ArrayList arrayList = new ArrayList();
        if (!(getConsultarAdhesionVendedorBodyResponseBean().getListaCuentasVendedorBean() == null || getConsultarAdhesionVendedorBodyResponseBean().getListaCuentasVendedorBean().getCuentaVendedor() == null)) {
            for (CuentaVendedor cuentaVendedor : getConsultarAdhesionVendedorBodyResponseBean().getListaCuentasVendedorBean().getCuentaVendedor()) {
                if (cuentaVendedor.getStatusAdhesion().intValue() == 0) {
                    arrayList.add(cuentaVendedor);
                }
            }
        }
        return arrayList;
    }

    public ConsultarAdhesionVendedorBodyResponseBean getConsultarAdhesionVendedorBodyResponseBean() {
        return (ConsultarAdhesionVendedorBodyResponseBean) this.c.loadObject(ConsultarAdhesionVendedorBodyResponseBean.class);
    }

    public void setConsultarAdhesionVendedorBodyResponseBean(ConsultarAdhesionVendedorBodyResponseBean consultarAdhesionVendedorBodyResponseBean) {
        if (consultarAdhesionVendedorBodyResponseBean != null) {
            this.c.saveObject(consultarAdhesionVendedorBodyResponseBean);
        } else {
            this.c.removeObject(ConsultarAdhesionVendedorBodyResponseBean.class);
        }
    }

    public AgendadosBean getAgendaTransferencia() {
        return (AgendadosBean) this.c.loadObject(AgendadosBean.class);
    }

    public DatosPersonales saveLoginPublic() {
        DatosPersonales datosPersonales = getLoginUnico().getDatosPersonales();
        savePersonalDate(datosPersonales);
        return datosPersonales;
    }

    public void savePersonalDate(DatosPersonales datosPersonales) {
        if (datosPersonales != null && !TextUtils.isEmpty(datosPersonales.getNroDocumento()) && !TextUtils.isEmpty(datosPersonales.getFechaNacimiento()) && !TextUtils.isEmpty(datosPersonales.getNup())) {
            this.c.saveStringObject(UserInfo.DOCUMENT, datosPersonales.getNroDocumento());
            this.c.saveStringObject(UserInfo.DATE, datosPersonales.getFechaNacimiento());
            this.c.saveStringObject(UserInfo.NUP, datosPersonales.getNup());
        }
    }

    public DatosPersonales getPersonalDate() {
        String loadStringObject = this.c.loadStringObject(UserInfo.DOCUMENT);
        String loadStringObject2 = this.c.loadStringObject(UserInfo.DATE);
        String loadStringObject3 = this.c.loadStringObject(UserInfo.NUP);
        if (TextUtils.isEmpty(loadStringObject) || TextUtils.isEmpty(loadStringObject2) || TextUtils.isEmpty(loadStringObject3)) {
            return null;
        }
        return new DatosPersonales(loadStringObject3, loadStringObject, loadStringObject2);
    }

    public void setAgendaTransferencia(AgendadosBean agendadosBean) {
        if (agendadosBean != null) {
            this.c.saveObject(agendadosBean);
        } else {
            this.c.removeObject(AgendadosBean.class);
        }
    }

    public String getCustomURLNavFragment() {
        return this.c.loadStringObject("KEY_CUSTOM_URL_NAV_FRAGMENT");
    }

    public void deepLinkSetUp(String str) {
        this.j = true;
        setCustomURLNavFragment(str);
    }

    public void setCustomURLNavFragment(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.c.saveStringObject("KEY_CUSTOM_URL_NAV_FRAGMENT", str);
        } else {
            this.c.removeStringObject("KEY_CUSTOM_URL_NAV_FRAGMENT");
        }
    }

    public void cleanAllSessionData() {
        c();
        cleanPrivateData();
    }

    private void c() {
        setConsDescripciones(null);
        setGetPromocionesHome(null);
        setCustomURLNavFragment("");
        d();
    }

    private void d() {
        setLocation(null);
        setFiltrosSucursales(null);
        setFiltrosCajero(null);
        setFiltrosDias(null);
        setFiltrosTipoCajero(null);
        setFiltrosPromociones(null);
        setFiltrosPromocionesRublos(null);
        setFiltrosPromocionesMPago(null);
    }

    public void cleanPrivateData() {
        e();
        g();
        cleanTransferenciasData();
        h();
        i();
        j();
        b();
        setMenuAction("");
    }

    private void e() {
        setLoginUnico(null);
        setNup(null);
        setSession(null);
        f();
    }

    private void f() {
        RatingBarDialog.setFlagShowPopUpCalificacion(true);
    }

    private void g() {
        setCnsDeuda(null);
        setUltimoResumenTC(null);
        setLimitesYDisponiblesTC(null);
        setSelected_tarjeta(null);
    }

    public void cleanTransferenciasData() {
        setTipoCuenta("");
        setDestinatario("");
        setCuentaOrigenDebito("");
        setCuentaOrigenDestino("");
    }

    /* renamed from: usuarioTieneCréditos reason: contains not printable characters */
    public boolean m3usuarioTieneCrditos() {
        List arrayList = (getLoginUnico() == null || getLoginUnico().getProductos() == null || getLoginUnico().getProductos().getPrestamos() == null) ? new ArrayList() : getLoginUnico().getProductos().getPrestamos().getPrestamos();
        if (arrayList == null) {
            return false;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (((Prestamo) arrayList.get(i2)).getTipo() != null && (((Prestamo) arrayList.get(i2)).getTipo().equals("30") || ((Prestamo) arrayList.get(i2)).getTipo().equals("31"))) {
                return true;
            }
        }
        return false;
    }

    private void h() {
        setDatosInicialesExtExv(null);
    }

    private void i() {
        setListaOcupaciones(null);
    }

    private void j() {
        setConsultarAdhesionVendedorBodyResponseBean(null);
    }

    public ListTableBean getTableByID(String str) {
        ListTableBean listTableBean = new ListTableBean();
        for (ListTableBean listTableBean2 : getConsDescripciones().getListTableBeans()) {
            if (listTableBean2.getIdTable().equals(str)) {
                return listTableBean2;
            }
        }
        return listTableBean;
    }

    public boolean isDeeplinkFlown() {
        return this.j;
    }

    public void setDeeplinkFlown(boolean z) {
        this.j = z;
    }

    public void cleanDeeplinkFlown() {
        this.j = false;
    }
}
