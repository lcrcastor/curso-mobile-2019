package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.commons.CConsDescripciones;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter.OnChangeValuesListener;
import ar.com.santander.rio.mbanking.app.ui.forms.ItemDecoratorForm;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormData;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataButton;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataDate;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataHeader;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataLeyend;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSection;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSelection;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSelectionSelectionMedioPago;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSelectionSelectionMedioPago.MedioPago;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataText;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataTitle;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormData;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter.OnButtonClickListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.Cuenta;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CoberturaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreguntasFamiliaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MedioPagoCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MedioPagoTarjetaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OcupacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntasFamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UbicacionBean;
import ar.com.santander.rio.mbanking.utils.UtilAccount;
import ar.com.santander.rio.mbanking.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class CompletarSeguroObjetosFragment extends BaseFragment {
    public static final String COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN = "COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN";
    public static final String FAMILIA_BEAN = "FAMILIA_BEAN";
    public static final String FOTO_ID_OBJETO = "FOTO_ID_OBJETO";
    public static final String FOTO_OBJETO = "FOTO_OBJETO";
    public static final String OCUPACIONES = "OCUPACIONES";
    public static final String PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN = "PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN";
    public static final String UBICACION = "UBICACION";
    ArrayList<String> a = new ArrayList<>();
    /* access modifiers changed from: private */
    public GetCotizacionSeguroObjetoBodyResponseBean ad;
    /* access modifiers changed from: private */
    public String ae;
    /* access modifiers changed from: private */
    public String af;
    /* access modifiers changed from: private */
    public UbicacionBean ag;
    private PlanSeguroBean ah;
    /* access modifiers changed from: private */
    public List<OcupacionBean> ai;
    private IActionCustom aj;
    /* access modifiers changed from: private */
    public FormAdapter ak;
    /* access modifiers changed from: private */
    public Boolean al = Boolean.valueOf(false);
    /* access modifiers changed from: private */
    public FormDataSelectionSelectionMedioPago am;
    @Inject
    public AnalyticsManager analyticsManager;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener b;
    /* access modifiers changed from: private */
    public List<IFormData> c = new ArrayList();
    /* access modifiers changed from: private */
    public List<IFormData> d = new ArrayList();
    /* access modifiers changed from: private */
    public List<IFormData> e = new ArrayList();
    private List<IFormData> f = new ArrayList();
    private RecyclerView g;
    /* access modifiers changed from: private */
    public FamiliaBean h;
    private GetPreguntasFamiliaBodyResponseBean i;
    @Inject
    public IDataManager mDataManager;
    @Inject
    public SessionManager sessionManager;

    public interface OnFragmentInteractionListener extends IFragmentBase {
    }

    private void z() {
    }

    public static CompletarSeguroObjetosFragment newInstance(FamiliaBean familiaBean, GetPreguntasFamiliaBodyResponseBean getPreguntasFamiliaBodyResponseBean, GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean, String str, String str2, UbicacionBean ubicacionBean, List<OcupacionBean> list) {
        CompletarSeguroObjetosFragment completarSeguroObjetosFragment = new CompletarSeguroObjetosFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(FAMILIA_BEAN, familiaBean);
        bundle.putParcelable("PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN", getPreguntasFamiliaBodyResponseBean);
        bundle.putParcelable(COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN, getCotizacionSeguroObjetoBodyResponseBean);
        bundle.putString(FOTO_OBJETO, str);
        bundle.putString(FOTO_ID_OBJETO, str2);
        bundle.putParcelable(UBICACION, ubicacionBean);
        bundle.putParcelableArrayList(OCUPACIONES, (ArrayList) list);
        completarSeguroObjetosFragment.setArguments(bundle);
        return completarSeguroObjetosFragment;
    }

    @SuppressLint({"StringFormatInvalid"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.h = (FamiliaBean) getArguments().getParcelable(FAMILIA_BEAN);
            this.i = (GetPreguntasFamiliaBodyResponseBean) getArguments().getParcelable("PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN");
            this.ad = (GetCotizacionSeguroObjetoBodyResponseBean) getArguments().getParcelable(COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN);
            this.ae = getArguments().getString(FOTO_OBJETO);
            this.af = getArguments().getString(FOTO_ID_OBJETO);
            this.ag = (UbicacionBean) getArguments().getParcelable(UBICACION);
            this.ah = a(this.ad.cotizacion.getListaPlanes().getListaPlanes());
            this.ai = getArguments().getParcelableArrayList(OCUPACIONES);
            if (this.ai != null) {
                for (OcupacionBean descOcupacion : this.ai) {
                    this.a.add(descOcupacion.getDescOcupacion());
                }
            }
        }
        this.d = new ArrayList();
        this.e = new ArrayList();
        this.c = new ArrayList();
        this.c.add(FormDataTitle.newInstance().setTitle(Html.fromHtml(getString(R.string.SEGUROS_CONTRATAR_SGRO, this.h.getNombreFamilia())).toString()));
        this.c.add(FormDataHeader.newInstance().setTitle(Html.fromHtml(this.h.getNombreFamilia()).toString()).setDescription("").setIconUrl(this.h.getImagenDetalle()));
        this.d.addAll(B());
        this.e.addAll(C());
        this.c.addAll(this.e);
        this.c.addAll(this.d);
        String descripcion = this.ad.cotizacion.getListaLeyendas().getLeyendaById("SEGOBJ_CONTRA").getDescripcion();
        if (!descripcion.isEmpty()) {
            this.c.add(getField("05", descripcion, null, null, this.a, false, "N"));
        }
        this.c.add(FormDataButton.newInstance().setText(getString(R.string.F27_13_TITLE_DIALOG_CONTRATAR)).setEnableButton(this.al.booleanValue()));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_completar_seguro_objetos, viewGroup, false);
        this.b.configureBackActionBar();
        b(inflate);
        y();
        z();
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.analyticsManager.trackScreen(Screens.contractInsurance(this.h.getNombreFamilia()));
    }

    private void b(View view) {
        c(view);
    }

    private void c(View view) {
        this.g = (RecyclerView) view.findViewById(R.id.family_questions_form);
        this.g.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        this.g.addItemDecoration(new ItemDecoratorForm(getContext(), this.c));
        this.g.setItemViewCacheSize(this.c.size());
    }

    public void onPause() {
        super.onPause();
    }

    @SuppressLint({"StringFormatInvalid"})
    private void y() {
        this.ak = new FormAdapter(this.c, getFragmentManager(), getContext());
        this.ak.setmOnChangeValuesListener(new OnChangeValuesListener() {
            public void isValidListener(ErrorMessage errorMessage) {
                CompletarSeguroObjetosFragment.this.al = errorMessage.getValid();
                CompletarSeguroObjetosFragment.this.ak.setButtonEnable(CompletarSeguroObjetosFragment.this.al.booleanValue());
            }
        });
        this.ak.setOnButtonClickListener(new OnButtonClickListener() {
            public void setActionButtton() {
                String str = "";
                for (int i = 0; i < CompletarSeguroObjetosFragment.this.ai.size(); i++) {
                    if (((OcupacionBean) CompletarSeguroObjetosFragment.this.ai.get(i)).getDescOcupacion().equalsIgnoreCase(CompletarSeguroObjetosFragment.this.a(CompletarSeguroObjetosFragment.this.c, CompletarSeguroObjetosFragment.this.getContext().getString(R.string.ID_4076_SEGUROS_LBL_OCUPACION)))) {
                        str = ((OcupacionBean) CompletarSeguroObjetosFragment.this.ai.get(i)).getCodOcupacion();
                    }
                }
                CompletarSeguroObjetosFragment.this.b.changeFragment(ConfirmarSeguroObjetosFragment.newInstance(CompletarSeguroObjetosFragment.this.e, CompletarSeguroObjetosFragment.this.d, CompletarSeguroObjetosFragment.this.h, CompletarSeguroObjetosFragment.this.ad, CompletarSeguroObjetosFragment.this.ae, CompletarSeguroObjetosFragment.this.af, CompletarSeguroObjetosFragment.this.ag, CompletarSeguroObjetosFragment.this.a(CompletarSeguroObjetosFragment.this.c, "Alias"), str, new MedioPagoTarjetaBean(CompletarSeguroObjetosFragment.this.am.getMedioPagoValue()), new MedioPagoCuentaBean(CompletarSeguroObjetosFragment.this.am.getMedioPagoValue())), FragmentConstants.SEGURO_OBJETO_CONFIRMAR);
                ((BaseActivity) CompletarSeguroObjetosFragment.this.getActivity()).hideKeyboard();
            }
        });
        this.g.setAdapter(this.ak);
    }

    public FormData getField(String str, String str2, String str3, String str4, ArrayList<String> arrayList, boolean z, String str5) {
        Integer num;
        int i2;
        boolean equalsIgnoreCase = str5.equalsIgnoreCase("N");
        if (str.equals("00")) {
            return (FormData) FormDataText.newInstance(Constants.LOCALE_DEFAULT_APP).setValueText(str2);
        }
        if (str.equals("01")) {
            if (str4 != null) {
                try {
                    if (!str4.isEmpty()) {
                        i2 = Integer.parseInt(str4);
                        num = Integer.valueOf(i2);
                        FormData a2 = a((FormData) FormDataText.newInstance(Constants.LOCALE_DEFAULT_APP).setMaxOfCharacters(num).setRequired(Boolean.valueOf(equalsIgnoreCase)).setEditable(Boolean.valueOf(z)).setLabelText(str2));
                        if (!z && str3 != null) {
                            a2.setValueText(str3);
                        }
                        return a2;
                    }
                } catch (Exception unused) {
                    num = Integer.valueOf(100);
                }
            }
            i2 = 100;
            num = Integer.valueOf(i2);
            FormData a22 = a((FormData) FormDataText.newInstance(Constants.LOCALE_DEFAULT_APP).setMaxOfCharacters(num).setRequired(Boolean.valueOf(equalsIgnoreCase)).setEditable(Boolean.valueOf(z)).setLabelText(str2));
            a22.setValueText(str3);
            return a22;
        } else if (str.equals("02")) {
            return (FormData) FormDataDate.newInstance(Constants.LOCALE_DEFAULT_APP).setRequired(Boolean.valueOf(equalsIgnoreCase)).setHint("Seleccionar").setLabelText(str2);
        } else {
            if (str.equals("03")) {
                return (FormData) FormDataSelection.newInstance(Constants.LOCALE_DEFAULT_APP).setStringList(arrayList).setHint("Seleccionar").setRequired(Boolean.valueOf(equalsIgnoreCase)).setLabelText(str2);
            }
            if (str.equals("04")) {
                return (FormDataSelectionSelectionMedioPago) FormDataSelectionSelectionMedioPago.newInstance(Constants.LOCALE_DEFAULT_APP).setStringMedioPagoList(A()).setHint("Seleccionar").setRequired(Boolean.valueOf(equalsIgnoreCase)).setLabelText(str2);
            }
            if (str.equals("05")) {
                return (FormDataLeyend) FormDataLeyend.newInstance(Constants.LOCALE_DEFAULT_APP).setValueText(str2);
            }
            return null;
        }
    }

    private FormData a(FormData formData) {
        if (formData.getLabelText().equalsIgnoreCase("alias")) {
            formData.setHint(getString(R.string.ID_4823_SEGUROS_MAS_EJ_TV_DE_PABLO));
        }
        return formData;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.b = (OnFragmentInteractionListener) context;
            this.aj = (IActionCustom) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    private PlanSeguroBean a(List<PlanSeguroBean> list) {
        for (PlanSeguroBean planSeguroBean : list) {
            if (planSeguroBean.isChecked()) {
                return planSeguroBean;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public String a(List<IFormData> list, String str) {
        for (IFormData iFormData : list) {
            if (iFormData.getLabelText().equalsIgnoreCase(str)) {
                return iFormData.getValueText();
            }
        }
        return null;
    }

    private ArrayList<MedioPago> A() {
        String str;
        ArrayList<MedioPago> arrayList = new ArrayList<>();
        ArrayList<Tarjeta> arrayList2 = new ArrayList<>();
        ArrayList<Cuenta> arrayList3 = new ArrayList<>();
        if (!(this.sessionManager.getLoginUnico().getProductos().getCuentas() == null || this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas() == null || this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas().isEmpty())) {
            for (Cuenta cuenta : this.sessionManager.getLoginUnico().getProductos().getCuentas().getCuentas()) {
                if (!cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CJA_AHO_DOLAR) && !cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CTA_CTE_DOLAR) && !cuenta.getTipo().equals(LoginConstants.TIPO_CUENTA_CU_DOLAR)) {
                    if (cuenta.getTipo().equalsIgnoreCase(LoginConstants.TIPO_CUENTA_CU)) {
                        str = LoginConstants.TIPO_CUENTA_CU_PESOS;
                    } else {
                        str = cuenta.getTipo();
                    }
                    cuenta.setTipo(str);
                    arrayList3.add(cuenta);
                }
            }
        }
        if (!(this.sessionManager.getLoginUnico().getProductos().getTarjetas() == null || this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas() == null || this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas().isEmpty())) {
            for (Tarjeta tarjeta : this.sessionManager.getLoginUnico().getProductos().getTarjetas().getTarjetas()) {
                if (!tarjeta.getClase().equalsIgnoreCase("T") && !tarjeta.getTipo().equalsIgnoreCase(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
                    arrayList2.add(tarjeta);
                }
            }
        }
        for (Tarjeta tarjeta2 : arrayList2) {
            MedioPago medioPago = new MedioPago();
            medioPago.setNro(tarjeta2.getNroTarjetaCredito());
            medioPago.setSucursal(tarjeta2.getNroSuc());
            medioPago.setTipo(tarjeta2.getTipo());
            medioPago.setDescripcion(Utils.mascaraTarjeta(tarjeta2));
            medioPago.setCreditCard(true);
            arrayList.add(medioPago);
        }
        for (Cuenta cuenta2 : arrayList3) {
            MedioPago medioPago2 = new MedioPago();
            String abreviatureAndAccountFormat = UtilAccount.getAbreviatureAndAccountFormat(CConsDescripciones.getConsDescripcionTPOCTACORTA(this.sessionManager), cuenta2.getTipo(), cuenta2.getNroSuc(), cuenta2.getNumero());
            medioPago2.setNro(cuenta2.getNumero());
            medioPago2.setSucursal(cuenta2.getNroSuc());
            medioPago2.setTipo(cuenta2.getTipo());
            medioPago2.setDescripcion(abreviatureAndAccountFormat);
            medioPago2.setAccount(true);
            arrayList.add(medioPago2);
        }
        return arrayList;
    }

    private List<IFormData> B() {
        ArrayList arrayList = new ArrayList();
        for (PreguntasFamiliaBean preguntasFamiliaBean : this.i.getListaPreguntas().getPregunta()) {
            String str = "";
            if (preguntasFamiliaBean.getTextoPregunta() != null && !preguntasFamiliaBean.getTextoPregunta().isEmpty()) {
                switch (Integer.parseInt(preguntasFamiliaBean.getTipoRespuesta())) {
                    case 1:
                        str = preguntasFamiliaBean.getRespuesta().getTextoIngresado();
                        break;
                    case 2:
                        str = preguntasFamiliaBean.getRespuesta().getFechaSeleccionada();
                        break;
                    case 3:
                        str = preguntasFamiliaBean.getRespuesta().getTextoOpcion();
                        break;
                    default:
                        str = preguntasFamiliaBean.getRespuesta().getTextoIngresado();
                        break;
                }
            }
            arrayList.add(getField("01", preguntasFamiliaBean.getTextoPregunta(), str, preguntasFamiliaBean.getCantMaxCaracteres(), null, false, preguntasFamiliaBean.getRespuestaOpcional()));
        }
        return arrayList;
    }

    private List<IFormData> C() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new FormDataSection().setLabelText("Cobertura"));
        arrayList.add(getField("01", "Cuota Mensual", this.ah.getCuota(), null, null, false, "N"));
        arrayList.add(getField("01", getContext().getString(R.string.ID_4070_SEGUROS_LBL_COBERTURA), this.ah.getNombre(), null, null, false, "N"));
        for (CoberturaBean coberturaBean : this.ah.getListaCoberturas().getListaCoberturas()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Suma Asegurada ");
            sb.append(coberturaBean.getDescripcion());
            arrayList.add(getField("01", sb.toString(), coberturaBean.getSumaAseguradaCober(), null, null, false, "N"));
        }
        this.am = (FormDataSelectionSelectionMedioPago) getField("04", getContext().getString(R.string.ID_4073_SEGUROS_LBL_MEDIO_PAGO), "Seleccionar", null, null, false, "N");
        arrayList.add(this.am);
        arrayList.add(getField("01", getContext().getString(R.string.ID_4033_SEGUROS_LBL_FORMA_PAGO), "Débito automático", null, null, false, "N"));
        arrayList.add(getField("03", getContext().getString(R.string.ID_4076_SEGUROS_LBL_OCUPACION), null, null, this.a, false, "N"));
        arrayList.add(new FormDataSection().setLabelText("Bien Asegurado"));
        arrayList.add(getField("01", "Alias", null, null, null, true, "N"));
        return arrayList;
    }

    public void onDetach() {
        super.onDetach();
        this.b = null;
        this.aj = null;
    }
}
