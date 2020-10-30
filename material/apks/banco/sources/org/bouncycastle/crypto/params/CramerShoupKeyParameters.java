package org.bouncycastle.crypto.params;

public class CramerShoupKeyParameters extends AsymmetricKeyParameter {
    private CramerShoupParameters b;

    protected CramerShoupKeyParameters(boolean z, CramerShoupParameters cramerShoupParameters) {
        super(z);
        this.b = cramerShoupParameters;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof CramerShoupKeyParameters)) {
            return false;
        }
        CramerShoupKeyParameters cramerShoupKeyParameters = (CramerShoupKeyParameters) obj;
        if (this.b != null) {
            return this.b.equals(cramerShoupKeyParameters.getParameters());
        }
        if (cramerShoupKeyParameters.getParameters() == null) {
            z = true;
        }
        return z;
    }

    public CramerShoupParameters getParameters() {
        return this.b;
    }

    public int hashCode() {
        boolean z = !isPrivate();
        return this.b != null ? z ^ this.b.hashCode() ? 1 : 0 : z ? 1 : 0;
    }
}
