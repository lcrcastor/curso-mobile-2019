package ar.com.santander.rio.mbanking.app.commons.accessibilty;

import android.content.Context;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;

public class CAccessibility {
    static String a = "un";
    private Context b;

    public CAccessibility(Context context) {
        this.b = context;
    }

    public static CAccessibility getInstance(Context context) {
        return new CAccessibility(context);
    }

    public static void sendAccessibilityEvent(Context context, String str) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getApplicationContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(16384);
            obtain.getText().add(str);
            accessibilityManager.sendAccessibilityEvent(obtain);
        }
    }

    public static String applyFilterMaskVinculo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Vínculo ");
        sb.append(str);
        return sb.toString();
    }

    public static String applyFilterMaskInterruptor(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Interruptor ");
        sb.append(str);
        return sb.toString();
    }

    public static String applyFilterMaskSelector(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Selector ");
        sb.append(str);
        return sb.toString();
    }

    public static String apllyFilterStores(String str) {
        return str.replace("Loc.", "Local");
    }

    public static String applyFilterMaskBotton(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Botón ");
        sb.append(str);
        return sb.toString();
    }

    public String applyFilterFragmentDialog(String str) {
        return applyFilterGeneral(new CFiltersAccessibility(this.b).filterRenovCapEInt(str));
    }

    public String applyFilterHS(String str) {
        return new CFiltersAccessibility(this.b).filterHs(str);
    }

    public String applyCFTEA(String str) {
        return new CFiltersAccessibility(this.b).filterCTFNA(str);
    }

    public String applyCFTEA(String str, String str2) {
        return new CFiltersAccessibility(this.b).filterCTFNA(str, str2);
    }

    public String applyFilterCUIT(String str) {
        return new CFiltersAccessibility(this.b).filterCUIT(str);
    }

    public String applyGuion(String str) {
        return new CFiltersAccessibility(this.b).filterGuion(str);
    }

    public String applyGuionLiteral(String str) {
        return new CFiltersAccessibility(this.b).filterGuionDescription(str);
    }

    public String applyYbarraO(String str) {
        return new CFiltersAccessibility(this.b).filter_OY(str);
    }

    public String applyShorcuts(String str) {
        return new CFiltersAccessibility(this.b).filterShorcuts(str);
    }

    public String applyCFTN_IVA(String str, String str2) {
        return new CFiltersAccessibility(this.b).filterCTFNA(str, str2);
    }

    public String applyCBU(String str) {
        return new CFiltersAccessibility(this.b).filterCBU(str);
    }

    public String applyOperador(String str) {
        return new CFiltersAccessibility(this.b).filterOperador(str);
    }

    public String applyVos(String str) {
        return new CFiltersAccessibility(this.b).filterUsuario(str);
    }

    public String applyEditText(String str) {
        return new CFiltersAccessibility(this.b).filterCuadroDeTexto(str);
    }

    public String filterCBUCVU(String str) {
        return new CFiltersAccessibility(this.b).filterCBUCVU(str);
    }

    public String filterTabNuevoDestinatarioCBUCVU(String str) {
        return new CFiltersAccessibility(this.b).filterTabNuevoDestinatarioCBUCVU(str);
    }

    public String applyFilterNumeroGradoBarra(String str) {
        return new CFiltersAccessibility(this.b).filterNumeroGradoConBarra(str);
    }

    public String applyFilterSiglasTelefono(String str) {
        return new CFiltersAccessibility(this.b).filter_Tel(str);
    }

    public String applyFilter_BsAs(String str) {
        return new CFiltersAccessibility(this.b).filter_BsAs(str);
    }

    public String applyFilter_Ciudad(String str) {
        return new CFiltersAccessibility(this.b).filter_Ciudad(str);
    }

    public String applyFilter_CABA(String str) {
        return new CFiltersAccessibility(this.b).filterCABA(str);
    }

    public String applyFilter_Email(String str) {
        return new CFiltersAccessibility(this.b).filter_Email(str);
    }

    public String applyFilterPagina(String str) {
        return new CFiltersAccessibility(this.b).filter_paginas(str);
    }

    public String applyFilter_IGJ(String str) {
        return new CFiltersAccessibility(this.b).filter_IGJ(str);
    }

    public String applyFilter_pisos(String str) {
        return new CFiltersAccessibility(this.b).filter_pisos(str);
    }

    public String applyFilterAlertDialog(String str) {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.b);
        return cFiltersAccessibility.filterPesos(cFiltersAccessibility.filterTimeAvailability(cFiltersAccessibility.filterTelephone(str)));
    }

    public String applyFilterTelephoneMask(String str) {
        return new CFiltersAccessibility(this.b).filterTelephone(str);
    }

    public String applyCFTEA_IVA_2(String str) {
        return new CFiltersAccessibility(this.b).filterCTFNA_IVA(str);
    }

    public String applyCFTEA_IVA_3(String str) {
        return new CFiltersAccessibility(this.b).filterCTFNACSImpuesto(str);
    }

    public String applyFilterDireccion(String str) {
        return new CFiltersAccessibility(this.b).filterDireccion(str);
    }

    public String applyFilterTimeAvailability(String str) {
        return new CFiltersAccessibility(this.b).filterTimeAvailability(str);
    }

    public String applyFilterDate(String str) {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.b);
        return cFiltersAccessibility.filterDateText(cFiltersAccessibility.filterDateFormatShortYear(cFiltersAccessibility.filterDateEmpty(str)));
    }

    public String applyFilterDateInText(String str) {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.b);
        return cFiltersAccessibility.filterDateInText(cFiltersAccessibility.filterDateFormatShortYear(cFiltersAccessibility.filterDateEmpty(str)));
    }

    public String applyFilterDateHour(String str) {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.b);
        return cFiltersAccessibility.filterDateText(cFiltersAccessibility.filterHs(cFiltersAccessibility.guionFilter(cFiltersAccessibility.filterDateFormatShortYear(cFiltersAccessibility.filterDateEmpty(str)))));
    }

    public String applyFilterDateTime(String str) {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.b);
        return cFiltersAccessibility.filterDateTimeText(cFiltersAccessibility.filterDateFormatShortYear(cFiltersAccessibility.filterDateEmpty(str)));
    }

    public String applyFilterAmount(String str) {
        return new CFiltersAccessibility(this.b).filterAmount(str);
    }

    public String applyFilterAccount(String str) {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.b);
        return cFiltersAccessibility.filterAccountsNumber(cFiltersAccessibility.filterSymbolAccounts(str));
    }

    public String applyFilterAccountName(String str) {
        return new CFiltersAccessibility(this.b).filterSymbolAccounts(str);
    }

    public String applyFilterTime(String str) {
        return new CFiltersAccessibility(this.b).filterBetweenTimeFormat(str);
    }

    public String applyFilterTasaInteres(String str) {
        return new CFiltersAccessibility(this.b).filterTasaInteres(str);
    }

    public String applyFilterTasaValue(String str) {
        new CFiltersAccessibility(this.b);
        return CFiltersAccessibility.filterTasaValue(str);
    }

    public String applyFilterSellado(String str) {
        return new CFiltersAccessibility(this.b).filterSellado(str);
    }

    public String applyFilterPhoneNumber(String str) {
        return new CFiltersAccessibility(this.b).filterPhoneNumber(str);
    }

    public String applyFilterAcronymTasas(String str, String str2) {
        return new CFiltersAccessibility(this.b).filter_acronym_Tasas(str, str2);
    }

    public String applyFilterAcronymTasas(String str) {
        return new CFiltersAccessibility(this.b).filter_acronym_Tasas(str);
    }

    public String applyFilterGuion(String str) {
        return new CFiltersAccessibility(this.b).guionFilter(str);
    }

    public String applyFilterShortCutBco(String str) {
        return new CFiltersAccessibility(this.b).bcoFilter(str);
    }

    public String applyFilterShortCutCta(String str) {
        return new CFiltersAccessibility(this.b).ctaFilter(str);
    }

    public String applyFilterShortCutEmail(String str) {
        return new CFiltersAccessibility(this.b).emailFilter(str);
    }

    public String applyFilterAdministracion(String str) {
        return new CFiltersAccessibility(this.b).administracionFilter(str);
    }

    public String applyFilterLiteralConceptoTransf(String str) {
        return new CFiltersAccessibility(this.b).LiteralConceptoTransfFilter(str);
    }

    public String applyFilterCantidadDeCuotas(String str) {
        return new CFiltersAccessibility(this.b).CantidadDeCuotasFilter(str);
    }

    public String applyFilterCharacterToCharacter(String str) {
        return new CFiltersAccessibility(this.b).filterChacterToCharacter(str);
    }

    public String applyFilterCharacterToCharacterSpecifict(String str) {
        return new CFiltersAccessibility(this.b).filterCharacterToCharacterSpecifict(str);
    }

    public String applyFilterControlNumber(String str) {
        return new CFiltersAccessibility(this.b).filterControlNumber(str);
    }

    public String applyFilterDistance(String str) {
        return new CFiltersAccessibility(this.b).filterDistance(str);
    }

    public String applyFilterXboxConsole(String str) {
        return new CFiltersAccessibility(this.b).xboxConsole(str);
    }

    public String applyFilterGeneral(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.b);
        return cFiltersAccessibility.filterStringComun(cFiltersAccessibility.filter_paginas(applyGuion(cFiltersAccessibility.filterHs(applyFilterLiteralConceptoTransf(cFiltersAccessibility.filterCodigoPostal(cFiltersAccessibility.filterCurrency(cFiltersAccessibility.filterSellado(cFiltersAccessibility.filterMnemonicos(applyFilterDate(cFiltersAccessibility.filterTalbackUnifications(applyFilterTime(cFiltersAccessibility.filterAmount(applyFilterXboxConsole(applyFilterShortCutCta(applyFilterAccount(applyFilterShortCutBco(applyFilterAdministracion(applyFilterShortCutEmail(str)))))))))))))))))));
    }

    public String applyCTFEA(String str) {
        return new CFiltersAccessibility(this.b).filterCTFNA(str);
    }

    public String applyCFTEA_IMP(String str, String str2) {
        return new CFiltersAccessibility(this.b).filterCTFEA_IMP(str, str2);
    }

    public String applyDeCuenta(String str, String str2) {
        CAccessibility cAccessibility = new CAccessibility(this.b);
        try {
            cAccessibility.applyFilterAccount(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cAccessibility.applyFilterAmount(str2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return applyDeCuenta(str, str2);
    }

    public String applyImport(String str, String str2) {
        return new CFiltersAccessibility(this.b).filterImport(str, str2);
    }

    public String applyNumberCharToChar(String str) {
        return new CFiltersAccessibility(this.b).filterNumberToNumber(str);
    }

    public String applyFilterCreditCard(String str) {
        CFiltersAccessibility cFiltersAccessibility = new CFiltersAccessibility(this.b);
        return cFiltersAccessibility.filterMnemonicos(cFiltersAccessibility.scapeOneCharacter(cFiltersAccessibility.scapeOneCharacter(cFiltersAccessibility.filterScapeCharacter(cFiltersAccessibility.filterNumberToNumber(str.toLowerCase())), "x"), "*"));
    }

    public String applyFilterMaskAccount(String str) {
        return new CFiltersAccessibility(this.b).filterMaskAccount(str);
    }

    public String applyFilterNumberOne(String str) {
        new CFiltersAccessibility(this.b);
        try {
            return Integer.valueOf(str).intValue() == 1 ? a : str;
        } catch (Exception unused) {
            return str;
        }
    }

    public String applyFilterDebinPreAutorizacion(String str) {
        return str.replace("-", UtilsCuentas.SEPARAOR2);
    }

    /* renamed from: applyFilterSSNNúmero reason: contains not printable characters */
    public String m0applyFilterSSNNmero(String str) {
        return new CFiltersAccessibility(this.b).filterSSNNumero(str);
    }
}
