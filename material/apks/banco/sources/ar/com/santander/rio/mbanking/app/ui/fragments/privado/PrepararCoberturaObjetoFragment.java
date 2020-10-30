package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.ui.activities.FotoObjetoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.UbicacionObjetoActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter.OnChangeValuesListener;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter.OnIntentListener;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataIntent;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSection;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormData;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataIntent;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.utils.VerticalDividerItemDecoration;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetOcupacionesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreguntasFamiliaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.UbicacionBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetOcupaciones;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class PrepararCoberturaObjetoFragment extends BaseFragment {
    public static final String BUTTON_STATE_ARG = "BUTTON_STATE_ARG";
    public static final String FAMILIA_BEAN_SELECTED_ARG = "FAMILIA_BEAN_SELECTED";
    public static final String GET_COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN_ARG = "GET_COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN";
    public static final String GET_PREGUNTAS_FAMILIA_RESPONSE_BODY_BEAN_ARG = "GET_PREGUNTAS_FAMILIA_RESPONSE_BODY_BEAN";
    public static final String SEGOBJ_UBQ_ID = "SEGOBJ_UBQ";
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener a;
    /* access modifiers changed from: private */
    public FamiliaBean ad = new FamiliaBean();
    /* access modifiers changed from: private */
    public GetCotizacionSeguroObjetoBodyResponseBean ae = new GetCotizacionSeguroObjetoBodyResponseBean();
    private IFormDataIntent af;
    private String ag;
    private String ah;
    private String ai;
    /* access modifiers changed from: private */
    public String aj;
    /* access modifiers changed from: private */
    public String ak;
    /* access modifiers changed from: private */
    public UbicacionBean al;
    private String am = "Comprobantes del Objeto";
    private IActionCustom an;
    @Inject
    public AnalyticsManager analyticsManager;
    private FormAdapter b;
    private List<IFormData> c = new ArrayList();
    /* access modifiers changed from: private */
    public Button d;
    /* access modifiers changed from: private */
    public boolean e = false;
    private RecyclerView f;
    private IFormDataIntent g;
    private IFormDataIntent h;
    /* access modifiers changed from: private */
    public GetPreguntasFamiliaBodyResponseBean i = new GetPreguntasFamiliaBodyResponseBean();
    @Inject
    public IDataManager mDataManager;

    public interface OnFragmentInteractionListener extends IFragmentBase {
    }

    public static PrepararCoberturaObjetoFragment newInstance(FamiliaBean familiaBean, GetPreguntasFamiliaBodyResponseBean getPreguntasFamiliaBodyResponseBean, GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean) {
        PrepararCoberturaObjetoFragment prepararCoberturaObjetoFragment = new PrepararCoberturaObjetoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("FAMILIA_BEAN_SELECTED", familiaBean);
        bundle.putParcelable(GET_PREGUNTAS_FAMILIA_RESPONSE_BODY_BEAN_ARG, getPreguntasFamiliaBodyResponseBean);
        bundle.putParcelable(GET_COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN_ARG, getCotizacionSeguroObjetoBodyResponseBean);
        prepararCoberturaObjetoFragment.setArguments(bundle);
        return prepararCoberturaObjetoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.ad = (FamiliaBean) getArguments().getParcelable("FAMILIA_BEAN_SELECTED");
            this.i = (GetPreguntasFamiliaBodyResponseBean) getArguments().getParcelable(GET_PREGUNTAS_FAMILIA_RESPONSE_BODY_BEAN_ARG);
            this.ae = (GetCotizacionSeguroObjetoBodyResponseBean) getArguments().getParcelable(GET_COTIZACION_SEGURO_OBJETO_BODY_RESPONSE_BEAN_ARG);
        }
        this.ag = this.ae.cotizacion.getHtmlEjFotoIdObjeto();
        this.ah = this.ae.cotizacion.getHtmlEjFotoObjeto();
        this.ai = this.ae.cotizacion.getListaLeyendas().getLeyendaById(SEGOBJ_UBQ_ID).getDescripcion();
        this.c = A();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_preparar_cobertura_objeto, viewGroup, false);
        this.a.configureBackActionBar();
        ((TextView) inflate.findViewById(R.id.title).findViewById(R.id.functionality_title)).setText(this.am);
        ((TextView) inflate.findViewById(R.id.subtitle).findViewById(R.id.section_title)).setText("Necesitamos los siguientes comprobantes para acreditar tu propiedad");
        this.d = (Button) inflate.findViewById(R.id.btNext);
        this.d.setText(PagoTarjetasConstants.ISBAN_DIALOG_CONTINUE_BUTTON_TEXT);
        this.d.setEnabled(this.e);
        this.d.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PrepararCoberturaObjetoFragment.this.y();
            }
        });
        this.f = (RecyclerView) inflate.findViewById(R.id.form);
        this.f.setHasFixedSize(true);
        this.f.addItemDecoration(new VerticalDividerItemDecoration(getContext()));
        this.f.setLayoutManager(new LinearLayoutManager(getContext()));
        z();
        return inflate;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.analyticsManager.trackScreen(Screens.objectState(this.ad.getNombreFamilia()));
    }

    /* access modifiers changed from: private */
    public void y() {
        this.al = new UbicacionBean();
        this.al.setNombre(this.af.getValue().getString(UbicacionObjetoActivity.ALIAS_EXTRA));
        this.al.setLatitud(this.af.getValue().getString(UbicacionObjetoActivity.LATITUD_EXTRA));
        this.al.setLongitud(this.af.getValue().getString(UbicacionObjetoActivity.LONGITUD_EXTRA));
        this.aj = this.g.getValue().getString(FotoObjetoActivity.PHOTO_STRING_DATA_BASE_64_RESULT_EXTRA);
        this.ak = this.h.getValue().getString(FotoObjetoActivity.PHOTO_STRING_DATA_BASE_64_RESULT_EXTRA);
        this.a.showProgressBar(VGetOcupaciones.nameService);
        this.mDataManager.getOcupaciones();
    }

    @Subscribe
    public void onGetSeleccionarOcupacion(GetOcupacionesEvent getOcupacionesEvent) {
        this.a.dismissProgressBar();
        final GetOcupacionesEvent getOcupacionesEvent2 = getOcupacionesEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_OBJETO_COMPLETAR, (BaseActivity) getActivity(), this.am) {
            /* access modifiers changed from: protected */
            public void onOk() {
                List listOcupaciones = getOcupacionesEvent2.getResponse().getGetOcupacionesBodyResponseBean().getOcupaciones().getListOcupaciones();
                if (listOcupaciones != null && listOcupaciones.size() > 0) {
                    PrepararCoberturaObjetoFragment.this.a.changeFragment(CompletarSeguroObjetosFragment.newInstance(PrepararCoberturaObjetoFragment.this.ad, PrepararCoberturaObjetoFragment.this.i, PrepararCoberturaObjetoFragment.this.ae, PrepararCoberturaObjetoFragment.this.aj, PrepararCoberturaObjetoFragment.this.ak, PrepararCoberturaObjetoFragment.this.al, listOcupaciones), FragmentConstants.SEGURO_OBJETO_COMPLETAR);
                }
            }

            /* access modifiers changed from: protected */
            public void onRes5Error(WebServiceEvent webServiceEvent) {
                super.onRes5Error(webServiceEvent);
            }
        };
        r1.handleWSResponse(getOcupacionesEvent);
    }

    private void z() {
        this.b = new FormAdapter(this.c, getFragmentManager(), getContext());
        this.b.setmOnChangeValuesListener(new OnChangeValuesListener() {
            public void isValidListener(ErrorMessage errorMessage) {
                PrepararCoberturaObjetoFragment.this.e = errorMessage.getValid().booleanValue();
                PrepararCoberturaObjetoFragment.this.d.setEnabled(PrepararCoberturaObjetoFragment.this.e);
            }
        });
        this.b.setmOnIntentListener(new OnIntentListener() {
            public void startIntent(Class cls, Bundle bundle, int i) {
                Intent intent = new Intent(PrepararCoberturaObjetoFragment.this.getActivity(), cls);
                intent.putExtras(bundle);
                PrepararCoberturaObjetoFragment.this.startActivityForResult(intent, i);
            }
        });
        this.f.setAdapter(this.b);
    }

    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 == 10) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("SHOW_INSTRUCTIONS_ARG", false);
            this.g.setBundle(bundle);
        } else if (i2 == 11) {
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("SHOW_INSTRUCTIONS_ARG", false);
            this.h.setBundle(bundle2);
        }
        if (i3 == -1) {
            this.b.setValueOfIntent(intent.getExtras(), i2);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.a = (OnFragmentInteractionListener) context;
            this.an = (IActionCustom) context;
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
        this.an = null;
    }

    private List<IFormData> A() {
        ArrayList arrayList = new ArrayList();
        Bundle bundle = new Bundle();
        bundle.putString(FotoObjetoActivity.INSTRUCTIONS_ARG, this.ah);
        bundle.putBoolean("SHOW_INSTRUCTIONS_ARG", true);
        bundle.putString(FotoObjetoActivity.TITLE_INSTRUCTION, getContext().getString(R.string.ID_4806_SEGUROS_TIT_FOTOS_DEL_OBJETO));
        bundle.putString(FotoObjetoActivity.FAMILY_NAME, this.ad.getNombreFamilia());
        arrayList.add(new FormDataSection().setLabelText("Foto"));
        this.g = (IFormDataIntent) new FormDataIntent().setIntentClass(FotoObjetoActivity.class).setSelectedMessage("Modificar").setRequestId(Integer.valueOf(10)).setBundle(bundle).setLabelText("Foto del Objeto").setHint("Tomar").setRequired(Boolean.valueOf(true));
        arrayList.add(this.g);
        Bundle bundle2 = new Bundle();
        bundle2.putString(FotoObjetoActivity.INSTRUCTIONS_ARG, this.ag);
        bundle2.putString(FotoObjetoActivity.TITLE_INSTRUCTION, getContext().getString(R.string.ID_4809_LBL_NUMERO_DE_SERIE));
        bundle2.putBoolean("SHOW_INSTRUCTIONS_ARG", true);
        this.h = (IFormDataIntent) new FormDataIntent().setIntentClass(FotoObjetoActivity.class).setSelectedMessage("Modificar").setRequestId(Integer.valueOf(11)).setBundle(bundle2).setLabelText(getContext().getString(R.string.ID_4809_LBL_NUMERO_DE_SERIE)).setHint("Tomar").setRequired(Boolean.valueOf(true));
        arrayList.add(this.h);
        arrayList.add(new FormDataSection().setLabelText("Ubicación"));
        Bundle bundle3 = new Bundle();
        bundle3.putString(SEGOBJ_UBQ_ID, this.ai);
        bundle3.putString(FotoObjetoActivity.FAMILY_NAME, this.ad.getNombreFamilia());
        this.af = (IFormDataIntent) new FormDataIntent().setIntentClass(UbicacionObjetoActivity.class).setSelectedMessage("Modificar").setRequestId(Integer.valueOf(12)).setBundle(bundle3).setLabelText("Ubicación del Objeto").setHint("Confirmar").setRequired(Boolean.valueOf(true));
        arrayList.add(this.af);
        return arrayList;
    }
}
