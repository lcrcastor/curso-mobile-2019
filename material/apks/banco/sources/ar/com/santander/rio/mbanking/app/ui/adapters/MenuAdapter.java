package ar.com.santander.rio.mbanking.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.app.ui.Model.MenuOption;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;
import java.util.StringTokenizer;

public class MenuAdapter extends ArrayAdapter<MenuOption> {
    private List<MenuOption> a;
    private LayoutInflater b;
    private Context c;
    /* access modifiers changed from: private */
    public MenuActionsListener d;

    public interface MenuActionsListener {
        void onClickItem(int i);
    }

    static class ViewHolder {
        @InjectView(2131365225)
        LinearLayout menuItem;
        @InjectView(2131365226)
        LinearLayout menuItemDesconectar;
        @InjectView(2131365227)
        LinearLayout menuItemLogado;
        @InjectView(2131365224)
        TextView menuText;
        @InjectView(2131365251)
        TextView nameUser;
        @InjectView(2131365271)
        TextView numNotificaciones;
        @InjectView(2131366297)
        TextView ultimaConexion;
        @InjectView(2131366359)
        View verticalLines;
        @InjectView(2131366405)
        LinearLayout viewNumNotificaciones;

        public ViewHolder(View view) {
            ButterKnife.inject((Object) this, view);
        }
    }

    public MenuAdapter(Context context, int i, List<MenuOption> list, MenuActionsListener menuActionsListener) {
        super(context, i, list);
        this.a = list;
        this.b = LayoutInflater.from(context);
        this.c = context;
        this.d = menuActionsListener;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        MenuOption menuOption = (MenuOption) this.a.get(i);
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = this.b.inflate(R.layout.menu_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MenuAdapter.this.d != null) {
                    MenuAdapter.this.d.onClickItem(i);
                }
            }
        });
        viewHolder.menuText.setText(menuOption.getTitle());
        a(viewHolder, menuOption);
        return view;
    }

    private void a(ViewHolder viewHolder, MenuOption menuOption) {
        switch (menuOption.getOptionType()) {
            case PRIVATE_ACCESS:
            case HOME:
            case OPTION:
                e(viewHolder, menuOption);
                return;
            case DISCONNECT:
                c(viewHolder, menuOption);
                return;
            case NAME:
                b(viewHolder, menuOption);
                return;
            case RESTRICTED_NO_CONNECTION:
                d(viewHolder, menuOption);
                return;
            default:
                return;
        }
    }

    private void b(ViewHolder viewHolder, MenuOption menuOption) {
        viewHolder.menuItemLogado.setVisibility(0);
        viewHolder.menuItem.setVisibility(8);
        viewHolder.menuItemDesconectar.setVisibility(8);
        viewHolder.nameUser.setText(menuOption.getTitle());
        viewHolder.ultimaConexion.setContentDescription(a(menuOption.getSubtitle(), menuOption.getSubtitle2()));
        TextView textView = viewHolder.ultimaConexion;
        StringBuilder sb = new StringBuilder();
        sb.append(menuOption.getSubtitle());
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(menuOption.getSubtitle2());
        textView.setText(sb.toString());
    }

    private String a(String str, String str2) {
        StringTokenizer stringTokenizer = new StringTokenizer(str2);
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            if (i == 0) {
                i++;
                StringBuilder sb = new StringBuilder();
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(stringTokenizer.nextToken());
                str = str.concat(sb.toString());
            } else if (i == 1) {
                i++;
                CAccessibility cAccessibility = new CAccessibility(this.c);
                try {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(UtilsCuentas.SEPARAOR2);
                    sb2.append(cAccessibility.applyFilterTime(stringTokenizer.nextToken()));
                    str = str.concat(sb2.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    private void c(ViewHolder viewHolder, MenuOption menuOption) {
        viewHolder.menuItemLogado.setVisibility(8);
        viewHolder.menuItem.setVisibility(8);
        viewHolder.menuItemDesconectar.setVisibility(0);
    }

    private void d(ViewHolder viewHolder, MenuOption menuOption) {
        viewHolder.menuItemLogado.setVisibility(8);
        viewHolder.menuItem.setVisibility(0);
        viewHolder.menuItemDesconectar.setVisibility(8);
        viewHolder.verticalLines.setVisibility(4);
        TextView textView = viewHolder.menuText;
        StringBuilder sb = new StringBuilder();
        sb.append("Opción deshabilitada ");
        sb.append(viewHolder.menuText.getText());
        textView.setContentDescription(sb.toString());
        viewHolder.menuText.setTextColor(this.c.getResources().getColor(R.color.white));
        viewHolder.menuItem.setBackgroundColor(this.c.getResources().getColor(R.color.grey_medium_light));
    }

    private void e(ViewHolder viewHolder, MenuOption menuOption) {
        viewHolder.menuItemLogado.setVisibility(8);
        viewHolder.menuItem.setVisibility(0);
        viewHolder.menuItemDesconectar.setVisibility(8);
        if (menuOption.isSelected() || menuOption.isPressed()) {
            viewHolder.verticalLines.setVisibility(0);
            if (menuOption.isSelected()) {
                TextView textView = viewHolder.menuText;
                StringBuilder sb = new StringBuilder();
                sb.append("Seleccionado ");
                sb.append(viewHolder.menuText.getText());
                textView.setContentDescription(sb.toString());
            }
            if (menuOption.isPressed()) {
                TextView textView2 = viewHolder.menuText;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Presionado ");
                sb2.append(viewHolder.menuText.getText());
                textView2.setContentDescription(sb2.toString());
            }
            viewHolder.menuText.setTextColor(this.c.getResources().getColor(R.color.red_light));
            viewHolder.menuItem.setBackgroundColor(this.c.getResources().getColor(R.color.white));
        } else {
            viewHolder.verticalLines.setVisibility(4);
            viewHolder.menuText.setContentDescription(viewHolder.menuText.getText());
            viewHolder.menuText.setTextColor(this.c.getResources().getColor(R.color.white));
            viewHolder.menuItem.setBackgroundColor(this.c.getResources().getColor(R.color.grey_dark));
        }
        if (menuOption.getValue() != null) {
            viewHolder.viewNumNotificaciones.setVisibility(0);
            viewHolder.numNotificaciones.setText(menuOption.getValue());
            return;
        }
        viewHolder.viewNumNotificaciones.setVisibility(4);
    }
}
