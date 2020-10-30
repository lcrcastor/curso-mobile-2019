package ar.com.santander.rio.mbanking.app.ui.fragments.privado;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.AmountView;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class CuentasFragment$$ViewInjector {
    public static void inject(Finder finder, final CuentasFragment cuentasFragment, Object obj) {
        cuentasFragment.slTransactions = (SlidingUpPanelLayout) finder.findRequiredView(obj, R.id.slTransactions, "field 'slTransactions'");
        cuentasFragment.cuentasContainer = finder.findRequiredView(obj, R.id.cuentasContainer, "field 'cuentasContainer'");
        cuentasFragment.wrapperCount = (ViewGroup) finder.findRequiredView(obj, R.id.wrapperContent, "field 'wrapperCount'");
        cuentasFragment.tabSelector = (HorizontalScrollList) finder.findRequiredView(obj, R.id.tabSelector, "field 'tabSelector'");
        cuentasFragment.idCuenta = (TextView) finder.findRequiredView(obj, R.id.f166selectorCuentaidCuenta, "field 'idCuenta'");
        cuentasFragment.viewSaldo = (LinearLayout) finder.findRequiredView(obj, R.id.f116cuentasFragmentsaldoTab, "field 'viewSaldo'");
        cuentasFragment.viewShareContentAlias = (TextView) finder.findRequiredView(obj, R.id.shareContentAlias, "field 'viewShareContentAlias'");
        cuentasFragment.selectorCuentas = (LinearLayout) finder.findRequiredView(obj, R.id.f117cuentasFragmentselector, "field 'selectorCuentas'");
        cuentasFragment.viewCopyContentCBU = (TextView) finder.findRequiredView(obj, R.id.copyContentCBU, "field 'viewCopyContentCBU'");
        cuentasFragment.viewCBU = (LinearLayout) finder.findRequiredView(obj, R.id.f115cuentasFragmentcbuTab, "field 'viewCBU'");
        cuentasFragment.cuentaCBUValue = (TextView) finder.findRequiredView(obj, R.id.f113cuentaCBUvalue, "field 'cuentaCBUValue'");
        cuentasFragment.cuentaAliasLabel = (TextView) finder.findRequiredView(obj, R.id.cuentaAlias_label, "field 'cuentaAliasLabel'");
        cuentasFragment.cuentaAliasValue = (TextView) finder.findRequiredView(obj, R.id.cuentaAlias_value, "field 'cuentaAliasValue'");
        cuentasFragment.imgAliasHelp = (ImageView) finder.findRequiredView(obj, R.id.cuentaAlias_help, "field 'imgAliasHelp'");
        cuentasFragment.cuentaSaldoValue = (AmountView) finder.findRequiredView(obj, R.id.f114cuentaSaldovalue, "field 'cuentaSaldoValue'");
        cuentasFragment.avLimiteExtraccion = (AmountView) finder.findRequiredView(obj, R.id.avLimiteExtraccion, "field 'avLimiteExtraccion'");
        cuentasFragment.ivArrow_limite_extraccion = (ImageView) finder.findRequiredView(obj, R.id.ivArrow_limite_extraccion, "field 'ivArrow_limite_extraccion'");
        cuentasFragment.limiteDescubierto = (AmountView) finder.findRequiredView(obj, R.id.limiteDescubierto, "field 'limiteDescubierto'");
        cuentasFragment.layoutKO = (RelativeLayout) finder.findRequiredView(obj, R.id.layout_ko_result, "field 'layoutKO'");
        cuentasFragment.layoutOK = (LinearLayout) finder.findRequiredView(obj, R.id.layout_ok_result, "field 'layoutOK'");
        cuentasFragment.layoutNoAccounts = (LinearLayout) finder.findRequiredView(obj, R.id.layout_no_accounts, "field 'layoutNoAccounts'");
        cuentasFragment.textKOResult = (TextView) finder.findRequiredView(obj, R.id.text_ko_result, "field 'textKOResult'");
        cuentasFragment.image_ko_result = (ImageView) finder.findRequiredView(obj, R.id.image_ko_result, "field 'image_ko_result'");
        View findRequiredView = finder.findRequiredView(obj, R.id.vgSelectorAccount, "field 'selectorAccount' and method 'seleccionarCuenta'");
        cuentasFragment.selectorAccount = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                cuentasFragment.y();
            }
        });
        View findRequiredView2 = finder.findRequiredView(obj, R.id.F06_00_btn_abm_alias, "field 'botonABMAlias' and method 'abmAlias'");
        cuentasFragment.botonABMAlias = (Button) findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                cuentasFragment.b(view);
            }
        });
        View findRequiredView3 = finder.findRequiredView(obj, R.id.F06_00_btn_modificar_alias, "field 'botonModificarAlias' and method 'modificarAlias'");
        cuentasFragment.botonModificarAlias = (Button) findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                cuentasFragment.c(view);
            }
        });
        cuentasFragment.viewAliasCuenta = (LinearLayout) finder.findRequiredView(obj, R.id.vContentAlias, "field 'viewAliasCuenta'");
        cuentasFragment.shareAlias = (LinearLayout) finder.findRequiredView(obj, R.id.shareAlias, "field 'shareAlias'");
        cuentasFragment.limite_extraction_row = (GridLayout) finder.findRequiredView(obj, R.id.limite__extraction_row, "field 'limite_extraction_row'");
        cuentasFragment.limite__descubierto_row = (LinearLayout) finder.findRequiredView(obj, R.id.limite__descubierto_row, "field 'limite__descubierto_row'");
        cuentasFragment.grayDividerLimiteExt = finder.findRequiredView(obj, R.id.grayDividerLimiteExt, "field 'grayDividerLimiteExt'");
    }

    public static void reset(CuentasFragment cuentasFragment) {
        cuentasFragment.slTransactions = null;
        cuentasFragment.cuentasContainer = null;
        cuentasFragment.wrapperCount = null;
        cuentasFragment.tabSelector = null;
        cuentasFragment.idCuenta = null;
        cuentasFragment.viewSaldo = null;
        cuentasFragment.viewShareContentAlias = null;
        cuentasFragment.selectorCuentas = null;
        cuentasFragment.viewCopyContentCBU = null;
        cuentasFragment.viewCBU = null;
        cuentasFragment.cuentaCBUValue = null;
        cuentasFragment.cuentaAliasLabel = null;
        cuentasFragment.cuentaAliasValue = null;
        cuentasFragment.imgAliasHelp = null;
        cuentasFragment.cuentaSaldoValue = null;
        cuentasFragment.avLimiteExtraccion = null;
        cuentasFragment.ivArrow_limite_extraccion = null;
        cuentasFragment.limiteDescubierto = null;
        cuentasFragment.layoutKO = null;
        cuentasFragment.layoutOK = null;
        cuentasFragment.layoutNoAccounts = null;
        cuentasFragment.textKOResult = null;
        cuentasFragment.image_ko_result = null;
        cuentasFragment.selectorAccount = null;
        cuentasFragment.botonABMAlias = null;
        cuentasFragment.botonModificarAlias = null;
        cuentasFragment.viewAliasCuenta = null;
        cuentasFragment.shareAlias = null;
        cuentasFragment.limite_extraction_row = null;
        cuentasFragment.limite__descubierto_row = null;
        cuentasFragment.grayDividerLimiteExt = null;
    }
}
