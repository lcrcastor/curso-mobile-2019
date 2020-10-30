package ar.com.santander.rio.mbanking.app.ui.activities.custodias;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.CustomSpinner;
import ar.com.santander.rio.mbanking.view.HorizontalScrollList;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;

public class CustodiasActivity$$ViewInjector {
    public static void inject(Finder finder, final CustodiasActivity custodiasActivity, Object obj) {
        custodiasActivity.tvTitle = (TextView) finder.findRequiredView(obj, R.id.view_title, "field 'tvTitle'");
        custodiasActivity.tvLabelTitular = (TextView) finder.findRequiredView(obj, R.id.tvLabelTitular, "field 'tvLabelTitular'");
        custodiasActivity.tvLabelValuePesos = (TextView) finder.findRequiredView(obj, R.id.tvLabelValuePesos, "field 'tvLabelValuePesos'");
        custodiasActivity.tvLabelValueDolar = (TextView) finder.findRequiredView(obj, R.id.tvLabelValueDolar, "field 'tvLabelValueDolar'");
        View findRequiredView = finder.findRequiredView(obj, R.id.spinner_change_account, "field 'selectorAccount' and method 'onChangeCuentaListener'");
        custodiasActivity.selectorAccount = (CustomSpinner) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                custodiasActivity.onChangeCuentaListener();
            }
        });
        custodiasActivity.tvFunctionalityTitle = (TextView) finder.findRequiredView(obj, R.id.functionality_title, "field 'tvFunctionalityTitle'");
        custodiasActivity.tabSelector = (HorizontalScrollList) finder.findRequiredView(obj, R.id.tabSelector, "field 'tabSelector'");
        custodiasActivity.rvCustodias = (RecyclerView) finder.findRequiredView(obj, R.id.recycler_custodias, "field 'rvCustodias'");
        custodiasActivity.layoutOKHeader = (LinearLayout) finder.findRequiredView(obj, R.id.layout_values_service, "field 'layoutOKHeader'");
        custodiasActivity.layoutOKResult = (LinearLayout) finder.findRequiredView(obj, R.id.layout_ok_result, "field 'layoutOKResult'");
        custodiasActivity.layoutError = finder.findRequiredView(obj, R.id.include_error, "field 'layoutError'");
        custodiasActivity.tvTitleError = (TextView) finder.findRequiredView(obj, R.id.title_error, "field 'tvTitleError'");
        custodiasActivity.ivError = (ImageView) finder.findRequiredView(obj, R.id.image_error, "field 'ivError'");
        custodiasActivity.tvDescriptionError = (TextView) finder.findRequiredView(obj, R.id.description_error, "field 'tvDescriptionError'");
        custodiasActivity.tvCustodiaDolaresLabel1 = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_tenencia_dolares_label_1, "field 'tvCustodiaDolaresLabel1'");
        custodiasActivity.tvCustodiaDolaresLabel2 = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_tenencia_dolares_label_2, "field 'tvCustodiaDolaresLabel2'");
        custodiasActivity.tvCustodiaDolaresLabel3 = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_tenencia_dolares_label_3, "field 'tvCustodiaDolaresLabel3'");
        custodiasActivity.tvCustodiaPesosLabel1 = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_tenencia_pesos_label_1, "field 'tvCustodiaPesosLabel1'");
        custodiasActivity.tvCustodiaPesosLabel2 = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_tenencia_pesos_label_2, "field 'tvCustodiaPesosLabel2'");
        custodiasActivity.tvCustodiaPesosLabel3 = (TextView) finder.findRequiredView(obj, R.id.tv_custodia_tenencia_pesos_label_3, "field 'tvCustodiaPesosLabel3'");
        custodiasActivity.tvVerTerminosCondiciones = (TextView) finder.findRequiredView(obj, R.id.tv_ver_terminos_condiciones, "field 'tvVerTerminosCondiciones'");
        custodiasActivity.viewParcialError = finder.findRequiredView(obj, R.id.view_msj_error, "field 'viewParcialError'");
        custodiasActivity.tvSectionMsjError = (TextView) finder.findRequiredView(obj, R.id.section_msj_error, "field 'tvSectionMsjError'");
    }

    public static void reset(CustodiasActivity custodiasActivity) {
        custodiasActivity.tvTitle = null;
        custodiasActivity.tvLabelTitular = null;
        custodiasActivity.tvLabelValuePesos = null;
        custodiasActivity.tvLabelValueDolar = null;
        custodiasActivity.selectorAccount = null;
        custodiasActivity.tvFunctionalityTitle = null;
        custodiasActivity.tabSelector = null;
        custodiasActivity.rvCustodias = null;
        custodiasActivity.layoutOKHeader = null;
        custodiasActivity.layoutOKResult = null;
        custodiasActivity.layoutError = null;
        custodiasActivity.tvTitleError = null;
        custodiasActivity.ivError = null;
        custodiasActivity.tvDescriptionError = null;
        custodiasActivity.tvCustodiaDolaresLabel1 = null;
        custodiasActivity.tvCustodiaDolaresLabel2 = null;
        custodiasActivity.tvCustodiaDolaresLabel3 = null;
        custodiasActivity.tvCustodiaPesosLabel1 = null;
        custodiasActivity.tvCustodiaPesosLabel2 = null;
        custodiasActivity.tvCustodiaPesosLabel3 = null;
        custodiasActivity.tvVerTerminosCondiciones = null;
        custodiasActivity.viewParcialError = null;
        custodiasActivity.tvSectionMsjError = null;
    }
}
