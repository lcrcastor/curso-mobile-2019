package org.bouncycastle.asn1.x509;

import java.util.Vector;

public class GeneralNamesBuilder {
    private Vector a = new Vector();

    public GeneralNamesBuilder addName(GeneralName generalName) {
        this.a.addElement(generalName);
        return this;
    }

    public GeneralNamesBuilder addNames(GeneralNames generalNames) {
        GeneralName[] names = generalNames.getNames();
        for (int i = 0; i != names.length; i++) {
            this.a.addElement(names[i]);
        }
        return this;
    }

    public GeneralNames build() {
        GeneralName[] generalNameArr = new GeneralName[this.a.size()];
        for (int i = 0; i != generalNameArr.length; i++) {
            generalNameArr[i] = (GeneralName) this.a.elementAt(i);
        }
        return new GeneralNames(generalNameArr);
    }
}
