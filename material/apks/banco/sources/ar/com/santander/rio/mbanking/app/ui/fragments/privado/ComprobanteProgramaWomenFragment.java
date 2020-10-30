package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.adapters.ProgramaWomenAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.LEGENDS_IDENTIFIERS;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.OPERATION_FLAG;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios;
import ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.SuscripcionWomenBodyResponseBean;
import ar.com.santander.rio.mbanking.view.AnimatedExpandableListView;
import butterknife.ButterKnife;
import com.squareup.otto.Bus;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

public class ComprobanteProgramaWomenFragment extends ITRSABaseFragment {
    @Inject
    AnalyticsManager a;
    private TextView ad;
    private View ae;
    private Bus af;
    private SuscripcionWomenBodyResponseBean ag;
    private AnimatedExpandableListView b;
    private ProgramaWomenAdapter c;
    private ListaUsuarios d;
    private List<String> e;
    private HashMap<String, List<Tarjeta>> f;
    private Button g;
    private String h = "A";
    private TextView i;

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_comprobante_programa_women, viewGroup, false);
        this.ae = inflate.findViewById(R.id.comprobante_view);
        ButterKnife.inject((Object) this, inflate);
        initializeView(inflate);
        setOnClickListeners();
        a(this.d);
        configureShareReceipt(this.ae, this.ag.getNroComprobante(), getString(R.string.IDXX_SHARE_WOMEN));
        createActionBarSharedreceipt();
        this.af.register(this);
        return inflate;
    }

    public static ComprobanteProgramaWomenFragment newInstance(ListaUsuarios listaUsuarios, List<String> list, HashMap<String, List<Tarjeta>> hashMap, SuscripcionWomenBodyResponseBean suscripcionWomenBodyResponseBean, String str, Bus bus) {
        ComprobanteProgramaWomenFragment comprobanteProgramaWomenFragment = new ComprobanteProgramaWomenFragment();
        comprobanteProgramaWomenFragment.setArguments(new Bundle());
        comprobanteProgramaWomenFragment.d = listaUsuarios;
        comprobanteProgramaWomenFragment.f = hashMap;
        comprobanteProgramaWomenFragment.e = list;
        comprobanteProgramaWomenFragment.h = str;
        comprobanteProgramaWomenFragment.ag = suscripcionWomenBodyResponseBean;
        comprobanteProgramaWomenFragment.af = bus;
        return comprobanteProgramaWomenFragment;
    }

    public void initializeView(View view) {
        this.b = (AnimatedExpandableListView) view.findViewById(R.id.F26_04_EXP_LST_TARJETAS);
        this.i = (TextView) view.findViewById(R.id.F26_04_LBL_TITLE);
        this.ad = (TextView) view.findViewById(R.id.textTitleTextView);
        this.g = (Button) view.findViewById(R.id.confirmarButton);
    }

    public void setOnClickListeners() {
        this.g.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ComprobanteProgramaWomenFragment.this.canExit();
            }
        });
    }

    public void configureExpandableListView(ListaUsuarios listaUsuarios, int i2) {
        this.c = new ProgramaWomenAdapter(getActivity(), this.e, this.f, this.h);
        this.c.setExpandible(false);
        View inflate = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.layout_tarjetas_women_comprobante_footer, null, false);
        TextView textView = (TextView) inflate.findViewById(R.id.SUSCRIP_TARJETAS_WOMEN_LEYENDA_TEXT_VIEW);
        TextView textView2 = (TextView) inflate.findViewById(R.id.SUSCRIP_TARJETAS_WOMEN_COMPROBANTE_NUEMERO_VALUE_TEXT_VIEW);
        TextView textView3 = (TextView) inflate.findViewById(R.id.SUSCRIP_TARJETAS_WOMEN_COMPROBANTE_FECHA_VALUE_TEXT_VIEW);
        this.b.addFooterView(inflate, null, false);
        this.b.setAdapter(this.c);
        if (this.h.equals(OPERATION_FLAG.SUSCRIBE)) {
            this.a.trackScreen(getResources().getString(R.string.analytics_trackevent_action_women_comprobante_suscripcion));
            for (List<Tarjeta> it : this.f.values()) {
                for (Tarjeta tipoTarjeta : it) {
                    tipoTarjeta.getTipoTarjeta();
                }
            }
            textView.setText(Html.fromHtml(this.ag.getLeyendaByTag(LEGENDS_IDENTIFIERS.WOMEN_INF_SUSC).descripcion));
        } else if (this.h.equals(OPERATION_FLAG.UNSUSCRIBE)) {
            this.a.trackScreen(getResources().getString(R.string.analytics_trackevent_action_women_comprobante_cancelacion));
            textView.setText(Html.fromHtml(this.ag.getLeyendaByTag(LEGENDS_IDENTIFIERS.WOMEN_INF_CMP_C).descripcion));
        }
        textView2.setText(this.ag.getNroComprobante());
        textView3.setText(this.ag.getFechaOperacion());
        try {
            textView3.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterDate(textView3.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            textView2.setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterCharacterToCharacter(textView2.getText().toString()));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        for (int i3 = 0; i3 < i2; i3++) {
            this.b.expandGroup(i3);
        }
    }

    private void a(ListaUsuarios listaUsuarios) {
        configureExpandableListView(listaUsuarios, this.e.size());
        if (this.h.equals(OPERATION_FLAG.SUSCRIBE)) {
            this.i.setText(getString(R.string.COMPROBANTE_DE_SUSCRIPCION_WOMEN_TITLE_LABEL));
        } else if (this.h.equals(OPERATION_FLAG.UNSUSCRIBE)) {
            this.i.setText(getString(R.string.COMPROBANTE_DE_CANCELACION_WOMEN_TITLE_LABEL));
        }
        if (this.h.equals(OPERATION_FLAG.SUSCRIBE)) {
            this.ad.setText(getString(R.string.COMPROBANTE_DE_SUSCRIPCION_WOMEN_SUBTITLE_LABEL));
        } else if (this.h.equals(OPERATION_FLAG.UNSUSCRIBE)) {
            this.ad.setText(getString(R.string.COMPROBANTE_DE_CANCELACION_WOMEN_SUBTITLE_LABEL));
        }
    }
}
