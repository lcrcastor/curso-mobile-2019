package ar.com.santander.rio.mbanking.app.module.longtermdeposit;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAmountAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CNumberOneToOneAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CTasaInteresAcc;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CTasaValueAcc;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.utils.UtilStyleCommons;
import ar.com.santander.rio.mbanking.utils.Utils;
import ar.com.santander.rio.mbanking.view.tables.BaseColumn.TypeStyle;
import ar.com.santander.rio.mbanking.view.tables.ColumAmountView;
import ar.com.santander.rio.mbanking.view.tables.ColumInputView;
import ar.com.santander.rio.mbanking.view.tables.ColumLabelView;
import ar.com.santander.rio.mbanking.view.tables.ColumValueView;
import ar.com.santander.rio.mbanking.view.tables.RowColumnView;

public class RowViewCreatorLongTermDeposit {
    public static RowColumnView getRowAmount(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        try {
            ColumAmountView columAmountView = new ColumAmountView(context, str, Integer.valueOf(R.id.input_amount_id), true);
            columAmountView.setImeOpts(5);
            columAmountView.setMaxLenght(Constants.MAX_LENGTH_AMOUNT);
            rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID180_PAYMENT_DETAILWITH_LBL_PAYMENTAMOUNT)), columAmountView);
        } catch (Exception e) {
            Log.e("@dev", "Error textview", e);
        }
        return rowColumnView;
    }

    public static RowColumnView getRowTerm(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumInputView columInputView = new ColumInputView(context, str, (int) R.id.input_term_id);
        columInputView.setMaxLenght(5);
        columInputView.setKeyboardNumeric();
        columInputView.setOnlyNumbers();
        columInputView.setIMEOptions(6);
        columInputView.clearTextChangedListeners();
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID313_TERM_LABEL)), columInputView);
        return rowColumnView;
    }

    public static RowColumnView getRowCurrency(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, Integer.valueOf(R.id.selector_currency_id));
        columValueView.setColorValue(R.color.generic_selectable);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID181_PAYMENT_DETAILWITH_LBL_CURRENCY)), columValueView, Integer.valueOf(R.id.row_selector_currency_id));
        ((TextView) rowColumnView.findViewById(R.id.selector_currency_id)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        ((TextView) rowColumnView.findViewById(R.id.selector_currency_id)).setCompoundDrawablePadding(Utils.dpToPx(5, context));
        return rowColumnView;
    }

    public static RowColumnView getRowExpirationDate(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, Integer.valueOf(R.id.selector_date_id));
        columValueView.setColorValue(R.color.generic_selectable);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID314_LONG_TERM_DEPOSIT_EXPIRATION_DATE)), columValueView, Integer.valueOf(R.id.row_selector_date_id));
        ((TextView) rowColumnView.findViewById(R.id.selector_date_id)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        ((TextView) rowColumnView.findViewById(R.id.selector_date_id)).setCompoundDrawablePadding(Utils.dpToPx(5, context));
        return rowColumnView;
    }

    public static RowColumnView getRowType(Context context, String str, String str2, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, str, typeStyle), new ColumValueView(context, str2, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowExpiredAction(Context context, String str) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, Integer.valueOf(R.id.input_expired_action_id));
        columValueView.setColorValue(R.color.generic_selectable);
        columValueView.setAlignment(3);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID317_FIXEDTERM_LBL_ENDDATEACTION)), columValueView, Integer.valueOf(R.id.row_selector_expired_action_id));
        if (VERSION.SDK_INT >= 17) {
            rowColumnView.setTextAlignment(3);
        }
        ((TextView) rowColumnView.findViewById(R.id.input_expired_action_id)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
        TextView textView = (TextView) rowColumnView.findViewById(R.id.input_expired_action_id);
        textView.setPadding(5, 0, 0, 0);
        textView.setCompoundDrawablePadding(2);
        textView.setMaxLines(2);
        textView.setGravity(6);
        return rowColumnView;
    }

    public static RowColumnView getRowCapital(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CAmountAcc());
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID321_FIXEDTERM_CONFIRM_LBL_AMOUNT), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowCurrencyValue(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID322_FIXEDTERM_CONFIRM_LBL_CURRENCY), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowTermDaysValue(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID323_FIXEDTERM_CONFIRM_LBL_FIXEDTERM), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowTNA(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, context.getString(R.string.ID324_FIXEDTERM_CONFIRM_LBL_TNA), typeStyle);
        columLabelView.setCElementAcc(new CTasaInteresAcc());
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CTasaValueAcc());
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowTEA(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, context.getString(R.string.ID325_FIXEDTERM_CONFIRM_LBL_TEA), typeStyle);
        columLabelView.setCElementAcc(new CTasaInteresAcc());
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        rowColumnView.setRow(columLabelView, columValueView);
        columValueView.setCElementAcc(new CTasaValueAcc());
        return rowColumnView;
    }

    public static RowColumnView getRowIntereses(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CAmountAcc());
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID326_FIXEDTERM_CONFIRM_LBL_RATE), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowImpuesto(Context context, String str, String str2, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str2, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CAmountAcc());
        rowColumnView.setRow(new ColumLabelView(context, str, typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowAccountValue(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CAccountAcc());
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID327_FIXEDTERM_CONFIRM_LBL_FROMACCOUNT), typeStyle), columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowConstitutionDate(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID328_FIXEDTERM_CONFIRM_LBL_CONSTDATE), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowVtoDate(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID329_FIXEDTERM_CONFIRM_LBL_ENDDATE), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowActionVto(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID330_FIXEDTERM_CONFIRM_LBL_ENDDATEACTION), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowNumCert(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, context.getString(R.string.ID346_FIXEDTERM_PROOF_LBL_CERTIFNUM), typeStyle);
        columLabelView.setTextAccesibility(context.getString(R.string.ID346_FIXEDTERM_PROOF_LBL_CERTIFNUM_ACC));
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CNumberOneToOneAcc());
        try {
            columValueView.setTextAccesibility(CAccessibility.getInstance(context.getApplicationContext()).applyFilterCharacterToCharacter(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowNumComp(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        ColumLabelView columLabelView = new ColumLabelView(context, context.getString(R.string.ID347_FIXEDTERM_PROOF_LBL_PROOFNUM), typeStyle);
        columLabelView.setTextAccesibility(context.getString(R.string.ID347_FIXEDTERM_PROOF_LBL_PROOFNUM_ACC));
        ColumValueView columValueView = new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle));
        columValueView.setCElementAcc(new CNumberOneToOneAcc());
        try {
            columValueView.setTextAccesibility(CAccessibility.getInstance(context.getApplicationContext()).applyFilterCharacterToCharacter(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rowColumnView.setRow(columLabelView, columValueView);
        return rowColumnView;
    }

    public static RowColumnView getRowValData(Context context, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID348_FIXEDTERM_PROOF_LBL_OPDATA), typeStyle), new ColumValueView(context, "", UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowValDate(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID349_FIXEDTERM_PROOF_LBL_DATE), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }

    public static RowColumnView getRowValTime(Context context, String str, TypeStyle typeStyle) {
        RowColumnView rowColumnView = new RowColumnView(context);
        rowColumnView.setRow(new ColumLabelView(context, context.getString(R.string.ID350_FIXEDTERM_PROOF_LBL_HOUR), typeStyle), new ColumValueView(context, str, UtilStyleCommons.getTypeStyleValue(typeStyle)));
        return rowColumnView;
    }
}
