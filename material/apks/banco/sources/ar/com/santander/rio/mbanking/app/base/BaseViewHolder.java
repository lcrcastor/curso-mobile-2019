package ar.com.santander.rio.mbanking.app.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import butterknife.ButterKnife;

public abstract class BaseViewHolder extends ViewHolder implements OnClickListener {
    public BaseViewHolder(@NonNull View view) {
        super(view);
        ButterKnife.inject((Object) this, view);
        view.setOnClickListener(this);
    }
}
