package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.Destino;
import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import com.google.gson.annotations.SerializedName;

public class ModificarSuscripcionnBodyRequestBean {
    @SerializedName("Destinos")
    public Destinos destinos;
    @SerializedName("Nup")
    public String nup;

    public ModificarSuscripcionnBodyRequestBean(Destinos destinos2) {
        this.destinos = destinos2;
    }

    public String getXMLRequest() {
        StringBuilder sb = new StringBuilder();
        sb.append("<body><Nup>");
        sb.append(this.nup);
        sb.append("</Nup>");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("<Destinos>");
        String sb4 = sb3.toString();
        int i = 0;
        while (i < this.destinos.getDestinos().size()) {
            Destino destino = (Destino) this.destinos.getDestinos().get(i);
            i++;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append("<destino Id=\"");
            sb5.append(i);
            sb5.append("\">");
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(sb6);
            sb7.append("<destinoTipo>");
            sb7.append(destino.getDestinoTipo());
            sb7.append("</destinoTipo>");
            String sb8 = sb7.toString();
            StringBuilder sb9 = new StringBuilder();
            sb9.append(sb8);
            sb9.append("<destinoSecuencia>");
            sb9.append(destino.getDestinoSecuencia());
            sb9.append("</destinoSecuencia>");
            String sb10 = sb9.toString();
            StringBuilder sb11 = new StringBuilder();
            sb11.append(sb10);
            sb11.append("<destinoDescripcion>");
            sb11.append(destino.getDestinoDescripcion());
            sb11.append("</destinoDescripcion>");
            String sb12 = sb11.toString();
            StringBuilder sb13 = new StringBuilder();
            sb13.append(sb12);
            sb13.append("<destinoEmpresaCel>");
            sb13.append(destino.getDestinoEmpresaCel());
            sb13.append("</destinoEmpresaCel>");
            String sb14 = sb13.toString();
            StringBuilder sb15 = new StringBuilder();
            sb15.append(sb14);
            sb15.append("<codOperacion>");
            sb15.append(destino.getCodOperacion());
            sb15.append("</codOperacion>");
            String sb16 = sb15.toString();
            StringBuilder sb17 = new StringBuilder();
            sb17.append(sb16);
            sb17.append("</destino>");
            sb4 = sb17.toString();
        }
        StringBuilder sb18 = new StringBuilder();
        sb18.append(sb4);
        sb18.append("</Destinos>");
        String sb19 = sb18.toString();
        StringBuilder sb20 = new StringBuilder();
        sb20.append(sb19);
        sb20.append("</body>");
        return sb20.toString();
    }
}
