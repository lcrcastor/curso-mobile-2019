package org.bouncycastle.crypto.params;

public class ElGamalKeyParameters extends AsymmetricKeyParameter {
    private ElGamalParameters b;

    protected ElGamalKeyParameters(boolean z, ElGamalParameters elGamalParameters) {
        super(z);
        this.b = elGamalParameters;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof ElGamalKeyParameters)) {
            return false;
        }
        ElGamalKeyParameters elGamalKeyParameters = (ElGamalKeyParameters) obj;
        if (this.b != null) {
            return this.b.equals(elGamalKeyParameters.getParameters());
        }
        if (elGamalKeyParameters.getParameters() == null) {
            z = true;
        }
        return z;
    }

    public ElGamalParameters getParameters() {
        return this.b;
    }

    public int hashCode() {
        if (this.b != null) {
            return this.b.hashCode();
        }
        return 0;
    }
}
