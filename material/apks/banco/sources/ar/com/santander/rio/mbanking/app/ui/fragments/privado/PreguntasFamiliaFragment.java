package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.commons.imageloader.ImageLoaderFactory;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.forms.ErrorMessage;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter;
import ar.com.santander.rio.mbanking.app.ui.forms.FormAdapter.OnChangeValuesListener;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormData;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataDate;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataSelectionKeyValue;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.FormDataText;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormData;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataDate;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataSelectionKeyValue;
import ar.com.santander.rio.mbanking.app.ui.forms.fields.IFormDataText;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.utils.VerticalDividerItemDecoration;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroObjetoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.PreguntasFamiliaResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreguntasFamiliaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaPreguntasFamilia;
import ar.com.santander.rio.mbanking.services.soap.beans.body.OpcionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PreguntasFamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Respuesta;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroObjeto;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso.Builder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class PreguntasFamiliaFragment extends BaseFragment {
    public static final String BUTTON_STATE_ARG = "BUTTON_STATE_ARG";
    public static final String GET_FAMILIA_PREGUNTAS = "GET_FAMILIA_PREGUNTAS";
    public static final String PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN = "PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN";
    public static final String SELECTED_FAMILIA = "SELECTED_FAMILIA";
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener a;
    /* access modifiers changed from: private */
    public ListaPreguntasFamilia ad;
    /* access modifiers changed from: private */
    public GetPreguntasFamiliaBodyResponseBean ae;
    /* access modifiers changed from: private */
    public FamiliaBean af;
    private List<IFormData> ag;
    private View ah;
    private FormAdapter ai;
    /* access modifiers changed from: private */
    public boolean aj = false;
    private IActionCustom ak;
    @Inject
    public AnalyticsManager analyticsManager;
    private Context b;
    private FragmentActivity c;
    private RecyclerView d;
    private TextView e;
    /* access modifiers changed from: private */
    public ImageView f;
    private TextView g;
    private TextView h;
    /* access modifiers changed from: private */
    public Button i;
    @Inject
    public IDataManager mDataManager;

    public interface OnFragmentInteractionListener extends IFragmentBase {
    }

    static class fieldFactory {
        fieldFactory() {
        }

        public static FormData a(PreguntasFamiliaBean preguntasFamiliaBean) {
            Integer num;
            boolean equalsIgnoreCase = preguntasFamiliaBean.getRespuestaOpcional().equalsIgnoreCase("N");
            if (preguntasFamiliaBean.getTipoRespuesta().equals("01")) {
                Integer.valueOf(0);
                try {
                    num = Integer.valueOf(!preguntasFamiliaBean.getCantMaxCaracteres().isEmpty() ? Integer.parseInt(preguntasFamiliaBean.getCantMaxCaracteres()) : 100);
                } catch (Exception unused) {
                    num = Integer.valueOf(100);
                }
                return (FormData) FormDataText.newInstance(Constants.LOCALE_DEFAULT_APP).setMaxOfCharacters(num).setRequired(Boolean.valueOf(equalsIgnoreCase)).setLabelText(preguntasFamiliaBean.getTextoPregunta());
            } else if (preguntasFamiliaBean.getTipoRespuesta().equals("02")) {
                return (FormData) FormDataDate.newInstance(Constants.LOCALE_DEFAULT_APP).setRequired(Boolean.valueOf(equalsIgnoreCase)).setHint("Seleccionar").setLabelText(preguntasFamiliaBean.getTextoPregunta());
            } else {
                if (!preguntasFamiliaBean.getTipoRespuesta().equals("03")) {
                    return null;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (OpcionBean opcionBean : preguntasFamiliaBean.getListaOpciones().getOpcion()) {
                    linkedHashMap.put(opcionBean.getIdOpcion(), opcionBean.getTextoOpcion());
                }
                return (FormData) FormDataSelectionKeyValue.newInstance(Constants.LOCALE_DEFAULT_APP).setStringHashMapList(linkedHashMap).setHint("Seleccionar").setRequired(Boolean.valueOf(equalsIgnoreCase)).setLabelText(preguntasFamiliaBean.getTextoPregunta());
            }
        }
    }

    public static PreguntasFamiliaFragment newInstance(PreguntasFamiliaResponseBean preguntasFamiliaResponseBean, FamiliaBean familiaBean) {
        PreguntasFamiliaFragment preguntasFamiliaFragment = new PreguntasFamiliaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GET_FAMILIA_PREGUNTAS, preguntasFamiliaResponseBean.getPreguntasFamiliaBodyResponseBean.listaPreguntas);
        bundle.putParcelable("PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN", preguntasFamiliaResponseBean.getPreguntasFamiliaBodyResponseBean);
        bundle.putParcelable("SELECTED_FAMILIA", familiaBean);
        preguntasFamiliaFragment.setArguments(bundle);
        return preguntasFamiliaFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.ad = (ListaPreguntasFamilia) getArguments().getParcelable(GET_FAMILIA_PREGUNTAS);
            this.ae = (GetPreguntasFamiliaBodyResponseBean) getArguments().getParcelable("PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN");
            this.af = (FamiliaBean) getArguments().getParcelable("SELECTED_FAMILIA");
        }
        this.ag = new ArrayList();
        for (PreguntasFamiliaBean a2 : this.ad.getPregunta()) {
            this.ag.add(fieldFactory.a(a2));
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.ah = layoutInflater.inflate(R.layout.fragment_preguntas_familia, viewGroup, false);
        g(bundle);
        z();
        y();
        return this.ah;
    }

    private void y() {
        this.ai = new FormAdapter(this.ag, getFragmentManager(), getContext());
        this.ai.setmOnChangeValuesListener(new OnChangeValuesListener() {
            public void isValidListener(ErrorMessage errorMessage) {
                PreguntasFamiliaFragment.this.aj = errorMessage.getValid().booleanValue();
                PreguntasFamiliaFragment.this.i.setEnabled(PreguntasFamiliaFragment.this.aj);
            }
        });
        this.d.addItemDecoration(new VerticalDividerItemDecoration(this.b));
        this.d.setItemViewCacheSize(this.ag.size());
        this.d.setAdapter(this.ai);
    }

    private void g(Bundle bundle) {
        this.a.configureBackActionBar();
        this.e = (TextView) this.ah.findViewById(R.id.functionality_title);
        this.g = (TextView) this.ah.findViewById(R.id.familia_name);
        this.h = (TextView) this.ah.findViewById(R.id.familia_description);
        this.f = (ImageView) this.ah.findViewById(R.id.familia_icon);
        this.d = (RecyclerView) this.ah.findViewById(R.id.family_questions_form);
        this.i = (Button) this.ah.findViewById(R.id.qoute_button);
        this.i.setEnabled(this.aj);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        this.e.setText(Html.fromHtml(getString(R.string.ID_4027_SEGUROS_LBL_SGRO, this.af.getNombreFamilia())));
        this.i.setText(getString(R.string.IDXX_SEGUROS_BTN_COTIZAR));
        this.d.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        this.g.setText(Html.fromHtml(this.af.getNombreFamilia()));
        this.h.setText("Para poder ofrecerte un plan a tu medida necesitamos que contestes algunas preguntas");
        this.h.setVisibility(0);
        this.i.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreguntasFamiliaFragment.this.a.showProgressBar(VGetCotizacionSeguroObjeto.nameService);
                PreguntasFamiliaFragment.this.ae.getListaPreguntas().setPregunta(PreguntasFamiliaFragment.this.a(PreguntasFamiliaFragment.this.ad.getPregunta()));
                PreguntasFamiliaFragment.this.mDataManager.getCotizacionSeguroObjeto(PreguntasFamiliaFragment.this.af.getIdFamilia(), PreguntasFamiliaFragment.this.ae.getListaPreguntas());
            }
        });
        this.analyticsManager.trackScreen(Screens.objectToContract(this.af.getNombreFamilia()));
    }

    /* access modifiers changed from: private */
    public List<PreguntasFamiliaBean> a(List<PreguntasFamiliaBean> list) {
        List<PreguntasFamiliaBean> clone = PreguntasFamiliaBean.clone(list);
        for (PreguntasFamiliaBean preguntasFamiliaBean : clone) {
            preguntasFamiliaBean.setRespuesta(a((IFormData) this.ag.get(clone.indexOf(preguntasFamiliaBean))));
        }
        return clone;
    }

    private static Respuesta a(IFormData iFormData) {
        Respuesta respuesta = new Respuesta();
        if (iFormData instanceof IFormDataSelectionKeyValue) {
            IFormDataSelectionKeyValue iFormDataSelectionKeyValue = (IFormDataSelectionKeyValue) iFormData;
            respuesta.setIdOpcion(iFormDataSelectionKeyValue.getKeyValue().getKey());
            respuesta.setTextoOpcion(iFormDataSelectionKeyValue.getKeyValue().getText());
            return respuesta;
        } else if (iFormData instanceof IFormDataText) {
            respuesta.setTextoIngresado(iFormData.getValueText());
            return respuesta;
        } else if (!(iFormData instanceof IFormDataDate)) {
            return null;
        } else {
            respuesta.setFechaSeleccionada(iFormData.getValueText());
            return respuesta;
        }
    }

    @Subscribe
    public void onGetCotizacionSeguroObjeto(GetCotizacionSeguroObjetoEvent getCotizacionSeguroObjetoEvent) {
        this.a.dismissProgressBar();
        final GetCotizacionSeguroObjetoEvent getCotizacionSeguroObjetoEvent2 = getCotizacionSeguroObjetoEvent;
        AnonymousClass3 r1 = new BaseWSResponseHandler(this.c, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, this, (BaseActivity) this.c) {
            /* access modifiers changed from: protected */
            public void onOk() {
                PreguntasFamiliaFragment.this.a.changeFragment(CotizarCoberturaObjetoFragment.newInstance(getCotizacionSeguroObjetoEvent2.getGetCotizacionSeguroObjetoResponseBean(), PreguntasFamiliaFragment.this.ae, PreguntasFamiliaFragment.this.af), FragmentConstants.SEGURO_COTIZACION_COBERTURA);
            }

            /* access modifiers changed from: protected */
            public void onRes5Error(WebServiceEvent webServiceEvent) {
                super.onRes5Error(webServiceEvent);
            }
        };
        r1.handleWSResponse(getCotizacionSeguroObjetoEvent);
    }

    private void z() {
        ImageLoaderFactory.getImageLoader(getContext()).loadImage(this.af.getImagenDetalle(), this.f);
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(500, TimeUnit.MILLISECONDS);
        new Builder(getContext()).downloader(new OkHttpDownloader(okHttpClient)).build().load(this.af.getImagenDetalle()).into(this.f, new Callback() {
            public void onError() {
                PreguntasFamiliaFragment.this.f.setImageResource(R.drawable.picture_g);
                PreguntasFamiliaFragment.this.f.setVisibility(0);
            }

            public void onSuccess() {
                PreguntasFamiliaFragment.this.f.setVisibility(0);
            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.a = (OnFragmentInteractionListener) context;
            this.ak = (IActionCustom) context;
            this.b = context;
            this.c = getActivity();
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
        this.ak = null;
    }
}
