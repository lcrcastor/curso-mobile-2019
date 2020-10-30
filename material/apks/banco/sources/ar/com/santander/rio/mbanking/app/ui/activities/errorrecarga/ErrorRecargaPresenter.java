package ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga;

import android.text.Html;
import ar.com.santander.rio.mbanking.app.base.BasePresenter;
import ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga.ErrorRecargaContract.Presenter;
import ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga.ErrorRecargaContract.View;
import ar.com.santander.rio.mbanking.managers.data.IDataManager;
import com.squareup.otto.Bus;

public class ErrorRecargaPresenter extends BasePresenter<View> implements Presenter {
    public ErrorRecargaPresenter(Bus bus, IDataManager iDataManager) {
        super(bus, iDataManager);
    }

    public void getErrorRecargaMsg(String str, String str2) {
        ((View) this.mBaseView).setErrorRecargaView(Html.fromHtml(str).toString(), Html.fromHtml(str2).toString());
    }
}
