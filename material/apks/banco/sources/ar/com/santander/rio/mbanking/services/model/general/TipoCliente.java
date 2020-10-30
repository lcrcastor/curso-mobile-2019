package ar.com.santander.rio.mbanking.services.model.general;

public enum TipoCliente {
    RTL,
    BP,
    BP_RTL;

    public String getTipoCliente() {
        switch (this) {
            case RTL:
                return "RTL";
            case BP:
                return "BP";
            case BP_RTL:
                return "BP/RTL";
            default:
                return "RTL";
        }
    }
}
