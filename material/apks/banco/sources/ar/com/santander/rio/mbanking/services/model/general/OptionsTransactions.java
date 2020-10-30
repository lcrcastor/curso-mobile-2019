package ar.com.santander.rio.mbanking.services.model.general;

import com.jmperezra.accordion_multilevel.items.BaseItemState;
import com.jmperezra.accordion_multilevel.items.ItemState;
import java.util.ArrayList;
import java.util.List;

public class OptionsTransactions extends BaseItemState {
    private List<ItemState> children;

    /* renamed from: id reason: collision with root package name */
    public Integer f248id;
    public String label;
    public String value;

    public OptionsTransactions() {
    }

    public OptionsTransactions(String str, String str2) {
        this.label = str2;
        this.value = str;
    }

    public OptionsTransactions(String str, String str2, boolean z) {
        this.label = str2;
        this.value = str;
        setSelected(z);
    }

    public Integer getId() {
        return this.f248id;
    }

    public String getLabel() {
        return this.label;
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
