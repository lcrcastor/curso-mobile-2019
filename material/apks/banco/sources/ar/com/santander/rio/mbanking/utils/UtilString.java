package ar.com.santander.rio.mbanking.utils;

import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.Patterns;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;

public class UtilString {

    static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String str) {
            super(str);
        }

        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
        }
    }

    public static boolean isNumericDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getUrlString(java.lang.String r10) {
        /*
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            r1.<init>(r10)     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            java.net.URI r10 = new java.net.URI     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            java.lang.String r3 = r1.getProtocol()     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            java.lang.String r4 = r1.getUserInfo()     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            java.lang.String r5 = r1.getHost()     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            int r6 = r1.getPort()     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            java.lang.String r7 = r1.getPath()     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            java.lang.String r8 = r1.getQuery()     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            java.lang.String r1 = r1.getRef()     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            java.lang.String r2 = "UTF-8"
            java.lang.String r9 = java.net.URLEncoder.encode(r1, r2)     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            r2 = r10
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ MalformedURLException | URISyntaxException -> 0x0033, UnsupportedEncodingException -> 0x002f }
            goto L_0x0034
        L_0x002f:
            r10 = move-exception
            r10.printStackTrace()
        L_0x0033:
            r10 = r0
        L_0x0034:
            if (r10 == 0) goto L_0x003a
            java.lang.String r0 = r10.toString()
        L_0x003a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.utils.UtilString.getUrlString(java.lang.String):java.lang.String");
    }

    public static String replaceCharacters(String str, String str2, String str3) {
        return str.replace(str2, str3);
    }

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String getNumberFromString(String str) {
        try {
            if (isNumber(str)) {
                return Integer.toString(Integer.parseInt(str));
            }
        } catch (Exception unused) {
        }
        return str;
    }

    public static String replaceHtmlCharacter(String str) {
        return str != null ? str.toLowerCase().replace("#a", "<").replace("#c", ">").replace(TarjetasConstants.OPEN_HTML_TAG, "<").replace(TarjetasConstants.CLOSE_HTML_TAG, ">") : "";
    }

    public static void stripUnderlines(TextView textView) {
        URLSpan[] uRLSpanArr;
        SpannableString spannableString = new SpannableString(textView.getText());
        for (URLSpan uRLSpan : (URLSpan[]) spannableString.getSpans(0, spannableString.length(), URLSpan.class)) {
            int spanStart = spannableString.getSpanStart(uRLSpan);
            int spanEnd = spannableString.getSpanEnd(uRLSpan);
            spannableString.removeSpan(uRLSpan);
            spannableString.setSpan(new URLSpanNoUnderline(uRLSpan.getURL()), spanStart, spanEnd, 0);
        }
        textView.setText(spannableString);
    }

    public static String capitalize(String str) {
        String[] split;
        if (str == null) {
            return "";
        }
        String lowerCase = str.toLowerCase();
        StringBuffer stringBuffer = new StringBuffer();
        for (String str2 : lowerCase.split(UtilsCuentas.SEPARAOR2)) {
            if (!str2.toString().isEmpty()) {
                char[] charArray = str2.trim().toCharArray();
                charArray[0] = Character.toUpperCase(charArray[0]);
                str2 = new String(charArray);
            }
            stringBuffer.append(str2);
            stringBuffer.append(UtilsCuentas.SEPARAOR2);
        }
        return stringBuffer.toString().trim();
    }

    public static Boolean isEmailValid(String str) {
        return Boolean.valueOf(!TextUtils.isEmpty(str) && Patterns.EMAIL_ADDRESS.matcher(str).matches());
    }
}
