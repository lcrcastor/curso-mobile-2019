package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;

public class ConfirmarDesconocimientoDePagoDebinFragment extends Fragment {
    private DetallePreAutorizacionBean a;
    private DetalleDePreAutorizacionFragmentButtomAceptarRechazar b;
    private String c;
    private OnFragmentInteractionListener d;

    public interface OnFragmentInteractionListener {
        void confirmaAccionPreAutorizacion(DetallePreAutorizacionBean detallePreAutorizacionBean, String str);
    }

    public static ConfirmarDesconocimientoDePagoDebinFragment newInstance(DetallePreAutorizacionBean detallePreAutorizacionBean, String str) {
        ConfirmarDesconocimientoDePagoDebinFragment confirmarDesconocimientoDePagoDebinFragment = new ConfirmarDesconocimientoDePagoDebinFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ARG1", detallePreAutorizacionBean);
        bundle.putString("ARG2", str);
        confirmarDesconocimientoDePagoDebinFragment.setArguments(bundle);
        return confirmarDesconocimientoDePagoDebinFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.b = (DetalleDePreAutorizacionFragmentButtomAceptarRechazar) getArguments().getParcelable("ARG1");
            this.c = getArguments().getString("ARG2");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_confirmar_desconocimiento_de_pago_debin, viewGroup, false);
        b(inflate);
        return inflate;
    }

    private void b(View view) {
        TextView textView = (TextView) view.findViewById(R.id.textViewTitulo);
        TextView textView2 = (TextView) view.findViewById(R.id.amountViewImporteMaximoData);
        TextView textView3 = (TextView) view.findViewById(R.id.textViewVendedor);
        TextView textView4 = (TextView) view.findViewById(R.id.textViewConfirmarDesconocimientoDePagoDebinCUIT);
        TextView textView5 = (TextView) view.findViewById(R.id.textViewCBUConfirmarDesconocimientoDePagoDebinData);
        TextView textView6 = (TextView) view.findViewById(R.id.textViewAliasConfirmarDesconocimientoDePagoDebinData);
        TextView textView7 = (TextView) view.findViewById(R.id.textViewBancoConfirmarDesconocimientoDePagoDebinData);
        TextView textView8 = (TextView) view.findViewById(R.id.textViewCuentaDebitoConfirmarDesconocimientoDePagoDebinData);
        TextView textView9 = (TextView) view.findViewById(R.id.textViewFechaDeSolicitudConfirmarDesconocimientoDePagoDebinData);
        TextView textView10 = (TextView) view.findViewById(R.id.textViewFechaDeVencimientoConfirmarDesconocimientoDePagoDebinData);
        TextView textView11 = (TextView) view.findViewById(R.id.textViewConceptoConfirmarDesconocimientoDePagoDebinData);
        TextView textView12 = (TextView) view.findViewById(R.id.textViewDescripcionConfirmarDesconocimientoDePagoDebinData);
        TextView textView13 = (TextView) view.findViewById(R.id.textViewIDDebinConfirmarDesconocimientoDePagoDebinData);
        if (this.c.equals("A")) {
            textView.setText(R.string.ID_4635_DEBIN_TIT_CONFIRMAR_AUTORIZACION_DEBIN);
            textView2.setText(this.a.getMontoPeriodo());
            textView4.setText(this.a.getVendedor().getCuit());
            textView8.setText(this.a.getComprador().getCuentaCompradorBean().getNumero());
            textView9.setText(this.a.getFechaCreacion());
            textView11.setText(this.a.getCodConcepto());
            textView12.setText(Html.fromHtml(this.a.getDetalle()).toString());
        }
    }

    public void setVisibiliteVisible(TextView textView) {
        textView.setVisibility(0);
    }

    public void setVisibiliteInvisible(TextView textView) {
        textView.setVisibility(4);
    }

    public void setVisibiliteGone(TextView textView) {
        textView.setVisibility(8);
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
        this.d = null;
    }
}
