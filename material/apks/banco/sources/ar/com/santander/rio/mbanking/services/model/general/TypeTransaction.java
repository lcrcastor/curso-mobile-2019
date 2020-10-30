package ar.com.santander.rio.mbanking.services.model.general;

public class TypeTransaction {
    public String label;
    public String value;

    public TypeTransaction() {
    }

    public TypeTransaction(String str, String str2) {
        this.label = str;
        this.value = str2;
    }
}
