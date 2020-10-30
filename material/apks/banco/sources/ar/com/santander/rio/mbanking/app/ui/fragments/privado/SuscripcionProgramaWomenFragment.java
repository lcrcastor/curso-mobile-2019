package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.adapters.ProgramaWomenAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.ProgramaWomenAdapter.OnChangeSelectedStatusListener;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.LEGENDS_IDENTIFIERS;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.OPERATION_FLAG;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios;
import ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.Usuario;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.view.AnimatedExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

public class SuscripcionProgramaWomenFragment extends BaseFragment {
    GetListaTjWomenBodyResponseBean a;
    private HashMap<String, List<Tarjeta>> ad = new HashMap<>();
    private List<String> ae = new ArrayList();
    private ListaUsuarios af = new ListaUsuarios();
    private List<Tarjeta> ag = new ArrayList();
    private Integer ah = null;
    private Boolean ai = null;
    @Inject
    AnalyticsManager b;
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener c;
    private ListaUsuarios d;
    private AnimatedExpandableListView e;
    private String f = "1";
    /* access modifiers changed from: private */
    public Button g;
    /* access modifiers changed from: private */
    public String h;
    /* access modifiers changed from: private */
    public AnimatedExpandableListView i;

    public interface OnFragmentInteractionListener {
        void irAConfirmacionProgramaWomen(boolean z, String str);
    }

