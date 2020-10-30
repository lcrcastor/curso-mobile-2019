package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.module.suscripcionSorpresa.SuscripcionSorpresaPresenter;
import ar.com.santander.rio.mbanking.app.module.suscripcionSorpresa.SuscripcionSorpresaPresenterImp;
import ar.com.santander.rio.mbanking.app.module.suscripcionSorpresa.SuscripcionSorpresaView;
import ar.com.santander.rio.mbanking.app.ui.activities.EditarCelularActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.ActualizarMensajesMyAEvent;
import ar.com.santander.rio.mbanking.services.events.ConsultaSuscripcionMyAEvent;
import ar.com.santander.rio.mbanking.services.events.ModificarSuscripcionEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.ActualizarMensajesMyARequestResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ConsultaSuscripcionMyAResponeBean;
import ar.com.santander.rio.mbanking.services.soap.beans.ModificarSuscripcionResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ErrorBodyBean;
import ar.com.santander.rio.mbanking.view.EditTextValidator;
import ar.com.santander.rio.mbanking.view.PhoneSelectorView;
import ar.com.santander.rio.mbanking.view.PhoneSelectorView.PhoneSelectorListener;
import ar.com.santander.rio.mbanking.view.tables.RowTwoColumnViewStyled;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class SuscripcionSorpresaFragment extends BaseFragment implements SuscripcionSorpresaView, IDialogListener, PhoneSelectorListener {
    @Inject
    IDataManager a;
    @InjectView(2131365785)
    Button actionButton;
    @Inject
    SessionManager b;
    @Inject
    AnalyticsManager c;
    @InjectView(2131365786)
    PhoneSelectorView celularPrincipalView;
    @InjectView(2131365787)
    PhoneSelectorView celularSeecundarioView;
    @InjectView(2131365788)
    ToggleButton checkAceptoTyC;
    SuscripcionSorpresaPresenter d;
    @InjectView(2131365789)
    TextView detalleNoSuscrito;
    boolean e = false;
    private BaseActivity f;
    @InjectView(2131365790)
    TextView label1;
    @InjectView(2131365793)
    View layoutTerminosNoSuscrito;
    @InjectView(2131365791)
    RowTwoColumnViewStyled mailView;
    @InjectView(2131365792)
    TextView terminosNoSuscrito;
    @InjectView(2131365794)
    TextView terminosSuscrito;
    @InjectView(2131366332)
    TextView vLink;
    @InjectView(2131366350)
    TextView vTitle;
    @InjectView(2131365795)
    View viewFragment;

    public void esClienteEstadoTO() {
    }

    public void modificarCelular(String str, String str2, String str3) {
    }

    public void onItemSelected(String str) {
    }

    public void onNegativeButton() {
    }

    public void onPositiveButton() {
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.suscripcion_sorpresa_fargment, viewGroup, false);
        ButterKnife.inject((Object) this, inflate);
        this.d = new SuscripcionSorpresaPresenterImp(this);
        this.celularPrincipalView.setPhoneSelectorListener(new PhoneSelectorListener() {
            public void onPhoneSelected(String str, String str2) {
                SuscripcionSorpresaFragment.this.a(str, str2, true);
                SuscripcionSorpresaFragment.this.c.trackScreen(SuscripcionSorpresaFragment.this.getString(R.string.analytics_screen_name_sus_editar_celular_principal));
            }
        });
        this.celularSeecundarioView.setPhoneSelectorListener(new PhoneSelectorListener() {
            public void onPhoneSelected(String str, String str2) {
                SuscripcionSorpresaFragment.this.a(str, str2, false);
                SuscripcionSorpresaFragment.this.c.trackScreen(SuscripcionSorpresaFragment.this.getString(R.string.analytics_screen_name_sus_editar_celular_secundario));
            }
        });
        this.d.onPageCreated();
        this.d.sendRequestConsultaSuscripcion();
        return inflate;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.f = (BaseActivity) getActivity();
    }

    @Subscribe
    public void onConsultaSuscripcion(ConsultaSuscripcionMyAEvent consultaSuscripcionMyAEvent) {
        dismisProgress();
        final ConsultaSuscripcionMyAEvent consultaSuscripcionMyAEvent2 = consultaSuscripcionMyAEvent;
        AnonymousClass3 r0 = new BaseWSResponseHandler(this.f, TypeOption.INTERMDIATE_VIEW, FragmentConstants.TARJETAS, this.f) {
            public void onOk() {
                if (consultaSuscripcionMyAEvent2.getResult() == TypeResult.OK) {
                    ConsultaSuscripcionMyAResponeBean consultaSuscripcionMyAResponeBean = (ConsultaSuscripcionMyAResponeBean) consultaSuscripcionMyAEvent2.getBeanResponse();
                    if (consultaSuscripcionMyAResponeBean != null) {
                        SuscripcionSorpresaFragment.this.d.getResponseConsultaSuscripcion(consultaSuscripcionMyAResponeBean.consultaSuscripcionMyAResponeBodyResponseBean);
                    }
                } else if (SuscripcionSorpresaFragment.this.getErrorListener() != null) {
                    SuscripcionSorpresaFragment.this.getErrorListener().onWebServiceErrorEvent(consultaSuscripcionMyAEvent2, SuscripcionSorpresaFragment.this.getTAG());
                }
            }
        };
        r0.handleWSResponse(consultaSuscripcionMyAEvent);
    }

    @Subscribe
    public void onActualizarMensajes(ActualizarMensajesMyAEvent actualizarMensajesMyAEvent) {
        dismisProgress();
        if (actualizarMensajesMyAEvent.getResult() == TypeResult.OK) {
            ErrorBodyBean errorBodyBean = ((ActualizarMensajesMyARequestResponseBean) actualizarMensajesMyAEvent.getBeanResponse()).errorBodyBean;
            this.c.trackScreen(getString(R.string.analytics_screen_name_sus_aviso_confirmacion));
            if (errorBodyBean != null) {
                FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000003_SusSorpresa_avisoOK), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                newInstance.show(supportFragmentManager, "Dialog");
                newInstance.setCancelable(false);
                newInstance.setDialogListener(this);
                this.d.getResponseActualizarMensajes(errorBodyBean);
            }
        } else if (getErrorListener() != null) {
            getErrorListener().onWebServiceErrorEvent(actualizarMensajesMyAEvent, getTAG());
        }
    }

    @Subscribe
    public void onModificarSuscripcion(ModificarSuscripcionEvent modificarSuscripcionEvent) {
        if (modificarSuscripcionEvent.getResult() == TypeResult.OK) {
            this.e = true;
            ModificarSuscripcionResponseBean modificarSuscripcionResponseBean = (ModificarSuscripcionResponseBean) modificarSuscripcionEvent.getBeanResponse();
            if (modificarSuscripcionResponseBean == null) {
                dismisProgress();
            } else if (!this.d.isOnlyMailModified() || !this.d.isSuscrito()) {
                this.d.getResponseModificarSuscripcion(modificarSuscripcionResponseBean.modificarSuscripcionBodyResponseBean);
            } else {
                dismisProgress();
                FragmentManager fragmentManager = getFragmentManager();
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000003_SusSorpresa_avisoMOD), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                newInstance.show(fragmentManager, "Dialog");
                newInstance.setCancelable(false);
                newInstance.setDialogListener(this);
            }
        } else {
            dismisProgress();
            if (getErrorListener() != null) {
                getErrorListener().onWebServiceErrorEvent(modificarSuscripcionEvent, getTAG());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    @OnCheckedChanged({2131365788})
    public void a(boolean z) {
        this.d.terminosAceptados(z);
        if (z) {
            this.actionButton.setEnabled(true);
        } else {
            this.actionButton.setEnabled(false);
        }
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131366332})
    public void y() {
        this.d.getResponseRequisitos();
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131365785})
    public void z() {
        IsbanDialogFragment isbanDialogFragment;
        if (((EditTextValidator) this.mailView.getValueView()).validate()) {
            this.d.onButtonAction(this.mailView, this.celularPrincipalView, this.celularSeecundarioView);
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        if (((EditTextValidator) this.mailView.getValueView()).getText().toString().equalsIgnoreCase("")) {
            isbanDialogFragment = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000019_SusSorpresa_errorEliminacionCorreo), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
        } else {
            isbanDialogFragment = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000020_SusSorpresa_errorCorreo), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
        }
        isbanDialogFragment.show(fragmentManager, "Dialog");
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131365792})
    public void A() {
        this.d.getResponseTerminosYCondiciones();
    }

    /* access modifiers changed from: 0000 */
    @OnClick({2131365794})
    public void B() {
        this.d.getResponseTerminosYCondiciones();
    }

    public void onPhoneSelected(String str, String str2) {
        a(str, str2, true);
    }

    public void setTitle() {
        this.vTitle.setText(getResources().getString(R.string.ID406_SUSSOPR_MAIN_LBL_TITLE));
        this.vLink.setText(getResources().getString(R.string.ID407_SUSSOPR_MAIN_BTN_REQ));
        this.vLink.setContentDescription(getResources().getString(R.string.ACCESSIBILITY_SUSCRIPCION_REQUISITOS));
    }

    public void setMail(String str) {
        this.mailView.setContent(str);
    }

    public void setCelularPrincipal(String str, String str2, String str3) {
        this.celularPrincipalView.setValueCelular(str);
        this.celularPrincipalView.setValueEmpresa(str2);
        this.celularPrincipalView.setCodCelular(str3);
    }

    public void setCelularSecundario(String str, String str2, String str3, boolean z) {
        if (z) {
            this.celularSeecundarioView.setVoidPhone();
            return;
        }
        this.celularSeecundarioView.setValueCelular(str);
        this.celularSeecundarioView.setValueEmpresa(str2);
        this.celularSeecundarioView.setCodCelular(str3);
    }

    public void setEstadoSuscripcion(boolean z, String str) {
        if (z) {
            this.c.trackScreen(getString(R.string.analytics_screen_name_sus_modificacion_suscripcion));
            this.label1.setText(getString(R.string.ID420_SUSSOPR_MODIF_LBL_INFORM));
            this.terminosSuscrito.setVisibility(0);
            this.detalleNoSuscrito.setVisibility(8);
            this.layoutTerminosNoSuscrito.setVisibility(8);
            this.actionButton.setText(R.string.ID429_SUSSOPR_MODIF_BTN_SAVE);
        } else {
            this.c.trackScreen(getString(R.string.analytics_screen_name_sus_adhesion_suscripcion));
            this.label1.setText(getString(R.string.ID408_SUSSOPR_MAIN_LBL_SUSSAV));
            this.terminosSuscrito.setVisibility(8);
            this.detalleNoSuscrito.setVisibility(0);
            this.layoutTerminosNoSuscrito.setVisibility(0);
            this.terminosNoSuscrito.setText(Html.fromHtml(getResources().getString(R.string.ID416_SUSSOPR_MAIN_LBL_IACCEPT)));
            this.detalleNoSuscrito.setVisibility(0);
            this.detalleNoSuscrito.setText(Html.fromHtml(str));
            try {
                this.detalleNoSuscrito.setContentDescription(CAccessibility.getInstance(getActivity()).applyFilterGeneral(this.detalleNoSuscrito.getText().toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.actionButton.setEnabled(false);
            this.actionButton.setText(R.string.ID417_SUSSOPR_MAIN_BTN_SUBSCRIBEME);
        }
        this.viewFragment.setVisibility(0);
        this.actionButton.setVisibility(0);
    }

    public void showProgress(String str) {
        super.showProgress(str);
    }

    public void verTerminosYCondiciones(String str) {
        this.c.trackScreen(getString(R.string.analytics_screen_name_sus_terminos));
        a(getString(R.string.ID440_SUSSOPR_SECCELULAR_LBL_TERMS), str);
    }

    public void verRequisitos(String str) {
        this.c.trackScreen(getString(R.string.analytics_screen_name_sus_requisitos));
        a(getString(R.string.ID441_SUSSOPR_REQUIREMENTS_LBL_TITLE), str);
    }

    public IDataManager getDataManager() {
        return this.a;
    }

    public Context getContext() {
        return getActivity();
    }

    public SessionManager getSessionManager() {
        return this.b;
    }

    public void showMessageNoDataModified() {
        IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MSG_USER000028_SusSorpresa_infoCambios), null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null).show(getFragmentManager(), "Dialog");
    }

    public void dismisProgress() {
        dismissProgress();
    }

    private void a(String str, String str2) {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
        intent.putExtra(InfoActivity.TITLE_TO_SHOW, str);
        intent.putExtra(InfoActivity.TEXT_TO_SHOW, str2);
        startActivityForResult(intent, 1);
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, boolean z) {
        String str3;
        Intent intent = new Intent(getActivity(), EditarCelularActivity.class);
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        Bundle bundle = new Bundle();
        if (z) {
            str3 = getString(R.string.ID430_SUSSOPR_CELULAR_LBL_EDIT);
        } else {
            str3 = getString(R.string.ID435_SUSSOPR_SECCELULAR_LBL_EDIT);
        }
        bundle.putString(EditarCelularActivity.TELEFONO, str);
        bundle.putString(EditarCelularActivity.EMPRESA, str2);
        bundle.putBoolean(EditarCelularActivity.PRINCIPAL, z);
        bundle.putString(EditarCelularActivity.TITLE, str3);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            getActivity();
            if (i2 == -1) {
                String stringExtra = intent.getStringExtra("celular");
                String stringExtra2 = intent.getStringExtra("empresaCel");
                String stringExtra3 = intent.getStringExtra("codCel");
                if (intent.getBooleanExtra(EditarCelularActivity.PRINCIPAL, true)) {
                    setCelularPrincipal(stringExtra, stringExtra2, stringExtra3);
                } else {
                    setCelularSecundario(stringExtra, stringExtra2, stringExtra3, intent.getBooleanExtra("eliminar_celular", false));
                }
            }
            getActivity();
        }
    }

    public void onSimpleActionButton() {
        ((SantanderRioMainActivity) getActivity()).goToOption(FragmentConstants.SUSC_SORPRESA);
    }
}
