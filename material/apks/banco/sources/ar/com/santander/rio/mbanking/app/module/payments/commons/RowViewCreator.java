package ar.com.santander.rio.mbanking.app.module.payments.commons;

import android.content.Context;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CDateAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CNroAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CNumberOneToOneAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CPhoneNumberAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CTimeAcc;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.utils.UtilDate;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.ColumAmountView;
import ar.com.santander.rio.mbanking.view.tables.ColumLabelView;
import ar.com.santander.rio.mbanking.view.tables.ColumValueView;
import ar.com.santander.rio.mbanking.view.tables.RowColumnView;

public class RowViewCreator {
    public static RowColumnView getRowCompany(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID175_PAYMENT_MAIN_LBL_COMPANY), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowIdentification(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID179_PAYMENT_DETAILWITH_LBL_ID), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowCurrency(Context context, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID181_PAYMENT_DETAILWITH_LBL_CURRENCY), typeStyle), new ColumValueView(context, context.getString(R.string.TEXT_NAME_PESOS), UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowExpired(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, UtilDate.isDate(str, Constants.FORMAT_DATE_WS_2) ? UtilDate.getDateFormat(str, Constants.FORMAT_DATE_WS_2) : Constants.FORMAT_DATE_NULL, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CDateAcc());
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID184_PAYMENT_DETAILWITH_LBL_ENDDATE), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowInvoice(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID185_PAYMENT_DETAILWITH_LBL_BILL), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowMethodPayment(Context context, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID186_PAYMENT_DETAILWITH_LBL_PAYMENTMETHOD), typeStyle), new ColumValueView(context, context.getString(R.string.ID187_PAYMENT_DETAILWITH_LBL_URL), UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowNroRecipt(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CNumberOneToOneAcc());
        ColumLabelView columLabelView = new ColumLabelView(context, context.getString(R.string.ID220_PAYMENT_DETAIL_LBL_PROOFNR), typeStyle);
        columLabelView.setCElementAcc(new CNroAcc());
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowNroControl(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CNumberOneToOneAcc());
        ColumLabelView columLabelView = new ColumLabelView(context, context.getString(R.string.ID391_CELULAR_PROOF_LBL_CTLNUM), typeStyle);
        columLabelView.setCElementAcc(new CNroAcc());
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowDataPayment(Context context, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID222_PAYMENT_DETAIL_LBL_OPDATE), typeStyle), new ColumValueView(context, "", UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowDate(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CDateAcc());
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID223_PAYMENT_DETAIL_LBL_DATE), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowTime(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CTimeAcc());
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID224_PAYMENT_DETAIL_LBL_TIME), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowNumberTopUp(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CPhoneNumberAcc());
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID375_CELULAR_DETAIL_LBL_NUMBERTORE), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowAccountSelector(Context context, String str, TypeStyle typeStyle, Boolean bool) {
        return getRowAccountValue(context, context.getString(R.string.ID183_PAYMENT_DETAILWITH_LBL_DEBITACCOUNT), str, Integer.valueOf(R.color.generic_selectable), typeStyle, bool);
    }

    public static RowColumnView getRowAccountValue(Context context, String str, String str2, Integer num, TypeStyle typeStyle, Boolean bool) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str2, Integer.valueOf(R.id.selector_account_id), num);
        columValueView.setCElementAcc(new CAccountAcc());
        rowColumnView.setRow(new ColumLabelView(context, str, typeStyle), columValueView, Integer.valueOf(R.id.row_selector_account_id));
        if (bool.booleanValue()) {
            ((TextView) rowColumnView.findViewById(R.id.selector_account_id)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
            ((TextView) rowColumnView.findViewById(R.id.selector_account_id)).setCompoundDrawablePadding(Utils.dpToPx(5, context));
        }
        return rowColumnView;
    }

    public static RowColumnView getRowAmountSelector(Context context, String str, TypeStyle typeStyle, Boolean bool) {
        return getRowAmountValue(context, context.getString(R.string.ID377_CELULAR_DETAIL_LBL_SELECT), str, Integer.valueOf(R.color.generic_selectable), typeStyle, bool);
    }

    public static RowColumnView getRowAmountValue(Context context, String str, String str2, Integer num, TypeStyle typeStyle, Boolean bool) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str2, Integer.valueOf(R.id.input_amount_id), num, null);
        columValueView.setCElementAcc(new CAmountAcc());
        rowColumnView.setRow(new ColumLabelView(context, str, typeStyle), columValueView, Integer.valueOf(R.id.row_selector_amount_id));
        if (bool.booleanValue()) {
            ((TextView) rowColumnView.findViewById(R.id.input_amount_id)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
            ((TextView) rowColumnView.findViewById(R.id.input_amount_id)).setCompoundDrawablePadding(Utils.dpToPx(5, context));
        }
        return rowColumnView;
    }

    public static RowColumnView getRowAmountSwitchValueToInput(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumAmountView columAmountView = new ColumAmountView(context, str, (int) R.id.input_amount_id, (int) R.color.red_corporative_text);
        columAmountView.setReadOnly();
        columAmountView.setCElementAcc(new CAccountAcc());
        columAmountView.setImeOpts(6);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID377_CELULAR_DETAIL_LBL_IMPORT), typeStyle), columAmountView, Integer.valueOf(R.id.row_selector_amount_id));
        return rowColumnView;
    }
}
