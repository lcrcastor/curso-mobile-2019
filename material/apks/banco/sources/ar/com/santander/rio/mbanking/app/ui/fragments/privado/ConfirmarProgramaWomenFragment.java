package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.base.ReceiptEvent;
import ar.com.santander.rio.mbanking.app.base.ReceiptEventBus;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.ProgramaWomenActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.ProgramaWomenAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.LEGENDS_IDENTIFIERS;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.OPERATION_FLAG;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.SuscripcionWomenEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.CustomDimenssion;
import ar.com.santander.rio.mbanking.services.model.general.EventTracker;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios;
import ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.Usuario;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscripcionWomenBodyResponseBean;
import ar.com.santander.rio.mbanking.view.AnimatedExpandableListView;
import butterknife.ButterKnife;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

public class ConfirmarProgramaWomenFragment extends BaseFragment {
    public static final String PROGRAMA_WOMEN_SUSCRIPCION = "Programa women suscripcion";
    @Inject
    AnalyticsManager a;
    /* access modifiers changed from: private */
    public HashMap<String, List<Tarjeta>> ad;
    /* access modifiers changed from: private */
    public Button ae;
    /* access modifiers changed from: private */
    public String af = "A";
    /* access modifiers changed from: private */
    public ImageView ag;
    private LinearLayout ah;
    /* access modifiers changed from: private */
    public boolean ai;
    /* access modifiers changed from: private */
    public Bus aj;
    private BaseActivity ak;
    private boolean al = false;
    @Inject
    SessionManager b;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener c;
    private AnimatedExpandableListView d;
    private ProgramaWomenAdapter e;
    private GetListaTjWomenBodyResponseBean f;
    /* access modifiers changed from: private */
    public ListaLeyendas g;
    /* access modifiers changed from: private */
    public ListaUsuarios h;
    /* access modifiers changed from: private */
    public List<String> i;

    public interface OnFragmentInteractionListener {
        void confirmarAccionProgramaWomen(ProgramaWomenActivity programaWomenActivity, String str, List<Tarjeta> list);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            this.c = (OnFragmentInteractionListener) activity;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(activity.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_confirmar_programa_women, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        initialize();
        this.ai = false;
        setInitialize(inflate);
        a(this.h);
        return inflate;
    }

    public static ConfirmarProgramaWomenFragment newInstance(ListaUsuarios listaUsuarios, List<String> list, HashMap<String, List<Tarjeta>> hashMap, boolean z, ListaLeyendas listaLeyendas, String str, GetListaTjWomenBodyResponseBean getListaTjWomenBodyResponseBean, Bus bus, BaseActivity baseActivity) {
        ConfirmarProgramaWomenFragment confirmarProgramaWomenFragment = new ConfirmarProgramaWomenFragment();
        Bundle bundle = new Bundle();
        confirmarProgramaWomenFragment.h = listaUsuarios;
        confirmarProgramaWomenFragment.ad = hashMap;
        confirmarProgramaWomenFragment.i = list;
        confirmarProgramaWomenFragment.g = listaLeyendas;
        confirmarProgramaWomenFragment.af = str;
        confirmarProgramaWomenFragment.f = getListaTjWomenBodyResponseBean;
        confirmarProgramaWomenFragment.aj = bus;
        confirmarProgramaWomenFragment.ak = baseActivity;
        confirmarProgramaWomenFragment.setArguments(bundle);
        return confirmarProgramaWomenFragment;
    }

    public void initialize() {
        this.d = new AnimatedExpandableListView(getActivity());
        this.e = new ProgramaWomenAdapter(getActivity(), this.i, this.ad, this.af);
        this.e.setExpandible(false);
    }

