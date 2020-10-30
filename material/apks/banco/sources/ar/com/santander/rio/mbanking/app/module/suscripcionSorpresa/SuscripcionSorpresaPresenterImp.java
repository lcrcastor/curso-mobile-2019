package ar.com.santander.rio.mbanking.app.module.suscripcionSorpresa;

import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.ui.activities.EditarCelularActivity;
import ar.com.santander.rio.mbanking.services.model.general.Destino;
import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.DestinoVinculado;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.ListaProductos;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.ListaSuscripciones;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.Producto;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.Suscripcion;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ActualizarMensajesMyABodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ConsultaSuscripcionMyAResponeBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.versions.VConsultaSuscripcionMyA;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.view.PhoneSelectorView;
import ar.com.santander.rio.mbanking.view.tables.RowTwoColumnViewStyled;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuscripcionSorpresaPresenterImp implements SuscripcionSorpresaPresenter {
    public static String CLIENTE_ESTADO_TO = "TO";
    public static String DESTINO_CEL = "CEL";
    public static String DESTINO_MAIL = "MAIL";
    public static String LEYENDA_NO_SUSCRITO = "SOR_COS";
    public static String LEYENDA_REQUISITOS = "SOR_REQ_HTML";
    public static String LEYENDA_TERMINOS = "SOR_TYC_HTML";
    public static String NUMERO_PAQUETE = "102";
    public static String NUMERO_PRODUCTO = "15";
    boolean a = false;
    String b;
    String c;
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private String l = "";
    private String m = "";
    private String n = "";
    private String o = "";
    private boolean p;
    private boolean q;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean u;
    private boolean v;
    private boolean w;
    private String x;
    private SuscripcionSorpresaView y;
    private ConsultaSuscripcionMyAResponeBodyResponseBean z;

    public void getResponseActualizarMensajes(ErrorBodyBean errorBodyBean) {
    }

    public SuscripcionSorpresaPresenterImp(SuscripcionSorpresaView suscripcionSorpresaView) {
        this.y = suscripcionSorpresaView;
    }

    public boolean isOnlyMailModified() {
        return this.q;
    }

    public boolean isSuscrito() {
        return this.a;
    }

    public void onPageCreated() {
        this.y.setTitle();
    }

    public void onButtonAction(RowTwoColumnViewStyled rowTwoColumnViewStyled, PhoneSelectorView phoneSelectorView, PhoneSelectorView phoneSelectorView2) {
        this.p = false;
        this.i = rowTwoColumnViewStyled.getContent();
        this.j = phoneSelectorView.getValueCelular();
        this.k = phoneSelectorView.getValueEmpresa();
        this.l = phoneSelectorView.getCodCelular();
        this.m = phoneSelectorView2.getValueCelular();
        this.n = phoneSelectorView2.getValueEmpresa();
        this.o = phoneSelectorView2.getCodCelular();
        a();
        if (!this.i.equalsIgnoreCase(this.d)) {
            this.p = true;
            this.r = true;
            this.q = true;
        }
        if (!this.j.equalsIgnoreCase(this.e)) {
            this.p = true;
            this.s = true;
            this.q = false;
        }
        if (!this.k.equalsIgnoreCase(this.f)) {
            this.p = true;
            this.t = true;
            this.q = false;
        }
        if (!this.m.equalsIgnoreCase(this.g)) {
            this.p = true;
            this.u = true;
            this.q = false;
        }
        if (!this.n.equalsIgnoreCase(this.h)) {
            this.p = true;
            this.v = true;
            this.q = false;
        }
        b();
    }

    private void a() {
        if (this.d == null) {
            this.d = "";
        }
        if (this.e == null) {
            this.e = "";
        }
        if (this.f == null) {
            this.f = "";
        }
        if (this.g == null) {
            this.g = "";
        }
        if (this.h == null) {
            this.h = "";
        }
        if (this.i == null) {
            this.i = "";
        }
        if (this.j == null) {
            this.j = "";
        }
        if (this.k == null) {
            this.k = "";
        }
        if (this.l == null) {
            this.l = "";
        }
        if (this.m == null || this.m.equalsIgnoreCase("Agregar número")) {
            this.m = "";
        }
        if (this.n == null) {
            this.n = "";
        }
        if (this.o == null) {
            this.o = "";
        }
    }

    private void b() {
        if (!this.a) {
            if (!this.p) {
                sendRequestActualizarMensajes(a(this.a, this.p), true);
            } else {
                sendRequestModificarSuscripcion(a(true));
            }
        } else if (!this.p) {
            this.y.showMessageNoDataModified();
        } else {
            sendRequestModificarSuscripcion(a(true));
        }
    }

    private Destinos a(boolean z2) {
        Destinos destinos = new Destinos();
        ArrayList arrayList = new ArrayList();
        if (this.a) {
            if (z2) {
                if (this.r) {
                    arrayList.add(c());
                }
                if (this.s || this.t) {
                    arrayList.add(d());
                }
                if ((this.u || this.v) && this.n != null && this.o != null && !this.n.equalsIgnoreCase("") && !this.o.equalsIgnoreCase("")) {
                    arrayList.add(e());
                }
            } else {
                if (this.s || this.t) {
                    arrayList.add(d());
                }
                if ((this.u || this.v) && ((this.u || this.v) && this.n != null && this.o != null && !this.n.equalsIgnoreCase("") && !this.o.equalsIgnoreCase(""))) {
                    arrayList.add(e());
                }
            }
        } else if (z2) {
            if (this.r) {
                arrayList.add(c());
            }
            if (this.s || this.t) {
                arrayList.add(d());
            }
            if (!this.u && !this.v) {
                arrayList.add(d());
                if (this.n != null && this.o != null && !this.n.equalsIgnoreCase("") && !this.o.equalsIgnoreCase("")) {
                    arrayList.add(e());
                }
            } else if (this.n != null && this.o != null && !this.n.equalsIgnoreCase("") && !this.o.equalsIgnoreCase("")) {
                arrayList.add(e());
            }
        } else {
            arrayList.add(d());
            if (this.n != null && this.o != null && !this.n.equalsIgnoreCase("") && !this.o.equalsIgnoreCase("")) {
                arrayList.add(e());
            }
        }
        destinos.setDestinos(arrayList);
        return destinos;
    }

    public void getResponseTerminosYCondiciones() {
        this.y.verTerminosYCondiciones(c(LEYENDA_TERMINOS));
    }

    public void getResponseRequisitos() {
        this.y.verRequisitos(c(LEYENDA_REQUISITOS));
    }

    public void sendRequestConsultaSuscripcion() {
        this.y.showProgress(VConsultaSuscripcionMyA.nameService);
        this.y.getDataManager().consultaSuscripcionMyA(NUMERO_PRODUCTO, NUMERO_PAQUETE);
    }

    public void getResponseConsultaSuscripcion(ConsultaSuscripcionMyAResponeBodyResponseBean consultaSuscripcionMyAResponeBodyResponseBean) {
        this.z = consultaSuscripcionMyAResponeBodyResponseBean;
        String str = "";
        ListaProductos listaProductos = consultaSuscripcionMyAResponeBodyResponseBean.getListaProductos();
        if (listaProductos != null) {
            ArrayList productos = listaProductos.getProductos();
            if (productos != null) {
                Iterator it = productos.iterator();
                while (it.hasNext()) {
                    Producto producto = (Producto) it.next();
                    if (producto.getNroProducto().equalsIgnoreCase(NUMERO_PRODUCTO)) {
                        ListaSuscripciones listaSuscripciones = producto.getListaSuscripciones();
                        if (listaSuscripciones != null) {
                            Iterator it2 = listaSuscripciones.getListaSuscripciones().iterator();
                            while (it2.hasNext()) {
                                Suscripcion suscripcion = (Suscripcion) it2.next();
                                if (suscripcion.getSuscripto().equalsIgnoreCase("S")) {
                                    this.a = true;
                                } else {
                                    str = c(LEYENDA_NO_SUSCRITO);
                                }
                                this.b = suscripcion.getFrecuenciaDefault();
                                this.c = suscripcion.getDapDefault();
                                this.x = suscripcion.getNroSuscripcion();
                            }
                        }
                    }
                }
            }
            a(consultaSuscripcionMyAResponeBodyResponseBean.getDestinos());
        }
        this.y.setEstadoSuscripcion(this.a, str);
    }

    private void a(Destinos destinos) {
        if (destinos != null) {
            List<Destino> destinos2 = destinos.getDestinos();
            if (destinos2 != null) {
                for (Destino destino : destinos2) {
                    if (destino.getDestinoTipo().equalsIgnoreCase(DESTINO_CEL)) {
                        if (destino.getDestinoSecuencia().equalsIgnoreCase("1")) {
                            this.e = a(destino.getDestinoDescripcion());
                            this.f = b(destino.getDestinoEmpresaCel());
                            this.y.setCelularPrincipal(this.e, this.f, destino.getDestinoEmpresaCel());
                        } else if (destino.getDestinoSecuencia().equalsIgnoreCase("2")) {
                            this.g = a(destino.getDestinoDescripcion());
                            this.h = b(destino.getDestinoEmpresaCel());
                            this.y.setCelularSecundario(this.g, this.h, destino.getDestinoEmpresaCel(), false);
                        }
                    } else if (destino.getDestinoTipo().equalsIgnoreCase(DESTINO_MAIL) && destino.getDestinoSecuencia().equalsIgnoreCase("1")) {
                        this.d = destino.getDestinoDescripcion();
                        this.y.setMail(this.d);
                    }
                }
            }
        }
    }

    private String a(String str) {
        String[] split = str.split("-");
        if (split.length <= 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(EditarCelularActivity.PREFIX_COD_AREA);
        sb.append(split[0]);
        sb.append("-");
        sb.append(EditarCelularActivity.PREFIX_NUMERO);
        sb.append(split[1]);
        return sb.toString();
    }

    public void sendRequestActualizarMensajes(ActualizarMensajesMyABodyRequestBean actualizarMensajesMyABodyRequestBean, boolean z2) {
        if (z2) {
            this.y.showProgress("");
        }
        this.y.getDataManager().actualizarMensajesMyA(actualizarMensajesMyABodyRequestBean);
    }

    public void sendRequestModificarSuscripcion(Destinos destinos) {
        this.y.showProgress("");
        this.y.getDataManager().modificarSuscripcionMyA(destinos);
    }

    public void getResponseModificarSuscripcion(ErrorBodyBean errorBodyBean) {
        if (!this.a && this.p) {
            sendRequestActualizarMensajes(a(!this.a, this.p), false);
        } else if (this.a) {
            sendRequestActualizarMensajes(a(this.a, this.p), false);
        }
    }

    public void terminosAceptados(boolean z2) {
        this.w = z2;
    }

    private String b(String str) {
        List<ListGroupBean> list = CConsDescripciones.getConsDescripcionTPOEMPRCEL(this.y.getSessionManager()).listGroupBeans;
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (ListGroupBean listGroupBean : list) {
                arrayList.add(listGroupBean.getLabel());
                if (listGroupBean.code.equalsIgnoreCase(str)) {
                    return listGroupBean.getLabel();
                }
            }
        }
        return null;
    }

    private ActualizarMensajesMyABodyRequestBean a(boolean z2, boolean z3) {
        List destinos = a(false).getDestinos();
        ActualizarMensajesMyABodyRequestBean actualizarMensajesMyABodyRequestBean = new ActualizarMensajesMyABodyRequestBean();
        ListaSuscripciones listaSuscripciones = new ListaSuscripciones();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (int i3 = 0; i3 < destinos.size(); i3++) {
            Destino destino = (Destino) destinos.get(i3);
            i2++;
            Suscripcion suscripcion = new Suscripcion();
            if (!z2 && !z3) {
                suscripcion.setCodOperacion("A");
            } else if (this.a || !this.p) {
                suscripcion.setCodOperacion("M");
            } else {
                suscripcion.setCodOperacion("A");
            }
            if (this.a) {
                suscripcion.setNroSuscripcion(this.x);
            }
            suscripcion.setNroMensaje("102");
            DestinoVinculado destinoVinculado = new DestinoVinculado();
            destinoVinculado.setDestVincTipo(destino.getDestinoTipo());
            destinoVinculado.setDestVincSecuencia(String.valueOf(i2));
            suscripcion.setDestinoVinculado(destinoVinculado);
            suscripcion.setCodigoFrecuencia(this.b);
            suscripcion.setCodigoDAP(this.c);
            arrayList.add(suscripcion);
        }
        listaSuscripciones.setListaSuscripciones(arrayList);
        actualizarMensajesMyABodyRequestBean.setListaSuscripciones(listaSuscripciones);
        return actualizarMensajesMyABodyRequestBean;
    }

    private String c(String str) {
        ListaLeyendas listaLeyendas = this.z.getListaLeyendas();
        if (listaLeyendas != null) {
            for (Leyenda leyenda : listaLeyendas.lstLeyendas) {
                if (leyenda.idLeyenda.equalsIgnoreCase(str)) {
                    return leyenda.descripcion;
                }
            }
        }
        return null;
    }

    private Destino c() {
        Destino destino = new Destino();
        destino.setDestinoDescripcion(this.i);
        destino.setDestinoTipo(DESTINO_MAIL);
        destino.setDestinoSecuencia("1");
        destino.setCodOperacion("M");
        return destino;
    }

    private Destino d() {
        Destino destino = new Destino();
        destino.setDestinoDescripcion(Utils.getPhoneFormatForService(this.j));
        destino.setDestinoTipo(DESTINO_CEL);
        destino.setCodOperacion("M");
        destino.setDestinoSecuencia("1");
        destino.setDestinoEmpresaCel(this.l);
        return destino;
    }

    private Destino e() {
        Destino destino = new Destino();
        String phoneFormatForService = Utils.getPhoneFormatForService(this.m);
        if (phoneFormatForService.length() == 0) {
            destino.setDestinoDescripcion(Utils.getPhoneFormatForService(this.g));
            destino.setCodOperacion("B");
            destino.setDestinoEmpresaCel(this.h);
        } else {
            destino.setDestinoDescripcion(phoneFormatForService);
            if (this.g.equalsIgnoreCase("")) {
                destino.setCodOperacion("A");
            } else {
                destino.setCodOperacion("M");
            }
            destino.setDestinoEmpresaCel(this.o);
        }
        destino.setDestinoTipo(DESTINO_CEL);
        destino.setDestinoSecuencia("2");
        return destino;
    }
}
