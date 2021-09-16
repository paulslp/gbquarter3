package gb.multifunctional.device;

public class Device {

    int printSpeed;

    int scanSpeed;

    int sendEmailSpeed;

    public Device(int printSpeed, int scanSpeed, int sendEmailSpeed) {
        this.printSpeed = printSpeed;
        this.scanSpeed = scanSpeed;
        this.sendEmailSpeed = sendEmailSpeed;
    }

    public int getPrintSpeed() {
        return printSpeed;
    }

    public int getScanSpeed() {
        return scanSpeed;
    }

    public int getSendEmailSpeed() {
        return sendEmailSpeed;
    }
}
