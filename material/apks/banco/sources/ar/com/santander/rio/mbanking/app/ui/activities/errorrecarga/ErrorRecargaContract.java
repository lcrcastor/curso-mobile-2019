package ar.com.santander.rio.mbanking.app.ui.activities.errorrecarga;

import ar.com.santander.rio.mbanking.app.base.IBasePresenter;
import ar.com.santander.rio.mbanking.app.base.IBaseView;

interface ErrorRecargaContract {

    public interface Presenter extends IBasePresenter<View> {
        void getErrorRecargaMsg(String str, String str2);
    }

    public interface View extends IBaseView {
        void setErrorRecargaView(String str, String str2);
    }
}
