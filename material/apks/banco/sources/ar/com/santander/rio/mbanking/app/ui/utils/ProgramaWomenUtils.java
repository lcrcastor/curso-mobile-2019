package ar.com.santander.rio.mbanking.app.ui.utils;

import android.app.Activity;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.activities.ProgramaWomenActivity;
import ar.com.santander.rio.mbanking.app.ui.constants.DebinConstants;
import ar.com.santander.rio.mbanking.app.ui.constants.WomenProgramConstants.OPERATION_FLAG;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment;
import ar.com.santander.rio.mbanking.components.IsbanDialogFragment.IDialogListener;
import ar.com.santander.rio.mbanking.services.soap.beans.ListaUsuarios;
import ar.com.santander.rio.mbanking.services.soap.beans.Tarjeta;
import ar.com.santander.rio.mbanking.services.soap.beans.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramaWomenUtils {
    public static void showDialogConfirmationABSubscription(final ProgramaWomenActivity programaWomenActivity, final String str, final List<Tarjeta> list) {
        IsbanDialogFragment newInstance = IsbanDialogFragment.newInstance(DebinConstants.DIALOG_CONFIRMATION_ABM_PREAUTOZICACIONE, "Confirmar", a(programaWomenActivity, str), null, null, programaWomenActivity.getString(R.string.BTN_DIALOG_ACCEPT), programaWomenActivity.getString(R.string.IDX_ALERT_BTN_CANCEL), null, null);
        newInstance.setDialogListener(new IDialogListener() {
            public void onItemSelected(String str) {
            }

            public void onNegativeButton() {
            }

            public void onSimpleActionButton() {
            }

            public void onPositiveButton() {
                programaWomenActivity.confirmSuscripcion(str, list);
            }
        });
        newInstance.show(programaWomenActivity.getSupportFragmentManager(), DebinConstants.DIALOG_CONFIRMATION_ABM_PREAUTOZICACIONE);
    }

    static String a(Activity activity, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(OPERATION_FLAG.SUSCRIBE, activity.getString(R.string.MSG_USER00530_Women));
        hashMap.put(OPERATION_FLAG.UNSUSCRIBE, activity.getString(R.string.MSG_USER00531_Women));
        return (String) hashMap.get(str);
    }

    public static List<Tarjeta> filterTarjetasMarcaWomen(ListaUsuarios listaUsuarios) {
        ListaUsuarios listaUsuarios2 = new ListaUsuarios();
        ArrayList arrayList = new ArrayList();
        for (Usuario usuario : listaUsuarios.getUsuario()) {
            List<Tarjeta> tarjeta = usuario.getListaTarjetas().getTarjeta();
            ArrayList arrayList2 = new ArrayList();
            for (Tarjeta tarjeta2 : tarjeta) {
                if (tarjeta2.getMarcaWomen().equalsIgnoreCase("1")) {
                    arrayList.add(tarjeta2);
                    arrayList2.add(tarjeta2);
                }
            }
            listaUsuarios2.getUsuario().add(usuario);
        }
        return arrayList;
    }

    public static List<String> filterUsersMarcaWomen(ListaUsuarios listaUsuarios) {
        ArrayList arrayList = new ArrayList();
        for (Usuario usuario : listaUsuarios.getUsuario()) {
            for (Tarjeta marcaWomen : usuario.getListaTarjetas().getTarjeta()) {
                if (marcaWomen.getMarcaWomen().equalsIgnoreCase("1") && !arrayList.contains(usuario.getNombre())) {
                    arrayList.add(usuario.getNombre());
                }
            }
        }
        return arrayList;
    }

    public static HashMap<String, List<Tarjeta>> filterUsuariosConTarjetasMarcaWomen(ListaUsuarios listaUsuarios) {
        HashMap<String, List<Tarjeta>> hashMap = new HashMap<>();
        for (Usuario usuario : listaUsuarios.getUsuario()) {
            List<Tarjeta> tarjeta = usuario.getListaTarjetas().getTarjeta();
            ArrayList arrayList = new ArrayList();
            for (Tarjeta tarjeta2 : tarjeta) {
                if (tarjeta2.getMarcaWomen().equalsIgnoreCase("1")) {
                    arrayList.add(tarjeta2);
                    hashMap.put(usuario.getNombre(), arrayList);
                }
            }
        }
        return hashMap;
    }

    public static List<String> filterListDataHeader(ListaUsuarios listaUsuarios) {
        ArrayList arrayList = new ArrayList();
        for (Usuario nombre : listaUsuarios.getUsuario()) {
            arrayList.add(nombre.getNombre());
        }
        return arrayList;
    }

    public static HashMap<String, List<Tarjeta>> filterListDataChild(ListaUsuarios listaUsuarios) {
        HashMap<String, List<Tarjeta>> hashMap = new HashMap<>();
        for (Usuario usuario : listaUsuarios.getUsuario()) {
            hashMap.put(usuario.getNombre(), usuario.getListaTarjetas().getTarjeta());
        }
        return hashMap;
    }
}