    public void setInitialize(View view) {
        this.d = (AnimatedExpandableListView) view.findViewById(R.id.F26_04_EXP_LST_TARJETAS);
        this.d.setVisibility(0);
        TextView textView = (TextView) view.findViewById(R.id.F26_04_LBL_TITLE);
        this.ae = (Button) view.findViewById(R.id.confirmarButton);
        this.ae.setText(getString(R.string.F24_21_btn_confirmar));
        this.d.setAdapter(this.e);
        this.ag = (ImageView) view.findViewById(R.id.F12_03_img_checkbox_tyc);
        this.ah = (LinearLayout) view.findViewById(R.id.linearLayoutAceptoTYC);
        this.ag.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_checkbox_unchecked, null));
        TextView textView2 = (TextView) view.findViewById(R.id.textTitleTextView);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.F12_03_rll_grupo6);
        this.d.setVisibility(0);
        this.ae.setContentDescription(getString(R.string.ID_4692_WOMEN_WOMEN_BOTON_CONFIRMAR_DESACTIVADO));
        this.ag.setContentDescription(getString(R.string.ID_4692_WOMEN_WOMEN_CHECKBOX_TERMINOYCONDICIONES_NO_SELECCIONADO));
        this.ah.setContentDescription(getString(R.string.ID_4692_WOMEN_WOMEN_TERMINOYCONDICIONES));
        EventTracker eventTracker = new EventTracker();
        if (this.af.equalsIgnoreCase(OPERATION_FLAG.SUSCRIBE)) {
            this.a.trackScreen(getResources().getString(R.string.analytics_trackevent_action_women_confirmacion_suscripcion));
            textView.setText(getResources().getString(R.string.ID_4700_WOMEN_CONFIRMARSUSCRIPCIONAWOMEN));
            textView2.setText(getResources().getString(R.string.ID_4701_WOMEN_CONFIRMARLASUSCRIPCIONDELASTARJETASAWOMEN));
            relativeLayout.setVisibility(0);
            this.ae.setEnabled(false);
            Button button = this.ae;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.ID_4692_WOMEN_WOMEN_BOTON_CONFIRMAR_ACTIVADO));
            sb.append(" la suscripción de las tarjetas a women");
            button.setContentDescription(sb.toString());
            eventTracker = a("Suscripciones", "Suscripción", "Suscrito");
        } else if (this.af.equalsIgnoreCase(OPERATION_FLAG.UNSUSCRIBE)) {
            eventTracker = a("Cancelaciones", "Cancelación", "No Suscrito");
            this.a.trackScreen(getResources().getString(R.string.analytics_trackevent_action_women_confirmacion_cancelacion));
            textView.setText(getResources().getString(R.string.ID_4712_WOMEN_CANCELARSUSCRIPCION));
            textView2.setText(getResources().getString(R.string.ID_4713_WOMEN_CONFIRMALASTARJETASADARDEBAJADEWOMEN));
            relativeLayout.setVisibility(8);
            this.ae.setEnabled(true);
            this.ai = true;
            Button button2 = this.ae;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getString(R.string.ID_4692_WOMEN_WOMEN_BOTON_CONFIRMAR_ACTIVADO));
            sb2.append(" las tarjetas a dar de baja de women");
            button2.setContentDescription(sb2.toString());
        }
        this.a.trackCustomDimension(getResources().getString(R.string.analytics_trackevent_category_watson_apertura), eventTracker);
        setOnClickListeners();
    }

    private EventTracker a(String str, String str2, String str3) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CustomDimenssion(1, this.b.getNup(), ""));
        arrayList.add(new CustomDimenssion(2, "Cliente Santander Rio", ""));
        arrayList.add(new CustomDimenssion(3, this.b.getSession(), getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
        arrayList.add(new CustomDimenssion(4, this.b.getTipoCuenta(), getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
        arrayList.add(new CustomDimenssion(6, str3, getResources().getString(R.string.analytics_trackevent_action_watson_de_chat)));
        String str4 = "";
        for (List<Tarjeta> it : this.ad.values()) {
            for (Tarjeta tipoTarjeta : it) {
                str4 = str4.concat(tipoTarjeta.getTipoTarjeta()).concat("-");
            }
        }
        EventTracker eventTracker = new EventTracker(str, str4, "women", str2, arrayList);
        return eventTracker;
    }

    public void setOnClickListeners() {
        this.ah.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmarProgramaWomenFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, "Términos y condiciones");
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, ConfirmarProgramaWomenFragment.this.g.getLeyendaByTag(LEGENDS_IDENTIFIERS.WOMEN_TYC).descripcion);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.ICON_TO_SHOW, R.drawable.ic_close);
                ConfirmarProgramaWomenFragment.this.getActivity().startActivity(intent);
            }
        });
        this.ag.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ConfirmarProgramaWomenFragment.this.ai = !ConfirmarProgramaWomenFragment.this.ai;
                if (ConfirmarProgramaWomenFragment.this.ai) {
                    ConfirmarProgramaWomenFragment.this.ae.setEnabled(true);
                    ConfirmarProgramaWomenFragment.this.ag.setBackground(ResourcesCompat.getDrawable(ConfirmarProgramaWomenFragment.this.getResources(), R.drawable.ic_checkbox_checked, null));
                    ConfirmarProgramaWomenFragment.this.ag.setContentDescription(ConfirmarProgramaWomenFragment.this.getString(R.string.ID_4692_WOMEN_WOMEN_CHECKBOX_TERMINOYCONDICIONES_SELECCIONADO));
                    return;
                }
                ConfirmarProgramaWomenFragment.this.ae.setEnabled(false);
                ConfirmarProgramaWomenFragment.this.ag.setBackground(ResourcesCompat.getDrawable(ConfirmarProgramaWomenFragment.this.getResources(), R.drawable.ic_checkbox_unchecked, null));
                ConfirmarProgramaWomenFragment.this.ag.setContentDescription(ConfirmarProgramaWomenFragment.this.getString(R.string.ID_4692_WOMEN_WOMEN_CHECKBOX_TERMINOYCONDICIONES_NO_SELECCIONADO));
            }
        });
        this.ae.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ConfirmarProgramaWomenFragment.this.ai) {
                    ArrayList arrayList = new ArrayList();
                    for (List addAll : ConfirmarProgramaWomenFragment.this.ad.values()) {
                        arrayList.addAll(addAll);
                    }
                    ConfirmarProgramaWomenFragment.this.c.confirmarAccionProgramaWomen((ProgramaWomenActivity) ConfirmarProgramaWomenFragment.this.getActivity(), ConfirmarProgramaWomenFragment.this.af, arrayList);
                }
            }
        });
    }

    public void configureExpandableListView(ListaUsuarios listaUsuarios, int i2) {
        View inflate = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.layout_programa_women_leyenda, null, false);
        ((LinearLayout) inflate.findViewById(R.id.F26_04_LNL_LEYENDA_1_WRAPPER)).setVisibility(0);
        if (this.af.equalsIgnoreCase(OPERATION_FLAG.SUSCRIBE)) {
            Leyenda leyendaByTag = this.f.getLeyendaByTag(LEGENDS_IDENTIFIERS.WOMEN_INF_CONF);
            TextView textView = (TextView) inflate.findViewById(R.id.textViewLeyenda1);
            textView.setVisibility(0);
            textView.setText(Html.fromHtml(leyendaByTag.descripcion));
        }
        this.d.addFooterView(inflate, null, false);
        this.d.setAdapter(this.e);
        for (int i3 = 0; i3 < i2; i3++) {
            this.d.expandGroup(i3);
        }
        for (Usuario listaTarjetas : listaUsuarios.getUsuario()) {
            for (Tarjeta marcaWomen : listaTarjetas.getListaTarjetas().getTarjeta()) {
                if (marcaWomen.getMarcaWomen().equalsIgnoreCase("1")) {
                    break;
                }
            }
        }
    }

    private void a(ListaUsuarios listaUsuarios) {
        configureExpandableListView(listaUsuarios, this.i.size());
    }

    @Subscribe
    public void onWomenProgramSuscription(SuscripcionWomenEvent suscripcionWomenEvent) {
        this.ak.dismissProgress();
        final SuscripcionWomenEvent suscripcionWomenEvent2 = suscripcionWomenEvent;
        AnonymousClass4 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, PROGRAMA_WOMEN_SUSCRIPCION, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                SuscripcionWomenBodyResponseBean suscripcionWomenBodyResponseBean = suscripcionWomenEvent2.getResponse().getSuscripcionWomenBodyResponseBean();
                if (suscripcionWomenEvent2.getResult().equals(TypeResult.OK)) {
                    ReceiptEventBus.getInstance().post(new ReceiptEvent((ITRSABaseFragment) ComprobanteProgramaWomenFragment.newInstance(ConfirmarProgramaWomenFragment.this.h, ConfirmarProgramaWomenFragment.this.i, ConfirmarProgramaWomenFragment.this.ad, suscripcionWomenBodyResponseBean, ConfirmarProgramaWomenFragment.this.af, ConfirmarProgramaWomenFragment.this.aj), FragmentConstants.PROGRAMA_WOMEN_COMPROBANTE));
                    ConfirmarProgramaWomenFragment.this.getActivity().finish();
                }
            }
        };
        r1.handleWSResponse(suscripcionWomenEvent);
    }
}
