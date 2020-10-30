package ar.com.santander.rio.mbanking.app.base;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Model.MenuOption;
import ar.com.santander.rio.mbanking.app.ui.Model.MenuOption.OptionType;
import ar.com.santander.rio.mbanking.app.ui.activities.ErrorActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.HomeActivity;
import ar.com.santander.rio.mbanking.app.ui.activities.SantanderRioMainActivity;
import ar.com.santander.rio.mbanking.app.ui.adapters.MenuAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.MenuAdapter.MenuActionsListener;
import ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WSErrorHandlerConstants;
import ar.com.santander.rio.mbanking.app.ui.fragments.FragmentConstants;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.OnBoardingTextStylingSet;
import ar.com.santander.rio.mbanking.components.ProgresIndicator;
import ar.com.santander.rio.mbanking.components.itrsa.OneClickListener;
import ar.com.santander.rio.mbanking.inject.GraphProvider;
import ar.com.santander.rio.mbanking.inject.GraphRetriever;
import ar.com.santander.rio.mbanking.inject.Modules;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.model.general.TipoCliente;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.otto.Bus;
import dagger.ObjectGraph;
import java.util.ArrayList;
import java.util.Iterator;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

@SuppressLint({"Registered"})
public class BaseActivity extends AppCompatActivity implements GraphProvider {
    public static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    /* access modifiers changed from: protected */
    public ArrayList<MenuOption> listMenuOptions = new ArrayList<>();
    protected OneClickListener mOneClickListener;
    @Inject
    Bus n;
    @Inject
    SessionManager o;
    private View p;
    public ProgresIndicator progressIndicator;
    private ObjectGraph q;
    /* access modifiers changed from: private */
    public WebServiceErrorListener r;
    private ActionBarType s = ActionBarType.MENU;

    public enum ActionBarType {
        MENU,
        MAIN_WITH_HELP,
        BACK,
        BACK_ONLY,
        BACK_WITH_ADD,
        BACK_WITH_HELP,
        BACK_WITH_MENU,
        MAIN_WITH_MENU,
        CANCEL_WITH_CONFIRMAR,
        TICK,
        ONLY_LOGO,
        CANCELAR,
        CANCELAR_CONFIRMAR,
        CONFIRMAR,
        SHARE,
        BACK_CANCEL,
        MAIN_WITH_CONFIG,
        CAM_CLOSE,
        PUSH_CLOSE,
        WHITE_BACK_CLOSE,
        WHITE_SUBE,
        WHITE_SUBE_ONLY,
        WHITE
    }

    public interface WebServiceErrorListener {
        void onBackWindow(String str);

        void onGotoBack();

        void onGotoCuentas();

        void onTransactionalTimeOut();

        void openMenuOnError(String str);
    }

    public void clearScreenData() {
    }

    public void showOnBoarding(int i, int i2, int i3, String str) {
    }

    public void showOnBoarding(int i, int i2, int i3, String str, OnBoardingTextStylingSet onBoardingTextStylingSet) {
    }

    public View getActionBarView() {
        return this.p;
    }

