package ar.com.santander.rio.mbanking.app.ui.activities.titulosvaloresdetail;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class TitulosValoresDetailActivity$$ViewInjector {
    public static void inject(Finder finder, TitulosValoresDetailActivity titulosValoresDetailActivity, Object obj) {
        titulosValoresDetailActivity.tvTitle = (TextView) finder.findRequiredView(obj, R.id.view_title, "field 'tvTitle'");
        titulosValoresDetailActivity.tvLabelTitulosValoresDesc = (TextView) finder.findRequiredView(obj, R.id.tv_label_titulos_valores_desc, "field 'tvLabelTitulosValoresDesc'");
        titulosValoresDetailActivity.getTvLabelTitulosCodEspecie = (TextView) finder.findRequiredView(obj, R.id.tv_label_titulos_cod_especie, "field 'getTvLabelTitulosCodEspecie'");
        titulosValoresDetailActivity.tvLabelTitulosValoresTenenciaValuada = (TextView) finder.findRequiredView(obj, R.id.tv_label_titulos_valores_tenencia_valuada, "field 'tvLabelTitulosValoresTenenciaValuada'");
        titulosValoresDetailActivity.rvTitulosValores = (RecyclerView) finder.findRequiredView(obj, R.id.recycler_titulos_valores_detail, "field 'rvTitulosValores'");
        titulosValoresDetailActivity.tvTitleLeyenda = (TextView) finder.findRequiredView(obj, R.id.tv_title_leyenda, "field 'tvTitleLeyenda'");
        titulosValoresDetailActivity.tvDescriptionLeyenda = (TextView) finder.findRequiredView(obj, R.id.tv_desc_leyenda, "field 'tvDescriptionLeyenda'");
        titulosValoresDetailActivity.scrollView = (NestedScrollView) finder.findRequiredView(obj, R.id.scrollView, "field 'scrollView'");
    }

    public static void reset(TitulosValoresDetailActivity titulosValoresDetailActivity) {
        titulosValoresDetailActivity.tvTitle = null;
        titulosValoresDetailActivity.tvLabelTitulosValoresDesc = null;
        titulosValoresDetailActivity.getTvLabelTitulosCodEspecie = null;
        titulosValoresDetailActivity.tvLabelTitulosValoresTenenciaValuada = null;
        titulosValoresDetailActivity.rvTitulosValores = null;
        titulosValoresDetailActivity.tvTitleLeyenda = null;
        titulosValoresDetailActivity.tvDescriptionLeyenda = null;
        titulosValoresDetailActivity.scrollView = null;
    }
}
