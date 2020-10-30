package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.adapters.ProgramaWomenAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.LEGENDS_IDENTIFIERS;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.OPERATION_FLAG;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios;
import ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.Usuario;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.view.AnimatedExpandableListView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

public class ProgramaWomenFragment extends BaseFragment {
    @Inject
    AnalyticsManager a;
    private Button ad;
    private Button ae;
    private boolean af = false;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener b;
    private AnimatedExpandableListView c;
    private AnimatedExpandableListView d;
    private ProgramaWomenAdapter e;
    /* access modifiers changed from: private */
    public GetListaTjWomenBodyResponseBean f;
    private List<String> g;
    private HashMap<String, List<Tarjeta>> h;
    private ListaUsuarios i;

    public interface OnFragmentInteractionListener {
        void irASuscripcionProgramaWomenFragment(String str);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.b = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_programa_women, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.a.trackScreen(getResources().getString(R.string.analytics_trackevent_action_women_home));
        initialize();
        setInitialize(inflate);
        setOnClickListeners();
        return inflate;
    }

    public static ProgramaWomenFragment newInstance(ListaUsuarios listaUsuarios, List<String> list, HashMap<String, List<Tarjeta>> hashMap, boolean z, GetListaTjWomenBodyResponseBean getListaTjWomenBodyResponseBean) {
        ProgramaWomenFragment programaWomenFragment = new ProgramaWomenFragment();
        programaWomenFragment.setArguments(new Bundle());
        programaWomenFragment.af = z;
        programaWomenFragment.i = listaUsuarios;
        programaWomenFragment.h = hashMap;
        programaWomenFragment.g = list;
        programaWomenFragment.f = getListaTjWomenBodyResponseBean;
        return programaWomenFragment;
    }

    public void initialize() {
        this.e = new ProgramaWomenAdapter(getActivity(), this.g, this.h, "2");
        this.e.setExpandible(false);
    }

