package ar.com.santander.rio.mbanking.components;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseDialogFragment;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.view.DatePicker;
import ar.com.santander.rio.mbanking.view.DatePicker.IDatePickerDialogListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsbanDatePickerDialogFragment extends BaseDialogFragment implements OnClickListener, IDatePickerDialogListener {
    private static String ag = "title";
    private static String ah = "date";
    private static String ai = "dateFormat";
    @InjectView(2131364117)
    Button aceptarButton;
    String ad;
    String ae;
    String af;
    private IDateDialogListener aj;
    @InjectView(2131364242)
    Button cancelButton;
    @InjectView(2131364572)
    DatePicker datePicker;
    @InjectView(2131364619)
    TextView titleView;

    public interface IDateDialogListener {
        void onDateSelected(Date date);
    }

    public void onBackPressed() {
    }

    public void onDateUpdated(Date date) {
        String format = new SimpleDateFormat("dd/MM/yyyy").format(date);
        FragmentActivity activity = getActivity();
        StringBuilder sb = new StringBuilder();
        sb.append("FECHA SELECCIONADA ");
        sb.append(format);
        CAccessibility.sendAccessibilityEvent(activity, sb.toString());
    }

    public static IsbanDatePickerDialogFragment newInstance(String str) {
        IsbanDatePickerDialogFragment isbanDatePickerDialogFragment = new IsbanDatePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ag, str);
        isbanDatePickerDialogFragment.setArguments(bundle);
        return isbanDatePickerDialogFragment;
    }

    public static IsbanDatePickerDialogFragment newInstance(String str, String str2, String str3) {
        IsbanDatePickerDialogFragment isbanDatePickerDialogFragment = new IsbanDatePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ag, str);
        bundle.putString(ah, str2);
        bundle.putString(ai, str3);
        isbanDatePickerDialogFragment.setArguments(bundle);
        return isbanDatePickerDialogFragment;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        y();
        Builder builder = new Builder(getActivity());
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.date_dialog_layout, null);
        ButterKnife.inject((Object) this, inflate);
        builder.setView(inflate);
        this.titleView.setText(this.ad);
        if (this.ae != null) {
            this.datePicker.setSelectedDate(this.ae, this.af);
        }
        this.cancelButton.setOnClickListener(this);
        this.aceptarButton.setOnClickListener(this);
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

    private void y() {
        this.ad = getArguments().getString(ag);
        this.ae = getArguments().getString(ah);
        this.af = getArguments().getString(ai);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.aceptarButton) {
            Date date = this.datePicker.getDate();
            if (this.aj != null) {
                String format = new SimpleDateFormat("dd/MM/yyyy").format(date);
                FragmentActivity activity = getActivity();
                StringBuilder sb = new StringBuilder();
                sb.append("FECHA SELECCIONADA ");
                sb.append(format);
                CAccessibility.sendAccessibilityEvent(activity, sb.toString());
                this.aj.onDateSelected(date);
            }
            dismiss();
        } else if (view.getId() == R.id.cancelButton) {
            dismiss();
        }
    }

    public IDateDialogListener getDialogListener() {
        return this.aj;
    }

    public void setDialogListener(IDateDialogListener iDateDialogListener) {
        this.aj = iDateDialogListener;
    }
}
