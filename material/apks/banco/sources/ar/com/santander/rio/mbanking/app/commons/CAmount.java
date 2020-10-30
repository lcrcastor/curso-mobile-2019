package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.utils.UtilString;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CAmount {
    public final String[] bracket = {"(", ")"};
    public String mAmount;
    public String mCurrencySymbol = "";
    public String mFormat;
    public boolean shouldCleanAmount = true;

    public CAmount() {
    }

    public CAmount(String str, String str2) {
        this.mAmount = str;
        this.mFormat = str2;
    }

    public CAmount(String str) {
        this.mAmount = str;
    }

    public static CAmount getInstance(String str) {
        return new CAmount(str);
    }

    public static CAmount getInstance(String str, String str2) {
        return new CAmount(str, str2);
    }

    public static CAmount getInstance() {
        return new CAmount();
    }

    public String getAmount() {
        if (isAmountPossite()) {
            return getAmountPossitive();
        }
        return getAmountNegative();
    }

    public String getAmountPossitive() {
        if (this.shouldCleanAmount) {
            cleanAmount();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrencySymbol);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(getAmountFormattedES());
        return sb.toString();
    }

    public String getAmountNegative() {
        if (this.shouldCleanAmount) {
            cleanAmount();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrencySymbol);
        sb.append(this.bracket[0]);
        sb.append(getAmountFormattedES());
        sb.append(this.bracket[1]);
        return sb.toString();
    }

    public boolean isAmountPossite() {
        if (this.mAmount == null) {
            return true;
        }
        return !this.mAmount.contains("-");
    }

    public boolean isAmountNegative() {
        return this.mAmount.contains("-");
    }

    public String getAmountFormattedES() {
        try {
            return getAmountFormattedES(new Double(this.mAmount));
        } catch (Exception unused) {
            return this.mAmount;
        }
    }

    public String getAmountFormattedES(Double d) {
        try {
            if (this.mFormat == null) {
                return new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_APP)).format(d);
            }
            return new DecimalFormat(this.mFormat, DecimalFormatSymbols.getInstance(Constants.LOCALE_DEFAULT_APP)).format(d);
        } catch (Exception unused) {
            return this.mAmount;
        }
    }

    public void cleanAmount() {
        try {
            if (this.mAmount != null) {
                this.mAmount = this.mAmount.replace(Constants.SYMBOL_POSITIVE, "").replace("-", "").replace(this.bracket[0], "").replace(this.bracket[1], "").replace(Constants.SYMBOL_CURRENCY_DOLAR, "").replace(Constants.SYMBOL_CURRENCY_PESOS, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSymbolCurrency(String str) {
        this.mCurrencySymbol = str;
    }

    public void setShouldCleanAmount(boolean z) {
        this.shouldCleanAmount = z;
    }

    public void setSymbolCurrencyDollarOrPeso(boolean z) {
        if (z) {
            setSymbolCurrency(Constants.SYMBOL_CURRENCY_DOLAR);
        } else {
            setSymbolCurrency(Constants.SYMBOL_CURRENCY_PESOS);
        }
    }

    public void setSymbolCurrencyDollarOrPeso(String str) {
        if (Constants.SYMBOL_CURRENCY_PESOS_STR.equals(str)) {
            setSymbolCurrency(Constants.SYMBOL_CURRENCY_PESOS);
        } else {
            setSymbolCurrency(Constants.SYMBOL_CURRENCY_DOLAR);
        }
    }

    public String getAmountToWs(String str) {
        try {
            CAmount cAmount = new CAmount(this.mAmount);
            cAmount.cleanAmount();
            CFormatterAmounts cFormatterAmounts = new CFormatterAmounts();
            Double doubleFromString = cFormatterAmounts.getDoubleFromString(cAmount.mAmount);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(cFormatterAmounts.getAmountToWebService(doubleFromString.doubleValue()));
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public boolean isAmountValid() {
        return UtilString.isNumericDouble(this.mAmount);
    }

    public double getFormatDoubleAmount() {
        try {
            if (isAmountValid()) {
                return Double.parseDouble(this.mAmount);
            }
        } catch (Exception unused) {
        }
        return 0.0d;
    }

    public Double getDoubleFromAmountFormattedES() {
        try {
            CAmount instance = getInstance(this.mAmount);
            instance.cleanAmount();
            return Double.valueOf(Double.parseDouble(instance.getAmount().replace(".", "").replace(",", ".")));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getNegativeFromBrackets(String str) {
        String str2;
        String[] strArr = {"(", ")"};
        if (str != null) {
            try {
                str2 = str.replace(strArr[0], "- ").replace(strArr[1], "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            str2 = null;
        }
        return str2;
    }
}
