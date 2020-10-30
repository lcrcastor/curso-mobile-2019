package ar.com.santander.rio.mbanking.app.module.accounts;

import android.content.Context;
import android.util.SparseArray;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.services.model.general.OptionsTransactions;

public class ConstantsTransactions {
    public static final String INDEX_TYPE_ACCOUNT_LARGE = "COMBOGRUPMOVPER";

    public static OptionsTransactions getTypeTransactionDefault(Context context) {
        return new OptionsTransactions("000", context.getString(R.string.TYPE_FILTER_TRANSACTIONS_0));
    }

    public static OptionsTransactions getTypeAmountDefault(Context context) {
        return new OptionsTransactions("T", context.getString(R.string.TEXT_TYPE_AMOUNT_ALL_2));
    }

    public static SparseArray<OptionsTransactions> getAllAmounts(Context context) {
        SparseArray<OptionsTransactions> sparseArray = new SparseArray<>();
        sparseArray.append(sparseArray.size(), getTypeAmountDefault(context));
        sparseArray.append(sparseArray.size(), new OptionsTransactions("D", context.getString(R.string.TEXT_TYPE_AMOUNT_DEBITO)));
        sparseArray.append(sparseArray.size(), new OptionsTransactions("C", context.getString(R.string.TEXT_TYPE_AMOUNT_CREDIT)));
        return sparseArray;
    }
}
