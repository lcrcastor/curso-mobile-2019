package org.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.jce.MultiCertStoreParameters;

public class MultiCertStoreSpi extends CertStoreSpi {
    private MultiCertStoreParameters a;

    public MultiCertStoreSpi(CertStoreParameters certStoreParameters) {
        super(certStoreParameters);
        if (!(certStoreParameters instanceof MultiCertStoreParameters)) {
            StringBuilder sb = new StringBuilder();
            sb.append("org.bouncycastle.jce.provider.MultiCertStoreSpi: parameter must be a MultiCertStoreParameters object\n");
            sb.append(certStoreParameters.toString());
            throw new InvalidAlgorithmParameterException(sb.toString());
        }
        this.a = (MultiCertStoreParameters) certStoreParameters;
    }

    public Collection engineGetCRLs(CRLSelector cRLSelector) {
        boolean searchAllStores = this.a.getSearchAllStores();
        List arrayList = searchAllStores ? new ArrayList() : Collections.EMPTY_LIST;
        for (CertStore cRLs : this.a.getCertStores()) {
            Collection cRLs2 = cRLs.getCRLs(cRLSelector);
            if (searchAllStores) {
                arrayList.addAll(cRLs2);
            } else if (!cRLs2.isEmpty()) {
                return cRLs2;
            }
        }
        return arrayList;
    }

    public Collection engineGetCertificates(CertSelector certSelector) {
        boolean searchAllStores = this.a.getSearchAllStores();
        List arrayList = searchAllStores ? new ArrayList() : Collections.EMPTY_LIST;
        for (CertStore certificates : this.a.getCertStores()) {
            Collection certificates2 = certificates.getCertificates(certSelector);
            if (searchAllStores) {
                arrayList.addAll(certificates2);
            } else if (!certificates2.isEmpty()) {
                return certificates2;
            }
        }
        return arrayList;
    }
}
