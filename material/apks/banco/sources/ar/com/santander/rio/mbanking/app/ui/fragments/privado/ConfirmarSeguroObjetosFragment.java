package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.FotoObjetoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter.OnIntentListener;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter.OnValidateControl;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormCheckBox;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataButton;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataHeader;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataLeyend;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataTitle;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormData;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ItemDecoratorForm;
import ar.com.santander.rio.mbanking.app.ui.list_forms.ListAdapter.OnButtonClickListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ContratarSeguroObjetoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MedioPagoCuentaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.MedioPagoTarjetaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UbicacionBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VContratarSeguroObjeto;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ConfirmarSeguroObjetosFragment extends BaseFragment {
    public static final String ALIAS = "ALIAS";
    public static final String COTIZACION_SEGURO_OBJETO = "COTIZACION_SEGURO_OBJETO";
    public static final String FAMILIA_BEAN_SELECTED = "FAMILIA_BEAN_SELECTED";
    public static final String IFORM_DATA_LIST = "IFORM_DATA_LIST";
    public static final String IFORM_DATA_PREGUNTAS = "IFORM_DATA_PREGUNTAS";
    public static final String MEDIO_PAGO_CUENTA_ARG = "MEDIO_PAGO_CUENTA_ARG";
    public static final String MEDIO_PAGO_TARJETA_CREDITO_ARG = "MEDIO_PAGO_TARJETA_CREDITO_ARG";
    public static final String OCUPACION = "OCUPACION";
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener a;
    /* access modifiers changed from: private */
    public String ad;
    /* access modifiers changed from: private */
    public String ae;
    /* access modifiers changed from: private */
    public FormAdapter af;
    private RecyclerView ag;
    /* access modifiers changed from: private */
    public FamiliaBean ah;
    private IActionCustom ai;
    private FragmentActivity aj;
    /* access modifiers changed from: private */
    public MedioPagoCuentaBean ak;
    /* access modifiers changed from: private */
    public MedioPagoTarjetaBean al;
    @Inject
    public AnalyticsManager analyticsManager;
    private List<IFormData> b = new ArrayList();
    /* access modifiers changed from: private */
    public List<IFormData> c = new ArrayList();
    /* access modifiers changed from: private */
    public List<IFormData> d = new ArrayList();
    /* access modifiers changed from: private */
    public GetCotizacionSeguroObjetoBodyResponseBean e;
    /* access modifiers changed from: private */
    public String f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public UbicacionBean h;
    /* access modifiers changed from: private */
    public PlanSeguroBean i;
    @Inject
    public IDataManager mDataManager;
    @Inject
    public SessionManager sessionManager;

    public interface OnFragmentInteractionListener extends IFragmentBase {
        void showComprobanteSeguroAccidente(ContratarSeguroObjetoBodyResponseBean contratarSeguroObjetoBodyResponseBean, List<IFormData> list, List<IFormData> list2, FamiliaBean familiaBean, GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean);
    }

    private void z() {
    }

    public static ConfirmarSeguroObjetosFragment newInstance(List<IFormData> list, List<IFormData> list2, FamiliaBean familiaBean, GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean, String str, String str2, UbicacionBean ubicacionBean, String str3, String str4, MedioPagoTarjetaBean medioPagoTarjetaBean, MedioPagoCuentaBean medioPagoCuentaBean) {
        ConfirmarSeguroObjetosFragment confirmarSeguroObjetosFragment = new ConfirmarSeguroObjetosFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IFORM_DATA_LIST, (ArrayList) list);
        bundle.putParcelableArrayList(IFORM_DATA_PREGUNTAS, (ArrayList) list2);
        bundle.putParcelable("FAMILIA_BEAN_SELECTED", familiaBean);
        bundle.putParcelable(CompletarSeguroObjetosFragment.COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN, getCotizacionSeguroObjetoBodyResponseBean);
        bundle.putString(CompletarSeguroObjetosFragment.FOTO_OBJETO, str);
        bundle.putString(CompletarSeguroObjetosFragment.FOTO_ID_OBJETO, str2);
        bundle.putParcelable(CompletarSeguroObjetosFragment.UBICACION, ubicacionBean);
        bundle.putString(ALIAS, str3);
        bundle.putString(OCUPACION, str4);
        bundle.putParcelable(MEDIO_PAGO_CUENTA_ARG, medioPagoCuentaBean);
        bundle.putParcelable(MEDIO_PAGO_TARJETA_CREDITO_ARG, medioPagoTarjetaBean);
        confirmarSeguroObjetosFragment.setArguments(bundle);
        return confirmarSeguroObjetosFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.c = (List) getArguments().getParcelableArrayList(IFORM_DATA_LIST).clone();
            this.d = (List) getArguments().getParcelableArrayList(IFORM_DATA_PREGUNTAS).clone();
            this.ah = (FamiliaBean) getArguments().getParcelable("FAMILIA_BEAN_SELECTED");
            this.e = (GetCotizacionSeguroObjetoBodyResponseBean) getArguments().getParcelable(CompletarSeguroObjetosFragment.COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN);
            this.f = b(getArguments().getString(CompletarSeguroObjetosFragment.FOTO_OBJETO));
            this.g = b(getArguments().getString(CompletarSeguroObjetosFragment.FOTO_ID_OBJETO));
            this.h = (UbicacionBean) getArguments().getParcelable(CompletarSeguroObjetosFragment.UBICACION);
            this.ad = getArguments().getString(ALIAS);
            this.ae = getArguments().getString(OCUPACION);
            this.i = a(this.e.cotizacion.getListaPlanes().getListaPlanes());
            this.ak = (MedioPagoCuentaBean) getArguments().getParcelable(MEDIO_PAGO_CUENTA_ARG);
            this.al = (MedioPagoTarjetaBean) getArguments().getParcelable(MEDIO_PAGO_TARJETA_CREDITO_ARG);
        }
    }

    private String b(String str) {
        return this.aj.getSharedPreferences(FotoObjetoActivity.SEGUROS_2_SHARE_PREFERENCE_EDITOR_NAME, 0).getString(str, "");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_confirmar_seguro_objetos, viewGroup, false);
        this.a.configureBackActionBar();
        b(inflate);
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.analyticsManager.trackScreen(Screens.confirmInsurance(this.ah.getNombreFamilia()));
    }

    private void b(View view) {
        c(view);
        y();
        z();
    }

    private void c(View view) {
        this.ag = (RecyclerView) view.findViewById(R.id.family_questions_form);
        this.ag.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        if (this.b.isEmpty()) {
            this.b.add(FormDataTitle.newInstance().setTitle(getContext().getString(R.string.ID_4134_SEGUROS_LBL_CONF_CONTR_SGRO)));
            this.b.add(FormDataHeader.newInstance().setTitle(Html.fromHtml(this.ah.getNombreFamilia()).toString()).setDescription("").setIconUrl(this.ah.getImagenDetalle()));
            this.b.addAll(this.c);
            this.b.addAll(this.d);
        }
        for (IFormData iFormData : this.b) {
            if (iFormData.getEditable().booleanValue()) {
                iFormData.setEditable(Boolean.valueOf(false));
            }
        }
        setLeyenda();
        setTerminosYCondiciones();
        this.ag.addItemDecoration(new ItemDecoratorForm(getContext(), this.b));
        this.ag.setItemViewCacheSize(this.b.size());
        this.b.add(FormDataButton.newInstance().setText(getString(R.string.F27_13_TITLE_DIALOG_CONFIRMAR)).setEnableButton(false));
    }

    private void y() {
        this.af = new FormAdapter(this.b, getFragmentManager(), getContext());
        this.af.setmOnIntentListener(new OnIntentListener() {
            public void startIntent(Class cls, Bundle bundle, int i) {
                Intent intent = new Intent(ConfirmarSeguroObjetosFragment.this.getActivity(), cls);
                intent.putExtras(bundle);
                ConfirmarSeguroObjetosFragment.this.startActivityForResult(intent, i);
            }
        });
        this.af.setmOnValidateControl(new OnValidateControl() {
            public void validateControl(Boolean bool) {
                ConfirmarSeguroObjetosFragment.this.af.setButtonEnable(bool.booleanValue());
            }
        });
        this.af.setOnButtonClickListener(new OnButtonClickListener() {
            public void setActionButtton() {
                ConfirmarSeguroObjetosFragment.this.showConfirmarMovilDialog();
            }
        });
        this.ag.setAdapter(this.af);
    }

    public void setLeyenda() {
        int size = this.b.size();
        String descripcion = this.e.cotizacion.getListaLeyendas().getLeyendaById("SEGOBJ_CON").getDescripcion();
        if (!descripcion.isEmpty()) {
            this.b.add(size, FormDataLeyend.newInstance(Constants.LOCALE_DEFAULT_APP).setValueText(descripcion));
        }
    }

    public void setTerminosYCondiciones() {
        Bundle bundle = new Bundle();
        bundle.putString(InfoActivity.TYPE_INFO, InfoType.DEFAULT.name());
        bundle.putString(InfoActivity.TITLE_TO_SHOW, getContext().getString(R.string.f183ID_4686_SEGUROS_TRMINOS_Y_CONDICIONES));
        bundle.putString(InfoActivity.TEXT_TO_SHOW, this.e.cotizacion.getListaLeyendas().getLeyendaById("SEGOBJ_TYC").getDescripcion());
        bundle.putString(InfoActivity.CONTENT_DESCRIPTION, getContext().getString(R.string.ID_4134_SEGUROS_LBL_CONF_CONTR_SGRO));
        int size = this.b.size();
        if (this.b.get(size - 1) instanceof FormDataLeyend) {
            size--;
        }
        this.b.add(size, FormCheckBox.newInstance(Constants.LOCALE_DEFAULT_APP).setDescription("He leído y acepto ").setLink("Términos y Condiciones").setIntentClass(InfoActivity.class).setBundle(bundle));
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.a = (OnFragmentInteractionListener) context;
            this.ai = (IActionCustom) context;
            this.aj = getActivity();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.a = null;
        this.ai = null;
    }

    private PlanSeguroBean a(List<PlanSeguroBean> list) {
        for (PlanSeguroBean planSeguroBean : list) {
            if (planSeguroBean.isChecked()) {
                return planSeguroBean;
            }
        }
        return null;
    }

    public void showConfirmarMovilDialog() {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getContext().getString(R.string.F27_13_TITLE_DIALOG_CONFIRMAR), getContext().getString(R.string.F27_34_BODY_CONFIRM_DIALOG), null, null, getContext().getString(R.string.IDX_ALERT_BTN_YES), getContext().getString(R.string.IDX_ALERT_BTN_NO), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                newInstance.dismiss();
                ConfirmarSeguroObjetosFragment.this.a.showProgressBar(VContratarSeguroObjeto.nameService);
                ContratarSeguroObjetoBodyRequestBean contratarSeguroObjetoBodyRequestBean = new ContratarSeguroObjetoBodyRequestBean(ConfirmarSeguroObjetosFragment.this.e.cotizacion.getCodRamo(), ConfirmarSeguroObjetosFragment.this.e.cotizacion.getCodProducto(), ConfirmarSeguroObjetosFragment.this.i.getSumaAsegurada(), ConfirmarSeguroObjetosFragment.this.e.cotizacion.getNumCotizacion(), ConfirmarSeguroObjetosFragment.this.i.getCodPlan(), ConfirmarSeguroObjetosFragment.this.ad, ConfirmarSeguroObjetosFragment.this.ae, ConfirmarSeguroObjetosFragment.this.f, ConfirmarSeguroObjetosFragment.this.g, ConfirmarSeguroObjetosFragment.this.h, ConfirmarSeguroObjetosFragment.this.ak, ConfirmarSeguroObjetosFragment.this.al);
                ConfirmarSeguroObjetosFragment.this.mDataManager.contratarSeguroObjeto(contratarSeguroObjetoBodyRequestBean);
            }
        });
        newInstance.show(getFragmentManager(), "isbanDialogConfirm");
    }

    @Subscribe
    public void onContratarSeguroObjeto(ContratarSeguroObjetoEvent contratarSeguroObjetoEvent) {
        this.a.dismissProgressBar();
        final ContratarSeguroObjetoEvent contratarSeguroObjetoEvent2 = contratarSeguroObjetoEvent;
        AnonymousClass5 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.FIRMA_SEGURO, (BaseActivity) getActivity(), getContext().getString(R.string.ID_4134_SEGUROS_LBL_CONF_CONTR_SGRO)) {
            /* access modifiers changed from: protected */
            public void onOk() {
                ConfirmarSeguroObjetosFragment.this.a.showComprobanteSeguroAccidente(contratarSeguroObjetoEvent2.getResponse().contratarSeguroObjetoBodyResponseBean, ConfirmarSeguroObjetosFragment.this.c, ConfirmarSeguroObjetosFragment.this.d, ConfirmarSeguroObjetosFragment.this.ah, ConfirmarSeguroObjetosFragment.this.e);
            }

            /* access modifiers changed from: protected */
            public void onRes5Error(WebServiceEvent webServiceEvent) {
                super.onRes5Error(webServiceEvent);
            }
        };
        r1.handleWSResponse(contratarSeguroObjetoEvent);
    }
}
