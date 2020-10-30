package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

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
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.RecalificacionesProductsAdapter;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.services.events.GetLimitesProductosEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetLimitesProductosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetLimitesProductos;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class RecalificacionesFragment extends BaseFragment {
    public static final String COD_RECA_ERROR = "0";
    public static final String COD_RECA_NO_APLICA = "3";
    public static final String COD_RECA_R = "R";
    public static final String COD_RECA_RECALIFICAR = "1";
    public static final String COD_RECA_REDISTRIBUIR = "2";
    private View a;
    /* access modifiers changed from: private */
    public RecyclerView b;
    /* access modifiers changed from: private */
    public Button c;
    /* access modifiers changed from: private */
    public int d;
    /* access modifiers changed from: private */
    public GetLimitesProductosEvent e;
    @Inject
    public IDataManager mDataManager;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.a = layoutInflater.inflate(R.layout.fragment_recalificaciones, viewGroup, false);
        this.b = (RecyclerView) this.a.findViewById(R.id.rvProductos);
        this.c = (Button) this.a.findViewById(R.id.btnConfirmar);
        this.b.setLayoutManager(new LinearLayoutManager(getContext()));
        return this.a;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        this.c.setEnabled(false);
        this.c.setText(R.string.ID_4841_RECALIFICACION_BTN_CONSULTAR_NUEVOS_LIMITES);
        this.c.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RecalificacionesFragment.this.y();
            }
        });
        this.b.setHasFixedSize(true);
        this.mDataManager.getLimitesProductos();
        ((SantanderRioMainActivity) getActivity()).showProgress(VGetLimitesProductos.nameService);
    }

    @Subscribe
    public void onGetLimitesProductos(GetLimitesProductosEvent getLimitesProductosEvent) {
        this.e = getLimitesProductosEvent;
        ((SantanderRioMainActivity) getActivity()).dismissProgress();
        final GetLimitesProductosEvent getLimitesProductosEvent2 = getLimitesProductosEvent;
        AnonymousClass2 r1 = new BaseWSResponseHandler(getActivity(), TypeOption.INTERMDIATE_VIEW, FragmentConstants.RECALIFICACIONES, this, (BaseActivity) getActivity()) {
            /* access modifiers changed from: protected */
            public void onOk() {
                RecalificacionesFragment.this.c.setEnabled(true);
                RecalificacionesFragment.this.d = getLimitesProductosEvent2.getLimitesProductosResponseBean.getLimitesProductosBodyResponseBean.limiteTotalActual;
                RecalificacionesFragment.this.b.setAdapter(new RecalificacionesProductsAdapter(RecalificacionesFragment.this.getContext(), RecalificacionesFragment.this.e, getLimitesProductosEvent2.getLimitesProductosResponseBean.getLimitesProductosBodyResponseBean.listaProductos.producto));
            }

            /* access modifiers changed from: protected */
            public void onRes4Error(WebServiceEvent webServiceEvent) {
                RecalificacionesFragment.this.a(webServiceEvent.getErrorBodyBean().resDesc, RecalificacionesFragment.this.getString(R.string.IDXX_PRIVATEMENU_BTN_RECALIFICACIONES));
            }
        };
        r1.handleWSResponse(getLimitesProductosEvent);
    }

    /* access modifiers changed from: private */
    public void y() {
        GetLimitesProductosBodyResponseBean getLimitesProductosBodyResponseBean = this.e.getLimitesProductosResponseBean.getLimitesProductosBodyResponseBean;
        if (getLimitesProductosBodyResponseBean.codReca.equalsIgnoreCase("0") || getLimitesProductosBodyResponseBean.codReca.equalsIgnoreCase("3")) {
            final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getLimitesProductosBodyResponseBean.msjTituloReca, getLimitesProductosBodyResponseBean.msjDescReca, null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onPositiveButton() {
                }

                public void onSimpleActionButton() {
                    newInstance.dismiss();
                }
            });
            newInstance.show(getFragmentManager(), getLimitesProductosBodyResponseBean.msjCodReca);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("limiteTotalActual", this.d);
        bundle.putInt("nuevaLineaCrediticia", getLimitesProductosBodyResponseBean.nuevaLineaCrediticia);
        bundle.putInt("limiteSinAsignar", getLimitesProductosBodyResponseBean.limiteSinAsignar);
        bundle.putString("codReca", getLimitesProductosBodyResponseBean.codReca);
        bundle.putParcelable("listaProductos", getLimitesProductosBodyResponseBean.listaProductos);
        bundle.putParcelable("listaLeyendas", getLimitesProductosBodyResponseBean.listaLeyendas);
        RecalificacionesConsultaLimitesFragment recalificacionesConsultaLimitesFragment = new RecalificacionesConsultaLimitesFragment();
        recalificacionesConsultaLimitesFragment.setArguments(bundle);
        ((SantanderRioMainActivity) getActivity()).changeFragmentAnimation(recalificacionesConsultaLimitesFragment, R.anim.slide_in_right, R.anim.slide_out_left, R.anim.no_animation, R.anim.slide_out_right);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        ((SantanderRioMainActivity) getActivity()).restartActionBar();
        ((SantanderRioMainActivity) getActivity()).setActionBarType(ActionBarType.MENU);
        ((SantanderRioMainActivity) getActivity()).setErrorFragment(str, str2);
    }
}
