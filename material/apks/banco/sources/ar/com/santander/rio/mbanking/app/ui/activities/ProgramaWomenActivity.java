package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarProgramaWomenFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ConfirmarProgramaWomenFragment.OnFragmentInteractionListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuscripcionProgramaWomenFragment;
import ar.com.santander.rio.mbanking.app.ui.utils.ProgramaWomenUtils;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios;
import ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean;
import butterknife.ButterKnife;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

public class ProgramaWomenActivity extends ITRSABaseActivity implements OnFragmentInteractionListener, SuscripcionProgramaWomenFragment.OnFragmentInteractionListener {
    protected View mActionBar;
    @Inject
    IDataManager p;
    SessionManager q;
    private GetListaTjWomenBodyResponseBean r;
    private ListaUsuarios s;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView((int) R.layout.layout_programa_women_activity);
        ButterKnife.inject((Activity) this);
        Bundle extras = getIntent().getExtras();
        this.r = (GetListaTjWomenBodyResponseBean) extras.getParcelable("getListaTjWomenBodyResponseBean");
        String string = extras.getString("accion");
        initialize();
        setInitialize();
        configActionBarDefault();
        SuscripcionProgramaWomenFragment newInstance = SuscripcionProgramaWomenFragment.newInstance(this.q, string, this.r);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.content, newInstance, "suscripcionProgramaWomenFragment");
        beginTransaction.commit();
    }

    public void onBackPressed() {
        b();
    }

    private void b() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void initialize() {
        ButterKnife.inject((Activity) this);
    }

    public void setInitialize() {
        this.s = this.r.getListaUsuarios();
    }

    public void configActionBarDefault() {
        setActionBarType(ActionBarType.BACK_ONLY);
        this.mActionBar = getSupportActionBar().getCustomView();
        ((ImageView) this.mActionBar.findViewById(R.id.back_imgButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FragmentManager fragmentManager = ProgramaWomenActivity.this.getFragmentManager();
                fragmentManager.popBackStackImmediate();
                if (fragmentManager.getBackStackEntryCount() == 0) {
                    ProgramaWomenActivity.this.finish();
                }
            }
        });
    }

    public void confirmSuscripcion(String str, List<Tarjeta> list) {
        showProgress("suscripcion Women");
        this.p.womenProgramSuscription(str, list);
    }

    public void confirmarAccionProgramaWomen(ProgramaWomenActivity programaWomenActivity, String str, List<Tarjeta> list) {
        ProgramaWomenUtils.showDialogConfirmationABSubscription(this, str, list);
    }

    public void irAConfirmacionProgramaWomen(boolean z, String str) {
        configActionBarDefault();
        ListaUsuarios listaUsuarios = new ListaUsuarios(this.s.getUsuario());
        listaUsuarios.setUsuario(this.s.getSelectedUserList());
        List filterListDataHeader = ProgramaWomenUtils.filterListDataHeader(listaUsuarios);
        HashMap filterListDataChild = ProgramaWomenUtils.filterListDataChild(listaUsuarios);
        if (filterListDataHeader.size() > 0) {
            ConfirmarProgramaWomenFragment newInstance = ConfirmarProgramaWomenFragment.newInstance(listaUsuarios, filterListDataHeader, filterListDataChild, z, this.r.getListaLeyendas(), str, this.r, getEventBus(), this);
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.content, newInstance, "confirmarProgramaWomenFragment");
            beginTransaction.addToBackStack("confirmarProgramaWomenFragment");
            beginTransaction.commit();
        }
    }
}
