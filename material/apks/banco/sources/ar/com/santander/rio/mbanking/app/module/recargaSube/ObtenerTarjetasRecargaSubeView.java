package ar.com.santander.rio.mbanking.app.module.recargaSube;

import ar.com.santander.rio.mbanking.app.base.IBaseView;
import java.util.List;

public interface ObtenerTarjetasRecargaSubeView extends IBaseView {
    void callErrorScreen(String str, String str2);

    void getTarjetas(List list, List list2, List list3, String str, String str2, String str3);

    void goToRegistrarSube(String str, String str2);
}
