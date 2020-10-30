package ar.com.santander.rio.mbanking.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BackEventListener;
import ar.com.santander.rio.mbanking.app.base.BaseActivity;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType;
import ar.com.santander.rio.mbanking.app.base.BaseActivity.WebServiceErrorListener;
import ar.com.santander.rio.mbanking.app.base.BaseFragment;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.base.IErrorListener;
import ar.com.santander.rio.mbanking.app.base.ITRSABaseFragment;
import ar.com.santander.rio.mbanking.app.base.ReceiptEvent;
import ar.com.santander.rio.mbanking.app.base.ReceiptEventBus;
import ar.com.santander.rio.mbanking.app.base.ShareReceiptInterface;
import ar.com.santander.rio.mbanking.app.base.ShareReceiptListener;
import ar.com.santander.rio.mbanking.app.commons.CustomActionBarListener;
import ar.com.santander.rio.mbanking.app.module.recargaSube.ObtenerTarjetasRecargaSubePresenter;
import ar.com.santander.rio.mbanking.app.module.recargaSube.ObtenerTarjetasRecargaSubeView;
import ar.com.santander.rio.mbanking.app.module.recargaSube.SharedPreferencesData;
import ar.com.santander.rio.mbanking.app.ui.Model.MenuOption;
import ar.com.santander.rio.mbanking.app.ui.Model.MenuOption.OptionType;
import ar.com.santander.rio.mbanking.app.ui.activities.InfoActivity.InfoType;
import ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga.ErrorRecargaActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.MenuAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.MenuAdapter.MenuActionsListener;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.BlankFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.EmptyFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.ErrorFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.BuySellDollarsFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.CompletarBlanqueoPinFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteBlanqueoPinFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteProgramaWomenFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroAccidenteFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroObjetoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteSeguroObjetoFragment.OnFragmentInteractionListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ComprobanteTurnoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.CuentasFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.DetalleDebinFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.EnvioDineroFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ExtraccionSinTarjetaFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.LimiteExtraccionComprobanteFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.MarcacionPorViajeFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.NotificacionesFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.NuevoPagoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.PagoTarjetasFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.PlazosFijoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.ProgramaWomenFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.RecalificacionComprobanteFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.RecargaCelularesFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SegmentoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SegurosFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SolicitudTurnoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuperClubFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.SuscripcionSorpresaFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TarjetasFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaCreditosFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaFondosFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TenenciaInversionesFragments;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.TransferenciasFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.UbicacionTurnoFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.privado.WatsonFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.BuzonNotifPushFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.CajerosFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.LoginFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.LoginFragment.ILoginListener;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.NumerosUtilesFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.PromocionesFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RecargaSubeWelcomeFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.RegistrarSubeFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.SoftTokenFragment;
import ar.com.santander.rio.mbanking.app.ui.fragments.publico.SucursalesHomeFragment;
import ar.com.santander.rio.mbanking.app.ui.utils.ProgramaWomenUtils;
import ar.com.santander.rio.mbanking.components.EncuestaDialogFragment;
import ar.com.santander.rio.mbanking.components.EncuestaDialogFragment.EncuestaDialogListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.analytics.AnalyticsManager;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import ar.com.santander.rio.mbanking.managers.notifications.PushNotificationsManager;
import ar.com.santander.rio.mbanking.managers.preferences.SettingsManager;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.GetListaTjWomenEvent;
import ar.com.santander.rio.mbanking.services.events.GetTurnoEvent;
import ar.com.santander.rio.mbanking.services.events.LoginEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.services.model.general.DestinosMyA;
import ar.com.santander.rio.mbanking.services.model.general.TipoCliente;
import ar.com.santander.rio.mbanking.services.soap.beans.LoginUnicoResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ContratarSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FamiliaBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetCotizacionSeguroObjetoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetListaTjWomenBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaRespuestas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LoginUnicoBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.RespuestaBean;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetListaTjWomen;
import ar.com.santander.rio.mbanking.services.soap.versions.VGetTurno;
import ar.com.santander.rio.mbanking.utils.DeepLinkUtils;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class SantanderRioMainActivity extends BaseActivity implements DrawerListener, WebServiceErrorListener, IErrorListener, ShareReceiptListener, ObtenerTarjetasRecargaSubeView, MenuActionsListener, OnFragmentInteractionListener, ProgramaWomenFragment.OnFragmentInteractionListener, SolicitudTurnoFragment.OnFragmentInteractionListener, ILoginListener {
    public static final String TAG_FRAGMENT_LOAD = "tag_fragment_load";
    private String A;
    private String B;
    private CheckVersionManager C;
    /* access modifiers changed from: private */
    public ReceiptEvent D;
    /* access modifiers changed from: private */
    public GetListaTjWomenBodyResponseBean E;
    private int F = -1;
    /* access modifiers changed from: private */
    public boolean G = false;
    private String H;
    private String I;
    private String J;
    private ObtenerTarjetasRecargaSubePresenter K;
    private Bundle L = null;
    public ActionBar mActionBar;
    @InjectView(2131364641)
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    @InjectView(2131364054)
    ListView menuLateralList;
    @Inject
    Bus p;
    @Inject
    IDataManager q;
    @Inject
    SettingsManager r;
    @Inject
    PushNotificationsManager s;
    @Inject
    public SessionManager sessionManager;
    @Inject
    AnalyticsManager t;
    @Inject
    SoftTokenManager u;
    MenuAdapter v;
    private Bus w;
    /* access modifiers changed from: private */
    public boolean x;
    private MenuOption y;
    private boolean z = false;

    public void backToPrincipalFragment() {
    }

    public void changeFragment(Fragment fragment, String str) {
    }

    public void configureActionBar() {
    }

    public void configureBackActionBar() {
    }

    public void configureLayout() {
    }

    public void initialize() {
    }

    public void onDrawerSlide(View view, float f) {
    }

    public void onDrawerStateChanged(int i) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        this.w = ReceiptEventBus.getInstance();
        this.w.register(this);
        if (getIntent() == null || !getIntent().getBooleanExtra(WSErrorHandlerConstants.INTENT_EXTRA_BACK_ANIMATION, false)) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        ButterKnife.inject((Activity) this);
        this.y = new MenuOption();
        this.C = new CheckVersionManager(this.sessionManager, this);
        super.setWsErrorListener(this);
        this.s.setUp(this);
        d();
        this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout, R.string.ACCESSIBILITY_MENU_OPEN, R.string.ACCESSIBILITY_MENU_CLOSE);
        this.menuLateralList.setAdapter(e());
        this.mDrawerLayout.addDrawerListener(this);
        if (bundle == null) {
            this.A = getIntent().getExtras().getString("MENU_ACTION");
            b();
            a(this.A);
            return;
        }
        String menuAction = this.sessionManager.getMenuAction();
        if (!TextUtils.isEmpty(menuAction)) {
            this.A = menuAction;
            Iterator it = this.listMenuOptions.iterator();
            while (it.hasNext()) {
                MenuOption menuOption = (MenuOption) it.next();
                menuOption.setSelected(false);
                if (menuOption.getAction().equalsIgnoreCase(this.A)) {
                    this.y = menuOption;
                    menuOption.setSelected(true);
                    a(menuOption);
                    Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag("tag_fragment_load");
                    BaseFragment baseFragment = (BaseFragment) findFragmentByTag;
                    baseFragment.setTAG(menuOption.getAction());
                    baseFragment.setErrorListener(this);
                    findFragmentByTag.setRetainInstance(true);
                }
            }
            this.v.notifyDataSetChanged();
        }
    }

    private void b() {
        this.B = "";
        if (getIntent().getExtras().getString(FragmentConstants.INTENT_DATA_RECARGA_SUBE) != null) {
            this.B = getIntent().getExtras().getString(FragmentConstants.INTENT_DATA_RECARGA_SUBE);
        }
    }

    private void c() {
        try {
            this.H = getIntent().getExtras().getString("SUBE_TITULO").isEmpty() ? "" : getIntent().getExtras().getString("SUBE_TITULO");
            this.I = getIntent().getExtras().getString("SUBE_DESCRIPCION").isEmpty() ? "" : getIntent().getExtras().getString("SUBE_DESCRIPCION");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.J = getIntent().getExtras().getString("SUBE_BUTTON").isEmpty() ? "" : getIntent().getExtras().getString("SUBE_BUTTON");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void d() {
        this.mActionBar = getSupportActionBar();
        if (this.mActionBar != null) {
            this.mActionBar.setElevation(BitmapDescriptorFactory.HUE_RED);
            this.mActionBar.setDisplayOptions(16);
            setActionBarType(ActionBarType.MENU);
            getSupportActionBar().getCustomView().findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SantanderRioMainActivity.this.switchDrawer();
                }
            });
        }
    }

    public void restartActionBar() {
        d();
        invalidateOptionsMenu();
    }

    public void lockMenu(boolean z2) {
        if (this.mDrawerLayout == null) {
            return;
        }
        if (z2) {
            this.mDrawerLayout.setDrawerLockMode(1);
        } else {
            this.mDrawerLayout.setDrawerLockMode(0);
        }
    }

    private MenuAdapter e() {
        this.z = false;
        String[] stringArray = getResources().getStringArray(R.array.menu_publico_options);
        String[] stringArray2 = getResources().getStringArray(R.array.menu_publico_actions);
        this.listMenuOptions = new ArrayList();
        for (int i = 0; i < stringArray.length; i++) {
            MenuOption menuOption = new MenuOption(OptionType.OPTION, false, stringArray[i], "", null, stringArray2[i]);
            if (stringArray2[i].equals(FragmentConstants.HOME)) {
                menuOption.setOptionType(OptionType.HOME);
            }
            this.listMenuOptions.add(menuOption);
        }
        this.v = new MenuAdapter(this, R.layout.menu_item, this.listMenuOptions, this);
        return this.v;
    }

    public MenuAdapter createMenuPrivadoAdapter(int i, String str, String str2, String str3) {
        this.z = true;
        this.v = super.createMenuPrivadoAdapter(i, str, str2, str3, this);
        return this.v;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        System.gc();
        this.p.register(this);
        if (this.D != null) {
            if (!this.D.getTAG().equalsIgnoreCase(TypeResult.BEAN_ERROR_RES_5.toString()) || this.D.getActivity() == this) {
                setBlankFragment();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        SantanderRioMainActivity.this.D.getReceiptFragment().setShareReceiptListener(SantanderRioMainActivity.this);
                        SantanderRioMainActivity.this.dismissProgress();
                        SantanderRioMainActivity.this.changeFragmentAnimation(SantanderRioMainActivity.this.D.getReceiptFragment());
                        SantanderRioMainActivity.this.D = null;
                    }
                }, 400);
            } else {
                gotoHome();
            }
        }
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.p.unregister(this);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onClikVolver() {
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag("tag_fragment_load");
        if ((findFragmentByTag instanceof ComprobanteSeguroAccidenteFragment) || (findFragmentByTag instanceof ComprobanteSeguroObjetoFragment)) {
            a(FragmentConstants.SEGUROS);
        }
        if (findFragmentByTag instanceof ComprobanteProgramaWomenFragment) {
            a(FragmentConstants.PROGRAMA_WOMEN);
        }
        boolean z2 = findFragmentByTag instanceof LimiteExtraccionComprobanteFragment;
        if (z2) {
            a(FragmentConstants.CUENTAS);
        }
        if (z2) {
            a(FragmentConstants.CUENTAS_PRIVADA);
        }
        if (findFragmentByTag instanceof RecalificacionComprobanteFragment) {
            a(FragmentConstants.RECALIFICACIONES);
        }
        if (findFragmentByTag instanceof ComprobanteTurnoFragment) {
            a(FragmentConstants.NERS_ENCOLADOR);
        }
        if (findFragmentByTag instanceof ComprobanteBlanqueoPinFragment) {
            a(FragmentConstants.BLANQUEO_DE_CLAVE_BANELCO);
        }
    }

    private void c(final int i) {
        if ((this.z && ((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.NAME) || ((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.DISCONNECT) {
            if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.DISCONNECT) {
                showLogOutMessage(true, i);
            }
            if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.HOME) {
                showLogOutMessage(true, i);
            } else if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.PRIVATE_ACCESS) {
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), String.format(getResources().getString(R.string.MSG_USER000030_Menu_avisoSalirCustom), new Object[]{((MenuOption) this.listMenuOptions.get(i)).getTitle()}), null, null, "Sí", "No", null);
                newInstance.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onSimpleActionButton() {
                    }

                    public void onPositiveButton() {
                        SantanderRioMainActivity.this.x = false;
                        ((MenuOption) SantanderRioMainActivity.this.listMenuOptions.get(i)).setSelected(false);
                        ((MenuOption) SantanderRioMainActivity.this.listMenuOptions.get(i)).setPressed(false);
                        SantanderRioMainActivity.this.v.notifyDataSetChanged();
                        SantanderRioMainActivity.this.deslogado(((MenuOption) SantanderRioMainActivity.this.listMenuOptions.get(i)).getAction());
                    }

                    public void onNegativeButton() {
                        SantanderRioMainActivity.this.x = false;
                        ((MenuOption) SantanderRioMainActivity.this.listMenuOptions.get(i)).setSelected(false);
                        ((MenuOption) SantanderRioMainActivity.this.listMenuOptions.get(i)).setPressed(false);
                        SantanderRioMainActivity.this.v.notifyDataSetChanged();
                    }
                });
                if (!this.x) {
                    newInstance.show(getSupportFragmentManager(), "Dialog");
                    this.x = true;
                }
            }
        } else if (this.C.isLoginEnabled() || ((MenuOption) this.listMenuOptions.get(i)).getOptionType() != OptionType.RESTRICTED_NO_CONNECTION) {
            Iterator it = this.listMenuOptions.iterator();
            while (it.hasNext()) {
                ((MenuOption) it.next()).setSelected(false);
            }
            if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.HOME) {
                gotoHomeActivity();
                return;
            }
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
            this.y = (MenuOption) this.listMenuOptions.get(i);
            a(this.y.getAction());
        }
    }

    public void setBlankFragment() {
        BlankFragment blankFragment = new BlankFragment();
        blankFragment.setsDisableFragmentAnimations(true);
        getSupportFragmentManager().beginTransaction().addToBackStack(EmptyFragment.class.getSimpleName()).replace(R.id.content_frame, blankFragment, "tag_fragment_load").commit();
    }

    public void setErrorFragment(String str) {
        getSupportFragmentManager().beginTransaction().addToBackStack(ErrorFragment.class.getSimpleName()).replace(R.id.content_frame, ErrorFragment.newInstance(str), "tag_fragment_load").commit();
        f();
    }

    public void setErrorFragment(String str, String str2) {
        getSupportFragmentManager().beginTransaction().addToBackStack(ErrorFragment.class.getSimpleName()).replace(R.id.content_frame, ErrorFragment.newInstance(str, str2), "tag_fragment_load").commit();
        f();
    }

    public void setErrorFragment(String str, int i) {
        getSupportFragmentManager().beginTransaction().addToBackStack(ErrorFragment.class.getSimpleName()).replace(R.id.content_frame, ErrorFragment.newInstance(str, i), "tag_fragment_load").commit();
        f();
    }

    public void setErrorFragment(String str, int i, String str2) {
        getSupportFragmentManager().beginTransaction().addToBackStack(ErrorFragment.class.getSimpleName()).replace(R.id.content_frame, ErrorFragment.newInstance(str, i, str2), "tag_fragment_load").commit();
        f();
    }

    public void setErrorFragment(String str, int i, int i2) {
        getSupportFragmentManager().beginTransaction().addToBackStack(ErrorFragment.class.getSimpleName()).replace(R.id.content_frame, ErrorFragment.newInstance(str, i, i2), "tag_fragment_load").commit();
        f();
    }

    private void f() {
        setActionBarType(ActionBarType.MENU);
        ((ImageView) getSupportActionBar().getCustomView().findViewById(R.id.toggle)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SantanderRioMainActivity.this.switchDrawer();
            }
        });
    }

    private Fragment a(MenuOption menuOption) {
        final IsbanDialogFragment isbanDialogFragment;
        final IsbanDialogFragment isbanDialogFragment2;
        MenuOption menuOption2 = menuOption;
        if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.MOBILE_BANKING)) {
            setActionBarType(ActionBarType.BACK);
            this.mDrawerLayout.setDrawerLockMode(1);
        } else {
            setActionBarType(ActionBarType.MENU);
            this.mDrawerLayout.setDrawerLockMode(0);
            getSupportActionBar().getCustomView().findViewById(R.id.toggle).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SantanderRioMainActivity.this.switchDrawer();
                }
            });
        }
        Fragment fragment = null;
        if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.CUENTAS)) {
            b(true);
            fragment = this.sessionManager.usuarioTieneCuentas() ? new CuentasFragment(FragmentConstants.CUENTAS) : ErrorFragment.newInstance(getString(R.string.ID544_ACCOUNTS_ERROR_LBL_NOACCOUNTS));
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.CUENTAS_PRIVADA)) {
            b(true);
            fragment = this.sessionManager.usuarioTieneCuentas() ? new CuentasFragment(FragmentConstants.CUENTAS_PRIVADA) : ErrorFragment.newInstance(getString(R.string.ID544_ACCOUNTS_ERROR_LBL_NOACCOUNTS));
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.CUENTAS_PRIVADA)) {
            b(true);
            fragment = this.sessionManager.usuarioTieneTarjetas() ? new TarjetasFragment() : ErrorFragment.newInstance(getString(R.string.ID544_ACCOUNTS_ERROR_LBL_NOACCOUNTS));
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.TARJETAS)) {
            b(true);
            fragment = this.sessionManager.usuarioTieneTarjetas() ? new TarjetasFragment() : ErrorFragment.newInstance(getString(R.string.ID547_CCARDS_ERROR_LBL_NOCCARDS));
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.CREDITOS) || menuOption.getAction().equalsIgnoreCase(FragmentConstants.SOLICITAR_CREDITO)) {
            b(true);
            fragment = TenenciaCreditosFragment.getInstance(menuOption.getAction());
            menuOption2.setAction(FragmentConstants.CREDITOS);
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.RECARGA_SUBE)) {
            b(true);
            fragment = new RecargaSubeWelcomeFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.ENVIO_EFECTIVO)) {
            b(true);
            if (this.sessionManager.getLoginUnico().getDestinosMyA() == null || !"SA".equalsIgnoreCase(this.sessionManager.getLoginUnico().getDestinosMyA().getEstadoSuscripcion()) || !this.u.isSoftTokenAvailable().booleanValue()) {
                setErrorFragment("Para poder realizar esta operación, necesitamos que actives tu Token de Seguridad desde un cajero automático de la red Banelco.", (int) R.drawable.error_continuacion, (int) R.string.USER200001_ENV_TITLE);
                if (!this.u.isSoftTokenAvailable().booleanValue()) {
                    isbanDialogFragment2 = IsbanDialogFragment.newInstance(null, getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getResources().getString(R.string.USER200008_ENV), getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), getResources().getString(R.string.USER200008_BTN));
                    isbanDialogFragment2.setAutoClose(Boolean.valueOf(false));
                } else {
                    isbanDialogFragment2 = IsbanDialogFragment.newInstance(getResources().getString(R.string.USER000008_ENV_TITLE), getResources().getString(R.string.USER000008_ENV_BODY), null, getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                }
                isbanDialogFragment2.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onPositiveButton() {
                        if (!isbanDialogFragment2.getAutoClose().booleanValue()) {
                            isbanDialogFragment2.setAutoClose(Boolean.valueOf(true));
                            isbanDialogFragment2.closeDialog();
                        }
                        SantanderRioMainActivity.this.openMenuOnError(FragmentConstants.ENVIO_EFECTIVO);
                    }

                    public void onNegativeButton() {
                        Intent intent = new Intent(SantanderRioMainActivity.this.getApplicationContext(), InfoActivity.class);
                        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                        intent.putExtra(InfoActivity.TITLE_TO_SHOW, SantanderRioMainActivity.this.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG_TIT));
                        intent.putExtra(InfoActivity.TEXT_TO_SHOW, SantanderRioMainActivity.this.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG));
                        SantanderRioMainActivity.this.t.trackScreen(SantanderRioMainActivity.this.getString(R.string.analytics_softtoken_ayuda_acerca_token_virtual));
                        SantanderRioMainActivity.this.startActivity(intent);
                    }

                    public void onSimpleActionButton() {
                        SantanderRioMainActivity.this.openMenuOnError(FragmentConstants.ENVIO_EFECTIVO);
                    }
                });
                isbanDialogFragment2.show(getSupportFragmentManager(), "DialogNewVersion");
            } else {
                fragment = new EnvioDineroFragment();
            }
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.BLANQUEO_DE_CLAVE_BANELCO)) {
            b(true);
            fragment = CompletarBlanqueoPinFragment.newInstance(this.sessionManager.getLoginUnico().getProductos().getTarjetasDebito().getTarjetas(), this.u.isSoftTokenAvailable().booleanValue());
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.EXTRACCION_SIN_TARJETA)) {
            b(true);
            if (this.sessionManager.getLoginUnico().getDestinosMyA() == null || !"SA".equalsIgnoreCase(this.sessionManager.getLoginUnico().getDestinosMyA().getEstadoSuscripcion()) || !this.u.isSoftTokenAvailable().booleanValue()) {
                setErrorFragment("Para poder realizar esta operación, necesitamos que actives tu Token de Seguridad desde un cajero automático de la red Banelco.", (int) R.drawable.error_continuacion, (int) R.string.USER200001_EXT_TITLE);
                if (!this.u.isSoftTokenAvailable().booleanValue()) {
                    isbanDialogFragment = IsbanDialogFragment.newInstance(null, getResources().getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getResources().getString(R.string.USER200008_EXT), getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), getResources().getString(R.string.USER200008_BTN));
                    isbanDialogFragment.setAutoClose(Boolean.valueOf(false));
                } else {
                    isbanDialogFragment = IsbanDialogFragment.newInstance(getResources().getString(R.string.USER000008_EXT_TITLE), getResources().getString(R.string.USER000008_EXT_BODY), null, getResources().getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null, null);
                }
                isbanDialogFragment.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onPositiveButton() {
                        if (!isbanDialogFragment.getAutoClose().booleanValue()) {
                            isbanDialogFragment.setAutoClose(Boolean.valueOf(true));
                            isbanDialogFragment.closeDialog();
                        }
                        SantanderRioMainActivity.this.openMenuOnError(FragmentConstants.EXTRACCION_SIN_TARJETA);
                    }

                    public void onNegativeButton() {
                        Intent intent = new Intent(SantanderRioMainActivity.this.getApplicationContext(), InfoActivity.class);
                        intent.putExtra(InfoActivity.TYPE_INFO, InfoType.DEFAULT);
                        intent.putExtra(InfoActivity.TITLE_TO_SHOW, SantanderRioMainActivity.this.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG_TIT));
                        intent.putExtra(InfoActivity.TEXT_TO_SHOW, SantanderRioMainActivity.this.getResources().getString(R.string.ID2222_TOKENSEGURIDAD_LBL_AYUDA_TOKSEG));
                        SantanderRioMainActivity.this.t.trackScreen(SantanderRioMainActivity.this.getString(R.string.analytics_softtoken_ayuda_acerca_token_virtual));
                        SantanderRioMainActivity.this.startActivity(intent);
                    }

                    public void onSimpleActionButton() {
                        SantanderRioMainActivity.this.openMenuOnError(FragmentConstants.EXTRACCION_SIN_TARJETA);
                    }
                });
                isbanDialogFragment.show(getSupportFragmentManager(), "DialogNewVersion");
            } else {
                fragment = new ExtraccionSinTarjetaFragment();
            }
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.SOFT_TOKEN)) {
            b(true);
            fragment = new SoftTokenFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.DEBIN)) {
            b(true);
            fragment = new DetalleDebinFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.PROGRAMA_WOMEN) || menuOption.getAction().equalsIgnoreCase(FragmentConstants.PROGRAMA_WOMEN_SUSCRIBIR)) {
            this.G = menuOption.getAction().equalsIgnoreCase(FragmentConstants.PROGRAMA_WOMEN_SUSCRIBIR);
            menuOption2.setAction(FragmentConstants.PROGRAMA_WOMEN);
            showProgress(VGetListaTjWomen.nameService);
            this.q.getListaTjWomen();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.NECESITAS_AYUDA)) {
            if (!(getSupportFragmentManager().findFragmentByTag("tag_fragment_load") instanceof WatsonFragment)) {
                this.t.trackCustomDimension(getResources().getString(R.string.analytics_screen_watson), 59, getResources().getString(R.string.analytics_custom_dimensions_screen_apertura));
                fragment = WatsonFragment.newInstance(this.sessionManager, this.r, this.t);
            }
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.COMODINES_SUPERCLUB)) {
            b(true);
            fragment = new SuperClubFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.PAGO_SERVICIOS)) {
            b(true);
            fragment = new NuevoPagoFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.SEGUROS)) {
            b(true);
            fragment = new SegurosFragment();
        } else if (DeepLinkUtils.isSegurosDeepLink(menuOption.getAction())) {
            b(true);
            fragment = SegurosFragment.getInstance(menuOption.getAction());
            menuOption2.setAction(FragmentConstants.SEGUROS);
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.NERS_ENCOLADOR)) {
            showProgress(VGetTurno.nameService);
            this.q.getTurno();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.PAGO_TARJETAS)) {
            b(true);
            if (!this.sessionManager.usuarioTieneCuentas()) {
                fragment = ErrorFragment.newInstance(getString(R.string.ID546_PAYTRANSFIXED_ERROR_LBL_NOACCOUNTS));
            } else if (!this.sessionManager.usuarioTieneTarjetas()) {
                fragment = ErrorFragment.newInstance(getString(R.string.ID547_CCARDS_ERROR_LBL_NOCCARDS));
            } else if (this.sessionManager.usuarioTieneCuentas() && this.sessionManager.usuarioTieneTarjetas()) {
                fragment = new PagoTarjetasFragment();
            }
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.RECARGA_CELULARES)) {
            b(true);
            fragment = this.sessionManager.usuarioTieneCuentas() ? RecargaCelularesFragment.getInstance() : ErrorFragment.newInstance(getString(R.string.ID546_PAYTRANSFIXED_ERROR_LBL_NOACCOUNTS));
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.TRANSFERENCIAS)) {
            b(true);
            if (!this.sessionManager.usuarioTieneCuentas()) {
                fragment = ErrorFragment.newInstance(getString(R.string.ID546_PAYTRANSFIXED_ERROR_LBL_NOACCOUNTS));
            } else if (this.sessionManager.getLoginUnico().getDestinosMyA() == null || "TO".equalsIgnoreCase(this.sessionManager.getLoginUnico().getDestinosMyA().getEstadoSuscripcion())) {
                IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, getResources().getString(R.string.MSG_USER000009_Transferencias_errorServidor), null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
                newInstance.setDialogListener(new IDialogListener() {
                    public void onItemSelected(String str) {
                    }

                    public void onNegativeButton() {
                    }

                    public void onPositiveButton() {
                    }

                    public void onSimpleActionButton() {
                        SantanderRioMainActivity.this.openMenuOnError(FragmentConstants.TRANSFERENCIAS);
                        SantanderRioMainActivity.this.setErrorFragment("", (int) R.drawable.error_continuacion, (int) R.string.ID442_TRANSFERENCE_LBL_TITLE);
                    }
                });
                newInstance.show(getSupportFragmentManager(), "DialogNewVersion");
            } else {
                this.sessionManager.setTipoCuenta("");
                this.sessionManager.setDestinatario("");
                fragment = new TransferenciasFragment();
            }
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.PLAZOS_FIJOS) || menuOption.getAction().equalsIgnoreCase(FragmentConstants.CONSTITUCION_PLAZO_FIJO)) {
            b(true);
            fragment = PlazosFijoFragment.newInstance(null, menuOption.getAction());
            menuOption2.setAction(FragmentConstants.PLAZOS_FIJOS);
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.SEGMENTO)) {
            b(true);
            fragment = SegmentoFragment.getInstance();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.SUSC_SORPRESA)) {
            b(true);
            fragment = new SuscripcionSorpresaFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.NOTIF_PUSH)) {
            b(true);
            fragment = new BuzonNotifPushFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.PROMOS)) {
            b(false);
            if (checkPlayServices()) {
                fragment = PromocionesFragment.getInstance();
            } else {
                showMapsDialog();
                fragment = ErrorFragment.newInstance("");
            }
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.CAJEROS)) {
            b(false);
            if (checkPlayServices()) {
                fragment = new CajerosFragment();
            } else {
                showMapsDialog();
                fragment = ErrorFragment.newInstance("");
            }
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.SUCURSALES)) {
            b(false);
            if (checkPlayServices()) {
                fragment = new SucursalesHomeFragment();
            } else {
                showMapsDialog();
                fragment = ErrorFragment.newInstance("");
            }
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.NUMEROS_UTILES)) {
            b(true);
            fragment = new NumerosUtilesFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.RECARGA_SUBE)) {
            b(true);
            fragment = new RegistrarSubeFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.NOTIFICACIONES)) {
            b(true);
            fragment = new NotificacionesFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.MOBILE_BANKING)) {
            b(true);
            fragment = new LoginFragment();
            ((LoginFragment) fragment).setLoginListener(this);
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.COMPRAVENTA_DOLARES)) {
            fragment = new BuySellDollarsFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.FONDOS_INVERSION) || menuOption.getAction().equalsIgnoreCase(FragmentConstants.FONDOS_SUSCRIPCION)) {
            fragment = TenenciaFondosFragment.newInstance(null, menuOption.getAction());
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.MARCACION_POR_VIAJE)) {
            fragment = new MarcacionPorViajeFragment();
        } else if (menuOption.getAction().equalsIgnoreCase(FragmentConstants.TENENCIA_INVERSIONES)) {
            b(true);
            fragment = new TenenciaInversionesFragments();
        }
        setFragmentErrorListener(fragment, menuOption.getAction());
        return fragment;
    }

    @Subscribe
    public void getTurno(GetTurnoEvent getTurnoEvent) {
        dismissProgress();
        final GetTurnoEvent getTurnoEvent2 = getTurnoEvent;
        AnonymousClass9 r0 = new BaseWSResponseHandler(this, TypeOption.INITIAL_VIEW, FragmentConstants.SEGURO_COTIZACION, this, "Solicitar Ticket para Atención en Caja") {
            /* access modifiers changed from: protected */
            public void onOk() {
                SantanderRioMainActivity.this.changeFragment(SantanderRioMainActivity.validarTurno(getTurnoEvent2));
                SantanderRioMainActivity.this.v.notifyDataSetChanged();
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                SantanderRioMainActivity.this.changeFragment(SolicitudTurnoFragment.newInstance(getTurnoEvent2));
                SantanderRioMainActivity.this.v.notifyDataSetChanged();
            }

            /* access modifiers changed from: protected */
            public void onRes8Error() {
                SantanderRioMainActivity.this.changeFragment(SolicitudTurnoFragment.newInstance(true, getTurnoEvent2));
                SantanderRioMainActivity.this.v.notifyDataSetChanged();
            }
        };
        r0.handleWSResponse(getTurnoEvent);
    }

    public static Fragment validarTurno(GetTurnoEvent getTurnoEvent) {
        if (getTurnoEvent.getResponse().getTurnoBodyResponseBean.getTurno() != null) {
            return UbicacionTurnoFragment.newInstance(getTurnoEvent.getResponse().getTurnoBodyResponseBean);
        }
        return SolicitudTurnoFragment.newInstance(getTurnoEvent);
    }

    public void showLogOutMessage(final boolean z2, final int i) {
        if (this.sessionManager == null || !this.sessionManager.getFlagMustShowPopUp()) {
            super.onBackPressed();
        } else if (this.sessionManager.getLoginUnico().getCalificacion() == null || this.sessionManager.getLoginUnico().getCalificacion().idEncuesta == null || this.sessionManager.getLoginUnico().getCalificacion().idEncuesta.isEmpty() || this.sessionManager.getLoginUnico().getCalificacion().idEncuesta.equalsIgnoreCase("0")) {
            a((MenuOption) this.listMenuOptions.get(i), z2);
        } else {
            EncuestaDialogFragment.newInstance(this.sessionManager.getLoginUnico().getCalificacion().listaPreguntas.preguntas, new EncuestaDialogListener() {
                public void onRejectEncuesta() {
                    SantanderRioMainActivity.this.a((MenuOption) SantanderRioMainActivity.this.listMenuOptions.get(i), z2);
                }

                public void onCallService(List<RespuestaBean> list) {
                    SantanderRioMainActivity.this.q.logOutSession(SantanderRioMainActivity.this.sessionManager.getLoginUnico().getCalificacion().idEncuesta, new ListaRespuestas(list));
                }

                public void onCompleteEncuesta() {
                    ((MenuOption) SantanderRioMainActivity.this.listMenuOptions.get(i)).setSelected(false);
                    ((MenuOption) SantanderRioMainActivity.this.listMenuOptions.get(i)).setPressed(false);
                    SantanderRioMainActivity.this.v.notifyDataSetChanged();
                    SantanderRioMainActivity.this.sessionManager.setFlagMustShowPopUp(false);
                    SantanderRioMainActivity.this.gotoHomeActivity();
                }
            }).show(getSupportFragmentManager(), "DialogEncuesta");
        }
    }

    /* access modifiers changed from: private */
    public void a(final MenuOption menuOption, boolean z2) {
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getResources().getString(R.string.MSG_USER000030_Menu_avisoSalir), null, null, "Sí", "No", null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                newInstance.dismiss();
                menuOption.setSelected(false);
                menuOption.setPressed(false);
                SantanderRioMainActivity.this.v.notifyDataSetChanged();
                SantanderRioMainActivity.this.q.logOutSession();
                SantanderRioMainActivity.this.sessionManager.setFlagMustShowPopUp(false);
                SantanderRioMainActivity.this.gotoHomeActivity();
            }

            public void onNegativeButton() {
                menuOption.setSelected(false);
                menuOption.setPressed(false);
                SantanderRioMainActivity.this.v.notifyDataSetChanged();
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    public void changeFragment(Fragment fragment) {
        changeFragment(fragment, true);
    }

    public void changeFragment(final Fragment fragment, final boolean z2) {
        int i = fragment instanceof ShareReceiptInterface ? HttpStatus.SC_INTERNAL_SERVER_ERROR : 0;
        try {
            ((ITRSABaseFragment) fragment).setShareReceiptListener(this);
        } catch (ClassCastException unused) {
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (z2) {
                    SantanderRioMainActivity.this.getSupportFragmentManager().beginTransaction().addToBackStack(fragment.getClass().getSimpleName()).replace(R.id.content_frame, fragment, "tag_fragment_load").commit();
                } else {
                    SantanderRioMainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, "tag_fragment_load").commit();
                }
            }
        }, (long) i);
    }

    public void changeFragmentAnimation(Fragment fragment) {
        if (fragment instanceof LoginFragment) {
            getSupportFragmentManager().beginTransaction().addToBackStack(fragment.getClass().getSimpleName()).setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.content_frame, fragment, "tag_fragment_load").commit();
        } else {
            getSupportFragmentManager().beginTransaction().addToBackStack(fragment.getClass().getSimpleName()).setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left).replace(R.id.content_frame, fragment, "tag_fragment_load").commit();
        }
    }

    public void changeFragmentAnimation(Fragment fragment, int i, int i2) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(i, i2).addToBackStack(fragment.getClass().getSimpleName()).replace(R.id.content_frame, fragment, "tag_fragment_load").commit();
    }

    public void changeFragmentAnimation(Fragment fragment, int i, int i2, int i3, int i4) {
        getSupportFragmentManager().beginTransaction().addToBackStack(fragment.getClass().getSimpleName()).setCustomAnimations(i, i2, i3, i4).replace(R.id.content_frame, fragment, "tag_fragment_load").commit();
    }

    public void addFragment(Fragment fragment, int i, int i2) {
        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(i, i2, i, i2).add(R.id.content_frame, fragment, "tag_fragment_load").commit();
    }

    public void backLastFragment() {
        getSupportFragmentManager().popBackStackImmediate();
        hideKeyboard();
    }

    private void a(String str) {
        if (!str.equals(FragmentConstants.SIN_PRODUCTOS)) {
            if (!str.equalsIgnoreCase(FragmentConstants.RECARGA_SUBE_WELCOME)) {
                if (!str.equalsIgnoreCase(FragmentConstants.AGREGAR_SUBE)) {
                    Iterator it = this.listMenuOptions.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        MenuOption menuOption = (MenuOption) it.next();
                        menuOption.setSelected(false);
                        if (!DeepLinkUtils.isAlternativeDP(menuOption.getAction(), str)) {
                            if (menuOption.getAction().equalsIgnoreCase(str)) {
                                this.A = str;
                                this.sessionManager.setMenuAction(this.A);
                                this.y = menuOption;
                                menuOption.setSelected(true);
                                b(menuOption);
                                break;
                            }
                        } else {
                            a(menuOption, str);
                            break;
                        }
                    }
                } else {
                    setActionBarType(ActionBarType.BACK);
                    b(true);
                    c();
                    changeFragment(RecargaSubeWelcomeFragment.newInstance(this.B, this.H, this.I, this.J));
                }
            } else {
                setActionBarType(ActionBarType.BACK);
                b(true);
                c();
                RecargaSubeWelcomeFragment newInstance = RecargaSubeWelcomeFragment.newInstance(this.B, this.H, this.I, this.J);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                changeFragment(newInstance);
            }
        } else {
            a((int) R.string.ID14_PRIVATEMENU_BTN_ACCOUNTS, (int) R.string.IDXXX_DEEP_LINK_NO_PRODUCTS);
        }
        this.v.notifyDataSetChanged();
    }

    public void selectMenuItem(String str) {
        if (!str.equals(FragmentConstants.SIN_PRODUCTOS)) {
            Iterator it = this.listMenuOptions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MenuOption menuOption = (MenuOption) it.next();
                menuOption.setSelected(false);
                if (menuOption.getAction().equalsIgnoreCase(str)) {
                    this.A = str;
                    this.sessionManager.setMenuAction(this.A);
                    this.y = menuOption;
                    menuOption.setSelected(true);
                    break;
                }
            }
        } else {
            ErrorFragment newInstance = ErrorFragment.newInstance(getString(R.string.IDXXX_USER_HAVE_NO_PRODUCTS), (int) R.drawable.cruz_blanca_fondo_celeste, (int) R.string.ID14_PRIVATEMENU_BTN_ACCOUNTS);
            b(FragmentConstants.SIN_PRODUCTOS);
            if (newInstance != null) {
                changeFragment(newInstance);
            }
        }
        this.v.notifyDataSetChanged();
    }

    private void a(MenuOption menuOption, String str) {
        this.A = menuOption.getAction();
        this.sessionManager.setMenuAction(this.A);
        this.y = menuOption;
        menuOption.setAction(str);
        menuOption.setSelected(true);
        b(menuOption);
    }

    private void b(MenuOption menuOption) {
        Fragment a = a(menuOption);
        if (this.L != null) {
            a.setArguments(this.L);
        }
        if (a != null) {
            changeFragment(a, !g());
        }
    }

    private boolean g() {
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag("tag_fragment_load");
        return findFragmentByTag != null && (findFragmentByTag.getClass().getSimpleName().equalsIgnoreCase(CajerosFragment.class.getSimpleName()) || findFragmentByTag.getClass().getSimpleName().equalsIgnoreCase(SucursalesHomeFragment.class.getSimpleName()) || findFragmentByTag.getClass().getSimpleName().equalsIgnoreCase(NumerosUtilesFragment.class.getSimpleName()) || findFragmentByTag.getClass().getSimpleName().equalsIgnoreCase(PromocionesFragment.class.getSimpleName()) || findFragmentByTag.getClass().getSimpleName().equalsIgnoreCase(SoftTokenFragment.class.getSimpleName()) || findFragmentByTag.getClass().getSimpleName().equalsIgnoreCase(BuzonNotifPushFragment.class.getSimpleName()));
    }

    public void startNewActivity(Intent intent) {
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.fillInStackTrace();
            Log.e("@dev", "Exception:", e);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1001) {
            switch (i) {
                case 102:
                    if (i2 == -1) {
                        if (intent.hasExtra("SUBE_FLOW_GO_TO_LOGIN") && intent.getBooleanExtra("SUBE_FLOW_GO_TO_LOGIN", false)) {
                            goToAgregarTarjetaSube();
                            break;
                        } else {
                            getIntent().removeExtra("SUBE_TITULO");
                            getIntent().putExtra("SUBE_TITULO", intent.getStringExtra("SUBE_TITULO"));
                            getIntent().removeExtra("SUBE_DESCRIPCION");
                            getIntent().putExtra("SUBE_DESCRIPCION", intent.getStringExtra("SUBE_DESCRIPCION"));
                            getIntent().removeExtra("SUBE_BUTTON");
                            getIntent().putExtra("SUBE_BUTTON", intent.getStringExtra("SUBE_BUTTON"));
                            if (intent.hasExtra(FragmentConstants.INTENT_DATA_RECARGA_SUBE)) {
                                getIntent().removeExtra(FragmentConstants.INTENT_DATA_RECARGA_SUBE);
                                this.B = intent.getStringExtra(FragmentConstants.INTENT_DATA_RECARGA_SUBE);
                            }
                            a(FragmentConstants.RECARGA_SUBE_WELCOME);
                            break;
                        }
                    }
                    break;
                case 103:
                    if (i2 == -1) {
                        if (!this.sessionManager.getSession().isEmpty()) {
                            goToAgregarTarjetaSube();
                            break;
                        } else {
                            LoginFragment loginFragment = new LoginFragment();
                            loginFragment.setLoginListener(this);
                            changeFragment(loginFragment);
                            break;
                        }
                    }
                    break;
            }
        } else if (i2 == 0) {
            Toast.makeText(this, getString(R.string.NEED_PLAY_SERVICES), 0).show();
            finish();
        }
        if (i2 == -1 && intent != null && intent.hasExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION)) {
            for (int i3 = 0; i3 < this.listMenuOptions.size(); i3++) {
                if (intent.getIntExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, -1) == i3) {
                    ((MenuOption) this.listMenuOptions.get(i3)).setSelected(true);
                    this.y.setSelected(false);
                } else {
                    ((MenuOption) this.listMenuOptions.get(i3)).setSelected(false);
                }
            }
            this.v.notifyDataSetChanged();
        }
        if (i == 1 && i2 == -1) {
            String stringExtra = intent.getStringExtra("suscripcion");
            if (stringExtra == null || !stringExtra.equalsIgnoreCase("CANCEL")) {
                makeLogin(this.sessionManager.getLoginUnico());
            } else {
                if (this.q != null) {
                    this.q.logOutSession();
                }
                deslogado(false);
            }
        }
        a(i, i2, intent);
        super.onActivityResult(i, i2, intent);
    }

    private void a(int i, int i2, Intent intent) {
        if (i == 65543 && !activityResultHandler(i2, intent) && i2 == -1) {
            a(intent);
        }
    }

    private void a(Intent intent) {
        if (intent != null) {
            try {
                if (intent.getBooleanExtra(SegurosConstants.CONFIRMACION, false)) {
                    ContratarSeguroObjetoBodyResponseBean contratarSeguroObjetoBodyResponseBean = (ContratarSeguroObjetoBodyResponseBean) intent.getExtras().get("CONTRATAR_SEGURO_BODY");
                    ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("LIST_GENERIC");
                    ArrayList parcelableArrayListExtra2 = intent.getParcelableArrayListExtra("LIST_PREGUNTAS");
                    FamiliaBean familiaBean = (FamiliaBean) intent.getExtras().get("FAMILIA_SELECTED");
                    GetCotizacionSeguroObjetoBodyResponseBean getCotizacionSeguroObjetoBodyResponseBean = (GetCotizacionSeguroObjetoBodyResponseBean) intent.getExtras().get("COTIZACIÓN");
                    overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity);
                    ReceiptEventBus.getInstance().post(new ReceiptEvent((ITRSABaseFragment) ComprobanteSeguroObjetoFragment.newInstance(contratarSeguroObjetoBodyResponseBean, parcelableArrayListExtra, parcelableArrayListExtra2, familiaBean, getCotizacionSeguroObjetoBodyResponseBean), FragmentConstants.SEGUROS));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void logado(LoginEvent loginEvent) {
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag("tag_fragment_load");
        if ((findFragmentByTag == null || !(findFragmentByTag instanceof LoginFragment) || !((LoginFragment) findFragmentByTag).getHasToQueueLoginEvent()) && loginEvent.getResult() == TypeResult.OK) {
            LoginUnicoBodyResponseBean loginUnicoBodyResponseBean = ((LoginUnicoResponseBean) loginEvent.getBeanResponse()).getLoginUnicoBodyResponseBean();
            if (loginUnicoBodyResponseBean.getEstado().equalsIgnoreCase(LoginUnicoBodyResponseBean.ESTADO_OK)) {
                DatosPersonales datosPersonales = loginUnicoBodyResponseBean.getDatosPersonales();
                DestinosMyA destinosMyA = loginUnicoBodyResponseBean.getDestinosMyA();
                if (datosPersonales.getAceptaTyC().equalsIgnoreCase(LoginConstants.ACEPTA_TERMINOS) && (destinosMyA.getEstadoSuscripcion().equalsIgnoreCase(LoginConstants.SUSC_SA) || destinosMyA.getEstadoSuscripcion().equalsIgnoreCase(LoginConstants.SUSC_TO))) {
                    String estadoSuscripcion = loginUnicoBodyResponseBean.getDestinosMyA().getEstadoSuscripcion();
                    if (!estadoSuscripcion.equalsIgnoreCase(LoginConstants.SUSC_NO_SUSC) && !estadoSuscripcion.equalsIgnoreCase(LoginConstants.SUSC_ALTA_CEL) && !estadoSuscripcion.equalsIgnoreCase(LoginConstants.SUSC_ALTA_MAIL)) {
                        makeLogin(loginUnicoBodyResponseBean);
                    }
                }
            }
        }
    }

    public void deslogado(boolean z2) {
        gotoHome();
    }

    public void deslogado(String str) {
        this.sessionManager.setFlagMustShowPopUp(false);
        this.menuLateralList.setAdapter(e());
        a(str);
        if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        }
    }

    @Subscribe
    public void makeLogin(LoginUnicoBodyResponseBean loginUnicoBodyResponseBean) {
        DatosPersonales datosPersonales = loginUnicoBodyResponseBean.getDatosPersonales();
        f();
        this.t.trackEvent(getString(R.string.analytics_category_login), getString(R.string.analytics_action_login_exitoso), datosPersonales.getNup());
        this.L = null;
        this.s.register(this, datosPersonales.getNup());
        int parseInt = (loginUnicoBodyResponseBean.getCrm() == null || !UtilString.isNumber(loginUnicoBodyResponseBean.getCrm().cantidad)) ? 0 : Integer.parseInt(loginUnicoBodyResponseBean.getCrm().cantidad);
        String str = "";
        if (!datosPersonales.getSegmento().isEmpty() && !datosPersonales.getUrlSegmento().isEmpty()) {
            str = datosPersonales.getSegmento();
            Crashlytics.setString("segmento", str);
        }
        ListView listView = this.menuLateralList;
        StringBuilder sb = new StringBuilder();
        sb.append(datosPersonales.getNombre());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(datosPersonales.getApellido());
        listView.setAdapter(createMenuPrivadoAdapter(parseInt, sb.toString(), datosPersonales.getUltimoAcceso(), str));
        if (!this.B.isEmpty()) {
            this.sessionManager.saveLoginPublic();
            a(datosPersonales);
        } else if (!TextUtils.isEmpty(this.sessionManager.getCustomURLNavFragment())) {
            i();
        } else if (!this.sessionManager.usuarioTieneCuentas() && this.sessionManager.usuarioTieneTarjetas()) {
            a(FragmentConstants.TARJETAS);
        } else if (this.sessionManager.usuarioTieneCuentas() && !this.sessionManager.usuarioTieneTarjetas()) {
            h();
        } else if (this.sessionManager.usuarioTieneCuentas() || this.sessionManager.usuarioTieneTarjetas()) {
            h();
        } else {
            a(FragmentConstants.SIN_PRODUCTOS);
        }
    }

    private void h() {
        if (!this.sessionManager.getLoginUnico().getDatosPersonales().getTipoCliente().equalsIgnoreCase(TipoCliente.BP.getTipoCliente())) {
            a(FragmentConstants.CUENTAS);
        } else {
            a(FragmentConstants.CUENTAS_PRIVADA);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007d, code lost:
        if (r4.sessionManager.usuarioTieneCuentas() != false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0085, code lost:
        if (r4.sessionManager.usuarioTieneTarjetas() == false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0087, code lost:
        a((int) ar.com.santander.rio.mbanking.R.string.ID14_PRIVATEMENU_BTN_ACCOUNTS, (int) ar.com.santander.rio.mbanking.R.string.IDXXX_DEEP_LINK_NO_ACCOUNTS);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x008a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0091, code lost:
        if (r4.sessionManager.usuarioTieneTarjetas() != false) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0093, code lost:
        r0 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00bc, code lost:
        a(r0);
        r4.sessionManager.setCustomURLNavFragment("");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c6, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i() {
        /*
            r4 = this;
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r4.sessionManager
            boolean r0 = r0.usuarioTieneCuentas()
            r1 = 2131755590(0x7f100246, float:1.9142064E38)
            if (r0 != 0) goto L_0x001a
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r4.sessionManager
            boolean r0 = r0.usuarioTieneTarjetas()
            if (r0 != 0) goto L_0x001a
            r0 = 2131756362(0x7f10054a, float:1.914363E38)
            r4.a(r1, r0)
            return
        L_0x001a:
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r4.sessionManager
            java.lang.String r0 = r0.getCustomURLNavFragment()
            r2 = -1
            int r3 = r0.hashCode()
            switch(r3) {
                case -2145555105: goto L_0x005b;
                case -979033824: goto L_0x0051;
                case -894292643: goto L_0x0047;
                case 929088752: goto L_0x003d;
                case 1157777132: goto L_0x0033;
                case 2066859031: goto L_0x0029;
                default: goto L_0x0028;
            }
        L_0x0028:
            goto L_0x0064
        L_0x0029:
            java.lang.String r3 = "PAGO_SERVICIOS"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0064
            r2 = 0
            goto L_0x0064
        L_0x0033:
            java.lang.String r3 = "PAGO_TARJETAS"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0064
            r2 = 1
            goto L_0x0064
        L_0x003d:
            java.lang.String r3 = "FONDOS_SUSCRIPCION"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0064
            r2 = 4
            goto L_0x0064
        L_0x0047:
            java.lang.String r3 = "SOLICITAR_CREDITO"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0064
            r2 = 3
            goto L_0x0064
        L_0x0051:
            java.lang.String r3 = "CONSTITUCION_PLAZO_FIJO"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0064
            r2 = 2
            goto L_0x0064
        L_0x005b:
            java.lang.String r3 = "PROGRAMA_WOMEN_SUSCRIBIR"
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0064
            r2 = 5
        L_0x0064:
            r3 = 2131756361(0x7f100549, float:1.9143627E38)
            switch(r2) {
                case 0: goto L_0x0096;
                case 1: goto L_0x0096;
                case 2: goto L_0x006b;
                case 3: goto L_0x006b;
                case 4: goto L_0x0077;
                case 5: goto L_0x008b;
                default: goto L_0x006a;
            }
        L_0x006a:
            goto L_0x00bc
        L_0x006b:
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r4.sessionManager
            boolean r2 = r2.usuarioTieneCuentas()
            if (r2 != 0) goto L_0x0077
            r4.a(r1, r3)
            return
        L_0x0077:
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r4.sessionManager
            boolean r2 = r2.usuarioTieneCuentas()
            if (r2 != 0) goto L_0x008b
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r4.sessionManager
            boolean r2 = r2.usuarioTieneTarjetas()
            if (r2 == 0) goto L_0x008b
            r4.a(r1, r3)
            return
        L_0x008b:
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r4.sessionManager
            boolean r1 = r1.usuarioTieneTarjetas()
            if (r1 != 0) goto L_0x00bc
            java.lang.String r0 = ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants.CUENTAS
            goto L_0x00bc
        L_0x0096:
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r4.sessionManager
            boolean r2 = r2.usuarioTieneCuentas()
            if (r2 != 0) goto L_0x00aa
            ar.com.santander.rio.mbanking.managers.session.SessionManager r2 = r4.sessionManager
            boolean r2 = r2.usuarioTieneTarjetas()
            if (r2 == 0) goto L_0x00aa
            r4.a(r1, r3)
            return
        L_0x00aa:
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r4.sessionManager
            boolean r1 = r1.usuarioTieneTarjetas()
            if (r1 != 0) goto L_0x00bc
            ar.com.santander.rio.mbanking.managers.session.SessionManager r1 = r4.sessionManager
            boolean r1 = r1.usuarioTieneCuentas()
            if (r1 == 0) goto L_0x00bc
            java.lang.String r0 = "PAGO_SERVICIOS"
        L_0x00bc:
            r4.a(r0)
            ar.com.santander.rio.mbanking.managers.session.SessionManager r0 = r4.sessionManager
            java.lang.String r1 = ""
            r0.setCustomURLNavFragment(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity.i():void");
    }

    private void a(int i, int i2) {
        ErrorFragment newInstance = ErrorFragment.newInstance(getString(i2), (int) R.drawable.cruz_blanca_fondo_celeste, getString(i));
        b(FragmentConstants.SIN_PRODUCTOS);
        if (newInstance != null) {
            changeFragment(newInstance);
        }
        this.sessionManager.setCustomURLNavFragment("");
    }

    public void setFragmentErrorListener(Fragment fragment, String str) {
        if (fragment != null) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            baseFragment.setTAG(str);
            baseFragment.setErrorListener(this);
            fragment.setRetainInstance(true);
        }
        if (fragment instanceof LoginFragment) {
            ((LoginFragment) fragment).setLoginListener(this);
        }
    }

    private void a(DatosPersonales datosPersonales) {
        if (this.B.equalsIgnoreCase(FragmentConstants.REGISTRAR_SUBE) || this.B.equalsIgnoreCase(FragmentConstants.RECARGA_SUBE_SIN_DATOS)) {
            if (this.sessionManager.usuarioTieneCuentasEnPesos()) {
                goToRegistrarSube("", "");
            } else {
                callErrorScreen("Recarga SUBE", getString(R.string.ID546_PAYTRANSFIXED_ERROR_LBL_NOACCOUNTS));
            }
        } else if (this.B.equalsIgnoreCase(FragmentConstants.AGREGAR_SUBE) || this.B.equalsIgnoreCase(FragmentConstants.SUBE_FLOW_AGREGAR) || this.B.equalsIgnoreCase(FragmentConstants.AGREGAR_SUBE_SIN_TARJETA)) {
            if (this.sessionManager.usuarioTieneCuentasEnPesos()) {
                goToAgregarTarjetaSube();
            } else {
                callErrorScreen("Recarga SUBE", getString(R.string.ID546_PAYTRANSFIXED_ERROR_LBL_NOACCOUNTS));
            }
        } else if (this.B.equalsIgnoreCase(FragmentConstants.RECARGA_SUBE)) {
            chequearTarjetasUsuarioRegistradas(datosPersonales);
        }
    }

    public void chequearTarjetasUsuarioRegistradas(DatosPersonales datosPersonales) {
        String nup = !TextUtils.isEmpty(this.sessionManager.getNup()) ? this.sessionManager.getNup() : new SharedPreferencesData(getApplicationContext()).getPreference(getString(R.string.NUP));
        String session = TextUtils.isEmpty(this.sessionManager.getSession()) ? "" : this.sessionManager.getSession();
        this.K = new ObtenerTarjetasRecargaSubePresenter(this.p, this.q, this.r, this);
        this.K.attachView(this);
        this.K.getTarjetasSubeData("SUBE", datosPersonales.getNroDocumento(), datosPersonales.getFechaNacimiento(), session, nup);
    }

    public void closeDrawer() {
        if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        }
    }

    public void switchDrawer() {
        try {
            if (this.A.equals(FragmentConstants.BLANQUEO_DE_CLAVE_BANELCO)) {
                ShareReceiptInterface shareReceiptInterface = (ShareReceiptInterface) getSupportFragmentManager().findFragmentByTag("tag_fragment_load");
            } else {
                ((ShareReceiptInterface) getSupportFragmentManager().findFragmentByTag("tag_fragment_load")).canExitMenu();
            }
        } catch (ClassCastException e) {
            e.fillInStackTrace();
        }
        if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        } else {
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        }
    }

    public AnalyticsManager getAnalyticsManager() {
        return this.t;
    }

    public void showProgressIndicator(String str) {
        showProgress(str);
    }

    public void dismissProgressIndicator() {
        dismissProgress();
    }

    public void attachView() {
        if (!this.K.isViewAttached()) {
            this.K.attachView(this);
        }
    }

    public void detachView() {
        if (this.K != null && this.K.isViewAttached()) {
            this.K.detachView();
        }
    }

    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
            return;
        }
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag("tag_fragment_load");
        if (findFragmentByTag == null || !findFragmentByTag.isVisible() || !(findFragmentByTag instanceof BackEventListener)) {
            super.onBackPressed();
        } else {
            ((BackEventListener) findFragmentByTag).onBackPressed();
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void callErrorScreen(String str, String str2) {
        dismissProgressBar();
        detachView();
        Intent intent = new Intent(this, ErrorRecargaActivity.class);
        intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE, str);
        intent.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, str2);
        startActivityForResult(intent, WSErrorHandlerConstants.INTENT_RES4_REQUEST_CODE);
    }

    public void onWebServiceErrorEvent(WebServiceEvent webServiceEvent, String str) {
        checkError(webServiceEvent, str);
        if (!str.equalsIgnoreCase(FragmentConstants.MOBILE_BANKING)) {
            TypeOption typeOption = webServiceEvent.getTypeOption();
            TypeResult result = webServiceEvent.getResult();
            switch (typeOption) {
                case INITIAL_VIEW:
                    if (result == TypeResult.SERVER_ERROR || result == TypeResult.UNKNOWN_ERROR) {
                        setErrorFragment(Html.fromHtml("").toString());
                        return;
                    } else if (result == TypeResult.BEAN_ERROR_RES_1 || result == TypeResult.BEAN_ERROR_RES_2 || result == TypeResult.BEAN_ERROR_RES_3) {
                        setErrorFragment("");
                        return;
                    } else if (result == TypeResult.BEAN_ERROR_RES_4) {
                        setErrorFragment(webServiceEvent.getMessageToShow());
                        if (str.equals(FragmentConstants.RECARGA_CELULARES)) {
                            setErrorFragment(Html.fromHtml(webServiceEvent.getMessageToShow()).toString(), (int) R.drawable.error_continuacion, (int) R.string.ID364_CELULAR_PRE_LBL_TITLE);
                            return;
                        }
                        return;
                    } else if (result == TypeResult.BEAN_ERROR_RES_8) {
                        setErrorFragment(webServiceEvent.getMessageToShow(), (int) R.drawable.ico_reloj_gris);
                        b(str);
                        return;
                    } else {
                        return;
                    }
                case INTERMDIATE_VIEW:
                    if (result == TypeResult.BEAN_ERROR_RES_4) {
                        setErrorFragment(webServiceEvent.getMessageToShow());
                        b(str);
                        return;
                    } else if (result == TypeResult.BEAN_ERROR_RES_8) {
                        setErrorFragment(webServiceEvent.getMessageToShow(), (int) R.drawable.ico_reloj_gris);
                        b(str);
                        return;
                    } else {
                        return;
                    }
                case TRANSACTIONAL_FINAL_VIEW:
                    if (result == TypeResult.BEAN_ERROR_RES_4) {
                        setErrorFragment(webServiceEvent.getMessageToShow());
                        b(str);
                        return;
                    } else if (result == TypeResult.BEAN_ERROR_RES_8) {
                        setErrorFragment(webServiceEvent.getMessageToShow(), (int) R.drawable.ico_reloj_gris);
                        b(str);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void clearScreenData() {
        try {
            Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag("tag_fragment_load");
            if (findFragmentByTag instanceof TenenciaCreditosFragment) {
                ((TenenciaCreditosFragment) findFragmentByTag).presenterLongTermDeposit.onLoad();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void openMenuOnError(String str) {
        openMenu(str);
    }

    public void onTransactionalTimeOut() {
        goToOption(FragmentConstants.CUENTAS);
    }

    public void onBackWindow(String str) {
        if (str.equalsIgnoreCase(FragmentConstants.MOBILE_BANKING)) {
            return;
        }
        if (getActionBarType() == ActionBarType.BACK) {
            onBackPressed();
        } else {
            goToOption(str);
        }
    }

    public void onGotoCuentas() {
        goToOption(FragmentConstants.CUENTAS);
    }

    public void onGotoBack() {
        onBackPressed();
    }

    private void b(String str) {
        if (!str.equalsIgnoreCase(FragmentConstants.MOBILE_BANKING)) {
            lockMenu(false);
            restartActionBar();
        }
    }

    public void openMenu(String str) {
        if (!this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START) && !str.equalsIgnoreCase(FragmentConstants.MOBILE_BANKING)) {
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.q.logOutSession();
        if (this.w != null) {
            this.w.unregister(this);
        }
        super.onDestroy();
    }

    public void onDrawerOpened(View view) {
        hideKeyboard();
    }

    public void onDrawerClosed(View view) {
        if (this.F > -1) {
            c(this.F);
            this.F = -1;
        }
        hideKeyboard();
    }

    public void onLoginOk() {
        makeLogin(this.sessionManager.getLoginUnico());
    }

    public void goToOption(String str) {
        if (str.equals(FragmentConstants.CUENTAS) && !this.sessionManager.usuarioTieneCuentas()) {
            str = FragmentConstants.TARJETAS;
        }
        a(str);
    }

    public void loadActionBarShare(int i, CustomActionBarListener customActionBarListener) {
        a(i, customActionBarListener, (int) R.layout.actionbar_shared);
    }

    public void loadActionBarModal(int i, CustomActionBarListener customActionBarListener) {
        a(i, customActionBarListener, (int) R.layout.actionbar_back);
    }

    private void a(final int i, final CustomActionBarListener customActionBarListener, final int i2) {
        new Handler().post(new Runnable() {
            public void run() {
                SantanderRioMainActivity.this.d(i2);
                customActionBarListener.actionBarLoaded(i, SantanderRioMainActivity.this.mActionBar.getCustomView());
            }
        });
    }

    /* access modifiers changed from: private */
    public void d(int i) {
        ActionBar actionBar = this.mActionBar;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setCustomView(((LayoutInflater) getSystemService("layout_inflater")).inflate(i, null), new LayoutParams(-1, -1));
        invalidateOptionsMenu();
    }

    private void b(boolean z2) {
        if (z2) {
            getWindow().setSoftInputMode(16);
        } else {
            getWindow().setSoftInputMode(48);
        }
    }

    public void onPrivateMenuOptionSelectedInNestedActivity(int i) {
        if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.HOME || ((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.NAME || ((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.PRIVATE_ACCESS || ((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.DISCONNECT) {
            if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.DISCONNECT) {
                if (this.sessionManager == null || !this.sessionManager.getFlagMustShowPopUp()) {
                    super.onBackPressed();
                } else {
                    ((MenuOption) this.listMenuOptions.get(i)).setSelected(false);
                    ((MenuOption) this.listMenuOptions.get(i)).setPressed(false);
                    this.v.notifyDataSetChanged();
                    deslogado(false);
                    this.q.logOutSession();
                    this.sessionManager.setFlagMustShowPopUp(false);
                }
            }
            if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.HOME) {
                if (this.sessionManager == null || !this.sessionManager.getFlagMustShowPopUp()) {
                    super.onBackPressed();
                    return;
                }
                ((MenuOption) this.listMenuOptions.get(i)).setSelected(false);
                ((MenuOption) this.listMenuOptions.get(i)).setPressed(false);
                this.v.notifyDataSetChanged();
                deslogado(true);
                this.q.logOutSession();
                this.sessionManager.setFlagMustShowPopUp(false);
            } else if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.PRIVATE_ACCESS) {
                ((MenuOption) this.listMenuOptions.get(i)).setSelected(false);
                ((MenuOption) this.listMenuOptions.get(i)).setPressed(false);
                this.v.notifyDataSetChanged();
                deslogado(((MenuOption) this.listMenuOptions.get(i)).getAction());
            }
        } else {
            goToOption(((MenuOption) this.listMenuOptions.get(i)).getAction());
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    @Subscribe
    public void showReceipt(ReceiptEvent receiptEvent) {
        this.D = receiptEvent;
    }

    @Subscribe
    public void onGetListaTjWomen(GetListaTjWomenEvent getListaTjWomenEvent) {
        dismissProgress();
        final GetListaTjWomenEvent getListaTjWomenEvent2 = getListaTjWomenEvent;
        AnonymousClass14 r0 = new BaseWSResponseHandler(this, TypeOption.INITIAL_VIEW, "Consulta de getListaTjWomen", this) {
            /* access modifiers changed from: protected */
            public void onOk() {
                GetListaTjWomenBodyResponseBean listaTjWomenBodyResponseBean = getListaTjWomenEvent2.getResponse().getListaTjWomenBodyResponseBean();
                SantanderRioMainActivity.this.E = listaTjWomenBodyResponseBean;
                if (listaTjWomenBodyResponseBean.getListaUsuarios() != null) {
                    HashMap filterUsuariosConTarjetasMarcaWomen = ProgramaWomenUtils.filterUsuariosConTarjetasMarcaWomen(listaTjWomenBodyResponseBean.getListaUsuarios());
                    SantanderRioMainActivity.this.changeFragment(ProgramaWomenFragment.newInstance(listaTjWomenBodyResponseBean.getListaUsuarios(), ProgramaWomenUtils.filterUsersMarcaWomen(listaTjWomenBodyResponseBean.getListaUsuarios()), filterUsuariosConTarjetasMarcaWomen, SantanderRioMainActivity.this.G, listaTjWomenBodyResponseBean));
                }
            }

            /* access modifiers changed from: protected */
            public void onRes4Error() {
                EmptyFragment newInstance = EmptyFragment.newInstance("Women", Html.fromHtml(getListaTjWomenEvent2.getErrorBodyBean().resDesc).toString(), R.drawable.error_continuacion);
                FragmentTransaction beginTransaction = SantanderRioMainActivity.this.getSupportFragmentManager().beginTransaction();
                beginTransaction.add((int) R.id.content_frame, (Fragment) newInstance);
                beginTransaction.commit();
            }
        };
        r0.handleWSResponse(getListaTjWomenEvent);
    }

    public void irASuscripcionProgramaWomenFragment(String str) {
        Intent intent = new Intent(this, ProgramaWomenActivity.class);
        intent.putExtra("getListaTjWomenBodyResponseBean", this.E);
        intent.putExtra("accion", str);
        startActivityForResult(intent, 101);
    }

    public void configActionBarDefault() {
        setActionBarType(ActionBarType.BACK_ONLY);
        getSupportActionBar().getCustomView().findViewById(R.id.back_imgButton).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SantanderRioMainActivity.this.backLastFragment();
            }
        });
    }

    public void onClickItem(int i) {
        this.F = i;
        if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() != OptionType.DISCONNECT) {
            this.y.setSelected(false);
            ((MenuOption) this.listMenuOptions.get(i)).setSelected(true);
            this.v.notifyDataSetChanged();
            closeDrawer();
            return;
        }
        this.y.setSelected(false);
        ((MenuOption) this.listMenuOptions.get(i)).setSelected(true);
        this.v.notifyDataSetChanged();
        c(this.F);
        this.F = -1;
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }

    public IDataManager getDataManager() {
        return this.q;
    }

    public CheckVersionManager getCheckVersionManager() {
        return this.C;
    }

    public void showProgressBar(String str) {
        showProgressIndicator("");
    }

    public void dismissProgressBar() {
        dismissProgress();
    }

    public void getTarjetas(List list, List list2, List list3, String str, String str2, String str3) {
        dismissProgressBar();
        detachView();
        Intent intent = new Intent(this, RecargaSubeActivity.class);
        intent.putExtra(getString(R.string.RECARGA_SUBE_FLOW), getString(R.string.RECARGA_SUBE));
        if (this.B.equalsIgnoreCase(FragmentConstants.SUBE_FLOW_AGREGAR) || this.B.equalsIgnoreCase(FragmentConstants.AGREGAR_SUBE)) {
            intent.putExtra(FragmentConstants.INTENT_DATA_SUBE_BACK_TRANSITION, true);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.LISTA_CUENTAS), (ArrayList) list);
        bundle.putParcelableArrayList(getString(R.string.LISTA_RECARGAS), (ArrayList) list2);
        bundle.putParcelableArrayList(getString(R.string.LISTA_VALORES), (ArrayList) list3);
        bundle.putString("SUBE_TITULO", str2);
        bundle.putString("SUBE_DESCRIPCION", str3);
        bundle.putString(getString(R.string.SESSION_USER), str);
        intent.putExtras(bundle);
        intent.addFlags(603979776);
        startActivityForResult(intent, 102);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToRegistrarSube(String str, String str2) {
        dismissProgressBar();
        detachView();
        Intent intent = new Intent(this, RecargaSubeActivity.class);
        intent.putExtra(getString(R.string.RECARGA_SUBE_FLOW), getString(R.string.REGISTRAR_SUBE));
        intent.putExtra("SUBE_TITULO", str);
        intent.putExtra("SUBE_DESCRIPCION", str2);
        intent.addFlags(603979776);
        startActivityForResult(intent, 102);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToAgregarTarjetaSube() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    SantanderRioMainActivity.this.dismissProgressBar();
                } catch (Exception unused) {
                }
            }
        }, 1000);
        this.B = "";
        this.sessionManager.setCustomURLNavFragment(FragmentConstants.PAGO_SERVICIOS);
        this.L = new Bundle();
        this.L.putString(FragmentConstants.PAGO_SERVICIO_EMPRESA, "SUBE");
        i();
    }
}
