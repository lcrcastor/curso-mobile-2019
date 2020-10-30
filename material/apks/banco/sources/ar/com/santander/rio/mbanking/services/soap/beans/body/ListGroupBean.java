package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.text.Html;
import com.google.gson.annotations.SerializedName;
import com.jmperezra.accordion_multilevel.items.BaseItemState;
import com.jmperezra.accordion_multilevel.items.ItemState;
import java.util.ArrayList;
import java.util.List;

public class ListGroupBean extends BaseItemState {
    private List<ItemState> children;
    @SerializedName("codigo")
    public String code;
    @SerializedName("descripcion")
    private String description;

    public Integer getId() {
        return null;
    }

    public String getDescription() {
        return this.description;
    }

    public ListGroupBean() {
    }

    public ListGroupBean(String str, String str2) {
        this.code = str;
        this.description = str2;
    }

    public String getCode() {
        return this.code;
    }

    public String getLabel() {
        return Html.fromHtml(this.description).toString();
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
}
