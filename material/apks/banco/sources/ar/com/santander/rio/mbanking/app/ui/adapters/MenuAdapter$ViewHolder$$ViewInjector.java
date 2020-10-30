package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import butterknife.ButterKnife.Finder;

public class MenuAdapter$ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder viewHolder, Object obj) {
        viewHolder.menuText = (TextView) finder.findRequiredView(obj, R.id.menuText, "field 'menuText'");
        viewHolder.nameUser = (TextView) finder.findRequiredView(obj, R.id.nameUser, "field 'nameUser'");
        viewHolder.ultimaConexion = (TextView) finder.findRequiredView(obj, R.id.ultimaConexion, "field 'ultimaConexion'");
        viewHolder.verticalLines = finder.findRequiredView(obj, R.id.verticalLines, "field 'verticalLines'");
        viewHolder.menuItem = (LinearLayout) finder.findRequiredView(obj, R.id.menu_item, "field 'menuItem'");
        viewHolder.menuItemLogado = (LinearLayout) finder.findRequiredView(obj, R.id.menu_item_logado, "field 'menuItemLogado'");
        viewHolder.menuItemDesconectar = (LinearLayout) finder.findRequiredView(obj, R.id.menu_item_desconectar, "field 'menuItemDesconectar'");
        viewHolder.viewNumNotificaciones = (LinearLayout) finder.findRequiredView(obj, R.id.viewNumNotificaciones, "field 'viewNumNotificaciones'");
        viewHolder.numNotificaciones = (TextView) finder.findRequiredView(obj, R.id.numNotificaciones, "field 'numNotificaciones'");
    }

    public static void reset(ViewHolder viewHolder) {
        viewHolder.menuText = null;
        viewHolder.nameUser = null;
        viewHolder.ultimaConexion = null;
        viewHolder.verticalLines = null;
        viewHolder.menuItem = null;
        viewHolder.menuItemLogado = null;
        viewHolder.menuItemDesconectar = null;
        viewHolder.viewNumNotificaciones = null;
        viewHolder.numNotificaciones = null;
    }
}
