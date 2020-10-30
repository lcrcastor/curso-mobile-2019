package ar.com.santander.rio.mbanking.components;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseDialogFragment;
import java.util.Date;

public class FormularioDialgoFragment extends BaseDialogFragment {

    public interface IDateDialogListener {
        void onDateSelected(Date date);
    }

    private void y() {
    }

    public void onBackPressed() {
    }

    public static FormularioDialgoFragment newInstance() {
        return new FormularioDialgoFragment();
    }

    public Dialog onCreateDialog(Bundle bundle) {
        y();
        Builder builder = new Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.formulario_dialog_layout, null));
        return builder.create();
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().requestWindowFeature(1);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onResume() {
        super.onResume();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }
}
