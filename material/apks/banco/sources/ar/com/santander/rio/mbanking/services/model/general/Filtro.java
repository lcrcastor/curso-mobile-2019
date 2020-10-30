package ar.com.santander.rio.mbanking.services.model.general;

import android.text.Html;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ClasificatorValueBean;
import com.jmperezra.accordion_multilevel.items.BaseItemState;
import com.jmperezra.accordion_multilevel.items.ItemState;
import com.jmperezra.accordion_multilevel.items.styles.DefaultItemStyle;
import com.jmperezra.accordion_multilevel.items.styles.StyleItem;
import java.util.ArrayList;
import java.util.List;

public class Filtro extends BaseItemState {
    private List<ItemState> children;
    public ClasificatorValueBean clasificatorBean;

    /* renamed from: id reason: collision with root package name */
    private Integer f247id;

    public Filtro(ClasificatorValueBean clasificatorValueBean) {
        this.clasificatorBean = clasificatorValueBean;
    }

    public Filtro() {
        this.clasificatorBean = new ClasificatorValueBean();
    }

    public String getLabel() {
        return Html.fromHtml(this.clasificatorBean.name).toString();
    }

    public Integer getId() {
        return this.f247id;
    }

    public List<ItemState> getChildren() {
        return this.children;
    }

    public void addChild(ItemState itemState) {
        if (this.children == null) {
            this.children = new ArrayList();
        }
        this.children.add(itemState);
    }

    public StyleItem getStyleItem() {
        if (this.styleItem == null) {
            return new DefaultItemStyle();
        }
        return this.styleItem;
    }
}
