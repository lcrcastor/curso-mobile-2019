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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler.IActionCustom;
import ar.com.santander.rio.mbanking.app.base.IFragmentBase;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.adapters.CotizarCoberturaObjetoAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.idLeyenda;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.analytics.InsuranceAnalytics.Screens;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetCotizacionSeguroObjetoEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CotizacionSeguroObjetoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreguntasFamiliaBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LeyendaItem;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendaSeguroMovil;
import ar.com.santander.rio.mbanking.services.soap.beans.body.PlanSeguroBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetCotizacionSeguroObjeto;
import ar.com.santander.rio.mbanking.utils.itrsa.HtmlTextView;
import com.squareup.otto.Subscribe;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.inject.Inject;

public class CotizarCoberturaObjetoFragment extends BaseFragment {
    public static final String GET_COTIZACIONES = "GET_COTIZACIONES";
    public static final String PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN = "PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN";
    public static final String PREPARAR_COBERTURA_OBJETO_FRAGMENT_TAG = "PREPARAR_COBERTURA_OBJETO_FRAGMENT";
    public static final String SELECTED_FAMILIA = "SELECTED_FAMILIA";
    /* access modifiers changed from: private */
    public OnFragmentInteractionListener a;
    /* access modifiers changed from: private */
    public ListaLeyendaSeguroMovil ad;
    private View ae;
    /* access modifiers changed from: private */
    public SeekBar af;
    /* access modifiers changed from: private */
    public GetCotizacionSeguroObjetoBodyResponseBean ag;
    /* access modifiers changed from: private */
    public CotizacionSeguroObjetoBean ah;
    /* access modifiers changed from: private */
    public ArrayList<String> ai = new ArrayList<>();
    /* access modifiers changed from: private */
    public CotizarCoberturaObjetoAdapter aj;
    /* access modifiers changed from: private */
    public GetPreguntasFamiliaBodyResponseBean ak;
    /* access modifiers changed from: private */
    public FamiliaBean al;
    /* access modifiers changed from: private */
    public int am;
    private PlanSeguroBean an = null;
    @Inject
    public AnalyticsManager analyticsManager;
    private FragmentActivity ao;
    private IActionCustom ap;
    /* access modifiers changed from: private */
    public int aq = 0;
    private TextView b;
    private TextView c;
    private TextView d;
    /* access modifiers changed from: private */
    public HtmlTextView e;
    private RecyclerView f;
    /* access modifiers changed from: private */
    public TextView g;
    private RelativeLayout h;
    private Button i;
    @Inject
    public IDataManager mDataManager;

    public interface OnFragmentInteractionListener extends IFragmentBase {
    }