    public void setInitialize(View view) {
        TextView textView = (TextView) view.findViewById(R.id.F26_04_LBL_TITLE);
        TextView textView2 = (TextView) view.findViewById(R.id.textViewSubTitle);
        this.c = (AnimatedExpandableListView) view.findViewById(R.id.F26_04_EXP_LST_TARJETAS);
        this.c.setVisibility(0);
        TextView textView3 = (TextView) view.findViewById(R.id.textSinTenencias);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeSinLeyenda);
        this.ad = (Button) view.findViewById(R.id.F26_04_BTN_SUSCRIBIRME);
        this.ae = (Button) view.findViewById(R.id.cancelarSuscripcion);
        textView.setText(getResources().getString(R.string.ID_4692_WOMEN_WOMEN_LBL));
        textView2.setText(getResources().getString(R.string.ID_4693_WOMEN_TUSTARJETASSUSCRIPTASALPROGRAMA));
        this.ad.setText(getResources().getString(R.string.ID_4695_WOMEN_SUSCRIBIRTARJETAS));
        this.ae.setText(getResources().getString(R.string.ID_4696_WOMEN_DARDEBAJATARJETAS));
        String titular = this.f.getTitular();
        String suscriptoTitular = this.f.getSuscriptoTitular();
        if (titular.equalsIgnoreCase("S") && suscriptoTitular.equalsIgnoreCase("S")) {
            this.ad.setVisibility(0);
            this.ae.setVisibility(0);
        }
        if (titular.equalsIgnoreCase("S") && suscriptoTitular.equalsIgnoreCase("N")) {
            this.ad.setVisibility(0);
            this.ae.setVisibility(8);
        }
        if (titular.equalsIgnoreCase("S") && suscriptoTitular.equalsIgnoreCase("T")) {
            this.ad.setVisibility(8);
            this.ae.setVisibility(0);
        }
        if (titular.equalsIgnoreCase("N") && suscriptoTitular.equalsIgnoreCase("N")) {
            this.ad.setVisibility(8);
            this.ae.setVisibility(8);
        }
        if (!this.f.hasAtLeastOneWomenMark()) {
            this.d = new AnimatedExpandableListView(getActivity());
            String string = getString(R.string.ID_4692_WOMEN_WOMEN_SIN_TENENCIAS_TEXTO_SPANNEABLE);
            int indexOf = string.indexOf(getString(R.string.ID_4692_WOMEN_WOMEN_SIN_TENENCIAS_TEXTO_CLICKEABLE_SPANNEABLE));
            int length = getString(R.string.ID_4692_WOMEN_WOMEN_SIN_TENENCIAS_TEXTO_CLICKEABLE_SPANNEABLE).length() + indexOf;
            SpannableString spannableString = new SpannableString(string);
            AnonymousClass1 r8 = new ClickableSpan() {
                public void onClick(View view) {
                    Intent intent = new Intent(ProgramaWomenFragment.this.getActivity(), InfoActivity.class);
                    intent.putExtra(InfoActivity.TEXT_TO_SHOW, "https://santanderrio.com.ar/banco/online/banca-women/tarjeta-santander-rio-women");
                    intent.putExtra(InfoActivity.TYPE_INFO, InfoType.WEBVIEW_HTTP_NO_TITLE_WOMEN);
                    ProgramaWomenFragment.this.startActivity(intent);
                }
            };
            spannableString.setSpan(new UnderlineSpan(), indexOf, length, 33);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.generic_red)), indexOf, length, 33);
            spannableString.setSpan(r8, indexOf, length, 33);
            int indexOf2 = string.indexOf("Women");
            spannableString.setSpan(new StyleSpan(1), indexOf2, "Women".length() + indexOf2, 18);
            textView3.setText(TextUtils.concat(new CharSequence[]{Html.fromHtml(this.f.getLeyendaByTag(LEGENDS_IDENTIFIERS.WOMEN_HOME).descripcion.replace("\n", "")), spannableString}));
            textView3.setClickable(true);
            textView3.setMovementMethod(LinkMovementMethod.getInstance());
            relativeLayout.setVisibility(0);
            this.d.setVisibility(8);
            this.c.setVisibility(8);
            textView2.setVisibility(8);
            return;
        }
        setSeleccionarTarjetaMarcacionView();
    }

    public void setOnClickListeners() {
        if (this.ad.getVisibility() == 0) {
            if (this.af && y()) {
                this.b.irASuscripcionProgramaWomenFragment(OPERATION_FLAG.SUSCRIBE);
            }
            this.ad.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ProgramaWomenFragment.this.b.irASuscripcionProgramaWomenFragment(OPERATION_FLAG.SUSCRIBE);
                }
            });
        }
        this.ae.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProgramaWomenFragment.this.b.irASuscripcionProgramaWomenFragment(OPERATION_FLAG.UNSUSCRIBE);
            }
        });
    }

    private boolean y() {
        for (Usuario listaTarjetas : this.i.getUsuario()) {
            List<Tarjeta> tarjeta = listaTarjetas.getListaTarjetas().getTarjeta();
            ArrayList arrayList = new ArrayList();
            for (Tarjeta tarjeta2 : tarjeta) {
                if (tarjeta2.getMarcaWomen().equalsIgnoreCase("0") && tarjeta2.getSuscripcionHabilitada().equalsIgnoreCase("1")) {
                    arrayList.add(tarjeta2);
                }
            }
            if (arrayList.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public void configureExpandableListView(AnimatedExpandableListView animatedExpandableListView, int i2) {
        View inflate = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.layout_programa_women_leyenda, null, false);
        ((LinearLayout) inflate.findViewById(R.id.F26_04_LNL_LEYENDA_1_WRAPPER)).setVisibility(0);
        Leyenda leyendaByTag = this.f.getLeyendaByTag(LEGENDS_IDENTIFIERS.WOMEN_LEG);
        TextView textView = (TextView) inflate.findViewById(R.id.textViewLeyenda1);
        textView.setVisibility(0);
        textView.setText(Html.fromHtml(leyendaByTag.descripcion));
        TextView textView2 = (TextView) inflate.findViewById(R.id.lblTyC);
        View findViewById = inflate.findViewById(R.id.tycContainer);
        findViewById.setContentDescription(getString(R.string.ID_4692_WOMEN_WOMEN_VER_TERMINOYCONDICIONES));
        findViewById.setVisibility(0);
        findViewById.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ProgramaWomenFragment.this.getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.TITLE_TO_SHOW, "Términos y condiciones");
                intent.putExtra(InfoActivity.TEXT_TO_SHOW, ProgramaWomenFragment.this.f.getListaLeyendas().getLeyendaByTag(LEGENDS_IDENTIFIERS.WOMEN_TYC).descripcion);
                intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                intent.putExtra(InfoActivity.ICON_TO_SHOW, R.drawable.ic_close);
                ProgramaWomenFragment.this.getActivity().startActivity(intent);
            }
        });
        animatedExpandableListView.addFooterView(inflate, null, false);
        animatedExpandableListView.setAdapter(this.e);
        this.d = new AnimatedExpandableListView(getActivity());
        this.d = animatedExpandableListView;
        for (int i3 = 0; i3 < i2; i3++) {
            this.d.expandGroup(i3);
        }
        try {
            textView.setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterAmount(textView.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setSeleccionarTarjetaMarcacionView() {
        configureExpandableListView(this.c, this.g.size());
    }
}
