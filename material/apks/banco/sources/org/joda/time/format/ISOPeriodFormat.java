package org.joda.time.format;

import android.support.media.ExifInterface;

public class ISOPeriodFormat {
    private static PeriodFormatter a;
    private static PeriodFormatter b;
    private static PeriodFormatter c;
    private static PeriodFormatter d;
    private static PeriodFormatter e;

    protected ISOPeriodFormat() {
    }

    public static PeriodFormatter standard() {
        if (a == null) {
            a = new PeriodFormatterBuilder().appendLiteral("P").appendYears().appendSuffix("Y").appendMonths().appendSuffix("M").appendWeeks().appendSuffix(ExifInterface.LONGITUDE_WEST).appendDays().appendSuffix("D").appendSeparatorIfFieldsAfter("T").appendHours().appendSuffix("H").appendMinutes().appendSuffix("M").appendSecondsWithOptionalMillis().appendSuffix("S").toFormatter();
        }
        return a;
    }

    public static PeriodFormatter alternate() {
        if (b == null) {
            b = new PeriodFormatterBuilder().appendLiteral("P").printZeroAlways().minimumPrintedDigits(4).appendYears().minimumPrintedDigits(2).appendMonths().appendDays().appendSeparatorIfFieldsAfter("T").appendHours().appendMinutes().appendSecondsWithOptionalMillis().toFormatter();
        }
        return b;
    }

    public static PeriodFormatter alternateExtended() {
        if (c == null) {
            c = new PeriodFormatterBuilder().appendLiteral("P").printZeroAlways().minimumPrintedDigits(4).appendYears().appendSeparator("-").minimumPrintedDigits(2).appendMonths().appendSeparator("-").appendDays().appendSeparatorIfFieldsAfter("T").appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSecondsWithOptionalMillis().toFormatter();
        }
        return c;
    }

    public static PeriodFormatter alternateWithWeeks() {
        if (d == null) {
            d = new PeriodFormatterBuilder().appendLiteral("P").printZeroAlways().minimumPrintedDigits(4).appendYears().minimumPrintedDigits(2).appendPrefix(ExifInterface.LONGITUDE_WEST).appendWeeks().appendDays().appendSeparatorIfFieldsAfter("T").appendHours().appendMinutes().appendSecondsWithOptionalMillis().toFormatter();
        }
        return d;
    }

    public static PeriodFormatter alternateExtendedWithWeeks() {
        if (e == null) {
            e = new PeriodFormatterBuilder().appendLiteral("P").printZeroAlways().minimumPrintedDigits(4).appendYears().appendSeparator("-").minimumPrintedDigits(2).appendPrefix(ExifInterface.LONGITUDE_WEST).appendWeeks().appendSeparator("-").appendDays().appendSeparatorIfFieldsAfter("T").appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSecondsWithOptionalMillis().toFormatter();
        }
        return e;
    }
}
