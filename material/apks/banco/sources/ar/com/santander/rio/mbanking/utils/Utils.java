package ar.com.santander.rio.mbanking.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import ar.com.santander.rio.mbanking.app.ui.activities.EditarCelularActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.components.mapInfo.MyMarker;
import ar.com.santander.rio.mbanking.services.model.general.Tarjeta;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static class Documento {

        public static class CUIT {
            private static final List<String> a = new ArrayList<String>() {
                {
                    add("20");
                    add("23");
                    add("24");
                    add("27");
                    add("30");
                    add("33");
                    add("34");
                    add("50");
                    add("55");
                }
            };
            private static final List<Integer> b = new ArrayList<Integer>() {
                {
                    add(Integer.valueOf(5));
                    add(Integer.valueOf(4));
                    add(Integer.valueOf(3));
                    add(Integer.valueOf(2));
                    add(Integer.valueOf(7));
                    add(Integer.valueOf(6));
                    add(Integer.valueOf(5));
                    add(Integer.valueOf(4));
                    add(Integer.valueOf(3));
                    add(Integer.valueOf(2));
                    add(Integer.valueOf(1));
                }
            };

            public static String format(String str) {
                String str2;
                String str3 = "";
                try {
                    if (TextUtils.isEmpty(str)) {
                        return str3;
                    }
                    String replaceFirst = str.replaceFirst("^0+(?!$)", "");
                    if (TextUtils.isEmpty(replaceFirst)) {
                        return str3;
                    }
                    String replaceAll = replaceFirst.replaceAll("[^\\d]", "");
                    if (replaceAll != null) {
                        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                        decimalFormatSymbols.setDecimalSeparator(',');
                        decimalFormatSymbols.setGroupingSeparator('.');
                        str2 = String.format("%s-%s-%s", new Object[]{replaceAll.substring(0, 2), replaceAll.substring(2, replaceAll.length() - 1), replaceAll.substring(replaceAll.length() - 1, replaceAll.length())});
                    } else {
                        str2 = str3;
                    }
                    return str2;
                } catch (Exception e) {
                    e.fillInStackTrace();
                    return str3;
                }
            }

            private static Boolean a(String str) {
                Boolean valueOf = Boolean.valueOf(false);
                if (TextUtils.isEmpty(str)) {
                    return valueOf;
                }
                for (String equals : a) {
                    if (equals.equals(str)) {
                        return Boolean.valueOf(true);
                    }
                }
                return valueOf;
            }

            private static Boolean a(String str, ArrayList<String> arrayList) {
                Boolean valueOf = Boolean.valueOf(false);
                if (TextUtils.isEmpty(str)) {
                    return valueOf;
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals(str)) {
                        return Boolean.valueOf(true);
                    }
                }
                return valueOf;
            }

            private static Boolean b(String str) {
                boolean z = false;
                Integer valueOf = Integer.valueOf(0);
                for (int i = 0; i < str.length(); i++) {
                    valueOf = Integer.valueOf(valueOf.intValue() + (Integer.parseInt(Character.toString(str.charAt(i))) * ((Integer) b.get(i)).intValue()));
                }
                if (valueOf.intValue() % 11 == 0) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }

            public static Boolean isValid(String str) {
                String replaceAll = str.replaceAll("[^\\d]", "");
                boolean z = false;
                if (replaceAll.length() != 11) {
                    return Boolean.valueOf(false);
                }
                String substring = replaceAll.substring(0, 2);
                replaceAll.substring(2, 10);
                replaceAll.substring(10, 11);
                if (a(substring).booleanValue() && b(replaceAll).booleanValue()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }

            public static Boolean isValid(String str, String str2, String str3) {
                String replaceAll = str.replaceAll("[^\\d]", "");
                boolean z = false;
                if (replaceAll.length() != Integer.valueOf(str2).intValue()) {
                    return Boolean.valueOf(false);
                }
                String substring = replaceAll.substring(0, 2);
                replaceAll.substring(2, 10);
                replaceAll.substring(10, 11);
                if (a(substring, new ArrayList(Arrays.asList(str3.split("\\s*,\\s*")))).booleanValue() && b(replaceAll).booleanValue()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        }

        public static class DNI {
            public static String mask(String str) {
                String format = format(str);
                String replaceAll = format.substring(0, format.length() - 3).replaceAll("\\d", "X");
                String substring = format.substring(format.length() - 3);
                StringBuilder sb = new StringBuilder();
                sb.append(replaceAll);
                sb.append(substring);
                return sb.toString();
            }

            public static String format(String str) {
                String str2 = "";
                String replaceAll = str.replaceAll("[^\\d]", "");
                if (replaceAll == null) {
                    return str2;
                }
                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                decimalFormatSymbols.setDecimalSeparator(',');
                decimalFormatSymbols.setGroupingSeparator('.');
                return new DecimalFormat("###,###", decimalFormatSymbols).format(Long.parseLong(replaceAll));
            }
        }

        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.lang.String format(java.lang.String r1, java.lang.String r2) {
            /*
                int r0 = r1.hashCode()
                switch(r0) {
                    case -1262221469: goto L_0x00f4;
                    case -1220802826: goto L_0x00ea;
                    case -1017102889: goto L_0x00e0;
                    case -426501744: goto L_0x00d5;
                    case 67: goto L_0x00cb;
                    case 68: goto L_0x00c0;
                    case 69: goto L_0x00b6;
                    case 70: goto L_0x00ab;
                    case 73: goto L_0x00a0;
                    case 76: goto L_0x0095;
                    case 77: goto L_0x0089;
                    case 78: goto L_0x007e;
                    case 80: goto L_0x0072;
                    case 84: goto L_0x0066;
                    case 88: goto L_0x005b;
                    case 66568: goto L_0x004f;
                    case 67839: goto L_0x0044;
                    case 2080021: goto L_0x0038;
                    case 2080029: goto L_0x002c;
                    case 1036451775: goto L_0x0021;
                    case 1336658757: goto L_0x0015;
                    case 1853983832: goto L_0x0009;
                    default: goto L_0x0007;
                }
            L_0x0007:
                goto L_0x00ff
            L_0x0009:
                java.lang.String r0 = "CÉDULA MILITAR"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 17
                goto L_0x0100
            L_0x0015:
                java.lang.String r0 = "PASAPORTE"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 13
                goto L_0x0100
            L_0x0021:
                java.lang.String r0 = "LIBRETA DE ENROLAMIENTO"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 1
                goto L_0x0100
            L_0x002c:
                java.lang.String r0 = "CUIT"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 9
                goto L_0x0100
            L_0x0038:
                java.lang.String r0 = "CUIL"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 11
                goto L_0x0100
            L_0x0044:
                java.lang.String r0 = "DNI"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 5
                goto L_0x0100
            L_0x004f:
                java.lang.String r0 = "CDI"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 19
                goto L_0x0100
            L_0x005b:
                java.lang.String r0 = "X"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 6
                goto L_0x0100
            L_0x0066:
                java.lang.String r0 = "T"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 8
                goto L_0x0100
            L_0x0072:
                java.lang.String r0 = "P"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 12
                goto L_0x0100
            L_0x007e:
                java.lang.String r0 = "N"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 4
                goto L_0x0100
            L_0x0089:
                java.lang.String r0 = "M"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 16
                goto L_0x0100
            L_0x0095:
                java.lang.String r0 = "L"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 10
                goto L_0x0100
            L_0x00a0:
                java.lang.String r0 = "I"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 14
                goto L_0x0100
            L_0x00ab:
                java.lang.String r0 = "F"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 20
                goto L_0x0100
            L_0x00b6:
                java.lang.String r0 = "E"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 0
                goto L_0x0100
            L_0x00c0:
                java.lang.String r0 = "D"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 18
                goto L_0x0100
            L_0x00cb:
                java.lang.String r0 = "C"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 2
                goto L_0x0100
            L_0x00d5:
                java.lang.String r0 = "CERTIFICADO INTERNACIONAL"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 21
                goto L_0x0100
            L_0x00e0:
                java.lang.String r0 = "DNI EXTRANJERO"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 7
                goto L_0x0100
            L_0x00ea:
                java.lang.String r0 = "LIBRETA CÍVICA"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 3
                goto L_0x0100
            L_0x00f4:
                java.lang.String r0 = "CÉDULA DE IDENTIDAD"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 15
                goto L_0x0100
            L_0x00ff:
                r1 = -1
            L_0x0100:
                switch(r1) {
                    case 0: goto L_0x0109;
                    case 1: goto L_0x0109;
                    case 2: goto L_0x0109;
                    case 3: goto L_0x0109;
                    case 4: goto L_0x0109;
                    case 5: goto L_0x0109;
                    case 6: goto L_0x0109;
                    case 7: goto L_0x0109;
                    case 8: goto L_0x0104;
                    case 9: goto L_0x0104;
                    case 10: goto L_0x0104;
                    case 11: goto L_0x0104;
                    default: goto L_0x0103;
                }
            L_0x0103:
                return r2
            L_0x0104:
                java.lang.String r1 = ar.com.santander.rio.mbanking.utils.Utils.Documento.CUIT.format(r2)
                return r1
            L_0x0109:
                java.lang.String r1 = ar.com.santander.rio.mbanking.utils.Utils.Documento.DNI.format(r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.utils.Utils.Documento.format(java.lang.String, java.lang.String):java.lang.String");
        }

        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.lang.String mask(java.lang.String r1, java.lang.String r2) {
            /*
                int r0 = r1.hashCode()
                switch(r0) {
                    case -1262221469: goto L_0x00f4;
                    case -1220802826: goto L_0x00ea;
                    case -1017102889: goto L_0x00e0;
                    case -426501744: goto L_0x00d5;
                    case 67: goto L_0x00cb;
                    case 68: goto L_0x00c0;
                    case 69: goto L_0x00b6;
                    case 70: goto L_0x00ab;
                    case 73: goto L_0x00a0;
                    case 76: goto L_0x0095;
                    case 77: goto L_0x0089;
                    case 78: goto L_0x007e;
                    case 80: goto L_0x0072;
                    case 84: goto L_0x0066;
                    case 88: goto L_0x005b;
                    case 66568: goto L_0x004f;
                    case 67839: goto L_0x0044;
                    case 2080021: goto L_0x0038;
                    case 2080029: goto L_0x002c;
                    case 1036451775: goto L_0x0021;
                    case 1336658757: goto L_0x0015;
                    case 1853983832: goto L_0x0009;
                    default: goto L_0x0007;
                }
            L_0x0007:
                goto L_0x00ff
            L_0x0009:
                java.lang.String r0 = "CÉDULA MILITAR"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 17
                goto L_0x0100
            L_0x0015:
                java.lang.String r0 = "PASAPORTE"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 13
                goto L_0x0100
            L_0x0021:
                java.lang.String r0 = "LIBRETA DE ENROLAMIENTO"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 1
                goto L_0x0100
            L_0x002c:
                java.lang.String r0 = "CUIT"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 9
                goto L_0x0100
            L_0x0038:
                java.lang.String r0 = "CUIL"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 11
                goto L_0x0100
            L_0x0044:
                java.lang.String r0 = "DNI"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 5
                goto L_0x0100
            L_0x004f:
                java.lang.String r0 = "CDI"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 19
                goto L_0x0100
            L_0x005b:
                java.lang.String r0 = "X"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 6
                goto L_0x0100
            L_0x0066:
                java.lang.String r0 = "T"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 8
                goto L_0x0100
            L_0x0072:
                java.lang.String r0 = "P"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 12
                goto L_0x0100
            L_0x007e:
                java.lang.String r0 = "N"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 4
                goto L_0x0100
            L_0x0089:
                java.lang.String r0 = "M"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 16
                goto L_0x0100
            L_0x0095:
                java.lang.String r0 = "L"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 10
                goto L_0x0100
            L_0x00a0:
                java.lang.String r0 = "I"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 14
                goto L_0x0100
            L_0x00ab:
                java.lang.String r0 = "F"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 20
                goto L_0x0100
            L_0x00b6:
                java.lang.String r0 = "E"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 0
                goto L_0x0100
            L_0x00c0:
                java.lang.String r0 = "D"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 18
                goto L_0x0100
            L_0x00cb:
                java.lang.String r0 = "C"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 2
                goto L_0x0100
            L_0x00d5:
                java.lang.String r0 = "CERTIFICADO INTERNACIONAL"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 21
                goto L_0x0100
            L_0x00e0:
                java.lang.String r0 = "DNI EXTRANJERO"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 7
                goto L_0x0100
            L_0x00ea:
                java.lang.String r0 = "LIBRETA CÍVICA"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 3
                goto L_0x0100
            L_0x00f4:
                java.lang.String r0 = "CÉDULA DE IDENTIDAD"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L_0x00ff
                r1 = 15
                goto L_0x0100
            L_0x00ff:
                r1 = -1
            L_0x0100:
                switch(r1) {
                    case 0: goto L_0x0104;
                    case 1: goto L_0x0104;
                    case 2: goto L_0x0104;
                    case 3: goto L_0x0104;
                    case 4: goto L_0x0104;
                    case 5: goto L_0x0104;
                    case 6: goto L_0x0104;
                    case 7: goto L_0x0104;
                    default: goto L_0x0103;
                }
            L_0x0103:
                return r2
            L_0x0104:
                java.lang.String r1 = ar.com.santander.rio.mbanking.utils.Utils.Documento.DNI.mask(r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.utils.Utils.Documento.mask(java.lang.String, java.lang.String):java.lang.String");
        }
    }

    public static void centerMapInPositionWithMarkersBounds(GoogleMap googleMap, HashMap<Marker, MyMarker> hashMap, LatLng latLng) {
    }

    public static String formatDist(float f) {
        if (f >= 1000.0f) {
            float f2 = f / 1000.0f;
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%.1f", new Object[]{Float.valueOf(f2)}));
            sb.append(" km.");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append((int) f);
        sb2.append(" mts.");
        return sb2.toString();
    }

    public static void centerMapWithMarkersBounds(GoogleMap googleMap, HashMap<Marker, MyMarker> hashMap, LatLng latLng) {
        Builder builder = new Builder();
        for (Marker position : hashMap.keySet()) {
            builder.include(position.getPosition());
        }
        builder.include(latLng);
        CameraUpdate newLatLngBounds = CameraUpdateFactory.newLatLngBounds(builder.build(), 100);
        googleMap.moveCamera(newLatLngBounds);
        googleMap.animateCamera(newLatLngBounds);
    }

    public static void setListViewHeight(ListView listView, int i, int i2, Context context) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter != null) {
            int paddingTop = listView.getPaddingTop() + listView.getPaddingBottom() + (listView.getDividerHeight() * (adapter.getCount() - 1)) + (dpToPx(i, context) * i2);
            LayoutParams layoutParams = listView.getLayoutParams();
            layoutParams.height = paddingTop;
            listView.setLayoutParams(layoutParams);
        }
    }

    public static int dpToPx(int i, Context context) {
        return (int) (((double) (((float) i) * context.getResources().getDisplayMetrics().density)) + 0.5d);
    }

    public static int pxToDp(int i, Context context) {
        return (int) (((double) (((float) i) / context.getResources().getDisplayMetrics().density)) + 0.5d);
    }

    public static String formatIsbanHTMLCode(String str) {
        return str != null ? str.replaceAll(TarjetasConstants.OPEN_HTML_TAG, "<").replaceAll("#a", "<").replaceAll(TarjetasConstants.CLOSE_HTML_TAG, ">").replaceAll("#c", ">") : "";
    }

    public static int dpToPx(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }
        return getDisplayMetrics(context).heightPixels;
    }

    public static int getScreenHeightSizeForContent(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            return getDisplayMetrics(context).heightPixels - dpToPx(context, 81.0f);
        } catch (Exception e) {
            Log.e("Utils ERROR", "getScreenHeightSizeForContent: ", e);
            return 0;
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static boolean isConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String mascaraTarjeta(Tarjeta tarjeta) {
        String str = "";
        if (tarjeta.getTipo().equals(TarjetasConstants.CODIGO_TARJETA_VISA)) {
            if (tarjeta.getClase().equals("T")) {
                StringBuilder sb = new StringBuilder();
                sb.append(TarjetasConstants.VISAR_CARD_MASK);
                sb.append(tarjeta.getNroTarjetaCredito());
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(TarjetasConstants.VISA_CARD_MASK);
            sb2.append(tarjeta.getNroTarjetaCredito());
            return sb2.toString();
        } else if (tarjeta.getTipo().equals(TarjetasConstants.CODIGO_TARJETA_AMEX)) {
            if (tarjeta.getNroTarjetaCredito().charAt(tarjeta.getNroTarjetaCredito().length() - 1) == '0') {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(TarjetasConstants.AMEX_CARD_MASK);
                sb3.append(tarjeta.getNroTarjetaCredito());
                return sb3.toString();
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(TarjetasConstants.AMEX_CARD_MASK);
            sb4.append(tarjeta.getNroTarjetaCredito());
            return sb4.toString();
        } else if (!tarjeta.getTipo().equals("05")) {
            return str;
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(TarjetasConstants.DEBITO_CARD_MASK);
            sb5.append(tarjeta.getNroTarjetaDebito());
            return sb5.toString();
        }
    }

    public static String getDescTipoTarjeta(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.equals(TarjetasConstants.CODIGO_TARJETA_VISA)) {
            return TarjetasConstants.DESC_TARJETA_VISA;
        }
        if (str.equals(TarjetasConstants.CODIGO_TARJETA_AMEX)) {
            return TarjetasConstants.DESC_TARJETA_AMEX;
        }
        if (str.equals("05")) {
            return TarjetasConstants.DESC_TARJETA_DEBITO;
        }
        return null;
    }

    public static String mascaraTarjetaLabel(Tarjeta tarjeta) {
        String str = "";
        if (tarjeta == null) {
            return str;
        }
        String mascaraTarjeta = mascaraTarjeta(tarjeta);
        if (mascaraTarjeta == null || mascaraTarjeta.length() <= 0) {
            return mascaraTarjeta;
        }
        String replace = mascaraTarjeta.toUpperCase().replace(TarjetasConstants.MARCA_AMEX.toUpperCase(), TarjetasConstants.MARCA_AMEX_CODIF.toUpperCase()).replace(TarjetasConstants.CCARD_MASK, TarjetasConstants.CCARD_MASK_LABEL);
        String[] split = replace.split("-");
        if (split != null && split.length == 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(split[0]);
            sb.append(split[1]);
            replace = sb.toString();
        }
        return replace.toUpperCase().replace(TarjetasConstants.MARCA_AMEX_CODIF.toUpperCase(), TarjetasConstants.MARCA_AMEX.toUpperCase());
    }

    public static boolean isAdicional(Tarjeta tarjeta) {
        return tarjeta.getCodigoTitularidad().equals("AD");
    }

    public static boolean isRecargable(Tarjeta tarjeta) {
        return tarjeta.getClase().equals("T");
    }

    public static String getPhoneFormatForService(String str) {
        String str2 = "";
        String[] split = str.split("-");
        if (split.length <= 1) {
            return str2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(split[0].replaceFirst(EditarCelularActivity.PREFIX_COD_AREA, ""));
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("-");
        sb3.append(split[1].replaceFirst(EditarCelularActivity.PREFIX_NUMERO, ""));
        return sb3.toString();
    }

    public static String getDecimalGroupingFormat(String str) {
        try {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("es", "AR"));
            decimalFormat.applyPattern("###,###");
            return decimalFormat.format(Double.parseDouble(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