    public void onPause() {
        this.ah = Integer.valueOf(this.e.getLastVisiblePosition());
        this.ai = Boolean.valueOf(this.g.isEnabled());
        super.onPause();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.ah != null) {
            this.e.smoothScrollToPosition(this.ah.intValue());
        }
        if (this.ai != null) {
            this.g.setEnabled(this.ai.booleanValue());
        }
    }

    public static SuscripcionProgramaWomenFragment newInstance(SessionManager sessionManager, String str, GetListaTjWomenBodyResponseBean getListaTjWomenBodyResponseBean) {
        SuscripcionProgramaWomenFragment suscripcionProgramaWomenFragment = new SuscripcionProgramaWomenFragment();
        suscripcionProgramaWomenFragment.setArguments(new Bundle());
        suscripcionProgramaWomenFragment.d = getListaTjWomenBodyResponseBean.getListaUsuarios();
        suscripcionProgramaWomenFragment.h = str;
        suscripcionProgramaWomenFragment.a = getListaTjWomenBodyResponseBean;
        return suscripcionProgramaWomenFragment;
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_suscripcion_programa_women_fragment, viewGroup, false);
        b(inflate);
        setSeleccionarTarjetaMarcacionView(this.d);
        setOnClickListeners();
        return inflate;
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

    public void setSeleccionarTarjetaMarcacionView(ListaUsuarios listaUsuarios) {
        prepareListData(listaUsuarios);
        configureExpandableListView(this.e, this.af.getUsuario().size());
    }

    private void b(View view) {
        prepareListData(this.d);
        this.e = new AnimatedExpandableListView(getActivity());
        this.e = (AnimatedExpandableListView) view.findViewById(R.id.F26_04_EXP_LST_TARJETAS);
        this.g = (Button) view.findViewById(R.id.seleccionarButtom);
        ((TextView) view.findViewById(R.id.F26_04_LBL_TITLE)).setText(getResources().getString(R.string.ID_4697_WOMEN_SELECCIONARTARJETAS));
        this.e.setVisibility(0);
        TextView textView = (TextView) view.findViewById(R.id.textViewSubTitle);
        this.g.setText(getResources().getString(R.string.ID_4699_WOMEN_SELECCIONAR));
        if (this.h.equals(OPERATION_FLAG.SUSCRIBE)) {
            this.b.trackScreen(getResources().getString(R.string.analytics_trackevent_action_women_inicio_suscripcion));
            a(this.d);
            this.e.setAdapter(new ProgramaWomenAdapter(getActivity(), this.ae, this.ad, this.f));
            textView.setText(getResources().getString(R.string.ID_4698_WOMEN_SELECCIONALASTARJETASQUEQUERESSUSCRIBIRAWOMEN));
            return;
        }
        this.b.trackScreen(getResources().getString(R.string.analytics_trackevent_action_women_inicio_cancelacion));
        b(this.d);
        this.e.setAdapter(new ProgramaWomenAdapter(getActivity(), this.ae, this.ad, this.f));
        textView.setText(getResources().getString(R.string.ID_4711_WOMEN_SELECCIONALASTARJETASQUEQUERESDARDEBAJADEWOMEN));
    }

    public void prepareListData(ListaUsuarios listaUsuarios) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        for (Usuario usuario : this.a.getListaUsuarios().getUsuario()) {
            arrayList.add(usuario.getNombre());
            hashMap.put(usuario.getNombre(), usuario.getListaTarjetas().getTarjeta());
        }
    }

    public void configureExpandableListView(AnimatedExpandableListView animatedExpandableListView, int i2) {
        ProgramaWomenAdapter programaWomenAdapter = new ProgramaWomenAdapter(getActivity(), this.ae, this.ad, this.f);
        View inflate = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(R.layout.layout_programa_women_leyenda, null, false);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.F26_04_LNL_LEYENDA_1_WRAPPER);
        LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.F26_04_LNL_LEYENDA_2_WRAPPER);
        LinearLayout linearLayout3 = (LinearLayout) inflate.findViewById(R.id.F26_04_LNL_LEYENDA_3_WRAPPER);
        if (this.h.equalsIgnoreCase("B")) {
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(8);
            linearLayout3.setVisibility(8);
            this.g.setEnabled(false);
            animatedExpandableListView.setAdapter(programaWomenAdapter);
            programaWomenAdapter.setOnChangeSelectedStatusListener(new OnChangeSelectedStatusListener() {
                public void onChangeSelectedStatus(boolean z) {
                    if (z) {
                        SuscripcionProgramaWomenFragment.this.g.setEnabled(true);
                    } else {
                        SuscripcionProgramaWomenFragment.this.g.setEnabled(false);
                    }
                }
            });
            this.i = new AnimatedExpandableListView(getActivity());
            this.i = animatedExpandableListView;
            for (int i3 = 0; i3 < i2; i3++) {
                this.i.expandGroup(i3);
            }
            this.i.setOnGroupClickListener(new OnGroupClickListener() {
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                    if (SuscripcionProgramaWomenFragment.this.i.isGroupExpanded(i)) {
                        SuscripcionProgramaWomenFragment.this.i.collapseGroupWithAnimation(i);
                    } else {
                        SuscripcionProgramaWomenFragment.this.i.expandGroupWithAnimation(i);
                    }
                    return true;
                }
            });
        }
        if (this.h.equalsIgnoreCase("A")) {
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(0);
            linearLayout3.setVisibility(8);
            Leyenda leyendaByTag = this.a.getLeyendaByTag(LEGENDS_IDENTIFIERS.WOMEN_LEG);
            TextView textView = (TextView) inflate.findViewById(R.id.textViewLeyenda1);
            textView.setVisibility(0);
            textView.setText(Html.fromHtml(leyendaByTag.descripcion));
            animatedExpandableListView.addFooterView(inflate, null, false);
            try {
                textView.setContentDescription(CAccessibility.getInstance(getActivity().getApplicationContext()).applyFilterAmount(textView.getText().toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.g.setEnabled(false);
            animatedExpandableListView.setAdapter(programaWomenAdapter);
            programaWomenAdapter.setOnChangeSelectedStatusListener(new OnChangeSelectedStatusListener() {
                public void onChangeSelectedStatus(boolean z) {
                    if (z) {
                        SuscripcionProgramaWomenFragment.this.g.setEnabled(true);
                    } else {
                        SuscripcionProgramaWomenFragment.this.g.setEnabled(false);
                    }
                }
            });
            this.i = new AnimatedExpandableListView(getActivity());
            this.i = animatedExpandableListView;
            for (int i4 = 0; i4 < i2; i4++) {
                this.i.expandGroup(i4);
            }
            this.i.setOnGroupClickListener(new OnGroupClickListener() {
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                    if (SuscripcionProgramaWomenFragment.this.i.isGroupExpanded(i)) {
                        SuscripcionProgramaWomenFragment.this.i.collapseGroupWithAnimation(i);
                    } else {
                        SuscripcionProgramaWomenFragment.this.i.expandGroupWithAnimation(i);
                    }
                    return true;
                }
            });
        }
    }

    public void setOnClickListeners() {
        this.g.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SuscripcionProgramaWomenFragment.this.c.irAConfirmacionProgramaWomen(false, SuscripcionProgramaWomenFragment.this.h);
            }
        });
    }

    private void y() {
        this.ag.clear();
        this.af.getUsuario().clear();
        this.ae.clear();
        this.ad.clear();
    }

    private void a(ListaUsuarios listaUsuarios) {
        y();
        for (Usuario usuario : listaUsuarios.getUsuario()) {
            List<Tarjeta> tarjeta = usuario.getListaTarjetas().getTarjeta();
            ArrayList arrayList = new ArrayList();
            for (Tarjeta tarjeta2 : tarjeta) {
                if (tarjeta2.getMarcaWomen().equalsIgnoreCase("0") && tarjeta2.getSuscripcionHabilitada().equalsIgnoreCase("1")) {
                    arrayList.add(tarjeta2);
                }
            }
            if (arrayList.size() > 0) {
                this.ae.add(usuario.getNombre());
                this.af.getUsuario().add(usuario);
                this.ad.put(usuario.getNombre(), arrayList);
            }
        }
    }

    private void b(ListaUsuarios listaUsuarios) {
        y();
        for (Usuario usuario : listaUsuarios.getUsuario()) {
            List<Tarjeta> tarjeta = usuario.getListaTarjetas().getTarjeta();
            ArrayList arrayList = new ArrayList();
            for (Tarjeta tarjeta2 : tarjeta) {
                if (tarjeta2.getMarcaWomen().equalsIgnoreCase("1") && tarjeta2.getSuscripcionHabilitada().equalsIgnoreCase("1")) {
                    arrayList.add(tarjeta2);
                }
            }
            if (arrayList.size() > 0) {
                this.ae.add(usuario.getNombre());
                this.af.getUsuario().add(usuario);
                this.ad.put(usuario.getNombre(), arrayList);
            }
        }
    }
}
