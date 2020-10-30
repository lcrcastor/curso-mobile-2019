package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.STATUS_FLAGS;
import ar.com.santander.rio.mbanking.app.ui.utils.PreAutorizacionesDebinUtil;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetallePreAutorizacionCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.itrsa.TextViewUtils;
import java.util.List;

public class DetalleDePreAutorizacionFragmentButtomAceptarRechazar extends Fragment implements OnClickListener {
    private GetDetallePreAutorizacionCompradorBodyResponseBean a;
    private TextView ad;
    private TextView ae;
    private TextView af;
    private TextView ag;
    private TextView ah;
    private TextView ai;
    private TextView aj;
    private TextView ak;
    private TextView al;
    private TextView am;
    private TextView an;
    private LinearLayout ao;
    private Button ap;
    private Button aq;

    /* renamed from: ar reason: collision with root package name */
    private SettingsManager f239ar;
    private SessionManager as;
    private TextView at;
    private TextView au;
    private RelativeLayout av;
    private DetallePreAutorizacionBean b;
    private OnFragmentInteractionListener c;
    private List<ListGroupBean> d;
    private List<ListGroupBean> e;
    private List<ListGroupBean> f;
    private List<ListGroupBean> g;
    private List<ListGroupBean> h;
    private TextView i;

    public interface OnFragmentInteractionListener {
        void pagarPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean);