    public static CotizarCoberturaObjetoFragment newInstance(GetCotizacionSeguroObjetoResponseBean getCotizacionSeguroObjetoResponseBean, GetPreguntasFamiliaBodyResponseBean getPreguntasFamiliaBodyResponseBean, FamiliaBean familiaBean) {
        CotizarCoberturaObjetoFragment cotizarCoberturaObjetoFragment = new CotizarCoberturaObjetoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GET_COTIZACIONES, getCotizacionSeguroObjetoResponseBean.getCotizacionSeguroObjetoBodyResponseBean.cotizacion);
        bundle.putParcelable("GET_COTIZACIONES_RESPONSE_BEAN", getCotizacionSeguroObjetoResponseBean.getCotizacionSeguroObjetoBodyResponseBean);
        bundle.putParcelable("PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN", getPreguntasFamiliaBodyResponseBean);
        bundle.putParcelable("SELECTED_FAMILIA", familiaBean);
        cotizarCoberturaObjetoFragment.setArguments(bundle);
        getCotizacionSeguroObjetoResponseBean.getCotizacionSeguroObjetoBodyResponseBean.cotizacion.getListaLeyendas().getLeyendaById(idLeyenda.COBERTURA_OBJETO_DETALLE);
        return cotizarCoberturaObjetoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.ad = new ListaLeyendaSeguroMovil();
            this.ah = (CotizacionSeguroObjetoBean) getArguments().getParcelable(GET_COTIZACIONES);
            this.ad.setLeyenda(this.ah.getListaLeyendas().getLeyenda());
            this.ag = (GetCotizacionSeguroObjetoBodyResponseBean) getArguments().getParcelable("GET_COTIZACIONES_RESPONSE_BEAN");
            this.ak = (GetPreguntasFamiliaBodyResponseBean) getArguments().getParcelable("PREGUNTAS_FAMILIA_BODY_RESPONSE_BEAN");
            this.al = (FamiliaBean) getArguments().getParcelable("SELECTED_FAMILIA");
        }
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.ae = layoutInflater.inflate(R.layout.fragment_cotizar_cobertura_objeto, viewGroup, false);
        z();
        y();
        return this.ae;
    }

    private void y() {
        this.ai.clear();
        for (PlanSeguroBean desc : this.ah.getListaPlanes().getListaPlanes()) {
            this.ai.add(desc.getDesc());
        }
    }

    private void z() {
        this.a.configureBackActionBar();
        this.b = (TextView) this.ae.findViewById(R.id.functionality_title);
        this.h = (RelativeLayout) this.ae.findViewById(R.id.rlCobertura);
        this.c = (TextView) this.ae.findViewById(R.id.tvCoutaMensualValue);
        this.e = (HtmlTextView) this.ae.findViewById(R.id.htmltvValueCobertura);
        this.f = (RecyclerView) this.ae.findViewById(R.id.rvCobertura);
        this.g = (TextView) this.ae.findViewById(R.id.tvLabelSumaAsegurada);
        this.af = (SeekBar) this.ae.findViewById(R.id.sbSumaAsegurada);
        this.i = (Button) this.ae.findViewById(R.id.qoute_button);
        this.d = (TextView) this.ae.findViewById(R.id.leyenda);
        this.b.setText(getString(R.string.ID_4796_SEGUROS_TIT_COTIZAR_COBERTURA));
        this.d.setText(Html.fromHtml(this.ag.cotizacion.getListaLeyendas().getLeyendaById("SEGOBJ_LEG_SUM").getDescripcion()));
        this.aj = new CotizarCoberturaObjetoAdapter(getContext());
        this.f.setLayoutManager(new LinearLayoutManager(getContext()));
        this.f.setAdapter(this.aj);
        this.ae.findViewById(R.id.link_detalle_cobertura).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CotizarCoberturaObjetoFragment.this.ad != null && CotizarCoberturaObjetoFragment.this.ad.getLeyenda() != null) {
                    LeyendaItem leyendaItem = null;
                    Iterator it = CotizarCoberturaObjetoFragment.this.ad.getLeyenda().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        LeyendaItem leyendaItem2 = (LeyendaItem) it.next();
                        if (leyendaItem2.getIdLeyenda().equalsIgnoreCase(idLeyenda.COBERTURA_OBJETO_DETALLE)) {
                            leyendaItem = leyendaItem2;
                            break;
                        }
                    }
                    if (leyendaItem != null) {
                        CotizarCoberturaObjetoFragment.this.showDetalle(CotizarCoberturaObjetoFragment.this.getString(R.string.ID_4913_SEGUROS_LBL_MAS_INFORMACION_DE_LAS_DISTINTAS_OPCIONES_DEL_PRODUCTO), leyendaItem.getDescripcion());
                    }
                }
            }
        });
    }

    public void showDetalle(String str, String str2) {
        Intent intent = new Intent(getContext(), InfoActivity.class);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, str);
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str2);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.ICON_TO_SHOW, R.drawable.ic_close);
        intent.putExtra(InfoActivity.CONTENT_DESCRIPTION, getString(R.string.ID_4796_SEGUROS_TIT_COTIZAR_COBERTURA));
        getContext().startActivity(intent);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        if (this.an != null) {
            this.g.setText(getString(R.string.IDXX_SEGUROS_LBL_SUMA_ASEGURADA, this.an.getSumaAsegurada()));
            a(b(this.ah.getSumaAseguradaMinima()).intValue(), b(this.ah.getSumaAseguradaMaxima()).intValue(), b(this.ah.getRangoSalto()).intValue(), b(this.an.getSumaAsegurada()).intValue());
            a(this.an);
        } else {
            a((PlanSeguroBean) this.ah.getListaPlanes().getListaPlanes().get(0));
        }
        this.h.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("PlanSeleccionado", CotizarCoberturaObjetoFragment.this.getString(R.string.ID_4051_SEGUROS_LABEL_COBERTURA), null, CotizarCoberturaObjetoFragment.this.ai, PagoTarjetasConstants.ISBAN_DIALOG_CANCEL_BUTTON_TEXT, null, null, CotizarCoberturaObjetoFragment.this.e.getText().toString(), CotizarCoberturaObjetoFragment.this.ai);
                newInstance.setDialogListener(new IDialogListener() {
                    public void onNegativeButton() {
                    }

                    public void onPositiveButton() {
                    }

                    public void onSimpleActionButton() {
                    }

                    public void onItemSelected(String str) {
                        CotizarCoberturaObjetoFragment.this.aq = 0;
                        Iterator it = CotizarCoberturaObjetoFragment.this.ai.iterator();
                        while (it.hasNext()) {
                            if (((String) it.next()).equalsIgnoreCase(str)) {
                                CotizarCoberturaObjetoFragment.this.a((PlanSeguroBean) CotizarCoberturaObjetoFragment.this.ah.getListaPlanes().getListaPlanes().get(CotizarCoberturaObjetoFragment.this.aq));
                                return;
                            }
                            CotizarCoberturaObjetoFragment.this.aq = CotizarCoberturaObjetoFragment.this.aq + 1;
                        }
                    }
                });
                newInstance.show(CotizarCoberturaObjetoFragment.this.getFragmentManager(), "COBERTURADIALOG");
            }
        });
        this.i.setText(getString(R.string.ID_4049_SEGUROS_CONTINUAR));
        this.i.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CotizarCoberturaObjetoFragment.this.a.changeFragment(PrepararCoberturaObjetoFragment.newInstance(CotizarCoberturaObjetoFragment.this.al, CotizarCoberturaObjetoFragment.this.ak, CotizarCoberturaObjetoFragment.this.ag), CotizarCoberturaObjetoFragment.PREPARAR_COBERTURA_OBJETO_FRAGMENT_TAG);
            }
        });
        A();
        this.analyticsManager.trackScreen(Screens.insuranceQuote(this.al.getNombreFamilia()));
    }

    private void A() {
        try {
            this.c.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.c.getText().toString()));
            this.g.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterAmount(this.g.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(PlanSeguroBean planSeguroBean) {
        if (this.an == null) {
            this.g.setText(getString(R.string.IDXX_SEGUROS_LBL_SUMA_ASEGURADA, planSeguroBean.getSumaAsegurada()));
            a(b(this.ah.getSumaAseguradaMinima()).intValue(), b(this.ah.getSumaAseguradaMaxima()).intValue(), b(this.ah.getRangoSalto()).intValue(), b(planSeguroBean.getSumaAsegurada()).intValue());
        } else {
            this.an.setChecked(false);
        }
        this.an = planSeguroBean;
        this.an.setChecked(true);
        for (PlanSeguroBean planSeguroBean2 : this.ag.cotizacion.getListaPlanes().getListaPlanes()) {
            if (planSeguroBean2.getCodPlan().equalsIgnoreCase(this.an.getCodPlan())) {
                planSeguroBean2.setChecked(true);
            } else {
                planSeguroBean2.setChecked(false);
            }
        }
        this.e.setText(this.an.getDesc());
        this.c.setText(this.an.getCuota());
        this.aj.setList(this.an.getListaCoberturas().getListaCoberturas());
    }

    private void a(final int i2, int i3, final int i4, int i5) {
        this.af.setMax((i3 - i2) / i4);
        this.af.setProgress((i5 - i2) / i4);
        this.af.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                CotizarCoberturaObjetoFragment.this.am = i2 + (seekBar.getProgress() * i4);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                CotizarCoberturaObjetoFragment.this.af.setEnabled(false);
                CotizarCoberturaObjetoFragment.this.a.showProgressBar(VGetCotizacionSeguroObjeto.nameService);
                CotizarCoberturaObjetoFragment.this.mDataManager.getRecotizacionSeguroObjeto(CotizarCoberturaObjetoFragment.this.al.getIdFamilia(), CotizarCoberturaObjetoFragment.this.ah.getNumCotizacion(), CotizarCoberturaObjetoFragment.this.c(CotizarCoberturaObjetoFragment.this.am));
            }
        });
    }

    @Subscribe
    public void onGetRecotizacionSeguroObjeto(GetCotizacionSeguroObjetoEvent getCotizacionSeguroObjetoEvent) {
        this.af.setEnabled(true);
        this.a.dismissProgressBar();
        final GetCotizacionSeguroObjetoEvent getCotizacionSeguroObjetoEvent2 = getCotizacionSeguroObjetoEvent;
        AnonymousClass5 r1 = new BaseWSResponseHandler(this.ao, TypeOption.INTERMDIATE_VIEW, FragmentConstants.FONDOS_INVERSION, this, (BaseActivity) this.ao) {
            /* access modifiers changed from: protected */
            public void onOk() {
                CotizarCoberturaObjetoFragment.this.ai.clear();
                if (CotizarCoberturaObjetoFragment.this.ah != null) {
                    getCotizacionSeguroObjetoEvent2.getGetCotizacionSeguroObjetoResponseBean().getCotizacionSeguroObjetoBodyResponseBean.cotizacion.setSumaAseguradaMaxima(CotizarCoberturaObjetoFragment.this.ah.getSumaAseguradaMaxima());
                    getCotizacionSeguroObjetoEvent2.getGetCotizacionSeguroObjetoResponseBean().getCotizacionSeguroObjetoBodyResponseBean.cotizacion.setSumaAseguradaMinima(CotizarCoberturaObjetoFragment.this.ah.getSumaAseguradaMinima());
                    getCotizacionSeguroObjetoEvent2.getGetCotizacionSeguroObjetoResponseBean().getCotizacionSeguroObjetoBodyResponseBean.cotizacion.setListaLeyendas(CotizarCoberturaObjetoFragment.this.ah.getListaLeyendas());
                    getCotizacionSeguroObjetoEvent2.getGetCotizacionSeguroObjetoResponseBean().getCotizacionSeguroObjetoBodyResponseBean.cotizacion.setHtmlEjFotoIdObjeto(CotizarCoberturaObjetoFragment.this.ah.getHtmlEjFotoIdObjeto());
                    getCotizacionSeguroObjetoEvent2.getGetCotizacionSeguroObjetoResponseBean().getCotizacionSeguroObjetoBodyResponseBean.cotizacion.setHtmlEjFotoObjeto(CotizarCoberturaObjetoFragment.this.ah.getHtmlEjFotoObjeto());
                }
                CotizarCoberturaObjetoFragment.this.ag.cotizacion = getCotizacionSeguroObjetoEvent2.getGetCotizacionSeguroObjetoResponseBean().getCotizacionSeguroObjetoBodyResponseBean.cotizacion;
                CotizarCoberturaObjetoFragment.this.ah = CotizarCoberturaObjetoFragment.this.ag.cotizacion;
                if (CotizarCoberturaObjetoFragment.this.ah.getListaPlanes().getListaPlanes().size() > 0) {
                    for (PlanSeguroBean desc : CotizarCoberturaObjetoFragment.this.ah.getListaPlanes().getListaPlanes()) {
                        CotizarCoberturaObjetoFragment.this.ai.add(desc.getDesc());
                    }
                    CotizarCoberturaObjetoFragment.this.aj.notifyDataSetChanged();
                    CotizarCoberturaObjetoFragment.this.g.setText(CotizarCoberturaObjetoFragment.this.getString(R.string.IDXX_SEGUROS_LBL_SUMA_ASEGURADA, CotizarCoberturaObjetoFragment.this.c(CotizarCoberturaObjetoFragment.this.am)));
                    CotizarCoberturaObjetoFragment.this.a((PlanSeguroBean) CotizarCoberturaObjetoFragment.this.ah.getListaPlanes().getListaPlanes().get(CotizarCoberturaObjetoFragment.this.aq));
                }
            }

            /* access modifiers changed from: protected */
            public void onRes5Error(WebServiceEvent webServiceEvent) {
                super.onRes5Error(webServiceEvent);
            }
        };
        r1.handleWSResponse(getCotizacionSeguroObjetoEvent);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.a = (OnFragmentInteractionListener) context;
            this.ap = (IActionCustom) context;
            this.ao = getActivity();
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
        this.ap = null;
    }

    private Integer b(String str) {
        return Integer.valueOf(Integer.parseInt(str.replace("$", "").trim().replace(".", "").split(",")[0]));
    }

    /* access modifiers changed from: private */
    public String c(int i2) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);
        decimalFormat.applyPattern("$ ###,###.##");
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat.format((double) i2));
        sb.append(",00");
        return sb.toString();
    }
}
