package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class TarjetaLimites {
    @SerializedName("document")
    DocumentLimites document;

    public TarjetaLimites(DocumentLimites documentLimites) {
        this.document = documentLimites;
    }

    public TarjetaLimites() {
    }

    public DocumentLimites getDocument() {
        return this.document;
    }

    public void setDocument(DocumentLimites documentLimites) {
        this.document = documentLimites;
    }
}
