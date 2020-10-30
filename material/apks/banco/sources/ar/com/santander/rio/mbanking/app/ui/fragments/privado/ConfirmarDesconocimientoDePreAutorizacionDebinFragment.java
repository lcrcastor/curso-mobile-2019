package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;

public class ConfirmarDesconocimientoDePreAutorizacionDebinFragment extends Fragment implements OnClickListener {
    private DetallePreAutorizacionBean a;
    private TextView ad;
    private TextView ae;
    private TextView af;
    private TextView ag;
    private TextView ah;
    private TextView ai;
    private TextView aj;
    private Button ak;
    private String b;
    private OnFragmentInteractionListener c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;

    public interface OnFragmentInteractionListener {
        void confirmaAccionPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean, String str);
    }

    public static ConfirmarDesconocimientoDePreAutorizacionDebinFragment newInstance(DetallePreAutorizacionBean detallePreAutorizacionBean, String str) {
        ConfirmarDesconocimientoDePreAutorizacionDebinFragment confirmarDesconocimientoDePreAutorizacionDebinFragment = new ConfirmarDesconocimientoDePreAutorizacionDebinFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("DETALLE_PRE_AUTORIZACION_PARAM", detallePreAutorizacionBean);
        bundle.putString("TIPO_OPERACION_PARAM", str);
        confirmarDesconocimientoDePreAutorizacionDebinFragment.setArguments(bundle);
        return confirmarDesconocimientoDePreAutorizacionDebinFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.a = (DetallePreAutorizacionBean) getArguments().getParcelable("DETALLE_PRE_AUTORIZACION_PARAM");
            this.b = getArguments().getString("TIPO_OPERACION_PARAM");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_confirmar_desconocimiento_de_pago_debin, viewGroup, false);
        b(inflate);
        y();
        return inflate;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.c = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.c = null;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.F32_02_BTN_PAGAR_DEBIN) {
            this.c.confirmaAccionPreAutorizacion(this.a, this.b);
        }
    }

    private void b(View view) {
        this.ak = (Button) view.findViewById(R.id.F32_02_BTN_PAGAR_DEBIN);
        this.d = (TextView) view.findViewById(R.id.textViewTitulo);
        this.e = (TextView) view.findViewById(R.id.amountViewImporteMaximoData);
        this.f = (TextView) view.findViewById(R.id.textViewVendedor);
        this.g = (TextView) view.findViewById(R.id.textViewConfirmarDesconocimientoDePagoDebinCUIT);
        this.h = (TextView) view.findViewById(R.id.textViewCBUConfirmarDesconocimientoDePagoDebinData);
        this.i = (TextView) view.findViewById(R.id.textViewAliasConfirmarDesconocimientoDePagoDebinData);
        this.ad = (TextView) view.findViewById(R.id.textViewBancoConfirmarDesconocimientoDePagoDebinData);
        this.ae = (TextView) view.findViewById(R.id.textViewCuentaDebitoConfirmarDesconocimientoDePagoDebinData);
        this.af = (TextView) view.findViewById(R.id.textViewFechaDeSolicitudConfirmarDesconocimientoDePagoDebinData);
        this.ag = (TextView) view.findViewById(R.id.textViewFechaDeVencimientoConfirmarDesconocimientoDePagoDebinData);
        this.ah = (TextView) view.findViewById(R.id.textViewConceptoConfirmarDesconocimientoDePagoDebinData);
        this.ai = (TextView) view.findViewById(R.id.textViewDescripcionConfirmarDesconocimientoDePagoDebinData);
        this.aj = (TextView) view.findViewById(R.id.textViewIDDebinConfirmarDesconocimientoDePagoDebinData);
    }

    private void y() {
        this.ak.setOnClickListener(this);
        this.d.setText(R.string.ID_4635_DEBIN_TIT_CONFIRMAR_AUTORIZACION_DEBIN);
        this.e.setText(this.a.getMontoPeriodo());
        this.g.setText(this.a.getVendedor().getCuit());
        this.ae.setText(this.a.getComprador().getCuentaCompradorBean().getNumero());
        this.af.setText(this.a.getFechaCreacion());
        this.ah.setText(this.a.getCodConcepto());
        this.ai.setText(Html.fromHtml(this.a.getDetalle()).toString());
    }
}
