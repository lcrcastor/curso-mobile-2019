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

public class DetalleDePreAutorizacionFragmentButtomDesvincular extends Fragment implements OnClickListener {
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
    private TextView ao;
    private Button ap;
    private LinearLayout aq;
    private DetallePreAutorizacionBean b;
    private SessionManager c;
    private SettingsManager d;
    private List<ListGroupBean> e;
    private List<ListGroupBean> f;
    private List<ListGroupBean> g;
    private List<ListGroupBean> h;
    private List<ListGroupBean> i;
    public OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void desvincularPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean);
    }

    public static DetalleDePreAutorizacionFragmentButtomDesvincular newInstance(SessionManager sessionManager, SettingsManager settingsManager) {
        DetalleDePreAutorizacionFragmentButtomDesvincular detalleDePreAutorizacionFragmentButtomDesvincular = new DetalleDePreAutorizacionFragmentButtomDesvincular();
        detalleDePreAutorizacionFragmentButtomDesvincular.setArguments(new Bundle());
        detalleDePreAutorizacionFragmentButtomDesvincular.c = sessionManager;
        detalleDePreAutorizacionFragmentButtomDesvincular.d = settingsManager;
        return detalleDePreAutorizacionFragmentButtomDesvincular;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        z();
        this.a = (GetDetallePreAutorizacionCompradorBodyResponseBean) arguments.getParcelable("DETALLE_PREAUTORIZACION_ARG");
        this.b = this.a.getPreautorizacion();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_debin_detalle_pre_autorizacion_recibida_boton_desvincular, viewGroup, false);
        b(inflate);
        y();
        return inflate;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetalleDePreAutorizacionFragmentButtomAceptarRechazar.OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) activity;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(activity.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ID_4435_DEBIN_DESVINCULAR) {
            this.mListener.desvincularPreAutorizacion(this.b);
        }
    }

    private void b(View view) {
        this.ad = (TextView) view.findViewById(R.id.amountViewImporteMaximoData);
        this.ae = (TextView) view.findViewById(R.id.textViewDetallePreAutorizacionRecibidaCUIT);
        this.af = (TextView) view.findViewById(R.id.textViewCuentaDebitoData);
        this.ag = (TextView) view.findViewById(R.id.textViewEstadoData);
        this.ah = (TextView) view.findViewById(R.id.textViewPeriodoData);
        this.ai = (TextView) view.findViewById(R.id.textViewImporteMaximoPorPeriodoData);
        this.aj = (TextView) view.findViewById(R.id.textViewImporteMaximoPorDebinData);
        this.ak = (TextView) view.findViewById(R.id.textViewCantidadMaximaDeDebinPorPeriodoData);
        this.al = (TextView) view.findViewById(R.id.textViewConceptoData);
        this.am = (TextView) view.findViewById(R.id.textViewDescripcionData);
        this.an = (TextView) view.findViewById(R.id.textViewIDSolicitudData);
        this.ao = (TextView) view.findViewById(R.id.textViewLeyendaPreautorizacionData);
        this.ap = (Button) view.findViewById(R.id.ID_4435_DEBIN_DESVINCULAR);
        this.aq = (LinearLayout) view.findViewById(R.id.F32_02_row_botones);
    }

    private void y() {
        String sucursal = this.b.getComprador().getCuentaCompradorBean().getSucursal();
        String montoPeriodo = this.b.getMontoPeriodo();
        String montoPorDebin = this.b.getMontoPorDebin();
        String labelIntableList = PreAutorizacionesDebinUtil.getLabelIntableList(this.b.getCodConcepto(), this.f);
        String detalle = this.b.getDetalle();
        String idPreautorizacion = this.b.getIdPreautorizacion();
        String cuit = this.b.getVendedor().getCuit();
        String labelIntableList2 = PreAutorizacionesDebinUtil.getLabelIntableList(this.b.getCodEstado(), this.e);
        String numero = this.b.getComprador().getCuentaCompradorBean().getNumero();
        String tipo = this.b.getComprador().getCuentaCompradorBean().getTipo();
        String descIntableList = PreAutorizacionesDebinUtil.getDescIntableList(this.b.getPeriodo(), this.h);
        String descIntableList2 = PreAutorizacionesDebinUtil.getDescIntableList(this.b.getMoneda(), this.i);
        String cantidad = this.b.getCantidad();
        TextViewUtils.setTextValue(this.ad, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.ae, cuit);
        TextViewUtils.setTextValue(this.af, UtilsCuentas.formatNumeroCuenta(PreAutorizacionesDebinUtil.getDescIntableList(tipo, this.g), sucursal, numero));
        TextViewUtils.setTextValue(this.ag, labelIntableList2);
        TextViewUtils.setTextValue(this.ah, descIntableList);
        TextViewUtils.setTextValue(this.ai, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.aj, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPorDebin));
        TextViewUtils.setTextValue(this.ak, cantidad);
        TextViewUtils.setTextValue(this.al, labelIntableList);
        TextViewUtils.setTextValue(this.am, Html.fromHtml(detalle).toString());
        TextViewUtils.setTextValue(this.an, idPreautorizacion);
        TextViewUtils.setTextValue(this.ao, Html.fromHtml(this.a.getLeyendaPreautorizacion()).toString());
        try {
            this.ad.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.ad.getText().toString()));
            this.ae.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.ae.getText().toString()));
            this.af.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.af.getText().toString()));
            this.ai.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.ai.getText().toString()));
            this.aj.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.aj.getText().toString()));
            this.an.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.an.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.ap.setOnClickListener(this);
        this.ap.setVisibility(8);
        this.aq.setVisibility(8);
        if (this.b.getMostrarDesvincular().equals(STATUS_FLAGS.POSITIVE)) {
            this.ap.setVisibility(0);
            this.aq.setVisibility(0);
        }
    }

    private void z() {
        this.e = this.c.getTableByID(PRE_AUTORIZACIONES.ESTREPEAUT).getListGroupBeans();
        this.f = this.c.getTableByID(PRE_AUTORIZACIONES.CONDEBIN).getListGroupBeans();
        this.i = this.c.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
        this.g = this.c.getTableByID(PRE_AUTORIZACIONES.TPOCTACORTA).getListGroupBeans();
        this.h = this.c.getTableByID(PRE_AUTORIZACIONES.PERIODOPREAUT).getListGroupBeans();
        this.i = this.c.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
    }
}
