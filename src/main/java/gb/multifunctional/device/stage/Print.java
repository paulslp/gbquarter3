package gb.multifunctional.device.stage;

import gb.multifunctional.device.Device;
import gb.multifunctional.device.Document;

public class Print extends Stage {
    public Print(Document document, Device device) {
        super(device.getPrintSpeed());
        this.description = "Печать " + document.getListCount() + " листов";
    }

}
