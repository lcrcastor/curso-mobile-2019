package ar.com.santander.rio.mbanking.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Model.MenuOption;
import ar.com.santander.rio.mbanking.app.ui.Model.MenuOption.OptionType;
import ar.com.santander.rio.mbanking.app.ui.adapters.MenuAdapter;
import ar.com.santander.rio.mbanking.app.ui.adapters.MenuAdapter.MenuActionsListener;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.model.general.DatosPersonales;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.crashlytics.android.Crashlytics;
import javax.inject.Inject;

public class PrivateMenuActivity extends AbstractNavDrawerActivity implements MenuActionsListener {
    public static final String MENU_PRIVADO_SELECTED_OPTION_POSITION = "PrivateMenuSelectedOptionPosition";
    public boolean menuLocked = false;
    @Inject
    public SessionManager sessionManager;

    public boolean canExit(int i) {
        return true;
    }

    public int getDrawerLayoutId() {
        return R.id.drawer_layout;
    }

    public int getLeftDrawerId() {
        return R.id.ID16_PRIVATEMENU_BTN_CREDITS;
    }

    public int getMainLayout() {
        return R.layout.activity_nav_drawer;
    }

    public String getSelectedOption() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        String selectedOption = getSelectedOption();
        if (selectedOption != null) {
            for (int i = 0; i < this.listMenuOptions.size(); i++) {
                if (((MenuOption) this.listMenuOptions.get(i)).getTitle().equalsIgnoreCase(selectedOption)) {
                    ((MenuOption) this.listMenuOptions.get(i)).setSelected(true);
                    ((MenuOption) this.listMenuOptions.get(i)).setPressed(true);
                } else {
                    ((MenuOption) this.listMenuOptions.get(i)).setSelected(false);
                    ((MenuOption) this.listMenuOptions.get(i)).setPressed(false);
                }
            }
            notifyDataSetChanged();
        }
    }

    public boolean isMenuLocked() {
        return this.menuLocked;
    }

    public void lockMenu(boolean z) {
        this.menuLocked = z;
        if (isMenuLocked()) {
            getDrawerLayout().setDrawerLockMode(1);
        } else {
            getDrawerLayout().setDrawerLockMode(0);
        }
    }

    public BaseAdapter getBaseAdapter() {
        DatosPersonales datosPersonales = this.sessionManager.getLoginUnico().getDatosPersonales();
        int parseInt = (this.sessionManager.getLoginUnico().getCrm() == null || !UtilString.isNumber(this.sessionManager.getLoginUnico().getCrm().cantidad)) ? 0 : Integer.parseInt(this.sessionManager.getLoginUnico().getCrm().cantidad);
        String str = "";
        if (!datosPersonales.getSegmento().isEmpty() && !datosPersonales.getUrlSegmento().isEmpty()) {
            str = datosPersonales.getSegmento();
            Crashlytics.setString("segmento", str);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(datosPersonales.getNombre());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(datosPersonales.getApellido());
        return a(parseInt, sb.toString(), datosPersonales.getUltimoAcceso(), str);
    }

    private MenuAdapter a(int i, String str, String str2, String str3) {
        return super.createMenuPrivadoAdapter(i, str, str2, str3, this);
    }

    public void showLogOutMessage(boolean z, final int i) {
        if (this.sessionManager != null && this.sessionManager.getFlagMustShowPopUp()) {
            final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(getString(R.string.IDX_ALERT_LBL_TITLE_ADVISE), getResources().getString(R.string.MSG_USER000030_Menu_avisoSalir), null, null, "Sí", "No", null);
            newInstance.setDialogListener(new IDialogListener() {
                public void onItemSelected(String str) {
                }

                public void onNegativeButton() {
                }

                public void onSimpleActionButton() {
                }

                public void onPositiveButton() {
                    newInstance.dismiss();
                    Intent intent = PrivateMenuActivity.this.getIntent();
                    intent.putExtra(PrivateMenuActivity.MENU_PRIVADO_SELECTED_OPTION_POSITION, i);
                    PrivateMenuActivity.this.setResult(-1, intent);
                    PrivateMenuActivity.this.finish();
                    PrivateMenuActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
            newInstance.show(getSupportFragmentManager(), "Dialog");
        }
    }

    public void onClickItem(int i) {
        ((MenuOption) this.listMenuOptions.get(i)).setSelected(false);
        ((MenuOption) this.listMenuOptions.get(i)).setPressed(false);
        notifyDataSetChanged();
        if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.HOME || ((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.NAME || ((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.PRIVATE_ACCESS || ((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.DISCONNECT) {
            if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.DISCONNECT) {
                showLogOutMessage(false, i);
            }
            if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.HOME) {
                showLogOutMessage(false, i);
            } else if (((MenuOption) this.listMenuOptions.get(i)).getOptionType() == OptionType.PRIVATE_ACCESS) {
                showLogOutMessage(false, i);
            }
        } else {
            closeDrawer();
            Intent intent = new Intent(this, SantanderRioMainActivity.class);
            intent.putExtra(MENU_PRIVADO_SELECTED_OPTION_POSITION, i);
            setResult(-1, intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!activityResultHandler(i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
    }
}