    public void setmOneClickListener(OneClickListener oneClickListener) {
        this.mOneClickListener = oneClickListener;
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        b();
        GraphRetriever.from(this).inject(this);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    private void b() {
        setRequestedOrientation(1);
    }

    public Bus getEventBus() {
        return this.n;
    }

    public void changeActionBarTitle(String str) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle((CharSequence) str);
        }
    }

    public void changeActionBarTitle(int i) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(i);
        }
    }

    public ObjectGraph getGraph() {
        if (this.q == null) {
            this.q = GraphRetriever.from(getApplication()).plus(Modules.listModulesForActivity(this));
        }
        return this.q;
    }

    public void checkError(WebServiceEvent webServiceEvent, String str) {
        TypeResult result = webServiceEvent.getResult();
        TypeOption typeOption = webServiceEvent.getTypeOption();
        String titleToShow = webServiceEvent.getTitleToShow();
        if (titleToShow == null || "".equalsIgnoreCase(titleToShow)) {
            if (result != TypeResult.BEAN_WARNING) {
                titleToShow = getString(R.string.IDX_ALERT_LBL_TITLE_ERROR);
            } else {
                titleToShow = getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE);
            }
        }
        String str2 = titleToShow;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        IsbanDialogFragment isbanDialogFragment = null;
        switch (typeOption) {
            case INITIAL_VIEW:
                switch (result) {
                    case SERVER_ERROR:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(getResources().getString(R.string.MSG_USER000002_General_errorNoconexion)).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        break;
                    case UNKNOWN_ERROR:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(getResources().getString(R.string.MSG_USER000029_General_errorGenerico)).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_1:
                    case BEAN_ERROR_RES_2:
                    case BEAN_ERROR_RES_3:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_5:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                BaseActivity.this.gotoHome();
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_6:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_7:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                if (BaseActivity.this.getWsErrorListener() != null) {
                                    BaseActivity.this.r.onGotoCuentas();
                                }
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_9:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        break;
                    case BEAN_WARNING:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                }
            case INTERMDIATE_VIEW:
                switch (result) {
                    case SERVER_ERROR:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(getResources().getString(R.string.MSG_USER000002_General_errorNoconexion)).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                    case UNKNOWN_ERROR:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(getResources().getString(R.string.MSG_USER000029_General_errorGenerico)).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                    case BEAN_ERROR_RES_1:
                    case BEAN_ERROR_RES_2:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(Html.fromHtml(str2).toString(), Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                    case BEAN_ERROR_RES_3:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(Html.fromHtml(str2).toString(), Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                    case BEAN_ERROR_RES_5:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                BaseActivity.this.gotoHome();
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_6:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_7:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                if (BaseActivity.this.getWsErrorListener() != null) {
                                    BaseActivity.this.r.onGotoCuentas();
                                }
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_9:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                if (BaseActivity.this.getWsErrorListener() != null) {
                                    BaseActivity.this.r.onGotoBack();
                                }
                            }
                        });
                        break;
                    case BEAN_WARNING:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                }
            case TRANSACTIONAL_FINAL_VIEW:
                switch (result) {
                    case SERVER_ERROR:
                    case UNKNOWN_ERROR:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), Utils.formatIsbanHTMLCode(Html.fromHtml(getResources().getString(R.string.MSG_USER000055)).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                if (BaseActivity.this.getWsErrorListener() != null) {
                                    BaseActivity.this.r.onTransactionalTimeOut();
                                }
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_1:
                    case BEAN_ERROR_RES_2:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                    case BEAN_ERROR_RES_3:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                    case BEAN_ERROR_RES_5:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                BaseActivity.this.gotoHome();
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_6:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_7:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                if (BaseActivity.this.getWsErrorListener() != null) {
                                    BaseActivity.this.r.onGotoCuentas();
                                }
                            }
                        });
                        break;
                    case BEAN_ERROR_RES_9:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        isbanDialogFragment.setDialogListener(new IDialogListener() {
                            public void onItemSelected(String str) {
                            }

                            public void onNegativeButton() {
                            }

                            public void onPositiveButton() {
                            }

                            public void onSimpleActionButton() {
                                if (BaseActivity.this.getWsErrorListener() != null) {
                                    BaseActivity.this.r.onGotoBack();
                                }
                            }
                        });
                        break;
                    case BEAN_WARNING:
                        isbanDialogFragment = IsbanDialogFragment.newInstance(str2, Utils.formatIsbanHTMLCode(Html.fromHtml(webServiceEvent.getMessageToShow()).toString()), null, null, getString(R.string.ID1_ALERT_BTN_ACCEPT), null, null);
                        break;
                }
        }
        if (isbanDialogFragment != null) {
            isbanDialogFragment.show(supportFragmentManager, "Dialog");
        }
    }

    public void onResume() {
        super.onResume();
        hideKeyboard();
        BaseApplication.activityResumed();
        c();
    }

    private void c() {
        if (BaseApplication.getPendingEvents() != null) {
            Iterator it = BaseApplication.getPendingEvents().iterator();
            while (it.hasNext()) {
                this.n.post((WebServiceEvent) it.next());
            }
            BaseApplication.setPendingEvents(null);
        }
    }

    public void onPause() {
        super.onPause();
        BaseApplication.activityPaused();
    }

    public void customErrorDialog(String str, String str2) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(str, str2, null, PagoTarjetasConstants.ISBAN_DIALOG_ACCEPT_BUTTON_TEXT, null, null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }
        });
        newInstance.show(getSupportFragmentManager(), "DialogNewVersion");
    }

    public MenuAdapter createMenuPrivadoAdapter(int i, String str, String str2, String str3, MenuActionsListener menuActionsListener) {
        String[] stringArray = getResources().getStringArray(R.array.menu_privado_options);
        String[] stringArray2 = getResources().getStringArray(R.array.menu_privado_actions);
        this.listMenuOptions = new ArrayList<>();
        MenuOption menuOption = new MenuOption(OptionType.NAME, false, str, getString(R.string.ID11_PRIVATEMENU_LBL_LASTDEPOSIT), str2, "");
        this.listMenuOptions.add(menuOption);
        MenuOption menuOption2 = new MenuOption(OptionType.DISCONNECT, false, "", "", null, "");
        this.listMenuOptions.add(menuOption2);
        for (int i2 = 0; i2 < stringArray.length; i2++) {
            MenuOption menuOption3 = new MenuOption(OptionType.OPTION, false, stringArray[i2], "", null, stringArray2[i2]);
            if ((!stringArray[i2].equals(FragmentConstants.CREDITOS_MENU) || this.o.m3usuarioTieneCrditos() || this.o.usuarioTieneCuentas()) && ((!stringArray[i2].equals(getString(R.string.IDXX_PRIVATEMENU_BTN_NERS)) || this.o.usuarioTieneTarjetas() || this.o.m3usuarioTieneCrditos() || this.o.usuarioTieneCuentas()) && ((this.o.usuarioTieneCuentasOperativasEnPesos() || this.o.usuarioTieneTarjetasCreditoHabilitadas() || !stringArray2[i2].equals(FragmentConstants.SEGUROS)) && ((this.o.usuarioTieneCuentasOperativasEnPesos() || this.o.usuarioTieneTarjetasCreditoHabilitadas() || !stringArray2[i2].equals(FragmentConstants.SEGUROS)) && (!stringArray[i2].toUpperCase().equals(FragmentConstants.CREDITOS.toUpperCase()) || this.o.m3usuarioTieneCrditos()))))) {
                if (!this.o.usuarioTieneCuentas()) {
                    if (!stringArray2[i2].equals(FragmentConstants.CUENTAS)) {
                        if (!stringArray2[i2].equals(FragmentConstants.CUENTAS_PRIVADA)) {
                            if (!stringArray2[i2].equals(FragmentConstants.PAGO_SERVICIOS)) {
                                if (!stringArray2[i2].equals(FragmentConstants.RECARGA_CELULARES)) {
                                    if (!stringArray2[i2].equals(FragmentConstants.TRANSFERENCIAS)) {
                                        if (!stringArray2[i2].equals(FragmentConstants.PLAZOS_FIJOS)) {
                                            if (!stringArray2[i2].equals(FragmentConstants.ENVIO_EFECTIVO)) {
                                                if (!stringArray2[i2].equals(FragmentConstants.EXTRACCION_SIN_TARJETA)) {
                                                    if (!stringArray2[i2].equals(FragmentConstants.BLANQUEO_DE_CLAVE_BANELCO)) {
                                                        if (!stringArray2[i2].equals(FragmentConstants.COMPRAVENTA_DOLARES)) {
                                                            if (!stringArray2[i2].equals(FragmentConstants.PAGO_TARJETAS)) {
                                                                if (!stringArray2[i2].equals(FragmentConstants.FONDOS_INVERSION)) {
                                                                    if (!stringArray2[i2].equals(FragmentConstants.DEBIN)) {
                                                                        if (stringArray2[i2].equals(FragmentConstants.TENENCIA_INVERSIONES)) {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (!this.o.usuarioTieneCuentasOperativas() && stringArray2[i2].equals(FragmentConstants.DEBIN)) {
                }
                if ((this.o.usuarioTieneTarjetas() || (!stringArray2[i2].equals(FragmentConstants.TARJETAS) && !stringArray2[i2].equals(FragmentConstants.PAGO_TARJETAS) && !stringArray2[i2].equals(FragmentConstants.MARCACION_POR_VIAJE) && !stringArray2[i2].equals(FragmentConstants.PROGRAMA_WOMEN))) && (this.o.usuarioTieneTarjetasNoMastercard() || !stringArray2[i2].equals(FragmentConstants.TARJETAS))) {
                    if (stringArray2[i2].equals(FragmentConstants.NOTIFICACIONES)) {
                        if (i > 0) {
                            menuOption3.setValue(String.valueOf(i));
                            this.listMenuOptions.add(menuOption3);
                        }
                    } else if (stringArray2[i2].equals(FragmentConstants.SEGMENTO)) {
                        if (str3.equalsIgnoreCase(LoginConstants.TIPO_SEGMENTO_ADVANCE)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(getString(R.string.ID12_PRIVATEMENU_BTN_SEGMENT));
                            sb.append(UtilsCuentas.SEPARAOR2);
                            sb.append(LoginConstants.TIPO_SEGMENTO_ADVANCE_MENU_TEXT);
                            menuOption3.setTitle(UtilString.capitalize(sb.toString()));
                            this.listMenuOptions.add(menuOption3);
                        } else if (str3.equalsIgnoreCase(LoginConstants.TIPO_SEGMENTO_SELECT)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(getString(R.string.ID12_PRIVATEMENU_BTN_SEGMENT));
                            sb2.append(UtilsCuentas.SEPARAOR2);
                            sb2.append(LoginConstants.TIPO_SEGMENTO_SELECT);
                            menuOption3.setTitle(UtilString.capitalize(sb2.toString()));
                            this.listMenuOptions.add(menuOption3);
                        } else if (str3.equalsIgnoreCase(LoginConstants.TIPO_SEGMENTO_UNIVERSIDADES)) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(getString(R.string.ID12_PRIVATEMENU_BTN_SEGMENT));
                            sb3.append(UtilsCuentas.SEPARAOR2);
                            sb3.append(LoginConstants.TIPO_SEGMENTO_UNIVERSIDADES);
                            menuOption3.setTitle(UtilString.capitalize(sb3.toString()));
                            this.listMenuOptions.add(menuOption3);
                        }
                    } else if (stringArray2[i2].equals(FragmentConstants.HOME)) {
                        menuOption3.setOptionType(OptionType.HOME);
                        this.listMenuOptions.add(menuOption3);
                    } else if (stringArray2[i2].equals(FragmentConstants.CAJEROS) || stringArray2[i2].equals(FragmentConstants.SUCURSALES) || stringArray2[i2].equals(FragmentConstants.PROMOS) || stringArray2[i2].equals(FragmentConstants.NUMEROS_UTILES) || stringArray2[i2].equals(FragmentConstants.SOFT_TOKEN)) {
                        menuOption3.setOptionType(OptionType.PRIVATE_ACCESS);
                        this.listMenuOptions.add(menuOption3);
                    } else {
                        this.listMenuOptions.add(menuOption3);
                    }
                    if (!this.o.usuarioTieneTarjetas() && stringArray2[i2].equals(FragmentConstants.PROGRAMA_WOMEN)) {
                        Iterator it = this.listMenuOptions.iterator();
                        while (it.hasNext()) {
                            MenuOption menuOption4 = (MenuOption) it.next();
                            if (menuOption4.getAction().equals(FragmentConstants.PROGRAMA_WOMEN)) {
                                this.listMenuOptions.remove(menuOption4);
                            }
                        }
                    }
                    if (!this.o.usuarioTieneTarjetasDebito() && stringArray2[i2].equals(FragmentConstants.BLANQUEO_DE_CLAVE_BANELCO)) {
                        Iterator it2 = this.listMenuOptions.iterator();
                        while (it2.hasNext()) {
                            MenuOption menuOption5 = (MenuOption) it2.next();
                            if (menuOption5.getAction().equals(FragmentConstants.BLANQUEO_DE_CLAVE_BANELCO)) {
                                this.listMenuOptions.remove(menuOption5);
                            }
                        }
                    }
                    if (this.o.getLoginUnico().habilitarWatson != null && this.o.getLoginUnico().habilitarWatson.equalsIgnoreCase("N") && stringArray2[i2].equals(FragmentConstants.NECESITAS_AYUDA)) {
                        Iterator it3 = this.listMenuOptions.iterator();
                        while (it3.hasNext()) {
                            MenuOption menuOption6 = (MenuOption) it3.next();
                            if (menuOption6.getAction().equals(FragmentConstants.NECESITAS_AYUDA)) {
                                this.listMenuOptions.remove(menuOption6);
                            }
                        }
                    }
                    if (stringArray[i2].equalsIgnoreCase(FragmentConstants.CUENTAS) && this.o.getLoginUnico() != null && this.o.getLoginUnico().getDatosPersonales() != null && this.o.getLoginUnico().getDatosPersonales().getTipoCliente().equalsIgnoreCase(TipoCliente.BP.getTipoCliente())) {
                        Iterator it4 = this.listMenuOptions.iterator();
                        while (it4.hasNext()) {
                            MenuOption menuOption7 = (MenuOption) it4.next();
                            if (menuOption7.getAction().equals(FragmentConstants.CUENTAS)) {
                                this.listMenuOptions.remove(menuOption7);
                            }
                        }
                    }
                    if (stringArray2[i2].equalsIgnoreCase(FragmentConstants.CUENTAS_PRIVADA) && this.o.getLoginUnico() != null && this.o.getLoginUnico().getDatosPersonales() != null && this.o.getLoginUnico().getDatosPersonales().getTipoCliente().equalsIgnoreCase(TipoCliente.RTL.getTipoCliente())) {
                        Iterator it5 = this.listMenuOptions.iterator();
                        while (it5.hasNext()) {
                            MenuOption menuOption8 = (MenuOption) it5.next();
                            if (menuOption8.getAction().equals(FragmentConstants.CUENTAS_PRIVADA)) {
                                this.listMenuOptions.remove(menuOption8);
                            }
                        }
                    }
                    if (stringArray2[i2].equalsIgnoreCase(FragmentConstants.TENENCIA_INVERSIONES) && this.o.getLoginUnico() != null && this.o.getLoginUnico().getDatosPersonales() != null && this.o.getLoginUnico().getDatosPersonales().getTipoCliente().equalsIgnoreCase(TipoCliente.RTL.getTipoCliente())) {
                        Iterator it6 = this.listMenuOptions.iterator();
                        while (it6.hasNext()) {
                            MenuOption menuOption9 = (MenuOption) it6.next();
                            if (menuOption9.getAction().equals(FragmentConstants.TENENCIA_INVERSIONES)) {
                                this.listMenuOptions.remove(menuOption9);
                            }
                        }
                    }
                }
            }
        }
        return new MenuAdapter(this, R.layout.menu_item, this.listMenuOptions, menuActionsListener);
    }

    public void gotoHome(boolean z) {
        Intent intent = new Intent(this, SantanderRioMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("MENU_ACTION", FragmentConstants.MOBILE_BANKING);
        bundle.putBoolean(WSErrorHandlerConstants.INTENT_EXTRA_BACK_ANIMATION, z);
        intent.putExtras(bundle);
        setResult(0);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void gotoHome() {
        gotoHome(true);
    }

    public void gotoHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(67108864);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
            }
        }
    }

    public void showKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
            }
        }
    }

    public ActionBarType getActionBarType() {
        return this.s;
    }

    public void setActionBarType(ActionBarType actionBarType) {
        try {
            setActionBarType(actionBarType, (this.o.getLoginUnico() == null || this.o.getLoginUnico().getDatosPersonales() == null) ? null : this.o.getLoginUnico().getDatosPersonales().getSegmento());
        } catch (Exception e) {
            e.fillInStackTrace();
            setActionBarType(actionBarType, null);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x013f  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0167  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0172  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setActionBarType(ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType r6, java.lang.String r7) {
        /*
            r5 = this;
            android.support.v7.app.ActionBar r0 = r5.getSupportActionBar()
            if (r0 == 0) goto L_0x000b
            r1 = 16
            r0.setDisplayOptions(r1)
        L_0x000b:
            java.lang.String r1 = "layout_inflater"
            java.lang.Object r1 = r5.getSystemService(r1)
            android.view.LayoutInflater r1 = (android.view.LayoutInflater) r1
            r2 = 0
            if (r1 == 0) goto L_0x0128
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.MENU
            if (r6 != r3) goto L_0x0023
            r3 = 2131492893(0x7f0c001d, float:1.860925E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0023:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK
            if (r6 != r3) goto L_0x0030
            r3 = 2131492897(0x7f0c0021, float:1.8609259E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0030:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK_ONLY
            if (r6 != r3) goto L_0x003d
            r3 = 2131492896(0x7f0c0020, float:1.8609257E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x003d:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK_WITH_ADD
            if (r6 != r3) goto L_0x004a
            r3 = 2131492899(0x7f0c0023, float:1.8609263E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x004a:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK_WITH_HELP
            if (r6 != r3) goto L_0x0057
            r3 = 2131492900(0x7f0c0024, float:1.8609265E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0057:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK_WITH_MENU
            if (r6 != r3) goto L_0x0064
            r3 = 2131492901(0x7f0c0025, float:1.8609267E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0064:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.MAIN_WITH_MENU
            if (r6 != r3) goto L_0x0071
            r3 = 2131492913(0x7f0c0031, float:1.8609291E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0071:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.CANCEL_WITH_CONFIRMAR
            r4 = 2131492903(0x7f0c0027, float:1.8609271E38)
            if (r6 != r3) goto L_0x007e
            android.view.View r1 = r1.inflate(r4, r2)
            goto L_0x0129
        L_0x007e:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.ONLY_LOGO
            if (r6 != r3) goto L_0x008b
            r3 = 2131492916(0x7f0c0034, float:1.8609297E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x008b:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.SHARE
            if (r6 != r3) goto L_0x0098
            r3 = 2131492915(0x7f0c0033, float:1.8609295E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0098:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.BACK_CANCEL
            if (r6 != r3) goto L_0x00a5
            r3 = 2131492894(0x7f0c001e, float:1.8609253E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x00a5:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.MAIN_WITH_HELP
            if (r6 != r3) goto L_0x00b2
            r3 = 2131492912(0x7f0c0030, float:1.860929E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x00b2:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.CANCELAR
            if (r6 != r3) goto L_0x00bf
            r3 = 2131492902(0x7f0c0026, float:1.860927E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x00bf:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.CANCELAR_CONFIRMAR
            if (r6 != r3) goto L_0x00c8
            android.view.View r1 = r1.inflate(r4, r2)
            goto L_0x0129
        L_0x00c8:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.CONFIRMAR
            if (r6 != r3) goto L_0x00d4
            r3 = 2131492904(0x7f0c0028, float:1.8609273E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x00d4:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.MAIN_WITH_CONFIG
            if (r6 != r3) goto L_0x00e0
            r3 = 2131492911(0x7f0c002f, float:1.8609287E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x00e0:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.PUSH_CLOSE
            if (r6 != r3) goto L_0x00ec
            r3 = 2131492895(0x7f0c001f, float:1.8609255E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x00ec:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.CAM_CLOSE
            if (r6 != r3) goto L_0x00f8
            r3 = 2131492891(0x7f0c001b, float:1.8609247E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x00f8:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.WHITE_SUBE
            if (r6 != r3) goto L_0x0104
            r3 = 2131492922(0x7f0c003a, float:1.860931E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0104:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.WHITE_BACK_CLOSE
            if (r6 != r3) goto L_0x0110
            r3 = 2131492917(0x7f0c0035, float:1.86093E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0110:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.WHITE_SUBE_ONLY
            if (r6 != r3) goto L_0x011c
            r3 = 2131492921(0x7f0c0039, float:1.8609308E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x011c:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.WHITE
            if (r6 != r3) goto L_0x0128
            r3 = 2131492920(0x7f0c0038, float:1.8609306E38)
            android.view.View r1 = r1.inflate(r3, r2)
            goto L_0x0129
        L_0x0128:
            r1 = r2
        L_0x0129:
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.CAM_CLOSE
            if (r6 == r3) goto L_0x016a
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.WHITE_SUBE
            if (r6 == r3) goto L_0x016a
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.WHITE_BACK_CLOSE
            if (r6 == r3) goto L_0x016a
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.WHITE_SUBE_ONLY
            if (r6 == r3) goto L_0x016a
            ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType r3 = ar.com.santander.rio.mbanking.app.base.BaseActivity.ActionBarType.WHITE
            if (r6 == r3) goto L_0x016a
            if (r7 == 0) goto L_0x0167
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants.TIPO_SEGMENTO_ADVANCE
            boolean r3 = r7.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x014b
            r5.b(r1)
            goto L_0x016a
        L_0x014b:
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants.TIPO_SEGMENTO_SELECT
            boolean r3 = r7.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0157
            r5.c(r1)
            goto L_0x016a
        L_0x0157:
            java.lang.String r3 = ar.com.santander.rio.mbanking.app.ui.constants.LoginConstants.TIPO_SEGMENTO_UNIVERSIDADES
            boolean r7 = r7.equalsIgnoreCase(r3)
            if (r7 == 0) goto L_0x0163
            r5.d(r1)
            goto L_0x016a
        L_0x0163:
            r5.a(r1)
            goto L_0x016a
        L_0x0167:
            r5.a(r1)
        L_0x016a:
            android.support.v7.app.ActionBar$LayoutParams r7 = new android.support.v7.app.ActionBar$LayoutParams
            r3 = -1
            r7.<init>(r3, r3)
            if (r0 == 0) goto L_0x018f
            r0.setCustomView(r1, r7)
            if (r1 == 0) goto L_0x017e
            android.view.ViewParent r7 = r1.getParent()
            r2 = r7
            android.support.v7.widget.Toolbar r2 = (android.support.v7.widget.Toolbar) r2
        L_0x017e:
            if (r2 == 0) goto L_0x0187
            r7 = 0
            r2.setPadding(r7, r7, r7, r7)
            r2.setContentInsetsAbsolute(r7, r7)
        L_0x0187:
            r7 = 0
            r0.setElevation(r7)
            r5.s = r6
            r5.p = r1
        L_0x018f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.base.BaseActivity.setActionBarType(ar.com.santander.rio.mbanking.app.base.BaseActivity$ActionBarType, java.lang.String):void");
    }

    public WebServiceErrorListener getWsErrorListener() {
        return this.r;
    }

    public void setWsErrorListener(WebServiceErrorListener webServiceErrorListener) {
        this.r = webServiceErrorListener;
    }

    public void showMapsDialog() {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getString(R.string.MESSAGE_UPDATE_GOOGLE_MAPS), null, null, getString(R.string.MESSAGE_UPDATE_GOOGLE_MAPS_ACTUALIZAR), getString(R.string.MESSAGE_UPDATE_GOOGLE_MAPS_NO_ACTUALIZAR), null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                String string = BaseActivity.this.getString(R.string.MESSAGE_UPDATE_GOOGLE_MAPS_URL);
                try {
                    BaseActivity baseActivity = BaseActivity.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("market://details?id=");
                    sb.append(string);
                    baseActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                } catch (ActivityNotFoundException e) {
                    e.fillInStackTrace();
                    BaseActivity baseActivity2 = BaseActivity.this;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("https://play.google.com/store/apps/details?id=");
                    sb2.append(string);
                    baseActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb2.toString())));
                }
            }
        });
        newInstance.show(getSupportFragmentManager(), "Dialog");
    }

    public boolean checkPlayServices() {
        int isGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        if (GoogleApiAvailability.getInstance().isUserResolvableError(isGooglePlayServicesAvailable)) {
            b(isGooglePlayServicesAvailable);
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void b(int i) {
        GoogleApiAvailability.getInstance().getErrorDialog(this, i, 1001).show();
    }

    public void showProgress(String str) {
        if (this.progressIndicator == null || !this.progressIndicator.isVisible()) {
            this.progressIndicator = ProgresIndicator.newInstance(str);
            this.progressIndicator.show(getSupportFragmentManager(), str);
        }
    }

    public void dismissProgress() {
        if (this.progressIndicator != null) {
            try {
                this.progressIndicator.dismiss();
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
    }

    public Boolean isProgressActive() {
        return this.progressIndicator != null ? Boolean.valueOf(this.progressIndicator.isVisible()) : Boolean.valueOf(false);
    }

    /* access modifiers changed from: 0000 */
    public void a(View view) {
        View findViewById = view.findViewById(R.id.inl_logo_default);
        if (findViewById != null) {
            findViewById.findViewById(R.id.logo).setVisibility(0);
            findViewById.findViewById(R.id.logo_advance).setVisibility(8);
            findViewById.findViewById(R.id.logo_select).setVisibility(8);
            findViewById.findViewById(R.id.logo_universidades).setVisibility(8);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(View view) {
        View findViewById = view.findViewById(R.id.inl_logo_default);
        findViewById.findViewById(R.id.logo).setVisibility(8);
        findViewById.findViewById(R.id.logo_advance).setVisibility(0);
        findViewById.findViewById(R.id.logo_universidades).setVisibility(8);
        findViewById.findViewById(R.id.logo_select).setVisibility(8);
    }

    /* access modifiers changed from: 0000 */
    public void c(View view) {
        View findViewById = view.findViewById(R.id.inl_logo_default);
        findViewById.findViewById(R.id.logo).setVisibility(8);
        findViewById.findViewById(R.id.logo_advance).setVisibility(8);
        findViewById.findViewById(R.id.logo_universidades).setVisibility(8);
        findViewById.findViewById(R.id.logo_select).setVisibility(0);
    }

    /* access modifiers changed from: 0000 */
    public void d(View view) {
        View findViewById = view.findViewById(R.id.inl_logo_default);
        findViewById.findViewById(R.id.logo).setVisibility(8);
        findViewById.findViewById(R.id.logo_advance).setVisibility(8);
        findViewById.findViewById(R.id.logo_select).setVisibility(8);
        findViewById.findViewById(R.id.logo_universidades).setVisibility(0);
    }

    public void handleWSError(Intent intent) {
        if (!intent.hasExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION)) {
            setResult(-1, intent);
            onBackPressed();
        } else if (intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION).equalsIgnoreCase(WSErrorHandlerConstants.GO_TO_NEXT_ERROR_SCREEN)) {
            Intent intent2 = new Intent(getBaseContext(), ErrorActivity.class);
            intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_TITLE));
            intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE, intent.getStringExtra(WSErrorHandlerConstants.INTENT_EXTRA_MESSAGE));
            intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE, intent.getSerializableExtra(WSErrorHandlerConstants.INTENT_EXTRA_MENU_TYPE));
            intent2.putExtra(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, intent.getIntExtra(WSErrorHandlerConstants.INTENT_EXTRA_ERROR_IMG, 0));
            startActivityForResult(intent2, WSErrorHandlerConstants.INTENT_RES4_REQUEST_CODE);
        } else if (intent.getStringExtra(WSErrorHandlerConstants.WS_ERROR_DO_ACTION).equalsIgnoreCase(WSErrorHandlerConstants.GO_TO_LOGIN)) {
            setResult(-1, intent);
            gotoHome();
        } else {
            setResult(-1, intent);
            onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x001d A[Catch:{ Exception -> 0x0017 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean activityResultHandler(int r3, android.content.Intent r4) {
        /*
            r2 = this;
            r0 = -1
            r1 = 1
            if (r3 != r0) goto L_0x0019
            if (r4 == 0) goto L_0x0019
            java.lang.String r3 = "PrivateMenuSelectedOptionPosition"
            boolean r3 = r4.hasExtra(r3)     // Catch:{ Exception -> 0x0017 }
            if (r3 != 0) goto L_0x001b
            java.lang.String r3 = "WS_ERROR_DO_ACTION"
            boolean r3 = r4.hasExtra(r3)     // Catch:{ Exception -> 0x0017 }
            if (r3 != 0) goto L_0x001b
            goto L_0x0019
        L_0x0017:
            r3 = move-exception
            goto L_0x0024
        L_0x0019:
            r3 = 0
            r1 = 0
        L_0x001b:
            if (r1 == 0) goto L_0x002e
            r2.setResult(r0, r4)     // Catch:{ Exception -> 0x0017 }
            r2.finish()     // Catch:{ Exception -> 0x0017 }
            goto L_0x002e
        L_0x0024:
            r3.fillInStackTrace()
            java.lang.String r4 = "ACTIVITY_RESULT_HANDLER"
            java.lang.String r0 = "activityResultHandler: "
            android.util.Log.e(r4, r0, r3)
        L_0x002e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.app.base.BaseActivity.activityResultHandler(int, android.content.Intent):boolean");
    }
}