        void rechazarPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean);
    }

    public static DetalleDePreAutorizacionFragmentButtomAceptarRechazar newInstance(SessionManager sessionManager, SettingsManager settingsManager) {
        DetalleDePreAutorizacionFragmentButtomAceptarRechazar detalleDePreAutorizacionFragmentButtomAceptarRechazar = new DetalleDePreAutorizacionFragmentButtomAceptarRechazar();
        detalleDePreAutorizacionFragmentButtomAceptarRechazar.setArguments(new Bundle());
        detalleDePreAutorizacionFragmentButtomAceptarRechazar.as = sessionManager;
        detalleDePreAutorizacionFragmentButtomAceptarRechazar.f239ar = settingsManager;
        return detalleDePreAutorizacionFragmentButtomAceptarRechazar;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        A();
        this.a = (GetDetallePreAutorizacionCompradorBodyResponseBean) arguments.getParcelable("DETALLE_PREAUTORIZACION_ARG");
        this.b = this.a.getPreautorizacion();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_debin_detalle_pre_autorizacion_recibida_buttom_aceptar_rechazar, viewGroup, false);
        b(inflate);
        y();
        z();
        return inflate;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            this.c = (OnFragmentInteractionListener) activity;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(activity.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.c = null;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.F32_02_BTN_PAGAR_DEBIN /*2131363731*/:
                this.c.pagarPreAutorizacion(this.b);
                return;
            case R.id.F32_02_BTN_RECHAZAR_DEBIN /*2131363732*/:
                this.c.rechazarPreAutorizacion(this.b);
                return;
            default:
                return;
        }
    }

    private void b(View view) {
        this.i = (TextView) view.findViewById(R.id.amountViewImporteMaximoData);
        this.ad = (TextView) view.findViewById(R.id.textViewDetallePreAutorizacionRecibidaCUIT);
        this.ae = (TextView) view.findViewById(R.id.textViewCuentaDebitoData);
        this.af = (TextView) view.findViewById(R.id.textViewEstadoData);
        this.ag = (TextView) view.findViewById(R.id.textViewPeriodoData);
        this.ah = (TextView) view.findViewById(R.id.textViewImporteMaximoPorPeriodoData);
        this.ai = (TextView) view.findViewById(R.id.textViewImporteMaximoPorDebinData);
        this.aj = (TextView) view.findViewById(R.id.textViewCantidadMaximaDeDebinPorPeriodoData);
        this.ak = (TextView) view.findViewById(R.id.textViewConceptoData);
        this.al = (TextView) view.findViewById(R.id.textViewDescripcionData);
        this.am = (TextView) view.findViewById(R.id.textViewIDSolicitudData);
        this.an = (TextView) view.findViewById(R.id.textViewLeyendaPreautorizacionData);
        this.ao = (LinearLayout) view.findViewById(R.id.F32_02_row_botones);
        this.ap = (Button) view.findViewById(R.id.F32_02_BTN_PAGAR_DEBIN);
        this.aq = (Button) view.findViewById(R.id.F32_02_BTN_RECHAZAR_DEBIN);
        this.at = (TextView) view.findViewById(R.id.F32_02_lbl_moneda);
        this.au = (TextView) view.findViewById(R.id.F32_02_lbl_importe);
        this.av = (RelativeLayout) view.findViewById(R.id.rll_comp);
    }

    private void y() {
        String sucursal = this.b.getComprador().getCuentaCompradorBean().getSucursal();
        String montoPeriodo = this.b.getMontoPeriodo();
        String montoPorDebin = this.b.getMontoPorDebin();
        String labelIntableList = PreAutorizacionesDebinUtil.getLabelIntableList(this.b.getCodConcepto(), this.e);
        String detalle = this.b.getDetalle();
        String idPreautorizacion = this.b.getIdPreautorizacion();
        String cuit = this.b.getVendedor().getCuit();
        String labelIntableList2 = PreAutorizacionesDebinUtil.getLabelIntableList(this.b.getCodEstado(), this.d);
        String numero = this.b.getComprador().getCuentaCompradorBean().getNumero();
        String tipo = this.b.getComprador().getCuentaCompradorBean().getTipo();
        String descIntableList = PreAutorizacionesDebinUtil.getDescIntableList(this.b.getPeriodo(), this.g);
        String descIntableList2 = PreAutorizacionesDebinUtil.getDescIntableList(this.b.getMoneda(), this.h);
        String cantidad = this.b.getCantidad();
        TextViewUtils.setTextValue(this.at, descIntableList2);
        TextViewUtils.setTextValue(this.au, montoPeriodo);
        TextViewUtils.setTextValue(this.ad, cuit);
        TextViewUtils.setTextValue(this.ae, UtilsCuentas.formatNumeroCuenta(PreAutorizacionesDebinUtil.getDescIntableList(tipo, this.f), sucursal, numero));
        TextViewUtils.setTextValue(this.af, labelIntableList2);
        TextViewUtils.setTextValue(this.ag, descIntableList);
        TextViewUtils.setTextValue(this.ah, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.ai, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPorDebin));
        TextViewUtils.setTextValue(this.aj, cantidad);
        TextViewUtils.setTextValue(this.ak, labelIntableList);
        TextViewUtils.setTextValue(this.al, Html.fromHtml(detalle).toString());
        TextViewUtils.setTextValue(this.am, idPreautorizacion);
        TextViewUtils.setTextValue(this.an, Html.fromHtml(this.a.getLeyendaPreautorizacion()).toString());
        try {
            this.ad.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.ad.getText().toString()));
            this.av.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.at.getText().toString().concat("").concat(this.au.getText().toString())));
            this.ae.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.ae.getText().toString()));
            this.ah.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.ah.getText().toString()));
            this.ai.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.ai.getText().toString()));
            this.am.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.am.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.ap.setOnClickListener(this);
        this.aq.setOnClickListener(this);
    }

    private void z() {
        this.ap.setVisibility(8);
        this.aq.setVisibility(8);
        this.ao.setVisibility(8);
        if (this.b.getMostrarAceptarRechazar().equals(STATUS_FLAGS.POSITIVE)) {
            this.ap.setVisibility(0);
            this.aq.setVisibility(0);
            this.ao.setVisibility(0);
        }
    }

    private void A() {
        this.d = this.as.getTableByID(PRE_AUTORIZACIONES.ESTREPEAUT).getListGroupBeans();
        this.e = this.as.getTableByID(PRE_AUTORIZACIONES.CONDEBIN).getListGroupBeans();
        this.h = this.as.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
        this.f = this.as.getTableByID(PRE_AUTORIZACIONES.TPOCTACORTA).getListGroupBeans();
        this.g = this.as.getTableByID(PRE_AUTORIZACIONES.PERIODOPREAUT).getListGroupBeans();
        this.h = this.as.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
    }
}
