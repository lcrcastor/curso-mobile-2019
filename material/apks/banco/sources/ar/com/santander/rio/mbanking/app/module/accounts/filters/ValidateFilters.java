package ar.com.santander.rio.mbanking.app.module.accounts.filters;

import ar.com.santander.rio.mbanking.app.commons.CAmount;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.utils.UtilString;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadablePartial;

public class ValidateFilters {
    public static boolean isDateAfterToToday(DateTime dateTime) {
        try {
            return dateTime.toLocalDate().isAfter(DateTime.now().toLocalDate());
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isDateBeforeOrEquals(DateTime dateTime, DateTime dateTime2) {
        boolean z = false;
        try {
            if (dateTime.toLocalDate().isBefore(dateTime2.toLocalDate()) || dateTime.toLocalDate().isEqual(dateTime2.toLocalDate())) {
                z = true;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isValidAmount(String str) {
        boolean z = false;
        try {
            if (CAmount.getInstance(str).getAmountToWs(Constants.SYMBOL_POSITIVE) != null) {
                z = true;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isMinorOrEquals(String str, String str2) {
        boolean z = false;
        try {
            if (UtilString.isNumericDouble(str) && UtilString.isNumericDouble(str2)) {
                if (Double.parseDouble(str) <= Double.parseDouble(str2)) {
                    z = true;
                }
                return z;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static boolean isDiffBetweenDate(DateTime dateTime, DateTime dateTime2, int i) {
        boolean z = false;
        try {
            if (Days.daysBetween((ReadablePartial) dateTime.toLocalDate(), (ReadablePartial) dateTime2.toLocalDate()).getDays() <= i) {
                z = true;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }
}
