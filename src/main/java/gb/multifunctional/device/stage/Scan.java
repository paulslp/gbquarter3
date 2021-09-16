package gb.multifunctional.device.stage;

import gb.multifunctional.device.Device;
import gb.multifunctional.device.Document;

public class Scan extends Stage {

    public Scan(Document document, Device device) {
        super(device.getScanSpeed());
        this.description = "Сканирование " + document.getListCount() + " листов";
    }
}
