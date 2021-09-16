package gb.multifunctional.device.stage;

import gb.multifunctional.device.Device;
import gb.multifunctional.device.Document;

public class SendEmail extends Stage {
    public SendEmail(Document document, Device device) {
        super(device.getSendEmailSpeed());
        this.description = "Отправка электронного документа " + document.getListCount() + " листов";
    }
}
