package ar.com.santander.rio.mbanking.utils;

import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.PagoTarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.TransferenciasConstants.TipoCuentaDestino;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class UtilDate {
    public static String getCurrentDateTimeToString(String str) {
        return new SimpleDateFormat(str).format(new Date());
    }

    public static String getDateFormat(String str) {
        return getDateFormat(str, Constants.FORMAT_DATE_WS_1);
    }

    public static String getDateFormat(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }

    public static String getDateFormat(String str, String str2) {
        try {
            return DateTime.parse(str, DateTimeFormat.forPattern(str2)).toString(Constants.FORMAT_DATE_APP);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String getDateFormat(String str, String str2, String str3) {
        try {
            return DateTime.parse(str, DateTimeFormat.forPattern(str2)).toString(str3, Constants.LOCALE_DEFAULT_APP);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String getTimeFormat(String str, String str2) {
        try {
            return DateTime.parse(str, DateTimeFormat.forPattern(str2)).toString(Constants.FORMAT_TIME);
        } catch (Exception unused) {
            return str;
        }
    }

    public static DateTime getDateFromString(String str, String str2) {
        try {
            return DateTime.parse(str, DateTimeFormat.forPattern(str2));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getDateWithNameMonth(String str, String str2) {
        try {
            return DateTime.parse(str, DateTimeFormat.forPattern(str2)).toString(Constants.FORMAT_DATE_NAME_MONTH_APP, Constants.LOCALE_DEFAULT_APP);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String getDateWithFullNameMonth(String str, String str2, String str3) {
        return DateTime.parse(str, DateTimeFormat.forPattern(str2)).toString(str3, Constants.LOCALE_DEFAULT_APP);
    }

    public static boolean isDate(String str, String str2) {
        try {
            DateTime.parse(str, DateTimeFormat.forPattern(str2)).toDate();
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String getDateFormat2(String str) {
        String str2;
        String str3 = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(6, 8));
            sb.append("/");
            sb.append(str.substring(4, 6));
            sb.append("/");
            sb.append(str.substring(0, 4));
            str2 = sb.toString();
        } catch (Exception e) {
            Log.e(PagoTarjetasConstants.ISBAN_DIALOG_ERROR_TITLE, "getDateFormat2: ", e);
            str2 = str3;
        }
        return str2;
    }

    public static String getExpireDateFormat(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(6));
        sb.append("/");
        sb.append(str.substring(4, 6));
        sb.append("/");
        sb.append(str.substring(2, 4));
        return sb.toString();
    }

    public static String getCurrentDate(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(4));
        sb.append(str.substring(2, 4));
        sb.append(str.substring(0, 2));
        return sb.toString();
    }

    public static String getCurrentDateFormat(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 2));
        sb.append("/");
        sb.append(str.substring(2, 4));
        sb.append("/");
        sb.append(str.substring(6));
        return sb.toString();
    }

    public static boolean isDateBefore(Date date, Date date2) {
        return date.before(date2);
    }

    public static boolean isDateAfter(Date date, Date date2) {
        return date.after(date2);
    }

    public static boolean isDateEqual(Date date, Date date2) {
        return date.equals(date2);
    }

    public static Date resetHours(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(14, 0);
        instance.set(13, 0);
        instance.set(12, 0);
        instance.set(11, 0);
        return instance.getTime();
    }

    public static String getMonthFromNumber(String str) {
        if (str.equals("01")) {
            return "ENE";
        }
        if (str.equals("02")) {
            return "FEB";
        }
        if (str.equals("03")) {
            return "MAR";
        }
        if (str.equals("04")) {
            return "ABR";
        }
        if (str.equals("05")) {
            return "MAY";
        }
        if (str.equals(TarjetasConstants.CODIGO_TARJETA_MASTERCARD)) {
            return "JUN";
        }
        if (str.equals(TarjetasConstants.CODIGO_TARJETA_VISA)) {
            return "JUL";
        }
        if (str.equals("08")) {
            return "AGO";
        }
        if (str.equals("09")) {
            return "SEP";
        }
        if (str.equals("10")) {
            return "OCT";
        }
        if (str.equals(TipoCuentaDestino.CAJA_AHORRO_PESOS)) {
            return "NOV";
        }
        return str.equals(TipoCuentaDestino.CAJA_AHORRO_DOLARES) ? "DIC" : "";
    }

    public static long getDifferenceBetweenDates(Date date, Date date2) {
        return (date.getTime() - date2.getTime()) / 86400000;
    }

    public static Boolean isDateWeekend(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(7);
        if (i == 7 || i == 1) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }
}
