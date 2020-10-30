package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.LimiteExtraccionModificacionAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.LimiteExtraccionModificacionAdapter.event;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetLimitesExtraccionEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.model.general.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.GetLimitesExtraccionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LimiteExtraccionBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetLimitesExtraccion;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class LimiteExtraccionModificacionFragment extends BaseFragment {
    private View a;
    /* access modifiers changed from: private */
    public String ad;
    /* access modifiers changed from: private */
    public String ae = "";
    /* access modifiers changed from: private */
    public LimiteExtraccionModificacionAdapter af;
    /* access modifiers changed from: private */
    public ArrayList<String> ag = new ArrayList<>();
    /* access modifiers changed from: private */
    public HashMap<String, List<LimiteExtraccionBean>> ah = new HashMap<>();
    /* access modifiers changed from: private */
    public String ai = "";
    /* access modifiers changed from: private */
    public List<Leyenda> aj;
    /* access modifiers changed from: private */
    public String ak;
    /* access modifiers changed from: private */
    public GetLimitesExtraccionResponseBean al;
    private TextView b;
    private TextView c;
    private RecyclerView d;
    @Inject
    public IDataManager dataManager;
    private Button e;
    private LinearLayout f;
    /* access modifiers changed from: private */
    public ImageView g;
    /* access modifiers changed from: private */
    public String h;
    /* access modifiers changed from: private */
    public String i;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.h = getArguments().getString("cajeroBanelcoSR");
        this.i = getArguments().getString("cajeroBanelcoOB");
        this.ad = getArguments().getString("cajeroLink");
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.a = getActivity().getLayoutInflater().inflate(R.layout.limite_extraccion_modificacion_fragment, viewGroup, false);
        initialize();
        return this.a;
    }

    public void initialize() {
        configureActionBar();
        configureLayout();
        this.ag.clear();
        this.ag.add(getString(R.string.ID_4759_CUENTAS_LBL_PERMANENTE));
        this.ag.add(getString(R.string.ID_4759_CUENTAS_LBL_TEMPORAL));
        this.ah.put(this.ag.get(0), new ArrayList());
        this.ah.put(this.ag.get(1), new ArrayList());
    }

    public void configureActionBar() {
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.BACK_ONLY);
        ((SantanderRioMainActivity) getActivity()).lockMenu(true);
        ((ImageView) ((SantanderRioMainActivity) getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LimiteExtraccionModificacionFragment.this.onBackPressed();
            }
        });
    }

    public void configureLayout() {
        this.b = (TextView) this.a.findViewById(R.id.tvLimiteActual);
        this.c = (TextView) this.a.findViewById(R.id.tvTiempoValidez);
        TextView textView = (TextView) this.a.findViewById(R.id.tvLegends);
        this.e = (Button) this.a.findViewById(R.id.btnContinuar);
        this.d = (RecyclerView) this.a.findViewById(R.id.rvLimites);
        this.f = (LinearLayout) this.a.findViewById(R.id.llTiempoValidez);
        this.g = (ImageView) this.a.findViewById(R.id.ivArrow);
        this.d.setLayoutManager(new LinearLayoutManager(getContext()));
        this.e.setEnabled(false);
        this.g.setVisibility(8);
        this.f.setVisibility(8);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        this.b.setText(this.h);
        this.f.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LimiteExtraccionModificacionFragment.this.y();
            }
        });
        this.e.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedItemLimiteExtraccion", LimiteExtraccionModificacionFragment.this.af.getLeSelectedItem());
                bundle.putString("cajeroBanelcoSR", LimiteExtraccionModificacionFragment.this.h);
                bundle.putString("cajeroBanelcoOB", LimiteExtraccionModificacionFragment.this.i);
                bundle.putString("cajeroLink", LimiteExtraccionModificacionFragment.this.ad);
                bundle.putString("limiteValidez", LimiteExtraccionModificacionFragment.this.ai);
                bundle.putString("leyendas", LimiteExtraccionModificacionFragment.this.ae);
                bundle.putString("leyendasConfirmacion", LimiteExtraccionModificacionFragment.this.ak);
                LimiteExtraccionConfirmacionFragment limiteExtraccionConfirmacionFragment = new LimiteExtraccionConfirmacionFragment();
                limiteExtraccionConfirmacionFragment.setArguments(bundle);
                ((SantanderRioMainActivity) LimiteExtraccionModificacionFragment.this.getActivity()).changeFragmentAnimation(limiteExtraccionConfirmacionFragment, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
            }
        });
        this.af = new LimiteExtraccionModificacionAdapter(getContext(), new event() {
            public void onClickItem() {
                LimiteExtraccionModificacionFragment.this.z();
            }
        });
        this.d.setAdapter(this.af);
        if (this.aj == null) {
            showProgress(VGetLimitesExtraccion.nameService);
            this.dataManager.getLimitesExtraccion();
        } else {
            setBackInfoView();
        }
        AnonymousClass5 r3 = new AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setText(view.getContentDescription());
            }
        };
        try {
            this.b.setContentDescription(CAccessibility.getInstance(getContext()).applyFilterAmount(this.h));
        } catch (Exception e2) {
            e2.fillInStackTrace();
        }
        this.b.setAccessibilityDelegate(r3);
    }

    /* access modifiers changed from: private */
    public void y() {
        if (this.ah.size() > 1) {
            final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("selector_tiempo_validez", getString(R.string.ID_4758_CUENTAS_LBL_TIEMPO_DE_VIGENCIA), null, this.ag, null, null, null, this.ai, this.ag);
            newInstance.setDialogListener(new IDialogListener() {
                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onItemSelected(String str) {
                    LimiteExtraccionModificacionFragment.this.b(str);
                    newInstance.dismiss();
                }
            });
            newInstance.setCancelable(true);
            newInstance.show(getActivity().getSupportFragmentManager(), "Dialog");
        }
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        if (str.equalsIgnoreCase(getString(R.string.ID_4759_CUENTAS_LBL_TEMPORAL)) && this.ah.size() == 1) {
            str = getString(R.string.ID_4768_CUENTAS_LBL_24HS);
        }
        this.e.setEnabled(false);
        this.c.setText(str);
        this.ai = str;
        this.af.setList((List) this.ah.get(str));
        if (str.equalsIgnoreCase(getString(R.string.ID_4759_CUENTAS_LBL_TEMPORAL))) {
            for (Leyenda leyenda : this.aj) {
                if (leyenda.getIdLeyenda().equalsIgnoreCase("LIM_TEMP")) {
                    this.ae = leyenda.getDescripcion();
                    return;
                }
            }
        } else if (str.equalsIgnoreCase(getString(R.string.ID_4759_CUENTAS_LBL_PERMANENTE))) {
            for (Leyenda leyenda2 : this.aj) {
                if (leyenda2.getIdLeyenda().equalsIgnoreCase("LIMITE")) {
                    this.ae = leyenda2.getDescripcion();
                    return;
                }
            }
        }
    }

    @Subscribe
    public void onGetLimitesExtraccion(GetLimitesExtraccionEvent getLimitesExtraccionEvent) {
        dismissProgress();
        final GetLimitesExtraccionEvent getLimitesExtraccionEvent2 = getLimitesExtraccionEvent;
        AnonymousClass7 r0 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.LIMITE_EXTRACCION, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                LimiteExtraccionModificacionFragment.this.al = (GetLimitesExtraccionResponseBean) getLimitesExtraccionEvent2.getBeanResponse();
                LimiteExtraccionModificacionFragment.this.aj = LimiteExtraccionModificacionFragment.this.al.getLimitesExtraccionBodyResponseBean.listaLeyendas.leyendaList;
                Iterator it = LimiteExtraccionModificacionFragment.this.aj.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Leyenda leyenda = (Leyenda) it.next();
                    if (leyenda.getIdLeyenda().equalsIgnoreCase("CAMB_LIM")) {
                        LimiteExtraccionModificacionFragment.this.ak = leyenda.getDescripcion();
                        break;
                    }
                }
                if (LimiteExtraccionModificacionFragment.this.al.getLimitesExtraccionBodyResponseBean != null) {
                    if (LimiteExtraccionModificacionFragment.this.al.getLimitesExtraccionBodyResponseBean.limitesPermanentes != null) {
                        LimiteExtraccionModificacionFragment.this.ah.put(LimiteExtraccionModificacionFragment.this.ag.get(0), LimiteExtraccionModificacionFragment.this.al.getLimitesExtraccionBodyResponseBean.limitesPermanentes.listaLimitesPermanentes);
                        LimiteExtraccionModificacionFragment.this.ai = (String) LimiteExtraccionModificacionFragment.this.ag.get(0);
                    } else {
                        LimiteExtraccionModificacionFragment.this.ah.remove(LimiteExtraccionModificacionFragment.this.ag.get(0));
                        LimiteExtraccionModificacionFragment.this.ai = "";
                    }
                    if (LimiteExtraccionModificacionFragment.this.al.getLimitesExtraccionBodyResponseBean.limitesTemporales != null) {
                        LimiteExtraccionModificacionFragment.this.ah.put(LimiteExtraccionModificacionFragment.this.ag.get(1), LimiteExtraccionModificacionFragment.this.al.getLimitesExtraccionBodyResponseBean.limitesTemporales.listaLimitesTemporales);
                        LimiteExtraccionModificacionFragment.this.ai = LimiteExtraccionModificacionFragment.this.ai.isEmpty() ? (String) LimiteExtraccionModificacionFragment.this.ag.get(1) : LimiteExtraccionModificacionFragment.this.ai;
                    } else {
                        LimiteExtraccionModificacionFragment.this.ah.remove(LimiteExtraccionModificacionFragment.this.ag.get(1));
                        LimiteExtraccionModificacionFragment.this.ai = LimiteExtraccionModificacionFragment.this.ai.isEmpty() ? "" : LimiteExtraccionModificacionFragment.this.ai;
                    }
                }
                if (LimiteExtraccionModificacionFragment.this.ah.size() > 1) {
                    LimiteExtraccionModificacionFragment.this.g.setVisibility(0);
                }
                if (!LimiteExtraccionModificacionFragment.this.ai.isEmpty()) {
                    LimiteExtraccionModificacionFragment.this.b(LimiteExtraccionModificacionFragment.this.ai);
                }
            }
        };
        r0.handleWSResponse(getLimitesExtraccionEvent);
    }

    public void setBackInfoView() {
        this.aj = this.al.getLimitesExtraccionBodyResponseBean.listaLeyendas.leyendaList;
        Iterator it = this.aj.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Leyenda leyenda = (Leyenda) it.next();
            if (leyenda.getIdLeyenda().equalsIgnoreCase("CAMB_LIM")) {
                this.ak = leyenda.getDescripcion();
                break;
            }
        }
        if (this.al.getLimitesExtraccionBodyResponseBean != null) {
            if (this.al.getLimitesExtraccionBodyResponseBean.limitesPermanentes != null) {
                this.ah.put(this.ag.get(0), this.al.getLimitesExtraccionBodyResponseBean.limitesPermanentes.listaLimitesPermanentes);
                this.ai = (String) this.ag.get(0);
            } else {
                this.ah.remove(this.ag.get(0));
                this.ai = "";
            }
            if (this.al.getLimitesExtraccionBodyResponseBean.limitesTemporales != null) {
                this.ah.put(this.ag.get(1), this.al.getLimitesExtraccionBodyResponseBean.limitesTemporales.listaLimitesTemporales);
                this.ai = this.ai.isEmpty() ? (String) this.ag.get(1) : this.ai;
            } else {
                this.ah.remove(this.ag.get(1));
                this.ai = this.ai.isEmpty() ? "" : this.ai;
            }
        }
        if (this.ah.size() > 1) {
            this.g.setVisibility(0);
        }
        if (!this.ai.isEmpty()) {
            b(this.ai);
        }
    }

    /* access modifiers changed from: private */
    public void z() {
        this.e.setEnabled(true);
    }

    public void onBackPressed() {
        ((SantanderRioMainActivity) getActivity()).backLastFragment();
    }
}
