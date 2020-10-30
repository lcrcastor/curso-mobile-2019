package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.PreAutorizacionDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.app.ui.utils.PreAutorizacionesDebinUtil;
import ar.com.santander.rio.mbanking.components.share.OptionsToShare;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import ar.com.santander.rio.mbanking.utils.itrsa.TextViewUtils;
import java.util.List;

public class ComprobantePreAutorizacionDebinFragment extends Fragment implements OnClickListener {
    public static final String DETALLE_PREAUTORIZACION_ARG = "DETALLE_PREAUTORIZACION_ARG";
    public static final String FECHA_DE_OPERACION = "FECHA_DE_OPERACION";
    public static final String LEYENDA = "LEYENDA";
    public static final String NRO_COMPROBANTE_ARG = "NRO_COMPROBANTE";
    public static final String TIPO_DE_OPERACION = "TIPO_DE_OPERACION";
    private SessionManager a;
    private List<ListGroupBean> ad;
    private List<ListGroupBean> ae;
    private List<ListGroupBean> af;
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
    private TextView aq;

    /* renamed from: ar reason: collision with root package name */
    private TextView f236ar;
    private TextView as;
    private TextView at;
    private Button au;
    /* access modifiers changed from: private */
    public View av;
    private String aw = "";
    /* access modifiers changed from: private */
    public boolean ax = false;
    private OptionsToShare ay;
    private SettingsManager b;
    private String c;
    private String d;
    private String e;
    /* access modifiers changed from: private */
    public String f;
    private DetallePreAutorizacionBean g;
    private List<ListGroupBean> h;
    private List<ListGroupBean> i;
    public OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void backToPrincipalPage();
    }

    public static ComprobantePreAutorizacionDebinFragment newInstance(SessionManager sessionManager, SettingsManager settingsManager, String str, String str2, String str3, DetallePreAutorizacionBean detallePreAutorizacionBean, String str4) {
        ComprobantePreAutorizacionDebinFragment comprobantePreAutorizacionDebinFragment = new ComprobantePreAutorizacionDebinFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("DETALLE_PREAUTORIZACION_ARG", detallePreAutorizacionBean);
        bundle.putString(NRO_COMPROBANTE_ARG, str);
        bundle.putString(FECHA_DE_OPERACION, str2);
        bundle.putString("LEYENDA", str3);
        bundle.putString(TIPO_DE_OPERACION, str4);
        comprobantePreAutorizacionDebinFragment.setArguments(bundle);
        comprobantePreAutorizacionDebinFragment.a = sessionManager;
        comprobantePreAutorizacionDebinFragment.b = settingsManager;
        return comprobantePreAutorizacionDebinFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        this.c = arguments.getString(NRO_COMPROBANTE_ARG);
        this.g = (DetallePreAutorizacionBean) arguments.getParcelable("DETALLE_PREAUTORIZACION_ARG");
        this.d = arguments.getString(FECHA_DE_OPERACION);
        this.e = arguments.getString("LEYENDA");
        this.f = arguments.getString(TIPO_DE_OPERACION);
        z();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.fragment_pre_autorizacion_debin_comprobante, viewGroup, false);
        b(inflate);
        y();
        return inflate;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ID_4440_DEBIN_VOLVER) {
            if (this.ax) {
                this.mListener.backToPrincipalPage();
            } else {
                canExit();
            }
        }
    }

    public void showSharedOptions() {
        PreAutorizacionesDebinUtil.getIsbanDialogToShared((AppCompatActivity) getActivity(), this.c, this.av, this.aw).show(getActivity().getSupportFragmentManager(), PRE_AUTORIZACIONES.SHARED_PREAUTORIZACIONES_DEBIN_FRAGMENT);
    }

    private void b(View view) {
        this.ag = (TextView) view.findViewById(R.id.textViewTitulo);
        this.ah = (TextView) view.findViewById(R.id.amountViewImporteMaximoData);
        this.aj = (TextView) view.findViewById(R.id.textViewConfirmarAutorizacionDebinCUIT);
        this.ak = (TextView) view.findViewById(R.id.textViewCuentaDebitoConfirmarAutorizacionDebinData);
        this.ai = (TextView) view.findViewById(R.id.textViewFechaDeSolicitudData);
        this.al = (TextView) view.findViewById(R.id.textViewPeriodoMaximoData);
        this.am = (TextView) view.findViewById(R.id.textViewImporteMaximoPorPeriodoData);
        this.an = (TextView) view.findViewById(R.id.textViewImporteMaximoPorDebinData);
        this.ao = (TextView) view.findViewById(R.id.textViewCantidadMaximaDeDebinPorPeriodoData);
        this.ap = (TextView) view.findViewById(R.id.textViewConceptoData);
        this.aq = (TextView) view.findViewById(R.id.textViewDescripcionData);
        this.f236ar = (TextView) view.findViewById(R.id.textViewIDSolicitudData);
        this.as = (TextView) view.findViewById(R.id.textViewFechaDeOperacionData);
        this.at = (TextView) view.findViewById(R.id.textViewNumeroDeComprobanteDataData);
        this.au = (Button) view.findViewById(R.id.ID_4440_DEBIN_VOLVER);
        this.av = view.findViewById(R.id.comprobantePreautorizacionDebin);
    }

    private void y() {
        String fechaCreacion = this.g.getFechaCreacion();
        String sucursal = this.g.getComprador().getCuentaCompradorBean().getSucursal();
        String montoPeriodo = this.g.getMontoPeriodo();
        String montoPorDebin = this.g.getMontoPorDebin();
        String labelIntableList = PreAutorizacionesDebinUtil.getLabelIntableList(this.g.getCodConcepto(), this.i);
        String detalle = this.g.getDetalle();
        String idPreautorizacion = this.g.getIdPreautorizacion();
        String cuit = this.g.getVendedor().getCuit();
        String numero = this.g.getComprador().getCuentaCompradorBean().getNumero();
        String tipo = this.g.getComprador().getCuentaCompradorBean().getTipo();
        String descIntableList = PreAutorizacionesDebinUtil.getDescIntableList(this.g.getPeriodo(), this.ae);
        String descIntableList2 = PreAutorizacionesDebinUtil.getDescIntableList(this.g.getMoneda(), this.af);
        String cantidad = this.g.getCantidad();
        TextViewUtils.setTextValue(this.ai, fechaCreacion);
        TextViewUtils.setTextValue(this.ah, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.aj, cuit);
        TextViewUtils.setTextValue(this.ak, UtilsCuentas.formatNumeroCuenta(PreAutorizacionesDebinUtil.getDescIntableList(tipo, this.ad), sucursal, numero));
        TextViewUtils.setTextValue(this.al, descIntableList);
        TextViewUtils.setTextValue(this.am, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPeriodo));
        TextViewUtils.setTextValue(this.an, descIntableList2.concat(UtilsCuentas.SEPARAOR2).concat(montoPorDebin));
        TextViewUtils.setTextValue(this.ao, cantidad);
        TextViewUtils.setTextValue(this.ap, labelIntableList);
        TextViewUtils.setTextValue(this.aq, Html.fromHtml(detalle).toString());
        TextViewUtils.setTextValue(this.f236ar, idPreautorizacion);
        TextViewUtils.setTextValue(this.as, this.d);
        TextViewUtils.setTextValue(this.at, this.c);
        if (this.f.equals("A")) {
            this.ag.setText(R.string.ID_4642_DEBIN_TIT_COMPROBANTE_ACEPTACION_PREAUTORIZACION);
            this.aw = getResources().getString(R.string.ID_4642_DEBIN_TIT_COMPROBANTE_ACEPTACION_PREAUTORIZACION);
        } else if (this.f.equals("R")) {
            this.ag.setText(R.string.ID_4644_DEBIN_TIT_COMPROBANTE_RECHAZO_PREAUTORIZACION);
            this.aw = getResources().getString(R.string.ID_4644_DEBIN_TIT_COMPROBANTE_RECHAZO_PREAUTORIZACION);
        } else if (this.f.equals("B")) {
            this.ag.setText(R.string.ID_4646_DEBIN_TIT_COMPROBANTE_ANULACION_PREAUTORIZACION);
            this.aw = getResources().getString(R.string.ID_4646_DEBIN_TIT_COMPROBANTE_ANULACION_PREAUTORIZACION);
        } else if (this.f.equals("D")) {
            this.ag.setText(R.string.ID_4647_DEBIN_TIT_CONFIRMAR_DESCONOCIMIENTO);
            this.aw = getResources().getString(R.string.ID_4647_DEBIN_TIT_CONFIRMAR_DESCONOCIMIENTO);
        }
        CAccessibility cAccessibility = new CAccessibility(getActivity().getApplicationContext());
        try {
            this.ai.setContentDescription(cAccessibility.applyFilterDate(this.ai.getText().toString()));
            this.ah.setContentDescription(cAccessibility.applyFilterAmount(this.ah.getText().toString()));
            this.aj.setContentDescription(cAccessibility.applyFilterCharacterToCharacter(this.aj.getText().toString()));
            this.ak.setContentDescription(cAccessibility.applyFilterAccount(this.ak.getText().toString()));
            this.al.setContentDescription(cAccessibility.applyFilterGeneral(this.al.getText().toString()));
            this.am.setContentDescription(cAccessibility.applyFilterAmount(this.am.getText().toString()));
            this.an.setContentDescription(cAccessibility.applyFilterAmount(this.an.getText().toString()));
            this.an.setContentDescription(cAccessibility.applyFilterAmount(this.an.getText().toString()));
            this.ao.setContentDescription(cAccessibility.applyFilterGeneral(this.ao.getText().toString()));
            this.ap.setContentDescription(cAccessibility.applyFilterGeneral(this.ap.getText().toString()));
            this.aq.setContentDescription(cAccessibility.applyFilterGeneral(this.aq.getText().toString()));
            this.f236ar.setContentDescription(cAccessibility.applyFilterCharacterToCharacter(this.f236ar.getText().toString()));
            this.as.setContentDescription(cAccessibility.applyFilterDate(this.as.getText().toString()));
            this.at.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterCharacterToCharacter(this.at.getText().toString()));
            this.ag.setContentDescription(cAccessibility.applyFilterDebinPreAutorizacion(this.ag.getText().toString()));
        } catch (Exception unused) {
        }
        this.au.setOnClickListener(this);
    }

    private void z() {
        this.h = this.a.getTableByID(PRE_AUTORIZACIONES.ESTREPEAUT).getListGroupBeans();
        this.i = this.a.getTableByID(PRE_AUTORIZACIONES.CONDEBIN).getListGroupBeans();
        this.af = this.a.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
        this.ad = this.a.getTableByID(PRE_AUTORIZACIONES.TPOCTACORTA).getListGroupBeans();
        this.ae = this.a.getTableByID(PRE_AUTORIZACIONES.PERIODOPREAUT).getListGroupBeans();
        this.af = this.a.getTableByID(PRE_AUTORIZACIONES.MONEDADESCSIMBOLO).getListGroupBeans();
    }

    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        this.ay.onRequestPermissionsResult(i2, strArr, iArr);
    }

    public boolean canExit() {
        if (!this.ax) {
            this.ay = new OptionsToShareImpl(this, getContext(), getFragmentManager()) {
                public View getViewToShare() {
                    return ComprobantePreAutorizacionDebinFragment.this.av;
                }

                public void receiveIntentAppShare(Intent intent) {
                    ComprobantePreAutorizacionDebinFragment.this.startActivity(Intent.createChooser(intent, ComprobantePreAutorizacionDebinFragment.this.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
                }

                public String getFileName() {
                    String str = "";
                    if (ComprobantePreAutorizacionDebinFragment.this.f.equals("A")) {
                        return ComprobantePreAutorizacionDebinFragment.this.getResources().getString(R.string.ID_4642_DEBIN_TIT_COMPROBANTE_ACEPTACION_PREAUTORIZACION);
                    }
                    if (ComprobantePreAutorizacionDebinFragment.this.f.equals("R")) {
                        return ComprobantePreAutorizacionDebinFragment.this.getResources().getString(R.string.ID_4644_DEBIN_TIT_COMPROBANTE_RECHAZO_PREAUTORIZACION);
                    }
                    if (ComprobantePreAutorizacionDebinFragment.this.f.equals("B")) {
                        return ComprobantePreAutorizacionDebinFragment.this.getResources().getString(R.string.ID_4646_DEBIN_TIT_COMPROBANTE_ANULACION_PREAUTORIZACION);
                    }
                    return ComprobantePreAutorizacionDebinFragment.this.f.equals("D") ? ComprobantePreAutorizacionDebinFragment.this.getResources().getString(R.string.ID_4647_DEBIN_TIT_CONFIRMAR_DESCONOCIMIENTO) : str;
                }

                public String getSubjectReceiptToShare() {
                    String str = "";
                    if (ComprobantePreAutorizacionDebinFragment.this.f.equals("A")) {
                        return ComprobantePreAutorizacionDebinFragment.this.getResources().getString(R.string.ID_4642_DEBIN_TIT_COMPROBANTE_ACEPTACION_PREAUTORIZACION);
                    }
                    if (ComprobantePreAutorizacionDebinFragment.this.f.equals("R")) {
                        return ComprobantePreAutorizacionDebinFragment.this.getResources().getString(R.string.ID_4644_DEBIN_TIT_COMPROBANTE_RECHAZO_PREAUTORIZACION);
                    }
                    if (ComprobantePreAutorizacionDebinFragment.this.f.equals("B")) {
                        return ComprobantePreAutorizacionDebinFragment.this.getResources().getString(R.string.ID_4646_DEBIN_TIT_COMPROBANTE_ANULACION_PREAUTORIZACION);
                    }
                    return ComprobantePreAutorizacionDebinFragment.this.f.equals("D") ? ComprobantePreAutorizacionDebinFragment.this.getResources().getString(R.string.ID_4647_DEBIN_TIT_CONFIRMAR_DESCONOCIMIENTO) : str;
                }

                public void optionDownloadSelected() {
                    super.optionDownloadSelected();
                    ComprobantePreAutorizacionDebinFragment.this.ax = true;
                }

                public void optionShareSelected() {
                    super.optionShareSelected();
                    ComprobantePreAutorizacionDebinFragment.this.ax = true;
                }

                public void onAbortShare() {
                    super.onAbortShare();
                    ComprobantePreAutorizacionDebinFragment.this.ax = true;
                    ((PreAutorizacionDebinActivity) ComprobantePreAutorizacionDebinFragment.this.getActivity()).backToPrincipalPage();
                }
            };
            this.ay.showAlert();
        }
        return this.ax;
    }
}
