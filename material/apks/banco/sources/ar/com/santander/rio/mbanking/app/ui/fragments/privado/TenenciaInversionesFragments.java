package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.custodias.CustodiasActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores.TitulosValoresActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.TenenciaInversionesCuentasAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.TenenciaInversionesCuentasAdapter.ChildClickListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.utils.CuentasUtils;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.TenenciaInversionesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.TenenciaInversionesProductosEnum;
import ar.com.santander.rio.mbanking.services.model.general.TipoCliente;
import ar.com.santander.rio.mbanking.services.soap.beans.body.BancaPrivada;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Cuenta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Individuos;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.Producto;
import ar.com.santander.rio.mbanking.services.soap.beans.body.TenenciaInversionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VTenenciaInversiones;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class TenenciaInversionesFragments extends BaseFragment {
    /* access modifiers changed from: private */
    public InversionesAnalyticsImpl a;
    private Context aA;
    private ChildClickListener aB = new ChildClickListener() {
        public void onClick(View view, int i, Cuenta cuenta, Producto producto) {
            StringBuilder sb = new StringBuilder();
            sb.append("Position: ");
            sb.append(i);
            sb.append(" CodProduct: ");
            sb.append(producto.getCodProducto());
            Log.d("Activity Click", sb.toString());
            TenenciaInversionesFragments.this.a(cuenta, producto, i);
        }
    };
    private TextView ad;
    private TextView ae;
    private TextView af;
    private TextView ag;
    private TextView ah;
    private TextView ai;
    private ImageView aj;
    private LinearLayout ak;
    private LinearLayout al;
    private TextView am;
    private ImageView an;
    @Inject
    public AnalyticsManager analyticsManager;
    private TextView ao;
    private TextView ap;
    /* access modifiers changed from: private */
    public RecyclerView aq;
    /* access modifiers changed from: private */

    /* renamed from: ar reason: collision with root package name */
    public TenenciaInversionesCuentasAdapter f242ar;
    private List<Cuenta> as = new ArrayList();
    private List<Cuenta> at = new ArrayList();
    private BancaPrivada au;
    private Individuos av;
    /* access modifiers changed from: private */
    public String aw;
    private ListaLeyendas ax;
    private CAccessibility ay;
    private Activity az = null;
    private TenenciaInversionesBodyResponseBean b;
    private View c;
    private View d;
    private View e;
    private View f;
    private View g;
    private View h;
    private TextView i;
    @Inject
    public IDataManager iDataManager;
    @Inject
    public SessionManager sessionManager;

    /* JADX WARNING: Removed duplicated region for block: B:12:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0068  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r3) {
        /*
            r2 = this;
            super.onCreate(r3)
            android.content.Context r3 = r2.getContext()
            ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility r3 = ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility.getInstance(r3)
            r2.ay = r3
            ar.com.santander.rio.mbanking.managers.session.SessionManager r3 = r2.sessionManager
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r3 = r3.getLoginUnico()
            ar.com.santander.rio.mbanking.services.model.general.DatosPersonales r3 = r3.getDatosPersonales()
            java.lang.String r3 = r3.getTipoCliente()
            r2.aw = r3
            ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl r3 = new ar.com.santander.rio.mbanking.app.module.inversionesbancaprivada.analytics.InversionesAnalyticsImpl
            android.content.Context r0 = r2.getContext()
            ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager r1 = r2.analyticsManager
            r3.<init>(r0, r1)
            r2.a = r3
            ar.com.santander.rio.mbanking.managers.session.SessionManager r3 = r2.sessionManager
            ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean r3 = r3.getLoginUnico()
            ar.com.santander.rio.mbanking.services.model.general.DatosPersonales r3 = r3.getDatosPersonales()
            java.lang.String r3 = r3.getTipoCliente()
            int r0 = r3.hashCode()
            r1 = 2126(0x84e, float:2.979E-42)
            if (r0 == r1) goto L_0x0050
            r1 = 1964887305(0x751dcd09, float:2.0003643E32)
            if (r0 == r1) goto L_0x0046
            goto L_0x005a
        L_0x0046:
            java.lang.String r0 = "BP/RTL"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x005a
            r3 = 0
            goto L_0x005b
        L_0x0050:
            java.lang.String r0 = "BP"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x005a
            r3 = 1
            goto L_0x005b
        L_0x005a:
            r3 = -1
        L_0x005b:
            switch(r3) {
                case 0: goto L_0x0068;
                case 1: goto L_0x0063;
                default: goto L_0x005e;
            }
        L_0x005e:
            java.lang.String r3 = "RTL"
            r2.aw = r3
            goto L_0x006c
        L_0x0063:
            java.lang.String r3 = "BP"
            r2.aw = r3
            goto L_0x006c
        L_0x0068:
            java.lang.String r3 = "BP/RTL"
            r2.aw = r3
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaInversionesFragments.onCreate(android.os.Bundle):void");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.c = layoutInflater.inflate(R.layout.fragment_tenencia_inversiones, viewGroup, false);
        ((SantanderRioMainActivity) this.az).restartActionBar();
        this.i = (TextView) this.c.findViewById(R.id.option_selected_title);
        this.ad = (TextView) this.c.findViewById(R.id.tvCambiar);
        this.ae = (TextView) this.c.findViewById(R.id.functionality_title);
        this.af = (TextView) this.c.findViewById(R.id.tvLabelValuePesos);
        this.ag = (TextView) this.c.findViewById(R.id.tvLabelValueDolar);
        this.ah = (TextView) this.c.findViewById(R.id.section_title_value);
        this.aj = (ImageView) this.c.findViewById(R.id.imageView11);
        this.d = this.c.findViewById(R.id.subtitle_view_gray);
        this.ai = (TextView) this.c.findViewById(R.id.text);
        this.g = this.c.findViewById(R.id.amount_view);
        this.h = this.c.findViewById(R.id.leyenda);
        this.ak = (LinearLayout) this.c.findViewById(R.id.llViewsWithValuesOfService);
        this.e = this.c.findViewById(R.id.include_error);
        this.f = this.c.findViewById(R.id.view_msj_error);
        this.al = (LinearLayout) this.c.findViewById(R.id.layout_partial_content);
        this.aq = (RecyclerView) this.c.findViewById(R.id.list_form);
        this.aq.setLayoutManager(new LinearLayoutManager(getContext()));
        this.am = (TextView) this.c.findViewById(R.id.title_error);
        this.an = (ImageView) this.c.findViewById(R.id.image_error);
        this.ao = (TextView) this.c.findViewById(R.id.description_error);
        this.ap = (TextView) this.c.findViewById(R.id.section_msj_error);
        return this.c;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.aA = context;
        this.az = getActivity();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.ae.setText(getText(R.string.ID_4300_INVERSIONES_LBL_TENENCIA_DE_INVERSIONES));
        if (this.aw.equals("BP/RTL") || this.aw.equals("BP")) {
            this.iDataManager.getTenenciaInversiones("BP");
            this.a.registerScreenHome("BP");
        } else {
            this.iDataManager.getTenenciaInversiones("RTL");
            this.a.registerScreenHome("RTL");
        }
        ((SantanderRioMainActivity) getActivity()).showProgress(VTenenciaInversiones.nameService);
    }

    @Subscribe
    public void onGetTenenciaInversiones(TenenciaInversionesEvent tenenciaInversionesEvent) {
        ((SantanderRioMainActivity) getActivity()).dismissProgress();
        final TenenciaInversionesEvent tenenciaInversionesEvent2 = tenenciaInversionesEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INITIAL_VIEW, VTenenciaInversiones.nameService, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                TenenciaInversionesFragments.this.d(tenenciaInversionesEvent2);
            }

            /* access modifiers changed from: protected */
            public void onServerError() {
                TenenciaInversionesFragments.this.b(tenenciaInversionesEvent2);
            }

            /* access modifiers changed from: protected */
            public void onUnknownError() {
                TenenciaInversionesFragments.this.b(tenenciaInversionesEvent2);
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                TenenciaInversionesFragments.this.a(tenenciaInversionesEvent2);
            }
        };
        r1.handleWSResponse(tenenciaInversionesEvent);
    }

    /* access modifiers changed from: private */
    public void a(TenenciaInversionesEvent tenenciaInversionesEvent) {
        this.aq.removeAllViewsInLayout();
        this.aq.removeAllViews();
        this.h.setVisibility(8);
        if (this.aw.equals("BP/RTL") || this.aw.equals("BP")) {
            this.i.setText(getText(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA));
        } else if (this.aw.equals("RTL")) {
            this.i.setText(getText(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES));
        }
        changeViewSelector();
        this.al.setVisibility(8);
        this.ak.setVisibility(8);
        this.e.setVisibility(0);
        this.am.setText(Html.fromHtml(tenenciaInversionesEvent.getErrorBodyBean().resTitle));
        this.an.setImageResource(R.drawable.error_continuacion);
        this.ao.setText(Html.fromHtml(tenenciaInversionesEvent.getErrorBodyBean().resDesc));
    }

    /* access modifiers changed from: private */
    public void b(TenenciaInversionesEvent tenenciaInversionesEvent) {
        this.aq.removeAllViewsInLayout();
        this.aq.removeAllViews();
        this.h.setVisibility(8);
        if (this.aw.equals("BP/RTL") || this.aw.equals("BP")) {
            this.i.setText(getText(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA));
        } else if (this.aw.equals("RTL")) {
            this.i.setText(getText(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES));
        }
        changeViewSelector();
        this.al.setVisibility(8);
        this.ak.setVisibility(8);
        this.e.setVisibility(0);
        this.am.setText(R.string.USER200043_TITLE);
        this.an.setImageResource(R.drawable.error_continuacion);
        this.ao.setText(R.string.MSG_USER000002_General_errorNoconexion);
        this.a.registerEventError();
    }

    private void c(TenenciaInversionesEvent tenenciaInversionesEvent) {
        this.aq.removeAllViewsInLayout();
        this.aq.removeAllViews();
        if (this.aw.equals("BP/RTL") || this.aw.equals("BP")) {
            this.i.setText(getText(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA));
        } else if (this.aw.equals("RTL")) {
            this.i.setText(getText(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES));
        }
        if (tenenciaInversionesEvent != null) {
            changeViewSelector();
            this.al.setVisibility(0);
            this.ak.setVisibility(8);
        }
        this.d.setVisibility(0);
        this.ak.setVisibility(0);
        if (this.av != null) {
            this.ah.setText(this.av.getTenConsolidada().getNombrePerfil());
            String msjeAgrupador = this.av.getAgrupadorCuenta().getMsjeAgrupador();
            if (msjeAgrupador != null) {
                this.am.setText(msjeAgrupador);
            }
        } else {
            this.ah.setText(this.au.getTenConsolidada().getNombrePerfil());
            if (this.au == null || this.au.getAgrupadorCuenta().getMsjeAgrupador() == null) {
                this.am.setText(R.string.ERROR_NO_HAY_TENENCIA);
            } else {
                this.am.setText(Html.fromHtml(this.au.getAgrupadorCuenta().getMsjeAgrupador().toString()));
            }
            this.a.registerEventSinTenenciaInversiones();
        }
        if (this.au != null) {
            perfilDelInversorBancaPrivada();
        } else {
            perfilDelInversor();
        }
        this.e.setVisibility(0);
        this.a.tenenciaInversionesErrorTotal();
        if (this.aw.equalsIgnoreCase(TipoCliente.RTL.getTipoCliente()) && this.av != null && this.av.getTenConsolidada() != null) {
            this.af.setText(this.av.getTenConsolidada().getImportePesos());
            this.ag.setText(this.av.getTenConsolidada().getImporteDolares());
        } else if (this.aw.equalsIgnoreCase(TipoCliente.BP_RTL.getTipoCliente()) || !(!this.aw.equalsIgnoreCase(TipoCliente.BP.getTipoCliente()) || this.au == null || this.au.getTenConsolidada() == null)) {
            this.af.setText(this.au.getTenConsolidada().getImportePesos());
            this.ag.setText(this.au.getTenConsolidada().getImporteDolares());
        } else {
            this.ag.setText("U$S 0,00");
            this.af.setText("$ 0,00");
        }
        this.an.setImageResource(R.drawable.error_empty_fetch);
        a(this.af);
        a(this.ag);
    }

    /* access modifiers changed from: private */
    public void d(TenenciaInversionesEvent tenenciaInversionesEvent) {
        try {
            this.b = tenenciaInversionesEvent.response.getTenenciaInversionesBodyResponseBean();
            this.au = this.b.getBancaPrivada();
            this.av = this.b.getIndividuos();
            this.ax = this.b.getListaLeyendas();
            changeViewSelector();
            if (this.b.getIndividuos() == null) {
                this.e.setVisibility(8);
                this.al.setVisibility(0);
                this.ak.setVisibility(0);
                this.aq.removeAllViewsInLayout();
                this.aq.removeAllViews();
                if (!this.aw.equals("BP/RTL")) {
                    if (!this.aw.equals("BP")) {
                        if (this.aw.equals("RTL")) {
                            z();
                            return;
                        }
                        return;
                    }
                }
                y();
                return;
            }
            z();
        } catch (Exception e2) {
            c(tenenciaInversionesEvent);
            e2.printStackTrace();
        }
    }

    private void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.a.tenenciaInversionesErrorParcial();
            this.f.setVisibility(0);
            this.ap.setText(Html.fromHtml(str));
            return;
        }
        this.f.setVisibility(8);
    }

    private void y() {
        this.i.setText(getText(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA));
        this.af.setText(this.au.getTenConsolidada().getImportePesos());
        this.ag.setText(this.au.getTenConsolidada().getImporteDolares());
        setLeyendaHome("TEN_INV");
        b(this.au.getMsjeError() != null ? Html.fromHtml(this.au.getMsjeError()).toString() : null);
        if (this.au.getAgrupadorCuenta().getMsjeAgrupador() != null) {
            this.h.setVisibility(8);
            this.am.setText(Html.fromHtml(this.au.getAgrupadorCuenta().getMsjeAgrupador()).toString());
        }
        if (this.au.getAgrupadorCuenta() != null) {
            this.aq.setVisibility(0);
            this.as.clear();
            this.as.addAll(this.au.getAgrupadorCuenta().getCuentaList());
        } else {
            this.aq.setVisibility(8);
        }
        if (this.au.getTenConsolidada().getDescripcionPerfil() != null) {
            this.aj.setVisibility(0);
            perfilDelInversorBancaPrivada();
        } else {
            this.aj.setVisibility(4);
        }
        if (this.au.getTenConsolidada().getNombrePerfil() != null) {
            this.d.setVisibility(0);
            this.ah.setText(this.au.getTenConsolidada().getNombrePerfil());
        } else {
            this.d.setVisibility(8);
        }
        this.f242ar = new TenenciaInversionesCuentasAdapter(this.as, this.aB, getString(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA));
        this.aq.setAdapter(this.f242ar);
        a(this.af);
        a(this.ag);
    }

    private void a(TextView textView) {
        try {
            textView.setContentDescription(this.ay.applyFilterGeneral(textView.getText().toString()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void z() {
        this.i.setText(getText(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES));
        this.af.setText(this.av.getTenConsolidada().getImportePesos());
        this.ag.setText(this.av.getTenConsolidada().getImporteDolares());
        setLeyendaHome("INV_RTL");
        if (this.av.getMsjeError() != null) {
            this.ap.setText(Html.fromHtml(this.av.getMsjeError()));
        }
        if (this.av.getAgrupadorCuenta() != null) {
            if (this.av.getAgrupadorCuenta().getCuentaList() != null) {
                this.aq.setVisibility(0);
                this.at.clear();
                this.at.addAll(this.av.getAgrupadorCuenta().getCuentaList());
            } else {
                c((TenenciaInversionesEvent) null);
            }
        } else if (this.av.getPlazoFijo() != null) {
            this.aq.setVisibility(0);
        } else {
            this.aq.setVisibility(8);
        }
        if (this.av.getTenConsolidada().getDescripcionPerfil() != null) {
            this.aj.setVisibility(0);
            perfilDelInversor();
        } else {
            this.aj.setVisibility(4);
        }
        if (this.av.getTenConsolidada().getNombrePerfil() != null) {
            this.d.setVisibility(0);
            this.ah.setText(this.av.getTenConsolidada().getNombrePerfil());
        } else {
            this.d.setVisibility(8);
        }
        this.f242ar = new TenenciaInversionesCuentasAdapter(this.at, this.av.getPlazoFijo(), this.aB, getString(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES));
        this.aq.setAdapter(this.f242ar);
        a(this.af);
        a(this.ag);
    }

    public void perfilDelInversorBancaPrivada() {
        this.aj.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TenenciaInversionesFragments.this.goToPerfilInversorBancaPrivada();
            }
        });
    }

    public void perfilDelInversor() {
        this.aj.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TenenciaInversionesFragments.this.goToPerfilInversor();
            }
        });
    }

    public void goToPerfilInversorBancaPrivada() {
        Intent intent = new Intent(getActivity(), PerfilDelInversorActivity.class);
        intent.putExtra("NOMBRE_PERFIL", this.au.getTenConsolidada().getNombrePerfil());
        intent.putExtra("DESCRIPCION_PERFIL", this.au.getTenConsolidada().getDescripcionPerfil());
        startActivity(intent);
    }

    public void goToPerfilInversor() {
        Intent intent = new Intent(getActivity(), PerfilDelInversorActivity.class);
        intent.putExtra("NOMBRE_PERFIL", this.av.getTenConsolidada().getNombrePerfil());
        intent.putExtra("DESCRIPCION_PERFIL", this.av.getTenConsolidada().getDescripcionPerfil());
        startActivity(intent);
    }

    public void setLeyendaHome(String str) {
        if (this.ax.lstLeyendas != null) {
            this.ai.setText(Html.fromHtml(this.ax.getLeyendaByTag(str).getDescripcion()));
            this.h.setVisibility(0);
            return;
        }
        this.h.setVisibility(8);
    }

    public void changeViewSelector() {
        if (this.sessionManager.getLoginUnico().getDatosPersonales().getTipoCliente().equals("BP/RTL")) {
            this.ad.setVisibility(0);
            this.ad.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TenenciaInversionesFragments.this.popUpCambiarVista();
                }
            });
            return;
        }
        this.ad.setVisibility(8);
    }

    public void popUpCambiarVista() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA));
        arrayList.add(getString(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES));
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDXX_POP_UP_HOME), null, null, arrayList, getString(R.string.IDX_ALERT_BTN_CANCEL), null, null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onItemSelected(String str) {
                if (TenenciaInversionesFragments.this.f242ar != null) {
                    TenenciaInversionesFragments.this.f242ar.clear();
                }
                TenenciaInversionesFragments.this.aq.removeAllViews();
                if (str.equals(TenenciaInversionesFragments.this.getString(R.string.ID_4297_INVERSIONES_LBL_INVERSIONES_BANCA_PRIVADA))) {
                    TenenciaInversionesFragments.this.aw = "BP";
                    TenenciaInversionesFragments.this.a.registerEventCambiarCuentaPrivada();
                    TenenciaInversionesFragments.this.iDataManager.getTenenciaInversiones(TenenciaInversionesFragments.this.aw);
                    ((SantanderRioMainActivity) TenenciaInversionesFragments.this.getActivity()).showProgress(VTenenciaInversiones.nameService);
                    TenenciaInversionesFragments.this.a.cambiarclientebp();
                } else if (str.equals(TenenciaInversionesFragments.this.getString(R.string.ID_4298_INVERSIONES_LBL_INVERSIONES))) {
                    TenenciaInversionesFragments.this.aw = "RTL";
                    TenenciaInversionesFragments.this.a.registerEventCambiarCuentaRetail();
                    TenenciaInversionesFragments.this.iDataManager.getTenenciaInversiones(TenenciaInversionesFragments.this.aw);
                    ((SantanderRioMainActivity) TenenciaInversionesFragments.this.getActivity()).showProgress(VTenenciaInversiones.nameService);
                    TenenciaInversionesFragments.this.a.cambiarclientertl();
                }
            }

            public void onSimpleActionButton() {
                newInstance.closeDialog();
            }
        });
        newInstance.show(getFragmentManager(), getString(R.string.IDXX_POP_UP_HOME));
    }

    /* access modifiers changed from: 0000 */
    public void a(Cuenta cuenta, Producto producto, int i2) {
        if (producto.getCodProducto().equalsIgnoreCase(TenenciaInversionesProductosEnum.TITULOS_VALORES.getCodProd())) {
            c(cuenta, producto, i2);
        } else if (producto.getCodProducto().equalsIgnoreCase(TenenciaInversionesProductosEnum.CUSTODIA.getCodProd())) {
            b(cuenta, producto, i2);
        } else if (producto.getCodProducto().equalsIgnoreCase(TenenciaInversionesProductosEnum.FONDO_COMUNES.getCodProd())) {
            b(cuenta);
        } else if (producto.getCodProducto().equalsIgnoreCase(TenenciaInversionesProductosEnum.LIQUIDEZ.getCodProd())) {
            a(cuenta);
        } else if (producto.getCodProducto().equalsIgnoreCase(TenenciaInversionesProductosEnum.PLAZOS_FIJOS.getCodProd())) {
            c(this.aw);
        }
    }

    private void b(Cuenta cuenta, Producto producto, int i2) {
        String str;
        try {
            ar.com.santander.rio.mbanking.services.model.general.Cuenta associatedAccount = CuentasUtils.getAssociatedAccount(this.sessionManager, cuenta.getCuenta());
            Intent intent = new Intent(getActivity(), CustodiasActivity.class);
            intent.putExtra("CuentaClass", associatedAccount);
            intent.putParcelableArrayListExtra("CUENTAS", CuentasUtils.getAllCuentas(this.sessionManager));
            intent.putExtra("IDX_CUENTA", i2);
            String str2 = "TIPO_CUENTA";
            if (this.aw.equals("BP")) {
                str = "";
            } else {
                str = associatedAccount.getTipo();
            }
            intent.putExtra(str2, str);
            intent.putExtra("SUC_CUENTA", cuenta.getSucInt() > -1 ? Integer.valueOf(cuenta.getSucInt()) : "");
            intent.putExtra("NRO_CUENTA", cuenta.getCuenta());
            intent.putExtra("TIPO_CLIENTE", this.aw.equals("RTL") ? "RTL" : "BP");
            startActivity(intent);
        } catch (Exception unused) {
            Log.d("Service", "Implementacion erronea");
        }
    }

    private void a(Cuenta cuenta) {
        if (this.aw.equals("BP/RTL") || this.aw.equals("BP")) {
            ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(new CuentasFragment(FragmentConstants.CUENTAS_PRIVADA), R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
        } else if (this.aw.equals("RTL")) {
            ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(new CuentasFragment(FragmentConstants.CUENTAS), R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
        }
    }

    private void c(Cuenta cuenta, Producto producto, int i2) {
        String str;
        ar.com.santander.rio.mbanking.services.model.general.Cuenta associatedAccount = CuentasUtils.getAssociatedAccount(this.sessionManager, cuenta.getCuenta());
        Intent intent = new Intent(getActivity(), TitulosValoresActivity.class);
        intent.putExtra("CuentaClass", associatedAccount);
        intent.putParcelableArrayListExtra("CUENTAS", CuentasUtils.getAllCuentas(this.sessionManager));
        intent.putExtra("IDX_CUENTA", i2);
        String str2 = "TIPO_CUENTA";
        if (this.aw.equals("BP")) {
            str = "";
        } else {
            str = associatedAccount.getTipo();
        }
        intent.putExtra(str2, str);
        intent.putExtra("SUC_CUENTA", cuenta.getSucInt() > -1 ? Integer.valueOf(cuenta.getSucInt()) : "");
        intent.putExtra("NRO_CUENTA", cuenta.getCuenta());
        intent.putExtra("TIPO_CLIENTE", this.aw.equals("RTL") ? "RTL" : "BP");
        startActivity(intent);
    }

    private void b(Cuenta cuenta) {
        try {
            TenenciaFondosFragment newInstance = TenenciaFondosFragment.newInstance(cuenta.getNroCta(), FragmentConstants.FONDOS_INVERSION);
            newInstance.setTAG(FragmentConstants.FONDOS_INVERSION);
            ((SantanderRioMainActivity) getActivity()).selectMenuItem(FragmentConstants.FONDOS_INVERSION);
            ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(newInstance, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
            if (this.aw.equals("RTL")) {
                this.a.registerEventCambiarCuentaFondosComunesTitular();
            } else if (this.aw.equals("BP")) {
                this.a.registerEventCambiarCuentaFondosComunesBP();
            }
        } catch (Exception unused) {
            Log.d("Service", "Implementacion erronea");
        }
    }

    private void c(String str) {
        try {
            if (this.aw.equals("BP")) {
                this.a.registerEventCambiarCuentaTenenciaPlazoFijoBP();
            } else {
                this.a.registerEventCambiarCuentaTenenciaPlazoFijoRTL();
            }
            PlazosFijoFragment newInstance = PlazosFijoFragment.newInstance(str, FragmentConstants.PLAZOS_FIJOS);
            newInstance.setTAG(FragmentConstants.PLAZOS_FIJOS);
            ((SantanderRioMainActivity) getActivity()).selectMenuItem(FragmentConstants.PLAZOS_FIJOS);
            ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(newInstance, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
        } catch (Exception unused) {
            Log.d("Service", "Implementacion erronea");
        }
    }
}
