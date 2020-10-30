package ar.com.santander.rio.mbanking.app.module.creditos;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.Html;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CBaseAccesibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CTasaValueAcc;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.ColumAmountView;
import ar.com.santander.rio.mbanking.view.tables.ColumLabelView;
import ar.com.santander.rio.mbanking.view.tables.ColumValueView;
import ar.com.santander.rio.mbanking.view.tables.RowColumnView;

public class RowViewCreatorCreditos {
    public static RowColumnView getRowImporteSolicitado(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumAmountView columAmountView = new ColumAmountView(context, str, Integer.valueOf(R.id.input_amount_id), true);
        columAmountView.setMaxPlaceDecimal(0);
        columAmountView.setMaxPlaceInteger(10);
        rowColumnView.setRow(new ColumLabelView(context, Html.fromHtml(context.getString(R.string.ID129_CREDITS_LENDCONF_LBL_IMPORT_SIN_ASTERISCO))), columAmountView);
        rowColumnView.setContentDescription(context.getString(R.string.ID129_CREDITS_LENDCONF_LBL_IMPORT_SIN_ASTERISCO));
        return rowColumnView;
    }

    public static RowColumnView getRowImporteMinimo(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, context.getString(R.string.ID130_CREDITS_LENDCONF_LBL_MINIMPORT), typeStyle);
        columLabelView.setCElementAcc(new CBaseAccesibility(context.getString(R.string.ACCESSIBILITY_CREDITOS_IMPORTE_MIN)));
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CAmountAcc());
        try {
            String applyFilterAmount = CAccessibility.getInstance(context).applyFilterAmount(str);
            StringBuilder sb = new StringBuilder();
            sb.append(context.getString(R.string.LBL_MINIMPORT));
            sb.append(applyFilterAmount);
            rowColumnView.setContentDescription(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowImporteMaximo(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, Html.fromHtml(context.getString(R.string.ID131_CREDITS_LENDCONF_LBL_MAXIMPORT_SIN_ASTERISCO)));
        columLabelView.setCElementAcc(new CBaseAccesibility(context.getString(R.string.ACCESSIBILITY_CREDITOS_IMPORTE_MAX_SIN_ASTERISCO)));
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CAmountAcc());
        try {
            String applyFilterAmount = CAccessibility.getInstance(context).applyFilterAmount(str);
            StringBuilder sb = new StringBuilder();
            sb.append(context.getString(R.string.LBL_MAXIMPORT));
            sb.append(applyFilterAmount);
            rowColumnView.setContentDescription(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowCantidadCuotas(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setClickable(true);
        ColumValueView columValueView = new ColumValueView(context, str, Integer.valueOf(R.id.selector_cant_cuotas));
        columValueView.setColorValue(R.color.generic_selectable);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID132_CREDITS_LENDCONF_LBL_NUMCUOTES)), columValueView, Integer.valueOf(R.id.row_selector_cant_cuotas));
        ((TextView) rowColumnView.findViewById(R.id.selector_cant_cuotas)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        ((TextView) rowColumnView.findViewById(R.id.selector_cant_cuotas)).setCompoundDrawablePadding(Utils.dpToPx(5, context));
        return rowColumnView;
    }

    public static RowColumnView getRowFechaPrimeraCuota(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setClickable(true);
        ColumValueView columValueView = new ColumValueView(context, str, Integer.valueOf(R.id.selector_date_id));
        columValueView.setColorValue(R.color.generic_selectable);
        ColumLabelView columLabelView = new ColumLabelView(context, Html.fromHtml(context.getString(R.string.ID133_CREDITS_LENDCONF_LBL_FIRSTCUOTE_SIN_ASTERISCO)));
        columLabelView.setCElementAcc(new CBaseAccesibility(context.getString(R.string.ACCESSIBILITY_CREDITOS_IMPORTE_PRIMERA_CUOTA_SIN_ASTERISCO)));
        rowColumnView.setRow(columLabelView, columValueView, Integer.valueOf(R.id.row_selector_date_id));
        ((TextView) rowColumnView.findViewById(R.id.selector_date_id)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        ((TextView) rowColumnView.findViewById(R.id.selector_date_id)).setCompoundDrawablePadding(Utils.dpToPx(5, context));
        return rowColumnView;
    }

    @TargetApi(17)
    public static RowColumnView getRowDestino(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setClickable(true);
        ColumValueView columValueView = new ColumValueView(context, str, Integer.valueOf(R.id.selector_destino));
        columValueView.setColorValue(R.color.generic_selectable);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID134_CREDITS_LENDCONF_LBL_TO)), columValueView, Integer.valueOf(R.id.row_selector_cant_destino));
        ((TextView) rowColumnView.findViewById(R.id.selector_destino)).setTextAlignment(3);
        ((TextView) rowColumnView.findViewById(R.id.selector_destino)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        ((TextView) rowColumnView.findViewById(R.id.selector_destino)).setCompoundDrawablePadding(Utils.dpToPx(5, context));
        return rowColumnView;
    }

    public static RowColumnView getRowTipoTasa(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, Html.fromHtml(context.getString(R.string.ID135_CREDITS_LENDCONF_LBL_RATETYPE)).toString(), typeStyle);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        StringBuilder sb = new StringBuilder();
        sb.append(columLabelView.mViewLabel.getText().toString());
        sb.append(", ");
        sb.append(columValueView.mViewValue.getText().toString());
        rowColumnView.setContentDescription(sb.toString());
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowTasaNominal(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, Html.fromHtml(context.getString(R.string.TC449812_Det_Prestamo_Prendarios_UVAS)).toString(), typeStyle);
        columLabelView.setCElementAcc(new CBaseAccesibility(context.getString(R.string.ID126_CREDITS_MAIN_LBL_YEARLYRATE)));
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CTasaValueAcc());
        try {
            String applyFilterTasaValue = CAccessibility.getInstance(context).applyFilterTasaValue(str);
            StringBuilder sb = new StringBuilder();
            sb.append(context.getString(R.string.ID126_CREDITS_MAIN_LBL_YEARLYRATE));
            sb.append(applyFilterTasaValue);
            rowColumnView.setContentDescription(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }
}
