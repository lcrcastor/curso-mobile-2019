package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.utils.PreAutorizacionesDebinUtil;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.itrsa.TextViewUtils;
import java.util.List;

public class ConfirmarAceptarRechazarPreAutorizacionDebinFragment extends Fragment implements OnClickListener {
    private DetallePreAutorizacionBean a;
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
    private TextView ap;
    private Button aq;
    private String b;
    private SettingsManager c;
    private SessionManager d;
    private List<ListGroupBean> e;
    private List<ListGroupBean> f;
    private List<ListGroupBean> g;
    private List<ListGroupBean> h;
    private List<ListGroupBean> i;
    public OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void showDialogConfirmationAbmPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean, String str);
    }

    public static ConfirmarAceptarRechazarPreAutorizacionDebinFragment newInstance(SessionManager sessionManager, SettingsManager settingsManager, DetallePreAutorizacionBean detallePreAutorizacionBean, String str) {
        ConfirmarAceptarRechazarPreAutorizacionDebinFragment confirmarAceptarRechazarPreAutorizacionDebinFragment = new ConfirmarAceptarRechazarPreAutorizacionDebinFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("DETALLE_PRE_AUTORIZACION_ARG", detallePreAutorizacionBean);
        bundle.putString("TIPO_OPERACION_ARG", str);
        confirmarAceptarRechazarPreAutorizacionDebinFragment.setArguments(bundle);
        confirmarAceptarRechazarPreAutorizacionDebinFragment.d = sessionManager;
        confirmarAceptarRechazarPreAutorizacionDebinFragment.c = settingsManager;
        return confirmarAceptarRechazarPreAutorizacionDebinFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.a = (DetallePreAutorizacionBean) getArguments().getParcelable("DETALLE_PRE_AUTORIZACION_ARG");
            this.b = getArguments().getString("TIPO_OPERACION_ARG");
        }
        z();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_confirmar_autorizacion_debin, viewGroup, false);
        b(inflate);
        y();
        return inflate;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
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
    }

    public void onClick(View view) {
        if (view.getId() == R.id.F32_02_BTN_PAGAR_DEBIN) {
            this.mListener.showDialogConfirmationAbmPreAutorizacion(this.a, this.b);
        }
    }

    private void b(View view) {
        this.ao = (TextView) view.findViewById(R.id.textViewTitulo);
        this.ap = (TextView) view.findViewById(R.id.textViewImporteMaximo);
        this.ad = (TextView) view.findViewById(R.id.amountViewImporteMaximoData);
        this.ae = (TextView) view.findViewById(R.id.textViewDetallePreAutorizacionRecibidaCUIT);
        this.af = (TextView) view.findViewById(R.id.textViewCuentaDebitoData);
        this.ag = (TextView) view.findViewById(R.id.textViewEstadoDataConfirmarAutorizacionDebin);
        this.ah = (TextView) view.findViewById(R.id.textViewPeriodoDataConfirmarAutorizacionDebin);
        this.ai = (TextView) view.findViewById(R.id.textViewImporteMaximoPorPeriodoDataConfirmarAutorizacionDebin);
        this.aj = (TextView) view.findViewById(R.id.textViewImporteMaximoPorDebinDataConfirmarAutorizacionDebin);
        this.ak = (TextView) view.findViewById(R.id.textViewCantidadMaximaDeDebinPorPeriodoDataConfirmarAutorizacionDebin);
        this.al = (TextView) view.findViewById(R.id.textViewConceptoDataConfirmarAutorizacionDebin);
        this.am = (TextView) view.findViewById(R.id.textViewDescripcionDataConfirmarAutorizacionDebin);
        this.an = (TextView) view.findViewById(R.id.textViewIDSolicitudDataConfirmarAutorizacionDebin);
        this.aq = (Button) view.findViewById(R.id.F32_02_BTN_PAGAR_DEBIN);
    }

    private void y() {
        if (this.b.equals("A")) {
            this.ao.setText(R.string.ID_4645_DEBIN_TIT_CONFIRMAR_ACEPTACION_PREAUTORIZACION);
        } else if (this.b.equals("R")) {
            this.ao.setText(R.string.ID_4643_DEBIN_TIT_CONFIRMAR_RECHAZO_PREAUTORIZACION);
        } else if (this.b.equals("B")) {
            this.ao.setText(R.string.ID_4645_DEBIN_TIT_CONFIRMAR_ANULACION_PREAUTORIZACION);
        } else if (this.b.equals("D")) {
            this.ao.setText(R.string.ID_4647_DEBIN_TIT_CONFIRMAR_DESCONOCIMIENTO);
        }
        String sucursal = this.a.getComprador().getCuentaCompradorBean().getSucursal();
        String montoPeriodo = this.a.getMontoPeriodo();
        String montoPorDebin = this.a.getMontoPorDebin();
        String labelIntableList = PreAutorizacionesDebinUtil.getLabelIntableList(this.a.getCodConcepto(), this.f);
        String detalle = this.a.getDetalle();
        String idPreautorizacion = this.a.getIdPreautorizacion();
        String cuit = this.a.getVendedor().getCuit();
        String labelIntableList2 = PreAutorizacionesDebinUtil.getLabelIntableList(this.a.getCodEstado(), this.e);
        String numero = this.a.getComprador().getCuentaCompradorBean().getNumero();
        String tipo = this.a.getComprador().getCuentaCompradorBean().getTipo();
        String descIntableList = PreAutorizacionesDebinUtil.getDescIntableList(this.a.getPeriodo(), this.h);
        String descIntableList2 = PreAutorizacionesDebinUtil.getDescIntableList(this.a.getMoneda(), this.i);
        String cantidad = this.a.getCantidad();
        TextViewUtils.setTextValue(this.ad, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.ae, cuit);
        TextViewUtils.setTextValue(this.af, UtilsCuentas.formatNumeroCuenta(PreAutorizacionesDebinUtil.getDescIntableList(tipo, this.g), sucursal, numero));
        TextViewUtils.setTextValue(this.ag, labelIntableList2);
        TextViewUtils.setTextValue(this.ah, descIntableList);
        TextViewUtils.setTextValue(this.ai, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.aj, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPorDebin));
        TextViewUtils.setTextValue(this.ak, cantidad);
        TextViewUtils.setTextValue(this.al, labelIntableList);
        TextViewUtils.setTextValue(this.am, detalle);
        TextViewUtils.setTextValue(this.an, idPreautorizacion);
        try {
            this.ao.setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterGeneral(this.ao.getText().toString()));
            this.ad.setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterAmount(this.ad.getText().toString()));
            this.af.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.af.getText().toString()));
            this.ae.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.ae.getText().toString()));
            this.ai.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.ai.getText().toString()));
            this.aj.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.aj.getText().toString()));
            this.an.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.an.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.aq.setOnClickListener(this);
    }

    private void z() {
        this.e = this.d.getTableByID(PRE_AUTORIZACIONES.ESTREPEAUT).getListGroupBeans();
        this.f = this.d.getTableByID(PRE_AUTORIZACIONES.CONDEBIN).getListGroupBeans();
        this.i = this.d.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
        this.g = this.d.getTableByID(PRE_AUTORIZACIONES.TPOCTACORTA).getListGroupBeans();
        this.h = this.d.getTableByID(PRE_AUTORIZACIONES.PERIODOPREAUT).getListGroupBeans();
        this.i = this.d.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
    }
}
