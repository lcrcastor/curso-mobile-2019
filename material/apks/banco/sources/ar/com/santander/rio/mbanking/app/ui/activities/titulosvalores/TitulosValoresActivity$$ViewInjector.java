package ar.com.santander.rio.mbanking.app.ui.activities.titulosvalores;

import android.support.v4.widget.NestedScrollView;
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

public class TitulosValoresActivity$$ViewInjector {
    public static void inject(Finder finder, final TitulosValoresActivity titulosValoresActivity, Object obj) {
        titulosValoresActivity.tvTitle = (TextView) finder.findRequiredView(obj, R.id.view_title, "field 'tvTitle'");
        titulosValoresActivity.tvLabelTitular = (TextView) finder.findRequiredView(obj, R.id.tvLabelTitular, "field 'tvLabelTitular'");
        titulosValoresActivity.tvLabelValuePesos = (TextView) finder.findRequiredView(obj, R.id.tvLabelValuePesos, "field 'tvLabelValuePesos'");
        titulosValoresActivity.tvLabelValueDolar = (TextView) finder.findRequiredView(obj, R.id.tvLabelValueDolar, "field 'tvLabelValueDolar'");
        View findRequiredView = finder.findRequiredView(obj, R.id.spinner_change_account, "field 'selectorAccount' and method 'onChangeCuentaListener'");
        titulosValoresActivity.selectorAccount = (CustomSpinner) findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                titulosValoresActivity.onChangeCuentaListener();
            }
        });
        titulosValoresActivity.tvFunctionalityTitle = (TextView) finder.findRequiredView(obj, R.id.functionality_title, "field 'tvFunctionalityTitle'");
        titulosValoresActivity.tabSelector = (HorizontalScrollList) finder.findRequiredView(obj, R.id.tabSelector, "field 'tabSelector'");
        titulosValoresActivity.rvTitulosValores = (RecyclerView) finder.findRequiredView(obj, R.id.recycler_titulos_valores, "field 'rvTitulosValores'");
        titulosValoresActivity.layoutOKResult = (LinearLayout) finder.findRequiredView(obj, R.id.layout_ok_result, "field 'layoutOKResult'");
        titulosValoresActivity.layoutOKHeader = (LinearLayout) finder.findRequiredView(obj, R.id.layout_ok_header, "field 'layoutOKHeader'");
        titulosValoresActivity.layoutError = finder.findRequiredView(obj, R.id.include_error, "field 'layoutError'");
        titulosValoresActivity.tvTitleError = (TextView) finder.findRequiredView(obj, R.id.title_error, "field 'tvTitleError'");
        titulosValoresActivity.ivError = (ImageView) finder.findRequiredView(obj, R.id.image_error, "field 'ivError'");
        titulosValoresActivity.tvDescriptionError = (TextView) finder.findRequiredView(obj, R.id.description_error, "field 'tvDescriptionError'");
        titulosValoresActivity.tvVerTerminosCondiciones = (TextView) finder.findRequiredView(obj, R.id.tv_ver_terminos_condiciones, "field 'tvVerTerminosCondiciones'");
        titulosValoresActivity.scrollView = (NestedScrollView) finder.findRequiredView(obj, R.id.scrollView, "field 'scrollView'");
        titulosValoresActivity.viewParcialError = finder.findRequiredView(obj, R.id.view_msj_error, "field 'viewParcialError'");
        titulosValoresActivity.tvSectionMsjError = (TextView) finder.findRequiredView(obj, R.id.section_msj_error, "field 'tvSectionMsjError'");
    }

    public static void reset(TitulosValoresActivity titulosValoresActivity) {
        titulosValoresActivity.tvTitle = null;
        titulosValoresActivity.tvLabelTitular = null;
        titulosValoresActivity.tvLabelValuePesos = null;
        titulosValoresActivity.tvLabelValueDolar = null;
        titulosValoresActivity.selectorAccount = null;
        titulosValoresActivity.tvFunctionalityTitle = null;
        titulosValoresActivity.tabSelector = null;
        titulosValoresActivity.rvTitulosValores = null;
        titulosValoresActivity.layoutOKResult = null;
        titulosValoresActivity.layoutOKHeader = null;
        titulosValoresActivity.layoutError = null;
        titulosValoresActivity.tvTitleError = null;
        titulosValoresActivity.ivError = null;
        titulosValoresActivity.tvDescriptionError = null;
        titulosValoresActivity.tvVerTerminosCondiciones = null;
        titulosValoresActivity.scrollView = null;
        titulosValoresActivity.viewParcialError = null;
        titulosValoresActivity.tvSectionMsjError = null;
    }
}
