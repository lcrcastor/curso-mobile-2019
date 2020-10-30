package ar.com.santander.rio.mbanking.app.module.accounts;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccountNameAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CComunAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CFiltersAccessibility;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.ColumLabelView;
import ar.com.santander.rio.mbanking.view.tables.ColumValueView;
import ar.com.santander.rio.mbanking.view.tables.RowColumnView;

public class RowViewCreatorAccounts {
    public static RowColumnView getRowAccount(Context context, String str, String str2, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str2, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CAccountAcc());
        ColumLabelView columLabelView = new ColumLabelView(context, str, typeStyle);
        columLabelView.setCElementAcc(new CAccountNameAcc());
        columLabelView.setContentDescription(new CFiltersAccessibility(context).filterSymbolAccounts(str));
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowDate(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CDateAcc());
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID101_ACCOUNTS_DETAIL_LBL_DATE), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowSucOrigen(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, context.getString(R.string.ID102_ACCOUNTS_DETAIL_LBL_SUC), typeStyle);
        columLabelView.setContentDescription(context.getString(R.string.ACCESSIBILITY_ACCOUNT_SUC_ORIGEN));
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CComunAcc());
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowReference(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID103_ACCOUNTS_DETAIL_LBL_REF), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowAmount(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setContentDescription(new CFiltersAccessibility(context).filterAmountAccount(str));
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID104_ACCOUNTS_DETAIL_LBL_PRICE), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowCurrency(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID105_ACCOUNTS_DETAIL_LBL_BADGE), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }
}
