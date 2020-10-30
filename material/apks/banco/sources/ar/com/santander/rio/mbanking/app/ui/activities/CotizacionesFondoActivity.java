package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.funds.CotizacionesFondoPresenter;
import ar.com.santander.rio.mbanking.app.module.funds.CotizacionesFondoView;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.FondosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CuentaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.InformacionFondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.utils.UtilAmount;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class CotizacionesFondoActivity extends MvpPrivateMenuActivity implements CotizacionesFondoView {
    @InjectView(2131363053)
    ImageButton imgBtnSort;
    @InjectView(2131363054)
    TextView lblLegales;
    @InjectView(2131363056)
    LinearLayout lst_wrapper;
    ArrayList<CategoriaFondosBean> p = new ArrayList<>();
    ArrayList<CuentaFondosBean> q = new ArrayList<>();
    ArrayList<String> r = new ArrayList<>();
    LegalesFondosBean s;
    @InjectView(2131363073)
    CustomSpinner selectorTipoFondo;
    @InjectView(2131363074)
    CustomSpinner selectorValoresFondo;
    @Inject
    AnalyticsManager t;
    private final Boolean u = Boolean.valueOf(false);
    /* access modifiers changed from: private */
    public final Boolean v = Boolean.valueOf(true);
    /* access modifiers changed from: private */
    public CotizacionesFondoPresenter w;

    public void clearScreenData() {
    }

    public int getMainLayout() {
        return R.layout.layout_cotizaciones_fondo;
    }

    public void onPointerCaptureChanged(boolean z) {
    }

    public void initialize() {
        this.w = new CotizacionesFondoPresenter(this.mBus, this.mDataManager, this);
        this.w.attachView(this);
        this.p = getIntent().getParcelableArrayListExtra(FondosConstants.INTENT_EXTRA_CATEGORIAS);
        this.q = getIntent().getParcelableArrayListExtra("CUENTAS");
        this.s = (LegalesFondosBean) getIntent().getParcelableExtra(FondosConstants.INTENT_EXTRA_LEGALES);
        configureActionBar();
    }

    public void configureActionBar() {
        setActionBarType(ActionBarType.PUSH_CLOSE);
        this.mActionBar = getSupportActionBar().getCustomView();
        ImageView imageView = (ImageView) ButterKnife.findById(this.mActionBar, (int) R.id.ok);
        if (imageView != null) {
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    CotizacionesFondoActivity.this.onBackPressed();
                }
            });
        }
    }

    public void configureLayout() {
        int i;
        this.r.clear();
        this.r.add(getString(R.string.F24_13_SELECTOR_CAMBIAR_TIPO_TODOS_TIPOS));
        this.lst_wrapper.removeAllViews();
        TableLayout tableLayout = new TableLayout(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService("layout_inflater");
        Iterator it = this.p.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            CategoriaFondosBean categoriaFondosBean = (CategoriaFondosBean) it.next();
            ViewGroup viewGroup = null;
            LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.list_item_section_header_fondos, null);
            if (i2 == 0) {
                linearLayout.findViewById(R.id.separator).setVisibility(8);
            }
            TextView textView = (TextView) linearLayout.findViewById(R.id.section_text);
            textView.setText(Html.fromHtml(categoriaFondosBean.getNombreCategoria()));
            try {
                textView.setContentDescription(FondosConstants.applyAccesibilityFilterName(getBaseContext(), textView.getText().toString()));
            } catch (Exception unused) {
            }
            this.r.add(categoriaFondosBean.getNombreCategoria());
            tableLayout.addView(linearLayout, i2);
            i2++;
            if (categoriaFondosBean.getFondosBean() != null && categoriaFondosBean.getFondosBean().getFondosBean().size() > 0) {
                Iterator it2 = categoriaFondosBean.getFondosBean().getFondosBean().iterator();
                while (it2.hasNext()) {
                    FondoBean fondoBean = (FondoBean) it2.next();
                    LinearLayout linearLayout2 = (LinearLayout) layoutInflater.inflate(R.layout.list_item_cotizaciones_fondo, viewGroup);
                    TextView textView2 = (TextView) linearLayout2.findViewById(R.id.F24_13_lbl_data_nombre_fondo);
                    TextView textView3 = (TextView) linearLayout2.findViewById(R.id.F24_13_lbl_data_valorCuota);
                    TextView textView4 = (TextView) linearLayout2.findViewById(R.id.F24_13_lbl_data_valorUltimoDia);
                    TextView textView5 = (TextView) linearLayout2.findViewById(R.id.F24_13_lbl_data_valorUltimos30);
                    TextView textView6 = (TextView) linearLayout2.findViewById(R.id.F24_13_lbl_data_valorUltimos90);
                    TextView textView7 = (TextView) linearLayout2.findViewById(R.id.F24_13_lbl_data_valorAno);
                    ImageView imageView = (ImageView) linearLayout2.findViewById(R.id.F24_13_img_flecha_ultimo_dia);
                    ImageView imageView2 = (ImageView) linearLayout2.findViewById(R.id.F24_13_img_flecha_ultimos_30);
                    LayoutInflater layoutInflater2 = layoutInflater;
                    ImageView imageView3 = (ImageView) linearLayout2.findViewById(R.id.F24_13_img_flecha_ultimos_90);
                    Iterator it3 = it;
                    ImageView imageView4 = (ImageView) linearLayout2.findViewById(R.id.F24_13_img_flecha_ValorAno);
                    Iterator it4 = it2;
                    textView2.setText(Html.fromHtml(fondoBean.getNombre()));
                    if (!TextUtils.isEmpty(fondoBean.getValorCuota())) {
                        textView3.setText(fondoBean.getValorCuota());
                        textView3.setTag(fondoBean.getValorCuota());
                    } else {
                        textView3.setText("N/A");
                        textView3.setTag("");
                    }
                    TableLayout tableLayout2 = tableLayout;
                    if (!TextUtils.isEmpty(fondoBean.getValorUltimoDia())) {
                        if (fondoBean.getValorUltimoDia().indexOf("-") == -1) {
                            i = i2;
                            textView4.setText(fondoBean.getValorUltimoDia().replace(Constants.SYMBOL_POSITIVE, ""));
                            textView4.setTextColor(textView4.getContext().getResources().getColor(R.color.generic_selectable));
                            imageView.setBackground(getResources().getDrawable(R.drawable.green_arrow));
                            imageView.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_POSITIVA));
                        } else {
                            i = i2;
                            textView4.setText(fondoBean.getValorUltimoDia().replace("-", ""));
                            textView4.setTextColor(textView4.getContext().getResources().getColor(R.color.generic_selectable));
                            imageView.setBackground(getResources().getDrawable(R.drawable.red_arrow));
                            imageView.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_NEGATIVA));
                        }
                        textView4.setTag(fondoBean.getValorUltimoDia());
                    } else {
                        i = i2;
                        textView4.setText("N/A");
                        textView4.setTag("");
                    }
                    if (!TextUtils.isEmpty(fondoBean.getValorUltimoMes())) {
                        if (fondoBean.getValorUltimoMes().indexOf("-") == -1) {
                            textView5.setText(fondoBean.getValorUltimoMes().replace(Constants.SYMBOL_POSITIVE, ""));
                            textView5.setTextColor(textView5.getContext().getResources().getColor(R.color.generic_selectable));
                            imageView2.setBackground(getResources().getDrawable(R.drawable.green_arrow));
                            imageView2.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_POSITIVA));
                        } else {
                            textView5.setText(fondoBean.getValorUltimoMes().replace("-", ""));
                            textView5.setTextColor(textView5.getContext().getResources().getColor(R.color.generic_selectable));
                            imageView2.setBackground(getResources().getDrawable(R.drawable.red_arrow));
                            imageView2.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_NEGATIVA));
                        }
                        textView5.setTag(fondoBean.getValorUltimoMes());
                    } else {
                        textView5.setText("N/A");
                        textView5.setTag("");
                    }
                    if (!TextUtils.isEmpty(fondoBean.getValorUltimoTrimestre())) {
                        if (fondoBean.getValorUltimoTrimestre().indexOf("-") == -1) {
                            textView6.setText(fondoBean.getValorUltimoTrimestre().replace(Constants.SYMBOL_POSITIVE, ""));
                            textView6.setTextColor(textView6.getContext().getResources().getColor(R.color.generic_selectable));
                            imageView3.setBackground(getResources().getDrawable(R.drawable.green_arrow));
                            imageView3.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_POSITIVA));
                        } else {
                            textView6.setText(fondoBean.getValorUltimoTrimestre().replace("-", ""));
                            textView6.setTextColor(textView6.getContext().getResources().getColor(R.color.generic_selectable));
                            imageView3.setBackground(getResources().getDrawable(R.drawable.red_arrow));
                            imageView3.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_NEGATIVA));
                        }
                        textView6.setTag(fondoBean.getValorUltimoTrimestre());
                    } else {
                        textView6.setText("N/A");
                        textView6.setTag("");
                    }
                    if (!TextUtils.isEmpty(fondoBean.getValorUltimoAno())) {
                        if (fondoBean.getValorUltimoAno().indexOf("-") == -1) {
                            textView7.setText(fondoBean.getValorUltimoAno().replace(Constants.SYMBOL_POSITIVE, ""));
                            textView7.setTextColor(textView7.getContext().getResources().getColor(R.color.generic_selectable));
                            imageView4.setBackground(getResources().getDrawable(R.drawable.green_arrow));
                            imageView4.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_POSITIVA));
                        } else {
                            textView7.setText(fondoBean.getValorUltimoAno().replace("-", ""));
                            textView7.setTextColor(textView7.getContext().getResources().getColor(R.color.generic_selectable));
                            imageView4.setBackground(getResources().getDrawable(R.drawable.red_arrow));
                            imageView4.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_NEGATIVA));
                        }
                        textView7.setTag(fondoBean.getValorUltimoAno());
                    } else {
                        textView7.setText("N/A");
                        textView7.setTag("");
                    }
                    try {
                        CAccessibility instance = CAccessibility.getInstance(this);
                        textView2.setContentDescription(FondosConstants.applyAccesibilityFilterName(getBaseContext(), textView2.getText().toString()));
                        textView3.setContentDescription(instance.applyFilterGeneral(textView3.getText().toString()));
                        textView4.setContentDescription(instance.applyFilterGeneral(textView4.getText().toString()));
                        textView5.setContentDescription(instance.applyFilterGeneral(textView5.getText().toString()));
                        textView6.setContentDescription(instance.applyFilterGeneral(textView6.getText().toString()));
                        textView7.setContentDescription(instance.applyFilterGeneral(textView7.getText().toString()));
                    } catch (Exception unused2) {
                    }
                    tableLayout = tableLayout2;
                    int i3 = i;
                    tableLayout.addView(linearLayout2, i3);
                    i2 = i3 + 1;
                    layoutInflater = layoutInflater2;
                    it = it3;
                    it2 = it4;
                    viewGroup = null;
                }
            }
            layoutInflater = layoutInflater;
            it = it;
        }
        this.lst_wrapper.addView(tableLayout);
        displayCotizacionesValorCuota();
        this.selectorValoresFondo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CotizacionesFondoActivity.this.w.showValoresDialog(CotizacionesFondoActivity.this.selectorValoresFondo.getLabel());
            }
        });
        this.selectorTipoFondo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CotizacionesFondoActivity.this.w.showTiposFondosDialog(CotizacionesFondoActivity.this.selectorTipoFondo.getLabel(), CotizacionesFondoActivity.this.r);
                CotizacionesFondoActivity.this.t.trackEvent(CotizacionesFondoActivity.this.getString(R.string.analytics_event_category_fondos), CotizacionesFondoActivity.this.getString(R.string.analytics_event_action_pantalla_seleccionar_fondo), CotizacionesFondoActivity.this.getString(R.string.analytics_event_label_pantalla_cotizacion_por_tipo));
            }
        });
        this.imgBtnSort.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CotizacionesFondoActivity.this.t.trackEvent(CotizacionesFondoActivity.this.getString(R.string.analytics_event_category_fondos), CotizacionesFondoActivity.this.getString(R.string.analytics_event_action_pantalla_seleccionar_fondo), CotizacionesFondoActivity.this.getString(R.string.analytics_event_label_pantalla_mayor_menor_cotizacion));
                CotizacionesFondoActivity.this.w.showOrdenarListaDialog();
            }
        });
        this.lblLegales.setText(Html.fromHtml(a(this.s.getLeyendaLegales())));
    }

    private String a(List<String> list) {
        String str = "";
        for (String concat : list) {
            str = str.concat("\n").concat(concat);
        }
        return str;
    }

    public void displayCotizacionesValorCuota() {
        a(true, false, false, false, false);
        this.selectorValoresFondo.setLabel(getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_CUOTA));
    }

    public void displayCotizacionesUltimoDia() {
        a(false, true, false, false, false);
        this.selectorValoresFondo.setLabel(getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMO_DIA));
    }

    public void displayCotizacionesUltimos30() {
        a(false, false, true, false, false);
        this.selectorValoresFondo.setLabel(getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMOS_30_DIAS));
    }

    public void displayCotizacionesUltimos90() {
        a(false, false, false, true, false);
        this.selectorValoresFondo.setLabel(getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMOS_90_DIAS));
    }

    public void displayCotizacionesUltimoAno() {
        a(false, false, false, false, true);
        this.selectorValoresFondo.setLabel(getString(R.string.F24_13_SELECTOR_CAMBIAR_VALOR_ULTIMO_ANO));
    }

    private void a(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        TableLayout tableLayout = (TableLayout) this.lst_wrapper.getChildAt(0);
        if (tableLayout != null) {
            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                LinearLayout linearLayout = (LinearLayout) tableLayout.getChildAt(i);
                if (((TextView) linearLayout.findViewById(R.id.section_text)) == null) {
                    LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(R.id.F24_13_rll_data_valorCuota);
                    LinearLayout linearLayout3 = (LinearLayout) linearLayout.findViewById(R.id.F24_13_rll_data_ultimo_dia);
                    LinearLayout linearLayout4 = (LinearLayout) linearLayout.findViewById(R.id.F24_13_rll_data_ultimos30);
                    LinearLayout linearLayout5 = (LinearLayout) linearLayout.findViewById(R.id.F24_13_rll_data_ultimos90);
                    LinearLayout linearLayout6 = (LinearLayout) linearLayout.findViewById(R.id.F24_13_rll_data_valorAno);
                    if (z) {
                        linearLayout2.setVisibility(0);
                    } else {
                        linearLayout2.setVisibility(8);
                    }
                    if (z2) {
                        linearLayout3.setVisibility(0);
                    } else {
                        linearLayout3.setVisibility(8);
                    }
                    if (z3) {
                        linearLayout4.setVisibility(0);
                    } else {
                        linearLayout4.setVisibility(8);
                    }
                    if (z4) {
                        linearLayout5.setVisibility(0);
                    } else {
                        linearLayout5.setVisibility(8);
                    }
                    if (z5) {
                        linearLayout6.setVisibility(0);
                    } else {
                        linearLayout6.setVisibility(8);
                    }
                }
            }
        }
    }

    public void displayCotizacionesTodosTipos() {
        a(false, "");
        this.selectorTipoFondo.setLabel(getString(R.string.F24_13_SELECTOR_CAMBIAR_TIPO_TODOS_TIPOS));
    }

    public void displayCotizacionesFiltro(String str) {
        a(true, str);
        this.selectorTipoFondo.setLabel(str);
    }

    private void a(boolean z, String str) {
        String str2 = "";
        TableLayout tableLayout = (TableLayout) this.lst_wrapper.getChildAt(0);
        if (tableLayout != null) {
            String str3 = str2;
            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                LinearLayout linearLayout = (LinearLayout) tableLayout.getChildAt(i);
                TextView textView = (TextView) linearLayout.findViewById(R.id.section_text);
                if (textView != null) {
                    str3 = textView.getText().toString();
                    if (!z) {
                        linearLayout.setVisibility(0);
                    } else if (str3.equalsIgnoreCase(str)) {
                        linearLayout.setVisibility(0);
                    } else {
                        linearLayout.setVisibility(8);
                    }
                } else if (!z) {
                    linearLayout.setVisibility(0);
                } else if (str3.equalsIgnoreCase(str)) {
                    linearLayout.setVisibility(0);
                } else {
                    linearLayout.setVisibility(8);
                }
            }
        }
    }

    public void sortCotizacionesDescendente() {
        a(this.u);
    }

    public void sortCotizacionesAscendente() {
        a(this.v);
    }

    private void a(Boolean bool) {
        TableLayout tableLayout = (TableLayout) this.lst_wrapper.getChildAt(0);
        if (tableLayout != null) {
            TableLayout tableLayout2 = new TableLayout(getBaseContext());
            ArrayList arrayList = new ArrayList();
            arrayList.clear();
            while (tableLayout.getChildCount() > 0) {
                LinearLayout linearLayout = (LinearLayout) tableLayout.getChildAt(0);
                if (((TextView) linearLayout.findViewById(R.id.section_text)) != null) {
                    if (arrayList.size() > 0) {
                        arrayList = a(arrayList, bool);
                        for (int i = 0; i < arrayList.size(); i++) {
                            tableLayout2.addView((View) arrayList.get(i));
                        }
                        arrayList.clear();
                    }
                    tableLayout.removeView(linearLayout);
                    tableLayout2.addView(linearLayout);
                } else {
                    tableLayout.removeView(linearLayout);
                    arrayList.add(linearLayout);
                }
            }
            if (arrayList.size() > 0) {
                ArrayList a = a(arrayList, bool);
                for (int i2 = 0; i2 < a.size(); i2++) {
                    tableLayout2.addView((View) a.get(i2));
                }
                a.clear();
            }
            this.lst_wrapper.removeAllViews();
            this.lst_wrapper.addView(tableLayout2);
        }
    }

    private ArrayList<View> a(ArrayList<View> arrayList, final Boolean bool) {
        Collections.sort(arrayList, new Comparator<View>() {
            /* renamed from: a */
            public int compare(View view, View view2) {
                TextView textView;
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.F24_13_rll_data_ultimo_dia);
                LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.F24_13_rll_data_ultimos30);
                LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.F24_13_rll_data_ultimos90);
                LinearLayout linearLayout4 = (LinearLayout) view.findViewById(R.id.F24_13_rll_data_valorAno);
                TextView textView2 = null;
                if (((LinearLayout) view.findViewById(R.id.F24_13_rll_data_valorCuota)).getVisibility() == 0) {
                    textView2 = (TextView) view.findViewById(R.id.F24_13_lbl_data_valorCuota);
                    textView = (TextView) view2.findViewById(R.id.F24_13_lbl_data_valorCuota);
                } else if (linearLayout.getVisibility() == 0) {
                    textView2 = (TextView) view.findViewById(R.id.F24_13_lbl_data_valorUltimoDia);
                    textView = (TextView) view2.findViewById(R.id.F24_13_lbl_data_valorUltimoDia);
                } else if (linearLayout2.getVisibility() == 0) {
                    textView2 = (TextView) view.findViewById(R.id.F24_13_lbl_data_valorUltimos30);
                    textView = (TextView) view2.findViewById(R.id.F24_13_lbl_data_valorUltimos30);
                } else if (linearLayout3.getVisibility() == 0) {
                    textView2 = (TextView) view.findViewById(R.id.F24_13_lbl_data_valorUltimos90);
                    textView = (TextView) view2.findViewById(R.id.F24_13_lbl_data_valorUltimos90);
                } else if (linearLayout4.getVisibility() == 0) {
                    textView2 = (TextView) view.findViewById(R.id.F24_13_lbl_data_valorAno);
                    textView = (TextView) view2.findViewById(R.id.F24_13_lbl_data_valorAno);
                } else {
                    textView = null;
                }
                Double valueOf = Double.valueOf(UtilAmount.getAmount(textView2.getText().toString()));
                Double valueOf2 = Double.valueOf(UtilAmount.getAmount(textView.getText().toString()));
                if (textView2.getTag().toString().indexOf("-") != -1) {
                    valueOf = Double.valueOf(valueOf.doubleValue() * -1.0d);
                }
                if (textView.getTag().toString().indexOf("-") != -1) {
                    valueOf2 = Double.valueOf(valueOf2.doubleValue() * -1.0d);
                }
                int i = 1;
                if (bool == CotizacionesFondoActivity.this.v) {
                    if (valueOf.compareTo(valueOf2) < 0) {
                        i = -1;
                    }
                    return i;
                }
                if (valueOf.compareTo(valueOf2) >= 0) {
                    i = -1;
                }
                return i;
            }
        });
        return arrayList;
    }

    public void attachView() {
        if (!this.w.isViewAttached()) {
            this.w.attachView(this);
        }
    }

    public void detachView() {
        if (this.w.isViewAttached()) {
            this.w.detachView();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
        super.onCreate(bundle);
        this.t.trackScreen(getString(R.string.analytics_screen_pantalla_cotozaciones));
        ButterKnife.inject((Activity) this);
        initialize();
        configureLayout();
    }

    public void gotoInformacionFondo(InformacionFondoBean informacionFondoBean, FondoBean fondoBean) {
        Intent intent = new Intent(this, InformacionFondoActivity.class);
        intent.putExtra("DETALLE_FONDO", informacionFondoBean);
        intent.putExtra(FondosConstants.INTENT_EXTRA_FONDO, fondoBean);
        intent.putExtra("CUENTAS", this.q);
        startActivityForResult(intent, 3);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent) && i == 3 && i2 == -1) {
            Intent intent2 = new Intent();
            if (intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
                intent2.putExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION, intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION));
                if (intent.hasExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE)) {
                    intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
                }
            } else if (intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
                intent2.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1));
            }
            setResult(-1, intent2);
            finish();
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public void onDestroy() {
        detachView();
        super.onDestroy();
    }
}
