package ar.com.santander.rio.mbanking.app.commons;

import android.text.TextUtils;
import android.util.Patterns;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectDestinatarios;
import ar.com.santander.rio.mbanking.app.ui.Model.ObjectEnvioDineroOperacionesRealizadas;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.BodyRequest.TipoOperacion;
import ar.com.santander.rio.mbanking.app.ui.constants.EnvioConstants.Estado;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AMAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BAgendadosEnvEfeDestinatarioBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaAgendadosEnvEfeListaDestinatariosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaMandatosExtEnvListaMandatosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvFiltroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ExtEnvLeyendaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListTableBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MandatoBean;
import ar.com.santander.rio.mbanking.utils.UtilString;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CExtEnv {
    public static List<ExtEnvCuentaBean> getCuentas(SessionManager sessionManager) {
        return sessionManager.getDatosInicialesExtExv().listaCuentas.cuenta;
    }

    public static Boolean getTyC(SessionManager sessionManager) {
        return Boolean.valueOf(!sessionManager.getDatosInicialesExtExv().aceptaTyC.equalsIgnoreCase("N"));
    }

    public static String getDescripcionByEstado(SessionManager sessionManager, String str) {
        for (ListGroupBean listGroupBean : CConsDescripciones.getConsDescripcionEXTENV_ESTADO(sessionManager).listGroupBeans) {
            if (listGroupBean.code.equalsIgnoreCase(str)) {
                return listGroupBean.getLabel();
            }
        }
        return null;
    }

    public static String getFiltroDias1(SessionManager sessionManager) {
        return sessionManager.getDatosInicialesExtExv().filtrosMandatos.filtroCantDias1;
    }

    public static String getFiltroDias2(SessionManager sessionManager) {
        return sessionManager.getDatosInicialesExtExv().filtrosMandatos.filtroCantDias2;
    }

    public static String getFiltroDefault(SessionManager sessionManager) {
        return sessionManager.getDatosInicialesExtExv().filtrosMandatos.filtroDefault;
    }

    public static String[] getTiposDocumento(SessionManager sessionManager) {
        ListTableBean consDescripcionENV_TIPODOC = CConsDescripciones.getConsDescripcionENV_TIPODOC(sessionManager);
        ArrayList arrayList = new ArrayList();
        for (ListGroupBean label : consDescripcionENV_TIPODOC.listGroupBeans) {
            arrayList.add(label.getLabel());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String getCodigoTipoDocumento(SessionManager sessionManager, String str) {
        for (ListGroupBean listGroupBean : CConsDescripciones.getConsDescripcionENV_TIPODOC(sessionManager).listGroupBeans) {
            if (listGroupBean.getLabel().equals(str)) {
                return listGroupBean.code;
            }
        }
        return null;
    }

    public static String convertCodigoTipoDocumentoBanelco(String str) {
        return str.equals("X") ? "N" : str;
    }

    public static String getDescripcionTipoDocumento(SessionManager sessionManager, String str) {
        for (ListGroupBean listGroupBean : CConsDescripciones.getConsDescripcionENV_TIPODOC(sessionManager).listGroupBeans) {
            if (listGroupBean.code.equals(str)) {
                return listGroupBean.getLabel();
            }
        }
        return null;
    }

    public static String[] getEstadosMandato(SessionManager sessionManager) {
        ListTableBean consDescripcionEXTENV_ESTADO = CConsDescripciones.getConsDescripcionEXTENV_ESTADO(sessionManager);
        ArrayList arrayList = new ArrayList();
        for (ListGroupBean label : consDescripcionEXTENV_ESTADO.listGroupBeans) {
            arrayList.add(label.getLabel());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] getEstadosVisiblesMandato(SessionManager sessionManager) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getDescripcionByEstado(sessionManager, Estado.Cobrado));
        arrayList.add(getDescripcionByEstado(sessionManager, Estado.Pendiente));
        arrayList.add(getDescripcionByEstado(sessionManager, Estado.Vencido));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String getCodigoEstadoMandato(SessionManager sessionManager, String str) {
        for (ListGroupBean listGroupBean : CConsDescripciones.getConsDescripcionEXTENV_ESTADO(sessionManager).listGroupBeans) {
            if (listGroupBean.getLabel().equals(str)) {
                return listGroupBean.code;
            }
        }
        return null;
    }

    public static String getDescripcionEstadoMandato(SessionManager sessionManager, String str) {
        for (ListGroupBean listGroupBean : CConsDescripciones.getConsDescripcionEXTENV_ESTADO(sessionManager).listGroupBeans) {
            if (listGroupBean.code.equals(str)) {
                return listGroupBean.getLabel();
            }
        }
        return null;
    }

    public static String getTyCTitulo(SessionManager sessionManager) {
        for (ExtEnvLeyendaBean extEnvLeyendaBean : sessionManager.getDatosInicialesExtExv().listaLeyendas.leyenda) {
            if ("EXTENV_TYC".equalsIgnoreCase(extEnvLeyendaBean.idLeyenda)) {
                return extEnvLeyendaBean.titulo;
            }
        }
        return null;
    }

    public static String getTyCDescripcion(SessionManager sessionManager) {
        for (ExtEnvLeyendaBean extEnvLeyendaBean : sessionManager.getDatosInicialesExtExv().listaLeyendas.leyenda) {
            if ("EXTENV_TYC".equalsIgnoreCase(extEnvLeyendaBean.idLeyenda)) {
                return extEnvLeyendaBean.descripcion;
            }
        }
        return null;
    }

    public static String getFuncionalidadTitulo(SessionManager sessionManager) {
        for (ExtEnvLeyendaBean extEnvLeyendaBean : sessionManager.getDatosInicialesExtExv().listaLeyendas.leyenda) {
            if ("EXTENV_FUNC".equalsIgnoreCase(extEnvLeyendaBean.idLeyenda)) {
                return extEnvLeyendaBean.titulo;
            }
        }
        return null;
    }

    public static String getFuncionalidadDescripcion(SessionManager sessionManager) {
        for (ExtEnvLeyendaBean extEnvLeyendaBean : sessionManager.getDatosInicialesExtExv().listaLeyendas.leyenda) {
            if ("EXTENV_FUNC".equalsIgnoreCase(extEnvLeyendaBean.idLeyenda)) {
                return extEnvLeyendaBean.descripcion;
            }
        }
        return null;
    }

    public static String getLimiteTitulo(SessionManager sessionManager) {
        for (ExtEnvLeyendaBean extEnvLeyendaBean : sessionManager.getDatosInicialesExtExv().listaLeyendas.leyenda) {
            if ("EXTENV_LIM".equalsIgnoreCase(extEnvLeyendaBean.idLeyenda)) {
                return extEnvLeyendaBean.titulo;
            }
        }
        return null;
    }

    public static String getLimiteDescripcion(SessionManager sessionManager) {
        for (ExtEnvLeyendaBean extEnvLeyendaBean : sessionManager.getDatosInicialesExtExv().listaLeyendas.leyenda) {
            if ("EXTENV_LIM".equalsIgnoreCase(extEnvLeyendaBean.idLeyenda)) {
                return extEnvLeyendaBean.descripcion;
            }
        }
        return null;
    }

    public static Boolean isEmailValid(String str) {
        return Boolean.valueOf(!TextUtils.isEmpty(str) && Patterns.EMAIL_ADDRESS.matcher(str).matches());
    }

    public static int getLeyendaValidaByEstado(String str, String str2) {
        if (str.equalsIgnoreCase(TipoOperacion.ExtraccionSinTarjeta)) {
            char c = 65535;
            switch (str2.hashCode()) {
                case 65861:
                    if (str2.equals(Estado.Bloqueado)) {
                        c = 2;
                        break;
                    }
                    break;
                case 66480:
                    if (str2.equals(Estado.Cancelado)) {
                        c = 4;
                        break;
                    }
                    break;
                case 66902:
                    if (str2.equals(Estado.Cobrado)) {
                        c = 3;
                        break;
                    }
                    break;
                case 79097:
                    if (str2.equals(Estado.Pendiente)) {
                        c = 1;
                        break;
                    }
                    break;
                case 84863:
                    if (str2.equals(Estado.Vencido)) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return R.string.ID2061_EXTTARJETA_LBL_OPEVALIDA;
                case 1:
                    return R.string.ID2074_EXTTARJETA_LBL_OPEVALIDA;
                case 2:
                    return R.string.ID2105_EXTTARJETA_LBL_OPEVALIDA;
                case 3:
                    return R.string.ID2118_EXTTARJETA_LBL_OPEVALIDA;
                case 4:
                    return R.string.ID2131_EXTTARJETA_LBL_OPEVALIDA;
            }
        } else if (str.equalsIgnoreCase(TipoOperacion.EnvioDinero)) {
            return R.string.ID2075_ENVEFECT_LBL_OPERVAL;
        }
        return R.string.ID2075_ENVEFECT_LBL_OPERVAL;
    }

    public static Integer getValueWithoutFormat(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str.replaceAll("[$,. ]", "")));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static String getFormattedNumericValue(Integer num) {
        return new DecimalFormat("#,##0", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_APP)).format(num);
    }

    public static String getFormattedNumericWithDecimalsValue(Integer num) {
        return new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_APP)).format(num);
    }

    public static String getFormattedValue(Integer num) {
        return String.format("$ %s", new Object[]{getFormattedNumericValue(num)});
    }

    public static ArrayList<ObjectDestinatarios> getDestinatarios(ConsultaAgendadosEnvEfeListaDestinatariosBean consultaAgendadosEnvEfeListaDestinatariosBean) {
        ArrayList<ObjectDestinatarios> arrayList = new ArrayList<>();
        if (consultaAgendadosEnvEfeListaDestinatariosBean != null) {
            for (AMAgendadosEnvEfeDestinatarioBean aMAgendadosEnvEfeDestinatarioBean : consultaAgendadosEnvEfeListaDestinatariosBean.destinatario) {
                arrayList.add(new ObjectDestinatarios(UtilString.capitalize(aMAgendadosEnvEfeDestinatarioBean.nombreDest), aMAgendadosEnvEfeDestinatarioBean.tipoDocDest, aMAgendadosEnvEfeDestinatarioBean.numeroDocDest, aMAgendadosEnvEfeDestinatarioBean.emailDest));
            }
        }
        return arrayList;
    }

    public static void consultaAgendados(IDataManager iDataManager) {
        iDataManager.consultaAgendados();
    }

    public static void bajaAgendado(IDataManager iDataManager, int i, BAgendadosEnvEfeDestinatarioBean bAgendadosEnvEfeDestinatarioBean) {
        iDataManager.BDestinatario(i, bAgendadosEnvEfeDestinatarioBean);
    }

    public static ArrayList<ObjectEnvioDineroOperacionesRealizadas> getMandatos(ConsultaMandatosExtEnvListaMandatosBean consultaMandatosExtEnvListaMandatosBean) {
        ConsultaMandatosExtEnvListaMandatosBean consultaMandatosExtEnvListaMandatosBean2 = consultaMandatosExtEnvListaMandatosBean;
        ArrayList arrayList = new ArrayList();
        if (consultaMandatosExtEnvListaMandatosBean2 != null) {
            Iterator it = consultaMandatosExtEnvListaMandatosBean2.mandato.iterator();
            while (it.hasNext()) {
                MandatoBean mandatoBean = (MandatoBean) it.next();
                String str = mandatoBean.mandatario.descCtaDebito;
                String str2 = mandatoBean.mandatario.tipoCuenta;
                String str3 = mandatoBean.mandatario.sucursalCuenta;
                String str4 = mandatoBean.mandatario.numeroCuenta;
                String str5 = mandatoBean.beneficiario.nombreDest;
                String str6 = mandatoBean.beneficiario.emailDest;
                String str7 = mandatoBean.beneficiario.tipoDocumento;
                String str8 = mandatoBean.beneficiario.numeroDocumento;
                String str9 = mandatoBean.detalle.importe;
                String str10 = mandatoBean.detalle.fechaAlta;
                String str11 = mandatoBean.detalle.fechaVencimiento;
                String str12 = mandatoBean.detalle.estado;
                Iterator it2 = it;
                String str13 = mandatoBean.detalle.codExtraccion;
                String str14 = str12;
                ArrayList arrayList2 = arrayList;
                ObjectEnvioDineroOperacionesRealizadas objectEnvioDineroOperacionesRealizadas = r3;
                String str15 = str14;
                String str16 = str13;
                ObjectEnvioDineroOperacionesRealizadas objectEnvioDineroOperacionesRealizadas2 = new ObjectEnvioDineroOperacionesRealizadas(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str15, str16, mandatoBean.detalle.idMandato, mandatoBean.detalle.numComprobante);
                ArrayList arrayList3 = arrayList2;
                arrayList3.add(objectEnvioDineroOperacionesRealizadas);
                arrayList = arrayList3;
                it = it2;
            }
        }
        return arrayList;
    }

    public static void consultaMandatosEnv(IDataManager iDataManager, String str) {
        consultaMandatos(iDataManager, TipoOperacion.EnvioDinero, str);
    }

    public static void consultaMandatosExt(IDataManager iDataManager, String str) {
        consultaMandatos(iDataManager, TipoOperacion.ExtraccionSinTarjeta, str);
    }

    public static void consultaMandatos(IDataManager iDataManager, String str, String str2) {
        consultaMandatos(iDataManager, str, str2, null);
    }

    public static void consultaMandatos(IDataManager iDataManager, String str, String str2, ExtEnvFiltroBean extEnvFiltroBean) {
        iDataManager.consultaMandatos(new ConsultaMandatosExtEnvBodyRequestBean(str, str2, extEnvFiltroBean));
    }
}
