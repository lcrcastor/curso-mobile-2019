package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.DocumentConsumos;
import com.google.gson.annotations.SerializedName;

public class UltimosConsumosTCBodyResponseBean extends ErrorBodyBean {
    @SerializedName("document")
    private DocumentConsumos document;

    public UltimosConsumosTCBodyResponseBean(DocumentConsumos documentConsumos) {
        this.document = documentConsumos;
    }

    public UltimosConsumosTCBodyResponseBean(String str, DocumentConsumos documentConsumos) {
        super(str);
        this.document = documentConsumos;
    }

    public UltimosConsumosTCBodyResponseBean(String str, String str2, String str3, String str4, String str5, DocumentConsumos documentConsumos) {
        super(str, str2, str3, str4, str5);
        this.document = documentConsumos;
    }

    public UltimosConsumosTCBodyResponseBean() {
    }

    public DocumentConsumos getDocument() {
        return this.document;
    }

    public void setDocument(DocumentConsumos documentConsumos) {
        this.document = documentConsumos;
    }
}
