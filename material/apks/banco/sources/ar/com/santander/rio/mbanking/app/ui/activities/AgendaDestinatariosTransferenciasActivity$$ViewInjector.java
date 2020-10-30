package ar.com.santander.rio.mbanking.app.ui.activities;

import android.widget.ListView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.view.ClearableEditText;
import butterknife.ButterKnife.Finder;

public class AgendaDestinatariosTransferenciasActivity$$ViewInjector {
    public static void inject(Finder finder, AgendaDestinatariosTransferenciasActivity agendaDestinatariosTransferenciasActivity, Object obj) {
        agendaDestinatariosTransferenciasActivity.lstDestinatarios = (ListView) finder.findRequiredView(obj, R.id.listAgendaDestinatarios, "field 'lstDestinatarios'");
        agendaDestinatariosTransferenciasActivity.clrFilter = (ClearableEditText) finder.findRequiredView(obj, R.id.search, "field 'clrFilter'");
    }

    public static void reset(AgendaDestinatariosTransferenciasActivity agendaDestinatariosTransferenciasActivity) {
        agendaDestinatariosTransferenciasActivity.lstDestinatarios = null;
        agendaDestinatariosTransferenciasActivity.clrFilter = null;
    }
}
