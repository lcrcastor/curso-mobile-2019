package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.utils.PreAutorizacionesDebinUtil;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.itrsa.TextViewUtils;
import java.util.List;

public class ConfirmarPreAutorizacionDebinFragment extends Fragment implements OnClickListener {
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
    private SessionManager ao;
    private DetalleDePreAutorizacionFragmentButtomAceptarRechazar b;
    private String c;
    private OnFragmentInteractionListener d;
    private List<ListGroupBean> e;
    private List<ListGroupBean> f;
    private List<ListGroupBean> g;
    private List<ListGroupBean> h;
    private List<ListGroupBean> i;

    public interface OnFragmentInteractionListener {
        void confirmaAccionPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean, String str);
    }

    public static ConfirmarPreAutorizacionDebinFragment newInstance(DetallePreAutorizacionBean detallePreAutorizacionBean, String str) {
        ConfirmarPreAutorizacionDebinFragment confirmarPreAutorizacionDebinFragment = new ConfirmarPreAutorizacionDebinFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ARG1", detallePreAutorizacionBean);
        bundle.putString("ARG2", str);
        confirmarPreAutorizacionDebinFragment.setArguments(bundle);
        return confirmarPreAutorizacionDebinFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.b = (DetalleDePreAutorizacionFragmentButtomAceptarRechazar) getArguments().getParcelable("ARG1");
            this.c = getArguments().getString("ARG2");
        }
        z();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_confirmar_autorizacion_debin, viewGroup, false);
        b(inflate);
        y();
        return inflate;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.d = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.F32_02_BTN_PAGAR_DEBIN) {
            this.d.confirmaAccionPreAutorizacion(this.a, this.c);
        }
    }

    private void y() {
        String sucursal = this.a.getComprador().getCuentaCompradorBean().getSucursal();
        String montoPeriodo = this.a.getMontoPeriodo();
        String montoPorDebin = this.a.getMontoPorDebin();
        String codEstadoInTableList = PreAutorizacionesDebinUtil.getCodEstadoInTableList(this.a.getCodConcepto(), this.f);
        String detalle = this.a.getDetalle();
        String idPreautorizacion = this.a.getIdPreautorizacion();
        String cuit = this.a.getVendedor().getCuit();
        String codEstadoInTableList2 = PreAutorizacionesDebinUtil.getCodEstadoInTableList(this.a.getCodEstado(), this.e);
        String numero = this.a.getComprador().getCuentaCompradorBean().getNumero();
        String tipo = this.a.getComprador().getCuentaCompradorBean().getTipo();
        String descIntableList = PreAutorizacionesDebinUtil.getDescIntableList(this.a.getPeriodo(), this.h);
        String descIntableList2 = PreAutorizacionesDebinUtil.getDescIntableList(this.a.getMoneda(), this.i);
        String cantidad = this.a.getCantidad();
        TextViewUtils.setTextValue(this.ad, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.ae, cuit);
        TextViewUtils.setTextValue(this.af, UtilsCuentas.formatNumeroCuenta(PreAutorizacionesDebinUtil.getDescIntableList(tipo, this.g), sucursal, numero));
        TextViewUtils.setTextValue(this.ag, codEstadoInTableList2);
        TextViewUtils.setTextValue(this.ah, descIntableList);
        TextViewUtils.setTextValue(this.ai, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.aj, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPorDebin));
        TextViewUtils.setTextValue(this.ak, cantidad);
        TextViewUtils.setTextValue(this.al, codEstadoInTableList);
        TextViewUtils.setTextValue(this.am, Html.fromHtml(detalle).toString());
        TextViewUtils.setTextValue(this.an, idPreautorizacion);
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
    }

    private void z() {
        this.e = this.ao.getTableByID(PRE_AUTORIZACIONES.ESTREPEAUT).getListGroupBeans();
        this.f = this.ao.getTableByID(PRE_AUTORIZACIONES.CONDEBIN).getListGroupBeans();
        this.i = this.ao.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
        this.g = this.ao.getTableByID(PRE_AUTORIZACIONES.TPOCTACORTA).getListGroupBeans();
        this.h = this.ao.getTableByID(PRE_AUTORIZACIONES.PERIODOPREAUT).getListGroupBeans();
        this.i = this.ao.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
    }
}
