package ar.com.santander.rio.mbanking.app.ui.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseWSResponseHandler;
import ar.com.santander.rio.mbanking.app.ui.activities.PreAutorizacionDebinActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants.PRE_AUTORIZACIONES;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.components.share.OptionsToShareImpl;
import ar.com.santander.rio.mbanking.services.events.AbmPreautorizacionCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.GetDetallePreAutorizacionCompradorEvent;
import ar.com.santander.rio.mbanking.services.events.GetPreAutorizacionesEvent;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeOption;
import ar.com.santander.rio.mbanking.services.events.WebServiceEvent.TypeResult;
import ar.com.santander.rio.mbanking.services.soap.beans.DetallePreAutorizacionBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetDetallePreAutorizacionCompradorResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.AbmPreautorizacionCompradorBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetPreAutorizacionesBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListGroupBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PreAutorizacionesDebinUtil {
    public static void showPreautorizacionesOptions(AppCompatActivity appCompatActivity, List<ListGroupBean> list, int i, final IDialogListener iDialogListener) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (ListGroupBean label : list) {
            arrayList.add(label.getLabel());
        }
        final IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(DebinConstants.POP_UP_PREAUTORIZACIONES, appCompatActivity.getString(R.string.ID_4633_DEBIN_TIT_SELECCIONA_ESTADO), null, arrayList, appCompatActivity.getString(R.string.ID93_ACCOUNTS_CHANGEACC_BTN_CANCEL), null, null, ((ListGroupBean) list.get(i)).getLabel(), arrayList2);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
                iDialogListener.onItemSelected(str);
            }

            public void onPositiveButton() {
                iDialogListener.onPositiveButton();
            }

            public void onNegativeButton() {
                iDialogListener.onNegativeButton();
            }

            public void onSimpleActionButton() {
                newInstance.dismiss();
                iDialogListener.onSimpleActionButton();
            }
        });
        newInstance.show(appCompatActivity.getSupportFragmentManager(), "");
    }

    public static void showDialogConfirmationAbmPreautozicacione(final PreAutorizacionDebinActivity preAutorizacionDebinActivity, final String str, final DetallePreAutorizacionBean detallePreAutorizacionBean) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(DebinConstants.DIALOG_CONFIRMATION_ABM_PREAUTOZICACIONE, preAutorizacionDebinActivity.getString(R.string.USER200044_TITLE), a(preAutorizacionDebinActivity, str), null, null, preAutorizacionDebinActivity.getString(R.string.BTN_POSITIVE_PRE_AUTORIZACION_DEBIN), preAutorizacionDebinActivity.getString(R.string.BTN_NEGATIVE_PRE_AUTORIZACION_DEBIN), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                preAutorizacionDebinActivity.procesarPreAutorizacion(str, detallePreAutorizacionBean);
            }
        });
        newInstance.show(preAutorizacionDebinActivity.getSupportFragmentManager(), DebinConstants.DIALOG_CONFIRMATION_ABM_PREAUTOZICACIONE);
    }

    static String a(Activity activity, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("A", activity.getString(R.string.MSG_USER00522_DEBIN));
        hashMap.put("R", activity.getString(R.string.MSG_USER00523_DEBIN));
        hashMap.put("B", activity.getString(R.string.MSG_USER00524_DEBIN));
        return (String) hashMap.get(str);
    }

    public static String getCodEstadoInTableList(String str, List<ListGroupBean> list) {
        String str2 = "";
        for (int i = 0; i < list.size(); i++) {
            if (((ListGroupBean) list.get(i)).getLabel().equals(str)) {
                return ((ListGroupBean) list.get(i)).getCode();
            }
        }
        return str2;
    }

    public static int getPosEstadoInTableList(String str, List<ListGroupBean> list) {
        Integer valueOf = Integer.valueOf(-1);
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                break;
            } else if (((ListGroupBean) list.get(i)).getLabel().equals(str)) {
                valueOf = Integer.valueOf(i);
                break;
            } else {
                i++;
            }
        }
        return valueOf.intValue();
    }

    public static String getDescIntableList(String str, List<ListGroupBean> list) {
        String str2 = "";
        for (ListGroupBean listGroupBean : list) {
            if (listGroupBean.getCode().equals(str)) {
                return listGroupBean.getDescription();
            }
        }
        return str2;
    }

    public static String gePeriodoIntableList(String str, List<ListGroupBean> list) {
        String str2 = "";
        for (ListGroupBean listGroupBean : list) {
            if (listGroupBean.getCode().equals(str)) {
                return listGroupBean.getCode();
            }
        }
        return str2;
    }

    public static String getLabelIntableList(String str, List<ListGroupBean> list) {
        String str2 = "";
        for (ListGroupBean listGroupBean : list) {
            if (listGroupBean.getCode().equals(str)) {
                return listGroupBean.getLabel();
            }
        }
        return str2;
    }

    public static void onGetPreAutorizacionesResult(PreAutorizacionDebinActivity preAutorizacionDebinActivity, GetPreAutorizacionesEvent getPreAutorizacionesEvent) {
        final GetPreAutorizacionesEvent getPreAutorizacionesEvent2 = getPreAutorizacionesEvent;
        final PreAutorizacionDebinActivity preAutorizacionDebinActivity2 = preAutorizacionDebinActivity;
        AnonymousClass3 r0 = new BaseWSResponseHandler(preAutorizacionDebinActivity, TypeOption.INITIAL_VIEW, PRE_AUTORIZACIONES.CONSULTA_TENENCIA_PRE_AUTORIZACIONES_RECIBIDAS, preAutorizacionDebinActivity) {
            /* access modifiers changed from: protected */
            public void onOk() {
                GetPreAutorizacionesBodyResponseBean preAutorizacionesBodyResponseBean = getPreAutorizacionesEvent2.getResponse().getPreAutorizacionesBodyResponseBean();
                if (getPreAutorizacionesEvent2.getResult().equals(TypeResult.OK)) {
                    preAutorizacionDebinActivity2.consultaPreAutorizaciones(preAutorizacionesBodyResponseBean, Boolean.valueOf(getPreAutorizacionesEvent2.isUpdate()));
                }
            }
        };
        r0.handleWSResponse(getPreAutorizacionesEvent);
    }

    public static void onGetDetallePreAutorizacionCompradorResult(PreAutorizacionDebinActivity preAutorizacionDebinActivity, GetDetallePreAutorizacionCompradorEvent getDetallePreAutorizacionCompradorEvent) {
        final GetDetallePreAutorizacionCompradorEvent getDetallePreAutorizacionCompradorEvent2 = getDetallePreAutorizacionCompradorEvent;
        final PreAutorizacionDebinActivity preAutorizacionDebinActivity2 = preAutorizacionDebinActivity;
        AnonymousClass4 r0 = new BaseWSResponseHandler(preAutorizacionDebinActivity, TypeOption.INITIAL_VIEW, PRE_AUTORIZACIONES.DETALLE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT, preAutorizacionDebinActivity) {
            /* access modifiers changed from: protected */
            public void onOk() {
                GetDetallePreAutorizacionCompradorResponseBean getDetallePreAutorizacionCompradorResponseBean = (GetDetallePreAutorizacionCompradorResponseBean) getDetallePreAutorizacionCompradorEvent2.getBeanResponse();
                if (getDetallePreAutorizacionCompradorEvent2.getResult().equals(TypeResult.OK) && getDetallePreAutorizacionCompradorResponseBean != null) {
                    preAutorizacionDebinActivity2.irAlFragmentDetallePreAutorizacionComprador(getDetallePreAutorizacionCompradorResponseBean.getGetDetallePreAutorizacionCompradorBodyResponseBean());
                }
            }
        };
        r0.handleWSResponse(getDetallePreAutorizacionCompradorEvent);
    }

    public static void onAbmPreAutorizacioneCompradorResult(PreAutorizacionDebinActivity preAutorizacionDebinActivity, AbmPreautorizacionCompradorEvent abmPreautorizacionCompradorEvent, String str, DetallePreAutorizacionBean detallePreAutorizacionBean) {
        final AbmPreautorizacionCompradorEvent abmPreautorizacionCompradorEvent2 = abmPreautorizacionCompradorEvent;
        final PreAutorizacionDebinActivity preAutorizacionDebinActivity2 = preAutorizacionDebinActivity;
        final DetallePreAutorizacionBean detallePreAutorizacionBean2 = detallePreAutorizacionBean;
        final String str2 = str;
        AnonymousClass5 r0 = new BaseWSResponseHandler(preAutorizacionDebinActivity, TypeOption.INITIAL_VIEW, PRE_AUTORIZACIONES.DETALLE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT, preAutorizacionDebinActivity) {
            /* access modifiers changed from: protected */
            public void onOk() {
                AbmPreautorizacionCompradorBodyResponseBean abmPreautorizacionCompradorBodyResponseBean = abmPreautorizacionCompradorEvent2.getResponse().abmPreautorizacionCompradorBodyResponseBean;
                preAutorizacionDebinActivity2.m1irAComprobanteDeOperacin(abmPreautorizacionCompradorBodyResponseBean.getNroComprobante(), abmPreautorizacionCompradorBodyResponseBean.getFechaOperacion(), abmPreautorizacionCompradorBodyResponseBean.getLeyendaComp(), detallePreAutorizacionBean2, str2);
            }
        };
        r0.handleWSResponse(abmPreautorizacionCompradorEvent);
    }

    public static IsbanDialogFragment getIsbanDialogToShared(AppCompatActivity appCompatActivity, String str, View view, String str2) {
        final AppCompatActivity appCompatActivity2 = appCompatActivity;
        final View view2 = view;
        final AppCompatActivity appCompatActivity3 = appCompatActivity2;
        final String str3 = str2;
        final String str4 = str;
        final AnonymousClass6 r0 = new OptionsToShareImpl(appCompatActivity2, appCompatActivity2, appCompatActivity.getSupportFragmentManager()) {
            public View getViewToShare() {
                return view2;
            }

            public void receiveIntentAppShare(Intent intent) {
                appCompatActivity3.startActivity(Intent.createChooser(intent, appCompatActivity3.getString(R.string.IDXX_SHARE_OPTION_RECEIPT)));
            }

            public String getFileName() {
                return Html.fromHtml(str3.concat(str4)).toString();
            }

            public String getSubjectReceiptToShare() {
                String str = str3;
                StringBuilder sb = new StringBuilder();
                sb.append(" - ");
                sb.append(str4);
                return Html.fromHtml(str.concat(sb.toString())).toString();
            }

            public void optionDownloadSelected() {
                super.optionDownloadSelected();
            }

            public void optionShareSelected() {
                super.optionShareSelected();
            }

            public void onAbortShare() {
                super.onAbortShare();
                appCompatActivity3.onBackPressed();
            }
        };
        ArrayList arrayList = new ArrayList();
        arrayList.add(appCompatActivity.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT));
        arrayList.add(appCompatActivity.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD));
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance("mPopupMenu", appCompatActivity.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_TITLE), null, arrayList, appCompatActivity2.getString(R.string.ID_4109_SEGUROS_LBL_CANCELAR), null, null, null, arrayList);
        newInstance.setDialogListener(new IDialogListener() {
            public void onNegativeButton() {
            }

            public void onPositiveButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onItemSelected(String str) {
                if (str.equalsIgnoreCase(appCompatActivity2.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT))) {
                    r0.optionShareSelected();
                } else if (str.equalsIgnoreCase(appCompatActivity2.getResources().getString(R.string.IDXX_SHARE_OPTION_RECEIPT_DOWNLOAD))) {
                    r0.optionDownloadSelected();
                }
            }
        });
        newInstance.setCancelable(true);
        return newInstance;
    }
}
