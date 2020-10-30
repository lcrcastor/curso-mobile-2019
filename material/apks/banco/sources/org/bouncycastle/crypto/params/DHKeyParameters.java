package org.bouncycastle.crypto.params;

public class DHKeyParameters extends AsymmetricKeyParameter {
    private DHParameters b;

    protected DHKeyParameters(boolean z, DHParameters dHParameters) {
        super(z);
        this.b = dHParameters;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DHKeyParameters)) {
            return false;
        }
        DHKeyParameters dHKeyParameters = (DHKeyParameters) obj;
        if (this.b != null) {
            return this.b.equals(dHKeyParameters.getParameters());
        }
        if (dHKeyParameters.getParameters() == null) {
            z = true;
        }
        return z;
    }

    public DHParameters getParameters() {
        return this.b;
    }

    public int hashCode() {
        boolean z = !isPrivate();
        return this.b != null ? z ^ this.b.hashCode() ? 1 : 0 : z ? 1 : 0;
    }
}
